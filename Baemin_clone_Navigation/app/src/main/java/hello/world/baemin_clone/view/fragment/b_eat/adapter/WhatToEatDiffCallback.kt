package hello.world.baemin_clone.view.fragment.b_eat.adapter

import androidx.recyclerview.widget.DiffUtil
import hello.world.baemin_clone.data.WhatToEat

class WhatToEatDiffCallback : DiffUtil.ItemCallback<WhatToEat>() {
    override fun areItemsTheSame(oldItem: WhatToEat, newItem: WhatToEat): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: WhatToEat, newItem: WhatToEat): Boolean {
        return oldItem == newItem
    }
}