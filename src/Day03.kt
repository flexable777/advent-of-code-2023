fun main() {

    fun isPartNumber(input: List<String>, x: Int, y: Int): Boolean {

        //all ups
        if (x > 0) {
            //check up left
            if (y > 0) {
                val value = input[x - 1][y - 1]
                if (!value.isDigit() && value != '.') {
                    return true
                }
            }

            //up
            if (true) {
                val value = input[x - 1][y]
                if (!value.isDigit() && value != '.') {
                    return true
                }
            }

            //check up right
            if (y < input.first().length - 1) {
                val value = input[x - 1][y + 1]
                if (!value.isDigit() && value != '.') {
                    return true
                }
            }
        }

        //check left
        if (y > 0) {
            val value = input[x][y - 1]
            if (!value.isDigit() && value != '.') {
                return true
            }
        }

        //check right
        if (y <= input.first().length - 2) {
            val value = input[x][y + 1]
            if (!value.isDigit() && value != '.') {
                return true
            }
        }

        //all downs

        if (x < input.size - 1) {
            //check down left
            if (y > 0) {
                val value = input[x + 1][y - 1]
                if (!value.isDigit() && value != '.') {
                    return true
                }
            }

            //check down
            if (true) {
                val value = input[x + 1][y]
                if (!value.isDigit() && value != '.') {
                    return true
                }
            }

            //check down right
            if (y <= input.first().length - 2) {
                val value = input[x + 1][y + 1]
                if (!value.isDigit() && value != '.') {
                    return true
                }
            }
        }

        return false
    }

    fun isPartNumberWithStarHit(input: List<String>, x: Int, y: Int): Pair<Pair<Int, Int>, Boolean> {

        //all ups
        if (x > 0) {
            //check up left
            if (y > 0) {
                val value = input[x - 1][y - 1]
                if (value == '*') {
                    return (x - 1) to (y - 1) to true
                }
            }

            //up
            if (true) {
                val value = input[x - 1][y]
                if (value == '*') {
                    return (x - 1) to y to true
                }
            }

            //check up right
            if (y < input.first().length - 1) {
                val value = input[x - 1][y + 1]
                if (value == '*') {
                    return (x - 1) to (y + 1) to true
                }
            }
        }

        //check left
        if (y > 0) {
            val value = input[x][y - 1]
            if (value == '*') {
                return (x) to (y - 1) to true
            }
        }

        //check right
        if (y <= input.first().length - 2) {
            val value = input[x][y + 1]
            if (value == '*') {
                return (x) to (y + 1) to true
            }
        }

        //all downs

        if (x < input.size - 1) {
            //check down left
            if (y > 0) {
                val value = input[x + 1][y - 1]
                if (value == '*') {
                    return (x + 1) to (y - 1) to true
                }
            }

            //check down
            if (true) {
                val value = input[x + 1][y]
                if (value == '*') {
                    return (x + 1) to (y) to true
                }
            }

            //check down right
            if (y <= input.first().length - 2) {
                val value = input[x + 1][y + 1]
                if (value == '*') {
                    return (x + 1) to (y + 1) to true
                }
            }
        }

        //Don't care
        return (x) to (y) to false
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