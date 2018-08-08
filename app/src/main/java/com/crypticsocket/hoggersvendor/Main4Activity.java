package com.crypticsocket.hoggersvendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

//open orders.

public class Main4Activity extends AppCompatActivity {

    private RecyclerView mFoodList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mFoodList = (RecyclerView) findViewById(R.id.orderLayout);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getEmail();

        if(user.equals("juiceberg@gmail.com"))
        {mDatabase = FirebaseDatabase.getInstance().getReference().child("juice_Orders");}
        else if (user.equals("maddhukcafe@gmail.com"))
        {mDatabase = FirebaseDatabase.getInstance().getReference().child("maddhuk_Orders");}
        else if (user.equals("teashop@gmail.com"))
        {mDatabase = FirebaseDatabase.getInstance().getReference().child("tea_Orders");}
        else if (user.equals("dosaco@gmail.com"))
        {mDatabase = FirebaseDatabase.getInstance().getReference().child("dosa_Orders");}
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter <Order, OrderViewHolder> FRBA = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(
                Order.class,
                R.layout.menu,
                OrderViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Order model, int position) {

                viewHolder.setUserName(model.getItemname());
                viewHolder.setItemName(model.getUsername());
                final String order_key = getRef(position).getKey().toString();
                viewHolder.orderView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Main4Activity.this, Main5Activity.class);
                        intent.putExtra("orderID",order_key);
                        startActivity(intent);
                    }
                });
            }
        };
        mFoodList.setAdapter(FRBA);
    }

    public void myMenu(View view)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        View orderView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            orderView = itemView;
        }

        public void setUserName(String username)
        {
            TextView username_content = (TextView) orderView.findViewById(R.id.orderUserName);
            username_content.setText(username);
        }

        public void setItemName(String itemname)
        {
            TextView itemname_content = (TextView) orderView.findViewById(R.id.orderItemName);
            itemname_content.setText(itemname);
        }

    }
}
