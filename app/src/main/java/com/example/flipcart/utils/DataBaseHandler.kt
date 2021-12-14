package com.example.flipcart.utils

import android.annotation.SuppressLint
import android.app.DownloadManager.COLUMN_TITLE
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.flipcart.model.CartModel
import com.example.flipcart.model.Users


val DATABASE_NAME = "LOGINDB"

val USER_TABLE = "users"
val COL_USERNAME = "username"
val COL_EMAIL = "Email"
val COL_PASSWORD = "password"
val COL_ID = "id"

// shopping cart

val SHOPPING_CART_TABLE = "shoppingcart"
val COL_TITLE = "title"
val COL_QUANTITY = "quantity"
val COL_PRICE = "price"
val COLOUMN_ID = "id"

class DataBaseHandler(var appContext : Context) : SQLiteOpenHelper(appContext,
    DATABASE_NAME,null,1) {

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $USER_TABLE"
    private val DROP_SHOPPING_CART_TABLE = "DROP TABLE IF EXISTS $SHOPPING_CART_TABLE"

    override fun onCreate(db: SQLiteDatabase?) {

        val createUserTable = (" CREATE TABLE " + USER_TABLE + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USERNAME + " TEXT," +
                COL_EMAIL + " TEXT," +
                COL_PASSWORD + " TEXT)")

        val createCartTable = (" CREATE TABLE " + SHOPPING_CART_TABLE + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT," +
                COL_QUANTITY + " INTEGER," +
                COL_PRICE + " REAL)")

        db!!.execSQL(createUserTable);
        db.execSQL(createCartTable);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        //Drop User Table if exist
        db!!.execSQL(DROP_USER_TABLE)
        // Create tables again
        onCreate(db)

        db!!.execSQL(DROP_SHOPPING_CART_TABLE)
        onCreate(db)

    }

    fun adduser(users: Users) {

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_USERNAME, users.username)
        cv.put(COL_EMAIL, users.email)
        cv.put(COL_PASSWORD, users.password)
        var result = db.insert(USER_TABLE,null, cv)
        print("=====Result=====${result}")
        db.close()
    }

    fun updateUser(users: Users) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, users.username)
        values.put(COL_EMAIL, users.email)
        values.put(COL_PASSWORD, users.password)
        // updating row
        db.update(
            USER_TABLE, values, "$COL_ID = ?",
            arrayOf(users.id.toString())
        )
        db.close()
    }

    // this method is used check user exist or not
    //email
    //return true false

    fun checkUser(email: String): Boolean {

        //array elements to fetch
        val columns = arrayOf(COL_ID)
        val db = this.readableDatabase

        val selections = "$COL_EMAIL = ?"

        val selectionargs = arrayOf(email)

        //query user table with condition

        val cursor = db.query(USER_TABLE, columns, selections, selectionargs, null, null, null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }
        return false

    }

//this method is used to check user exist or not
    //email and password
    // return true false

    fun checkUser(email: String, password: String): Boolean {

        //array elements to fetch
        val columns = arrayOf(COL_ID)
        val db = this.readableDatabase

        val selections = "$COL_EMAIL = ? AND $COL_PASSWORD = ?"

        val selectionargs = arrayOf(email, password)

        //query user table with condition

        val cursor = db.query(USER_TABLE, columns, selections, selectionargs, null, null, null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }
        return false

    }//shopping cart

    //add to cart button click
    fun addProductToCart(cartModel: CartModel) {

        val db = this.writableDatabase
        val cartCV = ContentValues()
        cartCV.put(COLOUMN_ID,cartModel.id)
        cartCV.put( COL_TITLE , cartModel.title)
        cartCV.put(COL_QUANTITY, cartModel.quantity)
        cartCV.put(COL_PRICE,cartModel.price)
        var result = db.insert(SHOPPING_CART_TABLE,null, cartCV)
        print("=====Result=====${result}")
        db.close()

    }
    @SuppressLint("Recycle", "Range")
    fun getCartProducts(): List<CartModel> {
        //list of cart product

        val cartList: ArrayList<CartModel> = ArrayList<CartModel>()
        val selectQuery = "SELECT * FROM $SHOPPING_CART_TABLE"

        val db = this.readableDatabase
        var cursor: Cursor?= null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var title: String
        var quantity: Int
        var price: Double
        var image: String

        if (cursor.moveToFirst()) {
            do {
                id =  cursor.getInt(cursor.getColumnIndex("id"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                quantity = cursor.getInt(cursor.getColumnIndex("quantity"))
                price = cursor.getDouble(cursor.getColumnIndex("price"))
                image = cursor.getString(cursor.getColumnIndex("image"))
                val cartItem = CartModel(id,  title, price, "",  quantity)
                cartList.add(cartItem)
            } while (cursor.moveToNext())
        }
        return cartList
    }
/*

  */

}


/*

fun checkproduct(product: String): Boolean {

    val columns = arrayOf(COLOUMN_ID)
    val db = this.readableDatabase

    val selections = "$COLOUMN_ID= ?"
    val selectionargs = arrayOf(product)
    val cursor =
        db.query(SHOPPING_CART_TABLE, columns, selections, selectionargs, null, null, null)

    val cursorCount = cursor.count
    cursor.close()
    db.close()
    if (cursorCount > 0) {
        return true
    }
    return false
}

//delete a product

fun removeProducts() {
    val db = this.writableDatabase
    val id = "id"
    //deleting row
    */
/*val cartvalues = ContentValues()
    cartvalues.put(COLOUMN_ID,id)
    db.delete(SHOPPING_CART_TABLE, "COLOUMN_ID = $id",null);*//*

    db.delete(SHOPPING_CART_TABLE, null, null)
    db.close()
}

//shopping cart update

fun updateproduct(shoppingcart: CartModel) {

    val db = this.writableDatabase
    val values = ContentValues()
    val cartValues = ContentValues()
    var cursor: Cursor? = null
    cartValues.put(COL_PRODUCT, shoppingcart.title)
    cartValues.put(COL_QUANTITY, shoppingcart.quantity)
    cartValues.put(COL_PRICE, shoppingcart.price)
    // updating row
    if (cursor != null) {
        db.update(
            SHOPPING_CART_TABLE, cartValues, "$COLOUMN_ID = ?",
            arrayOf(cursor.getString(0))
        )
        cursor.requery()
        db.close()
    }

}
*/

