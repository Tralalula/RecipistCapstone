package util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tobias.recipist.R;
import com.jmedeisis.draglinearlayout.DragLinearLayout;

/**
 * Created by Tobias on 08-06-2016.
 */
public class Utilities {

    public static final int TOOLBAR_NAVIGATION_ICON_CLICK_ID = 16908332;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    // http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public static EditText addEmptyEditTextView(Context context, ViewGroup parent, String hint, int maxLines, int inputType) {
        EditText editText = new EditText(context);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        editText.setHint(hint);
        editText.setMaxLines(maxLines);
        editText.setInputType(inputType);

        if (parent != null) parent.addView(editText);
        return editText;
    }

    public static void addEditTextViewToViewGroup(Context context, ViewGroup viewGroup, String editTextText) {
        EditText editText = addEmptyEditTextView(context, viewGroup, "Heat oil till flash point.", 1, InputType.TYPE_CLASS_TEXT);
        editText.setText(editTextText);
    }

    public static void addDrawableToTheRightOfEditTextView(EditText editText, Drawable drawable) {
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public static void makeEditTextViewRightDrawableDeletable(final ViewGroup parent, final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width())) {
                        parent.removeView(editText);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
