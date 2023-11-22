package com.betuel.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betuel.core.domain.preferences.Preferences
import com.betuel.core.domain.use_case.FilterOutDigits
import com.betuel.core.util.UiEvent
import com.betuel.core.util.UiText
import com.betuel.core.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AgeViewModel(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {
    var age by mutableStateOf("20")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age: String) {
        if (age.length <= 3) {
            this.age = filterOutDigits(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.error_age_cant_be_empty)
                    )
                )
                return@launch
            }

            preferences.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Success)
        }
    }
}