package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.reflect.KProperty0

fun ViewContainer.AbsolutePosition(x: Int, y: Int, builder: SingleViewContainer.() -> Unit) {
    (+AbsolutePosition(ViewOption(x), ViewOption(y))).builder()
}

fun ViewContainer.AbsolutePosition(x: KProperty0<Int>, y: Int, builder: SingleViewContainer.() -> Unit) {
    val _x = ViewOption(0)
    (+AbsolutePosition(_x, ViewOption(y))).apply(builder).let {
        _x.set(it, x)
    }
}

fun ViewContainer.AbsolutePosition(x: Int, y: KProperty0<Int>, builder: SingleViewContainer.() -> Unit) {
    val _y = ViewOption(0)
    (+AbsolutePosition(ViewOption(x), _y)).apply(builder).let {
        _y.set(it, y)
    }
}

fun ViewContainer.AbsolutePosition(x: KProperty0<Int>, y: KProperty0<Int>, builder: SingleViewContainer.() -> Unit) {
    val _x = ViewOption(0)
    val _y = ViewOption(0)
    (+AbsolutePosition(_x, _y)).apply(builder).let {
        _x.set(it, x)
        _y.set(it, y)
    }
}

private class AbsolutePosition constructor(private val x: ViewOption<Int>, private val y: ViewOption<Int>) : SingleViewContainer() {

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun spacers(orientation: Orientation): Int {
        return 0
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        child!!.draw(drawable, viewState, Element(x.get(), y.get()))
    }
}