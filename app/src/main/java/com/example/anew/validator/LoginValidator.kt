package com.example.anew.logic.validator

import com.example.anew.data.entities.LoginUser

class LoginValidator() {

    fun checklogin(name:String, password:String):Boolean{

        val admin = LoginUser()
        return (admin.name ==name && admin.pass == password)

    }
}