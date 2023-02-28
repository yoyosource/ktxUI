package de.yoyosource.ktxui.api.views.special

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.*
import kotlin.reflect.KProperty0

fun ViewContainer.Conditional(value: Boolean, inverted: Boolean = false, builder: SingleViewBuilder): Conditional<*> {
    return (+ConditionalImpl(ViewOption(value), inverted)).apply(builder)
}

fun ViewContainer.Conditional(value: KProperty0<Boolean>, inverted: Boolean = false, builder: SingleViewBuilder): Conditional<*> {
    val _value = ViewOption(false)
    return (+ConditionalImpl(_value, inverted)).apply(builder).let {
        _value.set(it, value)
    }
}

sealed interface Conditional<S> : ViewProtocol<S, Conditional<S>> where S : ViewBase

private class ConditionalImpl constructor(private val value: ViewOption<Boolean>, private val inverted: Boolean): SingleViewContainer(), Conditional<ConditionalImpl> {

    override val selfView: ConditionalImpl = this
    override val selfAPI: Conditional<ConditionalImpl> = this

    override fun redraw(view: ViewBase) {
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