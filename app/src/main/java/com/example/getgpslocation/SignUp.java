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

public class SignUp extends AppCompatActivity {


    DatabaseHelper myDB;
    EditText et_name,et_pnumber,et_email,et_password;
    Button btnsignup;
    String name,pnumber,email,password;
    TextView loginbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        myDB = new DatabaseHelper(this);
        et_name=(EditText)findViewById(R.id.editText_uname);
        et_pnumber=(EditText)findViewById(R.id.editText_uphone);
        et_password=(EditText)findViewById(R.id.editText_upassword);
        et_email=(EditText)findViewById(R.id.editText_uemail);
        btnsignup=(Button)findViewById(R.id.button_signup);
        loginbtn=(TextView)findViewById(R.id.hvaccount);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SignUp.this,SignIn.class);
                startActivity(i);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });



    }



    public void signup()
    {
        initializes();
        if(!validate()){
            Toast.makeText(SignUp.this,"signup can't be done",Toast.LENGTH_SHORT).show();
        } else {


                boolean insertDataU = myDB.insertdataUser(name, pnumber, password, email);
                if (insertDataU) {
                    Intent i = new Intent(SignUp.this, Contact.class);
                    i.putExtra("uphone",pnumber);
                    startActivity(i);
                    Toast.makeText(SignUp.this, "data inserted user", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, "data  cannot be inserted user", Toast.LENGTH_SHORT).show();
                }



        }
    }




    public void initializes()
    {
        name=et_name.getText().toString().trim();
        pnumber=et_pnumber.getText().toString().trim();
        email=et_email.getText().toString().trim();
        password=et_password.getText().toString().trim();
    }

    public boolean validate()
    {
        boolean valid=true;

        if(pnumber.isEmpty()||pnumber.length()<10){
            et_pnumber.setError("please enter the valid phone number");
            valid=false;
        }
        if(password.isEmpty()||password.length()<7){
            et_password.setError(" password length 8");
            valid=false;
        }
        if(email.isEmpty()||!isValidEmail(email)) {
            et_email.setError("please enter the valid email");
            valid=false;
        }
        if(name.isEmpty()||name.length()<5) {
            et_name.setError("please enter the valid name");
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
