package de.yoyosource.ktxui.views.utils

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Single(builder: SingleViewBuilder): ViewAPI {
    return SingleImpl(this).apply(builder).child ?: Empty()
}

private class SingleImpl(private val viewContainer: ViewContainer): SingleViewContainer() {

    private var count = 0
    override fun size(drawableData: DrawableData): Element {
        TODO()
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        TODO()
    }

    override fun <T : View> T.unaryPlus(): T {
        if (this@SingleImpl.count > 0) {
            throw IllegalStateException("SingleViewContainer can only contain one view")
        }
        this@SingleImpl.viewContainer.apply {
            +this@unaryPlus
        }
        this@SingleImpl.child = this
        this@SingleImpl.count++
        return this
    }
}
