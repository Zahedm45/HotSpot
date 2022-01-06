package com.example.hotspot.view.phoneAuthentification

import android.content.Intent
import android.graphics.Color.BLACK
import android.graphics.Color.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.hotspot.databinding.ActivityPhoneAuthBinding
import com.example.hotspot.other.ButtonAnimations
import com.example.hotspot.repository.Repository
import com.example.hotspot.view.AfterLoginActivity
import com.example.hotspot.view.LoginActivity
import com.example.hotspot.view.createProfilePackage.ActivityCreateProfile
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentSnapshot
import com.hbb20.CountryCodePicker
import com.hbb20.CountryCodePicker.PhoneNumberValidityChangeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ActivityPhoneAuthentification : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneAuthBinding

    private lateinit var forceResendtingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var verifyID: String

    private val TAG = "MAIN_TAG"

    private val repository = Repository

    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.enterVerificationLinearLayout.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.progressBar2.visibility = View.GONE

        firebaseAuth = FirebaseAuth.getInstance()

        countrySelector()
        hasCodeBeenEntered()

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

                if (e is FirebaseAuthInvalidCredentialsException) {
                    errorToast("Invalid phone number.")
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    errorToast("SMS quota on Firebase exceeded.")
                    // The SMS quota for the project has been exceeded
                }

            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                //This method is called if code has been succesfully sent.
                // User should now be notified to enter the code.
                verifyID = verificationId
                forceResendtingToken = token
                errorToast("Code sent.")
                binding.PhoneNumberSentTo.text = "Verification code sent to: "+ "$phoneNumber"
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
            phoneNumber = binding.ccp.fullNumberWithPlus
            startPhoneNumberVerification(phoneNumber)
        }

        binding.submitButton.setOnClickListener{
            verifyPhoneNumberWithCode(verifyID, binding.verifyCodeTextEdit.text.toString().trim())
        }

        binding.resendText.setOnClickListener{
            val phoneNumber = binding.ccp.fullNumberWithPlus
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
        binding.progressBar2.visibility = View.VISIBLE
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    //val user = task.result?.user
                    var isCreated = false
                    CoroutineScope(IO).launch {
                        isCreated = repository.isUserProfileCreated()
                        if(!isCreated) {
                            startCreateProfileActivity()
                        }
                        else{
                            startLoggedInActivity()
                        }
                    }
                    //start create profile activity

                } else {
                    binding.progressBar2.visibility = View.GONE
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        errorToast("Invalid verification code.")
                        binding.progressBar2.visibility = View.GONE
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    // [END sign_in_with_phone]


    private fun countrySelector(){
        binding.ccp.registerCarrierNumberEditText(binding.phoneNumberEditText)

        binding.ccp.setPhoneNumberValidityChangeListener {
            if(!it){
                binding.phoneNumberEditText.setTextColor(RED)
                ButtonAnimations.fadeOut(binding.phoneAuthContinueButton)
            }
            else if(it){
                ButtonAnimations.fadeIn(binding.phoneAuthContinueButton)
                binding.phoneNumberEditText.setTextColor(BLACK)
            }
        }
    }

    private fun hasCodeBeenEntered(){

        binding.verifyCodeTextEdit.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {

                Log.d("Editable",s.length.toString())
                if (s.length == 6) {
                    ButtonAnimations.fadeIn(binding.submitButton)
                    binding.verifyCodeTextEdit.setTextColor(BLACK)
                }
                else {
                    binding.verifyCodeTextEdit.setTextColor(RED)
                    ButtonAnimations.fadeOut(binding.submitButton)
                }
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

    private fun startCreateProfileActivity(){
        val intentCreateProfile = Intent(this@ActivityPhoneAuthentification, ActivityCreateProfile::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intentCreateProfile)
        finish()
    }

    private fun startLoggedInActivity(){
        val intent = Intent(this@ActivityPhoneAuthentification, AfterLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}