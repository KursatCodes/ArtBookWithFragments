package com.muhammedkursatgokgun.artbook.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammedkursatgokgun.artbook.R
import com.muhammedkursatgokgun.artbook.activities.ArtActivity
import com.muhammedkursatgokgun.artbook.databinding.FragmentUploadBinding
import com.muhammedkursatgokgun.artbook.model.Art

private var _binding: FragmentUploadBinding?=null
private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
private lateinit var permissionLauncher: ActivityResultLauncher<String>
private val binding get() = _binding!!
private var selectedImage :Uri? = null
private var artNames = ArrayList<Art>()
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

        registerLauncer()

        binding.buttonUpload.setOnClickListener {
            var name = binding.editTextTittle.text.toString()
            var comment = binding.editTextComment.text.toString()
            var image: Uri?=null
            selectedImage?.let {
                image = selectedImage
            }
            var newArt = Art(name,comment,image)
            artNames= ArrayList<Art>()
            artNames.add(newArt)
            val action=
                UploadFragmentDirections.actionUploadFragmentToArtFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.imageView.setOnClickListener {
            requestPermission()
        }
    }
    private fun requestPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this@UploadFragment.requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(requireView(),"Need permission.",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission"){
                        //request permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
                }else{
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

            }else{
                //intent to galerry
                //start activity for result
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }else{
            if(ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this@UploadFragment.requireActivity(),Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(requireView(),"Need permission.",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission"){
                        //request permission
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }.show()
                }else{
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }

            }else{
                //intent to galerry
                //start activity for result
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }
    }
    private fun registerLauncer(){
        activityResultLauncher = registerForActivityResult (ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                val intentFromResult =it.data
                if(intentFromResult != null){
                    selectedImage = intentFromResult.data
                    selectedImage.let {
                        binding.imageView.setImageURI(it)
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult (ActivityResultContracts.RequestPermission()){
            if(it){
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }else{
                Toast.makeText(this.context,"Need permission dedikya gardaş.",Toast.LENGTH_LONG).show()

            }
        }
    }

}