package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basket4all.common.classes.PlayerStatsClass
import com.example.basket4all.data.local.daos.MatchDao
import com.example.basket4all.data.local.daos.PlayerStatsDao
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.data.local.entities.PlayerStats
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PlayerStatsViewModel(private val playerStatsDao: PlayerStatsDao): ViewModel() {

    suspend fun insert(playerId: Int) {
        val newStats = PlayerStats(playerId = playerId)
        playerStatsDao.insert(newStats)
    }

    suspend fun getByPlayerId(id: Int): PlayerStats {
        return playerStatsDao.getByPlayer(id).first()
    }

    suspend fun updateStats(playerId: Int, matches: List<MatchStats>) {
        val playerStats = getByPlayerId(playerId)
        val globalStats = PlayerStatsClass()
        matches.forEach {
            val stats = it.stats
            globalStats.id = playerId
            globalStats.minutes += stats.minutes
            globalStats.faults.setInFaults(
                globalStats.faults.getInFaults() + stats.faults.getInFaults()
            )
            globalStats.faults.setOutFaults(
                globalStats.faults.getOutFaults() + stats.faults.getOutFaults()
            )
            globalStats.steals += stats.steals
            globalStats.block.setInBlock(
                globalStats.block.getInBlock() + stats.block.getInBlock()
            )
            globalStats.block.setOutBlock(
                globalStats.block.getOutBlock() + stats.block.getOutBlock()
            )
            globalStats.games.inc()
            globalStats.lastPass.setAssist(
                globalStats.lastPass.getAssist() + stats.lastPass.getAssist()
            )
            globalStats.lastPass.setNoAssist(
                globalStats.lastPass.getNoAssist() + stats.lastPass.getNoAssist()
            )
            globalStats.losts += globalStats.losts
            globalStats.rebounds.setOfRebound(
                globalStats.rebounds.getOfRebound() + stats.rebounds.getOfRebound()
            )
            globalStats.rebounds.setDefRebound(
                globalStats.rebounds.getDefRebound() + stats.rebounds.getDefRebound()
            )
            val shots =  globalStats.shots
            // Shots of 2 points - NO ZONE -
            shots.get2Shots().setCornerL(
                shots.get2Shots().getCornerLeftSuccess() + stats.shots.get2Shots().getCornerLeftSuccess(),
                shots.get2Shots().getCornerLeftFailed() + stats.shots.get2Shots().getCornerLeftFailed()
            )
            shots.get2Shots().setForty5L(
                shots.get2Shots().get45LeftSuccess() + stats.shots.get2Shots().get45LeftSuccess(),
                shots.get2Shots().get45LeftFailed() + stats.shots.get2Shots().get45LeftFailed()
            )
            shots.get2Shots().setCenter(
                shots.get2Shots().getCenterSuccess() + stats.shots.get2Shots().getCenterSuccess(),
                shots.get2Shots().getCenterFailed() + stats.shots.get2Shots().getCenterFailed()
            )
            shots.get2Shots().setCornerR(
                shots.get2Shots().getCornerRightSuccess() + stats.shots.get2Shots().getCornerRightSuccess(),
                shots.get2Shots().getCornerRightFailed() + stats.shots.get2Shots().getCornerRightFailed()
            )
            shots.get2Shots().setForty5R(
                shots.get2Shots().get45RightSuccess() + stats.shots.get2Shots().get45RightSuccess(),
                shots.get2Shots().get45RightFailed() + stats.shots.get2Shots().get45RightFailed()
            )
            // Shots of 3 points
            shots.get3Shots().setCornerL(
                shots.get3Shots().getCornerLeftSuccess() + stats.shots.get3Shots().getCornerLeftSuccess(),
                shots.get3Shots().getCornerLeftFailed() + stats.shots.get3Shots().getCornerLeftFailed()
            )
            shots.get3Shots().setForty5L(
                shots.get3Shots().get45LeftSuccess() + stats.shots.get3Shots().get45LeftSuccess(),
                shots.get3Shots().get45LeftFailed() + stats.shots.get3Shots().get45LeftFailed()
            )
            shots.get3Shots().setCenter(
                shots.get3Shots().getCenterSuccess() + stats.shots.get3Shots().getCenterSuccess(),
                shots.get3Shots().getCenterFailed() + stats.shots.get3Shots().getCenterFailed()
            )
            shots.get3Shots().setCornerR(
                shots.get3Shots().getCornerRightSuccess() + stats.shots.get3Shots().getCornerRightSuccess(),
                shots.get3Shots().getCornerRightFailed() + stats.shots.get3Shots().getCornerRightFailed()
            )
            shots.get3Shots().setForty5R(
                shots.get3Shots().get45RightSuccess() + stats.shots.get3Shots().get45RightSuccess(),
                shots.get3Shots().get45RightFailed() + stats.shots.get3Shots().get45RightFailed()
            )
            // Free shots
            shots.getFreeShots().setTotal(
                shots.getFreeShots().getSuccess() + stats.shots.getFreeShots().getSuccess(),
                shots.getFreeShots().getFailed() + stats.shots.getFreeShots().getFailed()
            )
            // Zone shots
            shots.getZoneShots().setLeft(
                shots.getZoneShots().getLeftSuccess() + stats.shots.getZoneShots().getLeftSuccess(),
                shots.getZoneShots().getLeftFailed() + stats.shots.getZoneShots().getLeftFailed()
            )
            shots.getZoneShots().setCenter(
                shots.getZoneShots().getCenterSuccess() + stats.shots.getZoneShots().getCenterSuccess(),
                shots.getZoneShots().getCenterFailed() + stats.shots.getZoneShots().getCenterFailed()
            )
            shots.getZoneShots().setRight(
                shots.getZoneShots().getRightSuccess() + stats.shots.getZoneShots().getRightSuccess(),
                shots.getZoneShots().getRightFailed() + stats.shots.getZoneShots().getRightFailed()
            )
        }
        playerStats.stats = globalStats
        playerStatsDao.update(playerStats)
    }
}

class PlayerStatsViewModelFactory(private val playerStatsDao: PlayerStatsDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerStatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayerStatsViewModel(playerStatsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}