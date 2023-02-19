package com.rosh.developer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rosh.developer.R
import com.rosh.developer.adapters.RvAdapter
import com.rosh.developer.adapters.RvClick
import com.rosh.developer.databinding.BottomSheetBinding
import com.rosh.developer.databinding.FragmentUsersShowBinding
import com.rosh.developer.models.User
import com.rosh.developer.mydb.MyDbHelper
import com.rosh.developer.utils.MySharedPreference
import com.rosh.developer.utils.MySharedPreference.LOG_IN_STATE
import java.io.File


class UsersShowFragment : Fragment(), RvClick {
    private val binding by lazy { FragmentUsersShowBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var rvAdapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        showAlertDialog()
        myDbHelper = MyDbHelper(requireContext())
        val users = myDbHelper.getAllUser()

        rvAdapter = RvAdapter(requireContext(), users, this)
        binding.rv.adapter = rvAdapter

        MySharedPreference.init(requireContext())
        if (!LOG_IN_STATE) {
            LOG_IN_STATE = true
        }





        return binding.root
    }

    override fun itemClicked(user: User) {
        val bottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val bottomSheetBinding = BottomSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()


    }

    override fun itemMenuClicked(user: User, view: View, position: Int) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.my_menu)
        popupMenu.setOnMenuItemClickListener {
            rvAdapter.List.removeAt(position)
            rvAdapter.notifyItemRemoved(position)
            rvAdapter.notifyItemRangeChanged(0, rvAdapter.itemCount)
            myDbHelper.deleteUser(user)
            val file = File(requireActivity().filesDir, "${user.id}.jpg")
            file.delete()
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()


    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Tizimdan chiqmoqchimisiz")
        builder.setPositiveButton("Xa") { _, _ ->
            LOG_IN_STATE = false
            val homeFragment = HomeFragment()
            parentFragmentManager.beginTransaction().replace(R.id.my_container, homeFragment).commit()

        }
        builder.setNegativeButton("Yo'q", null)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}