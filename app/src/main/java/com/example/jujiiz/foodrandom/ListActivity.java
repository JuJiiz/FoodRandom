package com.example.jujiiz.foodrandom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAddList;
    private String TAG = "MYLOG";

    Intent intent;

    myDBClass db = new myDBClass(this);

    ArrayList<HashMap<String, String>> FoodList;
    String[] strName, strType;
    Bitmap[] bmIMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FoodList = new ArrayList<HashMap<String, String>>();
        FoodList = db.SelectData("foodTable");
        Log.d(TAG, "FoodList: " + FoodList);
        if (FoodList != null) {
            if (!FoodList.isEmpty()) {
                strName = new String[FoodList.size()];
                strType = new String[FoodList.size()];
                bmIMG = new Bitmap[FoodList.size()];

                for (int i = 0; i < FoodList.size(); i++) {
                    if (!FoodList.get(i).get("food_img").equals("")){
                        byte[] encodeByte = Base64.decode(FoodList.get(i).get("food_img"), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        bmIMG[i] = bitmap;
                    }else{
                        bmIMG[i] = null;
                    }

                    strName[i] = FoodList.get(i).get("food_name");
                    strType[i] = FoodList.get(i).get("food_type");
                }

                CustomAdapter adapter = new CustomAdapter(getApplicationContext(), bmIMG, strName, strType);

                ListView listView = findViewById(R.id.lvFoodList);
                listView.setAdapter(adapter);
            }
        }
    }

    private void init() {
        btnAddList = findViewById(R.id.btnAddList);

        btnAddList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnAddList) {
            intent = new Intent(getApplicationContext(), AddActivity.class);
            this.startActivity(intent);
        }
    }
}
