package com.skocur.pkpseatfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.MyOptionsPickerView;
import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.main_header)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/LexendDeca-Regular.ttf"));

        RecyclerView recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CompartmentAdapter(this));

        final TextView carNumber = findViewById(R.id.car_number);

        findViewById(R.id.container_car_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> cars = new ArrayList<>();
                cars.add("1");
                cars.add("2");
                cars.add("3");
                cars.add("4");

                MyOptionsPickerView singlePicker = new MyOptionsPickerView(MainActivity.this);
                singlePicker.setPicker(cars);
                singlePicker.setTitle("Choose car");
                singlePicker.setCyclic(false);
                singlePicker.setSelectOptions(0);
                singlePicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        carNumber.setText(cars.get(options1));
                        carNumber.setVisibility(View.VISIBLE);
                    }
                });

                singlePicker.show();
            }
        });
    }
}
