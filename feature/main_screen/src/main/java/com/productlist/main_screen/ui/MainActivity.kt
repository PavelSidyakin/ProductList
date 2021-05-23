package com.productlist.main_screen.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.productlist.main_screen.MainScreenComponentHolder
import com.productlist.main_screen.R
import com.productlist.main_screen.impl.di.MainScreenInjector
import com.productlist.product_ui.fragment.ProductDetailsFragment
import com.productlist.product_ui.fragment.ProductListFragment

/**
 * The main launcher activity.
 */
class MainActivity : AppCompatActivity() {

    private val mainScreenComponent: MainScreenInjector by lazy {
        MainScreenComponentHolder.getInjector()
    }

    private val navController: NavController
        get() {
            return findNavController(R.id.nav_host_fragment_container)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true);
            setDisplayShowHomeEnabled(true);
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)

        ProductListFragment.observeProductId(this) { productId ->
            if (navHostFragment.childFragmentManager.primaryNavigationFragment is ProductListFragment) {
                navController.navigate(
                    R.id.action_productListFragment_to_productDetailsFragment,
                    ProductDetailsFragment.createArgumentsBundle(productId)
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}