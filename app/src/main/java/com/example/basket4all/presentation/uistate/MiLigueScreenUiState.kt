import com.example.basket4all.data.local.entities.TeamEntity

data class MiLigueScreenUiState(
  // Datos generales de la liga
  val name: String = "",
  val category: String = "",
  val division: String = "",
  // Lista de equipos
  val rivalsTeamsList: List<TeamEntity> = listOf(),
  // Flag de carga de la pantalla
  val loading: Boolean = false
)
