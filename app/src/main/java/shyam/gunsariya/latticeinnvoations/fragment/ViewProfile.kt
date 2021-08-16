package shyam.gunsariya.latticeinnvoations.fragment

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = shareViewModel
            myProfile = this@ViewProfile
        }

        binding?.genderAgeTxt?.text = shareViewModel.genDer.value.toString()+", "+shareViewModel.age.value.toString()+" Years"
        binding?.addressTxt?.text = shareViewModel.addLineOne.value.toString()+", "+shareViewModel.addLineTwo.value.toString()
        binding?.statePincodeTxt?.text = shareViewModel.stAte.value.toString()+", "+shareViewModel.pinCode.value.toString()
    }

}