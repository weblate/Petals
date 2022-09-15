package br.com.colman.petals.use.pause.repository.converter

import io.objectbox.converter.PropertyConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME

class LocalTimeConverter : PropertyConverter<LocalTime, String> {
  override fun convertToEntityProperty(str: String?): LocalTime? {
    if (str == null) return null
    return LocalTime.parse(str, ISO_LOCAL_TIME)
  }

  override fun convertToDatabaseValue(localTime: LocalTime?): String? {
    if (localTime == null) return null
    return localTime.format(ISO_LOCAL_TIME)
  }
}
