package de.yoyosource.ktxui.views.shapes

import de.yoyosource.ktxui.Drawable
import de.yoyosource.ktxui.DrawableData
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.ViewElement
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import java.awt.Color
import kotlin.reflect.KProperty0

fun ViewContainer.Rectangle(): Rectangle {
    return +RectangleImpl()
}

sealed interface Rectangle {
    fun width(width: Int): Rectangle
    fun width(width: KProperty0<Int>): Rectangle

    fun height(height: Int): Rectangle
    fun height(height: KProperty0<Int>): Rectangle

    fun color(color: Color): Rectangle
    fun color(color: KProperty0<Color>): Rectangle
}

private class RectangleImpl : ViewElement(), Rectangle {


    private var width = ViewOption(0)
    private var height = ViewOption(0)
    private var color = ViewOption(Color.BLACK)

    override fun size(drawableData: DrawableData): Element {
        return Element(width.get(), height.get())
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, size) = viewState[this]
        drawable.drawRectangle(location, size, color.get())
    }

    override fun width(width: Int) = this.width.set(this, width)

    override fun width(width: KProperty0<Int>) = this.width.set(this, width)

    override fun height(height: Int) = this.height.set(this, height)

    override fun height(height: KProperty0<Int>) = this.height.set(this, height)

    override fun color(color: Color) = this.color.set(this, color)

    override fun color(color: KProperty0<Color>) = this.color.set(this, color)
}
