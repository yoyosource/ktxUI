package de.yoyosource.ktxui

import de.yoyosource.ktxui.utils.Element
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage

enum class DebugMode {
    SIZE,
    DRAW,
}

interface DrawableData {

    fun enableDebug(debugMode: DebugMode)
    fun disableDebug(debugMode: DebugMode)
    fun isDebugEnabled(debugMode: DebugMode): Boolean
    fun debug(debugMode: DebugMode, log: String)

    fun getSize(): Element
    fun getTextSize(text: String, font: Font): Element
}

interface Drawable : DrawableData {

    fun fillBackground(color: Color)

    fun rotate(angle: Double, translated: () -> Unit)

    fun translate(location: Element, translated: () -> Unit)

    fun drawRectangle(location: Element, size: Element, color: Color)

    fun drawRoundedRectangle(location: Element, size: Element, color: Color, arcWidth: Int, arcHeight: Int)

    fun drawCircle(location: Element, radius: Int, color: Color)

    fun drawOval(location: Element, width: Int, height: Int, color: Color)

    fun drawText(text: String, font: Font, color: Color, location: Element): Int

    fun drawImage(bufferedImage: BufferedImage, location: Element)
}
