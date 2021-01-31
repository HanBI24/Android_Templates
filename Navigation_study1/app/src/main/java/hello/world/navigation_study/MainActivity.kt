package hello.world.navigation_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.viewpager.widget.PagerAdapter
import hello.world.navigation_study.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // navigation들을 담는 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment

        // navigation controller
        val navController = navHostFragment.navController

        // bottom navigation view와 navigation을 묶어줌
        // 일반 fragment만 사용하고 싶으면 controller 연결할 필요 없음
        NavigationUI.setupWithNavController(mBinding.myBottomNav, navController)
    }
}