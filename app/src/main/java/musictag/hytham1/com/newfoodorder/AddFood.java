package musictag.hytham1.com.newfoodorder;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class AddFood extends AppCompatActivity {
    ImageButton foodImage;
    EditText name, desc, price;
    private static final int GALLREQ = 2;
    private Uri uri = null;
    private StorageReference storageReference = null;
    private DatabaseReference mRef;
   // private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        desc = findViewById(R.id.itemDescription);
        name = findViewById(R.id.itemName);
        price = findViewById(R.id.itemPrice);

        storageReference = FirebaseStorage.getInstance().getReference();
        mRef = FirebaseDatabase.getInstance().getReference("Item4");
    }

    public void ImageButtonClicked(View view) {
        Intent intentGall = new Intent(Intent.ACTION_GET_CONTENT);
        intentGall.setType("Image/*");
        startActivityForResult(intentGall, GALLREQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLREQ && resultCode == RESULT_OK) {
            uri = data.getData();
            foodImage = findViewById(R.id.foodImageButton);
            foodImage.setImageURI(uri);


        }
    }

    public void addItemButtonClicked(View view) {
        final String desc_text = desc.getText().toString().trim();
        final String name_text = name.getText().toString().trim();
        final String price_text = price.getText().toString().trim();

        if (!TextUtils.isEmpty(name_text) && !TextUtils.isEmpty(desc_text) && !TextUtils.isEmpty(price_text)) {



            try {
                StorageReference filePath = storageReference.child(uri.getLastPathSegment());
                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final Uri downLoadUri = taskSnapshot.getDownloadUrl();
                        Toast.makeText(AddFood.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        final DatabaseReference newPost = mRef.push();
                        newPost.child("desc").setValue(desc_text);
                        newPost.child("name").setValue(name_text);
                        newPost.child("price").setValue(price_text);
                        newPost.child("image").setValue(downLoadUri.toString());
                    }
                });

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        name.setText("");
        desc.setText("");
        price.setText("");
    }
}