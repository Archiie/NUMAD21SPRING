package edu.neu.madcourse.numad21s_archita_sundaray;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //essential components needed for Recycler View: RecyclerView, Adapter, LayoutManager
    private ArrayList<ItemCard> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RviewAdapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private FloatingActionButton addButton;

    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //action listener to the "Clicky Clicky" button
        Button clickyButton = findViewById(R.id.clickyButton);
        clickyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClickyClicky.class); //from - to mapping
            startActivity(intent); //this executes the intent
        });

        //action listener to the "Link Collector" button
        Button linkCollectorButton = findViewById(R.id.linkCollectorButton);
        linkCollectorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LinkCollector.class); //from - to mapping
            startActivity(intent); //this executes the intent
        });

        //do I do this separately or can I add it to the above code?
        init(savedInstanceState);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int pos = 0;
                addItem(pos);
            }
        });


        //describing which gesture signifies what action is performed
        //here, swiping left or right signifies deleting the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Snackbar.make(MainActivity.this, recyclerView, "Deleted an item", Snackbar.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition();
                itemList.remove(position);
                rviewAdapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //Retaining information on change in orientations
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        //need to generate unique key for each item
        for (int i=0; i<size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", itemList.get(i).getItemName());
            outState.putString(KEY_OF_INSTANCE + i + "1", itemList.get(i).getItemDesc());
            outState.putBoolean(KEY_OF_INSTANCE + i + "2", itemList.get(i).getStatus());
        }
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState){
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {
        //when the activity is not opened the first time
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (itemList == null || itemList.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);
                //retrieving keys that were stored in the instance
                for (int i = 0; i < size; i++) {
                    String itemName = savedInstanceState.getString(KEY_OF_INSTANCE + i + 0);
                    String itemDesc = savedInstanceState.getString(KEY_OF_INSTANCE + i + 1);
                    boolean isChecked = savedInstanceState.getBoolean(KEY_OF_INSTANCE + i + 2);

                    //making sure that there are no duplicates like this: "XXX(checked)"
                    if (isChecked) {
                        itemName = itemName.substring(0, itemName.lastIndexOf("("));
                    }
                    ItemCard itemCard = new ItemCard(itemName, itemDesc, isChecked);
                    itemList.add(itemCard);
                }
            }
        }
        //when the activity is opened for the first time
        else {
            ItemCard item1 = new ItemCard("Gmail", "Example Description", false);
            ItemCard item2 = new ItemCard("Google", "Example Description", false);
            ItemCard item3 = new ItemCard("Youtube", "Example Description", false);
            itemList.add(item1);
            itemList.add(item2);
            itemList.add(item3);
        }
    }

    private void createRecyclerView() {
        rLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        rviewAdapter = new RviewAdapter(itemList);

        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                itemList.get(position).onItemClick(position);
                rviewAdapter.notifyItemChanged(position);
            }

            @Override
            public void onCheckBoxClick(int position) {
                itemList.get(position).onCheckBoxClick(position);
                rviewAdapter.notifyItemChanged(position);
            }
        };
        rviewAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManager);
    }

    private void addItem(int position) {
        itemList.add(position, new ItemCard("Dummy item", "ITEM id: " + Math.abs(new Random().nextInt(100000)), false));
        Snackbar.make(MainActivity.this, recyclerView, "Add an item", Snackbar.LENGTH_SHORT).show();
        rviewAdapter.notifyItemInserted(position);
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