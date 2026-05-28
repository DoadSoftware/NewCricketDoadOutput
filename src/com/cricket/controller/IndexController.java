package com.cricket.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
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
import com.cricket.config.DatabaseContextHolder;
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

	private File speedFile = new File("C:\\Sports\\Cricket\\Speed\\SPEED.txt");
	private final ObjectMapper objectMapper = new ObjectMapper();
	private long last_match_time_stamp = 0;
	private boolean show_speed = false;
	private long speed_match_time_stamp = 0;
	public boolean Plotter_file_change = false;
	public String expiryDate = "";
	public static String basePath = "";
	
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
		DatabaseContextHolder.setDb("LOCAL");
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
	
	@RequestMapping(value = {"/output"}, method = {RequestMethod.GET, RequestMethod.POST})
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

	        throws Exception {

	    // VALIDATE LICENSE
	    if (current_date == null || current_date.trim().isEmpty()) {
	        model.addAttribute("error_message", "You must be connected to the internet online");
	        return "error";
	    }

	    LocalDate currentDate = LocalDate.parse(current_date);
	    LocalDate expiryDateLocal = LocalDate.parse(expiry_date);

	    if (expiryDateLocal.isBefore(currentDate)) {
	        model.addAttribute("error_message", "This software has expired");
	        return "error";
	    }

	    expiryDate = String.valueOf(ChronoUnit.DAYS.between(currentDate, expiryDateLocal));

	    // COMMON VARIABLES
	    boolean isArchive = select_type != null && !select_type.trim().isEmpty() && !select_type.equals(",");
	    String seriesType = isArchive ? select_type.split(",", -1)[0]: "";
	    basePath = isArchive ? CricketUtil.CRICKET_ARCHIVE_DIRECTORY + CricketUtil.ARCHIVE_MATCHES_DIRECTORY 
				+ seriesType + "/" : CricketUtil.CRICKET_DIRECTORY;
	    
	    if (Category.equalsIgnoreCase("men")) {
	    	basePath = "C:\\Sports\\CricketMen\\";
	    	DatabaseContextHolder.setDb("MEN");
	    } else if (Category.equalsIgnoreCase("women")) {
	    	basePath = "C:\\Sports\\CricketWomen\\";
	    	DatabaseContextHolder.setDb("WOMEN");
	    }
	    
	    speedFile = new File(basePath + "Speed\\SPEED.txt");
	    System.out.println("basePath - " + basePath);
	    
	    // LAST MODIFIED
	    System.out.println("basePath = " + basePath);
	    last_match_time_stamp = new File(basePath + CricketUtil.MATCHES_DIRECTORY + selectedMatch).lastModified();

	    // CONFIGURATION
	    session_configuration = new Configuration(selectedMatch,select_broadcaster,select_second_broadcaster,vizIPAddress,vizPortNumber,
	            vizLanguage, qtIPAddress, qtPortNumber, primaryVariousOptions, vizSecondaryIPAddress,vizSecondaryPortNumber, vizSecondaryLanguage,
	            previewOnOrOff,selectInfobar, generateInteractiveFile,Category,seriesType);
				
	    session_configuration.setCategory(Category);

	    JAXBContext.newInstance(Configuration.class).createMarshaller().marshal(session_configuration,new File(
	                CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY+ configuration_file_name));

	    // PRINT WRITERS + DB
	    print_writers = CricketFunctions.processPrintWriter(session_configuration);
	    GetVariousDBData("", session_configuration, headToHead);

	    // INITIALIZE OBJECTS
	    this_scene = new Scene();
	    this_animation = new Animation(new Infobar());
	    session_match = new MatchAllData();

	    // LOAD FILES
	    session_match.setSetup(loadFile(basePath + CricketUtil.SETUP_DIRECTORY + selectedMatch, Setup.class));
	    session_match.setMatch(loadFile(basePath + CricketUtil.MATCHES_DIRECTORY + selectedMatch,Match.class));
	    session_match.setEventFile(loadFile(basePath + CricketUtil.EVENT_DIRECTORY + selectedMatch,EventFile.class));

	    // INITIALIZE MATCH
	    if (session_match.getMatch() != null) {
	        session_match.getMatch().setMatchFileName(selectedMatch);
	        
	        System.out.println("selectedMatch = " + selectedMatch);
	        initializeMatchData(true,session_match,session_configuration,session_players,session_team,session_ground);
	    }

	    // HEAD TO HEAD
	    if (headToHead.getH2hPlayer() == null) {
	        headToHead.setH2hPlayer(new ArrayList<>());
	    }
	    if (headToHead.getH2hTeam() == null) {
	        headToHead.setH2hTeam(new ArrayList<>());
	    }

	    if (headToHead.getH2hPlayer().isEmpty()) {
	        HeadToHead extractedH2H = CricketFunctions.extractHeadToHead(session_match,cricketService, basePath);
	        headToHead.setH2hPlayer(extractedH2H.getH2hPlayer());
	        headToHead.setH2hTeam(extractedH2H.getH2hTeam());
	    }

	    if (session_match.getMatch() != null) {
	        session_match.getMatch().setMatchStats(MatchStats);
	    }

	    GetVariousDBData("NEW", session_configuration, headToHead);

	    // LOAD SCENES
	    loadScenes(select_broadcaster);

	    // SPECIAL CASES
	    if (select_broadcaster.equalsIgnoreCase(Constants.MPL)) {
	        this_caption.this_fullFramesGfx.setFullFrameBase(session_configuration);
	    } else if (select_broadcaster.equalsIgnoreCase(Constants.BENGAL_T20)) {
	        this_caption.this_fullFramesGfx.PopulateFfFooter(0, "", session_match, 0);
	    }

	    // WHICH INNING
	    if(session_match.getMatch().getInning() != null) {
			model.addAttribute("which_inning", session_match.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning()
					.equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null).getInningNumber());
		} else {
			model.addAttribute("which_inning", "1");
		}

	    // MODEL ATTRIBUTES
	    model.addAttribute("session_match", session_match);
	    model.addAttribute("expiryDate", expiryDate);
	    model.addAttribute("session_configuration", session_configuration);
	    model.addAttribute("select_second_broadcaster", select_second_broadcaster);
	    model.addAttribute("select_broadcaster", select_broadcaster);
	    model.addAttribute("select_type", select_type);

	    return "output";
	}
	
	@RequestMapping(value = {"/processCricketProcedures.html"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processCricketProcedures(
		@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
		@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess) 
			throws Exception 
	{

		String process = whatToProcess.toUpperCase();
		//System.out.println("whatToProcess - " + whatToProcess + " | valueToProcess - " + valueToProcess);
		switch (process) {
		case "GET-CATEGORY-DATA":
		    String category = valueToProcess.trim().toLowerCase(); // "men" or "women"

		    File matchDir;
		    if (category.equalsIgnoreCase("men")) {
		        matchDir = new File("C:\\Sports\\CricketMen\\Matches\\");
		        DatabaseContextHolder.setDb("MEN");
		    } else if (category.equalsIgnoreCase("women")) {
		        matchDir = new File("C:\\Sports\\CricketWomen\\Matches\\");
		        DatabaseContextHolder.setDb("WOMEN");
		    } else {
		        matchDir = new File(CricketUtil.CRICKET_SERVER_DIRECTORY + CricketUtil.MATCHES_DIRECTORY);
		    }

		    File[] files = matchDir.listFiles(f -> f.isFile() && f.getName().toLowerCase().endsWith(".json"));
		    List<String> matchNames = new ArrayList<>();
		    if (files != null) {
		        for (File f : files) {
		            matchNames.add(f.getName());
		        }
		    }

		    Map<String, Object> response = new HashMap<>();
		    response.put("configuration", session_configuration);
		    response.put("matchFiles", matchNames);
		    return objectMapper.writeValueAsString(response);
		case "HEAD_TO_HEAD_FILE":
            return handleHeadToHead();
        case "GET-CONFIG-DATA":
            return handleConfigData(valueToProcess);
        case "DB_DATA_READ":
            return handleDbRead();
        case "RE_READ_DATA":
            return handleReReadData();
        case "TURN_ON_OR_OFF_SPEED":
            return handleSpeedToggle(valueToProcess);
        case "TURN_ON_OR_OFF_AUDIO":
            return handleAudioToggle(valueToProcess);
        case "READ-MATCH-AND-POPULATE":
            return handleReadMatchAndPopulate();
            
		default:
			switch(session_configuration.getBroadcaster()) {
			case Constants.NPL: case Constants.MPL: case Constants.APL: case Constants.VIDARBHA:
				if(process.split(",")[0].toUpperCase().equalsIgnoreCase("highlightProfile") || 
						process.split(",")[0].toUpperCase().equalsIgnoreCase("highlightLeader")) {
					this_animation.ChangeOn(process, print_writers, session_configuration);
				}
				break;
			}
			if(process.toUpperCase().equalsIgnoreCase("IMPACT-CHANGE-ON")) {
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
				case Constants.VIDARBHA:
				//	this_animation.Lof_ISPL_ChangeOn(this_caption.this_lowerThirdGfx.impactPlayerData, print_writers, session_configuration);
				//	this_caption.this_lowerThirdGfx.chnageOn = true;
					TimeUnit.MILLISECONDS.sleep(3000);
					this_caption.whichSide = 1;
					this_caption.PopulateGraphics(this_caption.this_lowerThirdGfx.impactPlayerData, session_match);
					this_animation.CutBack(this_caption.this_lowerThirdGfx.impactPlayerData, print_writers, session_configuration);
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
			if(process.toUpperCase().equalsIgnoreCase("PLAYING-XI-CHANGE-ON")) {
				this_animation.AnimateIn("Shift_T", print_writers, session_configuration);
			}
			if(process.toUpperCase().equalsIgnoreCase("ANIMATE-IN-PROFILE_IN_AT")) {
				System.out.println(this_animation.whichGraphicOnScreen);
				this_animation.ChangeOn(this_animation.whichGraphicOnScreen, print_writers, session_configuration);
			}
			if(process.contains("GRAPHICS-OPTIONS") || process.contains("GRAPHICS-OPTIONS_DATA")) {
				return objectMapper.writeValueAsString(GetGraphicOption(valueToProcess,session_configuration, headToHead));
			}else if(process.contains("POPULATE-GRAPHICS")) {
				return handlePopulateGraphics(valueToProcess);
			}
			else if(process.contains("ANIMATE-IN-GRAPHICS") || process.contains("ANIMATE-OUT-GRAPHICS") || 
					process.contains("ANIMATE-OUT-INFOBAR") || process.contains("QUIDICH-COMMANDS") || 
					process.contains("ANIMATE-OUT-TAPE") || process.contains("ANIMATE-OUT-IDENT") || 
					process.contains("ANIMATE-OUT-TARGET") || process.contains("ANIMATE-OUT-BOTTOM")) {

				if(process.contains("ANIMATE-OUT-GRAPHICS")) {
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
				
				processAnimations(process, session_configuration, valueToProcess, print_writers, headToHead);
			}else if(process.contains("ANIMATE-OUT-SECOND_PLAYING")) {
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
						
						if (this_animation.lineUpCount == 2 && !this_caption.this_fullFramesGfx.PlayerId.isEmpty()) {
							TimeUnit.MILLISECONDS.sleep(500);
							for (int i = 2; i >= 1; i--) {
								this_caption.this_fullFramesGfx.populateTeamLineUpFooter(print_writers, i, process.split(",")[0], 
										session_configuration,"SHOW-TOSS",session_match);
								this_animation.processAnimation(Constants.BACK,print_writers,"Change_BigImageLineUp$Footer",(i == 2 ? "START" : "SHOW 0.0"));

								if (i == 2) {
									TimeUnit.MILLISECONDS.sleep(1000);
								}
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
				this_animation.ResetAnimation("CLEAR-INFOBAR_DATA", print_writers, session_configuration);
			}else if(whatToProcess.contains("CANCLE-GRAPHICS")) {
				resetInfobarSections();
			}
			return objectMapper.writeValueAsString(this_animation);
		}
	}
	
	private String handleHeadToHead() throws Exception {
	    CricketFunctions.exportMatchData(session_match, basePath);
	    return objectMapper.writeValueAsString(session_match);
	}
	private String handleConfigData(String valueToProcess)throws Exception {
	    session_configuration = (Configuration) JAXBContext.newInstance(Configuration.class).createUnmarshaller()
	                    .unmarshal(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + valueToProcess));
	    return objectMapper.writeValueAsString(session_configuration);
	}
	private String handleDbRead() throws Exception {
	    GetVariousDBData("ONLY_DB",session_configuration,headToHead);
	    return objectMapper.writeValueAsString(session_match);
	}
	private String handleReReadData() throws Exception {
		System.out.println(basePath);
		
	    HeadToHead extractedH2H = CricketFunctions.extractHeadToHead(session_match,cricketService, basePath);
	    headToHead.setH2hPlayer(extractedH2H.getH2hPlayer());
	    headToHead.setH2hTeam(extractedH2H.getH2hTeam());

	    GetVariousDBData("UPDATE",session_configuration,headToHead);
	    return objectMapper.writeValueAsString(session_match);
	}
	private String handleSpeedToggle(String valueToProcess) {
	    show_speed = valueToProcess.equalsIgnoreCase("TRUE");
	    return String.valueOf(show_speed);
	}
	private String handleAudioToggle(String valueToProcess) {
	    this_animation.audioenabled = valueToProcess.equalsIgnoreCase("TRUE")
			? "TRUE" : "FALSE";
	    return null;
	}

	private String handleReadMatchAndPopulate() throws Exception {
	    if (session_match == null || session_match.getMatch() == null) {
	        return objectMapper.writeValueAsString(null);
	    }
	    File matchFile = new File(basePath + CricketUtil.MATCHES_DIRECTORY+ session_match.getMatch().getMatchFileName());

	    if (last_match_time_stamp != matchFile.lastModified()) {
	        session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ,
	                                CricketUtil.MATCH + "," + CricketUtil.EVENT,session_match,session_configuration,basePath),
	                        session_players,session_team,session_ground);
	        session_match.getSetup().setGenerateInteractiveFile(session_configuration.getGenerateInteractiveFile());
	        last_match_time_stamp = matchFile.lastModified();
	        MatchStats = CricketFunctions.getAllEvents(session_match,session_configuration.getBroadcaster(),session_match.getEventFile().getEvents());
	        CricketFunctions.getInteractive(session_match,"FULL_WRITE", basePath);
	        session_match.getMatch().setMatchStats(MatchStats);
	        updateInfobar();
	    }
	    handleSpeedGraphics();
	    handleFieldPlotter();
	    return objectMapper.writeValueAsString(session_match);
	}
	private void updateInfobar() throws Exception {
	    if (session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
	        this_caption.this_lofInfobarGfx.updateInfobar(print_writers, session_match);
	    } else {
	        this_caption.this_infobarGfx.updateInfobar(print_writers, session_match);
	    }
	}
	private void handleSpeedGraphics() throws Exception {
	    if (!show_speed || !speedFile.exists()) {
	        return;
	    }
	    long currentTimestamp = speedFile.lastModified();

	    boolean updated = speed_match_time_stamp == 0 || 
		Math.abs(speed_match_time_stamp - currentTimestamp) > 100;

	    if (!updated) {
	        return;
	    }

	    speed_match_time_stamp = currentTimestamp;

	    switch (session_configuration.getBroadcaster()) {
	        case Constants.T20_MUMBAI: case Constants.NPL: case Constants.APL: case Constants.VIDARBHA:
	            this_caption.this_infobarGfx.speed(CricketFunctions.processPrintWriter(session_configuration).get(0),session_match,session_configuration, basePath);
	            break;
	        case Constants.ISPL:
	            this_caption.this_lofInfobarGfx.speed(CricketFunctions.processPrintWriter(session_configuration).get(0),session_match);
	            break;
	    }
	}
	private void handleFieldPlotter() throws Exception {
	    File plotterFile = new File("C:\\Sports\\Cricket\\Fielder\\Fielder_Text\\FieldPlotter.txt");
	    if (!plotterFile.exists()) {
	        return;
	    }
	    List<String> lines = Files.readAllLines(plotterFile.toPath());
	    if (lines.size() < 2) {
	        return;
	    }
	    if (!lines.get(1).trim().equalsIgnoreCase("true")) {
	        return;
	    }
	    String formationFile = lines.get(0).trim();
	    fielderFormation = CricketFunctions.getFielderFormation(CricketUtil.CRICKET_DIRECTORY + "Fielder/" + formationFile);

	    if (!fielderFormation.isCheckbox()) {
	        return;
	    }
	    if (this_caption.this_infobarGfx != null) {
	        this_caption.this_infobarGfx.updateFieldPlotter(print_writers,session_match);
	    }
	}
	private String handlePopulateGraphics(String valueToProcess)throws Exception {
		String graphicsType = this_animation.getTypeOfGraphicsOnScreen(session_configuration, valueToProcess);
		String currentGraphicsType = this_animation.getTypeOfGraphicsOnScreen(session_configuration,
				this_animation.whichGraphicOnScreen);
		String command = valueToProcess.split(",")[0];
		switch (graphicsType) {
		case Constants.INFO_BAR:
			this_caption.whichSide = 1;
			String lastDataPart = "";
			if (this_animation.infobar.isInfobar_on_screen()) {
				if (command.equalsIgnoreCase("Control_F12") || command.equalsIgnoreCase("Shift_F12")) {
					this_caption.whichSide = 2;
				} else if (session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
					lastDataPart = this_caption.this_lofInfobarGfx.infobar.getLast_middle_section();
					this_caption.whichSide = (lastDataPart != null && !lastDataPart.isEmpty() && !lastDataPart.equalsIgnoreCase(CricketUtil.BATSMAN)
							&& !lastDataPart.equalsIgnoreCase("IDENT")) ? 2 : 1;
				} else {
					switch (session_configuration.getBroadcaster()) {
					case Constants.T20_MUMBAI:
						switch(command) {
						case "Alt_2": case "Alt_7":
							this_caption.whichSide = 2;
							break;
						case "Alt_5":
							lastDataPart = this_caption.this_infobarGfx.infobar.getLast_right_full_section();
							this_caption.whichSide = (lastDataPart != null && !lastDataPart.isEmpty() && !lastDataPart.equalsIgnoreCase("BLANK")) ? 2 : 1;
							break;
						case "Alt_3": case "Alt_4": case "Alt_6":
							lastDataPart = this_caption.this_infobarGfx.infobar.getLast_full_section();
							this_caption.whichSide = (lastDataPart != null && !lastDataPart.isEmpty() && !lastDataPart.equalsIgnoreCase("BLANK")) ? 2 : 1;
							break;
						case "Alt_8":
							lastDataPart = this_caption.this_infobarGfx.infobar.getLast_right_section();
							this_caption.whichSide = (lastDataPart != null && !lastDataPart.isEmpty() && !lastDataPart.equalsIgnoreCase(CricketUtil.BOWLER)) ? 2 : 1;
							break;
						}
						break;
					default:
						this_caption.whichSide = 2;
						break;
					}
				}
			}
			this_caption.PopulateGraphics(valueToProcess, session_match);
			this_animation.caption = this_caption;
			switch (session_configuration.getBroadcaster()) {
			case Constants.ISPL:
				if (!command.equalsIgnoreCase("F12")) {
					this_caption.this_lofInfobarGfx.setPositionOfScoreBug(this_caption.this_lofInfobarGfx.infobar.getMiddle_section(),2,session_configuration,0);
					this_animation.processInfoBarPreview(valueToProcess,print_writers,this_caption.whichSide,session_configuration,this_animation.whichGraphicOnScreen);
				}
				break;
			case Constants.T20_MUMBAI:
				switch(command) {
				case "Control_F12": case "Shift_F12":
					this_animation.processInfoBarPreview(valueToProcess,print_writers,this_caption.whichSide,
							session_configuration,this_animation.whichGraphicOnScreen);
					break;
				default:
					if (this_caption.status.equalsIgnoreCase(Constants.OK)) {
						processAnimations("ANIMATE-IN-GRAPHICS",session_configuration,valueToProcess,print_writers,headToHead);
						this_caption.status = CricketUtil.YES;
					}
					return objectMapper.writeValueAsString(this_caption);
				}
				break;
			default:
				if (this_caption.status.equalsIgnoreCase(Constants.OK)) {
					processAnimations("ANIMATE-IN-GRAPHICS",session_configuration,valueToProcess,print_writers,headToHead);
					this_caption.status = CricketUtil.YES;
				}
				return objectMapper.writeValueAsString(this_caption);
			}
			break;
		default:
			if (Set.of(Constants.ICC_U19_2023,Constants.ISPL,Constants.BENGAL_T20,Constants.NPL,Constants.LEGENDS,
					Constants.MPL,Constants.APL,Constants.VIDARBHA).contains(session_configuration.getBroadcaster())
					&& !session_configuration.getPrimaryVariousOptions().contains(Constants.FULL_FRAMER)
					&& graphicsType.contains(Constants.FULL_FRAMER)) {

				this_caption.setStatus("Error: Full framers captions NOT selected on start-up");
				return objectMapper.writeValueAsString(this_caption);
			}
			if (this_animation.whichGraphicOnScreen.isEmpty()) {
				if (!this_animation.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
					this_animation.ResetAnimation(this_animation.infobar.isInfobar_on_screen() ? "" : "CLEAR-ALL",print_writers,session_configuration);
				}
				this_caption.whichSide = 1;
			} else {
				this_caption.whichSide = 2;
				if (Set.of(Constants.FULL_FRAMER,Constants.LOWER_THIRD,
						Constants.NAME_SUPERS + Constants.LOWER_THIRD,
						Constants.BOUNDARIES + Constants.LOWER_THIRD,
						Constants.BUGS).contains(currentGraphicsType)
						&& !graphicsType.equals(currentGraphicsType)) {

					this_caption.whichSide = 1;
					this_caption.PopulateGraphics(valueToProcess, session_match);
					processGraphicsPreview(graphicsType, valueToProcess);
					this_caption.setStatus(currentGraphicsType.replace("_", " ")+ " is on screen. " 
						+ graphicsType.replace("_", " ")+ " not allowed");
					return objectMapper.writeValueAsString(this_caption);
				}
			}
			this_caption.PopulateGraphics(valueToProcess, session_match);
			this_animation.caption = this_caption;
			processGraphicsPreview(graphicsType, valueToProcess);
			break;
		}

		System.out.println(this_caption.getStatus());
		return objectMapper.writeValueAsString(this_caption.status);
	}
	private void processGraphicsPreview(String graphicsType,String valueToProcess) throws Exception {
	    switch (graphicsType) {
	    case Constants.FULL_FRAMER:
	        if (session_configuration.getBroadcaster().equalsIgnoreCase(Constants.ISPL)) {
	            this_animation.Lof_ISPL_FullFramesPreview(valueToProcess,print_writers,this_caption.whichSide,
	            		session_configuration,this_animation.whichGraphicOnScreen);
	        } else {
	            this_animation.processFullFramesPreview(valueToProcess,print_writers,this_caption.whichSide,
	            		session_configuration,this_animation.whichGraphicOnScreen);
	        }
	        break;
	    case Constants.LOWER_THIRD:
	    case Constants.NAME_SUPERS + Constants.LOWER_THIRD:
	    case Constants.BOUNDARIES + Constants.LOWER_THIRD:
	        this_animation.processL3Preview(valueToProcess,print_writers,this_caption.whichSide,
	        		session_configuration,session_match);
	        break;
	    case Constants.BUGS:
	        this_animation.processBugsPreview(valueToProcess,print_writers,this_caption.whichSide,
	        		session_configuration,this_animation.whichGraphicOnScreen);
	        break;
	    case Constants.MINIS:
	        this_animation.processMiniPreview(valueToProcess,print_writers,this_caption.whichSide,
	        		session_configuration,this_animation.whichGraphicOnScreen);
	        break;
	    }
	}
	private void resetInfobarSections() {
		this_caption.whichSide = 1;
		switch (session_configuration.getWhichInfobar()) {
		case "LOF_INFOBAR":
			setIfNotEmpty(
					this_caption.this_lofInfobarGfx.infobar.getLast_middle_section(),
					this_caption.this_lofInfobarGfx.infobar::setMiddle_section
			);
			break;
		default:
			switch (session_configuration.getBroadcaster()) {
			case Constants.T20_MUMBAI:
				setIfNotEmpty(
						this_caption.this_infobarGfx.infobar.getLast_middle_section(),
						this_caption.this_infobarGfx.infobar::setMiddle_section
				);
				setIfNotEmpty(
						this_caption.this_infobarGfx.infobar.getLast_right_bottom(),
						this_caption.this_infobarGfx.infobar::setRight_bottom
				);
				setIfNotEmpty(
						this_caption.this_infobarGfx.infobar.getLast_right_section(),
						this_caption.this_infobarGfx.infobar::setRight_section
				);
				setIfNotEmpty(
						this_caption.this_infobarGfx.infobar.getLast_full_section(),
						this_caption.this_infobarGfx.infobar::setFull_section
				);
				setIfNotEmpty(
						this_caption.this_infobarGfx.infobar.getLast_left_bottom(),
						this_caption.this_infobarGfx.infobar::setLeft_bottom
				);
				setIfNotEmpty(
						this_caption.this_infobarGfx.infobar.getLast_right_full_section(),
						this_caption.this_infobarGfx.infobar::setRight_full_section
				);
				break;
			}
			break;
		}
	}

	private void setIfNotEmpty(String value, Consumer<String> setter) {
		if (value != null && !value.isEmpty()) {
			setter.accept(value);
		}
	}
	
	public void infobarAnimateOutAllSection(Configuration session_configuration, MatchAllData session_match, List<PrintWriter> print_writers, HeadToHead headToHead) throws Exception {
		
		int Inn_Number = session_match.getMatch().getInning().stream().filter(inn -> inn.getIsCurrentInning()
				.equalsIgnoreCase(CricketUtil.YES)).findAny().orElse(null).getInningNumber();
		
		switch(session_configuration.getBroadcaster()) {
		case Constants.NPL: case Constants.LEGENDS: case Constants.APL: case Constants.MPL: case Constants.VIDARBHA:
			this_caption.whichSide = 2;

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
		case Constants.T20_MUMBAI:
			if(this_caption.this_infobarGfx.infobar.getLast_right_section() != null && !this_caption.this_infobarGfx.infobar.getLast_right_section().isEmpty()) {
				this_caption.PopulateGraphics("Alt_8," + Inn_Number + ",BOWLER", session_match);
				this_animation.AnimateOut("Alt_8," + Inn_Number + ",BOWLER", print_writers, session_configuration);
			}
			
			if(this_caption.this_infobarGfx.infobar.getLast_right_full_section() != null && !this_caption.this_infobarGfx.infobar.getLast_right_full_section().isEmpty()) {
				this_caption.PopulateGraphics("Alt_5," + Inn_Number + ",BLANK", session_match);
				this_animation.AnimateOut("Alt_5," + Inn_Number + ",BLANK", print_writers, session_configuration);
			}
			
			if(this_caption.this_infobarGfx.infobar.getLast_full_section() != null && !this_caption.this_infobarGfx.infobar.getLast_full_section().isEmpty()) {
				this_caption.PopulateGraphics("Alt_6," + Inn_Number + ",BLANK", session_match);
				this_animation.AnimateOut("Alt_6," + Inn_Number + ",BLANK", print_writers, session_configuration);
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
						switch(session_configuration.getBroadcaster()) {
						case Constants.T20_MUMBAI:
							switch(valueToProcess.split(",")[0]) {
							case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_8":
							    String lastSection = null;
							    switch(valueToProcess.split(",")[0]) {
							        case "Alt_5":
							            lastSection = this_caption.this_infobarGfx.infobar.getLast_right_full_section();
							            break;
							        case "Alt_3": case "Alt_4": case "Alt_6":
							            lastSection = this_caption.this_infobarGfx.infobar.getLast_full_section();
							            break;
							        case "Alt_8":
							            lastSection = this_caption.this_infobarGfx.infobar.getLast_right_section();
							            break;
							    }
							    
							    if(lastSection != null && !lastSection.trim().isEmpty()) {
							        if(this_caption.whichSide == 1) {
							            this_animation.AnimateIn(valueToProcess,print_writers,session_configuration);
							        } else {
							            this_animation.ChangeOn(valueToProcess,print_writers,session_configuration);
							            TimeUnit.MILLISECONDS.sleep(700);
							            this_caption.whichSide = 1;
							            this_caption.PopulateGraphics(valueToProcess,session_match);
							            this_animation.CutBack(valueToProcess,print_writers,session_configuration);
							        }
							    } else {
							        this_animation.AnimateOut(valueToProcess,print_writers,session_configuration);
							    }
							    break;
							case "Alt_2": case "Alt_7":
								this_animation.ChangeOn(valueToProcess, print_writers, session_configuration);
								TimeUnit.MILLISECONDS.sleep(600);
								this_caption.whichSide = 1;
								this_caption.PopulateGraphics(valueToProcess, session_match);
								this_animation.CutBack(valueToProcess, print_writers, session_configuration);
								break;
							}
							break;
						default:
							this_animation.ChangeOn(valueToProcess, print_writers, session_configuration);
							TimeUnit.MILLISECONDS.sleep(1000);
							this_caption.whichSide = 1;
							this_caption.PopulateGraphics(valueToProcess, session_match);
							this_animation.CutBack(valueToProcess, print_writers, session_configuration);
							break;
						}
					}
				}
				break;
			default:
				//FF,Mini,LT,Pop-Up Animation
				if(this_animation.whichGraphicOnScreen.isEmpty()) {
					if(session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
						this_animation.Lof_ISPL_AnimateIn(valueToProcess, print_writers, session_configuration);
					}else {
						this_animation.AnimateIn(valueToProcess, print_writers, session_configuration);
					}
				} else { // Change on
					
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
	            cricketService.getAllStatsType().stream().filter(st -> st.getStats_full_name().equalsIgnoreCase(whatToProcess.split(",")[2]))
	                .findAny().orElse(null) : null;
	    if (stat != null) {
	        return (List<T>) cricketService.getAllStats().stream().filter(st -> st.getStats_type_id() == stat.getStats_id())
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
			return (List<T>) CricketFunctions.processAllFixtures(session_fixture, session_team);
		case "Alt_9":
			return (List<T>) session_infoBarStats;
		case "Alt_0":
			return (List<T>) session_commentator;
		case "Alt_2":
			if(whatToProcess.contains(",")) {
	  			switch (whatToProcess.split(",")[1]) {
	  			case "PROMO":
	  				return (List<T>) CricketFunctions.processAllFixtures(session_fixture, session_team);
	  			}
			 }
			break;
		case "Alt_6":
		 if(whatToProcess.contains(",")) {
  			switch (whatToProcess.split(",")[1]) {
  			case "Commentators":
  				return (List<T>) session_commentator;
  			case "FreeTextDb":
  				return (List<T>) session_infoBarStats;
  			case "PROMO":
  				return (List<T>) CricketFunctions.processAllFixtures(session_fixture, session_team);
  			}
		  }
		  break;
		case "Alt_a":
			return (List<T>) CricketFunctions.processAllStaff(cricketService, session_match.getSetup().getHomeTeamId());
		case "Alt_s":
			return (List<T>) CricketFunctions.processAllStaff(cricketService, session_match.getSetup().getAwayTeamId());
		case "Alt_q":
			return (List<T>) CricketFunctions.processAllPott(cricketService);
		case "Alt_Shift_R": case "Alt_z": case "Alt_Shift_W": case "Control_0":
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
		case "Control_Shift_F8":
			if(whatToProcess.contains(",")) {
				List<Tournament> tournamentStats = CricketFunctions.extractTournamentData("CURRENT_MATCH_DATA",false, headToHead.getH2hPlayer(),
				        cricketService,session_match, past_tournament_stats);
				tournamentStats.removeIf(tournament -> tournament.getPlayer().getTeamId() != Integer.valueOf(whatToProcess.split(",")[1])); 
				switch(whatToProcess.split(",")[2]) {
					case "MOST RUNS":
						Collections.sort(tournamentStats,new CricketFunctions.BatsmenMostRunComparator());
						break;
					case "MOST WICKETS":
						Collections.sort(tournamentStats,new CricketFunctions.BowlerWicketsComparator());
						break;
					case "MOST FOURS":
						Collections.sort(tournamentStats,new CricketFunctions.BatsmanFoursComparator());
						break;
					case "MOST SIXES":
						Collections.sort(tournamentStats,new CricketFunctions.BatsmanSixesComparator());
						break;
					}
		        return tournamentStats.size() > 5 ? (List<T>) tournamentStats.subList(0, 5) : (List<T>) tournamentStats;
			}else {
				return (List<T>) cricketService.getTeams();
			}
		case "z": case "x": case "c": case "v": case "Control_Shift_Z": case "Control_Shift_Y": case "Shift_@": case "Shift_$": case "Control_Shift_@": case "Control_Alt_5":
		case "Control_Alt_9": case "Control_Alt_0":	case "Alt_Shift_K": case "Alt_Shift_X": case "Alt_Shift_T": case "Alt_Shift_V": 
			
			List<Tournament> tournamentStats = CricketFunctions.extractTournamentData(
			        "CURRENT_MATCH_DATA",false, headToHead.getH2hPlayer(),
			        cricketService,session_match, past_tournament_stats);

			if (Constants.MPL.equalsIgnoreCase(session_configuration.getBroadcaster())) {

			    String category = session_configuration.getCategory();
			    if (category != null && !category.trim().isEmpty()) {
			        tournamentStats.removeIf(t ->t == null || t.getPlayer() == null ||
					        !CricketFunctions.genderMatches(category, t.getPlayer().getGender())
					);
			    }
			}

			Comparator<Tournament> comparator = SORT_MAP.get(whatToProcess);

			if (comparator != null) {
			    tournamentStats.sort(comparator);
			}

			return (List<T>) tournamentStats;
			
		case "Control_Alt_8":
			mvp_leaderBoard mvp = new mvp_leaderBoard();
			List<Player> mvp_player = new ArrayList<Player>();
 			
			if(new File(basePath + CricketUtil.MVP).exists()) {
				mvp = (objectMapper.readValue(new File(basePath + CricketUtil.MVP), mvp_leaderBoard.class));
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
	
		case "Control_z": case "Control_x": case "Alt_Shift_@": case "Control_Alt_6":

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
		            for(BestStats bs : tourn.getBatsman_best_Stats()) {
		            	top_ten_beststat.add(CricketFunctions.getProcessedBatsmanBestStats(bs));
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
	
	public void GetVariousDBData(String typeOfUpdate,Configuration config,HeadToHead headToHead)throws Exception {

	    if (!Set.of(Constants.ICC_U19_2023,Constants.ISPL,Constants.BENGAL_T20,Constants.NPL,Constants.LEGENDS,Constants.T20_MUMBAI,
	    		Constants.MPL,Constants.APL,Constants.VIDARBHA).contains(config.getBroadcaster())) {
	        return;
	    }

	    loadSessionData();
	    if (new File(basePath + "ParScores BB.html").exists() && session_match != null) {
	        session_dls = CricketFunctions.populateDuckWorthLewis(session_match);
	    }
	    switch (typeOfUpdate) {
	    case "ONLY_DB":
	        updateGraphicsData(config);
	        break;
	    case "NEW":
	        loadTournamentData(config, headToHead);
	        this_caption = new Caption(print_writers,config,session_statistics,cricketService.getAllStatsType(),session_name_super,  session_bugs,session_infoBarStats,session_fixture,session_team,session_ground,
	        		session_variousText,session_commentator, session_staff,session_players,session_pott,session_playoff,session_teamChanges,session_performance_bug,new FullFramesGfx(),new LowerThirdGfx(),
	        		new InfobarGfx(),new LofInfobarGfx(),new BugsAndMiniGfx(),1,"","-",past_tournament_stats,past_tape,session_dls,headToHead.getH2hPlayer(),past_tournament_stats,cricketService);

	        updateBoundaryData();
	        updateGraphicsData(config);
	        break;
	    case "UPDATE":
	        initializeMatchData(false, session_match, config, session_players, session_team, session_ground);
	        loadTournamentData(config, headToHead);
	        updateBoundaryData();
	        updateGraphicsData(config);
	        break;
	    }
	}

	private void loadSessionData() throws Exception {

	    session_statistics = cricketService.getAllStats();
	    session_performance_bug = cricketService.getPerformanceBugs();
	    session_name_super = cricketService.getNameSupers();
	    session_team = cricketService.getTeams();
	    session_ground = cricketService.getGrounds();
	    session_bugs = cricketService.getBugs();
	    session_infoBarStats = cricketService.getInfobarStats();
	    session_variousText = cricketService.getVariousTexts();
	    session_commentator = cricketService.getCommentator();
	    session_staff = cricketService.getStaff();
	    session_fixture = CricketFunctions.processAllFixtures(cricketService);
	    session_players = cricketService.getAllPlayer();
	    session_pott = cricketService.getPott();
	    session_playoff = cricketService.getPlayOff();
	}

	private void loadTournamentData(Configuration config, HeadToHead headToHead) throws Exception {

	    if (config.getBroadcaster().equalsIgnoreCase(Constants.ISPL)) {
	        past_tape = CricketFunctions.extractTapeData("PAST_MATCHES_DATA",cricketService,session_match,null,headToHead.getH2hPlayer());
	    }
	    past_tournament_stats = CricketFunctions.extractTournamentData("PAST_MATCHES_DATA",false,headToHead.getH2hPlayer(),cricketService,session_match,null);
	    past_tournament_boundaries = CricketFunctions.extracttournamentFoursAndSixesData("PAST_MATCHES_DATA",headToHead.getH2hPlayer(),session_match,null);
	}

	private void updateBoundaryData() {

	    String sixes = String.valueOf(past_tournament_boundaries.getTournament_sixes());
	    String fours = String.valueOf(past_tournament_boundaries.getTournament_fours());

	    this_caption.this_infobarGfx.previous_sixes = sixes;
	    this_caption.this_infobarGfx.previous_fours = fours;

	    this_caption.this_lofInfobarGfx.previous_sixes = sixes;
	    this_caption.this_lofInfobarGfx.previous_fours = fours;

	    this_caption.this_lofInfobarGfx.previous_nines = String.valueOf(past_tournament_boundaries.getTournament_nines());

	    this_caption.this_bugsAndMiniGfx.previous_sixes = sixes;
	    this_caption.this_bugsAndMiniGfx.previous_fours = fours;
	}

	private void updateGraphicsData(Configuration config) {

	    String whichInfobar = config.getWhichInfobar();

	    // =====================================================
	    // BUGS + MINI
	    // =====================================================

	    this_caption.this_bugsAndMiniGfx.bugs = session_bugs;
	    this_caption.this_bugsAndMiniGfx.Teams = session_team;
	    this_caption.this_bugsAndMiniGfx.VariousText = session_variousText;
	    this_caption.this_bugsAndMiniGfx.performanceBugs = session_performance_bug;
	    this_caption.this_bugsAndMiniGfx.statistics = session_statistics;
	    this_caption.this_bugsAndMiniGfx.statsTypes = cricketService.getAllStatsType();

	    // =====================================================
	    // INFOBAR
	    // =====================================================

	    if (whichInfobar.equalsIgnoreCase("LOF_INFOBAR")) {

	        this_caption.this_lofInfobarGfx.statistics = session_statistics;
	        this_caption.this_lofInfobarGfx.statsTypes = cricketService.getAllStatsType();
	        this_caption.this_lofInfobarGfx.infobarStats = session_infoBarStats;
	        this_caption.this_lofInfobarGfx.Grounds = session_ground;
	        this_caption.this_lofInfobarGfx.dls = session_dls;
	        this_caption.this_lofInfobarGfx.Commentators = session_commentator;
	        this_caption.this_lofInfobarGfx.Players = session_players;

	    } else {

	        this_caption.this_infobarGfx.statistics = session_statistics;
	        this_caption.this_infobarGfx.statsTypes = cricketService.getAllStatsType();
	        this_caption.this_infobarGfx.infobarStats = session_infoBarStats;
	        this_caption.this_infobarGfx.Grounds = session_ground;
	        this_caption.this_infobarGfx.dls = session_dls;
	        this_caption.this_infobarGfx.Commentators = session_commentator;
	        this_caption.this_infobarGfx.Players = session_players;
	        this_caption.this_infobarGfx.fixtures = session_fixture;
	        this_caption.this_infobarGfx.teams = session_team;
	    }

	    // =====================================================
	    // LOWER THIRD
	    // =====================================================

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

	    // =====================================================
	    // FULL FRAME
	    // =====================================================

	    this_caption.this_fullFramesGfx.statistics = session_statistics;
	    this_caption.this_fullFramesGfx.statsTypes = cricketService.getAllStatsType();
	    this_caption.this_fullFramesGfx.fixTures = session_fixture;
	    this_caption.this_fullFramesGfx.Teams = session_team;
	    this_caption.this_fullFramesGfx.Grounds = session_ground;
	    this_caption.this_fullFramesGfx.tournaments = past_tournament_stats;
	    this_caption.this_fullFramesGfx.VariousText = session_variousText;
	    this_caption.this_fullFramesGfx.Potts = session_pott;
	    this_caption.this_fullFramesGfx.Playoffs = session_playoff;
	}
	
	public static MatchAllData initializeMatchData(boolean withMatchStats, MatchAllData session_match,Configuration session_configuration, List<Player> session_players, 
			List<Team> session_team, List<Ground> session_ground) throws StreamWriteException, DatabindException, JAXBException, IOException, URISyntaxException {

	    if(withMatchStats) {
	    	MatchStats = CricketFunctions.getAllEvents(session_match,session_configuration.getBroadcaster(),session_match.getEventFile().getEvents());
	    }
	    
	    session_match = CricketFunctions.populateMatchVariables(CricketFunctions.readOrSaveMatchFile(CricketUtil.READ,CricketUtil.SETUP + "," + CricketUtil.MATCH 
	    			+ "," + CricketUtil.EVENT,session_match,session_configuration, basePath),session_players,session_team,session_ground);
	    session_match.getSetup().setMatchFileTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
	    session_match.getSetup().setGenerateInteractiveFile(session_configuration.getGenerateInteractiveFile());
	    CricketFunctions.getInteractive(session_match, "FULL_WRITE", basePath);

	    return session_match;
	}
	
	private <T> T loadFile(String path, Class<T> clazz) throws IOException {
	    File file = new File(path);
	    if (!file.exists()) {
	        return null;
	    }
	    return objectMapper.readValue(file, clazz);
	}

	private void loadScenes(String broadcaster)throws IOException, InterruptedException {
	    switch (broadcaster) {
	        case Constants.ISPL:
	            if (session_configuration.getPrimaryVariousOptions().contains(Constants.FULL_FRAMER)) {
	                this_scene.LoadScene("FULL-FRAMERS",print_writers,session_configuration);
	            }
	            if (session_configuration.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
	                this_scene.LoadScene("OVERLAYS", print_writers, session_configuration);
	            } else {
	                this_scene.LoadScene("TRADITIONAL_OVERLAYS", print_writers, session_configuration);
	            }
	            this_animation.ResetAnimation("CLEAR-ALL", print_writers, session_configuration);
	            break;
	        case Constants.T20_MUMBAI:
	            if (session_configuration.getPrimaryVariousOptions().contains(Constants.FULL_FRAMER)) {
	                this_scene.LoadScene("FULL-FRAMERS",print_writers,session_configuration);
	            }
	            if (session_match.getSetup() != null && session_match.getSetup().getMatchType()
						.equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
	                this_scene.LoadScene("OVERLAYS_SUPER_OVER", print_writers, session_configuration);
	            } else {
	                this_scene.LoadScene("OVERLAYS", print_writers, session_configuration);
	                this_animation.ResetAnimation("CLEAR-ALL",print_writers,session_configuration);
	                this_caption.this_infobarGfx.TournamentColor(print_writers, session_configuration);
	                this_caption.this_fullFramesGfx.FFTournamentColor(print_writers, session_configuration);
	            }
	            break;

	        case Constants.ICC_U19_2023: case Constants.BENGAL_T20: case Constants.NPL: case Constants.LEGENDS:
	        case Constants.MPL: case Constants.APL: case Constants.VIDARBHA:

	            if (session_configuration.getPrimaryVariousOptions().contains(Constants.FULL_FRAMER)) {
	                this_scene.LoadScene("FULL-FRAMERS", print_writers,session_configuration);
	            }
	            this_scene.LoadScene("OVERLAYS",print_writers,session_configuration);
	            this_animation.ResetAnimation("CLEAR-ALL",print_writers,session_configuration);
	            break;
	    }
	}
	
}