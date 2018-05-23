package com.example.dylan.firebase_app;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class AdapterListView extends ArrayAdapter<Item> {
    private final Activity context;
    private List<Item> items = null;
    DatabaseReference productDatabase;
    public static final String EXTRA_MESSAGE = "com.example.dylan.firebase_app";



    public AdapterListView(Activity context, List<Item> items) {
        super(context, R.layout.custom_item_row, items);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.items = items;
        productDatabase = FirebaseDatabase.getInstance().getReference("productos");


    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_item_row, null,true);

        TextView txtName = (TextView) rowView.findViewById(R.id.txtNameL);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.txtPriceL);
        FloatingActionButton deleteButton = rowView.findViewById(R.id.deleteButtonL);
        final FloatingActionButton detailButton = rowView.findViewById(R.id.btnDetail);

        txtName.setText(items.get(position).getName());
        txtPrice.setText(items.get(position).getPrice());

        deleteButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteItem(items.get(position));
            }
        });

        detailButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                detailItem(items.get(position));
            }
        });



        //Bitmap bm = BitmapFactory.decodeFile(songs.get(position).getPath());
        //imageView.setImageResource(R.drawable.tfm);
        //imageView.setImageBitmap(bm);

        //Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
        //Uri path1 = ContentUris.withAppendedId(artworkUri, songs.get(position).getAlbumId());
        //Glide.with(imageView.getContext()).load(path1).into(imageView);

        //imageView.setImageBitmap(bm);
        return rowView;

    };

    public void deleteItem(Item item) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("productos").child(item.getId());
        reference.removeValue();

    }

    public void detailItem(Item item) {
        Intent intent = new Intent(context, ItemDetail.class);
        intent.putExtra(EXTRA_MESSAGE, "");
        intent.putExtra("name", item.getName());
        intent.putExtra("price", item.getPrice());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("path", item.getPhoto());

        context.startActivity(intent);

    }


}
