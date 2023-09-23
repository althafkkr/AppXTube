package com.example.appxtube;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.appxtube.dialog.VideoOptionsDialog;
import com.example.appxtube.event.EventGoHome;
import com.example.appxtube.event.EventVideoOptions;
import com.example.appxtube.fragment.DetailedFragment;
import com.example.appxtube.fragment.HomeFragment;
import com.example.appxtube.fragment.LibraryFragment;
import com.example.appxtube.listener.OnBackPressedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.frame_layout);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        HomeFragment homeFragment = new HomeFragment();
        currentFragment = new HomeFragment();
        replaceFragment(homeFragment,false);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_home) {
                    HomeFragment homeFragment = new HomeFragment();
                    currentFragment = new HomeFragment();
                    replaceFragment(homeFragment,false);
                    return true;
                } else if (item.getItemId() == R.id.nav_library) {
                    LibraryFragment libraryFragment = new LibraryFragment();
                    currentFragment = new LibraryFragment();
                    replaceFragment(libraryFragment,false);
                    return true;
                }
                return false;
            }
        });
    }



    private void replaceFragment(Fragment fragment,boolean addToBackStack) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            // Add this transaction to the back stack
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(R.id.frame_layout,fragment,fragment
                .getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.notification) {
            Toast.makeText(this, "Notification clicked", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.search) {
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.account) {
            Toast.makeText(this, "Account clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void eventVideoOption(EventVideoOptions event) {
        if (event.action.equalsIgnoreCase("play")) {
            DetailedFragment detailedFragment = new DetailedFragment(event.item);
            currentFragment = new DetailedFragment();
            replaceFragment(detailedFragment,true);
        } else {
//            HomeFragment homeFragment = new HomeFragment();
//            homeFragment.createVideoOptionDialog(event.item);
            VideoOptionsDialog bottomDialog = new VideoOptionsDialog();
            bottomDialog.show(getSupportFragmentManager(), bottomDialog.getTag());
        }
    }

    @Subscribe
    public void eventGoHome(EventGoHome event) {
        Fragment fragment = getCurrentFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragment instanceof HomeFragment) {
            finish();
        } else if (fragment instanceof  DetailedFragment) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else {
                replaceFragment(new HomeFragment(),false);
            }
        }else {
            replaceFragment(new HomeFragment(),false);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getCurrentFragment();

        if (currentFragment instanceof DetailedFragment) {
            ((DetailedFragment) currentFragment).onBackPressed();
        } else if (currentFragment instanceof LibraryFragment) {
            ((LibraryFragment)currentFragment).onBackPressed();
        }else {
            super.onBackPressed();
        }

    }

    @Nullable
    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.frame_layout);
    }
}