package de.yoyosource.ktxui.views.events

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Event
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Button(builder: SingleViewBuilder): Button {
    return (+ButtonImpl()).apply(builder)
}

sealed interface Button: Event {
    fun click(viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int)
    fun onClick(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) -> Unit): Button
}

private class ButtonImpl : SingleViewContainer(), Button {

    private var action: (Int, Int, Int, Int, Int, Int) -> Unit = { _, _, _, _, _, _ -> }

    override fun size(drawableData: DrawableData): Element {
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        viewState.set(this, location)
        child?.size(drawableData, screenSize, location, viewState)
    }

    override fun click(viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) {
        action(viewPosX, viewPosY, relativeX, relativeY, x, y)
    }

    override fun onClick(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) -> Unit): Button {
        this.action = action
        return this
    }
}

fun <V : ViewAPI> V.onClick(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) -> Unit): V {
    this as ViewBase
    val self = this
    val event = ButtonImpl()
    parent!!.swap(this, event)
    event.apply { +self }
    event.onClick(action)
    return this
}
