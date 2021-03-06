package com.example.driverproject.driver_slip;

import android.content.Intent;
import android.service.autofill.SaveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    EditText editTextFullName , editTextAge, editTextContact;
    public String gender;
    private Spinner spinner;
    private static final String[] paths = {"MALE", "FEMALE"};
    UserInformation userInformation;
    Button buttonSaveInfo;

    DatabaseReference db;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db=FirebaseDatabase.getInstance().getReference();
        editTextFullName=(EditText)findViewById(R.id.editTextFullName);
        editTextAge=(EditText)findViewById(R.id.editTextAge);
        editTextContact=(EditText)findViewById(R.id.editTextContact);


        firebaseAuth=FirebaseAuth.getInstance();


        buttonSaveInfo=(Button)findViewById(R.id.buttonSaveInfo);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(EditProfileActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        buttonSaveInfo.setOnClickListener(this);

    }

    private void saveInfo()
    {
            String fname= editTextFullName.getText().toString().trim();
            String contact= editTextContact.getText().toString().trim();
            String age= editTextAge.getText().toString().trim();

            userInformation= new UserInformation(fname,contact,age,gender);

            FirebaseUser user= firebaseAuth.getCurrentUser();
            db.child(user.getUid()).setValue(userInformation);
            Toast.makeText(this,"Details added ..",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v)
    {
        if(v==buttonSaveInfo)
        {
            saveInfo();
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (position)
        {
            case 1:
                gender="Male";
                break;
            case 2:
                gender="Female";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        Toast.makeText(this,"Please select gender !",Toast.LENGTH_SHORT).show();
    }
}
