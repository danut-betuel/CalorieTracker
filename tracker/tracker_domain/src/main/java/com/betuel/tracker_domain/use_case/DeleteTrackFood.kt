package com.betuel.tracker_domain.use_case

import com.betuel.tracker_domain.model.MealType
import com.betuel.tracker_domain.model.TrackableFood
import com.betuel.tracker_domain.model.TrackedFood
import com.betuel.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class DeleteTrackFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(trackedFood: TrackedFood) {
        repository.deletedTrackedFood(trackedFood)
    }
}