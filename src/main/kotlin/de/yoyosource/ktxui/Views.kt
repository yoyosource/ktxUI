package de.yoyosource.ktxui

abstract class View {

    internal var parent: ViewContainer? = null

    open fun redraw() {
        parent?.redraw()
    }

    abstract fun size(drawableData: DrawableData): Element
    open fun size(drawableData: DrawableData, screenSize: Element, viewState: ViewState) {
        val size = size(drawableData)
        viewState.sizeMap[this] = size
        screenSize - size
    }

    open fun spacers(orientation: Orientation): Int {
        return 0
    }
    abstract fun draw(drawable: Drawable, viewState: ViewState, location: Element)
}

abstract class ViewContainer : View() {
    internal var children: List<View> = emptyList()

    open operator fun <T: View> T.unaryPlus(): T {
        children += this
        this.parent = this@ViewContainer
        return this
    }

    override fun spacers(orientation: Orientation): Int {
        return children.sumOf { it.spacers(orientation) }
    }
}

abstract class OrientedViewContainer(open val orientation: Orientation) : ViewContainer()

abstract class SingleViewContainer : ViewContainer() {
    internal var child: View? = null

    override operator fun <T: View> T.unaryPlus(): T {
        if (child != null) {
            throw IllegalStateException("Child already set")
        }
        child = this
        this.parent = this@SingleViewContainer
        return this
    }

    override fun spacers(orientation: Orientation): Int {
        return child?.spacers(orientation) ?: 0
    }
}