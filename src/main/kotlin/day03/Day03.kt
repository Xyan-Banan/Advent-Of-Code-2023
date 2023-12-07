package day03

import readInput
import kotlin.math.abs
import kotlin.test.assertEquals

fun main() {
    assertEquals(part1(readInput("day03/day03_test")), 4361)
    assertEquals(part2(readInput("day03/day03_test")), 467835)

    val lines = readInput("day03/day03")
    println(part1(lines))
    println(part2(lines))
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

fun part2(lines: List<String>): Int {
    var currentNumber: Int? = null
    var coordinates: List<Coordinate>? = null
    val numbers = mutableListOf<Number>()
    val gears = mutableListOf<Coordinate>()
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

                    if (char == '*') gears.add(Coordinate(i, j))
                }
            }
        }
        if (currentNumber != null && coordinates != null) {
            numbers.add(Number(currentNumber, coordinates))
        }
        currentNumber = null
        coordinates = null
    }
    return gears.sumOf { gear ->
        val connections = numbers.filter { it.coordinates.any { it isAdjacentTo gear } }
        if (connections.size == 2) connections[0].value * connections[1].value else 0
    }
}

private data class Number(val value: Int, val coordinates: List<Coordinate>)
data class Coordinate(val i: Int, val j: Int)

private infix fun Coordinate.isAdjacentTo(other: Coordinate) = abs(i - other.i) <= 1 && abs(j - other.j) <= 1
private infix fun List<Coordinate>.isAdjacentTo(other: List<Coordinate>) =
    any { coordinate -> other.any { otherCoordinate -> coordinate isAdjacentTo otherCoordinate } }
