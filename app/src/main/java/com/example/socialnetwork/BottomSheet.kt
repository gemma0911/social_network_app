package com.example.socialnetwork

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.adapter.ImageAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(private val list : ArrayList<Uri>) : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var bottomSheetDialog : BottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        var view : View = LayoutInflater.from(context).inflate(R.layout.activity_demo,null)
        bottomSheetDialog.setContentView(view)

        var rcv = view.findViewById<RecyclerView>(R.id.listImageView)

        var imageAdapter : ImageAdapter = ImageAdapter()
        imageAdapter.setFilteredList(list)

        rcv.adapter = imageAdapter
        rcv.layoutManager = LinearLayoutManager(context)

        return bottomSheetDialog
    }

}