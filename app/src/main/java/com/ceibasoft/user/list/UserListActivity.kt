package com.ceibasoft.user.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceibasoft.R
import com.ceibasoft.user.model.User
import com.ceibasoft.utils.Request

class UserListActivity : AppCompatActivity() {

    private lateinit var rvUserList: RecyclerView
    private lateinit var etFilter: EditText
    private lateinit var pbSpinner: ProgressBar
    private lateinit var tvMessage: TextView
    private lateinit  var request: Request

    private val adapter: UserListAdapter = UserListAdapter(ArrayList())
    private var users: ArrayList<User> = ArrayList()
    private var filterBy: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        tvMessage = findViewById(R.id.tv_message)
        pbSpinner = findViewById(R.id.pb_spinner)
        rvUserList = findViewById(R.id.rv_user_list)

        rvUserList.adapter = adapter
        rvUserList.layoutManager = LinearLayoutManager(this)

        etFilter = findViewById(R.id.et_filter)
        etFilter.addTextChangedListener { searchByTitle(it.toString()) }
        request = Request.geInstance(this.applicationContext)
        getUserList()

    }

    override fun onBackPressed() {
        return
    }

    private fun getUserList() {
        request.getUserList { users ->
            this.runOnUiThread {
                this.users = users
                adapter.update(users)
                setupUI()
            }
        }
    }

    private fun setupUI(){
        if (users.size == 0) {
            tvMessage.text = getText(R.string.not_data_to_empty)
        } else {
            (users.size.toString() + " " + getText(R.string.users)).also { tvMessage.text = it }
        }
        pbSpinner.visibility = View.GONE
    }

    private fun searchByTitle(text: String) {
        this.filterBy = text
        val usersFiltered =
            this.users.filter { user -> user.name.toString().contains(text) }
        adapter.update(usersFiltered)
    }
}