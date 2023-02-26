package de.yoyosource.ktxui.views.events

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Event
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Scroll(builder: SingleViewBuilder): Scroll {
    return (+ScrollImpl()).apply(builder)
}

sealed interface Scroll: Event {
    fun scroll(viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int, wheelRotation: Double)
    fun onScroll(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int, wheelRotation: Double) -> Unit): Scroll
}

private class ScrollImpl : SingleViewContainer(), Scroll {

    private var action: (Int, Int, Int, Int, Int, Int, Double) -> Unit = { _, _, _, _, _, _, _ -> }

    override fun size(drawableData: DrawableData): Element {
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        viewState.set(this, location)
        child?.size(drawableData, screenSize, location, viewState)
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
    this as ViewBase
    val self = this
    val event = ScrollImpl()
    parent!!.swap(this, event)
    event.apply { +self }
    event.onScroll(action)
    return this
}