package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.reflect.KProperty0

fun ViewContainer.Padding(builder: SingleViewContainer.() -> Unit): Padding {
    return (+PaddingImpl()).apply(builder)
}

sealed interface Padding {
    fun padding(padding: Int): Padding
    fun padding(padding: KProperty0<Int>): Padding
    fun padding(side: Side, padding: Int): Padding
    fun padding(side: Side, padding: KProperty0<Int>): Padding
}

private class PaddingImpl : SingleViewContainer(), Padding {

    private var top: ViewOption<Int> = ViewOption(0)
    private var bottom: ViewOption<Int> = ViewOption(0)
    private var left: ViewOption<Int> = ViewOption(0)
    private var right: ViewOption<Int> = ViewOption(0)

    override fun padding(padding: Int) = apply {
        top.set(this, padding)
        bottom.set(this, padding)
        left.set(this, padding)
        right.set(this, padding)
    }

    override fun padding(padding: KProperty0<Int>) = apply {
        top.set(this, padding)
        bottom.set(this, padding)
        left.set(this, padding)
        right.set(this, padding)
    }

    override fun padding(side: Side, padding: Int) = apply {
        when (side) {
            Side.TOP -> top.set(this, padding)
            Side.BOTTOM -> bottom.set(this, padding)
            Side.LEFT -> left.set(this, padding)
            Side.RIGHT -> right.set(this, padding)
        }
    }

    override fun padding(side: Side, padding: KProperty0<Int>) = apply {
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

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        drawableData.debug(DebugMode.SIZE, "$this $location _ $screenSize [${left.get()} ${right.get()} ${top.get()} ${bottom.get()}]")
        child!!.size(drawableData, screenSize.copy() - Element(left.get() + right.get(), top.get() + bottom.get()), location.copy() + Element(left.get(), top.get()), viewState)
        location + screenSize
    }
}

private fun <V: ViewAPI> V.padding(mutator: Padding.() -> Unit): V {
    if (this !is View) return this
    val parent = this.parent!!
    if (parent is Padding) {
        parent.mutator()
        return this
    }
    val padding = PaddingImpl()
    parent.swap(this, padding)
    padding.apply {
        +this@padding
        this.mutator()
    }
    return this
}

fun <V: ViewAPI> V.padding(padding: Int) = padding { padding(padding) }

fun <V: ViewAPI> V.padding(padding: KProperty0<Int>) = padding { padding(padding) }

fun <V: ViewAPI> V.padding(side: Side, padding: Int) = padding { padding(side, padding) }

fun <V: ViewAPI> V.padding(side: Side, padding: KProperty0<Int>) = padding { padding(side, padding) }
