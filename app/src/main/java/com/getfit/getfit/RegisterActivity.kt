package com.getfit.getfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextUsername = findViewById<EditText>(R.id.editText_username)
        val editTextPassword = findViewById<EditText>(R.id.editText_password)
        val textViewRLogin = findViewById<TextView>(R.id.textView_login)

        textViewRLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}