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

var colorProperty by Observer(::testInt) {
    if (it > 50) {
        Color.RED
    } else {
        Color.BLACK
    }
}

var angle by Observer(0.0)

val True by Observer(true)

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
                            .color(::colorProperty)
                    }
                    /*HCenter {
                        Image("/test.png")
                    }*/
                }
            }
        }.padding(5)
    }

    /*
    screen = Screen {
        HCenter {
            VCenter {
                ZStack {
                    Rotate {
                        VStack {
                            Divider(100)
                        }
                    }.angle(0.0)
                    Rotate {
                        VStack {
                            Divider(100)
                        }
                    }.angle(45.0)
                    Rotate {
                        VStack {
                            Divider(100)
                        }
                    }.angle(90.0)
                    Rotate {
                        VStack {
                            Divider(100)
                        }
                    }.angle(135.0)
                    Rotate {
                        VStack {
                            Divider(100)
                        }
                    }.angle(180.0)
                    Rotate {
                        VStack {
                            Divider(100)
                        }
                    }.angle(225.0)
                    Rotate {
                        VStack {
                            Divider(100)
                        }
                    }.angle(270.0)
                    Rotate {
                        VStack {
                            Divider(100)
                        }
                    }.angle(315.0)

                    Rotate {
                        VStack {
                            Divider(100)
                        }
                    }.angle(::angle)
                }
            }
        }
    }

    screen = Screen {
        Conditional(::True, inverted = true) {
            Text("Hello World")
        }
    }
     */

    KtxUIFrame(screen)

    /*
    delayedAnimation(5000) {
        linearAnimation(::angle, Double.MAX_VALUE)
            .start()
    }.start()
     */

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
