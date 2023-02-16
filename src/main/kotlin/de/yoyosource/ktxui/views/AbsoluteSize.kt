package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.reflect.KProperty0

fun ViewContainer.AbsoluteSize(width: Int, height: Int, builder: SingleViewContainer.() -> Unit) {
    (+AbsoluteSize(ViewOption(width), ViewOption(height))).builder()
}

fun ViewContainer.AbsoluteSize(width: KProperty0<Int>, height: Int, builder: SingleViewContainer.() -> Unit) {
    val _width = ViewOption(0)
    (+AbsoluteSize(_width, ViewOption(height))).apply(builder).let {
        _width.set(it, width)
    }
}

fun ViewContainer.AbsoluteSize(width: Int, height: KProperty0<Int>, builder: SingleViewContainer.() -> Unit) {
    val _height = ViewOption(0)
    (+AbsoluteSize(ViewOption(width), _height)).apply(builder).let {
        _height.set(it, height)
    }
}

fun ViewContainer.AbsoluteSize(width: KProperty0<Int>, height: KProperty0<Int>, builder: SingleViewContainer.() -> Unit) {
    val _width = ViewOption(0)
    val _height = ViewOption(0)
    (+AbsoluteSize(_width, _height)).apply(builder).let {
        _width.set(it, width)
        _height.set(it, height)
    }
}

private class AbsoluteSize constructor(private val width: ViewOption<Int>, private val height: ViewOption<Int>) : SingleViewContainer() {

    override fun size(drawableData: DrawableData): Element {
        return Element(width.get(), height.get())
    }

    override fun spacers(orientation: Orientation): Int {
        return 0
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        val size = viewState.sizeMap[this]!!
        child!!.draw(drawable, viewState, location.copy())
        location + size
    }
}