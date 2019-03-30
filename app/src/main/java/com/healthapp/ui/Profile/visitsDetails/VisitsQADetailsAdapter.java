package com.healthapp.ui.Profile.visitsDetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthapp.R;
import com.healthapp.Retrofit.LastVisitsDetails;


import java.util.List;

public class VisitsQADetailsAdapter extends RecyclerView.Adapter<VisitsQADetailsAdapter.MyViewHolder> {
    List<LastVisitsDetails> lastVisitsModelList;

    public void setLastVisitsModelList( List<LastVisitsDetails> lastVisitsModelList ) {
        this.lastVisitsModelList = lastVisitsModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i ) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_all_visits_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull MyViewHolder myViewHolder, int position ) {
        LastVisitsDetails lastVisits = lastVisitsModelList.get(position);
        myViewHolder.question.setText(lastVisits.getName());
        myViewHolder.answer.setText(lastVisits.getAnswer());

    }

    @Override
    public int getItemCount() {
        return lastVisitsModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, answer;

        public MyViewHolder( @NonNull View itemView ) {
            super(itemView);
            question = itemView.findViewById(R.id.unit_name);
            answer = itemView.findViewById(R.id.visit_num);

        }
    }
}
