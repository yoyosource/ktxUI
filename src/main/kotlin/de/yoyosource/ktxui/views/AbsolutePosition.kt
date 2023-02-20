package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

fun ViewContainer.AbsolutePosition(x: Int, y: Int, builder: SingleViewContainer.() -> Unit): ViewAPI {
    return (+AbsolutePosition(ViewOption(x), ViewOption(y))).apply(builder)
}

fun ViewContainer.AbsolutePosition(x: KProperty0<Int>, y: Int, builder: SingleViewContainer.() -> Unit): ViewAPI {
    val _x = ViewOption(0)
    return (+AbsolutePosition(_x, ViewOption(y))).apply(builder).let {
        _x.set(it, x)
    }
}

fun ViewContainer.AbsolutePosition(x: Int, y: KProperty0<Int>, builder: SingleViewContainer.() -> Unit): ViewAPI {
    val _y = ViewOption(0)
    return (+AbsolutePosition(ViewOption(x), _y)).apply(builder).let {
        _y.set(it, y)
    }
}

fun ViewContainer.AbsolutePosition(x: KProperty0<Int>, y: KProperty0<Int>, builder: SingleViewContainer.() -> Unit): ViewAPI {
    val _x = ViewOption(0)
    val _y = ViewOption(0)
    return (+AbsolutePosition(_x, _y)).apply(builder).let {
        _x.set(it, x)
        _y.set(it, y)
    }
}

private class AbsolutePosition constructor(private val x: ViewOption<Int>, private val y: ViewOption<Int>) : SingleViewContainer() {

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        child?.size(drawableData, screenSize.copy(), Element(x.get(), y.get()), viewState)
    }

    override fun spacers(orientation: Orientation): Int {
        return 0
    }
}