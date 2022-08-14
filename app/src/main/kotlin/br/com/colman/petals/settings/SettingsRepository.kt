package br.com.colman.petals.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

interface Property<T, Y> {
  fun get(): T
  operator fun component1() = get()

  fun set(value: Y)
  operator fun component2() = ::set
}

class SharedProperty(
  val datastore: DataStore<Preferences>,
  val key: Preferences.Key<String>,
  val defaultValue: String
) : Property<Flow<String>, String> {

  override fun get(): Flow<String> = datastore.data.map { it[key] ?: defaultValue }

  override fun set(value: String) {
    runBlocking { datastore.edit { it[key] = value } }
  }
}

class SettingsRepository(
  private val datastore: DataStore<Preferences>
) {

  val currencyIcon = SharedProperty(datastore, CurrencyIcon, "$")
  val dateFormat = SharedProperty(datastore, DateFormat, "yyyy-MM-dd")
  val timeFormat = SharedProperty(datastore, TimeFormat, "HH:mm")
  val dateTimeFormat = SharedProperty(datastore, DateTimeFormat, "yyyy-MM-dd HH:mm")

  companion object {
    val CurrencyIcon = stringPreferencesKey("currency_icon")
    val DateFormat = stringPreferencesKey("date_format")
    val TimeFormat = stringPreferencesKey("time_format")
    val DateTimeFormat = stringPreferencesKey("date_time_format")
  }
}
