package com.productlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.productlist.MainScreenComponentHolder
import com.productlist.impl.di.MainScreenInjector
import com.productlist.main_screen.R
import com.productlist.product_ui.fragment.ProductDetailsFragment
import com.productlist.product_ui.fragment.ProductListFragment

class MainActivity : AppCompatActivity() {

    private val mainScreenComponent: MainScreenInjector by lazy {
        MainScreenComponentHolder.getInjector()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    ProductListFragment(),
                )
                .commit()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is ProductListFragment -> fragment.onProductSelectedListener = { productId ->
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fragment_container,
                        ProductDetailsFragment.newInstance(productId),
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}