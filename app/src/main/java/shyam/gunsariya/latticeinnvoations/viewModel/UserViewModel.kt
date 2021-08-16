package shyam.gunsariya.latticeinnvoations.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel(){

    private val _mobile_number = MutableLiveData<String>()
    val mobile_number : LiveData<String> = _mobile_number
    private val _full_name = MutableLiveData<String>()
    val full_name : LiveData<String> = _full_name
    private val _gender = MutableLiveData<String>()
    val genDer : LiveData<String> = _gender
    private val _dob = MutableLiveData<String>()
    val dOb : LiveData<String> = _dob
    private val _add_line_one = MutableLiveData<String>()
    val addLineOne : LiveData<String> = _add_line_one
    private val _add_line_two = MutableLiveData<String>()
    val addLineTwo : LiveData<String> = _add_line_two
    private val _pincode = MutableLiveData<String>()
    val pinCode : LiveData<String> = _pincode
    private val _district = MutableLiveData<String>()
    val distRict : LiveData<String> = _district
    private val _state = MutableLiveData<String>()
    val stAte : LiveData<String> = _state
    private val _age = MutableLiveData<String>()
    val age : LiveData<String> = _age

    fun setAge(age: String){
        _age.value = age
    }


    fun setMobileNumber(mobileNumber: String) {
        _mobile_number.value = mobileNumber
    }

    fun registerUser(fullName: String, gender: String, dob: String, add_line_one:String,add_line_two: String, pincode: String, district: String, state: String){
        _full_name.value = fullName
        _gender.value = gender
        _dob.value = dob
        _add_line_one.value = add_line_one
        _add_line_two.value = add_line_two
        _pincode.value = pincode
        _district.value = district
        _state.value = state
    }
}