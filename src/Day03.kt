fun main() {
    fun part1(input: List<String>): Int {
        val n = input.getOrNull(0)?.length ?: return 0
        var gamma = 0
        var epsilon = 0
        for (index in 0 until n) {
            gamma *= 2
            epsilon *= 2

            val counter = intArrayOf(0, 0)
            for (s in input) {
                counter[s[index] - '0']++
            }
            if (counter[0] > counter[1]) {
                epsilon++
            } else {
                gamma++
            }
        }
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val n = input.getOrNull(0)?.length ?: return 0
        val oxygen = input.toMutableList()
        for (index in 0 until n) {
            val counter = intArrayOf(0, 0)
            for (s in oxygen) {
                counter[s[index] - '0']++
            }
            val expected = if (counter[0] > counter[1]) '0' else '1'
            oxygen.removeAll { it[index] != expected }

            println("$expected: $oxygen")
        }

        val co2 = input.toMutableList()
        for (index in 0 until n) {
            val counter = intArrayOf(0, 0)
            for (s in co2) {
                counter[s[index] - '0']++
            }
            val expected = if (counter[1] == 0 || (counter[0] <= counter[1] && counter[0] != 0)) '0' else '1'
            co2.removeAll { it[index] != expected }

            println("$expected: $co2")
        }
        return oxygen.single().toInt(radix = 2) * co2.single().toInt(radix = 2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}