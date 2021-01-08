package hello.world.mvvm_templates_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hello.world.mvvm_templates_test.databinding.MemoItemBinding
import hello.world.mvvm_templates_test.listener.ItemClickListener
import hello.world.mvvm_templates_test.listener.MainViewModelListener
import hello.world.mvvm_templates_test.model.Memo

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var items = ArrayList<Memo>()
    private var itemClickListener: ItemClickListener? = null
    private var memo: Memo? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val binding = MemoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])

        // RecyclerView 터치 이벤트 발생 시 터치한 position과 memo 리스너에 넘겨줌
        memo = items[position]
        holder.itemView.setOnClickListener(View.OnClickListener {
            itemClickListener?.OnItemClick(position, memo!!)
        })
    }

    // 아이템 터치 리스너 초기화
    fun setOnItemClickListener(listener: ItemClickListener) {
        this.itemClickListener = listener
    }

    class ViewHolder(private val binding: MemoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(memo: Memo) {
            binding.model = memo
        }
    }
}