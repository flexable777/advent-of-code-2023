import Game.CubeSet
import Game.CubeSet.Color

fun main() {
    fun readGames(input: List<String>): List<Game> {
        val games = input.map { s ->
            val stringSplit = s.split(":")
            val gameId = stringSplit.first().replace("Game ", "").toInt()
            val sets = stringSplit[1].trim().split("; ")

            val cubeSets = sets.map { stringSet ->
                val cubes = stringSet.split(", ")
                cubes.map {
                    val (amount, color) = it.split(" ")
                    CubeSet(
                        amount = amount.toInt(),
                        color = Color.valueOf(color.uppercase())
                    )
                }
            }

            Game(
                id = gameId,
                cubeSets = cubeSets
            )

        }
        return games
    }

    fun lessThan(cubeSet: List<CubeSet>, maxAmount: Int, color: Color): Boolean {
        val found = cubeSet.find { it.color == color }
        return if (found != null) {
            found.amount <= maxAmount
        } else true
    }

    fun part1(input: List<String>): Long {
        //read games
        val games = readGames(input)

        return games.filter { game ->
            val reds = game.cubeSets.map { cubeSet ->
                lessThan(cubeSet = cubeSet, maxAmount = 12, color = Color.RED)
            }.all { it }

            val greens = game.cubeSets.map { cubeSet ->
                lessThan(cubeSet = cubeSet, maxAmount = 13, color = Color.GREEN)
            }.all { it }

            val blues = game.cubeSets.map { cubeSet ->
                lessThan(cubeSet = cubeSet, maxAmount = 14, color = Color.BLUE)
            }.all { it }

            reds && greens && blues
        }.sumOf { it.id }.toLong()
    }

    fun amountOf(cubeSet: List<CubeSet>, color: Color): Int {
        val found = cubeSet.find { it.color == color }
        return found?.amount ?: 0
    }

    fun part2(input: List<String>): Long {
        //read games
        val games = readGames(input)

        return games.sumOf { game ->
            val reds = game.cubeSets.maxOf { cubeSet ->
                amountOf(cubeSet = cubeSet, color = Color.RED)
            }

            val greens = game.cubeSets.maxOf { cubeSet ->
                amountOf(cubeSet = cubeSet, color = Color.GREEN)
            }

            val blues = game.cubeSets.maxOf { cubeSet ->
                amountOf(cubeSet = cubeSet, color = Color.BLUE)
            }
            reds * greens * blues
        }.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsStringLines("Day02_test")
    check(part1(testInput) == 8L)
    check(part2(testInput) == 2286L)

    val input = readInputAsStringLines("Day02")
    part1(input).println(1)
    part2(input).println(2)
}

data class Game(
    val id: Int,
    val cubeSets: List<List<CubeSet>>
) {
    data class CubeSet(
        val color: Color,
        val amount: Int,
    ) {
        enum class Color {
            RED, GREEN, BLUE
        }
    }
}