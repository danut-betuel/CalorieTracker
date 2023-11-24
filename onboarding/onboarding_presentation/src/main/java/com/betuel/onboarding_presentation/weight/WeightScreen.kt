package com.betuel.onboarding_presentation.weight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.betuel.core.R
import com.betuel.core.util.UiEvent
import com.betuel.core_ui.LocalSpacing
import com.betuel.onboarding_presentation.components.ActionButton
import com.betuel.onboarding_presentation.components.UnitTextField
import com.betuel.onboarding_presentation.destinations.ActivityLevelScreenDestination
import com.betuel.onboarding_presentation.destinations.WeightScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
internal fun WeightScreen(
    scaffoldState: ScaffoldState,
    navigator: DestinationsNavigator,
    viewModel: WeightViewModel = getViewModel()
) {
    UiEventEffect(
        uiEvents = viewModel.uiEvent,
        scaffoldState = scaffoldState,
        navigateToNextScreen = { navigator.navigate(ActivityLevelScreenDestination) }
    )

    WeightScreenContent(
        weight = viewModel.weight,
        onWeightEnter = viewModel::onWeightEnter,
        onNextClick = viewModel::onNextClick
    )
}

@Composable
fun WeightScreenContent(
    weight: String,
    onWeightEnter: (String) -> Unit,
    onNextClick: () -> Unit
) {
    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_weight),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = weight,
                onValueChange = onWeightEnter,
                unit = stringResource(id = R.string.kg)
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun UiEventEffect(
    uiEvents: Flow<UiEvent>,
    scaffoldState: ScaffoldState,
    navigateToNextScreen: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        uiEvents.collect { event ->
            when (event) {
                is UiEvent.Success -> navigateToNextScreen()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }
}
