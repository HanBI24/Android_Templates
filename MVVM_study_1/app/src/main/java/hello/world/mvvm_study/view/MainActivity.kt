package hello.world.mvvm_study.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hello.world.mvvm_study.R
import hello.world.mvvm_study.adapter.UserListAdapter
import hello.world.mvvm_study.databinding.ActivityMainBinding
import hello.world.mvvm_study.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)        // binding을 통해 layout을 설정하기 때문에 필요없음

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(      // ActivityMainBinding: activity_main.xml의 이름이 자동으로 바뀜
            this,
            R.layout.activity_main
        )
        binding.viewModel = viewModel       // viewModel binding

        // adapter 연결
        val mAdapter = UserListAdapter(this)
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        // viewModel의 LiveData를 관찰하여 UI 업데이트
        viewModel.allUsers.observe(this, Observer { users ->
            users?.let {
                mAdapter.setUsers(it)
            }
        })

        binding.btnAdd.setOnClickListener {
            val dlg = UserDialog(this)
            dlg.show()
        }

        binding.btnRemove.setOnClickListener {
            viewModel.deleteAll()
            mAdapter.notifyDataSetChanged()
        }
    }
}