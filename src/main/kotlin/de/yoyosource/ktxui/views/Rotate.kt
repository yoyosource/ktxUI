package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.reflect.KProperty0

fun ViewContainer.Rotate(builder: SingleViewContainer.() -> Unit): Rotate {
    return (+Rotate()).apply(builder)
}

class Rotate : SingleViewContainer() {

    private var angle: ViewOption<Double> = ViewOption(0.0)

    fun angle(angle: Double) = apply {
        this.angle.set(this, angle)
    }

    fun angle(angle: KProperty0<Double>) = apply {
        this.angle.set(this, angle)
    }

    private fun endPoint(size: Element, angle: Double): Element {
        val angle = Math.toRadians(angle)

        val x = size.x * cos(angle) - size.y * sin(angle)
        val y = size.x * sin(angle) + size.y * cos(angle)

        return Element(x, y)
    }

    private fun rotate(size: Element, angle: Double): Element {
        val halfSize = size.copy() / 2
        val angle = Math.toRadians(angle)

        val p1 = Element(0, 0) - halfSize
        val p2 = Element(size.x, 0) - halfSize
        val p3 = Element(0, size.y) - halfSize
        val p4 = Element(size.x, size.y) - halfSize

        val p1r = Element(p1.x * cos(angle) - p1.y * sin(angle), p1.x * sin(angle) + p1.y * cos(angle)) + halfSize
        val p2r = Element(p2.x * cos(angle) - p2.y * sin(angle), p2.x * sin(angle) + p2.y * cos(angle)) + halfSize
        val p3r = Element(p3.x * cos(angle) - p3.y * sin(angle), p3.x * sin(angle) + p3.y * cos(angle)) + halfSize
        val p4r = Element(p4.x * cos(angle) - p4.y * sin(angle), p4.x * sin(angle) + p4.y * cos(angle)) + halfSize

        val minX = minOf(p1r.x, p2r.x, p3r.x, p4r.x)
        val maxX = maxOf(p1r.x, p2r.x, p3r.x, p4r.x)
        val minY = minOf(p1r.y, p2r.y, p3r.y, p4r.y)
        val maxY = maxOf(p1r.y, p2r.y, p3r.y, p4r.y)

        // println("size: $size, minX: $minX, maxX: $maxX, minY: $minY, maxY: $maxY")

        return Element(maxX - minX, maxY - minY)
    }

    override fun size(drawableData: DrawableData): Element {
        return endPoint(child?.size(drawableData) ?: Element(0, 0), angle.get())
    }

    override fun size(drawableData: DrawableData, screenSize: Element, viewState: ViewState) {
        val size = size(drawableData)
        // val size = endPoint(screenSize, angle.get())
        child?.let {
            it.size(drawableData, size.copy(), viewState)
        }
        viewState.sizeMap[this] = size
        screenSize - size
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        val angle = angle.get()
        val size = viewState.sizeMap[this]!!
        val halfSize = size.copy() / 2
        drawable.translate(location.copy() + (endPoint(size, -angle) / 2) - halfSize) {
            drawable.rotate(Math.toRadians(angle)) {
                val newLocation = Element(0, 0)
                child?.let {
                    it.draw(drawable, viewState, newLocation)
                }
                location + newLocation
            }
        }
    }
}