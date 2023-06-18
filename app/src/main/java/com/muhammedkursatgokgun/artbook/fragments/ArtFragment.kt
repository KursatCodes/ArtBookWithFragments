package com.muhammedkursatgokgun.artbook.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.transaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammedkursatgokgun.artbook.R
import com.muhammedkursatgokgun.artbook.activities.MainActivity
import com.muhammedkursatgokgun.artbook.databinding.FragmentArtBinding
import com.muhammedkursatgokgun.artbook.fragments.UploadFragmentDirections.ActionUploadFragmentToArtFragment
import com.muhammedkursatgokgun.artbook.model.Art
import kotlin.concurrent.fixedRateTimer

private var _binding: FragmentArtBinding?=null
private lateinit var auth: FirebaseAuth
private var artArrayList = ArrayList<Art>()
private val binding get() = _binding!!
class ArtFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.art_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_art -> {
                // Navigate to settings screen.
                var arguman=2
                val action = ArtFragmentDirections.actionArtFragmentToUploadFragment()
                Navigation.findNavController(requireView()).navigate(action)
                true
            }
            R.id.sign_out -> {
                // Save profile changes.
                auth.signOut()
                val intent = Intent(this.context,MainActivity::class.java)
                startActivity(intent)
                onDestroy()
                /*fragmentManager?.commit {
                    setReorderingAllowed(true)
                    replace<UploadFragment>(R.id.artFragment)
                }*/

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        auth = Firebase.auth
    }


}