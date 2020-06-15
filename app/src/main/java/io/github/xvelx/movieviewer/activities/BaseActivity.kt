package io.github.xvelx.movieviewer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import io.github.xvelx.movieviewer.R
import io.github.xvelx.movieviewer.util.start
import io.github.xvelx.movieviewer.util.stop
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base)

        navigationController = findNavController(R.id.fragmentNavHost)
        appBarConfiguration = AppBarConfiguration(navigationController.graph)
        setupActionBarWithNavController(navigationController, appBarConfiguration)
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()
            || super.onSupportNavigateUp()

    override fun onBackPressed() {
        appProgressBar.stop()
        super.onBackPressed()
    }

    fun showLoading() = appProgressBar.start()

    fun hideLoading() = appProgressBar.stop()
}