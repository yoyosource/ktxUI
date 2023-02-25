package de.yoyosource.ktxui

import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.ViewState

@DslMarker
annotation class KtxUIDsl

@KtxUIDsl
abstract class View : ViewAPI {

    internal var parent: ViewContainer? = null

    open fun redraw(view: View = this) {
        parent?.redraw(view)
    }

    abstract fun size(drawableData: DrawableData): Element
    abstract fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState)

    open fun spacers(orientation: Orientation): Int {
        return 0
    }
}

interface DrawableView {
    fun draw(drawable: Drawable, viewState: ViewState)
}

interface ViewAPI

abstract class ViewElement : View(), DrawableView {
    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        val size = size(drawableData)
        viewState.set(this, location, size)
        location + size
    }
}

abstract class ViewContainer : View() {
    internal val children: MutableList<View> = mutableListOf()

    open operator fun <T : View> T.unaryPlus(): T {
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

    override operator fun <T : View> T.unaryPlus(): T {
        if (this@SingleViewContainer.child != null) {
            throw IllegalStateException("SingleViewContainer can only contain one view")
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
