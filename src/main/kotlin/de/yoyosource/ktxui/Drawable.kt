package de.yoyosource.ktxui

import java.awt.Color
import java.awt.Font

interface DrawableData {
    fun getSize(): Element
    fun getTextSize(text: String, font: Font): Element
}

abstract class Drawable : DrawableData {

    fun draw(screen: Screen) {
        val viewState = ViewState()
        val (width, height) = getSize()

        screen.size(this, Element(width, height), viewState)
        fillBackground(Color.WHITE)
        screen.draw(this, viewState, Element(0, 0))
    }

    internal abstract fun fillBackground(color: Color)

    abstract fun drawText(text: String, font: Font, color: Color, location: Element): Int
}
