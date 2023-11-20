package com.example.recycleraston

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.recycleraston.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()

    private var id = 0
    private var firstName = ""
    private var lastName = ""
    private var phoneNumber = ""


    companion object {
        const val REQUEST_KEY = "FragmentB_REQUEST_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            idEditText.addTextChangedListener {
                id = it.toString().toInt()
            }
            firstNameEdit.addTextChangedListener {
                firstName = it.toString()
            }
            lastNameEdit.addTextChangedListener {
                lastName = it.toString()
            }
            phoneNumEdit.addTextChangedListener {
                phoneNumber = it.toString()
            }

            addBtn.setOnClickListener {
                bundle.apply {
                    putInt("id", id)
                    putString("firstName", firstName)
                    putString("lastName", lastName)
                    putString("phoneNumber", phoneNumber)
                }
                Log.d("Add fragment", "$id, $firstName, $lastName, $phoneNumber")
                setFragmentResult(REQUEST_KEY, bundle)
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}