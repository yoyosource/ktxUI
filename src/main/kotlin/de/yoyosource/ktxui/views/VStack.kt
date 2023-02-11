package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.math.max
import kotlin.math.min

fun ViewContainer.VStack(builder: OrientedViewContainer.() -> Unit) {
    (+VStack()).builder()
}

class VStack internal constructor() : OrientedViewContainer(Orientation.VERTICAL) {
    override fun size(drawableData: DrawableData): Element {
        val size = Element(0, 0)
        children.forEach {
            val childSize = it.size(drawableData)
            size.x = max(size.x, childSize.x)
            size.y += childSize.y
        }
        return size
    }

    override fun size(drawableData: DrawableData, screenSize: Element, viewState: ViewState) {
        val currentSize = size(drawableData)
        val spacerSize = screenSize.copy() - currentSize

        val spacers = children.filterIsInstance<Spacer>()
        val splitSize = spacers.size + min(spacers(Orientation.VERTICAL) - spacers.size, 1)

        // println("Spacer: ${spacers.size}   Splitting: $splitSize   Size: $currentSize   Screen: $screenSize")
        var spacerCalculation = SpacerCalculation(spacerSize.y, splitSize)
        spacers.forEach {
            it.size(drawableData, screenSize.copy(height = spacerCalculation.next()), viewState)
        }

        val views = children.filterNot { viewState.sizeMap.containsKey(it) }
            .filter { it.spacers(Orientation.VERTICAL) > 0 }
            .toSet()
        val componentSplitSize: Int = spacerSize.y / if (splitSize == 0) 1 else splitSize
        spacerCalculation = SpacerCalculation(spacerSize.y, views.size)
        // println("Other Views sizes: $componentSplitSize   Components: ${views.size}")
        children.forEach {
            if (viewState.sizeMap.containsKey(it)) {
                return@forEach
            }
            val current = it.size(drawableData)
            current.x = screenSize.x
            if (views.contains(it)) {
                current.y = current.y + spacerCalculation.next()
            }
            it.size(drawableData, current, viewState)
        }
        viewState.sizeMap[this] = screenSize
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        val current = location.copy()
        children.forEach {
            it.draw(drawable, viewState, current.copy())
            current.y += viewState.sizeMap[it]!!.y
        }
        location + viewState.sizeMap[this]!!
    }
}
