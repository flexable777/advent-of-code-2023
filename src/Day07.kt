fun main() {

    data class Hand(
        val cards: String,
        val bid: Long,
        val jokerRule: Boolean, //quick fix
    ) : Comparable<Hand> {
        fun getHandValue(): Long {
            val characterMapCount = mutableMapOf<Char, Int>()

            //count duplicates
            cards.forEach { card ->
                if (characterMapCount[card] != null) {
                    characterMapCount[card] = (characterMapCount[card]!! + 1)
                } else {
                    characterMapCount[card] = 1
                }
            }

            val currentValue =
                when (characterMapCount.size) {
                    1 -> {
                        7
                    }

                    2 -> {
                        if (characterMapCount.any { it.value == 4 }) {
                            6
                        } else 5
                    }

                    3 -> {
                        if (characterMapCount.any { it.value == 3 }) {
                            4
                        } else 3
                    }

                    4 -> {
                        2
                    }

                    else -> {
                        1
                    }
                }

            val newValueWithJoker = if (jokerRule && characterMapCount.containsKey('J')) {
                when (characterMapCount.size) {
                    1 -> {
                        7
                    }

                    2 -> {
                        7
                    }

                    3 -> {
                        if (characterMapCount['J'] == 2 || characterMapCount['J'] == 3) {
                            6
                        } else if (characterMapCount.any { it.value == 3 } && characterMapCount['J'] != 3) {
                            6
                        } else 5
                    }

                    4 -> {
                        4
                    }

                    else -> {
                        2
                    }
                }
            } else 0L

            return if (newValueWithJoker > currentValue) {
                newValueWithJoker
            } else currentValue.toLong()
        }

        override fun compareTo(other: Hand): Int {
            val handValue1 = getHandValue()
            val handValue2 = other.getHandValue()
            return if (handValue1 != handValue2) {
                handValue1.compareTo(handValue2)
            } else {
                val jokerChar = if (jokerRule) '1' else 'W'
                val cards1 = cards
                    .replace('A', 'Z')
                    .replace('K', 'Y')
                    .replace('Q', 'X')
                    .replace('J', jokerChar)
                    .replace('T', 'V')
                val cards2 = other.cards
                    .replace('A', 'Z')
                    .replace('K', 'Y')
                    .replace('Q', 'X')
                    .replace('J', jokerChar)
                    .replace('T', 'V')
                cards1.compareTo(cards2)
            }
        }
    }

    fun part1(input: List<String>): Long {
        return input.map { s ->
            val (c, b) = s.split(' ')
            Hand(
                cards = c,
                bid = b.toLong(),
                jokerRule = false,
            )
        }.sorted().mapIndexed { index, card ->
            (index + 1) * card.bid
        }.sum()
    }

    fun part2(input: List<String>): Long {
        return input.map { s ->
            val (c, b) = s.split(' ')
            Hand(
                cards = c,
                bid = b.toLong(),
                jokerRule = true,
            )
        }.sorted().mapIndexed { index, card ->
            (index + 1) * card.bid
        }.sum()
    }

    // test if implementation meets criteria from the description:
    val testInput = readInputAsStringLines("Day07_test")
    check(part1(testInput) == 6440L)
    check(part2(testInput) == 5905L)

    val input = readInputAsStringLines("Day07")
    part1(input).println(1) //251058093
    part2(input).println(2) //249781879
}
