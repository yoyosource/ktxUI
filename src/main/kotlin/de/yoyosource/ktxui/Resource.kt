package de.yoyosource.ktxui

import org.intellij.lang.annotations.Language
import java.io.File
import java.io.InputStream
import java.net.URL
import kotlin.reflect.KProperty

interface Resource<T : Any> : Observer<T>

abstract class ResourceImpl<T : Any> : ObserverImpl<T>(), Resource<T>

fun <T : Any> JarResource(path: String, reader: (InputStream?) -> T): Resource<T> {
    if (!path.startsWith("/")) {
        throw IllegalArgumentException("Path must start with /")
    }
    return object : ResourceImpl<T>() {

        private val value: T by lazy {
            val resource = Resource::class.java.getResourceAsStream(path)
                ?: throw IllegalArgumentException("Resource does not exist: $path")
            reader(resource)
        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            throw UnsupportedOperationException()
        }
    }
}

fun <T : Any> FileResource(path: String, reader: (File) -> T): Resource<T> {
    val file = File(path)
    return object : ResourceImpl<T>() {

        init {
            if (!file.exists()) {
                throw IllegalArgumentException("File does not exist: $path")
            }
            if (!file.isFile) {
                throw IllegalArgumentException("Path is not a file: $path")
            }

            Thread {
                var lastModified = file.lastModified()
                while (true) {
                    Thread.sleep(1000)
                    if (file.lastModified() > lastModified) {
                        lastModified = file.lastModified()
                        value = reader(file)
                        notifyObservers(reader(file))
                    }
                }
            }.start()
        }

        private var value: T = reader(file)

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            throw UnsupportedOperationException()
        }
    }
}

fun <T : Any> NetworkResource(url: String, reader: (InputStream) -> T): Resource<T> {
    return object : ResourceImpl<T>() {
        private val value: T by lazy {
            reader(URL(url).openStream())
        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            throw UnsupportedOperationException()
        }
    }
}