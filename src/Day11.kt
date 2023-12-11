import kotlin.math.abs

fun main() {

    fun part1_and_2(input: List<String>, emptySpace: Int): Long {
        val indeciesToAddExtraColumns = mutableListOf<Int>()

        for ((colIndex, col) in input.first().withIndex()) {
            var foundGalaxy = false
            for ((rowIndex, row) in input.withIndex()) {
                if (row[colIndex] == '#') {
                    foundGalaxy = true
                }
            }
            if (!foundGalaxy) {
                indeciesToAddExtraColumns += colIndex
            }
        }

        var rowsAdded = 0
        var colsAdded = 0

        val newCoordinatesForGalaxies = mutableListOf<Pair<Int, Int>>()

        input.forEachIndexed { rowIndex, row ->
            if (!row.contains('#')) {
                rowsAdded++
            }
            row.forEachIndexed { colIndex, col ->
                if (colIndex in indeciesToAddExtraColumns) {
                    colsAdded++
                }
                if (col == '#') {
                    newCoordinatesForGalaxies += (rowIndex + rowsAdded * emptySpace) to (colIndex + colsAdded * emptySpace)
                }
            }
            colsAdded = 0
        }

        val galaxyPairs = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        newCoordinatesForGalaxies.forEach { galaxy1 ->
            newCoordinatesForGalaxies.forEach { galaxy2 ->
                val candidate = Pair(galaxy1, galaxy2)
                val candidate2 = Pair(galaxy2, galaxy1)
                if (galaxy1 != galaxy2 && candidate !in galaxyPairs && candidate2 !in galaxyPairs) {
                    galaxyPairs += candidate
                }
            }
        }

        return galaxyPairs.sumOf { abs(it.first.first.toLong() - it.second.first) + abs(it.first.second - it.second.second) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsStringLines("Day11_test")
    check(part1_and_2(testInput, 1) == 374L)
    check(part1_and_2(testInput, 9) == 1030L)

    val input = readInputAsStringLines("Day11")
    part1_and_2(input, 1).println(1) //9509330
    part1_and_2(input, 999_999).println(2) //635832237682
}
