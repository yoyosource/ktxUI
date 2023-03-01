package de.yoyosource.ktxui

interface Protocol<A> : View where A : Protocol<A> {
    val redraw: () -> Unit
        get() = {}
    val selfAPI: A
}