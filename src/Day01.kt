fun main() {
    fun part1(input: List<Int>): Int {
        var result = 0
        for (index in 1 until input.size) {
            if (input[index - 1] < input[index]) result++
        }
        return result
    }

    fun part2(input: List<Int>): Int {
        val windowedSums = input.windowed(size = 3).map(List<Int>::sum)
        var result = 0
        for (index in 1 until windowedSums.size) {
            if (windowedSums[index - 1] < windowedSums[index]) result++
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
