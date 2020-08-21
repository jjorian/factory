package ir.bokaeyan.factory.supporthelper

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.JsonObject

object AppPreferences {
    private const val NAME = "SpinKotlin"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences


    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }
    var ID:Int
    get()= preferences.getInt("ID",0)
        set(value)= preferences.edit{it.putInt("ID",value)}

    var UserName:String?
        get()= preferences.getString("UserName","")
    set(value)= preferences.edit{it.putString("UserName",value)}

    var BaseUrl:String?
        get()= preferences.getString("BaseUrl","http://erp.mehremandegar.org/")
        set(value)= preferences.edit{it.putString("BaseUrl",value)}
}