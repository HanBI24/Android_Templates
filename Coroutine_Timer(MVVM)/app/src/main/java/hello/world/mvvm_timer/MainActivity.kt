package hello.world.mvvm_timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TokenWatcher
import android.widget.Toast
import androidx.activity.viewModels
import hello.world.mvvm_timer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: CountViewModel by viewModels()
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainActivity
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}