package com.rosh.developer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rosh.developer.R
import com.rosh.developer.databinding.FragmentHomeBinding
import com.rosh.developer.mydb.MyDbHelper
import com.rosh.developer.utils.MyContains.UZB_PHONE_NUM_PREFIX

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private var state = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myDbHelper = MyDbHelper(requireContext())
        var number: String
        var password: String
        val users = myDbHelper.getAllUser()



        binding.button.setOnClickListener {
            number = binding.edtNumber.text.toString().trim()
            password = binding.edtParol.text.toString().trim()

            if (number.length > 1 && password.isNotEmpty()) {
                if (!UZB_PHONE_NUM_PREFIX.contains(number.substring(0, 2).toInt())) {
                    binding.edtNumber.error = "Raqam xato!"
                } else {


                    users.forEach {
                        if (it.number == number && password == it.password) {
                            state = true
                            val usersShowFragment = UsersShowFragment()
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.my_container, usersShowFragment).commit()
                        }
                    }
                    if (!state) {
                        Toast.makeText(context, "Iltimos, parol yoki raqamni boshidan tekshiring!!", Toast.LENGTH_LONG).show()
                    }

                }
            }


        }


        binding.sign.setOnClickListener {
            val registrationFragment = RegistrationFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.my_container, registrationFragment)
                .addToBackStack("back")
                .commit()
        }

        return binding.root
    }

}