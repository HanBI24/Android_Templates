package hello.world.baemin_clone.view.fragment.b_eat.repository

import hello.world.baemin_clone.data.WhatToEat

interface EatWhatRepository {
    suspend fun getWhatToEatItems(): List<WhatToEat>
}