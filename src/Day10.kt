fun main() {
    fun part1(input: List<String>): Long {

        return 1L
    }

    fun part2(input: List<String>): Long {

        return 1L
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsStringLines("Day10_test")
    check(part1(testInput) == 1L)
    check(part2(testInput) == 1L)

    val input = readInputAsStringLines("Day10")
    part1(input).println(1)
    part2(input).println(2)
}
