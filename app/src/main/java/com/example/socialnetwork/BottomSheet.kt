package com.example.socialnetwork

import android.net.Uri
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(private val list : ArrayList<Uri>) : BottomSheetDialogFragment() {
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        var bottomSheetDialog : BottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
//        var view : View = LayoutInflater.from(context).inflate(R.layout.activity_demo,null)
//        bottomSheetDialog.setContentView(view)
//
//        var rcv = view.findViewById<RecyclerView>(R.id.listImageView)
//
//        var imageAdapter : ImageAdapter = ImageAdapter()
//        imageAdapter.setFilteredList(list)
//
//        rcv.adapter = imageAdapter
//        rcv.layoutManager = LinearLayoutManager(context)
//
//        return bottomSheetDialog
//    }
}