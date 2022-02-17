package com.example.aboutme

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val ownerName = OwnerName("Alexandr Poltavetc")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.ownerName = ownerName
        binding.inputButton.setOnClickListener {
            addNickname(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addNickname(view: View) {
        if (binding.editTextPersonName.text.toString().isBlank()) {
            Toast.makeText(this, R.string.isEmptyNickname, Toast.LENGTH_SHORT).show()
        } else {
            binding.apply {
                ownerName?.nickname = getString(R.string.letsStart) + " ${editTextPersonName.text}!"
                invalidateAll()
                editTextPersonName.visibility = View.GONE
                view.visibility = View.GONE
                nicknameTextView.visibility = View.VISIBLE
            }

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}