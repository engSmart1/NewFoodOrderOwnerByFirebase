package musictag.hytham1.com.newfoodorder;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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


/**
 * Created by Hytham on 11/7/2017.
 */

public class LoginActivity extends AppCompatActivity {
    EditText ownerEmail , ownerPass;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ownerEmail = findViewById(R.id.email);
        ownerPass = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("owner");


    }


    public void signInClicked(View view) {


        String owner_email = ownerEmail.getText().toString().trim();
        String owner_pass = ownerPass.getText().toString().trim();

        if (!TextUtils.isEmpty(owner_email) && !TextUtils.isEmpty(owner_pass)){

            //Toast.makeText(this, "uuuuuuuuuuuuu", Toast.LENGTH_SHORT).show();

            mAuth.signInWithEmailAndPassword( owner_email , owner_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()){
                       checkOwnerExist();
                    }


                }
            });
        }


    }

    public void  checkOwnerExist() {
        final String owner_id = mAuth.getCurrentUser().getUid();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(owner_id)){

                    startActivity( new Intent( LoginActivity.this , MainActivity.class));

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }





}
