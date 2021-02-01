package hello.world.baemin_clone.adapter

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hello.world.baemin_clone.R
import hello.world.baemin_clone.data.GridItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GridRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var gridItemList: List<GridItem>? = null

    class GridItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gridImage = itemView.findViewById<ImageView>(R.id.iv_grid_image)
        private val gridTitle = itemView.findViewById<TextView>(R.id.tv_grid_title)

        fun bind(gridItem: GridItem) {
            gridImage.setImageResource(gridItem.image)
            gridTitle.text = gridItem.title

            if(gridItem.image == R.drawable.baemin_dul) animateImageView(gridImage)
        }

        private fun animateImageView(aniView: View) {
            var cnt = 0
            ObjectAnimator.ofFloat(aniView, "translationY", 7f).apply {
                duration = 100
                repeatCount = 2
                addListener(object: Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        cnt++
                        // UI Object인 image에 애니메이션 delay를 줘야하기 때문에 Main thread로 설정
                        CoroutineScope(Main).launch {
                            if(cnt%2==0) delay(1000)
                            else delay(1000)
                            start()
                        }
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                })
                start()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GridItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_grid, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        gridItemList?.let {
            (holder as GridItemViewHolder).bind(it[position])
        }
    }

    override fun getItemCount() = gridItemList?.size ?: 0

    //functions
    fun submitList(list: List<GridItem>?) {
        gridItemList = list
        notifyDataSetChanged()
    }
}