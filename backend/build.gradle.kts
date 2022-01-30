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
import br.com.colman.petals.Libs
import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
  kotlin("jvm")
  id("org.springframework.boot") version "3.0.0-M1"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("plugin.spring") version "1.5.31"
}

java.sourceCompatibility = VERSION_17


repositories {
  mavenCentral()
  maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
  // Projects
  implementation(project(":common"))

  // Spring
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  testImplementation("org.springframework.boot:spring-boot-starter-test")


  // Kotest
  testImplementation(Libs.Kotest.junitRunner)
  testImplementation(Libs.Kotest.property)
  testImplementation(Libs.Kotest.spring)
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
