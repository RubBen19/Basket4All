import com.example.basket4all.data.local.entities.MatchStats

data class PlayerStatsUiScreen(
  // Variables de las estadísticas por partido
  val ppp: Float = 0.0f,
  val mpp: Float = 0.0f,
  val app: Float = 0.0f,
  val rpp: Float = 0.0f,
  val fpp: Float = 0.0f,
  val lpp: Float = 0.0f,
  val spp: Float = 0.0f,
  val bpp: Float = 0.0f,
  val mPlayed: Int = 0,
  // Estadística de tiro generales
  val twoPIn: Int = 0,
  val twoPOut: Int = 0,
  val twoPShoots: Int = 0,
  val twoPPercent: Float = 0.0f,
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
  val blocks: Int = 0,
  // Lista de partidos
  val matchesPlayed: List<MatchStats> = listOf(),
  // Flag
  val loading: Boolean = false,
)
  
