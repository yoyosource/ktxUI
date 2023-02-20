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

fun ViewContainer.Oval(): Oval {
    return +OvalImpl()
}

sealed interface Oval {
    fun width(radius: Int): Oval
    fun width(radius: KProperty0<Int>): Oval

    fun height(radius: Int): Oval
    fun height(radius: KProperty0<Int>): Oval

    fun color(color: Color): Oval
    fun color(color: KProperty0<Color>): Oval
}

private class OvalImpl : ViewElement(), Oval {

    private var width = ViewOption(0)
    private var height = ViewOption(0)
    private var color = ViewOption(Color.BLACK)

    override fun size(drawableData: DrawableData): Element {
        return Element(width.get(), height.get())
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, _) = viewState[this]
        drawable.drawOval(location, width.get(), height.get(), color.get())
    }

    override fun width(radius: Int) = this.width.set(this, radius)

    override fun width(radius: KProperty0<Int>) = this.width.set(this, radius)

    override fun height(radius: Int) = this.height.set(this, radius)

    override fun height(radius: KProperty0<Int>) = this.height.set(this, radius)

    override fun color(color: Color) = this.color.set(this, color)

    override fun color(color: KProperty0<Color>) = this.color.set(this, color)
}
