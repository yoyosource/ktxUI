package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import java.awt.Color
import java.awt.Font
import kotlin.math.max
import kotlin.reflect.KProperty0

fun ViewContainer.Text(text: String): Text {
    return +Text { text }
}

fun ViewContainer.Text(text: KProperty0<String>): Text {
    return observableInit(text) { +Text(it) }
}

class Text internal constructor(val text: () -> String) : View() {

    private var fontName: ViewOption<String> = ViewOption(Font.MONOSPACED)
    private var fontStyle: ViewOption<Int> = ViewOption(Font.PLAIN)
    private var fontSize: ViewOption<Int> = ViewOption(12)
    private var color: ViewOption<Color> = ViewOption(Color.BLACK)
    private var alignment: ViewOption<TextAlignment> = ViewOption(TextAlignment.LEFT)

    private var font
        get() = Font(fontName.get(), fontStyle.get(), fontSize.get())
        set(value) {
            fontName.set(this, value.name)
            fontStyle.set(this, value.style)
            fontSize.set(this, value.size)
        }

    fun color(color: Color) = this.color.set(this, color)

    fun color(color: KProperty0<Color>) = this.color.set(this, color)

    fun font(font: String) = this.fontName.set(this, font)

    fun font(font: KProperty0<String>) = this.fontName.set(this, font)

    fun style(style: Int) = this.fontStyle.set(this, style)

    fun style(style: KProperty0<Int>) = this.fontStyle.set(this, style)

    fun size(size: Int) = this.fontSize.set(this, size)

    fun size(size: KProperty0<Int>) = this.fontSize.set(this, size)

    fun alignment(alignment: TextAlignment) = this.alignment.set(this, alignment)

    fun alignment(alignment: KProperty0<TextAlignment>) = this.alignment.set(this, alignment)

    override fun size(drawableData: DrawableData): Element {
        val size = Element(0, 0)
        split(text()) {
            val textSize = drawableData.getTextSize(it, font)
            size.x = max(size.x, textSize.x)
            size.y += textSize.y
        }
        return size
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        val strings = mutableListOf<String>()
        split(text()) { strings.add(it) }

        val textAlignment = alignment.get()
        val widths = if (textAlignment == TextAlignment.LEFT) Array(strings.size) { 0 }.toList() else strings.map {
            drawable.getTextSize(it, font).x
        }
        val maxWidth = widths.max() ?: 0

        val current = location.copy()
        strings.forEachIndexed { index, s ->
            val x = when (textAlignment) {
                TextAlignment.CENTER -> current.x + (maxWidth - widths[index]) / 2
                TextAlignment.RIGHT -> current.x + maxWidth - widths[index]
                else -> current.x
            }
            current.y += drawable.drawText(s, font, color.get(), Element(x, current.y))
        }
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

enum class TextAlignment {
    LEFT, CENTER, RIGHT
}
