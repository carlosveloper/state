package com.carlosveloper.state;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.carlosveloper.state.Utils.Global;
import com.theartofdev.edmodo.cropper.CropImage;

public class State extends AppCompatActivity {
    ImageView IVimage;
    Uri imagen_perfil;
    Button Btcontinuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        UI();
        click();
    }

    private void UI(){

        IVimage = findViewById(R.id.image);
        Btcontinuar=findViewById(R.id.continuar);
    }


    private void click(){

        Btcontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarEdicionImagen();
            }
        });


        IVimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funcion_cortar();
            }
        });

    }

    public void iniciarEdicionImagen(){
        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    public void funcion_cortar() {
        CropImage.activity()
               // .setAspectRatio(4, 4)
                .setFixAspectRatio(true)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //("Foto", "Entre a ver foto");
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                imagen_perfil = result.getUri();
                //("obtuve imagen",""+imagen_perfil);


                llenar_subida();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                //("error imagen",result.getError().toString());
            }


        }
    }


    private void llenar_subida() {
        Global.imagen_perfil=imagen_perfil;
        Glide.with(this).load(imagen_perfil).apply(RequestOptions.circleCropTransform()).into(IVimage);

    }
}