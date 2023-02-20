package de.yoyosource.ktxui.views.events

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Drag(builder: SingleViewContainer.() -> Unit): Drag {
    return (+DragImpl()).apply(builder)
}

sealed interface Drag {
    fun drag(viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int)
    fun onDrag(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) -> Unit): Drag
}

private class DragImpl : DrawableSingleViewContainer(), Drag {

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

    override fun drag(viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) {
        action(viewPosX, viewPosY, relativeX, relativeY, x, y)
    }

    override fun onDrag(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) -> Unit): Drag {
        this.action = action
        return this
    }
}

fun <V : ViewAPI> V.onDrag(action: (viewPosX: Int, viewPosY: Int, relativeX: Int, relativeY: Int, x: Int, y: Int) -> Unit): V {
    this as View
    val self = this
    val event = DragImpl()
    parent!!.swap(this, event)
    event.apply { +self }
    event.onDrag(action)
    return this
}
