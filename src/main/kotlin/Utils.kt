import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File( "src/main/kotlin","$name.txt").readLines()
fun readInputAsInts(name: String) = File("src", "$name.txt").readLines().map { it.toInt() }
fun readIntList(name: String, separator: String) = File("src","$name.txt").readText().split(separator).map { it.toInt() }
