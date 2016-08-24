package com.sharearide.research.jnapor.pokedex;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sharearide.research.jnapor.pokedex.data.DatabaseManipulator;
import com.sharearide.research.jnapor.pokedex.data.PokedexContract;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseManipulator.initializeDatabase(this);

        Button button = (Button) findViewById(R.id.sign_in);
        final EditText username = (EditText) findViewById(R.id.unametxt);
        final EditText password = (EditText) findViewById(R.id.passtxt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString();
                String pass = password.getText().toString();

                int count = DatabaseManipulator.login(getApplicationContext(), uname, pass);
                
            }
        });
    }


}
