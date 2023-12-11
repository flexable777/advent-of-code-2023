fun main() {

    fun canConnectTo(from: Tile, to: Tile): Boolean {
        return when (from.value) {
            '|', 'S' -> { //S in main
                when (to.relativePosition) {
                    Direction.N -> {
                        to.value in listOf('|', 'F', '7', 'S')
                    }

                    Direction.W -> {
                        false
                    }

                    Direction.E -> {
                        false
                    }

                    Direction.S -> {
                        to.value in listOf('|', 'J', 'L', 'S')
                    }

                    else -> false
                }
            }
            '-' -> {
                when (to.relativePosition) {
                    Direction.N -> {
                        false
                    }

                    Direction.W -> {
                        to.value in listOf('-', 'L', 'F', 'S')
                    }

                    Direction.E -> {
                        to.value in listOf('-', 'J', '7', 'S')
                    }

                    Direction.S -> {
                        false
                    }

                    else -> false
                }
            }
            'L' -> {
                when (to.relativePosition) {
                    Direction.N -> {
                        to.value in listOf('|', 'F', '7', 'S')
                    }

                    Direction.W -> {
                        false
                    }

                    Direction.E -> {
                        to.value in listOf('-', 'J', '7', 'S')
                    }

                    Direction.S -> {
                        false
                    }

                    else -> false
                }
            }
            'J' -> {
                when (to.relativePosition) {
                    Direction.N -> {
                        to.value in listOf('|', 'F', '7', 'S')
                    }

                    Direction.W -> {
                        to.value in listOf('-', 'L', 'F', 'S')
                    }

                    Direction.E -> {
                        false
                    }

                    Direction.S -> {
                        false
                    }

                    else -> false
                }
            }
            '7' -> {
                when (to.relativePosition) {
                    Direction.N -> {
                        false
                    }

                    Direction.W -> {
                        to.value in listOf('-', 'L', 'F', 'S')
                    }

                    Direction.E -> {
                        false
                    }

                    Direction.S -> {
                        to.value in listOf('|', 'J', 'L', 'S')
                    }

                    else -> false
                }
            }
            'F' -> {
                when (to.relativePosition) {
                    Direction.N -> {
                        false
                    }

                    Direction.W -> {
                        false
                    }

                    Direction.E -> {
                        to.value in listOf('-', 'J', '7', 'S')
                    }

                    Direction.S -> {
                        to.value in listOf('|', 'J', 'L', 'S')
                    }

                    else -> false
                }
            }
            else -> return false
        }
    }

    fun getOnlyPossibleNeighbour(
        grid: List<String>,
        currentTile: Tile,
        previousTile: Tile,
    ): Tile? {
        val neighbours =
            getNeighbours(
                input = grid,
                x = currentTile.index.first,
                y = currentTile.index.second,
                includeDiagonal = false
            )

        val foundNeighbour = neighbours.find { neighbour ->
            neighbour.index != previousTile.index && canConnectTo(
                from = currentTile,
                to = neighbour
            )
        }

        return foundNeighbour
    }

    fun part1(input: List<String>): Long {
        var startIndex: Pair<Int, Int>? = null
        for ((x, row) in input.withIndex()) {
            for ((y, column) in row.withIndex()) {
                if (column == 'S') {
                    startIndex = x to y
                    break
                }
            }
        }

        val startTile = Tile(
            index = startIndex!!,
            value = input[startIndex.first][startIndex.second],
            relativePosition = null
        )

        val neighbours =
            getNeighbours(
                input = input,
                x = startTile.index.first,
                y = startTile.index.second,
                includeDiagonal = false
            ).filter { it.value != '.' }

        val counters = neighbours.map { neighbour ->
            var counter = 1L
            var foundTile: Tile?
            var previous = startTile
            var currentTile = neighbour
            while (true) {
                foundTile = getOnlyPossibleNeighbour(
                    grid = input,
                    currentTile = currentTile,
                    previousTile = previous,
                )

                counter++

                if (foundTile == null) {
                    counter = 1
                    break
                } else if (foundTile.value == 'S') {
                    break
                }
                previous = currentTile
                currentTile = foundTile
            }
            counter / 2
        }

        return counters.max()
    }

    fun printNiceGrid(input: List<String>, toReplace: List<Tile>) {
        val gridWithPaths: List<String> = input.mapIndexed { x, row ->
            row.mapIndexed { y, char ->
                val find = toReplace.find { it.index == x to y }
                if (find != null) {
                    when (find.value) {
                        'F' -> '┎'
                        '-' -> '━'
                        '|' -> '┃'
                        'J' -> '┛'
                        '7' -> '┒'
                        'L' -> '┖'
                        else -> char
                    }
                } else '.'
            }.joinToString("")
        }
        gridWithPaths.forEach { println(it) }
    }

    fun isInsideLoop(coordinate: Pair<Int, Int>, borders: List<Tile>, grid: List<String>, line: String): Boolean {
        var borderCount = 0

        for (i in coordinate.second..<grid.first().length) {
            val c = coordinate.first to i
            val s = borders.find { it.index == c && it.value in listOf('|', '7', 'F', 'S')}
            if (c != coordinate && s != null) {
                borderCount++
            }
        }

        return borderCount > 0 && borderCount % 2 != 0
    }

    fun part2(input: List<String>): Long {
        var startIndex_: Pair<Int, Int>? = null
        for ((x, row) in input.withIndex()) {
            for ((y, column) in row.withIndex()) {
                if (column == 'S') {
                    startIndex_ = x to y
                    break
                }
            }
        }

        val startTile = Tile(
            index = startIndex_!!,
            value = input[startIndex_.first][startIndex_.second],
            relativePosition = null
        )

        val neighbours =
            getNeighbours(
                input = input,
                x = startTile.index.first,
                y = startTile.index.second,
                includeDiagonal = false
            ).filter { it.value != '.' }

        val lists = neighbours.map { neighbour ->
            var foundTile: Tile?
            var previous = startTile
            var currentTile = neighbour
            val path = mutableListOf<Tile>()
            path += previous
            path += currentTile
            while (true) {
                foundTile = getOnlyPossibleNeighbour(
                    grid = input,
                    currentTile = currentTile,
                    previousTile = previous,
                )

                if (foundTile != null) {
                    path += foundTile!!
                }

                if (foundTile == null) {
                    break
                } else if (foundTile!!.value == 'S') {
                    break
                }
                previous = currentTile
                currentTile = foundTile!!
            }
            path
        }

        val finalPath = lists.maxBy { it.size }.dropLast(1)

        printNiceGrid(input, finalPath)

        var count = 0L

        input.forEachIndexed { rowIndex, s ->
            s.forEachIndexed { colIndex, c ->

                if (finalPath.none { it.index == rowIndex to colIndex }) {
                    val v = isInsideLoop(
                        coordinate = rowIndex to colIndex,
                        borders = finalPath,
                        grid = input,
                        line = s,
                    )
                    if (v) {
                        count++
                    }
                }
            }
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsStringLines("Day10_test")
    val testInput2 = readInputAsStringLines("Day10_test2")
//    check(part1(testInput) == 8L)
//    check(part2(testInput2) == 4L)

    val input = readInputAsStringLines("Day10")
//    part1(input).println(1)
    part2(input).println(2)
}
