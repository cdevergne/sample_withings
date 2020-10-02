package com.devergne.withings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.devergne.withings.R
import com.devergne.withings.data.Image
import com.devergne.withings.ui.list.ListFragmentDirections

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val navController = findNavController(R.id.nav_host_fragment)
    val appBarConfiguration = AppBarConfiguration(navController.graph)

    setupActionBarWithNavController(navController, appBarConfiguration)
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment)
    return navController.navigateUp() || super.onSupportNavigateUp()
  }

  fun navigateToDetail(imageList: List<Image>) {
    val action = ListFragmentDirections.actionListFragmentToDetailFragment(imageList.toTypedArray())
    findNavController(R.id.nav_host_fragment).navigate(action)
  }
}