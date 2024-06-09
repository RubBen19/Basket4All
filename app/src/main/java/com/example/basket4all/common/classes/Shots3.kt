package com.example.basket4all.common.classes

class Shots3 {
    //Desde la esquina izquierda(Acertados, fallados)
    private var cornerL = Pair(0, 0)
    //Desde la esquina derecha(Acertados, fallados)
    private var cornerR = Pair(0, 0)
    //Desde la 45º izquierda(Acertados, fallados)
    private var fortyFiveL = Pair(0, 0)
    //Desde la 45º derecha(Acertados, fallados)
    private var fortyFiveR = Pair(0, 0)
    //Desde el centro(Acertados, fallados)
    private var center = Pair(0, 0)
    //Totales(Acertados, fallados)
    private var total = Pair(0, 0)

    /*** SET ***/
    fun setCornerL(success: Int, fail: Int) {
        this.cornerL = Pair(success, fail)
        totalReload()
    }
    fun setCornerR(success: Int, fail: Int) {
        this.cornerR = Pair(success, fail)
        totalReload()
    }
    fun setForty5L(success: Int, fail: Int) {
        this.fortyFiveL = Pair(success, fail)
        totalReload()
    }
    fun setForty5R(success: Int, fail: Int) {
        this.fortyFiveR = Pair(success, fail)
        totalReload()
    }
    fun setCenter(success: Int, fail: Int) {
        this.center = Pair(success, fail)
        totalReload()
    }

    /*** Métodos del TOTAL ***/
    //Actualizar total
    private fun totalReload() {
        val success = cornerL.first + cornerR.first + fortyFiveL.first + fortyFiveR.first + center.first
        val failed = cornerL.second + cornerR.second + fortyFiveL.second + fortyFiveR.second + center.second
        this.total = Pair(success, failed)
    }
    //Obtener total de aciertos
    fun getSuccess(): Int {
        return total.first
    }
    //Obtener total de fallos
    fun getFailed(): Int {
        return total.second
    }
    //Obtener total de tiros
    fun getShots(): Int {
        return total.first + total.second
    }
    //Obtener total de puntos acertados
    fun getPoints(): Int {
        return total.first * 3
    }
    //Obtener total de puntos fallados
    fun getPointsFailed(): Int {
        return total.second * 3
    }

    /*** Métodos de tiros desde la ESQUINA IZQUIERDA ***/
    //Obtener canastas acertadas
    fun getCornerLeftSuccess(): Int {
        return cornerL.first
    }
    //Obtener canastas falladas
    fun getCornerLeftFailed(): Int {
        return cornerL.second
    }
    //Obtener total de tiros
    fun getCornerLeftShots(): Int {
        return (cornerL.first + cornerL.second)
    }
    //Obtener puntos acertados
    fun getCornerLeftPoints(): Int {
        return cornerL.first * 3
    }
    //Obtener puntos fallados
    fun getCornerLeftPointsFailed(): Int {
        return cornerL.second * 3
    }
    //Acierto
    fun cornerLeftPoint() {
        cornerL.first.inc()
        total.first.inc()
    }
    //Fallo
    fun cornerLeftFail() {
        cornerL.second.inc()
        total.second.inc()
    }

    /*** Métodos de tiros desde la ESQUINA DERECHA ***/
    //Obtener canastas acertadas
    fun getCornerRightSuccess(): Int {
        return cornerR.first
    }
    //Obtener canastas falladas
    fun getCornerRightFailed(): Int {
        return cornerR.second
    }
    //Obtener total de tiros
    fun getCornerRightShots(): Int {
        return (cornerR.first + cornerR.second)
    }
    //Obtener puntos acertados
    fun getCornerRightPoints(): Int {
        return cornerR.first * 3
    }
    //Obtener puntos fallados
    fun getCornerRightPointsFailed(): Int {
        return cornerR.second * 3
    }
    //Acierto
    fun cornerRightPoint() {
        cornerR.first.inc()
        total.first.inc()
    }
    //Fallo
    fun cornerRightFail() {
        cornerR.second.inc()
        total.second.inc()
    }

    /*** Métodos de tiros desde 45º IZQUIERDA ***/
    //Obtener canastas acertadas
    fun get45LeftSuccess(): Int {
        return fortyFiveL.first
    }
    //Obtener canastas falladas
    fun get45LeftFailed(): Int {
        return fortyFiveL.second
    }
    //Obtener total de tiros
    fun get45LeftShots(): Int {
        return (fortyFiveL.first + fortyFiveL.second)
    }
    //Obtener puntos acertados
    fun get45LeftPoints(): Int {
        return fortyFiveL.first * 3
    }
    //Obtener puntos fallados
    fun get45LeftPointsFailed(): Int {
        return fortyFiveL.second * 3
    }
    //Acierto
    fun FfLeftPoint() {
        fortyFiveL.first.inc()
        total.first.inc()
    }
    //Fallo
    fun FfLeftFail() {
        fortyFiveL.second.inc()
        total.second.inc()
    }

    /*** Métodos de tiros desde 45º DERECHA ***/
    //Obtener canastas acertadas
    fun get45RightSuccess(): Int {
        return fortyFiveR.first
    }
    //Obtener canastas falladas
    fun get45RightFailed(): Int {
        return fortyFiveR.second
    }
    //Obtener total de tiros
    fun get45RightShots(): Int {
        return (fortyFiveR.first + fortyFiveR.second)
    }
    //Obtener puntos acertados
    fun get45RightPoints(): Int {
        return fortyFiveR.first * 3
    }
    //Obtener puntos fallados
    fun get45RightPointsFailed(): Int {
        return fortyFiveR.second * 3
    }
    //Acierto
    fun FfRightPoint() {
        fortyFiveR.first.inc()
        total.first.inc()
    }
    //Fallo
    fun FfRightFail() {
        fortyFiveR.second.inc()
        total.second.inc()
    }

    /*** Métodos de tiros desde el CENTRO ***/
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
        return (center.first + center.second)
    }
    //Obtener puntos acertados
    fun getCenterPoints(): Int {
        return center.first * 3
    }
    //Obtener puntos fallados
    fun getCenterPointsFailed(): Int {
        return center.second * 3
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