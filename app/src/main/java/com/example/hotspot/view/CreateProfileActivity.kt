package com.example.hotspot.view

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.viewModel.CreateProfileController

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var createProfileVM : CreateProfileController
    private var progressDialog: ProgressDialog? = null


    private lateinit var binding: ActivityCreateProfileBinding

    var bitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        createProfileVM = CreateProfileController(this,  binding)


        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Please wait")
        progressDialog?.setMessage("Loading ...")



        binding.activityCreateProfileSelectImgTv.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }



        binding.activityCreateProfileCreateprofileBtn.setOnClickListener {
            progressDialog!!.show()
            createProfileVM.createNewProfile(bitMap, { -> updateUIOnSuccess()}, { msg -> updateUIOnFailure(msg)})

        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            val uri = data.data
            bitMap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            binding.activityCreateProfileImage.setImageBitmap(bitMap)

        }
    }





    private fun updateUIOnSuccess() {

        progressDialog?.dismiss()
        Toast.makeText(baseContext, "Successfully profile created.", Toast.LENGTH_LONG).show()
        val intent = Intent(this, AfterLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()

    }


    private fun updateUIOnFailure(message: String) {
        progressDialog?.dismiss()
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }










}