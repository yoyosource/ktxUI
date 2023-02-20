package de.yoyosource.ktxui.views.events

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Hover(builder: SingleViewContainer.() -> Unit): Hover {
    return (+HoverImpl()).apply(builder)
}

sealed interface Hover {
    fun hover(viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int)
    fun onHover(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) -> Unit): Hover
}

private class HoverImpl : DrawableSingleViewContainer(), Hover {

    private var action: (Int, Int, Int, Int, Int, Int) -> Unit = { _, _, _, _, _, _ -> }

    override fun size(drawableData: DrawableData): Element {
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        viewState.set(this, location, screenSize)
        child?.size(drawableData, screenSize.copy(), location.copy(), viewState)
        location + screenSize
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
    }

    override fun hover(viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) {
        action(viewPosX, viewPosY, relativeX, relativeY, x, y)
    }

    override fun onHover(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) -> Unit): Hover {
        this.action = action
        return this
    }
}