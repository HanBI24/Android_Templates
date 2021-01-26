package hello.world.mvvm_study.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import hello.world.mvvm_study.db.data.UserDao
import hello.world.mvvm_study.db.data.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// exportSchema = true => Json 파일로 생성 가능
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        // 자기 자신을 받아들여서 Singleton 방식으로 구현
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // INSTANCE가 널이 아니면 INSTANCE(이미 생성되어있는 db 객체들)을 반환하고,
            // 널이면 db 생성 후 instance를 저장
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // addCallback()를 통해 초기 데이터를 넣어줌
                    .addCallback(AppDatabaseCallback(scope))
                    // 이전 데이터의 저장 없이, 새로운 버전으로 시작할 경우 다시 이전 데이터는 지우고 migration을 함
                    // Dao나 Entity의 데이터가 변경되었을 경우 version을 증가시켜서 migration을 진행해야 함
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        // db가 처음 생성되었을 때 할 행동
        // 초기 데이터를 넣어줌
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.userDao())
                }
            }
        }

        // db가 열릴 때마다 할 행동
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//        }

        suspend fun populateDatabase(userDao: UserDao) {
            // 초기 데이터에 들어갈 내용
            userDao.insert(UserEntity("Lilly", "여", "1993-07-25"))
        }
    }
}