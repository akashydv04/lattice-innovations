package shyam.gunsariya.latticeinnvoations.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import shyam.gunsariya.latticeinnvoations.R

class MainActivity : AppCompatActivity(R.layout.activity_main){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_container) as NavHostFragment
        val navController = navHostFragment.navController


        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_container).navigateUp() || super.onSupportNavigateUp()
    }

}