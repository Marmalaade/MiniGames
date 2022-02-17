package com.example.diceroller

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rollButton.setOnClickListener {
            rollDice(diceImage)
            resultExplanation()
        }
    }

    private fun resultExplanation() {
        val explanationList = arrayOf(
            "Not bad",
            "Hm?",
            "Lucky number :D",
            "I hate it",
            "I like it",
            "Idk what can i say..."
        ).random()
        explanationField.text = explanationList
    }

    private fun rollDice(view: ImageView) {
        val result = when (Random.nextInt(6) + 1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage.setImageResource(result)
    }

}