data class MatchStatsScreenUiState(
  // Tiro
  val TwoPIn: Int = 0,
  val TwoPOut: Int = 0,
  val TwoPShoots: Int = 0,
  val TwoPPercent: Float = 0.0f,
  val ThreePIn: Int = 0,
  val ThreePOut: Int = 0,
  val ThreePShoots: Int = 0,
  val ThreePPercent: Float = 0.0f,
  val FpIn: Int = 0,
  val FpOut: Int = 0,
  val FpShoots: Int = 0,
  val FpPercent: Float = 0.0f,
  val ZpIn: Int = 0,
  val ZpOut: Int = 0,
  val ZpShoots: Int = 0,
  val ZpPercent: Float = 0.0f,
   // Estadísticas de pases
  val totalPasses: Int = 0,
  val assist: Int = 0,
  val probAssist: Float = 0.0f,
  // Estadísticas de rebotes
  val offensiveReb: Int = 0,
  val defensiveReb: Int = 0,
  // Otras estadísticas
  val fouls: Int = 0,
  val losts: Int = 0,
  val steals: Int = 0,
  val blocks: Int = 0
)
  
