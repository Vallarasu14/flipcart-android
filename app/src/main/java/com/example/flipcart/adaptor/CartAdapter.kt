package com.example.flipcart.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flipcart.R
import com.example.flipcart.model.CartModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.productitem.view.*

class CartAdapter(private val context: Context,private val cartItems:List<CartModel>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.carttitle)
        var price: TextView = itemView.findViewById(R.id.cartprice)
        var productimage: ImageView = itemView.productimage
        var qty:TextView = itemView.findViewById(R.id.qty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cartitem, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = cartItems[position].title
        holder.price.text =cartItems[position].price.toString()
        Picasso.get().load(cartItems[position].image).into(holder.productimage)
        holder.qty.text = cartItems[position].quantity.toString()
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}