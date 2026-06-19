package com.cricket.captions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.BeanUtils;
import com.cricket.containers.Infobar;
import com.cricket.controller.IndexController;
import com.cricket.ispl.mvp_leaderBoard;
import com.cricket.model.BatBallGriff;
import com.cricket.model.BattingCard;
import com.cricket.model.BestStats;
import com.cricket.model.BowlingCard;
import com.cricket.model.Commentator;
import com.cricket.model.Configuration;
import com.cricket.model.DuckWorthLewis;
import com.cricket.model.Event;
import com.cricket.model.FieldersData;
import com.cricket.model.Fixture;
import com.cricket.model.Ground;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.InfobarStats;
import com.cricket.model.Inning;
import com.cricket.model.LeagueTable;
import com.cricket.model.MatchAllData;
import com.cricket.model.OverByOverData;
import com.cricket.model.Player;
import com.cricket.model.Review;
import com.cricket.model.Setup;
import com.cricket.model.Statistics;
import com.cricket.model.StatsType;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class LofInfobarGfx 
{
	public Configuration config;
	public String slashOrDash = "-", WhichProfile = "", containerName = "", status = "", previous_sixes = "",base_name = "", 
			stats_text = "", par_Overs="", Comms_Name,color = "", color2 = "", prev_score = "", new_score = "",
			prev_wicket = "", new_wicket = "", prevTeamScore = "", currTeamScore = "", freeText = "",logo_name = "" , 
			color_name = "", logo_name1 = "" , color_name1 = "",WhichStyle = "",today_sixes="",previous_fours="",today_fours="",
			previous_nines="",today_nines="";
	
	boolean isThisOverLimitExceed = false, isbatsmannotout = false,isimpactBowlIn = false,isimpactBatIn = false,cumm_runs = false,
			boc_tape_on_screen = false,bc_log_50_screen=false,cumm_cr=false;
	
	public int FirstPlayerId,lastXballs,sponsor_omo,infobarStatsId,rowId=0,challengedRuns,PreOnStrikeBatsmen,CurrOnStrikeBatsmen,
			team_id,omo=0,whichInning=0,cr_balls=0,this_over_balls=0,boc_size=0,matchPromoId,bowler_id=0,highlight_player;

	public Inning inning = new Inning();
	public Team team = new Team();
	public Infobar infobar = new Infobar();
	public Animation this_animation = new Animation();
	
	public static long speed_match_time_stamp=0,last_speed_match_time_stamp =0;
	
	public BattingCard battingCard;
	public BowlingCard bowling_Card;
	
	public List<Statistics> statistics;
	public List<StatsType> statsTypes;
	public List<InfobarStats> infobarStats;
	public List<Ground> Grounds;
	public List<DuckWorthLewis> dls;
	public List<Commentator> Commentators;
	public List<Player> Players;
	public List<HeadToHeadPlayer> headToHead;
	public List<Tournament> past_tournament_stats;
	
	@JsonIgnore
	public CricketService cricketService;

	@JsonIgnore
	public List<PrintWriter> print_writers; 
	public List<BattingCard> battingCardList = new ArrayList<BattingCard>();
	public BowlingCard bowlingCard = new BowlingCard();
	public List<String> this_data_str = new ArrayList<String>();
	public List<OverByOverData> manhattan = new ArrayList<OverByOverData>();
	public List<Player> player_XI = new ArrayList<Player>();
	public List<Tournament> this_series = new ArrayList<Tournament>();
	public List<BestStats> top_batsman_beststats = new ArrayList<BestStats>();
	public List<BestStats> top_bowler_beststats = new ArrayList<BestStats>();
	public List<BestStats> tapeBall_beststats = new ArrayList<BestStats>();
	public List<Fixture> fixtures = new ArrayList<Fixture>();
	public List<BatBallGriff> griff = new ArrayList<BatBallGriff>();
	public List<Team> teams = new ArrayList<Team>();
	public mvp_leaderBoard mvp = new mvp_leaderBoard();
	List<Player> mvp_player = new ArrayList<Player>();
	
	public List<Integer> PlayerId, PlayerIdIn;
	
	public Player player;
	public Statistics stat;
	public StatsType statsType;
	public InfobarStats infoBarStats;
	public Ground ground;
	public Fixture fixture;
	public Tournament tournament;
	public LeagueTable leagueTable;
	public String masterCricketDirectory;
	
	public LofInfobarGfx() {
		super();
		this.infobar.setLast_full_section(null);
	}

	public LofInfobarGfx(Configuration config, String slashOrDash, List<PrintWriter> print_writers, List<Statistics> statistics, List<StatsType> statsTypes, 
			List<InfobarStats> infobarStats, List<Ground> Grounds, List<Commentator> commentators, List<DuckWorthLewis> dls, List<Player> players, 
			List<HeadToHeadPlayer> headToHead, List<Tournament> past_tournament_stats, CricketService cricketService, String masterCricketDirectory) {
		super();
		this.config = config;
		this.slashOrDash = slashOrDash;
		this.print_writers = print_writers;
		this.statistics = statistics;
		this.statsTypes = statsTypes;
		this.infobarStats = infobarStats;
		this.Grounds = Grounds;
		this.Commentators = commentators;
		this.dls = dls;
		this.Players = players;
		this.headToHead = headToHead;
		this.past_tournament_stats = past_tournament_stats;
		this.cricketService = cricketService;
		this.masterCricketDirectory = masterCricketDirectory;
		
	}

	public String populatebonus(List<PrintWriter> print_writers,int WhichSide,MatchAllData matchAllData) throws InterruptedException
	{
		if(matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventType().equalsIgnoreCase(CricketUtil.LOG_50_50)) {
			int bonus = 0;
			int challengeRuns = 0;
			challengeRuns = matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventRuns();
			bonus = matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventExtraRuns();
			
			playChallengeWipe(print_writers, bonus, challengeRuns);
			return Constants.OK;
		}else {
			return "50-50 is not logged";
		}
	}
	
	public String updateInfobar(List<PrintWriter> print_writers,MatchAllData matchAllData) throws Exception {

		switch (config.getBroadcaster()) {
		case Constants.ISPL:
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
				if(inning == null) {
					return "updateInfobar: Inning return is NULL";
				}
				
				if(CricketFunctions.GetTargetData(matchAllData).getRemaningRuns() <= 0 || matchAllData.getMatch().getInning().get(1).getTotalWickets() >= 10 || 
						(CricketFunctions.GetTargetData(matchAllData).getRemaningRuns() > 0 && matchAllData.getMatch().getInning().get(1).getTotalWickets() >= 10 
						|| matchAllData.getMatch().getInning().get(1).getTotalOvers() >= matchAllData.getSetup().getMaxOvers())) {
					
					if(infobar.isInfobar_on_screen() && infobar.isBottom_infobar_on_screen()) {
						if(infobar.isResult_on_screen() == false) {
							if(infobar.getFull_section() != null && !infobar.getFull_section().isEmpty()) {
//								infobar.setFull_section(CricketUtil.RESULT);
//								populateFullSection(print_writers, matchAllData, 2);
//								ResultAnimation("CHANGE_ON");
//								TimeUnit.MILLISECONDS.sleep(2000);
//								populateFullSection(print_writers, matchAllData, 1);
//								ResultAnimation("CUTBACK");
//								
//								infobar.setFull_section(CricketUtil.RESULT);
//								infobar.setResult_on_screen(true);
							}else{
//								infobar.setResult_on_screen(true);
//								
//								this.infobar.setFull_section(CricketUtil.RESULT);
//								infobar.setFull_section(CricketUtil.RESULT);
//								
//								populateFullSection(print_writers, matchAllData, 1);
//								ResultAnimation("ANIMATE_IN");
							}
						}
					}
				}else {
					if(infobar.getFull_section() != null && !infobar.getFull_section().isEmpty()) {
						populateFullSection(print_writers, matchAllData, 1);   
					}
					
					if(infobar.getRight_section() != null && !infobar.getRight_section().isEmpty()) {
						populateVizInfobarRightSection(true,print_writers, matchAllData, 1, 0);
					}
					infobar.setResult_on_screen(false);
				}
				
				if(infobar.isInfobar_on_screen()) {
					populatebonus(print_writers, 1, matchAllData);
				}
								
				if(infobar.isChallengeRunOnScreen()) {
					populateChallengedSection(true,print_writers, matchAllData, 1);
				}
				
				if(infobar.isSuperOverThisOverOnScreen()) {
					populateSuperOver(true,print_writers, matchAllData, 1);
				}
				
				if(infobar.getMiddle_section() != null && !infobar.getMiddle_section().isEmpty()) {
					if(infobar.getMiddle_section().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						populateVizInfobarMiddleSection(print_writers, matchAllData, 2);
						populateVizInfobarMiddleSection(print_writers, matchAllData, 1);
					}else {
						switch(infobar.getMiddle_section().toUpperCase()) {
						case "CHALLENGED_IDENT": case "CHALLENGED_RUNS": case "CHALLENGED_RUNS_CUMM": case "TAPE_BALL_FULL": case "TAPE_BALL_SHORT": case "SUPER_OVER_FULL": 
						case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER": case "MVP_LB_IDENT": case "MVP_LB_SINGLE_PLAYER": case "MVP_LB_ALL_PLAYER":
							populateCurrentBatsmen(print_writers, matchAllData, 2);
							populateVizInfobarBowler(print_writers, matchAllData, 2);
							
							populateCurrentBatsmen(print_writers, matchAllData, 1);
							populateVizInfobarBowler(print_writers, matchAllData, 1);
							break;
						case "CURR_PARTNERSHIP": case "SB_MATCH_PROMO": case "POINTS_TABLE": case "TEAM_FORMGUIDE": case "LB_MOST_RUNS": case "LB_MOST_WICKETS": 
						case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY":
							populateVizInfobarMiddleSection(print_writers, matchAllData, 2);
							populateVizInfobarMiddleSection(print_writers, matchAllData, 1);
							break;
						case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER": case "BATTINGCARD": case "BOWLINGCARD": case "HOWOUT": 
						case "TARGET": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH": case "IDENT":
							break;
						}
					}
				}
				
				populateInfobarTeamNameScore(true,print_writers,matchAllData,2);
			break;
		}
		return Constants.OK;
	}
	public String populateTapeBall(List<PrintWriter> print_writers,MatchAllData matchAllData,int WhichSide) {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
		if(inning == null) {
			return "populateInfobarTeamNameScore: Inning return is NULL";
		}
		
		if(infobar.getMiddle_section().equalsIgnoreCase("TAPE_BALL_FULL")) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 2\0",print_writers);
		}
		else if(infobar.getMiddle_section().equalsIgnoreCase("TAPE_BALL_SHORT")) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 6\0",print_writers);
		}
		return Constants.OK;
	}
	public String populateSuperOver(boolean is_this_updating, List<PrintWriter> print_writers,MatchAllData matchAllData,int WhichSide) {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
		if(inning == null) {
			return "populateInfobarTeamNameScore: Inning return is NULL";
		}
		
		int total_balls=6;
		boolean extra_bowled = false;
		
		if(is_this_updating == false) {
			if(infobar.getMiddle_section().equalsIgnoreCase("SUPER_OVER_FULL")) {
				infobar.setSuperOverThisOverOnScreen(false);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + "$SuperOverStart"
						+ "$TeamLogo1$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + matchAllData.getSetup().getHomeTeam().getTeamBadge() + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + "$SuperOverStart"
						+ "$TeamLogo1$img_TeamLogoGlow*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + matchAllData.getSetup().getHomeTeam().getTeamBadge() + "\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + "$SuperOverStart"
						+ "$TeamLogo2$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + matchAllData.getSetup().getAwayTeam().getTeamBadge() + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + "$SuperOverStart"
						+ "$TeamLogo2$img_TeamLogoGlow*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + matchAllData.getSetup().getAwayTeam().getTeamBadge() + "\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 11\0",print_writers);
			}
			else if(infobar.getMiddle_section().equalsIgnoreCase("SUPER_OVER_SHORT") || infobar.getMiddle_section().equalsIgnoreCase("SUPER_OVER_THIS_OVER")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 12\0",print_writers);
				
				if(infobar.getMiddle_section().equalsIgnoreCase("SUPER_OVER_SHORT")) {
					infobar.setSuperOverThisOverOnScreen(false);
				}else if(infobar.getMiddle_section().equalsIgnoreCase("SUPER_OVER_THIS_OVER")) {
					infobar.setSuperOverThisOverOnScreen(true);
				}
			}
		}
		
		this_data_str = new ArrayList<String>();
		this_data_str.add(String.join(",",  new ArrayList<>(Arrays.asList(IndexController.MatchStats.getOverData().getThisOverTxt().split(",")))
		        .stream().map(s -> s.replace("WIDE", "WD").replace("NO_BALL", "NB").replace("LEG_BYE", "LB").replace("BYE", "B")
		        .replace("PENALTY", "PN").replace("LOG_WICKET", "W").replace("WICKET", "W"))
		        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {Collections.reverse(list); return list;}))
		        .toArray(new String[0])));
		
		String overData = this_data_str.get(this_data_str.size() - 1);
		if (overData == null || overData.trim().isEmpty()) {
		    return "populateVizInfobarRightBottom: This over data returned invalid";
		}
		
		for(int i=1; i<=12; i++) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$geom_Alpha*ALPHA*ALPHA SET 50\0", print_writers);
		}
			
		for(int iBall = 0; iBall < overData.split(",").length; iBall++) {
			
			if(iBall < 12) {
				isThisOverLimitExceed = true;
				extra_bowled = false;
				switch (overData.split(",")[iBall].toUpperCase()) {
				case CricketUtil.DOT:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
					break;
				case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + overData.split(",")[iBall] + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
					break;
				case CricketUtil.FOUR:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + CricketUtil.FOUR + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
					break;
				case CricketUtil.SIX:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + CricketUtil.SIX + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
					break;
				case CricketUtil.NINE:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 4\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
					break;	
				case "W":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 2\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
					break;

				default:
					if(overData.split(",")[iBall].toUpperCase().contains("BOUNDARY")) {
						if(overData.split(",")[iBall].toUpperCase().equalsIgnoreCase("6BOUNDARY")|| overData.split(",")[iBall].toUpperCase().equalsIgnoreCase("4BOUNDARY")) {
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Boundaries$fig_Ball*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).
									split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
							
						}else if(overData.split(",")[iBall].toUpperCase().equalsIgnoreCase("9BOUNDARY")) {
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 4\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						}
						else if(!overData.isEmpty()) {
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + 
									this_data_str.get(this_data_str.size()-1).split(",")[iBall] + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
							
							switch (overData.split(",")[iBall].toUpperCase()) {
							case "1B": case "2B": case "3B": case "4B": case "5B": case "6B":
							case "1LB": case "2LB": case "3LB": case "4LB": case "5LB": case "6LB":
								
								break;
							default:
								if(overData.split(",")[iBall].contains("NB") || overData.split(",")[iBall].contains("WD")) {
									total_balls++;
									extra_bowled = true;
								}
								break;
							}
						}
					}else {
						
						if(!overData.isEmpty()) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + overData.split(",")[iBall] + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						}
						
						switch (overData.split(",")[iBall].toUpperCase()) {
						case "1B": case "2B": case "3B": case "4B": case "5B": case "6B":
						case "1LB": case "2LB": case "3LB": case "4LB": case "5LB": case "6LB":
							
							break;

						default:
							if(overData.split(",")[iBall].contains("NB") || overData.split(",")[iBall].contains("WD")) {
								total_balls++;
								extra_bowled = true;
							}
							break;
						}
					}
					break;
				}
			}
		}
		
		if(total_balls > 6 && total_balls <= 9) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$SuperOverStatic$ThisOverAll$ThisOver*FUNCTION*Grid*num_row SET " + "3" + "\0", print_writers);
			if(extra_bowled) {
				if(total_balls == 7) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok1*VALUE SET 118\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Offset", "SHOW 0.0");
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok2*VALUE SET 90\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Offset", "START");
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Ball7", "START");
					
				}else if(total_balls == 8) {
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Ball8", "START");
				}else if(total_balls == 9) {
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Ball9", "START");
				}
			}
		}else if(total_balls > 9) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$SuperOverStatic$ThisOverAll$ThisOver*FUNCTION*Grid*num_row SET " + "4" + "\0", print_writers);
			if(extra_bowled) {
				if(total_balls == 10) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok1*VALUE SET 90\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Offset", "SHOW 0.0");
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok2*VALUE SET 80\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Offset", "START");
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Ball10", "START");
					
				}else if(total_balls == 11) {
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Ball11", "START");
				}else if(total_balls == 12) {
					this_animation.processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + WhichSide + "$Ball12", "START");
				}
			}
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$SuperOverStatic$ThisOverAll$ThisOver*FUNCTION*Grid*num_row SET " + "2" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok1*VALUE SET 118\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok2*VALUE SET 118\0", print_writers);
		}
		
		if(is_this_updating == false) {
			if(total_balls > 6 && total_balls <= 9) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok2*VALUE SET 90\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok2*VALUE SET 90\0", print_writers);
			}else if(total_balls > 9) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok2*VALUE SET 80\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$SuperOverStatic$ThisOverAll$ThisOver*ANIMATION*KEY*$sok2*VALUE SET 80\0", print_writers);
			}
		}
		cr_balls = total_balls;
		infobar.setLast_this_over(this_data_str.get(this_data_str.size()-1));
		
		return Constants.OK;
	}
	public String populateTarget(List<PrintWriter> print_writers,MatchAllData matchAllData) {
		
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
		if(inning == null) {
			return "populateInfobarTeamNameScore: Inning return is NULL";
		}
		if(inning.getInningNumber() == 2) {
			infobar.setTarget_on_screen(true);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Target$txt_Score*GEOM*TEXT SET " 
					+ CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + "\0", print_writers);
		}
		return Constants.OK;
	}
	public String populateInfobar(List<PrintWriter> print_writers,String whatToProcess, MatchAllData matchAllData, int WhichSide) throws InterruptedException, CloneNotSupportedException, IOException, JAXBException {
		
		switch (config.getBroadcaster()) {
		case Constants.ISPL:
			infobar.setLast_full_section(null);
			status = populateInfobarTeamNameScore(false,print_writers,matchAllData,1);
			if(status == Constants.OK) {
				status = populateVizInfobarLeftBottom(print_writers, matchAllData, 1);
				if(status == Constants.OK) {
					this.infobar.setMiddle_section(whatToProcess.split(",")[2]);
					if(infobar.getMiddle_section().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						this.infobar.setRight_section(CricketUtil.BOWLER);
						this.infobar.setRight_bottom(CricketUtil.BOWLER);
					}
					populateVizInfobarMiddleSection(print_writers, matchAllData, 1);
					setPositionOfScoreBug(whatToProcess, 2, config, 0);
				} else {
					return status;
				}
			} else {
				return status;
			}
			break;	
			
		}
		return Constants.OK;
	}
	public String populateChallengedSection(boolean is_this_updating, List<PrintWriter> print_writers,MatchAllData matchAllData, int whichSide) {
		switch(config.getBroadcaster()) {
		case Constants.ISPL:
			int BonusRuns = 0,total_balls=6;
			boolean extra_bowled = false;
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "populateInfobarTeamNameScore: Inning return is NULL";
			}
			
			if(is_this_updating == false) {
				if(infobar.getMiddle_section().equalsIgnoreCase("CHALLENGED_IDENT")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
							+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 5\0",print_writers);
				}else if(infobar.getMiddle_section().equalsIgnoreCase("CHALLENGED_RUNS")) {
					cumm_cr = true;
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
							+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 1\0",print_writers);
				}else if(infobar.getMiddle_section().equalsIgnoreCase("CHALLENGED_RUNS_CUMM")) {
					cumm_cr = true;
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
							+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 1\0",print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$ChallengeRuns$txt_ChangeRun*GEOM*TEXT SET " + challengedRuns + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + "$ChallengeOverStart"
						+ "$TeamLogo$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + "$ChallengeOverStart"
						+ "$TeamLogo$img_TeamLogoGlow*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + inning.getBatting_team().getTeamBadge() + "\0", print_writers);
				
				infobar.setChallengeRunOnScreen(true);
				infobar.setTop_stage(false);
			}
			
			this_data_str = new ArrayList<String>();
			this_data_str.add(String.join(",",  new ArrayList<>(Arrays.asList(IndexController.MatchStats.getOverData().getThisOverTxt().split(",")))
			        .stream().map(s -> s.replace("WIDE", "WD").replace("NO_BALL", "NB").replace("LEG_BYE", "LB").replace("BYE", "B")
			        .replace("PENALTY", "PN").replace("LOG_WICKET", "W").replace("WICKET", "W"))
			        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {Collections.reverse(list); return list;}))
			        .toArray(new String[0])));
			
			if(this_data_str.get(this_data_str.size()-1) == null || this_data_str.get(this_data_str.size()-1).split(",").length > 24) {
				return "populateVizInfobarRightBottom: This over data returned invalid";
			}
			
			BonusRuns = Integer.valueOf(CricketFunctions.processThisOverRunsCount(infobar.getLast_bowler().getPlayerId(), 
					matchAllData.getEventFile().getEvents()).split("-")[0]);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
					"$ChallengeOver$RunsScored$txt_Runs*GEOM*TEXT SET " + BonusRuns + "\0", print_writers);
			
			for(int i=1; i<=12; i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$geom_Alpha*ALPHA*ALPHA SET 50\0", print_writers);
			}
				
			for(int iBall = 0; iBall < this_data_str.get(this_data_str.size()-1).split(",").length; iBall++) {
				
				if(iBall < 12) {
					isThisOverLimitExceed = true;
					extra_bowled = false;
					switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
					case CricketUtil.DOT:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;
					case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + 
								this_data_str.get(this_data_str.size()-1).split(",")[iBall] + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						
						break;
					case CricketUtil.FOUR:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + CricketUtil.FOUR + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;
					case CricketUtil.SIX:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + CricketUtil.SIX + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;
					case CricketUtil.NINE:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 4\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;	
					case "W":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 2\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;

					default:
						if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().contains("BOUNDARY")) {
							if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().equalsIgnoreCase("6BOUNDARY")||
									this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().equalsIgnoreCase("4BOUNDARY")) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Boundaries$fig_Ball*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
								
							}else if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().equalsIgnoreCase("9BOUNDARY")) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 4\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
							}
							else if(!this_data_str.get(this_data_str.size()-1).isEmpty()) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + 
										this_data_str.get(this_data_str.size()-1).split(",")[iBall] + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
								
								switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
								case "1B": case "2B": case "3B": case "4B": case "5B": case "6B":
								case "1LB": case "2LB": case "3LB": case "4LB": case "5LB": case "6LB":
									
									break;
								default:
									if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("NB") || 
											this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("WD")) {
										total_balls++;
										extra_bowled = true;
									}
									break;
								}
							}
						}else {
							
							if(!this_data_str.get(this_data_str.size()-1).isEmpty()) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + 
										this_data_str.get(this_data_str.size()-1).split(",")[iBall] + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
										"$ChallengeOver$ThisOverAll$ThisOver$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
							}
							
							switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
							case "1B": case "2B": case "3B": case "4B": case "5B": case "6B":
							case "1LB": case "2LB": case "3LB": case "4LB": case "5LB": case "6LB":
								
								break;

							default:
								if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("NB") || 
										this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("WD")) {
									total_balls++;
									extra_bowled = true;
								}
								break;
							}
						}
						break;
					}
				}else {
					
					infobar.setMiddle_section("CHALLENGED_RUNS_CUMM");
					if(infobar.getLast_middle_section() != null && !infobar.getLast_middle_section().isEmpty()) {
						if(infobar.getLast_middle_section().equalsIgnoreCase("CHALLENGED_RUNS")) {
							if(cumm_cr) {
								this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls", "CONTINUE REVERSE");
							}
						}
					}
					
					for(BowlingCard boc : inning.getBowlingCard()) {
						switch (boc.getStatus().toUpperCase()) {
						case CricketUtil.CURRENT + CricketUtil.BOWLER:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
									+ whichSide + "$ChallengeOver$CummulativeRun$txt_ThisOver*GEOM*TEXT SET " + "THIS OVER" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
									+ whichSide + "$ChallengeOver$CummulativeRun$RunsAll$Select_Wickets*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
									+ whichSide + "$ChallengeOver$CummulativeRun$RunsAll$fig_Runs*GEOM*TEXT SET " + CricketFunctions.processThisOverRunsCount(boc.
									getPlayerId(), matchAllData.getEventFile().getEvents()).split("-")[0] + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
									+ whichSide + "$ChallengeOver$CummulativeRun$RunsAll$txt_Runs*GEOM*TEXT SET " + "RUN" + CricketFunctions.Plural(Integer.
										valueOf(CricketFunctions.processThisOverRunsCount(boc.getPlayerId(), matchAllData.getEventFile().getEvents()).
										split("-")[0])).toUpperCase() + "\0", print_writers);
							break;
						}
					}
					
					if(infobar.getLast_middle_section() != null && !infobar.getLast_middle_section().isEmpty()) {
						if(infobar.getLast_middle_section().equalsIgnoreCase("CHALLENGED_RUNS")) {
							infobar.setLast_middle_section("CHALLENGED_RUNS_CUMM");
							if(cumm_cr) {
								this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CahllengeCumulative", "START");
								cumm_cr = false;
							}
						}
					}
				}
			}
			
			if(total_balls > 6 && total_balls <= 9) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$ThisOverAll$ThisOver*FUNCTION*Grid*num_row SET " + "3" + "\0", print_writers);
				if(extra_bowled) {
					if(total_balls == 7) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok1*VALUE SET 118\0", print_writers);
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Offset", "SHOW 0.0");
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok2*VALUE SET 90\0", print_writers);
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Offset", "START");
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Ball7", "START");
						
					}else if(total_balls == 8) {
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Ball8", "START");
					}else if(total_balls == 9) {
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Ball9", "START");
					}
				}
				
			}else if(total_balls > 9) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$ThisOverAll$ThisOver*FUNCTION*Grid*num_row SET " + "4" + "\0", print_writers);
				if(extra_bowled) {
					if(total_balls == 10) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok1*VALUE SET 90\0", print_writers);
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Offset", "SHOW 0.0");
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
								"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok2*VALUE SET 68\0", print_writers);
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Offset", "START");
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Ball10", "START");
						
					}else if(total_balls == 11) {
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Ball11", "START");
					}else if(total_balls == 12) {
						this_animation.processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + whichSide + "$Ball12", "START");
					}
				}
				
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$ThisOverAll$ThisOver*FUNCTION*Grid*num_row SET " + "2" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok1*VALUE SET 118\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok2*VALUE SET 118\0", print_writers);
			}
			
			if(BonusRuns >= challengedRuns) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$RunsScored$Select_ScoreType*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$RunsScored$txt_ChallengeRuns*GEOM*TEXT SET +" + Math.round((BonusRuns/2)) + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$ChallengeOver$RunsScored$Select_ScoreType*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
				if(Math.round((BonusRuns/2)) > 0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
							"$ChallengeOver$RunsScored$txt_ChallengeRuns*GEOM*TEXT SET -" + Math.round((BonusRuns/2))  + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
							"$ChallengeOver$RunsScored$txt_ChallengeRuns*GEOM*TEXT SET 0\0", print_writers);
				}
			}
			
			if(is_this_updating == false) {
				if(total_balls > 6 && total_balls <= 9) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
							"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok2*VALUE SET 90\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
							"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok2*VALUE SET 90\0", print_writers);
				}else if(total_balls > 9) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
							"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok2*VALUE SET 68\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
							"$ChallengeOver$ThisOverAll$ThisOver*ANIMATION*KEY*$ok2*VALUE SET 68\0", print_writers);
				}
			}
			cr_balls = total_balls;
			infobar.setLast_this_over(this_data_str.get(this_data_str.size()-1));
			break;
		}
		return Constants.OK;
	}
	
	public String populateMvpLeaderBoard(boolean is_this_updating, List<PrintWriter> print_writers,MatchAllData matchAllData, int whichSide) throws StreamReadException, DatabindException, IOException {
		switch(config.getBroadcaster()) {
		case Constants.ISPL:
			if(is_this_updating == false) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
						+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 17\0",print_writers);
				
				switch (infobar.getMiddle_section()) {
				case "MVP_LB_IDENT":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
							+ "$MVP_LeaderBoard$select_DataType*FUNCTION*Omo*vis_con SET 0\0",print_writers);
					break;
				case "MVP_LB_SINGLE_PLAYER": case "MVP_LB_ALL_PLAYER":
					if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MVP).exists()) {
						mvp = (new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MVP), mvp_leaderBoard.class));
					}
					if(mvp == null) {
						return "populateMvpLeaderBoard : mvp file does not exists";
					}
					
					List<mvp_leaderBoard.Player> finalFivePlayers = new ArrayList<>();
					
					//topdatalist
					for (int i = 0; i < mvp.getData().getTop().size(); i++) {
						String player_id = mvp.getData().getTop().get(i).getPlayerId();
					    finalFivePlayers.add(mvp.getData().getTop().get(i));
					    mvp_player.add(cricketService.getAllPlayer().stream().filter(plyr -> Long.valueOf(player_id).equals(plyr.getOnlineId())).findAny().orElse(null));
					}
					for (int i = 0; i < mvp.getData().getList().size(); i++) {
						String player_id = mvp.getData().getList().get(i).getPlayerId();
					    finalFivePlayers.add(mvp.getData().getList().get(i));
					    mvp_player.add(cricketService.getAllPlayer().stream().filter(plyr -> Long.valueOf(player_id).equals(plyr.getOnlineId())).findAny().orElse(null));
					}
					
					switch (infobar.getMiddle_section()) {
					case "MVP_LB_SINGLE_PLAYER":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
								+ "$MVP_LeaderBoard$select_DataType*FUNCTION*Omo*vis_con SET 1\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
								+ "$MVP_LeaderBoard$Style2$LeaderBoardData$Select_Highlight*FUNCTION*Omo*vis_con SET 0\0",print_writers);
						
						team = cricketService.getTeams().stream().filter(tm -> tm.getTeamId() == mvp_player.get(highlight_player-1).getTeamId()).findAny().orElse(null);
						if(team == null) {
							return "team is null";
						}
						
						if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
									+ "$MVP_LeaderBoard$Style2$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + team.getTeamName4() + Constants.LEFT_1024 
									+ mvp_player.get(highlight_player-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
									+ "$MVP_LeaderBoard$Style2$Image$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\" + Constants.Local_ISPL_PHOTOS_PATH 
									+ team.getTeamName4() + Constants.LEFT_1024 + mvp_player.get(highlight_player-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
								+ "$MVP_LeaderBoard$Style2$Image$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + team.getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
								+ "$MVP_LeaderBoard$Style2$Image$txt_Rank*GEOM*TEXT SET " + finalFivePlayers.get(highlight_player-1).getPosition() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
								+ "$MVP_LeaderBoard$Style2$LeaderBoardData$Dehighlight$txt_Name*GEOM*TEXT SET " + mvp_player.get(highlight_player-1).getTicker_name() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
								+ "$MVP_LeaderBoard$Style2$LeaderBoardData$Dehighlight$txt_TeamName*GEOM*TEXT SET " + team.getTeamName4() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
								+ "$MVP_LeaderBoard$Style2$LeaderBoardData$Dehighlight$txt_Value*GEOM*TEXT SET " + finalFivePlayers.get(highlight_player-1).getFinalPoints() + "\0", print_writers);
						break;
					case "MVP_LB_ALL_PLAYER":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
								+ "$MVP_LeaderBoard$select_DataType*FUNCTION*Omo*vis_con SET 2\0",print_writers);
						for(int i=1;i<=finalFivePlayers.size();i++) {
							if(i<=5) {
								int team_id = mvp_player.get(i-1).getTeamId();
								team = cricketService.getTeams().stream().filter(tm -> tm.getTeamId() == team_id).findAny().orElse(null);
								
								if(i == highlight_player) {
									if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
												+ "$MVP_LeaderBoard$Style3$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + team.getTeamName4() + Constants.STRAIGHT_1024 
												+ mvp_player.get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									}else {
										CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
												+ "$MVP_LeaderBoard$Style3$Image$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\" + Constants.Local_ISPL_PHOTOS_PATH 
												+ team.getTeamName4() + Constants.STRAIGHT_1024 + mvp_player.get(i-1).getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
									}
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Select_Highlight*FUNCTION*Omo*vis_con SET 1\0",print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Highlight$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + team.getTeamBadge() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Highlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + team.getTeamBadge() + "\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Highlight$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + team.getTeamBadge() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Highlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + team.getTeamBadge() + "\0", print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Highlight$txt_Name*GEOM*TEXT SET " + mvp_player.get(i-1).getTicker_name() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Highlight$txt_TeamName*GEOM*TEXT SET " + team.getTeamName4() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Highlight$txt_Value*GEOM*TEXT SET " + finalFivePlayers.get(i-1).getFinalPoints() + "\0", print_writers);

								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Select_Highlight*FUNCTION*Omo*vis_con SET 0\0",print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Dehighlight$txt_Name*GEOM*TEXT SET " + mvp_player.get(i-1).getTicker_name() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Dehighlight$txt_TeamName*GEOM*TEXT SET " + team.getTeamName4() + "\0", print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide 
											+ "$MVP_LeaderBoard$Style3$LeaderBoardData$" + i + "$Dehighlight$txt_Value*GEOM*TEXT SET " + finalFivePlayers.get(i-1).getFinalPoints() + "\0", print_writers);
								}
							}
						}
						break;
					}
					break;
				}
			}
			break;
		}
		return Constants.OK;
	}
	
	public void populateInfobarColor(List<PrintWriter> print_writers, int whichSide) {
		for(int i=1; i<=12; i++) {
			
			//CHALLENGE RUNS - SIDE 1
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color2 + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
			
			//CHALLENGE RUNS - SIDE 2
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + (3-whichSide) + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + (3-whichSide) + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + (3-whichSide) + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + (3-whichSide) + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + (3-whichSide) + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + (3-whichSide) + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color2 + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + (3-whichSide) + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + (3-whichSide) + 
					"$ChallengeOver$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
	
			//THIS OVER - SIDE 2
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color2 + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color2 + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + 
					"$ThisOver_All$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
		}
		
		for(int i=1; i<=16; i++) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Runs$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Runs$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Boundaries$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Boundaries$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Wicket$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Wicket$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color2 + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$9$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$9$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
		//--------Side 2----------------------------------------------------------------------------------------------------------------------------------------------	
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Runs$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Runs$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Boundaries$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Boundaries$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Wicket$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$Wicket$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color2 + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$9$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$14BallTimeline$"
					+ "ThisOverAll$ThisOver$TimeBall" + i + "$9$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
		}
		
		// NEW WIPE ANIMATION COLOR
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$Prticles$"
				+ "img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$Prticles$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$Blast$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Wickets$Prticles$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Wickets$Blast$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Wickets$WicketValue$"
				+ "img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color2 + "\0", print_writers);
		
		
		//ANIMATION COLOR
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Four$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Four$"
				+ "Reflection$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Six$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Six$"
				+ "Reflection$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + color + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Wicket$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Wicket$"
				+ "Reflection$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + color2 + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$9$ShiftY$img_Base2"
				+ "*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$9$ShiftY$img_Text2"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$9$ShiftY$All$noname$Reflection$"
				+ "text$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$9$ShiftY$All$img_Base1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		
		//ANIMATION COLOR - CHALLENGE OVERS
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
				"$ChallengeOver$EventAnimations$Boundaries$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
				"$ChallengeOver$EventAnimations$Wicket$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color2 + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
				"$ChallengeOver$EventAnimations$9$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
				"$ChallengeOver$EventAnimations$9$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
				"$ChallengeOver$EventAnimations$9$ShiftY$All$noname$Reflection$text$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
				"$ChallengeOver$EventAnimations$9$ShiftY$All$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		
		//INFOBAR BOTTOM COLOR
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$TeamName$img_Base1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$TeamName$img_Text1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
		
		//INFOBAR LEFT COLOR
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter1$Name$img_Base1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter1$Name$img_Text1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter1$Score$img_Base2"
				+ "*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter1$Score$img_Text2"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter2$Name$img_Base1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter2$Name$img_Text1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter2$Score$img_Base2"
				+ "*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + color + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter2$Score$img_Text2"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Bowler$img_Base1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color2 + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Bowler$img_Text1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color2 + "\0", print_writers);
	}
	
	public void populateInfobarSuperOverColor(List<PrintWriter> print_writers, int whichSide) {
		for(int i=1; i<=12; i++) {
			//THIS OVER
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + "SO" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + "SO" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + "SO" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$ThisOver_All$"
					+ "ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + "SO" + "\0", print_writers);
			
			for(int j=1;j<=2;j++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + j + 
						"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + j + 
						"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Runs$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + j + 
						"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + j + 
						"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$9$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + j + 
						"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + whichSide + 
						"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Wicket$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + j + 
						"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + "SO" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + j + 
						"$SuperOverStatic$ThisOverAll$ThisOver$Ball" + i + "$Select_Type$Boundaries$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + "SO" + "\0", print_writers);
			}
		}
		
		//ANIMATION COLOR
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Four$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Four$"
				+ "Reflection$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Six$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Six$"
				+ "Reflection$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + "SO" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Wicket$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$Wicket$"
				+ "Reflection$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + "SO" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$9$"
				+ "img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$9$"
				+ "Reflection$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$Position_Y_For_TapeStatic$9$"
				+ "img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
		
		//INFOBAR BOTTOM COLOR
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$TeamName$img_Base1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$TeamName$img_Text1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
		
		//INFOBAR LEFT COLOR
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter1$Name$img_Base1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter1$Name$img_Text1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter1$Score$img_Base2"
				+ "*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter1$Score$img_Text2"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + "SO" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter2$Name$img_Base1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter2$Name$img_Text1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter2$Score$img_Base2"
				+ "*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Batter2$Score$img_Text2"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + "SO" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Bowler$img_Base1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "SO" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Left_DataGrp$AllSections$Bowler$img_Text1"
				+ "*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "SO" + "\0", print_writers);
	}
	
	public String populateInfobarTeamNameScore(boolean is_this_updating,List<PrintWriter> print_writers,MatchAllData matchAllData, int whichSide) throws InterruptedException, IOException {
		
		switch(config.getBroadcaster()) {
		case Constants.ISPL:
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "populateInfobarTeamNameScore: Inning return is NULL";
			}
			if(is_this_updating == false) {
				
				bowlingCard = inning.getBowlingCard().stream().filter(boc -> boc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)
						|| boc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.LAST+CricketUtil.BOWLER)).findAny().orElse(null);
				if(bowlingCard != null) {
					bowler_id = bowlingCard.getPlayerId();
				}
				
				if(inning.getBatting_team().getTeamName4().contains("KHILADI XI") || inning.getBatting_team().getTeamName4().contains("MASTER 11")) {
					if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						color = "KHILADI_XI";
					}else if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
						color = "MASTER_XI";
					}
				}else {
					color = inning.getBatting_team().getTeamName4();
				}
				
				if(inning.getBowling_team().getTeamName4().contains("KHILADI XI") || inning.getBowling_team().getTeamName4().contains("MASTER 11")) {
					if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
						color2 = "KHILADI_XI";
					}else if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
						color2 = "MASTER_XI";
					}
				}else {
					color2 = inning.getBowling_team().getTeamName4();
				}
				
				//-----------------------------SUPER OVER---------------------------------//
				
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
					populateInfobarSuperOverColor(print_writers, whichSide);
				}else {
					//Challenged Runs Section Color - This Over Color - Batsman and Bowler Color
					populateInfobarColor(print_writers, whichSide);
				}
				//---------------------------------------------------------------------------------------------//
				
				for(int i = 1; i <= 10; i++) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + whichSide + "$TimeLine$"
							+ "TimeLineData$TimeBall" + i + "$Select_OverType*FUNCTION*Omo*vis_con SET 0\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + (3-whichSide) + "$TimeLine$"
							+ "TimeLineData$TimeBall" + i + "$Select_OverType*FUNCTION*Omo*vis_con SET 0\0",print_writers);
				}
				
				//--------------------------------------------------------------//
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$TeamName$txt_Team*GEOM*TEXT SET " + 
						inning.getBatting_team().getTeamName4() + "\0", print_writers);
				
				if(infobar.isInfobar_on_screen()) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + (3-whichSide) + 
							"$Select_Subline*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + whichSide + 
							"$Select_Subline*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$WaterMark$Out$select_BugStyle"
						+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				
				if(inning.getInningNumber() == 2) {
					infobar.setTarget_on_screen(true);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Target$txt_Score*GEOM*TEXT SET " 
							+ CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + "\0", print_writers);
				}
			}
			
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$txt_Score*GEOM*TEXT SET " + 
//					CricketFunctions.getTeamScore(inning, slashOrDash, false) + "\0", print_writers);
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$txt_Score*GEOM*TEXT SET " + 
//					CricketFunctions.getTeamScore(inning, slashOrDash, false) + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$txt_Score*GEOM*TEXT SET " + 
					CricketFunctions.getTeamScoreAddBonusRuns(matchAllData.getEventFile().getEvents(), inning, bowler_id, "-", false) + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$txt_Score*GEOM*TEXT SET " + 
					CricketFunctions.getTeamScoreAddBonusRuns(matchAllData.getEventFile().getEvents(), inning, bowler_id, "-", false) + "\0", print_writers);
			
			if(matchAllData.getSetup().getTargetOvers() != null && !matchAllData.getSetup().getTargetOvers().isEmpty()) {
				if(matchAllData.getSetup().getTargetType() != null && !matchAllData.getSetup().getTargetType().isEmpty()) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$txt_DLSOvers*GEOM*TEXT SET " + 
							"(" + matchAllData.getSetup().getTargetOvers() + ") " + matchAllData.getSetup().getTargetType().toUpperCase() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$txt_DLSOvers*GEOM*TEXT SET " + 
							"(" + matchAllData.getSetup().getTargetOvers() + ") " + matchAllData.getSetup().getTargetType().toUpperCase() + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$txt_DLSOvers*GEOM*TEXT SET " + 
							"(" + matchAllData.getSetup().getTargetOvers() + ")" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$txt_DLSOvers"
							+ "*GEOM*TEXT SET " + "(" + matchAllData.getSetup().getTargetOvers() + ")" + "\0", print_writers);
				}
			}else {
				if(inning.getTotalOvers() == 0 || inning.getTotalOvers() > 1) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$"
							+ "txt_DLSOvers*GEOM*TEXT SET\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$"
							+ "txt_DLSOvers*GEOM*TEXT SET\0", print_writers);
				}else {
					if(inning.getTotalBalls() == 1) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay"
								+ "$txt_DLSOvers*GEOM*TEXT SET\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$"
								+ "txt_DLSOvers*GEOM*TEXT SET\0", print_writers);
					}
					else if(inning.getTotalBalls() > 0) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay"
								+ "$txt_DLSOvers*GEOM*TEXT SET\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$"
								+ "txt_DLSOvers*GEOM*TEXT SET\0", print_writers);
					}
					else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay"
								+ "$txt_DLSOvers*GEOM*TEXT SET\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$"
								+ "txt_DLSOvers*GEOM*TEXT SET\0", print_writers);
					}
				}
			}
			
			infobar.setPlayer_outOrnot(false);
			
			if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
				if(infobar.isPowerplay_on_screen() == false) {
					infobar.setPowerplay_on_screen(true);
		         }
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$CenterGRp$Main$BattingTeamGrp$PowerPlay$txt_PP*GEOM*TEXT SET " + 
//						"SUPER OVER" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$txt_Overs*GEOM*TEXT SET \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$txt_Overs*GEOM*TEXT SET \0", print_writers);
				
				if(inning.getTotalOvers() > 0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$"
							+ "txt_DLSOvers*GEOM*TEXT SET " + "" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay"
							+ "$txt_DLSOvers*GEOM*TEXT SET " + "" + "\0", print_writers);
				}else if(inning.getTotalOvers() == 0) {
					if(inning.getTotalBalls() == 0 || inning.getTotalBalls() == 1) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$"
								+ "txt_DLSOvers*GEOM*TEXT SET " + "" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$"
								+ "txt_DLSOvers*GEOM*TEXT SET " + "" + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$"
								+ "txt_DLSOvers*GEOM*TEXT SET " + "" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$"
								+ "txt_DLSOvers*GEOM*TEXT SET " + "" + "\0", print_writers);
					}
				}
			}else {
				
				if (!CricketFunctions.processPowerPlay(CricketUtil.MINI, matchAllData).isEmpty()) {
					if (infobar.isPowerplay_on_screen() == true) {
					} else {
						infobar.setPowerplay_on_screen(true);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_BottomInfo$In_Out$PowerPlay START \0", print_writers);
					}
				} else {
					if (infobar.isPowerplay_on_screen() == true) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_BottomInfo$In_Out$PowerPlay CONTINUE REVERSE \0", print_writers);
						infobar.setPowerplay_on_screen(false);
					}
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreAll$MoveForPowerPlay$txt_Overs*GEOM*TEXT SET " + 
						CricketFunctions.OverBalls(inning.getTotalOvers(),inning.getTotalBalls()) + "\0", print_writers);
				if(inning.getTotalOvers() == 1 && inning.getTotalBalls() == 0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$txt_Overs*GEOM*TEXT SET " + 
							CricketFunctions.OverBalls(inning.getTotalOvers(),inning.getTotalBalls()) + " OVER" + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$ScoreFor11$txt_Overs*GEOM*TEXT SET " + 
							CricketFunctions.OverBalls(inning.getTotalOvers(),inning.getTotalBalls()) + " OVERS" + "\0", print_writers);
				}
			}
			
			break;	
		}
		return Constants.OK;
	}

	public void populateTwoBatsmenSingleBatsman(List<PrintWriter> print_writers, MatchAllData matchAllData,
			int WhichSide, int WhichSubSide, int WhichBatsman, List<BattingCard> battingCardList) throws InterruptedException {
	
		switch(config.getBroadcaster()) {
		case Constants.ISPL:
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + 
					"$Image$Side" + WhichSide + "$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(), battingCardList.get(WhichBatsman-1).getPlayerId()).isEmpty()) {
				switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber() ,battingCardList.get(WhichBatsman-1).getPlayerId())) {
				case "IMP_IN":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + 
							"$Image$Side" + WhichSide + "$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					break;
				}
			}
			
			if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + "$Image$Side" 
						+ WhichSide + "$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBatting_team().getTeamName4() + Constants.SMALL_512 
						+ battingCardList.get(WhichBatsman-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + "$Image$Side" 
						+ WhichSide + "$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + "\\" + Constants.Local_ISPL_PHOTOS_PATH + inning.getBatting_team().getTeamName4() 
						+ Constants.SMALL_512 + battingCardList.get(WhichBatsman-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + "$Name$Side" + WhichSide 
				 + "$txt_Name*GEOM*TEXT SET " + battingCardList.get(WhichBatsman-1).getPlayer().getTicker_name() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + "$Score$Side" + WhichSide 
					 + "$txt_Runs*GEOM*TEXT SET " + battingCardList.get(WhichBatsman-1).getRuns() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + "$Score$Side" + WhichSide 
					 + "$txt_Balls*GEOM*TEXT SET " + battingCardList.get(WhichBatsman-1).getBalls() + "\0", print_writers);
			
			
			if((WhichBatsman == 1 && battingCardList.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) || 
					(WhichBatsman == 2 && battingCardList.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT))) {
				this_animation.processAnimation(Constants.FRONT, print_writers, "LowLight$Batter" + WhichBatsman, "SHOW 0.0");
			} else {
				this_animation.processAnimation(Constants.FRONT, print_writers, "LowLight$Batter" + WhichBatsman, "SHOW 0.500");
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + 
						"$Image$Select_Strike*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
			
			if((WhichBatsman == 1 && battingCardList.get(0).getOnStrike() != null && battingCardList.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES))
					||(WhichBatsman == 2 && battingCardList.get(1).getOnStrike() != null && battingCardList.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES))) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + 
						"$Image$Select_Strike*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			} else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Batter" + WhichBatsman + 
						"$Image$Select_Strike*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
			break;
		}
	}
	public String populateCurrentBatsmen(List<PrintWriter> print_writers, MatchAllData matchAllData,int WhichSide) 
			throws InterruptedException 
	{
		if(inning.getPartnerships() != null && inning.getPartnerships().size() <= 0) {
			return "populateCurrentBatsmen: Partnership array size is zero [" + inning.getPartnerships().size() + "]";
		}
		
		battingCardList = new ArrayList<BattingCard>();
		for(int iBat = 1; iBat <= 2; iBat++) {
			if(iBat == 1) {
				battingCardList.add(inning.getBattingCard().stream().filter(bc -> bc.getPlayerId() == 
					inning.getPartnerships().get(inning.getPartnerships().size() - 1).getFirstBatterNo()).findAny().orElse(null));
			} else {
				battingCardList.add(inning.getBattingCard().stream().filter(bc -> bc.getPlayerId() == 
					inning.getPartnerships().get(inning.getPartnerships().size() - 1).getSecondBatterNo()).findAny().orElse(null));
			}
			if(battingCardList.get(battingCardList.size()-1) == null) {
				return "populateCurrentBatsmen: One or more batsmen return are NULL";
			}
		}

		switch(config.getBroadcaster()) {
		case Constants.ISPL:
			if(infobar.getLast_batsmen() != null && infobar.getLast_batsmen().size() >= 2) {
				if(infobar.getLast_batsmen().get(0).getPlayerId() != battingCardList.get(0).getPlayerId()) {
					populateTwoBatsmenSingleBatsman(print_writers, matchAllData, WhichSide, 2, 1, battingCardList);
					this_animation.processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Batter1" , "START");
					TimeUnit.MILLISECONDS.sleep(800);
					populateTwoBatsmenSingleBatsman(print_writers, matchAllData, 1, 1, 1, battingCardList);
					TimeUnit.MILLISECONDS.sleep(200);
					this_animation.processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Batter1", "SHOW 0.0");
				} else {
					populateTwoBatsmenSingleBatsman(print_writers, matchAllData, WhichSide, 1, 1, battingCardList);
				}
				if(infobar.getLast_batsmen().get(1).getPlayerId() != battingCardList.get(1).getPlayerId()) {
					populateTwoBatsmenSingleBatsman(print_writers, matchAllData, WhichSide, 2, 2, battingCardList);
					this_animation.processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Batter2" , "START");
					TimeUnit.MILLISECONDS.sleep(800);
					populateTwoBatsmenSingleBatsman(print_writers, matchAllData, 1, 1, 2, battingCardList);
					TimeUnit.MILLISECONDS.sleep(200);
					this_animation.processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Batter2", "SHOW 0.0");
				} else {
					populateTwoBatsmenSingleBatsman(print_writers, matchAllData, WhichSide, 1, 2, battingCardList);
				}
			} else {
				populateTwoBatsmenSingleBatsman(print_writers, matchAllData, WhichSide, 1, 1, battingCardList);
				populateTwoBatsmenSingleBatsman(print_writers, matchAllData, WhichSide, 1, 2, battingCardList);
			}
			
			infobar.setIs_player_outOrnot(false);
			infobar.setLast_batsmen(battingCardList);
			break;
		}
		return Constants.OK;
	}
	public void populateRightTopBowler(List<PrintWriter> print_writers, MatchAllData matchAllData,
			int WhichSide, int WhichSubSide) throws InterruptedException {
	
		switch(config.getBroadcaster()) {
		case Constants.ISPL:
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			
			bowler_id = bowlingCard.getPlayerId();
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Bowler$Side" + WhichSide + 
					"$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bowler_id).isEmpty()) {
				switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bowler_id)) {
				case "IMP_IN":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Bowler$Side" + WhichSide + 
							"$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					break;
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Bowler$Side" + WhichSide 
					 + "$txt_Name*GEOM*TEXT SET " + bowlingCard.getPlayer().getTicker_name() + "\0", print_writers);
				
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Bowler$Side" + WhichSide 
					 + "$txt_Figure*GEOM*TEXT SET " + bowlingCard.getWickets() + slashOrDash + bowlingCard.getRuns() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Score$Bowler$Side" + WhichSide 
					 + "$txt_Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowlingCard.getOvers(), bowlingCard.getBalls()) + "\0", print_writers);
				
			if(bowlingCard.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
				//this_animation.processAnimation(Constants.FRONT, print_writers, "LowLight$Bowler", "SHOW 0.0");
			} else {
				this_animation.processAnimation(Constants.FRONT, print_writers, "LowLight$Bowler", "SHOW 0.500");
			}
			break;	
		}
	}
	public String populateVizInfobarBowler(List<PrintWriter> print_writers, MatchAllData matchAllData,int WhichSide) throws InterruptedException {

		bowlingCard = inning.getBowlingCard().stream().filter(boc -> boc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.CURRENT+CricketUtil.BOWLER)
			|| boc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.LAST+CricketUtil.BOWLER)).findAny().orElse(null);
		bowler_id = bowlingCard.getPlayerId();
		
		if(bowlingCard == null) {
			return "populateVizInfobarBowler: no current bowler found";
		}
		
		switch(config.getBroadcaster()) {
		case Constants.ISPL:
			if(infobar.getLast_bowler() != null) {
				if(infobar.getLast_bowler().getPlayerId() != bowlingCard.getPlayerId()) {
					populateRightTopBowler(print_writers, matchAllData, 2, 1);
					this_animation.processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Bowler", "START");
					TimeUnit.MILLISECONDS.sleep(500);
					populateRightTopBowler(print_writers, matchAllData, 1, 1);
					this_animation.processAnimation(Constants.FRONT, print_writers, "LowLight$Bowler", "SHOW 0.0");
				} else {
					populateRightTopBowler(print_writers, matchAllData, 2, 1);
					populateRightTopBowler(print_writers, matchAllData, 1, 1);
					this_animation.processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Bowler", "SHOW 0.0");
				}
			} else {
				populateRightTopBowler(print_writers, matchAllData, 2, 1);
				populateRightTopBowler(print_writers, matchAllData, 1, 1);
				this_animation.processAnimation(Constants.FRONT, print_writers, "LowLight$Bowler", "SHOW 0.0");
			}
			
			infobar.setLast_bowler(bowlingCard);
			break;	
		}
		
		return Constants.OK;
	}
	public String populateVizInfobarRightBottom(List<PrintWriter> print_writers, MatchAllData matchAllData,
		int WhichSide,int WhichSubSide) throws InterruptedException 
	{
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> 
			inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);

		if(inning == null) {
			return "populateVizInfobarRightBottom: Inning return is NULL";
		}
		
		switch(config.getBroadcaster()) {
		case Constants.ISPL:
			if(infobar.getRight_bottom() != null && !infobar.getRight_bottom().isEmpty()) {
				switch(infobar.getRight_bottom().toUpperCase()) {
				case CricketUtil.BOWLER:
					this.infobar.setRight_bottom(CricketUtil.BOWLER);
					populateVizInfobarBowler(print_writers, matchAllData, WhichSide);
					break;
				case "BOWLING_END":
					if(inning.getBowling_team().getTeamName4().contains("KHILADI XI") || inning.getBowling_team().getTeamName4().contains("MASTER 11")) {
						if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
							color2 = "KHILADI_XI";
						}else if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
							color2 = "MASTER_XI";
						}
					}else {
						color2 = inning.getBowling_team().getTeamName4();
					}
					
					if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side" + WhichSide + "$Free_Text$img_Text1*TEXTURE*IMAGE SET " 
								+ Constants.ISPL_TEXT1 + color2 + "\0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side" + WhichSide + "$Select*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
					
					if(bowlingCard.getBowling_end() == 1) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side" + WhichSide + "$Free_Text$txt_Header*GEOM*TEXT SET " 
								+ matchAllData.getSetup().getGround().getFirst_bowling_end() + "\0", print_writers);
					}
					else if(bowlingCard.getBowling_end() == 2) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side" + WhichSide + "$Free_Text$txt_Header*GEOM*TEXT SET " 
								+ matchAllData.getSetup().getGround().getSecond_bowling_end() + "\0", print_writers);
					}
					break;
				case CricketUtil.OVER:
					this_data_str = new ArrayList<String>();
					this_data_str.add(CricketFunctions.getEventsText(CricketUtil.OVER,infobar.getLast_bowler().getPlayerId() ,
							",", matchAllData.getEventFile().getEvents(),0));
					
					if(this_data_str.get(this_data_str.size()-1) == null || this_data_str.get(this_data_str.size()-1).split(",").length > 11) {
						return "populateVizInfobarRightBottom: This over data returned invalid";
					}
					
					if(inning.getBowling_team().getTeamName4().contains("KHILADI XI") || inning.getBowling_team().getTeamName4().contains("MASTER 11")) {
						if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
							color2 = "KHILADI_XI";
						}else if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
							color2 = "MASTER_XI";
						}
					}else {
						color2 = inning.getBowling_team().getTeamName4();
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side" + WhichSide + "$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
					
					for(int iBall = 0; iBall < this_data_str.get(this_data_str.size()-1).split(",").length; iBall++) {
						
						if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side" + WhichSide + "$This_Over$Balls$"+(iBall + 1)+"$img_Text1*TEXTURE*IMAGE SET " 
									+ Constants.ISPL_TEXT1 + color2 + "\0", print_writers);
						}
						
						switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
						case CricketUtil.DOT:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Choose_Type*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
							break;
						case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE: 
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Run$txt_Number*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).
									split(",")[iBall] + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Choose_Type*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
							break;
						case CricketUtil.FOUR:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Four$txt_4*GEOM*TEXT SET " + CricketUtil.FOUR + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Choose_Type*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
							break;
						case CricketUtil.SIX:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Six$txt_6*GEOM*TEXT SET " + CricketUtil.SIX + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Choose_Type*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
							break;
						case CricketUtil.NINE:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Six$txt_6*GEOM*TEXT SET " + CricketUtil.NINE + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Choose_Type*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
							break;
						case "W":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Wicket$txt_W*GEOM*TEXT SET " + "W" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
									+ "$Balls$" + (iBall + 1) + "$Choose_Type*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
							break;

						default:
							
							if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().contains("BOUNDARY")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
										+ "$Balls$" + (iBall + 1) + "$Extra$txt_Extra*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
										+ "$Balls$" + (iBall + 1) + "$Choose_Type*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
							}else {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
										+ "$Balls$" + (iBall + 1) + "$Extra$txt_Extra*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase() + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
										+ "$Balls$" + (iBall + 1) + "$Choose_Type*FUNCTION*Omo*vis_con SET 5 \0", print_writers);
							}
							
							break;
						}
					}
					
//					if(this_data_str.get(this_data_str.size()-1).split(",").length > 6) {
//						if(infobar.getLast_this_over() != null && infobar.getLast_this_over().split(",").length > 6) {
//							if(infobar.isThisOvers_Title_Fade() == false) {
//								this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$This_Over_Title_Fade_Out", "START");
//								infobar.setThisOvers_Title_Fade(true);
//							}
//						}
//					} else {
//						if(infobar.getLast_this_over() != null && infobar.getLast_this_over().split(",").length <= 6) {
//							this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$This_Over_Title_Fade_Out", "CONTINUE REVERSE");
//							infobar.setThisOvers_Title_Fade(false);
//						}
//					}
					
					if(Integer.valueOf(CricketFunctions.processThisOverRunsCount(infobar.getLast_bowler().getPlayerId(),matchAllData.getEventFile().getEvents())
							.split(slashOrDash)[1]) > 0) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
								 + "$Balls*FUNCTION*Omo*vis_con SET " + (this_data_str.get(this_data_str.size()-1).split(",").length) + " \0", print_writers);
					}
					else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Stage2$Side"+ WhichSide + "$This_Over"
								 + "$Balls*FUNCTION*Omo*vis_con SET " + "0" + " \0", print_writers);
					}
					
					infobar.setLast_this_over(this_data_str.get(this_data_str.size()-1));
					break;
				}
				
				infobar.setLast_right_bottom(infobar.getRight_bottom());
			}
			break;	
		}
		return Constants.OK;
	}
	
	public static String TournamentFoursAndSixes(List<Tournament> past_tournament_stat) {
    	int fours=0;int sixes=0;
    	for(Tournament tn:past_tournament_stat) {
    		fours = fours + tn.getFours();
    		sixes =sixes + tn.getSixes();
    	}
		return String.valueOf(fours+","+sixes);
    	
    }
	
	public String populateVizInfobarRightSection(boolean is_this_updating,List<PrintWriter> print_writers, MatchAllData matchAllData,
			int WhichSide,int WhichSubSide) throws InterruptedException, CloneNotSupportedException 
		{	
			
			return Constants.OK;
		}
	
	public String populateVizInfobarLeftBottom(List<PrintWriter> print_writers, MatchAllData matchAllData,int WhichSide) 
	{
		
		return Constants.OK;
	}
	public String populateVizInfobarMiddleSection(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws InterruptedException, CloneNotSupportedException, IOException, JAXBException 
	{
		switch(config.getBroadcaster()) {
		case Constants.ISPL:
			switch(infobar.getMiddle_section().toUpperCase()) {
			case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER":
				if(infobar.isInfobar_on_screen()) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + (3-WhichSide) + 
							"$Select_Subline*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
							"$Select_Subline*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}
				PlayerProfileCareer(print_writers, matchAllData, WhichSide);
				break;
			case "BATTINGCARD":
				BattingCard(print_writers, matchAllData, WhichSide);
				break;
			case "BOWLINGCARD":
				BowlingCard(print_writers, matchAllData, WhichSide);
				break;
			case "HOWOUT":
				Howout(print_writers, matchAllData, WhichSide);
				break;
			case "BAT_THIS_MATCH":
				BatThisMatch(print_writers, matchAllData, WhichSide);
				break;
			case "BALL_THIS_MATCH":
				BallThisMatch(print_writers, matchAllData, WhichSide);
				break;
			case "SB_MATCH_PROMO": case "CURR_PARTNERSHIP": case "POINTS_TABLE": case "TEAM_FORMGUIDE": case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": 
			case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
				populateCurrentBatsmen(print_writers, matchAllData, WhichSide);
				populateVizInfobarBowler(print_writers, matchAllData, WhichSide);
				
				switch(infobar.getMiddle_section().toUpperCase()) {
				case "SB_MATCH_PROMO":
					MatchPromo(print_writers, matchAllData, WhichSide);
					break;
				case "CURR_PARTNERSHIP":
					Current_Partnership(print_writers, matchAllData, WhichSide);
					break;
				case "POINTS_TABLE":
					Points_Table(print_writers, matchAllData, WhichSide);
					break;
				case "TEAM_FORMGUIDE":
					Team_FormGuide(print_writers, matchAllData, WhichSide);
					break;
				case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": 
				case "LB_BEST_FIGURE": case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
					LeaderBoard(print_writers, matchAllData, WhichSide);
					break;
				}
				break;
			case "TARGET":
				Target(print_writers, matchAllData, WhichSide);
				break;
			case "IDENT":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$txt_Team1"
						+ "*GEOM*TEXT SET " + matchAllData.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$txt_Team2"
						+ "*GEOM*TEXT SET " + matchAllData.getSetup().getAwayTeam().getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$Select_Type"
						+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$txt_MatchNumber"
						+ "*GEOM*TEXT SET " + matchAllData.getSetup().getMatchIdent() + "\0", print_writers);
				break;
			
			case CricketUtil.BATSMAN:
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$txt_Team1"
						+ "*GEOM*TEXT SET " + matchAllData.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$txt_Team2"
						+ "*GEOM*TEXT SET " + matchAllData.getSetup().getAwayTeam().getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$Select_Type"
						+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$txt_MatchNumber"
						+ "*GEOM*TEXT SET " + matchAllData.getSetup().getMatchIdent() + "\0", print_writers);
				
				this.infobar.setMiddle_section(CricketUtil.BATSMAN);
				
				populateCurrentBatsmen(print_writers, matchAllData, WhichSide);
				populateVizInfobarBowler(print_writers, matchAllData, WhichSide);
				break;
			}
			break;	
		}
		return Constants.OK;
	}
	public String populateFullSection(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws Exception {
		switch(infobar.getFull_section().toUpperCase()) {
		
		case "THIS_OVER": case "THIS_OVER_RUNS":
			this_data_str = new ArrayList<String>();
			this_data_str.add(String.join(",",  new ArrayList<>(Arrays.asList(IndexController.MatchStats.getOverData().getThisOverTxt().split(",")))
			        .stream().map(s -> s.replace("WIDE", "WD")
			                   .replace("NO_BALL", "NB")
			                   .replace("LEG_BYE", "LB")
			                   .replace("BYE", "B")
			                   .replace("PENALTY", "PN")
			                   .replace("LOG_WICKET", "W")
			                   .replace("WICKET", "W"))
			        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {Collections.reverse(list); return list;}))
			        .toArray(new String[0])));
			
			int totalOverSize = 6;
			this_over_balls = totalOverSize;
			boolean extra_bowled = false;
			
			if(this_data_str.get(this_data_str.size()-1) == null) {
				return "populateVizInfobarRightBottom: This over data returned invalid";
			}
			
			for(int i=1; i<=12; i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
						WhichSide + "$ThisOver_All$ThisOverAll$Ball" + i + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
						WhichSide + "$ThisOver_All$ThisOverAll$Ball" + i + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);	
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
						WhichSide + "$ThisOver_All$ThisOverAll$Ball" + i + "$geom_Alpha*ALPHA*ALPHA SET 50\0", print_writers);
			}
			
			if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().isEmpty() && 
					infobar.getLast_full_section().equalsIgnoreCase("THIS_OVER_RUNS")) {
				
				if(this_data_str.get(this_data_str.size()-1).split(",").length <= 12) {
					if(this_data_str.get(this_data_str.size()-1).split(",").length == 1 && this_data_str.get(this_data_str.size()-1).split(",")[0].isEmpty()) {
						this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$CumullativeThisOverSmall", "CONTINUE");
						TimeUnit.MILLISECONDS.sleep(1000);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
							WhichSide + "$Select_Graphics*FUNCTION*Omo*vis_con SET 3\0", print_writers);
					
					if(this_data_str.get(this_data_str.size()-1).split(",").length == 1 && this_data_str.get(this_data_str.size()-1).split(",")[0].isEmpty()) {
						this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ThisOver", "START");
						CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$"
								+ "ThisOver*ANIMATION*KEY*$BOF1*VALUE SET " + "215" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$"
								+ "ThisOver*ANIMATION*KEY*$BOF2*VALUE SET " + "215" + "\0", print_writers);
						this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side1$Offset", "START");
						for(int i=1;i<=6;i++) {
							this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side1$Ball" + i, "START");
						}
					}
				}
				
				infobar.setLast_full_section("THIS_OVER");
				infobar.setFull_section("THIS_OVER");
			}
			
			if(infobar.getFull_section().equalsIgnoreCase("THIS_OVER")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
						WhichSide + "$Select_Graphics*FUNCTION*Omo*vis_con SET 3\0", print_writers);
			}
			
			for(int iBall = 0; iBall < this_data_str.get(this_data_str.size()-1).split(",").length; iBall++) {
				if(iBall < 12) {
					isThisOverLimitExceed = true;
					extra_bowled = false;
					switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
					case CricketUtil.DOT:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET 0\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;
					case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE:
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).
								split(",")[iBall] + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;
					case CricketUtil.FOUR:
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + CricketUtil.FOUR + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;
					case CricketUtil.SIX:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + CricketUtil.SIX + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;
					case CricketUtil.NINE:
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 3\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;	
					case "W":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 2\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
								WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
						break;

					default:
						if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().contains("BOUNDARY")) {
							if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().equalsIgnoreCase("6BOUNDARY")||
									this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().equalsIgnoreCase("4BOUNDARY")) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type$Boundaries$fig_Ball*GEOM*TEXT SET " + 
										this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().replace("BOUNDARY", "") + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
								
							}else if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase().equalsIgnoreCase("9BOUNDARY")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 3\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
							}
							else if(!this_data_str.get(this_data_str.size()-1).isEmpty()) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase() + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
								
								switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
								case "1B": case "2B": case "3B": case "4B": case "5B": case "6B":
								case "1LB": case "2LB": case "3LB": case "4LB": case "5LB": case "6LB":
									
									break;
	
								default:
									if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("NB") || 
											this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("WD")) {
										totalOverSize++;
										extra_bowled = true;
									}
									break;
								}
							}
						}else {
							
							if(!this_data_str.get(this_data_str.size()-1).split(",")[0].isEmpty()) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).
										split(",")[iBall].toUpperCase() + "\0", print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
										WhichSide + "$ThisOver_All$ThisOverAll$Ball" + (iBall + 1) + "$geom_Alpha*ALPHA*ALPHA SET 100\0", print_writers);
							}
							
							switch (this_data_str.get(this_data_str.size()-1).split(",")[iBall].toUpperCase()) {
							case "1B": case "2B": case "3B": case "4B": case "5B": case "6B":
							case "1LB": case "2LB": case "3LB": case "4LB": case "5LB": case "6LB":
								
								break;

							default:
								if(this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("NB") || 
										this_data_str.get(this_data_str.size()-1).split(",")[iBall].contains("WD")) {
									totalOverSize++;
									extra_bowled = true;
								}
								break;
							}
						}
						break;
					}
				}else {
					infobar.setFull_section("THIS_OVER_RUNS");
					if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().isEmpty()) {
						if(infobar.getLast_full_section().equalsIgnoreCase("THIS_OVER")) {
							if(cumm_runs) {
								this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ThisOver", "CONTINUE");
								TimeUnit.MILLISECONDS.sleep(1000);
							}
						}
					}
					
					for(BowlingCard boc : inning.getBowlingCard()) {
						switch (boc.getStatus().toUpperCase()) {
						case CricketUtil.CURRENT + CricketUtil.BOWLER:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
									WhichSide + "$CumullativeThisOverSmall$ThisOver$Head$txt_Head*GEOM*TEXT SET " + "THIS OVER" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
									WhichSide + "$CumullativeThisOverSmall$ThisOver$Stat1$txt_StatHead*GEOM*TEXT SET " + "RUNS" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
									WhichSide + "$CumullativeThisOverSmall$ThisOver$Stat1$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.
									processThisOverRunsCount(boc.getPlayerId(), matchAllData.getEventFile().getEvents()).split("-")[0] + "\0", print_writers);
							
							break;
						case CricketUtil.LAST + CricketUtil.BOWLER:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
									WhichSide + "$CumullativeThisOverSmall$ThisOver$Head$txt_Head*GEOM*TEXT SET " + "LAST OVER" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
									WhichSide + "$CumullativeThisOverSmall$ThisOver$Stat1$txt_StatHead*GEOM*TEXT SET " + "RUNS" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
									WhichSide + "$CumullativeThisOverSmall$ThisOver$Stat1$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.
									processThisOverRunsCount(boc.getPlayerId(), matchAllData.getEventFile().getEvents()).split("-")[0] + "\0", print_writers);
							
							break;
						}
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
							WhichSide + "$Select_Graphics*FUNCTION*Omo*vis_con SET 10\0", print_writers);
					
					if(infobar.getLast_full_section() != null && !infobar.getLast_full_section().isEmpty()) {
						if(infobar.getLast_full_section().equalsIgnoreCase("THIS_OVER")) {
							infobar.setLast_full_section("THIS_OVER_RUNS");
							if(cumm_runs) {
								TimeUnit.MILLISECONDS.sleep(500);
								this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$CumullativeThisOverSmall", "START");
								cumm_runs = false;
							}
						}
					}
				}
			}
			
			if(totalOverSize <= 12) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
						WhichSide + "$ThisOver_All$ThisOverAll$ThisOver*FUNCTION*Grid*num_col SET " + totalOverSize + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
						WhichSide + "$ThisOver_All$ThisOverAll$ThisOver*FUNCTION*Grid*num_col SET " + "12" + "\0", print_writers);
			}
			
			if(infobar.getLast_full_section() != "THIS_OVER") {
				String value1="";
				switch (totalOverSize) {
				case 6:
					value1 = "215";
					break;
				case 7:
					value1 = "180";
					break;
				case 8:
					value1 = "155";
					break;
				case 9:
					value1 = "130";
					break;
				default:
					value1 = "120";
					break;
				}
				
				CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$"
						+ "ThisOver*ANIMATION*KEY*$BOF1*VALUE SET " + value1 + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$"
						+ "ThisOver*ANIMATION*KEY*$BOF2*VALUE SET " + value1 + "\0", print_writers);
				this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "START");
			}
			
			switch (totalOverSize) {
			case 7:
				if(extra_bowled == true) {
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF1*VALUE SET 215\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "SHOW 0.0");
					
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF2*VALUE SET 180\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "START");
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Ball7", "START");
					extra_bowled = false;
				}
				break;
			case 8:
				if(extra_bowled == true) {
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF1*VALUE SET 180\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "SHOW 0.0");
					
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF2*VALUE SET 155\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "START");
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Ball8", "START");
					extra_bowled = false;
				}
				break;
			case 9:
				if(extra_bowled == true) {
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF1*VALUE SET 155\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "SHOW 0.0");
					
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF2*VALUE SET 135\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "START");
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Ball9", "START");
					extra_bowled = false;
				}
				break;
			case 10:
				if(extra_bowled == true) {
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF1*VALUE SET 135\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "SHOW 0.0");
					
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF2*VALUE SET 120\0", print_writers);
					
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "START");
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Ball10", "START");
					extra_bowled = false;
				}
				break;
			case 11: case 12: 
				if(extra_bowled == true) {
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF1*VALUE SET 120\0", print_writers);
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "SHOW 0.0");
					
					CricketFunctions.DoadWriteCommandToSelectedViz(1, "-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$AllData$Side" + WhichSide + "$ThisOver_All$ThisOverAll$ThisOver"
							+ "*ANIMATION*KEY*$BOF2*VALUE SET 120\0", print_writers);
					
					this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Offset", "START");
					switch (totalOverSize) {
					case 11:
						this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Ball11", "START");
						break;
					case 12:
						this_animation.processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side" + WhichSide + "$Ball12", "START");
						break;
					}
					extra_bowled = false;
				}
				break;
			
			}
			
			this_over_balls = totalOverSize;
			infobar.setLast_this_over(this_data_str.get(this_data_str.size()-1));
			
			break;
			
		case "LAST_X_BALLS": case "LAST_X_BALLS_WITHOUT_CRR":
			
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> 
				inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			
			if(inning == null) {
				return "populateVizInfobarLeftBottom: Inning return is NULL";
			}
			
			this_data_str = new ArrayList<String>();
			this_data_str.add(CricketFunctions.getlastthirtyballsdata(matchAllData, slashOrDash, matchAllData.getEventFile().getEvents(), lastXballs));
			
			if(this_data_str.get(this_data_str.size()-1) == null || this_data_str.get(this_data_str.size()-1).split(slashOrDash).length > 4) {
				return "populateVizInfobarMiddleSection: Last " + lastXballs + " Balls data returned invalid";
			}
			
			if(infobar.getFull_section().equalsIgnoreCase("LAST_X_BALLS")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Select_Graphics*FUNCTION*Omo*vis_con SET 4\0", print_writers);
				
				int total_runs=0;
				if(inning.getSpecialRuns() != null && !inning.getSpecialRuns().isEmpty()) {
					if(inning.getSpecialRuns().startsWith("+")) {
						total_runs = (inning.getTotalRuns() + Integer.valueOf(inning.getSpecialRuns().replace("+", "")));
					}else if(inning.getSpecialRuns().startsWith("-")) {
						total_runs = (inning.getTotalRuns() - Integer.valueOf(inning.getSpecialRuns().replace("-", "")));
					}
				}else {
					total_runs = inning.getTotalRuns();
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$RunRateInnings1$RUNRATE$txt_StatHead*GEOM*TEXT SET " + "CRR" + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$RunRateInnings1$RUNRATE$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.generateRunRate(total_runs, inning.getTotalOvers(), 
								inning.getTotalBalls(), 2,matchAllData) + "\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$RunRateInnings1$Head$txt_Header2*GEOM*TEXT SET " + lastXballs + " BALLS" + "\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$RunRateInnings1$Stat1$txt_StatHead*GEOM*TEXT SET " + "RUN" + CricketFunctions.Plural(Integer.valueOf(this_data_str.
								get(this_data_str.size()-1).split(slashOrDash)[0])).toUpperCase() + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$RunRateInnings1$Stat1$txt_StatValue*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).split(slashOrDash)[0] + "\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$RunRateInnings1$Stat2$txt_StatHead*GEOM*TEXT SET " + "WICKET" + CricketFunctions.Plural(Integer.valueOf(this_data_str.
								get(this_data_str.size()-1).split(slashOrDash)[1])).toUpperCase() + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$RunRateInnings1$Stat2$txt_StatValue*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).split(slashOrDash)[1] + "\0",print_writers);
			}
			else if(infobar.getFull_section().equalsIgnoreCase("LAST_X_BALLS_WITHOUT_CRR")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Select_Graphics*FUNCTION*Omo*vis_con SET 15\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$LastX$LastXAll$Head$txt_Head*GEOM*TEXT SET LAST " + lastXballs + " BALLS" + "\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$LastX$LastXAll$Name$txt_Runs*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).split(slashOrDash)[0] + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$LastX$LastXAll$Name$txt_FirstName*GEOM*TEXT SET " + "RUN" + CricketFunctions.Plural(Integer.valueOf(this_data_str.
								get(this_data_str.size()-1).split(slashOrDash)[0])).toUpperCase() + "\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$LastX$LastXAll$Name$txt_Wickets*GEOM*TEXT SET " + this_data_str.get(this_data_str.size()-1).split(slashOrDash)[1] + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$LastX$LastXAll$Name$txt_LastName*GEOM*TEXT SET " + "WICKET" + CricketFunctions.Plural(Integer.valueOf(this_data_str.
								get(this_data_str.size()-1).split(slashOrDash)[1])).toUpperCase() + "\0",print_writers);
			}
			
			break;
		case "RRR":
			
			if(!matchAllData.getMatch().getInning().get(1).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
				return "populateVizInfobarLeftBottom: Required run rate available in 2nd inning only";
			}
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
					WhichSide + "$Select_Graphics*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Head$txt_Head*GEOM*TEXT SET " + "RUN RATES" + "\0",print_writers);
			int total_runs=0;
			if(inning.getSpecialRuns() != null && !inning.getSpecialRuns().isEmpty()) {
				if(inning.getSpecialRuns().startsWith("+")) {
					total_runs = (inning.getTotalRuns() + Integer.valueOf(inning.getSpecialRuns().replace("+", "")));
				}else if(inning.getSpecialRuns().startsWith("-")) {
					total_runs = (inning.getTotalRuns() - Integer.valueOf(inning.getSpecialRuns().replace("-", "")));
				}
			}else {
				total_runs = inning.getTotalRuns();
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Stat1$txt_StatHead*GEOM*TEXT SET " + "CURRENT" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Stat1$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.generateRunRate(total_runs, 
							inning.getTotalOvers(), inning.getTotalBalls(), 2,matchAllData) + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Stat2$txt_StatHead*GEOM*TEXT SET " + "REQUIRED" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Stat2$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(matchAllData).getRemaningRuns(),
							0, CricketFunctions.GetTargetData(matchAllData).getRemaningBall(), 2, matchAllData) + "\0",print_writers);
			break;
			
		case "REVIEWS_REMAINING":
			Review reviewRemaining = CricketFunctions.getReviewRemaining(matchAllData);
			String[] parts = reviewRemaining.getReviewStatus().split(",");
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
					WhichSide + "$Select_Graphics*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Head$txt_Head*GEOM*TEXT SET " + "REVIEWS REMAINING" + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Stat1$txt_StatHead*GEOM*TEXT SET " + matchAllData.getSetup().getHomeTeam().getTeamName4() + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Stat1$txt_StatValue*GEOM*TEXT SET " + Integer.parseInt(parts[0]) + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Stat2$txt_StatHead*GEOM*TEXT SET " + matchAllData.getSetup().getAwayTeam().getTeamName4() + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$RunRates_All$RunRates$Stat2$txt_StatValue*GEOM*TEXT SET " + Integer.parseInt(parts[1]) + "\0",print_writers);
			break;
			
		case "LINE_UP":
			
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getBattingTeamId() == team_id).findAny().orElse(null);
			if(inning == null) {
				return status;
			}
			if(matchAllData.getSetup().getHomeTeamId() == team_id) {
				team = matchAllData.getSetup().getHomeTeam();
				player_XI = matchAllData.getSetup().getHomeSquad();
			}else {
				team = matchAllData.getSetup().getAwayTeam();
				player_XI = matchAllData.getSetup().getAwaySquad();
			}
			
			if(WhichProfile.equalsIgnoreCase("ROLES")) {
				//--------------------
				
				String MatchFileName = null;
				PlayerId = new ArrayList<Integer>();
				PlayerIdIn = new ArrayList<Integer>();
				//System.out.println(headToHead.size());
				if(headToHead.size() > 1) {
					for (int i = headToHead.size() - 1; i >= 0; i--) {
					    if (headToHead.get(i).getTeam().getTeamId() == team.getTeamId()) {
					    	if (MatchFileName == null) {
					    		MatchFileName = headToHead.get(i).getMatchFileName(); 
					        }
					        if (!headToHead.get(i).getMatchFileName().equalsIgnoreCase(MatchFileName)) {
					            break;
					        }
					    }
					}
					if (MatchFileName == null) {
						MatchFileName = matchAllData.getMatch().getMatchFileName();
			        }
				}else {
					MatchFileName = matchAllData.getMatch().getMatchFileName();
				}
				
				if(MatchFileName != null) {
					Setup setup = new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + 
							MatchFileName), Setup.class);
					
					for(Player headToHead : (setup.getHomeTeamId()==team_id ? setup.getHomeSquad():setup.getAwaySquad())) {
						boolean playerFound = false;
						for (Player ply : player_XI) {
				    	    if(ply.getPlayerId() == headToHead.getPlayerId()) {
				    	    	playerFound = true;
				    	    	break;
				    	    }
				    	}
				        if (!playerFound) {  
				        	PlayerId.add(headToHead.getPlayerId());
				        }else {
				        	PlayerIdIn.add(headToHead.getPlayerId());
				        }
					}
				}
				//System.out.println("IN - " + PlayerIdIn.toString());
				//System.out.println("OUT - " + PlayerId.toString());
				//-----------------------
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + 
					WhichSide + "$Select_Graphics*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			//Color
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Playing11_All$Name$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + team.getTeamName4() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Playing11_All$TeamName$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + team.getTeamName4() + "\0", print_writers);
			
			for(int i=1; i<=11; i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Playing11_All$PlayerList$" + i + "$Data$DeHighlight$Name$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + team.getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Playing11_All$PlayerList$" + i + "$Data$DeHighlight$Name$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + team.getTeamName4() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$DeHighlight$Data$LastName$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + team.getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$DeHighlight$Data$LastName$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + team.getTeamName4() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$DeHighlight$Data$Stat$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + team.getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$DeHighlight$Data$Stat$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + team.getTeamName4() + "\0", print_writers);
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$Highlight$Name$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + team.getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$Highlight$Name$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + team.getTeamName4() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$Highlight$Data$LastName$img_Base1*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE1 + team.getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$Highlight$Data$LastName$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + team.getTeamName4() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$Highlight$Data$Stat$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + team.getTeamName4() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Data$Highlight$Data$Stat$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + team.getTeamName4() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Playing11_All$PlayerList$" + i + "$Image$SelectInOutIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
			
			rowId =0;
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Playing11_All$TeamName$txt_TeamFirstName*GEOM*TEXT SET " + team.getTeamName2() + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Playing11_All$TeamName$txt_TeamLastName*GEOM*TEXT SET " + team.getTeamName3() + "\0",print_writers);
			
			if(WhichProfile.equalsIgnoreCase("BATTING_CARD")) {
				Collections.sort(inning.getBattingCard());
				for (BattingCard bc : inning.getBattingCard()) {
					rowId = rowId + 1;
					
					if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						omo = 1;
						containerName = "Highlight";
					}else {
						omo = 0;
						containerName = "DeHighlight";
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Playing11_All$PlayerList$" + rowId + "$Data$Select_Style*FUNCTION*Omo*vis_con SET " + omo + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Playing11_All$PlayerList$" + rowId + "$Data$" + containerName + "$Data$Select_Style*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
							+ "PlayerList$" + rowId + "$Data$" + containerName + "$Name$txt_Name*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0",print_writers);
					
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
							+ "PlayerList$" + rowId + "$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + team.getTeamName4() + Constants.SMALL_512
							+ bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
							+ "PlayerList$" + rowId + "$Image$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH 
							+ team.getTeamName4() + Constants.SMALL_512 + bc.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Playing11_All$PlayerList$" + rowId + "$Image$Position_Y$InOut$Select_InOutIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Playing11_All$PlayerList$" + rowId + "$Image$Position_Y$Googly$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bc.getPlayerId()).isEmpty()) {
						switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bc.getPlayerId())) {
						case "IMP_IN":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
									"$Playing11_All$PlayerList$" + rowId + "$Image$Position_Y$Googly$select_GooglyImpact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							break;
						case "IMP_OUT":
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								if(bc.getBalls() == 0) {
									rowId = rowId - 1;
								}
							}
							break;
						}
					}
					
					if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
								+ "PlayerList$" + rowId + "$Data$" + containerName + "$img_Text2$Select_DataStyle*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						if (inning.getInningStatus().equalsIgnoreCase(CricketUtil.START)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Data$" + containerName + "$img_Text2$txt_Info*GEOM*TEXT SET " + "IN AT " + rowId + "\0",print_writers);
						} else if (inning.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Data$" + containerName + "$img_Text2$txt_Info*GEOM*TEXT SET " + "DNB" + "\0",print_writers);
						}
					}else if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
								+ "PlayerList$" + rowId + "$Data$" + containerName + "$Data$Stat$img_Text2$Select_DataStyle*FUNCTION*Omo*vis_con SET 3\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
								+ "PlayerList$" + rowId + "$Data$" + containerName + "$Data$Stat$img_Text2$Score_NotOut$txt_Run*GEOM*TEXT SET " + bc.getRuns() + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
								+ "PlayerList$" + rowId + "$Data$" + containerName + "$Data$Stat$img_Text2$Score_NotOut$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + "\0",print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
								+ "PlayerList$" + rowId + "$Data$" + containerName + "$Data$Stat$img_Text2$Select_DataStyle*FUNCTION*Omo*vis_con SET 2\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
								+ "PlayerList$" + rowId + "$Data$" + containerName + "$Data$Stat$img_Text2$Score_Out$txt_Run*GEOM*TEXT SET " + bc.getRuns() + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
								+ "PlayerList$" + rowId + "$Data$" + containerName + "$Data$Stat$img_Text2$Score_Out$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + "\0",print_writers);
					}
					
					if(bc.getPlayer().getRole().equalsIgnoreCase(CricketUtil.BATSMAN) || bc.getPlayer().getRole().equalsIgnoreCase("BAT/KEEPER")) {
						if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("RHB")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Batsman_RightHand" + "\0", print_writers);
						}else if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Batsman_Lefthand" + "\0", print_writers);
						}
					}
					else if(bc.getPlayer().getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if(bc.getPlayer().getBowlingStyle() == null) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_Bowler" + "\0", print_writers);
						}else {
							switch(bc.getPlayer().getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_Bowler" + "\0", print_writers);
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Off_Spinner" + "\0", print_writers);
								break;
							}
						}
					}
					else if(bc.getPlayer().getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(bc.getPlayer().getBowlingStyle() == null) {
							if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("RHB")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_BowlerAllrounerRightHand" + "\0", print_writers);
							}else if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_BowlerAllrounerLeftHand" + "\0", print_writers);
							}
						}else {
							switch(bc.getPlayer().getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("RHB")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
											+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_BowlerAllrounerRightHand" + "\0", print_writers);
								}else if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
											+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_BowlerAllrounerLeftHand" + "\0", print_writers);
								}
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("RHB")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
											+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Off_SpinnerAllrounderRightHand" + "\0", print_writers);
								}else if(bc.getPlayer().getBattingStyle().equalsIgnoreCase("LHB")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
											+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Off_SpinnerAllrounderLeftHand" + "\0", print_writers);
								}
								break;
							}
						}
					}
					
					player = player_XI.stream().filter(plyr -> plyr.getPlayerId() == bc.getPlayerId()).findAny().orElse(null);
					
					if(player != null) {
						if(player.getCaptainWicketKeeper() != null && !player.getCaptainWicketKeeper().isEmpty()) {
							if (player.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
										"$Playing11_All$PlayerList$" + rowId + "$Image$CaptainIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							} else if (player.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
										"$Playing11_All$PlayerList$" + rowId + "$Image$CaptainIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "WicketKeeper" + "\0", print_writers);
							} else if (player.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
										"$Playing11_All$PlayerList$" + rowId + "$Image$CaptainIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "WicketKeeper" + "\0", print_writers);
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
									"$Playing11_All$PlayerList$" + rowId + "$Image$CaptainIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						}
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
								"$Playing11_All$PlayerList$" + rowId + "$Image$CaptainIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					}
				}
			}else if(WhichProfile.equalsIgnoreCase("ROLES")) {
				rowId = 0;
				for(Player plyr : player_XI) {
					rowId = rowId + 1;
					
					omo = 0;
					containerName = "DeHighlight";
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Playing11_All$PlayerList$" + rowId + "$Data$Select_Style*FUNCTION*Omo*vis_con SET " + omo + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Playing11_All$PlayerList$" + rowId + "$Data$" + containerName + "$Data$Select_Style*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
							+ "PlayerList$" + rowId + "$Data$" + containerName + "$Name$txt_Name*GEOM*TEXT SET " + plyr.getFirstname() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
							+ "PlayerList$" + rowId + "$Data$" + containerName + "$Data$LastName$txt_LastName*GEOM*TEXT SET " + (plyr.getSurname() != null ? 
									plyr.getSurname() : "") + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Playing11_All$PlayerList$" + rowId + "$Image$Position_Y$InOut$Select_InOutIcon*FUNCTION*Omo*vis_con SET " + 
							(!PlayerIdIn.contains(plyr.getPlayerId())?"2":"0") + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Playing11_All$PlayerList$" + rowId + "$Image$Position_Y$Googly$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					
					if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
							+ "PlayerList$" + rowId + "$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + team.getTeamName4() + Constants.SMALL_512 + plyr.getPhoto() 
								+ CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
							+ "PlayerList$" + rowId + "$Image$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH  
							+ team.getTeamName4() + Constants.SMALL_512 + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
					
					if(plyr.getRole().equalsIgnoreCase(CricketUtil.BATSMAN) || plyr.getRole().equalsIgnoreCase("BAT/KEEPER")) {
						if(plyr.getBattingStyle().equalsIgnoreCase("RHB")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Batsman_RightHand" + "\0", print_writers);
						}else if(plyr.getBattingStyle().equalsIgnoreCase("LHB")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Batsman_Lefthand" + "\0", print_writers);
						}
					}
					else if(plyr.getRole().equalsIgnoreCase(CricketUtil.BOWLER)) {
						if(plyr.getBowlingStyle() == null) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_Bowler" + "\0", print_writers);
						}else {
							switch(plyr.getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_Bowler" + "\0", print_writers);
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Off_Spinner" + "\0", print_writers);
								break;
							}
						}
					}
					else if(plyr.getRole().equalsIgnoreCase("ALL-ROUNDER")) {
						if(plyr.getBowlingStyle() == null) {
							if(plyr.getBattingStyle().equalsIgnoreCase("RHB")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_BowlerAllrounerRightHand" + "\0", print_writers);
							}else if(plyr.getBattingStyle().equalsIgnoreCase("LHB")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
										+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_BowlerAllrounerLeftHand" + "\0", print_writers);
							}
						}else {
							switch(plyr.getBowlingStyle()) {
							case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
								if(plyr.getBattingStyle().equalsIgnoreCase("RHB")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
											+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_BowlerAllrounerRightHand" + "\0", print_writers);
								}else if(plyr.getBattingStyle().equalsIgnoreCase("LHB")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
											+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Pace_BowlerAllrounerLeftHand" + "\0", print_writers);
								}
								break;
							case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
								if(plyr.getBattingStyle().equalsIgnoreCase("RHB")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
											+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Off_SpinnerAllrounderRightHand" + "\0", print_writers);
								}else if(plyr.getBattingStyle().equalsIgnoreCase("LHB")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
											+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Off_SpinnerAllrounderLeftHand" + "\0", print_writers);
								}
								break;
							}
						}
					}
					
					if(plyr.getCaptainWicketKeeper() != null && !plyr.getCaptainWicketKeeper().isEmpty()) {
						if (plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
									"$Playing11_All$PlayerList$" + rowId + "$Image$CaptainIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						} else if (plyr.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
									"$Playing11_All$PlayerList$" + rowId + "$Image$CaptainIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "WicketKeeper" + "\0", print_writers);
						} else if (plyr.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
									"$Playing11_All$PlayerList$" + rowId + "$Image$CaptainIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Playing11_All$"
									+ "PlayerList$" + rowId + "$Image$Styleicon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "WicketKeeper" + "\0", print_writers);
						}
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
								"$Playing11_All$PlayerList$" + rowId + "$Image$CaptainIcon$Select_CaptainIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					}
				}
			}
			break;
			
		case "BALLS_SINCE_LAST_BOUNDARY":
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> 
				inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
		
			if(inning == null) {
				return "populateVizInfobarMiddleSection: Inning returned is NULL";
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 12 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$BallSince$ThisOver$Head$txt_Text*GEOM*TEXT SET " + "BALL" + CricketFunctions.Plural(Integer.valueOf(CricketFunctions.
					lastFewOversData(CricketUtil.BOUNDARY, matchAllData.getEventFile().getEvents(), inning.getInningNumber()))).toUpperCase()
						+ " SINCE LAST BOUNDARY" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$BallSince$ThisOver$Head$txt_StatValue*GEOM*TEXT SET " + CricketFunctions.lastFewOversData(CricketUtil.BOUNDARY, 
							matchAllData.getEventFile().getEvents(), inning.getInningNumber()) + "\0", print_writers);
			break;
				
		case CricketUtil.BOUNDARY:
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "populateVizInfobarMiddleSection: Inning returned is NULL";
			}
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 16 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Boundaries$BoundariesAll$Head$txt_Head*GEOM*TEXT SET " + "BOUNDARIES" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Boundaries$BoundariesAll"
					+ "$Stat1$txt_StatHead*GEOM*TEXT SET " + "FOURS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Boundaries$BoundariesAll"
					+ "$Stat1$txt_StatValue*GEOM*TEXT SET " + inning.getTotalFours() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Boundaries$BoundariesAll"
					+ "$Stat2$txt_StatHead*GEOM*TEXT SET " + "SIXES" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Boundaries$BoundariesAll"
					+ "$Stat2$txt_StatValue*GEOM*TEXT SET " + inning.getTotalSixes() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Boundaries$BoundariesAll"
					+ "$Stat3$txt_StatHead*GEOM*TEXT SET " + "NINES" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$Boundaries$BoundariesAll"
					+ "$Stat3$txt_StatValue*GEOM*TEXT SET " + inning.getTotalNines() + "\0", print_writers);
			
			break;
		case "COMMENTATORS":
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 9\0", print_writers);
			
			if(Integer.valueOf(Comms_Name.split(",")[4]) > 0 && Integer.valueOf(Comms_Name.split(",")[3]) > 0 
					&& Integer.valueOf(Comms_Name.split(",")[2]) > 0) {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$GenericText1Line$"
						+ "Generic$txt_Text1*GEOM*TEXT SET " + "COMMENTATORS: " + Commentators.get(Integer.valueOf(Comms_Name.split(",")[2])-1).getCommentatorName() + ", " 
						+ Commentators.get(Integer.valueOf(Comms_Name.split(",")[3])-1).getCommentatorName() + " & " 
						+ Commentators.get(Integer.valueOf(Comms_Name.split(",")[4])-1).getCommentatorName() + "\0", print_writers);
				
			}else if(Integer.valueOf(Comms_Name.split(",")[4]) == 0 && Integer.valueOf(Comms_Name.split(",")[3]) > 0 
					&& Integer.valueOf(Comms_Name.split(",")[2]) > 0) {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$GenericText1Line$"
						+ "Generic$txt_Text1*GEOM*TEXT SET " + "COMMENTATORS: " + Commentators.get(Integer.valueOf(Comms_Name.split(",")[2])-1).getCommentatorName() + " & " + 
						Commentators.get(Integer.valueOf(Comms_Name.split(",")[3])-1).getCommentatorName() + "\0", print_writers);
				
			}else if(Integer.valueOf(Comms_Name.split(",")[4]) == 0 && Integer.valueOf(Comms_Name.split(",")[3]) == 0 
					&& Integer.valueOf(Comms_Name.split(",")[2]) > 0) {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$GenericText1Line$"
						+ "Generic$txt_Text1*GEOM*TEXT SET " + "COMMENTATORS: " + Commentators.get(Integer.valueOf(Comms_Name.split(",")[2])-1).getCommentatorName() 
						+ "\0", print_writers);
			}
			break;
		
		case "FREE_TEXT":
			infoBarStats = infobarStats.stream().filter(infostats -> infostats.getOrder() == infobarStatsId).findAny().orElse(null);
			if(infoBarStats == null) {
				return "InfoBarFreeText: Stats  not found for [" + infobarStatsId + "]";
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 9\0", print_writers);
				
			if(infoBarStats.getText1() != null && !infoBarStats.getText1().isEmpty() &&
					infoBarStats.getText2() != null && !infoBarStats.getText2().isEmpty()) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$GenericText1Line$"
						+ "Generic$txt_Text1*GEOM*TEXT SET " + infoBarStats.getText1() + " " + infoBarStats.getText2() + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$GenericText1Line$"
						+ "Generic$txt_Text1*GEOM*TEXT SET " + infoBarStats.getText1() + "\0", print_writers);
			}
			break;
		case CricketUtil.TOSS:
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 9\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$GenericText1Line$"
					+ "Generic$txt_Text1*GEOM*TEXT SET " + CricketFunctions.generateTossResult(matchAllData, CricketUtil.FULL, CricketUtil.FIELD, CricketUtil.SHORT, 
							CricketUtil.CHOSE).toUpperCase().replace("TOSS", "TENX-U TIP TOP TOSS") + "\0", print_writers);			
			break;
			
		case CricketUtil.EXTRAS:
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "populateVizInfobarMiddleSection: Inning returned is NULL";
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 14 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$Head$txt_Head*GEOM*TEXT SET " + "EXTRAS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$Head$txt_ExtrasTotal*GEOM*TEXT SET " + (inning.getTotalWides() + inning.getTotalNoBalls() + 
							inning.getTotalByes() + inning.getTotalLegByes() + inning.getTotalPenalties()) + "\0", print_writers);
			
			if(inning.getTotalPenalties() == 0) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
						+ "$Extras$ExtrastAll$ExtraData$Select_Number*FUNCTION*Omo*vis_con SET 4\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
						+ "$Extras$ExtrastAll$ExtraData$Select_Number*FUNCTION*Omo*vis_con SET 5\0", print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$1$txt_Head*GEOM*TEXT SET " + "WB" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$2$txt_Head*GEOM*TEXT SET " + "NB" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$3$txt_Head*GEOM*TEXT SET " + "B" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$4$txt_Head*GEOM*TEXT SET " + "LB" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$5$txt_Head*GEOM*TEXT SET " + "P" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$1$txt_Runs*GEOM*TEXT SET " + inning.getTotalWides() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$2$txt_Runs*GEOM*TEXT SET " + inning.getTotalNoBalls() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$3$txt_Runs*GEOM*TEXT SET " + inning.getTotalByes() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$4$txt_Runs*GEOM*TEXT SET " + inning.getTotalLegByes() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Extras$ExtrastAll$ExtraData$5$txt_Runs*GEOM*TEXT SET " + inning.getTotalPenalties() + "\0", print_writers);
			
			break;
		case "LAST_WICKET":

			inning = matchAllData.getMatch().getInning().stream().filter(inn -> 
				inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			
			String how_out_txt = "";
			
			if(inning == null) {
				return "populateVizInfobarMiddleSection: Inning returned is NULL";
			}
			
			if(inning.getFallsOfWickets() == null && inning.getFallsOfWickets().isEmpty()) {
				return "populateVizInfobarMiddleSection: FoW returned is EMPTY";
			}
			
			battingCardList.add(inning.getBattingCard().stream().filter(bc -> bc.getPlayerId() == 
				inning.getFallsOfWickets().get(inning.getFallsOfWickets().size() - 1).getFowPlayerID()).findAny().orElse(null));

			if(battingCardList.get(battingCardList.size()-1) == null) {
				return "populateVizInfobarLeftBottom: Last wicket returned is invalid";
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 13 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$LastWicket$LastWicketAll$Head$txt_Head*GEOM*TEXT SET " + "LAST WICKET" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$LastWicket$LastWicketAll$"
					+ "Name$txt_FirstName*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getPlayer().getFirstname() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$LastWicket$LastWicketAll$"
					+ "Name$txt_LastName*GEOM*TEXT SET " + (battingCardList.get(battingCardList.size()-1).getPlayer().getSurname() != null ? 
							battingCardList.get(battingCardList.size()-1).getPlayer().getSurname() : "") + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$LastWicket$LastWicketAll$"
					+ "Score$txt_Runs*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getRuns() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$LastWicket$LastWicketAll$"
					+ "Score$txt_Balls*GEOM*TEXT SET OFF " + battingCardList.get(battingCardList.size()-1).getBalls() + "\0", print_writers);
			
			if(battingCardList.get(battingCardList.size()-1).getHowOut().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
				if(battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute() != null && 
						battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
					how_out_txt = "run out " + "sub (" + battingCardList.get(battingCardList.size()-1).getHowOutFielder().getTicker_name() + ")";
				} else {
					how_out_txt = "run out (" + battingCardList.get(battingCardList.size()-1).getHowOutFielder().getTicker_name() + ")";
				}
			}
			else if(battingCardList.get(battingCardList.size()-1).getHowOut().equalsIgnoreCase(CricketUtil.CAUGHT)) {
				if(battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute() != null && 
						battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
					how_out_txt = "c" +  " sub (" + battingCardList.get(battingCardList.size()-1).getHowOutFielder().getTicker_name() + ")  b " + 
							battingCardList.get(battingCardList.size()-1).getHowOutBowler().getTicker_name();
				} else {
					how_out_txt = "c " + battingCardList.get(battingCardList.size()-1).getHowOutFielder().getTicker_name() + "  b " + 
							battingCardList.get(battingCardList.size()-1).getHowOutBowler().getTicker_name();
				}
			}else if(battingCardList.get(battingCardList.size()-1).getHowOut().equalsIgnoreCase(CricketUtil.STUMPED)) {
				if(battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute() != null && 
						battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
					how_out_txt = "st" +  " sub (" + battingCardList.get(battingCardList.size()-1).getHowOutFielder().getTicker_name() + ")  b " + 
							battingCardList.get(battingCardList.size()-1).getHowOutBowler().getTicker_name();
				} else {
					how_out_txt = "st " + battingCardList.get(battingCardList.size()-1).getHowOutFielder().getTicker_name() + "  b " + 
							battingCardList.get(battingCardList.size()-1).getHowOutBowler().getTicker_name();
				}
			}else {
				if(!battingCardList.get(battingCardList.size()-1).getHowOutPartOne().isEmpty()) {
					how_out_txt = battingCardList.get(battingCardList.size()-1).getHowOutPartOne();
				}
				
				if(!battingCardList.get(battingCardList.size()-1).getHowOutPartTwo().isEmpty()) {
					if(!how_out_txt.trim().isEmpty()) {
						how_out_txt = how_out_txt + "  " + battingCardList.get(battingCardList.size()-1).getHowOutPartTwo();
					}else {
						how_out_txt = battingCardList.get(battingCardList.size()-1).getHowOutPartTwo();
					}
				}
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$LastWicket$"
					+ "LastWicketAll$txt_HowOut*GEOM*TEXT SET " + how_out_txt + "\0", print_writers);
			break;
		case "EQUATION_SHORT_SB":
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "populateVizInfobarMiddleSection: 1st Inning returned is NULL";
			}
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 19 \0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$ShortEquation$DataGrp$fig_Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(matchAllData).getRemaningRuns() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$ShortEquation$DataGrp$fig_Balls*GEOM*TEXT SET " + CricketFunctions.GetTargetData(matchAllData).getRemaningBall() + "\0", print_writers);
			break;
		case CricketUtil.PROJECTED:
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == 1 &&
				inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			
			if(inning == null) {
				return "populateVizInfobarMiddleSection: 1st Inning returned is NULL";
			}
			
			this_data_str = CricketFunctions.projectedScore(matchAllData);
			if(this_data_str.size() <= 0) {
				return "populateVizInfobarMiddleSection: Projected score invalid";
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$ProjectedScore_All$Projected$Head$txt_Head*GEOM*TEXT SET " + "PROJECTED SCORES" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$ProjectedScore_All$Projected"
					+ "$Stat1$txt_StatHead*GEOM*TEXT SET @CRR (" + this_data_str.get(0) + ")" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$ProjectedScore_All$Projected"
					+ "$Stat1$txt_StatValue*GEOM*TEXT SET " + this_data_str.get(1) + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$ProjectedScore_All$Projected"
					+ "$Stat2$txt_StatHead*GEOM*TEXT SET @" + this_data_str.get(2) + " RPO\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$ProjectedScore_All$Projected"
					+ "$Stat2$txt_StatValue*GEOM*TEXT SET " + this_data_str.get(3) + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$ProjectedScore_All$Projected"
					+ "$Stat3$txt_StatHead*GEOM*TEXT SET @" + this_data_str.get(4) + " RPO\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$ProjectedScore_All$Projected"
					+ "$Stat3$txt_StatValue*GEOM*TEXT SET " + this_data_str.get(5) + "\0", print_writers);
			
			break;
		case CricketUtil.RESULT:
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "populateVizInfobarMiddleSection: 1st Inning returned is NULL";
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 7 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Equation$DataGrp$Side1$Select_DataStyle*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			
			if(!CricketFunctions.GenerateMatchSummaryStatus(inning.getInningNumber(), matchAllData, CricketUtil.FULL, "", config.getBroadcaster(), true).getTargetOrResult().toUpperCase().contains(" TIED")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Equation$DataGrp$txt_ResultText*GEOM*TEXT SET " + CricketFunctions.GenerateMatchSummaryStatus(inning.getInningNumber(), 
							matchAllData, CricketUtil.FULL, "", config.getBroadcaster(), true).getTargetOrResult().toUpperCase() + "\0", print_writers);
			}else {
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Equation$DataGrp$txt_ResultText*GEOM*TEXT SET " + "SUPER OVER TIED - ANOTHER SUPER OVER TO FOLLOW" + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
							"$Equation$DataGrp$txt_ResultText*GEOM*TEXT SET " + "MATCH TIED - SUPER OVER TO FOLLOW" + "\0", print_writers);
				}
			}
			break;
		case "EQUATION":
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "populateVizInfobarMiddleSection: 1st Inning returned is NULL";
			}
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 7 \0", print_writers);
			
			if(CricketFunctions.GetTargetData(matchAllData).getRemaningBall() > 6) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
						+ "$Equation$DataGrp$Side1$Select_DataStyle*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Equation$DataGrp$txt_Equation*GEOM*TEXT SET " + "REQUIRED RUN RATE " + CricketFunctions.generateRunRate(CricketFunctions.GetTargetData(matchAllData).
							getRemaningRuns(),0, CricketFunctions.GetTargetData(matchAllData).getRemaningBall(), 2, matchAllData) + "\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Equation$DataGrp$fig_Runs*GEOM*TEXT SET " + CricketFunctions.GetTargetData(matchAllData).getRemaningRuns() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Equation$DataGrp$txt_Runs*GEOM*TEXT SET " + " OFF" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Equation$DataGrp$fig_Balls*GEOM*TEXT SET " + CricketFunctions.GetTargetData(matchAllData).getRemaningBall() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
						"$Equation$DataGrp$txt_Balls*GEOM*TEXT SET " + (matchAllData.getSetup().getTargetType() != null && !matchAllData.getSetup().getTargetType().isEmpty() 
							? " (" + matchAllData.getSetup().getTargetType().toUpperCase() +")":"") + "\0", print_writers);

			}
			else if(CricketFunctions.GetTargetData(matchAllData).getRemaningBall() <= 6){
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
						+ "$Equation$DataGrp$Side1$Select_DataStyle*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
						+ "$Equation$DataGrp$txt_ResultText*GEOM*TEXT SET " + "NEED " + CricketFunctions.GetTargetData(matchAllData).getRemaningRuns() + " OFF " 
						+ CricketFunctions.GetTargetData(matchAllData).getRemaningBall() + (matchAllData.getSetup().getTargetType() != null && !matchAllData.getSetup().getTargetType().isEmpty()
						? " (" + matchAllData.getSetup().getTargetType().toUpperCase() + ")" : "") + "\0", print_writers);
			}
			
			break;
		case "COMPARE":
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)
					&& inn.getInningNumber() == 2).findAny().orElse(null);
			
			if(inning == null) {
				return "populateVizInfobarRightSection: 2nd Inning returned is NULL";
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 5\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
				"$Comparison$Head$txt_Head*GEOM*TEXT SET " + "AT THIS STAGE" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Comparison$TeamGrp$txt_TeamName*GEOM*TEXT SET " + inning.getBowling_team().getTeamName4() + " WERE" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$Comparison$ScoreGrp$txt_Score*GEOM*TEXT SET " + CricketFunctions.compareInningData(matchAllData, "-", 1, 
							matchAllData.getEventFile().getEvents()) + "\0",print_writers);
			break;
			
		case "TIMELINE":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 17\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$14BallTimeline$Head$txt_Head*GEOM*TEXT SET " + "TIMELINE" + "\0", print_writers);
			
			this_data_str = new ArrayList<String>();
			this_data_str.add(String.join(",",  new ArrayList<>(Arrays.asList(IndexController.MatchStats.getTimeLine().split(",")))
			        .stream().map(s -> s.replace("WIDE", "WD")
			                   .replace("NO_BALL", "NB")
			                   .replace("LEG_BYE", "LB")
			                   .replace("BYE", "B")
			                   .replace("PENALTY", "PN")
			                   .replace("LOG_WICKET", "W")
			                   .replace("WICKET", "W"))
			        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {Collections.reverse(list); return list;}))
			        .toArray(new String[0])));
			if(this_data_str.get(this_data_str.size()-1) == null) {
				return "populateVizInfobarRightBottom: TIMELINE data returned invalid";
			}
			String[] elements = this_data_str.get(this_data_str.size() - 1).split(",");
			
			if (elements.length > 16) {
			    elements = Arrays.copyOfRange(elements, elements.length - 16, elements.length);
			}
			Collections.reverse(Arrays.asList(elements));
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$14BallTimeline$ThisOverAll$ThisOver*FUNCTION*Grid*num_col SET " + Math.min(elements.length, 16) + "\0", print_writers);		
			for(int iBall = 0; iBall < elements.length; iBall++) {
				if(iBall < 16) {
					switch (elements[iBall].toUpperCase()) {
					case "|":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
								"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 0\0", print_writers);		
						break;
					case CricketUtil.DOT:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
								"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
								"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						break;
					case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FIVE:
					case CricketUtil.FOUR: case CricketUtil.SIX:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
								"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + 
								elements[iBall] + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
								"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						
						break;
					case CricketUtil.NINE:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
								"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 4\0", print_writers);
						break;	
					case "W":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
								"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 3\0", print_writers);
						break;

					default:
						if(elements[iBall].toUpperCase().contains("BOUNDARY")) {
							if(elements[iBall].toUpperCase().equalsIgnoreCase("6BOUNDARY")||elements[iBall].toUpperCase().equalsIgnoreCase("4BOUNDARY")) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
										"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type$Boundaries$fig_Ball*GEOM*TEXT SET " + 
										elements[iBall].toUpperCase().replace("BOUNDARY", "") + "\0", print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
										"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 2\0", print_writers);
								
							}else if(elements[iBall].toUpperCase().equalsIgnoreCase("9BOUNDARY")) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
										"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 4\0", print_writers);
							}
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
									"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type$Runs$fig_Ball*GEOM*TEXT SET " + elements[iBall].toUpperCase() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
									"$14BallTimeline$ThisOverAll$ThisOver$TimeBall" + (iBall + 1) + "$Select_Type*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							
						}
						break;
					}
				}
			}
			break;
			
		case "OVER_TIMELINE":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 18\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + 
					"$TimeLine$Head$txt_Head*GEOM*TEXT SET " + "TIMELINE" + "\0", print_writers);
			
			for(Inning inn : matchAllData.getMatch().getInning()) {
				if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
					
					for(int j =0; j<= getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).size() - 1; j++) {
						
						if(CricketFunctions.getBallCountStartAndEndRange(matchAllData, inn).get(1) >= ((j+1)*6)) {	
							//------------ first powerplay ---------------------------------//
							if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("CR")) {
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
										+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 5\0",print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
										+ "TimeLineData$TimeBall" + (j+1) + "$ChallengeOver$txt_TapeBall*GEOM*TEXT SET 50-50/PP\0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
										+ "TimeLineData$TimeBall" + (j+1) + "$ChallengeOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
												matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
														matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
								
							}else if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("EO")) {
								if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("TO")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 4\0",print_writers);
									
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_TapeBall*GEOM*TEXT SET SWING BALL\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
									
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 3\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$PowerPlayOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
								}
							}else if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("CO")) {
								if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("TO")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 4\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_TapeBall*GEOM*TEXT SET SWING BALL\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
									
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$PowerPlayOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 3\0",print_writers);
									
								}
							}
						}else if(CricketFunctions.getBallCountStartAndEndRange(matchAllData, inning).get(2) == ((j+1)*6) && 
								CricketFunctions.getBallCountStartAndEndRange(matchAllData, inning).get(2) != 0) {
							//--------------------------------second powerplay ----------------------------------------------//
							if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("CR")) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
										+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 5\0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
										+ "TimeLineData$TimeBall" + (j+1) + "$ChallengeOver$txt_TapeBall*GEOM*TEXT SET 50-50/PP\0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
										+ "TimeLineData$TimeBall" + (j+1) + "$ChallengeOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
												matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
														matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
								
							}else if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("EO")) {
								if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("TO")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 4\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_TapeBall*GEOM*TEXT SET SWING BALL\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
									
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 3\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$PowerPlayOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
									
									
								}
							}else if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("CO")) {
								if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("TO")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 4\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_TapeBall*GEOM*TEXT SET SWING BALL\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
									
									
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$PowerPlayOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 3\0",print_writers);
									
									
								}
							}
						}else {
							if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("CR")) {
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
										+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 5\0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
										+ "TimeLineData$TimeBall" + (j+1) + "$ChallengeOver$txt_TapeBall*GEOM*TEXT SET 50-50\0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
										+ "TimeLineData$TimeBall" + (j+1) + "$ChallengeOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
												matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
														matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
								
							}else if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("EO")) {
								if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("TO")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 4\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_TapeBall*GEOM*TEXT SET SWING BALL\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 2\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$CompletedOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
								}
							}else if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("CO")) {
								if(getOverbyOver(inn.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData).get(j).contains("TO")) {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 4\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_TapeBall*GEOM*TEXT SET SWING BALL\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$TapeBallOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
								}else {
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$" 
											+ "TimeLineData$TimeBall" + (j+1) + "$RunningOver$txt_OverData*GEOM*TEXT SET " + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[0] + "-" + getOverbyOver(inn.getInningNumber(), 
											matchAllData.getEventFile().getEvents(), matchAllData).get(j).split("-")[1] + "\0",print_writers);
									CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$Side" + WhichSide + "$TimeLine$"
											+ "TimeLineData$TimeBall" + (j+1) + "$Select_OverType*FUNCTION*Omo*vis_con SET 1\0",print_writers);
								}
							}
						}
					}
				}
			}
			break;
		}
		return Constants.OK;
	}
	
	public String PlayerProfileCareer(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException {
		
		int k =0;
		String best = "-";
		stat = new Statistics();
		statsType = new StatsType();
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 0 \0",print_writers);
		
		if(FirstPlayerId <= 0 || WhichProfile == null) {
			return "InfoBarPlayerProfile: Player Id NOT found [" + FirstPlayerId + "]";
		}
		
		player = CricketFunctions.getPlayerFromMatchData(FirstPlayerId, matchAllData); 
		if(player == null) {
			return "InfoBarPlayerProfile: Player Id not found [" + FirstPlayerId + "]";
		}
		
		if(matchAllData.getSetup().getHomeTeamId() == player.getTeamId()) {
			team = matchAllData.getSetup().getHomeTeam();
		}else {
			team = matchAllData.getSetup().getAwayTeam();
		}
		if(team == null) {
			return "InfoBarPlayerProfile: team Id not found [" + player.getTeamId() + "]";
		}
		
		if(WhichProfile.equalsIgnoreCase("ISPL S1") || WhichProfile.equalsIgnoreCase("ISPL S2")) {
			statsType = statsTypes.stream().filter(st -> st.getStatsShortName().equalsIgnoreCase(WhichProfile)).findAny().orElse(null);
			if(statsType == null) {
				return "InfoBarPlayerProfile: Stats Type not found for profile [" + WhichProfile + "]";
			}
			
			stat = statistics.stream().filter(st -> st.getPlayerID() == FirstPlayerId && statsType.getStatsId() == st.getStatsTypeId()).findAny().orElse(null);
			if(stat == null) {
				return "InfoBarPlayerProfile: Stats not found for Player Id [" + FirstPlayerId + "]";
			}
			stat.setStats_type(statsType);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$Profile$Text$Age$txt_Age*GEOM*TEXT SET " + (WhichProfile.equalsIgnoreCase("ISPL S1") ? "ISPL SEASON 1" : "ISPL SEASON 2") + "\0", print_writers);
		}
		else if(WhichProfile.equalsIgnoreCase("ISPL_CAREER")) {
			
			Statistics statS1 = null, statS2=null; 
			
			statS1 = CricketFunctions.getStatsByType(FirstPlayerId, "ISPL S1", statsTypes, statistics);
		    statS2 = CricketFunctions.getStatsByType(FirstPlayerId, "ISPL S2", statsTypes, statistics);
		    
		    if (statS1 == null && statS2 == null) {
		        return "InfoBarPlayerProfile: Stats not found for Player Id [" + FirstPlayerId + "]";
		    }
		    
		    BeanUtils.copyProperties(statS1, stat);
		    stat = CricketFunctions.mergeIsplCareerStats(stat, statS2);
		    
			statsType = statsTypes.stream().filter(st -> st.getStatsShortName().equalsIgnoreCase("D10")).findAny().orElse(null);
			stat.setStats_type(statsType);
			
			stat = CricketFunctions.updateTournamentWithH2h(stat, headToHead, matchAllData, CricketUtil.FULL);
			stat = CricketFunctions.updateStatisticsWithMatchData(stat, matchAllData, CricketUtil.FULL);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$Profile$Text$Age$txt_Age*GEOM*TEXT SET " + "ISPL CAREER" + "\0", print_writers);
			
		}
		else if(WhichProfile.equalsIgnoreCase("THIS_SERIES") || WhichProfile.equalsIgnoreCase("THIS_SERIES_TAPE_BALL")){
			
			this_series = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead, cricketService, matchAllData, past_tournament_stats);
			tournament = this_series.stream().filter(st -> st.getPlayerId() == FirstPlayerId).findAny().orElse(null);
			
			for(Tournament tourn : this_series) {
				for(BestStats bs : tourn.getBatsman_best_Stats()) {
					top_batsman_beststats.add(bs);
				}
				for(BestStats bfig : tourn.getBowler_best_Stats()) {
					top_bowler_beststats.add(bfig);
				}
				for(BestStats tapeBall : tourn.getTapeBall_best_Stats()) {
					tapeBall_beststats.add(tapeBall);
				}
			}
			
			Collections.sort(top_batsman_beststats, new CricketFunctions.PlayerBestStatsComparator());
			Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
			Collections.sort(tapeBall_beststats, new CricketFunctions.PlayerBestStatsComparator());
			
			switch (infobar.getMiddle_section().toUpperCase()) {
			case "BAT_PROFILE_CAREER":
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
			case "BALL_PROFILE_CAREER":
				if(WhichProfile.equalsIgnoreCase("THIS_SERIES")){
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
				}else if(WhichProfile.equalsIgnoreCase("THIS_SERIES_TAPE_BALL")) {
					for(int j=0;j<= tapeBall_beststats.size()-1;j++) {
						if(tapeBall_beststats.get(j).getPlayerId() == FirstPlayerId) {
							if(k == 1) {
								break;
							}
							if(k == 0) {
								k += 1;
								if(tapeBall_beststats.get(j).getBestEquation() % 1000 > 0) {
									best = ((tapeBall_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (tapeBall_beststats.get(j).getBestEquation() % 1000));
									break;
								}
								else if(tapeBall_beststats.get(j).getBestEquation() % 1000 < 0) {
									best = (tapeBall_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(tapeBall_beststats.get(j).getBestEquation());
									break;
								}
								else if(tapeBall_beststats.get(j).getBestEquation() != 0) {
									if(tapeBall_beststats.get(j).getBestEquation() % 1000 == 0) {
										best = (tapeBall_beststats.get(j).getBestEquation() / 1000) + "-" + "0";
										break;
									}
								}
								break;
							}
						}else if(tapeBall_beststats.get(j).getPlayerId() != FirstPlayerId) {
							best = "-";
						}
					}
				}
				break;
			}
			
			if(WhichProfile.equalsIgnoreCase("THIS_SERIES")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Text$Age$txt_Age*GEOM*TEXT SET " + "ISPL SEASON 3" + "\0", print_writers);
			}else if(WhichProfile.equalsIgnoreCase("THIS_SERIES_TAPE_BALL")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Text$Age$txt_Age*GEOM*TEXT SET " + "IN ISPL SWING BALL OVERS" + "\0", print_writers);
			}
		}
		else if(WhichProfile.equalsIgnoreCase("SINGLE_DATA")) {
			if(player.getDebut().equalsIgnoreCase(CricketUtil.YES)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Text$Age$txt_Age*GEOM*TEXT SET " + "ON DEBUT" + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Text$Age$txt_Age*GEOM*TEXT SET \0", print_writers);
			}
		}
		
//		if(player.getAge() != null) {
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
//					"$Profile$Text$Carreer$txt_Carreer*GEOM*TEXT SET " + "AGE " + player.getAge() + "\0", print_writers);
//		}else {
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
//					"$Profile$Text$Carreer$txt_Carreer*GEOM*TEXT SET " + "AGE -" + "\0", print_writers);
//		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$Profile$Text$Carreer$txt_Carreer*GEOM*TEXT SET \0", print_writers);
		
		//NAME
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$Profile$Text$Name$txt_FirstName*GEOM*TEXT SET " + player.getFirstname() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$Profile$Text$Name$txt_LastName*GEOM*TEXT SET " + (player.getSurname() != null ? player.getSurname() : "")  + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$Profile$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),player.getPlayerId()).isEmpty()) {
			switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),player.getPlayerId())) {
			case "IMP_IN":  case "IMP_OUT":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				break;
			}
		}
		
		//PHOTO
		if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Profile$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + team.getTeamName4() + Constants.STRAIGHT_1024 + player.getPhoto() 
					+ CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Profile$Image$img_PlayerShadow*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + team.getTeamName4() + Constants.STRAIGHT_1024 + player.getPhoto() 
					+ CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Profile$Image$img_PlayerGlow*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + team.getTeamName4() + Constants.STRAIGHT_1024 + player.getPhoto() 
					+ CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Profile$Image$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH  
					+ team.getTeamName4() + Constants.STRAIGHT_1024 + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Profile$Image$img_PlayerShadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH 
					+ team.getTeamName4() + Constants.STRAIGHT_1024 + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Profile$Image$img_PlayerGlow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH 
					+ team.getTeamName4() + Constants.STRAIGHT_1024 + player.getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}
		
		//TEAMNAME & COLOR
		if(matchAllData.getSetup().getHomeTeamId() == player.getTeamId()) {
			if(matchAllData.getSetup().getHomeTeam().getTeamName4().contains("KHILADI XI") || 
					matchAllData.getSetup().getHomeTeam().getTeamName4().contains("MASTER 11")) {
				if(matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
					color_name = "KHILADI_XI";
				}else if(matchAllData.getSetup().getHomeTeam().getTeamName4().equalsIgnoreCase("MASTER 11")) {
					color_name = "MASTER_XI";
				}
			}else {
				color_name = matchAllData.getSetup().getHomeTeam().getTeamName4();
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$Profile$Text$TeamName$txt_TeamName*GEOM*TEXT SET " + matchAllData.getSetup().getHomeTeam().getTeamName1().toUpperCase() + "\0", print_writers);
		}else if(matchAllData.getSetup().getAwayTeamId() == player.getTeamId()) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$Profile$Text$TeamName$txt_TeamName*GEOM*TEXT SET " + matchAllData.getSetup().getAwayTeam().getTeamName1().toUpperCase() + "\0", print_writers);
			
			if(matchAllData.getSetup().getAwayTeam().getTeamName4().contains("KHILADI XI") || 
					matchAllData.getSetup().getAwayTeam().getTeamName4().contains("MASTER 11")) {
				if(matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
					color_name = "KHILADI_XI";
				}else if(matchAllData.getSetup().getAwayTeam().getTeamName4().equalsIgnoreCase("MASTER 11")) {
					color_name = "MASTER_XI";
				}
			}else {
				color_name = matchAllData.getSetup().getAwayTeam().getTeamName4();
			}
		}
		
		for(int i=1;i<=4;i++) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$Profile$Data$Style1$Line" + i + "$Dehighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color_name + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$Profile$Data$Style1$Line" + i + "$Dehighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color_name + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$Profile$Data$Style1$Line" + i + "$Highlight$img_Base2*TEXTURE*IMAGE SET " +  Constants.ISPL_BASE2 + color_name + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$Profile$Data$Style1$Line" + i + "$Highlight$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color_name + "\0", print_writers);
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$Profile$Data$Style2$Line1$Side1$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color_name + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$Profile$Data$Style2$Line1$Side1$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color_name + "\0", print_writers);
		

		switch (WhichProfile) {
		case "SINGLE_DATA":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Profile$Data$Select_DataStyle*FUNCTION*Omo*vis_con SET 1\0",print_writers);
			
			switch (infobar.getMiddle_section().toUpperCase()) {
			case "BAT_PROFILE_CAREER":
				if(player.getBattingStyle().equalsIgnoreCase("RHB")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Profile$Icon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Batsman_RightHand" + "\0", print_writers);
				}else if(player.getBattingStyle().equalsIgnoreCase("LHB")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Profile$Icon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Batsman_Lefthand" + "\0", print_writers);
				}
				
				switch (WhichStyle) {
				case "BATTING_STYLE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Profile$Data$Style2$Side1$Select_LineNumber*FUNCTION*Omo*vis_con SET 1\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style2$Line1$Side1$txt_Head*GEOM*TEXT SET \0", print_writers);
					
					if(player.getAge() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Text$Carreer$txt_Carreer*GEOM*TEXT SET " + "AGE " + player.getAge() + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Text$Carreer$txt_Carreer*GEOM*TEXT SET " + "AGE -" + "\0", print_writers);
					}
					
					if(player.getBattingStyle() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_ValueLine1*GEOM*TEXT SET " + CricketFunctions.getbattingstyle(player.getBattingStyle(), 
										CricketUtil.SHORT, true, true).toUpperCase() + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_ValueLine1*GEOM*TEXT SET " + "-" + "\0", print_writers);
					}
					break;
				case "STRIKE_RATE": case "BOUNDARY": case "RUNS_BALLS":
					inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
					battingCard = inning.getBattingCard().stream().filter(bc -> bc.getPlayerId() == FirstPlayerId).findAny().orElse(null);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Profile$Data$Style2$Side1$Select_LineNumber*FUNCTION*Omo*vis_con SET 0\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Text$Age$txt_Age*GEOM*TEXT SET " + "THIS MATCH" + "\0", print_writers);

					if(WhichStyle.equalsIgnoreCase("STRIKE_RATE")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_Head*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_Value*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(battingCard.getRuns(), 
										battingCard.getBalls(), 0) + "\0", print_writers);
					}
					else if(WhichStyle.equalsIgnoreCase("BOUNDARY")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_Head*GEOM*TEXT SET " + "4/6/9" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_Value*GEOM*TEXT SET " + battingCard.getFours() + "/" + battingCard.getSixes() + "/" +
								battingCard.getNines() + "\0", print_writers);
					}
					else if(WhichStyle.equalsIgnoreCase("RUNS_BALLS")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_Head*GEOM*TEXT SET \0", print_writers);
						if(battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$Profile$Data$Style2$Line1$Side1$txt_Value*GEOM*TEXT SET " + battingCard.getRuns() + "* (" + battingCard.getBalls() + ")\0", print_writers);
						}else if(battingCard.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$Profile$Data$Style2$Line1$Side1$txt_Value*GEOM*TEXT SET " + battingCard.getRuns() + " (" + battingCard.getBalls() + ")\0", print_writers);
						}
					}
					break;
				}
				break;
			case "BALL_PROFILE_CAREER":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Profile$Icon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Bowler" + "\0", print_writers);
				
				switch (WhichStyle) {
				case "BOWLING_STYLE":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Profile$Data$Style2$Side1$Select_LineNumber*FUNCTION*Omo*vis_con SET 1\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style2$Line1$Side1$txt_Head*GEOM*TEXT SET \0", print_writers);
					
					if(player.getAge() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Text$Carreer$txt_Carreer*GEOM*TEXT SET " + "AGE " + player.getAge() + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Text$Carreer$txt_Carreer*GEOM*TEXT SET " + "AGE -" + "\0", print_writers);
					}
					
					if(player.getBowlingStyle() != null) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_ValueLine1*GEOM*TEXT SET " + CricketFunctions.getbowlingstyle(player.getBowlingStyle()).toUpperCase() 
								+ "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_ValueLine1*GEOM*TEXT SET " + "-" + "\0", print_writers);
					}
					break;
				case "ECONOMY": case "FIGURES":
					inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
					bowling_Card = inning.getBowlingCard().stream().filter(boc -> boc.getPlayerId() == FirstPlayerId).findAny().orElse(null);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Profile$Data$Style2$Side1$Select_LineNumber*FUNCTION*Omo*vis_con SET 0\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Text$Age$txt_Age*GEOM*TEXT SET " + "THIS MATCH" + "\0", print_writers);

					if(WhichStyle.equalsIgnoreCase("ECONOMY")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_Head*GEOM*TEXT SET " + "ECONOMY" + "\0", print_writers);
						if(bowling_Card.getEconomyRate().equalsIgnoreCase("0.00")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$Profile$Data$Style2$Line1$Side1$txt_Value*GEOM*TEXT SET " + "-" + "\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$Profile$Data$Style2$Line1$Side1$txt_Value*GEOM*TEXT SET " + bowling_Card.getEconomyRate() + "\0", print_writers);
						}
					}
					else if(WhichStyle.equalsIgnoreCase("FIGURES")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_Head*GEOM*TEXT SET \0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style2$Line1$Side1$txt_Value*GEOM*TEXT SET " + bowling_Card.getWickets() + "-" + bowling_Card.getRuns() + 
								" (" + CricketFunctions.OverBalls(bowling_Card.getOvers(), bowling_Card.getBalls()) + ")\0", print_writers);
					}
					break;
				}
				break;
			}
			break;
		default:
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Profile$Data$Select_DataStyle*FUNCTION*Omo*vis_con SET 0\0",print_writers);
			
			switch (infobar.getMiddle_section().toUpperCase()) {
			case "BAT_PROFILE_CAREER":
				
				if(player.getBattingStyle().equalsIgnoreCase("RHB")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Profile$Icon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Batsman_RightHand" + "\0", print_writers);
				}else if(player.getBattingStyle().equalsIgnoreCase("LHB")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Profile$Icon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Batsman_Lefthand" + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Data$Style1$Line1$Dehighlight$txt_Head*GEOM*TEXT SET " + "MATCHES" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Data$Style1$Line2$Dehighlight$txt_Head*GEOM*TEXT SET " + "RUNS" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Data$Style1$Line3$Dehighlight$txt_Head*GEOM*TEXT SET " + "STRIKE RATE" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Data$Style1$Line4$Dehighlight$txt_Head*GEOM*TEXT SET " + "BEST" + "\0", print_writers);
				
				if(WhichProfile.equalsIgnoreCase("THIS_SERIES")){
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line1$Dehighlight$txt_Value*GEOM*TEXT SET " + tournament.getMatches() + "*\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line2$Dehighlight$txt_Value*GEOM*TEXT SET " + tournament.getRuns() + "\0", print_writers);
					
					if(!CricketFunctions.generateStrikeRate(tournament.getRuns(), tournament.getBallsFaced(), 0).isEmpty()) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style1$Line3$Dehighlight$txt_Value*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(tournament.getRuns(), 
										tournament.getBallsFaced(), 0) + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style1$Line3$Dehighlight$txt_Value*GEOM*TEXT SET -\0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + best + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line1$Dehighlight$txt_Value*GEOM*TEXT SET " + stat.getMatches() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line2$Dehighlight$txt_Value*GEOM*TEXT SET " + stat.getRuns() + "\0", print_writers);
					if(!CricketFunctions.generateStrikeRate(stat.getRuns(), stat.getBallsFaced(), 0).isEmpty()) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style1$Line3$Dehighlight$txt_Value*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(stat.getRuns(), stat.getBallsFaced(), 0) 
								+ "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$Profile$Data$Style1$Line3$Dehighlight$txt_Value*GEOM*TEXT SET -\0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + stat.getBestScore() + "\0", print_writers);
				}
				break;
			case "BALL_PROFILE_CAREER":
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Profile$Icon$img_Icon*TEXTURE*IMAGE SET " + Constants.ICONS_PATH + "Bowler" + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Data$Style1$Line1$Dehighlight$txt_Head*GEOM*TEXT SET " + "MATCHES" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Data$Style1$Line2$Dehighlight$txt_Head*GEOM*TEXT SET " + "WICKETS" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Data$Style1$Line3$Dehighlight$txt_Head*GEOM*TEXT SET " + "ECONOMY" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$Profile$Data$Style1$Line4$Dehighlight$txt_Head*GEOM*TEXT SET " + "BEST" + "\0", print_writers);
				
				if(WhichProfile.equalsIgnoreCase("THIS_SERIES")){
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line1$Dehighlight$txt_Value*GEOM*TEXT SET " + tournament.getMatches() + "*\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line2$Dehighlight$txt_Value*GEOM*TEXT SET " + tournament.getWickets() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line3$Dehighlight$txt_Value*GEOM*TEXT SET " + CricketFunctions.getEconomy(tournament.getRunsConceded(), 
								tournament.getBallsBowled(), 2, "-") + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + best + "\0", print_writers);
				}
				else if(WhichProfile.equalsIgnoreCase("THIS_SERIES_TAPE_BALL")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line1$Dehighlight$txt_Head*GEOM*TEXT SET " + "MATCHES" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line2$Dehighlight$txt_Head*GEOM*TEXT SET " + "WICKETS" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line3$Dehighlight$txt_Head*GEOM*TEXT SET " + "ECONOMY" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line4$Dehighlight$txt_Head*GEOM*TEXT SET " + "DOTS" + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line1$Dehighlight$txt_Value*GEOM*TEXT SET " + tournament.getMatches() + "*\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line2$Dehighlight$txt_Value*GEOM*TEXT SET " + tournament.getTapeBall_wickets() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line3$Dehighlight$txt_Value*GEOM*TEXT SET " + CricketFunctions.getEconomy(tournament.getTapeBall_runs(), 
								tournament.getTapeBall_balls(), 2, "-") + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + tournament.getTapeBall_dotsBall() + "\0", print_writers);
				}
				else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line1$Dehighlight$txt_Value*GEOM*TEXT SET " + stat.getMatches() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line2$Dehighlight$txt_Value*GEOM*TEXT SET " + stat.getWickets() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line3$Dehighlight$txt_Value*GEOM*TEXT SET " + CricketFunctions.getEconomy(stat.getRunsConceded(), stat.getBallsBowled(), 
							2, "-") + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$Profile$Data$Style1$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + stat.getBestFigures() + "\0", print_writers);
				}
				break;

			}
			break;
		}
		
		return Constants.OK;
    }
	
	public String Counter(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws CloneNotSupportedException {
		switch(infobar.getFull_section().toUpperCase()) {
		case "NINE_COUNTER":
			this_data_str = new ArrayList<String>();
			today_nines = String.valueOf(CricketFunctions.extracttournamentFoursAndSixesData("CURRENT_MATCH_DATA", headToHead, matchAllData, null).getTournament_nines());
			
			if(Integer.valueOf(today_nines) > 0 && matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventType()
					.equalsIgnoreCase(CricketUtil.NINE)) {
				if(matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventWasABoundary() != null && 
						matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventWasABoundary().equalsIgnoreCase(CricketUtil.YES)) {
					
					today_nines = String.valueOf(Integer.valueOf(today_nines));
				}
			}
			
			this_data_str.add(CricketFunctions.hundredsTensUnits(String.valueOf(Integer.valueOf(previous_nines) + Integer.valueOf(today_nines))));
			if(WhichSide == 1) {
				String new_nine_value = String.valueOf((Integer.valueOf(previous_nines) + Integer.valueOf(today_nines) + 1));
				this_data_str.add(CricketFunctions.hundredsTensUnits(new_nine_value));
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter" + 
					"$CounterHead$txt_Head1*GEOM*TEXT SET " + "NINES" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter" + 
					"$CounterHead$txt_Head2*GEOM*TEXT SET " + "THIS SEASON" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Hundredths$Side" 
					+ WhichSide + "$txt_Hundredths*GEOM*TEXT SET " + this_data_str.get(0).split(",")[0] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Tenths$Side" 
					+ WhichSide + "$txt_Tenths*GEOM*TEXT SET " + this_data_str.get(0).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Units$Side" 
					+ WhichSide + "$txt_Units*GEOM*TEXT SET " + this_data_str.get(0).split(",")[2] + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Hundredths$Side" 
					+ (3-WhichSide) + "$txt_Hundredths*GEOM*TEXT SET " + this_data_str.get(1).split(",")[0] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Tenths$Side" 
					+ (3-WhichSide) + "$txt_Tenths*GEOM*TEXT SET " + this_data_str.get(1).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Units$Side" 
					+ (3-WhichSide) + "$txt_Units*GEOM*TEXT SET " + this_data_str.get(1).split(",")[2] + "\0", print_writers);
			break;
		case "SIXES_COUNTER":
			this_data_str = new ArrayList<String>();
			today_sixes = String.valueOf(CricketFunctions.extracttournamentFoursAndSixesData("CURRENT_MATCH_DATA", headToHead, matchAllData, null).getTournament_sixes());
			
			if(Integer.valueOf(today_sixes) > 0 && matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventType()
					.equalsIgnoreCase(CricketUtil.SIX)) {
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
			
			//this_data_str.add(CricketFunctions.hundredsTensUnits(String.valueOf(Integer.valueOf(previous_sixes) + Integer.valueOf(today_sixes))));
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter" + 
					"$CounterHead$txt_Head1*GEOM*TEXT SET " + "SIXES" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter" + 
					"$CounterHead$txt_Head2*GEOM*TEXT SET " + "THIS SEASON" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Hundredths$Side" 
					+ WhichSide + "$txt_Hundredths*GEOM*TEXT SET " + this_data_str.get(0).split(",")[0] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Tenths$Side" 
					+ WhichSide + "$txt_Tenths*GEOM*TEXT SET " + this_data_str.get(0).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Units$Side" 
					+ WhichSide + "$txt_Units*GEOM*TEXT SET " + this_data_str.get(0).split(",")[2] + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Hundredths$Side" 
					+ (3-WhichSide) + "$txt_Hundredths*GEOM*TEXT SET " + this_data_str.get(1).split(",")[0] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Tenths$Side" 
					+ (3-WhichSide) + "$txt_Tenths*GEOM*TEXT SET " + this_data_str.get(1).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Units$Side" 
					+ (3-WhichSide) + "$txt_Units*GEOM*TEXT SET " + this_data_str.get(1).split(",")[2] + "\0", print_writers);
			break;
		case "FOUR_COUNTER":
			this_data_str = new ArrayList<String>();
			today_fours = String.valueOf(CricketFunctions.extracttournamentFoursAndSixesData("CURRENT_MATCH_DATA", headToHead, matchAllData, null).getTournament_fours());
			this_data_str.add(CricketFunctions.hundredsTensUnits(String.valueOf(Integer.valueOf(previous_fours) + Integer.valueOf(today_fours))));
			
			
			this_data_str = new ArrayList<String>();
			today_fours = String.valueOf(CricketFunctions.extracttournamentFoursAndSixesData("CURRENT_MATCH_DATA", headToHead, matchAllData, null).getTournament_fours());
			
			if(Integer.valueOf(today_fours) > 0 && matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventType().equalsIgnoreCase(CricketUtil.FOUR)) {
				if(matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventWasABoundary() != null && 
						matchAllData.getEventFile().getEvents().get(matchAllData.getEventFile().getEvents().size()-1).getEventWasABoundary().equalsIgnoreCase(CricketUtil.YES)) {
					
					today_fours = String.valueOf(Integer.valueOf(today_fours));
				}
			}
			this_data_str.add(CricketFunctions.hundredsTensUnits(String.valueOf(Integer.valueOf(previous_fours) + Integer.valueOf(today_fours))));
			if(WhichSide == 1) {
				String new_four_value = String.valueOf((Integer.valueOf(previous_fours) + Integer.valueOf(today_fours) + 1));
				this_data_str.add(CricketFunctions.hundredsTensUnits(new_four_value));
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter" + 
					"$CounterHead$txt_Head1*GEOM*TEXT SET " + "FOURS" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter" + 
					"$CounterHead$txt_Head2*GEOM*TEXT SET " + "THIS SEASON" + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Hundredths$Side" 
					+ WhichSide + "$txt_Hundredths*GEOM*TEXT SET " + this_data_str.get(0).split(",")[0] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Tenths$Side" 
					+ WhichSide + "$txt_Tenths*GEOM*TEXT SET " + this_data_str.get(0).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Units$Side" 
					+ WhichSide + "$txt_Units*GEOM*TEXT SET " + this_data_str.get(0).split(",")[2] + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Hundredths$Side" 
					+ (3-WhichSide) + "$txt_Hundredths*GEOM*TEXT SET " + this_data_str.get(1).split(",")[0] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Tenths$Side" 
					+ (3-WhichSide) + "$txt_Tenths*GEOM*TEXT SET " + this_data_str.get(1).split(",")[1] + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$AllData$BoundaryCounter$Counter$Units$Side" 
					+ (3-WhichSide) + "$txt_Units*GEOM*TEXT SET " + this_data_str.get(1).split(",")[2] + "\0", print_writers);
			break;
		}
		return Constants.OK;
	}
	
	public String BattingCard(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException {
		
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning)
				.findAny().orElse(null);
		if(inning == null) {
			return "PopulateScorecardFF: current inning is NULL";
		}
		
		if(inning.getBatting_team().getTeamName4().contains("KHILADI XI") || inning.getBatting_team().getTeamName4().contains("MASTER 11")) {
			if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
				color_name = "KHILADI_XI";
			}else if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
				color_name = "MASTER_XI";
			}
		}else {
			color_name = inning.getBatting_team().getTeamName4();
		}
		
		//--------------------------------
		int rowId = 0;
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BattingCard$RunsScored*ACTIVE SET 0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$BattingCard$Header$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BattingCard$Header$TextAll$txt_Header1*GEOM*TEXT SET " + 
				inning.getBatting_team().getTeamName2() + "\0",print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BattingCard$Header$TextAll$txt_Header2*GEOM*TEXT SET " + 
				inning.getBatting_team().getTeamName3() + "\0",print_writers);
		
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 3 \0",print_writers);
//		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
//				+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*num_row SET " + inning.getBattingCard().size() + "\0",print_writers);
//		
//		if(inning.getBattingCard().size() == 11) {
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
//					+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*row_offset SET 50.0\0", print_writers);
//		}else if(inning.getBattingCard().size() == 12) {
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
//					+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*row_offset SET 38\0",print_writers);
//		}else if(inning.getBattingCard().size() == 13) {
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
//					+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*row_offset SET 35\0",print_writers);
//		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*num_row SET 11\0",print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*row_offset SET 50.0\0", print_writers);
		
		Collections.sort(inning.getBattingCard());
		for(BattingCard bc : inning.getBattingCard()) {
			rowId++;
			switch (bc.getStatus().toUpperCase()) {
			case CricketUtil.STILL_TO_BAT:
					
				if(bc.getHowOut() == null || bc.getHowOut().isEmpty()) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 0\0", print_writers);

					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$StillToBat$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$StillToBat$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 230\0",print_writers);
					
					if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bc.getPlayerId()).isEmpty()) {
						switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bc.getPlayerId())) {
						case "IMP_IN":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$StillToBat$select_GooglyImpact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$StillToBat$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0",print_writers);
							break;
						case "IMP_OUT":
							if(bc.getBalls() == 0) {
								rowId = rowId - 1;
							}
							break;
						}
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$StillToBat$txt_Name*GEOM*TEXT SET " + 
							bc.getPlayer().getTicker_name() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$StillToBat$txt_In*GEOM*TEXT SET " + "AT" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$StillToBat$txt_At*GEOM*TEXT SET " + rowId + "\0", print_writers);
				} else {
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$BattingCard$DataAll$Row" + rowId + "$Out$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color_name + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$BattingCard$DataAll$Row" + rowId + "$Out$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color_name + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$Out$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$Out$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 230\0",print_writers);
					
					if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bc.getPlayerId()).isEmpty()) {
						switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bc.getPlayerId())) {
						case "IMP_IN": case "IMP_OUT":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$Out$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$Out$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0",print_writers);
							break;
						}
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$Out$txt_Name*GEOM*TEXT SET " + bc.getPlayer().getTicker_name() + "\0", print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$Out$txt_Runs*GEOM*TEXT SET " + bc.getRuns() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$Out$txt_Balls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0", print_writers);
				}
				break;
				
			default:
				
				switch (bc.getStatus().toUpperCase()) {
				case CricketUtil.OUT:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					containerName = "Out";
					base_name = "$img_Base2";
					color_name1 = "$img_Text2";
					break;
				case CricketUtil.NOT_OUT:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 2\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 1\0",print_writers);
					containerName = "NotOut";
					base_name = "$img_Base1";
					color_name1 = "$img_Text1";
					break;
				}
				
				if(base_name.contains("1")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + base_name + "*TEXTURE*IMAGE SET " + 
							Constants.ISPL_BASE1 + color_name + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + color_name1 + "*TEXTURE*IMAGE SET " + 
							Constants.ISPL_TEXT1 + color_name + "\0", print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + base_name + "*TEXTURE*IMAGE SET " + 
							Constants.ISPL_BASE2 + color_name + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + color_name1 + "*TEXTURE*IMAGE SET " + 
							Constants.ISPL_TEXT2 + color_name + "\0", print_writers);
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + "$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + "$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 230\0",print_writers);
				
				if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bc.getPlayerId()).isEmpty()) {
					switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bc.getPlayerId())) {
					case "IMP_IN": case "IMP_OUT":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + "$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + "$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0",print_writers);
						break;
					}
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + "$txt_Name*GEOM*TEXT SET " + 
						bc.getPlayer().getTicker_name() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + "$txt_Runs*GEOM*TEXT SET " + 
						bc.getRuns() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + "$txt_Balls*GEOM*TEXT SET " + 
						String.valueOf(bc.getBalls()) + "\0", print_writers);
				break;
			}
		}
		
		if(inning.getSpecialRuns() != null && !inning.getSpecialRuns().isEmpty()) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics$BattingCard$RunsScored*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*row_offset SET 42.0\0", print_writers);
			if(inning.getSpecialRuns().startsWith("+")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics$BattingCard$RunsScored$ChallengeData$ChallengeGrp$Select_ScoreType*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$RunsScored$ChallengeData$ChallengeGrp$txt_ChallengeRuns*GEOM*TEXT SET " + inning.getSpecialRuns() + "\0", print_writers);
			}else if(inning.getSpecialRuns().startsWith("-")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics$BattingCard$RunsScored$ChallengeData$ChallengeGrp$Select_ScoreType*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$RunsScored$ChallengeData$ChallengeGrp$txt_ChallengeRuns*GEOM*TEXT SET " + inning.getSpecialRuns() + "\0", print_writers);
			}
			
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics$BattingCard$RunsScored*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*row_offset SET 50.0\0", print_writers);
		}
		return Constants.OK;
	}
	public String Current_Partnership(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException {
	
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning)
				.findAny().orElse(null);
		if(inning == null) {
			return "PopulateScorecardFF: current inning is NULL";
		}
		
		if(inning.getBatting_team().getTeamName4().contains("KHILADI XI") || inning.getBatting_team().getTeamName4().contains("MASTER 11")) {
			if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
				color_name = "KHILADI_XI";
			}else if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
				color_name = "MASTER_XI";
			}
		}else {
			color_name = inning.getBatting_team().getTeamName4();
		}
		
		if(inning.getPartnerships() != null && inning.getPartnerships().size() <= 0) {
			return "populateVizInfobarMiddleSection: Partnership size is NULL/Zero";
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 8\0",print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Partnership$HeaderAll$txt_Header1*GEOM*TEXT SET " + CricketFunctions.ordinal(inning.getPartnerships().get(inning.
					getPartnerships().size()-1).getPartnershipNumber()) + " WICKET" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Partnership$HeaderAll$txt_Header2*GEOM*TEXT SET " + "PARTNERSHIP" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Partnership$ImageGrp$img_Player1*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + color_name + Constants.LEFT_1024 + 
				inning.getPartnerships().get(inning.getPartnerships().size()-1).getFirstPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Partnership$ImageGrp$img_Player2*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + color_name + Constants.RIGHT_1024 + 
				inning.getPartnerships().get(inning.getPartnerships().size()-1).getSecondPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Partnership$Runs$txt_Runs*GEOM*TEXT SET " + inning.getPartnerships().get(inning.getPartnerships().size()-1).getTotalRuns() + "*" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Partnership$Runs$txt_Ball*GEOM*TEXT SET OFF " + inning.getPartnerships().get(inning.getPartnerships().size()-1).getTotalBalls() + "\0", print_writers);
	
		return Constants.OK;
	}
	public String Points_Table(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException, JAXBException {
	
		if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "LeagueTable" + CricketUtil.XML_EXTENSION).exists()) {
			leagueTable = (LeagueTable)JAXBContext.newInstance(LeagueTable.class).createUnmarshaller().unmarshal(
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.LEAGUE_TABLE_DIRECTORY + "LeagueTable" + CricketUtil.XML_EXTENSION));
		}
		if(leagueTable == null) {
			return "populateFFPointsTable : League Table is null";
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 14\0",print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Standings$Text$txt_Header*GEOM*TEXT SET POINTS TABLE\0",print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide
				+ "$Standings$Rows*FUNCTION*Grid*num_row SET 8\0",print_writers);
		
		for(int i=0; i<=leagueTable.getLeagueTeams().size()-1;i++) {
			
			if(leagueTable.getLeagueTeams().get(i).getTeamName().equalsIgnoreCase(matchAllData.getSetup().getHomeTeam().getTeamBadge()) ||
					leagueTable.getLeagueTeams().get(i).getTeamName().equalsIgnoreCase(matchAllData.getSetup().getAwayTeam().getTeamBadge())) {
				omo = 1;
				containerName = "$Highlight";
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Standings$Rows$" + (i+1) + "$Highlight$Band$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + leagueTable.getLeagueTeams().get(i).getTeamName() 
						+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Standings$Rows$" + (i+1) + "$Highlight$Band$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + leagueTable.getLeagueTeams().get(i).getTeamName() 
						+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Standings$Rows$" + (i+1) + "$Highlight$Select_Qualify$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + 
						leagueTable.getLeagueTeams().get(i).getTeamName() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Standings$Rows$" + (i+1) + "$Highlight$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + leagueTable.getLeagueTeams().get(i).getTeamName() 
						+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Standings$Rows$" + (i+1) + "$Highlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + leagueTable.getLeagueTeams().get(i).getTeamName() 
						+ "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Standings$Rows$" + (i+1) + "$Highlight$Select_Qualify$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + 
						leagueTable.getLeagueTeams().get(i).getTeamName() + "\0", print_writers);
				
			}else {
				omo = 0;
				containerName = "$Dehighlight";
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide
					+ "$Standings$Rows$" + (i+1) + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
			
			if(leagueTable.getLeagueTeams().get(i).getQualifiedStatus().trim().equalsIgnoreCase("Q")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide
						+ "$Standings$Rows$" + (i+1) + containerName + "$Select_Qualify*FUNCTION*Omo*vis_con SET 1\0",print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide
						+ "$Standings$Rows$" + (i+1) + containerName + "$Select_Qualify*FUNCTION*Omo*vis_con SET 0\0",print_writers);
			}
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Standings$Rows$" + (i+1) + containerName + "$txt_Rank*GEOM*TEXT SET " + (i+1) + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Standings$Rows$" + (i+1) + containerName + "$txt_Name*GEOM*TEXT SET " + leagueTable.getLeagueTeams().get(i).getTeamName() + "\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Standings$Rows$" + (i+1) + containerName + "$PointData$txt_Played*GEOM*TEXT SET " + leagueTable.getLeagueTeams().get(i).getPlayed() + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Standings$Rows$" + (i+1) + containerName + "$PointData$txt_Won*GEOM*TEXT SET " + leagueTable.getLeagueTeams().get(i).getWon() + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Standings$Rows$" + (i+1) + containerName + "$PointData$txt_Points*GEOM*TEXT SET " + leagueTable.getLeagueTeams().get(i).getPoints() + "\0",print_writers);
		}
		return Constants.OK;
	}
	public String Team_FormGuide(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException, JAXBException {
		
		String MatchName = "";
		
		fixtures = CricketFunctions.getFixturesByTeam(team_id, CricketFunctions.processAllFixtures(cricketService));
		team = cricketService.getTeams().stream().filter(tm -> tm.getTeamId() == team_id).findAny().orElse(null);
		if(team == null) {
			return "Team_FormGuide : team is null";
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 13\0",print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$FormGuide$Text$txt_FirstName*GEOM*TEXT SET " + team.getTeamName2() + "\0",print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$FormGuide$Text$txt_LastName*GEOM*TEXT SET " + team.getTeamName3() + "\0",print_writers);
		rowId = 0;
		for(Fixture fix : fixtures) {
			rowId = rowId + 1;
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$FormGuide$Data$Line" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET 0\0",print_writers);
			
			if(fix.getMatchnumber() < 10) {
				MatchName = "MATCH " + fix.getMatchnumber();
			}else {
				MatchName = fix.getMatchfilename();
			}
			
			if(fix.getWinnerteam() != null) {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$txt_MatchNumber*GEOM*TEXT SET " + MatchName + "\0",print_writers);
				
				if(fix.getHometeamid() == team_id) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$txt_TeamName*GEOM*TEXT SET " + fix.getAway_Team().getTeamBadge().substring(0, 3) + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + fix.getAway_Team().getTeamBadge() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + fix.getAway_Team().getTeamBadge() + "\0", print_writers);
					
				}else if(fix.getAwayteamid() == team_id) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$txt_TeamName*GEOM*TEXT SET " + fix.getHome_Team().getTeamBadge().substring(0, 3) + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + fix.getHome_Team().getTeamBadge() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + fix.getHome_Team().getTeamBadge() + "\0", print_writers);
				}
				
				if(fix.getWinnerteam().equalsIgnoreCase(team.getTeamName1())) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$txt_Result*GEOM*TEXT SET " + "WON" + "\0",print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$txt_Result*GEOM*TEXT SET " + "LOST" + "\0",print_writers);
				}
			}else {
				Calendar cal = Calendar.getInstance();
				
				if(MatchName.equalsIgnoreCase(matchAllData.getMatch().getMatchFileName().replace(".json", ""))) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET 1\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$txt_MatchNumber*GEOM*TEXT SET " + MatchName + "\0",print_writers);
					
					if(fix.getHometeamid() == team_id) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$txt_TeamName*GEOM*TEXT SET " + fix.getAway_Team().getTeamBadge().substring(0, 3) + "\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + fix.getAway_Team().getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + fix.getAway_Team().getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + fix.getAway_Team().getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + fix.getAway_Team().getTeamBadge() + "\0", print_writers);
						
						
					}else if(fix.getAwayteamid() == team_id) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$txt_TeamName*GEOM*TEXT SET " + fix.getHome_Team().getTeamBadge().substring(0, 3) + "\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + fix.getHome_Team().getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + fix.getHome_Team().getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + fix.getHome_Team().getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + fix.getHome_Team().getTeamBadge() + "\0", print_writers);
					}
					
					if(fix.getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$txt_Result*GEOM*TEXT SET " + "TODAY" + "\0",print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Hiighlight$txt_Result*GEOM*TEXT SET " + CricketFunctions.ordinal(Integer.valueOf(fix.getDate().split("-")[0])) 
								+ " " + java.time.Month.of(Integer.valueOf(fix.getDate().split("-")[1])).getDisplayName(java.time.format.TextStyle.SHORT, java.util.
										Locale.ENGLISH).toUpperCase() + "\0",print_writers);
					}
					
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET 0\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$txt_MatchNumber*GEOM*TEXT SET " + MatchName + "\0",print_writers);
					
					if(fix.getHometeamid() == team_id) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$txt_TeamName*GEOM*TEXT SET " + fix.getAway_Team().getTeamBadge().substring(0, 3) + "\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + fix.getAway_Team().getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + fix.getAway_Team().getTeamBadge() + "\0", print_writers);
						
					}else if(fix.getAwayteamid() == team_id) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$txt_TeamName*GEOM*TEXT SET " + fix.getHome_Team().getTeamBadge().substring(0, 3) + "\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + fix.getHome_Team().getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + fix.getHome_Team().getTeamBadge() + "\0", print_writers);
						
					}
					
					if(fix.getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$txt_Result*GEOM*TEXT SET " + "TODAY" + "\0",print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$FormGuide$Data$Line" + rowId + "$Dehighlight$txt_Result*GEOM*TEXT SET " + CricketFunctions.ordinal(Integer.valueOf(fix.getDate().split("-")[0])) 
								+ " " + java.time.Month.of(Integer.valueOf(fix.getDate().split("-")[1])).getDisplayName(java.time.format.TextStyle.SHORT, java.util.
										Locale.ENGLISH).toUpperCase() + "\0",print_writers);
					}
				}
			}
		}
		return Constants.OK;
	}
	public String Target(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException {
		
		if(!matchAllData.getMatch().getInning().get(1).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)&&
				!matchAllData.getMatch().getInning().get(3).getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
			return "populateVizInfobarMiddleSection: Target available in 2nd inning only";
		}
		inning = matchAllData.getMatch().getInning().stream().filter(inn ->inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES) && 
				inn.getInningNumber() == 2).findAny().orElse(null);
		
		if(inning.getBatting_team().getTeamName4().contains("KHILADI XI") || inning.getBatting_team().getTeamName4().contains("MASTER 11")) {
			if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
				color_name = "KHILADI_XI";
			}else if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
				color_name = "MASTER_XI";
			}
		}else {
			color_name = inning.getBatting_team().getTeamName4();
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 9\0",print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$BigTarget$Data$Base$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color_name + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$BigTarget$Data$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color_name + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$BigTarget$Logo$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + inning.getBatting_team().getTeamBadge() + "\0",print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$BigTarget$Logo$img_TeamLogoGlow*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + inning.getBatting_team().getTeamBadge() + "\0",print_writers);

		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$BigTarget$Text$Name$txt_FirstName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName2() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$BigTarget$Text$Name$txt_LastName*GEOM*TEXT SET " + inning.getBatting_team().getTeamName3() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$BigTarget$Text$Need$txt_NeedRuns*GEOM*TEXT SET " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() + "\0", print_writers);
		
		if(matchAllData.getSetup().getTargetType() != null && !matchAllData.getSetup().getTargetType().isEmpty()) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$BigTarget$Data$img_Text1$txt_Head*GEOM*TEXT SET FROM " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers() + 
					" OVERS (" + matchAllData.getSetup().getTargetType().isEmpty() + ")\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$BigTarget$Data$img_Text1$txt_Head*GEOM*TEXT SET FROM " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers() + " OVERS" + "\0", print_writers);
		}
		return Constants.OK;
	}
	public String LeaderBoard(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws InterruptedException, JAXBException, IOException {
		
		this_series = new ArrayList<Tournament>();
		
		List<BestStats> top_batsman_bestst = new ArrayList<BestStats>();
		List<BestStats> top_bowler_bestst = new ArrayList<BestStats>();
		
		List<BestStats> tape_ball = new ArrayList<BestStats>();
		
		teams = cricketService.getTeams();
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 15\0",print_writers);
		
		switch(infobar.getMiddle_section().toUpperCase()) {
		case "LB_TAPE_BALL_OVER":
			if(WhichProfile.equalsIgnoreCase("WITHOUT_CURRENT")) {
				tape_ball = CricketFunctions.extractTapeData("PAST_MATCHES_DATA", cricketService, matchAllData, null, headToHead);
			}else if(WhichProfile.equalsIgnoreCase("WITH_CURRENT")) {
				tape_ball = CricketFunctions.extractTapeData("COMBINED_PAST_CURRENT_MATCH_DATA", cricketService, matchAllData, null, headToHead);
			}
			if(tape_ball == null) {
				return "populateLeaderBoard : tape_ball Stats is Null";
			}
			break;
		default:
			if(WhichProfile.equalsIgnoreCase("WITHOUT_CURRENT")) {
				this_series = CricketFunctions.extractTournamentData("PAST_MATCHES_DATA", false, headToHead, cricketService, 
						matchAllData, null);
//				this_series = past_tournament_stats;
			}else if(WhichProfile.equalsIgnoreCase("WITH_CURRENT")) {
				this_series = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead, cricketService, 
						matchAllData, past_tournament_stats);
			}
			break;
		}
		
		switch(infobar.getMiddle_section().toUpperCase()) {
		case "LB_TAPE_BALL_OVER":
			Collections.sort(tape_ball, new CricketFunctions.TapeBowlerWicketsComparator());
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_Header*GEOM*TEXT SET " + "MOST WICKETS" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_SubHeader*GEOM*TEXT SET " + "ISPL SWING BALL OVERS" + "\0",print_writers);
			
			rowId =0;
			for(int i=0;i<=tape_ball.size()-1;i++) {
				rowId = rowId + 1;
				if(rowId <= 5) {
					if(tape_ball.get(i).getPlayerId() == FirstPlayerId) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$LeaderBoard$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + teams.get(tape_ball.get(i).getPlayer().getTeamId()-1).getTeamBadge() 
							+ Constants.STRAIGHT_1024 + tape_ball.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
					
					if(highlight_player == rowId) {
						containerName ="$Highlight";
						omo = 1;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 
								+ teams.get(tape_ball.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 
								+ teams.get(tape_ball.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 
								+ teams.get(tape_ball.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 
								+ teams.get(tape_ball.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
					}else {
						containerName ="$Dehighlight";
						omo = 0;
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Name*GEOM*TEXT SET " + tape_ball.get(i).getPlayer().getTicker_name() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_TeamName*GEOM*TEXT SET " + teams.get(tape_ball.get(i).getPlayer().getTeamId()-1).getTeamName4() + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + tape_ball.get(i).getWickets() + "\0",print_writers);
				}
			}
			break;
		case "LB_HIGHEST_SR":
			
			Collections.sort(this_series,new CricketFunctions.BestBatsmanStrikeRateComparator());
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_Header*GEOM*TEXT SET " + "HIGHEST" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_SubHeader*GEOM*TEXT SET " + "STRIKE RATE" + "\0",print_writers);
			
			rowId =0;
			for(int i=0;i<=this_series.size()-1;i++) {
				if(this_series.get(i).getBallsFaced() >= 30) {
					rowId = rowId + 1;
					if(rowId <= 5) {
						if(this_series.get(i).getPlayerId() == FirstPlayerId) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
								+ WhichSide + "$LeaderBoard$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() 
								+ Constants.STRAIGHT_1024 + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}
						
						if(highlight_player == rowId) {
							containerName ="$Highlight";
							omo = 1;
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 
									+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 
									+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 
									+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 
									+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						}else {
							containerName ="$Dehighlight";
							omo = 0;
						}
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Name*GEOM*TEXT SET " + this_series.get(i).getPlayer().getTicker_name() + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_TeamName*GEOM*TEXT SET " + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamName4() + "\0",print_writers);
						
						double doubleValue = Double.parseDouble(CricketFunctions.generateStrikeRate(this_series.get(i).getRuns(), this_series.get(i).getBallsFaced(), 1));
				        int roundedValue = (int) Math.round(doubleValue);
				        
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + roundedValue + "\0",print_writers);
					}
				}
			}
			break;
		case "LB_BEST_ECONOMY":
			
			Collections.sort(this_series,new CricketFunctions.BestBowlerEconomyComparator());
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_Header*GEOM*TEXT SET " + "BEST" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_SubHeader*GEOM*TEXT SET " + "ECONOMY" + "\0",print_writers);
			
			rowId =0;
			for(int i=0;i<=this_series.size()-1;i++) {
				if(this_series.get(i).getBallsBowled() >= 36) {
					rowId = rowId + 1;
					if(rowId <= 5) {
						if(this_series.get(i).getPlayerId() == FirstPlayerId) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
								+ WhichSide + "$LeaderBoard$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() 
								+ Constants.STRAIGHT_1024 + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
						}
						
						if(highlight_player == rowId) {
							containerName ="$Highlight";
							omo = 1;
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 
									+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 
									+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 
									+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 
									+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						}else {
							containerName ="$Dehighlight";
							omo = 0;
						}
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Name*GEOM*TEXT SET " + this_series.get(i).getPlayer().getTicker_name() + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_TeamName*GEOM*TEXT SET " + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamName4() + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + CricketFunctions.getEconomy(this_series.get(i).getRunsConceded(), this_series.get(i).getBallsBowled(), 1, "-") + "\0",print_writers);
					}
				}
			}
			break;	
		case "LB_HIGHEST_SCORE":	
			for(Tournament tourn : this_series) {
				 for(BestStats bs : tourn.getBatsman_best_Stats()) {
					 
	            	top_batsman_bestst.add(CricketFunctions.getProcessedBatsmanBestStats(bs));
	            }
				Collections.sort(top_batsman_bestst,new CricketFunctions.BatsmanBestStatsComparator());
	        }
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_Header*GEOM*TEXT SET " + "HIGHEST" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_SubHeader*GEOM*TEXT SET " + "SCORES" + "\0",print_writers);
			
			rowId =0;
			for(int i=0;i<=top_batsman_bestst.size()-1;i++) {
				rowId = rowId + 1;
				if(rowId <= 5) {
					if(top_batsman_bestst.get(i).getPlayerId() == FirstPlayerId) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
							+ WhichSide + "$LeaderBoard$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + teams.get(top_batsman_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() 
							+ Constants.STRAIGHT_1024 + top_batsman_bestst.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
					
					if(highlight_player == rowId) {
						containerName ="$Highlight";
						omo = 1;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 
								+ teams.get(top_batsman_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 
								+ teams.get(top_batsman_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 
								+ teams.get(top_batsman_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 
								+ teams.get(top_batsman_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
					}else {
						containerName ="$Dehighlight";
						omo = 0;
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Name*GEOM*TEXT SET " + top_batsman_bestst.get(i).getPlayer().getTicker_name() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_TeamName*GEOM*TEXT SET " + teams.get(top_batsman_bestst.get(i).getPlayer().getTeamId()-1).getTeamName4() + "\0",print_writers);
					
					if(top_batsman_bestst.get(i).getBestEquation() % 2 == 0) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + top_batsman_bestst.get(i).getBestEquation() / 2 + "\0",print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + top_batsman_bestst.get(i).getBestEquation() / 2 + "*" + "\0",print_writers);
					}
				}
			}
			break;
		case "LB_BEST_FIGURE":
			
			for(Tournament tourn : this_series) {
				for(BestStats bs : tourn.getBowler_best_Stats()) {
					top_bowler_bestst.add(CricketFunctions.getProcessedBowlerBestStats(bs));
	            }
				Collections.sort(top_bowler_bestst,new CricketFunctions.BowlerBestStatsComparator());
	        }
			
			TimeUnit.MILLISECONDS.sleep(500);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_Header*GEOM*TEXT SET " + "BEST" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_SubHeader*GEOM*TEXT SET " + "FIGURES" + "\0",print_writers);
			
			rowId =0;
			for(int i=0;i<=top_bowler_bestst.size()-1;i++) {
				rowId = rowId + 1;
				if(rowId <= 5) {
					if(top_bowler_bestst.get(i).getPlayerId() == FirstPlayerId) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
							+ WhichSide + "$LeaderBoard$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + teams.get(top_bowler_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() 
							+ Constants.STRAIGHT_1024 + top_bowler_bestst.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
					
					if(highlight_player == rowId) {
						containerName ="$Highlight";
						omo = 1;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 
								+ teams.get(top_bowler_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 
								+ teams.get(top_bowler_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 
								+ teams.get(top_bowler_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 
								+ teams.get(top_bowler_bestst.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
					}else {
						containerName ="$Dehighlight";
						omo = 0;
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Name*GEOM*TEXT SET " + top_bowler_bestst.get(i).getPlayer().getTicker_name() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_TeamName*GEOM*TEXT SET " + teams.get(top_bowler_bestst.get(i).getPlayer().getTeamId()-1).getTeamName4() + "\0",print_writers);
					
					for(int j=0; j<2; j++) {
						if(j==0) {
							containerName ="$Highlight";
						}else {
							containerName ="$Dehighlight";
						}
						
						if(top_bowler_bestst.get(i).getBestEquation() % 1000 > 0) {
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + ((top_bowler_bestst.get(i).getBestEquation() / 1000) +1) 
									+ "-" + (1000 - (top_bowler_bestst.get(i).getBestEquation() % 1000)) + "\0",print_writers);
						}
						else if(top_bowler_bestst.get(i).getBestEquation() % 1000 < 0) {
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + (top_bowler_bestst.get(i).getBestEquation() / 1000) 
									+ "-" + Math.abs(top_bowler_bestst.get(i).getBestEquation()) + "\0",print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + (top_bowler_bestst.get(j).getBestEquation() / 1000) + "-" + "0" + "\0",print_writers);
						}
					}
					
				}
			}
			break;	
		case "LB_MOST_RUNS":
			Collections.sort(this_series,new CricketFunctions.BatsmenMostRunComparator());
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_Header*GEOM*TEXT SET " + "MOST" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_SubHeader*GEOM*TEXT SET " + "RUNS" + "\0",print_writers);
			
			rowId =0;
			for(int i=0;i<=this_series.size()-1;i++) {
				rowId = rowId + 1;
				if(rowId <= 5) {
					if(this_series.get(i).getPlayerId() == FirstPlayerId) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
							+ WhichSide + "$LeaderBoard$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() 
							+ Constants.STRAIGHT_1024 + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
					
					if(highlight_player == rowId) {
						containerName ="$Highlight";
						omo = 1;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
					}else {
						containerName ="$Dehighlight";
						omo = 0;
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Name*GEOM*TEXT SET " + this_series.get(i).getPlayer().getTicker_name() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_TeamName*GEOM*TEXT SET " + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamName4() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + this_series.get(i).getRuns() + "\0",print_writers);
				}
			}
			break;
		case "LB_MOST_WICKETS":
			Collections.sort(this_series,new CricketFunctions.BowlerWicketsComparator());
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_Header*GEOM*TEXT SET " + "MOST" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_SubHeader*GEOM*TEXT SET " + "WICKETS" + "\0",print_writers);
			
			rowId =0;
			for(int i=0;i<=this_series.size()-1;i++) {
				rowId = rowId + 1;
				if(rowId <= 5) {
					if(this_series.get(i).getPlayerId() == FirstPlayerId) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
							+ WhichSide + "$LeaderBoard$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() 
							+ Constants.STRAIGHT_1024 + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
					
					if(highlight_player == rowId) {
						containerName ="$Highlight";
						omo = 1;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
					}else {
						containerName ="$Dehighlight";
						omo = 0;
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Name*GEOM*TEXT SET " + this_series.get(i).getPlayer().getTicker_name() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_TeamName*GEOM*TEXT SET " + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamName4() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + this_series.get(i).getWickets() + "\0",print_writers);
				}
			}
			break;
		case "LB_MOST_FOURS":
			Collections.sort(this_series,new CricketFunctions.BatsmanFoursComparator());
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_Header*GEOM*TEXT SET " + "MOST" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_SubHeader*GEOM*TEXT SET " + "FOURS" + "\0",print_writers);
			
			rowId =0;
			for(int i=0;i<=this_series.size()-1;i++) {
				rowId = rowId + 1;
				if(rowId <= 5) {
					if(this_series.get(i).getPlayerId() == FirstPlayerId) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
							+ WhichSide + "$LeaderBoard$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() 
							+ Constants.STRAIGHT_1024 + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
					
					if(highlight_player == rowId) {
						containerName ="$Highlight";
						omo = 1;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
					}else {
						containerName ="$Dehighlight";
						omo = 0;
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Name*GEOM*TEXT SET " + this_series.get(i).getPlayer().getTicker_name() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_TeamName*GEOM*TEXT SET " + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamName4() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + this_series.get(i).getFours() + "\0",print_writers);
				}
			}
			break;
		case "LB_MOST_SIXES":
			Collections.sort(this_series,new CricketFunctions.BatsmanSixesComparator());
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_Header*GEOM*TEXT SET " + "MOST" + "\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$LeaderBoard$Text$txt_SubHeader*GEOM*TEXT SET " + "SIXES" + "\0",print_writers);
			
			rowId =0;
			for(int i=0;i<=this_series.size()-1;i++) {
				rowId = rowId + 1;
				if(rowId <= 5) {
					if(this_series.get(i).getPlayerId() == FirstPlayerId) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" 
							+ WhichSide + "$LeaderBoard$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() 
							+ Constants.STRAIGHT_1024 + this_series.get(i).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
					}
					
					if(highlight_player == rowId) {
						containerName ="$Highlight";
						omo = 1;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 
								+ teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamBadge() + "\0", print_writers);
					}else {
						containerName ="$Dehighlight";
						omo = 0;
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + "$Select_Highlight*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Name*GEOM*TEXT SET " + this_series.get(i).getPlayer().getTicker_name() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_TeamName*GEOM*TEXT SET " + teams.get(this_series.get(i).getPlayer().getTeamId()-1).getTeamName4() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$LeaderBoard$LeaderBoardData$Rows$" + rowId + containerName + "$txt_Value*GEOM*TEXT SET " + this_series.get(i).getSixes() + "\0",print_writers);
				}
			}
			break;	
		}
		
		return Constants.OK;
	}
	
	public String BowlingCard(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
	InterruptedException {
		
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == whichInning)
				.findAny().orElse(null);
		if(inning == null) {
			return "PopulateScorecardFF: current inning is NULL";
		}
		
		if(inning.getBowling_team().getTeamName4().contains("KHILADI XI") || inning.getBowling_team().getTeamName4().contains("MASTER 11")) {
			if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
				color_name = "KHILADI_XI";
			}else if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
				color_name = "MASTER_XI";
			}
		}else {
			color_name = inning.getBowling_team().getTeamName4();
		}
		
		//--------------------------------
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BowlingCard$TapeBallOver*ACTIVE SET 0\0",print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BowlingCard$DataAll*FUNCTION*Grid*row_offset SET 50.0\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BowlingCard$Header$img_TeamLogo*TEXTURE*IMAGE SET " + 
				Constants.ISPL_LOGOS_PATH + inning.getBowling_team().getTeamBadge() + "\0",print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BowlingCard$Header$TextAll$txt_Header1*GEOM*TEXT SET " + 
				inning.getBowling_team().getTeamName2() + "\0",print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics$BowlingCard$Header$TextAll$txt_Header2*GEOM*TEXT SET " + 
				inning.getBowling_team().getTeamName3() + "\0",print_writers);
		
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 4 \0",print_writers);
		
		for(int j=1;j<=11;j++) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics$BowlingCard$DataAll$Row" + j + "$Select_BallStyle*FUNCTION*Omo*vis_con SET 0\0",print_writers);
		}
		
		int log_50_bowlerNo = 0, tape_count = 0;
	
		if ((matchAllData.getEventFile().getEvents() != null) && (matchAllData.getEventFile().getEvents().size() > 0)) {
			for(Event evnt: matchAllData.getEventFile().getEvents()) {
				if(evnt.getEventInningNumber() == whichInning) {
					if(evnt.getEventExtra() != null) {
						if(evnt.getEventExtra().equalsIgnoreCase("tape")) {
							tape_count ++;
						}
						if(evnt.getEventExtra().equalsIgnoreCase("challenge")) {
							log_50_bowlerNo = evnt.getEventBowlerNo();
						}
					}
				}
			}
		}
		
		if(tape_count == 1) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics$BowlingCard$TapeBallOver$txt_Head*GEOM*TEXT SET ISPL SWING BALL OVER\0",print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics$BowlingCard$TapeBallOver$txt_Head*GEOM*TEXT SET ISPL SWING BALL OVERS\0",print_writers);
		}
		
		Collections.sort(inning.getBowlingCard());
		if(inning.getBowlingCard() != null && inning.getBowlingCard().size() > 0) {
			for(int iRow = 0; iRow <= inning.getBowlingCard().size()-1; iRow++) {
				if(inning.getBowlingCard().get(iRow).getRuns() > 0 || ((inning.getBowlingCard().get(iRow).getOvers()*6)
						+ inning.getBowlingCard().get(iRow).getBalls()) > 0) {
					
					boc_size = (iRow+1);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BowlingCard$DataAll$Select_LineNumber*FUNCTION*Omo*vis_con SET " + boc_size + "\0",print_writers);
					
					if(inning.getBowlingCard().get(iRow).getBallTypeOverNo().contains("tape")) {
						boc_tape_on_screen = true;
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$Select_Graphics$BowlingCard$TapeBallOver*ACTIVE SET 1\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$Select_Graphics$BowlingCard$DataAll*FUNCTION*Grid*row_offset SET 50.0\0", print_writers);
					}
					
					if(inning.getInningStatus().equalsIgnoreCase(CricketUtil.PAUSE)) {
						omo = 0;
						containerName = "$Dehighlight";
						base_name = "$img_Base2";
						color_name1 = "$img_Text2";
					}else {
						switch (inning.getBowlingCard().get(iRow).getStatus().toUpperCase()) {
						case (CricketUtil.OTHER + CricketUtil.BOWLER):
							omo = 0;
							containerName = "$Dehighlight";
							base_name = "$img_Base2";
							color_name1 = "$img_Text2";
							break;
						case (CricketUtil.LAST + CricketUtil.BOWLER):
							omo = 0;
							containerName = "$Dehighlight";
							base_name = "$img_Base2";
							color_name1 = "$img_Text2";
							break;
						case (CricketUtil.CURRENT + CricketUtil.BOWLER):
							omo = 1;
							containerName = "$Highlight";
							base_name = "$img_Base1";
							color_name1 = "$img_Text1";
							break;
						}
					}
					
					
					if(base_name.contains("1")) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$Select_Graphics$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle$" + containerName + base_name + "*TEXTURE*IMAGE SET " + 
								Constants.ISPL_BASE1 + color_name + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$Select_Graphics$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle$" + containerName + color_name1 + "*TEXTURE*IMAGE SET " + 
								Constants.ISPL_TEXT1 + color_name + "\0", print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$Select_Graphics$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle$" + containerName + base_name + "*TEXTURE*IMAGE SET " + 
								Constants.ISPL_BASE2 + color_name + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$Select_Graphics$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle$" + containerName + color_name1 + "*TEXTURE*IMAGE SET " + 
								Constants.ISPL_TEXT2 + color_name + "\0", print_writers);
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle*FUNCTION*Omo*vis_con SET " + omo + "\0",print_writers);
					
					boolean googly_imp = false; String OverType = "";
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle$" + containerName + "$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$Select_OverType1*FUNCTION*Omo*vis_con SET 0\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$Select_OverType2*FUNCTION*Omo*vis_con SET 0\0",print_writers);
					
					if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),
							inning.getBowlingCard().get(iRow).getPlayerId()).isEmpty()) {
						switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),
								inning.getBowlingCard().get(iRow).getPlayerId())) {
						case "IMP_IN": case "IMP_OUT":
							googly_imp = true;
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle$" + containerName + "$select_GooglyImpact*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							break;
						}
					}
					
					if(log_50_bowlerNo == inning.getBowlingCard().get(iRow).getPlayerId()) {
						OverType = "50_50";
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
								"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$Select_OverType1*FUNCTION*Omo*vis_con SET 2\0",print_writers);
						if(inning.getBowlingCard().get(iRow).getBallTypeOverNo().contains("tape")) {
							OverType = "50_50_TAPE";
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$Select_OverType2*FUNCTION*Omo*vis_con SET 1\0",print_writers);
						}
					}else {
						if(inning.getBowlingCard().get(iRow).getBallTypeOverNo().contains("tape")) {
							OverType = "TAPE";
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$Select_OverType1*FUNCTION*Omo*vis_con SET 1\0",print_writers);
							if(inning.getBowlingCard().get(iRow).getBallTypeOverNo().contains("tape,tape")) {
								OverType = "TAPE_TAPE";
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
										"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$Select_OverType2*FUNCTION*Omo*vis_con SET 1\0",print_writers);
							}
						}
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0",print_writers);
					
					if(googly_imp) {
						if(OverType.equalsIgnoreCase("50_50") || OverType.equalsIgnoreCase("TAPE")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 105\0",print_writers);
						}else if(OverType.equalsIgnoreCase("50_50_TAPE") || OverType.equalsIgnoreCase("TAPE_TAPE")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 65\0",print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 140\0",print_writers);
						}
					}else {
						if(OverType.equalsIgnoreCase("50_50") || OverType.equalsIgnoreCase("TAPE")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 145\0",print_writers);
						}else if(OverType.equalsIgnoreCase("50_50_TAPE") || OverType.equalsIgnoreCase("TAPE_TAPE")) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
									"$BowlingCard$DataAll$Row" + (iRow+1) + "$Select_BallStyle" + containerName + "$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 120\0",print_writers);
						}
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BowlingCard$DataAll$Row" + (iRow + 1) + "$Select_BallStyle" + containerName + "$txt_Name*GEOM*TEXT SET " + 
							inning.getBowlingCard().get(iRow).getPlayer().getTicker_name() + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BowlingCard$DataAll$Row" + (iRow + 1) + "$Select_BallStyle" + containerName + "$txt_Figures*GEOM*TEXT SET " + 
							inning.getBowlingCard().get(iRow).getWickets() + "-" + inning.getBowlingCard().get(iRow).getRuns() + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$Select_Graphics$BowlingCard$DataAll$Row" + (iRow + 1) + "$Select_BallStyle" + containerName + "$txt_Overs*GEOM*TEXT SET " + 
							CricketFunctions.OverBalls(inning.getBowlingCard().get(iRow).getOvers(), inning.getBowlingCard().get(iRow).getBalls()) + "\0",print_writers);
				}
			}
		}
	
		String tapeData = getBowlerRunsOverbyOver(inning.getInningNumber(), matchAllData.getEventFile().getEvents(), matchAllData);

		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide
				+ "$Select_Graphics$BowlingCard$TapeBallOver$txt_TapeBalRuns*GEOM*TEXT SET " + tapeData.split(",")[1] + "\0",print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide
				+ "$Select_Graphics$BowlingCard$TapeBallOver$txt_Runs*GEOM*TEXT SET " + "RUN" + 
				CricketFunctions.Plural(Integer.valueOf(tapeData.split(",")[1])).toUpperCase() + "\0",print_writers);
		
		return Constants.OK;
	}
	public String Howout(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
		if(inning == null) {
			return "Howout: current inning is NULL";
		}
		
		if(inning.getFallsOfWickets() == null && inning.getFallsOfWickets().isEmpty()) {
			return "populateVizInfobarMiddleSection-Howout: FoW returned is EMPTY";
		}
		
		battingCardList.add(inning.getBattingCard().stream().filter(bc -> bc.getPlayerId() == 
			inning.getFallsOfWickets().get(inning.getFallsOfWickets().size() - 1).getFowPlayerID()).findAny().orElse(null));
		
		if(battingCardList.get(battingCardList.size()-1) == null) {
			return "populateVizInfobarMiddleSection-Howout: Last wicket returned is invalid";
		}
		
		if(inning.getBatting_team().getTeamName4().contains("KHILADI XI") || inning.getBatting_team().getTeamName4().contains("MASTER 11")) {
			if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
				color_name = "KHILADI_XI";
			}else if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
				color_name = "MASTER_XI";
			}
		}else {
			color_name = inning.getBatting_team().getTeamName4();
		}
		
		for(int i=1;i<=4;i++) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Dehighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color_name + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Dehighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color_name + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Highlight$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color_name + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Highlight$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color_name + "\0", print_writers);
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 7 \0",print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Image$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),battingCardList.get(battingCardList.size()-1).getPlayerId()).isEmpty()) {
			switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),battingCardList.get(battingCardList.size()-1).getPlayerId())) {
			case "IMP_IN": case "IMP_OUT":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Image$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				break;
			}
		}
		
		//NAME
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Text$Name$txt_FirstName*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getPlayer().getFirstname() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Text$Name$txt_LastName*GEOM*TEXT SET " + (battingCardList.get(battingCardList.size()-1).getPlayer().getSurname() != null ? 
						battingCardList.get(battingCardList.size()-1).getPlayer().getSurname() : "")  + "\0", print_writers);
		
		//PHOTO
		if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBatting_team().getTeamName4() + Constants.STRAIGHT_1024 + 
					battingCardList.get(battingCardList.size()-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerShadow*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBatting_team().getTeamName4() + Constants.STRAIGHT_1024 + 
					battingCardList.get(battingCardList.size()-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerGlow*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBatting_team().getTeamName4() + Constants.STRAIGHT_1024 + 
					battingCardList.get(battingCardList.size()-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH + inning.getBatting_team().getTeamName4() 
					+ Constants.STRAIGHT_1024 + battingCardList.get(battingCardList.size()-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerShadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH + inning.getBatting_team().getTeamName4() 
					+ Constants.STRAIGHT_1024 + battingCardList.get(battingCardList.size()-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerGlow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH + inning.getBatting_team().getTeamName4() 
					+ Constants.STRAIGHT_1024 + battingCardList.get(battingCardList.size()-1).getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}
		
		//HowOut
		if (battingCardList.get(battingCardList.size()-1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
			if (battingCardList.get(battingCardList.size()-1).getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutPartOne() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET \0", print_writers);

			} else if (battingCardList.get(battingCardList.size()-1).getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RUN_OUT)) {
				if (battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute() != null
						&& battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutPartOne() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET " + "(sub - " + battingCardList.get(battingCardList.size()-1).getHowOutPartTwo().split(" ")[0] 
								+ ")" + "\0", print_writers);
				} else {
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutText() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
				}

			} else if (battingCardList.get(battingCardList.size()-1).getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.LBW)) {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutText() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET \0", print_writers);
				
			} else if (battingCardList.get(battingCardList.size()-1).getHowOut().toUpperCase()
					.equalsIgnoreCase(CricketUtil.CAUGHT_AND_BOWLED)) {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutText() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET \0", print_writers);

			} else if (battingCardList.get(battingCardList.size()-1).getHowOut().toUpperCase().equalsIgnoreCase("timed_out")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET timed out\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET \0", print_writers);

			} else if (battingCardList.get(battingCardList.size()-1).getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.MANKAD)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutText() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET \0", print_writers);

			} else if (battingCardList.get(battingCardList.size()-1).getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CAUGHT)) {
				if (battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute() != null
						&& battingCardList.get(battingCardList.size()-1).getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES)) {
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutPartOne() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutPartTwo() + "\0", print_writers);
				} else {
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutPartOne() + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
							"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutPartTwo() + "\0", print_writers);
				}
			} else {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutPartOne() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getHowOutPartTwo() + "\0", print_writers);
			}
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line1$Dehighlight$txt_Head*GEOM*TEXT SET " + "RUNS" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line1$Dehighlight$txt_Value*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getRuns() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line2$Dehighlight$txt_Head*GEOM*TEXT SET " + "BALLS" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line2$Dehighlight$txt_Value*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getBalls() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line3$Dehighlight$txt_Head*GEOM*TEXT SET " + "4s/6s/9s" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line3$Dehighlight$txt_Value*GEOM*TEXT SET " + battingCardList.get(battingCardList.size()-1).getFours() + "/" +
				battingCardList.get(battingCardList.size()-1).getSixes() + "/" + battingCardList.get(battingCardList.size()-1).getNines() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line4$Dehighlight$txt_Head*GEOM*TEXT SET " + "S/R" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + CricketFunctions.generateStrikeRate(battingCardList.get(battingCardList.size()-1).getRuns(), 
						battingCardList.get(battingCardList.size()-1).getBalls(), 0) + "\0", print_writers);
		
		return Constants.OK;
	}
	public String BatThisMatch(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
		if(inning == null) {
			return "BatThisMatch: current inning is NULL";
		}
		
		battingCard = inning.getBattingCard().stream().filter(bc -> bc.getPlayerId() == FirstPlayerId).findAny().orElse(null);
		if(battingCard == null) {
			return status;
		}
		
		if(inning.getBatting_team().getTeamName4().contains("KHILADI XI") || inning.getBatting_team().getTeamName4().contains("MASTER 11")) {
			if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
				color_name = "KHILADI_XI";
			}else if(inning.getBatting_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
				color_name = "MASTER_XI";
			}
		}else {
			color_name = inning.getBatting_team().getTeamName4();
		}
		
		for(int i=1;i<=4;i++) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Dehighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color_name + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Dehighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color_name + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Highlight$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color_name + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Highlight$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color_name + "\0", print_writers);
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 7 \0",print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Image$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),battingCard.getPlayerId()).isEmpty()) {
			switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),battingCard.getPlayerId())) {
			case "IMP_IN": case "IMP_OUT":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Image$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				break;
			}
		}
		
		//NAME
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Text$Name$txt_FirstName*GEOM*TEXT SET " + battingCard.getPlayer().getFirstname() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Text$Name$txt_LastName*GEOM*TEXT SET " + (battingCard.getPlayer().getSurname() != null ? battingCard.getPlayer().getSurname() : "") + "\0", print_writers);
		
		//PHOTO
		if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBatting_team().getTeamName4() + Constants.STRAIGHT_1024 + 
					battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerShadow*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBatting_team().getTeamName4() + Constants.STRAIGHT_1024 + 
					battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerGlow*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBatting_team().getTeamName4() + Constants.STRAIGHT_1024 + 
					battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH + inning.getBatting_team().getTeamName4() 
					+ Constants.STRAIGHT_1024 + battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerShadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH + inning.getBatting_team().getTeamName4() 
					+ Constants.STRAIGHT_1024 + battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerGlow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH + inning.getBatting_team().getTeamName4() 
					+ Constants.STRAIGHT_1024 + battingCard.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}
		
		//HowOut
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET " + "THIS MATCH" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line1$Dehighlight$txt_Head*GEOM*TEXT SET " + "RUNS" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line1$Dehighlight$txt_Value*GEOM*TEXT SET " + battingCard.getRuns() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line2$Dehighlight$txt_Head*GEOM*TEXT SET " + "BALLS" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line2$Dehighlight$txt_Value*GEOM*TEXT SET " + battingCard.getBalls() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line3$Dehighlight$txt_Head*GEOM*TEXT SET " + "4s/6s/9s" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line3$Dehighlight$txt_Value*GEOM*TEXT SET " + battingCard.getFours() + "/" + battingCard.getSixes() + "/" + battingCard.getNines() 
				+ "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line4$Dehighlight$txt_Head*GEOM*TEXT SET " + "S/R" + "\0", print_writers);
		
		if(battingCard.getStrikeRate() != null) {
			if(battingCard.getStrikeRate().trim().isEmpty()) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Data$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + "-" + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Data$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + Math.round(Float.valueOf(battingCard.getStrikeRate())) + "\0", print_writers);
			}
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$OutStat$Data$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + "-" + "\0", print_writers);
		}
		
		return Constants.OK;
	}
	public String BallThisMatch(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
		if(inning == null) {
			return "BatThisMatch: current inning is NULL";
		}
		
		bowling_Card = inning.getBowlingCard().stream().filter(boc -> boc.getPlayerId() == FirstPlayerId).findAny().orElse(null);
		if(bowling_Card == null) {
			return status;
		}
		
		if(inning.getBowling_team().getTeamName4().contains("KHILADI XI") || inning.getBowling_team().getTeamName4().contains("MASTER 11")) {
			if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("KHILADI XI")) {
				color_name = "KHILADI_XI";
			}else if(inning.getBowling_team().getTeamName4().equalsIgnoreCase("MASTER 11")) {
				color_name = "MASTER_XI";
			}
		}else {
			color_name = inning.getBowling_team().getTeamName4();
		}
		
		for(int i=1;i<=4;i++) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Dehighlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + color_name + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Dehighlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + color_name + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Highlight$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + color_name + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Data$Line" + i + "$Highlight$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + color_name + "\0", print_writers);
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 7 \0",print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Image$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		if(!CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bowling_Card.getPlayerId()).isEmpty()) {
			switch(CricketFunctions.checkBatAndBallImpactInOutPlayerISPL(matchAllData.getEventFile().getEvents(), inning.getInningNumber(),bowling_Card.getPlayerId())) {
			case "IMP_IN": case "IMP_OUT":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$OutStat$Image$Googly_Icon$select_GooglyIcon*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				break;
			}
		}
		
		//NAME
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Text$Name$txt_FirstName*GEOM*TEXT SET " + bowling_Card.getPlayer().getFirstname() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + "$OutStat$Text$Name"
				+ "$txt_LastName*GEOM*TEXT SET " + (bowling_Card.getPlayer().getSurname() != null ? bowling_Card.getPlayer().getSurname():"")  + "\0", print_writers);
		
		//PHOTO
		if(config.getPrimaryIpAddress().equalsIgnoreCase(Constants.LOCALHOST)) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_Player*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBowling_team().getTeamName4() + Constants.STRAIGHT_1024 + 
					bowling_Card.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerShadow*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBowling_team().getTeamName4() + Constants.STRAIGHT_1024 + 
					bowling_Card.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerGlow*TEXTURE*IMAGE SET " + Constants.ISPL_PHOTO_PATH + inning.getBowling_team().getTeamName4() + Constants.STRAIGHT_1024 + 
					bowling_Card.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_Player*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH + inning.getBowling_team().getTeamName4() 
					+ Constants.STRAIGHT_1024 + bowling_Card.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerShadow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH + inning.getBowling_team().getTeamName4() 
					+ Constants.STRAIGHT_1024 + bowling_Card.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$OutStat$Image$img_PlayerGlow*TEXTURE*IMAGE SET " + "\\\\" + config.getPrimaryIpAddress() + Constants.Local_ISPL_PHOTOS_PATH + inning.getBowling_team().getTeamName4() 
					+ Constants.STRAIGHT_1024 + bowling_Card.getPlayer().getPhoto() + CricketUtil.PNG_EXTENSION + "\0", print_writers);
		}
		
		//HowOut
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Text$HowOut$txt_HowOut1*GEOM*TEXT SET \0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Text$HowOut$txt_HowOut2*GEOM*TEXT SET " + "THIS MATCH" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line1$Dehighlight$txt_Head*GEOM*TEXT SET " + "OVERS" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line1$Dehighlight$txt_Value*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowling_Card.getOvers(), bowling_Card.getBalls()) + 
				"\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line2$Dehighlight$txt_Head*GEOM*TEXT SET " + "WICKETS" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line2$Dehighlight$txt_Value*GEOM*TEXT SET " + bowling_Card.getWickets() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line3$Dehighlight$txt_Head*GEOM*TEXT SET " + "RUNS" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line3$Dehighlight$txt_Value*GEOM*TEXT SET " + bowling_Card.getRuns() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
				"$OutStat$Data$Line4$Dehighlight$txt_Head*GEOM*TEXT SET " + "ECONOMY" + "\0", print_writers);
		if(bowling_Card.getEconomyRate().equalsIgnoreCase("0.00")) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$OutStat$Data$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + "-" + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$OutStat$Data$Line4$Dehighlight$txt_Value*GEOM*TEXT SET " + bowling_Card.getEconomyRate() + "\0", print_writers);
		}
		return Constants.OK;
	}
	public String MatchPromo(List<PrintWriter> print_writers, MatchAllData matchAllData, int WhichSide) throws JsonMappingException, JsonProcessingException, 
		InterruptedException {
		
		String newDate = "",date_data = "";
		String[] dateSuffix = {
				"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
				
				"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
				
				"th", "st", "nd", "rd", "th", "th", "th", "th", "th","th",
				
				"th", "st"
		};
		
		fixtures = CricketFunctions.processAllFixtures(cricketService);
		fixture = fixtures.stream().filter(fix -> fix.getMatchnumber() == matchPromoId).findAny().orElse(null);
		if(fixture == null) {
			return "MatchPromo: fixture is NULL";
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 10\0",print_writers);
		
		if(fixture.getMatchnumber() < 10) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$NextMatch$Header$txt_Header*GEOM*TEXT SET " + "MATCH " + fixture.getMatchnumber() + "\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$NextMatch$Header$txt_Header*GEOM*TEXT SET " + fixture.getMatchfilename() + "\0", print_writers);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		if(fixture.getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
					"$NextMatch$Data$img_Text1$txt_Head*GEOM*TEXT SET " + "TOMORROW - " + fixture.getLocalTime() + "\0", print_writers);
		}else {
			cal.add(Calendar.DATE, -1);
			if(fixture.getDate().equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()))) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$NextMatch$Data$img_Text1$txt_Head*GEOM*TEXT SET " + "UP NEXT" + "\0", print_writers);
			}else {
				newDate = fixture.getDate().split("-")[0];
				if(Integer.valueOf(newDate) < 10) {
					newDate = newDate.replaceFirst("0", "");
				}
				date_data = newDate + dateSuffix[Integer.valueOf(newDate)] + " " + 
						Month.of(Integer.valueOf(fixture.getDate().split("-")[1])) + " " + fixture.getDate().split("-")[2];
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$NextMatch$Data$img_Text1$txt_Head*GEOM*TEXT SET " + date_data + " - " + fixture.getLocalTime() + "\0", print_writers);
			}
		}
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + 
				WhichSide + "$NextMatch$Data$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + "ISPL" + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + 
				WhichSide + "$NextMatch$Data$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + "ISPL" + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$NextMatch$Logo1$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + fixture.getHome_Team().getTeamBadge() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$NextMatch$Logo1$img_TeamLogoGlow*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + fixture.getHome_Team().getTeamBadge() + "\0", print_writers);
		
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$NextMatch$Logo2$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + fixture.getAway_Team().getTeamBadge() + "\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
				+ "$NextMatch$Logo2$img_TeamLogoGlow*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + fixture.getAway_Team().getTeamBadge() + "\0", print_writers);
		
		return Constants.OK;
	}
	
	public String populateInfobarIdent(List<PrintWriter> print_writers,String whatToProcess, MatchAllData matchAllData,int WhichSide) {
		
		switch (config.getBroadcaster()) {
		case Constants.ISPL:
			inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
			if(inning == null) {
				return "populateInfobarTeamNameScore: Inning return is NULL";
			}
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$txt_Team1"
					+ "*GEOM*TEXT SET " + matchAllData.getSetup().getHomeTeam().getTeamName4() + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$txt_Team2"
					+ "*GEOM*TEXT SET " + matchAllData.getSetup().getAwayTeam().getTeamName4() + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$Select_Type"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$WaterMark$Out$select_BugStyle"
					+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$txt_MatchNumber"
					+ "*GEOM*TEXT SET " + matchAllData.getSetup().getMatchIdent() + "\0", print_writers);
			
			infoIdentSection(print_writers, whatToProcess, matchAllData, WhichSide);
			return Constants.OK;	
		}
		
		return Constants.OK;	
		
	}
	public String infoIdentSection(List<PrintWriter> print_writers,String whatToProcess, MatchAllData matchAllData,int WhichSide) {
			
		switch (config.getBroadcaster()) {
		case Constants.ISPL:
			switch (whatToProcess.split(",")[2]) {
			case CricketUtil.TOSS:
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
						"$Select_Subline*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				
				if(matchAllData.getSetup().getTossWinningTeam() == matchAllData.getSetup().getHomeTeamId()) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
							"$SubLineGrp1$txt_Subline1*GEOM*TEXT SET " + matchAllData.getSetup().getHomeTeam().getTeamName4() + /*" WON THE TENX-U TIP TOP TOSS" +*/ 
							"\0", print_writers);
				} else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
							"$SubLineGrp1$txt_Subline1*GEOM*TEXT SET " + matchAllData.getSetup().getAwayTeam().getTeamName4() + /*" WON THE TENX-U TIP TOP TOSS" +*/ 
							"\0", print_writers);
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
						"$SubLineGrp1$txt_Subline2*GEOM*TEXT SET " + "CHOSE TO " + matchAllData.getSetup().getTossWinningDecision() + "\0", print_writers);
				break;
			case "VENUE":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
						"$Select_Subline*FUNCTION*Omo*vis_con SET 2\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
						"$SubLineGrp2$txt_Subline1*GEOM*TEXT SET " + "LALBHAI CONTRACTOR" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
						"$SubLineGrp2$txt_Subline2*GEOM*TEXT SET " + "STADIUM | SURAT" + "\0", print_writers);
				break;
			case "SUPEROVER":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
						"$Select_Subline*FUNCTION*Omo*vis_con SET 2\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
						"$SubLineGrp2$txt_Subline1*GEOM*TEXT SET " + "SUPER" + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
						"$SubLineGrp2$txt_Subline2*GEOM*TEXT SET " + "OVER" + "\0", print_writers);
				break;
				
			case "TOURNAMENT":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$CenterGRp$IdentInfo$Side" + WhichSide 
						+ "$txt_IdentInfo*GEOM*TEXT SET " + "ISPL - 2026" + "\0", print_writers);
				break;
			case "TARGET":
				inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
				
				if(matchAllData.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER) && matchAllData.getSetup().getMaxOvers() == 1) {
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
							"$Select_Subline*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
							"$SubLineGrp1$txt_Subline1*GEOM*TEXT SET " + inning.getBatting_team().getTeamName4()+ " NEED" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" 
							+ WhichSide + "$SubLineGrp1$txt_Subline2*GEOM*TEXT SET " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() 
							+ String.valueOf(" OFF " + matchAllData.getSetup().getMaxOvers()*6)+ " BALLS" + "\0", print_writers);
				}else {
					if(matchAllData.getSetup().getTargetOvers() != null && !matchAllData.getSetup().getTargetOvers().isEmpty()) {
						if(matchAllData.getSetup().getTargetType() != null && !matchAllData.getSetup().getTargetType().isEmpty()) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
									"$Select_Subline*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
									"$SubLineGrp1$txt_Subline1*GEOM*TEXT SET " + inning.getBatting_team().getTeamName4()+ " NEED" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
									"$SubLineGrp1$txt_Subline2*GEOM*TEXT SET " + CricketFunctions.GetTargetData(matchAllData) .getTargetRuns() + 
									String.valueOf(" OFF " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers()) 
									+ " OVERS (" + matchAllData.getSetup().getTargetType().toUpperCase() + ")\0", print_writers);
						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
									"$Select_Subline*FUNCTION*Omo*vis_con SET 1\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
									"$SubLineGrp1$txt_Subline1*GEOM*TEXT SET " + inning.getBatting_team().getTeamName4()+ " NEED" + "\0", print_writers);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
									"$SubLineGrp1$txt_Subline2*GEOM*TEXT SET " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() 
									+ String.valueOf(" OFF " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers()) + " OVERS" + "\0", print_writers);
						}
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
								"$Select_Subline*FUNCTION*Omo*vis_con SET 1\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
								"$SubLineGrp1$txt_Subline1*GEOM*TEXT SET " + inning.getBatting_team().getTeamName4()+ " NEED" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side" + WhichSide + 
								"$SubLineGrp1$txt_Subline2*GEOM*TEXT SET " + CricketFunctions.GetTargetData(matchAllData).getTargetRuns() 
								+ String.valueOf(" OFF " + CricketFunctions.GetTargetData(matchAllData).getTargetOvers()) + " OVERS" + "\0", print_writers);
					}
				}
				break;
			case CricketUtil.RESULT:
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$CenterGRp$IdentInfo$Side" + WhichSide + "$txt_IdentInfo*GEOM*TEXT SET " 
						+ CricketFunctions.GenerateMatchSummaryStatus(2, matchAllData, CricketUtil.FULL, "|", config.getBroadcaster(), true).getTargetOrResult().toUpperCase() + "\0", print_writers);
				break;
			}
			break;	
		}
		return Constants.OK;
	}
	
	public String populateGriff(String whatToProcess,int WhichSide,MatchAllData matchAllData) {
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == Integer.valueOf(whatToProcess.split(",")[1])).findAny().orElse(null);
		if(inning == null) {
			return "populateBatThisMatch: Current Inning NOT found in this match";
		}
		player = cricketService.getAllPlayer().stream().filter(plyr ->plyr.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
		if(player == null) {
			return "player not found";
		}
		
		team = cricketService.getTeams().stream().filter(tm->tm.getTeamId() == player.getTeamId()).findAny().orElse(null);
		if(team == null) {
			return "Can't find team of the player";
		}

		rowId = 0;
		int counter = 0;
		String MatchName = "";
		boolean playerFound = false,h2h_playerFound = false;
		HeadToHeadPlayer lastH2H = null;
		
		switch (whatToProcess.split(",")[0]) {
		case "Alt_F1":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$BattingCard$DataAll$Row12*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$BattingCard$DataAll$Row13*ACTIVE SET 0\0", print_writers);
			
			for(int i=1;i<=13;i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$BattingCard$DataAll$Row" + i + "$StillToBat$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$BattingCard$DataAll$Row" + i + "$Out$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$BattingCard$DataAll$Row" + i + "$NotOut$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$BattingCard$DataAll$Row" + i + "$StillToBat$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 230\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$BattingCard$DataAll$Row" + i + "$Out$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 230\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide + 
						"$BattingCard$DataAll$Row" + i + "$NotOut$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 230\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row" + i + "$Out$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + team.getTeamBadge() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row" + i + "$Out$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + team.getTeamBadge() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row" + i + "$NotOut$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + team.getTeamBadge() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row" + i + "$NotOut$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + team.getTeamBadge() + "\0", print_writers);
			}
			break;
		case "Alt_F2":
			for(int i=1;i<=13;i++) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Dehighlight$img_Base2*TEXTURE*IMAGE SET " + Constants.ISPL_BASE2 + team.getTeamBadge() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Dehighlight$img_Text2*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT2 + team.getTeamBadge() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Highlight$img_Base1*TEXTURE*IMAGE SET " + Constants.ISPL_BASE1 + team.getTeamBadge() + "\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Highlight$img_Text1*TEXTURE*IMAGE SET " + Constants.ISPL_TEXT1 + team.getTeamBadge() + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Dehighlight$Select_OverType1*FUNCTION*Omo*vis_con SET 0\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Dehighlight$Select_OverType2*FUNCTION*Omo*vis_con SET 0\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Dehighlight$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Dehighlight$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Highlight$Select_OverType1*FUNCTION*Omo*vis_con SET 0\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Highlight$Select_OverType2*FUNCTION*Omo*vis_con SET 0\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Highlight$select_GooglyImpact*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$DataAll$Row" + i + "$Highlight$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0",print_writers);
			}
			break;
		}
		
		switch (whatToProcess.split(",")[0]) {
		case "Alt_F1":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$BattingCard$RunsScored*ACTIVE SET 0\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 3\0",print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$BattingCard$Header$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + team.getTeamBadge() + "\0",print_writers);
			
			if(player.getSurname() != null) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$Header$TextAll$txt_Header1*GEOM*TEXT SET " + player.getFirstname() + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$Header$TextAll$txt_Header2*GEOM*TEXT SET " + player.getSurname() + "\0",print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$Header$TextAll$txt_Header1*GEOM*TEXT SET \0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$Header$TextAll$txt_Header2*GEOM*TEXT SET " + player.getFirstname() + "\0",print_writers);
			}
			
			rowId = 0;
			counter = 0;

			MatchName = "";
			h2h_playerFound = false;

			lastH2H = null;   // FIX: track last record

			for (HeadToHeadPlayer h2h : headToHead) {
			    if (h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
			        // ================= MATCH CHANGE CHECK =================
			        if (!MatchName.equalsIgnoreCase(h2h.getMatchFileName())) {
			            // FIX: finalize previous match
			            if (!MatchName.isEmpty() && h2h_playerFound == false) {
			                rowId++;

			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BattingCard$DataAll*FUNCTION*Grid*num_row SET " + rowId + "\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 2\0",print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 0\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Name*GEOM*TEXT SET v " + lastH2H.getOpponentTeam().getTeamName4() + "\0",print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Runs*GEOM*TEXT SET \0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Balls*GEOM*TEXT SET DNP\0", print_writers);
			            }
			            // FIX: reset for new match
			            MatchName = h2h.getMatchFileName();
			            counter = 0;
			            h2h_playerFound = false;
			        }
			        counter++;                // FIX: increment only within same match
			        lastH2H = h2h;             // FIX: save last row

			        // ================= PLAYER FOUND =================
			        if (h2h.getPlayerId() == player.getPlayerId()) {
			            rowId++;
			            h2h_playerFound = true;

			            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			            		+ "$BattingCard$DataAll*FUNCTION*Grid*num_row SET " + rowId + "\0", print_writers);
			            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			            		+ "$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 2\0", print_writers);

			            if (h2h.getInningStarted().trim().contains("Y")) {
			                if (h2h.getDismissed().trim().equalsIgnoreCase("Y")) {
			                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 0\0", print_writers);
			                } else {
			                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 1\0", print_writers);
			                }

			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$NotOut$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName4() + "\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Runs*GEOM*TEXT SET " + h2h.getRuns() + "\0", print_writers);

			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Balls*GEOM*TEXT SET " + h2h.getBallsFaced() + "\0", print_writers);
			            }
			            else if(h2h.getInningStarted().trim().contains("N")) {
			            	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 0\0", print_writers);
						    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Name*GEOM*TEXT SET v " + lastH2H.getOpponentTeam().getTeamName4() + "\0", print_writers);
						    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Runs*GEOM*TEXT SET \0", print_writers);
						    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Balls*GEOM*TEXT SET DNB\0", print_writers);
			            }
			        }
			    }
			}

			// ================= FINAL MATCH FIX =================
			if (!MatchName.isEmpty() && h2h_playerFound == false && lastH2H != null) {
			    rowId++;
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BattingCard$DataAll*FUNCTION*Grid*num_row SET " + rowId + "\0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 2\0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 0\0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Name*GEOM*TEXT SET v " + lastH2H.getOpponentTeam().getTeamName4() + "\0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Runs*GEOM*TEXT SET \0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Balls*GEOM*TEXT SET DNP\0", print_writers);
			}

			for(BattingCard bc : inning.getBattingCard()) {
				if(bc.getPlayerId() == player.getPlayerId()) {
					rowId++;
					playerFound = true;
					if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll*FUNCTION*Grid*num_row SET " + rowId + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 2\0", print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 0\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName4() + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Runs*GEOM*TEXT SET \0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Balls*GEOM*TEXT SET DNB\0",print_writers);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll*FUNCTION*Grid*num_row SET " + rowId + "\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 2\0", print_writers);
						containerName = "NotOut";
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.OUT:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 0\0",print_writers);
							break;
						case CricketUtil.NOT_OUT:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
									+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 1\0",print_writers);
							break;
						}
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle$" + containerName + "$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName4() 
								+ "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll$Row" + rowId + "$" + containerName + "$txt_Runs*GEOM*TEXT SET " + bc.getRuns() + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BattingCard$DataAll$Row" + rowId + "$" + containerName + "$txt_Balls*GEOM*TEXT SET " + bc.getBalls() + "\0",print_writers);
					}
				}
			}
			if(!playerFound) {
				rowId++;
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll*FUNCTION*Grid*num_row SET " + rowId + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row" + rowId + "$Select_BatStyle*FUNCTION*Omo*vis_con SET 2\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_NotOutStar*ACTIVE SET 0\0",print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Name*GEOM*TEXT SET v " + inning.getBowling_team().getTeamName4() + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Runs*GEOM*TEXT SET \0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row" + rowId + "$NotOut$txt_Balls*GEOM*TEXT SET DNP\0",print_writers);
			}
			
			if(rowId == 12) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*row_offset SET 45.0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row12*ACTIVE SET 1\0", print_writers);
			}else if(rowId == 13) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*row_offset SET 42.0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row12*ACTIVE SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BattingCard$DataAll$Row13*ACTIVE SET 1\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$Select_Graphics$BattingCard$DataAll*FUNCTION*Grid*row_offset SET 50.0\0", print_writers);
			}
			
			break;
		case "Alt_F2":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$BowlingCard$TapeBallOver*ACTIVE SET 0\0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$Select_Graphics*FUNCTION*Omo*vis_con SET 4 \0",print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
					+ "$BowlingCard$Header$img_TeamLogo*TEXTURE*IMAGE SET " + Constants.ISPL_LOGOS_PATH + team.getTeamBadge() + "\0",print_writers);
			
			if(player.getSurname() != null) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$Header$TextAll$txt_Header1*GEOM*TEXT SET " + player.getFirstname() + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$Header$TextAll$txt_Header2*GEOM*TEXT SET " + player.getSurname() + "\0",print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$Header$TextAll$txt_Header1*GEOM*TEXT SET " + "" + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
						+ "$BowlingCard$Header$TextAll$txt_Header2*GEOM*TEXT SET " + player.getFirstname() + "\0",print_writers);
			}
			
			lastH2H = null;   // FIX: track last entry

			for (HeadToHeadPlayer h2h : headToHead) {
			    if (h2h.getTeam().getTeamName4().equalsIgnoreCase(team.getTeamName4())) {
			        // ================= MATCH CHANGE FIX =================
			        if (!MatchName.equalsIgnoreCase(h2h.getMatchFileName())) {
			            // FIX: finalize previous match
			            if (!MatchName.isEmpty() && h2h_playerFound == false && lastH2H != null) {
			                rowId++;
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BowlingCard$DataAll$Select_LineNumber*FUNCTION*Omo*vis_con SET " + rowId + "\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BowlingCard$DataAll$Row" + rowId + "$Select_BallStyle*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Name*GEOM*TEXT SET v " + lastH2H.getOpponentTeam().getTeamName4() + "\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Figures*GEOM*TEXT SET DNP\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Overs*GEOM*TEXT SET \0", print_writers);
			            }
			            // FIX: reset for new match
			            MatchName = h2h.getMatchFileName();
			            counter = 0;
			            h2h_playerFound = false;
			        }
			        counter++;              // FIX: count only inside same match
			        lastH2H = h2h;           // FIX: remember last row

			        // ================= PLAYER FOUND =================
			        if (h2h.getPlayerId() == player.getPlayerId()) {
			            rowId++;
			            h2h_playerFound = true;
			            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			            		+ "$BowlingCard$DataAll$Select_LineNumber*FUNCTION*Omo*vis_con SET " + rowId + "\0", print_writers);
			            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			            		+ "$BowlingCard$DataAll$Row" + rowId + "$Select_BallStyle*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			            		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Name*GEOM*TEXT SET v " + h2h.getOpponentTeam().getTeamName4() + "\0", print_writers);
			            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			            		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0", print_writers);

			            if (h2h.getBallsBowled() == 0) {
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Figures*GEOM*TEXT SET DNB\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Overs*GEOM*TEXT SET \0", print_writers);
			            } else {
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			                		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Figures*GEOM*TEXT SET " + h2h.getWickets() + "-" + h2h.getRunsConceded() + "\0", print_writers);
			                CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
		                    		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(0, h2h.getBallsBowled()) + "\0", print_writers);
			            }
			        }
			    }
			}

			// ================= FINAL MATCH FIX =================
			if (!MatchName.isEmpty() && h2h_playerFound == false && lastH2H != null) {
			    rowId++;
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BowlingCard$DataAll$Select_LineNumber*FUNCTION*Omo*vis_con SET " + rowId + "\0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BowlingCard$DataAll$Row" + rowId + "$Select_BallStyle*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Name*GEOM*TEXT SET v " + lastH2H.getOpponentTeam().getTeamName4() + "\0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Figures*GEOM*TEXT SET DNP\0", print_writers);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
			    		+ "$BowlingCard$DataAll$Row" + rowId + "$Dehighlight$txt_Overs*GEOM*TEXT SET \0", print_writers);
			}

			
			boolean playerIsInBoc = false;
			if(inning.getBowlingCard() != null) {
				for(BowlingCard boc : inning.getBowlingCard()) {
					if(boc.getPlayerId() == player.getPlayerId()) {
						playerIsInBoc = true;
						rowId++;
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BowlingCard$DataAll$Select_LineNumber*FUNCTION*Omo*vis_con SET " + rowId + "\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BowlingCard$DataAll$Row" + rowId + "$Select_BallStyle*FUNCTION*Omo*vis_con SET 1\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName4() + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0",print_writers);
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Figures*GEOM*TEXT SET " + boc.getWickets() + "-" 
								+ boc.getRuns() + "\0",print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
								+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), 
										boc.getBalls()) + "\0",print_writers);
						break;
					}else {
						playerIsInBoc = false;
					}
				}
			}
			
			if(!playerIsInBoc) {
				
				for(Inning inn : matchAllData.getMatch().getInning()) {
					if(inn.getInningNumber() != inning.getInningNumber()) {
						for(BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == player.getPlayerId()) {
								playerIsInBoc = true;
								
								rowId++;
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
										+ "$BowlingCard$DataAll$Select_LineNumber*FUNCTION*Omo*vis_con SET " + rowId + "\0",print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
										+ "$BowlingCard$DataAll$Row" + rowId + "$Select_BallStyle*FUNCTION*Omo*vis_con SET 1\0",print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
										+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Name*GEOM*TEXT SET v " + inn.getBowling_team().getTeamName4() + "\0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
										+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0",print_writers);
								
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
										+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Figures*GEOM*TEXT SET DNB\0",print_writers);
								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
										+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Overs*GEOM*TEXT SET \0",print_writers);
								
								break;
							}else {
								playerIsInBoc = false;
							}
						}
					}
				}
				
				if(!playerIsInBoc) {
					rowId++;
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$BowlingCard$DataAll$Select_LineNumber*FUNCTION*Omo*vis_con SET " + rowId + "\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$BowlingCard$DataAll$Row" + rowId + "$Select_BallStyle*FUNCTION*Omo*vis_con SET 1\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Name*GEOM*TEXT SET v " + inning.getBatting_team().getTeamName4() + "\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Name*FUNCTION*Maxsize*WIDTH_X SET 180\0",print_writers);
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Figures*GEOM*TEXT SET DNP\0",print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side" + WhichSide 
							+ "$BowlingCard$DataAll$Row" + rowId + "$Highlight$txt_Overs*GEOM*TEXT SET \0",print_writers);
				}
			}
			break;
		}
		return Constants.OK;
	}
	
	public List<String> getOverbyOver(int inning,List<Event> event, MatchAllData matchAllData) {
		
		String to = "";
		int runs = 0,wicket = 0,bowlerId = 0,cr_target=0;
		boolean over_start = false,is_this_current_over = false;
		ArrayList<String> allData = new ArrayList<String>();
		if ((matchAllData.getEventFile().getEvents() != null) && (matchAllData.getEventFile().getEvents().size() > 0)) {
			for(Event evnt: matchAllData.getEventFile().getEvents()) {
				if(evnt.getEventInningNumber() == inning) {
					if(evnt.getEventType().equalsIgnoreCase(CricketUtil.CHANGE_BOWLER)) {
						to = "";
						if(evnt.getEventExtra().equalsIgnoreCase("TAPE")) {
							to = "-TO";
						}
						if(evnt.getEventExtra().equalsIgnoreCase("challenge")) {
							to = "-CR";
							cr_target = Integer.valueOf(evnt.getEventSubExtra()); 
						}
						for(BowlingCard boc : matchAllData.getMatch().getInning().get(inning - 1).getBowlingCard()) {
							if(boc.getStatus().equalsIgnoreCase(CricketUtil.CURRENT + CricketUtil.BOWLER)) {
								bowlerId = boc.getPlayerId();
							}
						}
					}
					
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
	                    switch(evnt.getEventHowOut()) {
                        case CricketUtil.CAUGHT: case CricketUtil.CAUGHT_AND_BOWLED: case CricketUtil.BOWLED: case CricketUtil.LBW: 
                        case CricketUtil.STUMPED: case CricketUtil.RUN_OUT: case CricketUtil.HIT_WICKET: case CricketUtil.HIT_BALL_TWICE:
                        case CricketUtil.RETIRED_OUT:
                        	wicket += 1;
                        	break;
                        }
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
	                    if(evnt.getEventHowOut() != null && !evnt.getEventHowOut().trim().isEmpty()) {
	                    	wicket += 1;
	                    }
	                    break;										
					}
					
					if(evnt.getEventType().equalsIgnoreCase(CricketUtil.END_OVER)) {
						if(to.equalsIgnoreCase("-CR")) {
							if(cr_target <= runs) {
								runs += (runs/2);
							}else {
								runs -= (runs/2);
							}
						}
						
						if(is_this_current_over == true) {
							allData.set(allData.size()-1, runs + "-" + wicket + "-EO" + to);
						}else {
							allData.add(runs + "-" + wicket + "-EO" + to);
						}
						runs = 0;
						wicket = 0;
						over_start = false;
					}else if(evnt.getEventOverNo() == (matchAllData.getMatch().getInning().get(inning - 1).getTotalOvers())){
						if(!evnt.getEventType().equalsIgnoreCase(CricketUtil.CHANGE_BOWLER)) {
							if(evnt.getEventBowlerNo() == bowlerId) {
								if(to.equalsIgnoreCase("-CR")) {
									if(cr_target <= runs) {
										runs += (runs/2);
									}else {
										runs -= (runs/2);
									}
								}
								
								if(over_start == false) {
									allData.add(runs + "-" + wicket + "-CO" + to);
									over_start = true;
									is_this_current_over = true;
								}
								
								if(over_start == true) {
									String rw = runs + "-" + wicket;
									allData.set(allData.size()-1, runs + "-" + wicket + "-CO" + to);
								}
							}
						}
					}
				}
			}
		}
		//System.out.println("allData = " + allData);
		return allData;
		
	}
	
	public void playChallengeWipe(List<PrintWriter> print_writers, int bonusRuns, int challengeRuns) throws InterruptedException {
		
		if((bonusRuns*2) >= challengeRuns) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$Challenge_CumRuns$ChallngeRunsText$"
					+ "txt_ChallngeRuns*GEOM*TEXT SET +" + bonusRuns + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$Challenge_CumRuns$Base$"
					+ "Select_Base*FUNCTION*Omo*vis_con SET 1\0", print_writers);
		}else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$Challenge_CumRuns$ChallngeRunsText$"
					+ "txt_ChallngeRuns*GEOM*TEXT SET -" + bonusRuns + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Bottom_DataGrp$Main$Challenge_CumRuns$Base$"
					+ "Select_Base*FUNCTION*Omo*vis_con SET 0\0", print_writers);
		}
		this_animation.processAnimation(Constants.FRONT, print_writers, "Challenge_CumRuns", "START");
		TimeUnit.MILLISECONDS.sleep(500);
		
	}
	public String powerplay(List<PrintWriter> print_writers, MatchAllData matchAllData) {
		
		if(infobar.isPowerplay_on_screen() == false) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_BottomInfo$In_Out$PowerPlay START \0", print_writers);
			 infobar.setPowerplay_on_screen(true);
        }else {
        	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_BottomInfo$In_Out$PowerPlay CONTINUE REVERSE \0", print_writers);
        	infobar.setPowerplay_on_screen(false);
        }
		return Constants.OK;
		
	}
	public String InfobarManhattan(List<PrintWriter> print_writers, MatchAllData matchAllData,int WhichInning) {
		
		int maxRuns = 0,runsIncr = 0,powerplay_omo=0;
		double lngth = 0;
		String powerPlay = "";
		
		manhattan = new ArrayList<OverByOverData>();
		manhattan = CricketFunctions.getOverByOverData(matchAllData, WhichInning,"MANHATTAN" ,matchAllData.getEventFile().getEvents());
		if(manhattan == null) {
			return "populateManhattan is null";
		}
		
		inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getInningNumber() == WhichInning)
				.findAny().orElse(null);
		if(inning == null) {
			return "PopulateManhattan: current inning is NULL";
		}
		
		for (int j = 1; j < manhattan.size(); j++) {
			if(manhattan.get(j).getInningNumber() == WhichInning) {
				if(Integer.valueOf(manhattan.get(j).getOverTotalRuns()) > maxRuns){
					maxRuns = Integer.valueOf(manhattan.get(j).getOverTotalRuns()); // 33 runs came off 34th over
				}
				while (maxRuns % 5 != 0) {     // 5 label in y-axis
			 		maxRuns = maxRuns + 1;    // keep incrementing till max runs is divisible by 5. maxRuns = 35
				}
			}
		}
		
		for(int i = 0; i < 5;i++) {
			runsIncr = maxRuns / 5; // 35/5=7 -> Y axis will be plot like 6,12,18,24,30 & 36
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Manhattan$Runs_Axis$Runs_Data"
					+ "$txt_" + (i+1) + "*GEOM*TEXT SET " + runsIncr*(i+1) + "\0", print_writers);
		}
		
		
		for(int j = 1; j <= matchAllData.getSetup().getMaxOvers(); j++) {
			
			if((j*6) <= CricketFunctions.getBallCountStartAndEndRange(matchAllData, inning).get(1)) {
				powerplay_omo = 0;
				powerPlay = "$PowerPlay";
			}
			else {
				powerplay_omo = 1;
				powerPlay = "$Normal";
			}
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Manhattan$Wickets_Axis"
					+ "$Out$Wkt_" + j + "$Select_PowerPlay*FUNCTION*Omo*vis_con SET " + powerplay_omo + "\0", print_writers);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Manhattan$Bar$"
					+ j + "$Select_PowerPlay*FUNCTION*Omo*vis_con SET " + powerplay_omo + "\0", print_writers);
			
			
			if(j < manhattan.size()) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Manhattan$Bar$"
						+ "Position*FUNCTION*Grid*num_row SET 1\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Manhattan$Bar$"
						+ "Position*FUNCTION*Grid*num_col SET " + j + "\0", print_writers);
				
				lngth = ((50 * Integer.valueOf(manhattan.get(j).getOverTotalRuns())) / maxRuns);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Manhattan$Bar$"
						+ j + powerPlay + "$Bar*GEOM*height SET " + lngth + "\0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Manhattan$Wickets_Axis"
						+ "$Out$Wkt_" + j + powerPlay + "$Select_Wickets*FUNCTION*Omo*vis_con SET " + manhattan.get(j).getOverTotalWickets() + "\0", print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Manhattan$Wickets_Axis"
						+ "$Out$Wkt_" + j + powerPlay + "$Select_Wickets*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			}
		}
		return Constants.OK;
		
	}
	
	public Infobar updateFieldPlotter(List<PrintWriter> print_writer, MatchAllData match)
			throws InterruptedException, IOException {
		if (infobar.isFieldPlotter_on_screen() == true) {
			String data = new String(Files.readAllBytes(Paths.get("C:\\Sports\\Cricket\\Fielder\\Fielder_Text\\" + 
		            "FieldPlotter.txt")));
	        // Split the content by lines and print each line separately
	        String[] lines = data.split("\n");
	        
	        String plotterData = lines[0].trim();
	        
			FieldersData fielderFormation = new FieldersData();
			fielderFormation = CricketFunctions
					.getFielderFormation(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + plotterData);

			if (fielderFormation.isCheckbox() == true) {
				populateFieldPlotter(print_writer, match);
			}
		}
		return infobar;
	}
	
	public String populateFieldPlotter(List<PrintWriter> print_writers, MatchAllData matchAllData) throws InterruptedException, IOException {
		
		String data = new String(Files.readAllBytes(Paths.get("C:\\Sports\\Cricket\\Fielder\\Fielder_Text\\" + 
	            "FieldPlotter.txt")));
        // Split the content by lines and print each line separately
        String[] lines = data.split("\n");
        
        String plotterData = lines[0].trim();
        
		FieldersData fielderFormation = new FieldersData();
		fielderFormation = CricketFunctions
				.getFielderFormation(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + plotterData);
		if (fielderFormation.getStyle().equalsIgnoreCase("RHB")) {
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp1$Geom_Side$SelectSide"
					+ "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp1$Geom_Side$SelectSide"
					+ "$Off*GEOM*TEXT SET " + "OFF" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp2$Geom_Side$SelectSide"
					+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp2$Geom_Side$SelectSide"
					+ "$Leg*GEOM*TEXT SET " + "LEG" + "\0", print_writers);
		} else {
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp1$Geom_Side$SelectSide"
					+ "*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp1$Geom_Side$SelectSide"
					+ "$Off*GEOM*TEXT SET " + "LEG" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp2$Geom_Side$SelectSide"
					+ "*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$SideGrp2$Geom_Side$SelectSide"
					+ "$Leg*GEOM*TEXT SET " + "OFF" + "\0", print_writers);
		}
		for (int i = 0; i <= fielderFormation.getFielders().size() - 1; i++) {
			double ScaleX = 0, ScaleY = 0;
			ScaleX = ((-186) + (341 * ((fielderFormation.getFielders().get(i).getLeftLocation() - 10) / 457.0)));
			ScaleY = ((-186) + (341 * ((fielderFormation.getFielders().get(i).getTopLocation() - 50) / 427.0)))+10;

			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll" + (i + 1) + 
					"*TRANSFORMATION" + "*POSITION*X SET " + ScaleX + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll" + (i + 1) + 
					"*TRANSFORMATION" + "*POSITION*Z SET " + ScaleY + "\0", print_writers);
			
			if (fielderFormation.getFielders().get(i).getFielderhighlight().equalsIgnoreCase("YES")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll" + (i + 1) + 
						"$PositionY$PositionX$SelectPlayer*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*STAGE*DIRECTOR*Loop START \0", print_writers);
			} else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll" + (i + 1) + 
						"$PositionY$PositionX$SelectPlayer*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
			}
		}
//		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Preview.png Plotter 1.000 \0");
		TimeUnit.MILLISECONDS.sleep(100);
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
							//runs = 0;
							//wicket = 0;
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

	public void speed(PrintWriter print_writer, MatchAllData match) throws IOException, InterruptedException {

		String text_to_return = "";
		int lineIndex1 = 1;
		boolean found1 = false;
		BufferedReader br = new BufferedReader(new FileReader(CricketUtil.CRICKET_DIRECTORY + "Speed/SPEED.txt"));
		
		while ((text_to_return = br.readLine()) != null) {
			if (lineIndex1 == 1) {
				
				if(Double.valueOf(text_to_return) < 135.0) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$AllSections$Score$Bowler$Speed$select_SpeedType"
							+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$AllSections$Score$Bowler$Speed$NormalSpeed$txt_Figure"
							+ "*ANIMATION*KEY*$SP_KEY1*VALUE SET " + text_to_return + "\0",print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$AllSections$Score$Bowler$Speed$select_SpeedType"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$AllSections$Score$Bowler$Speed$HighSpeed$txt_Figure"
							+ "*ANIMATION*KEY*$HSP_KEY1*VALUE SET " + text_to_return + "\0",print_writers);
				}
				
				TimeUnit.MILLISECONDS.sleep(300);
				found1 = true;
				break;
			}
			lineIndex1++;
		}
		if (!found1) {
			// System.out.println("Line Not There");
		}
		
		this_animation.processAnimation(Constants.FRONT, print_writers, "Speed", "START");
		infobar.setLast_speed_value(match.getMatch().getCurrent_speed());
		for (Inning inn : match.getMatch().getInning()) {
			if (inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
				infobar.setLast_ball_value(String.valueOf(inn.getTotalBalls()));
				infobar.setLast_wide_value(String.valueOf(inn.getTotalWides()));
				infobar.setLast_noball_value(String.valueOf(inn.getTotalNoBalls()));
			}
		}
	}
	
	public void ResultAnimation(String typeOfAimation) throws InterruptedException, IOException {
		switch (typeOfAimation) {
		case "ANIMATE_OUT":
			this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Equation", "CONTINUE");
			break;
		case "CHANGE_ON":
			switch(infobar.getLast_full_section().toUpperCase()) {
			case CricketUtil.PROJECTED:
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ProjectedScore", "START");
				break;
			case CricketUtil.BOUNDARY:
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Boundaries", "START");
				break;
			case CricketUtil.EXTRAS:
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Extras", "START");
				break;
			case "LAST_WICKET":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastWicket", "START");
				break;
			case "BALLS_SINCE_LAST_BOUNDARY":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$BallSince", "START");
				break;
			case "THIS_OVER":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ThisOver", "START");
				break;
			case "CRR": case "RRR": case "REVIEWS_REMAINING":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRates", "START");
				break;
			case "LINE_UP":
				this_animation.processAnimation(Constants.FRONT, print_writers, "OutFor11", "CONTINUE REVERSE");
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Playing11", "START");
				break;
			case "LAST_X_BALLS":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRateInnings1", "START");
				break;
			case "LAST_X_BALLS_WITHOUT_CRR":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastX", "START");
				break;
			case "COMPARE":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Comparison", "START");
				break;
			case "COMMENTATORS": case "FREE_TEXT":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$GenericText1Line", "START");
				break;
			}
			this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Equation", "START");
			break;
		case "CUTBACK":
			switch(infobar.getLast_full_section().toUpperCase()) {
			case CricketUtil.PROJECTED:
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ProjectedScore", "SHOW 0.0");
				break;
			case CricketUtil.BOUNDARY:
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Boundaries", "SHOW 0.0");
				break;
			case CricketUtil.EXTRAS:
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Extras", "SHOW 0.0");
				break;
			case "LAST_WICKET":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastWicket", "SHOW 0.0");
				break;
			case "BALLS_SINCE_LAST_BOUNDARY":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$BallSince", "SHOW 0.0");
				break;
			case "THIS_OVER":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ThisOver", "SHOW 0.0");
				break;
			case "CRR": case "RRR": case "REVIEWS_REMAINING":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRates", "SHOW 0.0");
				break;
			case "LINE_UP":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Playing11", "SHOW 0.0");
				break;
			case "LAST_X_BALLS":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRateInnings1", "SHOW 0.0");
				break;
			case "LAST_X_BALLS_WITHOUT_CRR":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastX", "SHOW 0.0");
				break;
			case "COMPARE":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Comparison", "SHOW 0.0");
				break;
			case "COMMENTATORS": case "FREE_TEXT":
				this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$GenericText1Line", "SHOW 0.0");
				break;
			}
			
			this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Equation", "SHOW 2.180");
			this_animation.processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Equation", "SHOW 0.0");
			break;

		default:
			this_animation.processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Equation", "START");
			break;
		}
	}
	
	public void setPositionOfScoreBug(String whatToProcess,int WhichSide,Configuration config,int subline)
	{
		String SB_Position = "";
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.ISPL:
			switch (whatToProcess.split(",")[0]) {
			case "Control_F12": case "Shift_F12":
				SB_Position = "240";
				break;
			case "F12": case CricketUtil.BATSMAN:
				SB_Position = "368";
				break;
			case "IDENT":
				SB_Position = "180";
				break;
			case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER": case "CHALLENGED_IDENT": case "CHALLENGED_RUNS": case "CHALLENGED_RUNS_CUMM": 
			case "TAPE_BALL_FULL": case "BATTINGCARD": case "HOWOUT": case "CURR_PARTNERSHIP": case "TARGET": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
			case "SUPER_OVER_FULL": case "SB_MATCH_PROMO": case "POINTS_TABLE": case "TEAM_FORMGUIDE": case "LB_MOST_RUNS": case "LB_MOST_WICKETS":
			case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY":
			case "LB_TAPE_BALL_OVER": case "MVP_LB_IDENT": case "MVP_LB_SINGLE_PLAYER": case "MVP_LB_ALL_PLAYER":
				SB_Position = "895";
				break;
			case "BAT_GRIFF":
				//SB_Position = "895";
				SB_Position = String.valueOf((455 + (40*rowId)));
				break;
			case "BALL_GRIFF":
				SB_Position = String.valueOf((385 + (40*rowId)));
				break;
			case "BOWLINGCARD":
				if(boc_tape_on_screen) {
					SB_Position = String.valueOf((495 + (40*boc_size)));
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side"
							+ WhichSide + "$BowlingCard$TapeBallOver$Set_Runs_Side_" + WhichSide + "_Position_Y*TRANSFORMATION*POSITION*Y SET " 
							+ String.valueOf((405 - (40*boc_size))) + "\0",print_writers);
				}else {
					SB_Position = String.valueOf((385 + (40*boc_size)));
				}
				break;
			case "TAPE_BALL_SHORT":
				SB_Position = "545";
				break;
			case "SUPER_OVER_SHORT":
				SB_Position = "495";
				break;
			case "SUPER_OVER_THIS_OVER":
				SB_Position = "815";
				break;
			case "i": case ";":
				SB_Position = "625.5";
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Main$Animations$Position_Y_For_TapeStatic"
						+ "*TRANSFORMATION*POSITION*Y SET " + "25" + "\0",print_writers);
				break;
				
			case "w": case "f": case "s": case "0": case "8":
				switch (infobar.getLast_middle_section()) {
				case "TAPE_BALL_SHORT":
					SB_Position = "804.0";
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Main$Animations$Position_Y_For_TapeStatic"
							+ "*TRANSFORMATION*POSITION*Y SET " + "-210.0" + "\0",print_writers);
					break;
				case "SUPER_OVER_SHORT":
					SB_Position = "756.5";
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Main$Animations$Position_Y_For_TapeStatic"
							+ "*TRANSFORMATION*POSITION*Y SET " + "-150.0" + "\0",print_writers);
					break;
				default:
					SB_Position = "625.5";
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Main$Animations$Position_Y_For_TapeStatic"
							+ "*TRANSFORMATION*POSITION*Y SET " + "25" + "\0",print_writers);
					break;
				}
				break;
			}
			
			if(WhichSide == 1) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$WhiteBase"
						+ "*ANIMATION*KEY*$WB1*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$Mask__1_"
						+ "*ANIMATION*KEY*$M1*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$BaseGlow"
						+ "*ANIMATION*KEY*$BG1*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$PatternMask$Pattern"
						+ "*ANIMATION*KEY*$S1*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$PatternOutlineMask$"
						+ "PatternOutline*ANIMATION*KEY*$OS1*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$Outline"
						+ "*ANIMATION*KEY*$O1*VALUE SET " + SB_Position + "\0",print_writers);
				
			}else if(WhichSide == 2) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$WhiteBase"
						+ "*ANIMATION*KEY*$WB2*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$Mask__1_"
						+ "*ANIMATION*KEY*$M2*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$BaseGlow"
						+ "*ANIMATION*KEY*$BG2*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$PatternMask$Pattern"
						+ "*ANIMATION*KEY*$S2*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$PatternOutlineMask$"
						+ "PatternOutline*ANIMATION*KEY*$OS2*VALUE SET " + SB_Position + "\0",print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$TopBase$BaseAll$Outline"
						+ "*ANIMATION*KEY*$O2*VALUE SET " + SB_Position + "\0",print_writers);
			}
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> GetPreviewData(String whatToProcess,Configuration session_configuration, MatchAllData matchAllData) throws JsonMappingException, 
		JsonProcessingException, InterruptedException {
		
		List<String> statsData = new ArrayList<String>();
		stat = new Statistics();
		statsType = new StatsType();
		int k =0;
		String best = "-";
		
		switch ((whatToProcess.contains(",") ? whatToProcess.split(",")[0] : whatToProcess)) {
		case "Alt_3": case "Alt_4":
			System.out.println(whatToProcess);
			
			player = CricketFunctions.getPlayerFromMatchData(Integer.valueOf(whatToProcess.split(",")[2]), matchAllData);
			if(player == null) {
				statsData.add("InfoBarPlayerProfile: Player Id not found [" + whatToProcess.split(",")[2] + "]");
				return (List<T>) statsData;
			}
			
			if(whatToProcess.split(",")[3].equalsIgnoreCase("ISPL S1") || whatToProcess.split(",")[3].equalsIgnoreCase("ISPL S2")) {
				statsType = statsTypes.stream().filter(st -> st.getStatsShortName().equalsIgnoreCase(whatToProcess.split(",")[3])).findAny().orElse(null);
				if(statsTypes == null) {
					statsData.add("InfoBarPlayerProfile: Stats Type not found for profile [" + whatToProcess.split(",")[3] + "]");
					return (List<T>) statsData;
				}
				
				stat = statistics.stream().filter(st -> st.getPlayerID() == player.getPlayerId() && statsType.getStatsId() == st.getStatsTypeId()).findAny().orElse(null);
				if(stat == null) {
					statsData.add("InfoBarPlayerProfile: Stats not found for Player Id [" + whatToProcess.split(",")[2] + "]");
					return (List<T>) statsData;
				}
				stat.setStats_type(statsType);
				
			}else if(whatToProcess.split(",")[3].equalsIgnoreCase("ISPL_CAREER")) {
				
				Statistics statS1 = null, statS2=null; 
				
				statS1 = CricketFunctions.getStatsByType(Integer.valueOf(whatToProcess.split(",")[2]), "ISPL S1", statsTypes, statistics);
			    statS2 = CricketFunctions.getStatsByType(Integer.valueOf(whatToProcess.split(",")[2]), "ISPL S2", statsTypes, statistics);
			    
			    if (statS1 == null && statS2 == null) {
			    	statsData.add("InfoBarPlayerProfile: Stats not found for Player Id [" + whatToProcess.split(",")[2] + "]");
			    }
			    
			    BeanUtils.copyProperties(statS1, stat);
			    stat = CricketFunctions.mergeIsplCareerStats(stat, statS2);
			    
				statsType = statsTypes.stream().filter(st -> st.getStatsShortName().equalsIgnoreCase("D10")).findAny().orElse(null);
				stat.setStats_type(statsType);
				
				stat = CricketFunctions.updateTournamentWithH2h(stat, headToHead, matchAllData, CricketUtil.FULL);
				stat = CricketFunctions.updateStatisticsWithMatchData(stat, matchAllData, CricketUtil.FULL);
			}else if(whatToProcess.split(",")[3].equalsIgnoreCase("THIS_SERIES") || whatToProcess.split(",")[3].equalsIgnoreCase("THIS_SERIES_TAPE_BALL")){
				
				this_series = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead, cricketService, matchAllData, past_tournament_stats);
				tournament = this_series.stream().filter(st -> st.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
				
				for(Tournament tourn : this_series) {
					for(BestStats bs : tourn.getBatsman_best_Stats()) {
						top_batsman_beststats.add(bs);
					}
					for(BestStats bfig : tourn.getBowler_best_Stats()) {
						top_bowler_beststats.add(bfig);
					}
					for(BestStats tapeBall : tourn.getTapeBall_best_Stats()) {
						tapeBall_beststats.add(tapeBall);
					}
				}
				
				Collections.sort(top_batsman_beststats, new CricketFunctions.PlayerBestStatsComparator());
				Collections.sort(top_bowler_beststats, new CricketFunctions.PlayerBestStatsComparator());
				Collections.sort(tapeBall_beststats, new CricketFunctions.PlayerBestStatsComparator());
				
				switch (whatToProcess.split(",")[0]) {
				case "Alt_3":
					for(int j=0;j<= top_batsman_beststats.size()-1;j++) {
						if(top_batsman_beststats.get(j).getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])) {
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
				case "Alt_4":
					if(whatToProcess.split(",")[3].equalsIgnoreCase("THIS_SERIES")){
						for(int j=0;j<= top_bowler_beststats.size()-1;j++) {
							if(top_bowler_beststats.get(j).getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])) {
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
							}else if(top_bowler_beststats.get(j).getPlayerId() != Integer.valueOf(whatToProcess.split(",")[2])) {
								best = "-";
							}
						}
					}else if(whatToProcess.split(",")[3].equalsIgnoreCase("THIS_SERIES_TAPE_BALL")) {
						for(int j=0;j<= tapeBall_beststats.size()-1;j++) {
							if(tapeBall_beststats.get(j).getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])) {
								if(k == 1) {
									break;
								}
								if(k == 0) {
									k += 1;
									if(tapeBall_beststats.get(j).getBestEquation() % 1000 > 0) {
										best = ((tapeBall_beststats.get(j).getBestEquation() / 1000) +1) + "-" + (1000 - (tapeBall_beststats.get(j).getBestEquation() % 1000));
										break;
									}
									else if(tapeBall_beststats.get(j).getBestEquation() % 1000 < 0) {
										best = (tapeBall_beststats.get(j).getBestEquation() / 1000) + "-" + Math.abs(tapeBall_beststats.get(j).getBestEquation());
										break;
									}
									else if(tapeBall_beststats.get(j).getBestEquation() != 0) {
										if(tapeBall_beststats.get(j).getBestEquation() % 1000 == 0) {
											best = (tapeBall_beststats.get(j).getBestEquation() / 1000) + "-" + "0";
											break;
										}
									}
									break;
								}
							}else if(tapeBall_beststats.get(j).getPlayerId() != Integer.valueOf(whatToProcess.split(",")[2])) {
								best = "-";
							}
						}
					}
					break;
				}
			}
			
			if(whatToProcess.split(",")[3].equalsIgnoreCase("THIS_SERIES")) {
				statsData.add(player.getFull_name() + " - " + "ISPL SEASON 3");
			}else if(whatToProcess.split(",")[3].equalsIgnoreCase("THIS_SERIES_TAPE_BALL")) {
				statsData.add(player.getFull_name() + " - " + "IN ISPL SWING BALL OVERS");
			}else if(whatToProcess.split(",")[3].equalsIgnoreCase("ISPL S2")) {
				statsData.add(player.getFull_name() + " - " + "ISPL SEASON 2");
			}else if(whatToProcess.split(",")[3].equalsIgnoreCase("ISPL S1")) {
				statsData.add(player.getFull_name() + " - " + "ISPL SEASON 1");
			}else if(whatToProcess.split(",")[3].equalsIgnoreCase("SINGLE_DATA")) {
				switch (whatToProcess.split(",")[4]) {
				case "BATTING_STYLE": case "BOWLING_STYLE":
					if(player.getDebut().equalsIgnoreCase(CricketUtil.YES)) {
						statsData.add(player.getFull_name() + " - " + "ON DEBUT");
					}else {
						statsData.add(player.getFull_name());
					}
					break;
				default:
					statsData.add(player.getFull_name() + " - " + "THIS MATCH");
					break;
				}
			}else {
				statsData.add(player.getFull_name() + " - " + whatToProcess.split(",")[3].replace("_", " "));
			}
			
			switch (whatToProcess.split(",")[3]) {
			case "SINGLE_DATA":
				switch (whatToProcess.split(",")[4]) {
				case "BATTING_STYLE":
					statsData.add("BATTING STYLE," + (player.getBattingStyle() != null ? CricketFunctions.getbattingstyle(player.getBattingStyle(), 
							CricketUtil.SHORT, true, true).toUpperCase() : "-"));
					break;
				case "STRIKE_RATE": case "BOUNDARY": case "RUNS_BALLS":
					inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
					battingCard = inning.getBattingCard().stream().filter(bc -> bc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
					
					if(whatToProcess.split(",")[4].equalsIgnoreCase("STRIKE_RATE")) {
						statsData.add("STRIKE RATE," + CricketFunctions.generateStrikeRate(battingCard.getRuns(), battingCard.getBalls(), 0));
					}
					else if(whatToProcess.split(",")[4].equalsIgnoreCase("BOUNDARY")) {
						statsData.add("4/6/9," + battingCard.getFours() + "/" + battingCard.getSixes() + "/" + battingCard.getNines());
					}
					else if(whatToProcess.split(",")[4].equalsIgnoreCase("RUNS_BALLS")) {
						statsData.add("RUNS," + battingCard.getRuns()+(battingCard.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)?"*":""));
						statsData.add("BALLS," + battingCard.getBalls());
					}
					break;
				case "BOWLING_STYLE":
					statsData.add("BOWLING STYLE," + (player.getBowlingStyle() != null ? CricketFunctions.getbowlingstyle(player.getBowlingStyle()).toUpperCase() : "-"));
					break;
				case "ECONOMY": case "FIGURES":
					inning = matchAllData.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null);
					bowling_Card = inning.getBowlingCard().stream().filter(boc -> boc.getPlayerId() == Integer.valueOf(whatToProcess.split(",")[2])).findAny().orElse(null);
					
					if(bowling_Card != null) {
						if(whatToProcess.split(",")[4].equalsIgnoreCase("ECONOMY")) {
							statsData.add("ECONOMY," + (!bowling_Card.getEconomyRate().equalsIgnoreCase("0.00") ? bowling_Card.getEconomyRate() : "-"));
						}
						else if(whatToProcess.split(",")[4].equalsIgnoreCase("FIGURES")) {
							statsData.add("RUNS," + bowling_Card.getRuns());
							statsData.add("WICKETS," + bowling_Card.getWickets());
							statsData.add("OVERS," + CricketFunctions.OverBalls(bowling_Card.getOvers(), bowling_Card.getBalls()));
						}
					}else{
						statsData.add("ERROR," + "InfoBarPlayerProfile: Player Id not found in Bowling Card");
					}
					break;
				}
				break;
			case "THIS_SERIES":
				statsData.add("MATCHES," + tournament.getMatches());
				switch ((whatToProcess.contains(",") ? whatToProcess.split(",")[0] : whatToProcess)) {
				case "Alt_3":
					statsData.add("RUNS," + tournament.getRuns());
					if(!CricketFunctions.generateStrikeRate(tournament.getRuns(), tournament.getBallsFaced(), 0).isEmpty()) {
						statsData.add("STRIKE RATE," + CricketFunctions.generateStrikeRate(tournament.getRuns(), tournament.getBallsFaced(), 0));
					}else {
						statsData.add("STRIKE RATE," + "-");
					}
					statsData.add("BEST," + best);
					break;
				case "Alt_4":
					statsData.add("WICKETS," + tournament.getWickets());
					statsData.add("ECONOMY," + CricketFunctions.getEconomy(tournament.getRunsConceded(), tournament.getBallsBowled(), 2, "-"));
					statsData.add("BEST," + best);
					break;
				}
				break;
			case "THIS_SERIES_TAPE_BALL":
				statsData.add("MATCHES," + tournament.getMatches());
				statsData.add("WICKETS," + tournament.getTapeBall_wickets());
				statsData.add("ECONOMY," + CricketFunctions.getEconomy(tournament.getTapeBall_runs(), tournament.getTapeBall_balls(), 2, "-"));
				statsData.add("DOTS," + tournament.getTapeBall_dotsBall());
				break;
			default:
				statsData.add("MATCHES," + stat.getMatches());
				switch ((whatToProcess.contains(",") ? whatToProcess.split(",")[0] : whatToProcess)) {
				case "Alt_3":
					statsData.add("RUNS," + stat.getRuns());
					statsData.add("STRIKE RATE," + CricketFunctions.generateStrikeRate(stat.getRuns(), stat.getBallsFaced(), 0));
					statsData.add("BEST," + stat.getBestScore());
					break;
				case "Alt_4":
					statsData.add("WICKETS," + stat.getWickets());
					statsData.add("ECONOMY," + CricketFunctions.getEconomy(stat.getRunsConceded(), stat.getBallsBowled(), 2, slashOrDash));
					statsData.add("BEST," + stat.getBestFigures());
					break;
				}
				break;
			}
			return (List<T>) statsData;
		}
		return null;
	}
}