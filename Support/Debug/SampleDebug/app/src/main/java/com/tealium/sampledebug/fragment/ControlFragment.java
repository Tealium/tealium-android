package com.tealium.sampledebug.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tealium.sampledebug.R;
import com.tealium.sampledebug.helper.TealiumHelper;

import java.util.Locale;

/**
 * Created by karentamayo on 1/12/17.
 */

public class ControlFragment extends android.support.v4.app.Fragment implements
        View.OnClickListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener,
        RadioGroup.OnCheckedChangeListener {

    private final RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout relativeLayout;

    public ControlFragment() {
        super();

        layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        relativeLayout = null;
    }

    public boolean setControl(int layoutId) {

        if (getActivity() == null) {
            return false;
        }

        View control = LayoutInflater.from(getActivity()).inflate(layoutId, null);
        control.setLayoutParams(layoutParams);

        if (control instanceof AdapterView) {
            try {
                ((AdapterView<?>) control).setOnItemClickListener(this);
            } catch (RuntimeException e) {
                // Item click not supported.
                ((AdapterView<?>) control).setOnItemSelectedListener(this);
            }
        } else if (control instanceof RadioGroup) {
            ((RadioGroup) control).setOnCheckedChangeListener(this);
        } else if (control instanceof CompoundButton) {
            ((CompoundButton) control).setOnCheckedChangeListener(this);
        } else {
            control.setOnClickListener(this);
        }

        relativeLayout.removeAllViews();
        relativeLayout.addView(control);

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_custom, null);

        return relativeLayout;
    }

    // Interface Implementations

    @Override
    public void onClick(View v) {
        TealiumHelper.trackEvent(v.getClass().getSimpleName() + ":Click", null);
        Toast.makeText(
                getActivity(),
                String.format(Locale.US, "%s: Click!", v.getClass().getSimpleName()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        TealiumHelper.trackEvent(compoundButton.getClass().getSimpleName() + ":" + (isChecked ? "Checked" : "Unchecked"), null);
        Toast.makeText(
                getActivity(),
                String.format(Locale.US, "%s: %s!",
                        compoundButton.getClass().getSimpleName(),
                        (isChecked ? "Checked" : "Unchecked")),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        Toast.makeText(
                getActivity(),
                String.format(Locale.US, "%d was checked!", checkedId),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View selection, int position, long id) {
        Object item = adapterView.getAdapter().getItem(position);

        TealiumHelper.trackEvent(item.toString() + ":Click", null);

        Toast.makeText(
                getActivity(),
                String.format(Locale.US, "%s was clicked!", item.toString()),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View selectionView, int position, long id) {
        Object item = adapterView.getAdapter().getItem(position);

        TealiumHelper.trackEvent(item.toString() + ":Selected", null);

        Toast.makeText(
                getActivity(),
                String.format(Locale.US, "%s was selected!", item.toString()),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // Nothing to do here...
    }
}
