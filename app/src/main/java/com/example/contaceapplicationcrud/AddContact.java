package com.example.contaceapplicationcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity implements View.OnClickListener{
    EditText name, phoneNumber;
    Button btnAdd;
    DatabaseHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        name = (EditText) findViewById(R.id.editTextName);
        phoneNumber = (EditText) findViewById(R.id.editTextPhone);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(name.getText().toString().isEmpty() || phoneNumber.getText().toString().isEmpty()){
            name.setError("Please enter a name");
            name.requestFocus();
        }

        if(phoneNumber.getText().toString().isEmpty()){
            phoneNumber.setError("Please enter a phone number");
            phoneNumber.requestFocus();
        }

        ContactBDD contact = new ContactBDD(AddContact.this);
        Contact c = new Contact(name.getText().toString(), phoneNumber.getText().toString());
        long result = contact.addContact(c);
        if (result != -1){
            Toast.makeText(AddContact.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddContact.this, "Contact not added", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}