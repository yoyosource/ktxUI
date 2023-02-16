package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.reflect.KProperty0

fun ViewContainer.RelativePosition(x: Int, y: Int, builder: SingleViewContainer.() -> Unit) {
    (+RelativePosition(ViewOption(x), ViewOption(y))).builder()
}

fun ViewContainer.RelativePosition(x: KProperty0<Int>, y: Int, builder: SingleViewContainer.() -> Unit) {
    val _x = ViewOption(0)
    (+RelativePosition(_x, ViewOption(y))).apply(builder).let {
        _x.set(it, x)
    }
}

fun ViewContainer.RelativePosition(x: Int, y: KProperty0<Int>, builder: SingleViewContainer.() -> Unit) {
    val _y = ViewOption(0)
    (+RelativePosition(ViewOption(x), _y)).apply(builder).let {
        _y.set(it, y)
    }
}

fun ViewContainer.RelativePosition(x: KProperty0<Int>, y: KProperty0<Int>, builder: SingleViewContainer.() -> Unit) {
    val _x = ViewOption(0)
    val _y = ViewOption(0)
    (+RelativePosition(_x, _y)).apply(builder).let {
        _x.set(it, x)
        _y.set(it, y)
    }
}

private class RelativePosition constructor(private val x: ViewOption<Int>, private val y: ViewOption<Int>) : SingleViewContainer() {

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun spacers(orientation: Orientation): Int {
        return 0
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        child!!.draw(drawable, viewState, Element(x.get(), y.get()) + location)
    }
}