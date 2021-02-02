package hello.world.baemin_clone.view.fragment.b_eat.repository

import hello.world.baemin_clone.data.WhatToEat
import hello.world.baemin_clone.data.fakeWhatToEatList

object EatWhatRepositoryImpl : EatWhatRepository {
    override suspend fun getWhatToEatItems(): List<WhatToEat> {
        return fakeWhatToEatList
    }
}