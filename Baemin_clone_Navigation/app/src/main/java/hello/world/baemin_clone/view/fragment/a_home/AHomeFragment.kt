package hello.world.baemin_clone.view.fragment.a_home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import hello.world.baemin_clone.R
import hello.world.baemin_clone.data.BannerItem
import hello.world.baemin_clone.databinding.FragmentAHomeBinding
import hello.world.baemin_clone.view.EventActivity
import hello.world.baemin_clone.view.fragment.a_home.adapter.GridRecyclerViewAdapter
import hello.world.baemin_clone.view.fragment.a_home.adapter.ViewPagerAdapter
import hello.world.baemin_clone.view.fragment.a_home.listener.Interaction
import hello.world.baemin_clone.view.fragment.a_home.viewmodel.HomeViewModel
import hello.world.baemin_clone.view.ui.collapse
import hello.world.baemin_clone.view.ui.expand
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class AHomeFragment : Fragment(), Interaction{

    private var binding: FragmentAHomeBinding ?= null
    private val mBinding get() = binding!!
    private lateinit var gridRecyclerViewAdapter: GridRecyclerViewAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAHomeBinding.inflate(layoutInflater)
        binding = mBinding

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.tvSeeDetail.setOnClickListener(this)
        mBinding.ivArrow.setOnClickListener(this)
        mBinding.ivHamburger.setOnClickListener(this)
        mBinding.slideMenu.llLeftArea.setOnClickListener(this)

        initViewPager2()
        initGridRecyclerView()
        autoScrollViewPager()
        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getBannerItems()
        homeViewModel.getGridItems()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun subscribeObservers() {
        // Activity인 경우: this
        homeViewModel.bannerItemList.observe(viewLifecycleOwner, Observer {
            viewPagerAdapter.submitList(it)
        })
        homeViewModel.gridItemList.observe(viewLifecycleOwner, Observer {
            gridRecyclerViewAdapter.submitList(it)
        })
        homeViewModel.currentPosition.observe(viewLifecycleOwner, Observer {
            mBinding.viewPager2.currentItem = it
        })
    }

    private fun autoScrollViewPager() {
        // viewLifeCycleOwner: fragment의 Life cycle을 관여하는 FragmentViewLifeCycleOwner 클래스 객체 (권장)
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            // lifecycleScope: 현재 class의 생명이 살아있을 때만 작동하는 coroutine
            // Activity인 경우: this.lifecycleScope.isActive
            while (viewLifecycleOwner.lifecycleScope.isActive) {
                delay(3000)
                homeViewModel.getCurrentPosition()?.let {
                    homeViewModel.setCurrentPosition(it.plus(1) % 5)
                }
            }
        }
    }

    private fun initGridRecyclerView() {
        mBinding.gridRecyclerView.apply {
            gridRecyclerViewAdapter = GridRecyclerViewAdapter()
            layoutManager = GridLayoutManager(this@AHomeFragment.context, 4)
            adapter = gridRecyclerViewAdapter

        }
    }

    private fun initViewPager2() {
        mBinding.viewPager2.apply {
            viewPagerAdapter = ViewPagerAdapter(this@AHomeFragment)
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mBinding.tvPageNumber.text = (position+1).toString()
                    homeViewModel.setCurrentPosition(position)
                }
            })
        }
    }

    override fun onBannerItemClicked(bannerItem: BannerItem) {
        startActivity(Intent(this@AHomeFragment.context, EventActivity::class.java))
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it) {
                mBinding.ivHamburger -> {
                    if(mBinding.drawerLayout.isDrawerOpen(mBinding.slideMenu.llDrawer)) {
                        mBinding.drawerLayout.closeDrawer(mBinding.slideMenu.llDrawer)
                    } else {
                        mBinding.drawerLayout.openDrawer(mBinding.slideMenu.llDrawer)
                    }
                }

                mBinding.tvSeeDetail, mBinding.ivArrow -> {
                    if (mBinding.llDetail.visibility == View.GONE) {
                        mBinding.llDetail.expand(mBinding.nestedScrollView)
                        mBinding.tvSeeDetail.text = "닫기"
                        mBinding.ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    } else {
                        mBinding.llDetail.collapse()
                        mBinding.tvSeeDetail.text = "자세히보기"
                        mBinding.ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    }
                }

                mBinding.slideMenu.llLeftArea -> {
                    if(mBinding.drawerLayout.isDrawerOpen(mBinding.slideMenu.llDrawer)) {
                        mBinding.drawerLayout.closeDrawer(mBinding.slideMenu.llDrawer)
                    }
                }
            }
        }
    }

}