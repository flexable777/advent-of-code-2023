import kotlin.system.measureTimeMillis


fun main() {
    fun part1(input: String): Long {
        val (line1, line2) = input.lines()

        val times = line1.split(":")[1].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val distances = line2.split(":")[1].split(" ").filter { it.isNotBlank() }.map { it.toInt() }

        var sum = 1L
        times.forEachIndexed { index, lasts ->
            sum *= (0..lasts).map { n ->
                n * (lasts - n)
            }.count { it > distances[index] }
        }
        return sum
    }

    fun part2(input: String): Long {
        val (line1, line2) = input.lines()

        val last = line1.split(":")[1].replace(" ", "").toLong()
        val distance = line2.split(":")[1].replace(" ", "").toLong()

        return (0..last).map { n ->
            n * (last - n)
        }.count { it > distance }.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsString("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInputAsString("Day06")
    part1(input).println(1)
    println("" + measureTimeMillis {
        part2(input).println(2)
    } + " millis for part 2")
}
