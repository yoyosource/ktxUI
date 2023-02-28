package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.ViewProtocol
import de.yoyosource.ktxui.utils.ViewOption
import kotlin.reflect.KProperty0

interface CornerRadiusProtocol<S, A> : ViewProtocol<S, A>, ShapeProtocol<S, A> where S : ViewBase, A : CornerRadiusProtocol<S, A> {
    val arcWidth: ViewOption<Int>
    val arcHeight: ViewOption<Int>

    fun arcWidth(arcWidth: Int): A {
        this.arcWidth.set(selfView, arcWidth)
        return selfAPI
    }

    fun arcWidth(arcWidth: KProperty0<Int>): A {
        this.arcWidth.set(selfView, arcWidth)
        return selfAPI
    }

    fun arcHeight(arcHeight: Int): A {
        this.arcHeight.set(selfView, arcHeight)
        return selfAPI
    }

    fun arcHeight(arcHeight: KProperty0<Int>): A {
        this.arcHeight.set(selfView, arcHeight)
        return selfAPI
    }

    fun radius(radius: Int): A {
        this.arcWidth.set(selfView, radius)
        this.arcHeight.set(selfView, radius)
        return selfAPI
    }

    fun radius(radius: KProperty0<Int>): A {
        this.arcWidth.set(selfView, radius)
        this.arcHeight.set(selfView, radius)
        return selfAPI
    }
}