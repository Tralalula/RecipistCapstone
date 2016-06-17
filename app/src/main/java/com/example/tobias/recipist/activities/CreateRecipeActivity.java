package com.example.tobias.recipist.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tobias.recipist.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import data.Ingredients;
import util.Utilities;

// For handling camera images I used the following guides:
// http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
// http://blog-emildesign.rhcloud.com/?p=590
// https://developer.android.com/training/camera/photobasics.html
public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CAMERA = 1337;
    private static final int SELECT_FILE = 1338;

    private String mUserChosenTask;

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private ImageView mRecipeImage;
    private Bitmap mRecipeBitmapImage;

    private Button mRecipeEditIngredients;

    private String mCurrentTimeMillis;

    private ArrayList<Ingredients.Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        mCurrentTimeMillis = System.currentTimeMillis() + "";

        mRecipeImage = (ImageView) findViewById(R.id.recipe_image);
        mRecipeEditIngredients = (Button) findViewById(R.id.recipe_edit_ingredients);

        ingredients = new ArrayList<>();
        Ingredients.Ingredient ingredient = new Ingredients.Ingredient("100", "grams", "sugar");
        ingredients.add(ingredient);

        if (mRecipeImage != null) mRecipeImage.setOnClickListener(this);
        if (mRecipeEditIngredients != null) mRecipeEditIngredients.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mRecipeBitmapImage);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipeBitmapImage = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
        if (mRecipeBitmapImage != null) mRecipeImage.setImageBitmap(mRecipeBitmapImage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recipe_image:
                selectImage();
                break;
            case R.id.recipe_edit_ingredients:
                Intent intent = new Intent(this, CreateIngredientsActivity.class);
                intent.putParcelableArrayListExtra("INGREDIENTS", ingredients);
                startActivityForResult(intent, 666);
                break;
        }
    }



    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int item) {
                boolean result = Utilities.checkPermission(CreateRecipeActivity.this);

                if (items[item].equals("Take Photo")) {
                    mUserChosenTask = "Take Photo";
                    if (result) cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    mUserChosenTask = "Choose from Library";
                    if (result) galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + mCurrentTimeMillis + "image.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utilities.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mUserChosenTask.equals("Take Photo")) cameraIntent();
                    else if (mUserChosenTask.equals("Choose from Library")) galleryIntent();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult();
            } else if (requestCode == 666) {
                if (data != null) {
                    ingredients = data.getParcelableArrayListExtra("INGREDIENTS");
                    for (Ingredients.Ingredient ingredient : ingredients) {
                        System.out.println("PANCAKE " + ingredient.getIngredient());
                    }
                }
            }
        }
    }

    public Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;
        if (height > reqHeight) inSampleSize = Math.round((float) height / (float) reqHeight);
        int expectedWidth = width / inSampleSize;
        if (expectedWidth > reqWidth) inSampleSize = Math.round((float) width / (float) reqWidth);

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        mRecipeBitmapImage = BitmapFactory.decodeFile(path, options);
        return mRecipeBitmapImage;
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getApplicationContext().getContentResolver(),
                        data.getData()
                );
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (bitmap != null) {
            mRecipeImage.setImageBitmap(bitmap);
            mRecipeBitmapImage = bitmap;
        }
    }

    private void onCaptureImageResult() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + mCurrentTimeMillis + "image.jpg");
        Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700);
        galleryAddPic(file.getAbsolutePath());
        mRecipeImage.setImageBitmap(bitmap);
        mRecipeImage.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private void galleryAddPic(String path) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File file = new File(path);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}