import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf { row ->
            val (w, m) = row.split(":")[1].split(" |")

            val winning = w.chunked(size = 3).map { it.trim() }
            val myList = m.chunked(size = 3).map { it.trim() }

            2.0.pow(myList.count { it in winning } - 1).toLong()
        }
    }

    data class Card(
        val id: Int,
        val wins: Int,
    )

    fun countCardsAndCopies(cards: List<Card>, originalCardList: List<Card>, cardCount: Int): Long {
        return cards.sumOf { card ->
            if (card.wins > 0) {
                countCardsAndCopies(
                    cards = originalCardList.subList((card.id), card.id + card.wins),
                    originalCardList = originalCardList,
                    cardCount = cardCount,
                )
            } else 0L
        } + cards.size.toLong()
    }

    fun part2(input: List<String>): Long {
        val cards = input.mapIndexed() { index, row ->
            val (w, m) = row.split(":")[1].split(" |")

            val winning = w.chunked(size = 3).map { it.trim() }
            val myList = m.chunked(size = 3).map { it.trim() }

            Card(
                id = index + 1,
                wins = myList.count { it in winning }
            )
        }
        return countCardsAndCopies(cards, cards, 0).toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsStringLines("Day04_test")
    check(part1(testInput) == 13L)
    check(part2(testInput) == 30L)

    val input = readInputAsStringLines("Day04")
    part1(input).println(1)
    part2(input).println(2)
}
