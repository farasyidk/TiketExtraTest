package rasyidk.fa.tiketextratest.helper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import rasyidk.fa.tiketextratest.LoginActivity
import rasyidk.fa.tiketextratest.MainActivity

class UserSession {
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor
    var context: Context
    private var PRIVATE_MODE = 0
    private val PREFER_NAME = "Reg"
    private val IS_USER_LOGIN = "IsUserLoggedIn"
    private val KEY_TOKEN = "token"

    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    fun createUserLoginSession(token: String) {
        editor.putBoolean(IS_USER_LOGIN, true)

        editor.putString(KEY_TOKEN, token)

        editor.commit()
    }

    fun checkLogin(): Boolean {
        if (!isUserLoggedIn()) {
            Log.d("session", "logged")
            val i = Intent(context, LoginActivity::class.java)

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(i)

            return true
        }
        Log.d("session", "no loggin")
        return false
    }

    fun logoutUser() {

        editor.clear()
        editor.commit()

        val i = Intent(context, MainActivity::class.java)

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        context.startActivity(i)
    }

    fun getUserDetails(): HashMap<String, String> {

        val user = HashMap<String, String>()

        user[KEY_TOKEN] = pref.getString(KEY_TOKEN, null)


        return user
    }

    private fun isUserLoggedIn(): Boolean {
        return pref.getBoolean(IS_USER_LOGIN, false)
    }
}