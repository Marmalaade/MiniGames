package com.example.android.navigation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.R
import com.example.android.navigation.references.TableData
import java.text.SimpleDateFormat
import java.util.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.table_item, parent, false))

    override fun getItemCount(): Int = TableData.data.value?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TableData.data.value?.let {
            holder.setData(it[position])
        }
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val scoreText: TextView = view.findViewById(R.id.score_text)
        private val dateText: TextView = view.findViewById(R.id.date_text)

        fun setData(data: Pair<String, String>) {
            scoreText.text = view.context.getString(R.string.score_format, data.first)
            dateText.text = convertLongToTime(data.second.toLong())
        }

        private fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH)
            return format.format(date)
        }
    }
}