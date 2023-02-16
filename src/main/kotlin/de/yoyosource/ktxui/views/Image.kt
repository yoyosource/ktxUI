package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.reflect.KProperty0

fun ViewContainer.Image(image: String): Image {
    if (!image.startsWith("/")) {
        throw IllegalArgumentException("Image path must start with /")
    }
    ImageIO.read(Image::class.java.getResourceAsStream(image)).let { return +ImageImpl { it } }
}

fun ViewContainer.Image(image: BufferedImage): Image {
    return +ImageImpl { image }
}

fun ViewContainer.Image(image: KProperty0<BufferedImage>): Image {
    return observableInit(image) { +ImageImpl(it) }
}

sealed interface Image: ViewAPI {
}

private class ImageImpl constructor(val image: () -> BufferedImage): ViewElement(), Image {

    override fun size(drawableData: DrawableData): Element {
        return image().let { Element(it.width, it.height) }
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        drawable.drawImage(image(), location)
        location + viewState.sizeMap[this]!!
    }
}