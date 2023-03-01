package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.ViewProtocol
import de.yoyosource.ktxui.api.data.Color
import de.yoyosource.ktxui.utils.ViewOption
import kotlin.reflect.KProperty0

interface BorderProtocol<S, A> : ViewProtocol<S, A> where S : ViewBase, A : BorderProtocol<S, A> {
    val borderColor: ViewOption<Color>
    val borderWidth: ViewOption<Int>

    fun border(color: Color, width: Int = 1): A {
        this.borderColor.set(redraw, color)
        this.borderWidth.set(redraw, width)
        return selfAPI
    }

    fun border(color: KProperty0<Color>, width: Int = 1): A {
        this.borderColor.set(redraw, color)
        this.borderWidth.set(redraw, width)
        return selfAPI
    }

    fun border(color: Color, width: KProperty0<Int>): A {
        this.borderColor.set(redraw, color)
        this.borderWidth.set(redraw, width)
        return selfAPI
    }

    fun border(color: KProperty0<Color>, width: KProperty0<Int>): A {
        this.borderColor.set(redraw, color)
        this.borderWidth.set(redraw, width)
        return selfAPI
    }
}

fun BorderProtocol<*, *>.getBorderColor(): java.awt.Color {
    val color = borderColor.get()
    return java.awt.Color(color.red, color.green, color.blue, color.opacity)
}