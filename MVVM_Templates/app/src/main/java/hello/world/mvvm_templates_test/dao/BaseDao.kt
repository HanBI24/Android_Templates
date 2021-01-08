package hello.world.mvvm_templates_test.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert
    fun insert(obj: T)

    @Delete
    fun delete(obj: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(obj: T)
}