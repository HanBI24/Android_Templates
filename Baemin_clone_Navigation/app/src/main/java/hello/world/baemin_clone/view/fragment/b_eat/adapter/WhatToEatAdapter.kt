package hello.world.baemin_clone.view.fragment.b_eat.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import hello.world.baemin_clone.R
import hello.world.baemin_clone.data.WhatToEat

class WhatToEatAdapter :
    ListAdapter<WhatToEat, RecyclerView.ViewHolder>(WhatToEatDiffCallback()) {

    companion object {
        const val IMAGE_URL_TAG = "IMAGE_URL_TAG"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WhatToEatViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val whatToEatItem = getItem(position)
        (holder as WhatToEatViewHolder).bind(whatToEatItem)
    }

    class WhatToEatViewHolder
    private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        private val reView2 = itemView.findViewById<RecyclerView>(R.id.recyclerView2)
        private val imgView = itemView.findViewById<ImageView>(R.id.image)

        fun bind(item: WhatToEat) {
            tvTitle.text = item.title
            Log.d(IMAGE_URL_TAG, item.imageUrl)
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(imgView)
            reView2.apply {
                val flexBoxLayoutManager = FlexboxLayoutManager(itemView.context)
                flexBoxLayoutManager.flexDirection = FlexDirection.ROW
                flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START
                layoutManager = flexBoxLayoutManager
                adapter = FlexBoxListAdapter(item.tags.split(","))
            }
        }

        companion object {
            fun from(parent: ViewGroup): WhatToEatViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val layout = layoutInflater.inflate(R.layout.item_layout_what_to_eat, parent, false)
                return WhatToEatViewHolder(layout)
            }
        }
    }

    fun setList(list: List<WhatToEat>) {
        submitList(list)
    }
}