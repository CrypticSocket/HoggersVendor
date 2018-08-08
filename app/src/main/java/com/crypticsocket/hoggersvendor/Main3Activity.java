package com.crypticsocket.hoggersvendor;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//Add food activity java

public class Main3Activity extends AppCompatActivity {

    ImageButton imb;
    EditText name, price;
    private static final int GALLREQ =1;
    private Uri uri = null;
    private StorageReference storageReference = null;
    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        name = (EditText) findViewById(R.id.itemName);
        price = (EditText) findViewById(R.id.price);
        imb = (ImageButton) findViewById(R.id.foodImageButton);
        storageReference = FirebaseStorage.getInstance().getReference();
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
    }

    public void imageButton(View view)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,GALLREQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLREQ&&resultCode==RESULT_OK)
        {
            uri = data.getData();
            imb.setImageURI(uri);
        }

    }

    public void addToMenu(View view)
    {
        final String nameText,priceText;
        nameText = name.getText().toString().trim();
        priceText = price.getText().toString().trim();
        if(uri == null)
        {
            Toast.makeText(this, "Please Upload Image", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!TextUtils.isEmpty(nameText)&&!TextUtils.isEmpty(priceText)){
                StorageReference filePath = storageReference.child(uri.getLastPathSegment());
                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(Main3Activity.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                        final DatabaseReference newPost = mRef.push();
                        newPost.child("name").setValue(nameText);
                        newPost.child("price").setValue(priceText);
                        newPost.child("image").setValue(downloadUrl.toString());
                        Intent intent = new Intent (Main3Activity.this, Main2Activity.class);
                        startActivity(intent);
                    }
                });
            }
        }

    }
}
