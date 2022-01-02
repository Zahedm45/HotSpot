package com.example.hotspot.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.hotspot.R
import com.example.hotspot.databinding.ActivityPhoneAuthBinding
import com.example.hotspot.view.createProfilePackage.ActivityCreateProfile
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneAuthBinding

    private lateinit var forceResendtingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var verifyID: String

    private val TAG = "MAIN_TAG"

    private lateinit var progressDialog: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.enterVerificationLinearLayout.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressBar(this)

        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential){
                //this callback will be invoked in two situations:
                // 1: Instantly verified: In some cases, phone can automatically verify phone number
                // 2: Auto-retrieval: One some devices Google Play might be able to auto verify.
                errorToast("Verification success. Attempting to sign in.")
                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // called when an invalid request i made.
                // for example if an invalid phone number is entered.
                Log.w(TAG, "onVerificationFailed", e)
                binding.progressBar.visibility = View.GONE
                binding.phoneAuthContinueButton.visibility = View.VISIBLE
                errorToast("Invalid phone number.")
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                //This method is called if code has been succesfully sent.
                // User should now be notified to enter the code.
                verifyID = verificationId
                forceResendtingToken = token
                errorToast("Code sent.")
                binding.enterVerificationLinearLayout.visibility = View.VISIBLE
                binding.enterNumberLinearLayout.visibility = View.GONE
                binding.progressBar.visibility = View.GONE


            }
        }

        binding.phoneAuthContinueButton.setOnClickListener{
            if(binding.phoneNumberEditText.text.isEmpty()){
                Toast.makeText(this,"Please enter a phone number.",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val phoneNumber = binding.phoneNumberEditText.text.toString().trim()
            startPhoneNumberVerification(phoneNumber)
        }

        binding.submitButton.setOnClickListener{
            verifyPhoneNumberWithCode(verifyID, binding.verifyCodeTextEdit.text.toString().trim())
        }

        binding.resendText.setOnClickListener{
            val phoneNumber = binding.phoneNumberEditText.text.toString().trim()
            resendVerificationCode(phoneNumber, forceResendtingToken)
        }

    }

    private fun errorToast(toastMsg: String){
        Toast.makeText(this,toastMsg,Toast.LENGTH_LONG).show()
    }

    private fun startPhoneNumberVerification(phoneNumber: String){
        binding.progressBar.visibility = View.VISIBLE
        binding.phoneAuthContinueButton.visibility = View.GONE

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // [START resend_verification]
    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(mCallBacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }
    // [END resend_verification]

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String){
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
        // [END verify_with_code]
    }

    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    val intentCreateProfile = Intent(this, ActivityCreateProfile::class.java)
                    startActivity(intentCreateProfile)
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        errorToast("Invalid verification code.")
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    // [END sign_in_with_phone]
}