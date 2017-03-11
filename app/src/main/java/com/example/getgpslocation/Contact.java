package com.example.getgpslocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact extends AppCompatActivity {



    DatabaseHelper myDB;
    EditText et_name1,et_pnumber1,et_email1;
    EditText et_name2,et_pnumber2,et_email2;
    EditText et_name3,et_pnumber3,et_email3;

    Button btnaddcontact;
    String name1,pnumber1,email1;
    String name2,pnumber2,email2;
    String name3,pnumber3,email3;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);





        myDB = new DatabaseHelper(this);
        et_name1=(EditText)findViewById(R.id.editText_name1);
        et_pnumber1=(EditText)findViewById(R.id.editText_phone1);
        et_email1=(EditText)findViewById(R.id.editText_email1);

        et_name2=(EditText)findViewById(R.id.editText_name2);
        et_pnumber2=(EditText)findViewById(R.id.editText_phone2);
        et_email2=(EditText)findViewById(R.id.editText_email2);

        et_name3=(EditText)findViewById(R.id.editText_name3);
        et_pnumber3=(EditText)findViewById(R.id.editText_phone3);
        et_email3=(EditText)findViewById(R.id.editText_email3);


        btnaddcontact=(Button)findViewById(R.id.button_addcontact);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("uphone");
            //The key argument here must match that used in the other activity
        }



        btnaddcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });


    }



    public void addContact()
    {
        initializes();
        if(!validate()){
            Toast.makeText(Contact.this,"signup can't be done",Toast.LENGTH_SHORT).show();
        } else {


            boolean insertData1 = myDB.contactDetails(value ,name1, pnumber1, email1);
            boolean insertData2 = myDB.contactDetails(value ,name2, pnumber2, email2);
            boolean insertData3 = myDB.contactDetails(value ,name3, pnumber3, email3);
            if (insertData1 && insertData2 && insertData3) {
                Toast.makeText(Contact.this, "data inserted user", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Contact.this, SignIn.class);
                startActivity(i);
            } else {
                Toast.makeText(Contact.this, "data  cannot be inserted user", Toast.LENGTH_SHORT).show();
            }



        }
    }




    public void initializes()
    {
        name1=et_name1.getText().toString().trim();
        pnumber1=et_pnumber1.getText().toString().trim();
        email1=et_email1.getText().toString().trim();

        name2=et_name2.getText().toString().trim();
        pnumber2=et_pnumber2.getText().toString().trim();
        email2=et_email2.getText().toString().trim();

        name3=et_name3.getText().toString().trim();
        pnumber3=et_pnumber3.getText().toString().trim();
        email3=et_email3.getText().toString().trim();

    }

    public boolean validate()
    {
        boolean valid=true;

        if(pnumber1.isEmpty()||pnumber1.length()<10){
            et_pnumber1.setError("please enter the valid phone number");
            valid=false;
        }
        if(email1.isEmpty()||!isValidEmail(email1)) {
            et_email1.setError("please enter the valid email");
            valid=false;
        }
        if(name1.isEmpty()||name1.length()<7) {
            et_name1.setError("please enter the valid name");
            valid=false;
        }



        if(pnumber2.isEmpty()||pnumber2.length()<10){
            et_pnumber2.setError("please enter the valid phone number");
            valid=false;
        }
        if(email2.isEmpty()||!isValidEmail(email2)) {
            et_email2.setError("please enter the valid email");
            valid=false;
        }
        if(name2.isEmpty()||name2.length()<7) {
            et_name2.setError("please enter the valid name");
            valid=false;
        }


        if(pnumber3.isEmpty()||pnumber3.length()<10){
            et_pnumber3.setError("please enter the valid phone number");
            valid=false;
        }
        if(email3.isEmpty()||!isValidEmail(email3)) {
            et_email3.setError("please enter the valid email");
            valid=false;
        }
        if(name3.isEmpty()||name3.length()<7) {
            et_name3.setError("please enter the valid name");
            valid=false;
        }

        return valid;
    }


    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



}
