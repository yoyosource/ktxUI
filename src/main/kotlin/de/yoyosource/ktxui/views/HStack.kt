package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.math.max
import kotlin.math.min

fun ViewContainer.HStack(builder: OrientedViewContainer.() -> Unit) {
    (+HStack()).builder()
}

class HStack internal constructor() : OrientedViewContainer(Orientation.HORIZONTAL) {
    override fun size(drawableData: DrawableData): Element {
        val size = Element(0, 0)
        children.forEach {
            val childSize = it.size(drawableData)
            size.x += childSize.x
            size.y = max(size.y, childSize.y)
        }
        return size
    }

    override fun size(drawableData: DrawableData, screenSize: Element, viewState: ViewState) {
        val currentSize = size(drawableData)
        val spacerSize = screenSize.copy() - currentSize

        val spacers = children.filterIsInstance<Spacer>()
        val splitSize = spacers.size + min(spacers(Orientation.HORIZONTAL) - spacers.size, 1)

        // println("Spacer: ${spacers.size}   Splitting: $splitSize   Size: $currentSize   Screen: $screenSize   SpacerSize: $spacerSize")
        var spacerCalculation = SpacerCalculation(spacerSize.x, splitSize)
        spacers.forEach {
            it.size(drawableData, screenSize.copy(width = spacerCalculation.next()), viewState)
        }

        val views = children.filterNot { viewState.sizeMap.containsKey(it) }
            .filter { it.spacers(Orientation.HORIZONTAL) > 0 }
            .toSet()
        val componentSplitSize: Int = spacerSize.x / if (splitSize == 0) 1 else splitSize
        spacerCalculation = SpacerCalculation(componentSplitSize, views.size)
        // println("Other Views sizes: $componentSplitSize   Components: ${views.size}")
        children.forEach {
            if (viewState.sizeMap.containsKey(it)) {
                return@forEach
            }
            val current = it.size(drawableData).copy()
            current.y = screenSize.y
            if (views.contains(it)) {
                current.x = current.x + spacerCalculation.next()
            }
            it.size(drawableData, current, viewState)
        }
        viewState.sizeMap[this] = screenSize
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        val current = location.copy()
        children.forEach {
            it.draw(drawable, viewState, current.copy())
            current.x += viewState.sizeMap[it]!!.x
        }
        location + viewState.sizeMap[this]!!
    }
}
