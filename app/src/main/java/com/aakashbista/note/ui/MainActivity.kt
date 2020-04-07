package com.aakashbista.note.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.aakashbista.note.R
import com.aakashbista.note.ui.fragment.DatePickerFragment
import com.aakashbista.note.ui.navigation.NavigationManager

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val navigationManager by lazy{
        NavigationManager(this, R.id.fragment)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.fragment)
    NavigationUI.setupActionBarWithNavController(this, navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        NavigationUI.navigateUp(navController, null as DrawerLayout?)
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        navigationManager.onBackPressed { isRoot ->
             true
        }
    }



}
