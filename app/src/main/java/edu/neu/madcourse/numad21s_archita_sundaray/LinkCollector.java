package edu.neu.madcourse.numad21s_archita_sundaray;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.URI;
import java.util.ArrayList;
import java.util.Random;

public class LinkCollector extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
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
        setContentView(R.layout.activity_link_collector);

        //action listener to the button
        init(savedInstanceState);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialogBox();
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
                Snackbar.make(LinkCollector.this, recyclerView, "Deleted an item", Snackbar.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition();
                itemList.remove(position);
                rviewAdapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void openDialogBox() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String appName, String url) {
        ItemCard itemCard = new ItemCard(R.drawable.empty, appName, url);
        itemList.add(itemCard);
        if (!appName.equals("Default")) {
            Snackbar.make(LinkCollector.this, recyclerView, "Added an item", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(LinkCollector.this, recyclerView, "On receiving invalid URL, default Google is set", Snackbar.LENGTH_SHORT).show();
        }
    }

    //Retaining information on change in orientations
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        //need to generate unique key for each item
        for (int i=0; i<size; i++) {
            outState.putInt(KEY_OF_INSTANCE + i + "0", itemList.get(i).getImageSource());
            outState.putString(KEY_OF_INSTANCE + i + "1", itemList.get(i).getItemName());
            outState.putString(KEY_OF_INSTANCE + i + "2", itemList.get(i).getItemDesc());
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
                    Integer imgId = savedInstanceState.getInt(KEY_OF_INSTANCE + i + "0");
                    String itemName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String itemDesc = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");

                    ItemCard itemCard = new ItemCard(imgId, itemName, itemDesc);
                    itemList.add(itemCard);
                }
            }
        }
        //when the activity is opened for the first time
        else {
            ItemCard item1 = new ItemCard(R.drawable.pic_gmail_01, "Gmail", "https://www.google.com/gmail/about/#");
            ItemCard item2 = new ItemCard(R.drawable.pic_google_01, "Google", "https://www.google.com/");
            ItemCard item3 = new ItemCard(R.drawable.pic_youtube_01, "Youtube", "https://www.youtube.com");
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
                //to open in web browser
                System.out.println(itemList.get(position).getItemDesc());
                Uri webLink = Uri.parse(itemList.get(position).getItemDesc());
                Intent intent = new Intent(Intent.ACTION_VIEW, webLink); //from - to mapping
                startActivity(intent);
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