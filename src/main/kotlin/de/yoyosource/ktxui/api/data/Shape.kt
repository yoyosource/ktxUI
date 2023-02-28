package de.yoyosource.ktxui.api.data

import de.yoyosource.ktxui.Drawable
import de.yoyosource.ktxui.api.protocols.BorderProtocol
import de.yoyosource.ktxui.utils.Element

interface Shape {
    fun drawBorder(location: Element, size: Element, drawable: Drawable, borderAPI: BorderProtocol<*, *>)
    fun drawShape(location: Element, size: Element, drawable: Drawable)
}

class Circle : Shape {
    override fun drawBorder(location: Element, size: Element, drawable: Drawable, borderAPI: BorderProtocol<*, *>) {
        TODO("Not yet implemented")
    }

    override fun drawShape(location: Element, size: Element, drawable: Drawable) {
        TODO("Not yet implemented")
    }
}

class Rectangle : Shape {
    override fun drawBorder(location: Element, size: Element, drawable: Drawable, borderAPI: BorderProtocol<*, *>) {
        TODO("Not yet implemented")
    }

    override fun drawShape(location: Element, size: Element, drawable: Drawable) {
        TODO("Not yet implemented")
    }
}

class Oval : Shape {
    override fun drawBorder(location: Element, size: Element, drawable: Drawable, borderAPI: BorderProtocol<*, *>) {
        TODO("Not yet implemented")
    }

    override fun drawShape(location: Element, size: Element, drawable: Drawable) {
        TODO("Not yet implemented")
    }
}

class RoundRectangle : Shape {
    override fun drawBorder(location: Element, size: Element, drawable: Drawable, borderAPI: BorderProtocol<*, *>) {
        TODO("Not yet implemented")
    }

    override fun drawShape(location: Element, size: Element, drawable: Drawable) {
        TODO("Not yet implemented")
    }
}
