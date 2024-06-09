package com.example.basket4all.common.classes

class Rebound {
    private var defRebound: Int = 0 // Rebotes defensivos
    private var ofRebound: Int = 0 // Rebotes ofensivos

    /*** GETTERS ***/
    fun getDefRebound(): Int {
        return defRebound
    }

    fun getOfRebound(): Int {
        return ofRebound
    }

    fun getRebounds(): Int {
        return defRebound + ofRebound
    }

    /*** SETTERS ***/
    fun setDefRebound(value: Int) {
        defRebound = value
    }

    fun setOfRebound(value: Int) {
        ofRebound = value
    }

    /*** FUNCIONES ***/
    // Método para incrementar los rebotes defensivos
    fun incrementDefRebound() {
        defRebound++
    }

    // Método para incrementar los rebotes ofensivos
    fun incrementOfRebound() {
        ofRebound++
    }

    // Método para decrementar los rebotes defensivos
    fun decrementDefRebound() {
        if (defRebound > 0) defRebound--
    }

    // Método para decrementar los rebotes ofensivos
    fun decrementOfRebound() {
        if (ofRebound > 0) ofRebound--
    }
}
