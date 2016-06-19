package camera;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Base64;

import com.example.tobias.recipist.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import util.Utilities;

/**
 * Created by Tobias on 19-06-2016.
 */
public class AddPhoto {
    public static final int REQUEST_CAMERA_CODE = 1337;
    public static final int SELECT_FILE_CODE = 1338;

    private String mUserChosenTask;
    private String mCurrentTimeMillis;
    private Context mContext;

    public AddPhoto(Context context) {
        mCurrentTimeMillis = System.currentTimeMillis() + "";
        mContext = context;
    }

    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int item) {
                boolean result = Utilities.checkPermission(mContext);

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
        ((Activity) mContext).startActivityForResult(intent, REQUEST_CAMERA_CODE);

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) mContext).startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_CODE);
    }

    public Bitmap onSelectFromGalleryResult(Intent data) {
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        mContext.getContentResolver(),
                        data.getData()
                );
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return bitmap;
    }

    public Bitmap onCaptureImageResult() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + mCurrentTimeMillis + "image.jpg");
        Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700);
        galleryAddPic(file.getAbsolutePath());
        return bitmap;
    }

    private Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
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
        return BitmapFactory.decodeFile(path, options);
    }

    private void galleryAddPic(String path) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File file = new File(path);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        mContext.sendBroadcast(mediaScanIntent);
    }

    public void onRequestPermissionsResult(@NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (mUserChosenTask.equals("Take Photo")) cameraIntent();
            else if (mUserChosenTask.equals("Choose from Library")) galleryIntent();
        }
    }

    // http://stackoverflow.com/questions/26292969/can-i-store-image-files-in-firebase-using-java-api
    public String decodeDrawableToBase64String(int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), drawable);
        ByteArrayOutputStream byteArrOpStrm = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrOpStrm);
        bitmap.recycle();
        byte[] bytes = byteArrOpStrm.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // http://stackoverflow.com/questions/26292969/can-i-store-image-files-in-firebase-using-java-api
    public String decodeBitmapToBase64String(Bitmap bitmap) {
        ByteArrayOutputStream byteArrOpStrm = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrOpStrm);
        bitmap.recycle();
        byte[] bytes = byteArrOpStrm.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}