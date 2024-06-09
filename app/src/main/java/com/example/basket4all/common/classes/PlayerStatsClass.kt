package com.example.basket4all.common.classes

class PlayerStatsClass {
    val shots: Shooting = Shooting()
    val rebounds: Rebound = Rebound()
    val faults: Faults = Faults()
    val block: Blocks = Blocks()
    val lastPass: LastPasses = LastPasses()
    var losts: Int = 0
    var steals: Int = 0
    var minutes: Double = 0.0
    val games: Int = 0
}