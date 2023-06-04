package com.iquad.internship.mobiletask1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iquad.internship.mobiletask1.Adapters.ModulesAdapter;
import com.iquad.internship.mobiletask1.Classes.Module;

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

public class ModuleListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ModulesAdapter modulesAdapter;

    private static final String API_URL = "https://lts.madrasatie.com/api/imperium/get_class_icons?schoolID=37&username=S001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);

        recyclerView = findViewById(R.id.modulesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modulesAdapter = new ModulesAdapter();
        recyclerView.setAdapter(modulesAdapter);

        fetchModulesData();
    }

    private void fetchModulesData() {
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

                    // sorting the list based on status (active modules first)
                    Collections.sort(moduleList, new Comparator<Module>() {
                        @Override
                        public int compare(Module module1, Module module2) {
                            return Integer.compare(module2.getStatus(), module1.getStatus());
                        }
                    });

                    // setting the adapter with the module list on the UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("ModuleList", moduleList.toString());
                            modulesAdapter.setModuleList(moduleList);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}