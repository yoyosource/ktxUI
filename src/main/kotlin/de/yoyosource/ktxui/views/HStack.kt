package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.SpacerCalculation
import de.yoyosource.ktxui.utils.ViewState
import kotlin.math.max
import kotlin.math.min

fun ViewContainer.HStack(builder: OrientedViewContainer.() -> Unit) {
    (+HStack()).builder()
}

fun ViewContainer.HLeft(builder: OrientedViewContainer.() -> Unit) {
    HStack {
        builder()
        Spacer()
    }
}

fun ViewContainer.HCenter(builder: OrientedViewContainer.() -> Unit) {
    HStack {
        Spacer()
        builder()
        Spacer()
    }
}

fun ViewContainer.HRight(builder: OrientedViewContainer.() -> Unit) {
    HStack {
        Spacer()
        builder()
    }
}

private class HStack : OrientedViewContainer(Orientation.HORIZONTAL) {
    override fun size(drawableData: DrawableData): Element {
        val size = Element(0, 0)
        children.forEach {
            val childSize = it.size(drawableData)
            size.x += childSize.x
            size.y = max(size.y, childSize.y)
        }
        return size
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        val currentSize = size(drawableData)
        val spacerSize = screenSize.copy() - currentSize

        val spacers = children.filterIsInstance<Spacer>()
            .filter { it.isDynamic() }
            .map { it as View }
        val splitSize = spacers.size + min(spacers(Orientation.HORIZONTAL) - spacers.size, 1)

        val views = children.filterNot { it is Spacer }
            .filter { it.spacers(Orientation.HORIZONTAL) > 0 }
            .toSet()
        val componentSplitSize: Int = spacerSize.x / if (splitSize == 0) 1 else splitSize

        val spacerCalculation = SpacerCalculation(spacerSize.x, splitSize)
        val innerSpacerCalculation = SpacerCalculation(componentSplitSize, views.size)
        drawableData.debug(DebugMode.SIZE, "Spacer: ${spacers.size}   Splitting: $splitSize   Size: $currentSize   Screen: $screenSize   SpacerSize: $spacerSize")
        drawableData.debug(DebugMode.SIZE, "Other Views sizes: $componentSplitSize   Components: ${views.size}")
        val currentLocation = location.copy()
        children.forEach {
            if (it is Spacer && it.isDynamic()) {
                val size = screenSize.copy(width = spacerCalculation.next())
                it.size(drawableData, size, currentLocation, viewState)
                currentLocation.y = location.y
                drawableData.debug(DebugMode.SIZE, "HStack: $it $currentLocation")
                return@forEach
            }
            val current = it.size(drawableData).copy()
            current.y = screenSize.y
            if (views.contains(it)) {
                current.x = current.x + innerSpacerCalculation.next()
            }
            it.size(drawableData, current, currentLocation, viewState)
            currentLocation.y = location.y
            drawableData.debug(DebugMode.SIZE, "HStack: $it $currentLocation")
        }
        location.x = currentLocation.x
        location.y += currentSize.y
    }
}
