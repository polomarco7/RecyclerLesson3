package com.example.recycleraston

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recycleraston.EditFragment.Companion.REQUEST_KEY_EDIT
import com.example.recycleraston.databinding.FragmentMainFragmentBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainFragment : Fragment() {

    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()


    private val contactsAdapter = ContactsAdapter { contacts -> onItemClick(contacts) }

    private var id = 0
    private var firstName = ""
    private var lastName = ""
    private var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(AddItemFragment.REQUEST_KEY) { _, bundle ->
            id = bundle.getInt("id")
            firstName = bundle.getString("firstName", "")
            lastName = bundle.getString("lastName", "")
            phoneNumber = bundle.getString("phoneNumber", "")
            contactsAdapter.add(ContactsInfo(id, firstName, lastName, phoneNumber), 0)
        }
        setFragmentResultListener(REQUEST_KEY_EDIT) { _, bundle ->
            id = bundle.getInt("id")
            firstName = bundle.getString("firstName", "")
            lastName = bundle.getString("lastName", "")
            phoneNumber = bundle.getString("phoneNumber", "")
            contactsAdapter.updateContact(ContactsInfo(id, firstName, lastName, phoneNumber))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = contactsAdapter

        viewModel.contactsData.onEach {
            contactsAdapter.updateAll(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.addItemBtn.setOnClickListener {
            openAddFragment()
        }

        binding.selectBtn.setOnClickListener {
            Toast.makeText(context, "Выберите один или несколько элементов",
                Toast.LENGTH_LONG).show()
            contactsAdapter.setDelState = true
            contactsAdapter.notifyDataSetChanged()
            binding.addItemBtn.visibility = View.GONE
            binding.deleteLayout.visibility = View.VISIBLE
        }

        binding.delItemBtn.setOnClickListener {
            contactsAdapter.removeSelected()
            binding.addItemBtn.visibility = View.VISIBLE
            binding.deleteLayout.visibility = View.GONE
            contactsAdapter.setDelState = false
            contactsAdapter.notifyDataSetChanged()
        }

        binding.cancelBtn.setOnClickListener {
            contactsAdapter.cancelSelect()
            binding.addItemBtn.visibility = View.VISIBLE
            binding.deleteLayout.visibility = View.GONE
            contactsAdapter.setDelState = false
            contactsAdapter.notifyDataSetChanged()
        }
    }

    private fun onItemClick(item: ContactsInfo) {
        if (!contactsAdapter.setDelState) {
            val bundle = bundleOf("contacts" to item)
            findNavController().navigate(R.id.editFragment, bundle)
        }
    }

    private fun openAddFragment() {
        findNavController().navigate(R.id.addItemFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}