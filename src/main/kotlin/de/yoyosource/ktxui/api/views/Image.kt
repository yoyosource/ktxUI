package de.yoyosource.ktxui.api.views

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.api.data.Color
import de.yoyosource.ktxui.api.data.Clear
import de.yoyosource.ktxui.api.protocols.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import de.yoyosource.ktxui.utils.observableInit
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.reflect.KProperty0

fun ViewContainer.Image(image: BufferedImage): Image<*> {
    return +ImageImpl { image }
}

fun ViewContainer.Image(image: KProperty0<BufferedImage>): Image<*> {
    return observableInit(image) { +ImageImpl(it) }
}

sealed interface Image<S> : ViewProtocol<S, Image<S>>, PaddingProtocol<S, Image<S>>, ForegroundColorProtocol<S, Image<S>> where S : ViewBase

private class ImageImpl constructor(val image: () -> BufferedImage): DrawableView(), Image<ImageImpl> {

    override val selfView: ImageImpl = this
    override val selfAPI: Image<ImageImpl> = this

    override val topPadding: ViewOption<Int> = ViewOption(0)
    override val bottomPadding: ViewOption<Int> = ViewOption(0)
    override val leftPadding: ViewOption<Int> = ViewOption(0)
    override val rightPadding: ViewOption<Int> = ViewOption(0)

    override val foregroundColor: ViewOption<Color> = ViewOption(Clear)

    override fun size(drawableData: DrawableData): Element {
        return image().let { Element(it.width, it.height) } + getWholePadding()
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        if (foregroundColor.get() == Clear) {
            drawable.drawImage(image(), viewState[this].first.copy() + getLeadingPadding())
        } else {
            drawable.drawImage(image(), viewState[this].first.copy() + getLeadingPadding(), getForegroundColor())
        }
    }
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
