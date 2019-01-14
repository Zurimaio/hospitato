package com.ma.se.hospitato;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MedicalView extends AppCompatActivity {

    Personal_information personal_info;
    medical_information med_info;
    FloatingActionButton fab;
    Profile value;
    Toolbar toolbar;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_view);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPagercontainer);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        fab = (FloatingActionButton) findViewById(R.id.editButton);
        //fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicalView.this, EditView.class);
                intent.putExtra("FromMain", 2); //2 - For obtaining hospital positions
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logoutButton){
            startActivity(new Intent(this, Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            );
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    personal_info = new Personal_information();
                    //fab.setVisibility(View.VISIBLE);
                    return personal_info;
                case 1:
                    med_info = new medical_information();
                    //fab.setVisibility(View.INVISIBLE);
                    return  med_info;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "PersonalInfo";
                case 1:
                    return "MedicalInfo";

            }
            return null;
        }

        public void EditProfile(View view) {
            Log.d("imageButton","The user want to edit it's profile");

            Toast toast = Toast.makeText(getApplicationContext(),"It will be redirect to the Edit View", Toast.LENGTH_SHORT);
            toast.show();

            Intent toEdit = new Intent(MedicalView.this,EditView.class);
            toEdit.putExtra("Name",value.getName());
            toEdit.putExtra("Surname",value.getSurname());
            toEdit.putExtra("Nascita",value.getNascita());
            toEdit.putExtra("Email",value.getEmail());

            startActivity(toEdit);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logout = menu.findItem(R.id.logoutButton);
        MenuItem profile = menu.findItem(R.id.profileButton);
        MenuItem register = menu.findItem(R.id.registerButton);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            logout.setVisible(true);
            profile.setVisible(false);
            register.setVisible(false);
        }

        return  true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
        Menu menu = toolbar.getMenu();
        MenuItem register = menu.findItem(R.id.registerButton);
        MenuItem login =  menu.findItem(R.id.loginButton);
        MenuItem profile = menu.findItem(R.id.profileButton);
        register.setVisible(true);
        login.setVisible(true);
        profile.setVisible(false);
        */
    }
}