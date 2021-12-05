fun main() {
    fun part1(input: Input): Int {
        val markedBoards = List(input.boards.size) { Array(5) { BooleanArray(5) } }

        for (num in input.nums) {
            for (boardIdx in 0 until input.boards.size) {
                val board = input.boards[boardIdx]
                val marked = markedBoards[boardIdx]

                val rows = BooleanArray(5) { true }
                val columns = BooleanArray(5) { true }

                for (row in 0 until 5) {
                    for (column in 0 until 5) {
                        if (board[row][column] == num) {
                            marked[row][column] = true
                        }

                        rows[row] = rows[row] && marked[row][column]
                        columns[column] = columns[column] && marked[row][column]
                    }
                }

                fun sumUnmarked(): Int {
                    var sum = 0
                    for (row in 0 until 5) {
                        for (column in 0 until 5) {
                            if (!marked[row][column]) {
                                sum += board[row][column]
                            }
                        }
                    }
                    return sum
                }

                for (i in 0 until 5) {
                    if (rows[i] || columns[i]) {
                        println("$num $i")
                        return sumUnmarked() * num
                    }
                }
            }
        }

        error("Unreachable")
    }

    fun part2(input: Input): Int {
        val boards = input.boards.toMutableList()
        val markedBoards = MutableList(input.boards.size) { Array(5) { BooleanArray(5) } }

        for (num in input.nums) {
            val boardIt = boards.iterator()
            val markedIt = markedBoards.iterator()
            while (boardIt.hasNext()) {
                val board = boardIt.next()
                val marked = markedIt.next()

                val rows = BooleanArray(5) { true }
                val columns = BooleanArray(5) { true }

                for (row in 0 until 5) {
                    for (column in 0 until 5) {
                        if (board[row][column] == num) {
                            marked[row][column] = true
                        }

                        rows[row] = rows[row] && marked[row][column]
                        columns[column] = columns[column] && marked[row][column]
                    }
                }

                fun sumUnmarked(): Int {
                    var sum = 0
                    for (row in 0 until 5) {
                        for (column in 0 until 5) {
                            if (!marked[row][column]) {
                                sum += board[row][column]
                            }
                        }
                    }
                    return sum
                }

                for (i in 0 until 5) {
                    if (rows[i] || columns[i]) {
                        if (boards.size == 1) {
                            println("$num $i")
                            return sumUnmarked() * num
                        } else {
                            boardIt.remove()
                            markedIt.remove()
                            break
                        }
                    }
                }
            }
        }

        error("Unreachable")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Input.read("Day04_test").also { println(it) }
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = Input.read("Day04")
    println(part1(input))
    println(part2(input))
}

private data class Input(val nums: List<Int>, val boards: List<List<List<Int>>>) {

    companion object {
        fun read(fileName: String): Input {
            val input = readInput(fileName).filter { it.isNotEmpty() }
            val nums = input[0].split(",").map(String::toInt)

            val boards = mutableListOf<List<List<Int>>>()
            while (1 + boards.size * 5 < input.size) {
                val board = List(5) { row ->
                     input[1 + boards.size * 5 + row].split("\\s".toRegex()).filter(String::isNotEmpty).map(String::toInt)
                }
                boards.add(board)
            }

            return Input(nums, boards)
        }
    }

//    override fun toString(): String {
//        buildString {
//            append("nums: $nums")
//        }
//    }
}