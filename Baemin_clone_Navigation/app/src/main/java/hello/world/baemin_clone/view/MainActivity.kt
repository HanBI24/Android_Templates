package hello.world.baemin_clone.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import hello.world.baemin_clone.R
import hello.world.baemin_clone.view.fragment.a_home.adapter.GridRecyclerViewAdapter
import hello.world.baemin_clone.view.fragment.a_home.adapter.ViewPagerAdapter
import hello.world.baemin_clone.data.BannerItem
import hello.world.baemin_clone.data.fakeBannerItemList
import hello.world.baemin_clone.data.fakeGridItemList
import hello.world.baemin_clone.databinding.ActivityMainBinding
import hello.world.baemin_clone.databinding.LayoutSlideMenuBinding
import hello.world.baemin_clone.view.fragment.a_home.listener.Interaction
import hello.world.baemin_clone.view.ui.collapse
import hello.world.baemin_clone.view.ui.expand
import hello.world.baemin_clone.viewmodel.MainActivityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding ?= null
    // 매번 null 체크 안해도 되도록 선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null) setUpBottomNavigationBar()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }

    private fun setUpBottomNavigationBar() {
        // nav_host_fragment_container에 name 설정 필수 (안하면 NPE)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}