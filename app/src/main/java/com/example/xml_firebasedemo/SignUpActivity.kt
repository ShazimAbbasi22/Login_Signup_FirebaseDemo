package com.example.xml_firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()

        val signUpBtn = findViewById<Button>(R.id.signup_button)
        val usernameInput = findViewById<TextInputEditText>(R.id.input_username)
        val emailInput = findViewById<TextInputEditText>(R.id.input_email)
        val passwordInput = findViewById<TextInputEditText>(R.id.input_password)
        val confirmPasswordInput = findViewById<TextInputEditText>(R.id.input_confirmPassword)
        val signInIntent = findViewById<TextView>(R.id.signin_intent)
        val checkbox = findViewById<CheckBox>(R.id.checkBox)
        val mobileNumberInput = findViewById<TextInputEditText>(R.id.input_mobNumber)

        signUpBtn.setOnClickListener {
            val username = usernameInput.text?.trim().toString()
            val email = emailInput.text?.trim().toString()
            val password = passwordInput.text?.trim().toString()
            val confirmPassword = confirmPasswordInput.text?.trim().toString()
            val mobNumber = mobileNumberInput.text?.trim().toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || !checkbox.isChecked) {
                Toast.makeText(this, "Fill the required fields", Toast.LENGTH_SHORT).show()
            } else if (confirmPassword != password) {
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
            } else if (!email.endsWith(".com") || !email.contains("@")) {
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show()
            } else {

                CoroutineScope(Dispatchers.IO).launch {

                    try {

                        withContext(Dispatchers.Main) {
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "SignUp Successful",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    val intent =
                                        Intent(this@SignUpActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        applicationContext,
                                        "SignUp Unsuccessful ${e.message}",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                        }

                    } catch (e: Exception) {
                        Toast.makeText(
                            applicationContext,
                            "Error Occurred ${e.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }

            }

        }

        signInIntent.setOnClickListener {
            val intentToSignInScreen = Intent(this, LoginActivity::class.java)
            startActivity(intentToSignInScreen)
            finish()
        }
    }
}