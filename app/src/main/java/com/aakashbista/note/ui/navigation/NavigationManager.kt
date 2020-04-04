package com.aakashbista.note.ui.navigation

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.findNavController

class NavigationManager( private val activity: AppCompatActivity, @IdRes private val hostId: Int){
    val fragmentManager
        get() = activity.supportFragmentManager.findFragmentById(hostId)?.childFragmentManager

    val currentFragment: Fragment?
        get() = fragmentManager?.primaryNavigationFragment

    val currentNavigationFragment: NavigationFragment?
        get() = currentFragment as? NavigationFragment

    val controller
        get() = activity.findNavController(hostId)

    fun onBackPressed(fallback: (isRoot: Boolean) -> Unit) {
        val currentFragment = currentFragment as? NavigationFragment

        if (currentFragment == null) {
            fallback(false)
        } else {
            val handled = currentFragment.onBackPressed()

            if (!handled) {
                if (isRootDestination(destination = controller.currentDestination)) {
                    fallback(true)
                } else {
                    controller.navigateUp()
                }
            }
        }
    }

    fun isRootDestination(destination: NavDestination?): Boolean = destination?.id == controller.graph.startDestination
    fun popToRoot() = controller.popBackStack(controller.graph.startDestination, false)
}

