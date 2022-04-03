package com.example.securednotebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;
//to pass my view holder data
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    RealmResults<Note> notesList;

    public MyAdapter(Context context, RealmResults<Note> notesList) {
//create a constructor
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getDescription());
//format time from milliseconds
        String formatedTime = DateFormat.getDateTimeInstance().format(note.createdTime);
        holder.timeOutput.setText(formatedTime);
 //deleting a saved note
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu = new PopupMenu(context,view);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("DELETE")){
 //delete note
                          Realm realm =Realm.getDefaultInstance();
                          realm.beginTransaction();
                          note.deleteFromRealm();
                          realm.commitTransaction();
                            Toast.makeText(context,"Note deleted",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageoutput;
        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView. findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
            imageoutput = itemView.findViewById(R.id.imageoutput);
        }
    }
}
