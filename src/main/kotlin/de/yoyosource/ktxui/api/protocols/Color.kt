package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.ViewProtocol
import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.api.data.Color
import de.yoyosource.ktxui.utils.ViewOption
import kotlin.reflect.KProperty0

interface ForegroundColorProtocol<S, A> : ViewProtocol<S, A> where S : ViewBase, A : ForegroundColorProtocol<S, A> {
    val foregroundColor: ViewOption<Color>

    fun foregroundColor(color: Color): A {
        this.foregroundColor.set(selfView, color)
        return selfAPI
    }

    fun foregroundColor(color: KProperty0<Color>): A {
        this.foregroundColor.set(selfView, color)
        return selfAPI
    }
}

fun ForegroundColorProtocol<*, *>.getForegroundColor(): java.awt.Color {
    val color = foregroundColor.get()
    return java.awt.Color(color.red, color.green, color.blue, color.opacity)
}

interface BackgroundColorProtocol<S, A> : ViewProtocol<S, A> where S : ViewBase, A : BackgroundColorProtocol<S, A> {
    val backgroundColor: ViewOption<Color>

    fun backgroundColor(color: Color): A {
        this.backgroundColor.set(selfView, color)
        return selfAPI
    }

    fun backgroundColor(color: KProperty0<Color>): A {
        this.backgroundColor.set(selfView, color)
        return selfAPI
    }
}

fun BackgroundColorProtocol<*, *>.getBackgroundColor(): java.awt.Color {
    val color = backgroundColor.get()
    return java.awt.Color(color.red, color.green, color.blue, color.opacity)
}