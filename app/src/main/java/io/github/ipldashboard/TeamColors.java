package io.github.ipldashboard;

import java.util.HashMap;

public class TeamColors {
    static TeamColors teamColors = new TeamColors();

    private static HashMap<String,Integer> teamColorsMap;

    private TeamColors(){

        teamColorsMap = new HashMap<>();

        teamColorsMap.put("Kolkata Knight Riders",R.color.kolkata_knight_riders);
        teamColorsMap.put("Chennai Super Kings",R.color.chennai_super_kings);
        teamColorsMap.put("Mumbai Indians",R.color.mumbai_indians);
        teamColorsMap.put("Rajasthan Royals",R.color.rajasthan_royals);
        teamColorsMap.put("Punjab Kings",R.color.punjab_kings);
        teamColorsMap.put("Royal Challengers Bangalore",R.color.royal_challengers_bangalore);
        teamColorsMap.put("Sunrisers Hyderabad",R.color.sunrisers_hyderabad);
        teamColorsMap.put("Delhi Capitals",R.color.delhi_capitals);
        teamColorsMap.put("Pune Warriors",R.color.pune_warriors);
        teamColorsMap.put("Rising Pune Supergiants",R.color.rising_pune_supergiants);
        teamColorsMap.put("Kochi Tuskers Kerala",R.color.kochi_tuskers_kerala);
        teamColorsMap.put("Gujarat Lions",R.color.gujarat_lions);

    }

    public static HashMap<String, Integer> getTeamColorsMap() {
        return teamColorsMap;
    }

    public static TeamColors getTeamColors() {
        return teamColors;
    }
}
