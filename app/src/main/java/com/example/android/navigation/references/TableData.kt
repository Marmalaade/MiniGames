package com.example.android.navigation.references

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData

object TableData {

    var data: MutableLiveData<MutableList<Pair<String, String>>> = MutableLiveData(emptyList<Pair<String, String>>().toMutableList())

    private val DATA_KEY = "DATA_KEY"

    private var prefs: SharedPreferences? = null

    private fun getPreferences(context: Context): SharedPreferences? {
        if (prefs == null)
            prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        return prefs
    }

    fun loadData(context: Context) {
        getPreferences(context)?.apply {
            val dataSet = getStringSet(DATA_KEY, emptySet())

            if (dataSet != null)
                data = MutableLiveData(dataSet.map {
                    val arr = it.split(" ")
                    Pair(arr[0], arr[1])
                }.sortedBy { it.second.toLong() }.toMutableList())
        }
    }

    fun saveData(context: Context) {
        getPreferences(context)?.edit()?.apply {
            putStringSet(DATA_KEY, data.value?.sortedBy { it.second.toLong() }?.map { "${it.first} ${it.second}" }?.toSet())
        }?.apply()
    }

}