package com.example.contaceapplicationcrud;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context context;
    private List<Contact> contactList;
    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public List<Contact> getContactList() {
        return contactList;
    }
    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Contact contact = contactList.get(position);
        holder.textViewName.setText(contact.getName());
        holder.textViewPhone.setText(contact.getPhoneNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditContact.class);
                intent.putExtra("id", contact.getId());
                intent.putExtra("name", contact.getName());
                intent.putExtra("phone", contact.getPhoneNumber());
                v.getContext().startActivity(intent);
                ((Activity) v.getContext()).finish();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete Contact");
                builder.setMessage("Are you sure you want to delete or edit this contact?");
                builder.setPositiveButton("Delete", (dialog, which) -> {
                    ContactBDD contactBDD = new ContactBDD(v.getContext());
                    contactBDD.openWritable();
                    contactBDD.deleteContact(contact.getId());
                    contactList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, contactList.size());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return contactList.size();
    }
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPhone;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.TextViewName);
            textViewPhone = itemView.findViewById(R.id.TextViewPhone);
        }
        public TextView getTextViewName() {
            return textViewName;
        }
    }
}