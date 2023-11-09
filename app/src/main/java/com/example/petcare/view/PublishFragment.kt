package com.example.petcare.view
import androidx.fragment.app.Fragment
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petcare.databinding.FragmentPublishBinding
import java.util.Calendar

class PublishFragment : Fragment() {

    private lateinit var binding: FragmentPublishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentPublishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dia.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = "$day / ${month + 1} / $year"
            binding.dia.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }
}
