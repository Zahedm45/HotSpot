package com.example.hotspot.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.hotspot.databinding.ActivityCreateProfileBinding
import com.example.hotspot.viewModel.CreateProfileController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


  //  var bitMDrawable: BitmapDrawable? = null

    private lateinit var createProfileVM : CreateProfileController

    private lateinit var binding: ActivityCreateProfileBinding

    var bitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = Firebase.auth
        createProfileVM = CreateProfileController(this,  binding)



        binding.activityCreateProfileSelectImgTv.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }



        binding.activityCreateProfileCreateprofileBtn.setOnClickListener {
            createProfileVM.createNewProfile(bitMap, { -> updateUIOnSuccess()}, { -> updateUIOnFail()})

        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            val uri = data.data
            bitMap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            binding.activityCreateProfileImage.setImageBitmap(bitMap)
//            val bitMDrawable = BitmapDrawable(bitMap)
//            binding.activityCreateProfileImage.setBackgroundDrawable(bitMDrawable)

        }
    }

    override fun onStart() {
        super.onStart()

//        val currentUser = auth.currentUser
//        if(currentUser != null){
//       //     reload();
//        }
    }






    private fun updateUIOnSuccess() {

        Toast.makeText(baseContext, "Successfully profile created.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, AfterLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()

    }


    private fun updateUIOnFail() {
        Toast.makeText(baseContext, "Error uploading image to database! ", Toast.LENGTH_SHORT).show()
    }










}