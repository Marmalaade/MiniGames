package com.example.yummyclicker

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import com.example.yummyclicker.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.prefs.Preferences
import kotlin.properties.Delegates

private const val KEY_REVENUE = "revenue_key"
private const val KEY_SOLD = "sold_key"
private const val KEY_IN_GAME_TIME = "time_key"
private const val KEY_SHARED = "shared_pref"

class MainActivity : AppCompatActivity(), LifecycleObserver {
    private lateinit var binding: ActivityMainBinding
    private var revenue = 0
    private var dessertsSold = 0
    private var defaultName = "Party number:"
    private lateinit var inGameTimer: InGamTimer

    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    private val allDesserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 125),
        Dessert(R.drawable.icecreamsandwich, 500, 150),
        Dessert(R.drawable.jellybean, 1000, 175),
        Dessert(R.drawable.kitkat, 2000, 300),
        Dessert(R.drawable.lollipop, 3000, 325),
        Dessert(R.drawable.marshmallow, 4000, 350),
        Dessert(R.drawable.nougat, 5000, 400),
        Dessert(R.drawable.oreo, 6000, 600)
    )
    private var currentDessert = allDesserts[0]
    override fun onCreate(savedInstanceState: Bundle?) {
        this.window
            .setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inGameTimer = InGamTimer(this.lifecycle)
        if (savedInstanceState != null) {
            revenue = savedInstanceState.getInt(KEY_REVENUE, 0)
            dessertsSold = savedInstanceState.getInt(KEY_SOLD, 0)
            inGameTimer.secondCount = savedInstanceState.getInt(KEY_IN_GAME_TIME)
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.amountSold = dessertsSold
        binding.revenue = revenue
        binding.dessertButton.setOnClickListener {
            onYummyClicked()
        }
        loadData()
    }

    private fun onYummyClicked() {
        revenue += currentDessert.price
        dessertsSold++
        binding.revenue = revenue
        binding.amountSold = dessertsSold
        showCurrentYummy()
    }

    @SuppressLint("SetTextI18n")
    private fun showCurrentYummy() {
        var newYummy = allDesserts[0]
        for (ymmyes in allDesserts) {
            if (dessertsSold >= ymmyes.startProductionAmount) {
                newYummy = ymmyes
            } else break
        }
        if (newYummy != currentDessert) {
            currentDessert = newYummy
            binding.dessertButton.setImageResource(newYummy.imageId)
        }
        dessert_party_text.text = "$defaultName ${currentDessert.imageId.toString().substring(8).toString()}"
    }

    @SuppressLint("LogNotTimber")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_SOLD, dessertsSold)
        outState.putInt(KEY_IN_GAME_TIME, inGameTimer.secondCount)
        Timber.i("onSaveInstanceState called")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(applicationContext, "You are playing ${inGameTimer.secondCount} seconds now!", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveData()
    }

    private fun saveData() {
        val insertedRevenue = revenue_text.text.toString()
        val sharedPreferences = getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(KEY_REVENUE, insertedRevenue)
        }.apply()
        Timber.i("data saved $insertedRevenue")
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE)
        val savedRevenue = sharedPreferences.getString(KEY_REVENUE, null)
        revenue_text.text = savedRevenue
        Timber.i("data loaded ${revenue_text.text}")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text, dessertsSold, revenue))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}