package de.yoyosource.ktxui.views.position

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.*
import kotlin.reflect.KProperty0

fun ViewContainer.RelativePosition(x: Int, y: Int, builder: SingleViewBuilder): ViewAPI {
    return (+RelativePosition(ViewOption(x), ViewOption(y))).apply(builder)
}

fun ViewContainer.RelativePosition(x: KProperty0<Int>, y: Int, builder: SingleViewBuilder): ViewAPI {
    val _x = ViewOption(0)
    return (+RelativePosition(_x, ViewOption(y))).apply(builder).let {
        _x.set(it, x)
    }
}

fun ViewContainer.RelativePosition(x: Int, y: KProperty0<Int>, builder: SingleViewBuilder): ViewAPI {
    val _y = ViewOption(0)
    return (+RelativePosition(ViewOption(x), _y)).apply(builder).let {
        _y.set(it, y)
    }
}

fun ViewContainer.RelativePosition(x: KProperty0<Int>, y: KProperty0<Int>, builder: SingleViewBuilder): ViewAPI {
    val _x = ViewOption(0)
    val _y = ViewOption(0)
    return (+RelativePosition(_x, _y)).apply(builder).let {
        _x.set(it, x)
        _y.set(it, y)
    }
}

private class RelativePosition constructor(private val x: ViewOption<Int>, private val y: ViewOption<Int>) : SingleViewContainer() {

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        child?.size(drawableData, screenSize.copy(), location.copy() + Element(x.get(), y.get()), viewState)
    }

    override fun spacers(orientation: Orientation): Int {
        return 0
    }
}