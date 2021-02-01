package hello.world.navigation_study.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import hello.world.navigation_study.R
import hello.world.navigation_study.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // m~: member 변수
    private var mBinding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View Binding
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding = binding

        Log.d("Create", "Home_Fragment")

        // 1. 프래그먼트 전환 및 데이터 전달
//        binding.homeFragmentTitle.setOnClickListener {
//            findNavController().navigate(R.id.musicFragment, bundleOf("send" to 10))
//        }

        // 2. 프래그먼트 전환 및 데이터 전달 (권장)
        // (필쑤!) nav_graph.xml에서 action(화면 전환)을 설정해야 direction이 생김
        // argument는 그냥 fragment에서 받는 클래스만 생성할 뿐
        // default value
        // 여러개의 fragment에 값을 넘기기 위해선 그에 맞는 fragment view에 action을 추가하면 됨
//        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMusicFragment())
        binding.homeFragmentTitle.setOnClickListener {
//            findNavController().navigate(
//                HomeFragmentDirections.actionHomeFragmentToMusicFragment(
//                    itemId = 10
//                )
//            )
            // 일반적인 fragment 전환
            findNavController().navigate(R.id.friendsFragment)

        }
        return mBinding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        // view가 사라질 때 binding도 삭제 (memory leak)
        mBinding = null
        Log.d("Destroy", "Home_Fragment")
        super.onDestroyView()
    }
}
