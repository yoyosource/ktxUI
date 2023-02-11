package de.yoyosource.ktxui

enum class Orientation {
    HORIZONTAL,
    VERTICAL
}

enum class Side {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT
}

class ViewState

class Size(width: Int, height: Int) {

    var width: Int = width
        set(value) {
            field = value
            if (field < 0) {
                field = 0
            }
        }

    var height: Int = height
        set(value) {
            field = value
            if (field < 0) {
                field = 0
            }
        }

    fun component1() = width
    fun component2() = height

    operator fun plus(other: Size): Size {
        width += other.width
        height += other.height
        return this
    }

    operator fun minus(other: Size): Size {
        width -= other.width
        height -= other.height
        return this
    }

    fun copy(width: Int = this.width, height: Int = this.height): Size {
        return Size(width, height)
    }

    override fun toString(): String {
        return "Size(width=$width, height=$height)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Size) return false

        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }
}