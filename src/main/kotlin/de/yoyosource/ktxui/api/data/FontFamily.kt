package de.yoyosource.ktxui.api.data

open class FontFamily(val family: String)

object Serif : FontFamily(java.awt.Font.SERIF)
object SansSerif : FontFamily(java.awt.Font.SANS_SERIF)
object Monospace : FontFamily(java.awt.Font.MONOSPACED)