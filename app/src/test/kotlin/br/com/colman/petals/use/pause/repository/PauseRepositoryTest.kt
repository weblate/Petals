package br.com.colman.petals.use.pause.repository

import br.com.colman.petals.ObjectBoxListener
import br.com.colman.petals.use.MyObjectBox
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localTime
import io.kotest.property.arbitrary.next
import io.objectbox.kotlin.boxFor

class PauseRepositoryTest : FunSpec({

  val box = listener(ObjectBoxListener()).store.boxFor<Pause>()

  val target = PauseRepository(box)

  val pause = Pause(Arb.localTime().next(), Arb.localTime().next())
  val otherPause = Pause(Arb.localTime().next(), Arb.localTime().next())

  test("Set") {
    target.set(pause)
    box.all.single() shouldBe pause
  }

  test("Replaces existing value after set") {
    target.set(pause)
    target.set(otherPause)
    box.all.single() shouldBe otherPause
  }

  test("Get") {
    box.put(pause)
    target.get() shouldBe pause
  }

  test("Delete") {
    box.put(pause)
    target.delete()
    box.all.shouldBeEmpty()
  }

  isolationMode = IsolationMode.SingleInstance
})
