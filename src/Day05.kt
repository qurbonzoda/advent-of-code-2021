import kotlin.math.absoluteValue

fun main() {
    data class Input(val lines: List<Line>)

    fun read(fileName: String): Input {
        val input = readInput(fileName)
            .map { line -> line.split(" -> ") }
            .map { line -> line.flatMap { it.split(",") } }
            .map { line -> line.map(String::toInt) }
            .map { line ->
                val e1 = Point(line[0], line[1])
                val e2 = Point(line[2], line[3])
                Line(minOf(e1, e2), maxOf(e1, e2))
            }

        return Input(input)
    }

    val testInput = read("Day05_test").also { println(it) }
    check(solve(testInput.lines, isPart1 = true) == 5)
    check(solve(testInput.lines, isPart1 = false) == 12)

    val input = read("Day05")
    println(solve(input.lines, isPart1 = true))
    println(solve(input.lines, isPart1 = false))
}

private data class Point(val col: Int, val row: Int) : Comparable<Point> {
    override fun compareTo(other: Point): Int {
        if (this.col != other.col) return this.col.compareTo(other.col)
        return this.row.compareTo(other.row)
    }
}
private data class Line(val start: Point, val end: Point) : Comparable<Line>, Iterable<Point> {
    init {
        require(start <= end)
    }

    val isHorizontal: Boolean get() = start.row == end.row
    val isVertical: Boolean get() = start.col == end.col
    val isDiagonal: Boolean get() = (end.col - start.col) == (end.row - start.row).absoluteValue

    override fun compareTo(other: Line): Int {
        if (this.start != other.start) return this.start.compareTo(other.start)
        return this.end.compareTo(other.end)
    }

    override fun iterator(): Iterator<Point> = when {
        isHorizontal -> HorizontalIterator()
        isVertical -> VerticalIterator()
        isDiagonal -> DiagonalIterator()
        else -> emptyList<Point>().iterator()
    }

    private inner class HorizontalIterator : Iterator<Point> {
        init {
            require(isHorizontal)
        }
        val iter = (start.col..end.col).iterator()

        override fun hasNext(): Boolean = iter.hasNext()
        override fun next(): Point = Point(iter.next(), start.row)
    }

    private inner class VerticalIterator : Iterator<Point> {
        init {
            require(isVertical)
        }
        val iter = (start.row..end.row).iterator()

        override fun hasNext(): Boolean = iter.hasNext()
        override fun next(): Point = Point(start.col, iter.next())
    }

    private inner class DiagonalIterator : Iterator<Point> {
        init {
            require(isDiagonal)
        }
        val rowIter = (if (start.row <= end.row) (start.row..end.row) else (start.row.downTo(end.row))).iterator()
        val colIter = (start.col..end.col).iterator()

        override fun hasNext(): Boolean = rowIter.hasNext()
        override fun next(): Point = Point(colIter.next(), rowIter.next())
    }
}


private fun solve(lines: List<Line>, isPart1: Boolean): Int {
    val maxRow = lines.maxOf { maxOf(it.start.row, it.end.row) }
    val maxCol = lines.maxOf { maxOf(it.start.col, it.end.col) }
    val board = Array(maxRow + 1) { Array(maxCol + 1) { 0 } }

    for (line in lines) {
        if (!isPart1 || (line.isHorizontal || line.isVertical)) {
            for (point in line) {
                board[point.row][point.col]++
            }
        }
    }

    var result = 0
    for (row in 0..maxRow) {
        for (col in 0..maxCol) {
            result += if (board[row][col] > 1) 1 else 0
        }
    }

    return result
}
