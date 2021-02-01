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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import hello.world.baemin_clone.R
import hello.world.baemin_clone.adapter.GridRecyclerViewAdapter
import hello.world.baemin_clone.adapter.ViewPagerAdapter
import hello.world.baemin_clone.data.BannerItem
import hello.world.baemin_clone.data.fakeBannerItemList
import hello.world.baemin_clone.data.fakeGridItemList
import hello.world.baemin_clone.databinding.ActivityMainBinding
import hello.world.baemin_clone.databinding.LayoutSlideMenuBinding
import hello.world.baemin_clone.listener.Interaction
import hello.world.baemin_clone.view.ui.collapse
import hello.world.baemin_clone.view.ui.expand
import hello.world.baemin_clone.viewmodel.MainActivityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Interaction {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var gridRecyclerViewAdapter: GridRecyclerViewAdapter
    private val viewModel: MainActivityViewModel by viewModels()
    private var mBinding: ActivityMainBinding ?= null
    // 매번 null 체크 안해도 되도록 선언
    private val binding get() = mBinding!!
    // 코루틴을 생명주기에 적절하게 중지하기 위한 체크 변수
    private var isRunning = true
    private var delayTime = 3_000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // FakeItems로 따로 분리하여 처리 (코드 간결)
//        viewModel.setBannerItems(
//            listOf(
//                BannerItem(R.drawable.first),
//                BannerItem(R.drawable.second),
//                BannerItem(R.drawable.third),
//                BannerItem(R.drawable.fourth),
//                BannerItem(R.drawable.fifth)
//            )
//        )

        viewModel.setBannerItems(fakeBannerItemList)
        viewModel.setGridItems(fakeGridItemList)

        // listener 설정
        binding.ivHamburger.setOnClickListener(this)
        binding.tvSeeDetail.setOnClickListener(this)
        binding.ivArrow.setOnClickListener(this)
        // Get id in include tag
        // include된 layout은 해당 include에 반드시 id가 포함되어야 하며,
        // 그 id가 자동으로 binding된다.
        binding.slideMenu.llLeftArea.setOnClickListener(this)

        initViewPager2()
        subscribeObservers()
        // MainActivity가 destroy되지 않는 이상 코루틴은 계속 존재하기 때문에 onCreate()에서 한 번만 호출
        autoScrollViewPager()
    }

    private fun initViewPager2() {
        binding.viewPager2.apply {
            viewPagerAdapter = ViewPagerAdapter(this@MainActivity)
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    isRunning = true
                    binding.tvPageNumber.text = (position+1).toString()
                    // 유저가 직접 스크롤 했을 때 viewModel에 알려줘야 함
                    viewModel.setCurrentPosition(position)
                }
            })
        }
        binding.gridRecyclerView.apply {
            gridRecyclerViewAdapter = GridRecyclerViewAdapter()
            layoutManager = GridLayoutManager(this@MainActivity, 4)
            adapter = gridRecyclerViewAdapter
        }
    }

    // viewModel이랑 연동시킴으로써 변경이 감지될 때마다(observe) 각 adapter에 등록하거나 값을 수정
    private fun subscribeObservers() {
        viewModel.bannerItemList.observe(this, { bannerItemList ->
            viewPagerAdapter.submitList(bannerItemList)
        })

        viewModel.gridItemList.observe(this, { gridItemList ->
            gridRecyclerViewAdapter.submitList(gridItemList)
        })

        viewModel.currentPosition.observe(this, { currentPosition ->
            binding.viewPager2.currentItem = currentPosition
        })
    }

    private fun autoScrollViewPager() {
        lifecycleScope.launch {
            whenResumed {
                while(isRunning) {      // true일 때만 코루틴 실행
                    delay(delayTime)
                    viewModel.getCurrentPosition()?.let {
                        viewModel.setCurrentPosition((it.plus(1)) % 5)  // 나눈 나머지로 0, 1, 2, 3, ...
                    }
                }
            }
        }
    }

    override fun onBannerItemClicked(bannerItem: BannerItem) {
        startActivity(Intent(this@MainActivity, EventActivity::class.java))
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it) {
                binding.ivHamburger -> {
                    if(binding.drawerLayout.isDrawerOpen(binding.slideMenu.llDrawer)) {
                        binding.drawerLayout.closeDrawer(binding.slideMenu.llDrawer)
                    } else {
                        binding.drawerLayout.openDrawer(binding.slideMenu.llDrawer)
                    }
                }

                binding.tvSeeDetail, binding.ivArrow -> {
                    if(binding.llDetail.visibility == View.GONE) {
                        binding.llDetail.expand(scrollView = binding.nestedScrollView)
                        binding.tvSeeDetail.text = "닫기"
                        binding.ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    } else {
                        binding.llDetail.collapse()
                        binding.tvSeeDetail.text = "자세히 보기"
                        binding.ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    }
                }

                binding.slideMenu.llLeftArea -> {
                    if(binding.drawerLayout.isDrawerOpen(binding.slideMenu.llDrawer)) {
                        binding.drawerLayout.closeDrawer(binding.slideMenu.llDrawer)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}