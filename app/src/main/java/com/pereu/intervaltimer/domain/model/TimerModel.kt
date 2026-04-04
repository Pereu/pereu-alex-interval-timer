package com.pereu.intervaltimer.domain.model

data class TimerModel(
    val id: Int,
    val title: String,
    val totalTime: Int,
    val intervals: List<IntervalModel>
) {
    companion object {
        fun getMockData() = TimerModel(
            id = 68,
            title = "Тренировка 7",
            totalTime = 900,
            intervals = listOf(
                IntervalModel(title = "Ходьба в среднем темпе", time = 7),
                IntervalModel(title = "Ходьба в интенсивном темпе", time = 3),
                IntervalModel(title = "Ходьба в среднем темпе", time = 1),
                IntervalModel(title = "Медленный бег", time = 3),
                IntervalModel(title = "Ходьба в среднем темпе", time = 1),
                IntervalModel(title = "Ходьба в интенсивном темпе", time = 3),
                IntervalModel(title = "Ходьба в среднем темпе", time = 7),
                IntervalModel(title = "Медленный бег", time = 3)
            )
        )
    }
}

data class IntervalModel(
    val title: String,
    val time: Int
)
