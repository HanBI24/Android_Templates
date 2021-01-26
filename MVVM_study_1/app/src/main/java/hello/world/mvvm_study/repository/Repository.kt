package hello.world.mvvm_study.repository

import androidx.lifecycle.LiveData
import hello.world.mvvm_study.db.AppDatabase
import hello.world.mvvm_study.db.data.UserEntity

class Repository(mDatabase: AppDatabase) {
    private val userDao = mDatabase.userDao()
    // 변화 감지 시 메인 스레드에 알려줌
    val allUsers: LiveData<List<UserEntity>> = userDao.getAlphabetizedUsers()

    // Singleton
    companion object {
        private var sInstance: Repository? = null
        fun getInstance(database: AppDatabase): Repository {
            return sInstance ?: synchronized(this) {
                val instance = Repository(database)
                sInstance = instance
                instance
            }
        }
    }

    suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    suspend fun deleteAll() {
        userDao.deleteAll()
    }
}