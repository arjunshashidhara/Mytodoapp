package com.arjun.mytodoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;




public class TodoActivity extends AppCompatActivity {


        ArrayList<String> items;
        ArrayAdapter<String> itemsAdapter;
        ListView lvItems;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            readItems();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_todo);
            lvItems = (ListView) findViewById(R.id.lvItems);
            items = new ArrayList<>();
            itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            lvItems.setAdapter(itemsAdapter);
            items.add("Go to Gym");
            items.add("Buy grocery");
            items.add("Learn Android Development");

        }

       private void setupListViewListener() {

            lvItems.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {

                            items.remove(pos);
                            itemsAdapter.notifyDataSetChanged();
                            writeItems();
                            return true;

                        }

                    }
            );
        }

        private void readItems() {
            File filesDir = getFilesDir();
            File todoFile = new File(filesDir, "todo.txt");
            try {
                items = new ArrayList<String>(FileUtils.readLines(todoFile));
            } catch (IOException e) {
                items = new ArrayList<String>();
            }
        }

        private void writeItems() {
            File filesDir = getFilesDir();
            File todoFile = new File(filesDir, "todo.txt");
            try {
                FileUtils.writeLines(todoFile, items);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void onSubmit(View view) {

            EditText lvItems = (EditText) findViewById(R.id.tfLabel);
            String itemText = lvItems.getText().toString();
            itemsAdapter.add(itemText);
            lvItems.setText("");
            writeItems();
        }

}
