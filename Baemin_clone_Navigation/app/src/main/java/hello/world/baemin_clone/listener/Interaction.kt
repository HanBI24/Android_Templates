package hello.world.baemin_clone.listener

import android.view.View
import hello.world.baemin_clone.data.BannerItem

interface Interaction: View.OnClickListener {
    fun onBannerItemClicked(bannerItem: BannerItem)
}