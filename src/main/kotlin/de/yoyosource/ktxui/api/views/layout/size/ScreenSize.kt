package de.yoyosource.ktxui.api.views.layout.size

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

fun ViewContainer.ScreenSize(builder: SingleViewContainer.(screenWidth: KProperty0<Int>, screenHeight: KProperty0<Int>) -> Unit): ScreenSize<*> {
    return (+ScreenSizeImpl()).apply {
        builder(this::width, this::height)
    }
}

sealed interface ScreenSize<S> : ViewProtocol<S, ScreenSize<S>> where S : ViewBase

private class ScreenSizeImpl : SingleViewContainer(), ScreenSize<ScreenSizeImpl> {

    override val selfAPI: ScreenSize<ScreenSizeImpl> = this
    override val selfView: ScreenSizeImpl = this

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