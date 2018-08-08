package com.crypticsocket.hoggersvendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class singleFoodActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private String user, order_key=null,food_name,food_price,food_image;
    private TextView singleFoodTitle, singleFoodPrice;
    private ImageView singleFoodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);
        order_key = getIntent().getExtras().getString("FoodId");
        singleFoodImage = (ImageView) findViewById(R.id.singleImageView);
        singleFoodTitle = (TextView) findViewById(R.id.singleTitle);
        singleFoodPrice = (TextView) findViewById(R.id.singlePrice);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getEmail();
        if(user.equals("juiceberg@gmail.com"))
        {mRef = FirebaseDatabase.getInstance().getReference("juice_Item");}
        else if (user.equals("maddhukcafe@gmail.com"))
        {mRef = FirebaseDatabase.getInstance().getReference("maddhuk_Item");}
        else if (user.equals("teashop@gmail.com"))
        {mRef = FirebaseDatabase.getInstance().getReference("tea_Item");}
        else if (user.equals("dosaco@gmail.com"))
        {mRef = FirebaseDatabase.getInstance().getReference("dosa_Item");}

        mRef.child(order_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food_name = (String) dataSnapshot.child("name").getValue();
                food_price = (String) dataSnapshot.child("price").getValue();
                food_image = (String) dataSnapshot.child("image").getValue();
                singleFoodTitle.setText(food_name);
                singleFoodPrice.setText(food_price);
                Picasso.with(singleFoodActivity.this).load(food_image).into(singleFoodImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteItemClicked(View view)
    {
        mRef.child(order_key).removeValue();
        Intent intent = new Intent(singleFoodActivity.this, Main4Activity.class);
        startActivity(intent);
        Toast.makeText(this, "Item has been deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
