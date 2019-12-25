package com.example.calculator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.calculator.databinding.FragmentQuestionBinding
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myViewModel = ViewModelProviders.of(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
        ).get(MyViewModel::class.java)
        val binding: FragmentQuestionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false)
        myViewModel.getCurrentScore().value = 0
        myViewModel.generator()
        binding.data = myViewModel
        binding.lifecycleOwner = requireActivity()
        val sb = StringBuilder()
        initNumberButton(binding, sb)
        initFunctionButton(binding, sb, myViewModel)
        return binding.root
    }

    private fun initNumberButton(
        binding: FragmentQuestionBinding,
        sb: StringBuilder
    ) {
        val listener = View.OnClickListener {
            if (sb.length > 3) {
                sb.setLength(0)
            }
            when (it.id) {
                R.id.button0 -> sb.append("0")
                R.id.button1 -> sb.append("1")
                R.id.button2 -> sb.append("2")
                R.id.button3 -> sb.append("3")
                R.id.button4 -> sb.append("4")
                R.id.button5 -> sb.append("5")
                R.id.button6 -> sb.append("6")
                R.id.button7 -> sb.append("7")
                R.id.button8 -> sb.append("8")
                R.id.button9 -> sb.append("9")
            }
            binding.textView9.text = sb.toString()
        }
        binding.button0.setOnClickListener(listener)
        binding.button1.setOnClickListener(listener)
        binding.button2.setOnClickListener(listener)
        binding.button3.setOnClickListener(listener)
        binding.button4.setOnClickListener(listener)
        binding.button5.setOnClickListener(listener)
        binding.button6.setOnClickListener(listener)
        binding.button7.setOnClickListener(listener)
        binding.button8.setOnClickListener(listener)
        binding.button9.setOnClickListener(listener)
    }

    private fun initFunctionButton(
        binding: FragmentQuestionBinding,
        sb: StringBuilder,
        myViewModel: MyViewModel
    ) {
        binding.buttonClear.setOnClickListener {
            sb.setLength(0)
            val string = getString(R.string.input_indicator)
            sb.append(string)
            binding.textView9.text = string
        }
        binding.buttonSubmit.setOnClickListener {
            // sb 有可能为空
            if (sb.length > 3 || sb.isEmpty()) {
                Toast.makeText(
                    requireActivity(),
                    "请输入答案",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (Integer.valueOf(sb.toString()) == myViewModel.getAnswer().value) {
                myViewModel.answerCorrect()
                sb.setLength(0)
                sb.append(getString(R.string.answer_correct_message))
                binding.textView9.text = sb.toString()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "您本次得分:${myViewModel.getCurrentScore().value}  , 历史最高分:${myViewModel.getHighScore().value!!}",
                    Toast.LENGTH_SHORT
                ).show()
                val currentScore = myViewModel.getCurrentScore().value!!
                val winFlag = if (currentScore > myViewModel.getHighScore().value!!) {
                    myViewModel.getResultScoreMsg().value =
                        getString(R.string.win_score_msg) + currentScore
                    myViewModel.getResultMsg().value = getString(R.string.win_msg)
                    myViewModel.save()
                    true
                } else {
                    myViewModel.getResultScoreMsg().value =
                        getString(R.string.lose_score_msg) + currentScore
                    myViewModel.getResultMsg().value = getString(R.string.lose_msg)
                    false
                }
                Navigation.findNavController(it)
                    .navigate(
                        R.id.action_questionFragment_to_resultFragment,
                        bundleOf("flag" to winFlag)
                    )
            }
        }

    }
}
