package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.Protocol
import de.yoyosource.ktxui.utils.ViewOption
import kotlin.reflect.KProperty0

interface CornerRadiusProtocol<A> : Protocol<A>, ShapeProtocol<A> where A : CornerRadiusProtocol<A> {
    val arcWidth: ViewOption<Int>
    val arcHeight: ViewOption<Int>

    fun arcWidth(arcWidth: Int): A {
        this.arcWidth.set(redraw, arcWidth)
        return selfAPI
    }

    fun arcWidth(arcWidth: KProperty0<Int>): A {
        this.arcWidth.set(redraw, arcWidth)
        return selfAPI
    }

    fun arcHeight(arcHeight: Int): A {
        this.arcHeight.set(redraw, arcHeight)
        return selfAPI
    }

    fun arcHeight(arcHeight: KProperty0<Int>): A {
        this.arcHeight.set(redraw, arcHeight)
        return selfAPI
    }

    fun radius(radius: Int): A {
        this.arcWidth.set(redraw, radius)
        this.arcHeight.set(redraw, radius)
        return selfAPI
    }

    fun radius(radius: KProperty0<Int>): A {
        this.arcWidth.set(redraw, radius)
        this.arcHeight.set(redraw, radius)
        return selfAPI
    }
}