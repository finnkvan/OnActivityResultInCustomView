package com.pivincii.blogging

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.pivincii.blogging.activityresult.ActivityResultObserver
import com.pivincii.blogging.activityresult.ActivityResultObservable
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Build


private const val PICKFILE_REQUEST_CODE = 1001

/**
 * TODO: document your custom view class.
 */
class CustomView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr), ActivityResultObserver {

    private val pathFileTV by lazy { findViewById<TextView>(R.id.text_view) }
    private val pickFileBtn by lazy { findViewById<Button>(R.id.pick_file) }

    init {
        inflate(context, R.layout.sample_custom_view, this)
        pickFileBtn.setOnClickListener { pickFile() }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (context as ActivityResultObservable).addObserver(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        (context as ActivityResultObservable).removeObserver(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.dataString?.run { pathFileTV.text = this }
        }
    }

    private fun pickFile() {
        if (isStoragePermissionGranted()) {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "*/*"
            }
            (context as Activity).startActivityForResult(intent, PICKFILE_REQUEST_CODE)
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(READ_EXTERNAL_STORAGE), 1)
                false
            }
        } else {
            true
        }
    }
}
