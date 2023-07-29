package com.example.anew.ui.utilities

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient

class MyLocationManager {

    var context : Context? = null
    private lateinit var client: SettingsClient
    init{
        if(context!=null){
            client = LocationServices.getSettingsClient(context!!)
        }

    }






}