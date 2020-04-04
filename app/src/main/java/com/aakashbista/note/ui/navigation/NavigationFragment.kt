package com.aakashbista.note.ui.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

interface NavigationFragment {

    /**
     * Called when the back button is pressed.
     *
     * @return true if the event has be handled
     */
    fun onBackPressed() = false

    fun NavDirections.navigateSafe(
        options: NavOptions? = null,
        extras: FragmentNavigator.Extras? = null
    ) {
        (this@NavigationFragment as? Fragment)?.findNavController()?.let { controller ->

            this.navigateSafe(controller = controller, options = options, extras = extras)
        }
    }
}
