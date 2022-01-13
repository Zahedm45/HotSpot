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

    fun clickButton(button : AppCompatButton){
        button.animate().apply {
            duration = 55
            this.scaleXBy(-0.1f)
            this.scaleYBy(-0.1f)
            this.alpha(0.5f)
        }.withEndAction {
            button.animate().apply {
                duration = 55
                this.scaleXBy(0.1f)
                this.scaleYBy(0.1f)
                this.alpha(1f)
            }
        }.start()
    }
}