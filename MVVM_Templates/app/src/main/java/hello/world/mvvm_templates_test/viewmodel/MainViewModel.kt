package hello.world.mvvm_templates_test.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import hello.world.mvvm_templates_test.database.AppDatabase
import hello.world.mvvm_templates_test.listener.MainViewModelListener
import hello.world.mvvm_templates_test.model.Memo

// view 데이터 지켜보는 ObservableFiled, ObservableArrayList
class MainViewModel(private val listener: MainViewModelListener) {
    // TextView
    val myText = ObservableField<String>()
    // RecyclerView
    val memoList = ObservableArrayList<Memo>()

    fun addMemo() {
        listener.addMemo(Memo(0, myText.get()?: "null"))
        myText.set("")
    }

    fun loadMemo(database: AppDatabase?, owner: LifecycleOwner) {
        database?.memoDao()?.getAllMemoAsync()?.observe(owner, Observer {
            memoList.clear()
            for(memo in it) {
                memoList.add(memo)
            }
        })
    }
}