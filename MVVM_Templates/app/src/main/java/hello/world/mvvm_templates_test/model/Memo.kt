package hello.world.mvvm_templates_test.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// If you use data class, class make DTO to automatically.
@Entity(tableName = "memo")
data class Memo(@PrimaryKey(autoGenerate = true) var id: Long, var text: String)