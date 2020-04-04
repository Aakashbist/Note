package com.aakashbista.note.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator

fun NavDirections.navigateSafe(
    controller: NavController,
    options: NavOptions? = null,
    extras: FragmentNavigator.Extras? = null
) {
    val actionExists = controller.currentDestination?.getAction(this.actionId) != null
    if (actionExists) {
        controller.navigate(actionId, arguments, options, extras)
    }
}
