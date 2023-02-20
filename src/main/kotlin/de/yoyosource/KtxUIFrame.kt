package de.yoyosource

import de.yoyosource.ktxui.DebugMode
import de.yoyosource.ktxui.Drawable
import de.yoyosource.ktxui.ViewElement
import de.yoyosource.ktxui.impl.Graphics2dDrawable
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ModifierKey
import de.yoyosource.ktxui.utils.ViewState
import de.yoyosource.ktxui.views.Screen
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.*
import javax.swing.JFrame

class KtxUIFrame(private val screen: Screen) {

    private val debugModes: MutableSet<DebugMode> = mutableSetOf()

    private val jFrame = JFrame()
    private lateinit var drawable: Drawable
    private var viewState: ViewState? = null
    private var redraw = true

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
                width = canvas.width
                height = canvas.height
                viewState = null
                jFrame.repaint()
            }
        })

        canvas.addMouseListener(object: MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                val viewState = viewState ?: return
                e ?: return
                val viewElement = viewState[e.x, e.y] ?: return
                viewState.click(viewElement, e.x, e.y)
            }
        })

        canvas.addMouseMotionListener(object: MouseAdapter() {
            override fun mouseMoved(e: MouseEvent?) {
                val viewState = viewState ?: return
                e ?: return
                val viewElement = viewState[e.x, e.y] ?: return
                viewState.hover(viewElement, e.x, e.y)
            }

            override fun mouseDragged(e: MouseEvent?) {
                val viewState = viewState ?: return
                e ?: return
                val viewElement = viewState[e.x, e.y] ?: return
                viewState.drag(viewElement, e.x, e.y)
            }
        })

        canvas.addMouseWheelListener(object: MouseAdapter() {
            override fun mouseWheelMoved(e: MouseWheelEvent?) {
                val viewState = viewState ?: return
                e ?: return
                val viewElement = viewState[e.x, e.y] ?: return
                viewState.scroll(viewElement, e.x, e.y, e.preciseWheelRotation)
            }
        })

        canvas.addKeyListener(object: KeyAdapter() {
            private fun modifier(e: KeyEvent): Set<ModifierKey> {
                val modifiers = mutableSetOf<ModifierKey>()
                if (e.isAltDown) {
                    modifiers.add(ModifierKey.ALT)
                }
                if (e.isControlDown) {
                    modifiers.add(ModifierKey.CONTROL)
                }
                if (e.isShiftDown) {
                    modifiers.add(ModifierKey.SHIFT)
                }
                if (e.isMetaDown) {
                    modifiers.add(ModifierKey.META)
                }
                return modifiers
            }

            override fun keyTyped(e: KeyEvent?) {
                viewState?.type(e!!.keyCode, e.keyChar, modifier(e))
            }

            override fun keyPressed(e: KeyEvent?) {
                viewState?.press(e!!.keyCode, e.keyChar, modifier(e))
            }

            override fun keyReleased(e: KeyEvent?) {
                viewState?.release(e!!.keyCode, e.keyChar, modifier(e))
            }
        })

        canvas.focusTraversalKeysEnabled = false
        canvas.isFocusable = true
        canvas.requestFocus()

        // canvas.setSize(4 * 100, 3 * 100)
        canvas.setSize(896, 512)
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
            redraw = true
            if (it is ViewElement) {
                val previousSize = viewState!!.sizes[it]
                val newSize = it.size(drawable)
                if (previousSize != newSize) {
                    // println("Redraw because of size change: $previousSize -> $newSize for $it")
                    viewState = null
                }
            } else {
                viewState = null
            }
        }
        Thread {
            while (true) {
                if (redraw) {
                    redraw = false
                    canvas.repaint()
                }
                Thread.sleep(1000 / 60)
            }
        }.start()
    }

    private fun redraw(g: Graphics2D) {
        drawable = Graphics2dDrawable(g, width, height)
        debugModes.forEach {
            drawable.enableDebug(it)
        }
        var currentViewState = viewState
        if (currentViewState == null) {
            currentViewState = ViewState()
            val (width, height) = drawable.getSize()

            screen.size(drawable, Element(width, height), Element(0, 0), currentViewState)
        }
        drawable.fillBackground(Color.WHITE)
        currentViewState.order.forEach {
            if (debugModes.contains(DebugMode.DRAW_CLICKABLE)) {
                val (location, size) = currentViewState[it]
                drawable.drawRectangle(location.copy() - Element(1, 1), size.copy() + Element(2, 2), Color(50, 50, 50, 50))
            }
            it.draw(drawable, currentViewState)
        }
        viewState = currentViewState
    }

    fun enableDebug(debugMode: DebugMode) {
        debugModes.add(debugMode)
    }

    fun disableDebug(debugMode: DebugMode) {
        debugModes.remove(debugMode)
    }
}