package com.tealium.sampledebug.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tealium.sampledebug.ControlExpandableListActivity;
import com.tealium.sampledebug.ControlFragmentActivity;
import com.tealium.sampledebug.ControlGridViewActivity;
import com.tealium.sampledebug.ControlStackViewActivity;
import com.tealium.sampledebug.R;
import com.tealium.sampledebug.helper.TealiumHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by karentamayo on 1/12/17.
 */

public class SelectionFragment extends ListFragment {
    public static int REQUEST_CODE_SELECT = 1;

    private final Map<String, Integer> exampleLayouts = new HashMap<String, Integer>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Had to wait until getActivity() was populated to use it's context to access resources.

        setListAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.examples, R.layout.selection_item));

        Resources res = getResources();

        exampleLayouts.put(res.getString(R.string.button), R.layout.example_button);
        exampleLayouts.put(res.getString(R.string.checkbox), R.layout.example_checkbox);
        exampleLayouts.put(res.getString(R.string.checkedtextview), R.layout.example_checkedtextview);
        exampleLayouts.put(res.getString(R.string.datepicker), R.layout.example_datepicker);
        exampleLayouts.put(res.getString(R.string.imagebutton), R.layout.example_imagebutton);
        exampleLayouts.put(res.getString(R.string.radiogroup), R.layout.example_radiogroup);
        exampleLayouts.put(res.getString(R.string.ratingbar), R.layout.example_ratingbar);
        exampleLayouts.put(res.getString(R.string.seekbar), R.layout.example_seekbar);
        exampleLayouts.put(res.getString(R.string.spinner), R.layout.example_spinner);
        exampleLayouts.put(res.getString(R.string.switchwidget), R.layout.example_switch);
        exampleLayouts.put(res.getString(R.string.textview), R.layout.example_textview);
        exampleLayouts.put(res.getString(R.string.timepicker), R.layout.example_timepicker);
        exampleLayouts.put(res.getString(R.string.togglebutton), R.layout.example_togglebutton);
        exampleLayouts.put(res.getString(R.string.videoview), R.layout.example_videoview);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Resources res = getResources();
        final String selected = (String) getListAdapter().getItem(position);
        Integer layoutId;

        TealiumHelper.trackEvent(selected + ":Selected", null);

        if(res.getString(R.string.dialogfragment).equals(selected)) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ExampleDialogFragment newFragment = new ExampleDialogFragment();
            newFragment.show(ft, "tag:dialog");
        } else if(res.getString(R.string.gridview).equals(selected)) {
            startActivity(new Intent(getActivity(), ControlGridViewActivity.class));
        } else if(res.getString(R.string.stackview).equals(selected)) {
            startActivity(new Intent(getActivity(), ControlStackViewActivity.class));
        } else if(res.getString(R.string.expandablelist).equals(selected)) {
            startActivity(new Intent(getActivity(), ControlExpandableListActivity.class));
        } else if((layoutId = exampleLayouts.get(selected)) != null) {
            selectLayout(layoutId);
        } else {
            Toast.makeText(
                    getActivity(),
                    String.format(Locale.US, getResources().getString(R.string.error_hasnotbeenimplemented_format), selected),
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_SELECT && resultCode == ControlFragmentActivity.RESULT_SELECT) {
            selectLayout(data.getIntExtra(ControlFragmentActivity.KEY_CONTROL_LAYOUT_ID, 0));
        }
    }

    private void selectLayout(int layoutId) {

        if(layoutId == 0) {
            return;
        }

        ControlFragment customFragment = ((ControlFragment) getFragmentManager().findFragmentById(R.id.fragment_custom));

        if(customFragment == null || !customFragment.setControl(layoutId)) {
            Intent intent = new Intent(getActivity(), ControlFragmentActivity.class);
            intent.putExtra(ControlFragmentActivity.KEY_CONTROL_LAYOUT_ID, layoutId);
            startActivityForResult(intent, REQUEST_CODE_SELECT);
            return;
        }

        customFragment.setControl(layoutId);
    }
}
