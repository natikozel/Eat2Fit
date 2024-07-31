package com.example.finalproj.util

import com.example.finalproj.database.models.Gender
import com.example.finalproj.database.models.Goal

const val BREAK_FAST_MULTIPLIER = 0.3
const val LUNCH_MULTIPLIER = 0.4
const val DINNER_MULTIPLIER = 0.4
const val TDEE_MULTIPLER = 1.375 // {TODO} Assume user is lightly active

fun dailyCaloriesConsumption(
    weight: Double,
    height: Double,
    age: Int,
    gender: Gender,
//    goal: Goal
): Double {

    if (gender == Gender.MALE)
        return (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age))
    else if (gender == Gender.FEMALE)
        return (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age))

    return 0.0
}