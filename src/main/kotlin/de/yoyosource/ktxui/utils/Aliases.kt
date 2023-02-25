package de.yoyosource.ktxui.utils

import de.yoyosource.ktxui.OrientedViewContainer
import de.yoyosource.ktxui.SingleViewContainer
import de.yoyosource.ktxui.ViewContainer

typealias ViewBuilder = ViewContainer.() -> Unit
typealias OrientedViewBuilder = OrientedViewContainer.() -> Unit
typealias SingleViewBuilder = SingleViewContainer.() -> Unit
