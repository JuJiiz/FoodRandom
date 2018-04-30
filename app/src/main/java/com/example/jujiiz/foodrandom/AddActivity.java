package com.example.jujiiz.foodrandom;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import java.util.ArrayList;
import java.util.HashMap;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_REQUEST = 1;
    Bitmap imgBitmap;
    byte[] imgByteArray = null;
    String[] foodType = {"อาหารไทย", "อาหารต่างชาติ", "อาหารเพื่อสุขภาพ"};
    ContentValues Val;
    myDBClass db = new myDBClass(this);

    ImageView ivIMG;
    Button btnIMGPick, btnAdd;
    EditText etName;
    Spinner spType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, foodType);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(spinnerArrayAdapter);
    }

    private void init() {
        ivIMG = findViewById(R.id.ivIMG);
        btnIMGPick = findViewById(R.id.btnIMGPick);
        btnAdd = findViewById(R.id.btnAdd);
        etName = findViewById(R.id.etName);
        spType = findViewById(R.id.spType);

        btnIMGPick.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
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

    private void updateData() {
        Val = new ContentValues();
        if (ivIMG.getDrawable() != null) {
            String temp = Base64.encodeToString(imgByteArray, Base64.DEFAULT);
            Val.put("food_img", temp);
        } else {
            Val.put("food_img", "");
        }
        Val.put("food_name", etName.getText().toString());
        Val.put("food_type", spType.getSelectedItem().toString());
        Val.put("status", "Y");
        ArrayList<HashMap<String, String>> CheckList = db.SelectWhereData("foodTable", "food_name", etName.getText().toString());
        if (CheckList != null) {
            if (CheckList.isEmpty()) {
                db.InsertData("foodTable", Val);
            } else {
                db.UpdateData("foodTable", Val, "food_name", etName.getText().toString());
            }
        } else {
            db.InsertData("foodTable", Val);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnIMGPick) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        }

        if (v == btnAdd) {
            if (!etName.getText().toString().equals("")) {
                updateData();
                this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "กรุณากรอกชื่ออาหาร", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
