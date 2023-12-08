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

fun getNeighbours(input: List<String>, x: Int, y: Int): List<Pair<Pair<Int, Int>, Char>> {
    val n = mutableListOf<Pair<Pair<Int, Int>, Char>>()

    //all ups
    if (x > 0) {
        //check up left
        if (y > 0) {
            n += (x - 1) to (y - 1) to (input[x - 1][y - 1])
        }

        //up
        n += (x - 1) to (y) to (input[x - 1][y])

        //check up right
        if (y < input.first().length - 1) {
            n += (x - 1) to (y + 1) to (input[x - 1][y + 1])
        }
    }

    //check left
    if (y > 0) {
        n += (x) to (y - 1) to (input[x][y - 1])
    }

    //check right
    if (y <= input.first().length - 2) {
        n += (x) to (y + 1) to (input[x][y + 1])
    }

    //all downs

    if (x < input.size - 1) {
        //check down left
        if (y > 0) {
            n += (x + 1) to (y - 1) to (input[x + 1][y - 1])
        }

        //check down
        n += (x + 1) to (y) to (input[x + 1][y])

        //check down right
        if (y <= input.first().length - 2) {
            n += (x + 1) to (y + 1) to (input[x + 1][y + 1])
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
