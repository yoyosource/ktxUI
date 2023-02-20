package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import java.awt.Color
import kotlin.reflect.KProperty0

fun <V: ViewContainer> V.Background(color: Color, builder: DrawableSingleViewContainer.() -> Unit): ViewAPI {
    return (+Background(ViewOption(color))).apply(builder)
}

fun <V: ViewContainer> V.Background(color: KProperty0<Color>, builder: DrawableSingleViewContainer.() -> Unit): ViewAPI {
    val _color = ViewOption(Color.BLACK)
    return (+Background(_color)).apply(builder).let {
        _color.set(it, color)
    }
}

private class Background constructor(private val color: ViewOption<Color>) : DrawableSingleViewContainer() {

    override fun size(drawableData: DrawableData): Element {
        return child?.size(drawableData) ?: Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        viewState.set(this, location, screenSize)
        child?.size(drawableData, screenSize.copy(), location.copy(), viewState)
        location + screenSize
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, size) = viewState.get(this)
        drawable.drawRectangle(location, size, color.get())
    }
}

fun <V: ViewAPI> V.background(color: Color): V {
    this as View
    val background = Background(ViewOption(color))
    parent!!.swap(this, background)
    background.apply { +this@background }
    return this
}

fun <V: ViewAPI> V.background(color: KProperty0<Color>): V {
    this as View
    val _color = ViewOption(Color.BLACK)
    val background = Background(_color)
    parent!!.swap(this, background)
    background.apply { +this@background }
    _color.set(background, color)
    return this
}
