package de.yoyosource.ktxui.api.views.special

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

fun ViewContainer.Dynamic(value: KProperty0<SingleViewBuilder>): Dynamic<*> {
    return +DynamicImpl(value)
}

sealed interface Dynamic<S>: ViewProtocol<S, Dynamic<S>> where S: ViewBase

private class DynamicImpl(private val value: KProperty0<SingleViewBuilder>): SingleViewContainer(), Dynamic<DynamicImpl> {

    override val selfView: DynamicImpl = this
    override val selfAPI: Dynamic<DynamicImpl> = this

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