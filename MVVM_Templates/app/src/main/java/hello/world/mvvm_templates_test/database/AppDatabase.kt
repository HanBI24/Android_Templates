package hello.world.mvvm_templates_test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hello.world.mvvm_templates_test.dao.MemoDao
import hello.world.mvvm_templates_test.model.Memo

// 테이블 여러개 추가시
//@Database(entities = arrayOf(Memo::class, New1::class, New2::class, ...), version = 1, exportSchema = false)
@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun memoDao(): MemoDao

    // Singleton
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if(INSTANCE == null){
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "memo.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    fun destroyINSTANCE() {
        INSTANCE = null
    }
}