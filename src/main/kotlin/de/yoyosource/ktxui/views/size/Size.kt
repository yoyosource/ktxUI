package de.yoyosource.ktxui.views.size

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

fun <V: ViewAPI> ViewContainer.Size(builder: SingleViewContainer.(innerWidth: KProperty0<Int>, innerHeight: KProperty0<Int>, use: V.() -> V) -> Unit): ViewAPI {
    return (+SizeImpl()).apply {
        builder(this::width, this::height) {
            this@apply.element = this as View
            return@builder this
        }
    }
}

private class SizeImpl : SingleViewContainer() {

    var element: View? = null
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