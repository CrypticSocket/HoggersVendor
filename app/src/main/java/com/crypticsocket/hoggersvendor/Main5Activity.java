package com.crypticsocket.hoggersvendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main5Activity extends AppCompatActivity {

    private String order_key = null, foodName, emailId, user;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView foodNameOrdered, emailIdOrdered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getEmail();
        Intent intent = getIntent();
        order_key = intent.getExtras().getString("orderID");
        foodNameOrdered = (TextView) findViewById(R.id.foodNameO);
        emailIdOrdered = (TextView) findViewById(R.id.usersnameO);

        if(user.equals("juiceberg@gmail.com"))
        {mDatabase = FirebaseDatabase.getInstance().getReference().child("juice_Orders");}
        else if (user.equals("maddhukcafe@gmail.com"))
        {mDatabase = FirebaseDatabase.getInstance().getReference().child("maddhuk_Orders");}
        else if (user.equals("teashop@gmail.com"))
        {mDatabase = FirebaseDatabase.getInstance().getReference().child("tea_Orders");}
        else if (user.equals("dosaco@gmail.com"))
        {mDatabase = FirebaseDatabase.getInstance().getReference().child("dosa_Orders");}

        mDatabase.child(order_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foodName = (String) dataSnapshot.child("itemname").getValue();
                emailId = (String) dataSnapshot.child("username").getValue();

                foodNameOrdered.setText(foodName);
                emailIdOrdered.setText(emailId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fulfilled (View view)
    {
        mDatabase.child(order_key).removeValue();
        Intent intent = new Intent(Main5Activity.this, Main4Activity.class);
        startActivity(intent);
        finish();
    }
}
