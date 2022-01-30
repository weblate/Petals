/*
 * Petals APP
 * Copyright (C) 2021 Leonardo Colman Lopes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package br.com.colman.petals.hittimer

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldStartWith
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.positiveInt

class HitTimerResearchRepositoryTest : ShouldSpec({

  val previousContent = "abc\ndef\n"
  val file = tempfile().apply { writeText(previousContent) }

  val target = HitTimerResearchRepository(file)

  should("Keep current data in the file intact") {
    target.append(hitTimerResearchEntryArb.next())

    file.readText() shouldStartWith previousContent
  }

  should("Append the datapoint to the end of the file") {
    val entry = hitTimerResearchEntryArb.next()
    target.append(entry)

    val (seconds, score) = entry

    file.readText() shouldEndWith "$seconds,$score\n"
  }
})


val hitTimerResearchEntryArb = arbitrary {
  HitTimerResearchEntry(
    Arb.positiveInt().next(it),
    Arb.positiveInt().next(it)
  )
}

