package hello.world.baemin_clone.view.fragment.b_eat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import hello.world.baemin_clone.databinding.FragmentBEatBinding
import hello.world.baemin_clone.view.fragment.b_eat.adapter.WhatToEatAdapter
import hello.world.baemin_clone.view.fragment.b_eat.viewmodel.EatWhatViewModel

class BEatFragment : Fragment() {

    private var binding: FragmentBEatBinding? = null
    private val eatWhatViewModel: EatWhatViewModel by viewModels()
    private lateinit var whatToEatAdapter: WhatToEatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mBinding = FragmentBEatBinding.inflate(layoutInflater)
        binding = mBinding

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eatWhatViewModel.eatFakeWhatToEatList()

        whatToEatAdapter = WhatToEatAdapter()
        binding!!.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = whatToEatAdapter
        }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        eatWhatViewModel.eatWhatToEatList.observe(viewLifecycleOwner, {
            it?.let {
                whatToEatAdapter.apply {
                    setList(it)
                }
            }
        })
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}