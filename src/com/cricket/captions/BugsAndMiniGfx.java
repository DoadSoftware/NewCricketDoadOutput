package com.cricket.captions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import com.cricket.containers.ImpactData;
import com.cricket.controller.IndexController;
import com.cricket.model.BatBallGriff;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Configuration;
import com.cricket.model.Event;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.MatchAllData;
import com.cricket.model.MatchStats.VariousStats;
import com.cricket.model.OverByOverData;
import com.cricket.model.Partnership;
import com.cricket.model.PerformanceBug;
import com.cricket.model.Player;
import com.cricket.model.Setup;
import com.cricket.model.Statistics;
import com.cricket.model.StatsType;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BugsAndMiniGfx 
{
	public String status = "",homecolor = "", awaycolor = "",WhichGroup = "",containerName = "", previous_sixes = "",today_sixes="",
			previous_fours = "",today_fours="",logoCategory = "",reviewData="";
	public int FirstPlayerId, rowId1 = 0, plyr_count=0, numberOfRows = 0,omo=0,whichTeam;
	public String WhichScoreCard, WhichProfile, WhichStyle, WhichType, 
			containerName_2 = "",containerName_3 = "",containerName_4 = "",logo_name = "" , color_name = "",
			logo_name1 = "" , color_name1 = "",Player_photo="", whichGFX = "",whichtype = "", phaseWiseScore = "",
			manhattanOrNot = "";
	public boolean isVisited = false;
	int fallOfWickets;
	public static int  index_Player =0;
	int rowId=0, omo_num=0,Which_Inning = 2;
	String cont_name = "",text_name = "",stats_text="",whichColor="";
	
	@JsonIgnore
	public List<PrintWriter> print_writers; 
	public Configuration config;
	public List<Bugs> bugs;
	public List<Team> Teams;
	public List<VariousText> VariousText;
	public List<HeadToHeadPlayer> headToHead;
	//public List<Bugs> bugs;
	public List<PerformanceBug> performanceBugs;
	public List<Tournament> past_tournament_stats;
	public List<Player> Players;
	
	public List<BatBallGriff> griff = new ArrayList<BatBallGriff>();
	public List<String> this_data_str = new ArrayList<String>();
	ArrayList<BestStats> bowler_data = new ArrayList<BestStats>();
	ArrayList<BestStats> batter_data = new ArrayList<BestStats>();
	public List<Tournament> this_series = new ArrayList<Tournament>();
	public List<BestStats> top_batsman_beststats = new ArrayList<BestStats>();
	public List<BestStats> top_bowler_beststats = new ArrayList<BestStats>();
	public List<OverByOverData> manhattan, manhattan2 = new ArrayList<OverByOverData>();
	public List<ImpactData> impactList = new ArrayList<ImpactData>();
	
	//public FallOfWicket fallOfWickets;
	public BattingCard battingCard;
	public BowlingCard bowlingCard;
	public Partnership partnership,partnership1;
	public VariousText variousText;
	public Inning inning;
	public Player player;
	public Statistics stat;
	public StatsType statsType;
	public List<Statistics> statistics;
	public List<StatsType> statsTypes;
	public List<BattingCard> playerCards;
	public LeagueTable leagueTable;
	
	@JsonIgnore
	public CricketService cricketService;
	
	public Team team;
	public Bugs bug;
	public PerformanceBug performanceBug;
	public Tournament tournament;
	
	Set<String> processedMatches = new HashSet<>();
	Map<String, HeadToHeadPlayer> playerMatchData = new HashMap<>();
	
	public BugsAndMiniGfx() {
		super();
	}
	
	public BugsAndMiniGfx(List<PrintWriter> print_writers, Configuration config, List<Bugs> bugs, List<PerformanceBug> performanceBugs, List<Team> teams, 
			List<VariousText> VariousText, CricketService cricketService, List<HeadToHeadPlayer> headToHead, List<Statistics> statistics, List<StatsType> statsTypes, 
			List<Tournament> past_tournament_stats,List<Player> players) {
		super();
		this.print_writers = print_writers;
		this.config = config;
		this.bugs = bugs;
		this.Teams = teams;
		this.VariousText = VariousText;
		this.cricketService = cricketService;
		this.headToHead = headToHead;
		this.performanceBugs = performanceBugs;
		this.statistics = statistics;
		this.statsTypes = statsTypes;
		this.past_tournament_stats = past_tournament_stats;
		this.Players = players;
	}
	
	public static String getMatchCode(String matchName) {

	    // Handle null or empty input safely
	    if (matchName == null || matchName.trim().isEmpty()) {
	        return "";
	    }

	    matchName = matchName.trim();

	    // Bypass special matches like semi-final, final, qualifier etc.
	    if (!matchName.toUpperCase().startsWith("MATCH")) {
	        return matchName;
	    }

	    try {
	        // Extract number from "MATCH 01", "MATCH 02" etc.
	        String numberPart = matchName.replaceAll("[^0-9]", "");

	        int num = Integer.parseInt(numberPart);

	        return "M" + num;

	    } catch (Exception e) {
	        // If parsing fails, return original value safely
	        return matchName;
	    }
	}
	public String populateDRSDecision(String whatToProcess, int whichSide, MatchAllData matchAllData) throws IOException {
		team = Teams.stream().filter(tm -> tm.getTeamId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
		if(team == null) {
			return "populateDRSDecision: team is returning NULL";
		}
		if(PopulateBugBody(whichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populatePerformanceBug(String whatToProcess, int whichSide, MatchAllData matchAllData) throws IOException {
		performanceBug = performanceBugs.stream().filter(p -> p.getBugId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
		System.out.println(performanceBug.toString());
		if(performanceBug == null) {
			return "performanceBug: performanceBug is returning NULL";
		}
		if(performanceBug.getFlag() != null) {
			team = Teams.stream().filter(tm -> tm.getTeamBadge().equalsIgnoreCase(performanceBug.getFlag())).findAny().orElse(null);
			if(team == null) {
				return "performanceBug: Flag in database is returning NULL";
			}
		}
		
		if(PopulateBugBody(whichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populateBowlerVsAllBatsman(String whatToProcess,int WhichSide,MatchAllData matchAllData) throws StreamReadException, DatabindException, FileNotFoundException, IOException {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		if(inning == null) {
			return "Current Inning NOT found in this match";
		}
		player = cricketService.getAllPlayer().stream().filter(plyr->plyr.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
		if(player == null) {
			return "Player not found in db";
		}
		team = Teams.stream().filter(tm->tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
		if(team == null) {
			return "Can't find team of the player";
		}
//		batter_data = CricketFunctions.getBowlerVsAllBat(Integer.valueOf(whatToProcess.split(",")[2]), inning.getInningNumber(), 
//				cricketService.getAllPlayer(), matchAllData);
		for(BestStats bs : batter_data) {
			System.out.println("NAME : "+bs.getPlayer().getFull_name()+" : BALLS : "+bs.getBalls()+" : RUNS : "+bs.getRuns());
		}
		if(populateMiniBody(WhichSide, whatToProcess.split(",")[0], matchAllData, inning.getInningNumber()) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populateBatStatsVsAllBowlers(String whatToProcess,int WhichSide,MatchAllData matchAllData) throws StreamReadException, DatabindException, FileNotFoundException, IOException {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		if(inning == null) {
			return "Current Inning NOT found in this match";
		}
		player = cricketService.getAllPlayer().stream().filter(plyr->plyr.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
		if(player == null) {
			return "Player not found in db";
		}
		team = Teams.stream().filter(tm->tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
		if(team == null) {
			return "Can't find team of the player";
		}
		bowler_data = CricketFunctions.getBatsmanRunsVsAllBowlers(Integer.valueOf(whatToProcess.split(",")[2]), inning.getInningNumber(), cricketService.getAllPlayer(), matchAllData);
		for(BestStats stat : bowler_data) {
			System.out.println(stat.getPlayer().getFull_name()+" BALLS : "+stat.getBalls() +" RUNS : "+stat.getRuns());
		}
		if(populateMiniBody(WhichSide, whatToProcess.split(",")[0], matchAllData, inning.getInningNumber()) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populateFourCounter(String whatToProcess,int WhichSide,MatchAllData matchAllData) throws CloneNotSupportedException, IOException {
		
		this_data_str = new ArrayList<String>();
		
		if(config.getBroadcaster().equalsIgnoreCase(Constants.MPL)) {
//			today_fours = String.valueOf(CricketFunctions.extracttournamentFoursAndSixes("CURRENT_MATCH_DATA",tournament_matches, matchAllData, null).getTournament_fours());
			today_fours = String.valueOf(CricketFunctions.extracttournamentFoursAndSixesData("CURRENT_MATCH_DATA", headToHead, matchAllData, null).getTournament_fours());
			
		}else {
			today_fours = String.valueOf(CricketFunctions.extracttournamentFoursAndSixesData("CURRENT_MATCH_DATA", headToHead, matchAllData, null).getTournament_fours());
			
		}
		
		System.out.println("today_fours = " + today_fours);
		System.out.println("previous_fours = " + previous_fours);
		
		if(Integer.valueOf(today_fours) > 0 && matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventType().equalsIgnoreCase(CricketUtil.FOUR)) {
			if(matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventWasABoundary() != null && 
					matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventWasABoundary().equalsIgnoreCase(CricketUtil.YES)) {
				
				today_fours = String.valueOf(Integer.valueOf(today_fours));
			}
		}
		
		if(config.getBroadcaster().equalsIgnoreCase(Constants.MPL)) {
//			this_data_str.add(CricketFunctions.hundredsTensUnits(String.valueOf(Integer.valueOf(today_fours))));
//			if(WhichSide == 1) {
//				String new_four_value = String.valueOf((Integer.valueOf(today_fours) + 1));
//				this_data_str.add(CricketFunctions.hundredsTensUnits(new_four_value));
//			}
			this_data_str.add(CricketFunctions.hundredsTensUnits(String.valueOf(Integer.valueOf(previous_fours) + Integer.valueOf(today_fours))));
			if(WhichSide == 1) {
				String new_four_value = String.valueOf((Integer.valueOf(previous_fours) + Integer.valueOf(today_fours) + 1));
				this_data_str.add(CricketFunctions.hundredsTensUnits(new_four_value));
			}
		}else {
			this_data_str.add(CricketFunctions.hundredsTensUnits(String.valueOf(Integer.valueOf(previous_fours) + Integer.valueOf(today_fours))));
			if(WhichSide == 1) {
				String new_four_value = String.valueOf((Integer.valueOf(previous_fours) + Integer.valueOf(today_fours) + 1));
				this_data_str.add(CricketFunctions.hundredsTensUnits(new_four_value));
			}
		}
		
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populateCounter(String whatToProcess,int WhichSide,MatchAllData matchAllData) throws CloneNotSupportedException, IOException {
		
		this_data_str = new ArrayList<String>();
		today_sixes = String.valueOf(CricketFunctions.extracttournamentFoursAndSixesData("CURRENT_MATCH_DATA", headToHead, matchAllData, null).getTournament_sixes());
		
		System.out.println("today_sixes = " + today_sixes);
		System.out.println("previous_sixes = " + previous_sixes);
		
		if(Integer.valueOf(today_sixes) > 0 && matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventType().equalsIgnoreCase(CricketUtil.SIX)) {
			if(matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventWasABoundary() != null && 
					matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventWasABoundary().equalsIgnoreCase(CricketUtil.YES)) {
				
				today_sixes = String.valueOf(Integer.valueOf(today_sixes));
			}
		}
		
		this_data_str.add(CricketFunctions.hundredsTensUnits(String.valueOf(Integer.valueOf(previous_sixes) + Integer.valueOf(today_sixes))));
		if(WhichSide == 1) {
			String new_six_value = String.valueOf((Integer.valueOf(previous_sixes) + Integer.valueOf(today_sixes) + 1));
			this_data_str.add(CricketFunctions.hundredsTensUnits(new_six_value));
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populateGriff(String whatToProcess,int WhichSide,MatchAllData matchAllData) throws StreamReadException, DatabindException, FileNotFoundException, IOException {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		if(inning == null) {
			return "populateBatThisMatch: Current Inning NOT found in this match";
		}
		
		player = cricketService.getAllPlayer().stream().filter(plyr ->plyr.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
		if(player == null) {
			return "player not found";
		}
		
		team = Teams.stream().filter(tm->tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
		if(team == null) {
			return "Can't find team of the player";
		}
		
		switch (whatToProcess.split(",")[0]) {
		case Constants.NPL: case Constants.MPL:case Constants.BENGAL_T20: case Constants.APL: case Constants.LEGENDS:
			break;

		default:
			if(whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F1")) {
				griff = CricketFunctions.getBatBallGriff(player, CricketUtil.BATSMAN, player.getPlayerId(), team, headToHead, matchAllData);
			}else if(whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F2")) {
				griff =  CricketFunctions.getBatBallGriff(player, CricketUtil.BOWLER, player.getPlayerId(), team, headToHead, matchAllData);
			}
			if(griff == null) {
				return "Griff is null";
			}
			break;
		}
		
		if(populateMiniBody(WhichSide, whatToProcess.split(",")[0], matchAllData, inning.getInningNumber()) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}

	public String populateL3rdThisSeriesPowerPlay(String whatToProcess, int WhichSide, MatchAllData matchAllData) {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		if(inning == null) {
			return "populateBatThisMatch: Current Inning NOT found in this match";
		}
		team = Teams.stream().filter(tm->tm.getTeamId() == inning.getBattingTeamId()).findAny().orElse(null);
		if(team == null) {
			return "Can't find team of the player";
		}
		if(populateMiniThiSeiesBody(WhichSide, whatToProcess.split(",")[0], matchAllData, inning.getInningNumber()) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}

	private String populateMiniThiSeiesBody(int WhichSide, String string, MatchAllData matchAllData, int inningNumber) {
		int row_id=0;
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
				+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + team.getTeamBadge() + " \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
				+ "$Batting$txt_FirstName*GEOM*TEXT SET " + team.getTeamName1() + " \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
				+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
		
		for(int i=1; i<=13; i++) {
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
					+ "$Batting$Row" + i + "*ACTIVE SET 0\0", print_writers);
		}
		for(String str :IndexController.this_seriesPowerplay.get(inning.getBattingTeamId())) {
			row_id++;
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
					+ "$Batting$DataGrp*FUNCTION*Grid*num_row SET " + row_id + " \0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
					+ "$Batting$Row" + row_id + "*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
					+ "$Batting$Row" + row_id + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
					+ "$Batting$Row" + row_id + "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + Teams.get(Integer.valueOf(str.split(", v ")[1].trim())-1).getTeamName3() + " \0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
					+ "$Batting$Row" + row_id + "$Players_Dehighlight$fig_Out*GEOM*TEXT SET ("+ str.split(",")[1]+")\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
					+ "$Batting$Row" + row_id + "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + str.split(",")[0]+"\0", print_writers);
		}
		row_id++;
		String this_match =CricketFunctions.getFirstPowerPlayScore(matchAllData, inningNumber, matchAllData.getEventFile().getEvents());
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
				+ "$Batting$DataGrp*FUNCTION*Grid*num_row SET " + row_id + " \0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
				+ "$Batting$Row" + row_id + "*ACTIVE SET 1\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
				+ "$Batting$Row" + row_id + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
				+ "$Batting$Row" + row_id + "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + Teams.get(inning.getBowlingTeamId()-1).getTeamName3() + " \0", print_writers);
		int over = 0;
		
//		Double over = (((inning.getTotalOvers()*6)+inning.getTotalBalls()) > CricketFunctions.getBallCountStartAndEndRange(matchAllData, inning).get(1) ? 
//				CricketFunctions.getBallCountStartAndEndRange(matchAllData, inning).get(1) : Double.valueOf(CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls())));

		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
				+ "$Batting$Row" + row_id + "$Players_Highlight$fig_Out*GEOM*TEXT SET "+"("+ over + ")\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
				+ "$Batting$Row" + row_id + "$Players_Highlight$fig_Runs*GEOM*TEXT SET " + this_match.split(",")[0]+"\0", print_writers);
		return Constants.OK;
	}

	public String populateBugReview(String whatToProcess,int WhichSide,MatchAllData matchAllData) throws IOException {
		
		reviewData = whatToProcess.split(",")[3].toUpperCase();
		team = Teams.stream().filter(tm->tm.getTeamId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
		if(team == null) {
			return "Can't find team of the player";
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populatePopup(String whatToProcess, int whichSide, MatchAllData matchAllData) throws IOException{
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		if(inning == null) {
			return "inning is null";
		}
		switch (whatToProcess.split(",")[0]) {
		case "Control_Shift_U":
			battingCard = inning.getBattingCard().stream().filter(bc->bc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
			if(battingCard == null) {
				return ": player [" + battingCard.getPlayer().getFull_name() + "] is not present in batting card";
			}
			break;

		case "Control_Shift_V":
			bowlingCard = inning.getBowlingCard().stream().filter(boc->boc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
			if(bowlingCard == null) {
				return ": player [" + bowlingCard.getPlayer().getFull_name() + "] is not present in bowlingCard";
			}
			break;
		}
		if(PopulateBugBody(whichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String populatePopupChangeOn(String whatToProcess, int whichSide, MatchAllData matchAllData){
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		if(inning == null) {
			return "inning is null";
		}
		switch (whatToProcess.split(",")[0]) {
		case "Control_Shift_U_change_on":
			battingCard = inning.getBattingCard().stream().filter(bc->bc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
			if(battingCard == null) {
				return ": player [" + battingCard.getPlayer().getFull_name() + "] is not present in batting card";
			}
			break;

		case "Control_Shift_V_change_on":
			bowlingCard = inning.getBowlingCard().stream().filter(boc->boc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
			if(bowlingCard == null) {
				return ": player [" + bowlingCard.getPlayer().getFull_name() + "] is not present in bowlingCard";
			}
			break;
		}
		if(populatePopupSubSideData(whichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populatePointsTable(String whatToProcess, MatchAllData matchAllData,int WhichSide) throws ParseException, JAXBException, InterruptedException, 
		StreamReadException, DatabindException, FileNotFoundException, IOException
	{
		if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + WhichGroup + CricketUtil.XML_EXTENSION).exists()) {
			leagueTable = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + WhichGroup + CricketUtil.XML_EXTENSION));
		}
		
		if(leagueTable == null) {
			return "populatePointsTable : League Table is null";
		}
		
		if(populateMiniBody(WhichSide, whatToProcess.split(",")[0],matchAllData, 0) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populateWicketSequencing(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException
	{
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			status = "populateWicketSequencing match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
			if(inning == null) {
				return "populateWicketSequencing Inning is null";
			}
			
			player = CricketFunctions.getPlayerFromMatchData(Integer.valueOf(whatToProcess.split(",")[2]), matchAllData);
			battingCard = inning.getBattingCard().stream().filter(bc->bc.getPlayerId() == player.getPlayerId()).findAny().orElse(null);
			team = Teams.stream().filter(tm -> tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
			if(team == null) {
				return "populateWicketSequencing: Team id [" + battingCard.getPlayer().getTeamId() + "] from database is returning NULL";
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String populateBowlerWicketSequencing(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException
	{
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			status = "populateWicketSequencing match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
			if(inning == null) {
				return "populateWicketSequencing Inning is null";
			}
			player = CricketFunctions.getPlayerFromMatchData(Integer.valueOf(whatToProcess.split(",")[2]), matchAllData);			
			
			battingCard = inning.getBattingCard().stream().filter(bc->bc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[4])).findAny().orElse(null);
			
			team = Teams.stream().filter(tm -> tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
			if(team == null) {
				return "populateWicketSequencing: Team id [" + battingCard.getPlayer().getTeamId() + "] from database is returning NULL";
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String bugsDismissal(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException {
		
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			return "bugsDismissal match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
			if(inning == null) {
				return "bugsDismissal Inning is null";
			}
			battingCard = inning.getBattingCard().stream().filter(bc -> bc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
			if(battingCard == null) {
				return "bugsDismissal Batting Card is null";
			}
			team = Teams.stream().filter(tm -> tm.getTeamId() == battingCard.getPlayer().getTeamId()).findAny().orElse(null);
			if(team == null) {
				return "bugsDismissal: Team id [" + battingCard.getPlayer().getTeamId() + "] from database is returning NULL";
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String bugsover(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException {
		
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		if(inning == null) {
			return "populateBatScore Inning is null";
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String bugstape(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException {
		
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		
		if(inning == null) {
			return "populateBatScore Inning is null";
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String bugsPlayerOfMatch(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException {
		
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String bugsThirdUmpire(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException {
		
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String bugsCurrPartnership(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws Exception {
		
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			return "bugsCurrPartnership match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "bugsCurrPartnership Inning is null";
			}
			
//			partnership = inning.getPartnerships().stream().filter(pship -> pship.getPartnershipNumber() == 
//					inning.getPartnerships().get(inning.getPartnerships().size()-1).getPartnershipNumber()).findAny().orElse(null);
			
			List<Partnership > part = CricketFunctions.ConcussedPartnership(matchAllData.getMatch(), inning.getInningNumber());
			partnership = part.get(part.size()-1);
			
//			partnership = part.stream().filter(pship -> pship.getPartnershipNumber() == 
//					part.get(part.size()-1).getPartnershipNumber()).findAny().orElse(null);
			
			for(BattingCard bc : inning.getBattingCard()) {
				if(bc.getPlayerId() == partnership.getFirstBatterNo()) {
					partnership.setFirstPlayer(bc.getPlayer());
				}
				if(bc.getPlayerId() == partnership.getSecondBatterNo()) {
					partnership.setSecondPlayer(bc.getPlayer());
				}
			}
			if(partnership == null) {
				return "bugsCurrPartnership Partnership is null";
			}
			team = Teams.stream().filter(tm -> tm.getTeamId() == inning.getBattingTeamId()).findAny().orElse(null);
			if(team == null) {
				return "bugsCurrPartnership: Team id [" + inning.getBattingTeamId() + "] from database is returning NULL";
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String bugMultiPartnership(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws Exception {
		
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			return "bugMultiPartnership match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
			if(inning == null) {
				return "bugMultiPartnership Inning is null";
			}
			
			//partnership = inning.getPartnerships().stream().filter(pship -> pship.getPartnershipNumber() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
			List<Partnership > part = CricketFunctions.ConcussedPartnership(matchAllData.getMatch(), inning.getInningNumber());
			partnership = part.get(Integer.valueOf(whatToProcess.split(",")[2]) - 1);
			partnership1 = part.get(part.size()-1);
						
			for(BattingCard bc : inning.getBattingCard()) {
				if(bc.getPlayerId() == partnership.getFirstBatterNo()) {
					partnership.setFirstPlayer(bc.getPlayer());
				}
				if(bc.getPlayerId() == partnership.getSecondBatterNo()) {
					partnership.setSecondPlayer(bc.getPlayer());
				}
			}
			if(partnership == null) {
				return "bugMultiPartnership Partnership is null";
			}
			team = Teams.stream().filter(tm -> tm.getTeamId() == inning.getBattingTeamId()).findAny().orElse(null);
			if(team == null) {
				return "bugMultiPartnership: Team id [" + inning.getBattingTeamId() + "] from database is returning NULL";
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String bugsDB(String whatToProcess,int WhichSide,MatchAllData matchAllData) throws IOException {
		bug = this.bugs.stream().filter(bug -> bug.getBugId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
		if(bug == null) {
			return "bugsDB: bug is returning NULL";
		}
		
		if(bug.getFlag() != null) {
			team = Teams.stream().filter(tm -> tm.getTeamBadge().equalsIgnoreCase(bug.getFlag())).findAny().orElse(null);
			if(team == null) {
				return "bugsDB: Flag in database is returning NULL";
			}
		}
		
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String populateBowlScore(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException
	{
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			status = "populateBowlScore match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
			if(inning == null) {
				return "populateBowlScore Inning is null";
			}
			bowlingCard = inning.getBowlingCard().stream().filter(boc -> boc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
			if(bowlingCard == null) {
				return "populateBowlScore Bowling is null";
			}
			team = Teams.stream().filter(tm -> tm.getTeamId() == bowlingCard.getPlayer().getTeamId()).findAny().orElse(null);
			if(team == null) {
				return "populateBowlScore: Team id [" + bowlingCard.getPlayer().getTeamId() + "] from database is returning NULL";
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;;
		}
		return status;		
	}
	
	public String populateBugTarget(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException
	{
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			status = "populateBugTarget match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1]))
					.findAny().orElse(null);
				
			if(inning == null) {
				return "populateTarget: Current Inning NOT found in this match";
			}
			
			if(inning.getInningNumber() == 1) {
				return "populateTarget: Current Inning is 1";
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populateBatScore(String whatToProcess,MatchAllData matchAllData,int WhichSide) throws IOException
	{
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			status = "populateBatScore match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
			if(inning == null) {
				return "populateBatScore Inning is null";
			}
			battingCard = inning.getBattingCard().stream().filter(bc -> bc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
			if(battingCard == null) {
				return "populateBatScore Batting is null";
			}
			team = Teams.stream().filter(tm -> tm.getTeamId() == battingCard.getPlayer().getTeamId()).findAny().orElse(null);
			if(team == null) {
				return "populateBatScore: Team id [" + battingCard.getPlayer().getTeamId() + "] from database is returning NULL";
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String populateBugHighlight(String whatToProcess,MatchAllData matchAllData,int WhichSide, int whichInning) throws IOException
	{
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			status = "populateBatScore match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning).findAny().orElse(null);
			if(inning == null) {
				return "populateBatScore Inning is null";
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String bugsToss(String whatToProcess, MatchAllData matchAllData, int WhichSide) throws IOException {
		if (matchAllData == null || matchAllData.getMatch() == null ||
			matchAllData.getMatch().getInning() == null|| matchAllData.getSetup() == null) {
			status = "BugsToss match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).
					findAny().orElse(null);
			
			switch (config.getBroadcaster()) {
			case Constants.NPL: 
				team = Teams.stream().filter(tm->tm.getTeamName3().equalsIgnoreCase(whatToProcess.split(",")[2].split("-")[0])).findAny().orElse(null);
				break;
			case Constants.LEGENDS: case Constants.APL: case Constants.MPL: case Constants.T20_MUMBAI:
				team = Teams.stream().filter(tm->tm.getTeamName1().equalsIgnoreCase(whatToProcess.split(",")[2].split("-")[0])).findAny().orElse(null);
				break;	
			default:
				team = Teams.stream().filter(tm->tm.getTeamName4().equalsIgnoreCase(whatToProcess.split(",")[2].split("-")[0])).findAny().orElse(null);
				break;
			}
		}
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String populatebugManhattan(String whatToProcess,int WhichSide, MatchAllData matchAllData,int WhichInning) throws IOException {
		if (matchAllData == null || matchAllData.getMatch() == null ||
			matchAllData.getMatch().getInning() == null|| matchAllData.getSetup() == null) {
			status = "BugManhattan match is null Or Inning is null";
		} else {
			
			switch (whatToProcess.split(",")[2]) {
			case "MANHATTAN":
				manhattan = new ArrayList<OverByOverData>();
				manhattan = CricketFunctions.getOverByOverData(matchAllData, WhichInning,"MANHATTAN" ,matchAllData.getEventFile().getEvents());
				if(manhattan == null) {
					return "populateManhattan is null";
				}
				
				inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == WhichInning)
						.findAny().orElse(null);
				if(inning == null) {
					return "PopulateScorecardFF: current inning is NULL";
				}
				break;
			case "WORM":
				inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Which_Inning)
						.findAny().orElse(null);
				if(inning == null) {
					return "PopulateScorecardFF: current inning is NULL";
				}
				break;
			case "PARTNRSHIP": case "PHASE":
				inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == WhichInning)
					.findAny().orElse(null);
				if(inning == null) {
					return "populateMatchSummary: current inning is NULL";
				}
				break;
			}
			
		}
		
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String populatebugPowerplay(String whatToProcess,int WhichSide, MatchAllData matchAllData) throws IOException {
		if (matchAllData == null || matchAllData.getMatch() == null ||
			matchAllData.getMatch().getInning() == null|| matchAllData.getSetup() == null) {
			status = "BugPowerplay match is null Or Inning is null";
		} else {
			
			switch (config.getBroadcaster().toUpperCase()) {
			case Constants.NPL: case Constants.MPL: case Constants.T20_MUMBAI: case Constants.BENGAL_T20: case Constants.APL: case Constants.VIDARBHA:
				inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == 
					Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
				break;
			case Constants.ISPL:
				inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == 
					Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
				break;
			default:
				inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).
				findAny().orElse(null);
				break;
			}
		}
		
		if(PopulateBugBody(WhichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}

	public String populateBugResult(String whatToProcess, MatchAllData matchAllData, int whichSide) throws IOException {
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null|| matchAllData.getSetup() == null) {
				status = "populateBugResult match is null Or Inning is null";
		}else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "populateBugResult match is null Or Inning is null";
			}else if(inning.getInningNumber() == 1) {
				return "populateBugResult only work in 2nd Inning";
			}
		}
		if(PopulateBugBody(whichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	
	public String populatePerformanceBug(String whatToProcess, MatchAllData matchAllData, int whichSide) throws IOException {
		if (matchAllData == null || matchAllData.getMatch() == null ||
				matchAllData.getMatch().getInning() == null|| matchAllData.getSetup() == null) {
				status = "populateBugResult match is null Or Inning is null";
			} else {
				inning = matchAllData.getMatch().getInning().stream().
						filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).
						findAny().orElse(null);
			}
			if(PopulateBugBody(whichSide, whatToProcess,matchAllData) == Constants.OK) {
				status = Constants.OK;
			}
			return status;
	}
	
	public String populateBugSixDistance(String whatToProcess, MatchAllData matchAllData, int whichSide) throws IOException {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		if(inning == null) {
			return "inning is null";
		}
		if(PopulateBugBody(whichSide, whatToProcess,matchAllData) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String populatePopupSubSideData(int WhichSide, String whatToProcess, MatchAllData matchAllData) {
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.T20_MUMBAI:
			switch (whatToProcess.split(",")[0]) {
			case "Control_Shift_U_change_on": // POP-Up
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET " 
						+ (config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST) ? Constants.T20_MUMBAI_PHOTO_PATH : "\\\\" + config.getPrimaryIpAddress() + "\\\\" 
						+ Constants.T20_MUMBAI_PHOTO_PATH_NETWORK) + (config.getCategory().equalsIgnoreCase("MEN") ? "\\\\" + "MEN" : "\\\\" + "WOMEN") + Constants.STRAIGHT_1024 
						+ inning.getBatting_team().getTeamName4() + "\\" + battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION
						+ " \0", print_writers);
				
//				if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET "
//							+ Constants.T20_MUMBAI_PHOTO_PATH + Constants.LEFT_1024 + inning.getBatting_team().getTeamName4() + "\\\\" + battingCard.getPlayer().getPhoto()
//							+ CricketUtil.PNG_EXTENSION + " \0", print_writers);
//				}else {
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET "
//							+ "\\\\" + config.getPrimaryIpAddress() + Constants.T20_MUMBAI_PHOTO_PATH_NETWORK + Constants.LEFT_1024 + inning.getBatting_team().getTeamName4()
//							+ "\\\\" + battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION+ " \0", print_writers);
//				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$ColouredBase$Side" + WhichSide + "$img_Base1*TEXTURE*IMAGE SET "
						+ Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide +
						"$img_Text*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Text + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
				
				getBugImpact(battingCard.getPlayer().getPlayerId(),print_writers,matchAllData,WhichSide);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide + "$txt_FirstName*GEOM*TEXT SET "
						+ battingCard.getPlayer().getFirstname()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide + "$txt_LastName*GEOM*TEXT SET "
						+ (battingCard.getPlayer().getSurname()!=null?battingCard.getPlayer().getSurname():"") + "\0", print_writers);
				
				switch (whatToProcess.split(",")[3].toUpperCase()) {
				case "SCORE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
							+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Score"
							+ "$txt_Runs*GEOM*TEXT SET " + (battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)?battingCard.getRuns() + "*":battingCard.getRuns()) + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Score$txt_Balls"
							+ "*GEOM*TEXT SET OFF " + battingCard.getBalls() + "\0", print_writers);
					break;
				case "STRIKERATE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
							+ "$txt_StatHead*GEOM*TEXT SET STRIKE RATE\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
							+ "*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 1) + "\0", print_writers);
					break;
				
				case "BOUNDARY":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
							+ "$txt_StatHead*GEOM*TEXT SET FOURS/SIXES\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
							+ "*GEOM*TEXT SET " + battingCard.getFours() + "/" + battingCard.getSixes() + "\0", print_writers);
					break;
				case "BOUNDARY_PERCENT":
				    int fours = battingCard.getFours();
				    int sixes = battingCard.getSixes();
				    int totalRuns = battingCard.getRuns();
				    int boundaryRuns = (fours * 4) + (sixes * 6);
				    double boundaryPercent = 0;
				    if (totalRuns > 0) {
				        boundaryPercent = (boundaryRuns * 100.0) / totalRuns;
				    }
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				   
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name()+ "\0", print_writers);
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
							+ "$txt_StatHead*GEOM*TEXT SET BOUNDARY %\0", print_writers);
				   
//				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
//					        + "*GEOM*TEXT SET MATCH BOUNDARY%\0",print_writers);
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
							+ "*GEOM*TEXT SET " + String.format("%.0f", boundaryPercent) + "\0", print_writers);
				    break;
				}
				break;
			case "Control_Shift_V_change_on":
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET " 
						+ (config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST) ? Constants.T20_MUMBAI_PHOTO_PATH : "\\\\" + config.getPrimaryIpAddress() + "\\\\" 
						+ Constants.T20_MUMBAI_PHOTO_PATH_NETWORK) + (config.getCategory().equalsIgnoreCase("MEN") ? "\\\\" + "MEN" : "\\\\" + "WOMEN") + Constants.STRAIGHT_1024 
						+ inning.getBowling_team().getTeamName4() + "\\" + bowlingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION
						+ " \0", print_writers);
				
//				if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET "
//							+ Constants.T20_MUMBAI_PHOTO_PATH + Constants.LEFT_1024 + inning.getBowling_team().getTeamName4() + "\\\\" + bowlingCard.getPlayer().getPhoto()
//							+ CricketUtil.PNG_EXTENSION + " \0", print_writers);
//				}else {
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET "
//							+ "\\\\" + config.getPrimaryIpAddress() + Constants.T20_MUMBAI_PHOTO_PATH_NETWORK + Constants.LEFT_1024 + inning.getBowling_team().getTeamName4()
//							+ "\\\\" + bowlingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION+ " \0", print_writers);
//				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$ColouredBase$Side" + WhichSide + "$img_Base1*TEXTURE*IMAGE SET "
						+ Constants.T20_MUMBAI_Base1 + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide +
						"$img_Text*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Text + inning.getBowling_team().getTeamBadge() + "\0",print_writers);
				
				getBugImpact(bowlingCard.getPlayer().getPlayerId(),print_writers,matchAllData,WhichSide);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide + "$txt_FirstName*GEOM*TEXT SET "
						+ bowlingCard.getPlayer().getFirstname()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide + "$txt_LastName*GEOM*TEXT SET "
						+ (bowlingCard.getPlayer().getSurname()!=null?bowlingCard.getPlayer().getSurname():"") + "\0", print_writers);
				
				switch (whatToProcess.split(",")[3].toUpperCase()) {
				case "FIGURE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
							+ "$txt_StatHead*GEOM*TEXT SET THIS MATCH\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
							+ "*GEOM*TEXT SET " + bowlingCard.getWickets() +"-"+ bowlingCard.getRuns() + "\0", print_writers);
					break;
				case "ECONOMY":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
							+ "$txt_StatHead*GEOM*TEXT SET ECONOMY\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
							+ "*GEOM*TEXT SET " + bowlingCard.getEconomyRate() + "\0", print_writers);
					break;
				case "DOT_PERCENT":
				    String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BOWLER, matchAllData, inning.getInningNumber(),
				            bowlingCard.getPlayerId(),"-",matchAllData.getEventFile().getEvents()).split("-");
				    int dotBalls = Integer.parseInt(Count[0]);
				    int totalBallsBowled = (bowlingCard.getOvers() * 6) + bowlingCard.getBalls();
				    double dotPercent = 0;
				    if (totalBallsBowled > 0) {
				        dotPercent = (dotBalls * 100.0) / totalBallsBowled;
				    }
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
							+ "$txt_StatHead*GEOM*TEXT SET DOT BALL %\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
							+ "*GEOM*TEXT SET " + String.format("%.0f", dotPercent) + "\0", print_writers);
				    break;		
				}
				break;
			}
			break;
		default:
			switch (whatToProcess.split(",")[0]) {
			case "Control_Shift_U_change_on": // POP-Up
				switch (whatToProcess.split(",")[3].toUpperCase()) {
				case "SCORE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Select"
							+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six"
							+ "$Title$Main$Title*GEOM*TEXT SET THIS MATCH\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six$Data$Title"
							+ "*GEOM*TEXT SET " + (battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)?battingCard.getRuns() + "*":battingCard.getRuns())
							+ " (" + battingCard.getBalls() + ")" + "\0", print_writers);
					break;
				case "STRIKERATE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Select"
							+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six"
							+ "$Title$Main$Title*GEOM*TEXT SET STRIKE RATE\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six$Data$Title"
							+ "*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 1) + "\0", print_writers);
					break;
				
				case "BOUNDARY":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Select"
							+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six"
							+ "$Title$Main$Title*GEOM*TEXT SET FOURS/SIXES\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six$Data$Title"
							+ "*GEOM*TEXT SET " + battingCard.getFours() + "/" + battingCard.getSixes() + "\0", print_writers);
					break;	
				case "BOUNDARY_PERCENT":
				    int fours = battingCard.getFours();
				    int sixes = battingCard.getSixes();
				    int totalRuns = battingCard.getRuns();
				    int boundaryRuns = (fours * 4) + (sixes * 6);
				    double boundaryPercent = 0;
				    if (totalRuns > 0) {
				        boundaryPercent = (boundaryRuns * 100.0) / totalRuns;
				    }
				   
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name()+ "\0", print_writers);
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
				        + "*GEOM*TEXT SET BOUNDARY %\0",print_writers);
				   
//				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
//					        + "*GEOM*TEXT SET MATCH BOUNDARY%\0",print_writers);
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Score"
				        + "*GEOM*TEXT SET "+ String.format("%.0f", boundaryPercent)+ "\0",print_writers);
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide
				        + "$Seperator*ACTIVE SET 0\0",print_writers);
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
				        + "*GEOM*TEXT SET \0",print_writers);
				    break;
				}
				break;
			case "Control_Shift_V_change_on":
				switch (whatToProcess.split(",")[3].toUpperCase()) {
				case "FIGURE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Select"
							+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six"
							+ "$Title$Main$Title*GEOM*TEXT SET THIS MATCH\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six$Data$Title"
							+ "*GEOM*TEXT SET " + bowlingCard.getWickets() +"-"+ bowlingCard.getRuns() + " (" + CricketFunctions.OverBalls(bowlingCard.getOvers(),
							bowlingCard.getBalls())  + ")" + "\0", print_writers);
					break;
				case "ECONOMY":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Select"
							+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six"
							+ "$Title$Main$Title*GEOM*TEXT SET ECONOMY\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BatterScore$Body$Data$Side1$SubSide" + WhichSide + "$Four-Six$Data$Title"
							+ "*GEOM*TEXT SET " + bowlingCard.getEconomyRate() + "\0", print_writers);
					break;
				}
				break;
			}
			break;
		}
		return status;
	}
	
	public String populatePowerplayThisOver(String whatToProcess, int WhichSide, MatchAllData matchAllData) throws JsonMappingException, JsonProcessingException {
		List <VariousStats> this_over = null ;
		String score = "";
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES))
		.findAny().orElse(null);
		if(inning == null) {
			return "populatePlayingXI: current inning is NULL";
		}
		if(Integer.valueOf(whatToProcess.split(",")[1]) ==1) {
			this_over =  new ObjectMapper().readValue(
					 new ObjectMapper()	.writeValueAsString(IndexController.MatchStats.getPowerPlay1ThisOver()),
				    new TypeReference<List<VariousStats>>() {});
			team =(inning.getInningNumber()== 1 ? inning.getBatting_team() : inning.getBowling_team());
			score = IndexController.MatchStats.getHomeFirstPowerPlay().getTotalRuns()+"-"+
					IndexController.MatchStats.getHomeFirstPowerPlay().getTotalWickets();
		}else if(Integer.valueOf(whatToProcess.split(",")[1]) ==2){
			this_over =  new ObjectMapper().readValue(
					 new ObjectMapper()	.writeValueAsString(IndexController.MatchStats.getPowerPlay2ThisOver()),
				    new TypeReference<List<VariousStats>>() {});
			team =(inning.getInningNumber()== 2 ? inning.getBatting_team() : inning.getBowling_team());
			score = IndexController.MatchStats.getAwayFirstPowerPlay().getTotalRuns()+"-"+
					IndexController.MatchStats.getAwayFirstPowerPlay().getTotalWickets();
		}
		Collections.reverse(this_over);
		for (VariousStats obj : this_over) {
		    String processed = String.join(",", 
		        (Iterable<String>) Arrays.stream(obj.getThisOverTxt().split(","))
		            .map(s -> s.replace("WIDE", "WD")
		                       .replace("NO_BALL", "NB")
		                       .replace("LEG_BYE", "LB")
		                       .replace("BYE", "B")
		                       .replace("PENALTY", "PN")
		                       .replace("LOG_WICKET", "W")
		                       .replace("WICKET", "W")
		                       .replace("BOUNDARY", ""))
		            .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
		                Collections.reverse(list);
		                return list;
		            }))
		    );
		    obj.setThisOverTxt(processed);
		    //System.out.println(obj.getOver() + " " + obj.getThisOverTxt());
		}
		return PopulateThisOverBugBody(WhichSide, whatToProcess,matchAllData,this_over,score);
	}
	private String PopulateThisOverBugBody(int whichSide, String whatToProcess, MatchAllData matchAllData,
			List<VariousStats> this_over, String score) {
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Header$Logo$BGLogo*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_TEAMLOGO 
				+ CricketFunctions.whichLogo(whatToProcess, team.getTeamBadge()) + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Player$TextureColour$TeamLogo*TEXTURE*IMAGE SET " 
				+ Constants.T20_MUMBAI_TEAMLOGO + CricketFunctions.whichLogo(whatToProcess, team.getTeamBadge()) + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Header$Anim$Round*GEOM*TEXT SET " + team.getTeamName1() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader*ACTIVE SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$Top3*ACTIVE SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$AllSplits*ACTIVE SET 0\0", print_writers);

		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$PlayerName"
				+ "$TitleMS$Header*GEOM*TEXT SET POWERPLAY\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$PlayerName"
				+ "$TitleMS$Score*GEOM*TEXT SET "+ score +"\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$TeamColour*TEXTURE*IMAGE SET " 
				+ Constants.T20_MUMBAI_TEAMCOLOUR_GRADIENTS + team.getTeamBadge() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$TitleMS$Header*TEXTURE*IMAGE SET " 
				+ Constants.T20_MUMBAI_TEAMCOLOUR_FLAT + CricketFunctions.whichTextColor(team.getTeamBadge()) + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$TitleMS$Score*TEXTURE*IMAGE SET " 
				+ Constants.T20_MUMBAI_TEAMCOLOUR_FLAT + CricketFunctions.whichTextColor(team.getTeamBadge()) + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$Stats"
				+ "$InfoList*FUNCTION*Grid*num_row SET "+ this_over.size() +"\0", print_writers);
		
		//Veil Omo
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$Veil*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		for(int i=1;i<=this_over.size();i++) {
			int length = this_over.get(i-1).getThisOverTxt().split(",").length;
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$Stats"
					+ "$InfoList$Row" + i + "$TopRow$Title*GEOM*TEXT SET "+ "OVER "+(this_over.get(i-1).getOver()+1) +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$Stats"
					+ "$InfoList$Row" + i + "$TopRow$Value*GEOM*TEXT SET "+ (this_over.get(i-1).getTotalRuns()+"-" +this_over.get(i-1).getTotalWickets()) +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$Stats"
					+ "$InfoList$Row" + i + "$Select_Type*FUNCTION*Omo*vis_con SET "+(length > 10 ? 0 : 1)+"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$Stats"
					+ "$InfoList$Row" + i +"$ThisOver*FUNCTION*Grid*num_col SET "+ length +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$Stats"
					+ "$InfoList$Row" + i +"$ThisOver*FUNCTION*Grid*num_row SET 1\0", print_writers);

		
			if(length <=10) {
				for(int j=1;j<=length;j++) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$LOF_PowerPlay$Body$CurrentLeader$Stats"
							+ "$InfoList$Row" + i + "$ThisOver$" + j + "$Text*GEOM*TEXT SET "+this_over.get(i-1).getThisOverTxt().split(",")[j-1]+"\0", print_writers);
				}
			}
		}
		return Constants.OK;
	}

	public String T20VidarbhaBugBody(int WhichSide, String whatToProcess, MatchAllData matchAllData) {

	    switch (whatToProcess.split(",")[0]) {
		case "Control_4":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Sponsor_Pop$Side" + WhichSide +
					"$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
        	
        	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Sponsor_Pop$Side" + WhichSide +
					"$Select$2$img_Sponsor*TEXTURE*IMAGE SET " + Constants.SPONSERS_PATH + "EaseMyTrip"  + "\0", print_writers);
        	
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$band"
					+ "$txt_Header*GEOM*TEXT SET TOURNAMENT FOURS\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_1$txt_Unit*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_1$txt_Ten*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_1$txt_Hundread*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
			
			
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_2$txt_Unit*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_2$txt_Ten*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_2$txt_Hundread*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
			
			break;	
		case "6":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Sponsor_Pop$Side" + WhichSide +
					"$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
        	
        	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Sponsor_Pop$Side" + WhichSide +
					"$Select$2$img_Sponsor*TEXTURE*IMAGE SET " + Constants.SPONSERS_PATH + "Chirayu"  + "\0", print_writers);
        	
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes"
					+ "$txt_Header*GEOM*TEXT SET TOURNAMENT SIXES\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_1$txt_Unit*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_1$txt_Ten*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_1$txt_Hundread*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
			
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_2$txt_Unit*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_2$txt_Ten*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
					+ "$Side_2$txt_Hundread*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
			
		break;
	        case "o":
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select"
	                    + "*FUNCTION*Omo*vis_con SET 0  \0", print_writers);
	            CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select$PlayerOftheMatch$Data$Select_Sponsor"
	                    + "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToSelectedViz(2, "-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select$PlayerOftheMatch$Data$Select_Sponsor"
	                    + "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
	            break;

	        case "t":
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select"
	                    + "*FUNCTION*Omo*vis_con SET 2  \0", print_writers);

	            CricketFunctions.DoadWriteCommandToSelectedViz(2, "-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select$Third_Umpire$1$Data$Side_1$SubText$Side1"
	                    + "$group*ACTIVE SET 0 \0", print_writers);

	            for (VariousText vt : VariousText) {
	                if (vt.getVariousType().equalsIgnoreCase("THIRDUMPIRE") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Third_Umpire"
	                            + "$1$Data$Side_1$SubText$txt_Sub*GEOM*TEXT SET " + vt.getVariousText() + "\0", print_writers);
	                } else if (vt.getVariousType().equalsIgnoreCase("THIRDUMPIRE") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
//	                  CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Footer$Top_Align$Side" + WhichSide + "$Select_FooterType"
//	                      + "$Info_Text$Data$txt_Info_1*GEOM*TEXT SET " + CricketFunctions.
//	                      generateMatchSummaryStatus(WhichInning, matchAllData, CricketUtil.FULL, CricketUtil.BEAT).toUpperCase() + "\0", print_writers);
	                }
	            }
	            break;
	    }

	    switch (whatToProcess.split(",")[0]) {
	        case "Alt_p":

	            if (matchAllData.getSetup().getHomeTeam().getTeamName4().contains("KHILADI XI") || matchAllData.getSetup().getHomeTeam().getTeamName4().contains("MASTER 11")) {
	                if (matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
	                    homecolor = "KHILADI_XI";
	                } else if (matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase("MASTER 11")) {
	                    homecolor = "MASTER_XI";
	                }
	            } else {
	                homecolor = matchAllData.getSetup().getHomeTeam().getTeamBadge();
	            }

	            if (matchAllData.getSetup().getAwayTeam().getTeamName4().contains("KHILADI XI") || matchAllData.getSetup().getAwayTeam().getTeamName4().contains("MASTER 11")) {
	                if (matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
	                    awaycolor = "KHILADI_XI";
	                } else if (matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase("MASTER 11")) {
	                    awaycolor = "MASTER_XI";
	                }
	            } else {
	                awaycolor = matchAllData.getSetup().getAwayTeam().getTeamBadge();
	            }

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$Home$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$Away$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + awaycolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$Home$img_Logo*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$Away$img_Logo*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_LOGO_PATH + awaycolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss"
	                    + "$txt_Info*GEOM*TEXT SET " + whatToProcess.split(",")[2].split("-")[0] + " WON THE TOSS & ELECTED TO "
	                    + whatToProcess.split(",")[2].split("-")[1] + "\0", print_writers);

	            break;

	        case "h":

	        //	 homecolor = team.getTeamBadge();

	        	 awaycolor = inning.getBatting_team().getTeamBadge();

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + awaycolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + awaycolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + awaycolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + awaycolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + awaycolor + "\0", print_writers);
	            
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Name*GEOM*TEXT SET HIGHLIGHTS: " + "\0", print_writers);
	            if (inning.getTotalWickets() >= 10) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Runs*GEOM*TEXT SET " + inning.getTotalRuns() + "\0", print_writers);
	            } else {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Runs*GEOM*TEXT SET " + inning.getTotalRuns() + "-" + inning.getTotalWickets() + "\0", print_writers);
	            }

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + " " + CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
	                    + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	            break;
	        case "Control_Shift_F3":
	        	 awaycolor = inning.getBatting_team().getTeamBadge();
	        	 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
		                    + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	        	 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
		                    + Constants.VIDARBHA_BASE1 + awaycolor + "\0", print_writers);
		            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
		                    + Constants.VIDARBHA_BASE2 + awaycolor + "\0", print_writers);

		            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
		                    + Constants.VIDARBHA_TEXT1 + awaycolor + "\0", print_writers);
		            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
		                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + awaycolor + "\0", print_writers);

		            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
		                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
		            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
		                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + awaycolor + "\0", print_writers);
		            
				
				String summary = ""; 
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && matchAllData.getSetup().getMaxOvers() == 1) {
					summary = CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " OFF " + (matchAllData.getSetup().getMaxOvers()) + " OVER";
				}else {
					if(matchAllData.getSetup().getTargetOvers() != null && !matchAllData.getSetup().getTargetOvers().isEmpty()) {
						summary = CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " OFF " + (Integer.valueOf(CricketFunctions.GetTargetData(matchAllData).getTargetOvers())) + " OVERS ("
								+ (matchAllData.getSetup().getTargetType() != null ? matchAllData.getSetup().getTargetType().toUpperCase() : "") + ")";
					}else {
						summary = CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " OFF " + 
								(Integer.valueOf(CricketFunctions.GetTargetData(matchAllData).getTargetOvers())) + " OVERS";
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
                        + "$txt_Name*GEOM*TEXT SET "  + inning.getBatting_team().getTeamName1() + " NEED "+ summary + "\0", print_writers);
				
				 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Runs*GEOM*TEXT SET " + "\0", print_writers);
	            

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + ""  + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);
				
				break;
	        case "Control_Shift_R":
	        	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
	                    + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	        	stats_text = CricketFunctions.GenerateMatchSummaryStatus(inning.getInningNumber(), matchAllData, 
						CricketUtil.SHORT, "|", config.getBroadcaster(), true).getTargetOrResult().toUpperCase();
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
                        + "$txt_Name*GEOM*TEXT SET "  + stats_text + "\0", print_writers);
				
				 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Runs*GEOM*TEXT SET " + "\0", print_writers);
	            

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + ""  + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);
	            
	            String firstWord = stats_text.split(" ")[0]; 
	            for(Team tm:cricketService.getTeams()) {
	            	 if (tm.getTeamName4().equalsIgnoreCase(firstWord)) {
	            		awaycolor = tm.getTeamBadge();
	            	}
	            }
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + awaycolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + awaycolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + awaycolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + awaycolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + awaycolor + "\0", print_writers);
				
				break;
	        case "Control_y":
	            
	        	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Sponsor_Bug$Side" + WhichSide +
						"$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
	        	
	        	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Sponsor_Bug$Side" + WhichSide +
						"$Select$2$img_Sponsor*TEXTURE*IMAGE SET " + Constants.SPONSERS_PATH + "Armour"  + "\0", print_writers);
	        	
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Name*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + "" + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
                        + "$txt_Sub*GEOM*TEXT SET " + "POWERPLAY" + "\0", print_writers);

                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
                        + "$txt_Runs*GEOM*TEXT SET " + CricketFunctions.getPowerPlayScore(inning, inning.getInningNumber(), "-", matchAllData) + "\0", print_writers);


	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide
	                    + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	            break;
	            
	            
	       
	        case "g":

	            if (team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
	                if (team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
	                    homecolor = "KHILADI_XI";
	                } else if (team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
	                    homecolor = "MASTER_XI";
	                }
	            } else {
	                homecolor = team.getTeamBadge();
	            }

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Name*GEOM*TEXT SET " + bowlingCard.getPlayer().getTicker_name() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Runs*GEOM*TEXT SET " + "" + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + "" + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + " - " + bowlingCard.getDots()
	                    + " - " + bowlingCard.getRuns() + " - " + bowlingCard.getWickets() + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide
	                    + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	            break;

	        case "y":

	            if (team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
	                if (team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
	                    homecolor = "KHILADI_XI";
	                } else if (team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
	                    homecolor = "MASTER_XI";
	                }
	            } else {
	                homecolor = team.getTeamBadge();
	            }

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "\0", print_writers);

	            if (battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Runs*GEOM*TEXT SET " + battingCard.getRuns() + "*" + "\0", print_writers);
	            } else {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Runs*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
	            }
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + "(" + battingCard.getBalls() + ")" + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET FOUR" + CricketFunctions.Plural(battingCard.getFours()).toUpperCase() + "   " + 
	            		battingCard.getFours() + "  SIXES   " + battingCard.getSixes()
	                    + "\0", print_writers);

	            break;

	        case ".":

	            homecolor = "ISPL";
	            String posOrNeg = "";

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Name*GEOM*TEXT SET " + "50-50 OVER : " + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);

	            for (Event evnt : matchAllData.getEventFile().getEvents()) {
	                if (evnt.getEventInningNumber() == inning.getInningNumber()) {
	                    if (evnt.getEventType().equalsIgnoreCase(CricketUtil.LOG_50_50)) {
	                        int bonus = 0;
	                        int challengeRuns = 0;
	                        challengeRuns = evnt.getEventTotalRunsInAnOver();
	                        bonus = evnt.getEventExtraRuns();
	                        posOrNeg = evnt.getEventExtra();

	                        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                                + "$txt_Runs*GEOM*TEXT SET " + challengeRuns + " RUNS" + "\0", print_writers);
	                        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                                + "$txt_Balls*GEOM*TEXT SET " + "(" + posOrNeg + bonus + ")" + "\0", print_writers);
//	                      if((bonus*2) >= challengeRuns) {
//	                          CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
//	                                  + "$txt_Balls*GEOM*TEXT SET " + "(+" + bonus + ")" + "\0", print_writers);
//	                      }else {
//	                          CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
//	                                  + "$txt_Balls*GEOM*TEXT SET " + "(-" + bonus + ")" + "\0", print_writers);
//	                      }
	                    }
	                }
	            }
	            break;

	        case "/":

	            String tapeData = getBowlerRunsOverbyOver(inning.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData);
	            homecolor = "ISPL";

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Name*GEOM*TEXT SET " + tapeData.split(",")[0] + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Runs*GEOM*TEXT SET " + " TAPE BALL OVER : " + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + tapeData.split(",")[1] + " RUN"
	                    + CricketFunctions.Plural(Integer.valueOf(tapeData.split(",")[1])).toUpperCase() + " & " + tapeData.split(",")[2] + " WICKET"
	                    + CricketFunctions.Plural(Integer.valueOf(tapeData.split(",")[2])).toUpperCase() + "\0", print_writers);
	            break;

	        case "Shift_F":

	            if (team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
	                if (team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
	                    homecolor = "KHILADI_XI";
	                } else if (team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
	                    homecolor = "MASTER_XI";
	                }
	            } else {
	                homecolor = team.getTeamBadge();
	            }

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Runs*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + battingCard.getBalls() + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + battingCard.getHowOutText() + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
	                    + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	            break;

	        case "Shift_O":

	            if (team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
	                if (team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
	                    homecolor = "KHILADI_XI";
	                } else if (team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
	                    homecolor = "MASTER_XI";
	                }
	            } else {
	                homecolor = team.getTeamBadge();
	            }

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Runs*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + "(" + battingCard.getBalls() + ")" + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + battingCard.getHowOutText() + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
	                    + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	            break;

	        case "Control_k":

	            if (team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
	                if (team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
	                    homecolor = "KHILADI_XI";
	                } else if (team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
	                    homecolor = "MASTER_XI";
	                }
	            } else {
	                homecolor = team.getTeamBadge();
	            }

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Name*GEOM*TEXT SET " + "CURRENT PARTNERSHIP: " + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Runs*GEOM*TEXT SET " + partnership.getTotalRuns() + "*" + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + partnership.getTotalBalls() + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
	                    + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	            break;

	        case "Shift_F4":

	            if (team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
	                if (team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
	                    homecolor = "KHILADI_XI";
	                } else if (team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
	                    homecolor = "MASTER_XI";
	                }
	            } else {
	                homecolor = team.getTeamBadge();
	            }

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);

	            if (partnership.getPartnershipNumber() == 1) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Name*GEOM*TEXT SET " + partnership.getPartnershipNumber() + "st WICKET PARTNERSHIP: " + "\0", print_writers);
	            } else if (partnership.getPartnershipNumber() == 2) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Name*GEOM*TEXT SET " + partnership.getPartnershipNumber() + "nd WICKET PARTNERSHIP: " + "\0", print_writers);
	            } else if (partnership.getPartnershipNumber() == 3) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Name*GEOM*TEXT SET " + partnership.getPartnershipNumber() + "rd WICKET PARTNERSHIP: " + "\0", print_writers);
	            } else {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Name*GEOM*TEXT SET " + partnership.getPartnershipNumber() + "th WICKET PARTNERSHIP: " + "\0", print_writers);
	            }
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Runs*GEOM*TEXT SET " + partnership.getTotalRuns() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Balls*GEOM*TEXT SET " + partnership.getTotalBalls() + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                    + "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
	                    + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	            break;

	        case "k":
	        	if(team != null) {
	        		 if (team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
	 	                if (team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
	 	                    homecolor = "KHILADI_XI";
	 	                } else if (team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
	 	                    homecolor = "MASTER_XI";
	 	                }
	 	            } else {
	 	                homecolor = team.getTeamBadge();
	 	            }
	        	}else {
	        		 homecolor = "TLogo";
	        	}
	           

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Right_Section$img_Base1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$HeaderBand$img_Base2*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_BASE2 + homecolor + "\0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1*TEXTURE*IMAGE SET "
	                    + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + "$Data$img_text1$txt_Sub*"
	                    + "TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1 + homecolor + "\0", print_writers);

	            if (bug.getSponsor() != null) {
	                if (config.getSecondaryIpAddress() != null) {
	                    CricketFunctions.DoadWriteCommandToSelectedViz(2, "-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
	                            + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	                }
	                CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
	                        + "$img_Sponsor*ACTIVE SET 1 \0", print_writers);
	            } else {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide
	                        + "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
	            }

	            if (bug.getFlag() != null) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$img_Logo*ACTIVE SET 1 \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$img_Logo*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + homecolor + "\0", print_writers);
	            } else {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$img_Flag*ACTIVE SET 0 \0", print_writers);
	            }

	            if (bug.getText4() != null) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Balls*GEOM*TEXT SET " + "(" + bug.getText4() + ")" + "\0", print_writers);
	            } else {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Balls*GEOM*TEXT SET " + "" + "\0", print_writers);
	            }

	            if (bug.getText3() != null) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Runs*GEOM*TEXT SET " + bug.getText3() + "\0", print_writers);
	            } else {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Runs*GEOM*TEXT SET " + "" + "\0", print_writers);
	            }

	            if (bug.getText2() != null) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Sub*GEOM*TEXT SET " + bug.getText2() + "\0", print_writers);
	            } else {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);
	            }

	            if (bug.getText1() != null) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Name*GEOM*TEXT SET " + bug.getText1() + "\0", print_writers);
	            } else {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide
	                        + "$txt_Name*GEOM*TEXT SET " + "" + "\0", print_writers);
	            }

	            break;
	    }

	    return Constants.OK;
	}
	public String T20MumbaiBugBody(int WhichSide, String whatToProcess,MatchAllData matchAllData) {
		switch (whatToProcess.split(",")[0]) {
		case "Shift_F":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET " + battingCard.getPlayer().getFull_name() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
					battingCard.getRuns() + "(" + battingCard.getBalls() + ")" +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " +
					battingCard.getHowOutText() + "\0", print_writers);
			break;
		case "Alt_p":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_TossBug$CommonElements" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + team.getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_TossBug"
					+ "$txt_Info*GEOM*TEXT SET " + team.getTeamName1() + " WON THE TOSS "
					+"& CHOSE TO "+whatToProcess.split(",")[2].split("-")[1]+"\0", print_writers);
			break;
		case "6": case "Control_4":
			switch (whatToProcess.split(",")[0]) {
			case "6":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Header"
						+ "$txt_Header1*GEOM*TEXT SET TOURNAMENT\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Header"
						+ "$txt_Header2*GEOM*TEXT SET SIXES\0", print_writers);
				break;
			case "Control_4":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Header"
						+ "$txt_Header1*GEOM*TEXT SET TOURNAMENT\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Header"
						+ "$txt_Header2*GEOM*TEXT SET FOURS\0", print_writers);
				break;
			}
					
			System.out.println("data = " + this_data_str.get(0));
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Data"
					+ "$Hundredths$Side1$txt_Hundredths*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Data"
					+ "$Tenths$Side1$txt_Tenths*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Data"
					+ "$Units$Side1$txt_Units*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Data"
					+ "$Hundredths$Side2$txt_Hundredths*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Data"
					+ "$Tenths$Side2$txt_Tenths*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_BoundaryCounter$Data"
					+ "$Units$Side2$txt_Units*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
			break;	
		case "Control_Shift_U": // POP-Up
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET " 
					+ (config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST) ? Constants.T20_MUMBAI_PHOTO_PATH : "\\\\" + config.getPrimaryIpAddress() + "\\\\" 
					+ Constants.T20_MUMBAI_PHOTO_PATH_NETWORK) + (config.getCategory().equalsIgnoreCase("MEN") ? "\\\\" + "MEN" : "\\\\" + "WOMEN") + Constants.STRAIGHT_1024 
					+ inning.getBatting_team().getTeamName4() + "\\" + battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION
					+ " \0", print_writers);
			
//			if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET "
//						+ Constants.T20_MUMBAI_PHOTO_PATH + Constants.LEFT_1024 + inning.getBatting_team().getTeamName4() + "\\\\" + battingCard.getPlayer().getPhoto()
//						+ CricketUtil.PNG_EXTENSION + " \0", print_writers);
//			}else {
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET "
//						+ "\\\\" + config.getPrimaryIpAddress() + Constants.T20_MUMBAI_PHOTO_PATH_NETWORK + Constants.LEFT_1024 + inning.getBatting_team().getTeamName4()
//						+ "\\\\" + battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION+ " \0", print_writers);
//			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$ColouredBase$Side" + WhichSide + "$img_Base1*TEXTURE*IMAGE SET "
					+ Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide +
					"$img_Text*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Text + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
			
			getBugImpact(battingCard.getPlayer().getPlayerId(),print_writers,matchAllData,WhichSide);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide + "$txt_FirstName*GEOM*TEXT SET "
					+ battingCard.getPlayer().getFirstname()+ "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide + "$txt_LastName*GEOM*TEXT SET "
					+ (battingCard.getPlayer().getSurname()!=null?battingCard.getPlayer().getSurname():"") + "\0", print_writers);
			
			switch (whatToProcess.split(",")[3].toUpperCase()) {
			case "SCORE":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
						+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Score"
						+ "$txt_Runs*GEOM*TEXT SET " + (battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)?battingCard.getRuns() + "*":battingCard.getRuns()) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Score$txt_Balls"
						+ "*GEOM*TEXT SET OFF " + battingCard.getBalls() + "\0", print_writers);
				break;
			case "STRIKERATE":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
						+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
						+ "$txt_StatHead*GEOM*TEXT SET STRIKE RATE\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
						+ "*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 1) + "\0", print_writers);
				break;
			
			case "BOUNDARY":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
						+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
						+ "$txt_StatHead*GEOM*TEXT SET FOURS/SIXES\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
						+ "*GEOM*TEXT SET " + battingCard.getFours() + "/" + battingCard.getSixes() + "\0", print_writers);
				break;
			case "BOUNDARY_PERCENT":
			    int fours = battingCard.getFours();
			    int sixes = battingCard.getSixes();
			    int totalRuns = battingCard.getRuns();
			    int boundaryRuns = (fours * 4) + (sixes * 6);
			    double boundaryPercent = 0;
			    if (totalRuns > 0) {
			        boundaryPercent = (boundaryRuns * 100.0) / totalRuns;
			    }
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
						+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			   
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
						+ "$txt_StatHead*GEOM*TEXT SET BOUNDARY %\0", print_writers);
			   
//			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
//				        + "*GEOM*TEXT SET MATCH BOUNDARY%\0",print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
						+ "*GEOM*TEXT SET " + String.format("%.0f", boundaryPercent) + "\0", print_writers);
			    break;	
			}
			break;
		case "Control_Shift_V":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET " 
					+ (config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST) ? Constants.T20_MUMBAI_PHOTO_PATH : "\\\\" + config.getPrimaryIpAddress() + "\\\\" 
					+ Constants.T20_MUMBAI_PHOTO_PATH_NETWORK) + (config.getCategory().equalsIgnoreCase("MEN") ? "\\\\" + "MEN" : "\\\\" + "WOMEN") + Constants.STRAIGHT_1024 
					+ inning.getBowling_team().getTeamName4() + "\\" + bowlingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION
					+ " \0", print_writers);
			
//			if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET "
//						+ Constants.T20_MUMBAI_PHOTO_PATH + Constants.LEFT_1024 + inning.getBowling_team().getTeamName4() + "\\\\" + bowlingCard.getPlayer().getPhoto()
//						+ CricketUtil.PNG_EXTENSION + " \0", print_writers);
//			}else {
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$img_Player*TEXTURE*IMAGE SET "
//						+ "\\\\" + config.getPrimaryIpAddress() + Constants.T20_MUMBAI_PHOTO_PATH_NETWORK + Constants.LEFT_1024 + inning.getBowling_team().getTeamName4()
//						+ "\\\\" + bowlingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION+ " \0", print_writers);
//			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$ColouredBase$Side" + WhichSide + "$img_Base1*TEXTURE*IMAGE SET "
					+ Constants.T20_MUMBAI_Base1 + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide +
					"$img_Text*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Text + inning.getBowling_team().getTeamBadge() + "\0",print_writers);
			
			getBugImpact(bowlingCard.getPlayer().getPlayerId(),print_writers,matchAllData,WhichSide);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide + "$txt_FirstName*GEOM*TEXT SET "
					+ bowlingCard.getPlayer().getFirstname()+ "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Name$Side" + WhichSide + "$txt_LastName*GEOM*TEXT SET "
					+ (bowlingCard.getPlayer().getSurname()!=null?bowlingCard.getPlayer().getSurname():"") + "\0", print_writers);
			
			switch (whatToProcess.split(",")[3].toUpperCase()) {
			case "FIGURE":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
						+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
						+ "$txt_StatHead*GEOM*TEXT SET THIS MATCH\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
						+ "*GEOM*TEXT SET " + bowlingCard.getWickets() +"-"+ bowlingCard.getRuns() + "\0", print_writers);
				break;
			case "ECONOMY":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
						+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
						+ "$txt_StatHead*GEOM*TEXT SET ECONOMY\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
						+ "*GEOM*TEXT SET " + bowlingCard.getEconomyRate() + "\0", print_writers);
				break;
			case "DOT_PERCENT":
			    String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BOWLER, matchAllData, inning.getInningNumber(),
			            bowlingCard.getPlayerId(),"-",matchAllData.getEventFile().getEvents()).split("-");
			    int dotBalls = Integer.parseInt(Count[0]);
			    int totalBallsBowled = (bowlingCard.getOvers() * 6) + bowlingCard.getBalls();
			    double dotPercent = 0;
			    if (totalBallsBowled > 0) {
			        dotPercent = (dotBalls * 100.0) / totalBallsBowled;
			    }
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$select_DataType"
						+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats"
						+ "$txt_StatHead*GEOM*TEXT SET DOT BALL %\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$Data$Side" + WhichSide + "$Stats$txt_StatValue"
						+ "*GEOM*TEXT SET " + String.format("%.0f", dotPercent) + "\0", print_writers);
			    break;	
			}
			break;
		case "Control_Shift_F3":
//			String summary = "";
//			if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && matchAllData.getSetup().getMaxOvers() == 1) {
//				summary = CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " OFF " + (matchAllData.getSetup().getMaxOvers()*6) + " BALLS";
//			}else {
//				if(matchAllData.getSetup().getTargetOvers() == "" || matchAllData.getSetup().getTargetOvers().trim().isEmpty() && matchAllData.getSetup().getTargetRuns() == 0) {
//					summary = CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " OFF " + (Integer.valueOf(CricketFunctions.GetTargetData(matchAllData).getTargetOvers())*6) + " BALLS";
//				}else {
//					if(matchAllData.getSetup().getTargetOvers() != null && !matchAllData.getSetup().getTargetOvers().isEmpty()) {
//						summary = CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " OFF " + (Integer.valueOf(CricketFunctions.GetTargetData(matchAllData).getTargetOvers())*6) + " BALLS"
//								+ (matchAllData.getSetup().getTargetType() != null ? matchAllData.getSetup().getTargetType().toUpperCase() : "");
//					}
//				}
//			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_TargetBug$ColouredBase" +
					"$img_Base1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_TargetBug$Logo$img_Logo*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Logos + 
					inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_TargetBug$Header$img_Text*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Text + 
					inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_TargetBug$Header$txt_Header*GEOM*TEXT SET " + "TARGET" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_TargetBug$Data$txt_Runs*GEOM*TEXT SET " +
					CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_TargetBug$Data$txt_Dls*GEOM*TEXT SET " +
					(matchAllData.getSetup().getTargetType() != null ? matchAllData.getSetup().getTargetType().toUpperCase() : "") + "\0", print_writers);
			break;
		case "Control_Shift_R":
			whichColor = (config.getCategory().equalsIgnoreCase("WOMEN") ? "WOMENS" : "MENS");
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			
			stats_text = CricketFunctions.GenerateMatchSummaryStatus(inning.getInningNumber(), matchAllData, CricketUtil.SHORT, "", config.getBroadcaster(), 
					true).getTargetOrResult().toUpperCase();
			
			System.out.println("stats_text = " + stats_text);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET " + stats_text + " \0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + whichColor + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 0\0", print_writers);
			break;
		case "Control_Shift_J":
			whichColor = (config.getCategory().equalsIgnoreCase("WOMEN") ? "WOMENS" : "MENS");
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + whichColor + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName"
					+ "*GEOM*TEXT SET " + (performanceBug.getPlayerName() != null ? performanceBug.getPlayerName() : "") +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
					(performanceBug.getSubheader() != null ? performanceBug.getSubheader() : "") +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " +
					(performanceBug.getText4() != null ? performanceBug.getText4() : "") + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*GEOM*TEXT SET\0", print_writers);
			break;
		case "Shift_C":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$SixDistance" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$SixDistance$TextAll$Header_In$txt_Header"
					+ "*GEOM*TEXT SET " + "SIX DISTANCE" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$SixDistance$TextAll$Distance$txt_Distance"
					+ "*ANIMATION*KEY*$S*VALUE SET " + whatToProcess.split(",")[2] + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$SixDistance$TextAll$Distance$Meters"
					+ "*GEOM*TEXT SET METRES\0", print_writers);
			break;
		case "Shift_O":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET " + battingCard.getPlayer().getFull_name() +"\0", print_writers);
			
			if(battingCard.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " +
						battingCard.getHowOutText().split(" b ")[0] + "  b " + battingCard.getHowOutText().split(" b ")[1] +"\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " +
						battingCard.getHowOutText() +"\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
					battingCard.getRuns() + " (" + battingCard.getBalls() + ")" + " \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 0\0", print_writers);
			break;
		case "y":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName"
					+ "*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET " + battingCard.getPlayer().getFull_name() +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*GEOM*TEXT SET "
					+ "FOUR" + CricketFunctions.Plural(battingCard.getFours()).toUpperCase() + ": " + battingCard.getFours() 
					+ " | " + (battingCard.getSixes() == 1?" SIX: ":" SIXES: ") + battingCard.getSixes() + " | " + "S/R: " 
					+ CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 1) +"\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " + (battingCard.
				getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)? battingCard.getRuns() + "*":battingCard.getRuns()) +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " + battingCard.getBalls() +"\0", print_writers);
			
			break;
		case "g":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBowling_team().getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName"
					+ "*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET " + bowlingCard.getPlayer().getFull_name() +"\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
					CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + " - " + bowlingCard.getMaidens()
					+ " - " + bowlingCard.getRuns() + " - " + bowlingCard.getWickets() +"\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 0\0", print_writers);
				
			break;
		case "k":
			whichColor = (config.getCategory().equalsIgnoreCase("WOMEN") ? "WOMENS" : "MENS");
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + whichColor + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName"
					+ "*GEOM*TEXT SET " + (bug.getText1() != null ? bug.getText1() : "") +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
					(bug.getText2() != null ? bug.getText2() : "") +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " +
					(bug.getText3() != null ? bug.getText3() : "") + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*GEOM*TEXT SET " +
					(bug.getText4() != null ? bug.getText4() : "") + "\0", print_writers);
			break;
		case "Shift_F4":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName"
					+ "*GEOM*TEXT SET " + partnership.getFirstPlayer().getTicker_name() + " & " + partnership.getSecondPlayer().getTicker_name() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET " + (partnership.getPartnershipNumber()==0? "P'SHIP": CricketFunctions.ordinal(partnership.getPartnershipNumber()) + " WKT P'SHIP") + "\0", print_writers);
			
			if(partnership1.getPartnershipNumber() == partnership.getPartnershipNumber()) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
						(partnership.getPartnershipNumber()==0? "":partnership.getTotalRuns()) + "*\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " +
						partnership.getTotalBalls() + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
						(partnership.getPartnershipNumber()==0? "":partnership.getTotalRuns()) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " +
						partnership.getTotalBalls() + "\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 0\0", print_writers);
			
			break;
		case "Control_k":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName"
					+ "*GEOM*TEXT SET " + "CURRENT P'SHIP" +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET " + partnership.getFirstPlayer().getTicker_name() + " & " + partnership.getSecondPlayer().getTicker_name() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
					(partnership.getPartnershipNumber()==0? "":partnership.getTotalRuns()) +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " +
					partnership.getTotalBalls() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 0\0", print_writers);
			break;
		case "Control_y":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName"
					+ "*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
					CricketFunctions.getPowerPlayScore(inning, inning.getInningNumber(), "-", matchAllData) +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*GEOM*TEXT SET POWERPLAY\0", print_writers);
			
			break;
		case "h":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*ACTIVE SET 1\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_FirstName"
					+ "*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_LastName"
					+ "*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$Base" +
					"$img_EventBase1*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Runs*GEOM*TEXT SET " +
					CricketFunctions.getTeamScore(inning, "-", false) +"\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Balls*GEOM*TEXT SET " +
					CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bug$All$Text$txt_Sub*GEOM*TEXT SET HIGHLIGHTS\0", print_writers);
			break;
		case "r":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_DRS_Bug$TopLine$Text$txt_TeamName*GEOM*TEXT SET " 
					+ team.getTeamBadge() + " REVIEW" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_DRS_Bug$BottomLine$Text$Side" + WhichSide 
					+ "$txt_Info*GEOM*TEXT SET " + reviewData + "\0", print_writers);
			break;
		}
		return Constants.OK;	
	}
	
	public String PopulateBugBody(int WhichSide, String whatToProcess,MatchAllData matchAllData) throws IOException {
		
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.VIDARBHA:
			return T20VidarbhaBugBody(WhichSide, whatToProcess, matchAllData);
		case Constants.T20_MUMBAI:
			return T20MumbaiBugBody(WhichSide, whatToProcess, matchAllData);
		case Constants.NPL: case Constants.LEGENDS: case Constants.MPL: case Constants.APL:
			switch (whatToProcess.split(",")[0]) {
			case "Alt_f":
				switch (whatToProcess.split(",")[2]) {
				case "MANHATTAN":
					int maxRuns = 0,runsIncr = 0,powerplay_omo=0;
					double lngth = 0;
					String powerPlay = "";
					String powerPlay2 = "";
					switch (config.getBroadcaster().toUpperCase()) {
				
					case Constants.LEGENDS:
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Manhattan$Header$"
								+ "img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Manhattan$Header$"
								+ "txt_FirstName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Manhattan$Header"
								+ "$SubHeaderText$txt_FirstName*GEOM*TEXT SET " + matchAllData.getSetup().getMatchIdent() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + 
								"*FUNCTION*Omo*vis_con SET 6\0", print_writers);
						maxRuns = runsIncr = powerplay_omo=0;
						lngth = 0;
						powerPlay = "";
						
						
						for (int j = 1; j < manhattan.size(); j++) {
							if(manhattan.get(j).getInningNumber() == inning.getInningNumber()) {
								if(Integer.valueOf(manhattan.get(j).getOverTotalRuns()) > maxRuns){
									maxRuns = Integer.valueOf(manhattan.get(j).getOverTotalRuns()); // 33 runs came off 34th over
								}
								while (maxRuns % 5 != 0) {     // 5 label in y-axis
							 		maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
								}
							}
						}
						
						String GFX= "";
						String gfx_name ="";
						GFX="Manhattan";
						gfx_name ="minis$Side";
						
						for(int i = 0; i < 5;i++) {
							runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name + WhichSide + "$" + GFX + "$Runs_Axis$"
									+ "Runs_Data$txt_" + (i+1) + "*GEOM*TEXT SET " + runsIncr*(i+1) + "\0", print_writers);
						}
						
						int powerPlayValue =0;
						if(!CricketFunctions.getBallCountStartAndEndRange(matchAllData, inning).isEmpty()) {
							powerPlayValue = CricketFunctions.getBallCountStartAndEndRange(matchAllData, inning).get(1);
						}
						
						for(int j = 1; j <= matchAllData.getSetup().getMaxOvers(); j++) {
							
							if((j*6) <= powerPlayValue) {
								powerplay_omo = 0;
								powerPlay = "$PowerPlay";
								powerPlay2 = "$Img_Manhattan1";
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name  + WhichSide + "$Manhattan$Scale_Position$" + j +
										"$obj_ScaleY$Select_Style*FUNCTION*Omo*vis_con SET " + powerplay_omo + "\0", print_writers);
								
							}
							else {
								powerplay_omo = 1;
								powerPlay = "$NonPowerPlay";
								powerPlay2 = "$Img_Manhattan2";
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name  + WhichSide + "$Manhattan$Scale_Position$" + j +
										"$obj_ScaleY$Select_Style*FUNCTION*Omo*vis_con SET " + powerplay_omo + "\0", print_writers);
							}
							
							if(j < manhattan.size()) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name + WhichSide + "$" + GFX + "$Scale_Position$"
										+ "Position*FUNCTION*Grid*num_row SET 1\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name + WhichSide + "$" + GFX + "$Scale_Position$"
										+ "Position*FUNCTION*Grid*num_col SET " + (j) + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name + WhichSide + "$" + GFX + "$Scale_Position$"
										+ "Position$" + (j) + "*ACTIVE SET 1\0", print_writers);
								
								lngth = ((172 * Integer.valueOf(manhattan.get(j).getOverTotalRuns())) / maxRuns);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name + WhichSide + "$" + GFX + "$Scale_Position$"
										+ (j) +"$obj_ScaleY$"+ powerPlay + powerPlay2 + "*GEOM*height SET " + lngth + "\0", print_writers);	
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name + WhichSide + "$Manhattan$Scale_Position$"
										+ (j) + "$Wickets$WicketColour*FUNCTION*Omo*vis_con SET " + manhattan.get(j).getOverTotalWickets() + "\0", print_writers);
							}else {
								//-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$Side1$Manhattan$Wickets_Axis$Out$Wkt_1$PowerPlay$Select_Wickets*FUNCTION*Omo*vis_con SET 1>
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name  + WhichSide + "$Manhattan$Scale_Position$" + j +
										"$obj_ScaleY$Select_Style*FUNCTION*Omo*vis_con SET 0\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$" + gfx_name + WhichSide + "$Manhattan$Scale_Position$"
										+ (j) + "$Wickets$WicketColour*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							}
						}
						break;
					}
					break;
				case "WORM":
					int maxRuns1 = 0,runsIncr1=0,wkt_count=0;
					double over_count = 0.0;
					maxRuns1 = runsIncr1 = 0;
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "*FUNCTION*Omo*vis_con SET 7\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Header$"
							+ "img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + "TLogo" + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Header$"
							+ "txt_FirstName*GEOM*TEXT SET COMPARISON\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Header"
							+ "$SubHeaderText$txt_FirstName*GEOM*TEXT SET " + matchAllData.getSetup().getMatchIdent() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Worm$Overs_Axis$txt_Tittle*GEOM*TEXT SET OVERS\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Worm$Overs_Axis$Arrange$txt_6*GEOM*TEXT SET 5\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Worm$Overs_Axis$Arrange$txt_11*GEOM*TEXT SET 10\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Worm$Overs_Axis$Arrange$txt_16*GEOM*TEXT SET 15\0", print_writers);
					
					if(inning.getInningNumber() == 1) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Worm$Select_Team*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Worm$Worms$Graph$Worms$Team_2*ACTIVE SET 0\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Worm$1_Teams$txt_Tittle*GEOM*TEXT SET "+inning.getBatting_team().getTeamName3()+" \0", print_writers);	
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Worm$1_Teams$txt_Runs*GEOM*TEXT SET "+CricketFunctions.getTeamScore(inning, "-", false)+"\0", print_writers);
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Main$Side_" + WhichSide 
//								+ "$1_Teams$txt_Balls*GEOM*TEXT SET "+((6*inning.getTotalOvers())+inning.getTotalBalls())+"\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Worm$1_Teams$txt_Balls*GEOM*TEXT SET "+CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls())+"\0", print_writers);
									
						maxRuns1 = inning.getTotalRuns();
						
					}else if(inning.getInningNumber() == 2) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Worm$Select_Team*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Worm$Worms$Graph$Worms$Team_2*ACTIVE SET 1\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$2_Teams$Team_1$txt_Tittle"
								+ "*GEOM*TEXT SET "+matchAllData.getMatch().getInning().get(0).getBatting_team().getTeamName3()+"\0", print_writers);	
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$2_Teams$Team_1$txt_Runs"
								+ "*GEOM*TEXT SET "+CricketFunctions.getTeamScore(matchAllData.getMatch().getInning().get(0), "-", false)+"\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$2_Teams$Team_2$txt_Tittle"
								+ "*GEOM*TEXT SET "+matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName3()+"\0", print_writers);	
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$2_Teams$Team_2$txt_Runs"
								+ "*GEOM*TEXT SET "+CricketFunctions.getTeamScore(matchAllData.getMatch().getInning().get(1), "-", false)+"\0", print_writers);
							
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Main$Side_" + WhichSide + "$2_Teams$Team_1$txt_Balls*GEOM*TEXT SET "
//								+((6*matchAllData.getMatch().getInning().get(0).getTotalOvers())+ matchAllData.getMatch().getInning().get(0).getTotalBalls())+"\0", print_writers);
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Main$Side_" + WhichSide + "$2_Teams$Team_2$txt_Balls*GEOM*TEXT SET "
//								+((6*matchAllData.getMatch().getInning().get(1).getTotalOvers())+matchAllData.getMatch().getInning().get(1).getTotalBalls())+"\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$2_Teams$Team_1$txt_Balls*GEOM*TEXT SET "
								+CricketFunctions.OverBalls(matchAllData.getMatch().getInning().get(0).getTotalOvers(), matchAllData.getMatch().getInning().get(0).getTotalBalls())+"\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$2_Teams$Team_2$txt_Balls*GEOM*TEXT SET "
								+CricketFunctions.OverBalls(matchAllData.getMatch().getInning().get(1).getTotalOvers(), matchAllData.getMatch().getInning().get(1).getTotalBalls())+"\0", print_writers);
						
						if(matchAllData.getMatch().getInning().get(0).getTotalRuns() > matchAllData.getMatch().getInning().get(1).getTotalRuns()) {
							maxRuns1 = matchAllData.getMatch().getInning().get(0).getTotalRuns();
						}
						else {
							maxRuns1 = matchAllData.getMatch().getInning().get(1).getTotalRuns();
						}
					}
					
					for(int inn_count = 1; inn_count <= Which_Inning; inn_count++) {
						List<String> overByOverRuns = new ArrayList<String>();
						List<String> overByOverwicket = new ArrayList<String>();
						
							overByOverRuns.clear();
							overByOverwicket.clear();
							//wicket_which_over = "";
							for(OverByOverData Over : CricketFunctions.getOverByOverData(matchAllData,inn_count ,"WORM" ,matchAllData.getEventFile().getEvents())) {
								overByOverRuns.add(String.valueOf(Over.getOverTotalRuns()));
							}
							String cumm_runs = String.valueOf(0) + "," + String.join(",", overByOverRuns); // Store Per Overs Runs
							
							if(inn_count == 2) {
								wkt_count=0;
							}
							for(OverByOverData Wicket : CricketFunctions.getOverByOverData(matchAllData,inn_count ,"WORM" ,matchAllData.getEventFile().getEvents())) {
								wkt_count = wkt_count + 1;
								
								if(Wicket.getOverTotalWickets() > 0) {
									for(int w=1; w <= Wicket.getOverTotalWickets(); w++) {
										overByOverwicket.add(String.valueOf(wkt_count-1));
									}
								}
							}
							String cumm_wkts = String.join(",", overByOverwicket); // Store Per Overs Wickets
							
							
							if(matchAllData.getMatch().getInning().get(0).getTotalRuns() > matchAllData.getMatch().getInning().get(1).getTotalRuns()) {
								maxRuns1 = matchAllData.getMatch().getInning().get(0).getTotalRuns();
							}
							else {
								maxRuns1 = matchAllData.getMatch().getInning().get(1).getTotalRuns();
							}
							if(maxRuns1 % 5 == 0) {
								maxRuns1 = maxRuns1 + 1;
							}
							while (maxRuns1 % 5 != 0) {     // 5 label in y-axis
								maxRuns1 = maxRuns1 + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
							}
							
							for(int k = 1; k <= 5; k++) {           // For Y-Axis Value 
								runsIncr1 = maxRuns1 / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36	
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Worm$Runs_Axis$Runs_Data$txt_"+k+"*GEOM*TEXT SET " + (runsIncr1*k) +"\0", print_writers);
							}
							
							over_count = (matchAllData.getSetup().getTargetOvers() != null && !matchAllData.getSetup().getTargetOvers().isEmpty() ? 
							        Double.valueOf(matchAllData.getSetup().getTargetOvers()) : Double.valueOf(matchAllData.getSetup().getMaxOvers()));
							
							if(inn_count == 1) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*strDataY SET "+ cumm_runs.replaceFirst("0,", "") +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iGraphHeightInRuns SET " + (maxRuns1+5) +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iGraphWidthInOvers SET " + over_count +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iNumberOfOversForRandomData SET " + over_count +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iSetWorm INVOKE \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*strWicketsData SET " + cumm_wkts +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iSetWorm INVOKE \0", print_writers);
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*strDataY SET "+ cumm_runs.replaceFirst("0,", "") +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iGraphHeightInRuns SET " + (maxRuns1+5) +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iGraphWidthInOvers SET " + over_count +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iNumberOfOversForRandomData SET " + over_count +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iSetWorm INVOKE \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*strWicketsData SET " + cumm_wkts +"\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Worm$Worms$Team_"+inn_count+"$"+
										inn_count+"$AnimWorm*SCRIPT*INSTANCE*iSetWorm INVOKE \0", print_writers);								
								}
					}
					break;
				case "PARTNRSHIP":
					int impactOutPlayerId=0,row_size = 0;
					double Mult = 0,ScaleFac1 = 0, ScaleFac2 = 0;
					String Left_Batsman="", Right_Batsman="";
					
					boolean impactInThisInning = false, isImpactPlayerStillToBat = false, impactPlayerDataFilled = false, omoHasBeenSet = false;
					Player impactPlayer = null;
					
					impactList.clear();
					
					int concussedInId = 0,concussedOutId = 0;
					String isConcussed = "";
					
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.LEGENDS:
						omo = 3;
						Mult = 50;
						containerName_2 = "DataGrp";
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$Header$"
								+ "img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$Header$"
								+ "txt_FirstName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
								"*FUNCTION*Omo*vis_con SET " + omo + "\0", print_writers);
						
						rowId = 1;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
								+ containerName_2 + "$Row" + rowId +  "$Select_Row_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						for (Partnership ps : inning.getPartnerships()) {
							rowId = rowId + 1;
							Left_Batsman ="" ; Right_Batsman="";
							for (BattingCard bc : inning.getBattingCard()) {
								if(bc.getPlayerId() == ps.getFirstBatterNo()) {
									Left_Batsman = bc.getPlayer().getTicker_name();
								}
								else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
									Right_Batsman = bc.getPlayer().getTicker_name();
								}
							}
							
							if(inning.getPartnerships().size() >= 10 && inning.getTotalWickets()>=10) {
								if(ps.getPartnershipNumber()<=inning.getPartnerships().size()) {
									omo_num = 0;
									containerName = "$Players_Highlight";
								}
							}
							else {
								if(ps.getPartnershipNumber() < inning.getPartnerships().size()) {
									omo_num = 0;
									containerName = "$Players_Highlight";
								}
								else if(ps.getPartnershipNumber() >= inning.getPartnerships().size()) {
									omo_num = 1;
									switch (config.getBroadcaster().toUpperCase()) {
									case Constants.LEGENDS:
										containerName = "$Players_Highlight";
										break;
									}
								}
							}
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" +
									containerName_2 + "*FUNCTION*Grid*num_row SET " + (inning.getPartnerships().size() + 1) + " \0", print_writers);

							switch (config.getBroadcaster().toUpperCase()) {
							case Constants.LEGENDS:
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$"
										+ containerName_2 + "$Row" + rowId + "$Select_Row_Type" + containerName +"$img_Base*TEXTURE*IMAGE SET " + Constants.LEGENDS_BASE + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$"
										+ containerName_2 + "$Row" + rowId + "$Select_Row_Type" + containerName +"$img_Text*TEXTURE*IMAGE SET " + Constants.LEGENDS_TEXT + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
								
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$"
										+ containerName_2 + "$Row" + rowId + "$Select_Row_Type" + containerName +"$txt_Name*GEOM*TEXT SET " + Left_Batsman + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$"
										+ containerName_2 + "$Row" + rowId + "$Select_Row_Type" + containerName +"$txt_Name02*GEOM*TEXT SET " + Right_Batsman + "\0", print_writers);
								
								if(config.getBroadcaster().equalsIgnoreCase(Constants.LEGENDS)) {
									
									if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), ps.getFirstBatterNo()).isEmpty()) {
										switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), ps.getFirstBatterNo())) {
										case "IMP_IN":
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_1*FUNCTION*Omo*vis_con SET 2\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Select_Impact_1$ImpactGrp$Impact*GEOM*TEXT SET " + "IMP" + "\0", print_writers);
											break;
										case "IMP_OUT":
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_1*FUNCTION*Omo*vis_con SET 1\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Select_Impact_1$SubGrp$Impact*GEOM*TEXT SET " + "SUB" + "\0", print_writers);
											break;
										case "CON_IN":
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_1*FUNCTION*Omo*vis_con SET 2\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Select_Impact_1$ImpactGrp$Impact*GEOM*TEXT SET " + "CON" + "\0", print_writers);
											break;
										case "CON_OUT":
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_1*FUNCTION*Omo*vis_con SET 1\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Select_Impact_1$SubGrp$Impact*GEOM*TEXT SET " + "CON" + "\0", print_writers);
											break;
										}
									}else {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
												+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_1*FUNCTION*Omo*vis_con SET 0\0", print_writers);
									}
									
									if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), ps.getSecondBatterNo()).isEmpty()) {
										switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), ps.getSecondBatterNo())) {
										case "IMP_IN":
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_2*FUNCTION*Omo*vis_con SET 2\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Select_Impact_2$ImpactGrp$Impact*GEOM*TEXT SET " + "IMP" + "\0", print_writers);
											break;
										case "IMP_OUT":
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_2*FUNCTION*Omo*vis_con SET 1\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Select_Impact_2$SubGrp$Impact*GEOM*TEXT SET " + "SUB" + "\0", print_writers);
											break;
										case "CON_IN":
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_2*FUNCTION*Omo*vis_con SET 2\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Select_Impact_2$ImpactGrp$Impact*GEOM*TEXT SET " + "CON" + "\0", print_writers);
											break;
										case "CON_OUT":
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_2*FUNCTION*Omo*vis_con SET 1\0", print_writers);
											CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
													+ containerName_2 + "$Row" + rowId + containerName + "$Select_Impact_2$SubGrp$Impact*GEOM*TEXT SET " + "CON" + "\0", print_writers);
											break;
										}
									}else {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$" 
												+ containerName_2 + "$Row" + rowId + containerName + "$Name$Select_Impact_2*FUNCTION*Omo*vis_con SET 0\0", print_writers);
									}
								}
								break;
							}
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$"
								+ containerName_2 + "$Row" + rowId + "$Select_Row_Type" + containerName +"$fig_Runs*GEOM*TEXT SET " + ps.getTotalRuns() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$"
								+ containerName_2 + "$Row" + rowId + "$Select_Row_Type" + containerName +"$fig_Out*GEOM*TEXT SET " + ps.getTotalBalls() + "\0", print_writers);
							
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$"
//								+ containerName_2 + "$Row" + rowId  + "$Select_Row_Type" + containerName + "$Geom_Bar_1*GEOM*width SET " + ScaleFac1 + "\0", print_writers);
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$"
//								+ containerName_2 + "$Row" + rowId  + "$Select_Row_Type" + containerName + "$Geom_Bar_2*GEOM*width SET " + ScaleFac2 + "\0", print_writers);
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Partnership$"
								+ containerName_2 + "*FUNCTION*Grid*row_offset SET 35.5 \0", print_writers);
						break;
					}
					break;
				case "PHASE":
					
					phaseWiseScore = "";
					String PP1 ="-",PP2="-",PP3="-";
					if(inning.getInningNumber()==1) {
						phaseWiseScore = IndexController.MatchStats.getHomeFirstPowerPlay().getTotalRuns()+","+IndexController.MatchStats.getHomeFirstPowerPlay().getTotalWickets()+"_"+
										 IndexController.MatchStats.getHomeSecondPowerPlay().getTotalRuns()+","+IndexController.MatchStats.getHomeSecondPowerPlay().getTotalWickets()+"_"
										 +IndexController.MatchStats.getHomeThirdPowerPlay().getTotalRuns()+","+IndexController.MatchStats.getHomeThirdPowerPlay().getTotalWickets();
					}else if(inning.getInningNumber()==2) {
						phaseWiseScore = IndexController.MatchStats.getAwayFirstPowerPlay().getTotalRuns()+","+IndexController.MatchStats.getAwayFirstPowerPlay().getTotalWickets()+"_"+
								 IndexController.MatchStats.getAwaySecondPowerPlay().getTotalRuns()+","+IndexController.MatchStats.getAwaySecondPowerPlay().getTotalWickets()+"_"
								 +IndexController.MatchStats.getAwayThirdPowerPlay().getTotalRuns()+","+IndexController.MatchStats.getAwayThirdPowerPlay().getTotalWickets();
					}
					String fourSix = "";
					if(inning.getInningNumber()==1) {
						fourSix = IndexController.MatchStats.getHomeFirstPowerPlay().getTotalFours()+","+IndexController.MatchStats.getHomeFirstPowerPlay().getTotalSixes()+"_"+
								  IndexController.MatchStats.getHomeSecondPowerPlay().getTotalFours()+","+IndexController.MatchStats.getHomeSecondPowerPlay().getTotalSixes()+"_"+
								  IndexController.MatchStats.getHomeThirdPowerPlay().getTotalFours()+","+IndexController.MatchStats.getHomeThirdPowerPlay().getTotalSixes();
					}else if(inning.getInningNumber()==2) {
						fourSix = IndexController.MatchStats.getAwayFirstPowerPlay().getTotalFours()+","+IndexController.MatchStats.getAwayFirstPowerPlay().getTotalSixes()+"_"+
								  IndexController.MatchStats.getAwaySecondPowerPlay().getTotalFours()+","+IndexController.MatchStats.getAwaySecondPowerPlay().getTotalSixes()+"_"+
								  IndexController.MatchStats.getAwayThirdPowerPlay().getTotalFours()+","+IndexController.MatchStats.getAwayThirdPowerPlay().getTotalSixes();
					}
					
					if(Integer.valueOf(phaseWiseScore.split("_")[0].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore.split("_")[0].split(",")[1]) == 0) {
						if(Float.valueOf(CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls())) > 0.0) {
							PP1 = "0-0";
						}
					}else {
						PP1 = phaseWiseScore.split("_")[0].split(",")[0]+"-"+phaseWiseScore.split("_")[0].split(",")[1];
					}
					if(Integer.valueOf(phaseWiseScore.split("_")[1].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore.split("_")[1].split(",")[1]) == 0) {
						if(Float.valueOf(CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls())) > 6.0) {
							PP2 = "0-0";
						}
					}else {
						PP2 = phaseWiseScore.split("_")[1].split(",")[0]+"-"+phaseWiseScore.split("_")[1].split(",")[1];
					}
					if(Integer.valueOf(phaseWiseScore.split("_")[2].split(",")[0]) == 0 && Integer.valueOf(phaseWiseScore.split("_")[2].split(",")[1]) == 0) {
						if(Float.valueOf(CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls())) > 15.0) {
							PP3 = "0-0";
						}
					}else {
						PP3 = phaseWiseScore.split("_")[2].split(",")[0]+"-"+phaseWiseScore.split("_")[2].split(",")[1];
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$PhaseWise$Header$"
							+ "img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$PhaseWise$Header$"
							+ "txt_FirstName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$PhaseWise$Header$"
							+ "SubHeaderText$txt_FirstName*GEOM*TEXT SET " + matchAllData.getSetup().getMatchIdent() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"*FUNCTION*Omo*vis_con SET 5\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row2$txt_Name*GEOM*TEXT SET 1-6\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row2$fig_Played*GEOM*TEXT SET " + PP1 + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row2$fig_Won*GEOM*TEXT SET " + fourSix.split("_")[0].split(",")[0] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row2$fig_Lost*GEOM*TEXT SET " + fourSix.split("_")[0].split(",")[1] + "\0", print_writers);
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row3$txt_Name*GEOM*TEXT SET 7-15\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row3$fig_Played*GEOM*TEXT SET " + PP2 + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row3$fig_Won*GEOM*TEXT SET " + fourSix.split("_")[1].split(",")[0] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row3$fig_Lost*GEOM*TEXT SET " + fourSix.split("_")[1].split(",")[1] + "\0", print_writers);
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row4$txt_Name*GEOM*TEXT SET 16-20\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row4$fig_Played*GEOM*TEXT SET " + PP3 + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row4$fig_Won*GEOM*TEXT SET " + fourSix.split("_")[2].split(",")[0] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide +
							"$PhaseWise$Row4$fig_Lost*GEOM*TEXT SET " + fourSix.split("_")[2].split(",")[1] + "\0", print_writers);
					break;
				}
				
				break;
			case "Control_y":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Select" 
						+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$SelectInfo*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$img_Badges"
							+ "*TEXTURE*IMAGE SET " + (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				case Constants.MPL:
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +inning.getBatting_team().getTeamBadge()+ logoCategory +"\0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info01" 
						+ "*GEOM*TEXT SET POWERPLAY\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info03" 
						+ "*GEOM*TEXT SET " + CricketFunctions.getPowerPlayScore(inning, inning.getInningNumber(), "-", matchAllData) + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
						+ "*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1()+ "\0", print_writers);
				
				break;
			case "Control_4":
				
				if(config.getBroadcaster().equalsIgnoreCase(Constants.NPL) || config.getBroadcaster().equalsIgnoreCase(Constants.APL)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes"
							+ "$txt_Header*GEOM*TEXT SET TOURNAMENT FOURS\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$band$img_Base2*TEXTURE*IMAGE SET "
							+ "IMAGE*/Default/Essentials/Textures/Color01\0", print_writers);
				}else if(config.getBroadcaster().equalsIgnoreCase(Constants.MPL)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select$Tournament_Sixes$Sponsor$Select*"
							+ "FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes"
							+ "$txt_Header*GEOM*TEXT SET TOURNAMENT FOURS\0", print_writers);
					
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select$Tournament_Sixes$Sponsor$Select*"
							+ "FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes"
							+ "$txt_Header*GEOM*TEXT SET TOURNAMENT FOURS\0", print_writers);
					
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_1$txt_Unit*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_1$txt_Ten*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_1$txt_Hundread*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
				
				
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_2$txt_Unit*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_2$txt_Ten*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_2$txt_Hundread*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
				
				break;	
			case "6":
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select$Tournament_Sixes$Sponsor$Select*"
						+ "FUNCTION*Omo*vis_con SET 0\0", print_writers);
				
				if(config.getBroadcaster().equalsIgnoreCase(Constants.NPL) || config.getBroadcaster().equalsIgnoreCase(Constants.APL)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$band$img_Base2*TEXTURE*IMAGE SET "
							+ "IMAGE*/Default/Essentials/Textures/Color01\0", print_writers);
				}else if(config.getBroadcaster().equalsIgnoreCase(Constants.MPL)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select$Tournament_Sixes$Sponsor$Select*"
							+ "FUNCTION*Omo*vis_con SET 2\0", print_writers);
					
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes"
						+ "$txt_Header*GEOM*TEXT SET TOURNAMENT SIXES\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_1$txt_Unit*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_1$txt_Ten*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_1$txt_Hundread*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
				
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_2$txt_Unit*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_2$txt_Ten*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Tournament_Sixes$Data"
						+ "$Side_2$txt_Hundread*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
				
			break;
			case "Control_Shift_U": 
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				
				switch (config.getBroadcaster()) {
				case Constants.NPL:  case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Badge$img_Badges*TEXTURE*IMAGE SET " 
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBatting_team().getTeamBadge() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$img_Base2"
							+ "*TEXTURE*IMAGE SET IMAGE*/Default/Essentials/Textures/Color01\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);
					
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Badge$img_Badges"
							+ "*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH + inning.getBatting_team().getTeamBadge() + logoCategory + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name()+ "\0", print_writers);
					
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Badge$Side" + WhichSide + "$img_Badges"
							+ "*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$Side" + WhichSide + "$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);
					break;
				}
				
				switch (whatToProcess.split(",")[3].toUpperCase()) {
				case "SCORE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
							+ "*GEOM*TEXT SET \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Seperator*ACTIVE SET 1\0", print_writers);
					
					if(battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Score"
								+ "*GEOM*TEXT SET " + battingCard.getRuns() + "*" + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Score"
								+ "*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
							+ "*GEOM*TEXT SET " + battingCard.getBalls() + " BALL" + CricketFunctions.Plural(battingCard.getBalls()).toUpperCase() + "\0", print_writers);
					
					break;

				case "STRIKERATE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
							+ "*GEOM*TEXT SET STRIKE RATE \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Score"
							+ "*GEOM*TEXT SET " +CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 1) + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Seperator*ACTIVE SET 0\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
							+ "*GEOM*TEXT SET \0", print_writers);
					break;
					
				case "BOUNDARY_PERCENT":

				    int fours = battingCard.getFours();
				    int sixes = battingCard.getSixes();
				    int totalRuns = battingCard.getRuns();

				    int boundaryRuns = (fours * 4) + (sixes * 6);

				    double boundaryPercent = 0;

				    if (totalRuns > 0) {
				        boundaryPercent = (boundaryRuns * 100.0) / totalRuns;
				    }
				    
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);

				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
				        + "*GEOM*TEXT SET BOUNDARY %\0",print_writers);
				    
//				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
//					        + "*GEOM*TEXT SET MATCH BOUNDARY%\0",print_writers);

				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Score"
				        + "*GEOM*TEXT SET "+ String.format("%.0f", boundaryPercent)+ "\0",print_writers);

				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide
				        + "$Seperator*ACTIVE SET 0\0",print_writers);

				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
				        + "*GEOM*TEXT SET \0",print_writers);

				    break;
				    	
				 case "BOUNDARY":
					 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
								+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
							+ "*GEOM*TEXT SET FOURS/SIXES \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Score"
							+ "*GEOM*TEXT SET " +battingCard.getFours() + "/" + battingCard.getSixes() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Seperator*ACTIVE SET 0\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
							+ "*GEOM*TEXT SET \0", print_writers);
					break;	
				}
				break;
			case "Control_Shift_V":
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Badge$img_Badges*TEXTURE*IMAGE SET " 
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBowling_team().getTeamBadge() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$img_Base2"
							+ "*TEXTURE*IMAGE SET IMAGE*/Default/Essentials/Textures/Color02\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + bowlingCard.getPlayer().getTicker_name() + " - THIS MATCH"+ "\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Badge$img_Badges"
							+ "*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH + inning.getBowling_team().getTeamBadge() + logoCategory + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$txt_Header"
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);
					
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Badge$Side" + WhichSide + "$img_Badges"
							+ "*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$band$Side" + WhichSide + "$txt_Header"
							+ "*GEOM*TEXT SET " + bowlingCard.getPlayer().getTicker_name() + " - THIS MATCH" + "\0", print_writers);
					break;
				}
				
				switch (whatToProcess.split(",")[3].toUpperCase()) {
				
				case "DOT_PERCENT":

				    String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.BOWLER, matchAllData, inning.getInningNumber(),
				            bowlingCard.getPlayerId(),"-",matchAllData.getEventFile().getEvents()).split("-");

				    int dotBalls = Integer.parseInt(Count[0]);

				    int totalBallsBowled = (bowlingCard.getOvers() * 6) + bowlingCard.getBalls();

				    double dotPercent = 0;

				    if (totalBallsBowled > 0) {
				        dotPercent = (dotBalls * 100.0) / totalBallsBowled;
				    }

				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
				            + "*GEOM*TEXT SET DOT BALL %\0",print_writers);
				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Seperator"
				    		+ "*ACTIVE SET 0\0",print_writers);

				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Score"
				            + "*GEOM*TEXT SET " + String.format("%.0f", dotPercent)+ "\0",print_writers);

				    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
				            + "*GEOM*TEXT SET \0",print_writers);

				    break;
				    
				case "FIGURE":
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
							+ "*GEOM*TEXT SET \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Seperator"
							+ "*ACTIVE SET 1\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Score"
							+ "*GEOM*TEXT SET " + bowlingCard.getWickets() +"-"+ bowlingCard.getRuns() + "\0", print_writers);
					
					switch (config.getBroadcaster()) {
					case Constants.NPL: case Constants.MPL: case Constants.APL:
						if(bowlingCard.getOvers() == 0 && bowlingCard.getBalls() >= 0) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
									+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + 
									" OVERS" + "\0", print_writers);
						}else if(bowlingCard.getOvers() == 1 && bowlingCard.getBalls() == 0) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
									+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + 
									" OVER" + "\0", print_writers);
						}else if(bowlingCard.getOvers() == 1 && bowlingCard.getBalls() > 0) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
									+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + 
									" OVERS" + "\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
									+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + 
									" OVERS" + "\0", print_writers);
						}
						break;
					case Constants.LEGENDS:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
								+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + " OVER" + 
								CricketFunctions.Plural(bowlingCard.getOvers()).toUpperCase() + "\0", print_writers);
						break;
					}
					break;

				case "ECONOMY":
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$StatHead1"
							+ "*GEOM*TEXT SET ECONOMY \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Seperator*ACTIVE SET 0\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Score"
							+ "*GEOM*TEXT SET " + bowlingCard.getEconomyRate() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PopUps$POP_UP$Side" + WhichSide + "$Balls"
							+ "*GEOM*TEXT SET \0", print_writers);
					break;
				}
				break;
			case "Control_Shift_J":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Select*FUNCTION*Omo*vis_con SET 4\0", print_writers);
				
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					if(performanceBug.getSponsor() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "
								+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) + performanceBug.getSponsor()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "
								+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) +"TLogo"+"\0", print_writers);
					}
					break;
				case Constants.MPL: 
					if(performanceBug.getSponsor() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +performanceBug.getSponsor()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +"TLogo"+"\0", print_writers);
					}
					break;	
				case Constants.LEGENDS:
					if(performanceBug.getSponsor() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +performanceBug.getSponsor()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +"TLogo"+"\0", print_writers);
					}
					break;
				}
				
				if(performanceBug.getPlayerName() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$noname$Info01*GEOM*TEXT SET "+performanceBug.getPlayerName()+"\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$noname$Info01*GEOM*TEXT SET \0", print_writers);
				}
				if(performanceBug.getSubheader() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$noname$Info02*GEOM*TEXT SET "+  performanceBug.getSubheader() +"\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$noname$Info02*GEOM*TEXT SET \0", print_writers);
				}
				if(performanceBug.getScore() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$group$Info03*GEOM*TEXT SET "+performanceBug.getScore() +"\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$group$Info03*GEOM*TEXT SET \0", print_writers);
				}
				if(performanceBug.getText4() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$SelectInfo*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$group$Info04*GEOM*TEXT SET "+performanceBug.getText4() +"\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$SelectInfo*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$group$Info04*GEOM*TEXT SET \0", print_writers);
				}
				break;
			case "Control_Shift_F3": 
				
				String summary = "",team_name = "";
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && matchAllData.getSetup().getMaxOvers() == 1) {
					
					team_name = inning.getBatting_team().getTeamName1(); 
					summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS" + " TO WIN THE SUPER OVER";
					
				}else {
					
					if(matchAllData.getSetup().getTargetOvers() != null && !matchAllData.getSetup().getTargetOvers().isEmpty()) {
						team_name = inning.getBatting_team().getTeamName1();
						summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS" + " TO WIN " + "FROM " + 
								CricketFunctions.GetTargetData(matchAllData).getTargetOvers() + " OVERS" + (matchAllData.getSetup().getTargetType() != null && 
								!matchAllData.getSetup().getTargetType().isEmpty() ? " " + matchAllData.getSetup().getTargetType() : "");
					}else {
						team_name = inning.getBatting_team().getTeamName1();
						summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS" + " TO WIN " + 
								"FROM " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers() + " OVERS";
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges"
							+ "*TEXTURE*IMAGE SET " + (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +inning.getBatting_team().getTeamBadge()+ logoCategory +"\0", print_writers);
					break;	
				case Constants.LEGENDS:
					if (matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && matchAllData.getSetup().getMaxOvers() == 1) {
					    team_name = inning.getBatting_team().getTeamName3();
					    summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS TO WIN FROM " + (matchAllData.getSetup().getMaxOvers() * 6) + " BALLS";
					} else {
					    if (matchAllData.getSetup().getTargetOvers() == null || matchAllData.getSetup().getTargetOvers().trim().isEmpty() || matchAllData.getSetup().getTargetRuns() == 0) {
					        team_name = inning.getBatting_team().getTeamName3();
					        summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS TO WIN FROM " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers() + " OVERS";
					    } else {
					        if (!matchAllData.getSetup().getTargetOvers().isEmpty()) {
					            team_name = inning.getBatting_team().getTeamName3();
					            summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS TO WIN FROM " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers() + " OVERS";
					        }
					        if (matchAllData.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("VJD") || matchAllData.getSetup().getTargetType().toUpperCase().equalsIgnoreCase("DLS")) {
					            team_name = inning.getBatting_team().getTeamName3();
					            summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS TO WIN FROM " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers() + 
					            		" OVERS (" + matchAllData.getSetup().getTargetType() + ")";
					        }
					    }
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info03*GEOM*TEXT SET "+team_name+" "+summary+"\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info01*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info03*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info02*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info04*ACTIVE SET 0\0", print_writers);
				
				break;
			case "g":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Select" 
						+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBowling_team().getTeamBadge()+"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$Info02" 
							+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + " - " + bowlingCard.getMaidens() 
							+ " - " + bowlingCard.getRuns() + " - " + bowlingCard.getWickets() + "\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +inning.getBowling_team().getTeamBadge()+ logoCategory +"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$Info02" 
							+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + " - " + bowlingCard.getMaidens() 
							+ " - " + bowlingCard.getRuns() + " - " + bowlingCard.getWickets() + "\0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +inning.getBowling_team().getTeamBadge()+"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$Info02" 
							+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + " - " + bowlingCard.getMaidens() 
							+ " - " + bowlingCard.getRuns() + " - " + bowlingCard.getWickets() + "\0", print_writers);
					break;
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$Info01" 
						+ "*GEOM*TEXT SET " + bowlingCard.getPlayer().getTicker_name() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info01*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info02*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info03*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info04*ACTIVE SET 0\0", print_writers);
				
				break;
				
			case "y":
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Select" 
						+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$SelectInfo*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +inning.getBatting_team().getTeamBadge()+ logoCategory +"\0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info01" 
						+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "\0", print_writers);
				
				if(battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info03" 
							+ "*GEOM*TEXT SET " + battingCard.getRuns() + "*" + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info03" 
							+ "*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info04" 
						+ "*GEOM*TEXT SET  " + battingCard.getBalls() + "\0", print_writers);
				
				if(battingCard.getSixes() != 0 && battingCard.getFours() != 0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
							+ "*GEOM*TEXT SET " +"FOURS:  " + battingCard.getFours() + "   SIXES:  "  + battingCard.getSixes() + "\0", print_writers);
				}else if(battingCard.getFours() != 0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
							+ "*GEOM*TEXT SET " +"FOURS:  " + battingCard.getFours() + "\0", print_writers);
				}else if(battingCard.getSixes() != 0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
							+ "*GEOM*TEXT SET " + "SIXES:  " + battingCard.getSixes() + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
							+ "*GEOM*TEXT SET " + "STRIKE RATE: " + CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 1) + "\0", print_writers);
				}
				
				break;
			case "Shift_F":
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Select*FUNCTION*Omo*vis_con SET 4\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$SelectInfo*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +inning.getBatting_team().getTeamBadge()+ logoCategory +"\0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$noname$Info01*GEOM*TEXT SET "+ battingCard.getPlayer().getTicker_name() +"\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$noname$Info02*GEOM*TEXT SET "+ battingCard.getHowOutText() +"\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$group$Info03*GEOM*TEXT SET "+ battingCard.getRuns() +"\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$group$Info04*GEOM*TEXT SET "+ battingCard.getBalls() +"\0", print_writers);
				
				break;
			case "Shift_O":
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Select" 
						+ "*FUNCTION*Omo*vis_con SET 4\0", print_writers);
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +inning.getBatting_team().getTeamBadge()+ logoCategory +"\0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info01" 
						+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info03" 
						+ "*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info04" 
						+ "*GEOM*TEXT SET  " + battingCard.getBalls() + "\0", print_writers);
				
				
				if(battingCard.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
					if(battingCard.getWasHowOutFielderSubstitute() != null && battingCard.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
								+ "*GEOM*TEXT SET " + "run out " + " (sub - " + battingCard.getHowOutFielder().getTicker_name() + ")" + "\0", print_writers);
					} else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
								+ "*GEOM*TEXT SET " + "run out (" + battingCard.getHowOutFielder().getTicker_name() + ")" + "\0", print_writers);
					}
				}else if(battingCard.getHowOut().equalsIgnoreCase(CricketUtil.STUMPED)) {
					if(battingCard.getWasHowOutFielderSubstitute() != null && battingCard.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
								+ "*GEOM*TEXT SET " + "st" +  " (sub - " + battingCard.getHowOutFielder().getTicker_name() + ")  b " + 
								battingCard.getHowOutBowler().getTicker_name() + "\0", print_writers);
					} else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
								+ "*GEOM*TEXT SET " + "st " + battingCard.getHowOutFielder().getTicker_name() + "  b " + 
								battingCard.getHowOutBowler().getTicker_name() + "\0", print_writers);
					}
				}else if(battingCard.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
					if(battingCard.getWasHowOutFielderSubstitute() != null && battingCard.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
								+ "*GEOM*TEXT SET " + "c" +  " (sub - " + battingCard.getHowOutFielder().getTicker_name() + ")  b " + 
								battingCard.getHowOutBowler().getTicker_name() + "\0", print_writers);
					} else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
								+ "*GEOM*TEXT SET " + "c " + battingCard.getHowOutFielder().getTicker_name() + "  b " + 
								battingCard.getHowOutBowler().getTicker_name() + "\0", print_writers);
					}
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$Info02" 
							+ "*GEOM*TEXT SET " + battingCard.getHowOutText() + "\0", print_writers);
				}
				
				break;
			case "Control_k":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Select" 
						+ "*FUNCTION*Omo*vis_con SET 3\0", print_writers);
				
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Double$img_Badges*TEXTURE*IMAGE SET "
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +inning.getBatting_team().getTeamBadge()+ logoCategory +"\0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Double$Info01" 
						+ "*GEOM*TEXT SET CURRENT \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Double$Info02" 
						+ "*GEOM*TEXT SET P'SHIP : \0", print_writers);
				
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Double$Info03" 
						+ "*GEOM*TEXT SET " + partnership.getFirstPlayer().getTicker_name() + " " + (partnership.getPartnershipNumber()== 0 ? "" : partnership.getFirstBatterRuns()+" ("+partnership.getFirstBatterBalls()+")") + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Double$Info05" 
						+ "*GEOM*TEXT SET " + partnership.getSecondPlayer().getTicker_name() + " " + (partnership.getPartnershipNumber()== 0 ? "" : partnership.getSecondBatterRuns()+" ("+partnership.getSecondBatterBalls()+")")+ " \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Double$Info04" 
						+ "*GEOM*TEXT SET " + (partnership.getPartnershipNumber()==0? "":partnership.getTotalRuns() + "* (" + partnership.getTotalBalls() + ")") + " \0", print_writers);
				
				break;
			case "Shift_F4":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Select*FUNCTION*Omo*vis_con SET 3\0", print_writers);
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Double$img_Badges*TEXTURE*IMAGE SET "
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +inning.getBatting_team().getTeamBadge()+ logoCategory +"\0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				}
				
				System.out.println("partnership =" + partnership.getPartnershipNumber());
				
				if(partnership.getPartnershipNumber() == 0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$Info01*GEOM*TEXT SET  \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$Info04*GEOM*TEXT SET "+partnership.getTotalRuns() + " (" + partnership.getTotalBalls() + ")"+"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$Info03*GEOM*TEXT SET " + partnership.getFirstPlayer().getTicker_name() + " "+partnership.getFirstBatterRuns()+" ("+partnership.getFirstBatterBalls()+")"+"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$Info05*GEOM*TEXT SET " + partnership.getSecondPlayer().getTicker_name()  + " " + partnership.getSecondBatterRuns()+" ("+partnership.getSecondBatterBalls()+")"+"\0", print_writers);
					
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$Info01*GEOM*TEXT SET "+CricketFunctions.ordinal(partnership.getPartnershipNumber())+" WICKET" +"\0", print_writers);
					if(partnership.getPartnershipNumber() == inning.getPartnerships().size()) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Double$Info04*GEOM*TEXT SET "+partnership.getTotalRuns() + "* (" + partnership.getTotalBalls() + ")"+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Double$Info04*GEOM*TEXT SET "+partnership.getTotalRuns() + " (" + partnership.getTotalBalls() + ")"+"\0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$Info03*GEOM*TEXT SET " + partnership.getFirstPlayer().getTicker_name() + " "+partnership.getFirstBatterRuns()+" ("+partnership.getFirstBatterBalls()+")"+"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Double$Info05*GEOM*TEXT SET " + partnership.getSecondPlayer().getTicker_name()  + " " + partnership.getSecondBatterRuns()+" ("+partnership.getSecondBatterBalls()+")"+"\0", print_writers);
					
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Double$Info02*GEOM*TEXT SET P'SHIP:\0", print_writers);
				
				
				break;
			case "Alt_p":
				System.out.println(whatToProcess);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) +team.getTeamBadge()+"\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH + team.getTeamBadge() + logoCategory +"\0", print_writers);
					break;	
				case Constants.LEGENDS:		
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +team.getTeamBadge()+
							"\0", print_writers);
					break;
				}
				
			    switch (config.getBroadcaster()) {
				 case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info03*GEOM*TEXT SET " + team.getTeamName3() + " WON THE TOSS "
							+"& CHOSE TO "+whatToProcess.split(",")[2].split("-")[1]+"\0", print_writers);
					break;
				 case Constants.NPL: case Constants.APL:
					 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Single$Info03*GEOM*TEXT SET " + team.getTeamName1() + " WON THE TOSS "
								+"& CHOSE TO "+whatToProcess.split(",")[2].split("-")[1]+"\0", print_writers);
					 break;
				 default:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info03*GEOM*TEXT SET " + team.getTeamName1() + " WON THE TOSS "
							+"& CHOSE TO "+whatToProcess.split(",")[2].split("-")[1]+"\0", print_writers);
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info01*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info02*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info03*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info04*ACTIVE SET 0\0", print_writers);
				break;
			case "h":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Select*FUNCTION*Omo*vis_con SET 4\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$SelectInfo*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.MPL_LOGO_PATH +inning.getBatting_team().getTeamBadge()+ logoCategory +"\0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$img_Badges*TEXTURE*IMAGE SET "+ Constants.LEGENDS_LOGO_PATH +inning.getBatting_team().getTeamBadge()+"\0", print_writers);
					break;
				}

				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$noname$Info01*GEOM*TEXT SET " + (config.getBroadcaster().equalsIgnoreCase(Constants.LEGENDS) ? inning.getBatting_team().getTeamName3() 
					    	    : inning.getBatting_team().getTeamName1()) + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$noname$Info02*GEOM*TEXT SET HIGHLIGHTS\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$PowerPlayBug$group$Info04*GEOM*TEXT SET "+CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) +"\0", print_writers);
				
				if (inning.getTotalWickets() >= 10) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$group$Info03*GEOM*TEXT SET "+inning.getTotalRuns() +"\0", print_writers);
				} else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$PowerPlayBug$group$Info03*GEOM*TEXT SET "+inning.getTotalRuns()+ "-"+ inning.getTotalWickets() +"\0", print_writers);
				}
				break;	
			case "k":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					if(bug.getSponsor() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
								+(config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)+bug.getSponsor()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
								+(config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)+"TLogo"+"\0", print_writers);
					}
					break;
				case Constants.MPL: 
					
					if(bug.getSponsor() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.MPL_LOGO_PATH+bug.getSponsor()+"\0", print_writers);
					}else {
						
						if(config.getCategory().equalsIgnoreCase("MEN")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
									+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.MPL_LOGO_PATH+"TLogo_LT_M"+"\0", print_writers);
						}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
									+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.MPL_LOGO_PATH+"TLogo_LT_WM"+"\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
									+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.MPL_LOGO_PATH+"TLogo"+"\0", print_writers);
						}
					}
					break;	
				case Constants.LEGENDS:
					if(bug.getSponsor() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.LEGENDS_LOGO_PATH+bug.getSponsor()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.LEGENDS_LOGO_PATH+"TLogo"+"\0", print_writers);
					}
					break;
				}
				
				if(bug.getText4() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info04*GEOM*TEXT SET "+bug.getText4()+"\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info04*GEOM*TEXT SET \0", print_writers);
				}
				
				if(bug.getText3() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info03*GEOM*TEXT SET "+bug.getText3()+"\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info03*GEOM*TEXT SET \0", print_writers);
				}
				
				if(bug.getText2() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info02*GEOM*TEXT SET "+bug.getText2()+"\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info02*GEOM*TEXT SET \0", print_writers);
				}
				
				if(bug.getText1() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info01*GEOM*TEXT SET "+bug.getText1()+"\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$Info01*GEOM*TEXT SET \0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info01*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info02*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info03*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info04*ACTIVE SET 1\0", print_writers);
				
				
				break;
			case "Control_Shift_R":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info01*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info03*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info02*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info04*ACTIVE SET 0\0", print_writers);
				
				String matchResult = null;
				switch (config.getBroadcaster()) {
				case Constants.LEGENDS:
					matchResult = CricketFunctions.GenerateMatchSummaryStatus(inning.getInningNumber(), matchAllData, CricketUtil.MIDDLE, "", 
							config.getBroadcaster(), true).getTargetOrResult().toUpperCase();
					break;
				default:
					matchResult = CricketFunctions.GenerateMatchSummaryStatus(inning.getInningNumber(), matchAllData, CricketUtil.FULL, "", 
							config.getBroadcaster(), false).getTargetOrResult().toUpperCase();
					break;
				}
				
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
					if(matchResult.contains("tied")) {
						switch (config.getBroadcaster()) {
						case Constants.NPL: case Constants.APL:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
									+(config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)+"TLogo"+"\0", print_writers);
							break;
						case Constants.MPL: 
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
									+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.MPL_LOGO_PATH+"TLogo"+"\0", print_writers);
							break;	
						case Constants.LEGENDS:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
									+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.LEGENDS_LOGO_PATH+"TLogo"+"\0", print_writers);
							break;
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Single$Info03*GEOM*TEXT SET SUPER OVER TIED, WINNER WILL BE DECIDED BY ANOTHER SUPER OVER\0", print_writers);
					}else {
						if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
							if(CricketFunctions.GetTargetData(matchAllData).getRemaningRuns() <= 0) {
								switch (config.getBroadcaster()) {
								case Constants.NPL: case Constants.APL:
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
											+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)
											+matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamBadge()+"\0", print_writers);
									break;
								case Constants.MPL: 
									
									if(config.getCategory().equalsIgnoreCase("MEN")) {
										logoCategory = "M";
									}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
										logoCategory = "W";
									}else {
										logoCategory = "";
									}
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
											+ Constants.MPL_LOGO_PATH+matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamBadge()+ logoCategory +"\0", print_writers);
									break;	
								case Constants.LEGENDS:
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
											+ Constants.LEGENDS_LOGO_PATH+matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamBadge()+"\0", print_writers);
									break;
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$Info03*GEOM*TEXT SET "
										+ (config.getBroadcaster().equalsIgnoreCase(Constants.LEGENDS) ? matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName3() 
										: matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName1()) + " WIN THE SUPER OVER\0", print_writers);
							}else if(matchAllData.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
									matchAllData.getMatch().getInning().get(1).getTotalOvers() >= matchAllData.getSetup().getMaxOvers()) {
								
								switch (config.getBroadcaster()) {
								case Constants.NPL: case Constants.APL:
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
											+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)
											+matchAllData.getMatch().getInning().get(1).getBowling_team().getTeamBadge()+"\0", print_writers);
									break;
								case Constants.MPL: 
									
									if(config.getCategory().equalsIgnoreCase("MEN")) {
										logoCategory = "M";
									}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
										logoCategory = "W";
									}else {
										logoCategory = "";
									}
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
											+ Constants.MPL_LOGO_PATH+matchAllData.getMatch().getInning().get(1).getBowling_team().getTeamBadge()+ logoCategory +"\0", print_writers);
									break;	
								case Constants.LEGENDS:
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
											+ Constants.LEGENDS_LOGO_PATH+matchAllData.getMatch().getInning().get(1).getBowling_team().getTeamBadge()+"\0", print_writers);
									break;
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$Info03*GEOM*TEXT SET " + 
										(config.getBroadcaster().equalsIgnoreCase(Constants.LEGENDS) ? matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName3() 
												: matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName1()) + " WIN THE SUPER OVER\0", print_writers);
							}
						}else {
							for(Team tm : cricketService.getTeams()) {
								if(matchResult.contains(tm.getTeamName1())) {
									switch (config.getBroadcaster()) {
									case Constants.NPL: case Constants.APL:
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges"
												+ "*TEXTURE*IMAGE SET " + (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)
												+tm.getTeamBadge()+"\0", print_writers);
										break;
									case Constants.MPL: 
										
										if(config.getCategory().equalsIgnoreCase("MEN")) {
											logoCategory = "M";
										}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
											logoCategory = "W";
										}else {
											logoCategory = "";
										}
										
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges"
												+ "*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH+tm.getTeamBadge()+ logoCategory +"\0", print_writers);
										break;	
									case Constants.LEGENDS:
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges"
												+ "*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH+tm.getTeamBadge()+"\0", print_writers);
										break;
									}
								}
							}
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$Info03*GEOM*TEXT SET " + 
									matchResult.split("WIN")[0] + " WIN " + matchResult.split("WIN")[1] + "\0", print_writers);
						}
					}
				}else {
					if(matchResult.contains("tied")) {
						switch (config.getBroadcaster()) {
						case Constants.NPL: case Constants.APL:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
									+(config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)+"TLogo"+"\0", print_writers);
							break;
						case Constants.MPL: 
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
									+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.MPL_LOGO_PATH+"TLogo"+"\0", print_writers);
							break;	
						case Constants.LEGENDS:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
									+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.LEGENDS_LOGO_PATH+"TLogo"+"\0", print_writers);
							break;
						}
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Single$Info03*GEOM*TEXT SET MATCH TIED, WINNER WILL BE DECIDED BY SUPER OVER\0", print_writers);
					}else {
						for(Team tm : cricketService.getTeams()) {
							if(matchResult.contains(tm.getTeamName1())) {
								switch (config.getBroadcaster()) {
								case Constants.NPL: case Constants.APL:
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges"
											+ "*TEXTURE*IMAGE SET " + (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)
											+tm.getTeamBadge()+"\0", print_writers);
									break;
								case Constants.MPL: 
									
									if(config.getCategory().equalsIgnoreCase("MEN")) {
										logoCategory = "M";
									}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
										logoCategory = "W";
									}else {
										logoCategory = "";
									}
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges"
											+ "*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH+tm.getTeamBadge()+ logoCategory +"\0", print_writers);
									break;	
								case Constants.LEGENDS:
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges"
											+ "*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH+tm.getTeamBadge()+"\0", print_writers);
									break;
								}
							}
						}
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$Info03*GEOM*TEXT SET " 
								+ matchResult.split("WIN")[0] + " WIN " + matchResult.split("WIN")[1] + "\0", print_writers);
					}
				}
				break;
			case "Shift_C":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				switch (config.getBroadcaster()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
							+(config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)+"TLogo"+"\0", print_writers);
					break;
				case Constants.MPL: 
					if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.MPL_LOGO_PATH+"TLogo_WM"+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
								+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.MPL_LOGO_PATH+"TLogo"+"\0", print_writers);
					}
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
							+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.LEGENDS_LOGO_PATH+"TLogo"+"\0", print_writers);
					break;
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info03*GEOM*TEXT SET "+whatToProcess.split(",")[2]+" METRES"+"\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info01*GEOM*TEXT SET SIX DISTANCE\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info02*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info04*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info01*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
						+ "$Single$Info03*ACTIVE SET 1\0", print_writers);
				break;
			}
			break;
		case Constants.ICC_U19_2023:
			switch (whatToProcess.split(",")[0]) {
			case "Control_y":
				if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("NEP")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$img_Shadow*ACTIVE SET 0 \0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$img_Shadow*ACTIVE SET 1 \0", print_writers);
				}
				break;
			case "g": case "y": case "Shift_O": case "Shift_F": case "Control_k": case "Shift_F4":case "Alt_b":
				if(team.getTeamName4().equalsIgnoreCase("NEP")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$img_Shadow*ACTIVE SET 0 \0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$img_Shadow*ACTIVE SET 1 \0", print_writers);
				}
				break;	
			case "o":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select"
						+ "*FUNCTION*Omo*vis_con SET 0  \0", print_writers);
				CricketFunctions.DoadWriteCommandToSelectedViz(1,"-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select$PlayerOftheMatch$Data$Select_Sponsor"
						+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToSelectedViz(2,"-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select$PlayerOftheMatch$Data$Select_Sponsor"
						+ "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
				break;
			case "t":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select"
						+ "*FUNCTION*Omo*vis_con SET 2  \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToSelectedViz(2,"-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select$Third_Umpire$1$Data$Side_1$SubText$Side1"
						+ "$group*ACTIVE SET 0 \0", print_writers);
				
				for(VariousText vt : VariousText) {
					if(vt.getVariousType().equalsIgnoreCase("THIRDUMPIRE") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Third_Umpire"
								+ "$1$Data$Side_1$SubText$txt_Sub*GEOM*TEXT SET " + vt.getVariousText() + "\0", print_writers);
					}else if(vt.getVariousType().equalsIgnoreCase("THIRDUMPIRE") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Footer$Top_Align$Side" + WhichSide + "$Select_FooterType"
//							+ "$Info_Text$Data$txt_Info_1*GEOM*TEXT SET " + CricketFunctions.
//							generateMatchSummaryStatus(WhichInning, matchAllData, CricketUtil.FULL, CricketUtil.BEAT).toUpperCase() + "\0", print_writers);
					}
				}
				break;	
			}
			switch(whatToProcess.split(",")[0]) {
			case "Alt_p": 
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select"
						+ "*FUNCTION*Omo*vis_con SET 1  \0", print_writers);
				if(matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase("NEP")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$img_Flag1$img_Shadow*ACTIVE SET 0 \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$img_Flag2$img_Shadow*ACTIVE SET 1 \0", print_writers);
				}else if(matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase("NEP")){
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$img_Flag1$img_Shadow*ACTIVE SET 1 \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$img_Flag2$img_Shadow*ACTIVE SET 0 \0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$img_Flag1$img_Shadow*ACTIVE SET 1 \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$img_Flag2$img_Shadow*ACTIVE SET 1 \0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$img_Flag1*TEXTURE*IMAGE SET " 
						+ Constants.ICC_U19_2023_FLAG_PATH + matchAllData.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
						
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss$img_Flag2*TEXTURE*IMAGE SET " 
						+ Constants.ICC_U19_2023_FLAG_PATH + matchAllData.getSetup().getAwayTeam().getTeamName4() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Toss"
						+ "$txt_Info*GEOM*TEXT SET " + whatToProcess.split(",")[2].split("-")[0] + " WON THE TOSS & ELECTED TO " + 
						whatToProcess.split(",")[2].split("-")[1]+ "\0", print_writers);
				
				break;
			case "h":
				if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("NEP")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$img_Shadow*ACTIVE SET 0 \0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$img_Shadow*ACTIVE SET 1 \0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*ACTIVE SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + 
						"$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + inning.getBatting_team().getTeamName4() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Name*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "\0", print_writers);
				if (inning.getTotalWickets() >= 10) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Runs*GEOM*TEXT SET " + inning.getTotalRuns() + "\0", print_writers);
				} else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Runs*GEOM*TEXT SET " + inning.getTotalRuns()+ " - "+ inning.getTotalWickets() + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " +" "+ CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
						+ "$txt_Sub*GEOM*TEXT SET " + "HIGHLIGHTS" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
						+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
				break;
			case "Control_y":
				String pp = "";
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*ACTIVE SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + inning.getBatting_team().getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Name*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " + "" + "\0", print_writers);
				
				if(whatToProcess.split(",")[2].equalsIgnoreCase("p1")) {
					pp = CricketFunctions.getFirstPowerPlayScore(matchAllData,inning.getInningNumber(), matchAllData.getEventFile().getEvents());
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide 
							+ "$txt_Sub*GEOM*TEXT SET " + "POWERPLAY 1 " +  "(OVERS " + inning.getFirstPowerplayStartOver() + " TO " + 
							inning.getFirstPowerplayEndOver() + ") " + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Runs*GEOM*TEXT SET "  + pp.split(",")[0] + "\0", print_writers);
					
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("p2")) {
					pp=CricketFunctions.getSecPowerPlayScore(matchAllData, inning.getInningNumber(), matchAllData.getEventFile().getEvents());
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide 
							+ "$txt_Sub*GEOM*TEXT SET " + "POWERPLAY 2 " +  "(OVERS " + inning.getSecondPowerplayStartOver() + " TO " + 
							inning.getSecondPowerplayEndOver() + ") " + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Runs*GEOM*TEXT SET "  + pp.split(",")[0] + "\0", print_writers);
					
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("p3")) {
					pp=CricketFunctions.getThirdPowerPlayScore(matchAllData, inning.getInningNumber(), matchAllData.getEventFile().getEvents());
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide 
							+ "$txt_Sub*GEOM*TEXT SET " + "POWERPLAY 3 " +  "(OVERS " + inning.getThirdPowerplayStartOver() + " TO " + 
							inning.getThirdPowerplayEndOver() + ") " + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Runs*GEOM*TEXT SET "  + pp.split(",")[0] + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide 
						+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
				break;
			case "g":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*ACTIVE SET 1 \0", print_writers);
			
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + team.getTeamName4() + "\0", print_writers);
				
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
					if(inning.getInningNumber() == 1 || inning.getInningNumber() == 2) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$txt_Name*GEOM*TEXT SET " + bowlingCard.getPlayer().getTicker_name() + " - 1st INNS" + "\0", print_writers);
					}else if(inning.getInningNumber() == 3 || inning.getInningNumber() == 4) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$txt_Name*GEOM*TEXT SET " + bowlingCard.getPlayer().getTicker_name() + " - 2nd INNS" + "\0", print_writers);
					}
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET " + bowlingCard.getPlayer().getTicker_name() + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Runs*GEOM*TEXT SET " + "" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " + "" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide 
						+ "$txt_Sub*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + " - " + bowlingCard.getMaidens() 
							+ " - " + bowlingCard.getRuns() + " - " + bowlingCard.getWickets() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide 
						+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
				break;
				
			case "y":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*ACTIVE SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + team.getTeamName4() + "\0", print_writers);
				
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
					if(inning.getInningNumber() == 1 || inning.getInningNumber() == 2) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - 1st INNS" + "\0", print_writers);
					}else if(inning.getInningNumber() == 3 || inning.getInningNumber() == 4) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - 2nd INNS" + "\0", print_writers);
					}
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "\0", print_writers);
				}
				
				if(battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Runs*GEOM*TEXT SET "  + battingCard.getRuns() + "*" + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Runs*GEOM*TEXT SET "  + battingCard.getRuns()+ "\0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " + battingCard.getBalls() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide 
						+ "$txt_Sub*GEOM*TEXT SET " + battingCard.getFours() +" FOURS    " + battingCard.getSixes() + " SIXES"+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$SubText$Side" + WhichSide 
						+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
				break;
			case "Shift_F":case "Alt_b":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*ACTIVE SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + 
						"$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + team.getTeamName4() + "\0", print_writers);
				
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
					if(inning.getInningNumber() == 1 || inning.getInningNumber() == 2) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - 1st INNS" + "\0", print_writers);
					}else if(inning.getInningNumber() == 3 || inning.getInningNumber() == 4) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - 2nd INNS" + "\0", print_writers);
					}
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Runs*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " + battingCard.getBalls() + "\0", print_writers);
				switch(whatToProcess) {
					case "Alt_b":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
								+ "$txt_Sub*GEOM*TEXT SET  b " + player.getTicker_name() + "\0", print_writers);
						break;
					default:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
								+ "$txt_Sub*GEOM*TEXT SET " + battingCard.getHowOutText() + "\0", print_writers);
						break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
						+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
				
				break;
			case "Shift_O":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*ACTIVE SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + 
						"$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + team.getTeamName4() + "\0", print_writers);
				
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.TEST)) {
					if(inning.getInningNumber() == 1 || inning.getInningNumber() == 2) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - 1st INNS" + "\0", print_writers);
					}else if(inning.getInningNumber() == 3 || inning.getInningNumber() == 4) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " - 2nd INNS" + "\0", print_writers);
					}
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Runs*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " + battingCard.getBalls() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
						+ "$txt_Sub*GEOM*TEXT SET " + battingCard.getHowOutText() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
						+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
				
				break;
			case "Control_k":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*ACTIVE SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + 
						"$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + team.getTeamName4() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Name*GEOM*TEXT SET " + "CURRENT PARTNERSHIP" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Runs*GEOM*TEXT SET " + partnership.getTotalRuns() + "*" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " + partnership.getTotalBalls() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
						+ "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
						+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
				break;
			case "Shift_F4":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$img_Flag*ACTIVE SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + 
						"$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + team.getTeamName4() + "\0", print_writers);
				
				if(partnership.getPartnershipNumber() == 1) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET "+ partnership.getPartnershipNumber() + "st WICKET PARTNERSHIP" + "\0", print_writers);
					
				}else if(partnership.getPartnershipNumber() == 2) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET " + partnership.getPartnershipNumber() +"nd WICKET PARTNERSHIP" + "\0", print_writers);
					
				}else if(partnership.getPartnershipNumber() == 3) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET "+ partnership.getPartnershipNumber() + "rd WICKET PARTNERSHIP" + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET " + partnership.getPartnershipNumber() +"th WICKET PARTNERSHIP" + "\0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Runs*GEOM*TEXT SET " + partnership.getTotalRuns() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " + partnership.getTotalBalls() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
						+ "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
						+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
				break;
			case "k":
				if(bug.getSponsor() != null) {
					if(config.getSecondaryIpAddress()!= null) {
						CricketFunctions.DoadWriteCommandToSelectedViz(2, "-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
								+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
							+ "$img_Sponsor*ACTIVE SET 1 \0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
							+ "$img_Sponsor*ACTIVE SET 0 \0", print_writers);
				}
				
				if(bug.getFlag() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$img_Flag*ACTIVE SET 1 \0", print_writers);
					if(team.getTeamName4().equalsIgnoreCase("NEP")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$img_Shadow*ACTIVE SET 0 \0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$Data$MainTxt_Grp$Side" + WhichSide 
								+ "$img_Shadow*ACTIVE SET 1 \0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide + 
							"$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + team.getTeamName4() + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$img_Flag*ACTIVE SET 0 \0", print_writers);
				}
				
				if(bug.getText4() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Balls*GEOM*TEXT SET " + "(" + bug.getText4() + ")" + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Balls*GEOM*TEXT SET " + "" + "\0", print_writers);
				}
				
				if(bug.getText3() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Runs*GEOM*TEXT SET " + bug.getText3() + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Runs*GEOM*TEXT SET " + "" + "\0", print_writers);
				}
				
				if(bug.getText2() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
							+ "$txt_Sub*GEOM*TEXT SET " + bug.getText2() + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$SubText$Side" + WhichSide 
							+ "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);
				}
				
				if(bug.getText1() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET " + bug.getText1() + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_All$MainTxt_Grp$Side" + WhichSide 
							+ "$txt_Name*GEOM*TEXT SET " + "" + "\0", print_writers);
				}
				
				
				break;
			}
			break;
			
		case Constants.ISPL:
			switch (whatToProcess.split(",")[0]) {
			case "Control_Shift_R":
				
				String matchResult = CricketFunctions.GenerateMatchSummaryStatus(inning.getInningNumber(), matchAllData, CricketUtil.SHORT, "", 
						config.getBroadcaster(),true).getTargetOrResult().toUpperCase();
				
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
					if(matchResult.contains("tied")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
								+ Constants.ISPL_LOGOS_PATH + "ISPL" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
								+ Constants.ISPL_BASE1 + "ISPL" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
								+ Constants.ISPL_TEXT1 + "ISPL" + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
								+ "ANOTHER SUPER OVER TO FOLLOW" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
								"$txt_Sub*GEOM*TEXT SET \0", print_writers);
					}else {
						if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
							if(CricketFunctions.GetTargetData(matchAllData).getRemaningRuns() <= 0) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
										+ Constants.ISPL_LOGOS_PATH + matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
										+ Constants.ISPL_BASE1 + matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamBadge()+ "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
										+ Constants.ISPL_TEXT1 + matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamBadge() + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
										+ matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName1() + " WIN THE SUPER OVER " + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
										"$txt_Sub*GEOM*TEXT SET \0", print_writers);
							}else if(matchAllData.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
									matchAllData.getMatch().getInning().get(1).getTotalOvers() >= matchAllData.getSetup().getMaxOvers()) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
										+ Constants.ISPL_LOGOS_PATH + matchAllData.getMatch().getInning().get(1).getBowling_team().getTeamBadge() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
										+ Constants.ISPL_BASE1 + matchAllData.getMatch().getInning().get(1).getBowling_team().getTeamBadge() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
										+ Constants.ISPL_TEXT1 + matchAllData.getMatch().getInning().get(1).getBowling_team().getTeamBadge() + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
										+ matchAllData.getMatch().getInning().get(1).getBowling_team().getTeamName1() + " WIN THE SUPER OVER" + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
										"$txt_Sub*GEOM*TEXT SET \0", print_writers);
							}
						}else {
							for(Team tm : cricketService.getTeams()) {
								if(matchResult.contains(tm.getTeamName4())) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
											+ Constants.ISPL_LOGOS_PATH + tm.getTeamBadge() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
											+ Constants.ISPL_BASE1 + tm.getTeamBadge() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
											+ Constants.ISPL_TEXT1 + tm.getTeamBadge() + "\0", print_writers);
								}
							}
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
									+ matchResult + "\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
									"$txt_Sub*GEOM*TEXT SET \0", print_writers);
						}
					}
				}else {
					if(matchResult.contains("tied")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
								+ Constants.ISPL_LOGOS_PATH + "ISPL" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
								+ Constants.ISPL_BASE1 + "ISPL" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
								+ Constants.ISPL_TEXT1 + "ISPL" + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
								+ "SUPER OVER TO FOLLOW" + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
								"$txt_Sub*GEOM*TEXT SET \0", print_writers);
					}else {
						for(Team tm : cricketService.getTeams()) {
							if(matchResult.contains(tm.getTeamName4())) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
										+ Constants.ISPL_LOGOS_PATH + tm.getTeamBadge() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
										+ Constants.ISPL_BASE1 + tm.getTeamBadge() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
										+ Constants.ISPL_TEXT1 + tm.getTeamBadge() + "\0", print_writers);
							}
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
								+ matchResult + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
								"$txt_Sub*GEOM*TEXT SET \0", print_writers);
					}
				}
				break;
			case "Control_Shift_F3": 
				String summary = "",team_name = "";
				
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && matchAllData.getSetup().getMaxOvers() == 1) {
					team_name = inning.getBatting_team().getTeamName4(); 
					summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS" + " TO WIN FROM " 
							+ (matchAllData.getSetup().getMaxOvers()*6) + " BALLS";
				}else {
					if(matchAllData.getSetup().getTargetOvers() != null && !matchAllData.getSetup().getTargetOvers().isEmpty()) {
						team_name = inning.getBatting_team().getTeamName4();
						summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS" + " TO WIN FROM " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers() 
								+ " OVERS" + (matchAllData.getSetup().getTargetType() != null ? " " + matchAllData.getSetup().getTargetType().toUpperCase() : "");
					}else {
						team_name = inning.getBatting_team().getTeamName4();
						summary = "NEED " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + " RUNS" + " TO WIN FROM " 
								+ CricketFunctions.GetTargetData(matchAllData).getTargetOvers() + " OVERS";
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH + inning.getBatting_team().getTeamBadge()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1 + inning.getBatting_team().getTeamBadge()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1 + inning.getBatting_team().getTeamBadge()+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
						+ team_name + " " + summary + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
						"$txt_Sub*GEOM*TEXT SET \0", print_writers);
				
				break;
				
			case "o":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select"
						+ "*FUNCTION*Omo*vis_con SET 0  \0", print_writers);
				CricketFunctions.DoadWriteCommandToSelectedViz(1,"-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select$PlayerOftheMatch$Data$Select_Sponsor"
						+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToSelectedViz(2,"-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select$PlayerOftheMatch$Data$Select_Sponsor"
						+ "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
				break;
			case "t":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select"
						+ "*FUNCTION*Omo*vis_con SET 2  \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToSelectedViz(2,"-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Select$Third_Umpire$1$Data$Side_1$SubText$Side1"
						+ "$group*ACTIVE SET 0 \0", print_writers);
				
				for(VariousText vt : VariousText) {
					if(vt.getVariousType().equalsIgnoreCase("THIRDUMPIRE") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Center_Bug$Third_Umpire"
								+ "$1$Data$Side_1$SubText$txt_Sub*GEOM*TEXT SET " + vt.getVariousText() + "\0", print_writers);
					}else if(vt.getVariousType().equalsIgnoreCase("THIRDUMPIRE") && vt.getUseThis().toUpperCase().equalsIgnoreCase(CricketUtil.NO)) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Footer$Top_Align$Side" + WhichSide + "$Select_FooterType"
//							+ "$Info_Text$Data$txt_Info_1*GEOM*TEXT SET " + CricketFunctions.
//							generateMatchSummaryStatus(WhichInning, matchAllData, CricketUtil.FULL, CricketUtil.BEAT).toUpperCase() + "\0", print_writers);
					}
				}
				break;	
			}
			switch(whatToProcess.split(",")[0]) {
			case "Shift_C":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx-SixDistanceBug$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx-SixDistanceBug$img_Text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx-SixDistanceBug$TextAll$TextAll$txt_Header*GEOM*TEXT SET " 
						+ " " + whatToProcess.split(",")[3].toUpperCase() + " DISTANCE"+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx-SixDistanceBug$TextAll$TextAll$Distance$txt_Distance"
						+ "*ANIMATION*KEY*$S*VALUE SET " + whatToProcess.split(",")[2] + "\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx-SixDistanceBug$TextAll$TextAll$Distance$Meters*GEOM*TEXT SET " 
						+  "METRES" + "\0", print_writers);
				break;
			case "Control_Shift_F11":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Outline_TeamColour$img_Base1"
						+ "*TEXTURE*IMAGE SET "+Constants.ISPL_BASE1+team.getTeamBadge()+"\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Main$TeamName$img_Base1"
						+ "*TEXTURE*IMAGE SET "+Constants.ISPL_BASE1+team.getTeamBadge()+"\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Main$TeamName$img_Text1"
						+ "*TEXTURE*IMAGE SET "+Constants.ISPL_TEXT1+team.getTeamBadge()+"\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Main$TeamName$txt_Text1"
						+ "*GEOM*TEXT SET "+team.getTeamName4()+ " REVIEW"+"\0", print_writers);
				if(WhichSide == 1 && isVisited == false) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Main$BottomTextAll$Side"+WhichSide
							+ "$txt_Text2*GEOM*TEXT SET "+"ORIGINAL DECISION - "+whatToProcess.split(",")[3].toUpperCase()+"\0", print_writers);
					isVisited = true;
				}else {
					switch (whatToProcess.split(",")[3]) {
					case "reversednotout":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Main$BottomTextAll$Side"+WhichSide
								+ "$txt_Text2*GEOM*TEXT SET REVERSED - NOT OUT\0", print_writers);
						break;
					case "upheldnotout":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Main$BottomTextAll$Side"+WhichSide
								+ "$txt_Text2*GEOM*TEXT SET UPHELD - NOT OUT\0", print_writers);
						break;
					case "reversedout":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Main$BottomTextAll$Side"+WhichSide
								+ "$txt_Text2*GEOM*TEXT SET REVERSED - OUT\0", print_writers);
						break;
					case "upheldout":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Main$BottomTextAll$Side"+WhichSide
								+ "$txt_Text2*GEOM*TEXT SET UPHELD - OUT\0", print_writers);
						break;
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$DRS_Bug$Main$BottomTextAll$Side"+WhichSide
						+ "$txt_Text2*TEXTURE*IMAGE SET "+Constants.ISPL_TEXT2+team.getTeamBadge()+"\0", print_writers);
				break;
			case "Alt_p":
				
				if(matchAllData.getSetup().getHomeTeam().getTeamName4().contains("KHILADI XI") || matchAllData.getSetup().getHomeTeam().getTeamName4().contains("MASTER 11")) {
					if(matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						homecolor = "KHILADI_XI";
					}else if(matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase("MASTER 11")) {
						homecolor = "MASTER_XI";
					}
				}else {
					homecolor = matchAllData.getSetup().getHomeTeam().getTeamBadge();
				}
				
				if(matchAllData.getSetup().getAwayTeam().getTeamName4().contains("KHILADI XI") || matchAllData.getSetup().getAwayTeam().getTeamName4().contains("MASTER 11")) {
					if(matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						awaycolor = "KHILADI_XI";
					}else if(matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase("MASTER 11")) {
						awaycolor = "MASTER_XI";
					}
				}else {
					awaycolor = matchAllData.getSetup().getAwayTeam().getTeamBadge();
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Toss_Bug$Data$Team1$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH + homecolor + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Toss_Bug$Data$Team2$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH + awaycolor + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Toss_Bug$Data$txt_Info*GEOM*TEXT SET " + team.getTeamName4() 
					+ " WON THE TENX-U TIP TOP TOSS & CHOSE TO " + whatToProcess.split(",")[2].split("-")[1] + "\0", print_writers);
				
				break;
			case "h":
				if(inning.getBatting_team().getTeamName4().contains("KHILADI XI") || inning.getBatting_team().getTeamName4().contains("MASTER 11")) {
					if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						homecolor = "KHILADI_XI";
					}else if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
						homecolor = "MASTER_XI";
					}
				}else {
					homecolor = inning.getBatting_team().getTeamBadge();
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1+homecolor+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET " 
						+ ""+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
						+ inning.getBatting_team().getTeamName1()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
						+ CricketFunctions.getTeamScoreAddBonusRuns(matchAllData.getEventFile().getEvents(), inning, 0, "-", false) + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET " 
						+  "(" + CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) + ")" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Sub*GEOM*TEXT SET HIGHLIGHTS\0", print_writers);
				
				break;
			case "Control_y":
				String pp = "";
				
				if(inning.getBatting_team().getTeamName4().contains("KHILADI XI") || inning.getBatting_team().getTeamName4().contains("MASTER 11")) {
					if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						homecolor = "KHILADI_XI";
					}else if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
						homecolor = "MASTER_XI";
					}
				}else {
					homecolor = inning.getBatting_team().getTeamBadge();
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1 + homecolor + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide + 
						"$Data$img_text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + homecolor + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side" + WhichSide + 
						"$img_Logo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + homecolor + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide 
						+ "$txt_FirstName*GEOM*TEXT SET \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide 
						+ "$txt_LastName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName4() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " + "" + "\0", print_writers);
				
				if(whatToProcess.split(",")[2].equalsIgnoreCase("p1")) {
					pp = CricketFunctions.getFirstPowerPlayScore(matchAllData,inning.getInningNumber(), matchAllData.getEventFile().getEvents());
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide + "$txt_Sub*GEOM*TEXT SET " 
							+ "POWERPLAY 1" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide + "$txt_Runs*GEOM*TEXT SET " 
							+ pp.split(",")[0] + "\0", print_writers);
					
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("p2")) {
					pp=CricketFunctions.getSecPowerPlayScore(matchAllData, inning.getInningNumber(), matchAllData.getEventFile().getEvents());
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide + "$txt_Sub*GEOM*TEXT SET " 
							+ "POWERPLAY 2 (OVER " + CricketFunctions.getOverNumberFromString(inning.getSecondPowerplayStartOver()) + ") " + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide + "$txt_Runs*GEOM*TEXT SET " 
							+ pp.split(",")[0] + "\0", print_writers);
				}
				break;
			case "g":
				
				if(team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
					if(team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						homecolor = "KHILADI_XI";
					}else if(team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
						homecolor = "MASTER_XI";
					}
				}else {
					homecolor = team.getTeamBadge();
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1+homecolor+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
						+ bowlingCard.getPlayer().getTicker_name()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
						+bowlingCard.getWickets()+"-"+ bowlingCard.getRuns()+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET " 
						+  "(" + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + ")" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Sub*GEOM*TEXT SET \0", print_writers);
				break;
				
			case "y":
				
				if(team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
					if(team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						homecolor = "KHILADI_XI";
					}else if(team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
						homecolor = "MASTER_XI";
					}
				}else {
					homecolor = team.getTeamBadge();
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1+homecolor+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET " 
						+ ""+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
						+ battingCard.getPlayer().getTicker_name()+ "\0", print_writers);
				
				
				if(battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
							+ battingCard.getRuns()+"*"+ "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
							+ battingCard.getRuns()+ "\0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET " 
						+  "(" + battingCard.getBalls() + ")" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Sub*GEOM*TEXT SET "+
				"FOURS : "+battingCard.getFours()+"  | "+" SIXES : "+battingCard.getSixes()+"  | "+" NINE : "+battingCard.getNines()+"\0", print_writers);
				
				break;
			case ".":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Challenge_Bug$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH + inning.getBatting_team().getTeamBadge()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Challenge_Bug$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1 + inning.getBatting_team().getTeamBadge()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Challenge_Bug$Right_Section$TextGrp1$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1 + inning.getBatting_team().getTeamBadge()+ "\0", print_writers);
				
				int playerId = 0, overNumber = 0;
				
				for(Event evnt: matchAllData.getEventFile().getEvents()) {
					if(evnt.getEventInningNumber() == inning.getInningNumber()) {
						if(evnt.getEventExtra() != null) {
							if(evnt.getEventExtra().equalsIgnoreCase("challenge")) {
								playerId = evnt.getEventBowlerNo();
								overNumber = (evnt.getEventOverNo() + 1);
								break;
							}
						}
					}
				}
				
				List<OverByOverData> manhattan = new ArrayList<OverByOverData>();
				manhattan = CricketFunctions.getOverByOverData(matchAllData, inning.getInningNumber(), "MANHATTAN" , matchAllData.getEventFile().getEvents());
				for(int j=1;j<=manhattan.size();j++) {
					if(j == overNumber) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Challenge_Bug$Right_Section$TextGrp1$txt_Runs*GEOM*TEXT SET " 
								+ manhattan.get(j).getOverTotalRuns() + "\0", print_writers);
						break;
					}
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Challenge_Bug$Right_Section$TextGrp1$txt_Head*GEOM*TEXT SET 50-50 OVER:\0", print_writers);
				
				if(inning.getSpecialRuns() != null && !inning.getSpecialRuns().isEmpty()) {
					if(inning.getSpecialRuns().startsWith("-")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Challenge_Bug$ChallengeDataGrp$Select_ChallengeRuns*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Challenge_Bug$ChallengeDataGrp$Negative$txt_Runs*GEOM*TEXT SET " 
								+ (inning.getSpecialRuns().equalsIgnoreCase("-0") ? "0" : inning.getSpecialRuns()) + "\0", print_writers);
					}else if(inning.getSpecialRuns().startsWith("+")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Challenge_Bug$ChallengeDataGrp$Select_ChallengeRuns*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Challenge_Bug$ChallengeDataGrp$Positive$txt_Runs*GEOM*TEXT SET " + inning.getSpecialRuns() + "\0", print_writers);
					}
				}
				
				break;
			case "/":
				
				String tapeData = getBowlerRunsOverbyOver(inning.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData);
				
				homecolor = "ISPL";
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1 + homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide + "$Data$img_text1"
						+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + homecolor + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side" + WhichSide + "$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH + homecolor + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide 
						+ "$txt_FirstName*GEOM*TEXT SET " + tapeData.split(",")[0] + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide 
						+ "$txt_LastName*GEOM*TEXT SET \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide 
						+ "$txt_Sub*GEOM*TEXT SET " + "" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide 
						+ "$txt_Runs*GEOM*TEXT SET "  + " TAPE BALL OVER: " + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Text$Side" + WhichSide 
						+ "$txt_Balls*GEOM*TEXT SET " + tapeData.split(",")[1] + " RUN" + CricketFunctions.Plural(Integer.valueOf(tapeData.split(",")[1])).toUpperCase() 
						+ " & " + tapeData.split(",")[2] + " WICKET" + CricketFunctions.Plural(Integer.valueOf(tapeData.split(",")[2])).toUpperCase() + "\0", print_writers);
				break;	
			case "Shift_F":
				
				if(team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
					if(team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						homecolor = "KHILADI_XI";
					}else if(team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
						homecolor = "MASTER_XI";
					}
				}else {
					homecolor = team.getTeamBadge();
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1+homecolor+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET " 
						+ ""+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
						+ battingCard.getPlayer().getTicker_name()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
						+battingCard.getRuns()+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET " 
						+  "(" + battingCard.getBalls() + ")" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
						"$txt_Sub*GEOM*TEXT SET "+battingCard.getHowOutText()+"\0", print_writers);
				break;
			case "Shift_O":
				
				if(team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
					if(team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						homecolor = "KHILADI_XI";
					}else if(team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
						homecolor = "MASTER_XI";
					}
				}else {
					homecolor = team.getTeamName4();
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1+homecolor+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET " 
						+ ""+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
						+ battingCard.getPlayer().getTicker_name()+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
						+battingCard.getRuns()+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET " 
						+  "(" + battingCard.getBalls() + ")" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
						"$txt_Sub*GEOM*TEXT SET "+battingCard.getHowOutText()+"\0", print_writers);
				break;
			case "Control_k":
				
				if(team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
					if(team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						homecolor = "KHILADI_XI";
					}else if(team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
						homecolor = "MASTER_XI";
					}
				}else {
					homecolor = team.getTeamBadge();
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1+homecolor+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET " 
						+ ""+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
						+"CURRENT PARTNERSHIP"+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
						+partnership.getTotalRuns()+"*"+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET " 
						+  "(" + partnership.getTotalBalls() + ")" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
						"$txt_Sub*GEOM*TEXT SET \0", print_writers);
				break;
			case "Shift_F4":
				
				if(team.getTeamName4().contains("KHILADI XI") || team.getTeamName4().contains("MASTER 11")) {
					if(team.getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						homecolor = "KHILADI_XI";
					}else if(team.getTeamName4().equalsIgnoreCase("MASTER 11")) {
						homecolor = "MASTER_XI";
					}
				}else {
					homecolor = team.getTeamBadge();
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1+homecolor+ "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET " 
						+ ""+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
						+CricketFunctions.ordinal(partnership.getPartnershipNumber())+ " WICKET PARTNERSHIP"+ "\0", print_writers);
				
				if(partnership.getPartnershipNumber() == inning.getPartnerships().size()) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
							+partnership.getTotalRuns()+"*"+ "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
							+partnership.getTotalRuns()+ "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET " 
						+  "(" + partnership.getTotalBalls() + ")" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+
						"$txt_Sub*GEOM*TEXT SET \0", print_writers);
				break;
			case "k":
				
				if(bug.getSponsor() != null) {
					homecolor = bug.getSponsor();
				}else {
					homecolor = "ISPL";
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Logo$Side"+WhichSide+"$img_Logo*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_LOGOS_PATH+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$img_Base1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_BASE1+homecolor+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$img_text1*TEXTURE*IMAGE SET " 
						+ Constants.ISPL_TEXT1+homecolor+ "\0", print_writers);
				
				if(bug.getText4() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Sub*GEOM*TEXT SET "+bug.getText4()+"\0", print_writers);
					
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Sub*GEOM*TEXT SET \0", print_writers);
				}
				
				if(bug.getText3() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET " 
							+  bug.getText3() + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Balls*GEOM*TEXT SET \0", print_writers);
				}
				
				if(bug.getText2() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET " 
							+bug.getText2()+ "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_Runs*GEOM*TEXT SET \0", print_writers);
				}
				
				if(bug.getText1() != null) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET " 
							+ bug.getText1()+ "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_LastName*GEOM*TEXT SET \0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Bugs$Right_Section$Side"+WhichSide+"$txt_FirstName*GEOM*TEXT SET " 
						+ ""+ "\0", print_writers);
				
				break;
			}
			break;	
		case Constants.BENGAL_T20:
			switch(whatToProcess.split(",")[0]) {
				case "Control_y":
				
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1" 
							+ "*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + " - " + 
							CricketFunctions.getPowerPlayScore(inning, inning.getInningNumber(), "-", matchAllData) + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
							+ "*GEOM*TEXT SET POWERPLAY\0", print_writers);
					break;
				
				case "y":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					if(battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1" 
								+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " " + battingCard.getRuns() + 
								"* (" + battingCard.getBalls() + ")" + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1" 
								+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + " " + battingCard.getRuns() + 
								" (" + battingCard.getBalls() + ")" + "\0", print_writers);
					}
					
					if(battingCard.getSixes() != 0 && battingCard.getFours() != 0) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
								+ "*GEOM*TEXT SET " + "FOURS:  " + battingCard.getFours() + "   SIXES:  "  + battingCard.getSixes() + "\0", print_writers);
					}else if(battingCard.getFours() != 0) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
								+ "*GEOM*TEXT SET " + "FOURS:  " + battingCard.getFours() + "\0", print_writers);
					}else if(battingCard.getSixes() != 0) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
								+ "*GEOM*TEXT SET " + "SIXES:  "  + battingCard.getSixes() + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
								+ "*GEOM*TEXT SET " + "STRIKE RATE: " + CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 1) + "\0", print_writers);
					}
					
					break;
				case "g":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
							+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + " - " + bowlingCard.getDots() 
							+ " - " + bowlingCard.getRuns() + " - " + bowlingCard.getWickets() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1" 
							+ "*GEOM*TEXT SET " + bowlingCard.getPlayer().getTicker_name() + "\0", print_writers);
					
					break;
				case "Shift_F":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
							+ "*GEOM*TEXT SET " + battingCard.getHowOutText() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1" 
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "     " + battingCard.getRuns() + "(" + battingCard.getBalls() + ")" + "\0", print_writers);
					break;
				case "Shift_O":
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1" 
							+ "*GEOM*TEXT SET " + battingCard.getPlayer().getTicker_name() + "     " + battingCard.getRuns() + "(" + battingCard.getBalls() + ")" + "\0", print_writers);
					
					if(battingCard.getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
						if(battingCard.getWasHowOutFielderSubstitute() != null && battingCard.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
									+ "*GEOM*TEXT SET " + "run out " + "(sub - " + battingCard.getHowOutFielder().getTicker_name() + ")" + "\0", print_writers);
							
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
									+ "*GEOM*TEXT SET " + "run out " + "(" + battingCard.getHowOutFielder().getTicker_name() + ")" + "\0", print_writers);
						}
					}else if(battingCard.getHowOut().equalsIgnoreCase(CricketUtil.STUMPED)) {
						if(battingCard.getWasHowOutFielderSubstitute() != null && battingCard.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
									+ "*GEOM*TEXT SET " + "st" +  " (sub - " + battingCard.getHowOutFielder().getTicker_name() + ")  b " + 
									battingCard.getHowOutBowler().getTicker_name() + "\0", print_writers);
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
									+ "*GEOM*TEXT SET " + "st " + battingCard.getHowOutFielder().getTicker_name() + "  b " + 
									battingCard.getHowOutBowler().getTicker_name() + "\0", print_writers);
						}
					}else if(battingCard.getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
						if(battingCard.getWasHowOutFielderSubstitute() != null && battingCard.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
									+ "*GEOM*TEXT SET " + "c" +  " (sub - " + battingCard.getHowOutFielder().getTicker_name() + ")  b " + 
									battingCard.getHowOutBowler().getTicker_name() + "\0", print_writers);
						} else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
									+ "*GEOM*TEXT SET " + "c " + battingCard.getHowOutFielder().getTicker_name() + "  b " + 
									battingCard.getHowOutBowler().getTicker_name() + "\0", print_writers);
						}
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
								+ "*GEOM*TEXT SET " + battingCard.getHowOutText() + "\0", print_writers);
					}
					
					break;
				case "Control_k":
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1" 
							+ "*GEOM*TEXT SET CURRENT P'SHIP : \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
							+ "*GEOM*TEXT SET " + partnership.getFirstPlayer().getTicker_name() + " & " + partnership.getSecondPlayer().getTicker_name() + " - " +
							(partnership.getPartnershipNumber()==0? "":partnership.getTotalRuns() + "* (" + partnership.getTotalBalls() + ")") + " \0", print_writers);
					
					break;
//				case "Alt_p": 
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line"
//							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
//					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line$txt_Info" 
//							+ "*GEOM*TEXT SET " + whatToProcess.split(",")[2].split("-")[0] + " WON THE TOSS "
//							+"& CHOSE TO "+whatToProcess.split(",")[2].split("-")[1] + "\0", print_writers);
//					
//					break;
				case "h":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					
					if(whatToProcess.split(",")[2].equalsIgnoreCase("WITHOUT_SPONSOR")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
								+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
								+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
								+ "$select_Sponsor$img_Sponsor*TEXTURE*IMAGE SET " + Constants.BENGAL_SPONSOR_PATH + "CYCLE" + "\0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1" 
							+ "*GEOM*TEXT SET HIGHLIGHTS\0", print_writers);
					
					if (inning.getTotalWickets() >= 10) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
								+ "*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "   " + 
								inning.getTotalRuns() + "(" + 
								CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) + ")"+ "\0", print_writers);
					} else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
								+ "*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + "   " + 
								inning.getTotalRuns()+ "-"+ inning.getTotalWickets() + "(" + 
								CricketFunctions.OverBalls(inning.getTotalOvers(), inning.getTotalBalls()) + ")"+ "\0", print_writers);
						
					}
					break;
				case "Shift_C":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1" 
							+ "*GEOM*TEXT SET SIX DISTANCE\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2" 
							+ "*GEOM*TEXT SET " + whatToProcess.split(",")[2]+" METRES" + "\0", print_writers);
					
					break;
				case "Shift_Y":
					if(bug.getSponsor() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line"
								+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line"
								+ "$img_Sponsor*TEXTURE*IMAGE SET "+Constants.BENGAL_SPONSOR_PATH+bug.getSponsor()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line"
								+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line"
								+ "$img_Sponsor*TEXTURE*IMAGE SET "+Constants.BENGAL_SPONSOR_PATH+"TLogo"+"\0", print_writers);
					}
					
					if(bug.getText1() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line$txt_Info*GEOM*TEXT SET "+bug.getText1()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line$txt_Info*GEOM*TEXT SET \0", print_writers);
					}
					break;
				case "k":
					
					if(bug.getSponsor() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
								+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
								+ "$img_Sponsor*TEXTURE*IMAGE SET "+Constants.BENGAL_SPONSOR_PATH+bug.getSponsor()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
								+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line"
								+ "$img_Sponsor*TEXTURE*IMAGE SET "+Constants.BENGAL_SPONSOR_PATH+"TLogo"+"\0", print_writers);
					}
					
					if(bug.getText2() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2*GEOM*TEXT SET "+bug.getText2()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info2*GEOM*TEXT SET \0", print_writers);
					}
					
					if(bug.getText1() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1*GEOM*TEXT SET "+bug.getText1()+"\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_2Line$txt_Info1*GEOM*TEXT SET \0", print_writers);
					}
					break;
				case "Control_Shift_R":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line"
							+ "$select_Sponsor*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					
					String matchResult = null;
					matchResult = CricketFunctions.GenerateMatchSummaryStatus(inning.getInningNumber(), matchAllData, CricketUtil.FULL, "", 
							config.getBroadcaster(), true).getTargetOrResult().toUpperCase();
					
					if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
						if(matchResult.contains("tied")) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
//									+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.NPL_LOGO_PATH+"TLogo"+"\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line$txt_Info"
									+ "*GEOM*TEXT SET SUPER OVER TIED, WINNER WILL BE DECIDED BY ANOTHER SUPER OVER\0", print_writers);
						}else {
							if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
								if(CricketFunctions.GetTargetData(matchAllData).getRemaningRuns() <= 0) {
//									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
//											+ Constants.NPL_LOGO_PATH+matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamBadge()+"\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line$txt_Info*GEOM*TEXT SET "
											+ (config.getBroadcaster().equalsIgnoreCase(Constants.LEGENDS) ? matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName3() 
											: matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName1()) + " WIN THE SUPER OVER\0", print_writers);
								}else if(matchAllData.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
										matchAllData.getMatch().getInning().get(1).getTotalOvers() >= matchAllData.getSetup().getMaxOvers()) {
									
//									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges*TEXTURE*IMAGE SET "
//											+ Constants.NPL_LOGO_PATH+matchAllData.getMatch().getInning().get(1).getBowling_team().getTeamBadge()+"\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line$txt_Info*GEOM*TEXT SET " + 
											(config.getBroadcaster().equalsIgnoreCase(Constants.LEGENDS) ? matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName3() 
													: matchAllData.getMatch().getInning().get(1).getBatting_team().getTeamName1()) + " WIN THE SUPER OVER\0", print_writers);
								}
							}else {
								for(Team tm : cricketService.getTeams()) {
									if(matchResult.contains(tm.getTeamName1())) {
//										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges"
//												+ "*TEXTURE*IMAGE SET " + Constants.NPL_LOGO_PATH+tm.getTeamBadge()+"\0", print_writers);
									}
								}
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line$txt_Info*GEOM*TEXT SET " + 
										matchResult.split("WIN")[0] + " WIN " + matchResult.split("WIN")[1] + "\0", print_writers);
							}
						}
					}else {
						if(matchResult.contains("tied")) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide 
//									+ "$Single$img_Badges*TEXTURE*IMAGE SET "+Constants.NPL_LOGO_PATH+"TLogo"+"\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line$txt_Info"
									+ "*GEOM*TEXT SET MATCH TIED, WINNER WILL BE DECIDED BY SUPER OVER\0", print_writers);
						}else {
							for(Team tm : cricketService.getTeams()) {
								if(matchResult.contains(tm.getTeamName1())) {
//									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Bugs_new$Side" + WhichSide + "$Single$img_Badges"
//											+ "*TEXTURE*IMAGE SET " + Constants.NPL_LOGO_PATH+tm.getTeamBadge()+"\0", print_writers);
								}
							}
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_Bug_1Line$txt_Info*GEOM*TEXT SET " 
									+ matchResult.split("WIN")[0] + " WIN " + matchResult.split("WIN")[1] + "\0", print_writers);
						}
					}
					break;	
//				case "6":
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Units$Side1$fig_UnitOutline*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Units$Side1$fig_Unit*GEOM*TEXT SET " +this_data_str.get(0).split(",")[2]+ "\0", print_writers);
//					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Tenths$Side1$fig_TenthsOutline*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Tenths$Side1$fig_Tenths*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
//					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Hundreths$Side1$fig_HundrethsOutline*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Hundreths$Side1$fig_Hundreths*GEOM*TEXT SET " +this_data_str.get(0).split(",")[0]+ "\0", print_writers);
//					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Units$Side2$fig_UnitOutline*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Units$Side2$fig_Unit*GEOM*TEXT SET " +this_data_str.get(1).split(",")[2]+ "\0", print_writers);
//					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Tenths$Side2$fig_TenthsOutline*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Tenths$Side2$fig_Tenths*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
//					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Hundreths$Side2$fig_HundrethsOutline*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_LowerThirds$gfx_Counter$DataGrp"
//							+ "$Hundreths$Side2$fig_Hundreths*GEOM*TEXT SET " +this_data_str.get(1).split(",")[0]+ "\0", print_writers);
//				break;
				case "6":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Header"
							+ "$txt_Text*GEOM*TEXT SET TOURNAMENT SIXES\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Unit"
							+ "$Side1$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Unit"
							+ "$Side1$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Tenths"
							+ "$Side1$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Tenths"
							+ "$Side1$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Hundredths"
							+ "$Side1$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Hundredths"
							+ "$Side1$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
					
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Unit"
							+ "$Side2$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Unit"
							+ "$Side2$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Tenths"
							+ "$Side2$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Tenths"
							+ "$Side2$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Hundredths"
							+ "$Side2$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Hundredths"
							+ "$Side2$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
					
				break;
				case "Control_4":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Header"
							+ "$txt_Text*GEOM*TEXT SET TOURNAMENT FOURS\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Unit"
							+ "$Side1$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Unit"
							+ "$Side1$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[2] + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Tenths"
							+ "$Side1$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Tenths"
							+ "$Side1$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[1] + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Hundredths"
							+ "$Side1$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Hundredths"
							+ "$Side1$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(0).split(",")[0] + "\0", print_writers);
					
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Unit"
							+ "$Side2$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Unit"
							+ "$Side2$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[2] + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Tenths"
							+ "$Side2$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Tenths"
							+ "$Side2$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[1] + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Hundredths"
							+ "$Side2$Reflection3$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Counter$SelectText$Counter$Hundredths"
							+ "$Side2$Reflection1A$txt_Text*GEOM*TEXT SET "+this_data_str.get(1).split(",")[0] + "\0", print_writers);
					
				break;
				case "Alt_p":
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$All_TossBug$In_Out$Data"
						+ "$txt_Info*GEOM*TEXT SET " + whatToProcess.split(",")[2].split("-")[0] + " WON THE TOSS & ELECTED TO " + 
						whatToProcess.split(",")[2].split("-")[1]+ "\0", print_writers);
					
				 break;
				case "Control_Shift_U": 
					if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$img_Image"
								+ "*TEXTURE*IMAGE SET " + Constants.BENGAL_LOCAL_PHOTO_PATH + inning.getBatting_team().getTeamName4() 
								+ "\\\\" +battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION+ "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$img_Image"
								+ "*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.BENGAL_PHOTO_PATH + 
								inning.getBatting_team().getTeamName4() + "\\\\" + battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION+ "\0", print_writers);
					}
					if(battingCard.getPlayer().getSurname() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$PlayerName$txt_FirstName"
								+ "*GEOM*TEXT SET " + battingCard.getPlayer().getFirstname()+ "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$PlayerName$txt_LastName"
								+ "*GEOM*TEXT SET " + battingCard.getPlayer().getSurname()+ "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$PlayerName$txt_FirstName"
								+ "*GEOM*TEXT SET " + ""+ "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$PlayerName$txt_LastName"
								+ "*GEOM*TEXT SET " + battingCard.getPlayer().getFirstname()+ "\0", print_writers);
					}
					switch (whatToProcess.split(",")[3].toUpperCase()) {
					case "SCORE":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Select_TextType"
								+ "*FUNCTION*Omo*vis_con SET " + "0" + "\0", print_writers);
						if(battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text1$txt_Figure_Outline"
									+ "*GEOM*TEXT SET " + battingCard.getRuns()+"*" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text1$txt_Figure"
									+ "*GEOM*TEXT SET " + battingCard.getRuns() +"*" + "\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text1$txt_Figure_Outline"
									+ "*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text1$txt_Figure"
									+ "*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
						}
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text2$txt_Figure_Outline"
								+ "*GEOM*TEXT SET " + battingCard.getBalls() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text2$noname"
								+ "*GEOM*TEXT SET " + battingCard.getBalls() + "\0", print_writers);
						break;

					case "STRIKERATE":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Select_TextType"
								+ "*FUNCTION*Omo*vis_con SET " + "1" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Stat_Title$Text1$txt_Figure_Outline"
								+ "*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 1) + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Stat_Title$Text1$txt_Figure"
								+ "*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 1) + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Stat_Title$txt_Title"
								+ "*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
						break;
					case "BOUNDARY":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Select_TextType"
								+ "*FUNCTION*Omo*vis_con SET " + "1" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Stat_Title$Text1$txt_Figure_Outline"
								+ "*GEOM*TEXT SET " + battingCard.getFours() + "/" + battingCard.getSixes() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Stat_Title$Text1$txt_Figure"
								+ "*GEOM*TEXT SET " + battingCard.getFours() + "/" + battingCard.getSixes() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Stat_Title$txt_Title"
								+ "*GEOM*TEXT SET " + "FOURS/SIXES" + "\0", print_writers);
						break;	
					}
					break;
				case "Control_Shift_V":
					if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$img_Image"
								+ "*TEXTURE*IMAGE SET " + Constants.BENGAL_LOCAL_PHOTO_PATH + inning.getBowling_team().getTeamName4() 
								+ "\\\\" +bowlingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION+ "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$img_Image"
								+ "*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.BENGAL_PHOTO_PATH + 
								inning.getBowling_team().getTeamName4() + "\\\\" + bowlingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION+ "\0", print_writers);
					}
					if(bowlingCard.getPlayer().getSurname() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$PlayerName$txt_FirstName"
								+ "*GEOM*TEXT SET " + bowlingCard.getPlayer().getFirstname()+ "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$PlayerName$txt_LastName"
								+ "*GEOM*TEXT SET " + bowlingCard.getPlayer().getSurname()+ "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$PlayerName$txt_FirstName"
								+ "*GEOM*TEXT SET " + ""+ "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$PlayerName$txt_LastName"
								+ "*GEOM*TEXT SET " + bowlingCard.getPlayer().getFirstname()+ "\0", print_writers);
					}
					switch (whatToProcess.split(",")[3].toUpperCase()) {
					case "FIGURE":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Select_TextType"
								+ "*FUNCTION*Omo*vis_con SET " + "0" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text1$txt_Figure_Outline"
								+ "*GEOM*TEXT SET " + bowlingCard.getWickets() +"-"+ bowlingCard.getRuns() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text1$txt_Figure"
								+ "*GEOM*TEXT SET " + bowlingCard.getWickets() +"-"+ bowlingCard.getRuns() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text2$txt_Figure_Outline"
								+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Big_Score$Text2$noname"
								+ "*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + "\0", print_writers);
						break;

					case "ECONOMY":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Select_TextType"
								+ "*FUNCTION*Omo*vis_con SET " + "1" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Stat_Title$Text1$txt_Figure_Outline"
								+ "*GEOM*TEXT SET " +bowlingCard.getEconomyRate()+ "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Stat_Title$Text1$txt_Figure"
								+ "*GEOM*TEXT SET " + bowlingCard.getEconomyRate() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$gfx_Popup$Side"+WhichSide+"$Stat_Title$txt_Title"
								+ "*GEOM*TEXT SET " + "ECONOMY" + "\0", print_writers);
						break;
					}
					break;
			}
			break;
		}
		
		return Constants.OK;
	}

	public String populateMiniScorecard(int WhichSide, String whatToProcess, MatchAllData matchAllData) throws StreamReadException, DatabindException, NumberFormatException, FileNotFoundException, IOException {
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			status = "populateMiniScorecard match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1]))
					.findAny().orElse(null);
			if(inning == null) {
				return "populateMiniScorecard Inning is null";
			}
		}
		if(populateMiniBody(WhichSide, whatToProcess.split(",")[0],matchAllData, Integer.valueOf(whatToProcess.split(",")[1])) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String populateMiniBowlingcard(int WhichSide, String whatToProcess, MatchAllData matchAllData) throws StreamReadException, DatabindException, NumberFormatException, FileNotFoundException, IOException {
		if (matchAllData == null || matchAllData.getMatch() == null || matchAllData.getMatch().getInning() == null) {
			status = "populateMiniBowlingcard match is null Or Inning is null";
		} else {
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1]))
					.findAny().orElse(null);
			if(inning == null) {
				return "populateMiniBowlingcard Inning is null";
			}
		}
		if(populateMiniBody(WhichSide, whatToProcess.split(",")[0],matchAllData, Integer.valueOf(whatToProcess.split(",")[1])) == Constants.OK) {
			status = Constants.OK;
		}
		return status;
	}
	public String populateT20_Vidarbha_MiniBody(int WhichSide, String whatToProcess, MatchAllData matchAllData, int WhichInning) {

	    switch (whatToProcess) {
	        case "Alt_F7":
	            rowId = 1;
	            for (int i = 0; i <= leagueTable.getLeagueTeams().size() - 1; i++) {
	                rowId = rowId + 1;

	                if (matchAllData.getSetup().getHomeTeam().getTeamBadge().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())
	                        || matchAllData.getSetup().getAwayTeam().getTeamBadge().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {

	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                            + "$Row" + rowId + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
	                    containerName = "$Highlight";

	                } else {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                            + "$Row" + rowId + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
	                    containerName = "$Dehighlight";
	                }
	                
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp"
	                		+ "$Header$LooBase$img2*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE1
		                    + "TLogo" + " \0", print_writers);
	                
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp"
	                		+ "$Header$TeamNameGrp$img_Flag*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH
		                    + "TLogo" + " \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp"
	                		+ "$Header$headerBnd$img1*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE2
		                    + "TLogo" + " \0", print_writers);

	                if (leagueTable.getLeagueTeams().get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                            + "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Rank*GEOM*TEXT SET " + (rowId - 1) + " \0", print_writers);
	                } else if (leagueTable.getLeagueTeams().get(i).getQualifiedStatus().trim().equalsIgnoreCase("Q")) {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                            + "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Rank*GEOM*TEXT SET Q \0", print_writers);
	                }
	                
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                        + "$Row" + rowId + "$Select_Row_Type" + containerName + "$Base$img2*TEXTURE*IMAGE SET "
	                         + Constants.VIDARBHA_BASE2 + "TLogo" +  " \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                        + "$Row" + rowId + "$Select_Row_Type" + containerName + "$Base$saperator$img_txt2*TEXTURE*IMAGE SET "
	                         + Constants.VIDARBHA_TEXT2 + "TLogo" +  " \0", print_writers);
	                
	                
	                
	                for(Team tm:cricketService.getTeams()) {
	                	if(tm.getTeamBadge().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {
	                		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	    	                        + "$Row" + rowId + "$Select_Row_Type" + containerName + "$txt_Name*GEOM*TEXT SET "
	    	                        + tm.getTeamName3() + " \0", print_writers);
	                	}
	                	
	                }
	                

	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                        + "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Play*GEOM*TEXT SET "
	                        + leagueTable.getLeagueTeams().get(i).getPlayed() + " \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                        + "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Wins*GEOM*TEXT SET "
	                        + leagueTable.getLeagueTeams().get(i).getWon() + " \0", print_writers);

	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                        + "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Points*GEOM*TEXT SET "
	                        + leagueTable.getLeagueTeams().get(i).getPoints() + " \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
	                        + "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_NRR*GEOM*TEXT SET "
	                        + String.format("%.2f", leagueTable.getLeagueTeams().get(i).getNetRunRate()) + " \0", print_writers);
	            }
	            break;

	        case "Shift_F1":
	            int battingSize = 0;
	            cont_name = "";
	            omo_num = 0;
	            rowId = 0;
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Batting$LooBase$img2*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE2
	                    + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Batting$headerBnd$img1*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE1
	                    + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Batting$txt_FirstName*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE2
	                    + inning.getBatting_team().getTeamBadge() + " \0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Batting$txt_FirstName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + " \0", print_writers);

	            for (int i = 1; i <= inning.getBattingCard().size(); i++) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Batting$Row" + i + "*ACTIVE SET 1 \0", print_writers);
	            }

	            Collections.sort(inning.getBattingCard());

	            for (BattingCard bc : inning.getBattingCard()) {

	                rowId = rowId + 1;

	                switch (bc.getStatus().toUpperCase()) {
	                    case CricketUtil.OUT:
	                        omo_num = 0;
	                        cont_name = "$Players_Dehighlight";
	                        text_name = "$Data";
	                        battingSize = battingSize + 1;
	                        break;
	                    case CricketUtil.NOT_OUT:
	                        omo_num = 1;
	                        cont_name = "$Players_Highlight";
	                        text_name = "$img_text2";
	                        battingSize = battingSize + 1;
	                        break;
	                }
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
                            + "$Batting$Row" + rowId + cont_name + "$Data$Selec_tImpact*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
            		if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId()).isEmpty()) {
            			switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId())) {
            			case "IMP_IN":
            				 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
     	                            + "$Batting$Row" + rowId + cont_name + "$Data$Selec_tImpact*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
            				break;
            			}
            		}
	                

	                if (cont_name.equalsIgnoreCase("$Players_Highlight")) {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                            + "$Batting$Row" + rowId + cont_name + "$Base$img2*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE2
	                            + inning.getBatting_team().getTeamBadge() + " \0", print_writers);

	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                            + "$Batting$Row" + rowId + cont_name + text_name + "*TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT2
	                            + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
	                } else {
//	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
//	                            + "$Batting$Row" + rowId + cont_name + text_name + "*TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1
//	                            + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
	                }

	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Batting$DataGrp*FUNCTION*Grid*num_row SET " + battingSize + " \0", print_writers);

	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Batting$Row" + rowId + "$Select_Row_Type*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Batting$Row" + rowId + cont_name + "$txt_Name*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + " \0", print_writers);

	                if (bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                            + "$Batting$Row" + rowId + cont_name + "$txt_Out*GEOM*TEXT SET " + "" + " \0", print_writers);
	                } else if (bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                            + "$Batting$Row" + rowId + cont_name + "$txt_Out*GEOM*TEXT SET " + "" + " \0", print_writers);
	                }
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Batting$Row" + rowId + cont_name + "$obj_Divider*ACTIVE SET 0  \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Batting$Row" + rowId + cont_name + "$fig_Runs*GEOM*TEXT SET " + bc.getRuns() + " \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Batting$Row" + rowId + cont_name + "$fig_Out*GEOM*TEXT SET " + bc.getBalls() + " \0", print_writers);
	            }

	            break;

	        case "Shift_F2":
	            int bowling_size = 1;
	            rowId = 1;
	            cont_name = "";
	            omo_num = 0;
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Bowling$LooBase$img2*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE2
	                    + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Bowling$headerBnd$img1*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE1
	                    + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Bowling$txt_FirstName*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE2
	                    + inning.getBowling_team().getTeamBadge() + " \0", print_writers);

	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Bowling$img_Flag*TEXTURE*IMAGE SET " + Constants.VIDARBHA_LOGO_PATH + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
	            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                    + "$Bowling$txt_FirstName*GEOM*TEXT SET " + inning.getBowling_team().getTeamName1() + " \0", print_writers);

	            for (int i = 1; i <= inning.getBowlingCard().size(); i++) {
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Bowling$Row" + (i + 1) + "*ACTIVE SET 1 \0", print_writers);
	            }

	            for (BowlingCard boc : inning.getBowlingCard()) {
	                if (boc.getRuns() > 0 || ((boc.getOvers() * 6) + boc.getBalls()) > 0) {
	                    bowling_size = bowling_size + 1;
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                            + "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + bowling_size + " \0", print_writers);
	                }
	                switch (boc.getStatus().toUpperCase()) {
	                    case (CricketUtil.OTHER + CricketUtil.BOWLER):
	                        omo_num = 2;
	                        cont_name = "$Players_Dehighlight";
	                        text_name = "$Data";
	                        break;
	                    case (CricketUtil.LAST + CricketUtil.BOWLER):
	                        omo_num = 2;
	                        cont_name = "$Players_Dehighlight";
	                        text_name = "$Data";
	                        break;
	                    case (CricketUtil.CURRENT + CricketUtil.BOWLER):
	                        omo_num = 3;
	                        cont_name = "$Players_Highlight";
	                        text_name = "$img_text2";
	                        break;
	                }

	                rowId = rowId + 1;
	                
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
                            + "$Bowling$AllDataGrp$DataGrp$Row" + rowId + cont_name + "$Data$Selec_tImpact*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
            		if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId()).isEmpty()) {
            			switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId())) {
            			case "IMP_IN":
            				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
    	                            + "$Bowling$AllDataGrp$DataGrp$Row" + rowId + cont_name + "$Data$Selec_tImpact*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
            				break;
            			}
            		}
	                

	                if (cont_name.equalsIgnoreCase("$Players_Highlight")) {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                            + "$Bowling$Row" + rowId + cont_name + "$Base$img2*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE2
	                            + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                            + "$Bowling$Row" + rowId + cont_name + text_name + "*TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT2
	                            + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
	                } else {
//	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
//	                            + "$Bowling$Row" + rowId + cont_name + text_name + "*TEXTURE*IMAGE SET " + Constants.VIDARBHA_TEXT1
//	                            + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
	                }

	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Bowling$Row" + rowId + "$Select_Row_Type*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Bowling$Row" + rowId + cont_name + "$txt_Name*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + " \0", print_writers);
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Bowling$Row" + rowId + cont_name + "$fig_Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " \0", print_writers);
	                
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Bowling$Row1$Data$txt_Maidens*GEOM*TEXT SET " + "D" + " \0", print_writers);
	                
	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Bowling$Row" + rowId + cont_name + "$fig_Maidens*GEOM*TEXT SET " + boc.getDots() + " \0", print_writers);

	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Bowling$Row" + rowId + cont_name + "$fig_Runs*GEOM*TEXT SET " + boc.getRuns() + " \0", print_writers);

	                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                        + "$Bowling$Row" + rowId + cont_name + "$fig_Wickets*GEOM*TEXT SET " + boc.getWickets() + " \0", print_writers);

	                if (boc.getEconomyRate() != null) {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                            + "$Bowling$Row" + rowId + cont_name + "$fig_Economy*GEOM*TEXT SET " + boc.getEconomyRate() + " \0", print_writers);
	                } else {
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide
	                            + "$Bowling$Row" + rowId + cont_name + "$fig_Economy*GEOM*TEXT SET " + "-" + " \0", print_writers);
	                }
	            }
	            break;
	    }

	    return Constants.OK;
	}
	
	public String populateT20_MUMBAI_MiniBody(int WhichSide, String whatToProcess, MatchAllData matchAllData, int WhichInning) {
		switch(whatToProcess) {
		case "Alt_F7":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$SelectRows*FUNCTION*Omo*vis_con SET 10\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$SelectRows*SCRIPT*INSTANCE*controlwidth INVOKE\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Position$Header$Logo$TeamLogo$Out$Logo*TEXTURE*IMAGE SET " 
					+ Constants.T20_MUMBAI_TEAMLOGO + "TLogo" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Position$Header$Main$Side1$Heading$TeamTop*GEOM*TEXT SET "
					+ "STANDINGS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Position$Header$Main$Side1$Heading$TeamBtm*GEOM*TEXT SET " 
					+ matchAllData.getSetup().getTournament() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row1$Title$Data$P$Title*GEOM*TEXT SET P\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row1$Title$Data$M$Title*GEOM*TEXT SET W\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row1$Title$Data$W$Title*GEOM*TEXT SET L\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row1$Title$Data$PTS$Title*GEOM*TEXT SET PTS\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row1$Title$Data$Pco$Title*GEOM*TEXT SET NRR\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row1$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row6$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			rowId = 1;
			for(int i=0; i<=leagueTable.getLeagueTeams().size()-1;i++) {
				if(i==4) {
					rowId = 7;
				}else {
					rowId++;
				}
				
				if(matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())  
						|| matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {
					omo = 2;
					containerName = "$Data";
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName + "$TeamBase"
							+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_TEAMCOLOUR_GRADIENTS + leagueTable.getLeagueTeams().get(i).getTeamName() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName + "$Main*TEXTURE*IMAGE SET " 
							+ Constants.T20_MUMBAI_TEAMCOLOUR_FLAT + CricketFunctions.whichTextColor(leagueTable.getLeagueTeams().get(i).getTeamName()) + "\0", print_writers);
				}else {
					omo = 3;
					containerName = "$HL";
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName + "$TeamBase"
							+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_TEAMCOLOUR_FLAT + "LightBlue" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName + "$Main*TEXTURE*IMAGE SET " 
							+ Constants.T20_MUMBAI_TEAMCOLOUR_FLAT + "DarkGrey" + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + 
						"$Select*FUNCTION*Omo*vis_con SET " + omo + "\0", print_writers);
				
				for(Team tm : Teams) {
					if(tm.getTeamName4().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName 
								+ "$Name$Title*GEOM*TEXT SET " + tm.getTeamName1() + "\0", print_writers);
					}
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName 
						+ "$Data$P$Title*GEOM*TEXT SET " + leagueTable.getLeagueTeams().get(i).getPlayed() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName 
						+ "$Data$M$Title*GEOM*TEXT SET " + leagueTable.getLeagueTeams().get(i).getWon() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName 
						+ "$Data$W$Title*GEOM*TEXT SET " + leagueTable.getLeagueTeams().get(i).getLost() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName 
						+ "$Data$PTS$Title*GEOM*TEXT SET " + leagueTable.getLeagueTeams().get(i).getPoints() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$Body$Side1$Row" + rowId + containerName 
						+ "$Data$Eco$Title*GEOM*TEXT SET " + leagueTable.getLeagueTeams().get(i).getNetRunRate() + "\0", print_writers);
			}
			break;
		case "Shift_F1":
			int battingSize=0;
			cont_name = "";
			omo_num = 0;
			rowId = 0;
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Logo$img_Logos_Shadow*TEXTURE*IMAGE SET " 
					+ Constants.T20_MUMBAI_Logos + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Logo$img_Logos*TEXTURE*IMAGE SET " 
					+ Constants.T20_MUMBAI_Logos + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$Essentials$img_Base1"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Elements$img_Base1"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$HeaderGrp$txt_Text"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Text + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$ColouredBaseInWipe$TeamColourBase1$img_Base1"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$ColouredBaseInWipe$TeamColourBase2$img_Base2"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base2 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$ColouredBaseOutWipe$TeamColourBase1$img_Base1"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$ColouredBaseOutWipe$TeamColourBase2$img_Base2"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base2 + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$HeaderGrp$FirstnName$txt_FirstName"
					+ "*GEOM*TEXT SET " + inning.getBatting_team().getTeamName2() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$HeaderGrp$LastName$txt_LastName"
					+ "*GEOM*TEXT SET " + inning.getBatting_team().getTeamName3() + "\0", print_writers);

			Collections.sort(inning.getBattingCard());
			
			for (BattingCard bc : inning.getBattingCard()) {
				rowId = rowId + 1;
				switch (bc.getStatus().toUpperCase()) {
					case CricketUtil.OUT:
						omo_num = 0;
						cont_name = "$Out$Out";
						battingSize = battingSize + 1;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
							+ "$Row" + rowId + cont_name + "$img_Base1*TEXTURE*IMAGE SET "+ Constants.T20_MUMBAI_Base1 + 
							inning.getBatting_team().getTeamBadge() + "\0", print_writers);
						
						break;
					case CricketUtil.NOT_OUT:
						omo_num = 1;
						cont_name = "$Out$NotOut";
						battingSize = battingSize + 1;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$img_Base1*TEXTURE*IMAGE SET "+ Constants.T20_MUMBAI_Base1 + 
								inning.getBatting_team().getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$img_Text1*TEXTURE*IMAGE SET "+ Constants.T20_MUMBAI_Text1 + 
								inning.getBatting_team().getTeamBadge() + "\0", print_writers);
						break;
					case CricketUtil.STILL_TO_BAT:
						if(bc.getHowOut() != null) {
							omo_num = 0;
							cont_name = "$Out$Out";
							battingSize = battingSize + 1;
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$img_Base1*TEXTURE*IMAGE SET "+ Constants.T20_MUMBAI_Base1 + 
								inning.getBatting_team().getTeamBadge() + "\0", print_writers);
						}
						break;
					}
				if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId()).isEmpty()) {
					switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId())) {
					case "IMP_IN":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
						break;
					case "IMP_OUT":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						break;
					case "CON_IN":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 4\0", print_writers);
						break;
					case "CON_OUT":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 3\0", print_writers);
						break;
					}
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
							+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
						+ "$Row" + rowId + "$select_DataType*FUNCTION*Omo*vis_con SET " + omo_num + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
						+ "$Row" + rowId + cont_name + "$txt_BatterName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() 
						+ " \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
						+ "$Row" + rowId + cont_name + "$txt_Scores*GEOM*TEXT SET " + bc.getRuns() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
						+ "$Row" + rowId + cont_name + "$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + " \0", print_writers);
			}
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
					+ "$select_Rows*FUNCTION*Omo*vis_con SET " + battingSize + " \0", print_writers);
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniBowling$Body$Side" + WhichSide 
//					+ "$SelectRows*SCRIPT*INSTANCE*controlwidth INVOKE\0", print_writers);
			break;
		case "Shift_F2":
			
			int bowling_size = 0;
			rowId = 0;
			cont_name = "";
			omo_num = 0;
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Logo$img_Logos_Shadow*TEXTURE*IMAGE SET " 
					+ Constants.T20_MUMBAI_Logos + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Logo$img_Logos*TEXTURE*IMAGE SET " 
					+ Constants.T20_MUMBAI_Logos + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$Essentials$img_Base1"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Elements$img_Base1"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$HeaderGrp$txt_Text"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Text + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$ColouredBaseInWipe$TeamColourBase1$img_Base1"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$ColouredBaseInWipe$TeamColourBase2$img_Base2"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base2 + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$ColouredBaseOutWipe$TeamColourBase1$img_Base1"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base1 + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$ColouredBaseOutWipe$TeamColourBase2$img_Base2"
					+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_Base2 + inning.getBowling_team().getTeamBadge() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$HeaderGrp$FirstnName$txt_FirstName"
					+ "*GEOM*TEXT SET " + inning.getBowling_team().getTeamName2() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$HeaderGrp$LastName$txt_LastName"
					+ "*GEOM*TEXT SET " + inning.getBowling_team().getTeamName3() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
					+ "$select_Rows*FUNCTION*Omo*vis_con SET " + omo_num + " \0", print_writers);

			for (BowlingCard boc : inning.getBowlingCard()) {
				if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
					bowling_size=bowling_size + 1;
				}
				rowId = rowId + 1;
				switch (boc.getStatus().toUpperCase()) {
				case (CricketUtil.OTHER + CricketUtil.BOWLER): case (CricketUtil.LAST + CricketUtil.BOWLER):
					omo_num = 0;
					cont_name = "$Out$Out";
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
							+ "$Row" + rowId + cont_name + "$img_Base1*TEXTURE*IMAGE SET "+ Constants.T20_MUMBAI_Base1 + 
							inning.getBowling_team().getTeamBadge() + "\0", print_writers);
					
					break;
				case (CricketUtil.CURRENT + CricketUtil.BOWLER):
					omo_num = 1;
					cont_name = "$Out$NotOut";
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
							+ "$Row" + rowId + cont_name + "$img_Base1*TEXTURE*IMAGE SET "+ Constants.T20_MUMBAI_Base1 + 
							inning.getBowling_team().getTeamBadge() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
							+ "$Row" + rowId + cont_name + "$img_Text1*TEXTURE*IMAGE SET "+ Constants.T20_MUMBAI_Text1 + 
							inning.getBowling_team().getTeamBadge() + "\0", print_writers);
					
					break;
				}
				
				if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId()).isEmpty()) {
					switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId())) {
					case "IN":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniBowling$Body$Side" + WhichSide 
								+ "$Row" + rowId + cont_name + "$Main$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						break;
					case "IMP_IN":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
						break;
					case "IMP_OUT":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						break;
					case "CON_IN":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 4\0", print_writers);
						break;
					case "CON_OUT":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
								+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 3\0", print_writers);
						break;
					}
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
							+ "$Row" + rowId + cont_name + "$select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}

				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
						+ "$Row" + rowId + "$select_DataType*FUNCTION*Omo*vis_con SET " + omo_num + " \0", print_writers);

				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
						+ "$Row" + rowId + cont_name + "$txt_BatterName*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() 
						+ " \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
						+ "$Row" + rowId + cont_name + "$txt_Scores*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
						+ "$Row" + rowId + cont_name + "$txt_Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls())+ " \0", print_writers);
				
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Minis$PositionY$Datas" 
					+ "$select_Rows*FUNCTION*Omo*vis_con SET " + (inning.getBowlingCard().size()) + " \0", print_writers);
			break;
		}
		return Constants.OK;
	}
	public String populateMiniBody(int WhichSide, String whatToProcess, MatchAllData matchAllData, int WhichInning) throws StreamReadException, DatabindException, FileNotFoundException, IOException {
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.VIDARBHA:
			return populateT20_Vidarbha_MiniBody(WhichSide, whatToProcess, matchAllData, WhichInning);
		case Constants.T20_MUMBAI:
			return populateT20_MUMBAI_MiniBody(WhichSide, whatToProcess, matchAllData, WhichInning);
		case Constants.ICC_U19_2023:
			switch(whatToProcess) {
			case "Shift_F1":
				int battingSize=0;
				cont_name = "";
				omo_num = 0;
				rowId = 0;
				
				if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("NEP")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$img_Flag1*ACTIVE SET 0 \0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$img_Flag1*ACTIVE SET 1 \0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + inning.getBatting_team().getTeamName4() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Batting$txt_FirstName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				
				
				for(int i=1; i<=inning.getBattingCard().size(); i++) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row"+i+"*ACTIVE SET 1 \0", print_writers);	
				}
				
				Collections.sort(inning.getBattingCard());
				
				for (BattingCard bc : inning.getBattingCard()) {
					
					rowId = rowId + 1;
					
					switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.OUT:
							omo_num = 0;
							cont_name = "$Players_Dehighlight";
							battingSize = battingSize + 1;
							break;
						case CricketUtil.NOT_OUT:
							omo_num = 1;
							cont_name = "$Players_Highlight";
							battingSize = battingSize + 1;
							break;
						}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$DataGrp*FUNCTION*Grid*num_row SET " + battingSize + " \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$txt_Name*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + " \0", print_writers);
					
					if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$txt_Out*GEOM*TEXT SET " + "" + " \0", print_writers);
					}else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$txt_Out*GEOM*TEXT SET " +"" + " \0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$obj_Divider*ACTIVE SET 0  \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$fig_Runs*GEOM*TEXT SET " + bc.getRuns() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$fig_Out*GEOM*TEXT SET " + bc.getBalls() + " \0", print_writers);
				}
				
				break;
			case "Shift_F2":
				
				int bowling_size = 1;
				rowId = 1;
				cont_name = "";
				omo_num = 0;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
				
				if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("NEP")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$img_Flag1*ACTIVE SET 0 \0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$img_Flag1*ACTIVE SET 1 \0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Bowling$img_Flag*TEXTURE*IMAGE SET " + Constants.ICC_U19_2023_FLAG_PATH + inning.getBowling_team().getTeamName4() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Bowling$txt_FirstName*GEOM*TEXT SET " + inning.getBowling_team().getTeamName1() + " \0", print_writers);
				
				for(int i=1; i<=inning.getBowlingCard().size(); i++) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row"+(i)+"*ACTIVE SET 1 \0", print_writers);
				}
				
				for (BowlingCard boc : inning.getBowlingCard()) {
					if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
						bowling_size=bowling_size + 1;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + bowling_size + " \0", print_writers);
					}
					switch (boc.getStatus().toUpperCase()) {
					case (CricketUtil.OTHER + CricketUtil.BOWLER):
						omo_num = 2;
						cont_name = "$Players_Dehighlight";
						break;
					case (CricketUtil.LAST + CricketUtil.BOWLER):
						omo_num = 2;
						cont_name = "$Players_Dehighlight";
						break;
					case (CricketUtil.CURRENT + CricketUtil.BOWLER):
						omo_num = 3;
						cont_name = "$Players_Highlight";
						break;
					}
					
					rowId = rowId + 1;
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$txt_Name*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$fig_Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + " \0", print_writers);

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$fig_Maidens*GEOM*TEXT SET " + boc.getMaidens() + " \0", print_writers);

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$fig_Runs*GEOM*TEXT SET " + boc.getRuns() + " \0", print_writers);

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$fig_Wickets*GEOM*TEXT SET " + boc.getWickets() + " \0", print_writers);
					
					if(boc.getEconomyRate() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + rowId + cont_name + "$fig_Economy*GEOM*TEXT SET " + boc.getEconomyRate() + " \0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + rowId + cont_name + "$fig_Economy*GEOM*TEXT SET " + "-" + " \0", print_writers);
					}
					

				}
				break;
			}
			break;
		case Constants.NPL: case Constants.LEGENDS: case Constants.MPL: case Constants.APL:
			switch(whatToProcess) {
			case "Shift_F1":
				int battingSize=0;
				cont_name = "";
				omo_num = 0;
				rowId = 0;
				
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$img_Flag*TEXTURE*IMAGE SET " 
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBatting_team().getTeamBadge() + " \0", print_writers);
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH + inning.getBatting_team().getTeamBadge() + logoCategory + " \0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$txt_FirstName*GEOM*TEXT SET " + 
						(config.getBroadcaster().equalsIgnoreCase(Constants.LEGENDS)?inning.getBatting_team().getTeamName3():inning.getBatting_team().getTeamName3()) 
						+ " \0", print_writers);
				
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				
				
				for(int i=1; i<=inning.getBattingCard().size(); i++) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row"+i+"*ACTIVE SET 1 \0", print_writers);	
				}
				
				Collections.sort(inning.getBattingCard());
				
				for (BattingCard bc : inning.getBattingCard()) {
					
					rowId = rowId + 1;
					
					switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.OUT:
							omo_num = 0;
							cont_name = "$Players_Dehighlight";
							battingSize = battingSize + 1;
							break;
						case CricketUtil.NOT_OUT:
							omo_num = 1;
							cont_name = "$Players_Highlight";
							battingSize = battingSize + 1;
							break;
						case CricketUtil.STILL_TO_BAT:
							if(bc.getHowOut() != null) {
								if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT) || 
										bc.getHowOut().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
									omo_num = 0;
									cont_name = "$Players_Dehighlight";
									battingSize = battingSize + 1;
								}
							}
							break;	
						}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$DataGrp*FUNCTION*Grid*num_row SET " + battingSize + " \0", print_writers);
					
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.MPL:
						if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId()).isEmpty()) {
							switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId())) {
							case "IMP_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$In_Out*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							case "IMP_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$In_Out*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
									+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$In_Out*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
						break;
					case Constants.LEGENDS:
						if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId()).isEmpty()) {
							switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId())) {
							case "IMP_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact*GEOM*TEXT SET IMP\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							case "IMP_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$SubGrp$Impact*GEOM*TEXT SET SUB\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							case "CON_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact*GEOM*TEXT SET CON\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							case "CON_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$SubGrp$Impact*GEOM*TEXT SET CON\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
									+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
						break;
					case Constants.APL: 
						if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId()).isEmpty()) {
							switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId())) {
							case "IMP_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact$txt_BatterLastName*GEOM*TEXT SET IMP \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							case "IMP_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Sub$txt_BatterLastName*GEOM*TEXT SET SUB \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							case "CON_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact$txt_BatterLastName*GEOM*TEXT SET CON \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							case "CON_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Sub$txt_BatterLastName*GEOM*TEXT SET CON \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
									+ "$Batting$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
						break;
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$txt_Name*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + " \0", print_writers);
					String Out_not = "";
					if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$txt_Out*GEOM*TEXT SET " + "" + " \0", print_writers);
						Out_not = "*";
					}else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT) || 
							bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
						
						Out_not = "";
						if(bc.getHowOut() != null) {
							if(bc.getHowOut().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
								Out_not = "*";
							}
						}
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$txt_Out*GEOM*TEXT SET " +"" + " \0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$obj_Divider*ACTIVE SET 0  \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$fig_Runs*GEOM*TEXT SET " + bc.getRuns() +Out_not+ " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$fig_Out*GEOM*TEXT SET " + bc.getBalls() + " \0", print_writers);
				}
				
				break;
				
			case "Alt_F1":
				switch (config.getBroadcaster().toUpperCase()) {
//				case Constants.LEGENDS:
//					int row_id1 = 0;
//					String con_name = "";
//					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//							+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + team.getTeamBadge() + " \0", print_writers);
//					for(int i=1; i<=14; i++) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + i + 
//								"$Players_Highlight$Data$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + i + 
//								"$Players_Dehighlight$Data$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
//					}
//					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//							+ "$Batting$txt_FirstName*GEOM*TEXT SET " + player.getFull_name() + " \0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//							+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
//					
//					for(int i=1; i<=13; i++) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//								+ "$Batting$Row" + i + "*ACTIVE SET 0\0", print_writers);
//					}
//					
//					for(BatBallGriff gf :CricketFunctions.getBatBallGriff(player, CricketUtil.BATSMAN, FirstPlayerId, team, headToHead, matchAllData)){
//						
//						row_id1++;
//						
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + row_id1
//								+ "$Select_Row_Type*FUNCTION*Omo*vis_con SET 0\0",print_writers);
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$DataGrp"
//								+ "*FUNCTION*Grid*num_row SET " + row_id1 + " \0",print_writers);
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + row_id1
//								+ "*ACTIVE SET 1\0",print_writers);
//						
//						if(gf.getMatchNumber().equalsIgnoreCase(matchAllData.getMatch().getMatchFileName().replace(".json", ""))) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Mini$Side" + WhichSide + "$Select$Bating$AllDataGrp$CardAll$Data$DataGrp$Row"
//										+ row_id1 + "$RowAnimation$Select*FUNCTION*Omo*vis_con SET 1\0",print_writers);
//							con_name = "$Highlight";
//						}else {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Mini$Side" + WhichSide + "$Select$Bating$AllDataGrp$CardAll$Data$DataGrp$Row"
//										+ row_id1 + "$RowAnimation$Select*FUNCTION*Omo*vis_con SET 0\0",print_writers);
//							con_name = "$Dehighlight";
//						}
//						
//						System.out.println("GF_STATUS = " + gf.getStatus() + "  ID = " + gf.getMatchIdent());
//						if(gf.getMatchIdent().contains("ELIMINATOR")) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id1
//									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + gf.getOpponentTeam().getTeamName3() + ", ELM" + " \0",print_writers);
//						}else if(gf.getMatchIdent().contains("QUALIFIER 1")) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id1
//									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + gf.getOpponentTeam().getTeamName3() + ", QF 1" + " \0",print_writers);
//						}else if(gf.getMatchIdent().contains("QUALIFIER 2")) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id1
//									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + gf.getOpponentTeam().getTeamName3() + ", QF 2" + " \0",print_writers);
//						}else if(gf.getMatchIdent().contains("FINAL")) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id1
//									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + gf.getOpponentTeam().getTeamName3() + ", FINAL" + " \0",print_writers);
//						}else {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id1
//									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + gf.getOpponentTeam().getTeamName3() + ", " + getMatchCode(gf.getMatchNumber()) + " \0",print_writers);
//						}
//						
//						
//						if(gf.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
//							if(gf.getBallsFaced() != 0) {
//								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//										+ row_id1 + "$Players_Dehighlight$fig_Out*GEOM*TEXT SET " + gf.getBallsFaced() + "\0",print_writers);
//								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//										+ row_id1+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + gf.getRuns() + "\0", print_writers);
//							}else {
//								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//										+ row_id1 + "$Players_Dehighlight$fig_Out*GEOM*TEXT SET \0",print_writers);
//								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//										+ row_id1+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET DNB\0", print_writers);
//							}
//						}else if(gf.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//									+ row_id1 + "$Players_Dehighlight$fig_Out*GEOM*TEXT SET " + gf.getBallsFaced() + "\0",print_writers);
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//									+ row_id1+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + gf.getRuns() + "\0", print_writers);
//						}else if(gf.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//									+ row_id1 + "$Players_Dehighlight$fig_Out*GEOM*TEXT SET " + gf.getBallsFaced() + "\0",print_writers);
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//									+ row_id1+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + gf.getRuns() + "\0", print_writers);
//						}else {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//									+ row_id1 + "$Players_Dehighlight$fig_Out*GEOM*TEXT SET \0",print_writers);
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
//									+ row_id1+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + gf.getStatus() + "\0", print_writers);
//						}
//					}
//					break;
				case Constants.NPL: case Constants.APL: case Constants.MPL: case Constants.LEGENDS:
					int omo_num = 0;
					int row_id = 0, counter = 0;
					String MatchNam = "";
					boolean playerFound = false;
					
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.NPL: case Constants.APL:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$img_Flag*TEXTURE*IMAGE SET " + 
								(config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) + team.getTeamBadge() + " \0", print_writers);
						
						for(int i=1; i<=14; i++) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + i + 
									"$Players_Highlight$ImpactGrp$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + i + 
									"$Players_Dehighlight$ImpactGrp$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
						break;
					case Constants.MPL: 
						
						if(config.getCategory().equalsIgnoreCase("MEN")) {
							logoCategory = "M";
						}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
							logoCategory = "W";
						}else {
							logoCategory = "";
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH + team.getTeamBadge() + logoCategory + " \0", print_writers);
						break;	
					case Constants.LEGENDS:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + team.getTeamBadge() + " \0", print_writers);
						for(int i=1; i<=14; i++) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + i + 
									"$Players_Highlight$Data$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + i + 
									"$Players_Dehighlight$Data$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
						break;
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$txt_FirstName*GEOM*TEXT SET " + player.getFull_name() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
					
					for(int i=1; i<=13; i++) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + i + "*ACTIVE SET 0\0", print_writers);
					}
					
					processedMatches = new HashSet<>();
					playerMatchData = new HashMap<>();

					// FIRST PASS
					for(HeadToHeadPlayer h2h : headToHead) {
						if(h2h.getTeam() == null || h2h.getOpponentTeam() == null)
							continue;
						if(!h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4()))
							continue;
						String matchName = h2h.getMatchFileName();

						// STORE PLAYER DATA
						if(h2h.getPlayerId() == player.getPlayerId()) {
							playerMatchData.put(matchName, h2h);
						}
					}

					// SECOND PASS
					for(HeadToHeadPlayer h2h : headToHead) {
						if(h2h.getTeam() == null || h2h.getOpponentTeam() == null)
							continue;
						if(!h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4()))
							continue;
						String matchName = h2h.getMatchFileName();

						// AVOID DUPLICATE MATCH ROWS
						if(processedMatches.contains(matchName))
							continue;
						processedMatches.add(matchName);
						row_id++;

						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + row_id
								+ "$Select_Row_Type*FUNCTION*Omo*vis_con SET 0\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$DataGrp"
								+ "*FUNCTION*Grid*num_row SET " + row_id + " \0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" + row_id
								+ "*ACTIVE SET 1\0",print_writers);
						
						if(h2h.getMatchFileName().contains("ELIMINATOR")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", ELM" + " \0",print_writers);
						}else if(h2h.getMatchFileName().contains("QUALIFIER 1")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", QF 1" + " \0",print_writers);
						}else if(h2h.getMatchFileName().contains("QUALIFIER 2")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", QF 2" + " \0",print_writers);
						}else if(h2h.getMatchFileName().contains("FINAL")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", FINAL" + " \0",print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
									+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", " + getMatchCode(h2h.getMatchFileName().replace(".json", "")) + " \0",print_writers);
						}

						// PLAYER PLAYED
						if(playerMatchData.containsKey(matchName)) {
							HeadToHeadPlayer ply = playerMatchData.get(matchName);
							if(ply.getInningStarted().contains("Y")) {
								// NOT OUT
								if(ply.getDismissed().contains("N")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
											+ row_id + "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + ply.getRuns() + "* \0", print_writers);
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
											+ row_id + "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + ply.getRuns() + " \0",print_writers);
								}

								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+ row_id + "$Players_Dehighlight$fig_Out*GEOM*TEXT SET " + ply.getBallsFaced() + " \0", print_writers);
							}else {

								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+ row_id + "$Players_Dehighlight$fig_Out*GEOM*TEXT SET \0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+ row_id+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET DNB\0", print_writers);
							}

						}
						// PLAYER DID NOT PLAY
						else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
									+ row_id + "$Players_Dehighlight$fig_Out*GEOM*TEXT SET \0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
									+ row_id + "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET DNP\0", print_writers);
						}
					}
					
					for(BattingCard bc : inning.getBattingCard()) {
						if(bc.getPlayerId() == player.getPlayerId()) {
							row_id++;
							playerFound = true;
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + row_id + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$DataGrp*FUNCTION*Grid*num_row SET " + row_id + " \0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + row_id + "*ACTIVE SET 1\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + row_id + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								if(matchAllData.getSetup().getMatchIdent().contains("ELIMINATOR")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", ELM" + " \0",print_writers);
								}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 1")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", QF 1" + " \0",print_writers);
								}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 2")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", QF 2" + " \0",print_writers);
								}else if(matchAllData.getSetup().getMatchIdent().contains("FINAL")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", FINAL" + " \0",print_writers);
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
											+ "$Batting$Row" + row_id + "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", " + getMatchCode(matchAllData.getSetup().getMatchIdent()) + " \0", print_writers);
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + row_id + "$Players_Highlight$fig_Out*GEOM*TEXT SET \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + row_id + "$Players_Highlight$fig_Runs*GEOM*TEXT SET DNB\0", print_writers);
								
							}else {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + row_id + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$DataGrp*FUNCTION*Grid*num_row SET " + row_id + " \0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + row_id + "*ACTIVE SET 1\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + row_id + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								
								if(matchAllData.getSetup().getMatchIdent().contains("ELIMINATOR")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", ELM" + " \0",print_writers);
								}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 1")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", QF 1" + " \0",print_writers);
								}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 2")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", QF 2" + " \0",print_writers);
								}else if(matchAllData.getSetup().getMatchIdent().contains("FINAL")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", FINAL" + " \0",print_writers);
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
											+ "$Batting$Row" + row_id + "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", " + getMatchCode(matchAllData.getSetup().getMatchIdent()) + " \0", print_writers);
								}
								
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
											+ "$Batting$Row" + row_id + "$Players_Highlight$fig_Runs*GEOM*TEXT SET " + bc.getRuns() + "\0", print_writers);
									
								}else {
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
											+ "$Batting$Row" + row_id + "$Players_Highlight$fig_Runs*GEOM*TEXT SET " + bc.getRuns() + "*" + "\0", print_writers);
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Batting$Row" + row_id + "$Players_Highlight$fig_Out*GEOM*TEXT SET " + bc.getBalls() + "\0", print_writers);
								
							}
						}
					}
					if(!playerFound) {
						row_id++;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + row_id + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$DataGrp*FUNCTION*Grid*num_row SET " + row_id + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + row_id + "*ACTIVE SET 1\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + row_id + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						
						if(matchAllData.getSetup().getMatchIdent().contains("ELIMINATOR")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
									+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", ELM" + " \0",print_writers);
						}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 1")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
									+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", QF 1" + " \0",print_writers);
						}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 2")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
									+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", QF 2" + " \0",print_writers);
						}else if(matchAllData.getSetup().getMatchIdent().contains("FINAL")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Batting$Row" + row_id
									+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", FINAL" + " \0",print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
									+ "$Batting$Row" + row_id + "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName3() + ", " + getMatchCode(matchAllData.getSetup().getMatchIdent()) + " \0", print_writers);
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + row_id + "$Players_Highlight$fig_Out*GEOM*TEXT SET \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + row_id + "$Players_Highlight$fig_Runs*GEOM*TEXT SET DNP\0", print_writers);
						
					}
					break;
				}
				break;
				
			case "Shift_F2":
				
				int bowling_size = 0;
				rowId = 0;
				
				cont_name = "";
				omo_num = 0;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
				
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$img_Flag*TEXTURE*IMAGE SET " 
							+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) 
							+ inning.getBowling_team().getTeamBadge() + " \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Header$headerBnd$img_Header"
							+ "*TEXTURE*IMAGE SET IMAGE*/Default/Essentials/Textures/Color02\0", print_writers);
					for(int i=1;i<=14;i++) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$DataGrp$Row" + i + "$Players_Highlight$"
								+ "img_Highlight*TEXTURE*IMAGE SET IMAGE*/Default/Essentials/Textures/Color01\0", print_writers);
					}
					break;
				case Constants.MPL: 
					
					if(config.getCategory().equalsIgnoreCase("MEN")) {
						logoCategory = "M";
					}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						logoCategory = "W";
					}else {
						logoCategory = "";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$img_Flag*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH + inning.getBowling_team().getTeamBadge() + logoCategory + " \0", print_writers);
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$txt_FirstName*GEOM*TEXT SET " + 
						(config.getBroadcaster().equalsIgnoreCase(Constants.LEGENDS)?inning.getBowling_team().getTeamName3():inning.getBowling_team().getTeamName3())  
						+ " \0", print_writers);
				
				for(int i=1; i<=inning.getBowlingCard().size(); i++) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row"+(i)+"*ACTIVE SET 1 \0", print_writers);
				}
				
				for (BowlingCard boc : inning.getBowlingCard()) {
					if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
						bowling_size=bowling_size + 1;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + bowling_size + " \0", print_writers);
					}
					
					if(inning.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
						omo_num = 0;
						cont_name = "$Players_Dehighlight";
					}else {
						switch (boc.getStatus().toUpperCase()) {
						case (CricketUtil.OTHER + CricketUtil.BOWLER):
							omo_num = 0;
							cont_name = "$Players_Dehighlight";
							break;
						case (CricketUtil.LAST + CricketUtil.BOWLER):
							omo_num = 0;
							cont_name = "$Players_Dehighlight";
							break;
						case (CricketUtil.CURRENT + CricketUtil.BOWLER):
							omo_num = 1;
							cont_name = "$Players_Highlight";
							break;
						}
					}
					
					rowId = rowId + 1;
					
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.MPL:
						if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId()).isEmpty()) {
							switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId())) {
							case "IMP_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$In_Out*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							case "IMP_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$In_Out*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
									+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$In_Out*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
						break;
					case Constants.LEGENDS:
						if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId()).isEmpty()) {
							switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId())) {
							case "IMP_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact*GEOM*TEXT SET IMP\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							case "IMP_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$SubGrp$Impact*GEOM*TEXT SET SUB\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							case "CON_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact*GEOM*TEXT SET CON\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							case "CON_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$SubGrp$Impact*GEOM*TEXT SET CON\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
									+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
						break;
					case Constants.APL:
						if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId()).isEmpty()) {
							switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId())) {
							case "IMP_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact$txt_BatterLastName*GEOM*TEXT SET IMP \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							case "IMP_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact$txt_BatterLastName*GEOM*TEXT SET SUB \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							case "CON_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact$txt_BatterLastName*GEOM*TEXT SET CON \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								break;
							case "CON_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Batting$Row" 
										+  rowId + "$Select_Row_Type$" + cont_name + "$ImpactGrp$Impact$txt_BatterLastName*GEOM*TEXT SET CON \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								break;
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
									+ "$Bowling$Row" + rowId + "$Select_Row_Type" + cont_name + "$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
						break;
					}
					
					

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$txt_Name*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + " \0", print_writers);
					switch(config.getBroadcaster()) {
						case Constants.LEGENDS :
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + rowId + cont_name + 
									"$fig_Out*GEOM*TEXT SET " +  CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + " \0", print_writers);
							break;
						default:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
									+ "$Bowling$Row" + rowId + cont_name + "$fig_Out*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + " \0", print_writers);
							break;
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$fig_Runs*GEOM*TEXT SET " + boc.getWickets() + "-" + boc.getRuns() + " \0", print_writers);
				}
				break;
				
			case "Alt_F2":
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.LEGENDS:
					int row_no = 0, count = 0;
					String MatchName = "",ident="";
					rowId = 0;
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
					
					for(int i=1;i<=14;i++) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + i + 
								"$Players_Highlight$Data$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + i + 
								"$Players_Dehighlight$Data$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + team.getTeamBadge() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$txt_FirstName*GEOM*TEXT SET " + player.getFull_name() + " \0", print_writers);
					
					for(HeadToHeadPlayer h2h : headToHead) {
						
						if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + h2h.getMatchFileName()).exists() == true) {
							ident = new ObjectMapper().readValue(new InputStreamReader(new FileInputStream(new File(CricketUtil.CRICKET_DIRECTORY 
									+ CricketUtil.SETUP_DIRECTORY + h2h.getMatchFileName())), StandardCharsets.UTF_8), Setup.class).getMatchIdent();
						}
						
						if(h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())){
							System.out.println("PLAYER ID - " + player.getPlayerId() + " MATCH - " + h2h.getMatchFileName() + " count - " + count);
						}
						
						if(h2h.getPlayerId() == player.getPlayerId() && h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
							row_no++;
							MatchName = h2h.getMatchFileName();
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
									+ "$Select_Row_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$DataGrp"
									+ "*FUNCTION*Grid*num_row SET " + row_no + " \0",print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
									+ "$Select_Row_Type*FUNCTION*Omo*vis_con SET 0 \0", print_writers);

							if(ident.contains("ELIMINATOR")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", ELM" + " \0", print_writers);
							}else if(ident.contains("QUALIFIER 1")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", QF 1" + " \0", print_writers);
							}else if(ident.contains("QUALIFIER 2")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", QF 2" + " \0", print_writers);
							}else if(ident.contains("FINAL")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", FINAL" + " \0", print_writers);
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", " + getMatchCode(h2h.getMatchFileName().replace(".json", "")) + " \0", print_writers);
							}
							
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Mini$Side" + WhichSide + "$Select$Bowling$AllDataGrp$CardAll$Data$Row"
//									+ row_no + "*ACTIVE SET 1 \0",print_writers);
							
							if(h2h.getBallsBowled() == 0) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET \0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET DNB\0", print_writers);
								
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + h2h.getWickets() +"-"+h2h.getRunsConceded() + "\0",print_writers);
								
								if(h2h.getBallsBowled()%6 == 0) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
											+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET " + (h2h.getBallsBowled()/6) + "\0", print_writers);
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
											+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET " + (h2h.getBallsBowled()/6)+"."+h2h.getBallsBowled()%6 + "\0", print_writers);
								}
							}						
							count = 0;
						}else if(h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
							if(count == 10) { //For Impact Player use 11 Otherwise use 10
								row_no++;
								if(ident.contains("ELIMINATOR")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", ELM" + " \0", print_writers);
								}else if(ident.contains("QUALIFIER 1")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", QF 1" + " \0", print_writers);
								}else if(ident.contains("QUALIFIER 2")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", QF 2" + " \0", print_writers);
								}else if(ident.contains("FINAL")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", FINAL" + " \0", print_writers);
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + ", " + getMatchCode(h2h.getMatchFileName().replace(".json", "")) + " \0", print_writers);
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + row_no + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + row_no + " \0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Mini$Side" + WhichSide + "$Select$Bowling$AllDataGrp$CardAll$Data$Row"
										+ row_no + "*ACTIVE SET 1 \0",print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET \0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET DNP\0", print_writers);
								
								count = 0;
							}else if(!MatchName.equalsIgnoreCase(h2h.getMatchFileName()) && count < 11) {
								MatchName = h2h.getMatchFileName();
								count = 1;
							}else {
								if(count==10) {
									count=0;
								}
								count++;
							}
						}
					}
					
					boolean playerIsInBoc = false;
					if(inning.getBowlingCard() != null) {
						for(BowlingCard boc : inning.getBowlingCard()) {
							if(boc.getPlayerId() == player.getPlayerId()) {
								playerIsInBoc = true;
								row_no++;
								
								if(matchAllData.getSetup().getMatchIdent().contains("ELIMINATOR")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", ELM" + " \0", print_writers);
								}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 1")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", QF 1" + " \0", print_writers);
								}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 2")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", QF 2" + " \0", print_writers);
								}else if(matchAllData.getSetup().getMatchIdent().contains("FINAL")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", FINAL" + " \0", print_writers);
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
											+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", " + getMatchCode(matchAllData.getSetup().getMatchIdent()) + " \0", print_writers);
								}
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + row_no + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + row_no + " \0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Mini$Side" + WhichSide + "$Select$Bowling$AllDataGrp$CardAll$Data$Row"
										+ row_no + "*ACTIVE SET 1 \0",print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + boc.getWickets()+"-"+ boc.getRuns() + "\0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0", print_writers);
								break;
							}else {
								playerIsInBoc = false;
							}
						}
					}
					
					if(!playerIsInBoc) {
						row_no++;
						
						if(matchAllData.getSetup().getMatchIdent().contains("ELIMINATOR")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
									+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", ELM" + " \0", print_writers);
						}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 1")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
									+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", QF 1" + " \0", print_writers);
						}else if(matchAllData.getSetup().getMatchIdent().contains("QUALIFIER 2")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
									+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", QF 2" + " \0", print_writers);
						}else if(matchAllData.getSetup().getMatchIdent().contains("FINAL")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
									+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", FINAL" + " \0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no
									+ "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + ", " + getMatchCode(matchAllData.getSetup().getMatchIdent()) + " \0", print_writers);
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + row_no + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + row_no + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Mini$Side" + WhichSide + "$Select$Bowling$AllDataGrp$CardAll$Data$Row"
								+ row_no + "*ACTIVE SET 1 \0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
								+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET \0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
								+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET DNP\0", print_writers);
						
						
						List<Player> plyrs = (matchAllData.getSetup().getHomeTeamId() == team.getTeamId() ? matchAllData.getSetup().getHomeSquad() : 
							matchAllData.getSetup().getAwaySquad());
						for(Player plyr : plyrs) {
							if(plyr.getPlayerId() == player.getPlayerId()) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no
										+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET DNB\0", print_writers);
								break;
							}
						}
					}
					break;
				default:
					int row_no1 = 0, count1 = 0;
					String MatchName1 = "";
					rowId = 0;
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
					
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.NPL: case Constants.APL:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$img_Flag*TEXTURE*IMAGE SET " 
								+ (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH) + team.getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Header$headerBnd$img_Header"
								+ "*TEXTURE*IMAGE SET IMAGE*/Default/Essentials/Textures/Color02\0", print_writers);
						for(int i=1;i<=14;i++) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + i + 
									"$Players_Highlight$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + i + 
									"$Players_Dehighlight$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$DataGrp$Row" + i + "$Players_Highlight$"
									+ "img_Highlight*TEXTURE*IMAGE SET IMAGE*/Default/Essentials/Textures/Color01\0", print_writers);
						}
						break;
					case Constants.MPL:
						if(config.getCategory().equalsIgnoreCase("MEN")) {
							logoCategory = "M";
						}else if(config.getCategory().equalsIgnoreCase("WOMEN")) {
							logoCategory = "W";
						}else {
							logoCategory = "";
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$img_Flag*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH + team.getTeamBadge() + logoCategory + " \0", print_writers);
						break;	
					case Constants.LEGENDS:
						for(int i=1;i<=14;i++) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + i + 
									"$Players_Highlight$Data$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + i + 
									"$Players_Dehighlight$Data$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + team.getTeamBadge() + " \0", print_writers);
						break;
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$txt_FirstName*GEOM*TEXT SET " + player.getFull_name() + " \0", print_writers);
					
					processedMatches = new HashSet<>();
					playerMatchData = new HashMap<>();

					// FIRST PASS
					for(HeadToHeadPlayer h2h : headToHead) {
						if(h2h.getTeam() == null || h2h.getOpponentTeam() == null)
							continue;
						if(!h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4()))
							continue;
						if(h2h.getPlayerId() == player.getPlayerId()) {
							playerMatchData.put(h2h.getMatchFileName(), h2h);
						}
					}

					// SECOND PASS
					for(HeadToHeadPlayer h2h : headToHead) {
						if(h2h.getTeam() == null || h2h.getOpponentTeam() == null)
							continue;
						if(!h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4()))
							continue;
						String matchFile = h2h.getMatchFileName();
						// AVOID DUPLICATE MATCH ROWS
						if(processedMatches.contains(matchFile))
							continue;
						processedMatches.add(matchFile);
						row_no1++;

						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no1
								+ "$Select_Row_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$DataGrp"
								+ "*FUNCTION*Grid*num_row SET " + row_no1 + " \0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no1
								+ "$Select_Row_Type*FUNCTION*Omo*vis_con SET 0 \0", print_writers);

						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide+ "$Bowling$Row" + row_no1
								+ "$Players_Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName3() + " \0", print_writers);

						// PLAYER PLAYED
						if(playerMatchData.containsKey(matchFile)) {
							HeadToHeadPlayer ply = playerMatchData.get(matchFile);
							// DID NOT BOWL
							if(ply.getBallsBowled() == 0) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no1
										+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET \0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no1
										+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET DNB\0",print_writers);
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no1
										+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET " + ply.getWickets() + "-" + ply.getRunsConceded() + "\0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no1
										+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET " + CricketFunctions.OverBalls(0, ply.getBallsBowled()) + "\0", print_writers);
							}
						}
						// PLAYER DID NOT PLAY
						else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no1
									+ "$Players_Dehighlight$fig_Out*GEOM*TEXT SET \0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$Bowling$Row" + row_no1
									+ "$Players_Dehighlight$fig_Runs*GEOM*TEXT SET DNP\0",print_writers);
						}
					}
					
					boolean playerIsInBoc1 = false;
					if(inning.getBowlingCard() != null) {
						for(BowlingCard boc : inning.getBowlingCard()) {
							if(boc.getPlayerId() == player.getPlayerId()) {
								playerIsInBoc1 = true;
								row_no1++;
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + row_no1 + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + row_no1 + " \0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + row_no1 + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + row_no1 + "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + " \0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + row_no1 + "$Players_Highlight$fig_Out*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + " \0", print_writers);

								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + row_no1 + "$Players_Highlight$fig_Runs*GEOM*TEXT SET " + boc.getWickets()+"-"+ boc.getRuns() + "\0", print_writers);
								
								break;
							}else {
								playerIsInBoc1 = false;
							}
						}
					}
					
					if(!playerIsInBoc1) {
						row_no1++;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + row_no1 + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + row_no1 + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + row_no1 + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + row_no1 + "$Players_Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName3() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + row_no1 + "$Players_Highlight$fig_Out*GEOM*TEXT SET \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + row_no1 + "$Players_Highlight$fig_Runs*GEOM*TEXT SET DNP\0", print_writers);
						
						List<Player> plyrs = (matchAllData.getSetup().getHomeTeamId() == team.getTeamId() ? matchAllData.getSetup().getHomeSquad() : 
							matchAllData.getSetup().getAwaySquad());
						for(Player plyr : plyrs) {
							if(plyr.getPlayerId() == player.getPlayerId()) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
										+ "$Bowling$Row" + row_no1 + "$Players_Highlight$fig_Runs*GEOM*TEXT SET DNB\0", print_writers);
								break;
							}
						}
					}
					break;
				}
				break;	
			case "Alt_F7":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$PointsTale$AllDataGrp$DataGrp*FUNCTION*Grid*num_row SET 9\0", print_writers);
				
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide + "$PointsTale$img_Flag"
							+ "*TEXTURE*IMAGE SET " + (config.getBroadcaster().equalsIgnoreCase(Constants.APL)?Constants.APL_LOGO_PATH:Constants.NPL_LOGO_PATH)
							+ "TLogo \0", print_writers);
					break;
				case Constants.MPL:
					
					if(config.getCategory().equalsIgnoreCase("WOMEN")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$PointsTale$img_Flag*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH + "TLogo_WM \0", print_writers);
						
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$PointsTale$img_Flag*TEXTURE*IMAGE SET " + Constants.MPL_LOGO_PATH + "TLogo \0", print_writers);
						
					}
					break;	
				case Constants.LEGENDS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.LEGENDS_LOGO_PATH + "TLogo \0", print_writers);
					break;
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$Header$txt_FirstName*GEOM*TEXT SET POINTS TABLE\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row1$Players_Dehighlight$txt_Name*GEOM*TEXT SET \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row1$Players_Dehighlight$fig_Points*GEOM*TEXT SET PTS\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row1$Players_Dehighlight$fig_Draw*GEOM*TEXT SET NR/T\0", print_writers);
								
				rowId = 1;
				for(int i=0; i<=leagueTable.getLeagueTeams().size()-1;i++) {
					rowId++;
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$PointsTale$AllDataGrp$DataGrp*FUNCTION*Grid*num_row SET " + rowId + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$PointsTale$AllDataGrp$DataGrp$Row"+rowId+"*ACTIVE SET 1\0", print_writers);
					if(matchAllData.getSetup().getHomeTeam().getTeamBadge().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())  
							|| matchAllData.getSetup().getAwayTeam().getTeamBadge().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						containerName = "$Players_Highlight";
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						containerName = "$Players_Dehighlight";
					}
					
					if(leagueTable.getLeagueTeams().get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+
								containerName+"$txt_Rank*GEOM*TEXT SET " + (rowId - 1) + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+containerName+
								"$txt_Rank*GEOM*TEXT SET "+leagueTable.getLeagueTeams().get(i).getQualifiedStatus().trim()+"\0", print_writers);
					}
					
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.LEGENDS:
						for(Team team : Teams) {
							if(team.getTeamBadge().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+containerName+
										"$txt_Name*GEOM*TEXT SET "+team.getTeamName3()+"\0", print_writers);
							}
						}
						break;
					default:
						for(Team team : Teams) {
							if(team.getTeamName4().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+containerName+
										"$txt_Name*GEOM*TEXT SET "+team.getTeamName3()+"\0", print_writers);
							}
						}
						break;
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+containerName+"$fig_Played*GEOM*TEXT SET "+leagueTable.getLeagueTeams().get(i).getPlayed()+"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+containerName+"$fig_Won*GEOM*TEXT SET "+leagueTable.getLeagueTeams().get(i).getWon()+"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+containerName+"$fig_Lost*GEOM*TEXT SET "+leagueTable.getLeagueTeams().get(i).getLost()+"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+containerName+"$fig_Draw*GEOM*TEXT SET "+leagueTable.getLeagueTeams().get(i).getNoResult()+"\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$PointsTale$DataGrp$Row"+rowId+containerName+"$fig_Points*GEOM*TEXT SET "+leagueTable.getLeagueTeams().get(i).getPoints()+"\0", print_writers);
				}
				break;
			}
			break;	
		case Constants.ISPL:
			switch(whatToProcess) {
			case "Alt_F7":
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//						+ "$Batting$LooBase$img2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + 
//						inning.getBatting_team().getTeamName4() + " \0", print_writers);
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//						+ "$Batting$headerBnd$img1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + 
//						inning.getBatting_team().getTeamName4() + " \0", print_writers);
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//						+ "$Batting$txt_FirstName*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + 
//						inning.getBatting_team().getTeamName4() + " \0", print_writers);
//				
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//						+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + inning.getBatting_team().getTeamName4() + " \0", print_writers);
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//						+ "$Batting$txt_FirstName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName1() + " \0", print_writers);
				
				rowId = 1;
				for(int i=0; i<=leagueTable.getLeagueTeams().size()-1;i++) {
					rowId = rowId + 1;
					
					if(matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())  
							|| matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
								+ "$Row" + rowId + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
						containerName = "$Highlight";
						
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
								+ "$Row" + rowId + "$Select_Row_Type*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
						containerName = "$Dehighlight";
					}
					
					if(leagueTable.getLeagueTeams().get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
								+ "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Rank*GEOM*TEXT SET " + (rowId-1) + " \0", print_writers);
					}else if(leagueTable.getLeagueTeams().get(i).getQualifiedStatus().trim().equalsIgnoreCase("Q")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
								+ "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Rank*GEOM*TEXT SET Q \0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
							+ "$Row" + rowId + "$Select_Row_Type" + containerName + "$txt_Name*GEOM*TEXT SET " + 
							leagueTable.getLeagueTeams().get(i).getTeamName() + " \0", print_writers);
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
							+ "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Play*GEOM*TEXT SET " + 
							leagueTable.getLeagueTeams().get(i).getPlayed() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
							+ "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Wins*GEOM*TEXT SET " + 
							leagueTable.getLeagueTeams().get(i).getWon() + " \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
							+ "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_Points*GEOM*TEXT SET " + 
							leagueTable.getLeagueTeams().get(i).getPoints() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$MiniPointsTable$AllDataGrp$DataGrp"
							+ "$Row" + rowId + "$Select_Row_Type" + containerName + "$fig_NRR*GEOM*TEXT SET " + 
							String.format("%.2f", leagueTable.getLeagueTeams().get(i).getNetRunRate()) + " \0", print_writers);
					
				}
				break;
			case "Shift_F1":
				int battingSize=0;
				cont_name = "";
				omo_num = 0;
				rowId = 0;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Batting$LooBase$img2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + 
						inning.getBatting_team().getTeamName4() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Batting$headerBnd$img1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + 
						inning.getBatting_team().getTeamName4() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Batting$txt_FirstName*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + 
						inning.getBatting_team().getTeamName4() + " \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Batting$img_Flag*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + inning.getBatting_team().getTeamName4() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Batting$txt_FirstName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName4() + " \0", print_writers);
				
				for(int i=1; i<=inning.getBattingCard().size(); i++) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row"+i+"*ACTIVE SET 1 \0", print_writers);
				}
				
				Collections.sort(inning.getBattingCard());
				
				for (BattingCard bc : inning.getBattingCard()) {
					
					rowId = rowId + 1;
					
					switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.OUT:
							omo_num = 0;
							cont_name = "$Players_Dehighlight";
							text_name = "$Data";
							battingSize = battingSize + 1;
							break;
						case CricketUtil.NOT_OUT:
							omo_num = 1;
							cont_name = "$Players_Highlight";
							text_name = "$img_text2";
							battingSize = battingSize + 1;
							break;
						}
					
					if(CricketFunctions.checkImpactPlayer(matchAllData.getEventFile().getEvents(), inning.getInningNumber(), 
							bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$Impact$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
					}
					else if(CricketFunctions.checkImpactPlayerBowler(matchAllData.getEventFile().getEvents(), inning.getInningNumber(), 
							bc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$Impact$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$Impact$Select*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
					}
					
					if(cont_name.equalsIgnoreCase("$Players_Highlight")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$Base$img2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + 
								inning.getBatting_team().getTeamName4() + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + text_name +"*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + 
								inning.getBatting_team().getTeamName4() + " \0", print_writers);
					}else {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//								+ "$Batting$Row" + rowId + cont_name + text_name +"*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + 
//								inning.getBatting_team().getTeamName4() + " \0", print_writers);
					}
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$DataGrp*FUNCTION*Grid*num_row SET " + battingSize + " \0", print_writers);
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$txt_Name*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + " \0", print_writers);
					
					if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$txt_Out*GEOM*TEXT SET " + "" + " \0", print_writers);
					}else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Batting$Row" + rowId + cont_name + "$txt_Out*GEOM*TEXT SET " +"" + " \0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$obj_Divider*ACTIVE SET 0  \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$fig_Runs*GEOM*TEXT SET " + bc.getRuns() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Batting$Row" + rowId + cont_name + "$fig_Out*GEOM*TEXT SET " + bc.getBalls() + " \0", print_writers);
				}
				
				break;
			case "Shift_F2":
				int bowling_size = 1;
				rowId = 1;
				cont_name = "";
				omo_num = 0;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Bowling$LooBase$img2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + 
						inning.getBowling_team().getTeamName4() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Bowling$headerBnd$img1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + 
						inning.getBowling_team().getTeamName4() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Bowling$txt_FirstName*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + 
						inning.getBowling_team().getTeamName4() + " \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Bowling$img_Flag*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + inning.getBowling_team().getTeamName4() + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
						+ "$Bowling$txt_FirstName*GEOM*TEXT SET " + inning.getBowling_team().getTeamName4() + " \0", print_writers);
				
				for(int i=1; i<=inning.getBowlingCard().size(); i++) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row"+(i+1)+"*ACTIVE SET 1 \0", print_writers);
				}
				
				for (BowlingCard boc : inning.getBowlingCard()) {
					if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
						bowling_size=bowling_size + 1;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + bowling_size + " \0", print_writers);
					}
					switch (boc.getStatus().toUpperCase()) {
					case (CricketUtil.OTHER + CricketUtil.BOWLER):
						omo_num = 2;
						cont_name = "$Players_Dehighlight";
						text_name = "$Data";
						break;
					case (CricketUtil.LAST + CricketUtil.BOWLER):
						omo_num = 2;
						cont_name = "$Players_Dehighlight";
						text_name = "$Data";
						break;
					case (CricketUtil.CURRENT + CricketUtil.BOWLER):
						omo_num = 3;
						cont_name = "$Players_Highlight";
						text_name = "$img_text2";
						break;
					}
					
					rowId = rowId + 1;
					
					if(CricketFunctions.checkImpactPlayer(matchAllData.getEventFile().getEvents(), inning.getInningNumber(), 
							boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + rowId + cont_name + "$Impact$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
					}
					else if(CricketFunctions.checkImpactPlayerBowler(matchAllData.getEventFile().getEvents(), inning.getInningNumber(), 
							boc.getPlayerId()).equalsIgnoreCase(CricketUtil.YES)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + rowId + cont_name + "$Impact$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + rowId + cont_name + "$Impact$Select*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
					}
					
					if(cont_name.equalsIgnoreCase("$Players_Highlight")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + rowId + cont_name + "$Base$img2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + 
								inning.getBowling_team().getTeamName4() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + rowId + cont_name + text_name +"*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + 
								inning.getBowling_team().getTeamName4() + " \0", print_writers);
					}else {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
//								+ "$Bowling$Row" + rowId + cont_name + text_name +"*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + 
//								inning.getBowling_team().getTeamName4() + " \0", print_writers);
					}
					
					
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$txt_Name*GEOM*TEXT SET " + boc.getPlayer().getTicker_name() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$fig_Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + " \0", print_writers);

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$fig_Maidens*GEOM*TEXT SET " + boc.getMaidens() + " \0", print_writers);

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$fig_Runs*GEOM*TEXT SET " + boc.getRuns() + " \0", print_writers);

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
							+ "$Bowling$Row" + rowId + cont_name + "$fig_Wickets*GEOM*TEXT SET " + boc.getWickets() + " \0", print_writers);
					
					if(boc.getEconomyRate() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + rowId + cont_name + "$fig_Economy*GEOM*TEXT SET " + boc.getEconomyRate() + " \0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$minis$Side" + WhichSide 
								+ "$Bowling$Row" + rowId + cont_name + "$fig_Economy*GEOM*TEXT SET " + "-" + " \0", print_writers);
					}
					

				}
				break;
			}
			break;
		case Constants.BENGAL_T20:
			switch(whatToProcess) {
			case "Control_Shift_E":
				rowId = 0;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Title"
						+ "$txt_Name*GEOM*TEXT SET "+player.getFull_name()+" \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Title"
						+ "$txt_TeamName*GEOM*TEXT SET "+team.getTeamName1()+" \0", print_writers);
				
				for(BestStats stats : batter_data) {
					rowId++;
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
							+ "$Select_GraphicsType$Griff$Data$"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET "+"0"+" \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
							+ rowId +"$Dehighlight$txt_TeamName*GEOM*TEXT SET "+"v "+stats.getPlayer().getFull_name()+" \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
							+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
							+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+stats.getRuns()+" \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
							+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+stats.getBalls()+" \0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType$Griff$Select_Row*FUNCTION*Omo*vis_con SET "+batter_data.size()+" \0", print_writers);
				break;
			case "Alt_F2":
				rowId = 0;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Title"
						+ "$txt_Name*GEOM*TEXT SET "+player.getFull_name()+" \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Title"
						+ "$txt_TeamName*GEOM*TEXT SET "+team.getTeamName1()+" \0", print_writers);
				
				for(BatBallGriff grif : griff) {
					rowId++;
					if(grif.getMatchNumber().equalsIgnoreCase(matchAllData.getMatch().getMatchFileName().replace(".json", ""))){
						cont_name = "$Highlight";
						omo_num= 1;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +"$Highlight$txt_BatterName*GEOM*TEXT SET "+"v "+grif.getOpponentTeam().getTeamName3()+" \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Base2*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "2/" + team.getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Base3*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "3/" + team.getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Base1*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "1/" + team.getTeamBadge() + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Text1*TEXTURE*IMAGE SET " + Constants.BENGAL_TEXT_PATH + "1/" + team.getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Text2*TEXTURE*IMAGE SET " + Constants.BENGAL_TEXT_PATH + "2/" + team.getTeamBadge() + " \0", print_writers);
						
					}else {
						cont_name = "$Dehighlight";
						omo_num= 0;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +"$Dehighlight$txt_BatterName*GEOM*TEXT SET "+"v "+grif.getOpponentTeam().getTeamName3()+" \0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
							+ "$Select_GraphicsType$Griff$Data$"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET "+omo_num+" \0", print_writers);
					if(grif.getStatus().equalsIgnoreCase("DNB")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+""+" \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ "DNB"+" \0", print_writers);
					}else if(grif.getStatus().equalsIgnoreCase("BALL")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+grif.getWickets()+"-"+grif.getRunsConceded()+" \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ grif.getOversBowled()+" \0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+""+" \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ "DNP"+" \0", print_writers);
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType$Griff$Select_Row*FUNCTION*Omo*vis_con SET "+griff.size()+" \0", print_writers);
				
				break;
			case "Control_Shift_F":
				rowId = 0;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Title"
						+ "$txt_Name*GEOM*TEXT SET "+player.getFull_name()+" \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Title"
						+ "$txt_TeamName*GEOM*TEXT SET "+team.getTeamName1()+" \0", print_writers);
				for(BestStats stats : bowler_data) {
					rowId++;
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
							+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
							+ rowId+"$Dehighlight$txt_TeamName*GEOM*TEXT SET "+"v "+stats.getPlayer().getFull_name()+" \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
							+ "$Select_GraphicsType$Griff$Data$"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET "+"0"+" \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
							+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+stats.getRuns()+" \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
							+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ stats.getBalls()+" \0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType$Griff$Select_Row*FUNCTION*Omo*vis_con SET "+bowler_data.size()+" \0", print_writers);
				
				break;
			case "Alt_F1":
				rowId = 0;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Title"
						+ "$txt_Name*GEOM*TEXT SET "+player.getFull_name()+" \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Title"
						+ "$txt_TeamName*GEOM*TEXT SET "+team.getTeamName1()+" \0", print_writers);
				
				
				for(BatBallGriff gf :CricketFunctions.getBatBallGriff(player, CricketUtil.BATSMAN, player.getPlayerId(), 
						team, headToHead, matchAllData)){
					rowId++;
					if(gf.getMatchNumber().equalsIgnoreCase(matchAllData.getMatch().getMatchFileName().replace(".json", ""))){
						cont_name = "$Highlight";
						omo_num= 1;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_BatterName*GEOM*TEXT SET " + "v " + gf.getOpponentTeam().getTeamName3() + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Base2*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "2/" + team.getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Base3*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "3/" + team.getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Base1*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "1/" + team.getTeamBadge() + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Text1*TEXTURE*IMAGE SET " + Constants.BENGAL_TEXT_PATH + "1/" + team.getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Griff$Data$Rows$Select_Row$" + rowId  +"$GriffData$Select_Row_Type$" + 
								cont_name + "$img_Text2*TEXTURE*IMAGE SET " + Constants.BENGAL_TEXT_PATH + "2/" + team.getTeamBadge() + " \0", print_writers);
						
					}else {
						cont_name = "$Dehighlight";
						omo_num= 0;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_BatterName*GEOM*TEXT SET " + "v " + gf.getOpponentTeam().getTeamName3() + " \0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
							+ "$Select_GraphicsType$Griff$Data$"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET " + omo_num + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
							+ "$Select_GraphicsType$Griff$Select_Row*FUNCTION*Omo*vis_con SET " + rowId + " \0", print_writers);
					
					if(gf.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
						if(gf.getBallsFaced() != 0) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
									+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+gf.getRuns()+" \0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
									+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ gf.getBallsFaced()+" \0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
									+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+""+" \0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
									+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ "DNB"+" \0", print_writers);
						}
					}else if(gf.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Star*ACTIVE SET 1 \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+gf.getRuns()+"* \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ gf.getBallsFaced()+" \0", print_writers);
					}else if(gf.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+gf.getRuns()+" \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ gf.getBallsFaced()+" \0", print_writers);
					}else {
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
								+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ gf.getStatus()+" \0", print_writers);
					}
				}
				
//				for(BatBallGriff grif : griff) {
//					rowId++;
//					if(griff.get(rowId-1).getMatchNumber().equalsIgnoreCase(matchAllData.getMatch().getMatchFileName().replace(".json", ""))){
//						cont_name = "$Highlight";
//						omo_num= 1;
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_BatterName*GEOM*TEXT SET "+"v "+griff.get(rowId-1).getOpponentTeam().getTeamBadge()+" \0", print_writers);
//					}else {
//						cont_name = "$Dehighlight";
//						omo_num= 0;
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_TeamName*GEOM*TEXT SET "+"v "+griff.get(rowId-1).getOpponentTeam().getTeamBadge()+" \0", print_writers);
//					}
//					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
//							+ "$Select_GraphicsType$Griff$Data$"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET "+omo_num+" \0", print_writers);
//					
//					if(griff.get(rowId-1).getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
//						if(griff.get(rowId-1).getHow_out() != null && !griff.get(rowId-1).getHow_out().trim().isEmpty() && 
//								griff.get(rowId-1).getHow_out().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//									+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+griff.get(rowId-1).getRuns()+" \0", print_writers);
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//									+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ griff.get(rowId).getBallsFaced()+" \0", print_writers);
//						}else {
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//									+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+""+" \0", print_writers);
//							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//									+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ "DNB"+" \0", print_writers);
//						}
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
//						
//					}else if(griff.get(rowId-1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
//						
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+griff.get(rowId-1).getRuns()+" \0", print_writers);
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ griff.get(rowId-1).getBallsFaced()+" \0", print_writers);
//					}else if(griff.get(rowId-1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Star*ACTIVE SET 1 \0", print_writers);
//						
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+griff.get(rowId-1).getRuns()+" \0", print_writers);
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ griff.get(rowId-1).getBallsFaced()+" \0", print_writers);
//					}else {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Star*ACTIVE SET 0 \0", print_writers);
//						
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Runs*GEOM*TEXT SET "+""+" \0", print_writers);
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Griff$Rows$"
//								+ rowId +cont_name+"$txt_Balls*GEOM*TEXT SET "+ "DNP"+" \0", print_writers);
//					}
//				}
//
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
//						+ "$Select_GraphicsType$Griff$Select_Row*FUNCTION*Omo*vis_con SET "+griff.size()+" \0", print_writers);
				break;
			case "Alt_F7":
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
						+ "$Rows$TitleGrp$PointData$txt_Played*GEOM*TEXT SET P \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
						+ "$Rows$TitleGrp$PointData$txt_Won*GEOM*TEXT SET W \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
						+ "$Rows$TitleGrp$PointData$txt_Points*GEOM*TEXT SET PTS \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
						+ "$Rows$TitleGrp$PointData$txt_NRR*GEOM*TEXT SET NRR \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
						+ "$Title$txt_TeamName*GEOM*TEXT SET " + "POINTS TABLE" + " \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
						+ "$Rows$Select_Row*FUNCTION*Grid*num_row SET 7\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
						+ "$Rows$Select_Row*FUNCTION*Grid*row_offset SET 28.5\0", print_writers);
				
				rowId = 0;
				for(int i=0; i<=leagueTable.getLeagueTeams().size()-1;i++) {
					
					rowId = rowId + 1;
					
					if(matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())  
							|| matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
								+ "$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
						containerName = "$Highlight";
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
								+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Base$img_Base2*TEXTURE*IMAGE SET " + 
								Constants.BENGAL_BASE_PATH + "2/" + leagueTable.getLeagueTeams().get(i).getTeamName() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
								+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Base$img_Base3*TEXTURE*IMAGE SET " + 
								Constants.BENGAL_BASE_PATH + "3/" + leagueTable.getLeagueTeams().get(i).getTeamName() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
								+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Base$img_Base1*TEXTURE*IMAGE SET " + 
								Constants.BENGAL_BASE_PATH + "1/" + leagueTable.getLeagueTeams().get(i).getTeamName() + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
								+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Data$img_Text1*TEXTURE*IMAGE SET " + 
								Constants.BENGAL_TEXT_PATH + "1/" + leagueTable.getLeagueTeams().get(i).getTeamName() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
								+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Data$img_Text2*TEXTURE*IMAGE SET " + 
								Constants.BENGAL_TEXT_PATH + "2/" + leagueTable.getLeagueTeams().get(i).getTeamName() + " \0", print_writers);
						
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
								+ "$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
						containerName = "$Dehighlight";
					}
					
					if(leagueTable.getLeagueTeams().get(i).getQualifiedStatus().trim().equalsIgnoreCase("")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
								+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Data$txt_Position*GEOM*TEXT SET " + (rowId) + " \0", print_writers);
					}else if(leagueTable.getLeagueTeams().get(i).getQualifiedStatus().trim().equalsIgnoreCase("Q")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
								+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Data$txt_Position*GEOM*TEXT SET " + "Q" + " \0", print_writers);
					}
					
					for(Team tm : Teams) {
						if(tm.getTeamName4().equalsIgnoreCase(leagueTable.getLeagueTeams().get(i).getTeamName())) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
									+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Data$txt_Name*GEOM*TEXT SET " + 
									tm.getTeamName3() + " \0", print_writers);
						}
					}

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
							+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Data$txt_Played*GEOM*TEXT SET " + 
							leagueTable.getLeagueTeams().get(i).getPlayed() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
							+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Data$txt_Won*GEOM*TEXT SET " + 
							leagueTable.getLeagueTeams().get(i).getWon() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
							+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Data$txt_Points*GEOM*TEXT SET " + 
							leagueTable.getLeagueTeams().get(i).getPoints() + " \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$Side" + WhichSide + "$Standings$Data"
							+ "$Rows$" + rowId + "$Select_Highlight" + containerName + "$Data$txt_NRR*GEOM*TEXT SET " + 
							String.format("%.2f", leagueTable.getLeagueTeams().get(i).getNetRunRate()) + " \0", print_writers);
				}
				break;
			case "Shift_F1":
				int battingSize=0,concussedId=0;
				cont_name = "";
				omo_num = 0;
				rowId = 0;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 0 \0", print_writers);

				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType$Batting_Card$Data$Title$Out$In$txt_TeamName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName3() + " \0", print_writers);
				
				Collections.sort(inning.getBattingCard());
				
				for (BattingCard bc : inning.getBattingCard()) {
					
					rowId = rowId + 1;
					
					switch (bc.getStatus().toUpperCase()) {
					case CricketUtil.STILL_TO_BAT:
						if (bc.getHowOut() != null) {
							if (bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)
									|| bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.ABSENT_HURT)) {

								battingSize += 1;
								
								if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId()).isEmpty()) {
									switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId())) {
									case "IMP_IN":
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
												+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
												"$Data$Name$Select_Impact$IMPACT$IMP*GEOM*TEXT SET IMP\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
												+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
												"$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
										break;
									case "IMP_OUT":
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
												+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
												"$Data$Name$Select_Impact$SUBSTITUTE$IMP*GEOM*TEXT SET SUB\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
												+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
												"$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
										break;
									case "CON_IN":
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
												+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
												"$Data$Name$Select_Impact$IMPACT$IMP*GEOM*TEXT SET CON\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
												+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
												"$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
										break;
									case "CON_OUT":
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
												+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
												"$Data$Name$Select_Impact$SUBSTITUTE$IMP*GEOM*TEXT SET CON\0", print_writers);
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
												+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
												"$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
										break;	
									}
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
											+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
											"$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
								}
								
								if(concussedId == bc.getPlayerId()) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
											+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
											"$Data$Name$Select_Impact$IMPACT$IMP*GEOM*TEXT SET CON\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
											+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type$Out" + 
											"$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								}
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId  +"$Select_Row_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row*FUNCTION*Omo*vis_con SET " + battingSize + " \0", print_writers);
								

								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId  +"$BattingData$Select_Row_Type$" + 
										cont_name + "$Data$Name$txt_BatterName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + " \0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$BattingData$Select_Row_Type" + 
										cont_name + "$txt_Runs*GEOM*TEXT SET " + bc.getRuns() + " \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$BattingData$Select_Row_Type" + 
										cont_name + "$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + " \0", print_writers);

							}
						}
						break;
					default:
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.OUT:
							omo_num = 0;
							cont_name = "$Out";
							text_name = "$Data";
							battingSize = battingSize + 1;
							break;
						case CricketUtil.NOT_OUT:
							omo_num = 1;
							cont_name = "$Not_Out";
							text_name = "$img_Text1";
							battingSize = battingSize + 1;
							
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId  +"$BattingData$Select_Row_Type$" + 
										cont_name + "$img_Base2*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "2/" + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId  +"$BattingData$Select_Row_Type$" + 
										cont_name + "$img_Base3*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "3/" + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId  +"$BattingData$Select_Row_Type$" + 
										cont_name + "$img_Base1*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "1/" + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId  +"$BattingData$Select_Row_Type$" + 
										cont_name + "$img_Text1*TEXTURE*IMAGE SET " + Constants.BENGAL_TEXT_PATH + "1/" + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId  +"$BattingData$Select_Row_Type$" + 
										cont_name + "$img_Text2*TEXTURE*IMAGE SET " + Constants.BENGAL_TEXT_PATH + "2/" + inning.getBatting_team().getTeamBadge() + " \0", print_writers);
							break;
						}
						
						
						if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId()).isEmpty()) {
							switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), bc.getPlayerId())) {
							case "IMP_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact$IMPACT$IMP*GEOM*TEXT SET IMP \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
								break;
							case "IMP_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact$SUBSTITUTE$IMP*GEOM*TEXT SET SUB \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
								break;
							case "CON_IN":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact$IMPACT$IMP*GEOM*TEXT SET CON \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
								break;
							case "CON_OUT":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact$SUBSTITUTE$IMP*GEOM*TEXT SET CON \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
								break;	
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
									cont_name + "$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
						}
						
						if(bc.getHowOut() != null && !bc.getHowOut().isEmpty()) {
							if(bc.getHowOut().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
								concussedId = bc.getConcussionPlayerId();
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact$SUBSTITUTE$IMP*GEOM*TEXT SET CON \0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
										+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
										cont_name + "$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
							}
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId  +"$Select_Row_Type*FUNCTION*Omo*vis_con SET " + omo_num + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row*FUNCTION*Omo*vis_con SET " + battingSize + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId  +"$BattingData$Select_Row_Type$" + 
								cont_name + "$Data$Name$txt_BatterName*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$BattingData$Select_Row_Type" + 
								cont_name + "$txt_Runs*GEOM*TEXT SET " + bc.getRuns() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$BattingData$Select_Row_Type" + 
								cont_name + "$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + " \0", print_writers);
						
						if(concussedId == bc.getPlayerId()) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
									cont_name + "$Data$Name$Select_Impact$IMPACT$IMP*GEOM*TEXT SET CON \0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Batting_Card$Data$Rows$Select_Row$" + rowId +"$Select_Row_Type" + 
									cont_name + "$Data$Name$Select_Impact*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
						}
						break;
					}
				}
				
				break;
			case "Shift_F2":
				int bowling_size = 1;
				rowId = 0;
				cont_name = "";
				omo_num = 0;
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Bowling_Card$Title$txt_TeamName*GEOM*TEXT SET " + inning.getBowling_team().getTeamName3() + " \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
						+ "$Bowling_Card$Rows$Select_Row*FUNCTION*Omo*vis_con SET "+inning.getBowlingCard().size()+" \0", print_writers);
			
				
				
				for (BowlingCard boc : inning.getBowlingCard()) {
					rowId = rowId + 1;
					if(boc.getRuns() > 0 || ((boc.getOvers()*6)+boc.getBalls()) > 0) {
						bowling_size=bowling_size + 1;
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
//								+ "$Bowling$DataGrp*FUNCTION*Grid*num_row SET " + bowling_size + " \0", print_writers);
					}

					switch (boc.getStatus().toUpperCase()) {
					case (CricketUtil.OTHER + CricketUtil.BOWLER):case (CricketUtil.LAST + CricketUtil.BOWLER):
						omo_num = 0;
						cont_name = "$Out";
						text_name = "$Data";
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId  +"$BowlingData$Select_Row_Type" +cont_name +text_name+"$Name$txt_Name*GEOM*TEXT SET " +  boc.getPlayer().getTicker_name() + " \0", print_writers);
		
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type" + cont_name +text_name+"$Overs$txt_Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+cont_name +text_name+ "$Figures$txt_Figures*GEOM*TEXT SET " + boc.getWickets() +"-"+ boc.getRuns() + " \0", print_writers);

						break;
					
					case (CricketUtil.CURRENT + CricketUtil.BOWLER):
						omo_num = 1;
						cont_name = "$Not_Out";
						text_name = "$img_Text1";
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId  +"$BowlingData$Select_Row_Type$" + 
								cont_name + "$img_Base2*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "2/" + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId  +"$BowlingData$Select_Row_Type$" + 
								cont_name + "$img_Base3*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "3/" + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId  +"$BowlingData$Select_Row_Type$" + 
								cont_name + "$img_Base1*TEXTURE*IMAGE SET " + Constants.BENGAL_BASE_PATH + "1/" + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId  +"$BowlingData$Select_Row_Type$" + 
								cont_name + "$img_Text1*TEXTURE*IMAGE SET " + Constants.BENGAL_TEXT_PATH + "1/" + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId  +"$BowlingData$Select_Row_Type$" + 
								cont_name + "$img_Text2*TEXTURE*IMAGE SET " + Constants.BENGAL_TEXT_PATH + "2/" + inning.getBowling_team().getTeamBadge() + " \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId  +"$BowlingData$Select_Row_Type" +cont_name +"$img_Text1$Name$txt_Name*GEOM*TEXT SET " +  boc.getPlayer().getTicker_name() + " \0", print_writers);
		
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type" + cont_name +"$img_Text2$Overs$txt_Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) + " \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+cont_name + "$img_Text2$Figures$txt_Figures*GEOM*TEXT SET " + boc.getWickets()+"-"+  boc.getRuns() + " \0", print_writers);

						break;
					}
					
					if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId()).isEmpty()) {
						switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), boc.getPlayerId())) {
						case "IMP_IN":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+ cont_name +text_name+ 
									"$Name$Select_Impact$IMPACT$IMP*GEOM*TEXT SET IMP \0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+ cont_name +text_name+ 
									"$Name$Select_Impact*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
							break;
						case "IMP_OUT":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+ cont_name +text_name+ 
									"$Name$Select_Impact$SUBSTITUTE$IMP*GEOM*TEXT SET SUB \0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+ cont_name +text_name+ 
									"$Name$Select_Impact*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
							break;
						case "CON_IN":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+ cont_name +text_name+ 
									"$Name$Select_Impact$IMPACT$IMP*GEOM*TEXT SET CON \0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+ cont_name +text_name+ 
									"$Name$Select_Impact*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
							break;
						case "CON_OUT":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+ cont_name +text_name+ 
									"$Name$Select_Impact$SUBSTITUTE$IMP*GEOM*TEXT SET CON \0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
									+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+ cont_name +text_name+ 
									"$Name$Select_Impact*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
							break;	
						}
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
								+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$" + rowId +"$BowlingData$Select_Row_Type"+ cont_name +text_name+ 
								"$Name$Select_Impact*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Minis$All_Graphics$Side" + WhichSide 
							+ "$Select_GraphicsType$Bowling_Card$Data$Rows$Select_Row$"+rowId+"$Select_Row_Type*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0", print_writers);

				}
				break;
			}
			break;
		}
		
			
		return Constants.OK;
	}
	public String getBowlerRunsOverbyOver(int inning,List<Event> event, MatchAllData matchAllData) {
		
		int bowlerId = 0,runs = 0,wicket = 0;
		String name = "";
		boolean bowler_found = false;
		
		if ((matchAllData.getEventFile().getEvents() != null) && (matchAllData.getEventFile().getEvents().size() > 0)) {
			for(Event evnt: matchAllData.getEventFile().getEvents()) {
				if(evnt.getEventInningNumber() == inning) {
					if(evnt.getEventExtra() != null) {
						if(evnt.getEventExtra().equalsIgnoreCase("TAPE")) {
							bowlerId = evnt.getEventBowlerNo();
							bowler_found = true;
							runs = 0;
							wicket = 0;
						}
					}
					if(bowler_found && evnt.getEventBowlerNo() == bowlerId) {
						switch(evnt.getEventType()) {
						case CricketUtil.ONE : case CricketUtil.TWO: case CricketUtil.THREE:  case CricketUtil.FIVE : case CricketUtil.DOT:
		            	case CricketUtil.FOUR: case CricketUtil.SIX: case CricketUtil.NINE:
		            		runs += evnt.getEventRuns();
		                    break;
		            	case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY:
		            		runs += evnt.getEventRuns();
		                    break;

		            	case CricketUtil.LOG_WICKET:
		                    if (evnt.getEventRuns() > 0)
		                    {
		                    	runs += evnt.getEventRuns();
		                    }
		                    wicket += 1;
		                    break;

		            	case CricketUtil.LOG_ANY_BALL:
		            		runs += evnt.getEventRuns();
		                    if (evnt.getEventExtra() != null)
		                    {
		                    	runs += evnt.getEventExtraRuns();
		                    }
		                    if (evnt.getEventSubExtra() != null)
		                    {
		                    	runs += evnt.getEventSubExtraRuns();
		                    }
		                    break;										
						}
					}else if(evnt.getEventBowlerNo() != bowlerId && evnt.getEventBowlerNo() != 0) {
						bowler_found = false;
					}
				}
			}
		}
		
		for (BowlingCard boc : matchAllData.getMatch().getInning().get(inning - 1).getBowlingCard()) {
			if(boc.getPlayerId() == bowlerId) {
				name = boc.getPlayer().getTicker_name();
			}
		}
		
		return name + "," + runs + "," + wicket;
		
	}

	public String populateLofLeaderBoard(int WhichSide, String whatToProcess, MatchAllData matchAllData) throws Exception 
	{
		this_series = new ArrayList<Tournament>();
		if(whatToProcess.split(",")[3].equalsIgnoreCase("WITHOUT_CURRENT")) {
			this_series = past_tournament_stats;
		}else if(whatToProcess.split(",")[3].equalsIgnoreCase("WITH_CURRENT")) {
			this_series = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead, cricketService, 
					matchAllData, past_tournament_stats);
		}
		FirstPlayerId = Integer.valueOf((whatToProcess.split(",")[2]).split("_")[1]);
		if(FirstPlayerId <= 0) {
			return "populateLofLeaderBoard: Player Id NOT found [" + FirstPlayerId + "]";
		}
		
		switch (whatToProcess.split(",")[0]) {
		case "Alt_Shift_K":
			Collections.sort(this_series,new CricketFunctions.BatsmenMostRunComparator());
			break;
		case "Alt_Shift_X":
			Collections.sort(this_series,new CricketFunctions.BowlerWicketsComparator());
			break;
		case "Alt_Shift_T":
			Collections.sort(this_series,new CricketFunctions.BatsmanFoursComparator());
			break;
		case "Alt_Shift_V":
			Collections.sort(this_series,new CricketFunctions.BatsmanSixesComparator());
			break;
		}
		
		return T20MumbaiLeaderBoardBody(WhichSide, whatToProcess.split(",")[0], matchAllData);
	}
	private String T20MumbaiLeaderBoardBody(int WhichSide, String whatToProcess, MatchAllData matchAllData) {
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$Veil*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Header$Cap$CapIcon*ACTIVE SET " + 
				(whatToProcess.equalsIgnoreCase("Alt_Shift_K") || whatToProcess.equalsIgnoreCase("Alt_Shift_X") ? "1" : "0") + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Header$Cap$CapIcon*TEXTURE*IMAGE SET " 
				+ "IMAGE*/T20/Assets/Images/Textures/" + (whatToProcess.equalsIgnoreCase("Alt_Shift_K") ? "orangeCap" : "PurpleCap") + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Header$Anim$Round*GEOM*TEXT SET MOST\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Header$Anim$Round1*GEOM*TEXT SET " +
				(whatToProcess.equalsIgnoreCase("Alt_Shift_K") ? "RUNS" : whatToProcess.equalsIgnoreCase("Alt_Shift_X") ? "WICKETS" : 
					whatToProcess.equalsIgnoreCase("Alt_Shift_T") ? "FOURS" : "SIXES")+ "\0", print_writers);
		
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$PlayerName$TitleMS$FirstName"
				+ "*GEOM*TEXT SET " + matchAllData.getSetup().getTournament() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$PlayerName$TitleMS$LastName"
				+ "*GEOM*TEXT SET \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Player$ImagePlayer$Title$txTitle"
				+ "*GEOM*TEXT SET \0", print_writers);
		
		rowId = 1;
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + "$Title$Data$Title$Title"
				+ "*GEOM*TEXT SET \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + "$Title$Data$Matches"
				+ "$Value*GEOM*TEXT SET M\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + "$Title$Data$Wickets"
				+ "$Value*GEOM*TEXT SET " + (whatToProcess.equalsIgnoreCase("Alt_Shift_K") ? "R" : whatToProcess.equalsIgnoreCase("Alt_Shift_X") ? "W" : 
				whatToProcess.equalsIgnoreCase("Alt_Shift_T") ? "4s" : "6s") + "\0", print_writers);
		
		for(int i = 0; i <= this_series.size() - 1 ; i++) {
			rowId++;
			if(rowId <= 6) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" 
						+ rowId + "$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				if(this_series.get(i).getPlayerId() == FirstPlayerId) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" 
							+ rowId + "$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Player$TextureColour$TeamLogo"
							+ "*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_TEAMLOGO + CricketFunctions.whichLogo(whatToProcess, Teams.
							get(this_series.get(i).getPlayer().getTeamId() - 1).getTeamBadge()) + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Player$ImagePlayer$Headshot"
							+ "*TEXTURE*IMAGE SET " + (config.getPrimaryIpAddress().equalsIgnoreCase("LOCALHOST") ? 
							   Constants.T20_MUMBAI_PHOTO_PATH : "\\\\" + config.getPrimaryIpAddress() + Constants.T20_MUMBAI_PHOTO_PATH_NETWORK) 
							+ Constants.LEFT_1024 + Teams.get(this_series.get(i).getPlayer().getTeamId() - 1).getTeamName4() + "\\\\" 
							+ this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
				}
				
				for(int j=0; j<2; j++) {
					if(j==0) {
						containerName ="$Highlight";
					}else {
						containerName ="$Team";
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
							containerName + "$Pos$Position*GEOM*TEXT SET " + (i+1) + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
							containerName + "$Pos$Suffix*GEOM*TEXT SET \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
							containerName + "$Name_Stat$Name$FirstName*GEOM*TEXT SET " + this_series.get(i).getPlayer().getAbbrv_Name() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
							containerName + "$Name_Stat$Name$LastName*GEOM*TEXT SET \0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
							containerName + "$Name_Stat$stat$Stat1*GEOM*TEXT SET " + Teams.get(this_series.get(i).getPlayer().getTeamId() - 1).getTeamName4() 
							+ "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
							containerName + "$Matches$Value*GEOM*TEXT SET " + this_series.get(i).getMatches() + "\0", print_writers);
					
					switch(whatToProcess) {
					case "Alt_Shift_K":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
								containerName + "$Name_Stat$stat$Stat2*GEOM*TEXT SET | S/R " + CricketFunctions.generateStrikeRate(this_series.get(i).getRuns(), 
										this_series.get(i).getBallsFaced(), 0) + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
								containerName + "$Wickets$Value*GEOM*TEXT SET " + this_series.get(i).getRuns() + "\0", print_writers);
						break;
					case "Alt_Shift_X":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
								containerName + "$Name_Stat$stat$Stat2*GEOM*TEXT SET | ECON " + CricketFunctions.getEconomy(this_series.get(i).getRunsConceded(), 
										this_series.get(i).getBallsBowled(), 2, "-") + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
								containerName + "$Wickets$Value*GEOM*TEXT SET " + this_series.get(i).getWickets() + "\0", print_writers);
						break;
					case "Alt_Shift_T":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
								containerName + "$Name_Stat$stat$Stat2*GEOM*TEXT SET \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
								containerName + "$Wickets$Value*GEOM*TEXT SET " + this_series.get(i).getFours() + "\0", print_writers);
						break;
					case "Alt_Shift_V":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
								containerName + "$Name_Stat$stat$Stat2*GEOM*TEXT SET \0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$OTS_Leaderboard$Body$CurrentLeader$Stats$Data$Row" + rowId + 
								containerName + "$Wickets$Value*GEOM*TEXT SET " + this_series.get(i).getSixes() + "\0", print_writers);
						break;
					}
				}
			}
		}
		return Constants.OK;
	}
	
	public String populatePlayerProfile(int WhichSide, String whatToProcess, MatchAllData matchAllData, int WhichInning) throws Exception 
	{
		FirstPlayerId = Integer.valueOf(whatToProcess.split(",")[2]);
		WhichProfile = whatToProcess.split(",")[3];
		
		int k =0;
		String best = "-";
		
		if(FirstPlayerId <= 0 || WhichProfile == null) {
			return "populatePlayerProfile: Player Id NOT found [" + FirstPlayerId + "]";
		}
		
		player = CricketFunctions.getPlayerFromMatchData(FirstPlayerId, matchAllData);
		if(player == null) {
			return "populatePlayerProfile: Player id [" + whatToProcess.split(",")[2] + "] from database is returning NULL";
		}
		
		if(WhichProfile.equalsIgnoreCase("MCA T20s") || WhichProfile.equalsIgnoreCase("DT20")) {
			statsType = statsTypes.stream().filter(st -> st.getStats_short_name().equalsIgnoreCase(WhichProfile)).findAny().orElse(null);
			if(statsType == null) {
				return "InfoBarPlayerProfile: Stats Type not found for profile [" + WhichProfile + "]";
			}
			stat = statistics.stream().filter(st -> st.getPlayer_id() == FirstPlayerId && statsType.getStats_id() == st.getStats_type_id()).findAny().orElse(null);
			if(stat == null) {
				return "InfoBarPlayerProfile: Stats not found for Player Id [" + FirstPlayerId + "]";
			}
			
			statsType = statsTypes.stream().filter(st -> st.getStats_short_name().equalsIgnoreCase("DT20")).findAny().orElse(null);
			stat.setStats_type(statsType);
			
			stat = CricketFunctions.updateTournamentWithH2h(stat, headToHead, matchAllData, CricketUtil.FULL);
			stat = CricketFunctions.updateStatisticsWithMatchData(stat, matchAllData, CricketUtil.FULL);
			
		}else if(WhichProfile.equalsIgnoreCase("THIS_SERIES")) {
			this_series = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead, cricketService, matchAllData, past_tournament_stats);
			tournament = this_series.stream().filter(st -> st.getPlayerId() == FirstPlayerId).findAny().orElse(null);
			
			for(Tournament tourn : this_series) {
				for(BestStats bs : tourn.getBatsman_best_Stats()) {
					top_batsman_beststats.add(bs);
				}
				for(BestStats bfig : tourn.getBowler_best_Stats()) {
					top_bowler_beststats.add(bfig);
				}
			}
			
			Collections.sort(top_batsman_beststats, new CricketFunctions.PlayerBestStatsComparator());
			Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
			
			switch(whatToProcess.split(",")[0]) {
			case "Alt_Shift_N":
				for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
					if(top_batsman_beststats.get(j).getPlayerId() == FirstPlayerId) {
						if(k == 0) {
							k += 1;
							if(top_batsman_beststats.get(j).getBestEquation() % 2 == 0) {
								if(top_batsman_beststats.get(j).getBestEquation()/2 == 0) {
									best = "-";
								}else {
									best = String.valueOf(top_batsman_beststats.get(j).getBestEquation()/2);
								}
							}else {
								best = (top_batsman_beststats.get(j).getBestEquation()-1) / 2 + "*";
							}
							break;
						}
					}else {
						best = "-";
					}
				}
				break;
			case "Alt_Shift_M":
				for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
					if(top_bowler_beststats.get(j).getPlayerId() == FirstPlayerId) {
						if(k == 1) {
							break;
						}
						if(k == 0) {
							k += 1;
							if(top_bowler_beststats.get(j).getBestEquation() % 1000 > 0) {
								best = ((top_bowler_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (top_bowler_beststats.get(j).getBestEquation() % 1000));
								break;
							}
							else if(top_bowler_beststats.get(j).getBestEquation() % 1000 < 0) {
								best = (top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(top_bowler_beststats.get(j).getBestEquation());
								break;
							}
							else if(top_bowler_beststats.get(j).getBestEquation() != 0) {
								if(top_bowler_beststats.get(j).getBestEquation() % 1000 == 0) {
									best = (top_bowler_beststats.get(j).getBestEquation() / 1000) + "-" + "0";
									break;
								}
							}
							break;
						}
					}else if(top_bowler_beststats.get(j).getPlayerId() != FirstPlayerId) {
						best = "-";
					}
				}
				break;
			}
			
		}else {
			statsType = statsTypes.stream().filter(st -> st.getStats_short_name().equalsIgnoreCase(WhichProfile)).findAny().orElse(null);
			if(statsType == null) {
				return "InfoBarPlayerProfile: Stats Type not found for profile [" + WhichProfile + "]";
			}
			stat = statistics.stream().filter(st -> st.getPlayer_id() == FirstPlayerId && statsType.getStats_id() == st.getStats_type_id()).findAny().orElse(null);
			if(stat == null) {
				return "InfoBarPlayerProfile: Stats not found for Player Id [" + FirstPlayerId + "]";
			}
		}
		
		team = Teams.stream().filter(tm -> tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
		if(team == null) {
			return "populatePlayerProfile: Team id [" + player.getTeamId() + "] from database is returning NULL";
		}
		return T20MumbaiPlayerBioBody(WhichSide, whatToProcess, matchAllData, WhichInning, best);
	}
	private String T20MumbaiPlayerBioBody(int WhichSide, String whatToProcess, MatchAllData matchAllData,int whichInning, String best) {
		switch (whatToProcess.split(",")[0]) {
		case "Alt_Shift_N":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row1$Title*GEOM*TEXT SET " +
					"MATCHES" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row2$Title*GEOM*TEXT SET " + 
					"RUNS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row3$Title*GEOM*TEXT SET " + 
					"S/R" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row4$Title*GEOM*TEXT SET " + 
					"50s/100s" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row5$Title*GEOM*TEXT SET " + 
					"BEST" + "\0", print_writers);
			if(WhichProfile.equalsIgnoreCase("THIS_SERIES")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row1$Value*GEOM*TEXT SET " + 
						(tournament.getMatches() != 0 ? tournament.getMatches() : "-") + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row2$Value*GEOM*TEXT SET " + 
						(tournament.getRuns() != 0 ? tournament.getRuns() : "-")+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row3$Value*GEOM*TEXT SET " + 
						CricketFunctions.generateStrikeRate(tournament.getRuns(), tournament.getBallsFaced(), 0)+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row4$Value*GEOM*TEXT SET " + 
						(tournament.getFifty() == 0 && tournament.getHundreds() == 0 ? "-" : (tournament.getFifty() != 0 ? tournament.getFifty() : "-") 
								+ "/" + (tournament.getHundreds() != 0 ? tournament.getHundreds() : "-")) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row5$Value*GEOM*TEXT SET " + 
						best + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row1$Value*GEOM*TEXT SET " + 
						(stat.getMatches() != 0 ? stat.getMatches() : "-") + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row2$Value*GEOM*TEXT SET " + 
						(stat.getRuns() != 0 ? stat.getRuns() : "-")+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row3$Value*GEOM*TEXT SET " + 
						CricketFunctions.generateStrikeRate(stat.getRuns(), stat.getBalls_faced(), 0)+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row4$Value*GEOM*TEXT SET " + 
						(stat.getFifties() == 0 && stat.getHundreds() == 0 ? "-" : (stat.getFifties() != 0 ? stat.getFifties() : "-") 
								+ "/" + (stat.getHundreds() != 0 ? stat.getHundreds() : "-")) + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row5$Value*GEOM*TEXT SET " + 
						(stat.getBest_score() != null ? stat.getBest_score() : "-")+ "\0", print_writers);	
			}
			break;
		case "Alt_Shift_M":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row1$Title*GEOM*TEXT SET " +
					"MATCHES" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row2$Title*GEOM*TEXT SET " + 
					"WICKETS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row3$Title*GEOM*TEXT SET " + 
					"ECONOMY" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row4$Title*GEOM*TEXT SET " + 
					"3WI" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row5$Title*GEOM*TEXT SET " + 
					"BEST" + "\0", print_writers);
			
			if(WhichProfile.equalsIgnoreCase("THIS_SERIES")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row1$Value*GEOM*TEXT SET " + 
						(tournament.getMatches() != 0 ? tournament.getMatches() : "-") + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row2$Value*GEOM*TEXT SET " + 
						(tournament.getWickets() != 0 ? tournament.getWickets() : "-")+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row3$Value*GEOM*TEXT SET " + 
						CricketFunctions.getEconomy(tournament.getRunsConceded(), tournament.getBallsBowled(), 2, "-") + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row4$Value*GEOM*TEXT SET " + 
						(tournament.getThreeWicketHaul() != 0 ? tournament.getThreeWicketHaul() : "-")  + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row5$Value*GEOM*TEXT SET " + 
						(best != null ? best : "-")+ "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row1$Value*GEOM*TEXT SET " + 
						(stat.getMatches() != 0 ? stat.getMatches() : "-") + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row2$Value*GEOM*TEXT SET " + 
						(stat.getWickets() != 0 ? stat.getWickets() : "-")+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row3$Value*GEOM*TEXT SET " + 
						CricketFunctions.getEconomy(stat.getRuns_conceded(), stat.getBalls_bowled(), 2, "-") + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row4$Value*GEOM*TEXT SET " + 
						(stat.getPlus_3() != 0 ? stat.getPlus_3() : "-")  + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Stats$Data$Row5$Value*GEOM*TEXT SET " + 
						(stat.getBest_figures() != null ? stat.getBest_figures() : "-")+ "\0", print_writers);	
			}
			break;
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Header$Logo$BGLogo*TEXTURE*IMAGE SET " + Constants.T20_MUMBAI_TEAMLOGO 
				+ CricketFunctions.whichLogo(whatToProcess, team.getTeamBadge()) + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Player$TextureColour$TeamLogo*TEXTURE*IMAGE SET " 
				+ Constants.T20_MUMBAI_TEAMLOGO + CricketFunctions.whichLogo(whatToProcess, team.getTeamBadge()) + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Header$Anim$Round*GEOM*TEXT SET " + team.getTeamName1() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader*ACTIVE SET 1\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$Top3*ACTIVE SET 0\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$AllSplits*ACTIVE SET 0\0", print_writers);

		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName"
				+ "$TitleMS$FirstName*GEOM*TEXT SET " + player.getFirstname() +"\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName"
				+ "$TitleMS$LastName*GEOM*TEXT SET "+ (player.getSurname()==null ? "" : player.getSurname()) +"\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$TeamColour*TEXTURE*IMAGE SET " 
				+ Constants.T20_MUMBAI_TEAMCOLOUR_GRADIENTS + team.getTeamBadge() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName$TitleMS$FirstName*TEXTURE*IMAGE SET " 
				+ Constants.T20_MUMBAI_TEAMCOLOUR_FLAT + CricketFunctions.whichTextColor(team.getTeamBadge()) + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName$TitleMS$LastName*TEXTURE*IMAGE SET " 
				+ Constants.T20_MUMBAI_TEAMCOLOUR_FLAT + CricketFunctions.whichTextColor(team.getTeamBadge()) + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName$TitleMS$Title*TEXTURE*IMAGE SET " 
				+ Constants.T20_MUMBAI_TEAMCOLOUR_FLAT + CricketFunctions.whichTextColor(team.getTeamBadge()) + "\0", print_writers);
		
		if(WhichProfile.toUpperCase().equalsIgnoreCase("DT20")) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName$TitleMS$Title*GEOM*TEXT SET " +
					"T20 CAREER" + "\0", print_writers);
		}else if(WhichProfile.equalsIgnoreCase("MCA T20s")){
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName$TitleMS$Title*GEOM*TEXT SET " +
					"MCA T20 24-25" + "\0", print_writers);
		}else if(WhichProfile.equalsIgnoreCase("IPL")){
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName$TitleMS$Title*GEOM*TEXT SET " +
					"IPL CAREER" + "\0", print_writers);
		}else if(WhichProfile.equalsIgnoreCase("IPL 2025")){
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName$TitleMS$Title*GEOM*TEXT SET " +
					"IPL 2025" + "\0", print_writers);
		}else if(WhichProfile.equalsIgnoreCase("THIS_SERIES")){
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName$TitleMS$Title*GEOM*TEXT SET " +
					"T20 MUMBAI SEASON 3" + "\0", print_writers);
		}
			
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$PlayerName$TitleMS$Title*ACTIVE SET 1\0", 
				print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Player$ImagePlayer$txTitle*GEOM*TEXT SET " +
				CricketFunctions.RoleType(player.getRole().toUpperCase())+ "\0", print_writers);
		
		//Veil Omo
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$Veil*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		
		//player photo
		if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Player$ImagePlayer$Headshot*TEXTURE*IMAGE SET " 
					+ Constants.T20_MUMBAI_PHOTO_PATH  + Constants.LEFT_1024 + team.getTeamBadge() + "\\\\" + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$PlayerBio$Body$CurrentLeader$Player$ImagePlayer$Headshot*TEXTURE*IMAGE SET " 
					+ "\\\\" + config.getPrimaryIpAddress() + Constants.T20_MUMBAI_PHOTO_PATH_NETWORK + Constants.LEFT_1024 + team.getTeamBadge() + "\\\\" 
					+ player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}
		return Constants.OK;
	}

	public String getBugImpact(Integer player,List<PrintWriter> print_writers,MatchAllData matchAllData,int WhichSide) {
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.T20_MUMBAI:
			if(!CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), player).isEmpty()) {
				switch(CricketFunctions.checkBatAndBallImpactInOutPlayer(matchAllData.getEventFile().getEvents(), player)) {
				case "IMP_IN":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$Impact" 
							 + "$select_Impact*FUNCTION*Omo*vis_con SET 2\0", print_writers);
					break;
				case "IMP_OUT":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$Impact" 
							 + "$select_Impact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					break;
				case "CON_IN":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$Impact" 
							 + "$select_Impact*FUNCTION*Omo*vis_con SET 4\0", print_writers);
					break;
				case "CON_OUT":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$Impact" 
							 + "$select_Impact*FUNCTION*Omo*vis_con SET 3\0", print_writers);
					break;
				}
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_PopUpBug$PlayerImage$Side" + WhichSide + "$Impact" 
						 + "$select_Impact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
			break;
		}
		return "";
	}
}