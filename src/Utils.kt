import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInputAsStringLines(name: String) = Path("src/$name.txt").readLines()

/**
 * Reads the whole text from the given input txt file.
 */
fun readInputAsString(name: String) = Path("src/$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println(part: Int) = println("Part $part: $this")

enum class Direction {
    NW, N, NE, W, E, SW, S, SE
}

data class Tile(
    val index: Pair<Int, Int>,
    val value: Char,
    val relativePosition: Direction?,

)

fun getNeighbours(input: List<String>, x: Int, y: Int, includeDiagonal: Boolean = true): List<Tile> {
    val n = mutableListOf<Tile>()

    //all ups
    if (x > 0) {
        //check up left
        if (includeDiagonal && y > 0) {
            n += Tile(
                index = (x - 1) to (y - 1),
                value = (input[x - 1][y - 1]),
                relativePosition = Direction.NW,
            )
        }

        //up
        n += Tile(
            index = (x - 1) to (y),
            value = (input[x - 1][y]),
            relativePosition = Direction.N,
        )

        //check up right
        if (includeDiagonal && y < input.first().length - 1) {
            n += Tile(
                index = (x - 1) to (y + 1),
                value = (input[x - 1][y + 1]),
                relativePosition = Direction.NE,
            )
        }
    }

    //check left
    if (y > 0) {
        n += Tile(
            index = (x) to (y - 1),
            value = (input[x][y - 1]),
            relativePosition = Direction.W,
        )
    }

    //check right
    if (y <= input.first().length - 2) {
        n += Tile(
            index = (x) to (y + 1),
            value = (input[x][y + 1]),
            relativePosition = Direction.E,
        )
    }

    //all downs

    if (x < input.size - 1) {
        //check down left
        if (includeDiagonal && y > 0) {
            n += Tile(
                index = (x + 1) to (y - 1),
                value = (input[x + 1][y - 1]),
                relativePosition = Direction.SW,
            )
        }

        //check down
        n += Tile(
            index = (x + 1) to (y),
            value = (input[x + 1][y]),
            relativePosition = Direction.S,
        )

        //check down right
        if (includeDiagonal && y <= input.first().length - 2) {
            n += Tile(
                index = (x + 1) to (y + 1),
                value = (input[x + 1][y + 1]),
                relativePosition = Direction.SE,
            )
        }
    }

    return n
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i])
    }
    return result
}
