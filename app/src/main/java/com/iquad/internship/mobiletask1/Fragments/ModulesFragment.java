package com.iquad.internship.mobiletask1.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iquad.internship.mobiletask1.Adapters.ModulesAdapter;
import com.iquad.internship.mobiletask1.Classes.Module;
import com.iquad.internship.mobiletask1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModulesFragment extends Fragment {

    private RecyclerView modulesRecyclerView;
    private ModulesAdapter modulesAdapter;

    private static final String API_URL = "https://lts.madrasatie.com/api/imperium/get_class_icons?schoolID=37&username=S001";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modules, container, false);

        modulesRecyclerView = view.findViewById(R.id.modulesRecyclerView);
        modulesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        modulesAdapter = new ModulesAdapter();
        modulesRecyclerView.setAdapter(modulesAdapter);

        fetchModulesData(modulesAdapter);

        return view;
    }

    private void fetchModulesData(ModulesAdapter modulesAdapter) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray modulesArray = jsonObject.getJSONObject("data")
                            .getJSONObject("data")
                            .getJSONArray("modules");

                    List<Module> moduleList = new ArrayList<>();
                    for (int i = 0; i < modulesArray.length(); i++) {
                        JSONObject moduleObject = modulesArray.getJSONObject(i);
                        String id = moduleObject.getString("id");
                        int status = moduleObject.getInt("status");
                        String name = moduleObject.getString("name");
                        moduleList.add(new Module(id, status, name));
                    }

                    System.out.println("Module List Size: " + moduleList.size());

                    Log.d("ModuleData", modulesArray.toString()); // Debug log to check the retrieved modules data

                    List<Module> moduleList2 = new ArrayList<>();
                    for (int i = 0; i < modulesArray.length(); i++) {
                        JSONObject moduleObject = modulesArray.getJSONObject(i);
                        String id = moduleObject.getString("id");
                        int status = moduleObject.getInt("status");
                        String name = moduleObject.getString("name");
                        moduleList2.add(new Module(id, status, name));
                    }

                    Collections.sort(moduleList, new Comparator<Module>() {
                        @Override
                        public int compare(Module module1, Module module2) {
                            return Integer.compare(module2.getStatus(), module1.getStatus());
                        }
                    });

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("ModuleList", moduleList2.toString());
                            modulesAdapter.setModuleList(moduleList2);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}