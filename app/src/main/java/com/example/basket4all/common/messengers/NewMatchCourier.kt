package com.example.basket4all.common.messengers

import com.example.basket4all.common.classes.PlayerStatsClass

class NewMatchCourier {
    private val players: MutableList<Int> = mutableListOf()
    private val stats: MutableList<PlayerStatsClass> = mutableListOf()

    //Uso del patrón singleton para que solo pueda existir una instancia de NewMatchCourier
    companion object {
        @Volatile
        private var INSTANCE: NewMatchCourier? = null

        fun getInstance(): NewMatchCourier {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NewMatchCourier().also { INSTANCE = it }
            }
        }
    }

    fun newMatch() {
        players.clear()
        stats.clear()
    }

    fun addPlayer(player: Int, stats: PlayerStatsClass) {
        this.players.add(player)
        this.stats.add(stats)
    }

    fun getPlayers(): List<Int> {
        return players
    }

    fun getStats(): List<PlayerStatsClass> {
        return stats
    }
}