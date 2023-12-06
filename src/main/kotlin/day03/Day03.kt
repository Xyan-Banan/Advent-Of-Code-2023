package day03

import readInput
import kotlin.math.abs
import kotlin.test.assertEquals

fun main() {
    assertEquals(part1(readInput("day03/day03_test")), 4361)

    val lines = readInput("day03/day03")
    println(part1(lines))
}

fun part1(lines: List<String>): Int {
    var currentNumber: Int? = null
    var coordinates: List<Coordinate>? = null
    val numbers = mutableListOf<Number>()
    val symbols = mutableListOf<Coordinate>()
    for ((i, line) in lines.withIndex()) {
        for ((j, char) in line.withIndex()) {
            when {
                char.isDigit() -> {
                    currentNumber = (currentNumber ?: 0) * 10 + char.digitToInt()
                    coordinates = (coordinates ?: emptyList()) + Coordinate(i, j)
                }
                else -> {
                    if (currentNumber != null && coordinates != null) {
                        numbers.add(Number(currentNumber, coordinates))
                        currentNumber = null
                        coordinates = null
                    }

                    if (char != '.') symbols.add(Coordinate(i, j))
                }
            }
        }
        if (currentNumber != null && coordinates != null) {
            numbers.add(Number(currentNumber, coordinates))
        }
        currentNumber = null
        coordinates = null
    }
    return numbers.sumOf { number -> if (number.coordinates isAdjacentTo symbols) number.value else 0 }
}

private data class Number(val value: Int, val coordinates: List<Coordinate>)
data class Coordinate(val i: Int, val j: Int)

infix fun Coordinate.isAdjacentTo(other: Coordinate) = abs(i - other.i) <= 1 && abs(j - other.j) <= 1
infix fun List<Coordinate>.isAdjacentTo(other: List<Coordinate>) =
    any { coordinate -> other.any { otherCoordinate -> coordinate isAdjacentTo otherCoordinate } }
