package com.example.hotspot.other.util


import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

object ButtonAnimations {

    fun fadeIn(button: AppCompatButton){
        button.animate().apply {
            duration = 150
            this.alpha(1f)
        }.start()
    }

    fun fadeOut(button : AppCompatButton){
        button.animate().apply {
            duration = 110
            this.alpha(0.1f)
        }.start()
    }

    fun clickButton(button : AppCompatButton){
        button.animate().apply {
            duration = 75
            this.scaleXBy(-0.1f)
            this.scaleYBy(-0.1f)
            this.alpha(0.5f)
        }.withEndAction {
            button.animate().apply {
                duration = 75
                this.scaleXBy(0.1f)
                this.scaleYBy(0.1f)
                this.alpha(1f)
            }
        }.start()
    }

    fun clickText(tv : TextView){
        tv.animate().apply {
            duration = 55
            this.scaleXBy(-0.1f)
            this.scaleYBy(-0.1f)
            this.alpha(0.5f)
        }.withEndAction {
            tv.animate().apply {
                duration = 55
                this.scaleXBy(0.1f)
                this.scaleYBy(0.1f)
                this.alpha(1f)
            }
        }.start()
    }

    fun clickImageButton(button : ImageButton){
        button.animate().apply {
            duration = 75
            this.scaleXBy(-0.1f)
            this.scaleYBy(-0.1f)
            this.alpha(0.3f)
        }.withEndAction {
            button.animate().apply {
                duration = 75
                this.scaleXBy(0.1f)
                this.scaleYBy(0.1f)
                this.alpha(1f)
            }
        }.start()
    }
}