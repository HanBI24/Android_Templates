package hello.world.baemin_clone.view.fragment.a_home.listener

import android.view.View
import hello.world.baemin_clone.data.BannerItem

interface Interaction: View.OnClickListener {
    fun onBannerItemClicked(bannerItem: BannerItem)
}