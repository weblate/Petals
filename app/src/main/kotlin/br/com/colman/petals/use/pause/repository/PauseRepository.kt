package br.com.colman.petals.use.pause.repository

import io.objectbox.Box

class PauseRepository(
  private val box: Box<Pause>
) {

  fun get(): Pause? {
    return box.all.firstOrNull()
  }

  fun set(pause: Pause) {
    box.removeAll()
    box.put(pause)
  }

  fun delete() {
    box.removeAll()
  }
}
