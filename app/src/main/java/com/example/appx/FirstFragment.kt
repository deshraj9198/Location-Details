package com.example.appx

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appx.databinding.FragmentFirstBinding
import pub.devrel.easypermissions.EasyPermissions


class FirstFragment : Fragment(),EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (EasyPermissions.hasPermissions(requireContext(), *permissions)){
            gotNextScreen()
        }
        binding.btnAllow.setOnClickListener {
            EasyPermissions.requestPermissions(this,"Allow permission",23, *permissions)
        }
    }

    private fun gotNextScreen() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        gotNextScreen()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        AlertDialog.Builder(requireContext()).setTitle("App will not work")
            .setMessage("Please allow the location use for the app to work")
            .setPositiveButton("Retey",DialogInterface.OnClickListener { _, _ ->  })
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}