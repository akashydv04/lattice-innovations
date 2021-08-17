package shyam.gunsariya.latticeinnvoations.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import shyam.gunsariya.latticeinnvoations.R
import shyam.gunsariya.latticeinnvoations.databinding.FragmentRegistrationFormBinding
import shyam.gunsariya.latticeinnvoations.viewModel.UserViewModel
import java.time.LocalDate
import java.time.Period
import java.util.*


class RegistrationForm : Fragment() {

    private var binding: FragmentRegistrationFormBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()
    private var y:Int = 0
    private var m:Int = 0
    private var d:Int = 0
    private val BASE_URL = "https://api.postalpincode.in/pincode/"
    private var requestQueue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentRegistrationFormBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            registerUser = this@RegistrationForm
        }

        requestQueue = Volley.newRequestQueue(requireActivity())

        binding?.pincodeEdt?.addTextChangedListener (object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (count==6){
                    getDataFromPincode()
                }
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun dobPick(){
        val c = Calendar.getInstance()
        d = c.get(Calendar.DATE)
        m = c.get(Calendar.MONTH)
        y = c.get(Calendar.YEAR)

        val datePicker = DatePickerDialog(requireActivity(),
            {
                    _, year, month, dayOfMonth ->
                val months = month+1
                binding?.dobEdt?.setText("$dayOfMonth-$months-$year")
                /**
                 * Calculate age
                 */
                val  age = Period.between(
                    LocalDate.of(year, months, dayOfMonth),
                    LocalDate.now()
                ).years
                val sharedPreference: SharedPreferences = requireActivity().getSharedPreferences("lattice", Context.MODE_PRIVATE)
                val sharedEdit = sharedPreference.edit()
                sharedViewModel.setAge(age.toString())
                sharedEdit.putString("age", age.toString())
                sharedEdit.apply()
            },y,m,d)
        datePicker.show()

    }

    fun registerUser(){

        if (binding?.fullNameEdt?.text.toString() == "" && binding?.genderEdt?.selectedItem.toString() == ""
            && binding?.dobEdt?.text.toString() == "" && binding?.addressLine1Edt?.text.toString() == ""
            && binding?.addressLine2Edt?.text.toString() == "" && binding?.pincodeEdt?.text.toString() == ""
            && binding?.districtEdt?.text.toString() == "" && binding?.stateEdt?.text.toString() == ""){
            Toast.makeText(requireActivity(),"Something missing!!",Toast.LENGTH_LONG).show()
        }else{
            if (binding?.addressLine1Edt?.text.toString().length <3){
                Toast.makeText(requireActivity(),"Address line 1 should have more than 3 characters!!",Toast.LENGTH_LONG).show()
            }
            else {
                sharedViewModel.registerUser(
                    binding?.fullNameEdt?.text.toString(),
                    binding?.genderEdt?.selectedItem.toString(),
                    binding?.dobEdt?.text.toString(),
                    binding?.addressLine1Edt?.text.toString(),
                    binding?.addressLine2Edt?.text.toString(),
                    binding?.pincodeEdt?.text.toString(),
                    binding?.districtEdt?.text.toString(),
                    binding?.stateEdt?.text.toString()
                )
                val sharedPreference: SharedPreferences = requireActivity().getSharedPreferences("lattice", Context.MODE_PRIVATE)
                val sharedEdit = sharedPreference.edit()
                sharedEdit.putString("full_name", binding?.fullNameEdt?.text.toString())
                sharedEdit.putString("gender", binding?.genderEdt?.selectedItem.toString())
                sharedEdit.putString("dob", binding?.dobEdt?.text.toString())
                sharedEdit.putString("address_1", binding?.addressLine1Edt?.text.toString())
                sharedEdit.putString("address_2", binding?.addressLine2Edt?.text.toString())
                sharedEdit.putString("pincode", binding?.pincodeEdt?.text.toString())
                sharedEdit.putString("district", binding?.districtEdt?.text.toString())
                sharedEdit.putString("state", binding?.stateEdt?.text.toString())
                sharedEdit.apply()

                findNavController().navigate(R.id.action_registrationForm_to_weatherInfo,null,
                    NavOptions.Builder().setPopUpTo(R.id.registrationForm, true).build())
            }
        }
    }

    fun getDataFromPincode(){

        val request = StringRequest(Request.Method.GET,BASE_URL+ (binding?.pincodeEdt?.text.toString() ), { response ->
            Log.d("TAG", "getDataFromPincode:main $response")

            try {
                val outerArray = JSONArray(response)
                val outerObj = outerArray.getJSONObject(0)
                if (outerObj.getString("PostOffice")=="null"){
                    Toast.makeText(requireActivity(),"Wrong Pincode Entered", Toast.LENGTH_LONG).show()
                }else {
                    val newArray = outerObj.getJSONArray("PostOffice")
                    val newOuterObj = newArray.getJSONObject(0)
                    val district = newOuterObj.getString("District")
                    val state = newOuterObj.getString("State")
                    binding?.districtEdt?.setText(district)
                    binding?.stateEdt?.setText(state)
                }
            }
            catch (e:JSONException){
                Log.d("TAG", "getDataFromPincode: $e")
            }

        },
            { error ->
                Log.d("TAG", "getDataFromPincode:2 $error")
            })
        requestQueue?.add(request)
    }

}