package de.yoyosource.ktxui.views.shapes

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import java.awt.Color
import kotlin.reflect.KProperty0

fun ViewContainer.RoundedRectangle(): RoundedRectangle {
    return +RoundedRectangleImpl()
}

sealed interface RoundedRectangle: ViewAPI {
    fun width(width: Int): RoundedRectangle
    fun width(width: KProperty0<Int>): RoundedRectangle

    fun arcWidth(arcWidth: Int): RoundedRectangle
    fun arcWidth(arcWidth: KProperty0<Int>): RoundedRectangle

    fun height(height: Int): RoundedRectangle
    fun height(height: KProperty0<Int>): RoundedRectangle

    fun arcHeight(arcHeight: Int): RoundedRectangle
    fun arcHeight(arcHeight: KProperty0<Int>): RoundedRectangle

    fun radius(radius: Int): RoundedRectangle {
        return arcWidth(radius).arcHeight(radius)
    }
    fun radius(radius: KProperty0<Int>): RoundedRectangle {
        return arcWidth(radius).arcHeight(radius)
    }

    fun color(color: Color): RoundedRectangle
    fun color(color: KProperty0<Color>): RoundedRectangle
}

private class RoundedRectangleImpl : ViewElement(), RoundedRectangle {


    private var width = ViewOption(0)
    private var arcWidth = ViewOption(0)
    private var height = ViewOption(0)
    private var arcHeight = ViewOption(0)
    private var color = ViewOption(Color.BLACK)

    override fun size(drawableData: DrawableData): Element {
        return Element(width.get(), height.get())
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, size) = viewState[this]
        drawable.drawRoundedRectangle(location, size, color.get(), arcWidth.get(), arcHeight.get())
    }

    override fun width(width: Int) = this.width.set(this, width)

    override fun width(width: KProperty0<Int>) = this.width.set(this, width)

    override fun arcWidth(arcWidth: Int) = this.arcWidth.set(this, arcWidth)

    override fun arcWidth(arcWidth: KProperty0<Int>) = this.arcWidth.set(this, arcWidth)

    override fun height(height: Int) = this.height.set(this, height)

    override fun height(height: KProperty0<Int>) = this.height.set(this, height)

    override fun arcHeight(arcHeight: Int) = this.arcHeight.set(this, arcHeight)

    override fun arcHeight(arcHeight: KProperty0<Int>) = this.arcHeight.set(this, arcHeight)

    override fun color(color: Color) = this.color.set(this, color)

    override fun color(color: KProperty0<Color>) = this.color.set(this, color)
}
