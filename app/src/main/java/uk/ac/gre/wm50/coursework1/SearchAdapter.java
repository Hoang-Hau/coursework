package uk.ac.gre.wm50.coursework1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {


    private ArrayList<SearchModel> searchModelArrayList;

    // creating a constructor for our variables.
    public SearchAdapter(ArrayList<SearchModel> searchModelArrayList, Context context) {
        this.searchModelArrayList = searchModelArrayList;
    }

    public void filterList(ArrayList<SearchModel> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        searchModelArrayList = filterlist;
        // below line is to notify our adapter

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        return new SearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SearchModel model = searchModelArrayList.get(position);
        holder.searchid.setText(model.getSearchId());
        holder.searchname.setText(model.getSearchName());
        holder.searchdestination.setText(model.getSearchDestination());
        holder.searchdate.setText(model.getSearchDate());
        holder.searchrisk.setText(model.getSearchRisk());
        holder.searchdescription.setText(model.getSearchDescription());


    }

    @Override
    public int getItemCount() {
        return searchModelArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView searchid;
        private TextView searchname;
        private TextView searchdestination;
        private TextView searchdate;
        private TextView searchrisk;
        private TextView searchdescription;

        public MyViewHolder (@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            searchid = itemView.findViewById(R.id.trip_id_txt);
            searchname = itemView.findViewById(R.id.trip_name_txt);
            searchdestination = itemView.findViewById(R.id.trip_destination_txt);
            searchdate = itemView.findViewById(R.id.trip_date_txt);
            searchrisk = itemView.findViewById(R.id.trip_risk_txt);
            searchdescription = itemView.findViewById(R.id.trip_description_txt);
        }

    }
}
