package hello.world.baemin_clone.view.fragment.a_home.repository

import hello.world.baemin_clone.data.BannerItem
import hello.world.baemin_clone.data.GridItem

interface HomeRepository {
    suspend fun getBannerItems(): List<BannerItem>
    suspend fun getGridItems(): List<GridItem>
}