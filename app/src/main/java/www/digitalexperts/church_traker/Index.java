package www.digitalexperts.church_traker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI;
import www.digitalexperts.church_traker.databinding.ActivityIndexBinding;
import www.digitalexperts.church_traker.fragments.Home;
import www.digitalexperts.church_traker.fragments.Live;
import www.digitalexperts.church_traker.fragments.Pdfs;
import www.digitalexperts.church_traker.fragments.Teachings;

public class Index extends AppCompatActivity {
    ActivityIndexBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    final String Tag=this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_index);
        binding = ActivityIndexBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        storageperm();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(binding.drawerLayout)
                .build();
       ExpandableBottomBar bottomBar=findViewById(R.id.expandable_bottom_bar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //bottom nav
        /*NavigationUI.setupWithNavController(bottomNavigationView,
                navController);*/
        ExpandableBottomBarNavigationUI.setupWithNavController(bottomBar,navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
                if (id==R.id.visitationa){
                    String c="https://repentanceandholinessinfo.com/visitations.php";
                    Bundle bundle=new Bundle();
                    bundle.putString("web", c);
                    navController.navigate(R.id.wvinfo,bundle);
                }else if(id==R.id.propheciesa){
                    String c="https://repentanceandholinessinfo.com/prophecies.php";
                    Bundle bundle=new Bundle();
                    bundle.putString("web", c);
                    navController.navigate(R.id.wvinfo,bundle);
                }else if(id==R.id.nav_twitter){
                    String c="https://repentanceandholinessinfo.com/twitter.php";
                    Bundle bundle=new Bundle();
                    bundle.putString("web", c);
                    navController.navigate(R.id.wvinfo,bundle);
                }else if(id==R.id.nav_fb){
                    String c="https://web.facebook.com/jesusiscomingofficial/?_rdc=1&_rdr";
                    Bundle bundle=new Bundle();
                    bundle.putString("web", c);
                    navController.navigate(R.id.wvinfo,bundle);
                }else if(id==R.id.nav_tools){
                    navController.navigate(R.id.contactz);
                }else if(id==R.id.nav_jsl){
                    navController.navigate(R.id.radiostream);
                }else if(id==R.id.nav_share){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Repentance and Holiness android app on playstore\n https://play.google.com/store/apps/details?id=www.digitalexperts.church_traker&hl=en" + BuildConfig.APPLICATION_ID);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem,navController);
                //This is for closing the drawer after acting on it
               binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d(Tag, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();


                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(Tag, msg);
                        /* Toast.makeText(index.this, msg, Toast.LENGTH_SHORT).show();*/
                    }
                });
        FirebaseMessaging.getInstance().subscribeToTopic("Alerts")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successfull subscribed";
                        if (!task.isSuccessful()) {
                            Toast.makeText(Index.this, "not susccessfull", Toast.LENGTH_SHORT).show();
                        }
                        Log.d(Tag, msg);
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=www.digitalexperts.church_traker"));
            String title = "Complete Action Using";
            Intent chooser = Intent.createChooser(intent, title);
            startActivity(chooser);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private  void storageperm(){
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int grant = ContextCompat.checkSelfPermission(Index.this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(Index.this, permission_list, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    storageperm();
                    Toast.makeText(Index.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}