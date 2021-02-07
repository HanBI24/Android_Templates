package hello.world.mvvm_timer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class CountViewModel(application: Application) : AndroidViewModel(application) {
    val countText = MutableLiveData("10")
    private lateinit var job: Job
    private var cnt = 10
    private var clickCount = 0

    companion object {
        private const val TAG = "ViewModel"
    }

    private fun initJob() {
        job = Job()
    }

    fun decreaseCountText() {
        initJob()
        clickCount++
        val coroutineName1 = job.toString().split("{")
        val coroutineName2 = coroutineName1[1].substring(0, 6)
        if (coroutineName2 == "Active" && clickCount>1) {
            cancelJob()
        }
        else {
            viewModelScope.launch(job) {
                for (i in cnt downTo 1) {
                    countText.value = cnt.toString()
                    decreaseCount()
                }
            }
        }
    }

    private suspend fun decreaseCount() {
        cnt--
        delay(1000L)
    }

    private fun cancelJob() {
        if(job.isActive || job.isCompleted) job.cancel()
    }

    fun pauseCount() {
        clickCount = 0
        job.cancel()
        viewModelScope.cancel()
        initJob()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        cnt = 10
    }
}