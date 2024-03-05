package com.example.xml_firebasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        firestoreDb = FirebaseFirestore.getInstance()

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextAge = findViewById<EditText>(R.id.editTextAge)
        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        val saveBtn = findViewById<Button>(R.id.button)

        saveBtn.setOnClickListener {
            val name = editTextName.text.toString()
            val age = editTextAge.text.toString()
            val number = editTextNumber.text.toString()

            if (name.isEmpty() || age.isEmpty() || number.isEmpty()) {
                Toast.makeText(this, "Fill up the fields", Toast.LENGTH_SHORT).show()
            } else {

                val newUser = User(age, name, number)

                val userDocRef = firestoreDb.collection("users").document()
                userDocRef.set(newUser)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {e ->
                        Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }

            }
        }
    }
}