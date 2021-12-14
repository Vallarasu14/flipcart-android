package com.example.flipcart.adaptor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flipcart.model.PostModel
import com.example.flipcart.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.productitem.view.*


class Apiadaptor(private val context: Context, private val product: List<PostModel>) :
    RecyclerView.Adapter<Apiadaptor.ViewHolder>() {

    var onItemClickEvent: ((postModel:PostModel) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.title
        var price: TextView = itemView.price
        var productimage: ImageView = itemView.productimage

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.productitem, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int,) {
        holder.title.text = product[position].title
        holder.price.text =product[position].price.toString()
        Picasso.get().load(product[position].image).into(holder.productimage)

        var addToCart:Button = holder.itemView.findViewById(R.id.addtocart)
        addToCart.setOnClickListener {
            onItemClickEvent?.invoke(product[position])
        }

    }
    override fun getItemCount(): Int {
        return product.size
    }

}


