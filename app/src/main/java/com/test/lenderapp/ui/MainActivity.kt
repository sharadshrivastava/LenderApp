package com.test.lenderapp.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.lenderapp.R
import com.test.lenderapp.ui.budget.BudgetFragment
import com.test.lenderapp.ui.home.HomeFragment
import com.test.lenderapp.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name
    private val allPermission = 1
    //lateinit var navigationView:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        setupNavigationView()
    }

    private fun checkPermissions() {
        var result: Int
        val listPermissionsNeeded = ArrayList<String>()
        val permissions = retrievePermissions()

        for (p in permissions.orEmpty()) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                allPermission
            )
        }
    }

    private fun retrievePermissions(): Array<String>? {
        try {
            return packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
                .requestedPermissions
        } catch (e: PackageManager.NameNotFoundException) {
            Log.v(TAG, e.stackTrace.toString());
        }
        return null;
    }

    private fun setupNavigationView(){
        bottomNavigation.setOnNavigationItemSelectedListener{
            handleNavigationItems(it.itemId)
        }
        bottomNavigation.setOnNavigationItemReselectedListener {
            //This override is required to avoid recreation of fragment on double click.
        }
        bottomNavigation.selectedItemId = R.id.accounts
    }

    private fun handleNavigationItems(id:Int):Boolean{
        var fragment: Fragment? = null
        when(id){
            R.id.budget -> fragment = BudgetFragment.newInstance()
            R.id.accounts -> fragment = HomeFragment.newInstance()
            R.id.profile -> fragment = ProfileFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment?:HomeFragment.newInstance()).commitNow()
        return true
    }
}
