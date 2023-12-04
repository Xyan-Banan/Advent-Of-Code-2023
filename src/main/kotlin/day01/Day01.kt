package day01

import readInput

fun common(line: String): Int {
    val digit1 = line.first { it.isDigit() }.digitToInt()
    val digit2 = line.last { it.isDigit() }.digitToInt()

    return digit1 * 10 + digit2
}

fun main() {

    fun part1(lines: List<String>) = lines.sumOf { line -> common(line) }

    fun part2(lines: List<String>) = lines.sumOf { line ->
        val numbers = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        ).plus((1..9).map { it.toString() to it })

        //ПЁСЕЛИ > КОТЕЛИ

        val first = numbers.mapNotNull { entry ->
            line.indexOf(entry.key).takeIf { it >= 0 }?.let { it to entry }
        }.minBy { it.first }
            .second
            .value

        val reversedLine = line.reversed()
        val last = numbers.mapNotNull { entry ->
            reversedLine.indexOf(entry.key.reversed()).takeIf { it >= 0 }?.let { it to entry }
        }.minBy { it.first }
            .second
            .value

        return@sumOf first * 10 + last
    }

    val lines = readInput("day01")
    println(part1(lines))
    println(part2(lines))
}

fun notWorking(line: String) {
    val numbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )
    fun format1(line: String) = buildString {

        val numbersGrouped = numbers.entries.groupBy { it.key.length }

        var i = 0
        loop@
        while (i in line.indices) {
            for (j in 3..5) {
                if (i + j > line.length) break
                val localNumbers = numbersGrouped.getValue(j)
                for ((number, digit) in localNumbers) {
                    val substring = line.substring(i, i + j)
                    if (substring.contains(number)) {
                        append(digit)
                        i += j
                        continue@loop
                    }
                }
            }
            append(line[i])
            i++
        }
    }

    fun format2(line: String) = buildString {
        line.asSequence()
            .windowed(5, 3)
            .map { it.joinToString("") }
            .forEach { window ->
                val foundNumber = numbers.keys.firstOrNull { number -> window.contains(number) }
                if (length > 2) delete(length - 2, length)
                if (foundNumber == null) {
                    append(window)
                } else {
                    append(window.replace(foundNumber, numbers.getValue(foundNumber).toString()))
                }
            }
    }

    line.let { format1(it) }.let { common(it) }
    line.let { format2(it) }.let { common(it) }
}