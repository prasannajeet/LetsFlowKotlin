package com.prasan.letsflowkotlin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    val flowLiveData : MutableLiveData<Flow<Int>> by lazy {
        MutableLiveData<Flow<Int>>()
    }

    fun flowTutorialOne() {

        flowLiveData.value = flow {
            //Log.d(TAG, "Start flow")
            (0..10).forEach {
                // Emit items with 500 milliseconds delay
                delay(500)
                Log.d("TAG", "Emitting on Tutorial One $it")
                emit(it)

            }
        }.map {// Change the value before emitting
            it*it
        }.flowOn(Dispatchers.Default)

    }

    fun flowTutorialTwo() {
        flowLiveData.value = flowOf(5, 4, 3, 2, 1).onEach {
            delay(500)
            Log.d("TAG", "Emitting on Tutorial Two $it")
        }.map { it*it }.flowOn(Dispatchers.Default)
    }

    fun flowTutorialThree() {
        flowLiveData.value = (1..5).asFlow().onEach {
            delay(500)
            Log.d("TAG", "Emitting on Tutorial Three $it")
        }.catch { e->
            Log.e("TAG", "Caught $e")
        }.map { it*it }.flowOn(Dispatchers.Default)
    }

    fun flowTutorialZip() {

        val flowOne = flowOf("One", "Two", "Three")
        val flowTwo = flowOf("Ten", "Nine", "Eight")

        viewModelScope.launch {

            flowOne.zip(flowTwo) { firstString, secondString ->
                delay(500)
                "$firstString $secondString"
            }.collect {
                Log.d("Tutorial Zip", it)
            }
        }
    }
}