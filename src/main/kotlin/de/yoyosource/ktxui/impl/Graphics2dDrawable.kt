package de.yoyosource.ktxui.impl

import de.yoyosource.ktxui.Drawable
import de.yoyosource.ktxui.Element
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.font.TextLayout

class Graphics2dDrawable(private val g: Graphics2D, private val width: Int, private val height: Int): Drawable() {

    init {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    }

    override fun getSize(): Element {
        return Element(width, height)
    }

    override fun getTextSize(text: String, font: Font): Element {
        val textLayout = TextLayout(text, font, g.fontRenderContext)
        return Element(textLayout.advance.toInt(), textLayout.ascent.toInt() + textLayout.descent.toInt())
    }

    override fun fillBackground(color: Color) {
        g.color = color
        g.fillRect(0, 0, width, height)
    }

    override fun drawText(text: String, font: Font, color: Color, location: Element): Int {
        g.font = font
        g.color = color
        val textLayout = TextLayout(text, font, g.fontRenderContext)
        textLayout.draw(g, location.x.toFloat(), location.y.toFloat() + textLayout.ascent)
        return textLayout.ascent.toInt()
    }
}