package com.example.basket4all.common.classes

class LastPasses {
    private var assist = 0 // Últimos pases que terminaron en canasta (Asistencias)
    private var noAssist = 0 // Últimos pases que NO terminaron en canasta

    /*** GETTERS ***/
    fun getAssist(): Int {
        return assist
    }

    fun getNoAssist(): Int {
        return noAssist
    }

    /*** SETTERS ***/
    fun setAssist(value: Int) {
        assist = value
    }

    fun setNoAssist(value: Int) {
        noAssist = value
    }

    /*** FUNCIONES ***/
    // Método para incrementar las asistencias
    fun incrementAssist() {
        assist++
    }

    // Método para incrementar los últimos pases que no acabaron en canasta
    fun incrementNoAssist() {
        noAssist++
    }

    // Método para decrementar las asistencias
    fun decrementAssist() {
        if (assist > 0) assist--
    }

    // Método para decrementar los últimos pases que no acabaron en canasta
    fun decrementNoAssist() {
        if (noAssist > 0) noAssist--
    }
}
