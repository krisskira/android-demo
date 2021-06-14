package com.ceibasoft.user.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ceibasoft.R
import com.ceibasoft.user.model.Post

class PostListAdapter(private var posts: ArrayList<Post>) :
    RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    fun update(posts: ArrayList<Post>) {
        this.posts = posts
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPostBody: TextView = itemView.findViewById(R.id.tv_post_body)
        val tvPostTitle: TextView = itemView.findViewById(R.id.tv_post_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val vPostCard = inflater.inflate(R.layout.layout_card_post, parent, false)
        return ViewHolder(vPostCard)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post: Post = posts[position]
        holder.tvPostBody.text = post.body
        holder.tvPostTitle.text = post.title
    }

    override fun getItemCount(): Int {
        return posts.size
    }

}