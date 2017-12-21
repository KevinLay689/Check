package com.example.kevinlay.check.alternative_home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.models.User;

import java.util.List;

/**
 * Created by kevinlay on 12/21/17.
 */

public class AlternativeHomeAdapter extends RecyclerView.Adapter<AlternativeHomeAdapter.AhViewHolder> {

    private List<User> users;

    public AlternativeHomeAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public AhViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row, parent, false);

        return new AhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AhViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class AhViewHolder extends RecyclerView.ViewHolder {
        public AhViewHolder(View itemView) {
            super(itemView);
        }
    }
}
