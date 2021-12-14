package com.example.flipcart.activity


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flipcart.R
import com.example.flipcart.adaptor.Apiadaptor
import com.example.flipcart.api.RetrofitInterface
import com.example.flipcart.model.CartModel
import com.example.flipcart.model.PostModel
import com.example.flipcart.utils.DataBaseHandler
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_shopping.*
import kotlinx.android.synthetic.main.productitem.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Shopping_Activity : AppCompatActivity() {

    lateinit var Apiadaptor : Apiadaptor
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        initRecyclerView()

        val drawerlayout: DrawerLayout = findViewById(R.id.drawerlayout)
        val navView: NavigationView = findViewById(R.id.navView)

        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


       /* navView.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {

                // R.id.wishlist -> replaceFragment(My_Wishlist(),it.title.toString())
                R.id.mycart -> Toast.makeText(this, "My cart", Toast.LENGTH_SHORT).show()
                R.id.mychat -> Toast.makeText(this, "My Chart", Toast.LENGTH_SHORT).show()
            }
            true
        }*/

        recyclerview.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager

        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(RetrofitInterface::class.java)
        var call = API.posts

        call?.enqueue(object : Callback<List<PostModel?>?> {

            override fun onFailure(p0: Call<List<PostModel?>?>, p1: Throwable) {
                    println(p1)
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<List<PostModel?>?>,
                response: Response<List<PostModel?>?>
            ) {
                val responseBody = response.body()!!

                Apiadaptor = Apiadaptor(baseContext, responseBody as List<PostModel>)
                recyclerview.adapter = Apiadaptor

                Apiadaptor.onItemClickEvent = {
                    Toast.makeText(this@Shopping_Activity,"${it.title} Item added to cart",Toast.LENGTH_SHORT).show()
                    val dataBaseHandler = DataBaseHandler(applicationContext)
                    dataBaseHandler.addProductToCart(CartModel(it.id,it.title,it.price,it.image))
                }
                }
        })
    }

    fun initRecyclerView(){

        Apiadaptor = Apiadaptor(baseContext, arrayListOf())

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {

            return true
        }

        when (item.itemId) {
            R.id.logout -> {

                val builder = AlertDialog.Builder(this)

                builder.setTitle("Are You Sure")
                builder.setMessage("Do You Want To LogOut?")
                builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                })
                builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
                builder.show()

            }
            R.id.cart -> {
                startActivity(Intent(this, ShoppingCart::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }
}


