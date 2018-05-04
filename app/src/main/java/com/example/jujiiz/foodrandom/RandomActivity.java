package com.example.jujiiz.foodrandom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class RandomActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnRandom;
    ImageView ivFood;
    TextView tvFood;

    private String TAG = "MYLOG";
    myDBClass db = new myDBClass(this);

    ArrayList<HashMap<String, String>> FoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        init();
    }

    private void init() {
        btnRandom = findViewById(R.id.btnRandom);
        ivFood = findViewById(R.id.ivFood);
        tvFood = findViewById(R.id.tvFood);

        btnRandom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnRandom) {
            ivFood.setImageResource(0);
            FoodList = new ArrayList<HashMap<String, String>>();
            FoodList = db.SelectRandom("foodTable");
            Log.d(TAG, "FoodList: " + FoodList);
            if (FoodList != null) {
                if (!FoodList.isEmpty()) {
                    if (!FoodList.get(0).get("food_img").equals("")){
                        byte[] encodeByte = Base64.decode(FoodList.get(0).get("food_img"), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        ivFood.setImageBitmap(bitmap);
                    }
                    tvFood.setText(FoodList.get(0).get("food_name"));
                } else {
                    Toast.makeText(getApplicationContext(), "ไม่มีข้อมูล", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "ไม่มีข้อมูล", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
