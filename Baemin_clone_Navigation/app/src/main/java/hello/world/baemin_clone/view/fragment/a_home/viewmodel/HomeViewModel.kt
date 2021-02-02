package hello.world.baemin_clone.view.fragment.a_home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hello.world.baemin_clone.data.BannerItem
import hello.world.baemin_clone.data.GridItem
import hello.world.baemin_clone.view.fragment.a_home.repository.HomeRepository
import hello.world.baemin_clone.view.fragment.a_home.repository.HomeRepositoryImpl
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _bannerItemList: MutableLiveData<List<BannerItem>> = MutableLiveData()
    private val _gridItemList: MutableLiveData<List<GridItem>> = MutableLiveData()
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()

    val bannerItemList: LiveData<List<BannerItem>>
        get() = _bannerItemList
    val gridItemList: LiveData<List<GridItem>>
        get() = _gridItemList
    val currentPosition: LiveData<Int>
        get() = _currentPosition

    init {
        _currentPosition.value = 0
    }

    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    fun getCurrentPosition() = currentPosition.value

    fun getBannerItems() {
        viewModelScope.launch {
            val bannerItemLiveData = HomeRepositoryImpl.getBannerItems()
            withContext(Main) {
                _bannerItemList.value = bannerItemLiveData
            }
        }
    }

    fun getGridItems() {
        viewModelScope.launch {
            val gridItemLiveData = HomeRepositoryImpl.getGridItems()
            withContext(Main) {
                _gridItemList.value = gridItemLiveData
            }
        }
    }
}