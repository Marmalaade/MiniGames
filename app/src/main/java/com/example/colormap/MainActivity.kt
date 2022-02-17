package com.example.colormap

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlin.math.log10
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setColorListener()
    }

    private fun setColorListener() {
        val clickableList: List<View> = listOf(
            first_circle,
            second_circle,
            third_circle,
            fourth_circle,
            fifth_circle,
            sixth_circle,
            center_circle,
            constraint
        )
        for (el in clickableList) {
            el.setOnClickListener { makeColored(it) }
        }
        val listOfCircles = clickableList.toMutableList()

        with(listOfCircles) {
            remove(constraint)
            remove(center_circle)
        }

        center_circle.setOnClickListener {
            clickAnimation(listOfCircles)
        }
    }

    private fun clickAnimation(view: List<View>) {
        for (el in view) {
            el.startAnimation(AnimationUtils.loadAnimation(this, R.anim.circles_animation))
        }
    }

    private fun getBackGroundRandomColor(): Int =
        Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256));

    private fun getCircleRandomColor(): Int = resources.getIntArray(R.array.circeColors).random()

    private fun makeColored(view: View?) {

        when (view?.id) {
            R.id.center_circle -> view.background.setTint(getBackGroundRandomColor())
            R.id.first_circle -> view.background.setTint(getBackGroundRandomColor())
            R.id.second_circle -> view.background.setTint(getBackGroundRandomColor())
            R.id.third_circle -> view.background.setTint(getCircleRandomColor())
            R.id.fourth_circle -> view.background.setTint(getCircleRandomColor())
            R.id.fifth_circle -> view.background.setTint(getCircleRandomColor())
            R.id.sixth_circle -> view.background.setTint(getCircleRandomColor())
            else -> view?.setBackgroundColor(getBackGroundRandomColor())
        }
    }
}

