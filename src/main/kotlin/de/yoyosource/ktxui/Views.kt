package de.yoyosource.ktxui

@DslMarker
annotation class KtxUIDsl

@KtxUIDsl
abstract class View {

    internal var parent: ViewContainer? = null

    open fun redraw(view: View = this) {
        parent?.redraw(view)
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

interface ViewAPI

abstract class ViewElement : View()

abstract class ViewContainer : View() {
    internal val children: MutableList<View> = mutableListOf()

    open operator fun <T: View> T.unaryPlus(): T {
        if (this@ViewContainer.children.contains(this)) {
            throw IllegalStateException("Child already set")
        }
        this@ViewContainer.children += this
        this.parent = this@ViewContainer
        return this
    }

    open fun swap(old: View, new: View) {
        if (this.children.contains(old)) {
            this.children[this.children.indexOf(old)] = new
            new.parent = this
            old.parent = null
        } else {
            throw IllegalStateException("Child not found")
        }
    }

    override fun spacers(orientation: Orientation): Int {
        return children.sumOf { it.spacers(orientation) }
    }
}

abstract class OrientedViewContainer(open val orientation: Orientation) : ViewContainer()

abstract class SingleViewContainer : ViewContainer() {
    internal var child: View? = null

    override operator fun <T: View> T.unaryPlus(): T {
        if (this@SingleViewContainer.child != null) {
            throw IllegalStateException("Child already set")
        }
        this@SingleViewContainer.child = this
        this.parent = this@SingleViewContainer
        return this
    }

    override fun swap(old: View, new: View) {
        if (this.child == old) {
            this.child = new
            new.parent = this
            old.parent = null
        } else {
            throw IllegalStateException("Child not found")
        }
    }

    override fun spacers(orientation: Orientation): Int {
        return child?.spacers(orientation) ?: 0
    }
}