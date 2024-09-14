package com.example.firebase_tony_2

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase_tony_2.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityLoginBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //Firebase Auth

    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure actionbar
        actionBar = supportActionBar ?: return
        actionBar.title = "Login"

        //Configure Progress Dialog
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //Init firebase Auth
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        //Handle click, open SignUp Activity
        binding.noAccountTv.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }


        //Handle click, begin login
        binding.loginBtn.setOnClickListener{
            //before loggin in, validate data
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
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        actionBar = supportActionBar ?: return
        //get data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //No password entered
            binding.passwordEt.error = "Please enter password"
        }
        else{
            //data is validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener{
                //login success
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Logged in as $email",Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        // If user is already logged in go to profile Activity
        // Get current User
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //User is already logged in
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }


}