package de.yoyosource.ktxui.api.views.layout.size

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

fun ViewContainer.FitSize(builder: SingleViewContainer.(width: KProperty0<Int>, height: KProperty0<Int>) -> Unit): FitSize<*> {
    return (+FitSizeImpl()).apply {
        builder(this::width, this::height)
    }
}

sealed interface FitSize<S> : ViewProtocol<S, FitSize<S>> where S : ViewBase

private class FitSizeImpl : SingleViewContainer(), FitSize<FitSizeImpl> {

    override val selfAPI: FitSize<FitSizeImpl> = this
    override val selfView: FitSizeImpl = this

    var width by Observer(0)
    var height by Observer(0)

    override fun size(drawableData: DrawableData): Element {
        width = 0
        height = 0
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        val size = size(drawableData)
        width = size.x
        height = size.y
        child?.size(drawableData, size, location, viewState)
    }
}