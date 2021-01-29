package hello.world.navigation_study.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import hello.world.navigation_study.databinding.FragmentFriendsBinding
import hello.world.navigation_study.databinding.FragmentHomeBinding
import hello.world.navigation_study.databinding.FragmentMusicBinding
import java.lang.IllegalArgumentException

class MusicFragment : Fragment() {

    // m~: member 변수
    private var mBinding: FragmentMusicBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // View Binding
        val binding = FragmentMusicBinding.inflate(inflater, container, false)
        mBinding = binding

        Log.d("Create", "Home_Fragment")

        return mBinding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // 1. 데이터 받음
//        val sendValue = arguments?.getInt("send")!!
//        Toast.makeText(context, "$sendValue", Toast.LENGTH_SHORT).show()

        // 2-1. 데이터 받음 (권장)
        // 예외처리를 해줌으로써 argument에 default value가 없어도 앱이 에러나지 않도록 설정
        try {
            arguments?.let {
                val arguments = MusicFragmentArgs.fromBundle(it)
                val itemId = arguments.itemId
                Toast.makeText(context, "$itemId", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

//        2-2. 데이터 받음
//        val safeArgs: MusicFragmentArgs by navArgs()
//        val itemId = safeArgs.itemId
//        Toast.makeText(context, "$itemId", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        // view가 사라질 때 binding도 삭제
        mBinding = null
        Log.d("Destroy", "Home_Fragment")
        super.onDestroyView()
    }
}
