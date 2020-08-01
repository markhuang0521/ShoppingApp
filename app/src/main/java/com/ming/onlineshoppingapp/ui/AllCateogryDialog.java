package com.ming.onlineshoppingapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.SearchActivity;
import com.ming.onlineshoppingapp.models.Category;

import java.util.List;

public class AllCateogryDialog extends DialogFragment {
    public interface GetCategoryListner {
        void getCategoryResult(String category);
    }

    private GetCategoryListner clickListener;
    public static final String CALLING_ACTIVITY_KEY = "calling ";
    public static final String MAIN_ACTIIVTY_KEY = "main actvitiy ";
    public static final String SEARCH_ACTIIVTY_KEY = "search actvitiy ";
    public static final String CATEGORY_KEY = "category";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.all_category_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        Bundle bundle = getArguments();
        clickListener = (GetCategoryListner) getActivity();
        final String callingActivity = bundle.getString(CALLING_ACTIVITY_KEY);
        ListView listView = view.findViewById(R.id.listview);
        final List<String> categories = Category.getAllCategory();
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, categories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (callingActivity) {
                    case MAIN_ACTIIVTY_KEY:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        intent.putExtra(CATEGORY_KEY, categories.get(position));
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        dismiss();
                        break;
                    case SEARCH_ACTIIVTY_KEY:
                        clickListener.getCategoryResult(categories.get(position));
                        dismiss();
                        break;
                }

            }
        });
        return builder.create();


    }
}
