package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewState
import de.yoyosource.ktxui.utils.observableInit
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.reflect.KProperty0

fun ViewContainer.Image(image: BufferedImage): Image {
    return +ImageImpl { image }
}

fun ViewContainer.Image(image: KProperty0<BufferedImage>): Image {
    return observableInit(image) { +ImageImpl(it) }
}

sealed interface Image: ViewAPI {
}

fun ImageJarResource(path: String): Resource<BufferedImage> {
    return JarResource(path, ImageIO::read)
}

fun ImageFileResource(path: String): Resource<BufferedImage> {
    return FileResource(path, ImageIO::read)
}

fun ImageNetworkResource(url: String): Resource<BufferedImage> {
    return NetworkResource(url, ImageIO::read)
}

private class ImageImpl constructor(val image: () -> BufferedImage): DrawableView(), Image {

    override fun size(drawableData: DrawableData): Element {
        return image().let { Element(it.width, it.height) }
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        drawable.drawImage(image(), viewState[this].first)
    }
}