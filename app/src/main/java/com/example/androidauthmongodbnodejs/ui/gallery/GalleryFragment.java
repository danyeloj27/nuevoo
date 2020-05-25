package com.example.androidauthmongodbnodejs.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidauthmongodbnodejs.MainActivity;
import com.example.androidauthmongodbnodejs.R;
import com.example.androidauthmongodbnodejs.ServiceActivity;

public class GalleryFragment extends Fragment {

String name;
String codigo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        

        return inflater.inflate(R.layout.detail_services_layout, container, false);
    }
}
