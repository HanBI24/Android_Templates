package hello.world.mvvm_templates_test.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hello.world.mvvm_templates_test.R
import hello.world.mvvm_templates_test.database.AppDatabase
import hello.world.mvvm_templates_test.databinding.ActivityMainBinding
import hello.world.mvvm_templates_test.listener.ItemClickListener
import hello.world.mvvm_templates_test.listener.MainViewModelListener
import hello.world.mvvm_templates_test.model.Memo
import hello.world.mvvm_templates_test.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Memo의 변화는 ViewModel이 감지하고, ViewModel의 변화는 View가 감지할 수 있음.
// => BindingAdapter를 통해 정의
class MainActivity : AppCompatActivity() {
    // Data Binding
    private lateinit var binding: ActivityMainBinding
    var database: AppDatabase? = null
    private val listener = object : MainViewModelListener {
        override fun addMemo(memo: Memo) {
            // 비동기 처리를 위한 Coroutine의 IO Scope
            CoroutineScope(Dispatchers.IO).launch {
                database?.memoDao()?.insert(memo)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        database = AppDatabase.getInstance(applicationContext)
        val viewModel = MainViewModel(listener)
        binding.model = viewModel
        viewModel.loadMemo(database, this)

        val lm = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(binding.recyclerView.context, lm.orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

    }
}