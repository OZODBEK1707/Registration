package com.rosh.developer.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosh.developer.databinding.ItemRvBinding
import com.rosh.developer.models.User

class RvAdapter(val context: Context, val List: ArrayList<User>, val rvClick: RvClick):RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: User, position: Int) {
            itemRvBinding.apply {
                tvName.text = user.fullName
                tvNumber.text = user.number
                profileImage.setImageURI(Uri.parse(user.image))

                myProfile.setOnClickListener {
                    rvClick.itemClicked(user)
                }

                menu.setOnClickListener {
                    rvClick.itemMenuClicked(user,menu,position)
                }
                

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) =
        holder.onBind(List[position], position)


    override fun getItemCount(): Int = List.size


}

interface RvClick {

    fun itemClicked(user: User)
    fun itemMenuClicked(user: User,view: View,position: Int)
    fun callAction(user: User)

}