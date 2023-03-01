package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.Protocol
import de.yoyosource.ktxui.api.data.Color
import de.yoyosource.ktxui.utils.ViewOption
import kotlin.reflect.KProperty0

interface ForegroundColorProtocol<A> : Protocol<A> where A : ForegroundColorProtocol<A> {
    val foregroundColor: ViewOption<Color>

    fun foregroundColor(color: Color): A {
        this.foregroundColor.set(redraw, color)
        return selfAPI
    }

    fun foregroundColor(color: KProperty0<Color>): A {
        this.foregroundColor.set(redraw, color)
        return selfAPI
    }
}

fun ForegroundColorProtocol<*>.getForegroundColor(): java.awt.Color {
    val color = foregroundColor.get()
    return java.awt.Color(color.red, color.green, color.blue, color.opacity)
}

interface BackgroundColorProtocol<A> : Protocol<A> where A : BackgroundColorProtocol<A> {
    val backgroundColor: ViewOption<Color>

    fun backgroundColor(color: Color): A {
        this.backgroundColor.set(redraw, color)
        return selfAPI
    }

    fun backgroundColor(color: KProperty0<Color>): A {
        this.backgroundColor.set(redraw, color)
        return selfAPI
    }
}

fun BackgroundColorProtocol<*>.getBackgroundColor(): java.awt.Color {
    val color = backgroundColor.get()
    return java.awt.Color(color.red, color.green, color.blue, color.opacity)
}