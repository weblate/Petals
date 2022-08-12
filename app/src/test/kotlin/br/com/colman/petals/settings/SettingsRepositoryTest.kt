package br.com.colman.petals.settings

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import br.com.colman.petals.settings.SettingsRepository.Companion.CurrencyIcon
import br.com.colman.petals.settings.SettingsRepository.Companion.DateFormat
import br.com.colman.petals.settings.SettingsRepository.Companion.DateTimeFormat
import br.com.colman.petals.settings.SettingsRepository.Companion.TimeFormat
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.funSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

private fun SettingsTest(
  settingName: String,
  defaultValue: String,
  datastoreKey: Preferences.Key<String>,
  getValue: SettingsRepository.() -> Flow<String>,
  setValue: SettingsRepository.(String) -> Unit
) = funSpec {

  val datastore = PreferenceDataStoreFactory.create { tempfile(suffix = ".preferences_pb") }
  val target = SettingsRepository(datastore)

  context(settingName) {
    test("Default") {
      target.getValue().first() shouldBe defaultValue
    }

    test("Changes to specified") {
      target.setValue("XYZ")
      target.getValue().first() shouldBe "XYZ"
    }

    test("Persists new value to permanent storage") {
      target.setValue("XYZ")
      datastore.data.first().get(datastoreKey) shouldBe "XYZ"
    }
  }
}

class SettingsRepositoryTest : FunSpec({
  include(SettingsTest("Currency", "$", CurrencyIcon, SettingsRepository::currencyIcon, SettingsRepository::setCurrencyIcon))
  include(SettingsTest("Date", "yyyy-MM-dd", DateFormat, SettingsRepository::dateFormat, SettingsRepository::setDateFormat))
  include(SettingsTest("Time", "HH:mm", TimeFormat, SettingsRepository::timeFormat, SettingsRepository::setTimeFormat))
  include(SettingsTest("DateTime", "yyyy-MM-dd HH:mm", DateTimeFormat, SettingsRepository::dateTimeFormat, SettingsRepository::setDateTimeFormat))
})
