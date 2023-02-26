package de.yoyosource.ktxui.api.views

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.api.data.*
import de.yoyosource.ktxui.api.protocols.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import de.yoyosource.ktxui.utils.observableInit
import kotlin.math.max
import kotlin.reflect.KProperty0

fun ViewContainer.Text(text: String): Text<*> {
    return +TextImpl { text }
}

fun ViewContainer.Text(text: KProperty0<String>): Text<*> {
    return observableInit(text) { +TextImpl(it) }
}

sealed interface Text<S> : ViewProtocol<S, Text<S>>, PaddingProtocol<S, Text<S>>, ForegroundColorProtocol<S, Text<S>>, BorderProtocol<S, Text<S>>, FontProtocol<S, Text<S>> where S : ViewBase {
    val alignment: ViewOption<TextAlignment>

    fun alignment(alignment: TextAlignment): Text<S> {
        this.alignment.set(selfView, alignment)
        return selfAPI
    }

    fun alignment(alignment: KProperty0<TextAlignment>): Text<S> {
        this.alignment.set(selfView, alignment)
        return selfAPI
    }
}

private class TextImpl constructor(val text: () -> String) : DrawableView(), Text<TextImpl> {

    override val selfView: TextImpl = this
    override val selfAPI: TextImpl = this

    override val topPadding: ViewOption<Int> = ViewOption(0)
    override val bottomPadding: ViewOption<Int> = ViewOption(0)
    override val leftPadding: ViewOption<Int> = ViewOption(0)
    override val rightPadding: ViewOption<Int> = ViewOption(0)

    override val foregroundColor: ViewOption<Color> = ViewOption(black)

    override val borderColor: ViewOption<Color> = ViewOption(black)
    override val borderWidth: ViewOption<Int> = ViewOption(0)

    override val design: ViewOption<FontFamily> = ViewOption(Serif)
    override val weight: ViewOption<FontWeight> = ViewOption(Regular)
    override val size: ViewOption<Float> = ViewOption(12f)
    override val italic: ViewOption<Boolean> = ViewOption(false)
    override val strikeThrough: ViewOption<Boolean> = ViewOption(false)
    override val underline: ViewOption<Boolean> = ViewOption(false)
    override val kerning: ViewOption<Float> = ViewOption(0f)
    override val tracking: ViewOption<Float> = ViewOption(0f)
    override val ligatures: ViewOption<Boolean> = ViewOption(false)

    override val alignment: ViewOption<TextAlignment> = ViewOption(TextAlignment.LEFT)

    override fun size(drawableData: DrawableData): Element {
        val size = Element(0, 0)
        split(text()) {
            val textSize = drawableData.getTextSize(it, this)
            size.x = max(size.x, textSize.x)
            size.y += textSize.y
        }
        size + getWholePadding()
        return size
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        val size = size(drawableData)
        viewState.set(this, location, size)
        location + size
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val strings = mutableListOf<String>()
        split(text()) { strings.add(it) }

        val textAlignment = alignment.get()
        val widths = if (textAlignment == TextAlignment.LEFT) Array(strings.size) { 0 }.toList() else strings.map {
            drawable.getTextSize(it, this).x
        }
        val maxWidth by lazy {
            widths.max()
        }

        val current = viewState[this].first.copy() + getLeadingPadding()
        drawable.setColor(getForegroundColor())
        strings.forEachIndexed { index, s ->
            val x = when (textAlignment) {
                TextAlignment.CENTER -> current.x + (maxWidth - widths[index]) / 2
                TextAlignment.RIGHT -> current.x + maxWidth - widths[index]
                else -> current.x
            }
            current.y += drawable.drawText(s, this, Element(x, current.y))
        }

        val (position, size) = viewState[this]
        drawable.setColor(getBorderColor())
        drawable.drawBorder(this, position, size)
    }

    private fun split(s: String, consumer: (String) -> Unit) {
        var st = StringBuilder()
        for (i in s.indices) {
            if (s[i] == '\n') {
                consumer(st.toString())
                st = StringBuilder()
                if (i == s.length - 1) {
                    consumer(st.toString())
                }
            } else {
                st.append(s[i])
            }
        }
        if (st.isNotEmpty()) {
            consumer(st.toString())
        }
    }
}
