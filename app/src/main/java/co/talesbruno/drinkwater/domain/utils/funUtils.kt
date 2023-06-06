package co.talesbruno.drinkwater.domain.utils


fun formatTime(timeMillis: Long): String {
    val seconds = (timeMillis / 1000) % 60
    val minutes = (timeMillis / (1000 * 60)) % 60
    val hours = (timeMillis / (1000 * 60 * 60)) % 24

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}