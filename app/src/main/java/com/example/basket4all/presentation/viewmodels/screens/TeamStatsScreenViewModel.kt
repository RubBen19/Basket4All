package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.data.local.entities.TeamStats
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModel
import kotlinx.coroutines.launch

class TeamStatsScreenViewModel(
    private val teamStatsVM: TeamStatsViewModel,
    private val teamId: Int,
): ViewModel() {
    private val _stats = MutableLiveData<TeamStats>()
    val stats: LiveData<TeamStats> = _stats

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        searchStats()
    }

    private fun searchStats() {
        Log.d("TeamStatsScreenViewModel", "Buscando estadísticas")
        _loading.value = true
        viewModelScope.launch {
            _stats.value = teamStatsVM.getByTeamId(teamId)
            _loading.value = false
            Log.d("TeamScreenViewModel", "Estadísticas encontradas")
        }
    }
}

class TeamStatsScreenViewModelFactory(
    private val teamStatsVM: TeamStatsViewModel,
    private val teamId: Int,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamStatsScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamStatsScreenViewModel(teamStatsVM, teamId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}