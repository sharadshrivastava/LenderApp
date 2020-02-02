package com.test.lenderapp.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.test.lenderapp.R
import com.test.lenderapp.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name
    private val allPermission = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance()).commitNow()
        }
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
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), allPermission)
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
}
