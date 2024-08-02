package com.example.basket4all.presentation.uistate

import com.example.basket4all.data.local.entities.PlayerStats
import com.example.basket4all.data.local.entities.TeamEntity

data class ProfileUiState(
    val username: String = "",
    val surname: String = "",
    val number: Int = 0,
    val image: ByteArray? = null,
    val positions: List<String> = listOf(),
    val team: TeamEntity? = null,
    val stats: PlayerStats? = null,
    val loading: Boolean = false,
    val imageSelectorVisible: Boolean = false,
    val showInfo: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileUiState

        if (username != other.username) return false
        if (surname != other.surname) return false
        if (number != other.number) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (positions != other.positions) return false
        if (team != other.team) return false
        if (stats != other.stats) return false
        if (loading != other.loading) return false
        if (imageSelectorVisible != other.imageSelectorVisible) return false
        if (showInfo != other.showInfo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + number
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + positions.hashCode()
        result = 31 * result + (team?.hashCode() ?: 0)
        result = 31 * result + (stats?.hashCode() ?: 0)
        result = 31 * result + loading.hashCode()
        result = 31 * result + imageSelectorVisible.hashCode()
        result = 31 * result + showInfo.hashCode()
        return result
    }


}
