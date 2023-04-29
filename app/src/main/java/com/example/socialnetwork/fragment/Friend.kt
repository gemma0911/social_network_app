package com.example.socialnetwork.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.R
import com.example.socialnetwork.adapter.FriendAdapter
import com.example.socialnetwork.adapter.ListFriendAdapter
import com.example.socialnetwork.model.FriendViewModel
import com.example.socialnetwork.model.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Friend.newInstance] factory method to
 * create an instance of this fragment.
 */
private lateinit var viewModel : FriendViewModel
private lateinit var friendRecyclerView: RecyclerView
lateinit var adapterFriend: FriendAdapter
class Friend : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }


    companion object {
        fun newInstance(param1: String, param2: String) =
            Friend().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendRecyclerView = view.findViewById(R.id.listFriend)
        friendRecyclerView.layoutManager = LinearLayoutManager(context)
        friendRecyclerView.setHasFixedSize(true)
        adapterFriend = FriendAdapter()
        friendRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this)[FriendViewModel::class.java]
        viewModel.allFriend.observe(viewLifecycleOwner, Observer {
            adapterFriend.updateFriendList(it)
        })
    }
}