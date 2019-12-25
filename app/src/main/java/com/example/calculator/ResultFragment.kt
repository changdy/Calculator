package com.example.calculator


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.calculator.databinding.FragmentResultBinding


/**
 * A simple [Fragment] subclass.
 */
class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myViewModel = ViewModelProviders.of(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
        ).get(MyViewModel::class.java)
        val binding: FragmentResultBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        myViewModel.getCurrentScore().value = 0
        myViewModel.generator()
        val img = if (arguments!!.getBoolean("flag"))
            R.drawable.ic_sentiment_satisfied_black_24dp else R.drawable.ic_sentiment_dissatisfied_black_24dp
        binding.imageView2.setImageResource(img)
        binding.data = myViewModel
        binding.lifecycleOwner = requireActivity()
        binding.button10.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_titleFragment)
        }
        return binding.root
    }
}
