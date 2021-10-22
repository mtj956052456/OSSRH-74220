package com.mtj.common.permissionx_library

import android.Manifest
import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import com.mtj.common.R
import com.mtj.common.util.getSpannableToColor
import com.permissionx.guolindev.dialog.RationaleDialogFragment
import kotlinx.android.synthetic.main.permission_device_state_layout.*

@TargetApi(30)
class PermissionReadPhoneStateDialog() : RationaleDialogFragment() {

    var mTitle: String = ""
    var mContent: String = ""

    var mPermissions: List<String> = emptyList()

    constructor(title: String, permissions: List<String>) : this() {
        mTitle = title
        mPermissions = permissions
    }

    constructor(title: String, content: String, permissions: List<String>) : this() {
        mTitle = title
        mContent = content
        mPermissions = permissions
    }

    private val groupSet = HashSet<String>()

    private val permissionMap = mapOf(
        Manifest.permission.READ_CALENDAR to Manifest.permission_group.CALENDAR,
        Manifest.permission.WRITE_CALENDAR to Manifest.permission_group.CALENDAR,
        Manifest.permission.READ_CALL_LOG to Manifest.permission_group.CALL_LOG,
        Manifest.permission.WRITE_CALL_LOG to Manifest.permission_group.CALL_LOG,
        Manifest.permission.PROCESS_OUTGOING_CALLS to Manifest.permission_group.CALL_LOG,
        Manifest.permission.CAMERA to Manifest.permission_group.CAMERA,
        Manifest.permission.READ_CONTACTS to Manifest.permission_group.CONTACTS,
        Manifest.permission.WRITE_CONTACTS to Manifest.permission_group.CONTACTS,
        Manifest.permission.GET_ACCOUNTS to Manifest.permission_group.CONTACTS,
        Manifest.permission.ACCESS_FINE_LOCATION to Manifest.permission_group.LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION to Manifest.permission_group.LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION to Manifest.permission_group.LOCATION,
        Manifest.permission.RECORD_AUDIO to Manifest.permission_group.MICROPHONE,
        Manifest.permission.READ_PHONE_STATE to Manifest.permission_group.PHONE,
        Manifest.permission.READ_PHONE_NUMBERS to Manifest.permission_group.PHONE,
        Manifest.permission.CALL_PHONE to Manifest.permission_group.PHONE,
        Manifest.permission.ANSWER_PHONE_CALLS to Manifest.permission_group.PHONE,
        Manifest.permission.ADD_VOICEMAIL to Manifest.permission_group.PHONE,
        Manifest.permission.USE_SIP to Manifest.permission_group.PHONE,
        Manifest.permission.ACCEPT_HANDOVER to Manifest.permission_group.PHONE,
        Manifest.permission.BODY_SENSORS to Manifest.permission_group.SENSORS,
        Manifest.permission.ACTIVITY_RECOGNITION to Manifest.permission_group.ACTIVITY_RECOGNITION,
        Manifest.permission.SEND_SMS to Manifest.permission_group.SMS,
        Manifest.permission.RECEIVE_SMS to Manifest.permission_group.SMS,
        Manifest.permission.READ_SMS to Manifest.permission_group.SMS,
        Manifest.permission.RECEIVE_WAP_PUSH to Manifest.permission_group.SMS,
        Manifest.permission.RECEIVE_MMS to Manifest.permission_group.SMS,
        Manifest.permission.READ_EXTERNAL_STORAGE to Manifest.permission_group.STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE to Manifest.permission_group.STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION to Manifest.permission_group.STORAGE
    )

    var rootView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        rootView = from(context).inflate(R.layout.permission_device_state_layout, null)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val app_name = resources.getString(R.string.app_name)
//        if (mTitle.contains(app_name)) {
//            val index = mTitle.indexOf(app_name)
//            tvTitle.text = getSpannableToColor(mTitle, index, index + app_name.length, resources.getColor(R.color.colorPrimary))
//        } else {
            tvTitle.text = mTitle
//        }
        tvContent.text = mContent

        for (permission in mPermissions) {
            val permissionGroup = permissionMap[permission]
            if (permissionGroup != null && !groupSet.contains(permissionGroup)) {
                groupSet.add(permissionGroup)
            }
        }
    }

    override fun getNegativeButton(): View {
        return rootView!!.findViewById(R.id.tvCancel)
    }

    override fun getPositiveButton(): View {
        return rootView!!.findViewById(R.id.tvOk)
    }

    override fun getPermissionsToRequest(): List<String> {
        return mPermissions
    }

}