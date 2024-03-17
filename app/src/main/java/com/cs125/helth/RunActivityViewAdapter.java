package com.cs125.helth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RunActivityViewAdapter extends ArrayAdapter<Run> {
    private final ArrayList<Run> runs;

    // View lookup cache
    private static class ViewHolder {
        TextView a_id;
        TextView u_id;
        TextView date;
        TextView total_time;
        TextView pace;
        TextView average_heart_rate;
        TextView total_distance;
    }

    public RunActivityViewAdapter(Context context, ArrayList<Run> runs) {
        super(context, R.layout.welcomelist_row, runs);
        this.runs = runs;
    }

    @SuppressLint("DefaultLocale")
    public String convertToTime(double value){
        int minutes = (int) value;
        int seconds = (int) ((value - minutes) * 60);

        // Format the pace in minute:second format
        return String.format("%d:%02d", minutes, seconds);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the movie item for this position
        Run run = runs.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.welcomelist_row, parent, false);
            viewHolder.date = convertView.findViewById(R.id.date);
            viewHolder.pace = convertView.findViewById(R.id.pace);
            viewHolder.total_distance = convertView.findViewById(R.id.distance);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.date.setText(String.valueOf(run.date));
        viewHolder.pace.setText(convertToTime(run.pace) + "/Mile");
        viewHolder.total_distance.setText(String.valueOf(run.total_distance) + " Miles");
        // Return the completed view to render on screen
        return convertView;
    }

}