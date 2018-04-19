package com.example.jujiiz.foodrandom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_REQUEST = 1;
    Bitmap imgBitmap;
    byte[] imgByteArray = null;
    String[] foodType = {"อาหารไทย","อาหารต่างชาติ","อาหารเพื่อสุขภาพ"};

    ImageView ivIMG;
    Button btnIMGPick;
    EditText etName;
    Spinner spType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item,foodType);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(spinnerArrayAdapter);
    }

    private void init(){
        ivIMG = findViewById(R.id.ivIMG);
        btnIMGPick = findViewById(R.id.btnIMGPick);
        etName = findViewById(R.id.etName);
        spType = findViewById(R.id.spType);

        btnIMGPick.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                imgBitmap = BitmapFactory.decodeStream(imageStream);
                ivIMG.setImageBitmap(imgBitmap);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                imgBitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream);
                imgByteArray = outputStream.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnIMGPick) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        }
    }
}
