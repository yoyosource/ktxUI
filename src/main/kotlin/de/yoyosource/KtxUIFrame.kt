package de.yoyosource

import de.yoyosource.ktxui.Drawable
import de.yoyosource.ktxui.Element
import de.yoyosource.ktxui.Screen
import de.yoyosource.ktxui.ViewState
import de.yoyosource.ktxui.impl.Graphics2dDrawable
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JFrame

class KtxUIFrame(private val screen: Screen) {

    private val jFrame = JFrame()
    private lateinit var drawable: Drawable
    private var viewState: ViewState? = null

    private var width = 0
    private var height = 0

    init {
        jFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        val canvas = object: Canvas() {
            override fun paint(g: Graphics?) {
                redraw(g as Graphics2D)
            }
        }
        jFrame.addComponentListener(object: ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                width = jFrame.width
                height = jFrame.height
                viewState = null
                jFrame.repaint()
            }
        })

        canvas.focusTraversalKeysEnabled = false
        canvas.isFocusable = true
        canvas.requestFocus()

        canvas.setSize(4 * 100, 3 * 100)
        width = canvas.width
        height = canvas.height
        jFrame.setSize(width, height)
        jFrame.add(canvas)
        jFrame.pack()
        jFrame.isVisible = true

        screen.addRedrawListener {
            if (viewState == null) {
                return@addRedrawListener
            }
            val previousSize = viewState!!.sizeMap[it]
            val newSize = it.size(drawable)
            if (previousSize != newSize) {
                // println("Redraw because of size change: $previousSize -> $newSize for $it")
                viewState = null
            }
        }
        Thread {
            while (true) {
                canvas.repaint()
                Thread.sleep(1000 / 60)
            }
        }.start()
    }

    private fun redraw(g: Graphics2D) {
        drawable = Graphics2dDrawable(g, width, height)
        var currentViewState = viewState
        if (currentViewState == null) {
            currentViewState = ViewState()
            val (width, height) = drawable.getSize()

            screen.size(drawable, Element(width, height), currentViewState)
        }
        drawable.fillBackground(Color.WHITE)
        screen.draw(drawable, currentViewState, Element(0, 0))
        viewState = currentViewState
    }
}