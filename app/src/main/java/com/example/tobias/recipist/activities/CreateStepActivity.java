package com.example.tobias.recipist.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tobias.recipist.R;

import camera.AddPhoto;
import util.Utilities;

/**
 * Created by Tobias on 19-06-2016.
 */
public class CreateStepActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";

    private Toolbar mToolbar;
    private Menu mMenu;

    private EditText mStep;
    private ImageView mStepPhoto;
    private Button mAddPhoto;
    private FloatingActionButton mSaveStep;
    private AddPhoto mCamera;

    private Bitmap mStepPhotoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_step);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        mStep = (EditText) findViewById(R.id.recipe_step_text);
        mStepPhoto = (ImageView) findViewById(R.id.recipe_step_photo);
        mAddPhoto = (Button) findViewById(R.id.add_photo);
        mSaveStep = (FloatingActionButton) findViewById(R.id.save_step);

        if (mAddPhoto != null) mAddPhoto.setOnClickListener(this);
        if (mSaveStep != null) mSaveStep.setOnClickListener(this);

        mCamera = new AddPhoto(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mStepPhotoBitmap);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mStepPhotoBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
            if (mStepPhotoBitmap != null) mStepPhoto.setImageBitmap(mStepPhotoBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_step_toolbar, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_step:
                return true;
            case Utilities.TOOLBAR_NAVIGATION_ICON_CLICK_ID:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_photo:
                mCamera.selectImage();
                break;
            case R.id.save_step:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AddPhoto.SELECT_FILE_CODE:
                    Bitmap galleryBitmap = mCamera.onSelectFromGalleryResult(data);
                    mStepPhoto.setImageBitmap(galleryBitmap);
                    mStepPhotoBitmap = galleryBitmap;
                    break;
                case AddPhoto.REQUEST_CAMERA_CODE:
                    Bitmap capturedBitmap = mCamera.onCaptureImageResult();
                    mStepPhoto.setImageBitmap(capturedBitmap);
                    mStepPhotoBitmap = capturedBitmap;
            }
        }
    }
}
