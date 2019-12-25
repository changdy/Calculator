package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewModel = ViewModelProviders.of(this, SavedStateViewModelFactory(application, this))
            .get(MyViewModel::class.java)
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        when (navController.currentDestination!!.id) {
            R.id.questionFragment -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.quit_dialog_title))
                builder.setPositiveButton(R.string.dialog_positive_msg) { _, _ ->
                    myViewModel.getCurrentScore().value = 0
                    navController.navigateUp()
                }
                builder.setNegativeButton(R.string.dialog_negative_msg) { _, _ -> }
                builder.create().show()
            }
            R.id.titleFragment -> finish()
            else -> navController.navigate(R.id.titleFragment)

        }
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }
}
