package com.example.basket4all.common.classes

class ZoneShots {
    //Desde la izquierda(Acertados, fallados)
    private var left = Pair(0, 0)
    //Desde la derecha(Acertados, fallados)
    private var right = Pair(0, 0)
    //Desde el centro(Acertados, fallados)
    private var center = Pair(0, 0)
    //Totales(Acertados, fallados)
    private var total = Pair(0, 0)

    /*** SET ***/
    fun setLeft(success: Int, fail: Int) {
        this.left = Pair(success, fail)
        totalReload()
    }
    fun setRight(success: Int, fail: Int) {
        this.right = Pair(success, fail)
        totalReload()
    }
    fun setCenter(success: Int, fail: Int) {
        this.center = Pair(success, fail)
        totalReload()
    }

    /*** Métodos TOTAL ***/
    //Actualizar total
    private fun totalReload() {
        val success = left.first + right.first + center.first
        val failed = left.second + right.second + center.second
        this.total = Pair(success, failed)
    }
    //Obtener canastas acertadas
    fun getSuccess(): Int {
        return total.first
    }
    //Obtener canastas falladas
    fun getFailed(): Int {
        return total.second
    }
    //Obtener tiros a canasta
    fun getShots(): Int {
        return total.first + total.second
    }
    //Obtener puntos acertados
    fun getPoints(): Int {
        return total.first * 2
    }
    //Obtener puntos fallados
    fun getPointsFailed(): Int {
        return total.second * 2
    }

    /*** Métodos IZQUIERDA ***/
    //Obtener canastas acertadas
    fun getLeftSuccess(): Int {
        return left.first
    }
    //Obtener canastas falladas
    fun getLeftFailed(): Int {
        return left.second
    }
    //Obtener total de tiros
    fun getLeftShots(): Int {
        return left.first + left.second
    }
    //Obtener puntos acertados
    fun getLeftPoints(): Int {
        return left.first * 2
    }
    //Obtener puntos fallados
    fun getLeftPointsFailed(): Int {
        return left.second * 2
    }
    //Acierto
    fun leftPoint() {
        left.first.inc()
        total.first.inc()
    }
    //Fallo
    fun leftFail() {
        left.second.inc()
        total.second.inc()
    }

    /*** Métodos DERECHA ***/
    //Obtener canastas acertadas
    fun getRightSuccess(): Int {
        return right.first
    }
    //Obtener canastas falladas
    fun getRightFailed(): Int {
        return right.second
    }
    //Obtener total de tiros
    fun getRightShots(): Int {
        return right.first + right.second
    }
    //Obtener puntos acertados
    fun getRightPoints(): Int {
        return right.first * 2
    }
    //Obtener puntos fallados
    fun getRightPointsFailed(): Int {
        return right.second * 2
    }
    //Acierto
    fun rightPoint() {
        right.first.inc()
        total.first.inc()
    }
    //Fallo
    fun rightFail() {
        right.second.inc()
        total.second.inc()
    }

    /*** Métodos CENTRO ***/
    //Obtener canastas acertadas
    fun getCenterSuccess(): Int {
        return center.first
    }
    //Obtener canastas falladas
    fun getCenterFailed(): Int {
        return center.second
    }
    //Obtener total de tiros
    fun getCenterShots(): Int {
        return center.first + center.second
    }
    //Obtener puntos acertados
    fun getCenterPoints(): Int {
        return center.first * 2
    }
    //Obtener puntos fallados
    fun getCenterPointsFailed(): Int {
        return center.second * 2
    }
    //Acierto
    fun centerPoint() {
        center.first.inc()
        total.first.inc()
    }
    //Fallo
    fun centerFail() {
        center.second.inc()
        total.second.inc()
    }
}