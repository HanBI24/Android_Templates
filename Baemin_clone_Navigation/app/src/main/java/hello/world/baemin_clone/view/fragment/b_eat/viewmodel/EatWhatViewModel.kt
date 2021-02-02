package hello.world.baemin_clone.view.fragment.b_eat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hello.world.baemin_clone.data.WhatToEat
import hello.world.baemin_clone.view.fragment.b_eat.repository.EatWhatRepositoryImpl
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EatWhatViewModel(application: Application) : AndroidViewModel(application) {
    private val _eatWhatToEatList: MutableLiveData<List<WhatToEat>> = MutableLiveData()
    val eatWhatToEatList: LiveData<List<WhatToEat>>
        get() = _eatWhatToEatList

    fun eatFakeWhatToEatList() {
        // viewModelScope: 해당 viewModel이 destroy되면 자동으로 destroy됨
        viewModelScope.launch {
            // Retrofit 같은 네트워크 작업은 Dispatcher.IO 같은 Dispatcher를 이용해야 함
            withContext(IO) {
                _eatWhatToEatList.postValue(EatWhatRepositoryImpl.getWhatToEatItems())
            }
        }
    }
}