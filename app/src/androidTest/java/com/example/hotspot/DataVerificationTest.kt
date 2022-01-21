package com.example.hotspot

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hotspot.model.User
import com.example.hotspot.view.ActivityAfterLogin
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.provider.FirebaseInitProvider
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class DataVerificationTest {

    @get :Rule
    var activityRule: ActivityScenarioRule<ActivityAfterLogin>
            = ActivityScenarioRule(ActivityAfterLogin::class.java)

    lateinit var db: FirebaseFirestore
    lateinit var userRef: CollectionReference
    lateinit var messageRef: CollectionReference

    @Before
    fun setup() {

        db = Firebase.firestore
        userRef = db.collection("users")
        messageRef = db.collection("messages")
    }

    @Test
    fun testDatabase() {

         userRef.get()
             .addOnSuccessListener {
                 val users = it.toObjects<User>()

                 users.forEach { user ->

                     Assert.assertEquals(user.name == null, false)
                     Assert.assertEquals(user.uid == null, false)
                     Assert.assertEquals(user.age == null, false)
                     Assert.assertEquals(user.bio == null, false)
                     Assert.assertEquals(user.emailAddress == null, false)
                     Assert.assertEquals(user.gender == null, false)

                     verifyInput(user)
                         }
                 }
    }

    fun verifyInput(user: User) {
        val onlyLetters = user.name!!.matches("[a-zA-Z]*".toRegex())
        Assert.assertEquals(onlyLetters, true)

        Assert.assertEquals(user.uid!!.length == 28, true)

        Assert.assertEquals(user.age!! >= 13, true)

        Assert.assertEquals(user.bio!!.length >= 1, true)

        Assert.assertEquals(user.emailAddress!!.contains("@"), true)

        Assert.assertEquals((user.gender!!.equals("Male") || user.gender!!.equals("Female")), true)


    }
}