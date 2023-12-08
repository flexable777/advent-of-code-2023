fun main() {

    fun part1(input: List<String>): Long {
        val map = mutableMapOf<String, Pair<String, String>>()

        input.drop(2).forEach { line ->
            val split = line.split(" = ")
            val (left, right) = split[1].replace("(", "").replace(")", "").split(", ")

            map[split[0]] = left to right
        }
        val instructions = input.first().toList()

        var index = 0
        var steps = 1L

        var current = "AAA"
        val last = "ZZZ"

        while (true) {
            val instruction = instructions[index]

            val destination = if (instruction == 'L') {
                map[current]!!.first
            } else map[current]!!.second

            if (destination == last) {
                break
            }

            current = destination
            steps++

            if (index == instructions.size - 1) {
                index = 0
            } else {
                index++
            }
        }

        return steps
    }

    data class Node(
        val key: String,
        var steps: Long,
        var index: Int,
        var current: String,
    )

    fun part2(input: List<String>): Long {
        val map = mutableMapOf<String, Pair<String, String>>()

        input.drop(2).forEach { line ->
            val split = line.split(" = ")
            val (left, right) = split[1].replace("(", "").replace(")", "").split(", ")

            map[split[0]] = left to right
        }
        val instructions = input.first().toList()

        val startNodes = map.filter { it.key.endsWith("A") }.map {
            Node(
                key = it.key,
                steps = 0,
                index = 0,
                current = it.key,
            )
        }

        startNodes.forEach { node ->
            while (true) {
                val instruction = instructions[node.index]

                val destination = if (instruction == 'L') {
                    map[node.current]!!.first
                } else map[node.current]!!.second

                node.current = destination

                node.steps++

                if (node.index == instructions.size - 1) {
                    node.index = 0
                } else {
                    node.index++
                }

                if (node.current.endsWith("Z")) {
                    break
                }

            }
        }
        return findLCMOfListOfNumbers(startNodes.map { it.steps })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsStringLines("Day08_test")
    val testInput2 = readInputAsStringLines("Day08_test2")
    val testInputPart2 = readInputAsStringLines("Day08_test_part2")
    check(part1(testInput) == 2L)
    check(part1(testInput2) == 6L)
    check(part2(testInputPart2) == 6L)

    val input = readInputAsStringLines("Day08")
    part1(input).println(1)
    part2(input).println(2)
}
