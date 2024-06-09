package com.example.basket4all.common.classes

class Shooting {
    private val shotsOfZ = ZoneShots()
    private val freeShots = FreeShots()
    private val shotsOf2 = Shots2()
    private val shotsOf3 = Shots3()

    /*** Métodos GET ***/
    //Obtener tiros desde la zona
    fun getZoneShots(): ZoneShots {
        return shotsOfZ
    }
    //Obtener tiros libres
    fun getFreeShots(): FreeShots {
        return freeShots
    }
    //Obtener tiros de 2
    fun get2Shots(): Shots2 {
        return shotsOf2
    }
    //Obtener tiros de 3
    fun get3Shots(): Shots3 {
        return shotsOf3
    }

    /*** FUNCIONES ***/
    //Obtener total de canastas acertadas
    fun getSuccess(): Int {
        return shotsOf2.getSuccess() + shotsOf3.getSuccess() + shotsOfZ.getSuccess() + freeShots.getSuccess()
    }
    //Obtener total de canastas falladas
    fun getFailed(): Int {
        return shotsOf2.getFailed() + shotsOf3.getFailed() + shotsOfZ.getFailed() + freeShots.getFailed()
    }
    //Obtener total de canastas intentadas
    fun getShots(): Int {
        return shotsOf2.getShots() + shotsOf3.getShots() + shotsOfZ.getShots() + freeShots.getTotal()
    }
    //Obtener total de puntos
    fun getPoints(): Int {
        return shotsOf2.getPoints() + shotsOf3.getPoints() + shotsOfZ.getPoints() + freeShots.getSuccess()
    }
    //Obtener total de puntos fallados
    fun getPointsFailed(): Int {
        return shotsOf2.getPointsFailed() + shotsOf3.getPointsFailed() + shotsOfZ.getPointsFailed() + freeShots.getFailed()
    }
}