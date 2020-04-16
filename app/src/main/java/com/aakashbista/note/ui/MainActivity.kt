package com.aakashbista.note.ui

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.aakashbista.note.R
import com.aakashbista.note.ui.fragment.DatePickerFragment
import com.aakashbista.note.ui.navigation.NavigationManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val navigationManager by lazy{
        NavigationManager(this, R.id.fragment)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar as androidx.appcompat.widget.Toolbar)

        navController = Navigation.findNavController(this, R.id.fragment)
        bottom_navigation.setupWithNavController(navController)
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
