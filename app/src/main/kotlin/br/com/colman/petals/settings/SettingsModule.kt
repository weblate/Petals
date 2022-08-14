package br.com.colman.petals.settings

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val Context.settingsDatastore by preferencesDataStore("settings")

val SettingsModule = module {
  single { SettingsRepository(get<Context>().settingsDatastore) }

  single(named("currencyIcon")) { get<SettingsRepository>().currencyIcon }
  single(named("timeFormat")) { get<SettingsRepository>().timeFormat }
  single(named("dateFormat")) { get<SettingsRepository>().dateFormat }
  single(named("dateTimeFormat")) { get<SettingsRepository>().dateTimeFormat }
}
