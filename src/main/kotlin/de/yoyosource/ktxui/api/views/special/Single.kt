package de.yoyosource.ktxui.api.views.special

import de.yoyosource.ktxui.DrawableData
import de.yoyosource.ktxui.SingleViewContainer
import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.ViewContainer
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.SingleViewBuilder
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Single(builder: SingleViewBuilder) {
    SingleImpl(this).apply(builder)
}

private class SingleImpl(private val viewContainer: ViewContainer): SingleViewContainer() {

    private var count = 0
    override fun size(drawableData: DrawableData): Element {
        TODO()
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        TODO()
    }

    override fun <T : ViewBase> T.unaryPlus(): T {
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
