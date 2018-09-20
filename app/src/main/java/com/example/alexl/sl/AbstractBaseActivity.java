package com.example.alexl.sl;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

/**
 * Родительский класс для активити, подключает тулбар и навигейшендроуер
 */

public abstract class AbstractBaseActivity extends AppCompatActivity {

    private static final int SETTINGS_ID = 1;
    private static final int ABOUT_ID = 2;

    protected void initToolbarAndNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PrimaryDrawerItem itemSettings = new PrimaryDrawerItem().withIdentifier(SETTINGS_ID).withName(R.string.drawer_item_settings).withIcon(GoogleMaterial.Icon.gmd_settings);
        PrimaryDrawerItem itemAbout = new PrimaryDrawerItem().withIdentifier(ABOUT_ID).withName(R.string.drawer_item_about).withIcon(GoogleMaterial.Icon.gmd_info);

        Drawer mDrawer = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(itemSettings, itemAbout)
                .build();

        if (getSupportActionBar() != null) {
            if (beMain()) {
                mDrawer.setToolbar(this, toolbar);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    private boolean beMain() {
        return this.getClass() == MainActivity.class;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return false;
    }

}
