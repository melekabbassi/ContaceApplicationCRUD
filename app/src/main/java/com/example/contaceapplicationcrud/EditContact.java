package com.example.contaceapplicationcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContact extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    EditText id, name, phoneNumber;
    Button btnEdit;

    DatabaseHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        id = (EditText) findViewById(R.id.editTextId);
        name = (EditText) findViewById(R.id.editTextName);
        phoneNumber = (EditText) findViewById(R.id.editTextPhone);
        btnEdit = (Button) findViewById(R.id.buttonEdit);

        id.setOnFocusChangeListener(this);
        btnEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ContactBDD contact = new ContactBDD(EditContact.this);
        Contact c = new Contact(name.getText().toString(), phoneNumber.getText().toString());
        long result = contact.updateContact(c);
        if (result != -1) {
            Toast.makeText(EditContact.this, "Contact edited successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditContact.this, ListContact.class);
            startActivity(intent);
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
            phoneNumber.setText(c.getPhoneNumber());
        }
    }
}