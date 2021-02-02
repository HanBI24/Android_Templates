package hello.world.baemin_clone.view.fragment.a_home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import hello.world.baemin_clone.R
import hello.world.baemin_clone.data.BannerItem
import hello.world.baemin_clone.view.fragment.a_home.listener.Interaction

class ViewPagerAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        // banner item count
        const val ITEM_COUNT = 5
    }

    private var bannerItemList: List<BannerItem>? = null

    inner class BannerViewHolder constructor(itemView: View, private val interaction: Interaction) :
        RecyclerView.ViewHolder(itemView) {

        private val bannerView = itemView.findViewById<ImageView>(R.id.iv_banner_image)

        fun bind(bannerItem: BannerItem) {
            itemView.setOnClickListener {
                interaction.onBannerItemClicked(bannerItem)
            }
            bannerView.setImageResource(bannerItem.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_banner, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bannerItemList?.let { bannerItemList ->
            (holder as BannerViewHolder).bind(bannerItemList[position])
        }
    }

    override fun getItemCount() = ITEM_COUNT

    // viewModel에서 변경이 감지될 때마다(observe) submitList를 초기화 해줌
    fun submitList(list: List<BannerItem>?) {
        bannerItemList = list
        notifyDataSetChanged()
    }

}