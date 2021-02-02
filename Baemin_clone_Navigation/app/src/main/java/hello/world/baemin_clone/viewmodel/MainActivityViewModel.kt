package hello.world.baemin_clone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hello.world.baemin_clone.data.BannerItem
import hello.world.baemin_clone.data.GridItem

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    // fragment로 변경
//    private val _bannerItemList: MutableLiveData<List<BannerItem>> = MutableLiveData()
//    private val _gridItemList: MutableLiveData<List<GridItem>> = MutableLiveData()
//    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()
//
//    val bannerItemList: LiveData<List<BannerItem>>
//        get() = _bannerItemList
//
//    val gridItemList: LiveData<List<GridItem>>
//        get() = _gridItemList
//
//    val currentPosition: LiveData<Int>
//        get() = _currentPosition
//
//    init {
//        _currentPosition.value = 0
//    }
//
//    fun setBannerItems(list: List<BannerItem>) {
//        _bannerItemList.value = list
//    }
//
//    fun setCurrentPosition(position: Int) {
//        _currentPosition.value = position
//    }
//
//    fun setGridItems(list: List<GridItem>) {
//        _gridItemList.value = list
//    }
//
//    fun getCurrentPosition() = currentPosition.value
}