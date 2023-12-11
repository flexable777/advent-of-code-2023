fun main() {

    fun isPartNumber(input: List<String>, x: Int, y: Int): Boolean {
        return getNeighbours(input = input, x = x, y = y).any { !it.value.isDigit() && it.value != '.' }
    }

    fun isPartNumberWithStarHit(input: List<String>, x: Int, y: Int): Pair<Pair<Int, Int>, Boolean> {
        val toReturn = getNeighbours(input = input, x = x, y = y).find { it.value == '*' }
        return if (toReturn == null) {
            (x to y) to false
        } else {
            toReturn.index to true
        }
    }

    fun part1(input: List<String>): Long {

        var result = 0L

        for ((x, row) in input.withIndex()) {
            var currentNumber = ""
            var currentNumberIsPartOfEngine = false
            for ((y, char) in row.withIndex()) {
                if (char.isDigit()) {
                    currentNumber += char
                    if (isPartNumber(input, x, y)) {
                        currentNumberIsPartOfEngine = true
                    }
                } else {
                    if (currentNumber.isNotEmpty() && currentNumberIsPartOfEngine) {
                        result += currentNumber.toLong()
                    }
                    currentNumber = ""
                    currentNumberIsPartOfEngine = false
                }
            }
            if (currentNumber.isNotEmpty() && currentNumberIsPartOfEngine) {
                result += currentNumber.toLong()
            }
        }

        return result
    }

    fun part2(input: List<String>): Long {
        val map = mutableMapOf<Pair<Int, Int>, MutableList<Long>>()

        for ((x, row) in input.withIndex()) {
            var currentNumber = ""
            var currentNumberIsPartOfEngine = false
            var currentStarHit: Pair<Int, Int>? = null
            for ((y, char) in row.withIndex()) {
                if (char.isDigit()) {
                    currentNumber += char
                    val (coordinate, wasHit) = isPartNumberWithStarHit(input, x, y)
                    if (wasHit) {
                        currentNumberIsPartOfEngine = true
                        currentStarHit = coordinate
                    }
                } else {
                    if (currentNumber.isNotEmpty() && currentNumberIsPartOfEngine) {
                        if (!map.containsKey(currentStarHit)) {
                            map[currentStarHit!!] = mutableListOf()
                        }
                        map[currentStarHit]?.add(currentNumber.toLong())
                    }
                    currentNumber = ""
                    currentNumberIsPartOfEngine = false
                }
            }
            if (currentNumber.isNotEmpty() && currentNumberIsPartOfEngine) {
                if (!map.containsKey(currentStarHit)) {
                    map[currentStarHit!!] = mutableListOf()
                }
                map[currentStarHit]?.add(currentNumber.toLong())
            }
        }

        val sum = map.filter {
            it.value.size == 2
        }.map {
            it.value[0] * it.value[1]
        }.sum()

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsStringLines("Day03_test")
    check(part1(testInput) == 4361L)
    check(part2(testInput) == 467835L)

    val input = readInputAsStringLines("Day03")
    part1(input).println(1)
    part2(input).println(2)
}