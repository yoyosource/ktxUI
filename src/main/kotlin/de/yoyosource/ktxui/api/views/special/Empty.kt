package de.yoyosource.ktxui.api.views.special

import de.yoyosource.ktxui.DrawableData
import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.ViewProtocol
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Empty(): Empty<*> {
    return +EmptyImpl()
}

sealed interface Empty<S>: ViewProtocol<S, Empty<S>> where S: ViewBase

private class EmptyImpl : ViewBase(), Empty<EmptyImpl> {

    override val selfView: EmptyImpl = this
    override val selfAPI: Empty<EmptyImpl> = this

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
    }
}
