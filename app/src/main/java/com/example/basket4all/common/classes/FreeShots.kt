package com.example.basket4all.common.classes

class FreeShots {
    //Totales(Acertados, fallados)
    private var total = Pair(0, 0)

    //Obtener total de aciertos
    fun getSuccess(): Int {
        return total.first
    }
    //Obtener total de fallos
    fun getFailed(): Int {
        return total.second
    }
    //Obtener total de intentos
    fun getTotal(): Int {
        return total.first + total.second
    }
    //Cambiar el total
    fun setTotal(success: Int, failed: Int) {
        this.total = Pair(success, failed)
    }
}