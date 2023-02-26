package de.yoyosource.ktxui.api.views.layout

import de.yoyosource.ktxui.DrawableData
import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.ViewProtocol
import de.yoyosource.ktxui.api.protocols.PaddingProtocol
import de.yoyosource.ktxui.api.protocols.getLeadingPadding
import de.yoyosource.ktxui.api.protocols.getWholePadding
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewBuilder
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import kotlin.math.max

fun ViewContainer.ZStack(builder: ViewBuilder): ZStack<*> {
    return (+ZStackImpl()).apply(builder)
}
sealed interface ZStack<S> : ViewProtocol<S, ZStack<S>>, PaddingProtocol<S, ZStack<S>> where S : ViewBase

private class ZStackImpl : ViewContainer(), ZStack<ZStackImpl> {

    override val selfView: ZStackImpl = this
    override val selfAPI: ZStack<ZStackImpl> = this

    override val topPadding: ViewOption<Int> = ViewOption(0)
    override val bottomPadding: ViewOption<Int> = ViewOption(0)
    override val leftPadding: ViewOption<Int> = ViewOption(0)
    override val rightPadding: ViewOption<Int> = ViewOption(0)

    override fun size(drawableData: DrawableData): Element {
        val size = Element(0, 0)
        children.forEach {
            val childSize = it.size(drawableData)
            size.x = max(size.x, childSize.x)
            size.y = max(size.y, childSize.y)
        }
        return size + getWholePadding()
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        val loc = location.copy() + getLeadingPadding()
        val size = screenSize.copy() - getWholePadding()
        children.forEach {
            it.size(drawableData, size.copy(), loc.copy(), viewState)
        }
        location + screenSize
    }
}
