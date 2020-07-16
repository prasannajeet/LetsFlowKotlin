package com.prasan.letsflowkotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.prasan.letsflowkotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels()
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        mainViewModel.flowLiveData.observe(this, Observer {

            lifecycleScope.launch {
                it.collect {// If there is no collect function, the flow will not initiate
                    Log.d("TAG", "Collecting $it")
                }
            }

        })

        binding.button.setOnClickListener {
            mainViewModel.flowTutorialOne()
            mainViewModel.flowTutorialTwo()
            mainViewModel.flowTutorialThree()
            mainViewModel.flowTutorialZip()
        }
    }
}