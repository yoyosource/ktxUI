package de.yoyosource.ktxui.api.views.layout.size

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

fun <V: ViewProtocol<*, *>> ViewContainer.Size(builder: SingleViewContainer.(innerWidth: KProperty0<Int>, innerHeight: KProperty0<Int>, use: V.() -> V) -> Unit): Size<*> {
    return (+SizeImpl()).apply {
        builder(this::width, this::height) {
            this@apply.element = this.selfView
            return@builder this
        }
    }
}

sealed interface Size<S> : ViewProtocol<S, Size<S>> where S : ViewBase

private class SizeImpl : SingleViewContainer(), Size<SizeImpl> {

    override val selfAPI: Size<SizeImpl> = this
    override val selfView: SizeImpl = this

    var element: ViewBase? = null
    var width by Observer(0)
    var height by Observer(0)

    override fun size(drawableData: DrawableData): Element {
        if (element != null) {
            val (width, height) = element!!.size(drawableData)
            this.width = width
            this.height = height
        }
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        child?.size(drawableData, screenSize, location, viewState)
    }
}