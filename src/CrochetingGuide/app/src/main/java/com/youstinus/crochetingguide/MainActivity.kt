package com.youstinus.crochetingguide

import android.content.Context
import android.os.Bundle

import com.google.android.material.navigation.NavigationView

import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController

import android.view.Menu
import android.view.WindowManager
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.youstinus.crochetingguide.fragments.schemes.scheme.Scheme
import com.youstinus.crochetingguide.fragments.schemes.SchemesFragment
import com.youstinus.crochetingguide.utilities.H
import devdeeds.com.changelanguage.LocaleHelper

class MainActivity : AppCompatActivity(), /*NavigationView.OnNavigationItemSelectedListener,*/ SchemesFragment.OnListFragmentInteractionListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun attachBaseContext(newBase: Context?) {
        val newB = languageCheck(newBase)
        super.attachBaseContext(newB)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        setupNavigation()
        setScreenOn()
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        /*val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)*/

        /*if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
            supportActionBar!!.title = "Titulinis"
            navigationView.setCheckedItem(R.id.nav_camera)
        }*/

    }

    private fun setupNavigation() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.nav_home, R.id.nav_guide, R.id.nav_terms, R.id.nav_tips, R.id.nav_schemes,
                        R.id.nav_settings, R.id.nav_ads
                ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onListFragmentInteraction(item: Scheme) {
        val bundle = Bundle()
        bundle.putSerializable("item", item)
        //val fragment = SchemeFragment.newInstance()
        //fragment.arguments = bundle
        findNavController(R.id.nav_host_fragment).navigate(R.id.nav_scheme, bundle)
//        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }

    private fun languageCheck(newBase: Context?): Context? {
        /*val lng = getLanguagePref(newBase)
        if (lng != null && lng != "") {
            mLanguageCode = lng
        } else {
            setLanguagePref(newBase, mLanguageCode)
        }*/

        var newB = newBase
        if (newBase != null)
            newB = LocaleHelper.setLocale(newBase, "lt")

        return newB
    }

    private fun setScreenOn() {
        val screen = H.cuGetPrefs("screen-on", this)
        if (screen == null) {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else if (screen == "true") {
            window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else if (screen == "false") {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    /*override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        return true
        val id = item.itemId
        when (item.itemId) {
            R.id.nav_camera -> supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HomeFragment()).commit()
            R.id.nav_gallery -> supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, GuideFragment()).commit()
            R.id.nav_slideshow -> supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, TermsFragment()).commit()
            R.id.nav_schemes -> supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, SchemesFragment()).commit()
            R.id.nav_manage -> {
            }
            R.id.nav_share -> {
            }
            R.id.nav_send -> {
            }
            R.id.nav_exit -> finish()
            else -> {
            }
        }


        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }*/
}
