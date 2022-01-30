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

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/research/hit")
class HitTimerResearchController(
  private val repository: HitTimerResearchRepository
) {

  @PostMapping
  fun create(@RequestBody request: CreateEntryRequest): ResponseEntity<Any> {
    val validationResult = validateEntry(request)
    if(validationResult.errors.isEmpty()) {
      repository.append(request.toEntry())
      return ResponseEntity.status(CREATED).build()
    } else {
      return ResponseEntity.badRequest().body(validationResult.errors)
    }
  }
}

private fun CreateEntryRequest.toEntry() = HitTimerResearchEntry(breathholdSeconds, highScore)
