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
