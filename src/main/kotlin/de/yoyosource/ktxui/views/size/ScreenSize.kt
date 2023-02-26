package de.yoyosource.ktxui.views.size

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

fun ViewContainer.ScreenSize(builder: SingleViewContainer.(screenWidth: KProperty0<Int>, screenHeight: KProperty0<Int>) -> Unit): ViewAPI {
    return (+ScreenSizeImpl()).apply {
        builder(this::width, this::height)
    }
}

private class ScreenSizeImpl : SingleViewContainer() {

    var width by Observer(0)
    var height by Observer(0)

    override fun size(drawableData: DrawableData): Element {
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        width = screenSize.x
        height = screenSize.y
        child?.size(drawableData, screenSize, location, viewState)
    }
}