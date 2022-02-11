package net.basicmodel.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.stepstone.apprating.AppRatingDialog
import com.stepstone.apprating.listener.RatingDialogListener
import kotlinx.android.synthetic.main.layout_f3.*
import net.basicmodel.R


class Fragment3 : Fragment(), RatingDialogListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_f3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rate.setOnClickListener {
            showDialog()
        }
    }

    @SuppressLint("ResourceType")
    private fun showDialog() {
        AppRatingDialog.Builder()
            .setPositiveButtonText("Submit")
            .setNegativeButtonText("Cancel")
            .setNeutralButtonText("Later")
            .setNoteDescriptions(
                listOf(
                    "Very Bad",
                    "Not good",
                    "Quite ok",
                    "Very Good",
                    "Excellent !!!"
                )
            )
            .setDefaultRating(2)
            .setTitle("Rate this application")
            .setDescription("Please select some stars and give your feedback")
            .setCommentInputEnabled(true)
            .setDefaultComment("This app is pretty cool !")
            .setStarColor(R.color.start)
            .setNoteDescriptionTextColor(R.color.black)
            .setTitleTextColor(R.color.black)
            .setDescriptionTextColor(R.color.black)
            .setHint("Please write your comment here ...")
            .setHintTextColor(R.color.black)
            .setCommentTextColor(R.color.white)
            .setCommentBackgroundColor(R.color.start2)
            .setWindowAnimation(R.style.MyDialogFadeAnimation)
            .setCancelable(false)
            .setCanceledOnTouchOutside(true)
            .create(requireActivity())
            .setTargetFragment(this, 1) // only if listener is implemented by fragment
            .show()
    }


    override fun onNegativeButtonClicked() {//cancel
    }

    override fun onNeutralButtonClicked() {//later
    }

    override fun onPositiveButtonClicked(rate: Int, comment: String) {//submit
        Toast.makeText(activity, "Thanks for your rate", Toast.LENGTH_SHORT).show()
    }
}