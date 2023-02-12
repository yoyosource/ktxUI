package de.yoyosource

import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.Screen
import de.yoyosource.ktxui.animation.delayedAnimation
import de.yoyosource.ktxui.animation.linearAnimation
import de.yoyosource.ktxui.views.*
import java.awt.Color

var testInt by Observer(0)
var testText by Observer(::testInt) { "x$it" }
var testInt2 by Observer(::testInt) { it / 100 + 12 }

fun main() {
    var screen = Screen {
        VStack {
            Spacer()
            Text("Hello")
            VStack {
                Spacer()
                Text("Hello\n\nWorld")
                Spacer()
            }
            Text("World")
            Spacer()
        }
    }

    screen = Screen {
        VBottom {
            HCenter {
                ZStack {
                    HCenter {
                        Text("Hello World")
                    }
                    HCenter {
                        Text("____________").color(Color.RED)
                    }
                }
            }
            HCenter {
                Text(::testText)
            }
        }
    }

    screen = Screen {
        Padding {
            HCenter {
                VStack {
                    HCenter {
                        VStack {
                            Divider(5)
                        }
                    }
                    HCenter {
                        Divider(::testInt)
                    }
                    HCenter {
                        VStack {
                            Divider(5)
                        }
                    }
                    HRight {
                        Text(::testText)
                            .size(::testInt2)
                    }
                    HCenter {
                        Image("/test.png")
                    }
                }
            }
        }.padding(5)
    }

    KtxUIFrame(screen)

    /*
    animation {
        delayedAnimation(5000)
        loop {
            linearAnimation(::testInt, 1000)
            delayedAnimation(1000)
            linearAnimation(::testInt, 0)
            delayedAnimation(1000)
        }
    }.start()
     */

    delayedAnimation(5000) {
        val current = linearAnimation(::testInt, 1000)
            .start()
        current.onFinish {
            delayedAnimation(1000) {
                linearAnimation(::testInt, 0)
                    .start()
                    .onFinish {
                        delayedAnimation(1000) {
                            current.start()
                        }.start()
                    }
            }.start()
        }
    }.start()

    /*
    val image = BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB)
    val g = image.createGraphics() as Graphics2D
    val drawable = Graphics2dDrawable(g, 128, 128)

    drawable.draw(screen)
    screen.addRedrawListener {
        println("Redraw because of $it")
        drawable.draw(screen)
    }
    testInt = 64

    ImageIO.write(image, "png", File("test.png"))
     */
}