package com.aakashbista.note.extension


import androidx.fragment.app.DialogFragment

/**
 * Casts the parent navigation fragment to the given listener type,
 * throwing an exception if the fragment does not implement it.
 */
@Suppress("unchecked_cast")
inline fun <reified T> DialogFragment.getParentFragmentListener(
    includeDialogs: Boolean = false
): T {
    return try {
        val childFragmentManager = requireParentFragment().childFragmentManager
        val primaryFragment = childFragmentManager.primaryNavigationFragment
        if (!includeDialogs || primaryFragment is T) {
            primaryFragment as T
        } else {
            val indexOfCurrent = childFragmentManager.fragments.indexOf(element = this)
            childFragmentManager.fragments[indexOfCurrent - 1] as T
        }
    } catch (exception: Exception) {
        throw Exception("Parent navigation fragment must implement the dialog listener")
    }
}
