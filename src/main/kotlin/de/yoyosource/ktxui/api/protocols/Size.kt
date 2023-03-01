package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.ViewProtocol
import de.yoyosource.ktxui.utils.ViewOption
import kotlin.reflect.KProperty0

interface SizeProtocol<S, A> : ViewProtocol<S, A> where S : ViewBase, A : SizeProtocol<S, A> {
    fun size(size: Int): A
    fun size(size: KProperty0<Int>): A
}

interface RadiusProtocol<S, A> : ViewProtocol<S, A>, SizeProtocol<S, A> where S : ViewBase, A : RadiusProtocol<S, A> {
    val radius: ViewOption<Int>

    fun radius(radius: Int): A {
        this.radius.set(redraw, radius)
        return selfAPI
    }

    fun radius(radius: KProperty0<Int>): A {
        this.radius.set(redraw, radius)
        return selfAPI
    }

    override fun size(size: Int): A {
        this.radius.set(redraw, size)
        return selfAPI
    }

    override fun size(size: KProperty0<Int>): A {
        this.radius.set(redraw, size)
        return selfAPI
    }
}

interface WidthHeightProtocol<S, A> : ViewProtocol<S, A>, SizeProtocol<S, A> where S : ViewBase, A : WidthHeightProtocol<S, A> {
    val width: ViewOption<Int>
    val height: ViewOption<Int>

    fun width(width: Int): A {
        this.width.set(redraw, width)
        return selfAPI
    }

    fun width(width: KProperty0<Int>): A {
        this.width.set(redraw, width)
        return selfAPI
    }

    fun height(height: Int): A {
        this.height.set(redraw, height)
        return selfAPI
    }

    fun height(height: KProperty0<Int>): A {
        this.height.set(redraw, height)
        return selfAPI
    }

    override fun size(size: Int): A {
        this.width.set(redraw, size)
        this.height.set(redraw, size)
        return selfAPI
    }

    override fun size(size: KProperty0<Int>): A {
        this.width.set(redraw, size)
        this.height.set(redraw, size)
        return selfAPI
    }
}