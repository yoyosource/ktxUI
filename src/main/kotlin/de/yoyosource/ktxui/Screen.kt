package de.yoyosource.ktxui

fun Screen(builder: SingleViewContainer.() -> Unit): Screen {
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
}