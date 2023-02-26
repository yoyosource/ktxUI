package de.yoyosource.ktxui.api.data

val black = Color(0, 0, 0)
val blue = Color(0, 0, 255)
val brown = Color(165, 42, 42)
val clear = Color(0, 0, 0, 0)
val cyan = Color(0, 255, 255)
val gray = Color(128, 128, 128)
val green = Color(0, 255, 0)
val indigo = Color(75, 0, 130)
val mint = Color(189, 252, 201)
val orange = Color(255, 165, 0)
val pink = Color(255, 192, 203)
val purple = Color(128, 0, 128)
val red = Color(255, 0, 0)
val teal = Color(0, 128, 128)
val white = Color(255, 255, 255)
val yellow = Color(255, 255, 0)

data class Color(
    val red: Int,
    val green: Int,
    val blue: Int,
    val opacity: Int = 255
)

fun RGBColor(red: Double, green: Double, blue: Double, opacity: Double = 1.0): Color {
    return Color((red * 255.0).toInt(), (green * 255.0).toInt(), (blue * 255.0).toInt(), (opacity * 255.0).toInt())
}

fun HSBColor(hue: Double, saturation: Double, brightness: Double, opacity: Double): Color {
    val h = (hue - Math.floor(hue)) * 6.0
    val f = h - Math.floor(h)
    val p = brightness * (1.0 - saturation)
    val q = brightness * (1.0 - saturation * f)
    val t = brightness * (1.0 - (saturation * (1.0 - f)))
    return when (h.toInt()) {
        0 -> Color((brightness * 255.0).toInt(), (t * 255.0).toInt(), (p * 255.0).toInt(), (opacity * 255.0).toInt())
        1 -> Color((q * 255.0).toInt(), (brightness * 255.0).toInt(), (p * 255.0).toInt(), (opacity * 255.0).toInt())
        2 -> Color((p * 255.0).toInt(), (brightness * 255.0).toInt(), (t * 255.0).toInt(), (opacity * 255.0).toInt())
        3 -> Color((p * 255.0).toInt(), (q * 255.0).toInt(), (brightness * 255.0).toInt(), (opacity * 255.0).toInt())
        4 -> Color((t * 255.0).toInt(), (p * 255.0).toInt(), (brightness * 255.0).toInt(), (opacity * 255.0).toInt())
        5 -> Color((brightness * 255.0).toInt(), (p * 255.0).toInt(), (q * 255.0).toInt(), (opacity * 255.0).toInt())
        else -> Color(0, 0, 0, 0)
    }
}