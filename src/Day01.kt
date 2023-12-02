fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf { row ->
            val digit1 = row.find {
                it.isDigit()
            }
            val digit2 = row.findLast {
                it.isDigit()
            }
            "$digit1$digit2".toLong()
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { row ->
            val num = getNumbers(row)
            num
        }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInputAsStringLines("Day01_part1_test")) == 142L)
    check(part2(readInputAsStringLines("Day01_part2_test")) == 281L)

    val input = readInputAsStringLines("Day01")
    part1(input).println(1)
    part2(input).println(2)
}

fun getNumbers(s: String): Long {
    val numbersAsStrings = listOf(
        "1" to "one",
        "2" to "two",
        "3" to "three",
        "4" to "four",
        "5" to "five",
        "6" to "six",
        "7" to "seven",
        "8" to "eight",
        "9" to "nine",
    )

    val hits = mutableMapOf<Int, Int>()

    numbersAsStrings.forEachIndexed { i, numAsString ->
        val (digit, word) = numAsString

        val indexOfDigit = s.indexOf(digit)
        if (indexOfDigit >= 0) {
            hits[indexOfDigit] = i + 1
        }
        val lastIndexOfDigit = s.lastIndexOf(digit)
        if (lastIndexOfDigit >= 0) {
            hits[lastIndexOfDigit] = i + 1
        }

        val indexOfWord = s.indexOf(word)
        if (indexOfWord >= 0) {
            hits[indexOfWord] = i + 1
        }
        val lastIndexOfWord = s.lastIndexOf(word)
        if (lastIndexOfWord >= 0) {
            hits[lastIndexOfWord] = i + 1
        }
    }

    val numberAsString = hits[hits.minOf { it.key }].toString() + hits[hits.maxOf { it.key }].toString()

    return numberAsString.toLong()
}