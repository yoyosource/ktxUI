package de.yoyosource.ktxui.views.utils

import de.yoyosource.ktxui.DrawableData
import de.yoyosource.ktxui.View
import de.yoyosource.ktxui.ViewAPI
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Empty(): ViewAPI {
    return +EmptyImpl()
}

private class EmptyImpl : View() {

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
    }
}
