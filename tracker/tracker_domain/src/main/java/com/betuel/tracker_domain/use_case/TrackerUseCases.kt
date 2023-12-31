package com.betuel.tracker_domain.use_case

data class TrackerUseCases(
    val trackFood: TrackFood,
    val searchFood: SearchFood,
    val getsFoodsForDate: GetsFoodsForDate,
    val deleteTrackedFood: DeleteTrackFood,
    val calculateMealNutrients: CalculateMealNutrients
)