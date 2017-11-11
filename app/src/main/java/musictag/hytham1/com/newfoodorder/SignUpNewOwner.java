package musictag.hytham1.com.newfoodorder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpNewOwner extends AppCompatActivity {
    private EditText ET_Owner , ET_Pass;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_new_owner);

        ET_Owner = findViewById(R.id.et_signUpOwner);
        ET_Pass = findViewById(R.id.et_signUpPass);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("owner");
    }

    public void submitNewOwner(View view) {
        final String owner_text = ET_Owner.getText().toString().trim();
        String pass_text = ET_Pass.getText().toString().trim();

        if (!TextUtils.isEmpty(owner_text) && !TextUtils.isEmpty(pass_text)){


            mAuth.createUserWithEmailAndPassword(owner_text , pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String owner_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_owner = mDatabase.child(owner_id);
                        current_owner.child("Name").setValue(owner_text);
                        startActivity( new Intent(SignUpNewOwner.this , MainActivity.class));
                        Toast.makeText(SignUpNewOwner.this, "New Owner has created", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}
