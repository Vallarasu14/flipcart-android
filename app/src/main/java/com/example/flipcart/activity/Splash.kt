package com.example.flipcart.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.flipcart.R
import com.example.flipcart.activity.MainActivity

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashicon : ImageView = findViewById(R.id.splashicon)
        splashicon.alpha = 0f
        splashicon.animate().setDuration(2000).alpha(1f).withEndAction{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
    }
}