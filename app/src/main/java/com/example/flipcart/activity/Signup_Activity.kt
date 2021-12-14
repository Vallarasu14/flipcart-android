package com.example.flipcart.login

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flipcart.R
import com.example.flipcart.model.Users
import com.example.flipcart.utils.DataBaseHandler


class signup_Activity : AppCompatActivity() {

    lateinit var etusername:EditText
    lateinit var semail:EditText
    lateinit var spass:EditText

    lateinit var dataBaseHandler: DataBaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initView()

        initListeners()

    }
    private fun initView(){
         etusername = findViewById(R.id.etusername)
         semail = findViewById(R.id.semail)
         spass = findViewById(R.id.spass)
    }

    private fun initListeners() {

        dataBaseHandler = DataBaseHandler(this);

        val btnsignup: Button = findViewById(R.id.btnsignup)
        btnsignup.setOnClickListener {
            postDataToSQLite()
        }
    }


    private fun postDataToSQLite() {
        val username: EditText = findViewById(R.id.etusername)
        val email: EditText = findViewById(R.id.semail)
        val password: EditText = findViewById(R.id.spass)

        if (username.text.toString().isEmpty()) {
            username.error = "Please Enter The Username"
            username.requestFocus()
            return
        }

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

        if (password.text.toString().isEmpty()) {

            password.error = "Please Enter The Password"
            password.requestFocus()
            return
        }

        if (!dataBaseHandler!!.checkUser(semail!!.text.toString().trim())) {
            var user = Users(
                username = etusername!!.text.toString().trim(),
                email = semail!!.text.toString().trim(),
                password = spass!!.text.toString().trim()
            )
            dataBaseHandler!!.adduser(user)
            // Snack Bar to show success message that record saved successfully
            Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
            emptyInputEditText()

        }else {
        // Snack Bar to show error message that record already exists
        Toast.makeText(this,"Already registered Please Login to Continue",Toast.LENGTH_SHORT).show()
    }
    }

        private fun emptyInputEditText() {
            val etusername: EditText = findViewById(R.id.etusername)
            val semail: EditText = findViewById(R.id.semail)
            val spass: EditText = findViewById(R.id.spass)
            etusername!!.text = null
            semail!!.text = null
            spass!!.text = null
        }



}