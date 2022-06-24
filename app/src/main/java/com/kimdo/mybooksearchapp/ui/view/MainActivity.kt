package com.kimdo.mybooksearchapp.ui.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.viewModels
import androidx.datastore.preferences.preferencesDataStore

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.WorkManager

import com.kimdo.mybooksearchapp.R
import com.kimdo.mybooksearchapp.databinding.ActivityMainBinding
//import com.kimdo.mybooksearchapp.ui.viewmodel.BookSearchViewModel

import com.kimdo.mybooksearchapp.util.Constants.DATASTORE_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate( layoutInflater )}

//    lateinit var bookSearchViewModel: BookSearchViewModel
//    val bookSearchViewModel by viewModels<BookSearchViewModel>()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val Context.dataStore by preferencesDataStore( DATASTORE_NAME )
    private val workManager = WorkManager.getInstance(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )

        setupJetpackNavigation()
//        val database = BookSearchDatabase.getInstance(this)
//        val repository = BookSearchRepositoryImpl(database, dataStore)
//        val factory = BookSearchViewModelProviderFactory(repository,  workManager, this)
//        bookSearchViewModel = ViewModelProvider(this, factory)[ BookSearchViewModel::class.java]
    }

    private fun setupJetpackNavigation() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController( navController )

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_search, R.id.fragment_favorite, R.id.fragment_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        const val  TAG = "MainActivity"
    }
}