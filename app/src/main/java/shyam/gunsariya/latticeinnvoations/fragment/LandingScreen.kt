package shyam.gunsariya.latticeinnvoations.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import shyam.gunsariya.latticeinnvoations.R
import shyam.gunsariya.latticeinnvoations.databinding.FragmentLandingScreenBinding
import shyam.gunsariya.latticeinnvoations.viewModel.UserViewModel

class LandingScreen : Fragment() {

    //fragment binding data and initialize view model

    private var binding: FragmentLandingScreenBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentLandingScreenBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.landingScreen = this
    }

    //register function

    fun goToRegister(){
        if (binding?.mobileNumEdt?.text.toString() == "" || binding?.mobileNumEdt?.text.toString().length < 10 || binding?.mobileNumEdt?.text.toString().length > 10){
            Log.d("TAG", "Check Your Number Again"+binding?.mobileNumEdt?.text.toString().length)
        }
        else{
            sharedViewModel.setMobileNumber(binding?.mobileNumEdt?.text.toString())
            val sharedPreference: SharedPreferences = requireActivity().getSharedPreferences("lattice", Context.MODE_PRIVATE)
            val sharedEdit = sharedPreference.edit()
            sharedEdit.putString("mobile", binding?.mobileNumEdt?.text.toString())
            sharedEdit.apply()
            findNavController().navigate(R.id.action_landingScreen_to_registrationForm, null,
//                pop up back stack user can't go back
                NavOptions.Builder().setPopUpTo(R.id.landingScreen, true).build())

        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onStart() {
        super.onStart()
        val sharedPreference: SharedPreferences = requireActivity().getSharedPreferences("lattice", Context.MODE_PRIVATE)
        val fullname = sharedPreference.getString("fullname","")

        if (fullname != ""){
            findNavController().navigate(R.id.action_landingScreen_to_weatherInfo, null,
                NavOptions.Builder().setPopUpTo(R.id.landingScreen, true).build())
        }
    }
}