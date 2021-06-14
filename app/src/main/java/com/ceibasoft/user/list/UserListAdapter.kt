package com.ceibasoft.user.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ceibasoft.R
import com.ceibasoft.user.detail.UserDetailActivity
import com.ceibasoft.user.model.User

class UserListAdapter(private var users: List<User>): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    fun update(users: List<User>){
        this.users = users
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvUsername: TextView = itemView.findViewById<TextView>(R.id.tv_username)
        val tvEmail: TextView = itemView.findViewById<TextView>(R.id.tv_email)
        val tvPhone: TextView = itemView.findViewById<TextView>(R.id.tv_phone)
        val btnSeePosts: Button = itemView.findViewById<Button>(R.id.btn_see_posts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val vUserCard = inflater.inflate(R.layout.layout_card_user, parent, false)
        return ViewHolder(vUserCard)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User = users[position]
        holder.tvUsername.text = user.name
        holder.tvPhone.text = user.phone
        holder.tvEmail.text = user.email
        holder.btnSeePosts.setOnClickListener{
            val i = Intent(it.context, UserDetailActivity::class.java).apply {
               putExtra(User.name, user)
            }
            it.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

}