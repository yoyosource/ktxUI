package de.yoyosource.ktxui

import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

@DslMarker
annotation class KtxUIDsl

sealed interface View

// This is equivalent to `protocols` from Swift
interface ViewProtocol<S, A> : View where S : ViewBase, A : ViewProtocol<S, A> {
    val selfView: S
    val selfAPI: A
}

@KtxUIDsl
abstract class ViewBase : View {

    internal var parent: ViewContainer? = null

    open fun redraw(view: ViewBase = this) {
        parent?.redraw(view)
    }

    abstract fun size(drawableData: DrawableData): Element
    abstract fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState)

    open fun spacers(orientation: Orientation): Int {
        return 0
    }
}

abstract class DrawableView : ViewBase() {
    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        val size = size(drawableData)
        viewState.set(this, location, size)
        location + size
    }

    abstract fun draw(drawable: Drawable, viewState: ViewState)
}

abstract class ViewContainer : ViewBase() {
    internal val children: MutableList<ViewBase> = mutableListOf()

    open operator fun <T : ViewBase> T.unaryPlus(): T {
        if (this@ViewContainer.children.contains(this)) {
            throw IllegalStateException("Child already set")
        }
        this@ViewContainer.children += this
        this.parent = this@ViewContainer
        return this
    }

    open fun swap(old: ViewBase, new: ViewBase) {
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
    internal var child: ViewBase? = null

    override operator fun <T : ViewBase> T.unaryPlus(): T {
        if (this@SingleViewContainer.child != null) {
            throw IllegalStateException("SingleViewContainer can only contain one view")
        }
        this@SingleViewContainer.child = this
        this.parent = this@SingleViewContainer
        return this
    }

    override fun swap(old: ViewBase, new: ViewBase) {
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
