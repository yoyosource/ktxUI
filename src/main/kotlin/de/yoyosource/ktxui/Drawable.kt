package de.yoyosource.ktxui

import de.yoyosource.ktxui.api.protocols.BorderProtocol
import de.yoyosource.ktxui.api.protocols.FontProtocol
import de.yoyosource.ktxui.utils.Element
import java.awt.Color
import java.awt.image.BufferedImage

interface DrawableData {
    fun getSize(): Element

    fun getTextSize(text: String, font: FontProtocol<*, *>): Element
}

interface Drawable : DrawableData {

    fun setColor(color: Color)

    fun fillBackground()

    fun drawBorder(borderAPI: BorderProtocol<*, *>, location: Element, size: Element)

    fun drawText(text: String, font: FontProtocol<*, *>, location: Element): Int

    fun drawImage(image: BufferedImage, location: Element)
    fun drawImage(image: BufferedImage, location: Element, color: Color)

    fun drawRectangle(location: Element, size: Element, borderWidth: Int)
    fun fillRectangle(location: Element, size: Element)

    fun drawRoundedRectangle(location: Element, size: Element, arcWidth: Int, arcHeight: Int, borderWidth: Int)
    fun fillRoundedRectangle(location: Element, size: Element, arcWidth: Int, arcHeight: Int)

    fun drawCircle(location: Element, radius: Int, borderWidth: Int)
    fun fillCircle(location: Element, radius: Int)

    fun drawOval(location: Element, width: Int, height: Int, borderWidth: Int)
    fun fillOval(location: Element, width: Int, height: Int)
}
