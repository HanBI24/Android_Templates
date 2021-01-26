package hello.world.mvvm_study.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import hello.world.mvvm_study.db.AppDatabase
import hello.world.mvvm_study.db.data.UserEntity
import hello.world.mvvm_study.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel 상속 가능하지만, ApplicationViewModel을 상속하면 application을 파라미터로 받을 수 있음
// Application: Global Class
class MainViewModel(application: Application) : AndroidViewModel(application) {
    var addText: ObservableField<String> = ObservableField("Add")
    var removeText: ObservableField<String> = ObservableField("Delete")

    private val mApplication = application
    // Repository와 연결
    val repository: Repository = Repository(AppDatabase.getDatabase(application, viewModelScope))
    // data와 view를 viewModel을 통해 연결 (감지된 변화에 맞게 UI를 업데이트 할 수 있도록)
    var allUsers: LiveData<List<UserEntity>> = repository.allUsers

    // Repository에 접근해 데이터를 추가
    fun insert(userEntity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(userEntity)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

//    fun onClickButton() = Toast.makeText(mApplication, "Click", Toast.LENGTH_SHORT).show()

}