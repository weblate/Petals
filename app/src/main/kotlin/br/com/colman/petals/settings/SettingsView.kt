@file:OptIn(ExperimentalMaterialApi::class)

package br.com.colman.petals.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.colman.petals.R.string.currency_icon
import br.com.colman.petals.R.string.ok
import br.com.colman.petals.R.string.what_icon_should_be_used_for_currency
import com.skydoves.orchestra.spinner.Spinner
import compose.icons.TablerIcons
import compose.icons.tablericons.CalendarTime
import compose.icons.tablericons.Cash
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

private typealias StringFlowProperty = Property<Flow<String>, String>

@Composable
fun SettingsView(
  currencyProperty: StringFlowProperty,
  timeFormatProperty: StringFlowProperty,
  dateFormatProperty: StringFlowProperty,
  dateTimeFormatProperty: StringFlowProperty
) {
  Column {
    CurrencyListItem(currencyProperty)
  }
}

@Preview
@Composable
private fun CurrencyListItem(
  currencyProperty: StringFlowProperty = PreviewProperty(flowOf(""))
) {
  val (currency, setCurrency) = currencyProperty
  val currentCurrency by currency.collectAsState("$")

  var shouldShowDialog by remember { mutableStateOf(false) }

  ListItem(
    modifier = Modifier.clickable { shouldShowDialog = true },
    icon = { Icon(TablerIcons.Cash, null, Modifier.size(42.dp)) },

    secondaryText = { Text(stringResource(what_icon_should_be_used_for_currency)) }
  ) {
    Text(stringResource(currency_icon))
  }

  if (shouldShowDialog) {
    CurrencyDialog(currentCurrency, setCurrency) { shouldShowDialog = false }
  }
}

@Preview
@Composable
private fun DatesTimesListItem(
  timeProperty: StringFlowProperty = PreviewProperty(flowOf("HH:mm")),
  dateProperty: StringFlowProperty = PreviewProperty(flowOf("yyyy-MM-dd")),
  dateTimeProperty: StringFlowProperty = PreviewProperty(flowOf("yyyy-MM-dd HH:mm"))
) {
  val (time, setTime) = timeProperty
  val currentTime by time.collectAsState(
    ""
  )
  val (date, setDate) = dateProperty
  val currentDate by date.collectAsState("")

  val (dateTime, setDateTime) = dateTimeProperty
  val currentDateTime by dateTime.collectAsState("")

  var shouldShowDialog by remember { mutableStateOf(false) }

  ListItem(
    modifier = Modifier.clickable { shouldShowDialog = true },
    icon = { Icon(TablerIcons.CalendarTime, contentDescription = null, Modifier.size(42.dp)) },
    secondaryText = { Text("How we should format dates and times") }
  ) {
    Text("Date and Time format")
  }
  if (shouldShowDialog) {
    DatesTimesDialog(currentTime, setTime, currentDate, setDate, currentDateTime, setDateTime) {
      shouldShowDialog = false
    }
  }
}

@Preview
@Composable
fun DatesTimesDialog(
  currentTime: String = "HH:mm",
  setTime: (String) -> Unit = {},
  currentDate: String = "yyyy-MM-dd",
  setDate: (String) -> Unit = {},
  currentDateTime: String = "yyyy-MM-dd HH:mm",
  setDateTime: (String) -> Unit = {},
  onDismiss: () -> Unit = {}
) {
  var time by remember { mutableStateOf(currentTime) }
  var date by remember { mutableStateOf(currentDate) }
  var dateTime by remember { mutableStateOf(currentDateTime) }

  AlertDialog(
    onDismissRequest = onDismiss,
    text = {
      Column {

        val times = listOf("HH:mm", "hh:mm")
        val (selectedTime, setSelectedTime) = remember { mutableStateOf("HH:mm") }

        Row(verticalAlignment = CenterVertically) {
          Text("Time format")
          Spinner(
            text = selectedTime,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            itemList = times,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            spinnerPadding = 24.dp,
            spinnerBackgroundColor = Color.White,
            onSpinnerItemSelected = { index, item ->
              setSelectedTime(item)
            }
          ) {
            it.showArrow = true
          }
        }

        OutlinedTextField(
          time,
          { time = it },
          label = { Text("Time format") })












        OutlinedTextField(
          date,
          { date = it },
          label = { Text("Date format") })

        OutlinedTextField(
          dateTime,
          { dateTime = it },
          label = { Text("DateTime format") })
      }
    },
    confirmButton = {
      Text(
        stringResource(ok),
        Modifier
          .padding(8.dp)
          .clickable {
            setTime(time)
            setDate(date)
            setDateTime(dateTime)
            onDismiss()
          }
      )
    }
  )
}

@Preview
@Composable
private fun CurrencyDialog(
  currency: String = "$",
  setCurrency: (String) -> Unit = {},
  onDismiss: () -> Unit = {},
) {
  var currency by remember { mutableStateOf(currency) }

  AlertDialog(
    onDismissRequest = onDismiss,
    text = {
      OutlinedTextField(
        currency,
        { currency = it },
        label = { Text(stringResource(currency_icon)) })
    },
    confirmButton = {
      Text(
        stringResource(ok),
        Modifier
          .padding(8.dp)
          .clickable { setCurrency(currency); onDismiss() }
      )
    }
  )
}
