package com.ceibasoft.user.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceibasoft.R
import com.ceibasoft.user.model.Post
import com.ceibasoft.user.model.User
import com.ceibasoft.utils.Request

class UserDetailActivity : AppCompatActivity() {

    private lateinit var rvPostsList: RecyclerView
    private lateinit var request: Request

    private val adapter = PostListAdapter(ArrayList())
    private var posts = ArrayList<Post>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.post)
        }

        val tvMessage = findViewById<TextView>(R.id.tv_message)
        val pbSpinner = findViewById<ProgressBar>(R.id.pb_spinner)

        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val tveEmail = findViewById<TextView>(R.id.tv_email)
        val tvPhone = findViewById<TextView>(R.id.tv_phone)

        rvPostsList = findViewById(R.id.rv_post_list)
        rvPostsList.adapter = adapter
        rvPostsList.layoutManager = LinearLayoutManager(this)

        val user = intent.getParcelableExtra<User>(User.name)
        tvUsername.text = user?.name
        tveEmail.text = user?.email
        tvPhone.text = user?.phone

        val userId = user!!.id!!

        request = Request.geInstance(this.applicationContext)
        request.getPosts(userId) { posts ->
            this.runOnUiThread {
                this.posts = posts
                adapter.update(posts)
                if (posts.size == 0) {
                    tvMessage.text = getText(R.string.not_data_to_empty)
                } else {
                    tvMessage.text = posts.size.toString() + " " + getText(R.string.post)
                }
                pbSpinner.visibility = View.GONE
            }
        }
    }
}