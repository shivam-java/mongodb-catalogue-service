package com.mongodb_catalogue_service.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.mongodb_catalogue_service.config.Constants;
import com.mongodb_catalogue_service.model.CarrerSummary;
import com.mongodb_catalogue_service.model.Player;
import com.mongodb_catalogue_service.model.Profile;
import com.mongodb_catalogue_service.model.stats.BattingStats;
import com.mongodb_catalogue_service.model.stats.BowlingStats;
import com.mongodb_catalogue_service.model.stats.Stats;
import com.mongodb_catalogue_service.model.stats.batting_stats.ODIMatches;
import com.mongodb_catalogue_service.model.stats.batting_stats.T20IMatches;
import com.mongodb_catalogue_service.model.stats.batting_stats.TestMatches;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DefaultPlayerService {

    @Autowired
    private ApplicationContext applicationContext;

    public List<Player> getPlayers() {
        List<Player> players = null;
        try {

            Resource battingPlayerResource = applicationContext.getResource("classpath:" + "default_data/playerBatting.json");
            Resource bowlingPlayerResource = applicationContext.getResource("classpath:" + "default_data/playerBowling.json");
            InputStream battingPlayerInputStream = battingPlayerResource.getInputStream();
            InputStream bowlingPlayerInputStream = bowlingPlayerResource.getInputStream();
            JsonReader jsonReaderBattingPlayer = new JsonReader(new InputStreamReader(battingPlayerInputStream));
            JsonReader jsonReaderBowlingPlayer = new JsonReader(new InputStreamReader(bowlingPlayerInputStream));
            while (jsonReaderBattingPlayer.hasNext() && jsonReaderBowlingPlayer.hasNext()) {

                players = buildPlayers(jsonReaderBattingPlayer, jsonReaderBowlingPlayer);
            }
        } catch (Exception exception) {
            log.error("Error Occurred While Json Parsing ");
            exception.printStackTrace();
        }

        return players;
    }

    public List<Player> buildPlayers(JsonReader jsonReaderBattingPlayer, JsonReader jsonReaderBowlingPlayer) {
        List<Player> players = new ArrayList<>();
        Gson gson = new Gson();
        JsonObject jsonObjectBattingPlayer = gson.fromJson(jsonReaderBattingPlayer, JsonObject.class);
        JsonObject jsonObjectBowlingPlayer = gson.fromJson(jsonReaderBowlingPlayer, JsonObject.class);
        Profile profile = buildProfile(jsonObjectBattingPlayer);
        CarrerSummary carrerSummary = buildCarrerSummary(jsonObjectBattingPlayer);
        Stats stats = buildStats(jsonObjectBattingPlayer,jsonObjectBowlingPlayer);
        Player player = Player.builder().
                profile(profile)
                .carrerSummary(carrerSummary)
                .stats(stats).build();
        players.add(player);
        Player bowler = buildBowler(jsonObjectBowlingPlayer);
        players.add(bowler);
        return players;


    }

    public Player buildBowler(JsonObject jsonObjectBowlingPlayer) {
        Profile profile = buildProfile(jsonObjectBowlingPlayer);
        CarrerSummary carrerSummary = buildCarrerSummary(jsonObjectBowlingPlayer);
        Stats stats = buildStats(null,jsonObjectBowlingPlayer);
        Player player = Player.builder().
                profile(profile)
                .carrerSummary(carrerSummary)
                .stats(stats).build();
        return player;


    }

    public Profile buildProfile(JsonObject playerObject) {
        JsonObject profile = (JsonObject) playerObject.get(Constants.PROFILE);
        JsonArray teams = profile.get(Constants.TEAMS).getAsJsonArray();
        List<String> teams_list = new ArrayList<>();
        for (JsonElement team : teams) {
            teams_list.add(team.getAsString());
        }
        return Profile.builder().
                name(profile.get(Constants.NAME).getAsString())
                .age(profile.get(Constants.AGE).getAsInt())
                .birthPlace(profile.get(Constants.BIRTH_PLACE).getAsString())
                .role(profile.get(Constants.ROLE).getAsString())
                .teams(teams_list)
                .country(profile.get(Constants.COUNTRY).getAsString()).
                build();

    }

    public CarrerSummary buildCarrerSummary(JsonObject playerObject) {
        JsonObject carrer_summary = (JsonObject) playerObject.get(Constants.CARRER_SUMMARY);

        return CarrerSummary.builder().carrerSummary(carrer_summary.get(Constants.CARRER_SUMMARY).getAsString()).build();

    }

    public Stats buildStats(JsonObject battingStats,JsonObject bowlingStats) {
        BattingStats battingstats=null;
        BowlingStats bowlingStats1=null;
        try {
            if(battingStats!=null) {
                JsonObject batterObject = (JsonObject) battingStats.get(Constants.STATS);
                JsonObject batting_stats = (JsonObject) batterObject.get(Constants.BATTING_STATS);
                battingstats = buildBattingStats(batting_stats);
            }
            if (bowlingStats!=null) {
                JsonObject bowlerObject = (JsonObject) bowlingStats.get(Constants.STATS);
                JsonObject bowling_stats = (JsonObject) bowlerObject.get(Constants.BOWLING_STATS);
                bowlingStats1 = buildBowlingStats(bowling_stats);
            }
            return Stats.builder().battingStats(battingstats).bowlingStats(
                    bowlingStats1
            ).build();
        }
        catch (Exception exception)
        {

        }
        return null;
    }

    public BattingStats buildBattingStats(JsonObject playerObject) {

        JsonObject test_matches = (JsonObject) playerObject.get(Constants.TEST_MATCHES);
        JsonObject odi_matches = (JsonObject) playerObject.get(Constants.ODI_MATCHES);
        JsonObject t20_matches = (JsonObject) playerObject.get(Constants.T20_MATCHES);
        TestMatches testMatches = buildTestMatches(test_matches);
        ODIMatches odiMatches = buildODIMatches(odi_matches);
        T20IMatches t20IMatches = buildT20IMatches(t20_matches);
        return BattingStats.builder().
                testMatches(testMatches).t20IMatches(t20IMatches).odiMatches(odiMatches).
                build();

    }
    public BowlingStats buildBowlingStats(JsonObject playerObject) {

        JsonObject test_matches = (JsonObject) playerObject.get(Constants.TEST_MATCHES);
        JsonObject odi_matches = (JsonObject) playerObject.get(Constants.ODI_MATCHES);
        JsonObject t20_matches = (JsonObject) playerObject.get(Constants.T20_MATCHES);
        com.mongodb_catalogue_service.model.stats.bowling_stats.TestMatches testMatches = buildTestMatchesBowler(test_matches);
        com.mongodb_catalogue_service.model.stats.bowling_stats.ODIMatches odiMatches = buildODIMatchesBowler(odi_matches);
        com.mongodb_catalogue_service.model.stats.bowling_stats.T20IMatches t20IMatches = buildT20MatchesBowler(t20_matches);
        return BowlingStats.builder().
                testMatches(testMatches).t20IMatches(t20IMatches).odiMatches(odiMatches).
                build();

    }

 


    public TestMatches buildTestMatches(JsonObject playerObject) {
        return TestMatches.builder().
                matches(playerObject.get(Constants.NO_OF_MATCHES).getAsInt())
                .avg(playerObject.get(Constants.AVG).getAsInt())
                .fifties(playerObject.get(Constants.FIFTIES).getAsInt())
                .hundreds(playerObject.get(Constants.HUNDREDS).getAsInt())
                .runs(playerObject.get(Constants.RUNS).getAsInt())
                .strikeRate(playerObject.get(Constants.STRIKE_RATE).getAsInt())
                .innings(playerObject.get(Constants.INNINGS).getAsInt()).build();

    }

    public com.mongodb_catalogue_service.model.stats.bowling_stats.TestMatches buildTestMatchesBowler(JsonObject playerObject) {
        return com.mongodb_catalogue_service.model.stats.bowling_stats.TestMatches.builder().
                matches(playerObject.get(Constants.NO_OF_MATCHES).getAsInt())
                .avg(playerObject.get(Constants.AVG).getAsDouble())
                .innings(playerObject.get(Constants.INNINGS).getAsInt())
                .economy(playerObject.get(Constants.ECONOMY).getAsDouble())
                .fiveWicketsHauls(playerObject.get(Constants.FIVE_WICKET_HAULS).getAsInt())
                .tenWicketsHauls(playerObject.get(Constants.TEN_WICKET_HAULS).getAsInt())
                .wickets(playerObject.get(Constants.WICKETS).getAsInt())
                .build();

    }

    public com.mongodb_catalogue_service.model.stats.bowling_stats.ODIMatches buildODIMatchesBowler(JsonObject playerObject) {
        return com.mongodb_catalogue_service.model.stats.bowling_stats.ODIMatches.builder().
                matches(playerObject.get(Constants.NO_OF_MATCHES).getAsInt())
                .avg(playerObject.get(Constants.AVG).getAsDouble())
                .innings(playerObject.get(Constants.INNINGS).getAsInt())
                .economy(playerObject.get(Constants.ECONOMY).getAsDouble())
                .fiveWicketsHauls(playerObject.get(Constants.FIVE_WICKET_HAULS).getAsInt())
                .wickets(playerObject.get(Constants.WICKETS).getAsInt())
                .build();

    }

    public com.mongodb_catalogue_service.model.stats.bowling_stats.T20IMatches buildT20MatchesBowler(JsonObject playerObject) {
        return com.mongodb_catalogue_service.model.stats.bowling_stats.T20IMatches.builder().
                matches(playerObject.get(Constants.NO_OF_MATCHES).getAsInt())
                .avg(playerObject.get(Constants.AVG).getAsDouble())
                .innings(playerObject.get(Constants.INNINGS).getAsInt())
                .economy(playerObject.get(Constants.ECONOMY).getAsDouble())
                .fiveWicketsHauls(playerObject.get(Constants.FIVE_WICKET_HAULS).getAsInt())
                .wickets(playerObject.get(Constants.WICKETS).getAsInt())
                .build();

    }

    public ODIMatches buildODIMatches(JsonObject playerObject) {
        return ODIMatches.builder().
                matches(playerObject.get(Constants.NO_OF_MATCHES).getAsInt())
                .avg(playerObject.get(Constants.AVG).getAsInt())
                .fifties(playerObject.get(Constants.FIFTIES).getAsInt())
                .hundreds(playerObject.get(Constants.HUNDREDS).getAsInt())
                .runs(playerObject.get(Constants.RUNS).getAsInt())
                .strikeRate(playerObject.get(Constants.STRIKE_RATE).getAsInt())
                .innings(playerObject.get(Constants.INNINGS).getAsInt()).build();

    }

    public T20IMatches buildT20IMatches(JsonObject playerObject) {
        return T20IMatches.builder().
                matches(playerObject.get(Constants.NO_OF_MATCHES).getAsInt())
                .avg(playerObject.get(Constants.AVG).getAsInt())
                .fifties(playerObject.get(Constants.FIFTIES).getAsInt())
                .hundreds(playerObject.get(Constants.HUNDREDS).getAsInt())
                .runs(playerObject.get(Constants.RUNS).getAsInt())
                .strikeRate(playerObject.get(Constants.STRIKE_RATE).getAsInt())
                .innings(playerObject.get(Constants.INNINGS).getAsInt()).build();

    }
}
