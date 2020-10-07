package com.carlosveloper.state;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.carlosveloper.state.Model.Stickers;
import com.carlosveloper.state.Utils.DrawView;
import com.carlosveloper.state.Utils.Global;
import com.carlosveloper.state.Utils.ImageDrawingView;
import com.carlosveloper.state.adapter.VistaStickers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import petrov.kristiyan.colorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {

    ImageView IVpaleteColor,IVclose,IVsave, IVundo,IVsticker;
    LinearLayout mContentLine,mContentImage;
    RelativeLayout RlCirclePencil;
    Drawable mDrawableContornoCirclePencil;
    //private CaptureBitmapView mSig;
    DrawView mSig2;
    RecyclerView recyclerView;
    VistaStickers adapter;
    View layoutStickers;
    int TIPOEDICION=0;//0 linea 1 sticker 2 texto(beta)

    List<Stickers> misStickers=new ArrayList<>();


    ImageDrawingView viewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UI();
        click();
        iniciarlizarSticker();
        iniciar_recycler();
        changeColorBackgroundPencil(Color.argb(255, 0, 0, 0));

    }

    private void UI(){
        IVpaleteColor=findViewById(R.id.paleteColor);
        IVclose=findViewById(R.id.close);
        IVsave=findViewById(R.id.save);
        IVundo=findViewById(R.id.undo);
        IVsticker=findViewById(R.id.sticker);
        RlCirclePencil=findViewById(R.id.CirclePencil);


        layoutStickers=findViewById(R.id.layoutStickers);





        mContentLine = (LinearLayout) findViewById(R.id.signLayoutLine);
        mContentImage = (LinearLayout) findViewById(R.id.signLayoutImage);

        mSig2 = new DrawView(this);

        viewImage=new ImageDrawingView(this,null);


        Glide.with(this).load(Global.imagen_perfil)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mContentLine.setBackground(resource);
                            Drawable drawable = new BitmapDrawable(getResources(), mSig2.getBitmap());
                            mContentImage.setBackground(drawable);
                        }
                    }
                });









        //Permite Dibujar las lineas
        mContentLine.addView(mSig2, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //Permite Dibujar las Imagenes
        mContentImage.addView(viewImage, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);




    }

    private void iniciarlizarSticker(){
        misStickers.add(new Stickers("https://data1.sticker.fan/stickers_storage/2019/08/08/file_1281308.png","Goku"));

        misStickers.add(new Stickers("https://i.pinimg.com/originals/e4/b4/ed/e4b4ed577a787d39a5a363731f1b0fdb.png","Goku SS3"));
        misStickers.add(new Stickers("https://i.pinimg.com/originals/4c/46/ee/4c46ee47e0710a6d928454f68fc4ee17.png","Logo One Piece"));
        misStickers.add(new Stickers("https://i.pinimg.com/originals/d0/b8/c8/d0b8c8f42ae1432a57e9d3c5b83d0efe.png","Naruto"));

        misStickers.add(new Stickers("https://upload.wikimediΩΩΩΩΩΩΩΩΩΩΩΩΩa.org/wikipedia/commons/a/a8/Ski_trail_rating_symbol_black_circle.png","Circulo"));
        misStickers.add(new Stickers("https://static.vecteezy.com/system/resources/previews/001/209/957/non_2x/square-png.png","cuadrado"));
        misStickers.add(new Stickers("https://images.vexels.com/media/users/3/139261/isolated/preview/e81900b82cb04c2f11cff7427def25fa-forma-de-rombo-by-vexels.png","rombo"));
        misStickers.add(new Stickers("https://images.vexels.com/media/users/3/139255/isolated/preview/b4182d055af4a499eb1d10c73f7225d8-forma-de-rect--ngulo-negro-by-vexels.png","rectangulo"));
        misStickers.add(new Stickers("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/0851380d-e2ac-4cf3-b596-1d763ad62cbe/d66flz2-9fb1df46-2ed4-4b83-b21c-e59c4410bb1d.png/v1/fill/w_640,h_480,strp/triangulo_png__by_ediciones_kata_d66flz2-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3siaGVpZ2h0IjoiPD00ODAiLCJwYXRoIjoiXC9mXC8wODUxMzgwZC1lMmFjLTRjZjMtYjU5Ni0xZDc2M2FkNjJjYmVcL2Q2NmZsejItOWZiMWRmNDYtMmVkNC00YjgzLWIyMWMtZTU5YzQ0MTBiYjFkLnBuZyIsIndpZHRoIjoiPD02NDAifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.UZdTaQT7wOsIgie6H_5yDWR5rOCpMom_rrtvZ9_btQo","triangulo"));
        misStickers.add(new Stickers("https://images.vexels.com/media/users/3/139197/isolated/preview/5aecde8d1d89d062d6c2464aaa1213d8-forma-de-hex--gono-by-vexels.png","hexágono"));
        misStickers.add(new Stickers("https://image.flaticon.com/icons/png/512/649/649765.png","trapecio"));
        misStickers.add(new Stickers("https://images.vexels.com/media/users/3/139249/isolated/preview/c157211586534bdb8afe7caeb407ba15-forma-del-pent--gono-by-vexels.png","pentagono"));


        int position=0;
        for(Stickers p :misStickers){
            TransformBitmap(p.getUrlImage(),position);
            position++;
        }



    }


    private void iniciar_recycler(){
        recyclerView=findViewById(R.id.Recycler_stickers);
                adapter=new VistaStickers(misStickers, new VistaStickers.OnItemClick() {
                    @Override
                    public void onItemClickImagen(int position) {
                        Log.e("click sti",""+position);
                        viewImage.selectBitmap(misStickers.get(position).getBitmapImage());
                        viewImage.aumentarImagen();
                        closeLayoutMisStickers();
                    }
                });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }


    private void click(){

        RlCirclePencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //limpiar();
                layoutStickers.setVisibility(View.GONE);
                paletaColor();
                //finish();
            }
        });


        IVclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TIPOEDICION==0){
                    finish();
                }else{
                    //Esconde los stickers
                   closeLayoutMisStickers();

                }
            }
        });

        IVsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarImagen();
            }
        });

        IVundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  mSig.Undo();
                mSig2.onClickUndo();
            }
        });



        IVsticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TIPOEDICION=1;
                layoutStickers.setVisibility(View.VISIBLE);

                IVclose.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back));


                //  mSig.Undo();



                Drawable drawable = new BitmapDrawable(getResources(), mSig2.getBitmap());
                mContentImage.setBackground(drawable);
                mContentLine.setVisibility(View.INVISIBLE);
                mContentImage.setVisibility(View.VISIBLE);
                layoutStickers.setVisibility(View.VISIBLE);

            }
        });

    }



    void guardarImagen(){
        Bitmap signature = mSig2.getBitmap();
        Log.e("sig",signature.toString());
        if(isStoragePermissionGranted()){

            Log.e("permiso","guardo imagen");
            //   SaveImage(signature);
            storeImage(signature,"prueba_firma");

        }else{

            Log.e("permiso","no guardo imagen");

        }

    }


    private void paletaColor(){
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.setTitle("Elige el color"); // set the title of the dialog
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
              //  mSig.CambiarColors(color);
                Log.e("color elegido",""+color);
                Drawable drawable = new BitmapDrawable(getResources(), viewImage.getBitmap());
                mContentLine.setBackground(drawable);
                mContentLine.setVisibility(View.VISIBLE);
                mContentImage.setVisibility(View.INVISIBLE);
                mSig2.changeColor(color);
                changeColorBackgroundPencil(color);
            }

            @Override
            public void onCancel(){
                // put code
            }
        });

    }


    //////
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("TAG","Permission is granted");
                return true;
            } else {

                Log.e("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.e("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case 2: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    Bitmap signature = mSig2.getBitmap();
                    storeImage(signature,"");

                    storeImage(signature,"prueba_firma");
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }




    public void storeImage(Bitmap imageData, String filename) {
        // get path to external storage (SD card)
        File sdIconStorageDir = null;
        sdIconStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                "/Firma"
        );
        // create storage directories, if they don't exist
        if (!sdIconStorageDir.exists()) {
            sdIconStorageDir.mkdirs();
        }
        try {
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = "Image-"+ n +".jpg";


            //  String filePath = sdIconStorageDir.toString() + File.separator + filename+".jpg";
            String filePath = sdIconStorageDir.toString() + File.separator + fname;

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            //Toast.makeText(m_cont, "Image Saved at----" + filePath, Toast.LENGTH_LONG).show();
            // choose another format if PNG doesn't suit you
            imageData.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
            Log.e("TAG", "imagen guardada");
            Toast.makeText(this,"imagen guardada",Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Log.e("TAG", "Error saving image file: " + e.getMessage());
        } catch (IOException e) {
            Log.e("TAG", "Error saving image file: " + e.getMessage());
        }
    }



    void changeColorBackgroundPencil(int color){
        mDrawableContornoCirclePencil = ContextCompat.getDrawable(this, R.drawable.round_widget);
        mDrawableContornoCirclePencil.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        RlCirclePencil.setBackground(mDrawableContornoCirclePencil);

    }

    void closeLayoutMisStickers(){
        layoutStickers.setVisibility(View.GONE);
        TIPOEDICION=0;
        IVclose.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.close));
    }


    void TransformBitmap(String urlImage,int positon){
        Glide.with(this)
                .asBitmap()
                .load(urlImage)
                .into(new CustomTarget<Bitmap>(250,250) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        misStickers.get(positon).setBitmapImage(resource);
                   //     Drawable d = new BitmapDrawable(getResources(), bitmap);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }


}