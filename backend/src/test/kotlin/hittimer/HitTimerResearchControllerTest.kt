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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.io.File

@SpringBootTest
@AutoConfigureMockMvc
class HitTimerResearchControllerTest(
  mockMvc: MockMvc,
  @Value("\${PETALS_DB}") dbFile: String
) : FunSpec({

  test("Returns 201 when creating a new hit entry") {
    val response = mockMvc.makePost(5, 1)

    response.status shouldBe 201
  }

  test("Appends the new entry to the file") {
    mockMvc.makePost(10, 20)

    val file = File(dbFile)
    file.readText() shouldContain "10,20\n"
  }

  beforeTest {
    File(dbFile).apply {
      if (exists()) delete()
    }
  }

})

private fun MockMvc.makePost(breathhold: Int, highScore: Int): MockHttpServletResponse {
  return perform(
    post("/research/hit").content(
      """
      {
      "breathhold_seconds": $breathhold,
      "high_score": $highScore
      }
    """.trimIndent()
    ).contentType(APPLICATION_JSON)
  ).andReturn().response
}
