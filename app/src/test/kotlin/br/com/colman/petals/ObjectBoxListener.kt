package br.com.colman.petals

import br.com.colman.petals.use.MyObjectBox
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import io.objectbox.BoxStore
import kotlin.io.path.createTempDirectory

class ObjectBoxListener : TestListener {

  private val directory = createTempDirectory()
  val store: BoxStore = MyObjectBox.builder().directory(directory.toFile()).build()

  override suspend fun beforeSpec(spec: Spec) {
    store.removeAllObjects()
  }

  override suspend fun afterSpec(spec: Spec) {
    store.close()
  }
}