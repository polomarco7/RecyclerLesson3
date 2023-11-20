package com.example.recycleraston

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.recycleraston.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    companion object {
        const val REQUEST_KEY_EDIT = "EditFragment_REQUEST_KEY"
    }

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()

    private var contacts = ContactsInfo()
    private var id = 0
    private var firstName = ""
    private var lastName = ""
    private var phoneNumber = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments.let {
            if (it != null) {
                contacts = it.getParcelable("contacts")!!
            }
        }
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            idTextView.text = contacts.id.toString()
            nameTextEdit.setText(contacts.name)
            lastNameTextEdit.setText(contacts.surname)
            phoneTextEdit.setText(contacts.phoneNumber)

            lastName = contacts.surname
            firstName = contacts.name
            phoneNumber = contacts.phoneNumber

            nameTextEdit.addTextChangedListener {
                if (it.isNullOrEmpty())
                    firstName = it.toString()
            }
            lastNameTextEdit.addTextChangedListener {
                if (it.isNullOrEmpty())
                    lastName = it.toString()
            }
            phoneTextEdit.addTextChangedListener {
                if (it.isNullOrEmpty())
                    phoneNumber = it.toString()
            }

            editBtn.setOnClickListener {
                bundle.apply {
                    putInt("id", contacts.id!!)
                    putString("firstName", firstName)
                    putString("lastName", lastName)
                    putString("phoneNumber", phoneNumber)
                }
                Log.d("Edit fragment", "$id, $firstName, $lastName, $phoneNumber")
                setFragmentResult(REQUEST_KEY_EDIT, bundle)
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}