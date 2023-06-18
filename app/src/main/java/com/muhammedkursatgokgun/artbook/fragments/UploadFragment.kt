package com.muhammedkursatgokgun.artbook.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
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
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammedkursatgokgun.artbook.R
import com.muhammedkursatgokgun.artbook.activities.ArtActivity
import com.muhammedkursatgokgun.artbook.databinding.FragmentUploadBinding
import com.muhammedkursatgokgun.artbook.model.Art
import com.muhammedkursatgokgun.artbook.roomdb.database
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream

private var _binding: FragmentUploadBinding?=null
private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
private lateinit var permissionLauncher: ActivityResultLauncher<String>
private val binding get() = _binding!!
private var selectedImage :Uri? = null
private var selectedBitmap : Bitmap?=null
private var artNames = ArrayList<Art>()
private val myDisposable = CompositeDisposable()
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
        println("selam --- 1")
        val db = Room.databaseBuilder(
            requireContext(),
            database::class.java,
            "Art").build()
        println("selam --- 2")
        val myDao = db.dao()
        println("selam --- 111")
        //val arts : List<Art> = myDao.getAll()
        println("selam --- 222")
        registerLauncer()
        println("selam --- 3")
        binding.buttonUpload.setOnClickListener {
            var name = binding.editTextTittle.text.toString()
            var comment = binding.editTextComment.text.toString()
            println("selam --- 4")
            if(selectedBitmap!=null){
                var smallBitmap = makeSmallerBitmap(selectedBitmap!!,300)
                var outputStream = ByteArrayOutputStream()
                smallBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
                println("selam --- 7")
                var byteArray = outputStream.toByteArray()

                var art = Art(name,comment,byteArray)
                myDisposable.add(myDao.insert(art)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleRespose)
                )
            }

            //val action = UploadFragmentDirections.actionUploadFragmentToArtFragment()
            //Navigation.findNavController(it).navigate(action)
        }

        binding.imageView.setOnClickListener {
            requestPermission()
        }

        binding.deleteButton.setOnClickListener {

        }
        println("selam --- 5")
    }
    private fun handleRespose(){
        var action = UploadFragmentDirections.actionUploadFragmentToArtFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
    private fun makeBitmap(): Bitmap? {
        var selectedBitmap: Bitmap?=null
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(
                    requireActivity().contentResolver,
                    selectedImage!!
                )
                selectedBitmap = ImageDecoder.decodeBitmap(source)
                binding.imageView.setImageBitmap(selectedBitmap)

            } else {
                selectedBitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,selectedImage
                )
                binding.imageView.setImageBitmap(selectedBitmap)

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return selectedBitmap
    }
    private fun makeSmallerBitmap(image: Bitmap, maximumSize : Int) : Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio : Double = width.toDouble() / height.toDouble()
        if (bitmapRatio > 1) {
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        } else {
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }
        return Bitmap.createScaledBitmap(image,width,height,true)
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
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                selectedImage!!
                            )
                            selectedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageView.setImageBitmap(selectedBitmap)

                        } else {
                            selectedBitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,selectedImage
                            )
                            binding.imageView.setImageBitmap(selectedBitmap)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}