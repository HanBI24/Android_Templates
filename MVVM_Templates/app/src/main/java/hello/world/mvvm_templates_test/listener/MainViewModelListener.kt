package hello.world.mvvm_templates_test.listener

import hello.world.mvvm_templates_test.model.Memo

interface MainViewModelListener {
    fun addMemo(memo: Memo)
}

interface ItemClickListener {
    fun OnItemClick(position: Int, memo: Memo)
}