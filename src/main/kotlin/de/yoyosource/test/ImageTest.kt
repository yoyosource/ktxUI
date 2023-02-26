package de.yoyosource.test

import de.yoyosource.KtxUIFrame
import de.yoyosource.ktxui.api.views.Screen
import de.yoyosource.ktxui.api.views.Image
import de.yoyosource.ktxui.api.views.ImageJarResource

val testImage by ImageJarResource("/test.png")

fun main() {
    val screen = Screen {
        Image(::testImage)
    }

    KtxUIFrame(screen)
}