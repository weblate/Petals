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

plugins {
  kotlin("multiplatform")
  id("com.android.library")
}

repositories {
  mavenCentral()
  google()
}

kotlin {
  android()
  jvm()

  sourceSets {
    getByName("commonMain") {
      dependencies {
        api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
      }
    }
  }
}

android {
  compileSdk = 31
}
