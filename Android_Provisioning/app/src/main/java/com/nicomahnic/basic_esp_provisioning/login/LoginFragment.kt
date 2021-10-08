package com.nicomahnic.basic_esp_provisioning.login

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.nicomahnic.basic_esp_provisioning.R
import com.nicomahnic.basic_esp_provisioning.core.BaseFragment
import com.nicomahnic.basic_esp_provisioning.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.provider.Settings
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts.*
import com.nicomahnic.basic_esp_provisioning.utils.Utils


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginDataState, LoginViewEffect, LoginViewEvent, LoginVM>(
    R.layout.fragment_login
) {

    override val viewModel: LoginVM by viewModels()
    private lateinit var binding: FragmentLoginBinding

    private val responseLauncher = registerForActivityResult(StartActivityForResult()){
        viewModel.process(LoginViewEvent.ScanWiFi)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.btnScanNetworks.setOnClickListener{
            responseLauncher.launch(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        binding.btnSaveCredentials.setOnClickListener{
            val ssid =  binding.edtUser.text
            val passwd =  binding.edtPasswd.text
            if(ssid != null && passwd != null) {
                viewModel.process(
                    LoginViewEvent.SaveCredentials(
                        ssid.toString(),
                        passwd.toString()
                    )
                )
            }
        }
    }

    override fun renderViewState(viewState: LoginDataState) {
        when(viewState.state){
            is LoginViewState.Scanned -> {
                binding.spinner.let{
                    val adapter = ArrayAdapter(requireContext(),
                        android.R.layout.simple_spinner_item, viewState.data!!)
                    binding.spinner.adapter = adapter
                }
            }
            is LoginViewState.Connected -> {
                Utils.checkForInternet(requireContext())
                Handler().postDelayed({
                    viewModel.process(
                        LoginViewEvent.SetNewDevice("NUEVO DISP")
                    )
                }, 10000)
            }
            else -> {
                Log.d("NM", "LOGIN VIEW STATE -> NOT DEFINED")
            }
        }
    }

    override fun renderViewEffect(viewEffect: LoginViewEffect) {
        Log.i("NM", "renderViewEffect: $viewEffect")
        when(viewEffect){
            is LoginViewEffect.SetOK_GetNetworks -> {
                Log.d("NM", "Fragment ViewEffect -> ScannSuccess")
                binding.tvScannSuccess.visibility = View.VISIBLE
                binding.tvMacAddress.text = viewEffect.macAddress
            }
            is LoginViewEffect.SetFAIL_GetNetworks -> {
                Log.d("NM", "Fragment ViewEffect -> ScanNetworksFail")
                binding.tvScanNetworksFail.visibility = View.VISIBLE
            }
            is LoginViewEffect.SetOK_SaveCredentials -> {
                Log.d("NM", "Fragment ViewEffect -> SaveCredentialsSuccess")
                binding.tvSaveCredentialsSuccess.visibility = View.VISIBLE
                binding.tvMacAddress.text = viewEffect.macAddress
            }
            is LoginViewEffect.SetFAIL_SaveCredentials -> {
                Log.d("NM", "Fragment ViewEffect -> SaveCredentialsFail")
                binding.tvSaveCredentialsFail.visibility = View.VISIBLE
            }
        }
    }

}