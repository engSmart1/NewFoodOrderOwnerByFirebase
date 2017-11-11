package musictag.hytham1.com.newfoodorder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

import model.Food;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView mFoodList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth ;
    private FirebaseRecyclerAdapter<Food , FoodViewHolder> FBRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mFoodList = findViewById(R.id.foodList);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager( new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item4");
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FBRV = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class ,
                R.layout.single_menu_item,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, final int position) {
                viewHolder.setName( model.getName());
                viewHolder.setDesc( model.getDesc());
                viewHolder.setPrice( model.getPrice());
                viewHolder.setImage(getApplicationContext() , model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
                        alert.setMessage("Are You Sure To Delete this Item ?!");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FBRV.getRef(position).removeValue();
                                FBRV.notifyDataSetChanged();
                            }
                        });
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog dialog = alert.create();
                        dialog.show();
                    }
                });


            }
        };

        mFoodList.setAdapter(FBRV);
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        View mView = itemView;
        public FoodViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName (String name ){
            TextView textViewName = mView.findViewById(R.id.foodName);
            textViewName.setText(name);

        }
        public void setDesc (String desc ){
            TextView textViewDesc = mView.findViewById(R.id.foodDesc);
            textViewDesc.setText(desc);

        }
        public void setPrice (String price ){
            TextView textViewPrice = mView.findViewById(R.id.foodPrice);
            textViewPrice.setText(price);

        }
        public void setImage (Context context , String image ){
            ImageView imageView = mView.findViewById(R.id.foodImage);
            Picasso.with(context).load(image).into(imageView);



        }
    }
}
