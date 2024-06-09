package com.example.basket4all.common.messengers

/***
 * Clase encargada de gestionar la sesión de un usuario
 */
class SessionManager private constructor() {
    private var userId: Int = 0
    private var teamId: Int = 0
    private var isPlayer: Boolean? = false

    //Uso del patrón singleton para que solo pueda existir una instancia de SessionManager
    companion object {
        @Volatile
        private var INSTANCE: SessionManager? = null

        fun getInstance(): SessionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SessionManager().also { INSTANCE = it }
            }
        }
    }

    fun getUserId(): Int {
        return userId
    }

    fun getTeamId(): Int {
        return teamId
    }

    fun getRole(): Boolean? {
        return isPlayer
    }

    fun login(id:Int, teamId:Int, player: Boolean) {
        userId = id
        this.teamId = teamId
        isPlayer = player
    }

}