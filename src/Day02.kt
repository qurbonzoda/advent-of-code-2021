fun main() {
    fun part1(input: List<Pair<String, Int>>): Int {
        var resultX = 0
        var resultY = 0
        for ((command, value) in input) {
            when (command) {
                "forward" -> resultX += value
                "down" -> resultY += value
                "up" -> resultY -= value
            }
        }
        return resultX * resultY
    }

    fun part2(input: List<Pair<String, Int>>): Long {
        var aim = 0L
        var resultX = 0L
        var resultY = 0L
        for ((command, value) in input) {
            when (command) {
                "forward" -> {
                    resultX += value
                    resultY += aim * value
                }
                "down" -> aim += value
                "up" -> aim -= value
            }
        }
        return resultX * resultY
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test").map { line -> line.split(' ').let { Pair(it[0], it[1].toInt()) } }
    check(part1(testInput) == 150)
    check(part2(testInput) == 900L)

    val input = readInput("Day02").map { line -> line.split(' ').let { Pair(it[0], it[1].toInt()) } }
    println(part1(input))
    println(part2(input))
}