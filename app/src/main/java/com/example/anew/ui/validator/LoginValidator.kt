package com.example.anew.ui.validator

import com.example.anew.data.entities.LoginUser

class LoginValidator() {

    fun checkLogin(name:String, password:String):Boolean{

        val admin = LoginUser()
        return (admin.name ==name && admin.pass == password)

    }
}
