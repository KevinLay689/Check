package com.example.kevinlay.check.alternative_home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.database.DatabaseObject;
import com.example.kevinlay.check.models.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kevinlay on 12/21/17.
 */

public class AlternativeHomeAdapter extends RecyclerView.Adapter<AlternativeHomeAdapter.AhViewHolder> {

    private List<User> users;
    private OnItemClicked onItemClicked;

    public AlternativeHomeAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public AhViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row, parent, false);

        return new AhViewHolder(view);
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(AhViewHolder holder, final int position) {
        if(users.get(position).getProfilePic().length() > 1 && users.get(position).getPartner().length() < 1) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 30;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            byte[] decodedString = Base64.decode(users.get(position).getProfilePic(), Base64.DEFAULT);
            //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            InputStream is = new ByteArrayInputStream(decodedString);
            Bitmap decodedByte = BitmapFactory.decodeStream(is, null, options);
            Bitmap.createScaledBitmap(decodedByte, 100, 100, false);
            holder.mFreeProfile.setImageBitmap(decodedByte);
            decodedByte = null;

            holder.mFreeName.setText(users.get(position).getFirstName());
            holder.mFreeMajor.setText(users.get(position).getMajor());
            holder.mFreeStart.setText(DatabaseObject.clockConversion(users.get(position).getTimeStart()));
            holder.mFreeEnd.setText(DatabaseObject.clockConversion(users.get(position).getTimeEnd()));

            holder.mFreeRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicked.onItemClick(position);
                }
            });

        }
    }

    public void setOnClick(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class AhViewHolder extends RecyclerView.ViewHolder {

        private ImageView mFreeProfile;
        private TextView mFreeName, mFreeStart, mFreeEnd, mFreeMajor;
        private Button mFreeRequest;

        public AhViewHolder(View itemView) {
            super(itemView);

            mFreeProfile = (ImageView) itemView.findViewById(R.id.freeProfiles);
            mFreeName = (TextView)itemView.findViewById(R.id.freeName);
            mFreeStart = (TextView)itemView.findViewById(R.id.freeStartTime);
            mFreeEnd = (TextView)itemView.findViewById(R.id.freeEndTime);
            mFreeMajor = (TextView)itemView.findViewById(R.id.freeMajor);
            mFreeRequest = (Button) itemView.findViewById(R.id.freeRequest);
        }
    }
}
