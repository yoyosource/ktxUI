package de.yoyosource.ktxui

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage

interface DrawableData {
    fun getSize(): Element
    fun getTextSize(text: String, font: Font): Element
}

abstract class Drawable : DrawableData {

    fun draw(screen: Screen, viewState: ViewState = ViewState()) {
        val (width, height) = getSize()

        screen.size(this, Element(width, height), viewState)
        fillBackground(Color.WHITE)
        screen.draw(this, viewState, Element(0, 0))
    }

    internal abstract fun fillBackground(color: Color)

    abstract fun rotate(angle: Double, translated: () -> Unit)

    abstract fun translate(location: Element, translated: () -> Unit)

    abstract fun drawRectangle(location: Element, size: Element, color: Color)

    abstract fun drawText(text: String, font: Font, color: Color, location: Element): Int

    abstract fun drawImage(bufferedImage: BufferedImage, location: Element)
}
