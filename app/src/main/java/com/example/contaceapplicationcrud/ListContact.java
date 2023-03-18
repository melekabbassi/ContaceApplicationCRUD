package com.example.contaceapplicationcrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ListContact extends AppCompatActivity {
    RecyclerView ContactRecyclerView;
    RecyclerView.LayoutManager ContactLayoutManager;
    ContactAdapter ContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        ContactRecyclerView = findViewById(R.id.ContactRecyclerView);
        ContactLayoutManager = new LinearLayoutManager(this);
        ContactRecyclerView.setLayoutManager(ContactLayoutManager);
        ContactRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ContactBDD ContactBDD = new ContactBDD(this);
        ContactAdapter = new ContactAdapter(this, ContactBDD.getAllContact());
        ContactRecyclerView.setAdapter(ContactAdapter);
    }
}