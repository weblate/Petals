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

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.FileWriter

data class HitTimerResearchEntry(
  val breathholdSeconds: Int,
  val highScore: Int
)

@Configuration
class HitTimeResearchConfiguration(
  @Value("\${PETALS_DB}") private val filePath: String
) {

  private val file = File(filePath)

  @Bean
  fun repository() = HitTimerResearchRepository(file)
}

class HitTimerResearchRepository(
  private val file: File
) {

  fun append(entry: HitTimerResearchEntry) {
    FileWriter(file, true).use {
      it.appendLine(entry.toCsv())
    }
  }

  private fun HitTimerResearchEntry.toCsv() =
    "$breathholdSeconds,$highScore"
}
