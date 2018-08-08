package com.crypticsocket.hoggersvendor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Main6Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private String user;
    private RecyclerView mFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        mAuth = FirebaseAuth.getInstance();
        mFoodList = (RecyclerView) findViewById(R.id.recyclerView);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager(new LinearLayoutManager(this));
        user = mAuth.getCurrentUser().getEmail();
        if(user.equals("juiceberg@gmail.com"))
        {mRef = FirebaseDatabase.getInstance().getReference("juice_Item");}
        else if (user.equals("maddhukcafe@gmail.com"))
        {mRef = FirebaseDatabase.getInstance().getReference("maddhuk_Item");}
        else if (user.equals("teashop@gmail.com"))
        {mRef = FirebaseDatabase.getInstance().getReference("tea_Item");}
        else if (user.equals("dosaco@gmail.com"))
        {mRef = FirebaseDatabase.getInstance().getReference("dosa_Item");}
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Food,FoodViewHolder> FBRA = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.menu2,
                FoodViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                final String food_key = getRef(position).getKey().toString();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleFoodActivity = new Intent (Main6Activity.this, singleFoodActivity.class);
                        singleFoodActivity.putExtra("FoodId",food_key);
                        startActivity(singleFoodActivity);
                    }
                });
            }
        };
        mFoodList.setAdapter(FBRA);
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public FoodViewHolder(View itemView) {
            super(itemView);
            mView= itemView;
        }

        public void setName(String name){
            TextView food_name = (TextView) mView.findViewById(R.id.nameFood);
            food_name.setText(name);
        }

        public void setPrice(String price){
            TextView food_price = (TextView) mView.findViewById(R.id.priceFood);
            food_price.setText(price);
        }

        public void setImage(Context ctx, String image){
            ImageView food_image = (ImageView) mView.findViewById(R.id.foodImage);
            Picasso.with(ctx).load(image).into(food_image);
        }
    }
}
