package com.example.basicsanitycheck

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.progress_dialog.view.*

class MyDialog : DialogFragment() {

    private lateinit var myDialog: Dialog

    companion object {

        const val TAG_DEVICE_ROOT_CHECK = "device root check"
        const val TAG_DEVICE_CONNECTIVITY = "device connectivity"
        const val TAG_CUSTOM = "custom"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)

        when (tag) {

            TAG_DEVICE_ROOT_CHECK -> myDialog = showDeviceRootCheckDialog()

            TAG_DEVICE_CONNECTIVITY -> myDialog = showDeviceConnectivityDialog()

            else -> myDialog = showCustomDialog()
        }

        return myDialog
    }


    private fun showDeviceRootCheckDialog(): Dialog {

        val dialogBuilder = AlertDialog.Builder(activity)
                .setIcon(R.drawable.ic_warning_black)
                .setTitle("Rooted Device!!!")
                .setMessage("Your device is rooted!")
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                        Log.i("MasterBlaster", "User confirmed")
                        dismiss()
                        Toast.makeText(activity, "User confirmed", Toast.LENGTH_SHORT).show()
                    }
                }).setNegativeButton("No", object : DialogInterface.OnClickListener {

                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.i("MasterBlaster", "User denying the rooted device")
                        dismiss()
                        Toast.makeText(activity, "User denying the rooted device", Toast.LENGTH_SHORT).show()
                    }
                })

        val alertDialog = dialogBuilder.create()

        return alertDialog
    }

    private fun showDeviceConnectivityDialog(): Dialog {

        val dialogBuilder = AlertDialog.Builder(activity)
                .setIcon(R.drawable.ic_signal_cellular)
                .setTitle("No Network!!!")
                .setMessage("Your device is offline!")
                .setPositiveButton("Ok") { dialog, which ->
                    Log.i("MasterBlaster", "User confirmed the offline status")
                    dismiss()
                    Toast.makeText(activity, "User confirmed the offline status", Toast.LENGTH_SHORT).show()
                }
                .setCancelable(true)

        val alertDialog = dialogBuilder.create()

        return alertDialog

    }


    private fun showCustomDialog(): Dialog {

        val layoutInflater = activity?.layoutInflater
        val view = layoutInflater?.inflate(R.layout.progress_dialog, null, false)

        val dialogBuilder = AlertDialog.Builder(activity)
                .setView(view)
                .setCancelable(false)

        view?.pop_up_text?.text = "This is custom dialog box"
        view?.pop_up_close?.setOnClickListener {
            dismiss()
            Toast.makeText(activity, "cancle the dialog", Toast.LENGTH_SHORT).show()
        }

        val alertDialog = dialogBuilder.create()

        alertDialog.setOnKeyListener(object : DialogInterface.OnKeyListener {

            override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {

                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    dialog?.dismiss()
                }

                return true
            }
        })

        return alertDialog
    }

}