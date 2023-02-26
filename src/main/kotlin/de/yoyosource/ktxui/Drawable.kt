package de.yoyosource.ktxui

import de.yoyosource.ktxui.api.protocols.BorderAPI
import de.yoyosource.ktxui.api.protocols.FontAPI
import de.yoyosource.ktxui.utils.Element
import java.awt.Color

interface DrawableData {
    fun getSize(): Element

    fun getTextSize(text: String, font: FontAPI<*, *>): Element
}

interface Drawable : DrawableData {

    fun setColor(color: Color)

    fun fillBackground()

    fun drawBorder(borderAPI: BorderAPI<*, *>, location: Element, size: Element)

    fun drawText(text: String, font: FontAPI<*, *>, location: Element): Int

    fun fillRectangle(location: Element, size: Element)
}
