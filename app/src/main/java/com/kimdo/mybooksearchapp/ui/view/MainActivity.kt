package com.kimdo.mybooksearchapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.kimdo.mybooksearchapp.BuildConfig
import com.kimdo.mybooksearchapp.R
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepository
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepositoryImpl
import com.kimdo.mybooksearchapp.databinding.ActivityMainBinding
import com.kimdo.mybooksearchapp.ui.viewmodel.BookSearchViewModel
import com.kimdo.mybooksearchapp.ui.viewmodel.BookSearchViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate( layoutInflater )}

    lateinit var bookSearchViewModel: BookSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )

        setupBottomNavigation()
        if( savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
        }

        val repository = BookSearchRepositoryImpl()
        val factory = BookSearchViewModelProviderFactory(repository,   this)
        bookSearchViewModel = ViewModelProvider(this, factory)[ BookSearchViewModel::class.java]

    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            changeFragment(item.itemId)
            true
        }
    }

    private fun changeFragment(itemId: Int) {
        val targetFrament = when( itemId ) {
            R.id.fragment_search -> SearchFragment()
            R.id.fragment_favorite -> FavoriteFragment()
            R.id.fragment_settings -> SettingsFragment()
            else -> return
        }
        supportFragmentManager.beginTransaction()
            .replace( R.id.framelayout, targetFrament)
            .commitAllowingStateLoss()
    }

    companion object {
        const val  TAG = "MainActivity"
    }


}