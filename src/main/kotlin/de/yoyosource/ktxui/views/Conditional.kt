package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.reflect.KProperty0

fun <V: ViewContainer> V.Conditional(value: Boolean, inverted: Boolean = false, builder: V.() -> Unit) {
    if (value != inverted) {
        builder()
    }
}

fun ViewContainer.Conditional(value: KProperty0<Boolean>, inverted: Boolean = false, builder: SingleViewContainer.() -> Unit) {
    val _value = ViewOption(false)
    (+Conditional(_value, inverted)).apply(builder).let {
        _value.set(it, value)
    }
}

class Conditional internal constructor(private val value: ViewOption<Boolean>, private val inverted: Boolean): SingleViewContainer() {

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

    override fun spacers(orientation: Orientation): Int {
        if (value.get() != inverted) {
            return child!!.spacers(orientation)
        }
        return 0
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        if (value.get() != inverted) {
            child!!.draw(drawable, viewState, location.copy())
        }
        location + viewState.sizeMap[this]!!
    }
}