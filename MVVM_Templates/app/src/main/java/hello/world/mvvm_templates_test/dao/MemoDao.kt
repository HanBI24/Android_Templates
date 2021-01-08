package hello.world.mvvm_templates_test.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import hello.world.mvvm_templates_test.model.Memo

@Dao
interface MemoDao : BaseDao<Memo> {
    @Query("SELECT * FROM memo WHERE text = :text")
    fun getMemo(text: String): Memo?

    // 동기
    @Query("SELECT * FROM memo")
    fun getAllMemoSync(): List<Memo>

    // 비동기 (LiveData)
    @Query("SELECT * FROM memo")
    fun getAllMemoAsync(): LiveData<List<Memo>>
}