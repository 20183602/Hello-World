package com.example.ji_zhang_ben;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Costbean> mCostBeanList;
    private CostListAdapter mAdapter;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseHelper = new DatabaseHelper(this);
        mCostBeanList = new ArrayList<>();
        ListView costList = findViewById(R.id.start);
        initCostData();
        mAdapter = new CostListAdapter(this,mCostBeanList);
        costList.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view1 = inflater.inflate(R.layout.add_cost_date,null);
                final EditText tile = view1.findViewById(R.id.add_cost_tile);
                final EditText money = view1.findViewById(R.id.add_cost_money);
                final DatePicker date=view1.findViewById(R.id.add_cost_date);
                builder.setView(view1);
                builder.setTitle("Add account");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Costbean costbean = new Costbean();
                        costbean.cost_tile = tile.getText().toString();
                        costbean.cost_money = "￥"+money.getText().toString();
                        costbean.cost_date = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();
                        mDatabaseHelper.insert(costbean);
                        mCostBeanList.add(costbean);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
            }
        });
    }

    private void initCostData() {


        Cursor cursor = mDatabaseHelper.findAll();
        if (cursor != null){
            while (cursor.moveToNext()){
                Costbean costbean = new Costbean();
                costbean.cost_tile = cursor.getString(cursor.getColumnIndex("cost_tile"));
                costbean.cost_date = cursor.getString(cursor.getColumnIndex("cost_date"));
                costbean.cost_money = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costbean);
            }
            cursor.close();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
