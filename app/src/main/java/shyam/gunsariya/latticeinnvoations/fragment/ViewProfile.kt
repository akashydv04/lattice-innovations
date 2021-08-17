package shyam.gunsariya.latticeinnvoations.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import shyam.gunsariya.latticeinnvoations.databinding.FragmentViewProfileBinding
import shyam.gunsariya.latticeinnvoations.viewModel.UserViewModel


class ViewProfile : Fragment() {

    private var binding: FragmentViewProfileBinding? = null
    private val shareViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentViewProfileBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = shareViewModel
            myProfile = this@ViewProfile
        }

        val sharedPreference: SharedPreferences = requireActivity().getSharedPreferences("lattice", Context.MODE_PRIVATE)
        val fullName = sharedPreference.getString("fullname","")
        val line2 = sharedPreference.getString("gender","")+", "+sharedPreference.getString("age","")+" Years"
        val line3 = sharedPreference.getString("address_1","")+", "+sharedPreference.getString("address_2","")
        val line4 = sharedPreference.getString("state","")+", "+sharedPreference.getString("pincode","")

        binding?.fullNameTxt?.text = fullName
        binding?.genderAgeTxt?.text = line2
        binding?.addressTxt?.text = line3
        binding?.statePincodeTxt?.text = line4
    }

}