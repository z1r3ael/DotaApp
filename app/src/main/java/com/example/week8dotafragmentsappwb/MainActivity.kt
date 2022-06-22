package com.example.week8dotafragmentsappwb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.week8dotafragmentsappwb.data.Utils
import com.example.week8dotafragmentsappwb.databinding.ActivityMainBinding
import com.example.week8dotafragmentsappwb.fragments.AboutAppFragment
import com.example.week8dotafragmentsappwb.fragments.DotaCharacterListFragment
import com.example.week8dotafragmentsappwb.fragments.contract.HasCustomTitle
import com.example.week8dotafragmentsappwb.model.CharacterItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val bundle = intent.extras

        if (bundle != null && savedInstanceState == null) {
            val characterItems: ArrayList<CharacterItem> =
                bundle.getParcelableArrayList(Utils.CHARACTERS_LIST_KEY)!!
            val fragmentCharacterList = DotaCharacterListFragment.newInstance(
                characterItems = characterItems
            )
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .add(R.id.fragmentContainer, fragmentCharacterList)
                .commit()

        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    private fun updateUi() {
        val fragment = currentFragment

        if (fragment is HasCustomTitle) {
            binding.toolbar.title = getString(fragment.getTitleRes())
        }
        binding.toolbar.menu.findItem(R.id.about).isVisible = fragment !is AboutAppFragment
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about) {
            val fragmentAbout = AboutAppFragment()
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragmentAbout)
                .commit()
        }
        return true
    }
}