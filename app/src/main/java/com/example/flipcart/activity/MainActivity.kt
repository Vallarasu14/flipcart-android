package com.example.flipcart.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flipcart.R
import com.example.flipcart.login.signup_Activity
import com.example.flipcart.utils.DataBaseHandler

class MainActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var pass: EditText
    lateinit var dataBaseHandler: DataBaseHandler
    lateinit var btnlogin: Button
    lateinit var signup: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        initListeners()

    }

    private fun initView() {
        dataBaseHandler = DataBaseHandler(this);

        signup = findViewById(R.id.signup)
        email = findViewById(R.id.email)
        pass = findViewById(R.id.pass)
        btnlogin = findViewById(R.id.btnlogin)

    }

    private fun initListeners() {
        btnlogin.setOnClickListener {
            //verifyFromSQlite()
            val intent = Intent(this, Shopping_Activity::class.java)
            startActivity(intent)
        }

        signup.setOnClickListener {
            startActivity(Intent(this, signup_Activity::class.java))
        }
    }

    private fun verifyFromSQlite() {


        if (email.text.toString().isEmpty()) {

            email.error = "Please Enter Your email Address"
            email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {

            email.error = "Please Enter A Valid Email"
            email.requestFocus()
            return

        }

        if (pass.text.toString().isEmpty()) {

            pass.error = "Please Enter The Password"
            pass.requestFocus()
            return
        }
        if (dataBaseHandler!!.checkUser(
                email!!.text.toString().trim { it <= ' ' },
                pass!!.text.toString().trim { it <= ' ' })
        ) {
            val intent = Intent(this, Shopping_Activity::class.java)
            intent.putExtra("EMAIL", email!!.text.toString().trim { it <= ' ' })
            emptyInputEditText()
            startActivity(intent)
            Toast.makeText(this,"successfully Loggedin",Toast.LENGTH_SHORT).show()
        }else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
        }

    }
    private fun emptyInputEditText() {
        email!!.text = null
        pass!!.text = null
    }
}