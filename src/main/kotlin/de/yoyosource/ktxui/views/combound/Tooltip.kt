package de.yoyosource.ktxui.views.combound

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.api.views.layout.ZStack
import de.yoyosource.ktxui.api.views.getScreen
import de.yoyosource.ktxui.api.views.special.Conditional
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.utils.ViewState
import de.yoyosource.ktxui.views.events.onHover
import de.yoyosource.ktxui.views.position.RelativePosition
import de.yoyosource.ktxui.api.views.special.Dynamic
import de.yoyosource.ktxui.api.views.special.Empty

fun ViewContainer.Tooltip(builder: SingleViewBuilder): Tooltip {
    val tooltip = +TooltipImpl()
    tooltip.apply {
        ZStack {
            +DelegatingSize().apply(builder).also {
                it.delegate = { width, height ->
                    tooltip.innerSizeX = width
                    tooltip.innerSizeY = height
                }
            }
            Conditional(tooltip::visible) {
                RelativePosition(tooltip::relativePosX, tooltip::relativePosY) {
                    Dynamic(tooltip::dynamic)
                }
            }
        }.onHover { _, _, relativeX, relativeY, _, _ ->
            if (relativeX > tooltip.innerSizeX || relativeY > tooltip.innerSizeY) {
                tooltip.visible = false
                return@onHover
            }
            tooltip.visible = true
            tooltip.relativePosX = relativeX + 10
            tooltip.relativePosY = relativeY
        }
    }
    tooltip.getScreen().onHoverNothing {
        tooltip.visible = false
    }
    return tooltip
}

sealed interface Tooltip: ViewProtocol {
    fun setTooltip(tooltip: SingleViewBuilder): Tooltip
}

private class DelegatingSize: SingleViewContainer() {

    var delegate: (width: Int, height: Int) -> Unit = { _, _ -> }

    override fun size(drawableData: DrawableData): Element {
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        delegate(screenSize.x, screenSize.y)
        child?.size(drawableData, screenSize, location, viewState)
    }
}

private class TooltipImpl: SingleViewContainer(), Tooltip {

    var relativePosX by Observer(0)
    var relativePosY by Observer(0)
    var visible by Observer(false)
    var dynamic: SingleViewBuilder by Observer { Empty() }

    var innerSizeX = 0
    var innerSizeY = 0

    override fun size(drawableData: DrawableData): Element {
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        child?.size(drawableData, screenSize, location, viewState)
    }

    override fun setTooltip(tooltip: SingleViewBuilder): Tooltip {
        dynamic = tooltip
        return this
    }
}