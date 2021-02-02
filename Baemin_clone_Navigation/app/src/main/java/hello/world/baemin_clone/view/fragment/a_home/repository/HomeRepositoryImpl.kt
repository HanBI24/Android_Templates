package hello.world.baemin_clone.view.fragment.a_home.repository

import hello.world.baemin_clone.data.BannerItem
import hello.world.baemin_clone.data.GridItem
import hello.world.baemin_clone.data.fakeBannerItemList
import hello.world.baemin_clone.data.fakeGridItemList

// Singleton
object HomeRepositoryImpl : HomeRepository {
    override suspend fun getBannerItems(): List<BannerItem> {
        return fakeBannerItemList
    }

    override suspend fun getGridItems(): List<GridItem> {
        return fakeGridItemList
    }
}