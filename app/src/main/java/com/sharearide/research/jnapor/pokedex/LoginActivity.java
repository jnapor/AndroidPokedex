package com.sharearide.research.jnapor.pokedex;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sharearide.research.jnapor.pokedex.data.DatabaseManipulator;
import com.sharearide.research.jnapor.pokedex.data.PokedexContract;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseManipulator.initializeDatabase(this);


        Button button = (Button) findViewById(R.id.sign_in);
        RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.loginLayout);

        final EditText username = (EditText) parentLayout.findViewById(R.id.unametxt);
        final EditText password = (EditText) parentLayout.findViewById(R.id.passtxt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString();
                String pass = password.getText().toString();

                int count = DatabaseManipulator.login(getApplicationContext(), uname, pass);
                if(count == 0){
                    showAlertDialog();
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void showAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Incorrect username or password!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
