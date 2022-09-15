package br.com.colman.petals.use.pause.repository.converter

import br.com.colman.petals.use.repository.converter.propertyConverterTests
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localTime
import io.kotest.property.checkAll
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME

class LocalTimeConverterTest : FunSpec({

  val target = LocalTimeConverter()

  include(propertyConverterTests(target))

  test("Converts LocalTime to ISO LocalTime String") {
    Arb.localTime().checkAll {
      target.convertToDatabaseValue(it) shouldBe it.format(ISO_LOCAL_TIME)
    }
  }

  test("Converts anything itself generates to something itself accepts") {
    Arb.localTime().checkAll {
      var string = target.convertToDatabaseValue(it)
      var localTime = target.convertToEntityProperty(string)

      repeat(1000) {
        string = target.convertToDatabaseValue(localTime)
        localTime = target.convertToEntityProperty(string)
      }
      localTime shouldBe it
      string shouldBe it.format(ISO_LOCAL_TIME)
    }
  }
})
