package com.tealium.example.fragment;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tealium.example.R;
import com.tealium.example.helper.TealiumHelper;

public class ExampleDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, null);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        TealiumHelper.trackView("Simple Dialog Fragment", null);
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        TealiumHelper.trackView("Simple Dialog Fragment", null);
        return super.show(transaction, tag);
    }


}
