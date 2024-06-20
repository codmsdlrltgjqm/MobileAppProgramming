package com.example.mid20220969

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.mid20220969.databinding.FragmentOneBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OneFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var name:String
    lateinit var day:String
    lateinit var time:String
    lateinit var eat:String
    lateinit var star:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        binding.dayButton.setOnClickListener{

            context?.let{it1->
                DatePickerDialog(requireContext(), object: DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        Toast.makeText(context, "$year 년 ${month+1} 월 $dayOfMonth 일", Toast.LENGTH_LONG).show()
                        day="$year 년 ${month+1} 월 $dayOfMonth 일"
                        binding.dayText.text = day
                        binding.dayText.textSize = 15f
                        binding.dayText.setTextColor(Color.parseColor("#ff00ff"))
                    }
                }, 2024, 3,27).show()
            }
        }

        binding.timeButton.setOnClickListener{
            TimePickerDialog(requireContext(), object: TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute:Int){
                    Toast.makeText(context, "$hourOfDay 시 $minute 분", Toast.LENGTH_LONG).show()

                    time="$hourOfDay 시 $minute 분"
                    binding.timeText.text = time
                    binding.timeText.textSize = 15f
                    binding.timeText.setTextColor(Color.parseColor("#ff00ff"))

                }
            }, 14, 0, true).show()
        }
        val items = arrayOf<String>("많이", "보통", "적게")
        var selected = 0
        val eventHandler1 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    eat = "${items[selected]}"
                    binding.eatText.text = eat
                    binding.eatText.textSize = 15f
                    binding.eatText.setTextColor(Color.parseColor("#ff00ff"))
                    Toast.makeText(requireContext(), "$eat 선택", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.eat.setOnClickListener{
            AlertDialog.Builder(requireContext()).run(){
                setTitle("식사량 선택")
                setIcon(android.R.drawable.ic_dialog_alert)
                setSingleChoiceItems(items, 1, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]}선택")
                        selected =which
                    }
                })
                setPositiveButton("예", eventHandler1)
                setNegativeButton("아니오", eventHandler1)
                show()
            }
        }

        val stars = arrayOf<String>("나트륨 적게", "설탕 적게")
        val eventHandler2 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    star = "${stars[selected]} ${stars[selected+1]}"
                    binding.starText.text = star
                    binding.starText.textSize = 15f
                    binding.starText.setTextColor(Color.parseColor("#ff00ff"))
                    Toast.makeText(requireContext(), "$star", Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.star.setOnClickListener{
            AlertDialog.Builder(requireContext()).run(){
                setTitle("특이사항 선택")
                setIcon(android.R.drawable.ic_dialog_alert)

                setMultiChoiceItems(stars, booleanArrayOf(false, false), object:DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                        Log.d("mobileapp", "${items[which]} ${if(isChecked) "선택" else "해제"}")
                    }
                })

                setPositiveButton("예", eventHandler2)
                setNegativeButton("아니오", eventHandler2)
                show()
            }
        }

        binding.finishButton.setOnClickListener{
            binding.textview.isVisible = true
            binding.finishButton.text="수정"
            binding.textview.text = "$day \n $time \n $eat \n $star"
            binding.textview.setTextColor(Color.parseColor("#ff00ff"))
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}