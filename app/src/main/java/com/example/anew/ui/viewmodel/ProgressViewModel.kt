package com.example.anew.ui.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anew.logic.data.MarvelChars
import com.example.anew.logic.marvelLogic.MarvelLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.OffsetTime

class ProgressViewModel : ViewModel() {

    val progressState = MutableLiveData<Int> ()
    val items = MutableLiveData<MutableList<MarvelChars>>()

    fun processBackgroud(value: Long){
        viewModelScope.launch (Dispatchers.IO){
            val state1 = View.VISIBLE
            progressState.postValue(state1)
            delay(value)
            val state2 = View.GONE
            progressState.postValue(state2)
        }
    }

    fun sumInBackground(cant: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val state = View.VISIBLE
            progressState.postValue(state)

            var total = 0
            for (i in 1 .. cant){
                total += i
            }

            val state1 = View.GONE
            progressState.postValue(state1)

            Log.d("UCE", "${total}")
        }
    }
    suspend fun getMarvelChars(offset: Int, limit: Int){
        progressState.postValue(View.VISIBLE)
        val newItems = MarvelLogic()
            .getAllMarvelChars(offset, limit)
        items.postValue(newItems)
        progressState.postValue(View.GONE)
    }
}