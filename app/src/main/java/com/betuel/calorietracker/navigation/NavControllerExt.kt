package com.betuel.calorietracker.navigation

import androidx.navigation.NavController
import com.betuel.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}