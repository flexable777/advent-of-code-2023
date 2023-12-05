fun main() {

    data class Range(
        val sourceRange: LongRange,
        val destinationRange: LongRange,
        val rangeDiff: Long,
    )

    fun getRanges(s: String): List<Range> {
        val lines = s.lines()
        return lines.subList(fromIndex = 1, toIndex = lines.size).map { numbers ->
            val values = numbers.split(" ").map { it.toLong() }
            val destinationRangeStart = values[0]
            val sourceRangeStart = values[1]
            val rangeLength = values[2]
            val rangeDiff = destinationRangeStart - sourceRangeStart

            Range(
                sourceRange = (sourceRangeStart..(sourceRangeStart + (rangeLength - 1))),
                destinationRange = (destinationRangeStart..(destinationRangeStart + (rangeLength - 1))),
                rangeDiff = rangeDiff,
            )
        }
    }

    fun getLocation(seed: Long, ranges: List<List<Range>>): Long {
        val correctRange = ranges[0].find { seed in it.sourceRange }
        val newSeed = if (correctRange == null) {
            seed
        } else {
            seed + correctRange.rangeDiff
        }
        if (ranges.size == 1) {
            return newSeed
        }
        return getLocation(seed = newSeed, ranges.subList(fromIndex = 1, toIndex = ranges.size))
    }

    fun part1(input: String): Long {
        val data = input.split("\n\n")
        val seeds = data[0].split("seeds: ")[1].split(" ").map { it.toLong() }
        val ranges = data.subList(fromIndex = 1, toIndex = data.size).map {
            getRanges(it)
        }

        return seeds.minOf { seed ->
            getLocation(seed = seed, ranges = ranges)
        }
    }

    //TODO improve speed
    fun part2(input: String): Long {
        val data = input.split("\n\n")
        val seedRanges = data[0].split("seeds: ")[1].split(" ").map { it.toLong() }.windowed(2, 2).map {
            (it[0]..it[0] + (it[1] - 1))
        }
        val ranges = data.subList(fromIndex = 1, toIndex = data.size).map {
            getRanges(it)
        }

        var lowestLocation = Long.MAX_VALUE
        for (seedRange in seedRanges) {
            for (seed in seedRange) {
                val location = getLocation(seed = seed, ranges = ranges)
                if (location < lowestLocation) {
                    lowestLocation = location
                }
            }
        }

        return lowestLocation
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsString("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInputAsString("Day05")
    part1(input).println(1)
    part2(input).println(2)
}
