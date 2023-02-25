package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.DrawableData
import de.yoyosource.ktxui.SingleViewContainer
import de.yoyosource.ktxui.View
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.utils.ViewState

fun Screen(builder: SingleViewBuilder): Screen {
    return Screen().apply(builder)
}

class Screen internal constructor() : SingleViewContainer() {

    private var redraw = mutableSetOf<(View) -> Unit>()

    fun addRedrawListener(redraw: (View) -> Unit) {
        this.redraw.add(redraw)
    }

    fun removeRedrawListener(redraw: (View) -> Unit) {
        this.redraw.remove(redraw)
    }

    override fun redraw(view: View) {
        redraw.forEach { it(view) }
    }

    override fun size(drawableData: DrawableData): Element {
        throw IllegalStateException("Screen has no size")
    }

    override fun spacers(orientation: Orientation): Int {
        throw IllegalStateException("Screen has no spacers")
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        child?.size(drawableData, screenSize, location, viewState)
    }

    private var clickListener = mutableSetOf<() -> Unit>()

    fun clickNothing() {
        clickListener.forEach { it() }
    }

    fun onClickNothing(action: () -> Unit): Screen {
        clickListener.add(action)
        return this
    }

    private var hoverListener = mutableSetOf<() -> Unit>()

    fun hoverNothing() {
        hoverListener.forEach { it() }
    }

    fun onHoverNothing(action: () -> Unit): Screen {
        hoverListener.add(action)
        return this
    }

    private var dragListener = mutableSetOf<() -> Unit>()

    fun dragNothing() {
        dragListener.forEach { it() }
    }

    fun onDragNothing(action: () -> Unit): Screen {
        dragListener.add(action)
        return this
    }

    private var scrollListener = mutableSetOf<() -> Unit>()

    fun scrollNothing() {
        scrollListener.forEach { it() }
    }

    fun onScrollNothing(action: () -> Unit): Screen {
        scrollListener.add(action)
        return this
    }
}

fun View.getScreen(): Screen {
    var view = this
    while (view.parent != null) {
        view = view.parent!!
    }
    if (view is Screen) {
        return view
    }
    throw IllegalStateException("View is not in a Screen")
}