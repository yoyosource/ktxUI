package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.reflect.KProperty0

fun ViewContainer.Padding(builder: SingleViewContainer.() -> Unit): Padding {
    return (+Padding()).apply(builder)
}

class Padding internal constructor() : SingleViewContainer() {

    private var top: ViewOption<Int> = ViewOption(0)
    private var bottom: ViewOption<Int> = ViewOption(0)
    private var left: ViewOption<Int> = ViewOption(0)
    private var right: ViewOption<Int> = ViewOption(0)

    fun padding(padding: Int) = apply {
        top.set(this, padding)
        bottom.set(this, padding)
        left.set(this, padding)
        right.set(this, padding)
    }

    fun padding(padding: KProperty0<Int>) = apply {
        top.set(this, padding)
        bottom.set(this, padding)
        left.set(this, padding)
        right.set(this, padding)
    }

    fun padding(side: Side, padding: Int) = apply {
        when (side) {
            Side.TOP -> top.set(this, padding)
            Side.BOTTOM -> bottom.set(this, padding)
            Side.LEFT -> left.set(this, padding)
            Side.RIGHT -> right.set(this, padding)
        }
    }

    fun padding(side: Side, padding: KProperty0<Int>) = apply {
        when (side) {
            Side.TOP -> top.set(this, padding)
            Side.BOTTOM -> bottom.set(this, padding)
            Side.LEFT -> left.set(this, padding)
            Side.RIGHT -> right.set(this, padding)
        }
    }

    override fun size(drawableData: DrawableData): Element {
        return child?.let {
            val size = it.size(drawableData)
            return Element(size.x + left.get() + right.get(), size.y + top.get() + bottom.get())
        } ?: return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, viewState: ViewState) {
        super.size(drawableData, screenSize, viewState)
        child?.let {
            it.size(
                drawableData,
                screenSize.copy() - Element(left.get() + right.get(), top.get() + bottom.get()),
                viewState
            )
        }
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        child?.let {
            it.draw(drawable, viewState, location.copy() + Element(left.get(), top.get()))
        }
        location + viewState.sizeMap[this]!!
    }
}
