package com.muhammedkursatgokgun.artbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.muhammedkursatgokgun.artbook.R
import com.muhammedkursatgokgun.artbook.databinding.FragmentArtBinding

private var _binding: FragmentArtBinding?=null

private val binding get() = _binding!!
class ArtFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentArtBinding.inflate(inflater,container,false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
        //return inflater.inflate(R.layout.fragment_art, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonToUploadFr.setOnClickListener {
            val action = ArtFragmentDirections.actionArtFragmentToUploadFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}