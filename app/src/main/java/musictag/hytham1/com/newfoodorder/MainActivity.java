package musictag.hytham1.com.newfoodorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }




    public void addFoodButtonClicked(View view) {
        startActivity( new Intent(MainActivity.this , AddFood.class));
    }

    public void viewOrders(View view) {
        startActivity( new Intent(MainActivity.this , OpenOrders.class ));
    }
    public void SignedUp(View view){

        startActivity( new Intent(MainActivity.this , SignUpNewOwner.class));
    }

    public void showTheMenu(View view) {
        startActivity( new Intent(MainActivity.this , MenuActivity.class));
    }
}
