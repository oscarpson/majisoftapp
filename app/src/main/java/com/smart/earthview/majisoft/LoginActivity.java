package com.smart.earthview.majisoft;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smart.earthview.majisoft.model.LoginClass;
import com.smart.earthview.majisoft.model.MajiRepository;

import java.util.List;
import java.util.Random;

import static com.smart.earthview.majisoft.constant.SnackClass.setErrorSnackbar;

public class LoginActivity extends AppCompatActivity {
Button btninstall;
EditText edtusername,edtpassword;
    CoordinatorLayout relativemap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        btninstall=findViewById(R.id.btnlogin);
        edtusername=findViewById(R.id.editTextusername);
        edtpassword=findViewById(R.id.editTextpassword);
        relativemap=findViewById(R.id.relativemap);
        btninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()) {
                    if (edtpassword.getText().toString().trim().matches("Maji@2018") )  {
                        if(edtusername.getText().toString().trim().matches("majiadmin18")) {
                            addToDatabase();

                        }
                        else {  setErrorSnackbar(relativemap, "Wrong Username or Password kindly contact APP Admin");}
                    }
                }
                else {  setErrorSnackbar(relativemap, "Wrong Username or Password kindly contact APP Admin");}
            }
        });


    }

    private void addToDatabase() {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("login",0);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("logged",true);
        editor.apply();
        Intent ints = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(ints);
       // final MajiRepository majiRepository=new MajiRepository(getApplicationContext());
       // Random random=new Random();
        //LoginClass login=new LoginClass(random.nextInt(),"Maji","Maji");
       /* majiRepository.insertLogin(login);
        majiRepository.getLogin().observeForever(new Observer<List<LoginClass>>() {
            @Override
            public void onChanged(@Nullable List<LoginClass> loginClasses) {
                Log.e("login", loginClasses.size() + "");
                if (loginClasses.size() == 1) {
                    Intent ints = new Intent(getApplicationContext(), LandingActivity.class);
                    startActivity(ints);
                }
            }
        });
*/
    }

    private boolean validateFields() {
        if(edtpassword.getText().toString().trim().equals("")){
            edtpassword.setError("Please enter correct password");
            return false;
        }
        if(edtusername.getText().toString().trim().equals("")){
           edtusername.setError("please enter correct username");
            return false;
        }
        return true;
    }

}
