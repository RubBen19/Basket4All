package com.example.basket4all.common.classes

class Faults {
    private var inFaults: Int = 0 // Faltas dentro del triple
    private var outFaults: Int = 0 // Faltas fuera del triple

    /*** GETTERS ***/
    fun getInFaults(): Int {
        return inFaults
    }

    fun getOutFaults(): Int {
        return outFaults
    }

    fun getTotal(): Int {
        return inFaults + outFaults
    }

    /*** SETTERS ***/
    fun setInFaults(value: Int) {
        inFaults = value
    }

    fun setOutFaults(value: Int) {
        outFaults = value
    }

    /*** FUNCIONES ***/
    // Método para incrementar las faltas de dentro
    fun incrementInFaults() {
        inFaults++
    }

    // Método para incrementar las faltas de fuera
    fun incrementOutFaults() {
        outFaults++
    }

    // Método para decrementar las faltas de dentro
    fun decrementInFaults() {
        if (inFaults > 0) inFaults--
    }

    // Método para decrementar las faltas de fuera
    fun decrementOutFaults() {
        if (outFaults > 0) outFaults--
    }
}
