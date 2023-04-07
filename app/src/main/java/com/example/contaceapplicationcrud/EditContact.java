package com.example.contaceapplicationcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContact extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    EditText id, name, phone;
    Button btnEdit;

    DatabaseHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        id = (EditText) findViewById(R.id.editTextId);
        name = (EditText) findViewById(R.id.editTextName);
        phone = (EditText) findViewById(R.id.editTextPhone);
        btnEdit = (Button) findViewById(R.id.buttonEdit);

        id.setOnFocusChangeListener(this);
        btnEdit.setOnClickListener(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        if(id != -1) {
            this.id.setText(String.valueOf(id));
            this.name.setText(name);
            this.phone.setText(phone);
        }
    }

    @Override
    public void onClick(View v) {
        String idStr = id.getText().toString();
        if(idStr.isEmpty()){
            Toast.makeText(EditContact.this, "Please enter an id", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = Integer.parseInt(idStr);
        ContactBDD contact = new ContactBDD(EditContact.this);
        Contact c = new Contact(id, name.getText().toString(), phone.getText().toString());
        long result = contact.updateContact(c);
        if (result != -1) {
            Toast.makeText(EditContact.this, "Contact edited successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditContact.this, ListContact.class);
            startActivity(intent);
            intent.getIntExtra("id", id);
            intent.getStringExtra("name");
            intent.getStringExtra("phone");
            finish();
        } else {
            Toast.makeText(EditContact.this, "Contact not edited", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (id.getText().toString().isEmpty()) {
            id.setError("Please enter an id");
            id.requestFocus();
        } else {
            ContactBDD contact = new ContactBDD(EditContact.this);
            Contact c = contact.searchContact(Integer.parseInt(id.getText().toString()));
            name.setText(c.getName());
            phone.setText(c.getPhoneNumber());
        }
    }
}