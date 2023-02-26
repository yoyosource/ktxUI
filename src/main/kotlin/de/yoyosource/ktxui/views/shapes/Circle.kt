package de.yoyosource.ktxui.views.shapes

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import java.awt.Color
import kotlin.reflect.KProperty0

fun ViewContainer.Circle(): Circle {
    return +CircleImpl()
}

sealed interface Circle: ViewProtocol {
    fun radius(radius: Int): Circle
    fun radius(radius: KProperty0<Int>): Circle

    fun color(color: Color): Circle
    fun color(color: KProperty0<Color>): Circle
}

private class CircleImpl : DrawableView(), Circle {

    private var radius = ViewOption(0)
    private var color = ViewOption(Color.BLACK)

    override fun size(drawableData: DrawableData): Element {
        return Element(radius.get() * 2, radius.get() * 2)
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, _) = viewState[this]
        drawable.drawCircle(location, radius.get(), color.get())
    }

    override fun radius(radius: Int) = this.radius.set(this, radius)

    override fun radius(radius: KProperty0<Int>) = this.radius.set(this, radius)

    override fun color(color: Color) = this.color.set(this, color)

    override fun color(color: KProperty0<Color>) = this.color.set(this, color)
}
