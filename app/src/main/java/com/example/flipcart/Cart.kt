/*
package com.example.flipcart

import android.content.Context
import com.example.flipcart.model.CartModel
import io.paperdb.Paper

class Cart {

    companion object {
        fun addItem(cartModel: CartModel) {
            val cart = Cart.getCart()

            val targetItem = cart.singleOrNull { it.postModel.id == cartModel.postModel.id }
            if (targetItem == null) {
                cartModel.quantity++
                cart.add(cartModel)
            } else {
                targetItem.quantity++
            }
            Cart.saveCart(cart)
        }

        fun removeItem(cartModel: CartModel, context: Context) {
            val cart = Cart.getCart()

            val targetItem = cart.singleOrNull { it.postModel.id == cartModel.postModel.id }
            if (targetItem != null) {
                if (targetItem.quantity > 0) {
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }
            }

            Cart.saveCart(cart)
        }

        fun saveCart(cartModel: MutableList<CartModel>) {
            Paper.book().write("cart", cartModel)
        }

        fun getCart(): MutableList<CartModel> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            Cart.getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }

}
*/
