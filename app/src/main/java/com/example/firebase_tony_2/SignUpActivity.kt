package com.example.firebase_tony_2

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase_tony_2.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivitySignUpBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //progressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar ?: return
        actionBar.title = "Sign up"
        //Enable Back Button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //Configure progress dialog
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating Account In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, begin signup
        binding.SignBtn.setOnClickListener{
            //validate data
            validateData()
        }


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validateData() {
        //get Data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //Validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //Invalid email format
            binding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //password is not entered
            binding.passwordEt.error = "Please enter password"
        }
        else if (password.length <6){
            //password lenght is less than 6
            binding.passwordEt.error = "Password must be atleast 6 characters long "
        }
        else{
            //data is valid, continue normally
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //Show progress
        progressDialog.show()

        //Create account
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //Signup sucess
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with email $email",Toast.LENGTH_SHORT).show()

                //Open profile
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener{ e->
                //signup failed
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }

}