package com.example.myguides.common

import android.widget.Button
import android.widget.ImageButton

class ButtonHelper(private val button: Button) {
    private lateinit var currentText: String

    fun block(text: String) {
        currentText = button.text.toString()
        button.isEnabled = false
        button.text = text
    }

    fun unblock() {
        button.isEnabled = true
        button.text = currentText
    }
}

class ImageButtonHelper(private val imageButton: ImageButton) {
    fun block() {
        imageButton.isEnabled = false
    }

    fun unblock() {
        imageButton.isEnabled = true
    }
}
