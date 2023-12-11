package com.example.basket4all.common.classes

class Score(private var local: Int, private var visitor: Int) {

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
}