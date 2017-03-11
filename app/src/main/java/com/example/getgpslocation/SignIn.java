package com.example.getgpslocation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SignIn extends AppCompatActivity {


    DatabaseHelper myDB;
    EditText et_email, et_password;
    String email, password;
    Button btsignin;
    TextView gotsignup;
    private Session session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);




        myDB = new DatabaseHelper(this);

        et_email = (EditText) findViewById(R.id.editText_email);
        et_password = (EditText) findViewById(R.id.editText_password);
        btsignin = (Button) findViewById(R.id.btn_signin);
        gotsignup = (TextView) findViewById(R.id.create_ac);

        session = new Session(this);

        gotsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, SignUp.class);
                startActivity(i);
            }
        });

        btsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }




    public void Login() {
        initialize();

        if (!validate()) {
            Toast.makeText(this, "login failed", Toast.LENGTH_LONG).show();
        } else {
            if(myDB.getUser(email,password)){
                session.setLoggedin(true);
            } else{
                Toast.makeText(getApplicationContext(), "Wrong email/password",Toast.LENGTH_SHORT).show();
            }
        }

        if(session.loggedin()){
            Intent i=new Intent(SignIn.this, MainActivity.class);
            int phoneno = myDB.getPhoneU(email,password);
            i.putExtra("phno", phoneno);
            startActivity(i);}
    }


    public boolean validate() {
        boolean valid = true;

        if (password.isEmpty()) {
            et_password.setError("please enter the password");
            valid = false;
        }
        if (email.isEmpty()||!isValidEmail(email)) {
            et_email.setError("please enter valid email id");
            valid=false;
            //|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()||
        }
        return valid;
    }



    public void initialize(){
        email=et_email.getText().toString().trim();
        password=et_password.getText().toString().trim();
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



}
