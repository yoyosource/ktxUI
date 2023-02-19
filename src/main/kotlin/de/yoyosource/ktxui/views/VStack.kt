package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.math.max
import kotlin.math.min

fun ViewContainer.VStack(builder: OrientedViewContainer.() -> Unit) {
    (+VStack()).builder()
}

fun ViewContainer.VTop(builder: OrientedViewContainer.() -> Unit) {
    VStack {
        builder()
        Spacer()
    }
}

fun ViewContainer.VCenter(builder: OrientedViewContainer.() -> Unit) {
    VStack {
        Spacer()
        builder()
        Spacer()
    }
}

fun ViewContainer.VBottom(builder: OrientedViewContainer.() -> Unit) {
    VStack {
        Spacer()
        builder()
    }
}

private class VStack : OrientedViewContainer(Orientation.VERTICAL) {
    override fun size(drawableData: DrawableData): Element {
        val size = Element(0, 0)
        children.forEach {
            val childSize = it.size(drawableData)
            size.x = max(size.x, childSize.x)
            size.y += childSize.y
        }
        return size
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        val currentSize = size(drawableData)
        val spacerSize = screenSize.copy() - currentSize

        val spacers = children.filterIsInstance<Spacer>().map { it as View }
        val splitSize = spacers.size + min(spacers(Orientation.VERTICAL) - spacers.size, 1)

        val views = children.filterNot { it is Spacer }
            .filter { it.spacers(Orientation.VERTICAL) > 0 }
            .toSet()
        val componentSplitSize: Int = spacerSize.y / if (splitSize == 0) 1 else splitSize
        val spacerCalculation = SpacerCalculation(spacerSize.y, splitSize)
        val innerSpacerCalculation = SpacerCalculation(componentSplitSize, views.size)
        drawableData.debug(DebugMode.SIZE, "Spacer: ${spacers.size}   Splitting: $splitSize   Size: $currentSize   Screen: $screenSize   SpacerSize: $spacerSize")
        drawableData.debug(DebugMode.SIZE, "Other Views sizes: $componentSplitSize   Components: ${views.size}")
        val currentLocation = location.copy()
        children.forEach {
            if (it is Spacer) {
                val size = screenSize.copy(height = spacerCalculation.next())
                it.size(drawableData, size, currentLocation, viewState)
                currentLocation.x = location.x
                return@forEach
            }
            val current = it.size(drawableData).copy()
            current.x = screenSize.x
            if (views.contains(it)) {
                current.y = current.y + innerSpacerCalculation.next()
            }
            it.size(drawableData, current, currentLocation, viewState)
            currentLocation.x = location.x
        }
        location.y = currentLocation.y
    }
}
