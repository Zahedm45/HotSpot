package com.example.hotspot.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hotspot.R
import com.example.hotspot.databinding.FragmentCreateProfileBinding
import com.example.hotspot.model.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateProfile : Fragment(),View.OnClickListener {

    lateinit var navController : NavController

    private var _binding: FragmentCreateProfileBinding? = null
    private val binding get() = _binding!!

    private val loginVM : LoginViewModel by viewModels()

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_create_profile, container, false)

        _binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.createprofile_btn).setOnClickListener(this)

    }
    override fun onClick(p0: View?) {
        when (p0!!.id) {
  //          R.id.createprofile_btn -> navController.navigate(R.id.homeMapeCop)
  //          R.id.createprofile_btn -> loginVM.createUser(binding, auth, this)

            R.id.createprofile_btn -> createUser()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth


    }




    fun createUser() {
        val email = binding.createProfileEmailinput.text.toString()
        val password = binding.createProfilePassword.text.toString()
        Log.i(TAG ,"getUpdate...")


        this.activity?.let {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                       // updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)

                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                      //  updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        TODO("Not yet implemented")
    }


}