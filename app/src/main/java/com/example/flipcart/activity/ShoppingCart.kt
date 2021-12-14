package com.example.flipcart.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flipcart.R
import com.example.flipcart.adaptor.CartAdapter
import com.example.flipcart.utils.DataBaseHandler
import kotlinx.android.synthetic.main.activity_cart.*


class ShoppingCart: AppCompatActivity() {

    lateinit var CartAdapter : CartAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclercart.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclercart.layoutManager = linearLayoutManager

        val dataBaseHandler = DataBaseHandler(applicationContext)
        val cartList = dataBaseHandler.getCartProducts()
        CartAdapter = CartAdapter(baseContext,cartList )
        recyclercart.adapter = CartAdapter
    }
}

