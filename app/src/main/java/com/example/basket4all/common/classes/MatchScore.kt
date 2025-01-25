package com.example.basket4all.common.classes

class MatchScore(
    private var scoreQ1: Score = Score(0,0),
    private var scoreQ2: Score = Score(0,0),
    private var scoreQ3: Score = Score(0,0),
    private var scoreQ4: Score = Score(0,0),
) {
    fun getScore(q: Int = 0): String {
        return when(q) {
            1 -> return scoreQ1.getTotal()
            2 -> return scoreQ2.getTotal()
            3 -> return scoreQ3.getTotal()
            4 -> return scoreQ4.getTotal()
            else -> getMatchScore().getTotal()
        }
    }

    fun getLocalScore(): Int {
        return scoreQ1.getLocalScore() + scoreQ2.getLocalScore() + scoreQ3.getLocalScore() + scoreQ4.getLocalScore()
    }

    fun getVisitorScore(): Int {
        return scoreQ1.getVisitorScore() + scoreQ2.getVisitorScore() + scoreQ3.getVisitorScore() + scoreQ4.getVisitorScore()
    }

    private fun getMatchScore(): Score {
        val list = listOf(scoreQ1, scoreQ2, scoreQ3, scoreQ4)
        var local = 0
        var visitor = 0

        list.forEach { q ->
            local += q.getLocalScore()
            visitor += q.getVisitorScore()
        }

        return Score(local, visitor)
    }
}