package com.betuel.tracker_domain.use_case

import com.betuel.tracker_domain.model.MealType
import com.betuel.tracker_domain.model.TrackableFood
import com.betuel.tracker_domain.model.TrackedFood
import com.betuel.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import kotlin.math.roundToInt

class GetsFoodsForDate(
    private val repository: TrackerRepository
) {

    operator fun invoke(
        date: LocalDate
    ): Flow<List<TrackedFood>> {
        return repository.getFoodsForDate(date)
    }
}