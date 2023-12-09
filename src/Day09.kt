fun main() {

    fun getLastNumberInSequence(numbers: List<Int>): Long {
        val differences = numbers.zipWithNext().map { pair ->
            val (previous, next) = pair
            next - previous
        }
        return if (!differences.all { it == 0 }) {
            numbers.last().toLong() + getLastNumberInSequence(differences)
        } else {
            numbers.last().toLong() + differences.last()
        }
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { s ->
            val numbers = s.split(' ').map { it.toInt() }
            getLastNumberInSequence(numbers)
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { s ->
            val numbers = s.split(' ').map { it.toInt() }
            getLastNumberInSequence(numbers.reversed())
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsStringLines("Day09_test")
    check(part1(testInput) == 114L)
    check(part2(testInput) == 2L)

    val input = readInputAsStringLines("Day09")
    part1(input).println(1) //1666172641
    part2(input).println(2) //933
}