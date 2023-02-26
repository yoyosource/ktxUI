package de.yoyosource.ktxui.api.data

import java.awt.font.TextAttribute

open class FontWeight(val weight: Float)

object UltraLight : FontWeight(TextAttribute.WEIGHT_EXTRA_LIGHT)
object Light : FontWeight(TextAttribute.WEIGHT_LIGHT)
object Thin : FontWeight(TextAttribute.WEIGHT_DEMILIGHT)
object Regular : FontWeight(TextAttribute.WEIGHT_REGULAR)
object Semibold : FontWeight(TextAttribute.WEIGHT_SEMIBOLD)
object Medium : FontWeight(TextAttribute.WEIGHT_MEDIUM)
object Bold : FontWeight(TextAttribute.WEIGHT_BOLD)
object Heavy : FontWeight(TextAttribute.WEIGHT_HEAVY)
object Black : FontWeight(TextAttribute.WEIGHT_ULTRABOLD)