package com.discount.pigpigroad.discountapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discount.pigpigroad.discountapp.R;

/**
 * Created by XYSM on 2018/3/20.
 */

public class SearchPage extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View SearchPageLayout = inflater.inflate(R.layout.searchpage_layout,
                container, false);
        return SearchPageLayout;
    }
}
