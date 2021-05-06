package com.example.dell.app1;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregisterwithsqlite.R;


public class loginregister extends ActionBarActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;
    Button login;
    Button registerr;
    EditText enterpassword;
    TextView forgetpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginregister);
        login = (Button) findViewById(R.id.login_btn);
        registerr = (Button) findViewById(R.id.register_btn);
        enterpassword = (EditText) findViewById(R.id.password_edt);
        forgetpass = (TextView) findViewById(R.id.textView2);

        loginDataBaseAdapter = new LoginDataBaseAdapter(getApplicationContext());
        loginDataBaseAdapter.open();

        registerr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent i = new Intent(loginregister.this, registration.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                String Password = enterpassword.getText().toString();

                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(Password);

                if (Password.equals(storedPassword)) {
                    Toast.makeText(loginregister.this, "Congrats: Login Successfully", Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(loginregister.this, home.class);
                    startActivity(ii);
                } else if (Password.equals("")) {
                    Toast.makeText(loginregister.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(loginregister.this, "Password Incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                final Dialog dialog = new Dialog(loginregister.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.forget_search);
                dialog.show();

                final EditText security = (EditText) dialog.findViewById(R.id.securityhint_edt);
                final TextView getpass = (TextView) dialog.findViewById(R.id.textView3);

                Button ok = (Button) dialog.findViewById(R.id.getpassword_btn);
                Button cancel = (Button) dialog.findViewById(R.id.cancel_btn);

                ok.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        String userName = security.getText().toString();
                        if (userName.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please enter your securityhint", Toast.LENGTH_SHORT).show();
                        } else {
                            String storedPassword = loginDataBaseAdapter.getAllTags(userName);
                            if (storedPassword == null) {
                                Toast.makeText(getApplicationContext(), "Please enter correct securityhint", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("GET PASSWORD", storedPassword);
                                getpass.setText(storedPassword);
                            }
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// Close The Database
        loginDataBaseAdapter.close();
    }
}






