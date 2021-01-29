package hello.world.navigation_study.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hello.world.navigation_study.databinding.FragmentFriendsBinding
import hello.world.navigation_study.databinding.FragmentHomeBinding

class FriendsFragment : Fragment() {

    // m~: member 변수

    private var mBinding: FragmentFriendsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View Binding
        val binding = FragmentFriendsBinding.inflate(inflater, container, false)
        mBinding = binding

        Log.d("Create", "Home_Fragment")

        return mBinding?.root
    }

    override fun onDestroyView() {
        // view가 사라질 때 binding도 삭제
        mBinding = null
        Log.d("Destroy", "Home_Fragment")
        super.onDestroyView()
    }
}
