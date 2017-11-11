package musictag.hytham1.com.newfoodorder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import model.Order;

public class OpenOrders extends AppCompatActivity {


    private RecyclerView mRecyclerView;
     DatabaseReference mDatabase;
     FirebaseRecyclerAdapter <Order , OrderViewHolder> FBRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_orders);

        mRecyclerView = findViewById(R.id.orderLayout);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");


    }

    @Override
    protected void onStart() {
        super.onStart();

        FBRA = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(
                Order.class,
                R.layout.single_order_layout,
                OrderViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(final OrderViewHolder viewHolder, Order model, final int position) {

                viewHolder.setUserName(model.getUserName());
                viewHolder.setItemName(model.getItemName());

                viewHolder.orderView.setOnClickListener (new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(OpenOrders.this);
                        alert.setMessage("Are you want to delete this ?!");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int index) {
                              //  int selectedItem = position;
                                FBRA.getRef(position).removeValue();
                                FBRA.notifyDataSetChanged();



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

                        //Toast.makeText(OpenOrders.this, "Deleted...", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        };
        mRecyclerView.setAdapter(FBRA);

    }


    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        View orderView;
        public OrderViewHolder(View itemView) {
            super(itemView);
            orderView = itemView;

        }


        public void setUserName(String userName){
            TextView userName_text = orderView.findViewById(R.id.orderUserName);
            userName_text.setText(userName);

        }
        public void setItemName(String itemName){
            TextView itemName_text = orderView.findViewById(R.id.orderItemName);
            itemName_text.setText(itemName);

        }



    }

}
