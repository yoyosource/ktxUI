package de.yoyosource.ktxui.views.events

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Scroll(builder: SingleViewContainer.() -> Unit): Scroll {
    return (+ScrollImpl()).apply(builder)
}

sealed interface Scroll {
    fun scroll(viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int, wheelRotation: Double)
    fun onScroll(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int, wheelRotation: Double) -> Unit): Scroll
}

private class ScrollImpl : DrawableSingleViewContainer(), Scroll {

    private var action: (Int, Int, Int, Int, Int, Int, Double) -> Unit = { _, _, _, _, _, _, _ -> }

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

    override fun scroll(viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int, wheelRotation: Double) {
        action(viewPosX, viewPosY, relativeX, relativeY, x, y, wheelRotation)
    }

    override fun onScroll(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int, wheelRotation: Double) -> Unit): Scroll {
        this.action = action
        return this
    }
}

fun <V : ViewAPI> V.onScroll(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int, wheelRotation: Double) -> Unit): V {
    this as View
    val self = this
    val event = ScrollImpl()
    parent!!.swap(this, event)
    event.apply { +self }
    event.onScroll(action)
    return this
}