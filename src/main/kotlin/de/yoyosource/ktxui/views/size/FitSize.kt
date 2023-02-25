package de.yoyosource.ktxui.views.size

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

fun ViewContainer.FitSize(builder: SingleViewContainer.(width: KProperty0<Int>, height: KProperty0<Int>) -> Unit): ViewAPI {
    return (+FitSizeImpl()).apply {
        builder(this::width, this::height)
    }
}

private class FitSizeImpl : SingleViewContainer() {

    var width by Observer(0)
    var height by Observer(0)

    override fun size(drawableData: DrawableData): Element {
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        val size = size(drawableData)
        width = size.x
        height = size.y
        child?.size(drawableData, size, location, viewState)
    }

    override fun spacers(orientation: Orientation): Int {
        return 0
    }
}