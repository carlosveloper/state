package com.carlosveloper.state.ViewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class ViewModelPhoto extends ViewModel {

/*


    private MutableLiveData<Boolean> isViewLoading;
    private MutableLiveData<Boolean> intentHome;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;

    public ViewModelPhoto() {
        this.email = new MutableLiveData<>();
        this.password = new MutableLiveData<>();
        this.isViewLoading = new MutableLiveData<>();
        this.intentHome = new MutableLiveData<>();
        RetrofitInit();

    }

    public LiveData<String> getEmail(){
        return  email;
    }
    public LiveData<String> getPassword(){
        return  password;
    }
    public LiveData<Boolean> getIsViewLoading(){
        return  isViewLoading;
    }
    public LiveData<Boolean> getintentHome(){
        return  intentHome;
    }


    public  void  onClickLogin(String email,String pass){
        isViewLoading.setValue(true);
        if(validaEmail(email) &&     validaPassword(pass)  ){
            isViewLoading.setValue(false);
        }else {
            peticionLogin(email,pass);
        }
     //  resultado.setValue(Validation.validarEmail(data));
    }

    private boolean validaEmail(String user){
        HashMap<String, Object> meMap= Validation.validarEmail(user);
        boolean verificar= (boolean) meMap.get("bool");
        if (verificar){
            email.setValue((String) meMap.get("mensaje"));
        }
        return verificar;
    }

    private boolean validaPassword(String pass){
        HashMap<String, Object> meMap= Validation.validarPassword(pass);
        boolean verificar= (boolean) meMap.get("bool");
        if (verificar){
            password.setValue((String) meMap.get("mensaje"));
        }
        return verificar;
    }


    private void RetrofitInit(){
        NextClient = RetrofitClient.getInstance();
        NextService = NextClient.getMiniTwitterService();
    }


*/





}
