package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.reflect.KProperty0

fun ViewContainer.Image(image: String): Image {
    if (!image.startsWith("/")) {
        throw IllegalArgumentException("Image path must start with /")
    }
    return ImageIO.read(Image::class.java.getResourceAsStream(image)).let { return +Image { it } }
}

fun ViewContainer.Image(image: BufferedImage): Image {
    return +Image { image }
}

fun ViewContainer.Image(image: KProperty0<BufferedImage>): Image {
    return observableInit(image) { +Image(it) }
}

class Image internal constructor(val image: () -> BufferedImage): ViewElement() {

    override fun size(drawableData: DrawableData): Element {
        return image().let { Element(it.width, it.height) }
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        drawable.drawImage(image(), location)
        location + viewState.sizeMap[this]!!
    }
}