package com.example.jujiiz.foodrandom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRandomPage,btnListPage;

    Intent intent;
    myDBClass db = new myDBClass(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.getWritableDatabase();

        init();
    }

    private void init(){
        btnRandomPage = findViewById(R.id.btnRandomPage);
        btnListPage = findViewById(R.id.btnListPage);

        btnRandomPage.setOnClickListener(this);
        btnListPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnRandomPage){
            intent = new Intent(getApplicationContext(),RandomActivity.class);
            this.startActivity(intent);
        }
        if (v == btnListPage){
            intent = new Intent(getApplicationContext(),ListActivity.class);
            this.startActivity(intent);
        }
    }
}
