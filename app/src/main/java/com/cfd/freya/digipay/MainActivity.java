package com.cfd.freya.digipay;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cfd.freya.digipay.fragments.AboutUs;
import com.cfd.freya.digipay.fragments.Budget;
import com.cfd.freya.digipay.fragments.MainQR;
import com.cfd.freya.digipay.fragments.TransactionHistory;
import com.cfd.freya.digipay.starter.Login;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity {

    //View Objects
    Fragment fragment = null;
    Drawer result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify
                .with(new FontAwesomeModule());
        //View objects
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withIdentifier(1).withName("Home").withIcon( new IconDrawable(this, FontAwesomeIcons.fa_home));
        PrimaryDrawerItem  logsItem= new PrimaryDrawerItem().withIdentifier(2).withName("Transaction History").withIcon( new IconDrawable(this, FontAwesomeIcons.fa_exchange));
        PrimaryDrawerItem  split= new PrimaryDrawerItem().withIdentifier(3).withName("Split Money").withIcon( new IconDrawable(this, FontAwesomeIcons.fa_money));
        PrimaryDrawerItem  budgetItem = new PrimaryDrawerItem().withIdentifier(4).withName("Budget").withIcon( new IconDrawable(this, FontAwesomeIcons.fa_inr));
        SecondaryDrawerItem aboutItem = new SecondaryDrawerItem().withIdentifier(5).withName("About us").withIcon( new IconDrawable(this, FontAwesomeIcons.fa_user));
        SecondaryDrawerItem outItem = new SecondaryDrawerItem().withIdentifier(6).withName("Sign Out").withIcon( new IconDrawable(this, FontAwesomeIcons.fa_sign_out));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(

                        new ProfileDrawerItem().withName(Login.username.substring(0,Login.username.indexOf('@'))).withEmail(Login.username).withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        //create the drawer and remember the `Drawer` result object

        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        homeItem,
                        logsItem,
                        split,
                        budgetItem,
                        new DividerDrawerItem(),
                        outItem,
                        aboutItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        long choice =drawerItem.getIdentifier();
                        //creating fragment object

                        switch ((int)choice){
                            case 1:{
                                fragment= MainQR.newInstance();
                                break;
                            }
                            case 2:{
                                fragment= TransactionHistory.newInstance();
                                break;
                            }
                            case 3:{

                                fragment= AboutUs.newInstance();
                                break;
                            }
                            case 4:{
                                fragment= Budget.newInstance();
                                break;
                            }
                            case 5:{
                                fragment= AboutUs.newInstance();
                                break;
                            }
                            case 6:{
                                Intent intent=new Intent(MainActivity.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        //replacing the fragment
                        if (fragment != null) {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, fragment);
                            ft.commit();
                            closeDrawer();
                        }
                        // do something with the clicked item :D
                        return true;
                    }
                })
                .build();
        init();
    }
    public void init(){
        fragment=MainQR.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

    }
    public void closeDrawer(){
    result.closeDrawer();
    }


    @Override
    public void onBackPressed() {
        if(result.isDrawerOpen())
            result.closeDrawer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}