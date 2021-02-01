package hello.world.baemin_clone.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hello.world.baemin_clone.databinding.ActivityEventBinding

class EventActivity : AppCompatActivity() {
    private var binding: ActivityEventBinding ?= null
    private val mBinding get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.ivClose.setOnClickListener { finish() }
    }
}