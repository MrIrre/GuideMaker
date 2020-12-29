package com.example.myguides

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myguides.common.*


class LoginActivity : AppCompatActivity() {
    private lateinit var buttonHelper: ButtonHelper
    private lateinit var loginInput: EditText
    private lateinit var passwordInput: EditText
    private val client: UnauthorizedClient =
        UnauthorizedClient("http://10.0.2.2:5000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        loginInput = findViewById(R.id.login_input)
        passwordInput = findViewById(R.id.password_input)
        val button: Button = findViewById(R.id.login_button)
        buttonHelper = ButtonHelper(button)
    }

    fun login(view: View) {
        val login = loginInput.text.toString()
        val password = passwordInput.text.toString()

        if (login == "" || password == "") {
            AlertWindow.show(this, "Please input login and password")
            return
        }

        val authData = AuthData(login, password)

        buttonHelper.block("Logging...")

        AsyncRunner.runAsync(
            { client.login(authData) },
            {
                if (it.isSuccessful()) {
                    TokenHelper.saveToken(it.value!!.token)

                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val error = it.error
                    AlertWindow.show(this, "Authorize failed. Reason: $error")
                }
                buttonHelper.unblock()
            }
        )
    }
}
