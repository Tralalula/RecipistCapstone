package com.example.tobias.recipist.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tobias.recipist.R;
import com.jmedeisis.draglinearlayout.DragLinearLayout;

import java.util.ArrayList;

import data.Steps;
import util.Utilities;

/**
 * Created by Tobias on 19-06-2016.
 */
public class CreateStepActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Steps.Step> oldSteps;
    private ArrayList<Steps.Step> newSteps;

    private Toolbar mToolbar;
    private Menu mMenu;

    private Button mButtonAddStep;
    private DragLinearLayout mDragLinearLayoutSteps;
    private FloatingActionButton mFabSaveStep;

    private boolean mSortStepsMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ingredients);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        mSortStepsMode = true;

        mButtonAddStep = (Button) findViewById(R.id.add_ingredient);
        mFabSaveStep = (FloatingActionButton) findViewById(R.id.save_ingredients);
        mDragLinearLayoutSteps = (DragLinearLayout) findViewById(R.id.lin);

        if (mButtonAddStep != null) mButtonAddStep.setOnClickListener(this);
        if (mFabSaveStep != null) mFabSaveStep.setOnClickListener(this);

        if (getIntent() != null) {
            oldSteps = getIntent().getParcelableArrayListExtra("STEPS");
            newSteps = oldSteps;
            if (!oldSteps.isEmpty()) {
                for (Steps.Step step : newSteps) {
                    Utilities.addEditTextViewToViewGroup(this, mDragLinearLayoutSteps, step.getMethod());
                    handleStepsMode(true);
                }
            } else {
                Utilities.addEmptyEditTextView(this, mDragLinearLayoutSteps, "Heat oil till flash point.", 1, InputType.TYPE_CLASS_TEXT);
                handleStepsMode(true);
            }
        }

        handleStepsMode(false);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ArrayList<EditText> editTexts = (ArrayList<EditText>) savedInstanceState.getSerializable("EDITTEXTS");
        mDragLinearLayoutSteps.removeAllViews();
        if (editTexts != null) {
            for (EditText editText : editTexts) {
                ((ViewGroup) editText.getParent()).removeView(editText);
                mDragLinearLayoutSteps.addView(editText);
            }
        }

        mSortStepsMode = savedInstanceState.getBoolean("SORTMODE");
        handleStepsMode(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<EditText> editTexts = new ArrayList<>();
        int count = mDragLinearLayoutSteps.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mDragLinearLayoutSteps.getChildAt(i);
            EditText editText = (EditText) child;
            editTexts.add(editText);
        }
        outState.putSerializable("EDITTEXTS", editTexts);
        outState.putBoolean("SORTMODE", !mSortStepsMode);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_step_toolbar, menu);
        mMenu = menu;
        swapMode();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_ingredients:
                System.out.println("TEST TEST TEST");
                handleStepsMode(false);
                swapMode();
                return true;
            case Utilities.TOOLBAR_NAVIGATION_ICON_CLICK_ID:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_ingredient:
                Utilities.addEmptyEditTextView(this, mDragLinearLayoutSteps, "Heat oil till flash point.", 1, InputType.TYPE_CLASS_TEXT);
                handleStepsMode(true);
                break;
            case R.id.save_ingredients:
                Intent intent = new Intent();
                newSteps = new ArrayList<>();

                int count = mDragLinearLayoutSteps.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = mDragLinearLayoutSteps.getChildAt(i);
                    EditText editText = (EditText) child;

                    if (!Utilities.isEditTextEmpty(editText))
                        newSteps.add(new Steps.Step(editText.getText().toString()));
                }

                intent.putParcelableArrayListExtra("STEPS", newSteps);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("STEPS", oldSteps);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    private void handleStepsMode(boolean reversed) {
        boolean sortStepsMode = mSortStepsMode;
        if (reversed) sortStepsMode = !sortStepsMode;

        int count = mDragLinearLayoutSteps.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mDragLinearLayoutSteps.getChildAt(i);
            EditText editText = (EditText) child;

            if (!sortStepsMode) {
                Utilities.addDrawableToTheRightOfEditTextView(editText, getDrawable(R.drawable.ic_swap_vert_black_24dp));
                mDragLinearLayoutSteps.setViewDraggable(child, child);
            } else {
                Utilities.addDrawableToTheRightOfEditTextView(editText, getDrawable(R.drawable.ic_delete_black_24dp));
                Utilities.makeEditTextViewRightDrawableDeletable(mDragLinearLayoutSteps, editText);
            }
        }
    }

    private void swapMode() {
        if (!mSortStepsMode) {
            handleSortMode(getDrawable(R.drawable.done_black), true);
        } else {
            handleSortMode(getDrawable(R.drawable.sort), false);
        }
    }

    private void handleSortMode(Drawable drawable, boolean sortMode) {
        mMenu.getItem(0).setIcon(drawable);
        mSortStepsMode = sortMode;
    }
}
