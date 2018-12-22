package com.example.mahmoudahmed.masroqaapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SearchItem extends Fragment {
    EditText item,location;
    Button showResult;
    TextView result;
    FirebaseUser mUser;
    FirebaseDatabase firebaseDatabase;
    StorageReference Xstorage;
    DatabaseReference Xreference;
    String strType = "";
    String strLocation = "";
    ArrayList<String> allData = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.search, container, false);
        item = (EditText) mView.findViewById(R.id.etItemType);
        location = (EditText) mView.findViewById(R.id.etLocation);
        showResult = (Button) mView.findViewById(R.id.btn_search);

        return mView;
    }
}
