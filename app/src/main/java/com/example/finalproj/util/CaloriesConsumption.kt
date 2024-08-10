package com.example.finalproj.util

import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.models.Gender
import com.example.finalproj.database.models.Goal


/* FORMULA
** This function calculates the daily calories consumption of a user based on the following parameters:
**
 */

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

    val bmr: Double

    if (gender == Gender.MALE)
        bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
    else // Female case
        bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)

    return bmr * TDEE_MULTIPLER
}



fun calculateProgress(caloriesGoal: Int, currentCalories: Int): Float {

    if (caloriesGoal < currentCalories)
        return 100f

    val res = (currentCalories * 100) / caloriesGoal.toFloat()
    return if (!res.isNaN())
        res
    else
        0f
}

fun calculateCalories(): Int {


    val meals = AuthenticationManager.getUser().previousMeals
    var totalCalories = 0
    if (meals != null) {
        for (meal in meals) {
            totalCalories += meal.value.calories!!.toInt()
        }
    }
    return totalCalories
}

fun leftoverCalories(): Int {
    val user = AuthenticationManager.getUser()
    val userCalories = user.maxCalories
    val currentCalories = calculateCalories()
    return if (userCalories != null) {
        userCalories - currentCalories
    } else {
        0
    }
}

fun testBasedOnGoal(goal: Goal): String {
    return when (goal) {
        Goal.LOSE -> "Limit"
        Goal.GAIN -> "Minimum"
        Goal.MAINTAIN -> "Goal"
    }
}

fun calculateRemaining(
    availableCalories: Int,
    currentCalories: Int,
): String {
    return if (availableCalories - currentCalories < 0) {
        "Overeating"
    } else {
        (availableCalories - currentCalories).toString()
    }
}