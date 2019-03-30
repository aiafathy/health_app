package com.healthapp.ui.Profile.visitsFormType;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthapp.R;
import com.healthapp.Retrofit.LastVisits;
import com.healthapp.Retrofit.LastVisitsFormType;
import com.healthapp.ui.Profile.ProfileFragment.AllVisitsAdapter;

import java.util.List;

public class VisitsTypeFormAdapter extends RecyclerView.Adapter<VisitsTypeFormAdapter.MyViewHolder> {
    List<LastVisitsFormType> lastVisitsModelList;
    AllVisitsAdapter.LastVisitsClick lastVisitsClick;


    public VisitsTypeFormAdapter( AllVisitsAdapter.LastVisitsClick lastVisitsClick ) {
        this.lastVisitsClick = lastVisitsClick;
    }

    public void setLastVisitsModelList( List<LastVisitsFormType> lastVisitsModelList ) {
        this.lastVisitsModelList = lastVisitsModelList;
    }

    public interface LastVisitsClick {
        void onLastVisitsClick( int pos );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i ) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_all_visits_item, viewGroup, false);
        return new MyViewHolder(view, lastVisitsClick);
    }

    @Override
    public void onBindViewHolder( @NonNull MyViewHolder myViewHolder, int position ) {
        LastVisitsFormType lastVisits = lastVisitsModelList.get(position);
        myViewHolder.nameUnit.setText(lastVisits.getName());
        if (lastVisits.getTotal() == 1)
            myViewHolder.numVisits.setText("تمت زياراتها مرة واحدة");
        else if (lastVisits.getTotal() == 2)
            myViewHolder.numVisits.setText("تمت زياراتها مرتين");
        else
            myViewHolder.numVisits.setText("تمت زياراتها " + lastVisits.getTotal() + "  مرات  ");

    }

    @Override
    public int getItemCount() {
        return lastVisitsModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameUnit, numVisits;
        AllVisitsAdapter.LastVisitsClick lastVisitsClick;

        public MyViewHolder( @NonNull View itemView, AllVisitsAdapter.LastVisitsClick lastVisitsClick ) {
            super(itemView);
            this.lastVisitsClick = lastVisitsClick;
            nameUnit = itemView.findViewById(R.id.unit_name);
            numVisits = itemView.findViewById(R.id.visit_num);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick( View view ) {
            lastVisitsClick.onLastVisitsClick(getAdapterPosition());
        }
    }
}
