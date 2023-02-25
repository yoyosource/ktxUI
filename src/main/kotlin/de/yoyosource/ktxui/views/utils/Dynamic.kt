package de.yoyosource.ktxui.views.utils

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

fun ViewContainer.Dynamic(value: KProperty0<SingleViewBuilder>): ViewAPI {
    return +DynamicImpl(value)
}

private class DynamicImpl(private val value: KProperty0<SingleViewBuilder>): SingleViewContainer() {

    init {
        setView(value.get())
        value.isAccessible = true
        if (value.getDelegate() is Observer<*>) {
            (value.getDelegate() as Observer<*>).addObserver {
                setView(value.get())
            }
        }
    }

    override fun size(drawableData: DrawableData): Element {
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        child?.size(drawableData, screenSize, location, viewState)
    }

    fun setView(builder: SingleViewBuilder) {
        child?.parent = null
        child = null
        builder()
        child ?: Empty()
        redraw()
    }
}