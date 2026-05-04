package com.deepeye.musicpro.ui.routing

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.mediarouter.app.MediaRouteButton
import androidx.recyclerview.widget.RecyclerView
import com.deepeye.musicpro.R
import com.deepeye.musicpro.routing.AlexaEchoDetector
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class DevicePickerBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.bottom_sheet_device_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaRouteButton = view.findViewById<MediaRouteButton>(R.id.mediaRouteButton)
        val deviceRecycler = view.findViewById<RecyclerView>(R.id.deviceRecycler)
        val castSection = view.findViewById<View>(R.id.castSection)
        val btnBluetoothSettings = view.findViewById<MaterialButton>(R.id.btnBluetoothSettings)

        // Setup Cast
        castSection.setOnClickListener {
            mediaRouteButton.performClick()
        }

        // Setup Bluetooth Devices
        val echoDevices = AlexaEchoDetector.getEchoDevices(requireContext())
        deviceRecycler.adapter = DeviceAdapter(echoDevices) { device ->
            // Open Bluetooth settings to connect or show a toast
            startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
        }

        btnBluetoothSettings.setOnClickListener {
            startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
        }
    }

    private class DeviceAdapter(
        private val devices: List<BluetoothDevice>,
        private val onClick: (BluetoothDevice) -> Unit
    ) : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(android.R.id.text1)
            val icon: ImageView = view.findViewById(android.R.id.icon)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_device_row, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val device = devices[position]
            try {
                holder.name.text = device.name ?: "Unknown Device"
                holder.icon.setImageResource(R.drawable.ic_bluetooth)
                holder.itemView.setOnClickListener { onClick(device) }
            } catch (e: SecurityException) {
                holder.name.text = "Permission required"
            }
        }

        override fun getItemCount() = devices.size
    }
}
