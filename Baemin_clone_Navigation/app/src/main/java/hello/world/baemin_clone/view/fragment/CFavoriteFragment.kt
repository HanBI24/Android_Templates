package hello.world.baemin_clone.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hello.world.baemin_clone.R
import hello.world.baemin_clone.databinding.FragmentAHomeBinding
import hello.world.baemin_clone.databinding.FragmentCFavoriteeBinding

class CFavoriteFragment : Fragment() {

    private var binding: FragmentCFavoriteeBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mBinding = FragmentCFavoriteeBinding.inflate(layoutInflater)
        binding = mBinding

        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}