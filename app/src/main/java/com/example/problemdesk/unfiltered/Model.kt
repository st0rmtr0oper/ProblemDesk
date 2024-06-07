package com.example.problemdesk.unfiltered

data class Fabric(val regions: List<Region>)

data class Region(
    val nameOfRegion: String,
    val months: List<MonthPerformance>,
    val totalDone: Int,
    val totalExecuting: Int,
    val totalDeclined: Int
)

data class MonthPerformance(
    val nameOfMonth: String,
    val done: Int,
    val executing: Int,
    val declined: Int
)

val fabric = Fabric(
    regions = listOf(
        Region(
            nameOfRegion = "Area1",
            months = listOf(
                MonthPerformance(
                    nameOfMonth = "April",
                    done = 400,
                    executing = 50,
                    declined = 10
                ),
                MonthPerformance(
                    nameOfMonth = "May",
                    done = 300,
                    executing = 30,
                    declined = 5
                )
            ),
            totalDone = 700,
            totalExecuting = 80,
            totalDeclined = 15
        )
    )
)
