package day02

import readInput
import kotlin.math.max
import kotlin.test.assertEquals

private object TestCase {
    const val RED = 12
    const val GREEN = 13
    const val BLUE = 14
}

private data class Game(
    val id: Int,
    var red: Int = 0,
    var green: Int = 0,
    var blue: Int = 0,
)

private fun Game.isPossible() = red <= TestCase.RED && green <= TestCase.GREEN && blue <= TestCase.BLUE
private fun Game.setPower() = red * green * blue

fun main() {
    assertEquals(part1(readInput("day02/day02_test")), 8)
    assertEquals(part2(readInput("day02/day02_test")), 2286)

    val lines = readInput("day02/day02")

    println(part1(lines))
    println(part2(lines))
}

fun part1(lines: List<String>) = lines
    .map { line -> parseString(line) }
    .filter { it.isPossible() }
    .sumOf { it.id }

fun part2(lines: List<String>) = lines.sumOf { line -> parseString(line).setPower() }


private fun parseString(line: String): Game {
    val (title, data) = line.split(": ")
    val game = Game(id = title.split(' ')[1].toInt())
    data.split("; ").forEach { hand ->
        hand.split(", ").forEach { cubes ->
            val (quantity, color) = cubes.split(' ')
            when (color) {
                "red" -> game.red = max(game.red, quantity.toInt())
                "green" -> game.green = max(game.green, quantity.toInt())
                "blue" -> game.blue = max(game.blue, quantity.toInt())
            }
        }
    }
    return game
}


