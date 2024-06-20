package com.example.prac1

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
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.prac1.databinding.FragmentOneBinding
import java.time.DayOfWeek

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
    lateinit var room:String

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
                        binding.dayButton.text = day
                        binding.dayButton.textSize = 15f
                        binding.dayButton.setTextColor(Color.parseColor("#ffffff"))
                    }
                }, 2024, 3,27).show()
            }
        }

        binding.timeButton.setOnClickListener{
            TimePickerDialog(requireContext(), object: TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute:Int){
                    Toast.makeText(context, "$hourOfDay 시 $minute 분", Toast.LENGTH_LONG).show()
                    val period = if (hourOfDay < 12) "오전" else "오후"
                    val hour = if (hourOfDay < 12) hourOfDay else hourOfDay - 12
                    time="$period $hour 시 $minute 분"
                    binding.timeButton.text = time
                    binding.timeButton.textSize = 15f
                    binding.timeButton.setTextColor(Color.parseColor("#ffffff"))

                }
            }, 15, 0, true).show()
        }
        val items = arrayOf<String>("Room1", "Room2", "Room3")
        var selected = 0
        val eventHandler2 = object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    room = "${items[selected]}"
                    binding.roomButton.text = room
                    Toast.makeText(requireContext(), "$room 이 최종 선택되었습니다.", Toast.LENGTH_LONG).show()
                } else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                    Toast.makeText(requireContext(), "예약룸 선택이 취소되었습니다.", Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.roomButton.setOnClickListener{
            AlertDialog.Builder(requireContext()).run(){
                setTitle("예약룸 선택하기")

                setSingleChoiceItems(items, 0, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]}선택")
                        selected =which
                    }
                })
                setPositiveButton("OK", eventHandler2)
                setNegativeButton("CANCEL", eventHandler2)
                show()
            }
        }

        binding.finishButton.setOnClickListener{
            name = binding.nameText.text.toString()
            binding.textview.isVisible = true
            binding.textview.text = "예약자는 $name \n 예약 날짜는 $day $time \n 예약룸은 $room"
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