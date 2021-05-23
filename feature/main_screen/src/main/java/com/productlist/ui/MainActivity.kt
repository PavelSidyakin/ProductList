package com.productlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.productlist.MainScreenComponentHolder
import com.productlist.impl.di.MainScreenInjector
import com.productlist.main_screen.R
import com.productlist.product_ui.fragment.ProductDetailsFragment
import com.productlist.product_ui.fragment.ProductListFragment

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
            try {
                navController.navigate(
                    R.id.action_productListFragment_to_productDetailsFragment,
                    ProductDetailsFragment.createArgumentsBundle(productId)
                )
            } catch (th: Throwable) {
                // ignore
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}