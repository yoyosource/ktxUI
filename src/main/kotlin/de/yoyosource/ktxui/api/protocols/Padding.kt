package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.ViewAPI
import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.api.data.Side
import de.yoyosource.ktxui.utils.ViewOption
import kotlin.reflect.KProperty0

interface PaddingAPI<S, A> : ViewAPI<S, A> where S : ViewBase, A : PaddingAPI<S, A> {
    val topPadding: ViewOption<Int>
    val bottomPadding: ViewOption<Int>
    val leftPadding: ViewOption<Int>
    val rightPadding: ViewOption<Int>

    fun padding(padding: Int): A {
        this.topPadding.set(selfView, padding)
        this.bottomPadding.set(selfView, padding)
        this.leftPadding.set(selfView, padding)
        this.rightPadding.set(selfView, padding)
        return selfAPI
    }

    fun padding(padding: KProperty0<Int>): A {
        this.topPadding.set(selfView, padding)
        this.bottomPadding.set(selfView, padding)
        this.leftPadding.set(selfView, padding)
        this.rightPadding.set(selfView, padding)
        return selfAPI
    }

    fun padding(side: Side, padding: Int): A {
        when (side) {
            Side.TOP -> this.topPadding.set(selfView, padding)
            Side.BOTTOM -> this.bottomPadding.set(selfView, padding)
            Side.LEFT -> this.leftPadding.set(selfView, padding)
            Side.RIGHT -> this.rightPadding.set(selfView, padding)
        }
        return selfAPI
    }

    fun padding(side: Side, padding: KProperty0<Int>): A {
        when (side) {
            Side.TOP -> this.topPadding.set(selfView, padding)
            Side.BOTTOM -> this.bottomPadding.set(selfView, padding)
            Side.LEFT -> this.leftPadding.set(selfView, padding)
            Side.RIGHT -> this.rightPadding.set(selfView, padding)
        }
        return selfAPI
    }
}

fun PaddingAPI<*, *>.getWholePadding(): Element {
    return Element(
        leftPadding.get() + rightPadding.get(),
        topPadding.get() + bottomPadding.get()
    )
}

fun PaddingAPI<*, *>.getLeadingPadding(): Element {
    return Element(
        leftPadding.get(),
        topPadding.get()
    )
}