package de.yoyosource.ktxui.impl

import de.yoyosource.ktxui.DebugMode
import de.yoyosource.ktxui.Drawable
import de.yoyosource.ktxui.utils.Element
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.font.TextLayout
import java.awt.image.BufferedImage
import kotlin.math.ceil

class Graphics2dDrawable(private val g: Graphics2D, private val width: Int, private val height: Int): Drawable {

    private val enableDebugModes = mutableSetOf<DebugMode>()

    init {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    }

    override fun enableDebug(debugMode: DebugMode) {
        enableDebugModes.add(debugMode)
    }

    override fun disableDebug(debugMode: DebugMode) {
        enableDebugModes.remove(debugMode)
    }

    override fun isDebugEnabled(debugMode: DebugMode): Boolean {
        return enableDebugModes.contains(debugMode)
    }

    override fun debug(debugMode: DebugMode, log: String) {
        if (enableDebugModes.contains(debugMode)) println("$debugMode $log")
    }

    override fun getSize(): Element {
        return Element(width, height)
    }

    override fun getTextSize(text: String, font: Font): Element {
        if (text.isEmpty()) {
            val textLayout = TextLayout(" ", font, g.fontRenderContext)
            return Element(0, ceil(textLayout.bounds.height).toInt())
        }

        val textLayout = TextLayout(text, font, g.fontRenderContext)
        return Element(ceil(textLayout.bounds.width).toInt(), ceil(textLayout.ascent + textLayout.descent + textLayout.leading).toInt())
    }

    override fun fillBackground(color: Color) {
        g.color = color
        g.fillRect(0, 0, width, height)
    }

    override fun rotate(angle: Double, translated: () -> Unit) {
        g.rotate(angle)
        translated()
        g.rotate(-angle)
    }

    override fun translate(location: Element, translated: () -> Unit) {
        g.translate(location.x, location.y)
        translated()
        g.translate(-location.x, -location.y)
    }

    override fun drawRectangle(location: Element, size: Element, color: Color) {
        g.color = color
        var x = location.x
        var y = location.y
        var width = size.x
        var height = size.y
        if (width < 0) {
            x += width
            width *= -1
        }
        if (height < 0) {
            y += height
            height *= -1
        }
        g.fillRect(x, y, width, height)
    }

    override fun drawText(text: String, font: Font, color: Color, location: Element): Int {
        if (text.isEmpty()) {
            val textLayout = TextLayout(" ", font, g.fontRenderContext)
            return ceil(textLayout.ascent + textLayout.descent + textLayout.leading).toInt()
        }

        g.font = font
        g.color = color
        val textLayout = TextLayout(text, font, g.fontRenderContext)
        textLayout.draw(g, location.x.toFloat(), location.y.toFloat() + textLayout.ascent + textLayout.descent + textLayout.leading)
        return ceil(textLayout.ascent + textLayout.descent + textLayout.leading).toInt()
    }

    override fun drawImage(bufferedImage: BufferedImage, location: Element) {
        g.drawImage(bufferedImage, location.x, location.y, null)
    }
}