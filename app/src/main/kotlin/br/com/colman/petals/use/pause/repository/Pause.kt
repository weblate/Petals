package br.com.colman.petals.use.pause.repository

import br.com.colman.petals.use.pause.repository.converter.LocalTimeConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import java.time.LocalTime

@Entity
data class Pause(
  @Convert(converter = LocalTimeConverter::class, dbType = String::class)
  val startTime: LocalTime = LocalTime.now(),

  @Convert(converter = LocalTimeConverter::class, dbType = String::class)
  val endTime: LocalTime = LocalTime.now()
) {

  @Id var id: Long = 0

  @Transient
  private val passesThroughMidnight = startTime > endTime

  fun isActive(time: LocalTime = LocalTime.now()) = if (passesThroughMidnight) {
    time.isAfter(startTime) || time.isBefore(endTime)
  } else time in startTime..endTime
}
