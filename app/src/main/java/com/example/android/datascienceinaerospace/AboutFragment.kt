package com.example.android.datascienceinaerospace

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class AboutFragment : Fragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "About"
    }
}