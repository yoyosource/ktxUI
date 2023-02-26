package de.yoyosource.ktxui.api.protocols

import de.yoyosource.ktxui.ViewProtocol
import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.api.data.*
import de.yoyosource.ktxui.utils.ViewOption
import java.awt.font.TextAttribute
import kotlin.reflect.KProperty0

interface FontProtocol<S, A> : ViewProtocol<S, A> where S : ViewBase, A : FontProtocol<S, A> {
    val design: ViewOption<FontFamily>
    val weight: ViewOption<FontWeight>
    val size: ViewOption<Float>
    val italic: ViewOption<Boolean>
    val strikeThrough: ViewOption<Boolean>
    val underline: ViewOption<Boolean>
    val kerning: ViewOption<Float>
    val tracking: ViewOption<Float>
    val ligatures: ViewOption<Boolean>

    fun font(font: Font): A {
        font.invoke(this)
        return selfAPI
    }

    fun design(design: FontFamily): A {
        this.design.set(selfView, design)
        return selfAPI
    }

    fun design(design: KProperty0<FontFamily>): A {
        this.design.set(selfView, design)
        return selfAPI
    }

    fun weight(weight: FontWeight): A {
        this.weight.set(selfView, weight)
        return selfAPI
    }

    fun weight(weight: KProperty0<FontWeight>): A {
        this.weight.set(selfView, weight)
        return selfAPI
    }

    fun size(size: Float): A {
        this.size.set(selfView, size)
        return selfAPI
    }

    fun size(size: KProperty0<Float>): A {
        this.size.set(selfView, size)
        return selfAPI
    }

    fun bold(bold: Boolean = true): A {
        this.weight.set(selfView, if (bold) Bold else Regular)
        return selfAPI
    }

    fun italic(italic: Boolean = true): A {
        this.italic.set(selfView, italic)
        return selfAPI
    }

    fun italic(italic: KProperty0<Boolean>): A {
        this.italic.set(selfView, italic)
        return selfAPI
    }

    fun strikeThrough(strikeThrough: Boolean = true): A {
        this.strikeThrough.set(selfView, strikeThrough)
        return selfAPI
    }

    fun strikeThrough(strikeThrough: KProperty0<Boolean>): A {
        this.strikeThrough.set(selfView, strikeThrough)
        return selfAPI
    }

    fun underline(underline: Boolean = true): A {
        this.underline.set(selfView, underline)
        return selfAPI
    }

    fun underline(underline: KProperty0<Boolean>): A {
        this.underline.set(selfView, underline)
        return selfAPI
    }

    fun kerning(kerning: Float = 0f): A {
        this.kerning.set(selfView, kerning)
        return selfAPI
    }

    fun kerning(kerning: KProperty0<Float>): A {
        this.kerning.set(selfView, kerning)
        return selfAPI
    }

    fun tracking(tracking: Float = 0f): A {
        this.tracking.set(selfView, tracking)
        return selfAPI
    }

    fun tracking(tracking: KProperty0<Float>): A {
        this.tracking.set(selfView, tracking)
        return selfAPI
    }

    fun ligatures(ligatures: Boolean = true): A {
        this.ligatures.set(selfView, ligatures)
        return selfAPI
    }

    fun ligatures(ligatures: KProperty0<Boolean>): A {
        this.ligatures.set(selfView, ligatures)
        return selfAPI
    }
}

fun FontProtocol<*, *>.getFont(): java.awt.Font {
    val font = java.awt.Font(design.get().family, 0, (size.get() + 0.5).toInt())
    val attributes: MutableMap<TextAttribute, Any> = font.attributes as MutableMap<TextAttribute, Any>
    attributes[TextAttribute.FAMILY] = design.get().family
    attributes[TextAttribute.WEIGHT] = weight.get().weight
    attributes[TextAttribute.POSTURE] = if (italic.get()) TextAttribute.POSTURE_OBLIQUE else TextAttribute.POSTURE_REGULAR
    attributes[TextAttribute.STRIKETHROUGH] = if (strikeThrough.get()) TextAttribute.STRIKETHROUGH_ON else false
    attributes[TextAttribute.UNDERLINE] = if (underline.get()) TextAttribute.UNDERLINE_ON else -1
    attributes[TextAttribute.KERNING] = if (kerning.get() > 0) TextAttribute.KERNING_ON else 0
    attributes[TextAttribute.TRACKING] = tracking.get()
    attributes[TextAttribute.LIGATURES] = if (ligatures.get()) TextAttribute.LIGATURES_ON else 0
    return java.awt.Font(attributes)
}