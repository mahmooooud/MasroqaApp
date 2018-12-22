package com.example.mahmoudahmed.masroqaapp;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    ImageView imageView_Masroqa;
    DatabaseReference Xreference;
    String Image;
    TextView txName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Xreference = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mAuth = FirebaseAuth.getInstance();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        imageView_Masroqa = (ImageView) header.findViewById(R.id.imageView_Masroqa);
        txName = (TextView) header.findViewById(R.id.txName);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, new Post());
        fragmentTransaction.commit();

        Xreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Image = dataSnapshot.child("imgs").getValue().toString();
                String str_Name = dataSnapshot.child("Name").getValue().toString();
                txName.setText(str_Name);

                if (!Image.equals("") | !Image.isEmpty()) {

                    Picasso.with(Home.this).load(Image).into(imageView_Masroqa);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_users:
//                android.app.FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_place, new Users());
//                fragmentTransaction.commit();
//                drawerLayout.closeDrawers();
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_chat:
                android.app.FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_place, new Post());
                ft.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_edit_profile:
                android.app.FragmentManager FM = getFragmentManager();
                FragmentTransaction FT = FM.beginTransaction();
                FT.replace(R.id.fragment_place, new EditProfile());
                FT.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_search:
                android.app.FragmentManager FMS = getFragmentManager();
                FragmentTransaction FTS = FMS.beginTransaction();
                FTS.replace(R.id.fragment_place, new SearchItem());
                FTS.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_sign_out:
                Xreference.child("State").setValue("0");
                mAuth.signOut();
                finish();
                drawerLayout.closeDrawers();
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle("Exit")
                .setMessage("Do you want to Exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })
                .show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Xreference.child("State").setValue("1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Xreference.child("State").setValue("0");
    }

}
