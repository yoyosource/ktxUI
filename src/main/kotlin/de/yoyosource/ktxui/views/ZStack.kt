package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.math.max

fun ViewContainer.ZStack(builder: ViewContainer.() -> Unit) {
    (+ZStack()).builder()
}

private class ZStack constructor() : ViewContainer() {
    override fun size(drawableData: DrawableData): Element {
        val size = Element(0, 0)
        children.forEach {
            val childSize = it.size(drawableData)
            size.x = max(size.x, childSize.x)
            size.y = max(size.y, childSize.y)
        }
        return size
    }

    override fun size(drawableData: DrawableData, screenSize: Element, viewState: ViewState) {
        children.forEach {
            it.size(drawableData, screenSize.copy(), viewState)
        }
        viewState.sizeMap[this] = screenSize
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        children.forEach {
            it.draw(drawable, viewState, location.copy())
        }
        location + viewState.sizeMap[this]!!
    }
}
