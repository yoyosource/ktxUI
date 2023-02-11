package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.ViewContainer

fun ViewContainer.HStack(builder: ViewContainer.() -> Unit) {
    val current = HStack()
    current.builder()
    children += current
}

class HStack : ViewContainer()
