package hello.world.mvvm_templates_test.utils

import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hello.world.mvvm_templates_test.adapter.RecyclerAdapter
import hello.world.mvvm_templates_test.listener.ItemClickListener
import hello.world.mvvm_templates_test.model.Memo

// data binding을 하기 위한 필요 요소 object 클래스
// Memo의 변화는 ViewModel이 감지하고, ViewModel의 변화는 View가 감지할 수 있음.
// => BindingAdapter를 통해 정의
object DataBindingUtils {
    @BindingAdapter("bind_memolist")
    // JvmStatic: Kotlin에서 get/set을 자동으로 정의 (Companion에 등록된 변수를 static처럼 선언하기 위한 annotation
    // DatabindingUtils.getABC() = DatabindingUtils.Companion.getABC()
    @JvmStatic
    fun bindMemoList(recyclerView: RecyclerView, items: ObservableArrayList<Memo>) {
        if(recyclerView.adapter == null) {
            val lm = LinearLayoutManager(recyclerView.context)
            val adapter = RecyclerAdapter()
            recyclerView.layoutManager = lm
            recyclerView.adapter = adapter
        }
        // as: 캐스팅 실패 시 Exception 발생
        (recyclerView.adapter as RecyclerAdapter).items = items
        recyclerView.adapter?.notifyDataSetChanged()
        // RecyclerView 터치 이벤트 발생 시 Toast 출력
        (recyclerView.adapter as RecyclerAdapter).setOnItemClickListener(listener = object :
            ItemClickListener {
            override fun OnItemClick(position: Int, memo: Memo) {
                Toast.makeText(recyclerView.context, "$position 번 선택됨", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @BindingAdapter("bind_text")
    @JvmStatic
    fun bindText(textView: TextView, id: Long) {
        textView.text = id.toString()
    }
}