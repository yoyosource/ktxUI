package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.*
import kotlin.reflect.KProperty0

fun <V: ViewContainer> V.Conditional(value: Boolean, inverted: Boolean = false, builder: V.() -> Unit): ViewAPI {
    if (value != inverted) {
        builder()
    }
    return this
}

fun ViewContainer.Conditional(value: KProperty0<Boolean>, inverted: Boolean = false, builder: SingleViewBuilder): ViewAPI {
    val _value = ViewOption(false)
    return (+Conditional(_value, inverted)).apply(builder).let {
        _value.set(it, value)
    }
}

private class Conditional constructor(private val value: ViewOption<Boolean>, private val inverted: Boolean): SingleViewContainer() {

    override fun redraw(view: View) {
        if (view == this || value.get() != inverted) {
            super.redraw(view)
        }
    }

    override fun size(drawableData: DrawableData): Element {
        if (value.get() != inverted) {
            return child!!.size(drawableData)
        }
        return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        if (value.get() != inverted) {
            child!!.size(drawableData, screenSize.copy(), location.copy(), viewState)
            location + screenSize
        }
    }

    override fun spacers(orientation: Orientation): Int {
        if (value.get() != inverted) {
            return child!!.spacers(orientation)
        }
        return 0
    }
}