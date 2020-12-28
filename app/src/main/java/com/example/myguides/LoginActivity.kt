package com.example.myguides

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    lateinit var loginInput: EditText
    lateinit var passwordInput: EditText
    val client: UnauthorizedClient = UnauthorizedClient("http://10.0.2.2:5000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginInput = findViewById(R.id.login_input)
        passwordInput = findViewById(R.id.password_input)
    }

    fun login(view: View) {
        val login = loginInput.text.toString()
        val password = passwordInput.text.toString()

        if (login == "" || password == "") {
            val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this)

            dlgAlert.setMessage("Please input login and password")
            dlgAlert.setTitle("Error Message...")
            dlgAlert.setPositiveButton("OK", null)
            dlgAlert.setCancelable(true)
            dlgAlert.create().show()

            dlgAlert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which -> })

            return
        }

        val authData = AuthData(login, password)
        val loginResult = client.login(authData)
        if (loginResult.isSuccessful())
        {
            TokenHelper.saveToken(loginResult.value!!.token)

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            val error = loginResult.error
            val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this)

            dlgAlert.setMessage(error)
            dlgAlert.setTitle("Error Message...")
            dlgAlert.setPositiveButton("OK", null)
            dlgAlert.setCancelable(true)
            dlgAlert.create().show()

            dlgAlert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which -> })

            return
        }
    }
}