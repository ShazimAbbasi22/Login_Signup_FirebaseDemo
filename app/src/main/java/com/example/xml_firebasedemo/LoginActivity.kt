package com.example.xml_firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        val loginBtn = findViewById<Button>(R.id.login_button)
        val emailInput = findViewById<TextInputEditText>(R.id.input_email)
        val passwordInput = findViewById<TextInputEditText>(R.id.input_password)
        val signUpIntent = findViewById<TextView>(R.id.signup_intent)

        loginBtn.setOnClickListener {
            val email = emailInput.text?.trim().toString()
            val password = passwordInput.text?.trim().toString()

//            if (email.isEmpty() || password.isEmpty()) {
//                emailInput.error = "Please enter email"
//                passwordInput.error = "Please enter password"
//                return@setOnClickListener
//            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                emailInput.error = "Enter the valid email"
//            } else {
//                CoroutineScope(Dispatchers.IO).launch {
//
//                    try {
//
//                        withContext(Dispatchers.Main) {
//                            firebaseAuth.signInWithEmailAndPassword(email, password)
//                                .addOnCompleteListener { task ->
//                                    if (task.isSuccessful) {
//                                        Toast.makeText(
//                                            applicationContext,
//                                            "SignIn Successful",
//                                            Toast.LENGTH_SHORT
//                                        )
//                                            .show()
//                                        val intent =
//                                            Intent(this@LoginActivity, HomeActivity::class.java)
//                                        startActivity(intent)
//                                        finish()
//                                    } else {
//                                        Toast.makeText(
//                                            applicationContext,
//                                            "SignIn Unsuccessful",
//                                            Toast.LENGTH_SHORT
//                                        )
//                                            .show()
//                                    }
//                                }
//                        }
//
//                    } catch (e: Exception) {
//                        Toast.makeText(
//                            applicationContext,
//                            "Error Occurred ${e.message}",
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
//                    }
//
//                }
//            }

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill the required fields", Toast.LENGTH_SHORT).show()
            } else if (!email.endsWith(".com") || !email.contains("@")) {
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show()
            } else {

                CoroutineScope(Dispatchers.IO).launch {

                    try {

                        withContext(Dispatchers.Main) {
                            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(applicationContext, "SignIn Successful", Toast.LENGTH_SHORT)
                                        .show()
                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(applicationContext, "SignIn Unsuccessful", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }

                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, "Error Occurred ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

            }

        }

        signUpIntent.setOnClickListener {
            val intentToSignUpScreen = Intent(this, SignUpActivity::class.java)
            startActivity(intentToSignUpScreen)
        }
    }
}