package com.dicoding.myrecylervview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvHeroes;

    private ArrayList<Hero> list = new ArrayList<>();

    ///Menjaga Data
    private final String STATE_TITLE = "state_string";
    private final String STATE_LIST = "state_list";
    private final String STATE_MODE = "state_mode";
    private int mode;

    //
    private String title = "Mode List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvHeroes = findViewById(R.id.rv_heroes);
        rvHeroes.setHasFixedSize(true);

        ///////////
        if (savedInstanceState == null) {
            setActionBarTitle(title);
            list.addAll(HeroesData.getListData());
            showRecyclerList();
            mode = R.id.action_list;
        } else {
            title = savedInstanceState.getString(STATE_TITLE);
            ArrayList<Hero> stateList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            int stateMode = savedInstanceState.getInt(STATE_MODE);

            setActionBarTitle(title);
            if (stateList != null) {
                list.addAll(stateList);
            }
            setMode(stateMode);
        }



        //
//        list.addAll(HeroesData.getListData());
//        showRecyclerList();
//
//        setActionBarTitle(title);

    }

    //methode menandakan item yang dipilih
    private void showSelectedHero(Hero hero){
        Toast.makeText(this, "Kamu memilih" + hero.getName(), Toast.LENGTH_SHORT).show();
    }

    private void setActionBarTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    private void showRecyclerList(){
        rvHeroes.setLayoutManager(new LinearLayoutManager(this));
        ListHeroAdapter listHeroAdapter = new ListHeroAdapter(list);
        rvHeroes.setAdapter(listHeroAdapter);

        //interface setOnItemClickCallback//
        listHeroAdapter.setOnItemClickCallback(new ListHeroAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Hero data) {
                showSelectedHero(data);
            }
        });

        }


    //methode show Grid
    private void showRecyclerGrid(){
        rvHeroes.setLayoutManager(new GridLayoutManager(this, 2));
        GridHeroAdapter gridHeroAdapter = new GridHeroAdapter(list);
        rvHeroes.setAdapter(gridHeroAdapter);

        //interface setOnItemClick//
       gridHeroAdapter.setOnItemClickCallback(new GridHeroAdapter.OnItemClickCallback() {
           @Override
           public void onItemClicked(Hero data) {
               showSelectedHero(data);
           }
       });

    }

    //methode show Card view
    private void showRecyclerCardView(){
        rvHeroes.setLayoutManager(new LinearLayoutManager(this));
        CardViewHeroAdapter cardViewHeroAdapter = new CardViewHeroAdapter(list);
        rvHeroes.setAdapter(cardViewHeroAdapter);

    }


    //////Menu/////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override/////////
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE, title);
        outState.putParcelableArrayList(STATE_LIST, list);
        outState.putInt(STATE_MODE, mode);
    }

    private void setMode(int selectedMode) {
        switch (selectedMode){

            case R.id.action_list:
                title = "Mode List";
                showRecyclerList();
                break;

            case R.id.action_grid:
                title = "Mode Grid";
                showRecyclerGrid();
                break;

            case R.id.action_cardview:
                title = "Mode Card View";
                showRecyclerCardView();
                break;
        }

        mode = selectedMode;
        setActionBarTitle(title);
    }
}
