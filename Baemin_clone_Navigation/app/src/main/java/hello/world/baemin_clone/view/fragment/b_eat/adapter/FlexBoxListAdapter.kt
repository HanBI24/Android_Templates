package hello.world.baemin_clone.view.fragment.b_eat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hello.world.baemin_clone.R

class FlexBoxListAdapter(val list: List<String>) :
    RecyclerView.Adapter<FlexBoxListAdapter.TagsViewHolder>() {

    class TagsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTag = itemView.findViewById<TextView>(R.id.tv_tag)

        fun bind(tag: String) {
            tvTag.text = "${tag.trim()} >"
        }

        companion object {
            fun from(parent: ViewGroup): TagsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val layout = layoutInflater.inflate(R.layout.item_layout_tags, parent, false)
                return TagsViewHolder(layout)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlexBoxListAdapter.TagsViewHolder {
        return TagsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FlexBoxListAdapter.TagsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}