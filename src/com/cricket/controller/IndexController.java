package com.cricket.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cricket.captions.Animation;
import com.cricket.captions.BugsAndMiniGfx;
import com.cricket.captions.Caption;
import com.cricket.captions.Constants;
import com.cricket.captions.FullFramesGfx;
import com.cricket.captions.InfobarGfx;
import com.cricket.captions.LofInfobarGfx;
import com.cricket.captions.LowerThirdGfx;
import com.cricket.captions.Scene;
import com.cricket.containers.Infobar;
import com.cricket.containers.Stats;
import com.cricket.ispl.mvp_leaderBoard;
import com.cricket.model.BestStats;
import com.cricket.model.Bugs;
import com.cricket.model.Commentator;
import com.cricket.model.Configuration;
import com.cricket.model.DuckWorthLewis;
import com.cricket.model.EventFile;
import com.cricket.model.FieldersData;
import com.cricket.model.Fixture;
import com.cricket.model.Ground;
import com.cricket.model.HeadToHead;
import com.cricket.model.HeadToHeadPlayer;
import com.cricket.model.HeadToHeadTeam;
import com.cricket.model.InfobarStats;
import com.cricket.model.Match;
import com.cricket.model.MatchAllData;
import com.cricket.model.MatchStats;
import com.cricket.model.NameSuper;
import com.cricket.model.POTT;
import com.cricket.model.PerformanceBug;
import com.cricket.model.Player;
import com.cricket.model.Playoff;
import com.cricket.model.Setup;
import com.cricket.model.Staff;
import com.cricket.model.Statistics;
import com.cricket.model.StatsType;
import com.cricket.model.Team;
import com.cricket.model.Tournament;
import com.cricket.model.VariousText;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class IndexController 
{
	@Autowired
	CricketService cricketService;
	
	public static HeadToHead headToHead = new HeadToHead();
	public static Configuration session_configuration = new Configuration();
	public static MatchAllData session_match;
	public static String expiry_date = "2026-12-31";
	public static String current_date;
	public static Scene this_scene;
	public static Caption this_caption;
	public static Animation this_animation;
	public static List<PrintWriter> print_writers;
	public static MatchStats MatchStats;

	public static Map<Integer, List<String>> this_seriesPowerplay = new HashMap<>();
	public static List<Tournament> past_tournament_stats = new ArrayList<>();
	public static Tournament past_tournament_boundaries = new Tournament();
	public static List<BestStats> past_tape = new ArrayList<>();
	public static List<Statistics> session_statistics = new ArrayList<>();
	public static Statistics session_statistics_past_matches = new Statistics();
	public static List<NameSuper> session_name_super = new ArrayList<>();
	public static List<Bugs> session_bugs = new ArrayList<>();
	public static List<InfobarStats> session_infoBarStats = new ArrayList<>();
	public static List<Fixture> session_fixture = new ArrayList<>();
	public static List<Team> session_team = new ArrayList<>();
	public static List<Ground> session_ground = new ArrayList<>();
	public static List<VariousText> session_variousText = new ArrayList<>();
	public static List<Commentator> session_commentator = new ArrayList<>();
	public static List<Staff> session_staff = new ArrayList<>();
	public static List<Player> session_players = new ArrayList<>();
	public static List<POTT> session_pott = new ArrayList<>();
	public static List<Playoff> session_playoff = new ArrayList<>();
	public static List<String> session_teamChanges = new ArrayList<>();
	public static List<PerformanceBug> session_performance_bug = new ArrayList<>();

	public static FieldersData fielderFormation = new FieldersData();
	public static BugsAndMiniGfx this_bugs_mini = new BugsAndMiniGfx();
	public static List<DuckWorthLewis> session_dls = new ArrayList<>();

	private static final Map<String, Comparator<Tournament>> SORT_MAP;

	private final File speedFile = new File("C:\\Sports\\Cricket\\Speed\\SPEED.txt");
	private final ObjectMapper objectMapper = new ObjectMapper();
	private long last_match_time_stamp = 0;
	private boolean show_speed = false;
//	private long plotter_match_time_stamp1 = 0;
//	private long plotter_match_time_stamp2 = 0;
//	private long plotter_match_time_stamp3 = 0;
//	private long plotter_match_time_stamp4 = 0;
//	private long plotter_match_time_stamp = 0;
	private long speed_match_time_stamp = 0;
//	private long speedfile_match_time_stamp = 0;
//	private String plotterData, speedPath;
	public boolean Plotter_file_change = false;
	public String expiryDate = "";
	
	static {
	    Map<String, Comparator<Tournament>> map = new HashMap<>();

	    Comparator<Tournament> mostRuns =
	            new CricketFunctions.BatsmenMostRunComparator();
	    Comparator<Tournament> mostWickets =
	            new CricketFunctions.BowlerWicketsComparator();
	    Comparator<Tournament> fours =
	            new CricketFunctions.BatsmanFoursComparator();
	    Comparator<Tournament> sixes =
	            new CricketFunctions.BatsmanSixesComparator();
	    Comparator<Tournament> strikeRate =
	            new CricketFunctions.BestBatsmanStrikeRateComparator();
	    Comparator<Tournament> economy =
	            new CricketFunctions.BestBowlerEconomyComparator();

	    map.put("z", mostRuns);
	    map.put("Shift_@", mostRuns);
	    map.put("Alt_Shift_K", mostRuns);

	    map.put("x", mostWickets);
	    map.put("Shift_$", mostWickets);
	    map.put("Alt_Shift_X", mostWickets);

	    map.put("c", fours);
	    map.put("Control_Shift_@", fours);
	    map.put("Alt_Shift_T", fours);

	    map.put("v", sixes);
	    map.put("Control_Alt_5", sixes);
	    map.put("Alt_Shift_V", sixes);

	    map.put("Control_Shift_Z", strikeRate);
	    map.put("Control_Alt_9", strikeRate);

	    map.put("Control_Shift_Y", economy);
	    map.put("Control_Alt_0", economy);

	    SORT_MAP = Collections.unmodifiableMap(map);
	}
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model, 
		@RequestParam(value = "select_type", required = false, defaultValue = "") String select_type) 
		throws JAXBException, MalformedURLException, IOException, IllegalAccessException, InvocationTargetException 
	{
		
		if(current_date == null || current_date.isEmpty()) {
			current_date = CricketFunctions.getOnlineCurrentDate();
		}

		if(select_type.trim().isEmpty()) {
			model.addAttribute("match_files", new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
				@Override
			    public boolean accept(File pathname) {
			        String name = pathname.getName().toLowerCase();
			        return name.endsWith(".json") && pathname.isFile();
			    }
			}));
		}
		model.addAttribute("configuration_files", new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".xml") && pathname.isFile();
		    }
		}));
		
		return "initialise";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMatchesByType", method = RequestMethod.GET)
	public List<String> getMatchesByType(
	        @RequestParam(value = "select_type", required = false, defaultValue = "") String select_type)
	        throws IOException {

	    File[] files;
	    
	    if (select_type.isEmpty()) {
	        files = new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY)
	                .listFiles(pathname -> pathname.isFile() && pathname.getName().toLowerCase().endsWith(".json"));
	    } else {
	    	files = new File(CricketUtil.CRICKET_ARCHIVE_DIRECTORY + CricketUtil.ARCHIVE_MATCHES_DIRECTORY + select_type + "/" +
	                CricketUtil.MATCHES_DIRECTORY)
	                .listFiles(pathname -> pathname.isFile() && pathname.getName().toLowerCase().endsWith(".json"));
	    }

	    List<String> matchNames = new ArrayList<>();
	    if (files != null) {
	        for (File f : files) {
	            matchNames.add(f.getName());
	        }
	    }

	    return matchNames;
	}

	@RequestMapping(value = {"/Help"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String HelpPage()  
	{
		return "Help";
	}
		
	@RequestMapping(value = {"/output"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String outputPage(ModelMap model,
			@RequestParam(value = "configuration_file_name", required = false, defaultValue = "") String configuration_file_name,
			@RequestParam(value = "select_cricket_matches", required = false, defaultValue = "") String selectedMatch,
			@RequestParam(value = "select_type", required = false, defaultValue = "") String select_type,
			@RequestParam(value = "select_broadcaster", required = false, defaultValue = "") String select_broadcaster,
			@RequestParam(value = "select_second_broadcaster", required = false, defaultValue = "") String select_second_broadcaster,
			@RequestParam(value = "qtIPAddress", required = false, defaultValue = "") String qtIPAddress,
			@RequestParam(value = "qtPortNumber", required = false, defaultValue = "0") Integer qtPortNumber,
			@RequestParam(value = "generateInteractiveFile", required = false, defaultValue = "") String generateInteractiveFile,
			@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddress,
			@RequestParam(value = "vizPortNumber", required = false, defaultValue = "0") Integer vizPortNumber,
			@RequestParam(value = "vizSceneName", required = false, defaultValue = "") String vizScene,
			@RequestParam(value = "vizLanguage", required = false, defaultValue = "") String vizLanguage,
			@RequestParam(value = "primaryVariousOptions", required = false, defaultValue = "") String primaryVariousOptions,
			@RequestParam(value = "vizSecondaryIPAddress", required = false, defaultValue = "") String vizSecondaryIPAddress,
			@RequestParam(value = "vizSecondaryPortNumber", required = false, defaultValue = "0") Integer vizSecondaryPortNumber,
			@RequestParam(value = "vizSecondaryScene", required = false, defaultValue = "") String vizSecondaryScene,
			@RequestParam(value = "vizSecondaryLanguage", required = false, defaultValue = "") String vizSecondaryLanguage,
			@RequestParam(value = "vizTertiaryIPAddress", required = false, defaultValue = "") String vizTertiaryIPAddress,
			@RequestParam(value = "vizTertiaryPortNumber", required = false, defaultValue = "0") Integer vizTertiaryPortNumber,
			@RequestParam(value = "vizTertiaryScene", required = false, defaultValue = "") String vizTertiaryScene,
			@RequestParam(value = "vizTertiaryLanguage", required = false, defaultValue = "") String vizTertiaryLanguage,
			@RequestParam(value = "previewOnOrOff", required = false, defaultValue = "") String previewOnOrOff,
			@RequestParam(value = "selectInfobar", required = false, defaultValue = "") String selectInfobar,
			@RequestParam(value = "Category", required = false, defaultValue = "") String Category)
				throws StreamWriteException, DatabindException, IllegalAccessException, InvocationTargetException, 
				JAXBException, IOException, URISyntaxException, ParseException, InterruptedException, CloneNotSupportedException 
	{
		if(current_date == null || current_date.isEmpty()) {
			
			model.addAttribute("error_message","You must be connected to the internet online");
			return "error";
		
		} else if(new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date).before(new SimpleDateFormat("yyyy-MM-dd").parse(current_date))) {
			
			model.addAttribute("error_message","This software has expired");
			return "error";
			
		}else {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			LocalDate date1 = LocalDate.parse(current_date, dtf);
			LocalDate date2 = LocalDate.parse(expiry_date, dtf);
			
			long daysBetween = ChronoUnit.DAYS.between(date1, date2);
			
			expiryDate = String.valueOf(daysBetween);
			if (select_type == null || select_type.trim().isEmpty() || 
					select_type.equals(",")) {
				last_match_time_stamp = new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY 
						+ selectedMatch).lastModified();
		    } else {
		    	last_match_time_stamp = new File(CricketUtil.CRICKET_ARCHIVE_DIRECTORY + CricketUtil.ARCHIVE_MATCHES_DIRECTORY + select_type.split(",", -1)[0] + "/" +
		                CricketUtil.MATCHES_DIRECTORY + selectedMatch).lastModified();
		    }

			session_configuration = new Configuration(selectedMatch, select_broadcaster, select_second_broadcaster,
				vizIPAddress, vizPortNumber, vizLanguage, qtIPAddress, qtPortNumber, primaryVariousOptions, vizSecondaryIPAddress,
				vizSecondaryPortNumber, vizSecondaryLanguage, previewOnOrOff,selectInfobar, generateInteractiveFile, Category, select_type.split(",", -1)[0]);
			session_configuration.setCategory(Category);
			
			JAXBContext.newInstance(Configuration.class).createMarshaller().marshal(session_configuration, 
					new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + configuration_file_name));
				
			print_writers = CricketFunctions.processPrintWriter(session_configuration);

			GetVariousDBData("NEW", session_configuration, headToHead);
			
			this_scene = new Scene();
			this_animation = new Animation(new Infobar());
			
			session_match = new MatchAllData();
			
			if (select_type == null || select_type.trim().isEmpty() || 
					select_type.equals(",")) {
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + selectedMatch).exists()) {
					session_match.setSetup(objectMapper.readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + 
						selectedMatch), Setup.class));
				}
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + selectedMatch).exists()) {
					session_match.setMatch(objectMapper.readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + 
						selectedMatch), Match.class));
				}
				
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + selectedMatch).exists()) {
					session_match.setEventFile(objectMapper.readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + 
						selectedMatch), EventFile.class));
				}
		    } else {
		    	if(new File(CricketUtil.CRICKET_ARCHIVE_DIRECTORY + CricketUtil.ARCHIVE_MATCHES_DIRECTORY + select_type.split(",", -1)[0] + "/" + CricketUtil.SETUP_DIRECTORY + selectedMatch).exists()) {
					session_match.setSetup(objectMapper.readValue(new File(CricketUtil.CRICKET_ARCHIVE_DIRECTORY + CricketUtil.ARCHIVE_MATCHES_DIRECTORY + select_type.split(",", -1)[0] + "/" + CricketUtil.SETUP_DIRECTORY + 
						selectedMatch), Setup.class));
				}
				if(new File(CricketUtil.CRICKET_ARCHIVE_DIRECTORY + CricketUtil.ARCHIVE_MATCHES_DIRECTORY + select_type.split(",", -1)[0] + "/" + CricketUtil.MATCHES_DIRECTORY + selectedMatch).exists()) {
					session_match.setMatch(objectMapper.readValue(new File(CricketUtil.CRICKET_ARCHIVE_DIRECTORY + CricketUtil.ARCHIVE_MATCHES_DIRECTORY + select_type.split(",", -1)[0] + "/" + CricketUtil.MATCHES_DIRECTORY + 
						selectedMatch), Match.class));
				}
				
				if(new File(CricketUtil.CRICKET_ARCHIVE_DIRECTORY + CricketUtil.ARCHIVE_MATCHES_DIRECTORY + select_type.split(",", -1)[0] + "/" + CricketUtil.EVENT_DIRECTORY + selectedMatch).exists()) {
					session_match.setEventFile(objectMapper.readValue(new File(CricketUtil.CRICKET_ARCHIVE_DIRECTORY + CricketUtil.ARCHIVE_MATCHES_DIRECTORY + select_type.split(",", -1)[0] + "/" + CricketUtil.EVENT_DIRECTORY + 
						selectedMatch), EventFile.class));
				}
		    }

			session_match.getMatch().setMatchFileName(selectedMatch);
			MatchStats = CricketFunctions.getAllEvents(session_match,session_configuration.getBroadcaster(), session_match.getEventFile().getEvents());
			session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ, 
					CricketUtil.SETUP + "," + CricketUtil.MATCH + "," + CricketUtil.EVENT, session_match, session_configuration), session_players, session_team, session_ground);			
			session_match.getSetup().setMatchFileTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
			
			session_match.getSetup().setGenerateInteractiveFile(session_configuration.getGenerateInteractiveFile());
			CricketFunctions.getInteractive(session_match, "FULL_WRITE");
			
			if(headToHead.getH2hPlayer() == null) {
				headToHead.setH2hPlayer(new ArrayList<HeadToHeadPlayer>());
				headToHead.setH2hTeam(new ArrayList<HeadToHeadTeam>());
			}
			System.out.println("BEFORE headToHead.getH2hPlayer().size() = " + headToHead.getH2hPlayer().size());
			if(headToHead.getH2hPlayer().size() <= 0) {
				HeadToHead extractedH2H = CricketFunctions.extractHeadToHead(session_match, cricketService);
				headToHead.setH2hPlayer(extractedH2H.getH2hPlayer());
				headToHead.setH2hTeam(extractedH2H.getH2hTeam());
			}
//			this_seriesPowerplay = CricketFunctions.PowerPlayTeamThisSeries(session_match, cricket_matches);
			past_tournament_stats = CricketFunctions.extractTournamentData("PAST_MATCHES_DATA", false, headToHead.getH2hPlayer(), cricketService, session_match, null);
			session_match.getMatch().setMatchStats(MatchStats);
			
			switch (select_broadcaster) {
			case Constants.ISPL:
				if(session_configuration.getPrimaryVariousOptions().contains(Constants.FULL_FRAMER)) {
					this_scene.LoadScene("FULL-FRAMERS", print_writers, session_configuration);
				}
				
				if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
					this_scene.LoadScene("OVERLAYS", print_writers, session_configuration);
				}else{
					this_scene.LoadScene("TRADITIONAL_OVERLAYS", print_writers, session_configuration);
				}
				
				this_animation.ResetAnimation("CLEAR-ALL", print_writers, session_configuration);
				break;
			case Constants.T20_MUMBAI:
				if(session_configuration.getPrimaryVariousOptions().contains(Constants.FULL_FRAMER)) {
					this_scene.LoadScene("FULL-FRAMERS", print_writers, session_configuration);
				}
				if(session_match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
					this_scene.LoadScene("OVERLAYS_SUPER_OVER", print_writers, session_configuration);
				}else {
					this_scene.LoadScene("OVERLAYS", print_writers, session_configuration);
					this_animation.ResetAnimation("CLEAR-ALL", print_writers, session_configuration);
				}
				break;
			case Constants.ICC_U19_2023: case Constants.BENGAL_T20: case Constants.NPL: case Constants.LEGENDS:
			case Constants.MPL:	case Constants.APL:
				if(session_configuration.getPrimaryVariousOptions().contains(Constants.FULL_FRAMER)) {
					this_scene.LoadScene("FULL-FRAMERS", print_writers, session_configuration);
				}
				this_scene.LoadScene("OVERLAYS", print_writers, session_configuration);
				this_animation.ResetAnimation("CLEAR-ALL", print_writers, session_configuration);
				break;
			}
			
//			if(select_broadcaster.equalsIgnoreCase(Constants.MPL)) {
//				this_caption.this_fullFramesGfx.setFullFrameBase(session_configuration);
//			}else if(select_broadcaster.equalsIgnoreCase(Constants.BENGAL_T20)){
//				this_caption.this_fullFramesGfx.PopulateFfFooter(0, "", session_match, 0);
//			}
			
			if(session_match.getMatch().getInning() != null) {
				model.addAttribute("which_inning", session_match.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning()
						.equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null).getInningNumber());
			} else {
				model.addAttribute("which_inning", "1");
			}
			
			model.addAttribute("session_match", session_match);
			model.addAttribute("expiryDate", expiryDate);
			model.addAttribute("session_configuration", session_configuration);
			model.addAttribute("select_second_broadcaster", select_second_broadcaster);
			model.addAttribute("select_broadcaster", select_broadcaster);
			model.addAttribute("select_type", select_type);
			return "output";
		}
	}

	@RequestMapping(value = {"/processCricketProcedures.html"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processCricketProcedures(
		@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
		@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess) 
			throws Exception 
	{
		switch (whatToProcess.toUpperCase()) {
		case "HEAD_TO_HEAD_FILE":
			CricketFunctions.exportMatchData(session_match);
			
			return objectMapper.writeValueAsString(session_match);
		case "GET-CONFIG-DATA":

			session_configuration = (Configuration)JAXBContext.newInstance(Configuration.class).createUnmarshaller().unmarshal(
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + valueToProcess));
			
			return objectMapper.writeValueAsString(session_configuration);
			
		case "DB_DATA_READ":
			GetVariousDBData("ONLY_DB", session_configuration, headToHead);

			return objectMapper.writeValueAsString(session_match);
			
		case "RE_READ_DATA":
			
			HeadToHead extractedH2H = CricketFunctions.extractHeadToHead(session_match, cricketService);
			headToHead.setH2hPlayer(extractedH2H.getH2hPlayer());
			headToHead.setH2hTeam(extractedH2H.getH2hTeam());
			//this_seriesPowerplay = CricketFunctions.PowerPlayTeamThisSeries(session_match, cricket_matches);
			GetVariousDBData("UPDATE", session_configuration, headToHead);
			return objectMapper.writeValueAsString(session_match);
		
		case "TURN_ON_OR_OFF_SPEED":
			System.out.println("valueToProcess - " + valueToProcess);
			if(valueToProcess.equalsIgnoreCase("TRUE")) {
				show_speed = true;
			}else {
				show_speed = false;
			}
			return String.valueOf(show_speed);
			
		case "TURN_ON_OR_OFF_AUDIO":
			
			if(valueToProcess.equalsIgnoreCase("TRUE")) {
				this_animation.audioenabled = "TRUE";
			}else {
				this_animation.audioenabled = "FALSE";
			}
			return null;	
			
		case "READ-MATCH-AND-POPULATE":
			
			if(session_match == null) {
				return objectMapper.writeValueAsString(null);
			}
			
			if(last_match_time_stamp != new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY 
				+ session_match.getMatch().getMatchFileName()).lastModified()) {
				session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ,
					CricketUtil.MATCH + "," + CricketUtil.EVENT, session_match, session_configuration), session_players, session_team, session_ground);
				session_match.getSetup().setGenerateInteractiveFile(session_configuration.getGenerateInteractiveFile());

				last_match_time_stamp = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY 
						+ session_match.getMatch().getMatchFileName()).lastModified();
				MatchStats = CricketFunctions.getAllEvents(session_match,session_configuration.getBroadcaster(), session_match.getEventFile().getEvents());
				CricketFunctions.getInteractive(session_match, "FULL_WRITE");

				session_match.getMatch().setMatchStats(MatchStats);
				
				if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
					this_caption.this_lofInfobarGfx.updateInfobar(print_writers, session_match);
				}else {
					this_caption.this_infobarGfx.updateInfobar(print_writers, session_match);
				}
			}
			if(session_configuration.getBroadcaster() != null) {
				switch (session_configuration.getBroadcaster()) {
				case Constants.T20_MUMBAI: case Constants.NPL: case Constants.APL:
					if(show_speed == true) {
						if (speedFile.exists()) {
							long currentTimestamp = speedFile.lastModified();
						    // Check if speed_match_time_stamp is initialized
						    if (speed_match_time_stamp == 0) {
						        speed_match_time_stamp = currentTimestamp; // Set the initial value if uninitialized
						    }

						    // Use a tolerance for comparison
						    if (Math.abs(speed_match_time_stamp - currentTimestamp) > 100) {
						        this_caption.this_infobarGfx.speed(CricketFunctions.processPrintWriter(session_configuration).get(0), session_match, session_configuration);
						        speed_match_time_stamp = currentTimestamp; // Update to the new timestamp
						    }
						} else {
						    //System.out.println("File does not exist.");
						}
					}else if(show_speed == false) {
						if (speedFile.exists()) {
							long currentTimestamp = speedFile.lastModified();
						    if (speed_match_time_stamp == 0) {
						        speed_match_time_stamp = currentTimestamp; // Set the initial value if uninitialized
						    }

						    if (Math.abs(speed_match_time_stamp - currentTimestamp) > 100) {
						        speed_match_time_stamp = currentTimestamp; // Update to the new timestamp
						    } else {
						        //System.out.println("No modification detected.");
						    }
						}
					}
					break;
				case Constants.ISPL:
					if(show_speed == true) {
						if (speedFile.exists()) {
							long currentTimestamp = speedFile.lastModified();
						    // Check if speed_match_time_stamp is initialized
						    if (speed_match_time_stamp == 0) {
						        speed_match_time_stamp = currentTimestamp; // Set the initial value if uninitialized
						    }

						    // Use a tolerance for comparison
						    if (Math.abs(speed_match_time_stamp - currentTimestamp) > 100) {
//						    	if(this_animation.infobar.isInfobar_on_screen()) {
//						    		switch(session_match.getEventFile().getEvents().get(session_match.getEventFile().getEvents().size() - 1).getEventType()) {
//						    		case CricketUtil.CHANGE_BOWLER: case CricketUtil.END_OVER: case CricketUtil.NEW_BATSMAN: case CricketUtil.LOG_50_50:
//						    		case CricketUtil.LOG_OVERWRITE_BATSMAN_HOWOUT: case "LOG_PP_DATA":
//						    			break;
//						    		default:
//						    			this_caption.this_lofInfobarGfx.speed(CricketFunctions.processPrintWriter(session_configuration).get(0),session_match);
//						    			break;
//						    		}
//						    	}
						    	this_caption.this_lofInfobarGfx.speed(CricketFunctions.processPrintWriter(session_configuration).get(0),session_match);
						        speed_match_time_stamp = currentTimestamp; // Update to the new timestamp
						    }
						} else {
						    //System.out.println("File does not exist.");
						}
					}else if(show_speed == false) {
						if (speedFile.exists()) {
							long currentTimestamp = speedFile.lastModified();
						    if (speed_match_time_stamp == 0) {
						        speed_match_time_stamp = currentTimestamp; // Set the initial value if uninitialized
						    }

						    if (Math.abs(speed_match_time_stamp - currentTimestamp) > 100) {
						        speed_match_time_stamp = currentTimestamp; // Update to the new timestamp
						    } else {
						        //System.out.println("No modification detected.");
						    }
						}
					}
					break;
				}
			}			

//			if(new File("C:\\Sports\\Cricket\\Fielder\\Fielder_Text\\" + 
//		            "FieldPlotter.txt").exists()) {
//				
//				String data = new String(Files.readAllBytes(Paths.get("C:\\Sports\\Cricket\\Fielder\\Fielder_Text\\" + 
//			            "FieldPlotter.txt")));
//		        // Split the content by lines and print each line separately
//		        String[] lines = data.split("\n");
//		        
//		        plotterData = lines[0].trim();
//		        
//		        if(lines.length > 0) {
//					if(lines[1].trim().equalsIgnoreCase("true")) {
//						fielderFormation = CricketFunctions.getFielderFormation(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim());
//						if(fielderFormation.isCheckbox() == true) {
//							if(lines[0].trim().equalsIgnoreCase("FielderFormation.JSON")) {
//								if(plotter_match_time_stamp != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
//									plotter_match_time_stamp = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
//									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
//									Plotter_file_change = true;
//								}
//							}else if(lines[0].trim().equalsIgnoreCase("FielderFormation_1.JSON")) {
//								if(plotter_match_time_stamp1 != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
//									plotter_match_time_stamp1 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
//									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
//									Plotter_file_change = true;
//								}
//							}else if(lines[0].trim().equalsIgnoreCase("FielderFormation_2.JSON")) {
//								if(plotter_match_time_stamp2 != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
//									plotter_match_time_stamp2 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
//									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
//									Plotter_file_change = true;
//								}
//							}else if(lines[0].trim().equalsIgnoreCase("FielderFormation_3.JSON")) {
//								if(plotter_match_time_stamp3 != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
//									plotter_match_time_stamp3 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
//									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
//									Plotter_file_change = true;
//								}
//							}else if(lines[0].trim().equalsIgnoreCase("FielderFormation_4.JSON")) {
//								if(plotter_match_time_stamp4 != new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified()) {
//									plotter_match_time_stamp4 = new File(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + lines[0].trim()).lastModified();
//									//System.out.println("AfterCheckBox = " + fielderFormation.isCheckbox());
//									Plotter_file_change = true;
//								}
//							}
//						}
//					}else if(lines[1].trim().equalsIgnoreCase("false")) {
//						
//					}
//				}
//			}
			
//			if(Plotter_file_change == true) {
//				if(this_caption.this_infobarGfx!=null) {
//					this_caption.this_infobarGfx.updateFieldPlotter(print_writers, session_match);
//				}
//				Plotter_file_change = false;
//			}
			return objectMapper.writeValueAsString(session_match);
		default:
			if(session_configuration.getBroadcaster().equalsIgnoreCase(Constants.NPL) || 
					session_configuration.getBroadcaster().equalsIgnoreCase(Constants.MPL) ||
					session_configuration.getBroadcaster().equalsIgnoreCase(Constants.APL)) {
				if(whatToProcess.split(",")[0].toUpperCase().equalsIgnoreCase("highlightProfile") || 
						whatToProcess.split(",")[0].toUpperCase().equalsIgnoreCase("highlightLeader")) {
					
					this_animation.ChangeOn(whatToProcess, print_writers, session_configuration);
				}
			}
			if(whatToProcess.toUpperCase().equalsIgnoreCase("IMPACT-CHANGE-ON")) {
				switch (session_configuration.getBroadcaster()) {
				case Constants.ISPL:
					this_animation.Lof_ISPL_ChangeOn(this_caption.this_lowerThirdGfx.impactPlayerData, print_writers, session_configuration);
					this_caption.this_lowerThirdGfx.chnageOn = true;
					TimeUnit.MILLISECONDS.sleep(3000);
					this_caption.whichSide = 1;
					this_caption.PopulateGraphics(this_caption.this_lowerThirdGfx.impactPlayerData, session_match);
					this_animation.Lof_ISPL_CutBack(this_caption.this_lowerThirdGfx.impactPlayerData, print_writers, session_configuration);
					this_caption.this_lowerThirdGfx.chnageOn = false;
					break;
				case Constants.NPL: case Constants.APL:
					this_animation.ChangeOn(this_caption.this_lowerThirdGfx.impactPlayerData, print_writers, session_configuration);
					this_caption.this_lowerThirdGfx.chnageOn = true;
					TimeUnit.MILLISECONDS.sleep(3000);
					this_caption.whichSide = 1;
					this_caption.PopulateGraphics(this_caption.this_lowerThirdGfx.impactPlayerData, session_match);
					this_animation.CutBack(this_caption.this_lowerThirdGfx.impactPlayerData, print_writers, session_configuration);
					this_caption.this_lowerThirdGfx.chnageOn = false;
					break;
				default:
					this_animation.AnimateIn("Shift_I", print_writers, session_configuration);
					break;
				}
			}
			if(whatToProcess.toUpperCase().equalsIgnoreCase("PLAYING-XI-CHANGE-ON")) {
				this_animation.AnimateIn("Shift_T", print_writers, session_configuration);
			}
			if(whatToProcess.contains("GRAPHICS-OPTIONS")||whatToProcess.contains("GRAPHICS-OPTIONS_DATA")) {
				return objectMapper.writeValueAsString(GetGraphicOption(valueToProcess,session_configuration, headToHead));
			}else if(whatToProcess.contains("POPULATE-GRAPHICS")) {
				switch(this_animation.getTypeOfGraphicsOnScreen(session_configuration,valueToProcess)){
				case Constants.INFO_BAR:
					if(valueToProcess.split(",")[0].equalsIgnoreCase("Control_F12") || valueToProcess.split(",")[0].equalsIgnoreCase("Shift_F12")) {
						if(this_animation.infobar.isInfobar_on_screen()) {
							this_caption.whichSide = 2;
						} else {
							this_caption.whichSide = 1;
						}
						
						this_caption.PopulateGraphics(valueToProcess, session_match);
						this_animation.caption = this_caption;
						this_animation.processInfoBarPreview(valueToProcess, print_writers, this_caption.whichSide, session_configuration, 
								this_animation.whichGraphicOnScreen);
//						if(this_caption.status.equalsIgnoreCase(Constants.OK)) {
//							processAnimations("ANIMATE-IN-GRAPHICS", session_configuration, valueToProcess, print_writers);
//							return JSONObject.fromObject(this_caption).toString();
//						} else {
//							return JSONObject.fromObject(this_caption).toString();
//						}
					}else {
						if(this_animation.infobar.isInfobar_on_screen()) {
							if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
								if(this_caption.this_lofInfobarGfx.infobar.getLast_middle_section() != null && 
										!this_caption.this_lofInfobarGfx.infobar.getLast_middle_section().isEmpty()) {
									if(this_caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase(CricketUtil.BATSMAN) ||
											this_caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("IDENT")) {
										this_caption.whichSide = 1;
									}else {
										this_caption.whichSide = 2;
									}
								}else {
									this_caption.whichSide = 1;
								}
							}else {
								this_caption.whichSide = 2;
							}
						} else {
							this_caption.whichSide = 1;
						}
						
						this_caption.PopulateGraphics(valueToProcess, session_match);
						this_animation.caption = this_caption;
						
						switch (session_configuration.getBroadcaster()) {
						case Constants.ISPL:
							if(!valueToProcess.split(",")[0].equalsIgnoreCase("F12")) {
								this_caption.this_lofInfobarGfx.setPositionOfScoreBug(this_caption.this_lofInfobarGfx.infobar.getMiddle_section(), 
										2, session_configuration, 0);
								this_animation.processInfoBarPreview(valueToProcess, print_writers, this_caption.whichSide, 
										session_configuration, this_animation.whichGraphicOnScreen);
							}
							break;
						case Constants.T20_MUMBAI:
							this_animation.processInfoBarPreview(valueToProcess, print_writers, this_caption.whichSide, 
									session_configuration, this_animation.whichGraphicOnScreen);
							break;
						default:
							if(this_caption.status.equalsIgnoreCase(Constants.OK)) {
								processAnimations("ANIMATE-IN-GRAPHICS", session_configuration, valueToProcess, print_writers, headToHead);
								this_caption.status = CricketUtil.YES;
								return objectMapper.writeValueAsString(this_caption);
							} else {
								return objectMapper.writeValueAsString(this_caption);
							}
						}
					}
					break;
				default:
					switch (session_configuration.getBroadcaster()) {
					case Constants.ICC_U19_2023: case Constants.ISPL: case Constants.BENGAL_T20: case Constants.NPL: case Constants.LEGENDS:
					case Constants.MPL:	case Constants.APL:
						if(!session_configuration.getPrimaryVariousOptions().contains(Constants.FULL_FRAMER)
							&& this_animation.getTypeOfGraphicsOnScreen(session_configuration, valueToProcess).contains(Constants.FULL_FRAMER)) {
							this_caption.setStatus("Error: Full framers captions NOT selected on start-up");
							return objectMapper.writeValueAsString(this_caption);
						}
						break;
					}
					if(this_animation.whichGraphicOnScreen.isEmpty()) {
						if(!this_animation.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
							if(this_animation.infobar.isInfobar_on_screen() == false) {
								this_animation.ResetAnimation("CLEAR-ALL", print_writers, session_configuration);
							}else {
								this_animation.ResetAnimation("", print_writers, session_configuration);
							}
						}
						this_caption.whichSide = 1;
					} else {
						//Don't allow L3rds change-on while FFs are on screen
						switch (this_animation.getTypeOfGraphicsOnScreen(session_configuration, this_animation.whichGraphicOnScreen)) {
						case Constants.FULL_FRAMER: case Constants.LOWER_THIRD: 
						case Constants.NAME_SUPERS + Constants.LOWER_THIRD:
						case Constants.BOUNDARIES + Constants.LOWER_THIRD:
						case Constants.BUGS:	
							
							if(this_animation.getTypeOfGraphicsOnScreen(session_configuration,valueToProcess) 
								!= this_animation.getTypeOfGraphicsOnScreen(session_configuration,this_animation.whichGraphicOnScreen)) {

								//Make a preview of lowerThird when FullFrames is on Screen and vice-verca
								switch (this_animation.getTypeOfGraphicsOnScreen(session_configuration, this_animation.whichGraphicOnScreen)) {
								case Constants.FULL_FRAMER: 
									switch (this_animation.getTypeOfGraphicsOnScreen(session_configuration, valueToProcess)) {
									case Constants.LOWER_THIRD: case Constants.NAME_SUPERS + Constants.LOWER_THIRD: 
									case Constants.BOUNDARIES + Constants.LOWER_THIRD:
										this_caption.whichSide = 1;
										this_caption.PopulateGraphics(valueToProcess, session_match);
										this_animation.processL3Preview(valueToProcess, print_writers, this_caption.whichSide, session_configuration,session_match);
										break;
									case Constants.BUGS: 
										this_caption.whichSide = 1;
										this_caption.PopulateGraphics(valueToProcess, session_match);
										this_animation.processBugsPreview(valueToProcess, print_writers, this_caption.whichSide, session_configuration,this_animation.whichGraphicOnScreen);
										break;	
									}
									break;
								case Constants.BUGS: 
									switch (this_animation.getTypeOfGraphicsOnScreen(session_configuration, valueToProcess)) {
									case Constants.FULL_FRAMER:
										this_caption.whichSide = 1;
										this_caption.PopulateGraphics(valueToProcess, session_match);
										if(session_configuration.getBroadcaster().equalsIgnoreCase(Constants.ISPL)) {
											this_animation.Lof_ISPL_FullFramesPreview(valueToProcess, print_writers, this_caption.whichSide, 
													session_configuration, this_animation.whichGraphicOnScreen);
										}else {
											this_animation.processFullFramesPreview(valueToProcess, print_writers, this_caption.whichSide, 
													session_configuration, this_animation.whichGraphicOnScreen);	
										}
										break;
									case Constants.LOWER_THIRD: case Constants.NAME_SUPERS + Constants.LOWER_THIRD: 
									case Constants.BOUNDARIES + Constants.LOWER_THIRD:
										this_caption.whichSide = 1;
										this_caption.PopulateGraphics(valueToProcess, session_match);
										this_animation.processL3Preview(valueToProcess, print_writers, this_caption.whichSide, session_configuration,session_match);
										break;
									}
									break;	
								case Constants.LOWER_THIRD: case Constants.NAME_SUPERS + Constants.LOWER_THIRD: 
								case Constants.BOUNDARIES + Constants.LOWER_THIRD:
									switch (this_animation.getTypeOfGraphicsOnScreen(session_configuration, valueToProcess)) {
									case Constants.FULL_FRAMER:
										this_caption.whichSide = 1;
										this_caption.PopulateGraphics(valueToProcess, session_match);
										if(session_configuration.getBroadcaster().equalsIgnoreCase(Constants.ISPL)) {
											this_animation.Lof_ISPL_FullFramesPreview(valueToProcess, print_writers, this_caption.whichSide, 
													session_configuration, this_animation.whichGraphicOnScreen);
										}else {
											this_animation.processFullFramesPreview(valueToProcess, print_writers, this_caption.whichSide, 
													session_configuration, this_animation.whichGraphicOnScreen);	
										}
										break;
									case Constants.BUGS: 
										this_caption.whichSide = 1;
										this_caption.PopulateGraphics(valueToProcess, session_match);
										this_animation.processBugsPreview(valueToProcess, print_writers, this_caption.whichSide, session_configuration,this_animation.whichGraphicOnScreen);
										break;	
									}
									break;
								}
								
								this_caption.setStatus(this_animation.getTypeOfGraphicsOnScreen(session_configuration,
									this_animation.whichGraphicOnScreen).replace("_", " ") + " is on screen. "
									+ this_animation.getTypeOfGraphicsOnScreen(session_configuration,valueToProcess).replace(
									"_", " ") + " not allowed" );
								
								return objectMapper.writeValueAsString(this_caption);

							}
							break;
						}
						this_caption.whichSide = 2;
					}
					
					this_caption.PopulateGraphics(valueToProcess, session_match);
					this_animation.caption = this_caption;
					//Previews
					switch (this_animation.getTypeOfGraphicsOnScreen(session_configuration, valueToProcess)) {
					case Constants.FULL_FRAMER:
						if(session_configuration.getBroadcaster().equalsIgnoreCase(Constants.ISPL)) {
							this_animation.Lof_ISPL_FullFramesPreview(valueToProcess, print_writers, this_caption.whichSide, 
									session_configuration, this_animation.whichGraphicOnScreen);
						}else {
							this_animation.processFullFramesPreview(valueToProcess, print_writers, this_caption.whichSide, 
									session_configuration, this_animation.whichGraphicOnScreen);	
						}
						break;
					case Constants.LOWER_THIRD: 
					case Constants.NAME_SUPERS + Constants.LOWER_THIRD:
					case Constants.BOUNDARIES + Constants.LOWER_THIRD:
						this_animation.processL3Preview(valueToProcess, print_writers, this_caption.whichSide, session_configuration,session_match);
						break;
					case Constants.BUGS:
						this_animation.processBugsPreview(valueToProcess, print_writers, this_caption.whichSide, 
							session_configuration, this_animation.whichGraphicOnScreen);
						break;
					case Constants.MINIS:
						this_animation.processMiniPreview(valueToProcess, print_writers, this_caption.whichSide, 
							session_configuration, this_animation.whichGraphicOnScreen);
						break;
					}
					break;
				}
				System.out.println(this_caption.getStatus());
				return objectMapper.writeValueAsString(this_caption.status);
			}
			else if(whatToProcess.contains("ANIMATE-IN-GRAPHICS") || whatToProcess.contains("ANIMATE-OUT-GRAPHICS")
				|| whatToProcess.contains("ANIMATE-OUT-INFOBAR") || whatToProcess.contains("QUIDICH-COMMANDS") || 
				whatToProcess.contains("ANIMATE-OUT-TAPE") || whatToProcess.contains("ANIMATE-OUT-IDENT") || 
				whatToProcess.contains("ANIMATE-OUT-TARGET") || whatToProcess.contains("ANIMATE-OUT-BOTTOM")) {

				if(whatToProcess.contains("ANIMATE-OUT-GRAPHICS")) {
					switch (valueToProcess.split(",")[0]) {
					case "Alt_p":
						if(!this_animation.whichGraphicOnScreen.isEmpty()) {
							this_animation.status = "Cannot animate out bugs while " + 
								this_animation.whichGraphicOnScreen + " is on screen";
							return objectMapper.writeValueAsString(this_animation);
						}
						break;
					}
				}
				
				processAnimations(whatToProcess, session_configuration, valueToProcess, print_writers, headToHead);
			}else if(whatToProcess.contains("ANIMATE-OUT-SECOND_PLAYING")) {
				switch (session_configuration.getBroadcaster()) {
				case Constants.BENGAL_T20:
					if(this_animation.whichGraphicOnScreen.contains("Control_Shift_F7")) {
						this_animation.lineUpCount++;
						if(this_animation.lineUpCount == 3) {
							this_animation.lineUpCount = 0;
							
							this_animation.whichGraphicOnScreen = "";
						}
						this_animation.processAnimation(Constants.BACK, print_writers, "Anim_Lineup_Image_Big", "CONTINUE");
					}
					break;
				case Constants.ISPL:
					if(this_animation.whichGraphicOnScreen.contains("Shift_F8")) {
						this_animation.lineUpCount++;
						
						this_animation.processAnimation(Constants.BACK, print_writers, "anim_BigImageLineUp", "CONTINUE");
						
						if(this_animation.lineUpCount == 2) {
							TimeUnit.MILLISECONDS.sleep(500);
							if(!this_caption.this_fullFramesGfx.PlayerId.isEmpty()) {
								this_caption.this_fullFramesGfx.populateTeamLineUpFooter(print_writers, 2, whatToProcess.split(",")[0], 
										session_configuration, "SHOW-TOSS", session_match);
									this_animation.processAnimation(Constants.BACK, print_writers, "Change_BigImageLineUp$Footer", "START");
									TimeUnit.MILLISECONDS.sleep(1000);
									this_caption.this_fullFramesGfx.populateTeamLineUpFooter(print_writers, 1, whatToProcess.split(",")[0], 
											session_configuration, "SHOW-TOSS", session_match);
									this_animation.processAnimation(Constants.BACK, print_writers, "Change_BigImageLineUp$Footer", "SHOW 0.0");
							}
						}
						if(this_animation.lineUpCount == 3) {
							this_animation.lineUpCount = 0;
							this_animation.whichGraphicOnScreen = "";
						}
					}
					break;	
				case Constants.LEGENDS:
					if(this_animation.LineUpBigImage_On_Screen) {
						this_animation.lineUpCount++;
						this_animation.processAnimation(Constants.BACK, print_writers, "anim_Team_BigImage", "CONTINUE");
						if(this_animation.lineUpCount == 3) {
							this_animation.LineUpBigImage_On_Screen = false;
							this_animation.lineUpCount = 0;
						}
						this_animation.whichGraphicOnScreen = "";
					}	
					break;
				}
			} else if(whatToProcess.contains("GRAPHICS_PREVIEW-OPTIONS")) {
				switch (session_configuration.getBroadcaster()) {
				case Constants.ISPL:
					switch(session_configuration.getWhichInfobar()) {
					case "LOF_INFOBAR":
						return objectMapper.writeValueAsString(this_caption.this_lofInfobarGfx.GetPreviewData(valueToProcess,session_configuration,session_match));
					}
					break;
				default:
					return objectMapper.writeValueAsString(this_caption.this_infobarGfx.GetPreviewData(valueToProcess,session_configuration,session_match));
				}
			}else if(whatToProcess.contains("ANIMATE-OUT-ALL_INFOBAR_PART")) {
				infobarAnimateOutAllSection(session_configuration, session_match, print_writers, headToHead);
			}
			else if(whatToProcess.contains("CLEAR-ALL") || whatToProcess.contains("CLEAR-ALL-WITH-INFOBAR")) {
				this_animation.ResetAnimation(whatToProcess, print_writers, session_configuration);
			}else if(whatToProcess.contains("CANCLE-GRAPHICS")) {
				this_caption.whichSide = 1;
				switch(session_configuration.getWhichInfobar()) {
				case "LOF_INFOBAR":
					if(this_caption.this_lofInfobarGfx.infobar.getLast_middle_section() != null && !this_caption.this_lofInfobarGfx.infobar.getLast_middle_section().isEmpty()) {
						this_caption.this_lofInfobarGfx.infobar.setMiddle_section(this_caption.this_lofInfobarGfx.infobar.getLast_middle_section());
					}
					break;
				default:
					switch (session_configuration.getBroadcaster()) {
					case Constants.T20_MUMBAI:
						if(this_caption.this_infobarGfx.infobar.getLast_middle_section() != null && !this_caption.this_infobarGfx.infobar.getLast_middle_section().isEmpty()) {
							this_caption.this_infobarGfx.infobar.setMiddle_section(this_caption.this_infobarGfx.infobar.getLast_middle_section());
						}
						if(this_caption.this_infobarGfx.infobar.getLast_right_bottom() != null && !this_caption.this_infobarGfx.infobar.getLast_right_bottom().isEmpty()) {
							this_caption.this_infobarGfx.infobar.setRight_bottom(this_caption.this_infobarGfx.infobar.getLast_right_bottom());
						}
						if(this_caption.this_infobarGfx.infobar.getLast_right_section() != null && !this_caption.this_infobarGfx.infobar.getLast_right_section().isEmpty()) {
							this_caption.this_infobarGfx.infobar.setRight_section(this_caption.this_infobarGfx.infobar.getLast_right_section());
						}
						if(this_caption.this_infobarGfx.infobar.getLast_full_section() != null && !this_caption.this_infobarGfx.infobar.getLast_full_section().isEmpty()) {
							this_caption.this_infobarGfx.infobar.setFull_section(this_caption.this_infobarGfx.infobar.getLast_full_section());
						}
						if(this_caption.this_infobarGfx.infobar.getLast_left_bottom() != null && !this_caption.this_infobarGfx.infobar.getLast_left_bottom().isEmpty()) {
							this_caption.this_infobarGfx.infobar.setLeft_bottom(this_caption.this_infobarGfx.infobar.getLast_left_bottom());
						}
						break;
					}
					break;
				}
			}
			return objectMapper.writeValueAsString(this_animation);
		}
	}
	public void infobarAnimateOutAllSection(Configuration session_configuration, MatchAllData session_match, List<PrintWriter> print_writers, HeadToHead headToHead) 
			throws Exception {
		switch(session_configuration.getBroadcaster()) {
		case Constants.NPL: case Constants.LEGENDS: case Constants.APL:
			this_caption.whichSide = 2;
			int Inn_Number = session_match.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning()
					.equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null).getInningNumber();
			
			if(!this_caption.this_infobarGfx.infobar.getMiddle_section().equalsIgnoreCase("BATSMAN") && 
					!this_caption.this_infobarGfx.infobar.getMiddle_section().equalsIgnoreCase("PHOTO BATSMAN")) {
				
				if(this_caption.this_infobarGfx.infobar.getMiddle_section() != null && !this_caption.this_infobarGfx.infobar.getMiddle_section().isEmpty()) {
					this_caption.PopulateGraphics("Alt_2," + Inn_Number + (this_caption.this_infobarGfx.infobar.getOmo_value_bat() == 0
							?",BATSMAN":",PHOTO BATSMAN"), session_match);
					this_animation.caption = this_caption;
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimations("ANIMATE-IN-GRAPHICS", session_configuration, "Alt_2," + Inn_Number + 
							(this_caption.this_infobarGfx.infobar.getOmo_value_bat() == 0 ?",BATSMAN":",PHOTO BATSMAN"), print_writers, headToHead);
				}
			}
			
			if(!this_caption.this_infobarGfx.infobar.getRight_section().equalsIgnoreCase("BOWLER")) {
				if(this_caption.this_infobarGfx.infobar.getRight_section() != null && !this_caption.this_infobarGfx.infobar.getRight_section().isEmpty()) {
					this_caption.PopulateGraphics("Alt_8," + Inn_Number + ",BOWLER", session_match);
					this_animation.caption = this_caption;
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimations("ANIMATE-IN-GRAPHICS", session_configuration, "Alt_8," + Inn_Number + ",BOWLER", print_writers, headToHead);
				}
			}
			break;
		}
	}
	
	public void processAnimations(String whatToProcess, Configuration session_configuration, String valueToProcess, 
		List<PrintWriter> print_writers, HeadToHead headToHead) throws Exception
	{
		if(whatToProcess.contains("ANIMATE-IN-GRAPHICS")) {
			switch(this_animation.getTypeOfGraphicsOnScreen(session_configuration,valueToProcess)){
			case Constants.INFO_BAR:
				if(valueToProcess.split(",")[0].equalsIgnoreCase("Control_F12")) {
					if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
						this_animation.Lof_ISPL_AnimateIn(valueToProcess, print_writers, session_configuration);
					}else {
						this_animation.AnimateIn(valueToProcess, print_writers, session_configuration);
					}
				}else if(valueToProcess.split(",")[0].equalsIgnoreCase("Shift_F12")){
					if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
						this_animation.Lof_ISPL_ChangeOn(valueToProcess, print_writers, session_configuration);
						TimeUnit.MILLISECONDS.sleep(2000);
						this_caption.whichSide = 1;
						this_caption.PopulateGraphics(valueToProcess, session_match);
						this_animation.Lof_ISPL_CutBack(valueToProcess, print_writers, session_configuration);
					}else {
						this_animation.ChangeOn(valueToProcess, print_writers, session_configuration);
						TimeUnit.MILLISECONDS.sleep(2000);
						this_caption.whichSide = 1;
						this_caption.PopulateGraphics(valueToProcess, session_match);
						this_animation.CutBack(valueToProcess, print_writers, session_configuration);
					}
				}
				else if(valueToProcess.split(",")[0].equalsIgnoreCase("Alt_y")){
					if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
						this_animation.Lof_ISPL_AnimateIn(valueToProcess, print_writers, session_configuration);
					}else {
						this_animation.AnimateIn(valueToProcess, print_writers, session_configuration);
					}
				}else if(valueToProcess.split(",")[0].equalsIgnoreCase("Alt_e")){
					GetGraphicOption(valueToProcess,session_configuration, headToHead);
				}else {
					if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
						switch(valueToProcess.split(",")[0]) {
						case "Alt_1": case "Control_5": case "Alt_5": case "Alt_9": case "Alt_0": case "Control_4": case "6": case "Control_Alt_3":
							if(this_caption.whichSide == 1) {
								this_animation.Lof_ISPL_AnimateIn(valueToProcess, print_writers, session_configuration);
							}else {
								this_animation.Lof_ISPL_ChangeOn(valueToProcess, print_writers, session_configuration);
								TimeUnit.MILLISECONDS.sleep(2000);
								this_caption.whichSide = 1;
								this_caption.PopulateGraphics(valueToProcess, session_match);
								this_animation.Lof_ISPL_CutBack(valueToProcess, print_writers, session_configuration);
							}
							break;
						case "Alt_2": case "Alt_3": case "Alt_4": case "Control_6": case "Alt_c": case "Control_F8": case "Control_7": case "Control_8": 
						case "Alt_/": case "Control_9": case "Alt_F1": case "Alt_F2": case "Control_0": case "Shift_@": case "Shift_$": case "Control_Shift_@": 
						case "Control_Alt_5": case "Alt_Shift_@": case "Control_Alt_6": case "Control_Alt_9": case "Control_Alt_0":	case "Control_Alt_7":
						case "Control_Alt_8":
							
							if(this_caption.this_lofInfobarGfx.infobar.getLast_middle_section() != null && 
									!this_caption.this_lofInfobarGfx.infobar.getLast_middle_section().isEmpty()) {
								if(this_caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase(CricketUtil.BATSMAN)||
										this_caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("IDENT")) {
									this_animation.Lof_ISPL_AnimateIn(valueToProcess, print_writers, session_configuration);
								}else {
									this_animation.Lof_ISPL_ChangeOn(valueToProcess, print_writers, session_configuration);
									TimeUnit.MILLISECONDS.sleep(2000);
									this_caption.whichSide = 1;
									this_caption.PopulateGraphics(valueToProcess, session_match);
									this_animation.Lof_ISPL_CutBack(valueToProcess, print_writers, session_configuration);
								}
							}else {
								this_animation.Lof_ISPL_AnimateIn(valueToProcess, print_writers, session_configuration);
							}
							break;
						}
					}else {
						this_animation.ChangeOn(valueToProcess, print_writers, session_configuration);
						TimeUnit.MILLISECONDS.sleep(2000);
						this_caption.whichSide = 1;
						this_caption.PopulateGraphics(valueToProcess, session_match);
						this_animation.CutBack(valueToProcess, print_writers, session_configuration);
					}
					
				}
				break;
			default:
				if(this_animation.whichGraphicOnScreen.isEmpty()) {
					if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
						this_animation.Lof_ISPL_AnimateIn(valueToProcess, print_writers, session_configuration);
					}else {
						this_animation.AnimateIn(valueToProcess, print_writers, session_configuration);
					}
				} else { // Change on
					
					if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
						this_animation.Lof_ISPL_ChangeOn(valueToProcess, print_writers, session_configuration);
						TimeUnit.MILLISECONDS.sleep(2500);
						this_caption.whichSide = 1;
						this_caption.PopulateGraphics(valueToProcess, session_match);
						TimeUnit.MILLISECONDS.sleep(2000);
						this_animation.Lof_ISPL_CutBack(valueToProcess, print_writers, session_configuration);
					}else {
						this_animation.ChangeOn(valueToProcess, print_writers, session_configuration);
						TimeUnit.MILLISECONDS.sleep(2500);
						this_caption.whichSide = 1;
						this_caption.PopulateGraphics(valueToProcess, session_match);
						TimeUnit.MILLISECONDS.sleep(2000);
						this_animation.CutBack(valueToProcess, print_writers, session_configuration);
					}
				}
				break;
			}
		} else if(whatToProcess.contains("ANIMATE-OUT-GRAPHICS")) {
			switch (valueToProcess.split(",")[0]) {
			case "Alt_p":
				if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
					this_animation.Lof_ISPL_AnimateOut(valueToProcess, print_writers, session_configuration);
				}else {
					this_animation.AnimateOut(valueToProcess, print_writers, session_configuration);
				}
				
				break;
			default:
				if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
					this_animation.Lof_ISPL_AnimateOut(this_animation.whichGraphicOnScreen, print_writers, session_configuration);
				}else {
					this_animation.AnimateOut(this_animation.whichGraphicOnScreen, print_writers, session_configuration);			
				}
				break;
			}
		}else if(whatToProcess.contains("ANIMATE-OUT-INFOBAR")) {
			if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
				this_animation.Lof_ISPL_AnimateOut("F12,", print_writers, session_configuration);
			}else {
				this_animation.AnimateOut("F12,", print_writers, session_configuration);				
			}
		}else if(whatToProcess.contains("ANIMATE-OUT-IDENT")) {
			if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
				this_animation.Lof_ISPL_AnimateOut("Control_F12,", print_writers, session_configuration);
			}else {
				this_animation.AnimateOut("Control_F12,", print_writers, session_configuration);	
			}
		}else if(whatToProcess.contains("QUIDICH-COMMANDS")) {
			this_animation.processQuidichCommands(valueToProcess, print_writers, session_configuration);
		}else if(whatToProcess.contains("ANIMATE-OUT-TAPE")) {
			if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
				this_animation.Lof_ISPL_AnimateOut("Control_F8,", print_writers, session_configuration);
			}else {
				this_animation.AnimateOut("Control_F8,", print_writers, session_configuration);	
			}
		}else if(whatToProcess.contains("ANIMATE-OUT-TARGET")) {
			if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
				this_animation.Lof_ISPL_AnimateOut("Alt_y,", print_writers, session_configuration);
			}else {
				this_animation.AnimateOut("Alt_y,", print_writers, session_configuration);
			}
		}else if(whatToProcess.contains("ANIMATE-OUT-BOTTOM")) {
			if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
				this_animation.Lof_ISPL_AnimateOut("Shift_+,", print_writers, session_configuration);
			}
		}
	}
	public static List<Stats> getPlayerFromMatchData(String whatToProcess, MatchAllData match)
	{
		List<Stats> stats = new ArrayList<Stats>();
		
		switch(whatToProcess) {
		case "Shift_!":
			for(Player plyr : match.getSetup().getHomeSquad()) {
				stats.add(new Stats(plyr.getPlayerId(), match.getSetup().getHomeTeam(), plyr, new ArrayList<Statistics>()));
			}
			for(Player plyr : match.getSetup().getHomeSubstitutes()) {
				stats.add(new Stats(plyr.getPlayerId(), match.getSetup().getHomeTeam(), plyr, new ArrayList<Statistics>()));
			}
			for(Player plyr : match.getSetup().getAwaySquad()) {
				stats.add(new Stats(plyr.getPlayerId(), match.getSetup().getAwayTeam(), plyr, new ArrayList<Statistics>()));
			}
			for(Player plyr : match.getSetup().getAwaySubstitutes()) {
				stats.add(new Stats(plyr.getPlayerId(), match.getSetup().getAwayTeam(), plyr, new ArrayList<Statistics>()));
			}
			break;
		case "Shift_~":
			for(Player plyr : match.getSetup().getHomeSquad()) {
				stats.add(new Stats(plyr.getPlayerId(), match.getSetup().getHomeTeam(), plyr, null, null));
			}
			for(Player plyr : match.getSetup().getHomeSubstitutes()) {
				stats.add(new Stats(plyr.getPlayerId(), match.getSetup().getHomeTeam(), plyr, null, null));
			}
			for(Player plyr : match.getSetup().getAwaySquad()) {
				stats.add(new Stats(plyr.getPlayerId(), match.getSetup().getAwayTeam(), plyr, null, null));
			}
			for(Player plyr : match.getSetup().getAwaySubstitutes()) {
				stats.add(new Stats(plyr.getPlayerId(), match.getSetup().getAwayTeam(), plyr, null, null));
			}
			break;
		}
		return stats;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> GetGraphicOption(String whatToProcess,Configuration session_configuration, HeadToHead headToHead) throws Exception {
  switch ((whatToProcess.contains(",")?whatToProcess.split(",")[0]:whatToProcess)) {
  	case "Alt_3":
	    StatsType stat = (whatToProcess.split(",").length > 2) ? 
	            cricketService.getAllStatsType().stream()
	                .filter(st -> st.getStats_full_name().equalsIgnoreCase(whatToProcess.split(",")[2]))
	                .findAny().orElse(null) : null;
	    if (stat != null) {
	        return (List<T>) cricketService.getAllStats().stream()
	            .filter(st -> st.getStats_type_id() == stat.getStats_id())
	            .collect(Collectors.toList());
	    }
	    break;
  	case "Control_Shift_F5":
  	    return (List<T>) new ArrayList<>(CricketFunctions.ReadExcel("C:\\Sports\\Cricket\\Summary.xlsx").keySet());
  	case "Alt_Shift_F5":
  	  return (List<T>)cricketService.getPointers();
  	case "Alt_e":
			this_caption.whichSide = 1;
			this_caption.PopulateGraphics("Alt_e,", session_match);
			break;
		case "Control_Shift_J":
			return (List<T>) session_performance_bug;
		case "Control_Shift_X":
			if(whatToProcess.split(",")[whatToProcess.split(",").length-1].equalsIgnoreCase("BowlerVsBatsman")) {
				LowerThirdGfx.PlayerList = CricketFunctions.BowlerVsBatsman(Integer.valueOf(whatToProcess.split(",")[1]), 
					Integer.valueOf(whatToProcess.split(",")[2]), session_match.getEventFile().getEvents(), session_match);
			
			}else if(whatToProcess.split(",")[whatToProcess.split(",").length-1].equalsIgnoreCase("BatsmanVsBowler")) {
				LowerThirdGfx.PlayerList = CricketFunctions.BatsmanVsBowler(Integer.valueOf(whatToProcess.split(",")[1]), 
						Integer.valueOf(whatToProcess.split(",")[2]), session_match.getEventFile().getEvents(), session_match);
			}
			return (List<T>) LowerThirdGfx.PlayerList;
		case "F10": case "j":
		    return (List<T>) session_name_super;
		case "k": case "Shift_Y":
			return (List<T>) session_bugs;
		case "Shift_M":
			return (List<T>) cricketService.getLeaderBoards();
		case "Control_m": case "Shift_F11": case "Control_Shift_L": case "Control_9":
			return (List<T>) CricketFunctions.processAllFixtures(cricketService);
		case "Alt_9":
			return (List<T>) session_infoBarStats;
		case "Alt_0":
			return (List<T>) session_commentator;
		case "Alt_a":
			return (List<T>) CricketFunctions.processAllStaff(cricketService, session_match.getSetup().getHomeTeamId());
		case "Alt_s":
			return (List<T>) CricketFunctions.processAllStaff(cricketService, session_match.getSetup().getAwayTeamId());
		case "Alt_q":
			return (List<T>) CricketFunctions.processAllPott(cricketService);
		case "Alt_Shift_R": case "Alt_z": case "Alt_Shift_W": case "Control_0": //case "Control_Shift_F8":
			return (List<T>) session_team;
		case "Shift_!":
			List<Stats> database_statistics = new ArrayList<Stats>();
			database_statistics = getPlayerFromMatchData(whatToProcess ,session_match);
			
			for(Statistics stats : session_statistics) {
				for(int i=0;i<=database_statistics.size()-1;i++) {
					if(database_statistics.get(i).getPlayerId() == stats.getPlayer_id()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						database_statistics.get(i).getStatsList().add(stats);
					}
				}
			}
			
			return (List<T>) database_statistics;
		case "Shift_~":
			List<Stats> statistics = new ArrayList<Stats>();
			statistics = getPlayerFromMatchData(whatToProcess ,session_match);
			
			for(Statistics stats : session_statistics) {
				for(int i=0;i<=statistics.size()-1;i++) {
					if(statistics.get(i).getPlayerId() == stats.getPlayer_id()) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = CricketFunctions.updateTournamentWithH2h(stats, headToHead.getH2hPlayer(), session_match, CricketUtil.FULL);
						stats = CricketFunctions.updateStatisticsWithMatchData(stats, session_match, CricketUtil.FULL);
						statistics.get(i).setStats(stats);
					}
				}
			}
			for(Tournament tour : CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead.getH2hPlayer(),cricketService, session_match, past_tournament_stats)) {
				for(int i=0;i<=statistics.size()-1;i++) {
					if(tour.getPlayerId() == statistics.get(i).getPlayerId()) {
						statistics.get(i).setTournament(tour);
					}
				}
			}
			
			return (List<T>) statistics;
//		case "Control_Shift_F8":
//			if(whatToProcess.contains(",")) {
//				FullFramesGfx.stats_past_tournament = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead.getH2hPlayer(), cricketService, 
//						session_match, past_tournament_stats);
//				FullFramesGfx.stats_past_tournament.removeIf(tournament -> tournament.getPlayer().getTeamId() != Integer.valueOf(whatToProcess.split(",")[1])); 
//				switch(whatToProcess.split(",")[2]) {
//					case "MOST RUNS":
//						Collections.sort(FullFramesGfx.stats_past_tournament,new CricketFunctions.BatsmenMostRunComparator());
//						break;
//					case "MOST WICKETS":
//						Collections.sort(FullFramesGfx.stats_past_tournament,new CricketFunctions.BowlerWicketsComparator());
//						break;
//					case "MOST FOURS":
//						Collections.sort(FullFramesGfx.stats_past_tournament,new CricketFunctions.BatsmanFoursComparator());
//						break;
//					case "MOST SIXES":
//						Collections.sort(FullFramesGfx.stats_past_tournament,new CricketFunctions.BatsmanSixesComparator());
//						break;
//					}
//		        return FullFramesGfx.stats_past_tournament.size() > 5 ? (List<T>) FullFramesGfx.stats_past_tournament.subList(0, 5) : (List<T>) FullFramesGfx.stats_past_tournament;
//			}else {
//				return (List<T>) cricketService.getTeams();
//			}
		case "z": case "x": case "c": case "v": case "Control_Shift_Z": case "Control_Shift_Y": case "Shift_@": case "Shift_$": case "Control_Shift_@": case "Control_Alt_5":
		case "Control_Alt_9": case "Control_Alt_0":	case "Alt_Shift_K": case "Alt_Shift_X": case "Alt_Shift_T": case "Alt_Shift_V": 
			
			List<Tournament> tournamentStats = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, 
				headToHead.getH2hPlayer(), cricketService, session_match, past_tournament_stats);

			if (Constants.MPL.equalsIgnoreCase(session_configuration.getBroadcaster()) 
				&& session_configuration.getCategory() != null && !session_configuration.getCategory().trim().isEmpty()) {
				String gender = session_configuration.getCategory().trim();
			    tournamentStats.removeIf(t -> t.getPlayer() == null || !gender.equalsIgnoreCase(t.getPlayer().getGender()));
			}
		
		    Comparator<Tournament> comparator = SORT_MAP.get(whatToProcess);

		    if (comparator != null) {
		        tournamentStats.sort(comparator);
		    }
		    
			return (List<T>) tournamentStats;
			
//			List<Tournament> gender_Specific_tournament_stats = new ArrayList<Tournament>();
//			List<Tournament> tournament_stats = new ArrayList<Tournament>();
//			
//			if(session_configuration.getBroadcaster().equalsIgnoreCase(Constants.MPL)) {
//				if(!session_configuration.getCategory().trim().isEmpty()) {
//					gender_Specific_tournament_stats = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead.getH2hPlayer(), cricketService, 
//							session_match, past_tournament_stats);
//					
//					for(int i = 0; i <= gender_Specific_tournament_stats.size()-1; i++) {
//						if(gender_Specific_tournament_stats.get(i).getPlayer().getGender().equalsIgnoreCase(session_configuration.getCategory())) {
//							tournament_stats.add(gender_Specific_tournament_stats.get(i));
//						}
//					}
//				}else {
//					tournament_stats = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead.getH2hPlayer(), cricketService, 
//							session_match, past_tournament_stats);
//				}
//				
//			}else {
//				tournament_stats = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead.getH2hPlayer(), cricketService, 
//						session_match, past_tournament_stats);
//			}
//			
//			switch (whatToProcess) {
//			case "z": case "Shift_@": case "Alt_Shift_K":
//				Collections.sort(tournament_stats,new CricketFunctions.BatsmenMostRunComparator());
//				break;
//			case "x": case "Shift_$": case "Alt_Shift_X":
//				Collections.sort(tournament_stats,new CricketFunctions.BowlerWicketsComparator());
//				break;
//			case "c": case "Control_Shift_@": case "Alt_Shift_T":
//				Collections.sort(tournament_stats,new CricketFunctions.BatsmanFoursComparator());
//				break;
//			case "v": case "Control_Alt_5": case "Alt_Shift_V":
//				Collections.sort(tournament_stats,new CricketFunctions.BatsmanSixesComparator());
//				break;
//			case "Control_Shift_Z": case "Control_Alt_9": 
//				Collections.sort(tournament_stats,new CricketFunctions.BestBatsmanStrikeRateComparator());
//				break;
//			case "Control_Shift_Y": case "Control_Alt_0":
//				Collections.sort(tournament_stats,new CricketFunctions.BestBowlerEconomyComparator());
//				break;
//			}
//			return (List<T>) tournament_stats;
			
		case "Control_Alt_8":
			mvp_leaderBoard mvp = new mvp_leaderBoard();
			List<Player> mvp_player = new ArrayList<Player>();
 			
			if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MVP).exists()) {
				mvp = (objectMapper.readValue(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MVP), mvp_leaderBoard.class));
			}
			for (int i = 0; i < mvp.getData().getTop().size(); i++) {
				String player_id = mvp.getData().getTop().get(i).getPlayerId();
				mvp_player.add(cricketService.getAllPlayer().stream().filter(plyr -> Long.valueOf(player_id).equals(plyr.getOnlineId())).findAny().orElse(null));
			}
			for (int i = 0; i < mvp.getData().getList().size(); i++) {
				String player_id = mvp.getData().getList().get(i).getPlayerId();
				mvp_player.add(cricketService.getAllPlayer().stream().filter(plyr -> Long.valueOf(player_id).equals(plyr.getOnlineId())).findAny().orElse(null));
			}
			return (List<T>) mvp_player;
			
		case "Control_c": case "Control_Alt_7":
			List<BestStats> tapeball = new ArrayList<BestStats>();
			tapeball = CricketFunctions.extractTapeData("CURRENT_MATCH_DATA", cricketService, session_match, past_tape, headToHead.getH2hPlayer());
			Collections.sort(tapeball,new CricketFunctions.TapeBowlerWicketsComparator());
			return (List<T>) tapeball;
		case "Control_v":
			List<BestStats> log_fifty = new ArrayList<BestStats>();
			log_fifty = CricketFunctions.extractLogFifty("COMBINED_PAST_CURRENT_MATCH_DATA", CricketUtil.BOWLER, cricketService, session_match, null, 
					headToHead.getH2hPlayer());
			Collections.sort(log_fifty,new CricketFunctions.LogFiftyWicketsComparator());
			return (List<T>) log_fifty;
//		case "Control_z": case "Control_x":
//			
//	        List<Tournament> tournaments = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, 
//	        	headToHead.getH2hPlayer(), cricketService, session_match, past_tournament_stats);
//
//	        switch (whatToProcess) {
//			case "Control_z":
//		        Collections.sort(tournaments, new CricketFunctions.TopBatsmenBestStatsComparator());
//		        return (List<T>) tournaments;
//			case "Control_x":
//		        Collections.sort(tournaments, new CricketFunctions.TopBowlerBestStatsComparator());
//		        return (List<T>) tournaments;
//			}
//			return null;	
		case "Control_z": case "Control_x": case "Alt_Shift_@": case "Control_Alt_6":
//			 List<Tournament> tournaments = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead.getH2hPlayer(), cricketService, 
//		                session_match, past_tournament_stats);
			
			List<Tournament> gender_Specific_tournaments_stats = new ArrayList<Tournament>();
			List<Tournament> tournaments = new ArrayList<Tournament>();
			
			if(session_configuration.getBroadcaster().equalsIgnoreCase(Constants.MPL)) {
				
				if(!session_configuration.getCategory().trim().isEmpty()) {
					gender_Specific_tournaments_stats = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead.getH2hPlayer(), cricketService, 
							session_match, past_tournament_stats);
					
					for(int i = 0; i <= gender_Specific_tournaments_stats.size()-1; i++) {
						if(gender_Specific_tournaments_stats.get(i).getPlayer().getGender().equalsIgnoreCase(session_configuration.getCategory())) {
							tournaments.add(gender_Specific_tournaments_stats.get(i));
						}
					}
				}else {
					tournaments = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead.getH2hPlayer(), cricketService, 
			                session_match, past_tournament_stats);
				}
				
			}else {
				tournaments = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA", false, headToHead.getH2hPlayer(), cricketService, 
		                session_match, past_tournament_stats);
			}
			 
			List<BestStats> top_ten_beststat = new ArrayList<BestStats>();
	        for(Tournament tourn : tournaments) {
	        	
				switch (whatToProcess) {
				case "Control_z": case "Alt_Shift_@":
					//top_ten_beststat.clear();
		            for(BestStats bs : tourn.getBatsman_best_Stats()) {
//		            	System.out.println("bs = " + bs.getPlayer().getFull_name() + "  runs = " + bs.getBestEquation());
		            	top_ten_beststat.add(CricketFunctions.getProcessedBatsmanBestStats(bs));
		            	//break;
		            }
					Collections.sort(top_ten_beststat,new CricketFunctions.BatsmanBestStatsComparator());
					break;
				case "Control_x": case "Control_Alt_6":
		            for(BestStats bs : tourn.getBowler_best_Stats()) {
		            	top_ten_beststat.add(CricketFunctions.getProcessedBowlerBestStats(bs));
		            }
					Collections.sort(top_ten_beststat,new CricketFunctions.BowlerBestStatsComparator());
					break;
				}
	        }
	        
			return (List<T>) top_ten_beststat;	
		}
		return null;
	}
	
	public void GetVariousDBData(String typeOfUpdate, Configuration config, HeadToHead headToHead) throws StreamReadException, DatabindException, 
		IllegalAccessException, InvocationTargetException, JAXBException, IOException, CloneNotSupportedException, InterruptedException, URISyntaxException
	{
		switch (config.getBroadcaster()) {
		case Constants.ICC_U19_2023: case Constants.ISPL: case Constants.BENGAL_T20: case Constants.NPL: case Constants.LEGENDS: 
		case Constants.T20_MUMBAI: case Constants.MPL: case Constants.APL:
			
			switch (typeOfUpdate) {
			case "ONLY_DB":
				session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ, 
						CricketUtil.SETUP + "," + CricketUtil.MATCH + "," + CricketUtil.EVENT, session_match, config), session_players, session_team, session_ground);
				session_match.getSetup().setGenerateInteractiveFile(config.getGenerateInteractiveFile());

				MatchStats = CricketFunctions.getAllEvents(session_match, config.getBroadcaster(), session_match.getEventFile().getEvents());
				CricketFunctions.getInteractive(session_match, "FULL_WRITE");

				session_match.getMatch().setMatchStats(MatchStats);
				
				session_performance_bug = cricketService.getPerformanceBugs();
				session_name_super =  cricketService.getNameSupers();
				session_team =  cricketService.getTeams();
				session_ground =  cricketService.getGrounds();
				session_bugs = cricketService.getBugs();
				session_infoBarStats = cricketService.getInfobarStats();
				session_variousText = cricketService.getVariousTexts();
				session_commentator = cricketService.getCommentator();
				session_staff = cricketService.getStaff();
				session_fixture =  CricketFunctions.processAllFixtures(cricketService);
				session_players = cricketService.getAllPlayer();
				session_pott = cricketService.getPott();
				session_playoff = cricketService.getPlayOff();
				
				//Bug and Mini
				this_caption.this_bugsAndMiniGfx.bugs = session_bugs;
				this_caption.this_bugsAndMiniGfx.Teams = session_team;
				this_caption.this_bugsAndMiniGfx.VariousText = session_variousText;
				this_caption.this_bugsAndMiniGfx.performanceBugs = session_performance_bug;
				this_caption.this_bugsAndMiniGfx.statistics = session_statistics;
				this_caption.this_bugsAndMiniGfx.statsTypes = cricketService.getAllStatsType();
				
				if(config.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
					//InfoBar
					this_caption.this_lofInfobarGfx.statistics = session_statistics;
					this_caption.this_lofInfobarGfx.statsTypes = cricketService.getAllStatsType();
					this_caption.this_lofInfobarGfx.infobarStats  = session_infoBarStats;
					this_caption.this_lofInfobarGfx.Grounds = session_ground;
					this_caption.this_lofInfobarGfx.dls  = session_dls;
					this_caption.this_lofInfobarGfx.Commentators = session_commentator;
					this_caption.this_lofInfobarGfx.Players = session_players;
				}else {
					//InfoBar
					this_caption.this_infobarGfx.statistics = session_statistics;
					this_caption.this_infobarGfx.statsTypes = cricketService.getAllStatsType();
					this_caption.this_infobarGfx.infobarStats  = session_infoBarStats;
					this_caption.this_infobarGfx.Grounds = session_ground;
					this_caption.this_infobarGfx.dls  = session_dls;
					this_caption.this_infobarGfx.Commentators = session_commentator;
					this_caption.this_infobarGfx.Players = session_players;
				}
				
				//LowerThird
				this_caption.this_lowerThirdGfx.statistics = session_statistics;
				this_caption.this_lowerThirdGfx.statsTypes = cricketService.getAllStatsType();
				this_caption.this_lowerThirdGfx.nameSupers = session_name_super;
				this_caption.this_lowerThirdGfx.Teams = session_team;
				this_caption.this_lowerThirdGfx.Grounds = session_ground;
				this_caption.this_lowerThirdGfx.tournaments = past_tournament_stats;
				this_caption.this_lowerThirdGfx.tapeballs = past_tape;
				this_caption.this_lowerThirdGfx.dls = session_dls;
				this_caption.this_lowerThirdGfx.Staff = session_staff;
				this_caption.this_lowerThirdGfx.VariousText = session_variousText;
				this_caption.this_lowerThirdGfx.Potts = session_pott;
				this_caption.this_lowerThirdGfx.fixTures = session_fixture;
				
				
				//FullFrames
				this_caption.this_fullFramesGfx.statistics = session_statistics;
				this_caption.this_fullFramesGfx.statsTypes = cricketService.getAllStatsType();
				this_caption.this_fullFramesGfx.fixTures = session_fixture;
				this_caption.this_fullFramesGfx.Teams = session_team;
				this_caption.this_fullFramesGfx.Grounds = session_ground;
				this_caption.this_fullFramesGfx.tournaments = past_tournament_stats;
				this_caption.this_fullFramesGfx.VariousText = session_variousText;
				this_caption.this_fullFramesGfx.Potts = session_pott;
				this_caption.this_fullFramesGfx.Playoffs = session_playoff;
				break;
			default:
				session_statistics = cricketService.getAllStats();
				if(config.getBroadcaster().equalsIgnoreCase(Constants.ISPL)) {
					past_tape = CricketFunctions.extractTapeData("PAST_MATCHES_DATA", cricketService, session_match, null, headToHead.getH2hPlayer());
					past_tournament_stats = CricketFunctions.extractTournamentData("PAST_MATCHES_DATA", false, headToHead.getH2hPlayer(), cricketService, session_match, null);
				}else {
					past_tournament_stats = CricketFunctions.extractTournamentData("PAST_MATCHES_DATA", false, headToHead.getH2hPlayer(), cricketService, session_match, null);
				}
				
				past_tournament_boundaries = CricketFunctions.extracttournamentFoursAndSixesData("PAST_MATCHES_DATA", headToHead.getH2hPlayer(), session_match, null);
				
				session_performance_bug = cricketService.getPerformanceBugs();
				session_name_super =  cricketService.getNameSupers();
				session_team =  cricketService.getTeams();
				session_ground =  cricketService.getGrounds();
				session_bugs = cricketService.getBugs();
				session_infoBarStats = cricketService.getInfobarStats();
				session_variousText = cricketService.getVariousTexts();
				session_commentator = cricketService.getCommentator();
				session_staff = cricketService.getStaff();
				session_fixture =  CricketFunctions.processAllFixtures(cricketService);
				session_players = cricketService.getAllPlayer();
				session_pott = cricketService.getPott();
				session_playoff = cricketService.getPlayOff();
				
				if(new File(CricketUtil.CRICKET_DIRECTORY + "ParScores BB.html").exists() && session_match != null) {
					session_dls = CricketFunctions.populateDuckWorthLewis(session_match);
				}
				
				if(new File(CricketUtil.CRICKET_DIRECTORY + "TeamChanges.txt").exists()) {
					String text_to_return = "";
					try (BufferedReader br = new BufferedReader(new FileReader(CricketUtil.CRICKET_DIRECTORY + "TeamChanges.txt"))) {
						while((text_to_return = br.readLine()) != null) {
							if(text_to_return.contains("|")) {
								
							}else {
								if(text_to_return.contains("H") || text_to_return.contains("A")) {
									session_teamChanges.add(text_to_return);
								}
							}
						}
					}
				}
				
				switch (typeOfUpdate) {
				case "NEW":
					this_caption = new Caption(print_writers, config, session_statistics,cricketService.getAllStatsType(), session_name_super,
						session_bugs,session_infoBarStats,session_fixture, session_team, session_ground,session_variousText, session_commentator, session_staff, 
						session_players, session_pott,session_playoff, session_teamChanges, session_performance_bug, new FullFramesGfx(),new LowerThirdGfx(), 
						new InfobarGfx(), new LofInfobarGfx(), new BugsAndMiniGfx(), 1, "", "-", past_tournament_stats,past_tape,session_dls, headToHead.getH2hPlayer(), 
						past_tournament_stats, cricketService);
					this_caption.this_infobarGfx.previous_sixes = String.valueOf(past_tournament_boundaries.getTournament_sixes());
					this_caption.this_infobarGfx.previous_fours = String.valueOf(past_tournament_boundaries.getTournament_fours());
					
					this_caption.this_lofInfobarGfx.previous_sixes = String.valueOf(past_tournament_boundaries.getTournament_sixes());
					this_caption.this_lofInfobarGfx.previous_fours = String.valueOf(past_tournament_boundaries.getTournament_fours());
					//extracttournamentFoursAndSixesData also have nines runs data also
					this_caption.this_lofInfobarGfx.previous_nines = String.valueOf(past_tournament_boundaries.getTournament_nines());
							
					this_caption.this_bugsAndMiniGfx.previous_sixes =  String.valueOf(past_tournament_boundaries.getTournament_sixes());
					this_caption.this_bugsAndMiniGfx.previous_fours =  String.valueOf(past_tournament_boundaries.getTournament_fours());
					break;
					
				case "UPDATE":
					
					session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ, 
							CricketUtil.SETUP + "," + CricketUtil.MATCH + "," + CricketUtil.EVENT, session_match, config), session_players, session_team, session_ground);
					session_match.getSetup().setGenerateInteractiveFile(config.getGenerateInteractiveFile());
					CricketFunctions.getInteractive(session_match, "FULL_WRITE");
					
					this_caption.this_infobarGfx.previous_sixes = String.valueOf(past_tournament_boundaries.getTournament_sixes());
					this_caption.this_infobarGfx.previous_fours = String.valueOf(past_tournament_boundaries.getTournament_fours());
					
					this_caption.this_lofInfobarGfx.previous_sixes = String.valueOf(past_tournament_boundaries.getTournament_sixes());
					this_caption.this_lofInfobarGfx.previous_fours = String.valueOf(past_tournament_boundaries.getTournament_fours());
					//extracttournamentFoursAndSixesData also have nines runs data also
					this_caption.this_lofInfobarGfx.previous_nines = String.valueOf(past_tournament_boundaries.getTournament_nines());
							
					this_caption.this_bugsAndMiniGfx.previous_sixes =  String.valueOf(past_tournament_boundaries.getTournament_sixes());
					this_caption.this_bugsAndMiniGfx.previous_fours =  String.valueOf(past_tournament_boundaries.getTournament_fours());

					//Bug and Mini
					this_caption.this_bugsAndMiniGfx.bugs = session_bugs;
					this_caption.this_bugsAndMiniGfx.Teams = session_team;
					this_caption.this_bugsAndMiniGfx.VariousText = session_variousText;
					this_caption.this_bugsAndMiniGfx.performanceBugs = session_performance_bug;
					this_caption.this_bugsAndMiniGfx.statistics = session_statistics;
					this_caption.this_bugsAndMiniGfx.statsTypes = cricketService.getAllStatsType();
					//InfoBar
					if(config.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
						this_caption.this_lofInfobarGfx.statistics = session_statistics;
						this_caption.this_lofInfobarGfx.statsTypes = cricketService.getAllStatsType();
						this_caption.this_lofInfobarGfx.infobarStats  = session_infoBarStats;
						this_caption.this_lofInfobarGfx.Grounds = session_ground;
						this_caption.this_lofInfobarGfx.dls  = session_dls;
						this_caption.this_lofInfobarGfx.Commentators = session_commentator;
					}else {
						this_caption.this_infobarGfx.statistics = session_statistics;
						this_caption.this_infobarGfx.statsTypes = cricketService.getAllStatsType();
						this_caption.this_infobarGfx.infobarStats  = session_infoBarStats;
						this_caption.this_infobarGfx.Grounds = session_ground;
						this_caption.this_infobarGfx.dls  = session_dls;
						this_caption.this_infobarGfx.Commentators = session_commentator;	
					}
					
					//LowerThird
					this_caption.this_lowerThirdGfx.statistics = session_statistics;
					this_caption.this_lowerThirdGfx.statsTypes = cricketService.getAllStatsType();
					this_caption.this_lowerThirdGfx.nameSupers = session_name_super;
					this_caption.this_lowerThirdGfx.Teams = session_team;
					this_caption.this_lowerThirdGfx.Grounds = session_ground;
					this_caption.this_lowerThirdGfx.tournaments = past_tournament_stats;
					this_caption.this_lowerThirdGfx.tapeballs = past_tape;
					this_caption.this_lowerThirdGfx.dls = session_dls;
					this_caption.this_lowerThirdGfx.Staff = session_staff;
					this_caption.this_lowerThirdGfx.VariousText = session_variousText;
					this_caption.this_lowerThirdGfx.Potts = session_pott;
					this_caption.this_lowerThirdGfx.fixTures = session_fixture;
					
					//FullFrames
					this_caption.this_fullFramesGfx.statistics = session_statistics;
					this_caption.this_fullFramesGfx.statsTypes = cricketService.getAllStatsType();
					this_caption.this_fullFramesGfx.fixTures = session_fixture;
					this_caption.this_fullFramesGfx.Teams = session_team;
					this_caption.this_fullFramesGfx.Grounds = session_ground;
					this_caption.this_fullFramesGfx.tournaments = past_tournament_stats;
					this_caption.this_fullFramesGfx.VariousText = session_variousText;
					this_caption.this_fullFramesGfx.Potts = session_pott;
					this_caption.this_fullFramesGfx.Playoffs = session_playoff;
					if(new File(CricketUtil.CRICKET_DIRECTORY + "TeamChanges.txt").exists()) {
						String text_to_return = "";
						this_caption.this_fullFramesGfx.TeamChanges.clear();
						try (BufferedReader br = new BufferedReader(new FileReader(CricketUtil.CRICKET_DIRECTORY + "TeamChanges.txt"))) {
							while((text_to_return = br.readLine()) != null) {
								if(text_to_return.contains("|")) {
									
								}else {
									if(text_to_return.contains("H") || text_to_return.contains("A")) {
										this_caption.this_fullFramesGfx.TeamChanges.add(text_to_return);
									}
								}
							}
						}
					}
					
					break;
				}
				break;
			}
			break;
		}
	}
}