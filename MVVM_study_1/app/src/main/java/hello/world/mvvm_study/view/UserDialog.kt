package hello.world.mvvm_study.view

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import hello.world.mvvm_study.R
import hello.world.mvvm_study.databinding.UserDialogBinding
import hello.world.mvvm_study.db.data.UserEntity
import hello.world.mvvm_study.viewmodel.MainViewModel
import java.util.*

class UserDialog(mContext: Context) : Dialog(mContext) {
    private val viewModel: MainViewModel = MainViewModel(mContext.applicationContext as Application)
    // View Binding
    private lateinit var binding: UserDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserDialogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 다이얼로그 배경 투명
        Objects.requireNonNull(window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.buttonSave.setOnClickListener {
            viewModel.insert(UserEntity(
                binding.userName.text.toString(),
                binding.userGender.text.toString(),
                binding.userBirth.text.toString()))
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

    }
}