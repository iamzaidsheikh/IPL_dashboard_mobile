package io.github.ipldashboard;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.ipldashboard.controller.TeamNamesAdapter;
import io.github.ipldashboard.model.Team;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private RecyclerView recyclerView;
    private static ArrayList<Team> teams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.homeRecyclerView);

        queue = Volley.newRequestQueue(this);

        parseJSON();


    }


    private void parseJSON(){
        String url = "http://192.168.0.174:8080/team";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, response -> {

                    try {

                        for(int i=0;i<response.length();i++){
                            JSONObject teamDetails = response.getJSONObject(i);
                            Team team = new Team();
                            team.setId(Long.parseLong(teamDetails.getString("id")));
                            team.setTeamName(teamDetails.getString("teamName"));
                            team.setTotalMatches(Long.parseLong(teamDetails.getString("totalMatches")));
                            team.setTotalWins(Long.parseLong(teamDetails.getString("totalWins")));

                            this.teams.add(team);

                            //Log.i("Info","Added team "+team.getTeamName()+" successfully");
                        }

                        populateRecyclerView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //TODO : Create an error dialog
                    }
                }, error -> Log.i("ERROR","Couldn't parse JSON"));

        queue.add(jsonArrayRequest);

    }

    private void populateRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        TeamNamesAdapter teamNamesAdapter = new TeamNamesAdapter(teams);
        recyclerView.setAdapter(teamNamesAdapter);
    }


}