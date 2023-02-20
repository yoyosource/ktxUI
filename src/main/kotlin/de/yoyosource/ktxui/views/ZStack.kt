package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.DrawableData
import de.yoyosource.ktxui.ViewAPI
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.utils.ViewState
import kotlin.math.max

fun ViewContainer.ZStack(builder: ViewContainer.() -> Unit): ViewAPI {
    return (+ZStack()).apply(builder)
}

private class ZStack : ViewContainer() {
    override fun size(drawableData: DrawableData): Element {
        val size = Element(0, 0)
        children.forEach {
            val childSize = it.size(drawableData)
            size.x = max(size.x, childSize.x)
            size.y = max(size.y, childSize.y)
        }
        return size
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        children.forEach {
            it.size(drawableData, screenSize.copy(), location.copy(), viewState)
        }
    }
}
