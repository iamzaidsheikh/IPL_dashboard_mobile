package io.github.ipldashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

import io.github.ipldashboard.model.Match;
import io.github.ipldashboard.model.Team;

public class TeamActivity extends AppCompatActivity {

    private ImageView teamColorBannerImageView;
    private TextView teamNameTextView;
    private TextView totalWinsTextView;
    private TextView vs1TextView;
    private TextView opp1TextView;
    private TextView matchResultTextView;
    private TextView date1TextView;
    private TextView venueTextView;
    private TextView manOfTheMatchTextView;
    private ImageView winLossIndicator1ImageView;
    private TextView vs2TextView;
    private TextView opp2TextView;
    private TextView date2TextView;
    private ImageView winLossIndicator2ImageView;
    private TextView vs3TextView;
    private TextView opp3TextView;
    private TextView date3TextView;
    private ImageView winLossIndicator3ImageView;

    private RequestQueue queue;
    private String teamName;
    private static ArrayList<Match> matchArrayList = new ArrayList<>();
    private Team team;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_activity_layout);

        //Log.i("ONCREATE","OnCreate ran");

        Intent intent = getIntent();
        teamName = intent.getStringExtra("teamName");
        //Log.i("TEAM_NAME",teamName);

        queue = Volley.newRequestQueue(this);

        teamColorBannerImageView = findViewById(R.id.teamColorBanner_teamActivity);
        teamNameTextView = findViewById(R.id.teamNameTextView_teamActivity);
        totalWinsTextView = findViewById(R.id.totalWinsTextView_teamActivity);
        vs1TextView = findViewById(R.id.vs1TextView_teamActivity);
        opp1TextView = findViewById(R.id.opponent1TextView_teamActivity);
        matchResultTextView = findViewById(R.id.matchResultTextView_teamActivity);
        date1TextView = findViewById(R.id.date1TextView_teamActivity);
        venueTextView = findViewById(R.id.matchVenueTextView_teamActivity);
        manOfTheMatchTextView = findViewById(R.id.manOfTheMatchTextView_teamActivity);
        winLossIndicator1ImageView = findViewById(R.id.winLossIndicatorImageView1_teamActivity);
        vs2TextView = findViewById(R.id.vs2TextView_teamActivity);
        opp2TextView = findViewById(R.id.opponent2TextView_teamActivity);
        date2TextView = findViewById(R.id.date2TextView_teamActivity);
        winLossIndicator2ImageView = findViewById(R.id.winLossIndicatorImageView2_teamActivity);
        vs3TextView = findViewById(R.id.vs3TextView_teamActivity);
        opp3TextView = findViewById(R.id.opponent3TextView_teamActivity);
        date3TextView = findViewById(R.id.date3TextView_teamActivity);
        winLossIndicator3ImageView = findViewById(R.id.winLossIndicatorImageView3_teamActivity);

        parseJSON();



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void parseJSON(){
        String url = "http://192.168.0.174:8080/team/" + teamName;

        Log.i("URL",url);

        matchArrayList.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    try {
                            team  = new Team();
                            team.setId(Long.parseLong(response.getString("id")));
                            team.setTeamName(response.getString("teamName"));
                            team.setTotalMatches(Long.parseLong(response.getString("totalMatches")));
                            team.setTotalWins(Long.parseLong(response.getString("totalWins")));

                            JSONArray matchesList = response.getJSONArray("matches");

                            for(int i=0;i<matchesList.length();i++){
                                JSONObject jsonObject = matchesList.getJSONObject(i);
                                Match match = new Match();
                                match.setTeam1(jsonObject.getString("team1"));
                                match.setTeam2(jsonObject.getString("team2"));
                                match.setMatchWinner(jsonObject.getString("matchWinner"));
                                match.setResult(jsonObject.getString("result"));
                                match.setResultMargin(jsonObject.getString("resultMargin"));
                                match.setDate(LocalDate.parse(jsonObject.getString("date")));
                                match.setPlayerOfMatch(jsonObject.getString("playerOfMatch"));
                                match.setVenue(jsonObject.getString("venue"));

                                matchArrayList.add(match);

                            }
                            
                            fillTeamData();
                            fillLatestMatchData();
                            fillMiniCards();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        this.team.setTeamName("Default Team Name");
                    }
                }, error -> Log.i("ERROR","Couldn't parse JSON"));

        queue.add(jsonObjectRequest);

    }

    private void fillMiniCards() {
        Match match2 = matchArrayList.get(1);
        String opponent2 = (teamName.matches(match2.getTeam1())) ? match2.getTeam2() : match2.getTeam1();
        opp2TextView.setText(opponent2);
        date2TextView.setText(match2.getDate().toString());
        boolean didTeam2Win = teamName.matches(match2.getMatchWinner());
        winLossIndicator2ImageView.setImageResource(didTeam2Win ? R.color.victory : R.color.defeat);

        Match match3 = matchArrayList.get(2);
        String opponent3 = (teamName.matches(match3.getTeam1())) ? match3.getTeam2() : match3.getTeam1();
        opp3TextView.setText(opponent3);
        date3TextView.setText(match3.getDate().toString());
        boolean didTeam3Win = teamName.matches(match3.getMatchWinner());
        winLossIndicator3ImageView.setImageResource(didTeam3Win ? R.color.victory : R.color.defeat);
    }

    private void fillLatestMatchData() {
        Match latestMatch = matchArrayList.get(0);
        matchResultTextView.setText(latestMatch.getMatchWinner() + " win by " + latestMatch.getResultMargin() + " " + latestMatch.getResult());
        date1TextView.setText(latestMatch.getDate().toString());
        venueTextView.setText(latestMatch.getVenue());
        manOfTheMatchTextView.setText("Man of the match : " + latestMatch.getPlayerOfMatch());
        Log.i("Team1",latestMatch.getTeam1());
        Log.i("Team2",latestMatch.getTeam2());
        String opponentTeam = (teamName.matches(latestMatch.getTeam1())) ? latestMatch.getTeam2() : latestMatch.getTeam1();
        opp1TextView.setText(opponentTeam);
        boolean didTeamWin = teamName.matches(latestMatch.getMatchWinner());
        winLossIndicator1ImageView.setImageResource(didTeamWin ? R.color.victory : R.color.defeat);
    }

    private void fillTeamData() {
        teamColorBannerImageView.setImageResource(TeamColors.getTeamColorsMap().get(team.getTeamName()));
        teamNameTextView.setText(team.getTeamName());
        totalWinsTextView.setText("Total Wins : " + String.valueOf(team.getTotalWins()));
    }

}