package com.gzeinnumer.takephotoandsavegallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //todo 2
    Button takeFoto;

    //todo 10
    public static final int REQUEST_PERM_WRITE_STORAGE = 102;
    public static final int CAPTURE_COLLER_PHOTO = 104;

    //todo 13
    Bitmap resizeImage;

    //todo 18
    ImageView imagePreview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todo 3
        takeFoto = findViewById(R.id.take_foto);

        //todo 4
        takeFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 6
                takeFotoByCamera();
            }
        });

        //todo 19
        imagePreview = findViewById(R.id.image_preview);
    }

    //todo 7
    private void takeFotoByCamera() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
         if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
             ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
         } else {
             //todo 8
             takeCoolerPhotoByCamera();
         }
    }

    //todo 9
    private void takeCoolerPhotoByCamera() {
        //todo 11
        Intent cameraIntent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_COLLER_PHOTO);
    }

    //todo 12

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //todo 14
        Bitmap captureCoolerBitmap = (Bitmap) data.getExtras().get("data");
        //todo 20
        imagePreview.setImageBitmap(captureCoolerBitmap);
        saveImageToGalery(captureCoolerBitmap);

    }

    //todo 15
    private void saveImageToGalery(Bitmap finalBitmap) {
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);


        String root = Environment.getExternalStorageDirectory().toString();
        Toast.makeText(this, root, Toast.LENGTH_SHORT).show();
        File myDir = new File(root + "/gzeinnumer1");
        myDir.mkdirs();

        //todo 16
        String imageName = "Image-"+n+".jpg";
        File file = new File(myDir, imageName);

        if(file.exists()){
            file.delete();
        }
        try{
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            String resizeCoolerImagePath = file.getAbsolutePath();
            out.flush();
            out.close();
//            saveToSharedPreferences("coolerImagePath", resizeCoolerImagePath, MainActivity.this);
//            saveToSharedPreferences("coolerImageName", imageName, MainActivity.this);
//
//            txvCoolerPicPath.setText(imageName);
            //location path
            Toast.makeText(this, "Your Photo Save and Resize Success " + myDir + "/" + imageName, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Exaception Throw : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Exaception Throw : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}












