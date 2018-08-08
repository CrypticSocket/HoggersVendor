package com.crypticsocket.hoggersvendor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView tv,tv1;
    private EditText user, pass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text1);
        tv1 = (TextView) findViewById(R.id.text2);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void getStarted(View view)
    {
        String email = user.getText().toString().trim();
        if(email.equals("juiceberg@gmail.com")||email.equals("maddhukcafe@gmail.com")||email.equals("teashop@gmail.com")||email.equals("dosaco@gmail.com")) {
            String password = pass.getText().toString().trim();
            if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password))
            {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            checkUserExists();
                            Toast.makeText(MainActivity.this, "Logging In. Please Wait", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            tv1.setText("Username and Password do not match");
                        }
                    }
                });
            }
            else
            {
                tv1.setText("Can not leave field empty");
            }

        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }

    }

    public void checkUserExists()
    {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                {
                    Intent menuIntent = new Intent(MainActivity.this, Main4Activity.class);
                    startActivity(menuIntent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
