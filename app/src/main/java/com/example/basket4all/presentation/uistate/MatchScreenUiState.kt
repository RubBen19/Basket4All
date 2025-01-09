data class MatchScreenUiState(
  // Marcador final y fecha
  val localPoints: Int = 0,
  val visitorPoints: Int = 0,
  val date: String = "",
  // Puntos marcados por el equipo local en cada cuarto
  val localPointsQ1: Int = 0,
  val localPointsQ2: Int = 0,
  val localPointsQ3: Int = 0,
  val localPointsQ4: Int = 0,
  // Puntos marcados por el equipo visitante en cada cuarto
  val visitorPointsQ1: Int = 0,
  val visitorPointsQ2: Int = 0,
  val visitorPointsQ3: Int = 0,
  val visitorPointsQ4: Int = 0,
)
