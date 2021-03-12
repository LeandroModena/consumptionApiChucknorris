package dev.leandromodena.chucknorrisioio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

import dev.leandromodena.chucknorrisioio.datasource.CategoryRemoteDataSource;
import dev.leandromodena.chucknorrisioio.model.CategoryItem;
import dev.leandromodena.chucknorrisioio.presentation.CategoryPresenter;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private GroupAdapter adapter;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
          this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMain);

        adapter = new GroupAdapter();
        adapter.setOnItemClickListener((item, view) -> {
            Intent intent = new Intent(MainActivity.this, JokeActivity.class);
            CategoryItem categoryItem = (CategoryItem) item;


            intent.putExtra(JokeActivity.CATEGORY_KEY, ((CategoryItem) item).getCategoryName());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CategoryRemoteDataSource dataSource = new CategoryRemoteDataSource();
        new CategoryPresenter(this, dataSource).requestAll();


        populateItems();
    }

    public void progressBar(){
        if (progress == null){
            progress = new ProgressDialog(this);
            progress.setMessage(getString(R.string.loading));
            progress.setCancelable(false);
            progress.setIndeterminate(true);
        }
        progress.show();
    }

    public void hideProgressBar(){
        if (progress != null){
            progress.hide();
        }
    }

    public void showFailure(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public void showCategory(List<CategoryItem> categoryItems){
        adapter.addAll(categoryItems);
        adapter.notifyDataSetChanged();

    }

    public void populateItems(){
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =  item.getItemId();

        if (id == R.id.nav_home){

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}