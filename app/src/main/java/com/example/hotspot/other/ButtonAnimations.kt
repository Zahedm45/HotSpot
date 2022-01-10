package com.example.hotspot.other


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
}