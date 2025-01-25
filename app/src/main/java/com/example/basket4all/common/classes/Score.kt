package com.example.basket4all.common.classes

class Score(private var local: Int, private var visitor: Int) {

    fun getTotal(): String {
        return "$local - $visitor"
    }

    fun getLocalScore(): Int {
        return this.local
    }

    fun getVisitorScore(): Int {
        return this.visitor
    }

    fun setLocalScore(score: Int) {
        this.local = score
    }

    fun setVisitorScore(score: Int) {
        this.visitor = score
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Score

        if (local != other.local) return false
        if (visitor != other.visitor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = local
        result = 31 * result + visitor
        return result
    }


}