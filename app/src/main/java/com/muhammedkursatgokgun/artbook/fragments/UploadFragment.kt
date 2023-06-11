package com.muhammedkursatgokgun.artbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.muhammedkursatgokgun.artbook.R
import com.muhammedkursatgokgun.artbook.databinding.FragmentUploadBinding

private var _binding: FragmentUploadBinding?=null

private val binding get() = _binding!!

class UploadFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentUploadBinding.inflate(inflater,container,false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
        //return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUpload.setOnClickListener {
            val action = UploadFragmentDirections.actionUploadFragmentToArtFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}