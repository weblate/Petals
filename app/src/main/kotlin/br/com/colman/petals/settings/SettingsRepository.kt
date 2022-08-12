package br.com.colman.petals.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class SettingsRepository(
  private val datastore: DataStore<Preferences>
) {

  val currencyIcon = datastore.data.map { it[CurrencyIcon] ?: "$" }
  val dateFormat = datastore.data.map { it[DateFormat] ?: "yyyy-MM-dd" }
  val timeFormat = datastore.data.map { it[TimeFormat] ?: "HH:mm" }
  val dateTimeFormat = datastore.data.map { it[DateTimeFormat] ?: "yyyy-MM-dd HH:mm" }

  fun setCurrencyIcon(value: String): Unit = runBlocking {
    datastore.edit { it[CurrencyIcon] = value }
  }

  fun setDateFormat(value: String): Unit = runBlocking {
    datastore.edit { it[DateFormat] = value }
  }

  fun setTimeFormat(value: String): Unit = runBlocking {
    datastore.edit { it[TimeFormat] = value }
  }

  fun setDateTimeFormat(value: String): Unit = runBlocking {
    datastore.edit { it[DateTimeFormat] = value }
  }

  companion object {
    val CurrencyIcon = stringPreferencesKey("currency_icon")
    val DateFormat = stringPreferencesKey("date_format")
    val TimeFormat = stringPreferencesKey("time_format")
    val DateTimeFormat = stringPreferencesKey("date_time_format")
  }
}
