package shyam.gunsariya.latticeinnvoations.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import shyam.gunsariya.latticeinnvoations.R
import shyam.gunsariya.latticeinnvoations.databinding.FragmentRegistrationFormBinding
import shyam.gunsariya.latticeinnvoations.databinding.FragmentWeatherInfoBinding
import shyam.gunsariya.latticeinnvoations.viewModel.UserViewModel


class WeatherInfo : Fragment() {

    private var binding: FragmentWeatherInfoBinding? = null
    private val sharedModel : UserViewModel by activityViewModels()
    private val BASE_URL = "https://api.postalpincode.in/pincode/"
    private val weatherUrl = "https://api.weatherapi.com/v1/current.json?key=35c9f92ac5bf4df0811144140212307&q="
    private var requestQueue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentWeatherInfoBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedModel
            weatherFragment = this@WeatherInfo
        }

        requestQueue = Volley.newRequestQueue(requireActivity())
    }

    fun getDataUsingPincode(){

        if (binding?.pincodeEdt?.text.toString().length >5){
            val request = StringRequest(
                Request.Method.GET,BASE_URL+ (binding?.pincodeEdt?.text.toString() ), { response ->

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
                            binding?.cityEdt?.setText(district)
                            binding?.stateEdt?.setText(state)
                        }
                    }
                    catch (e: JSONException){
                        Log.d("TAG", "getDataFromPincode: $e")
                    }

                },
                { error ->
                    Log.d("TAG", "getDataFromPincode:2 $error")
                })
            requestQueue?.add(request)
        }
        else
        {
            Toast.makeText(requireActivity(),"Wrong Input Format", Toast.LENGTH_LONG).show()
        }

    }
    //CityName-use-what-user-has-entered-here&aqi=no

    fun getWeatherResult(){
        if (binding?.cityEdt?.text.toString() != ""){
            val request = StringRequest(
                Request.Method.GET,weatherUrl+ (binding?.cityEdt?.text.toString()+"&aqi=no"), { response ->

                    try {
                        Log.d("TAG", "getWeatherResult: "+response)
                        val outerObject = JSONObject(response)
                        val locationObj = outerObject.getJSONObject("location")
                        val currentObj = outerObject.getJSONObject("current")
                        val lat = locationObj.getString("lat")
                        val lon = locationObj.getString("lon")

                        val tempCenti = currentObj.getString("temp_c")
                        val tempFah = currentObj.getString("temp_f")

                        val latitude = "Latitude: $lat"
                        val longitude = "Longitude: $lon"

                        val centi = "Temperature in Centigrade: $tempCenti"
                        val fah = "Temperature in Fahrenheit: $tempFah"

                        binding?.latitudeTxt?.setText(latitude)
                        binding?.longitudeTxt?.setText(longitude)
                        binding?.tempCentigrade?.setText(centi)
                        binding?.tempFahrenheit?.setText(fah)
//                        val outerArray = JSONArray(response)
//                        val outerObj = outerArray.getJSONObject(0)
//                        val newArray = outerObj.getJSONArray("PostOffice")
//                        val newOuterObj = newArray.getJSONObject(0)
//                        val district = newOuterObj.getString("District")
//                        val state = newOuterObj.getString("State")
//                        binding?.cityEdt?.setText(district)
//                        binding?.stateEdt?.setText(state)
                    }
                    catch (e: JSONException){
                        Log.d("TAG", "getDataFromPincode: $e")
                    }

                },
                { error ->
                    Log.d("TAG", "getDataFromPincode:2 $error")
                })
            requestQueue?.add(request)
        }
        else
        {
            Toast.makeText(requireActivity(),"Wrong Input Format", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.my_profile->{
                findNavController().navigate(R.id.action_weatherInfo_to_viewProfile)
                true
            }
            R.id.logout->{
                findNavController().navigate(R.id.action_weatherInfo_to_landingScreen,null,
                    NavOptions.Builder().setPopUpTo(R.id.weatherInfo, true).build())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}