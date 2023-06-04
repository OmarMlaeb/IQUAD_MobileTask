package com.iquad.internship.mobiletask1.Adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.iquad.internship.mobiletask1.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iquad.internship.mobiletask1.Classes.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModulesAdapter extends RecyclerView.Adapter<ModulesAdapter.ModuleViewHolder> {

    private List<Module> moduleList;

    private List<Integer> colors;

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
        this.colors = generateRandomColors(moduleList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = moduleList.get(position);
        holder.moduleNameTextView.setText(module.getName());
        holder.moduleStatusTextView.setText(module.getStatus() == 1 ? "Active" : "Inactive");

        /* int color = module.getStatus() == 1 ? Color.GREEN : Color.RED;
        holder.moduleNameTextView.setTextColor(color);
        holder.moduleStatusTextView.setTextColor(color); */

        int color = colors.get(position);
        holder.moduleNameTextView.setTextColor(color);
        holder.moduleStatusTextView.setTextColor(color);
    }

    private List<Integer> generateRandomColors(int count) {
        List<Integer> colors = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            colors.add(color);
        }
        return colors;
    }


    @Override
    public int getItemCount() {
        int itemCount = moduleList != null ? moduleList.size() : 0;
        Log.d("RecyclerView", "Item count: " + itemCount);
        return itemCount;
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView moduleNameTextView;
        TextView moduleStatusTextView;

        ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleNameTextView = itemView.findViewById(R.id.moduleNameTextView);
            moduleStatusTextView = itemView.findViewById(R.id.moduleStatusTextView);
        }
    }
}
