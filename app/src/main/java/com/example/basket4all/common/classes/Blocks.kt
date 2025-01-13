package com.example.basket4all.common.classes

class Blocks {
    private var inBlock: Int = 0 // Tapones dentro del triple
    private var outBlock: Int = 0 // Tapones fuera del triple

    /*** GETTERS ***/
    fun getInBlock(): Int {
        return inBlock
    }

    fun getOutBlock(): Int {
        return outBlock
    }

    fun getBlocks(): Int {
        return inBlock + outBlock
    }

    /*** SETTERS ***/
    fun setInBlock(value: Int) {
        inBlock = value
    }

    fun setOutBlock(value: Int) {
        outBlock = value
    }

    /*** FUNCIONES ***/
    // Método para incrementar las tapones de dentro
    fun incrementInBlock() {
        inBlock++
    }

    // Método para incrementar las tapones de fuera
    fun incrementOutBlock() {
        outBlock++
    }

    // Método para decrementar las tapones de dentro
    fun decrementInBlock() {
        if (inBlock > 0) inBlock--
    }

    // Método para decrementar las tapones de fuera
    fun decrementOutBlock() {
        if (outBlock > 0) outBlock--
    }
}
