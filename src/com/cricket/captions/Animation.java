package com.cricket.captions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.cricket.containers.Infobar;
import com.cricket.containers.LowerThird;
import com.cricket.controller.IndexController;
import com.cricket.model.Configuration;
import com.cricket.model.MatchAllData;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;

public class Animation 
{
	public String whichGraphicOnScreen = "", specialBugOnScreen = "", status = "", whichPlayer = "", isComp = "",
			prevWhichPlayer = "",targetOnScreen = "",tapeballOnScreen = "", prevHighlightDirector = "0", prevLeaderHighlight = "0", 
			whichScorecard = "",audioenabled = "",bugs_pre = "",whichBowlingCard="";
	public Infobar infobar;
	public Caption caption;
	public int lastNumberOfRows=0,footercount=0,lineUpCount=0;
	public InfobarGfx this_infobarGfx;
	public LofInfobarGfx this_lofInfobarGfx;
	public FullFramesGfx this_fullFramesGfx;
	
	public boolean sponsorOnScreen = false;
	public boolean ExtraInfoOnScreen = false;
	public boolean MiddleSectionInfoOnScreen = false;
	public boolean bigScoreBug_On_Screen = false;
	public boolean LineUpBigImage_On_Screen = false;
	
	LowerThird LT = new LowerThird();
	
	public BugsAndMiniGfx this_bugs;
	public LowerThirdGfx this_lowerGfx;
	
	
	public Animation(Infobar infobar) {
		super();
		this.infobar = infobar;
	}

	public Animation() {
		super();
	}
	public void setPositionOfLowerThirds(Configuration config, List<PrintWriter> print_writers) 
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.ICC_U19_2023: 
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_NameSupers$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 40.0 \0",print_writers);
			}else if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_NameSupers$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 56.0 \0",print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_NameSupers$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 3.0 \0",print_writers);
			}
			break;	
		case Constants.ISPL:
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_NameSupers$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 40.0 \0",print_writers);
			}else if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_NameSupers$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 56.0 \0",print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_NameSupers$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 3.0 \0",print_writers);
			}
			break;
		}
	}
	
	public String getTypeOfGraphicsOnScreen(Configuration config,String whatToProcess)
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.T20_MUMBAI:
			switch (whatToProcess.split(",")[0]) {
			case "Alt_0": case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": case "Alt_9":
			case "Control_F12": case "Shift_F12":
				return Constants.INFO_BAR;
			case "F1": case "Control_Shift_F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Shift_T": case "Control_F7": case "m": case "Control_m": 
			case "Shift_F11": case "Shift_F10": case "p": case "Alt_Shift_J": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_F1": 
			case "Control_b": case "Alt_F9": case "Shift_F8": case "Control_F10": case "Control_Shift_F2": case "Shift_D": case "Alt_Shift_F10": case "Alt_Shift_F12": 
			case "Control_d": case "Control_e": case "Alt_Shift_F9": case "Alt_Shift_F2":
				return Constants.FULL_FRAMER;
			case "F5": case "F9": case "l": case "Shift_F5": case "Shift_F9": case "Control_h": case "Alt_F12": case "Control_Shift_M": 
			case "Control_Shift_O": case "Control_Shift_L": case "F7": case "F11": case "Control_a": case "q": case "u": case "Control_q":
			case "F8": case "Alt_F8": case "F10": case "j":case "Shift_F3": case "Control_F3": case "Shift_B": case "Control_F6": case "F6":
			case "Alt_Shift_F3":case "Shift_F6":case "Alt_Shift_O":case "Control_F5":case "Control_F9": case "d": case "e": case "Shift_I":
			case "Alt_F1": case "Alt_F2": case "Control_Shift_Q": case "Control_i": case "Control_shift_O":
				return Constants.LOWER_THIRD;
			case "Alt_p": case "r": case "h": case "Control_y": case "Control_k": case "Shift_F4": case "Shift_O": case "y": case "g": case "k":
			case "Shift_C": case "Control_Shift_F3": case "Control_Shift_R": case "Control_Shift_J":
			case "Alt_Shift_N":case "Alt_Shift_M": case "Alt_Shift_K": case "Alt_Shift_X": case "Alt_Shift_T": case "Alt_Shift_V":
			case "Control_Shift_U": case "Control_Shift_V": case "6": case "Control_4": case "Alt_Shift_E":
			case "Control_Shift_U_change_on": case "Control_Shift_V_change_on":
				return Constants.BUGS;
			case "Shift_F1": case "Shift_F2": case "Alt_F7":
				return Constants.MINIS;	
			}
			break;
		case Constants.LEGENDS:
			switch (whatToProcess.split(",")[0]) {
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": 
			case "Alt_9": case "Alt_0": case "Control_F12": case "Shift_F12":
				return Constants.INFO_BAR;
			case "F1": case "Control_Shift_A": case "Control_Shift_F1": case "F2": case "Control_Shift_F2": case "Control_F11": case "m": case "Control_m":
			case "Shift_F11": case "F4": case "Control_Shift_F4": case "Shift_K": case "Control_d": case "Control_e": case "Shift_T": case "Shift_P": case "Shift_Q":
			case "Alt_z": case "Shift_F8": case "highlightProfile": case "Control_F7": case "z": case "x": case "c": case "v": case "Control_p":
			case "Control_F10": case "Shift_F10": case "Control_Shift_D": case "Shift_D": case "Alt_F11": case "Control_z": case "Control_x": case "Control_Shift_Z":
			case "Control_Shift_Y": case "Alt_m": case "Alt_n": case "Control_Shift_E": case "Control_Shift_F": case "Alt_Shift_W": case "Control_Shift_I":
			case "Control_Shift_F8": case "Control_Shift_F7":case "Control_Shift_F5": case "Alt_Shift_F4":case "Control_Shift_K": 
			case "Alt_F5": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J":
				return Constants.FULL_FRAMER;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_Shift_R": case "Control_Shift_U": case "Control_Shift_V":
			case "h": case "Shift_F4": case "Shift_F":case "Alt_b": case "Alt_p": case "Control_Shift_F3":  case "Shift_C": case "Control_Shift_J": case "6":
			case "Control_y": case "Control_4":
				return Constants.BUGS;
			case "Shift_F1": case "Shift_F2": case "Alt_F7": case "Alt_F1": case "Alt_F2":case "Alt_Shift_F8": case "Alt_f":
				return Constants.MINIS;	
			
			case "F8": case "Control_Shift_B":
			case "Control_F5": case "Control_F9": case "Alt_F8": case "F5":case "F9": case "d": case "e": case "F7": case "F11": case "Control_h":
				
			case "F6": case "F10": case "Control_Shift_Q": case "Control_Shift_O": case "9": case "Alt_Shift_Q":
			case "Control_a":  case "Control_F3": case "Alt_k": case "Control_Shift_F10":
			case "Shift_F3": case "u":  case "q": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_g": case "j": case "Control_F6": case "Shift_F6": case "Alt_Shift_F3":
			case "Control_s": case "Alt_d": case "Control_f": case "Control_q": case "l": case "n": case "a": case "Control_F2":
			case "Alt_a": case "Alt_s":case "Shift_E": case "Alt_q": case "Alt_F6": case "Shift_A": case "Shift_R": case "Shift_U":
			case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": case "Alt_Shift_L": case "Shift_B": 
			case "Control_Shift_P": case "Control_Shift_M": case "Control_Shift_L":	case "Alt_Shift_F5":
			case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
			case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X": case "Shift_I":case "Alt_Shift_F7":case "Alt_Shift_F6":
				return Constants.LOWER_THIRD;	
			}
			break;
		case Constants.NPL: case Constants.MPL: case Constants.APL: case Constants.VIDARBHA:
			switch (whatToProcess.split(",")[0]) {
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": 
			case "Alt_9": case "Alt_0": case "Control_F12": case "Shift_F12": case "Control_Shift_(":
				return Constants.INFO_BAR;
			case "F1": case "Control_Shift_A": case "Control_Shift_F1": case "F2": case "Control_Shift_F2": case "Control_F11": case "m": case "Control_m":
			case "Shift_F11": case "F4": case "Control_Shift_F4": case "Shift_K": case "Control_d": case "Control_e": case "Shift_T": case "Shift_P": case "Shift_Q":
			case "Alt_z": case "Shift_F8": case "highlightProfile": case "Control_F7": case "z": case "x": case "c": case "v": case "Control_p":
			case "Control_F10": case "Shift_F10": case "Control_Shift_D": case "Shift_D": case "Alt_F11": case "Control_z": case "Control_x": case "Control_Shift_Z":
			case "Control_Shift_Y": case "Alt_m": case "Alt_n": case "Control_Shift_E": case "Control_Shift_F": case "Alt_Shift_W": case "Control_Shift_I":
			case "Control_Shift_F8": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_P": case "Control_Shift_K": case "Alt_Shift_Z": case "Shift_L":
			case "Shift_M": case "Control_Shift_P": case "Alt_Shift_R": case "Alt_F5": case "Alt_Shift_J":
				return Constants.FULL_FRAMER;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_Shift_R": case "Control_Shift_U": case "Control_Shift_V":
			case "h": case "Shift_F4": case "Shift_F":case "Alt_b": case "Alt_p": case "Control_Shift_F3":  case "Shift_C": case "Control_Shift_J": case "6":
			case "Control_y": case "Control_4":
				return Constants.BUGS;
			case "Shift_F1": case "Shift_F2": case "Alt_F7": case "Alt_F1": case "Alt_F2":  
				return Constants.MINIS;	
			
			case "F8": case "Control_Shift_B": 
			case "Control_F5": case "Control_F9": case "Alt_F8": case "F5":case "F9": case "d": case "e": case "F7": case "F11": case "Control_h":
			case "F6": case "F10": case "Control_Shift_Q": case "Control_Shift_O": case "9": case "Alt_Shift_Q":
			case "Control_a":  case "Control_F3": case "Alt_k": case "Control_Shift_F10":
			case "Shift_F3": case "u":  case "q": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_g": case "j": case "Control_F6": case "Shift_F6": case "Shift_I":
			case "Control_s": case "Alt_d": case "Control_f": case "Control_q": case "l": case "n": case "a": case "Control_F2":
			case "Alt_a": case "Alt_s":case "Shift_E": case "Alt_q": case "Alt_F6": case "Shift_A": case "Shift_R": case "Shift_U":
			case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": case "Alt_Shift_L": case "Shift_B": 
			case "Control_Shift_M": case "Control_Shift_L": case "Alt_Shift_O":  case "Alt_o": case "Shift_F7": case "Control_Shift_F9": 
			case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
			case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X": case "Control_6": case "Alt_Shift_F5": case "Alt_Shift_F3":
				return Constants.LOWER_THIRD;	
			}
			break;
		case Constants.ICC_U19_2023:
			switch (whatToProcess.split(",")[0]) {
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Shift_F10": case "Control_F11": case "Shift_F11": case "m": case "Control_m": 
			case "Shift_T": case "Control_d": case "Control_e": case "Control_F7": case "Control_F10":
			case "Shift_K": case "Alt_F9": case "Shift_D": case "p": case "Control_b": case "Alt_m": case "Alt_n":
			case "Alt_F10": case "Control_F1": case "Control_p": case "Shift_P": case "Shift_Q": 
			case "z": case "x": case "c": case "v": case "Alt_F11": case "Alt_z": case "Control_z": case "Control_x": case "r":
			case "Shift_Z": case "Shift_X":
				return Constants.FULL_FRAMER;
				
			case "F5": case "F6": case "F7": case "F8": case "F9": case "F10": case "F11": case "Alt_F8":
			case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k":
			case "Shift_F3": case "u": case "d": case "e": case "q": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_g": case "Control_h": case "j": case "Control_F6": case "Shift_F6": case "Alt_F1": case "Alt_F2":
			case "Control_s": case "Alt_d": case "Control_f": case "Control_q": case "l": case "n": case "a": case "Control_F2":
			case "Alt_a": case "Alt_s":case "Shift_E": case "Alt_q": case "Alt_F6": case "Shift_A": case "Shift_R": case "Shift_U":
			case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": case "Alt_Shift_L": case "Shift_B": 
			case "Control_Shift_F": case "Control_Shift_P": case "Control_Shift_M":
			case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
			case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
				switch (whatToProcess.split(",")[0]) {
				case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s": // Name super L3rd
					return Constants.NAME_SUPERS + Constants.LOWER_THIRD;
				case "q": case "Control_q": case "Alt_q": case "Shift_F7": // Boundary L3rd
					return Constants.BOUNDARIES + Constants.LOWER_THIRD;
				default:
					return Constants.LOWER_THIRD;
				}
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": 
			case "Alt_9": case "Alt_0": case "Control_F12":
				return Constants.INFO_BAR;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Alt_p": case "o": case "t": 
			case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b": case ".": case "/":
				return Constants.BUGS;
			case "Shift_F1": case "Shift_F2": case "Alt_F7": 
				return Constants.MINIS;
			}
			break;
		case Constants.BENGAL_T20:
			switch (whatToProcess.split(",")[0]) {
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Shift_F10": case "Control_F11": case "Shift_F11": case "m": case "Control_m": 
			case "Shift_T": case "Control_d": case "Control_e": case "Control_F7": case "Control_F10": case "9":
			case "Shift_K": case "Alt_F9": case "Shift_D": case "p": case "Control_b": case "Alt_m": case "Alt_n":
			case "Alt_F10": case "Control_F1": case "Control_p": case "Shift_P": case "Shift_Q": 
			case "z": case "x": case "c": case "v": case "Alt_F11": case "Alt_z": case "Control_z": case "Control_x": case "r":
			case "Shift_Z": case "Shift_X": case "Control_Shift_F1": case "Control_Shift_D": case "Alt_Shift_Z": case "Control_Shift_F7":
			case "Control_Shift_F2": case "Alt_Shift_R": case "Control_Shift_F4": case "Control_Shift_Z":
			case "Control_Shift_Y": case "Control_Shift_F8":
				return Constants.FULL_FRAMER;
				
			case "F5": case "F6": case "F7": case "F8": case "F9": case "F10": case "F11": case "Alt_F8":
			case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k": case "Control_Shift_F10":
			case "Shift_F3": case "u": case "d": case "e": case "q": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_g": case "Control_h": case "j": case "Control_F6": case "Shift_F6": case "Alt_Shift_Q":
			case "Control_s": case "Alt_d": case "Control_f": case "Control_q": case "l": case "n": case "a": case "Control_F2":
			case "Alt_a": case "Alt_s":case "Shift_E": case "Alt_q": case "Alt_F6": case "Shift_A": case "Shift_R": case "Shift_U":
			case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": case "Alt_Shift_L": case "Shift_B": 
			case "Control_Shift_P": case "Control_Shift_M":case "Shift_I": case "Alt_Shift_C": case "Control_Shift_B":
			case "Alt_Shift_F3":case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
			case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X": case "Control_Shift_L":
				switch (whatToProcess.split(",")[0]) {
				case "q": case "Control_q": case "Alt_q": case "Shift_F7": // Boundary L3rd
					return Constants.BOUNDARIES + Constants.LOWER_THIRD;
				default:
					return Constants.LOWER_THIRD;
				}
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": case "Alt_9": case "Alt_0":
			case "Control_F12": case "Shift_F12": case "Alt_e": case "Control_Shift_(":
				return Constants.INFO_BAR;
			case "Shift_O": case "Control_k": case "k": case "Shift_Y": case "g": case "y": case "Alt_p": case "o": case "t": case "Control_Shift_R": case "Shift_C":
			case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b": case ".": case "/": case "Control_Shift_U": case "Control_Shift_V": case "6":
			case "Control_4":	
				return Constants.BUGS;
			case "Shift_F1": case "Shift_F2": case "Alt_F7": case "Alt_F1": case "Alt_F2": case "Control_Shift_F": case "Control_Shift_E":
				return Constants.MINIS;
			}
			break;	
		case Constants.ISPL:
			switch (whatToProcess.split(",")[0]) {
			case "F1": case "F2": case "F4": case "Shift_F10": case "Shift_F11": case "m": case "Control_m": case "Shift_T": case "Shift_F8": case "Control_d": case "Control_e": 
			case "Control_F7": case "Control_F10": case "Shift_K": case "Shift_D": case "p": case "Alt_F10": case "Control_F1": case "Control_p": case "Shift_P": case "Shift_Q": 
			case "Control_F11": case "Control_Shift_F7": case "z": case "x": case "c": case "v": case "Alt_F11": case "Alt_z": case "Control_z": case "Control_x": 
			case "r": case "Control_c": case "Control_v": case "Shift_V": case "Control_Shift_Z": case "Control_Shift_Y": case "Alt_F5": case "Alt_Shift_F11":
			case "Alt_x": case "Control_Shift_F8":
				return Constants.FULL_FRAMER;
			case "F5": case "F6": case "F7": case "F8": case "F9": case "F10": case "F11": case "Alt_F8": case "Control_F5": case "Control_F9": 
			case "Control_a":  case "Control_F3": case "Alt_k": case "Alt_Shift_L": case "Shift_F3": case "u": case "d": case "e": case "q": 
			case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_g": case "Control_h": case "j": case "Control_F6": case "Shift_F6":
			case "Control_s": case "Alt_d": case "Control_f": case "Control_q": case "l": case "Control_Shift_F9": case "a": case "Control_F2":
			case "Alt_a": case "Alt_s":case "Shift_E": case "Alt_q": case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Shift_F7": 
			case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": case "Alt_Shift_D":case "Alt_Shift_E": case "Alt_Shift_F":
			case "Alt_Shift_G":case "Alt_Shift_H": case "Alt_Shift_O": case "Alt_Shift_B": case "Control_u": case "Shift_G": case "Shift_W":
			case "Control_Shift_X": case "Shift_I": case "Control_Shift_F10":
				switch (whatToProcess.split(",")[0]) {
				case "q": case "Control_q": case "Alt_q": case "Shift_F7": case "Control_Shift_F9":// Boundary L3rd
					return Constants.BOUNDARIES + Constants.LOWER_THIRD;
				default:
					return Constants.LOWER_THIRD;
				}
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": case "Alt_9": 
			case "Alt_0": case "Alt_c": case "Control_F8": case "Control_F12": case "Shift_F12": case "Alt_y": case "Control_6": case "Control_5":
			case "Alt_e": case "Control_7": case "Control_8": case "Alt_/": case "Control_9": case "Alt_F1": case "Alt_F2": case "Control_0":
			case "Shift_@": case "Shift_$": case "Control_Shift_@": case "Control_Alt_5": case "Alt_Shift_@": case "Control_Alt_6": case "Control_Alt_9": 
			case "Control_Alt_0": case "Control_Shift_(": case "Control_Alt_7": case "Control_4": case "6": case "Control_Alt_3": case "Control_Alt_8":
				return Constants.INFO_BAR;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Alt_p": case "o": case "t": case "Control_Shift_R": case "Control_Shift_F3":
			case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b": case ".": case "/": case "Control_Shift_F11": case "Shift_C":
				return Constants.BUGS;
			case "Shift_F1": case "Shift_F2": case "Alt_F7":
				return Constants.MINIS;
			}
			break;	
		}
		return "";
	}

	public String ISPL_AnimateIn(String whatToProcess, List<PrintWriter> print_writers, Configuration config) throws InterruptedException, IOException
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.ISPL:
			switch (whatToProcess.split(",")[0]) {
			case "Control_F12":
				
				if(this.infobar.isInfobar_on_screen() == true) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(500);
					
				}else {
					processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "SHOW 0.0");
				}
				
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Loop", "START");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				this.infobar.setInfobar_on_screen(true);
				break;
			case "F12": //Infobar
				
				if(this.infobar.isInfobar_on_screen()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(500);
				}
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Loop", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "START");
//				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$RightInfo_Bottom", "START");
				this.infobar.setInfobar_on_screen(true);
				this.infobar.setInfobar_pushed(false);
				this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				break;
			case "ArrowUp":
				if(this.infobar.isInfobar_on_screen() == true && this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Push", "CONTINUE");
					this.infobar.setInfobar_pushed(false);
				}
				break;
			case "ArrowDown":
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Push", "START");
					this.infobar.setInfobar_pushed(true);
					TimeUnit.MILLISECONDS.sleep(800);
				}
				break;
			case "ArrowLeft":
				if(this.infobar.isInfobar_on_screen() == true) {
					switch (whatToProcess.split(",")[0]) {
					case "ArrowLeft":
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "START");
							this.infobar.setInfobar_status(Constants.FORCED + Constants.SHRUNK_INFOBAR);
						}
						break;
					}
				}
				break;
			case "ArrowRight":
				if(this.infobar.isInfobar_on_screen() == true) {
					switch (whatToProcess.split(",")[0]) {
					case "ArrowRight":
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "CONTINUE");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						break;
					}
				}
				break;
			case "Alt_e":
				if(caption.this_infobarGfx.infobar.isPowerplay_on_screen() == false) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Powerplay", "START");
					caption.this_infobarGfx.infobar.setPowerplay_on_screen(true);
				}else {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Powerplay", "CONTINUE REVERSE");
					caption.this_infobarGfx.infobar.setPowerplay_on_screen(false);
				}
				break;
			case "w": case "i": case "f": case "s": case "0": case "8": case "Control_F8": case ";":
				System.out.println("config = " + caption.config.getWhichInfobar() + "   che = " + config.getWhichInfobar());
				if(whatToProcess.split(",")[0].equalsIgnoreCase("w")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("i")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("f")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("s")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("0")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 5 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase(";")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("8")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 6 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_F8")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 8\0", print_writers);
				}
//				else if(whatToProcess.split(",")[0].equalsIgnoreCase("9")) {
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 7 \0", print_writers);
//				}
				
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Wipes", "START");
				}
				break;
			case "m": case "Control_m":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Start_End", "START");
				processAnimation(Constants.BACK, print_writers, "anim_MatchId$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_D":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Start_End", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Target$In_Out$In", "START");
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_T": case "F1": case "Control_F10": case "F4": case "Shift_K": case "Control_F7": case "Control_F11": case "Shift_F11": 
			case "F2": case "Alt_F11": case "Shift_F10": case "Alt_F5": case "Control_Shift_F7":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				
				processAnimation(Constants.BACK, print_writers, "Start_End", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Wings", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$SubHeader", "START");
				this.whichScorecard = caption.this_fullFramesGfx.WhichScoreCard;
				switch (whatToProcess.split(",")[0]) {
				case "F1":
					if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("NORMAL")) {
						processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BatStyle2", "START");
					}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("SPLIT")) {
						processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$SplitBatBall_Card", "START");
					}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("TRADITIONAL")) {
						processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BattingCard_Normal", "START");
					}else {
						processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BatStyle3", "START");
					}
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BowlingCard_Normal", "START");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Partnership_List", "START");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$PlayingXI", "START");
					break;
				case "Control_Shift_F7":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$LineUp_Single", "START");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Manhattan", "START");
					break;
				case "Shift_K": 
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Manhattan", "START");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Both_Team", "START");
					break;
				case "Control_F11": case "Shift_F11":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Summary", "START");
					break;
				case "Alt_F11":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Doublemanhattan", "START");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Worm", "START");
					break;
				case "Alt_F5":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$DoubleTeamManhattan", "START");
					break;
				}
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Footer", "START");
				this.whichGraphicOnScreen = whatToProcess;
				caption.captionWhichGfx = whatToProcess.split(",")[0];
				caption.this_fullFramesGfx.whichGFX = whatToProcess.split(",")[0];
				break;	
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2":
			case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k":
			case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": 
			case "Alt_F8": case "F8": case "F10": case "a":	
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				processAnimation(Constants.FRONT, print_writers, "Anim_Lower_Third", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_F11":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.FRONT, print_writers, "Anim_DRS_Bug$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			}
			break;
		}
		return CricketUtil.YES;
	}
	
	public String AnimateIn(String whatToProcess, List<PrintWriter> print_writers, Configuration config) throws InterruptedException, IOException 
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.VIDARBHA:
			T20_VidarbhaAnimateIn(whatToProcess, print_writers, config);
			break;	
		case Constants.T20_MUMBAI:
			T20_MumbaiAnimateIn(whatToProcess, print_writers, config);
			break;
		case Constants.ISPL:
			ISPL_AnimateIn(whatToProcess, print_writers, config);
			break;
		case Constants.LEGENDS:
			switch (whatToProcess.split(",")[0]) {
			//FF
			case "Shift_D":
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Event_Logo", "START");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Target", "START");
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_F7":
				LineUpBigImage_On_Screen = true;
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				
				processAnimation(Constants.BACK, print_writers, "anim_Team_BigImage", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "9":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "START");
				this.whichGraphicOnScreen = whatToProcess;
				
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(true);
				
				break;
			case "Alt_Shift_Q":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "START");
				this.whichGraphicOnScreen = whatToProcess;
				
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(true);
				
				break;
			case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Event_Logo", "START");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Logo_FF", "START");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$PlayerProfile", "START");
				processAnimation(Constants.BACK, print_writers, "Sponsor$In_Out", "START");
				
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_(":case "Shift_*":
				switch (whatToProcess.split(",")[0]) {
				case "Shift_("://Six Animation
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp$GFX_GROUP$TextGrp$Select"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					break;
				case "Shift_*"://Four Animation
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp$GFX_GROUP$TextGrp$Select"
							+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
					break;
				}
				processAnimation(Constants.FRONT, print_writers, "Extra_PopUps", "START");
			break;
			case "m": case "Control_m": case "Shift_K": case "F4": case "Shift_T": case "F1": case "F2": case "Control_F7":
			case "Control_F11": case "Control_F10":case "Shift_F10": case "Control_p": case "Shift_F11":case "Control_Shift_F5":
			case "Control_Shift_D":case "Alt_Shift_F4":case "z": case "x": case "c": case "v":case "Control_z": case "Control_x": 
			case "Control_Shift_F8": case "Control_Shift_K": case "Control_Shift_Z": case "Control_Shift_Y": case "Alt_F5": 
			case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J":
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.LEGENDS:
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Essentials", "START");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Event_Logo", "START");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Logo_FF", "START");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Header", "START");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$SubHeader", "START");
					if(!whatToProcess.split(",")[0].matches("m|Control_m|Control_Shift_D|Alt_Shift_F4")) {
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
					}
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Footer", "START");
					processAnimation(Constants.BACK, print_writers, "Sponsor$In_Out", "START");
					
					switch (whatToProcess.split(",")[0]) {
					case "m": case "Control_m":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Match_Id", "START");
						break;
					case "z": case "x": case "c": case "v":case "Control_z": case "Control_x": case "Control_Shift_F8":
					case "Control_Shift_Z": case "Control_Shift_Y":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Leaderboard", "START");
						break;
					case "Alt_Shift_F4":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$TeamsAll", "START");
						break;
					case "Control_Shift_K":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$PlayOff", "START");
						break;
					case "F1":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$BattingCard", "START");
						break;
					case "F2":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$BowlingCard", "START");
						break;
					case "Control_Shift_D":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$DoubleMatchId", "START");
						break;
					case "F4":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$ParnershipList", "START");
						break;
					case "Shift_K":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Partnership", "START");
						break;
					case "Shift_T":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$TeamImage", "START");
						break;
					case "Control_F7":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Teams", "START");
						break;
					case "Control_F11": case "Shift_F11":case "Control_Shift_F5":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$MatchSummary", "START");
						break;
					case "Control_F10":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Manhattan", "START");
						break;
					case "Shift_F10":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Worm", "START");
						break;
					case "Alt_F5":
						 processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						 processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Manhattan_Phase_Compare", "START");
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Standings", "START");
						break;
					case "Control_Alt_F1":	
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Batting_Bowling_Card", "START");
						break;
					case "Control_Alt_F2":	
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Batting_Bowling_Manhattan", "START");
						break;
					case "Alt_Shift_J":	
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$BattingCard_Manhattan", "START");
						break;
					}
					break;
				}
				this.whichGraphicOnScreen = whatToProcess;
				break;
			//scoreBug and Ident
			case "Control_F12":
				if(this.infobar.isInfobar_on_screen() == true) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(500);
				}else {
					processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "SHOW 0.0");
				}
				
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Loop", "START");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				this.infobar.setInfobar_on_screen(true);
				break;
			case "F12": //Infobar
				processAnimation(Constants.FRONT, print_writers, "PopUps", "SHOW 0.0");
				if(this.infobar.isInfobar_on_screen()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(500);
				}
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Loop", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$RightInfo_Bottom", "START");
				this.infobar.setInfobar_on_screen(true);
				this.infobar.setInfobar_pushed(false);
				this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				break;
			case "ArrowUp":
				if(this.infobar.isInfobar_on_screen() == true && this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Push", "CONTINUE");
					this.infobar.setInfobar_pushed(false);
				}
				break;
			case "ArrowDown":
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Push", "START");
					this.infobar.setInfobar_pushed(true);
					TimeUnit.MILLISECONDS.sleep(800);
				}
				break;
			case "ArrowLeft":
				if(this.infobar.isInfobar_on_screen() == true) {
					switch (whatToProcess.split(",")[0]) {
					case "ArrowLeft":
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "START");
							this.infobar.setInfobar_status(Constants.FORCED + Constants.SHRUNK_INFOBAR);
						}
						break;
					}
				}
				break;
			case "ArrowRight":
				if(this.infobar.isInfobar_on_screen() == true) {
					switch (whatToProcess.split(",")[0]) {
					case "ArrowRight":
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "CONTINUE");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						break;
					}
				}
				break;
			case "Alt_e":
				if(caption.this_infobarGfx.infobar.isPowerplay_on_screen() == false) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Powerplay", "START");
					caption.this_infobarGfx.infobar.setPowerplay_on_screen(true);
				}else {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Powerplay", "CONTINUE REVERSE");
					caption.this_infobarGfx.infobar.setPowerplay_on_screen(false);
				}
				break;
				
			case "5": case ";": case "Shift_)": case "Control_Shift_*":
				if(whatToProcess.split(",")[0].equalsIgnoreCase("5")) {
					//CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp_New$Select*FUNCTION*Omo*vis_con SET 3\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp$TextGrp$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase(";")) {
					//CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp_New$Select*FUNCTION*Omo*vis_con SET 4\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp$TextGrp$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("Shift_)")) {
					//CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp_New$Select*FUNCTION*Omo*vis_con SET 5\0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_*")) {
					//CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp_New$Select*FUNCTION*Omo*vis_con SET 6\0", print_writers);
				}
				
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					//processAnimation(Constants.FRONT, print_writers, "Extra_PopUp_New", "START");
					processAnimation(Constants.FRONT, print_writers, "Extra_PopUps", "START");
				}
				break;
				
			case "w": case "i": case "f": case "s": case "0": case "8": case "Control_F8":
				if(whatToProcess.split(",")[0].equalsIgnoreCase("w")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("i")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("f")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("s")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("0")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 5 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("8")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 6 \0", print_writers);
				}
				
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Wipes", "START");
				}
				break;
				
			//MINI's
			case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":case "Alt_Shift_F8": case "Alt_f":
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			//Bug's
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b": case "Alt_p":
			case "Control_Shift_R": case "Control_Shift_F3":  case "Shift_C": case "Control_Shift_J":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.FRONT, print_writers, "Bug_In", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			
			//Pop-Up	
			case "6": case "Control_4":
				processAnimation(Constants.FRONT, print_writers, "PopUps$InOut", "START");
				TimeUnit.MILLISECONDS.sleep(400);
				switch (whatToProcess.split(",")[0]) {
				case "6":
//					processAnimation(Constants.FRONT, print_writers, "Sponsor", "START");
					break;
				}
				TimeUnit.MILLISECONDS.sleep(1300);
				this.whichGraphicOnScreen = whatToProcess;
				if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[0].
						equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[0])) {
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Hundreds", "START");
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Tens", "START");
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Units", "START");
				}
				else if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[1].
						equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[1])) {
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Tens", "START");
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Units", "START");
				}
				else if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[2].
						equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[2])) {
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Units", "START");
				}
				break;
			case "Control_Shift_U": case "Control_Shift_V":
				processAnimation(Constants.FRONT, print_writers, "PopUps$InOut", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			//LT
			case "Control_Shift_M": case "Control_Shift_L":
				AnimateIn("ArrowDown,", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(500);
				
				processAnimation(Constants.FRONT, print_writers, "anim_LT_Ident", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Shift_I":
				if(whichGraphicOnScreen.equalsIgnoreCase("Shift_I")) {
					processAnimation(Constants.FRONT, print_writers, "anim_ImpactLt$Change", "START");
					this.whichGraphicOnScreen = whatToProcess;
				}else {
					if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
						AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
						infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
					}
					processAnimation(Constants.FRONT, print_writers, "anim_ImpactLt$InOut", "START");
					this.whichGraphicOnScreen = whatToProcess.split(",")[0];
				}
				break;
				
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k": 
			case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": 
			case "Control_f":  case "Shift_E": case "Alt_F8": case "F8": case "F10": case "a":case "Alt_Shift_F5":case "Alt_Shift_F7":case "Alt_Shift_F6": case "Alt_d": case "Alt_Shift_F3":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut", "START");
				processAnimation(Constants.FRONT, print_writers, "Lt_Position", "START");
				this.whichGraphicOnScreen = whatToProcess;
				
				TimeUnit.MILLISECONDS.sleep(1200);
				processAnimation(Constants.FRONT, print_writers, "Lt_Position", "SHOW 0.0");
				break;
			}
			break;
		case Constants.NPL: case Constants.MPL: case Constants.APL:
			switch (whatToProcess.split(",")[0]) {
			case "Control_F12":
				if(this.infobar.isInfobar_on_screen() == true) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(500);
				}else {
					processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "SHOW 0.0");
				}
				
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Loop", "START");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				this.infobar.setInfobar_on_screen(true);
				break;
			case "F12": //Infobar
				
				if(this.infobar.isInfobar_on_screen()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(500);
				}
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Loop", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$RightInfo_Bottom", "START");
				switch (config.getBroadcaster()) {
				case Constants.NPL:  case Constants.APL: 
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Impact_Bat", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Impact_Bowl", "START");
					break;
				}
				
				
				this.infobar.setInfobar_on_screen(true);
				this.infobar.setInfobar_pushed(false);
				this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				break;
			case "ArrowUp":
				if(this.infobar.isInfobar_on_screen() == true && this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Push", "CONTINUE");
					this.infobar.setInfobar_pushed(false);
				}
				break;
			case "ArrowDown":
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Push", "START");
					this.infobar.setInfobar_pushed(true);
					TimeUnit.MILLISECONDS.sleep(800);
				}
				break;
			case "ArrowLeft":
				if(this.infobar.isInfobar_on_screen() == true) {
					switch (whatToProcess.split(",")[0]) {
					case "ArrowLeft":
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "START");
							this.infobar.setInfobar_status(Constants.FORCED + Constants.SHRUNK_INFOBAR);
						}
						break;
					}
				}
				break;
			case "ArrowRight":
				if(this.infobar.isInfobar_on_screen() == true) {
					switch (whatToProcess.split(",")[0]) {
					case "ArrowRight":
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "CONTINUE");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						break;
					}
				}
				break;
			case "Alt_e":
				if(caption.this_infobarGfx.infobar.isPowerplay_on_screen() == false) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Powerplay", "START");
					caption.this_infobarGfx.infobar.setPowerplay_on_screen(true);
					caption.this_infobarGfx.infobar.setForced_powerplay_out(false);
				}else {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Powerplay", "CONTINUE REVERSE");
					caption.this_infobarGfx.infobar.setPowerplay_on_screen(false);
					caption.this_infobarGfx.infobar.setForced_powerplay_out(true);
				}
				break;
				
			case "5": case ";": case "Shift_)": case "Control_Shift_*":
				if(whatToProcess.split(",")[0].equalsIgnoreCase("5")) {
					//CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp_New$Select*FUNCTION*Omo*vis_con SET 3\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp$TextGrp$Select*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase(";")) {
					//CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp_New$Select*FUNCTION*Omo*vis_con SET 4\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp$TextGrp$Select*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("Shift_)")) {
					//CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp_New$Select*FUNCTION*Omo*vis_con SET 5\0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_*")) {
					//CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Extra_PopUp_New$Select*FUNCTION*Omo*vis_con SET 6\0", print_writers);
				}
				
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					//processAnimation(Constants.FRONT, print_writers, "Extra_PopUp_New", "START");
					processAnimation(Constants.FRONT, print_writers, "Extra_PopUps", "START");
				}
				break;
				
			case "w": case "i": case "f": case "s": case "0": case "8": case "Control_F8":
				System.out.println("config = " + caption.config.getWhichInfobar() + "   che = " + config.getWhichInfobar());
				if(whatToProcess.split(",")[0].equalsIgnoreCase("w")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("i")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("f")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("s")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("0")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 5 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("8")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 6 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_F8")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Wipes$Select*FUNCTION*Omo*vis_con SET 8\0", print_writers);
				}
				
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Wipes", "START");
				}
				break;
			case "Alt_m": case "Alt_n":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_Milestone", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			 case "Shift_L":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "START");
				//processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "START");
				//processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "START");
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Row_Col", "START");
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_K":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Tree", "START");
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Alt_Shift_R":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$TeamSchedule", "START");

				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Control_Shift_P":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Row_Col", "START");

				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_M":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Leader_Board", "START");
				
				if(caption.this_fullFramesGfx.highlightplayer > 0) {
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + caption.this_fullFramesGfx.highlightplayer, "START");
					prevLeaderHighlight = String.valueOf(caption.this_fullFramesGfx.highlightplayer);
				}
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_Shift_W":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "START");
				
//				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "START");
//				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "START");
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Leader_Board", "START");
				
				processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player1", "START");
				
				this.whichGraphicOnScreen = whatToProcess;
				break;	
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
			case "Control_Shift_F8":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "START");
				
				switch(whatToProcess.split(",")[0]) {
				case "Control_Shift_F8": case "z": case "x":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "START");
					break;
				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Leader_Board", "START");
				
				if(Integer.valueOf(whatToProcess.split(",")[2].split("_")[0]) > 0) {
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + whatToProcess.split(",")[2].split("_")[0], "START");
					prevLeaderHighlight = whatToProcess.split(",")[2].split("_")[0];
				}
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_D":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_Target$In_Out", "START");
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				

				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_Shift_P":	
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Feild_Dimensions", "START");
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				

				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_Shift_Z":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "START");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Tournament_Teams", "START");
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "F1": case "Control_Shift_A": case "Control_Shift_F1": case "F2": case "Control_Shift_F2": case "Control_F11": case "F4": case "Shift_K": case "Control_Shift_F4": 
			case "Shift_F11": case "Control_d": case "Control_e": case "Shift_T": case "Shift_P": case "Shift_Q": case "Alt_z": case "Shift_F8": case "Control_F7": case "Control_p": 
			case "Control_F10": case "Shift_F10": case "Control_Shift_D": case "Alt_F11": case "Control_Shift_E": case "Control_Shift_F": case "Control_Shift_I": case "Alt_Shift_J": 
			case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_F5":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.MPL: case Constants.APL:
					
					if(audioenabled.equalsIgnoreCase("TRUE")) {
						processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
					}
					
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "START");
					
					if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_F10") && !whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F11") && 
					   !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_E") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F") && 
					   !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_I") && !whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F5")) {
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Footer", "START");
					}
					if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_d") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_e")
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Shift_P") && !whatToProcess.split(",")[0].equalsIgnoreCase("Shift_Q")
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_F7") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_D") 
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_p") && !whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F11")
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Shift_F10") && !whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F5")
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_E") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F")) {
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "START");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "START");
					}
					switch (whatToProcess.split(",")[0]) {
					case "Control_Alt_F1":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Batting_Bowling_Card", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Control_Alt_F2":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Batting_Bowling_Manhattan", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "F1": case "Control_Shift_A": case "Control_Shift_F1":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$BattingCard", "START");
						break;
					case "F2": case "Control_Shift_F2":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$BowlingCard", "START");
						break;
					case "Control_F11": case "Shift_F11": 
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Summary", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "F4": case "Control_Shift_F4":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Partnership_List", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Shift_K":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Partnership", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Alt_Shift_J":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$BattingCard_Manhattan", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;	
					case "Control_F7":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Teams", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;	
					case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Profile", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						if(Integer.valueOf(whatToProcess.split(",")[4]) > 0) {
							if(Integer.valueOf(whatToProcess.split(",")[4]) == 5) {
								processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$7", "START");
								prevHighlightDirector ="7";
							}else {
								processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$"+whatToProcess.split(",")[4], "START");
								prevHighlightDirector =whatToProcess.split(",")[4];
							}
							
						}
						break;
					case "Shift_T":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$LineUp_Image", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Alt_z":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Squad", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Shift_F8":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$TeamSingle", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Standings", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Control_F10":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Manhattan", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Shift_F10":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Worms", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Control_Shift_D":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Double_MatchId", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Alt_F11":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Manhattan_Comparison", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Alt_F5":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Manhattan_Phase_Compare", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Control_Shift_E": case "Control_Shift_F":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Player_V_Player", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					case "Control_Shift_I":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Innings_Story", "START");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						break;
					}
					break;
				}
				
				this.whichGraphicOnScreen = whatToProcess;
				caption.captionWhichGfx = whatToProcess.split(",")[0];
				caption.this_fullFramesGfx.whichGFX = whatToProcess.split(",")[0];
				break;	
			case "m": case "Control_m":
				processAnimation(Constants.BACK, print_writers, "Loop", "START");

				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.MPL: case Constants.APL:
					processAnimation(Constants.BACK, print_writers, "Anim_Ident", "START");
					break;
				default:
					processAnimation(Constants.BACK, print_writers, "Start_End", "START");
					processAnimation(Constants.BACK, print_writers, "anim_MatchId$In_Out", "START");
					break;
				}
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b": case "Alt_p":
			case "Control_Shift_R": case "Control_Shift_F3":  case "Shift_C": case "Control_Shift_J":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.FRONT, print_writers, "Bug_In", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_O":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Alt_Shift_F3":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "LT_PhaseComparison$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Control_Shift_B":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "LT_NextToBat$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "9":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "START");
				this.whichGraphicOnScreen = whatToProcess;
				
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(true);
				
				break;
			case "Alt_Shift_Q":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "START");
				this.whichGraphicOnScreen = whatToProcess;
				
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(true);
				
				break;
			case "6": case "Control_4":
				processAnimation(Constants.FRONT, print_writers, "PopUps$InOut", "START");
				TimeUnit.MILLISECONDS.sleep(1700);
				this.whichGraphicOnScreen = whatToProcess;
				if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[0].
						equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[0])) {
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Hundreds", "START");
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Tens", "START");
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Units", "START");
				}
				else if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[1].
						equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[1])) {
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Tens", "START");
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Units", "START");
				}
				else if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[2].
						equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[2])) {
					processAnimation(Constants.FRONT, print_writers, "PopUps$Change_Sixes$Units", "START");
				}
				break;
				
			case "Control_6":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "LT_Weather", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_Shift_F5":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "LT_Pointers", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Shift_I":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$BASE", "START");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$LOGO", "START");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$HEADER", "START");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$RIGHT_DATA", "START");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$BOTTOM_DATA", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Control_F5": case "F8": case "Alt_F8": case "Control_F9": case "F5": case "Control_a": case "Control_h": case "Shift_F3": case "F10": 
			case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_Shift_Q": case "F9":  case "d": case "e": case "F6": case "Control_F6": 
			case "Shift_F6": case "F7": case "F11": case "Control_s": case "Control_f": case "Alt_Shift_O":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$BASE", "START");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$LOGO", "START");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$HEADER", "START");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$BOTTOM_DATA", "START");
				
				switch (whatToProcess.split(",")[0]) {
				case "Control_F5": case "Control_F6": case "Shift_F6": case "F6": case "Control_F9": case "F5": case "F9":
				case "Control_a": case "Shift_F3": case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h":
				case "F7": case "F11": case "Control_s": case "Control_f": case "Control_Shift_Q": case "Alt_Shift_O":
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$RIGHT_DATA", "START");
					break;
				}
				
				switch (whatToProcess.split(",")[0]) {
				case "Shift_F3": case "Control_a": case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "F5": case "F7": case "F11":
				case "Control_s": case "Control_f": case "Control_h": case "Alt_Shift_O":
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$SUB_DATA", "START");
					break;	
				}
				
				if(whatToProcess.split(",")[0].equalsIgnoreCase("F10") && caption.this_lowerThirdGfx.setPriceMoney) {
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$Prize_Head", "START");
					caption.this_lowerThirdGfx.setPriceMoney = true;
				}
				
				if(caption.this_lowerThirdGfx.isImpact() == true) {
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$In$IMPACT", "START");
					caption.this_lowerThirdGfx.setImpact(true);
					caption.this_lowerThirdGfx.setPrev_impact(true);
				}
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_M": case "Control_Shift_L":
				AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "LT_MatchID$In_Out", "START");
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_F3":
				
				if(this.whichGraphicOnScreen.equalsIgnoreCase("Control_F3")) {
					processAnimation(Constants.FRONT, print_writers, "LT_Comparison$In_Out", "CONTINUE");
					this.isComp = "YES";
				}else {
					AnimateIn("ArrowLeft"+ ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					
					if(audioenabled.equalsIgnoreCase("TRUE")) {
						processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
					}
					
					processAnimation(Constants.FRONT, print_writers, "LT_Comparison$In_Out", "START");
					this.whichGraphicOnScreen = whatToProcess;
					this.isComp = "NO";
				}
				break;
			case "Control_Shift_U": case "Control_Shift_V":
				processAnimation(Constants.FRONT, print_writers, "PopUps$InOut", "START");
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_F10":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "LT_Manhattan$In", "START");
				
				this.whichGraphicOnScreen = whatToProcess;
				break;	
			}
			break;
		case Constants.ICC_U19_2023:

			//Full framers
			switch (whatToProcess.split(",")[0]) {
			case "Alt_z":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_Squad", "START");
				if(!caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("role")) {
					processAnimation(Constants.BACK, print_writers, "Anim_SquadDataChange", "START");
				}
				processAnimation(Constants.BACK, print_writers, "SquadFlare_Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_D":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Target", "START");
				processAnimation(Constants.BACK, print_writers, "TargetLoop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_b":
				//AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Push infobar
				//TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "In_At", "START");
				processAnimation(Constants.BACK, print_writers, "In_At_Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_m": case "Alt_n":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Milestone", "START");
				processAnimation(Constants.BACK, print_writers, "MilestoneLoop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "r":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_POTT", "START");
				processAnimation(Constants.BACK, print_writers, "POTT_Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F1": case "Shift_F10": case "Control_F11": case "Shift_F11": case "m": case "Control_m": 
			case "Shift_T": case "Control_d": case "Control_e": case "Control_F7": case "Control_F10": case "Shift_K": case "Alt_F9": case "Alt_F10": case "p": case "z": 
			case "x": case "c": case "v": case "Control_p": case "Shift_P": case "Shift_Q": case "Alt_F11": case "Control_z": case "Control_x": case "Shift_Z": case "Shift_X":
				
				setVariousAnimationsKeys("ANIMATE-IN", print_writers, config);
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$Flare_Loop", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Footer", "START");
				
				switch (whatToProcess.split(",")[0]) {
				case "F1": case "Control_Shift_A":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "START");
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "START");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "START");
					break;
				case "Control_F1":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card_Image", "START");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Worm", "START");
					break;
				case "Control_F11": case "Shift_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "START");
					break;
				case "m":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Ident", "START");
					break;
				case "Control_m":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Ident", "START");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LineUp_Image", "START");
					break;
				case "Control_d": case "Shift_P": case "Control_e": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Profile", "START");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Teams", "START");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Manhattan", "START");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership", "START");
					break;
				case "Alt_F9": case "Alt_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Team_Single", "START");
					break;
				case "p": 
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "START");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Standings", "START");
					break;
				case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Shift_Z": case "Shift_X":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Leader_Board", "START");
					break;
				case "Alt_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Manhattan_Comparison", "START");
					break;
				}
				
				switch (whatToProcess.split(",")[0]) {
				case "m": case "Control_m": case "Control_F11": case "Shift_F11": case "Control_F7": 
				case "p": case "z": case "x": case "c": case "v": case "Control_p": case "Alt_F11": case "Control_z": case "Control_x":
					processAnimation(Constants.BACK, print_writers, "Header_Shrink", "START");
					break;
				case "Shift_K": case "F4":
					TimeUnit.MILLISECONDS.sleep(1500);
					processAnimation(Constants.BACK, print_writers, "Sponsor", "START");
					break;
				case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
					if(Integer.valueOf(whatToProcess.split(",")[4]) > 0) {
						if((Integer.valueOf(whatToProcess.split(",")[4])  == 5)){
							processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$7", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$"+whatToProcess.split(",")[4], "START");
						}
					}
					break;
				}
				
				switch (whatToProcess.split(",")[0]) {
				 case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
					 processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player"+whatToProcess.split(",")[2].split("_")[0], "START");
					 this.prevWhichPlayer = whatToProcess.split(",")[2].split("_")[0];
					break;
				}
				
				switch (whatToProcess.split(",")[0]) {
				case "m": case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Base_Gradient", "START");
					break;
				}
				this.whichGraphicOnScreen = whatToProcess;
				lastNumberOfRows = caption.this_fullFramesGfx.numberOfRows;
				break;
			
			//NameSuperDB, HOWOUT, LTBatProfile, NameSuperPlayer, LtBallProfile, BatThisMatch, BallThisMatch
			case "F7": case "F11":
				if(this.infobar.isInfobar_on_screen() == true) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_LowerThirds$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 40.0 \0",print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_LowerThirds$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 3.0 \0",print_writers);
				}
				
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "F5": case "F6": case "F9": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k": case "Shift_F3": case "u": 
			case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_g": case "Control_h": case "Control_F6": case "Shift_F6": case "Control_s": case "Shift_E": 
			case "Alt_d": case "Control_f": case "l": case "n": case "a":  case "Alt_F1": case "Alt_F2": case "Alt_F6": case "Alt_Shift_L": case "Shift_A":  case "Shift_R": case "Shift_U": 
			case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": case "Shift_B": case "Control_Shift_F": case "Control_Shift_P": case "Alt_Shift_D": 
			case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H": case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":	
				if(this.infobar.isInfobar_on_screen() == true) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_LowerThirds$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 40.0 \0",print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_LowerThirds$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 3.0 \0",print_writers);
				}
				
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "START");
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;	
			case "Alt_q":
				if(this.infobar.isInfobar_on_screen() == true) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_POTT_Aramco$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 40.0 \0",print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_POTT_Aramco$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 3.0 \0",print_writers);
				}
				
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_POTT", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s": // Name super L3rd
				 setPositionOfLowerThirds(config, print_writers);
				AnimateIn(Constants.MIDDLE + Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_NameSupers", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "q": case "Control_q":// Boundary L3rd
				
				if(this.infobar.isInfobar_on_screen() == true) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BoundaryLowerthird$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 89.0 \0",print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BoundaryLowerthird$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 55.0 \0",print_writers);
				}
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Boundary_LT", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Alt_p":
				processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "START");
				this.specialBugOnScreen = CricketUtil.TOSS;
				break;
			case "o": case "t":
				processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b":
				processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$Essentials", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_F1": case "Shift_F2":
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			
			case "F12": //Infobar
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "START");
				this.infobar.setInfobar_on_screen(true);
				this.infobar.setInfobar_pushed(false);
				this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				break;
			case "ArrowUp":
				if(this.infobar.isInfobar_on_screen() == true && this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Push", "CONTINUE");
					this.infobar.setInfobar_pushed(false);
				}
				break;
			case "ArrowDown":
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Push", "START");
					this.infobar.setInfobar_pushed(true);
					TimeUnit.MILLISECONDS.sleep(800);
				}
				break;
			
			case "w": case "i": case "f": case "s":
				if(whatToProcess.split(",")[0].equalsIgnoreCase("w")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Right$Wipes$Select*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("i")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Right$Wipes$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("f")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Right$Wipes$Select*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("s")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Right$Wipes$Select*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
				}
				
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Wipes", "START");
				}
				break;
			case "Alt_f": case "Alt_g": case Constants.SHRUNK_INFOBAR: case Constants.MIDDLE + Constants.SHRUNK_INFOBAR:
				if(this.infobar.isInfobar_on_screen() == true) {
					switch (whatToProcess.split(",")[0]) {
					case "Alt_f":
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
								+ "ANIMATION*KEY*$Shrink_In*VALUE SET 120.0 -436.0 0.0\0",print_writers);
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "START");
							this.infobar.setInfobar_status(Constants.FORCED + Constants.SHRUNK_INFOBAR);
						} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "CONTINUE REVERSE");
							this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						}
						break;
					case "Alt_g":
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
								+ "ANIMATION*KEY*$Shrink_In*VALUE SET 183.0 -436.0 0.0\0",print_writers);
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "START");
							this.infobar.setInfobar_status(Constants.FORCED + Constants.MIDDLE + Constants.SHRUNK_INFOBAR);
						} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.MIDDLE + Constants.SHRUNK_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "CONTINUE REVERSE");
							this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						}
						break;
					case Constants.SHRUNK_INFOBAR:
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
								+ "ANIMATION*KEY*$Shrink_In*VALUE SET 120.0 -436.0 0.0\0",print_writers);
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "START");
							this.infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
						} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.SHRUNK_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "CONTINUE REVERSE");
							this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						}
						break;
					case Constants.MIDDLE + Constants.SHRUNK_INFOBAR:
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
								+ "ANIMATION*KEY*$Shrink_In*VALUE SET 183.0 -436.0 0.0\0",print_writers);
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "START");
							this.infobar.setInfobar_status(Constants.MIDDLE + Constants.SHRUNK_INFOBAR);
						} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.MIDDLE + Constants.SHRUNK_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small", "CONTINUE REVERSE");
							this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						}
						break;
					}
				}
				break;
			}
			break;
		case Constants.BENGAL_T20:

			//Full framers
			switch (whatToProcess.split(",")[0]) {
			case "Control_Shift_F10":
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Manhattan", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_z":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_Squad", "START");
				if(!caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("role")) {
					processAnimation(Constants.BACK, print_writers, "Anim_SquadDataChange", "START");
				}
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_D":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_Target", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_b":
				//AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Push infobar
				//TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "In_At", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_m": case "Alt_n":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Milestone", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "r":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_POTT", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "m": case "Control_m":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_Ident", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_Shift_Z":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_Teams", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_D":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_DoubleIdent", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_F7":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_Lineup_Image_Big", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_Shift_Q":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "START");
				this.whichGraphicOnScreen = whatToProcess;
				
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(true);
				
				break;
			case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				processAnimation(Constants.BACK, print_writers, "anim_Profile$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Profile$Main", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Alt_Shift_R":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Team_Fixtures", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F1": case "Shift_F10": case "Control_F11": case "Shift_F11": 
			case "Shift_T": case "Control_F7": case "Control_F10": case "Shift_K": case "Alt_F9": case "Alt_F10": case "p": case "z": case "x": 
			case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8": 
			case "Control_p": case "Alt_F11": case "Shift_Z": case "Shift_X": case "Control_Shift_F1": case "Control_Shift_F2": case "Control_Shift_F4":
				
				setVariousAnimationsKeys("ANIMATE-IN", print_writers, config);
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
				
				if(!whatToProcess.split(",")[0].equalsIgnoreCase("Shift_K") && !whatToProcess.split(",")[0].equalsIgnoreCase("z") &&
						!whatToProcess.split(",")[0].equalsIgnoreCase("x") && !whatToProcess.split(",")[0].equalsIgnoreCase("c") &&
						!whatToProcess.split(",")[0].equalsIgnoreCase("v") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_z") &&
						!whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_Z") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_x")
						&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_Y") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F8")) {
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Footer", "START");
				}
				
				switch (whatToProcess.split(",")[0]) {
				case "F1": case "Control_Shift_A":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
					break;
				case "Control_Shift_F1":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Batting_Card", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
					TimeUnit.MILLISECONDS.sleep(800);
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "START");
					if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
						TimeUnit.MILLISECONDS.sleep(800);
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side1$" + caption.this_fullFramesGfx.batperformer_id, "START");
						caption.this_fullFramesGfx.pervious_batperformer_id = caption.this_fullFramesGfx.batperformer_id;
					}
					else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
						
					}
					break;
				
				case "F2":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
					break;
				case "Control_Shift_F2":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Bowling_Card", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
					TimeUnit.MILLISECONDS.sleep(800);
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "START");
					if(caption.this_fullFramesGfx.ballperformer_id > 0) {
						TimeUnit.MILLISECONDS.sleep(800);
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side1$" + caption.this_fullFramesGfx.ballperformer_id, "START");
						caption.this_fullFramesGfx.pervious_ballperformer_id = caption.this_fullFramesGfx.ballperformer_id;
					}
					break;
				case "Control_Shift_F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "START");
					TimeUnit.MILLISECONDS.sleep(800);
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "START");
					if(caption.this_fullFramesGfx.ballperformer_id > 0) {
						TimeUnit.MILLISECONDS.sleep(800);
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Partnership_List$Side1$" + caption.this_fullFramesGfx.ballperformer_id, "START");
						caption.this_fullFramesGfx.pervious_ballperformer_id = caption.this_fullFramesGfx.ballperformer_id;
					}
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "START");
					break;
				case "Control_F1":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card_Image", "START");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Worms", "START");
					processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "START");
					break;
				case "Control_F11": case "Shift_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "START");
					switch (whatToProcess.split(",")[0]) {
					case "Control_F11":
						if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("trophy")) {
							processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "START");
						}
						break;
					}
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LineUp_Image", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Teams", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "START");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Manhattan", "START");
					processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership", "START");
					break;
				case "Alt_F9": case "Alt_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Team_Single", "START");
					break;
				case "p": 
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "START");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Standings", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "START");
					processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "START");
					break;
				case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
				case "Control_Shift_F8": case "Shift_Z": case "Shift_X":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Leaderboard", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "START");
					break;
				case "Alt_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Manhattan_Comparison", "START");
					break;
				}
				
				switch (whatToProcess.split(",")[0]) {
				case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Control_p":
					 processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "START");
					break;
				}
				
				this.whichGraphicOnScreen = whatToProcess;
				lastNumberOfRows = caption.this_fullFramesGfx.numberOfRows;
				break;
			
			//NameSuperDB, HOWOUT, LTBatProfile, NameSuperPlayer, LtBallProfile, BatThisMatch, BallThisMatch
			case "F7": case "F11":
				if(this.infobar.isInfobar_on_screen() == true) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_LowerThirds$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 40.0 \0",print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_LowerThirds$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 3.0 \0",print_writers);
				}
				
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "F5": 
				if(whatToProcess.split(",")[3].toUpperCase().equalsIgnoreCase("SPONSOR")) {
					AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.FRONT, print_writers, "anim_BatsmanScore_LT", "START");
					processAnimation(Constants.FRONT, print_writers, "Loop", "START");
					LT.setWhichSponsor("SPONSOR");
					this.whichGraphicOnScreen = whatToProcess;
				}else {
					AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "START");
					processAnimation(Constants.FRONT, print_writers, "Loop", "START");
					this.whichGraphicOnScreen = whatToProcess;
					LT.setWhichSponsor("NOSPONSOR");
				}
				break;
			case "F9":
				if(whatToProcess.split(",")[3].toUpperCase().equalsIgnoreCase("SPONSOR")) {
					AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.FRONT, print_writers, "anim_BowlerFigure_LT", "START");
					processAnimation(Constants.FRONT, print_writers, "Loop", "START");
					LT.setWhichSponsor("SPONSOR");
					this.whichGraphicOnScreen = whatToProcess;
				}else {
					AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "START");
					processAnimation(Constants.FRONT, print_writers, "Loop", "START");
					this.whichGraphicOnScreen = whatToProcess;
					LT.setWhichSponsor("NOSPONSOR");
				}
				break;
			case "Control_a":
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Projected_LT", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_F3":
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Fall_Of_Wickets", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;	
			case "Control_Shift_M": case "Control_Shift_L":
				AnimateIn("ArrowDown,", print_writers, config); // Push infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Ident", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				//processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "START");
				//processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;	
			case "Alt_Shift_C":
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Captain_LT", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;			
			case "F6": case "Control_F2": case "Control_F5": case "Control_F9":  case "Control_F3": case "Alt_k": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_g": case "Control_h": case "Control_F6": case "Shift_F6": case "Control_s":case "Shift_E": case "Alt_d": case "Control_f": case "l": case "n": case "a": case "Alt_F6": 
			case "Alt_Shift_L": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": case "Shift_B": case "Alt_F8": 
			case "F8": case "F10": case "j": case "Alt_a": case "Alt_s": case "Alt_Shift_F3": case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
			case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				//processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "START");
				//processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "9":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "START");
				this.whichGraphicOnScreen = whatToProcess;
				
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(true);
				
				break;
			case "Control_Shift_B":
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Next_To_Bat_LT", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_I":
				if(whichGraphicOnScreen.equalsIgnoreCase("Shift_I")) {
					processAnimation(Constants.FRONT, print_writers, "anim_Substitute", "CONTINUE");
					this.whichGraphicOnScreen = whatToProcess;
				}else {
					processAnimation(Constants.FRONT, print_writers, "anim_Substitute", "START");
					this.whichGraphicOnScreen = whatToProcess.split(",")[0];
				}
				break;
			case "Alt_q":
				if(this.infobar.isInfobar_on_screen() == true) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_POTT_Aramco$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 40.0 \0",print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_POTT_Aramco$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 3.0 \0",print_writers);
				}
				
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_POTT", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_U": case "Control_Shift_V":
				processAnimation(Constants.FRONT, print_writers, "anim_Popup", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "6": case "Control_4":
				processAnimation(Constants.FRONT, print_writers, "anim_Counter$In_Out", "START");
				TimeUnit.MILLISECONDS.sleep(1700);
				this.whichGraphicOnScreen = whatToProcess;
				if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[0].
						equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[0])) {
					processAnimation(Constants.FRONT, print_writers, "Change$Hundredths", "START");
					processAnimation(Constants.FRONT, print_writers, "Change$Tenths", "START");
					processAnimation(Constants.FRONT, print_writers, "Change$Units", "START");
				}
				else if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[1].
						equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[1])) {
					processAnimation(Constants.FRONT, print_writers, "Change$Tenths", "START");
					processAnimation(Constants.FRONT, print_writers, "Change$Units", "START");
				}
				else if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[2].
						equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[2])) {
					processAnimation(Constants.FRONT, print_writers, "Change$Units", "START");
				}
				break;
			case "q": case "Control_q":// Boundary L3rd
				
				if(this.infobar.isInfobar_on_screen() == true) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BoundaryLowerthird$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 89.0 \0",print_writers);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BoundaryLowerthird$Overall_Position_Y*"
						+ "TRANSFORMATION*POSITION*Y SET 55.0 \0",print_writers);
				}
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Boundary_LT", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			case "Alt_p":
				processAnimation(Constants.FRONT, print_writers, "anim_Toss", "START");
				this.specialBugOnScreen = CricketUtil.TOSS;
				break;
			case "o": case "t":
				processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": 
			case "Shift_F4": case "Shift_F":case "Alt_b": case "Shift_C":
				processAnimation(Constants.FRONT, print_writers, "anim_Bug_2Line", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_R": case "Shift_Y":
				processAnimation(Constants.FRONT, print_writers, "anim_Bug_1Line", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_F1": case "Shift_F2":
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_F1": case "Alt_F2": case "Control_Shift_F": case "Control_Shift_E":
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_F7":
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "5": case ";": case "Shift_)":
				if(whatToProcess.split(",")[0].equalsIgnoreCase("5")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$ExtraPopUp$TextGrp$Select_Data*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase(";")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$ExtraPopUp$TextGrp$Select_Data*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("Shift_)")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_All$ExtraPopUp$TextGrp$Select_Data*FUNCTION*Omo*vis_con SET 2\0", print_writers);
				}
				
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "anim_Extra_PopUps", "START");
				}
				break;	
			case "Control_F12":
				if(this.infobar.isInfobar_on_screen() == true) {
					
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Ident_To_Normal", "CONTINUE REVERSE");
					processAnimation(Constants.FRONT, print_writers, "Loop", "START");
					
					infobar.setMiddle_section("");
					infobar.setFull_section("");
					infobar.setRight_bottom("");
					infobar.setRight_section("");
					this_infobarGfx.infobar.setLast_bowler(null);
					this.infobar.setInfobar_on_screen(true);
				}else {
					
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut", "START");
					processAnimation(Constants.FRONT, print_writers, "Loop", "START");
					
					infobar.setMiddle_section("");
					infobar.setFull_section("");
					infobar.setRight_bottom("");
					infobar.setRight_section("");
					
					this.infobar.setInfobar_on_screen(true);
				}
				break;
				
			case "F12": //Infobar
				if(this.infobar.isInfobar_on_screen()) {
					
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Ident_To_Normal", "START");
					processAnimation(Constants.FRONT, print_writers, "Loop", "START");
					
					this.infobar.setInfobar_on_screen(true);
					this.infobar.setInfobar_pushed(false);
					this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}else {
					
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut", "START");
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Ident_To_Normal", "START");
					processAnimation(Constants.FRONT, print_writers, "Loop", "START");
					
					this.infobar.setInfobar_on_screen(true);
					this.infobar.setInfobar_pushed(false);
					this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				break;	
			case "ArrowUp":
				if(this.infobar.isInfobar_on_screen() == true && this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Push", "CONTINUE REVERSE");
					this.infobar.setInfobar_pushed(false);
				}
				break;
			case "ArrowDown":
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Push", "START");
					this.infobar.setInfobar_pushed(true);
					TimeUnit.MILLISECONDS.sleep(800);
				}
				break;
			
			case "w": case "i": case "f": case "s":
				if(whatToProcess.split(",")[0].equalsIgnoreCase("w")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Main$Fade_For_Shrink$Animation$Select_Type*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("i")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Main$Fade_For_Shrink$Animation$Select_Type*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("f")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Main$Fade_For_Shrink$Animation$Select_Type*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("s")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$Main$Fade_For_Shrink$Animation$Select_Type*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
				}
				
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Animation", "START");
				}
				break;
			case "Alt_f": case "Alt_g": case Constants.SHRUNK_INFOBAR: case Constants.MIDDLE + Constants.SHRUNK_INFOBAR:
				if(this.infobar.isInfobar_on_screen() == true) {
					switch (whatToProcess.split(",")[0]) {
					case "Alt_f":
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "START");
							this.infobar.setInfobar_status(Constants.FORCED + Constants.SHRUNK_INFOBAR);
						} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "CONTINUE");
							this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						}
						break;
					case "Alt_g":
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "START");
							this.infobar.setInfobar_status(Constants.FORCED + Constants.MIDDLE + Constants.SHRUNK_INFOBAR);
						} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.MIDDLE + Constants.SHRUNK_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "CONTINUE");
							this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						}
						break;
					case Constants.SHRUNK_INFOBAR:
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "START");
							this.infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
						} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.SHRUNK_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "CONTINUE");
							this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						}
						break;
					case Constants.MIDDLE + Constants.SHRUNK_INFOBAR:
						if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "START");
							this.infobar.setInfobar_status(Constants.MIDDLE + Constants.SHRUNK_INFOBAR);
						} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.MIDDLE + Constants.SHRUNK_INFOBAR)) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "CONTINUE");
							this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
						}
						break;
					}
				}
				break;
			}
			break;	
		}
		CricketFunctions.deletePreview();
		return CricketUtil.YES;
	}	
	
	private void T20_VidarbhaAnimateIn(String whatToProcess, List<PrintWriter> print_writers, Configuration config) throws InterruptedException, IOException {
		System.out.println(whatToProcess.split(",")[0]);
		switch (whatToProcess.split(",")[0]) {
		case "9":
			processAnimation(Constants.MIDDLE, print_writers, "Plotter", "START");
			this.whichGraphicOnScreen = whatToProcess;
			caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(true);
			break;
		case "Alt_Shift_Q":
			processAnimation(Constants.MIDDLE, print_writers, "Plotter", "START");
			this.whichGraphicOnScreen = whatToProcess;
			caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(true);
			break;
		case "Control_1":
			processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Bonus_In", "START");
			break;
		case "m": case "Control_m":
			AnimateIn("ArrowDown,", print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.BACK, print_writers, "Anim_MatchId$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Shift_D":
			AnimateIn("ArrowDown,", print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.BACK, print_writers, "Anim_Target$In_Out", "START");
			processAnimation(Constants.BACK, print_writers, "Anim_Target$Loop", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;	
		case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F7": case "Shift_T": case "Shift_F8": case "Control_F11":
		case "Shift_K":	case "Control_p": case "Shift_F11": case "z": case "x": case "c": case "v": case "Control_F10":
		case "Control_c": case "Control_v": case "Shift_V": case "Control_z": case "Control_x": case "Shift_F10": case "Control_d": case "Control_e":
			AnimateIn("ArrowDown,", print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(500);
			switch (whatToProcess.split(",")[0]) {
			case "F1": case "F2": case "F4":
				processAnimation(Constants.BACK, print_writers, "Sponsor$In_Out", "START");
				break;
			default:
				processAnimation(Constants.BACK, print_writers, "Sponsor", "SHOW 0.0");
				break;
			}
			processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "START");
			processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "START");
			switch (whatToProcess.split(",")[0]) {
			case "Control_d": case "Control_e":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Profile", "START");
				break;
			case "Shift_F10":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Worm", "START");
				break;
			case "F1": case "Control_Shift_A":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Batting_Card", "START");
				break;
			case "F2":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Bowling_Card", "START");
				break;
			case "Control_F11": case "Shift_F11":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Summary", "START");
				break;
			case "F4":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Partnership_List", "START");
				break;
			case "Control_F7":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Teams", "START");
				break;
			case "Shift_T":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Team_Single", "START");
				break;
			case "Shift_F8":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$LineUp_Image", "START");
				break;
			case "Shift_K":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Partnership", "START");
				break;
			case "Control_F10":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Manhattan", "START");
				break;
			case "Control_p":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Standings", "START");
				break;
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_c": case "Control_v":
			case "Shift_V":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$LeaderBoard", "START");
				break;
			}
			
			switch (whatToProcess.split(",")[0]) {
			 case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_c":
				 processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + whatToProcess.split(",")[2].split("_")[0], "START");
				 this.prevWhichPlayer = whatToProcess.split(",")[2].split("_")[0];
				break;
			}
			
			this.whichGraphicOnScreen = whatToProcess;
			break;
		
		//NameSuperDB, HOWOUT, LTBatProfile, NameSuperPlayer, LtBallProfile, BatThisMatch, BallThisMatch
		case "F7": case "F11": case "Control_f": case "Control_s":
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_LowerThirds$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 40.0 \0",print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_LowerThirds$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 3.0 \0",print_writers);
			}
			
			AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "START");
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Control_Shift_O":
			AnimateIn("ArrowDown,", print_writers, config); // Push infobar
			processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;	
		 case "Shift_I":
			 AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
			 TimeUnit.MILLISECONDS.sleep(1000);
			 processAnimation(Constants.FRONT, print_writers, "LT_Impact", "START");
			 this.whichGraphicOnScreen = whatToProcess;
			 break;
		 case "Control_4": case "6":	
			 processAnimation(Constants.FRONT, print_writers, "Sponsor_Pop$In_Out", "START");
			 processAnimation(Constants.FRONT, print_writers, "PopUps", "START");
			 this.whichGraphicOnScreen = whatToProcess;
			 break; 
		case "F5": case "F6": case "F9": case "Control_F2":
		case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_o":
		case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
		case "Control_g": case "Control_h": case "Control_F6": case "Shift_F6": case "Shift_E": case "Alt_Shift_L":
		case "Alt_d": case "l": case "a":  case "Alt_F1": case "Alt_F2": case "Alt_F6": case "Shift_A":  
		case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i":
		case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
		case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
			AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			
			switch (whatToProcess.split(",")[0]) {
			case "Shift_F3":
				processAnimation(Constants.FRONT, print_writers, "Sponsor_LT$In_Out", "START");
				break;
				default:
				processAnimation(Constants.FRONT, print_writers, "Sponsor_LT", "SHOW 0.0");
				break;
					
			}
		//	processAnimation(Constants.FRONT, print_writers, "Sponsor_LT$In_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "START");
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "START");
			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 0.0");
			this.whichGraphicOnScreen = whatToProcess;
			break;	
		case "Alt_q":
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_POTT_Aramco$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 40.0 \0",print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$All_POTT_Aramco$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 3.0 \0",print_writers);
			}
			
			AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "anim_POTT", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s": // Name super L3rd
			 AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "START");
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "CONTINUE REVERSE");
			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 0.0");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		 case "Shift_F7": case "Control_Shift_F9":
			 AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "Anim_Image_LT", "START");
			this.whichGraphicOnScreen = whatToProcess;
			 break;
		case "q": case "Control_q":// Boundary L3rd
			
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BoundaryLowerthird$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 89.0 \0",print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$BoundaryLowerthird$Overall_Position_Y*"
					+ "TRANSFORMATION*POSITION*Y SET 55.0 \0",print_writers);
			}
			AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "anim_Boundary_LT", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
			
		case "Alt_p":
			switch (config.getBroadcaster().toUpperCase()) {
			case Constants.BENGAL_T20:
				processAnimation(Constants.FRONT, print_writers, "anim_Toss", "START");
				break;
			default:
				processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "START");
				break;
			}
			this.specialBugOnScreen = CricketUtil.TOSS;
			break;
		case "o": case "t":
			processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Control_Shift_R": case "Control_Shift_F3":
		case "Shift_F4": case "Shift_F":case "Alt_b": case ".": case "/":	
			switch (whatToProcess.split(",")[0]) {
			case "Control_y": 
				processAnimation(Constants.FRONT, print_writers, "Sponsor_Bug$In_Out", "START");
				break;
			default:
				processAnimation(Constants.FRONT, print_writers, "Sponsor_Bug", "SHOW 0.0");
				break;
			}
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$Essentials$In", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Shift_F1": case "Shift_F2":
			AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Alt_F7":
			AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Shrink infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "Anim_MiniPoints$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Alt_c":
			processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage", "START");
			this.infobar.setChallengeRunOnScreen(true);
			break;
			
		case "Control_F12":
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Logos_All$Main$Select*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
				
				if(infobar.isChallengeRunOnScreen() == true) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage", "CONTINUE");
					infobar.setChallengeRunOnScreen(false);
				}
				
				if(this.targetOnScreen.equalsIgnoreCase("TARGET")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Target_Out", "START");
					this.targetOnScreen = "";
				}
				
				
				if(this.tapeballOnScreen.equalsIgnoreCase("TAPE")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TapedBall_Out", "START");
					this.tapeballOnScreen = "";
				}
				
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Batsman1_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Batsman2_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$StrikeOut", "START");
				TimeUnit.MILLISECONDS.sleep(100);
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Main_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Ident_In", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo$In_Out", "START");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				this_infobarGfx.infobar.setLast_bowler(null);
				this.infobar.setInfobar_on_screen(true);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Logos_All$Main$Select*FUNCTION*Omo*vis_con SET 0 \0", print_writers);

				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$In", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Ident_In", "START");
				TimeUnit.MILLISECONDS.sleep(1500);
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo$In_Out", "START");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				
				this.infobar.setInfobar_on_screen(true);
			}
			break;
		case "Control_F8":
			if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TapedBall_In", "START");
				this.tapeballOnScreen = "TAPE";
			}
			break;
		
		case "Alt_y":
			if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Target_In", "START");
				this.targetOnScreen = "TARGET";
			}
			break;
			
		case "F12": //Infobar
			if(this.infobar.isInfobar_on_screen()) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Logos_All$Main$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Ident_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo$In_Out", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Main_In", "START");
				if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BATSMAN)) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Batsman1_In", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Batsman2_In", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$StrikeIn", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Bowler_In", "START");
				}
				this.infobar.setInfobar_on_screen(true);
				this.infobar.setInfobar_pushed(false);
				this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$Logos_All$Main$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);

//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$In", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Main_In", "START");
				if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BATSMAN)) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Batsman1_In", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Batsman2_In", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$StrikeIn", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Bowler_In", "START");
				}
				this.infobar.setInfobar_on_screen(true);
				this.infobar.setInfobar_pushed(false);
				this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
			}
			break;
		case "ArrowUp":
			if(this.infobar.isInfobar_on_screen() == true && this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Push", "CONTINUE");
				this.infobar.setInfobar_pushed(false);
			}
			break;
		case "ArrowDown":
			if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Push", "START");
				this.infobar.setInfobar_pushed(true);
				TimeUnit.MILLISECONDS.sleep(800);
			}
			break;
		case "w": case "i": case "f": case "s": case ";": //case "9":
			if(whatToProcess.split(",")[0].equalsIgnoreCase("w")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$WIPES$Select*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
			}else if(whatToProcess.split(",")[0].equalsIgnoreCase("i")) {
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$All$CenterGRp$Main$WIPES$FreeHit"
						+ "$WipeBase" + "*TEXTURE*IMAGE SET " + Constants.VIDARBHA_BASE2 + "TLogo" + "\0", print_writers);
				TimeUnit.MILLISECONDS.sleep(500);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$WIPES$Select*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
			}else if(whatToProcess.split(",")[0].equalsIgnoreCase("f")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$WIPES$Select*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
			}else if(whatToProcess.split(",")[0].equalsIgnoreCase("s")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$WIPES$Select*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
			}else if(whatToProcess.split(",")[0].equalsIgnoreCase(";")) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$WIPES$Select*FUNCTION*Omo*vis_con SET 6 \0", print_writers);
			}
			
			if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Wipes", "START");
			}
			break;
		case "Control_2":
//			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$CenterGRp$Main$BattingTeamGrp$PowerPlay$txt_PP*GEOM*TEXT SET " + 
//					"POWERPLAY" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_InfoBar$Main$PowerPlay_In START \0", print_writers);
			break;
		case "Control_3":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_InfoBar$Main$PowerPlay_In CONTINUE REVERSE \0", print_writers);
			break;	
		case "Alt_f": case "Alt_g": case Constants.SHRUNK_INFOBAR: case Constants.MIDDLE + Constants.SHRUNK_INFOBAR:
//			System.out.println("this.infobar.isInfobar_on_screen() = " + this.infobar.isInfobar_on_screen());
//			System.out.println("whatToProcess = " + whatToProcess);
//			System.out.println("this.infobar.setInfobar_status = " + this.infobar.getInfobar_status());
			if(this.infobar.isInfobar_on_screen() == true) {
				switch (whatToProcess.split(",")[0]) {
				case "Alt_f":
					if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
//							+ "ANIMATION*KEY*$Shrink_In*VALUE SET 120.0 -436.0 0.0\0",print_writers);
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "START");
						this.infobar.setInfobar_status(Constants.FORCED + Constants.SHRUNK_INFOBAR);
					} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$Out", "START");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
					}
					break;
				case "Alt_g":
					if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
//							+ "ANIMATION*KEY*$Shrink_In*VALUE SET 183.0 -436.0 0.0\0",print_writers);
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "START");
						this.infobar.setInfobar_status(Constants.FORCED + Constants.MIDDLE + Constants.SHRUNK_INFOBAR);
					} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.MIDDLE + Constants.SHRUNK_INFOBAR)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$Out", "START");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
					}
					break;
				case Constants.SHRUNK_INFOBAR:
					if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
//							+ "ANIMATION*KEY*$Shrink_In*VALUE SET 120.0 -436.0 0.0\0",print_writers);
						if(infobar.isChallengeRunOnScreen() == true) {
							processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage", "CONTINUE");
							infobar.setChallengeRunOnScreen(false);
						}
						
						if(this.targetOnScreen.equalsIgnoreCase("TARGET")) {
							processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Target_Out", "START");
							this.targetOnScreen = "";
						}
						
						
						if(this.tapeballOnScreen.equalsIgnoreCase("TAPE")) {
							processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TapedBall_Out", "START");
							this.tapeballOnScreen = "";
						}
						
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "START");
						this.infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
					} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.SHRUNK_INFOBAR)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$Out", "START");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
					}
					break;
				case Constants.MIDDLE + Constants.SHRUNK_INFOBAR:
					if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
							+ "ANIMATION*KEY*$Shrink_In*VALUE SET 183.0 -436.0 0.0\0",print_writers);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small$In", "START");
						this.infobar.setInfobar_status(Constants.MIDDLE + Constants.SHRUNK_INFOBAR);
					} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.MIDDLE + Constants.SHRUNK_INFOBAR)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small$Out", "START");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
					}
					break;
				}
			}
			break;
		}
	}

	public String AnimateOut(String whatToProcess, List<PrintWriter> print_writers, Configuration config) throws InterruptedException, IOException
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.VIDARBHA:
			switch (whatToProcess.split(",")[0]) {
			case "9":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "CONTINUE");
				this.whichGraphicOnScreen = "";
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(false);
				break;
			case "Alt_Shift_Q":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "CONTINUE");
				this.whichGraphicOnScreen = "";
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(false);
				break;
			case "m": case "Control_m":
				processAnimation(Constants.BACK, print_writers, "Anim_MatchId$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.BACK, print_writers, "Anim_MatchId$In_Out", "SHOW 0.0");
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_D":
				processAnimation(Constants.BACK, print_writers, "Anim_Target$In_Out", "CONTINUE");

				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.BACK, print_writers, "Anim_Target$In_Out", "SHOW 0.0");
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;	
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F7": case "Shift_T": case "Shift_F8": case "Control_F11":
			case "Shift_K":	case "Control_p": case "Shift_F11": case "z": case "x": case "c": case "v": case "Control_c": case "Control_v":
			case "Shift_V": case "Control_F10": case "Control_z": case "Control_x": case "Shift_F10": case "Control_d": case "Control_e":
				switch (whatToProcess.split(",")[0]) {
				case "F1": case "F2": case "F4":
					processAnimation(Constants.BACK, print_writers, "Sponsor$In_Out", "CONTINUE");
					break;
				default:
					processAnimation(Constants.BACK, print_writers, "Sponsor", "SHOW 0.0");
					break;
				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials$Out", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header$Out", "CONTINUE");
				
				switch (whatToProcess.split(",")[0]) {
				case "Control_d": case "Control_e":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Profile", "CONTINUE");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Worm", "CONTINUE");
					break;
				case "F1": case "Control_Shift_A":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Batting_Card$Out", "CONTINUE");
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Bowling_Card$Out", "CONTINUE");
					break;
				case "Control_F11": case "Shift_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Summary$Out", "CONTINUE");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Partnership_List$Out", "CONTINUE");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Teams$Out", "CONTINUE");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Team_Single", "CONTINUE");
					break;
				case "Shift_F8":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$LineUp_Image$Out", "CONTINUE");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Partnership$Out", "CONTINUE");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Manhattan$Out", "CONTINUE");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Standings$Out", "CONTINUE");
					break;
				case "z": case "x": case "c": case "v": case "Control_c": case "Control_v": case "Shift_V": case "Control_z": case "Control_x":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$LeaderBoard$Out", "CONTINUE");
					break;
				}
				
				switch (whatToProcess.split(",")[0]) {
				 case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
					 processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player"+whatToProcess.split(",")[2].split("_")[0], "SHOW 1.574");
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player"+whatToProcess.split(",")[2].split("_")[0], "CONTINUE");
					this.prevWhichPlayer = "";
					this.whichPlayer = "";
					break;
				}
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "F7": case "F11": case "Control_f": case "Control_s":
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "CONTINUE REVERSE");
//				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 2.680");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_O":
				processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_I":	
				 processAnimation(Constants.FRONT, print_writers, "LT_Impact", "CONTINUE");
				 TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				 this.whichGraphicOnScreen = "";
				 break;	
			 case "Control_4": case "6":	
				 processAnimation(Constants.FRONT, print_writers, "Sponsor_Pop$In_Out", "CONTINUE");
				 processAnimation(Constants.FRONT, print_writers, "PopUps", "CONTINUE");
				 this.whichGraphicOnScreen = "";
				 TimeUnit.MILLISECONDS.sleep(1000);
				 processAnimation(Constants.FRONT, print_writers, "Sponsor_Pop", "SHOW 0.0");
				 break;  
				 
			case "F5": case "F6": case "F9": case "Control_F2": case "Alt_Shift_F3":
			case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_o":
			case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_g": case "Control_h": case "Control_F6": case "Shift_F6": case "Shift_E": case "Alt_Shift_L":
			case "Alt_d": case "l": case "a":  case "Alt_F1": case "Alt_F2": case "Alt_F6": case "Shift_A":  
			case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": 
			case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
			case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X": 
				 switch (whatToProcess.split(",")[0]) {
					case "Shift_F3":
						processAnimation(Constants.FRONT, print_writers, "Sponsor_LT$In_Out", "CONTINUE");
						break;
						default:
						processAnimation(Constants.FRONT, print_writers, "Sponsor_LT$In_Out", "SHOW 0.0");
						break;
							
					}	
				
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "CONTINUE REVERSE");
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 2.680");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				processAnimation(Constants.FRONT, print_writers, "Sponsor_LT", "SHOW 0.0");
				break;
			 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s":
				 processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 2.680");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			 case "Alt_q":
				 processAnimation(Constants.FRONT, print_writers, "anim_POTT", "CONTINUE");
				 TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Alt_p":
				if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.BENGAL_T20:
						processAnimation(Constants.FRONT, print_writers, "anim_Toss", "CONTINUE");
						break;
					default:
						processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "CONTINUE");
						break;
					}
					this.specialBugOnScreen = "";
				}
				break;
			case "o": case "t":
				processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			
			case "Shift_F7": case "Control_Shift_F9":
				processAnimation(Constants.FRONT, print_writers, "Anim_Image_LT", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;	
			case "q": case "Control_q":
				processAnimation(Constants.FRONT, print_writers, "anim_Boundary_LT", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b":
			case ".": case "/": case "Control_Shift_R": case "Control_Shift_F3":
				switch (whatToProcess.split(",")[0]) {
				case "Control_y": 
					processAnimation(Constants.FRONT, print_writers, "Sponsor_Bug$In_Out", "CONTINUE");
					break;
				default:
					processAnimation(Constants.FRONT, print_writers, "Sponsor_Bug", "SHOW 0.0");
					break;
				}
				processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$Essentials$Out", "START");
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_F1": case "Shift_F2": 
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "SHOW 0.0");
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Alt_F7":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniPoints$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniPoints$In_Out", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniPoints", "SHOW 0.0");
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;	
			case "Control_F8":
				if(infobar.getRight_section().equalsIgnoreCase("TARGET")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Target_Out", "START");
				}else if(infobar.getRight_section().equalsIgnoreCase("TAPED_BALL")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TapedBall_Out", "START");
				}else if(infobar.getRight_section().equalsIgnoreCase("EQUATION")) {
//					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$ToWin$In", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$ToWin$Out", "START");
				}else if(infobar.getRight_section().equalsIgnoreCase("TIMELINE")) {
//					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$ToWin$In", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TimeLine", "CONTINUE");
				}else if(infobar.getRight_section().equalsIgnoreCase("SUPER_OVER")) {
//					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$ToWin$In", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TOPRIGHT_FREETEXT_Out", "CONTINUE");
				}
				
				infobar.setRight_section("");
//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TapedBall_Out", "START");
//				this.tapeballOnScreen = "";
				break;
			case "Alt_y":
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Target_Out", "START");
				this.targetOnScreen = "";
				break;		
			case "Alt_c":
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage", "CONTINUE");
				infobar.setChallengeRunOnScreen(false);
				break;
			case "Control_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Ident_Out", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Ident_In", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo$In_Out", "SHOW 0.0");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				
				this.infobar.setInfobar_on_screen(false);
				break;
			case "F12": //Infobar
				if(infobar.isInfobar_on_screen() == true) {
					
					if(infobar.isChallengeRunOnScreen() == true) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage", "CONTINUE");
						infobar.setChallengeRunOnScreen(false);
					}
					
					if(this.targetOnScreen.equalsIgnoreCase("TARGET")) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Target_Out", "START");
						this.targetOnScreen = "";
					}
					
					if(this.tapeballOnScreen.equalsIgnoreCase("TAPE")) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TapedBall_Out", "START");
						this.tapeballOnScreen = "";
					}
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Ident_In", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo", "SHOW 0.0");
					TimeUnit.MILLISECONDS.sleep(200);
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Out", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Main_Out", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Out", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_Out", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_Out", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Batsman1_Out", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Batsman2_Out", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$StrikeOut", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Bowler_Out", "START");
					
					TimeUnit.MILLISECONDS.sleep(500);
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Main_In", "SHOW 0.0");
					
					infobar.setMiddle_section("");
					infobar.setFull_section("");
					infobar.setRight_bottom("");
					infobar.setRight_section("");
					
					this.infobar.setInfobar_on_screen(false);
				}
				break;
			}
			break;
		case Constants.T20_MUMBAI:
			T20_MumbaiAnimateOut(whatToProcess, print_writers, config);
			break;
		case Constants.ISPL:
			switch (whatToProcess.split(",")[0]) {
			case "Control_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "CONTINUE");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				
				this.infobar.setInfobar_on_screen(false);
				this.whichGraphicOnScreen = "";
				break;
			case "F12": //Infobar
				if(infobar.isInfobar_on_screen() == true) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "CONTINUE");
					infobar.setInfobar_on_screen(false);
				}
				this.whichGraphicOnScreen = "";
				break;
			case "m": case "Control_m":
				processAnimation(Constants.BACK, print_writers, "anim_MatchId$In_Out", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Start_End", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_D":
				processAnimation(Constants.BACK, print_writers, "Start_End", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Target$In_Out$In", "CONTINUE");
				this.whichGraphicOnScreen = whatToProcess;
				
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				break;
			case "Shift_T": case "F1": case "Control_F10": case "F4": case "Shift_K": case "Control_F7": case "Control_F11": case "Shift_F11": case "F2": 
			case "Alt_F11": case "Shift_F10": case "Alt_F5": case "Control_Shift_F7":
				processAnimation(Constants.BACK, print_writers, "Start_End", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Wings", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Footer", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichScorecard = "";
				this.whichGraphicOnScreen = "";
				break;
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2":
			case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k":
			case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": 
			case "Alt_F8": case "F8": case "F10": case "a":	case "Alt_Shift_F5":
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				processAnimation(Constants.FRONT, print_writers, "Anim_Lower_Third", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_F11":
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				processAnimation(Constants.FRONT, print_writers, "Anim_DRS_Bug$In_Out", "CONTINUE");
				this.whichGraphicOnScreen = "";
				caption.this_bugsAndMiniGfx.isVisited = false;
				break;
			}
			if(caption != null) {
				caption.captionWhichGfx = "";
			}
			break;	
			
		case Constants.LEGENDS:
			switch (whatToProcess.split(",")[0]) {
			//FF
			case "Shift_D":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Target", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Event_Logo", "CONTINUE");
				
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_F7":
				LineUpBigImage_On_Screen = false;
				processAnimation(Constants.BACK, print_writers, "anim_Team_BigImage", "CONTINUE");
				
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Event_Logo", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Logo_FF", "CONTINUE");
				if(!whatToProcess.split(",")[0].matches("m|Control_m|Control_Shift_D|Alt_Shift_F4")) {
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");					
				}
				processAnimation(Constants.BACK, print_writers, "Sponsor$In_Out", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$PlayerProfile", "CONTINUE");
				
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "9":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "CONTINUE");
				this.whichGraphicOnScreen = "";
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(false);
				break;
			case "Alt_Shift_Q":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "CONTINUE");
				this.whichGraphicOnScreen = "";
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(false);
				break;	
			case "m": case "Control_m": case "Shift_K": case "F4": case "Shift_T": case "F1": case "F2": case "Control_F7":
			case "Control_F11": case "Shift_F10": case "Control_p": case "Shift_F11":case "Control_F10":case "Control_Shift_F5":
			case "Control_Shift_D":case "Alt_Shift_F4":case "z": case "x": case "c": case "v":case "Control_z": case "Control_x": 
			case "Control_Shift_F8":case "Control_Shift_K": case "Control_Shift_Z": case "Control_Shift_Y": case "Alt_F5":
			case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J":	
				switch (whatToProcess.split(",")[0]) {
				case "Alt_Shift_J":	
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$BattingCard_Manhattan", "CONTINUE");
					break;
				case "Control_Alt_F1":	
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Batting_Bowling_Card", "CONTINUE");
					break;
				case "Control_Alt_F2":	
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Batting_Bowling_Manhattan", "CONTINUE");
					break;	
				case "m": case "Control_m":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Match_Id", "CONTINUE");
					break;
				case "Alt_Shift_F4":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$TeamsAll", "CONTINUE");
					break;
				case "F1":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$BattingCard", "CONTINUE");
					break;
				case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8":
				case "Control_Shift_Z": case "Control_Shift_Y":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Leaderboard", "CONTINUE");
					break;
				case "Control_Shift_K":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$PlayOff", "CONTINUE");
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$BowlingCard", "CONTINUE");
					break;
				case "Control_Shift_D":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$DoubleMatchId", "CONTINUE");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$ParnershipList", "CONTINUE");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Partnership", "CONTINUE");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Manhattan", "CONTINUE");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$TeamImage", "CONTINUE");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Teams", "CONTINUE");
					break;
				case "Control_F11": case "Shift_F11":case "Control_Shift_F5":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$MatchSummary", "CONTINUE");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Worm", "CONTINUE");
					break;
				case "Alt_F5":
					 processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					 processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Manhattan_Phase_Compare", "CONTINUE");
					break;	
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Standings", "CONTINUE");
					break;
				}
				
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Event_Logo", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Logo_FF", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Footer", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Sponsor$In_Out", "CONTINUE");
				
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			
			case "Control_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "CONTINUE");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				
				this.infobar.setInfobar_on_screen(false);
				this.whichGraphicOnScreen = "";
				break;
			case "F12": //Infobar
				if(infobar.isInfobar_on_screen() == true) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "CONTINUE");
					infobar.setInfobar_on_screen(false);
				}
				this.whichGraphicOnScreen = "";
				break;
			
			//Mini
			case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":case "Alt_Shift_F8": case "Alt_f":
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.whichGraphicOnScreen = "";
				break;
			
			//Bugs
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b": case "Alt_p":
			case "Control_Shift_R": case "Control_Shift_F3":  case "Shift_C": case "Control_Shift_J":
				processAnimation(Constants.FRONT, print_writers, "Bug_Out", "START");
				TimeUnit.MILLISECONDS.sleep(300);
				processAnimation(Constants.FRONT, print_writers, "Bug_In", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Bug_Out", "SHOW 0.0");
				AnimateIn("ArrowUp,", print_writers, config);
				this.whichGraphicOnScreen = "";
				break;
				
			//Pop-Up
			case "Control_Shift_U": case "Control_Shift_V":
				processAnimation(Constants.FRONT, print_writers, "PopUps$InOut", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "PopUps", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "PopUps$Out", "SHOW 2.800");
				this.whichGraphicOnScreen = "";
				break;
			case "6": case "Control_4":
				processAnimation(Constants.FRONT, print_writers, "PopUps$InOut", "CONTINUE");
				switch (whatToProcess.split(",")[0]) {
				case "6":
//					processAnimation(Constants.FRONT, print_writers, "Sponsor", "CONTINUE");
					break;
				}
				this.whichGraphicOnScreen = "";
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.FRONT, print_writers, "PopUps$Change", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "PopUps", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "PopUps$Out", "SHOW 2.800");
				processAnimation(Constants.FRONT, print_writers, "Sponsor", "SHOW 0.0");
				break;
			//LT
			case "Control_Shift_M": case "Control_Shift_L":
				processAnimation(Constants.FRONT, print_writers, "anim_LT_Ident", "CONTINUE");
				
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Shift_I":
				processAnimation(Constants.FRONT, print_writers, "anim_ImpactLt$InOut", "CONTINUE");
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(500);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				this.whichGraphicOnScreen = "";
				break;
				
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a": 
			case "Control_F3": case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": case "F8": 
			case "F10": case "a":case "Alt_Shift_F5":case "Alt_Shift_F7":case "Alt_Shift_F6": case "Alt_d": case "Alt_Shift_F3":
				processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut", "CONTINUE");
				
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(500);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				this.whichGraphicOnScreen = "";
				break;
			}
			if(caption != null) {
				caption.captionWhichGfx = "";
			}
			break;
		case Constants.NPL: case Constants.MPL: case Constants.APL:
			switch (whatToProcess.split(",")[0]) {
			case "Control_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "CONTINUE");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				
				this.infobar.setInfobar_on_screen(false);
				this.whichGraphicOnScreen = "";
				break;
			case "F12": //Infobar
				if(infobar.isInfobar_on_screen() == true) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "CONTINUE");
					infobar.setInfobar_on_screen(false);
				}
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				infobar.setLast_right_section("");
				infobar.setLast_right_bottom("");
				
				this.whichGraphicOnScreen = "";
				break;
			
			case "Alt_m": case "Alt_n":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_Milestone", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(700);
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;	
			
			case "Shift_L":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "CONTINUE");
				//processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "CONTINUE");
				//processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "CONTINUE");
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Row_Col", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
				
				TimeUnit.MILLISECONDS.sleep(700);
				
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_K":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Tree", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
				
				TimeUnit.MILLISECONDS.sleep(700);
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Alt_Shift_R":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$TeamSchedule", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
				
				TimeUnit.MILLISECONDS.sleep(700);
				
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Control_Shift_P":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Row_Col", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
				
				TimeUnit.MILLISECONDS.sleep(700);
				
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_M":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Leader_Board", "CONTINUE");
				
				if(Integer.valueOf(prevLeaderHighlight) > 0) {
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + prevLeaderHighlight, "CONTINUE");
				}
				
				prevLeaderHighlight = "0";
				
				TimeUnit.MILLISECONDS.sleep(700);
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Alt_Shift_W":	
//				if(sponsorOnScreen) {
//					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
//					sponsorOnScreen = false;
//				}else {
//					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$T_Logo", "CONTINUE");
//				}
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "CONTINUE");
//				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "CONTINUE");
//				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Leader_Board", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
				
				processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player1", "CONTINUE");
				
				prevLeaderHighlight = "0";
				
				TimeUnit.MILLISECONDS.sleep(700);
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;	
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
			case "Control_Shift_F8":	
//				if(sponsorOnScreen) {
//					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
//					sponsorOnScreen = false;
//				}else {
//					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$T_Logo", "CONTINUE");
//				}
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "CONTINUE");
				switch(whatToProcess.split(",")[0]) {
				case "Control_Shift_F8": case "z": case "x":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "CONTINUE");
					break;
				}
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Leader_Board", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
				
				if(Integer.valueOf(prevLeaderHighlight) > 0) {
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + prevLeaderHighlight, "CONTINUE");
				}
				
				prevLeaderHighlight = "0";
				
				TimeUnit.MILLISECONDS.sleep(700);
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_D":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Anim_Target$In_Out", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "SHOW 0.0", "START");
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Alt_Shift_P":	
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.BACK, print_writers, "Feild_Dimensions", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "SHOW 0.0", "START");
				TimeUnit.MILLISECONDS.sleep(500);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Alt_Shift_Z":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "CONTINUE");
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Tournament_Teams", "CONTINUE");
				
				TimeUnit.MILLISECONDS.sleep(700);
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Shift_F11": case "Control_d": case "Control_e": case "Shift_T": case "Shift_P": case "Shift_Q": case "Control_F7": case "F1": case "Control_Shift_A": 
			case "Control_Shift_F1": case "F2": case "Control_Shift_F2": case "Control_F11": case "F4": case "Shift_K": case "Control_Shift_F4": case "Alt_z": 
			case "Shift_F8": case "Control_p": case "Control_F10": case "Shift_F10": case "Control_Shift_D": case "Alt_F11": case "Control_Shift_E": case "Control_Shift_F": 
			case "Control_Shift_I": case "Alt_Shift_J": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_F5":
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.MPL: case Constants.APL:
//					if(audioenabled.equalsIgnoreCase("TRUE")) {
//						processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//					}
					
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$SubHeader", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$ExtraData", "CONTINUE");
					if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_F10") && !whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F11") && 
						!whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_E") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_E") && 
						!whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_I") &&
						!whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F5")) {
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Footer", "CONTINUE");
					}
					if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_d") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_e")
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Shift_P") && !whatToProcess.split(",")[0].equalsIgnoreCase("Shift_Q")
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_F7") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_D") 
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_p") && !whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F11")
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Shift_F10") && !whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F5")
							&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_E") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F")) {
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "CONTINUE");
					}
					
					switch (whatToProcess.split(",")[0]) {
					case "Control_Alt_F1":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Batting_Bowling_Card", "CONTINUE");
						break;
					case "Control_Alt_F2":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Batting_Bowling_Manhattan", "CONTINUE");
						break;	
					case "F1": case "Control_Shift_A": case "Control_Shift_F1":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$BattingCard", "CONTINUE");
						break;
					case "F2": case "Control_Shift_F2":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$BowlingCard", "CONTINUE");
						break;
					case "Control_F11": case "Shift_F11":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Summary", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;	
					case "F4": case "Control_Shift_F4":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Partnership_List", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Alt_Shift_J":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$BattingCard_Manhattan", "CONTINUE");
						break;	
					case "Shift_K":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Partnership", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Shift_T":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$LineUp_Image", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Control_F7":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Teams", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;	
					case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Profile", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						if(Integer.valueOf(prevHighlightDirector) > 0) {
							processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$"+prevHighlightDirector, "CONTINUE");
						}
						prevHighlightDirector = "0";
						break;
					case "Alt_z":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Teams", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Shift_F8":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$TeamSingle", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Standings", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Control_F10":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Manhattan", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Shift_F10":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Worms", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Control_Shift_D":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Double_MatchId", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Alt_F11":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Manhattan_Comparison", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Alt_F5":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Manhattan_Phase_Compare", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Control_Shift_E": case "Control_Shift_F":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Player_V_Player", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					case "Control_Shift_I":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Innings_Story", "CONTINUE");
						processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						break;
					}
					break;

				default:
					processAnimation(Constants.BACK, print_writers, "Start_End", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Wings", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Header", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$SubHeader", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Footer", "CONTINUE");
					break;
				}
				
				TimeUnit.MILLISECONDS.sleep(700);
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "m": case "Control_m":
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.MPL: case Constants.APL:
//					if(audioenabled.equalsIgnoreCase("TRUE")) {
//						processAnimation(Constants.BACK, print_writers, "Audio$Out", "START");
//					}
					
					processAnimation(Constants.BACK, print_writers, "Anim_Ident", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(700);
					processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
					break;
				default:
					processAnimation(Constants.BACK, print_writers, "anim_MatchId$In_Out", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Start_End", "CONTINUE");
					break;
				}
				TimeUnit.MILLISECONDS.sleep(400);
				AnimateIn("ArrowUp,", print_writers, config);
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b": case "Alt_p":
			case "Control_Shift_R": case "Control_Shift_F3":  case "Shift_C": case "Control_Shift_J":
				processAnimation(Constants.FRONT, print_writers, "Bug_Out", "START");
				TimeUnit.MILLISECONDS.sleep(300);
				processAnimation(Constants.FRONT, print_writers, "Bug_In", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Bug_Out", "SHOW 0.0");
				AnimateIn("ArrowUp,", print_writers, config);
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_M": case "Control_Shift_L":
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "LT_MatchID$In_Out", "CONTINUE");
				
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Control_F3":
				if(!this.isComp.equalsIgnoreCase("YES")) {
					processAnimation(Constants.FRONT, print_writers, "LT_Comparison$In_Out", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					this.isComp = "NO";
				}
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.FRONT, print_writers, "LT_Comparison$In_Out", "CONTINUE");
				
				TimeUnit.MILLISECONDS.sleep(1000);
				
				AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_O":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
//				}
				processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI$In_Out", "CONTINUE");
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_U": case "Control_Shift_V":
				processAnimation(Constants.FRONT, print_writers, "PopUps$InOut", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "PopUps", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "PopUps$Out", "SHOW 2.800");
				this.whichGraphicOnScreen = "";
				break;
			case "9":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "CONTINUE");
				this.whichGraphicOnScreen = "";
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(false);
				break;
			case "Alt_Shift_Q":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "CONTINUE");
				this.whichGraphicOnScreen = "";
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(false);
				break;
			case "6": case "Control_4":
				processAnimation(Constants.FRONT, print_writers, "PopUps$InOut", "CONTINUE");
				this.whichGraphicOnScreen = "";
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "PopUps$Change", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "PopUps", "SHOW 0.0");
				break;
				
			case "Alt_Shift_F3":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
//				}
				processAnimation(Constants.FRONT, print_writers, "LT_PhaseComparison$In_Out", "CONTINUE");
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.FRONT, print_writers, "LT_PhaseComparison", "SHOW 0.0");
				this.whichGraphicOnScreen = "";
				break;
				
			case "Control_Shift_B":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
//				}
				processAnimation(Constants.FRONT, print_writers, "LT_NextToBat$In_Out", "CONTINUE");
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.FRONT, print_writers, "LT_NextToBat", "SHOW 0.0");
				
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_F10":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
//				}
				processAnimation(Constants.FRONT, print_writers, "LT_Manhattan$Out", "START");
				
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				TimeUnit.MILLISECONDS.sleep(300);
				processAnimation(Constants.FRONT, print_writers, "LT_Manhattan", "SHOW 0.0");
				
				this.whichGraphicOnScreen = "";
				break;
				
			case "Control_6":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
//				}
				processAnimation(Constants.FRONT, print_writers, "LT_Weather", "CONTINUE");
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				this.whichGraphicOnScreen = "";
				TimeUnit.MILLISECONDS.sleep(2000);
				processAnimation(Constants.FRONT, print_writers, "LT_Weather", "SHOW 0.0");
				break;
			case "Alt_Shift_F5":
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
//				}
				processAnimation(Constants.FRONT, print_writers, "LT_Pointers", "CONTINUE");
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				this.whichGraphicOnScreen = "";
				TimeUnit.MILLISECONDS.sleep(2000);
				processAnimation(Constants.FRONT, print_writers, "LT_Pointers", "SHOW 0.0");
				break;
				
				
			case "Shift_I":
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$BASE", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$LOGO", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$HEADER", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$RIGHT_DATA", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$BOTTOM_DATA", "SHOW 1.600");
				
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$BASE", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$LOGO", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$HEADER", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$RIGHT_DATA", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$BOTTOM_DATA", "CONTINUE");
				
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				this.whichGraphicOnScreen = "";
				caption.this_lowerThirdGfx.chnageOn = false;
				break;
				
			case "F10": case "Shift_F3": case "u": case "Control_Shift_Q": case "F6": case "Control_F6": case "Shift_F6": case "Control_a": 
			case "Control_h": case "Alt_F8": case "F8": case "Control_F5": case "Control_F9": case "F5": case "F9": case "Shift_F5": 
			case "Shift_F9": case "Alt_F12": case "d": case "e": case "F7": case "F11": case "Control_s": case "Control_f": case "Alt_Shift_O":
				
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$BASE", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$LOGO", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$HEADER", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$BOTTOM_DATA", "SHOW 1.600");
				
//				if(audioenabled.equalsIgnoreCase("TRUE")) {
//					processAnimation(Constants.FRONT, print_writers, "Audio$Out", "START");
//				}
				
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$BASE", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$LOGO", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$HEADER", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$BOTTOM_DATA", "CONTINUE");
				
				switch (whatToProcess.split(",")[0]) {
				case "Shift_F3": case "u": case "F6": case "Control_F6": case "Shift_F6": case "Control_a": case "Control_h":
				case "Control_F5": case "Control_F9": case "F5": case "F9": case "Shift_F5": case "Shift_F9": case "Alt_F12":
				case "F7": case "F11": case "Control_s": case "Control_f": case "Control_Shift_Q": case "Alt_Shift_O":
					
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$RIGHT_DATA", "SHOW 1.600");
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$RIGHT_DATA", "CONTINUE");
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$SUB_DATA", "SHOW 1.600");
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$SUB_DATA", "CONTINUE");
					break;
				}
				
				if(whatToProcess.split(",")[0].equalsIgnoreCase("F10") && caption.this_lowerThirdGfx.setPriceMoney) {
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$Prize_Head", "SHOW 1.600");
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$Prize_Head", "CONTINUE");
					caption.this_lowerThirdGfx.setPriceMoney = false;
				}
				
				if(caption.this_lowerThirdGfx.isImpact() == true) {
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$IMPACT", "SHOW 1.600");
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out$Out$IMPACT", "CONTINUE");
					
					caption.this_lowerThirdGfx.setImpact(false);
					caption.this_lowerThirdGfx.setPrev_impact(false);
				}
				
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn("ArrowRight" + ",", print_writers, config); // Restore infobar
					infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
				this.whichGraphicOnScreen = "";
				break;	
			}
			if(caption != null) {
				caption.captionWhichGfx = "";
			}
			break;
		case Constants.ICC_U19_2023:
			switch (whatToProcess.split(",")[0]) {
			case "Alt_z":
				processAnimation(Constants.BACK, print_writers, "Anim_Squad", "CONTINUE");
				if(!caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("role")) {
					processAnimation(Constants.BACK, print_writers, "Anim_SquadDataChange", "CONTINUE");
				}
				processAnimation(Constants.BACK, print_writers, "SquadFlare_Loop", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_D":
				processAnimation(Constants.BACK, print_writers, "Target", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "TargetLoop", "CONTINUE");

				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Control_b":
				processAnimation(Constants.BACK, print_writers, "In_At", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "In_At_Loop", "CONTINUE");
				//TimeUnit.MILLISECONDS.sleep(1000);
				//AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Alt_m": case "Alt_n":
				processAnimation(Constants.BACK, print_writers, "Milestone", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "MilestoneLoop", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "r":
				processAnimation(Constants.BACK, print_writers, "Anim_POTT", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "POTT_Loop", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F1": case "Shift_F10": case "Control_F11": case "Shift_F11": case "m": case "Control_m": case "p":
			case "Shift_T": case "Control_d": case "Control_e": case "Control_F7": case "Control_F10": case "Shift_K": case "Alt_F9":
			case "Alt_F10": case "Control_p": case "Shift_P": case "Shift_Q":
			case "z": case "x": case "c": case "v": case "Alt_F11": case "Control_z": case "Control_x": case "Shift_Z": case "Shift_X":
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$Flare_Loop", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Footer", "CONTINUE");
				
				switch (whatToProcess.split(",")[0]) {
				case "F1": case "Control_Shift_A":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "CONTINUE");
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "CONTINUE");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "CONTINUE");
					break;
				case "Control_F1":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card_Image", "CONTINUE");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Worm", "CONTINUE");
					break;
				case "Control_F11": case "Shift_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "CONTINUE");
					break;
				case "m":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Ident", "CONTINUE");
					break;
				case "Control_m":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Ident", "CONTINUE");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LineUp_Image", "CONTINUE");
					break;
				
				case "Control_d": case "Shift_P": case "Control_e": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Profile", "CONTINUE");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Teams", "CONTINUE");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Manhattan", "CONTINUE");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership", "CONTINUE");
					break;
				case "Alt_F9": case "Alt_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Team_Single", "CONTINUE");
					break;
				case "p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "CONTINUE");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Standings", "CONTINUE");
					break;
				case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Shift_Z": case "Shift_X":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Leader_Board", "CONTINUE");
					break;
				case "Alt_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Manhattan_Comparison", "CONTINUE");
					break;
				}
				
				switch (whatToProcess.split(",")[0]) {
				case "m": case "Control_m": case "Control_F11": case "Shift_F11": case "Control_F7": 
				case "p": case "z": case "x": case "c": case "v": case "Control_p": case "Alt_F11": case "Control_z": case "Control_x":
					processAnimation(Constants.BACK, print_writers, "Header_Shrink", "CONTINUE");
					break;
//				case "Shift_K": case "F4":
//					processAnimation(Constants.BACK, print_writers, "Sponsor", "CONTINUE");
//					break;
				case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
					if(Integer.valueOf(whatToProcess.split(",")[4]) > 0) {
						if((Integer.valueOf(whatToProcess.split(",")[4])  == 5)){
							processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$7", "CONTINUE");
						}else {
							processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$"+whatToProcess.split(",")[4], "CONTINUE");
						}
						
					}
					break;
				}
				switch (whatToProcess.split(",")[0]) {
				 case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player"+whatToProcess.split(",")[2].split("_")[0], "CONTINUE");
					this.prevWhichPlayer = "";
					this.whichPlayer = "";
					break;
				}
				switch (whatToProcess.split(",")[0]) {
				case "m": case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Base_Gradient", "CONTINUE");
					break;
				}
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2":
			case "Control_F5": case "Control_F9": case "Control_F3": case "Alt_k": case "Control_a":
			case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Alt_Shift_L":
			case "Control_g": case "Control_h": case "Control_F6": case "Shift_F6": case "Control_s":case "Shift_E":
			case "Alt_d": case "Control_f": case "l": case "n": case "a":  case "Alt_F1": case "Alt_F2": case "Alt_F6": 
			case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i":  
			case "Shift_B": case "Control_Shift_F": case "Control_Shift_P":
			case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
			case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "CONTINUE REVERSE");
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 2.680");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s":
				 processAnimation(Constants.FRONT, print_writers, "anim_NameSupers", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_NameSupers", "SHOW 0.0");
				AnimateIn(Constants.MIDDLE + Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			 
			 case "Alt_q":
				 processAnimation(Constants.FRONT, print_writers, "anim_POTT", "CONTINUE");
				 TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Alt_p":
				if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "CONTINUE");
					this.specialBugOnScreen = "";
				}
				break;
			case "o": case "t":
				processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			case "q": case "Control_q":
				processAnimation(Constants.FRONT, print_writers, "anim_Boundary_LT", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b":
				processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$Essentials", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_F1": case "Shift_F2":
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			
			case "F12": //Infobar
				if(infobar.isInfobar_on_screen() == true) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "CONTINUE");
					infobar.setInfobar_on_screen(false);
				}
				break;
			}
			break;
		case Constants.BENGAL_T20:
			switch (whatToProcess.split(",")[0]) {
			case "9":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "CONTINUE");
				this.whichGraphicOnScreen = "";
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(false);
				break;
			case "Control_Shift_F10":
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Manhattan", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(500);
				this.whichGraphicOnScreen = "";
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Manhattan", "SHOW 0.0");
				break;
			case "Control_Shift_M": case "Control_Shift_L":
//				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Ident", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				this.whichGraphicOnScreen = "";
				processAnimation(Constants.FRONT, print_writers, "anim_Ident", "SHOW 0.0");
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				break;	
			case "Alt_z":
				processAnimation(Constants.BACK, print_writers, "Anim_Squad", "CONTINUE");
				if(!caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("role")) {
					processAnimation(Constants.BACK, print_writers, "Anim_SquadDataChange", "CONTINUE");
				}
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_D":
				processAnimation(Constants.BACK, print_writers, "Anim_Target", "CONTINUE");

				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Control_b":
				processAnimation(Constants.BACK, print_writers, "In_At", "CONTINUE");
				//TimeUnit.MILLISECONDS.sleep(1000);
				//AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Alt_Shift_Q":
				processAnimation(Constants.MIDDLE, print_writers, "Plotter", "CONTINUE");
				this.whichGraphicOnScreen = "";
				caption.this_infobarGfx.infobar.setFieldPlotter_on_screen(false);
				break;	
			case "Alt_m": case "Alt_n":
				processAnimation(Constants.BACK, print_writers, "Milestone", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "r":
				processAnimation(Constants.BACK, print_writers, "Anim_POTT", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			
			case "m": case "Control_m":
				processAnimation(Constants.BACK, print_writers, "Anim_Ident", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Alt_Shift_Z":
				processAnimation(Constants.BACK, print_writers, "Anim_Teams", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				processAnimation(Constants.BACK, print_writers, "Anim_Teams", "SHOW 0.0");
				this.whichGraphicOnScreen = "";
				break;
			
			case "Control_Shift_D":
				processAnimation(Constants.BACK, print_writers, "Anim_DoubleIdent", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_F7":
				processAnimation(Constants.BACK, print_writers, "Anim_Lineup_Image_Big", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
				processAnimation(Constants.BACK, print_writers, "anim_Profile$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Profile$Main", "CONTINUE");
				AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Alt_Shift_R":
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Team_Fixtures", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F1": case "Shift_F10": case "Control_F11": case "Shift_F11": case "p":
			case "Shift_T": case "Control_F7": case "Control_F10": case "Shift_K": case "Alt_F9": case "Alt_F10": case "Control_p": case "z": case "x": 
			case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8": case "Alt_F11": 
			case "Shift_Z": case "Shift_X": case "Control_Shift_F1": case "Control_Shift_F2": case "Control_Shift_F4":
				
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Footer", "CONTINUE");
				
				switch (whatToProcess.split(",")[0]) {
				case "F1": case "Control_Shift_A":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "CONTINUE");
					break;
				case "Control_Shift_F1":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "CONTINUE");
					if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side1$" + 
								caption.this_fullFramesGfx.pervious_batperformer_id, "CONTINUE REVERSE");
					}
					else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
						
					}
					caption.this_fullFramesGfx.pervious_batperformer_id = 0;
					caption.this_fullFramesGfx.batperformer_id = 0;
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "CONTINUE");
					break;
				case "Control_Shift_F2":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side1$" + 
							caption.this_fullFramesGfx.pervious_ballperformer_id, "CONTINUE REVERSE");
					
					caption.this_fullFramesGfx.pervious_ballperformer_id = 0;
					caption.this_fullFramesGfx.ballperformer_id = 0;
					break;
				case "Control_Shift_F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Partnership_List$Side1$" + 
							caption.this_fullFramesGfx.pervious_ballperformer_id, "CONTINUE REVERSE");
					
					caption.this_fullFramesGfx.pervious_ballperformer_id = 0;
					caption.this_fullFramesGfx.ballperformer_id = 0;
					break;	
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "CONTINUE");
					break;
				case "Control_F1":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card_Image", "CONTINUE");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Worms", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "CONTINUE");
					
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
					break;
				case "Control_F11": case "Shift_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "CONTINUE");
					switch (whatToProcess.split(",")[0]) {
					case "Control_F11":
						if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("trophy")) {
							processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "CONTINUE");
						}
						break;
					}
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LineUp_Image", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
					break;
				
				case "Control_d": case "Shift_P": case "Control_e": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Profile", "CONTINUE");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Teams", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Manhattan", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership", "CONTINUE");
					break;
				case "Alt_F9": case "Alt_F10":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Team_Single", "CONTINUE");
					break;
				case "p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "CONTINUE");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Standings", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
					break;
				case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
				case "Control_Shift_F8": case "Shift_Z": case "Shift_X":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Leaderboard", "CONTINUE");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "CONTINUE");
					break;
				case "Alt_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Manhattan_Comparison", "CONTINUE");
					break;
				}
				
				switch (whatToProcess.split(",")[0]) {
				case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Control_p":
					 processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "CONTINUE");
					break;
				}
				
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn("ArrowUp,", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "F5": 
				if(LT.getWhichSponsor().equalsIgnoreCase("SPONSOR")) {
					processAnimation(Constants.FRONT, print_writers, "anim_BatsmanScore_LT", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
					this.whichGraphicOnScreen = "";
					LT.setWhichSponsor("");
				}else {
					processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
					this.whichGraphicOnScreen = "";
					LT.setWhichSponsor("");
				}
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
				break;
			case "F9":
				if(LT.getWhichSponsor().equalsIgnoreCase("SPONSOR")) {
					processAnimation(Constants.FRONT, print_writers, "anim_BowlerFigure_LT", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
					this.whichGraphicOnScreen = "";
					LT.setWhichSponsor("");
				}else {
					processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
					this.whichGraphicOnScreen = "";
					LT.setWhichSponsor("");
				}
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
				break;
			case "Control_a":
				processAnimation(Constants.FRONT, print_writers, "anim_Projected_LT", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
				break;
			case "Shift_F3":
				processAnimation(Constants.FRONT, print_writers, "anim_Fall_Of_Wickets", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
				break;
			case "Alt_Shift_C":
				processAnimation(Constants.FRONT, print_writers, "anim_Captain_LT", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
				break;	
			case "F6": case "F7": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_F3": case "Alt_k": case "u": case "d": 
			case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Alt_Shift_L": case "Control_g": case "Control_h": case "Control_F6": case "Shift_F6": 
			case "Control_s":case "Shift_E": case "Alt_d": case "Control_f": case "l": case "n": case "a": case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": 
			case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": case "Shift_B": case "Control_Shift_P": case "Alt_F8": case "F8": 
			case "F10": case "j": case "Alt_a": case "Alt_s": case "Alt_Shift_F3": case "Alt_Shift_D": case "Alt_Shift_E": case "Alt_Shift_F": case "Alt_Shift_G": 
			case "Alt_Shift_H": case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
				break;
			case "Control_Shift_B":
				processAnimation(Constants.FRONT, print_writers, "anim_Next_To_Bat_LT", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				processAnimation(Constants.FRONT, print_writers, "anim_Next_To_Bat_LT", "SHOW 0.0");
				break;
			 case "Shift_I":
					processAnimation(Constants.FRONT, print_writers, "anim_Substitute", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.FRONT, print_writers, "anim_Substitute", "SHOW 0.0");
					this.whichGraphicOnScreen = "";
					break;
			 
			 case "Alt_q":
				 processAnimation(Constants.FRONT, print_writers, "anim_POTT", "CONTINUE");
				 TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			 case "Control_Shift_U": case "Control_Shift_V":
					processAnimation(Constants.FRONT, print_writers, "anim_Popup", "CONTINUE");
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.FRONT, print_writers, "anim_Popup", "SHOW 0.0");
					this.whichGraphicOnScreen = "";
					break;
			 case "6": case "Control_4":
					processAnimation(Constants.FRONT, print_writers, "anim_Counter$In_Out", "CONTINUE");
					this.whichGraphicOnScreen = "";
					TimeUnit.MILLISECONDS.sleep(500);
					processAnimation(Constants.FRONT, print_writers, "Change$Hundredths", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Change$Tenths", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Change$Units", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Change", "SHOW 0.0");
					
					TimeUnit.MILLISECONDS.sleep(1000);
					processAnimation(Constants.FRONT, print_writers, "anim_Counter$In_Out", "SHOW 0.0");
					break;
			case "Alt_p":
				if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.BENGAL_T20:
						processAnimation(Constants.FRONT, print_writers, "anim_Toss", "CONTINUE");
						break;
					default:
						processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "CONTINUE");
						break;
					}
					this.specialBugOnScreen = "";
				}
				break;
			case "o": case "t":
				processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			case "q": case "Control_q":
				processAnimation(Constants.FRONT, print_writers, "anim_Boundary_LT", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": 
			case "Shift_F4": case "Shift_F":case "Alt_b": case "Shift_C":
				processAnimation(Constants.FRONT, print_writers, "anim_Bug_2Line", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_R": case "Shift_Y":
				processAnimation(Constants.FRONT, print_writers, "anim_Bug_1Line", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_F1": case "Shift_F2":
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "SHOW 0.0");
				this.whichGraphicOnScreen = "";
//				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Alt_F1": case "Alt_F2": case "Control_Shift_F": case "Control_Shift_E":
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "SHOW 0.0");
				this.whichGraphicOnScreen = "";
				break;
			case "Alt_F7":
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Mini$In_Out", "SHOW 0.0");
//				AnimateIn(Constants.SHRUNK_INFOBAR + ",", print_writers, config); // Restore infobar
				this.whichGraphicOnScreen = "";
				break;	
			
			case "Control_F12":
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut", "CONTINUE");
				
				TimeUnit.MILLISECONDS.sleep(1500);
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar", "SHOW 0.0");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				
				this.infobar.setInfobar_on_screen(false);
				break;
				
			case "F12": //Infobar
				if(infobar.isInfobar_on_screen() == true) {
					
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut", "CONTINUE");
					
					TimeUnit.MILLISECONDS.sleep(1500);
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar", "SHOW 0.0");
					
					infobar.setMiddle_section("");
					infobar.setFull_section("");
					infobar.setRight_bottom("");
					infobar.setRight_section("");
					
					this.infobar.setInfobar_on_screen(false);
				}
				break;	
			}
			break;	
		}
		return CricketUtil.YES;
	}	
	public String ChangeOn(String whatToProcess,List<PrintWriter> print_writers,Configuration config) throws InterruptedException, IOException
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.VIDARBHA:
			T20_VidarbhaChangeom(whatToProcess,print_writers,config);
			
			break;	
		case Constants.T20_MUMBAI:
			T20_MumbaiChangeOn(whatToProcess, print_writers, config);
			break;
		case Constants.ISPL:
			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			switch(whatToProcess.split(",")[0]) {
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Change_Bottom", "START");
				
				TimeUnit.MILLISECONDS.sleep(1000);
				break;
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": 
			case "Alt_9": case "Alt_0": case "Alt_c":
				switch(whatToProcess.split(",")[0]) {
				case "Alt_1":
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_Bottom_Left", "START");
					break;
				case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_9": case "Alt_0": case "Alt_c":
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_CenterInfo", "START");
					TimeUnit.MILLISECONDS.sleep(300);
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_Sixes", "SHOW 0.0");
					break;
				case "Alt_7":
//					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "START");
					
//					if(infobar.getRight_bottom() != null && !infobar.getRight_bottom().trim().isEmpty()) {
//						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "START");
//					}else {
//						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$RightInfo_Bottom", "START");
//					}
					infobar.setRight_bottom(whatToProcess.split(",")[2]);
					break;
				case "Alt_8":
					if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER) && infobar.isTarget_on_screen() == true) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$In_Out", "CONTINUE");
						infobar.setRight_section(whatToProcess.split(",")[2]);
						TimeUnit.MILLISECONDS.sleep(700);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "SHOW 0.0");
						infobar.setRight_section_play(false);
						infobar.setTarget_on_screen(false);
					}else if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "CONTINUE");
						infobar.setRight_section(whatToProcess.split(",")[2]);
						TimeUnit.MILLISECONDS.sleep(700);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "SHOW 0.0");
						infobar.setRight_section_play(false);
					}else if(whatToProcess.split(",")[2].equalsIgnoreCase("TARGET")) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$In_Out", "START");
						TimeUnit.MILLISECONDS.sleep(700);
						infobar.setRight_section(whatToProcess.split(",")[2]);
						infobar.setTarget_on_screen(true);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "SHOW 0.0");
					}else if(!infobar.getRight_section().equalsIgnoreCase(CricketUtil.BOWLER) && 
							infobar.isTarget_on_screen() == true) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$In_Out", "CONTINUE");
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "CONTINUE");
						infobar.setRight_section(whatToProcess.split(",")[2]);
						infobar.setRight_section_play(true);
						infobar.setTarget_on_screen(false);
					}else if(!infobar.getRight_section().equalsIgnoreCase(CricketUtil.BOWLER) && infobar.isRight_section_play() == true) {
						TimeUnit.MILLISECONDS.sleep(200);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "START");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setRight_section(whatToProcess.split(",")[2]);
						infobar.setRight_section_play(true);
					}else {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "START");
						infobar.setRight_section(whatToProcess.split(",")[2]);
						infobar.setRight_section_play(true);
					}
					infobar.setRight_bottom("");
					break;
				}
				break;
				case "Shift_T": case "F1": case "Control_F10": case "F4": case "Shift_K": case "Control_F7": case "Control_F11": case "Shift_F11": case "F2": case "Alt_F11":
				case "Shift_F10": case "z": case "x": case "c": case "v": case "Alt_F5": case "Control_Shift_F7":
				
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
					processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Wings", "START");
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "F1":
						if(whichScorecard.equalsIgnoreCase("NORMAL")) {
							processAnimation(Constants.BACK, print_writers, "Change$BatStyle2$Change_Out", "START");
						}else if(whichScorecard.equalsIgnoreCase("SPLIT")) {
							processAnimation(Constants.BACK, print_writers, "Change$SplitBatBall_Card$Change_Out", "START");
						}else if(whichScorecard.equalsIgnoreCase("TRADITIONAL")) {
							processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Normal$Change_Out", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$BatStyle3$Change_Out", "START");
						}
						break;
					case "F2":
						processAnimation(Constants.BACK, print_writers, "Change$BowlingCard_Normal$Change_Out", "START");
						break;
					case "Shift_T":
						processAnimation(Constants.BACK, print_writers, "Change$PlayingXI$Change_Out", "START");
						break;
					case "Control_Shift_F7":
						processAnimation(Constants.BACK, print_writers, "Change$LineUp_Single$Change_Out", "START");
						break;
					case "Control_F10":
						processAnimation(Constants.BACK, print_writers, "Change$Manhattan$Change_Out", "START");
						break;
					case "F4":
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List$Change_Out", "START");
						break;
					case "Shift_K":
						processAnimation(Constants.BACK, print_writers, "Change$Partnership$Change_Out", "START");
						break;
					case "Control_F7":
						processAnimation(Constants.BACK, print_writers, "Change$Both_Team$Change_Out", "START");
						break;
					case "Control_F11": case "Shift_F11":
						processAnimation(Constants.BACK, print_writers, "Change$Summary$Change_Out", "START");
						break;
					case "Alt_F11":
						processAnimation(Constants.BACK, print_writers, "Change$Doublemanhattan$Change_Out", "START");
						break;
					case "Alt_F5":
						processAnimation(Constants.BACK, print_writers, "Change$DoubleTeamManhattan$Change_Out", "START");
						break;
					case "Shift_F10":
						processAnimation(Constants.BACK, print_writers, "Change$Worm$Change_Out", "START");
						break;
					case "z": case "x": case "c": case "v":
						processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard_3Col$Change_Out", "START");
						break;
					}
					TimeUnit.MILLISECONDS.sleep(500);
					switch(whatToProcess.split(",")[0]) {
					case "F1":
						if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("NORMAL")) {
							processAnimation(Constants.BACK, print_writers, "Change$BatStyle2$Change_In", "START");
						}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("SPLIT")) {
							processAnimation(Constants.BACK, print_writers, "Change$SplitBatBall_Card$Change_In", "START");
						}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("TRADITIONAL")) {
							processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Normal$Change_In", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$BatStyle3$Change_In", "START");
						}
						break;
					case "F2":
						processAnimation(Constants.BACK, print_writers, "Change$BowlingCard_Normal$Change_In", "START");
						break;
					case "F4":
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List$Change_In", "START");
						break;
					case "Shift_K":
						processAnimation(Constants.BACK, print_writers, "Change$Partnership$Change_In", "START");
						break;
					case "Shift_T":
						processAnimation(Constants.BACK, print_writers, "Change$PlayingXI$Change_In", "START");
						break;
					case "Control_Shift_F7":
						processAnimation(Constants.BACK, print_writers, "Change$LineUp_Single$Change_In", "START");
						break;
					case "Control_F10":
						processAnimation(Constants.BACK, print_writers, "Change$Manhattan$Change_In", "START");
						break;
					case "Control_F7":
						processAnimation(Constants.BACK, print_writers, "Change$Both_Team$Change_In", "START");
						break;
					case "Control_F11":
						processAnimation(Constants.BACK, print_writers, "Change$Summary$Change_In", "START");
						break;
					case "Alt_F11":
						processAnimation(Constants.BACK, print_writers, "Change$Doublemanhattan$Change_In", "START");
						break;
					case "Alt_F5":
						processAnimation(Constants.BACK, print_writers, "Change$DoubleTeamManhattan$Change_In", "START");
						break;
					case "Shift_F10":
						processAnimation(Constants.BACK, print_writers, "Change$Worm$Change_In", "START");
						break;
					case "z": case "x": case "c": case "v":
						processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard_3Col$Change_In", "START");
						break;
					}
				break;
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a":  
			case "Control_F3": case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": 
			case "F8": case "F10": case "a":
				processAnimation(Constants.FRONT, print_writers, "anim_LT_Change", "START");
				break;
			}
			break;
		case Constants.LEGENDS:
			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			switch(whatToProcess.split(",")[0]) {
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Change_Bottom", "START");
				TimeUnit.MILLISECONDS.sleep(1000);
				break;
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": 
			case "Alt_9": case "Alt_0": case "Alt_c":
				switch(whatToProcess.split(",")[0]) {
				case "Alt_1":
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_Bottom_Left", "START");
					break;
				case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_9": case "Alt_0": case "Alt_c":
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_CenterInfo", "START");
					TimeUnit.MILLISECONDS.sleep(300);
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_Sixes", "SHOW 0.0");
					break;
				case "Alt_7":
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "START");
					break;
				case "Alt_8":
					if(caption.this_infobarGfx.infobar.getRight_section().equalsIgnoreCase(CricketUtil.BOWLER) && infobar.isTarget_on_screen() == true) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$In_Out", "CONTINUE");
						TimeUnit.MILLISECONDS.sleep(700);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "SHOW 0.0");
						infobar.setRight_section_play(false);
						infobar.setTarget_on_screen(false);
					}else if(caption.this_infobarGfx.infobar.getRight_section().equalsIgnoreCase(CricketUtil.BOWLER)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "CONTINUE");
						TimeUnit.MILLISECONDS.sleep(700);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "SHOW 0.0");
						infobar.setRight_section_play(false);
					}else if(caption.this_infobarGfx.infobar.getRight_section().equalsIgnoreCase("TARGET")) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$In_Out", "START");
						TimeUnit.MILLISECONDS.sleep(700);
						infobar.setTarget_on_screen(true);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "SHOW 0.0");
					}else if(!caption.this_infobarGfx.infobar.getRight_section().equalsIgnoreCase(CricketUtil.BOWLER) && infobar.isTarget_on_screen() == true) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "CONTINUE");
						TimeUnit.MILLISECONDS.sleep(500);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$In_Out", "CONTINUE");
						infobar.setRight_section_play(true);
						infobar.setTarget_on_screen(false);
					}else if(!caption.this_infobarGfx.infobar.getRight_section().equalsIgnoreCase(CricketUtil.BOWLER) && infobar.isRight_section_play() == true) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "START");
						infobar.setRight_section_play(true);
					}else {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$In_Out", "START");
						infobar.setRight_section_play(true);
					}
					break;
				}
				break;
			//Minis
			case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":case "Alt_Shift_F8": case "Alt_f":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "START");
				break;
			//Bugs
			case "Shift_F": case "Alt_b": case "y": case "Shift_O":
				processAnimation(Constants.FRONT, print_writers, "Bug_Change", "START");
				break;
			//Pop-Up
			case "Control_Shift_U": case "Control_Shift_V":
				processAnimation(Constants.FRONT, print_writers, "PopUps$Change", "START");
				break;
			//LT
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a":  
			case "Control_F3": case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
			case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": case "F8": 
			case "F10": case "a": case "Alt_Shift_F5": case "Alt_Shift_F7": case "Alt_Shift_F6": case "Alt_d": case "Alt_Shift_F3":
				processAnimation(Constants.FRONT, print_writers, "anim_LT_Change", "START");
				processAnimation(Constants.FRONT, print_writers, "Lt_Position", "START");
				break;
			//FF
			case "F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Shift_T":  case "Control_F7": case "m": case "Control_m": 
			case "Shift_F10": case "Control_p":case "Control_F10":case "Control_Shift_F5": case "Control_Shift_D": case "Alt_Shift_F4": case "Control_d": 
			case "Control_e":case "Shift_P": case "Shift_Q": case "z": case "x": case "c": case "v":case "Control_z": case "Control_x": case "Control_Shift_F8": 
			case "Control_Shift_K": case "Control_Shift_Z": case "Control_Shift_Y":
				processAnimation(Constants.BACK, print_writers, "anim_Change$Event_Logo", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Change$Logo_FF", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Change$Header", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Change$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Change$Footer", "START");
				processAnimation(Constants.BACK, print_writers, "Sponsor$Change", "START");
				if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					switch(whatToProcess.split(",")[0]) {
					case "F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Shift_T": case "Control_F7":
					case "Shift_F10": case "Control_p":case "Control_d": case "Control_e":case "Shift_P": case "Shift_Q":
					case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8": 
					case "Control_Shift_K": case "Control_Shift_Z": case "Control_Shift_Y":
						if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("m") || 
								whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_m")|| 
								whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_D")|| 
								whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Alt_Shift_F4")) {
							processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "START");
						}
						break;
					case "m": case "Control_m":case "Control_Shift_D":case "Control_Shift_F5":case "Alt_Shift_F4":
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("m") && 
								!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_m")|| 
								whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_D")|| 
								whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Alt_Shift_F4")) {
							processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "CONTINUE");
							TimeUnit.MILLISECONDS.sleep(600);
							processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$FF_Base", "SHOW 0.0");
						}
						break;
					}
				}
				
				switch(whichGraphicOnScreen.split(",")[0]) {
				case "m": case "Control_m":
					processAnimation(Constants.BACK, print_writers, "anim_Change$MatchId", "START");
					break;
				case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8":
				case "Control_Shift_Z": case "Control_Shift_Y":
					processAnimation(Constants.BACK, print_writers, "anim_Change$LeaderBoard", "START");
					break;
				case "Alt_Shift_F4":
					processAnimation(Constants.BACK, print_writers, "anim_Change$TeamsAll", "START");
					break;
				case "Control_Shift_K":
					processAnimation(Constants.BACK, print_writers, "anim_Change$PlayOff", "START");
					break;
				case "Control_Shift_D":
					processAnimation(Constants.BACK, print_writers, "anim_Change$DoubleMatchId", "START");
					break;
				case "F1":
					processAnimation(Constants.BACK, print_writers, "anim_Change$BattingCard", "START");
					break;
				case "F2": 
					processAnimation(Constants.BACK, print_writers, "anim_Change$BowlingCard", "START");
					break;
				case "Control_F11": case "Control_Shift_F5":
					processAnimation(Constants.BACK, print_writers, "anim_Change$MatchSummary", "START");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "anim_Change$ParnershipList", "START");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Manhattan", "START");
					break;
				case "Shift_K": 
					processAnimation(Constants.BACK, print_writers, "anim_Change$Partnership", "START");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "anim_Change$TeamImage", "START");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Teams", "START");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Worm", "START");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Standings", "START");
					break;
				case "Control_d": case "Control_e":case "Shift_P": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "anim_Change$PlayerProfile", "START");
					break;
				}
				
				switch(whatToProcess.split(",")[0]) {
				case "m": case "Control_m":
					processAnimation(Constants.BACK, print_writers, "anim_Change$MatchId", "START");
					break;
				case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8":
				case "Control_Shift_Z": case "Control_Shift_Y":
					processAnimation(Constants.BACK, print_writers, "anim_Change$LeaderBoard", "START");
					break;
				case "Control_d": case "Control_e":case "Shift_P": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "anim_Change$PlayerProfile", "START");
					break;
				case "Control_Shift_K":
					processAnimation(Constants.BACK, print_writers, "anim_Change$PlayOff", "START");
					break;
				case "Alt_Shift_F4":
					processAnimation(Constants.BACK, print_writers, "anim_Change$TeamsAll", "START");
					break;
				case "Control_Shift_D":
					processAnimation(Constants.BACK, print_writers, "anim_Change$DoubleMatchId", "START");
					break;
				case "F1":
					processAnimation(Constants.BACK, print_writers, "anim_Change$BattingCard", "START");
					break;
				case "F2": 
					processAnimation(Constants.BACK, print_writers, "anim_Change$BowlingCard", "START");
					break;
				case "Control_F11": case "Control_Shift_F5":
					processAnimation(Constants.BACK, print_writers, "anim_Change$MatchSummary", "START");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "anim_Change$ParnershipList", "START");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Manhattan", "START");
					break;
				case "Shift_K": 
					processAnimation(Constants.BACK, print_writers, "anim_Change$Partnership", "START");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "anim_Change$TeamImage", "START");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Teams", "START");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Worm", "START");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Standings", "START");
					break;
				}
				break;
			}
			break;
		case Constants.NPL: case Constants.MPL: case Constants.APL:
			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			switch(whatToProcess.split(",")[0]) {
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Change_Bottom", "START");
				TimeUnit.MILLISECONDS.sleep(1000);
				break;
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": 
			case "Alt_9": case "Alt_0": case "Alt_c": case "Control_Shift_(":
				switch(whatToProcess.split(",")[0]) {
				case "Alt_1":
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.NPL: case Constants.MPL: case Constants.APL:
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_Bottom_Left", "START");
						break;
					}
					break;
				case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_9": case "Alt_0": case "Alt_c":
				case "Control_Shift_(":	
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.NPL: case Constants.APL:
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_CenterInfo", "START");
						TimeUnit.MILLISECONDS.sleep(300);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_Sixes", "SHOW 0.0");
						break;
					case Constants.MPL:
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_CenterInfo", "START");
						TimeUnit.MILLISECONDS.sleep(300);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_Sixes", "SHOW 0.0");
						break;	
					}
					break;
				case "Alt_7":

					switch(config.getBroadcaster().toUpperCase()) {
					case Constants.NPL: case Constants.MPL: case Constants.APL:
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "START");
						
//						if(infobar.getRight_bottom() != null && !infobar.getRight_bottom().trim().isEmpty()) {
//							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "START");
//						}else {
//							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$RightInfo_Bottom", "START");
//						}
						infobar.setRight_bottom(whatToProcess.split(",")[2]);
						break;
					}
					
					break;
				case "Alt_8":
					if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER) && infobar.isTarget_on_screen() == true) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$In_Out", "CONTINUE");
						infobar.setRight_section(whatToProcess.split(",")[2]);
						TimeUnit.MILLISECONDS.sleep(700);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "SHOW 0.0");
						infobar.setRight_section_play(false);
						infobar.setTarget_on_screen(false);
						infobar.setRight_bottom_play(false);
					}else if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$In_Out", "CONTINUE");
						infobar.setRight_section(whatToProcess.split(",")[2]);
						TimeUnit.MILLISECONDS.sleep(700);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "SHOW 0.0");
						infobar.setRight_section_play(false);
						infobar.setRight_bottom_play(false);
					}else if(whatToProcess.split(",")[2].equalsIgnoreCase("TARGET")) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$In_Out", "START");
						TimeUnit.MILLISECONDS.sleep(700);
						infobar.setRight_section(whatToProcess.split(",")[2]);
						infobar.setTarget_on_screen(true);
						infobar.setRight_bottom_play(false);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "SHOW 0.0");
					}else if(!infobar.getRight_section().equalsIgnoreCase(CricketUtil.BOWLER) && 
							infobar.isTarget_on_screen() == true) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$In_Out", "CONTINUE");
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$In_Out", "CONTINUE");
						infobar.setRight_section(whatToProcess.split(",")[2]);
						infobar.setRight_section_play(true);
						infobar.setTarget_on_screen(false);
						infobar.setRight_bottom_play(false);
					}else if(!infobar.getRight_section().equalsIgnoreCase(CricketUtil.BOWLER) && 
							infobar.isTarget_on_screen() == false && infobar.isRight_bottom_play() == true) {
						TimeUnit.MILLISECONDS.sleep(200);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "START");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setRight_section(whatToProcess.split(",")[2]);
						infobar.setRight_section_play(true);
						infobar.setTarget_on_screen(false);
						infobar.setRight_bottom_play(true);
					}
//					else if(!infobar.getRight_section().equalsIgnoreCase(CricketUtil.BOWLER) && infobar.isRight_section_play() == true) {
//						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Right_Bowl_Full_Over$Change", "START");
//						infobar.setRight_section(whatToProcess.split(",")[2]);
//						infobar.setRight_section_play(true);
//					}
					else {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All", "START");
						infobar.setRight_section(whatToProcess.split(",")[2]);
						infobar.setRight_section_play(true);
						infobar.setRight_bottom_play(true);
					}
					infobar.setRight_bottom("");
					break;
				}
				break;	
			case "Alt_Shift_W": case "Shift_L":
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
				//processAnimation(Constants.BACK, print_writers, "Change$Logo", "START");
				processAnimation(Constants.BACK, print_writers, "Change$Row_Col", "START");
				break;
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
			case "Control_Shift_F8":
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				if(!whatToProcess.split(",")[0].equalsIgnoreCase(whichGraphicOnScreen.split(",")[0])) {
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
					processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "START");
					processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
					switch(whatToProcess.split(",")[0]) {
					case "z": case "x":
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("z") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("x")) {
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "START");
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "START");
						}
						break;
					case "Control_Shift_F8":
						if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("z") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("x")) {
							processAnimation(Constants.BACK, print_writers, "Change$Logo", "START");
						}
						break;
					default:
						if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("z") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("x")) {
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "CONTINUE REVERSE");
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "CONTINUE REVERSE");
						}
						break;
					}
					
					processAnimation(Constants.BACK, print_writers, "Change$Leader_Board", "START");
					
					if(Integer.valueOf(whatToProcess.split(",")[2].split("_")[0]) > 0) {
						processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2$Player" + whatToProcess.split(",")[2].split("_")[0], "START");
					}
				}
				break;
				
			case "Control_Shift_F1": case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F2": case "Control_F11": case "F4": case "Control_Shift_F4":
			case "Shift_T":	case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q": case "Alt_z": case "Shift_F8": case "Control_p": case "Alt_Shift_J":
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "Audio$In", "START");
				}
				
				switch(whichGraphicOnScreen.split(",")[0]) {
				case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F1": case "Control_Shift_F2": case "Control_F11": case "F4": 
				case "Control_Shift_F4": case "Control_p": case "Alt_Shift_J":
					
					if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F1") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F1")
							|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F2") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F2")
							|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F4") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
							||whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Alt_Shift_J") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_F11")
							|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Alt_z") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Shift_F8")) {
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_p")) {
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "CONTINUE");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Logo", "START");
						}
					}else {
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_p")) {
							processAnimation(Constants.BACK, print_writers, "Change$Logo", "START");
						}else {
							if(whatToProcess.split(",")[0].equalsIgnoreCase("F1") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F1")
									|| whatToProcess.split(",")[0].equalsIgnoreCase("F2") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F2")
									|| whatToProcess.split(",")[0].equalsIgnoreCase("F4") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
									||whatToProcess.split(",")[0].equalsIgnoreCase("Alt_Shift_J") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_F11")
									|| whatToProcess.split(",")[0].equalsIgnoreCase("Alt_z") || whatToProcess.split(",")[0].equalsIgnoreCase("Shift_F8")) {
								processAnimation(Constants.BACK, print_writers, "Change$Logo", "START");
							}
						}
					}
					
					TimeUnit.MILLISECONDS.sleep(600);
					if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F1") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F2")
							|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F4") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F1") 
							|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F2") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
							||whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Alt_Shift_J") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_F11")
							|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Alt_z") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Shift_F8")) {
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_p")) {
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "CONTINUE REVERSE");
						}
					}else if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_p")) {
						if(whatToProcess.split(",")[0].equalsIgnoreCase("F1") || whatToProcess.split(",")[0].equalsIgnoreCase("F2")
							|| whatToProcess.split(",")[0].equalsIgnoreCase("F4") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F1") 
							|| whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F2") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
							||whatToProcess.split(",")[0].equalsIgnoreCase("Alt_Shift_J") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_F11")
							|| whatToProcess.split(",")[0].equalsIgnoreCase("Alt_z") || whatToProcess.split(",")[0].equalsIgnoreCase("Shift_F8")) {
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LogoBase", "START");
						}
					}
					
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
					processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer", "START");
					
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "F1": case "Control_Shift_A":
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F1")) {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
							processAnimation(Constants.BACK, print_writers, "Change$BattingCard", "START");
						}
						break;
					case "F2":
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
							processAnimation(Constants.BACK, print_writers, "Change$BowlingCard", "START");
						}
						break;
					case "Control_Shift_F1":
						if(whatToProcess.split(",")[0].equalsIgnoreCase("F1")) {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
							processAnimation(Constants.BACK, print_writers, "Change$BattingCard", "START");
						}
						break;
					case "Control_Shift_F2":
						if(whatToProcess.split(",")[0].equalsIgnoreCase("F2")) {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
							processAnimation(Constants.BACK, print_writers, "Change$BowlingCard", "START");
						}
						break;
					case "Control_F11":
						processAnimation(Constants.BACK, print_writers, "Change$Summary", "START");
						processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("F4") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
								&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_p")) {
							processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						}
						break;
					case "F4":
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_F11") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
								&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_p")) {
							processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						}
						
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")) {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
						}
						break;
					case "Control_Shift_F4":
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_F11") && !whatToProcess.split(",")[0].equalsIgnoreCase("F4")
								&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_p")) {
							processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						}
						if(whatToProcess.split(",")[0].equalsIgnoreCase("F4")) {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
						}
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Change$Standings", "START");
						processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("F4") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
								&& !whatToProcess.split(",")[0].equalsIgnoreCase("Control_p") && !whatToProcess.split(",")[0].equalsIgnoreCase("Control_F11")) {
							processAnimation(Constants.BACK, print_writers, "BG_Scale", "CONTINUE REVERSE");
						}
						break;
					case "Alt_Shift_J":
						processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Manhattan", "START");
						break;	
					}
					break;
					
				case "Shift_F8":
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
					processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer", "START");
					processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
					processAnimation(Constants.BACK, print_writers, "Change$TeamSingle", "START");
					break;
				case "Alt_z":
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
					processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer", "START");
					processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Teams", "START");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
					processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Logo", "START");
					processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
					processAnimation(Constants.BACK, print_writers, "Change$LineUp_Image", "START");
					break;
				case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
					processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Profile", "START");
					processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
					
					if(Integer.valueOf(prevHighlightDirector) > 0) {
						processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$" + prevHighlightDirector, "CONTINUE");
					}
					
					break;
				}
				
				switch(whatToProcess.split(",")[0]) {
				case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F1": case "Control_Shift_F2": case "Control_F11": case "F4": case "Control_Shift_F4":
				case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q": case "Alt_z": case "Control_p": case "Alt_Shift_J":
					
					switch(whatToProcess.split(",")[0]) {
					case "F1": case "Control_Shift_A":
						processAnimation(Constants.BACK, print_writers, "Change$BattingCard", "START");
						break;
					case "F2":
						processAnimation(Constants.BACK, print_writers, "Change$BowlingCard", "START");
						break;
					case "Control_Shift_F1":
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F1")) {
							processAnimation(Constants.BACK, print_writers, "Change$BattingCard", "START");
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						}
						break;
					case "Control_Shift_F2":
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F2")) {
							processAnimation(Constants.BACK, print_writers, "Change$BowlingCard", "START");
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						}
						break;
					case "Control_F11":
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F4") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
								&& !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_p")) {
							processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						}
						processAnimation(Constants.BACK, print_writers, "Change$Summary", "START");
						break;
					case "F4":
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_F11") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
								&& !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_p")) {
							processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						}
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
						break;
					case "Control_Shift_F4":
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_F11") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F4")
								&& !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_p")) {
							processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						}
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F4")) {
							processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
							processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "START");
						}
						break;
					case "Alt_Shift_J":
						processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Manhattan", "START");
						break;	
					case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
						if(Integer.valueOf(whatToProcess.split(",")[4]) > 0) {
							if((Integer.valueOf(whatToProcess.split(",")[4])  == 5)){
								processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$7", "START");
								prevHighlightDirector = "7";
							}else {
								processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$"+whatToProcess.split(",")[4], "START");
								prevHighlightDirector = whatToProcess.split(",")[4];
							}
						}
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Change$Standings", "START");
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F4") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
								&& !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_p") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_F11")) {
							processAnimation(Constants.BACK, print_writers, "BG_Scale", "START");
						}
						break;
					}
					break;
				}
				break;
			case "highlightProfile":
				if(!prevHighlightDirector.isEmpty()) {
					if(Integer.valueOf(prevHighlightDirector) > 0) {
						processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$"+prevHighlightDirector, "CONTINUE");
					}
				}
				if(Integer.valueOf(whatToProcess.split(",")[4]) > 0) {
					if(Integer.valueOf(whatToProcess.split(",")[4]) == 5) {
						processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$7", "START");
						prevHighlightDirector = "7" ;
					}else {
						processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$"+whatToProcess.split(",")[4], "START");
						prevHighlightDirector = whatToProcess.split(",")[4];
					}
					
				}
				break;
			case "highlightLeader":
				if(Integer.valueOf(prevLeaderHighlight) > 0) {
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + prevLeaderHighlight, "SHOW 1.0");
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + prevLeaderHighlight, "CONTINUE");
				}
				TimeUnit.MILLISECONDS.sleep(200);
				if(Integer.valueOf(whatToProcess.split(",")[2].split("_")[0]) > 0) {
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player"+whatToProcess.split(",")[2].split("_")[0], "START");
					prevLeaderHighlight = whatToProcess.split(",")[2].split("_")[0];
				}
				break;
			case "Shift_F":case "Alt_b": case "y": case "Shift_O":
				processAnimation(Constants.FRONT, print_writers, "Bug_Change", "START");
				break;
			case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "START");
				break;
			case "Control_Shift_M": case "Control_Shift_L":
				processAnimation(Constants.FRONT, print_writers, "LT_MatchID$Change$Change_Out$BOTTOM_DATA", "START");
				processAnimation(Constants.FRONT, print_writers, "LT_MatchID$Change$Change_In$BOTTOM_DATA", "START");
				break;
			case "Control_Shift_O":
				processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI$Change", "START");
				break;
			case "Control_Shift_U": case "Control_Shift_V":
				processAnimation(Constants.FRONT, print_writers, "PopUps$Change", "START");
				break;
			case "Control_F3":
				processAnimation(Constants.FRONT, print_writers, "LT_Comparison$In_Out", "CONTINUE");
				this.isComp = "YES";
				break;
			case "Shift_I":
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$HEADER", "START");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$RIGHT_DATA", "START");
				
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$HEADER", "START");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$RIGHT_DATA", "START");
				break;
				
			case "F10": case "u": case "Control_Shift_Q":
			case "Alt_F8": case "F8": case "Control_F5": case "Control_h":
			case "F6": case "Control_F6": case "Shift_F6": case "Control_F9": case "F5":
			case "Shift_F5": case "Shift_F9": case "Alt_F12": case "F9":  case "d": case "e": case "F7": case "F11": case "Control_s": case "Control_f": 
				 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$LOGO", "START");
				 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$HEADER", "START");
				 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$BOTTOM_DATA", "START");
				 
				 switch (whatToProcess.split(",")[0]) {
				 case "Control_F5": case "F6": case "Control_F6": case "Shift_F6": case "Control_F9": case "F5": case "F9": case "Control_Shift_Q":
				 case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h": case "F7": case "F11": case "Control_s": case "Control_f":
					 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$RIGHT_DATA", "START");
					 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$RIGHT_DATA", "START");
					break;
				 }
				 
				 switch (whatToProcess.split(",")[0]) {
				 case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "F5": case "F7": case "F11": case "Control_s": case "Control_f": case "Control_h":
					 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$SUB_DATA", "START");
					 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$SUB_DATA", "START");
					break;
				} 
				 
				 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$LOGO", "START");
				 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$HEADER", "START");
				 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$BOTTOM_DATA", "START");
				 
				if(whatToProcess.split(",")[0].equalsIgnoreCase("F10") && caption.this_lowerThirdGfx.setPriceMoney) {
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$Prize_Head", "START");
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$Prize_Head", "START");
					caption.this_lowerThirdGfx.setPriceMoney = true;
				}else {
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$Prize_Head", "START");
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$Prize_Head", "START");
					caption.this_lowerThirdGfx.setPriceMoney = false;
				}
				 
				 if(caption.this_lowerThirdGfx.isPrev_impact() == true) {
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$IMPACT", "START");
					
					caption.this_lowerThirdGfx.setPrev_impact(false);
				}
				
				 if(caption.this_lowerThirdGfx.isImpact() == true) {
					 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$IMPACT", "START");
					
					caption.this_lowerThirdGfx.setImpact(true);
					caption.this_lowerThirdGfx.setPrev_impact(true);
				 }
				 
				break;	
			}
			break;
			
		case Constants.ICC_U19_2023:
			
			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			
			switch (whatToProcess.split(",")[0]) {
			case "Control_x": case "Control_z": case "z": case "x": case "c": case "v": 
				setVariousAnimationsKeys("CHANGE-ON", print_writers, config);
				if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
				}
				processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + prevWhichPlayer, "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "Change$Leader_Board" , "START");
//				processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2$Player" + whatToProcess.split(",")[2].split("_")[0], "START");
				processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2$Player" + whatToProcess.split(",")[2].split("_")[0], "SHOW 0.800");
				processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2$Player" + whatToProcess.split(",")[2].split("_")[0], "CONTINUE");
				prevWhichPlayer = whatToProcess.split(",")[2].split("_")[0];
				break;
			}
			switch(whatToProcess.split(",")[0]) {
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Shift_T": case "p": case "Control_p":
			case "Shift_F8": 	
				setVariousAnimationsKeys("CHANGE-ON", print_writers, config);
				processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
				processAnimation(Constants.BACK, print_writers, "Change$Footer", "START");
				if(whichGraphicOnScreen.contains(",")) {
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "F1": case "Control_Shift_A":  
						processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "START");
						break;
					case "F2":  
						processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "START");
						break;
					case "F4":
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
						if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0]) && 
								(caption.this_fullFramesGfx.whichSponsor != null || !caption.this_fullFramesGfx.whichSponsor.isEmpty())) {
							processAnimation(Constants.BACK, print_writers, "Sponsor", "START");
						}
						break;
					case "Control_F11":
						processAnimation(Constants.BACK, print_writers, "Change$Summary", "START");
						switch(whatToProcess.split(",")[0]) {
						case "Control_F11":
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Header_Shrink", "CONTINUE REVERSE");
							break;
						}
						break;
					case "p":
						processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "START");
						switch(whatToProcess.split(",")[0]) {
						case "p":
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Header_Shrink", "CONTINUE REVERSE");
							break;
						}
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Change$Standings", "START");
						switch(whatToProcess.split(",")[0]) {
						case "Control_p":
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Header_Shrink", "CONTINUE REVERSE");
							break;
						}
						break;
					case "Shift_T":
						processAnimation(Constants.BACK, print_writers, "Change$LineUp_Image", "START");
						break;
					}
				}
				if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					switch(whatToProcess.split(",")[0]) {
					case "F1": case "Control_Shift_A": 
						processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "START");
						break;
					case "F2":
						processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "START");
						break;
					case "F4":
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
						if(caption.this_fullFramesGfx.whichSponsor != null || !caption.this_fullFramesGfx.whichSponsor.isEmpty()) {
							processAnimation(Constants.BACK, print_writers, "Sponsor", "START");
						}
						break;
					case "Control_F11":
						processAnimation(Constants.BACK, print_writers, "Header_Shrink", "START");
						processAnimation(Constants.BACK, print_writers, "Change$Summary", "START");
						break;
					case "p":
						processAnimation(Constants.BACK, print_writers, "Header_Shrink", "START");
						processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "START");
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Header_Shrink", "START");
						processAnimation(Constants.BACK, print_writers, "Change$Standings", "START");
						break;
					}
				}
				if(caption.this_fullFramesGfx.numberOfRows != lastNumberOfRows) {
					processAnimation(Constants.BACK, print_writers, "ConcussExtend_Y", "START");
					lastNumberOfRows = caption.this_fullFramesGfx.numberOfRows;
				}
				break;
			case "F7": case "F11": 
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "START");
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "START");
				break;
			case "F5": case "F6": case "F9": case "l": case "n": case "a": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a": 
			case "Control_F3": case "Alt_k":  case "Alt_F1": case "Alt_F2": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": 
			case "Alt_F12":case "Shift_E": case "Control_g": case "Control_h": case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":
			case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "b": case "Alt_j": 
			case "Control_i": case "Alt_Shift_L": case "Shift_B": case "Control_Shift_F": case "Control_Shift_P": case "Alt_Shift_D": case "Alt_Shift_E":
			case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H": case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "START");
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 0.0");
				break;	
			 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s":
				 processAnimation(Constants.FRONT, print_writers, "Anim_NameSuperChange", "START");
				break;
			case "q": case "Control_q":
				processAnimation(Constants.FRONT, print_writers, "Anim_Boundary_LTChange", "START");
				break;
//			case "Alt_c":
//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage", "START");
//				break;
			case "Control_F8":
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TapedBall_In", "START");
				}
				break;
			
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": case "Alt_9": case "Alt_0":
				switch(whatToProcess.split(",")[0]) {
				case "Alt_1":
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.ICC_U19_2023:
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_Bottom_Left", "START");
						break;
					}
					
					break;
				case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_9": case "Alt_0":
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.ICC_U19_2023:
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo", "START");
						if(infobar.getRight_section()!= null && !infobar.getRight_section().isEmpty()) {
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$In_Out", "SHOW 0.0");
							infobar.setRight_section("");
						}
						break;
					}
					
					break;
				case "Alt_7":

					switch(config.getBroadcaster().toUpperCase()) {
					case Constants.ICC_U19_2023: 
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "START");
						break;
					}
					
					break;
				case "Alt_8":
					if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$In_Out", "CONTINUE");
						infobar.setRight_section("");
					}else {
						if(infobar.getRight_section()!= null && !infobar.getRight_section().isEmpty()) {
							TimeUnit.MILLISECONDS.sleep(200);
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "START");
							TimeUnit.MILLISECONDS.sleep(500);
						}else {
							processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$In_Out", "START");
						}
						infobar.setRight_section(whatToProcess.split(",")[2]);
					}
					break;
				}
				break;
			
			case "Shift_F":case "Alt_b": case "y": case "Shift_O":
				processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "START");
				break;
			case "Shift_F1": case "Shift_F2":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "START");
				break;
			}
			break;
		
		case Constants.BENGAL_T20:
			
			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			
			switch (whatToProcess.split(",")[0]) {
			 case "Control_Shift_U": case "Control_Shift_V":
					processAnimation(Constants.FRONT, print_writers, "Change_Popup", "START");
					break;
			 case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
			 case "Control_Shift_F8":
				setVariousAnimationsKeys("CHANGE-ON", print_writers, config);
				if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
				}
				processAnimation(Constants.BACK, print_writers, "Change$Sponsor" , "START");
				processAnimation(Constants.BACK, print_writers, "Change$Leaderboard" , "START");
				processAnimation(Constants.BACK, print_writers, "Change$Extra_Info" , "START");
				break;
			}
			switch(whatToProcess.split(",")[0]) {
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Ident_Change", "START");
				break;
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Shift_T": case "p": case "Control_p":
			case "Shift_F8": case "Control_Shift_F1": case "Control_Shift_F2": case "Control_Shift_F4":
				setVariousAnimationsKeys("CHANGE-ON", print_writers, config);
				
				if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F1") && whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F1")
						|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F2") && whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F2")
						|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F1") && whatToProcess.split(",")[0].equalsIgnoreCase("F1")
						|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F2") && whatToProcess.split(",")[0].equalsIgnoreCase("F2")
						|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F4") && whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")
						|| whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F4") && whatToProcess.split(",")[0].equalsIgnoreCase("F4")) {
					
				}else {
					processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Footer", "START");
				}
				if(whichGraphicOnScreen.contains(",")) {
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "F1": case "Control_Shift_A":  
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F1")) {
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						}
						switch(whatToProcess.split(",")[0]) {
						case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F1": case "Control_Shift_F2":
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE REVERSE");
							break;
						}
						break;
					case "F2": 
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						}
						switch(whatToProcess.split(",")[0]) {
						case "F2": case "F1": case "Control_Shift_A": case "Control_Shift_F1": case "Control_Shift_F2":
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE REVERSE");
							break;
						}
						break;
						
					case "Control_Shift_F1":
						
						if(whatToProcess.split(",")[0].equalsIgnoreCase("F1")) {
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						}
						
						switch(whatToProcess.split(",")[0]) {
						case "F2": case "F1": case "Control_Shift_A": case "Control_Shift_F2":
							break;
						case "Control_Shift_F1":
							if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
								processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side1$" + 
										caption.this_fullFramesGfx.pervious_batperformer_id, "CONTINUE REVERSE");
								processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side2$" + caption.this_fullFramesGfx.batperformer_id, "START");
								caption.this_fullFramesGfx.pervious_batperformer_id = caption.this_fullFramesGfx.batperformer_id;
							}
							else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
								
							}
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE REVERSE");
							break;
						}
						break;
						
					case "Control_Shift_F2":
						
						if(whatToProcess.split(",")[0].equalsIgnoreCase("F2")) {
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						}
						
						switch(whatToProcess.split(",")[0]) {
						case "F2": case "F1": case "Control_Shift_A": case "Control_Shift_F1":
							break;
						case "Control_Shift_F2":
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side1$" + 
									caption.this_fullFramesGfx.pervious_ballperformer_id, "CONTINUE REVERSE");
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side2$" + caption.this_fullFramesGfx.ballperformer_id, "START");
							caption.this_fullFramesGfx.pervious_ballperformer_id = caption.this_fullFramesGfx.ballperformer_id;
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "CONTINUE REVERSE");
							break;
						}
						break;
						
					case "F4":
						
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")) {
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						}
						
						switch(whatToProcess.split(",")[0]) {
						case "F4":
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
							break;
						}
						break;
					case "Control_F11":
						processAnimation(Constants.BACK, print_writers, "Change$Summary", "START");
						switch(whatToProcess.split(",")[0]) {
						case "Control_F11":
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "CONTINUE REVERSE");
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
							if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("trophy")) {
								processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "CONTINUE REVERSE");
							}
							break;
						}
						break;
					case "Control_Shift_F4":
						
						if(whatToProcess.split(",")[0].equalsIgnoreCase("F4")) {
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						}
						break;	
					case "p":
						processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "START");
						switch(whatToProcess.split(",")[0]) {
						case "p":
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "CONTINUE REVERSE");
							break;
						}
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Change$Standings", "START");
						switch(whatToProcess.split(",")[0]) {
						case "Control_p":
							break;
						default:
							processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "CONTINUE REVERSE");
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "START");
							processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "CONTINUE REVERSE");
							break;
						}
						break;
					case "Shift_T":
						processAnimation(Constants.BACK, print_writers, "Change$LineUp_Image", "START");
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.500");
						processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "START");
						//TimeUnit.MILLISECONDS.sleep(1000);
						break;
					}
					
				}
				if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					switch(whatToProcess.split(",")[0]) {
					case "F1": case "Control_Shift_A": 
						processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "START");
						processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "START");
						break;
					case "F2":
						processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "START");
						processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "START");
						break;
						
					case "Control_Shift_F1":
						
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F1")) {
							processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "START");
							TimeUnit.MILLISECONDS.sleep(800);
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}
						
						if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
							TimeUnit.MILLISECONDS.sleep(800);
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side2$" + caption.this_fullFramesGfx.batperformer_id, "START");
							caption.this_fullFramesGfx.pervious_batperformer_id = caption.this_fullFramesGfx.batperformer_id;
						}
						else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
							
						}
						break;
					case "Control_Shift_F2":
						
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F2")) {
							processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "START");
							processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "START");
							TimeUnit.MILLISECONDS.sleep(800);
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}
						
						if(caption.this_fullFramesGfx.ballperformer_id > 0) {
							TimeUnit.MILLISECONDS.sleep(800);
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side2$" + caption.this_fullFramesGfx.ballperformer_id, "START");
							caption.this_fullFramesGfx.pervious_ballperformer_id = caption.this_fullFramesGfx.ballperformer_id;
						}
						break;
						
					case "F4":
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
//						if(caption.this_fullFramesGfx.whichSponsor != null || !caption.this_fullFramesGfx.whichSponsor.isEmpty()) {
//							processAnimation(Constants.BACK, print_writers, "Sponsor", "START");
//						}
						break;
					case "Control_Shift_F4":
						
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("F4")) {
							processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
							TimeUnit.MILLISECONDS.sleep(800);
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "START");
						}
						
						if(caption.this_fullFramesGfx.ballperformer_id > 0) {
							TimeUnit.MILLISECONDS.sleep(800);
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Partnership_List$Side2$" + caption.this_fullFramesGfx.ballperformer_id, "START");
							caption.this_fullFramesGfx.pervious_ballperformer_id = caption.this_fullFramesGfx.ballperformer_id;
						}
						break;	
					case "Control_F11":
						processAnimation(Constants.BACK, print_writers, "Change$Summary", "START");
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "START");
						if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("trophy")) {
							processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "START");
						}
						break;
					case "p":
						processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "START");
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "START");
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Change$Standings", "START");
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "START");
						processAnimation(Constants.BACK, print_writers, "Anin_Trophy$In_Out", "START");
						break;
					}
				}
				if(caption.this_fullFramesGfx.numberOfRows != lastNumberOfRows) {
					//processAnimation(Constants.BACK, print_writers, "ConcussExtend_Y", "START");
					lastNumberOfRows = caption.this_fullFramesGfx.numberOfRows;
				}
				break;
			case "F7": case "F11": 
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "START");
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "START");
				break;
			case "F5": case "F6": case "F9": case "l": case "n": case "a": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a": case "Control_F3": 
			case "Alt_k":  case "Alt_F1": case "Alt_F2": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":case "Shift_E":
			case "Control_g": case "Control_h": case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f": case "Alt_F6": case "Shift_A": case "Shift_R": 
			case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "b": case "Alt_j": case "Control_i": case "Alt_Shift_L": case "Shift_B": case "Control_Shift_E": 
			case "Control_Shift_F": case "Control_Shift_P": case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s": case "Alt_Shift_F3": case "Alt_Shift_D":
			case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H": case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Badge", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Sublines", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Topline", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Dynamic", "START");
				break;	
			case "q": case "Control_q":
				processAnimation(Constants.FRONT, print_writers, "Anim_Boundary_LTChange", "START");
				break;
//			case "Alt_c":
//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage", "START");
//				break;
			case "Control_F8":
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TapedBall_In", "START");
				}
				break;
			case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": case "Alt_9": case "Alt_0":
			case "Control_Shift_(":
				switch(whatToProcess.split(",")[0]) {
				case "Alt_1":
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.BENGAL_T20:
						processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section3_Change", "START");
						TimeUnit.MILLISECONDS.sleep(200);
						infobar.setLeft_bottom(whatToProcess.split(",")[2]);
						break;
					}
					
					break;
				case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_6": case "Alt_9": case "Alt_0": case "Control_Shift_(":
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.BENGAL_T20:
						
						if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BATSMAN)) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Fade_For_Analytics", "CONTINUE");
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics", "CONTINUE");
//							infobar.setRight_section("");
							TimeUnit.MILLISECONDS.sleep(2000);
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Fade_For_Analytics", "SHOW 0.0");
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics", "SHOW 0.0");
						}else {
							if(infobar.getMiddle_section()!= null && !infobar.getMiddle_section().isEmpty()) {
								
								if(infobar.getMiddle_section().equalsIgnoreCase(CricketUtil.BATSMAN)) {
									processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Fade_For_Analytics", "START");
									processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics", "START");
								}else {
									processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics$Change", "START");
								}
//								infobar.setRight_section("");
							}else {
								processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Fade_For_Analytics", "START");
								processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics", "START");
								infobar.setMiddle_section(whatToProcess.split(",")[2]);
							}
						}
						infobar.setMiddle_section(whatToProcess.split(",")[2]);
						
						break;
					}
					
					break;
				case "Alt_7":
					switch(config.getBroadcaster().toUpperCase()) {
					case Constants.BENGAL_T20: 
						processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section1_Change", "START");
						infobar.setRight_bottom(whatToProcess.split(",")[2]);
						break;
					}
					break;
				case "Alt_8": case "Alt_5":
					if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
						processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Fade_For_Section_2", "CONTINUE");
						processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section_2", "CONTINUE REVERSE");
						infobar.setRight_section("");
						TimeUnit.MILLISECONDS.sleep(2000);
						processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Fade_For_Section_2", "SHOW 0.0");
					}else {
						if(infobar.getRight_section()!= null && !infobar.getRight_section().isEmpty()) {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section_2$Change", "START");
						}else {
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Fade_For_Section_2", "START");
							processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section_2$InOut", "START");
						}
						infobar.setRight_section(whatToProcess.split(",")[2]);
					}
					break;
				}
				break;
			
			case "Shift_F":case "Alt_b": case "y": case "Shift_O":
				processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "START");
				break;
			case "Shift_F1": case "Shift_F2":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "START");
				break;
			}
			break;
		}
		return CricketUtil.YES;
	}
	private String T20_VidarbhaChangeom(String whatToProcess, List<PrintWriter> print_writers, Configuration config) throws InterruptedException {
		if(!whatToProcess.contains(",")) {
			return CricketUtil.NO;
		}
		switch (whatToProcess.split(",")[0]) {
		case "Control_x": case "Control_z": case "z": case "x": case "c": case "v": case "Control_c": case "Control_v":
//			setVariousAnimationsKeys("CHANGE-ON", print_writers, config);
			if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
				processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
			}
			processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + prevWhichPlayer, "SHOW 1.574");
			processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + prevWhichPlayer, "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard" , "START");
//			processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2$Player" + whatToProcess.split(",")[2].split("_")[0], "START");
			processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2$Player" + whatToProcess.split(",")[2].split("_")[0], "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2$Player" + whatToProcess.split(",")[2].split("_")[0], "CONTINUE");
			prevWhichPlayer = whatToProcess.split(",")[2].split("_")[0];
			break;
		}
		
		switch(whatToProcess.split(",")[0]) {
		case "F1": case "Control_Shift_A": case "F2": case "F4": case "Shift_T":  case "Shift_F8": case "p": case "Control_p": case "Control_F11":
		case "Shift_K":
//			setVariousAnimationsKeys("CHANGE-ON", print_writers, config);
			switch (whatToProcess.split(",")[0]) {
			case "F1": case "F2": case "F4":
				processAnimation(Constants.BACK, print_writers, "Sponsor$Change", "START");
				break;
			default:
				processAnimation(Constants.BACK, print_writers, "Sponsor", "SHOW 0.0");
				break;
			}
			processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
//			processAnimation(Constants.BACK, print_writers, "Change$Footer", "START");
			if(whichGraphicOnScreen.contains(",")) {
				switch(whichGraphicOnScreen.split(",")[0]) {
				case "F1": case "Control_Shift_A":  
					processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "START");
					break;
				case "F2":  
					processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "START");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Change$Partnership", "START");
					break;	
				case "Control_F11":
					processAnimation(Constants.BACK, print_writers, "Change$Summary", "START");
					switch(whatToProcess.split(",")[0]) {
					case "Control_F11":
						break;
					default:
						processAnimation(Constants.BACK, print_writers, "Header_Shrink", "CONTINUE REVERSE");
						break;
					}
					break;
				case "p":
					processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "START");
					switch(whatToProcess.split(",")[0]) {
					case "p":
						break;
					default:
						processAnimation(Constants.BACK, print_writers, "Header_Shrink", "CONTINUE REVERSE");
						break;
					}
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Change$Standings", "START");
					switch(whatToProcess.split(",")[0]) {
					case "Control_p":
						break;
					default:
						processAnimation(Constants.BACK, print_writers, "Header_Shrink", "CONTINUE REVERSE");
						break;
					}
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Change$Team_Single", "START");
					//TimeUnit.MILLISECONDS.sleep(1000);
					break;
				case "Shift_F8":
					processAnimation(Constants.BACK, print_writers, "Change$LineUp_Image", "START");
					//TimeUnit.MILLISECONDS.sleep(1000);
					break;	
				}
			}
			if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
				switch(whatToProcess.split(",")[0]) {
				case "F1": case "Control_Shift_A": 
					processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "START");
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "START");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "START");
					break;
				case "Control_F11":
//					processAnimation(Constants.BACK, print_writers, "Header_Shrink", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Summary", "START");
					break;
				case "p":
					processAnimation(Constants.BACK, print_writers, "Header_Shrink", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "START");
					break;
				case "Control_p":
//					processAnimation(Constants.BACK, print_writers, "Header_Shrink", "START");
					processAnimation(Constants.BACK, print_writers, "Change$Standings", "START");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Change$Partnership", "START");
					break;	
				}
			}
//			if(caption.this_fullFramesGfx.numberOfRows != lastNumberOfRows) {
//				processAnimation(Constants.BACK, print_writers, "ConcussExtend_Y", "START");
//				lastNumberOfRows = caption.this_fullFramesGfx.numberOfRows;
//			}
			break;
		case "Shift_I":
			processAnimation(Constants.FRONT, print_writers, "Change", "START");
			break;	
		case "F7": case "F11": case "Control_s": case "Control_f":
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "START");
			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "START");
			break;
		case "F5": case "F6": case "F9": case "l": case "a": case "Control_F2": //case "n":
		case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_o":  case "Alt_F1": case "Alt_F2":
		case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":case "Shift_E":
		case "Control_g": case "Control_h": case "Control_F6": case "Shift_F6": case "b":
		case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i":
		case "Alt_j": case "Control_i": case "Alt_Shift_L":
		case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
		case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "START");
			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 0.0");
			break;	
		 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s":
			 switch (config.getBroadcaster().toUpperCase()) {
				case Constants.VIDARBHA:
					processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "CONTINUE REVERSE");
					processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third$Top_Header", "SHOW 0.0");
					break;
			 }
			break;
		case "q": case "Control_q":
			processAnimation(Constants.FRONT, print_writers, "Anim_Boundary_LTChange", "START");
			break;
		case "Shift_F7": case "Control_Shift_F9":
			processAnimation(Constants.FRONT, print_writers, "Anim_Image_LtChange", "START");
			break;	
		case "Alt_c":
			processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage", "START");
			break;
		case "Shift_F12":
			processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo$Change", "START");
			break;
		case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": case "Alt_9": case "Alt_0":
			switch(whatToProcess.split(",")[0]) {
			case "Alt_1": case "Alt_9": case "Alt_0":
				if(infobar.getFull_section() != null && !infobar.getFull_section().trim().isEmpty()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Change", "START");
				}else {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_In", "START");
					if(infobar.getMiddle_section() != null && !infobar.getMiddle_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_In", "SHOW 0.0");
					}
					if(infobar.getRight_bottom() != null && !infobar.getRight_bottom().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_In", "SHOW 0.0");
					}
					infobar.setMiddle_section("");
					infobar.setRight_bottom("");
				}
				infobar.setFull_section(whatToProcess.split(",")[2]);
				break;
			case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6":
				if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BATSMAN)) {
					
					if(infobar.getFull_section() != null && !infobar.getFull_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Out", "START");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setFull_section("");
						infobar.setMiddle_section("");
					}else {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_Out", "START");
						infobar.setMiddle_section("");
						infobar.setFull_section("");
					}
				}else {
					if(infobar.getFull_section()!= null && !infobar.getFull_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Out", "START");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setFull_section("");
					}
					
					if(infobar.getMiddle_section() != null && !infobar.getMiddle_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_Change", "START");
					}else {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_In", "START");
					}
					infobar.setMiddle_section(whatToProcess.split(",")[2]);
				}
				
				break;
			case "Alt_7":
				if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
					if(infobar.getFull_section() != null && !infobar.getFull_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Out", "START");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setFull_section("");
						infobar.setRight_bottom("");
					}else {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_Out", "START");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setFull_section("");
						infobar.setRight_bottom("");
					}
				}else {
					if(infobar.getFull_section()!= null && !infobar.getFull_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Out", "START");
						infobar.setFull_section("");
					}
					
					if(infobar.getRight_bottom() != null && !infobar.getRight_bottom().trim().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_Change", "START");
					}else {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_In", "START");
					}
					infobar.setRight_bottom(whatToProcess.split(",")[2]);
				}
				
				break;
			case "Alt_8":
				if(whatToProcess.split(",")[2].equalsIgnoreCase("TARGET")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Target_Out", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Target_In", "START");
//					infobar.setRight_section("");
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("TAPED_BALL")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TapedBall_In", "START");
//					infobar.setRight_section("");
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("EQUATION")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$ToWin$In", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$ToWin$In", "START");
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("TIMELINE")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TimeLine", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TimeLine", "START");
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("SUPER_OVER")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TOPRIGHT_FREETEXT_In", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TOPRIGHT_FREETEXT_In", "START");
				}
				infobar.setRight_section(whatToProcess.split(",")[2]);
				infobar.setLast_right_section(whatToProcess.split(",")[2]);
				break;
			}
			break;
		
		case "Shift_F":case "Alt_b": case "y": case "Shift_O":
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "START");
			break;
		case "Shift_F1": case "Shift_F2":
			processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "START");
			break;
		}
		return CricketUtil.YES;
	}

	public String CutBack(String whatToProcess,List<PrintWriter> print_writers, Configuration config) throws InterruptedException, IOException
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.VIDARBHA:

			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			
			switch(whatToProcess.split(",")[0]) {
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo$Change", "SHOW 0.0");
				break;
			case "Shift_F1": case "Shift_F2":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "SHOW 0.0");
				break;
			case "Shift_F":case "Alt_b": case "y": case "Shift_O":
				processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_1": case "Alt_9": case "Alt_0":
				TimeUnit.MILLISECONDS.sleep(500);
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Change", "SHOW 0.0");
				break;
			case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": 
				TimeUnit.MILLISECONDS.sleep(1000);
				if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BATSMAN) && 
						infobar.getMiddle_section() != null && !infobar.getMiddle_section().isEmpty()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_Change", "SHOW 0.0");
				}
				break;
			case "Alt_7":
				if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER) && 
						infobar.getRight_bottom() != null && !infobar.getRight_bottom().isEmpty()) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_Change", "SHOW 0.0");
				}
				break;
			case "Alt_8":
//				if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
//					if(infobar.getRight_section()!= null && !infobar.getRight_section().isEmpty()) {
//						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "SHOW 0.0");
//					}
//				}
				break;
			case "Control_s": case "Control_f": case "F7": case "F11":
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.ICC_U19_2023:
					processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
					this.whichGraphicOnScreen = whatToProcess;
					break;
				case Constants.ISPL:
					processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "SHOW 0.900");
					this.whichGraphicOnScreen = whatToProcess;
					break;
			 }
			break;
			case "F5": case "F6": case "F9": case "Control_g": case "Control_h": case "Alt_F1": case "Alt_F2":
			case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_o": case "Control_F2":
			case "Control_F6": case "Shift_F6": case "l": case "a": //case "n":
			case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "b":
			case "Alt_j":	
				switch (config.getBroadcaster().toUpperCase()) {
					case Constants.ICC_U19_2023:
						processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
						this.whichGraphicOnScreen = whatToProcess;
						break;
					case Constants.ISPL:
						processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
						processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Lt_X_Position", "SHOW 0.900");
						this.whichGraphicOnScreen = whatToProcess;
						break;
				 }
				break;
			 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s":
				 switch (config.getBroadcaster().toUpperCase()) {
					case Constants.ICC_U19_2023:
						processAnimation(Constants.FRONT, print_writers, "Anim_NameSuperChange", "SHOW 0.0");
						this.whichGraphicOnScreen = whatToProcess;
						break;
					case Constants.ISPL:
						processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
						this.whichGraphicOnScreen = whatToProcess;
						break;
				 }
				break;
			 case "Shift_F7": case "Control_Shift_F9":
				 processAnimation(Constants.FRONT, print_writers, "Anim_Image_LtChange", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				 break;
			 case "Control_x": case "Control_z": case "z": case "x": case "c": case "v":
				 if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					 processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
				 }
				 processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LeaderBoard", "SHOW 2.2400");
				 processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard", "SHOW 0.0");
				
				 for(int iPlyr = 1; iPlyr <= 5; iPlyr++) {
					 if(iPlyr == Integer.valueOf(prevWhichPlayer)) {
						 processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + iPlyr, "SHOW 1.740");
					 } else {
						 processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + iPlyr, "SHOW 0.00");
					 }
				 }
				processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2", "SHOW 0.0");
				 
//				 setVariousAnimationsKeys("CUT-BACK", print_writers, config);
				 this.whichGraphicOnScreen = whatToProcess;
				 break;
				
			 case "F1": case "Control_Shift_A": case "F2": case "F4": case "Shift_T": case "p": case "Control_p": 
			 case "Shift_F8": case "Control_F11": case "Shift_K":
				 TimeUnit.MILLISECONDS.sleep(500);
					processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
//					processAnimation(Constants.BACK, print_writers, "Change$Footer", "SHOW 0.0");
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "F1": case "Control_Shift_A": 
						processAnimation(Constants.BACK, print_writers, "Sponsor$In_Out", "SHOW 1.940");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Batting_Card", "SHOW 2.240");
						processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Sponsor$Change", "SHOW 0.0");
						break;
					case "F2":  
						processAnimation(Constants.BACK, print_writers, "Sponsor$In_Out", "SHOW 1.940");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Bowling_Card", "SHOW 2.240");
						processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Sponsor$Change", "SHOW 0.0");
						break;
					case "F4":
						processAnimation(Constants.BACK, print_writers, "Sponsor$In_Out", "SHOW 1.940");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "SHOW 2.240");
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Sponsor$Change", "SHOW 0.0");
						break;
					case "Control_F11":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "SHOW 2.240");
						processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
						break;
					case "Shift_K":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership", "SHOW 2.240");
						processAnimation(Constants.BACK, print_writers, "Change$Partnership", "SHOW 0.0");
						break;	
					case "Shift_T":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Team_Single", "SHOW 2.240");
						processAnimation(Constants.BACK, print_writers, "Change$Team_Single", "SHOW 0.0");
						break;
					case "Shift_F8":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$LineUp_Image", "SHOW 2.240");
						processAnimation(Constants.BACK, print_writers, "Change$LineUp_Image", "SHOW 0.0");
						break;	
					case "p":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "SHOW 2.240");
						processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "SHOW 0.0");
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Standings", "SHOW 2.240");
						processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
						break;
					}
					if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
						switch(whatToProcess.split(",")[0]) {
						case "F1": case "Control_Shift_A":  
//							processAnimation(Constants.BACK, print_writers, "Header_Shrink", "SHOW 0.0");
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "SHOW 2.240");
							processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "SHOW 0.0");
							break;
						case "F2":
//							processAnimation(Constants.BACK, print_writers, "Header_Shrink", "SHOW 0.0");
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "SHOW 2.240");
							processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "SHOW 0.0");
							break;
						case "F4":
							processAnimation(Constants.BACK, print_writers, "Header_Shrink", "SHOW 0.0");
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "SHOW 2.240");
							processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
							break;
						case "Control_F11":
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "SHOW 2.240");
							processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
							break;
						case "Shift_K":
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership", "SHOW 2.240");
							processAnimation(Constants.BACK, print_writers, "Change$Partnership", "SHOW 0.0");
							break;	
						case "p":
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "SHOW 2.240");
							processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "SHOW 0.0");
							break;
						case "Control_p":
							processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Standings", "SHOW 2.240");
							processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
							break;
						}
					}
//					setVariousAnimationsKeys("CUT-BACK", print_writers, config);
//					processAnimation(Constants.BACK, print_writers, "ConcussExtend_Y", "SHOW 0.0");
				
				this.whichGraphicOnScreen = whatToProcess;
				break;
			}
			break;	
		case Constants.T20_MUMBAI:
			T20_MumbaiCutBack(whatToProcess, print_writers, config);
			break;
		case Constants.ISPL:
			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			switch(whatToProcess.split(",")[0]) {
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Change_Bottom", "SHOW 0.0");
				break;
			case "Alt_1":
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section3_Change", "SHOW 0.0");
				break;
//			case "Alt_c":
//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage_Change", "SHOW 0.0");
//				break;
			case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_9": case "Alt_0": case "Alt_c":
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_CenterInfo", "SHOW 0.0");
				break;
			case "Alt_7":
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "SHOW 0.0");
				break;
			case "Alt_8":
				if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
					if(infobar.getRight_section()!= null && !infobar.getRight_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "SHOW 0.0");
					}
				}
				break;
				
			case "F1": case "Shift_T": case "Control_F10": case "F4": case "Shift_K": case "Control_F7": case "Control_F11": case "Shift_F11":
			case "F2": case "Alt_F11": case "Shift_F10": case "z": case "x": case "c": case "v": case "Alt_F5":
				switch(whatToProcess.split(",")[0]) {
				case "F1":
					if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("NORMAL")) {
						processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BatStyle2", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$BatStyle2", "SHOW 0.0");
					}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("SPLIT")) {
						processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$SplitBatBall_Card", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$SplitBatBall_Card", "SHOW 0.0");
					}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("TRADITIONAL")) {
						processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BattingCard_Normal", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Normal", "SHOW 0.0");
					}else {
						processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BatStyle3", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "SHOW 0.0");
					}
					whichScorecard = caption.this_fullFramesGfx.WhichScoreCard;
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BowlingCard_Normal", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$BowlingCard_Normal", "SHOW 0.0");
					break;	
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$PlayingXI", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$PlayingXI", "SHOW 0.0");
					break;
				case "Control_Shift_F7":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$LineUp_Single", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$LineUp_Single", "SHOW 0.0");
					break;	
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Manhattan", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Manhattan", "SHOW 0.0");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Partnership_List", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Partnership", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Partnership", "SHOW 0.0");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Both_Team", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Both_Team", "SHOW 0.0");
					break;
				case "Control_F11": case "Shift_F11":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Summary", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
					break;
				case "Alt_F11":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Doublemanhattan", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Doublemanhattan", "SHOW 0.0");
					break;
				case "Alt_F5":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$DoubleTeamManhattan", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$DoubleTeamManhattan", "SHOW 0.0");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Worm", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Worm", "SHOW 0.0");
					break;
				case "z": case "x": case "c": case "v":
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$LeaderBoard_3Col", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard_3Col", "SHOW 0.0");
					break;
				}
				switch(whichGraphicOnScreen.split(",")[0]) {
				case "F1":
					if(whichScorecard.equalsIgnoreCase("NORMAL")) {
						processAnimation(Constants.BACK, print_writers, "Change$BatStyle2", "SHOW 0.0");
					}else if(whichScorecard.equalsIgnoreCase("SPLIT")) {
						processAnimation(Constants.BACK, print_writers, "Change$SplitBatBall_Card", "SHOW 0.0");
					}else if(whichScorecard.equalsIgnoreCase("TRADITIONAL")) {
						processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Normal", "SHOW 0.0");
					}else {
						processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "SHOW 0.0");
					}
					whichScorecard = caption.this_fullFramesGfx.WhichScoreCard;
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "Change$BowlingCard_Normal", "SHOW 0.0");
					break;	
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Change$PlayingXI", "SHOW 0.0");
					break;
				case "Control_Shift_F7":
					processAnimation(Constants.BACK, print_writers, "Change$LineUp_Single", "SHOW 0.0");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "Change$Manhattan", "SHOW 0.0");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Change$Partnership", "SHOW 0.0");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "Change$Both_Team", "SHOW 0.0");
					break;
				case "Control_F11":
					processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
					break;
				case "Alt_F11":
					processAnimation(Constants.BACK, print_writers, "Change$Doublemanhattan", "SHOW 0.0");
					break;
				case "Alt_F5":
					processAnimation(Constants.BACK, print_writers, "Change$DoubleTeamManhattan", "SHOW 0.0");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Change$Worm", "SHOW 0.0");
					break;
				case "z": case "x": case "c": case "v":
					processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard_3Col", "SHOW 0.0");
					break;
				}
				processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$Footer", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$Wings", "SHOW 0.0");
				caption.captionWhichGfx = whatToProcess.split(",")[0];
				caption.this_fullFramesGfx.whichGFX = whatToProcess.split(",")[0];
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a": case "Control_F3": 
			case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h":  case "Control_F6": 
			case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": case "F8": case "F10": case "a":
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			}
			break;
		case Constants.LEGENDS:
			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			switch(whatToProcess.split(",")[0]) {
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Change_Bottom", "SHOW 0.0");
				break;
			case "Alt_1":
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section3_Change", "SHOW 0.0");
				break;
			case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_9": case "Alt_0": case "Alt_c":
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_CenterInfo", "SHOW 0.0");
				break;
			case "Alt_7":
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "SHOW 0.0");
				break;
			case "Alt_8":
				if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
					if(caption.this_infobarGfx.infobar.getRight_section() != null && !caption.this_infobarGfx.infobar.getRight_section().isEmpty()) {
						TimeUnit.MILLISECONDS.sleep(500);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "SHOW 0.0");
						TimeUnit.MILLISECONDS.sleep(500);
					}
				}
				break;
			//Mini's
			case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":case "Alt_Shift_F8": case "Alt_f":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "SHOW 0.0");
				break;
			//Bugs
			case "Shift_F":case "Alt_b": case "y": case "Shift_O":
				processAnimation(Constants.FRONT, print_writers, "Bug_In", "SHOW 0.714");
				TimeUnit.MILLISECONDS.sleep(100);
				processAnimation(Constants.FRONT, print_writers, "Bug_Change", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			//pop-Up
			case "Control_Shift_U": case "Control_Shift_V":
				processAnimation(Constants.FRONT, print_writers, "PopUps$Change", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			//LT
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a": case "Control_F3": 
			case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h":  case "Control_F6": 
			case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": case "F8": case "F10": case "a":case "Alt_Shift_F5":
			case "Alt_Shift_F7": case "Alt_Shift_F6": case "Alt_d": case "Alt_Shift_F3":
				processAnimation(Constants.FRONT, print_writers, "anim_LT_Change", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			//FF
			case "F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Shift_T":  case "Control_F7": case "m": case "Control_m": case "Shift_F10": 
			case "Control_p":case "Control_F10":case "Control_Shift_F5": case "Control_Shift_D":case "Alt_Shift_F4":case "Control_d": case "Control_e": case "Shift_P": 
			case "Shift_Q": case "z": case "x": case "c": case "v":case "Control_z": case "Control_x": case "Control_Shift_F8":case "Control_Shift_K":
			case "Control_Shift_Z": case "Control_Shift_Y":
				processAnimation(Constants.BACK, print_writers, "anim_Change$Event_Logo", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "anim_Change$Logo_FF", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "anim_Change$Header", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "anim_Change$SubHeader", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "anim_Change$Footer", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Sponsor$Change", "SHOW 0.0");
				
				switch(whichGraphicOnScreen.split(",")[0]) {
				case "m": case "Control_m":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Match_Id", "SHOW 0.0");
					break;
				case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8":
				case "Control_Shift_Z": case "Control_Shift_Y":
					processAnimation(Constants.BACK, print_writers, "anim_Change$LeaderBoard", "SHOW 0.0");
					break;
				case "Control_Shift_K":
					processAnimation(Constants.BACK, print_writers, "anim_Change$PlayOff", "SHOW 0.0");
					break;
				case "Alt_Shift_F4":
					processAnimation(Constants.BACK, print_writers, "anim_Change$TeamsAll", "SHOW 0.0");
					break;
				case "Control_d": case "Control_e":case "Shift_P": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "anim_Change$PlayerProfile", "SHOW 0.0");
					break;
				case "Control_Shift_D":
					processAnimation(Constants.BACK, print_writers, "anim_Change$DoubleMatchId", "SHOW 0.0");
					break;
				case "F1":
					processAnimation(Constants.BACK, print_writers, "anim_Change$BattingCard", "SHOW 0.0");
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "anim_Change$BowlingCard", "SHOW 0.0");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Manhattan", "SHOW 0.0");
					break;
				case "Control_F11":case "Control_Shift_F5":
					processAnimation(Constants.BACK, print_writers, "anim_Change$MatchSummary", "SHOW 0.0");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "anim_Change$ParnershipList", "SHOW 0.0");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Partnership", "SHOW 0.0");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "anim_Change$TeamImage", "SHOW 0.0");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Teams", "SHOW 0.0");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Worm", "SHOW 0.0");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "anim_Change$Standings", "SHOW 0.0");
					break;
				}
				
				switch(whatToProcess.split(",")[0]) {
				case "m": case "Control_m":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Match_Id", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$MatchId", "SHOW 0.0");
					break;
				case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8":
				case "Control_Shift_Z": case "Control_Shift_Y":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Leaderboard", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$LeaderBoard", "SHOW 0.0");
					break;
				case "Alt_Shift_F4":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$TeamsAll", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$TeamsAll", "SHOW 0.0");
					break;
				case "Control_Shift_K":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$PlayOff", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$PlayOff", "SHOW 0.0");
					break;
				case "Control_Shift_D":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$DoubleMatchId", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$DoubleMatchId", "SHOW 0.0");
					break;
				case "Control_d": case "Control_e":case "Shift_P": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$PlayerProfile", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$PlayerProfile", "SHOW 0.0");
					break;
				case "F1":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$BattingCard", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$BattingCard", "SHOW 0.0");
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$BowlingCard", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$BowlingCard", "SHOW 0.0");
					break;
				case "Control_F11":case "Control_Shift_F5":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$MatchSummary", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$MatchSummary", "SHOW 0.0");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Manhattan", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$Manhattan", "SHOW 0.0");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$ParnershipList", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$ParnershipList", "SHOW 0.0");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Partnership", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$Partnership", "SHOW 0.0");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$TeamImage", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$TeamImage", "SHOW 0.0");
					break;
				case "Control_F7":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Teams", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$Teams", "SHOW 0.0");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Worm", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$Worm", "SHOW 0.0");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "anim_FullFrame$In_Out$Standings", "SHOW 2.000");
					processAnimation(Constants.BACK, print_writers, "anim_Change$Standings", "SHOW 0.0");
					break;
				}
				this.whichGraphicOnScreen = whatToProcess;
				break;	
			}
			break;
			
		case Constants.NPL: case Constants.MPL: case Constants.APL:

			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			
			switch(whatToProcess.split(",")[0]) {
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$Change_Bottom", "SHOW 0.0");
				break;
			case "Alt_1":
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.MPL: case Constants.APL:
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section3_Change", "SHOW 0.0");
					break;
				}
				break;
//			case "Alt_c":
//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage_Change", "SHOW 0.0");
//				break;
			case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_9": case "Alt_0": case "Alt_c":
				TimeUnit.MILLISECONDS.sleep(1000);
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.MPL: case Constants.APL:
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_CenterInfo", "SHOW 0.0");
					break;
				}
				
				break;
			case "Alt_7":
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.NPL: case Constants.MPL:case Constants.APL:
					TimeUnit.MILLISECONDS.sleep(500);
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "SHOW 0.0");
					break;
				}
				break;
			case "Alt_8":
				if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
					if(infobar.getRight_section()!= null && !infobar.getRight_section().isEmpty()) {
						TimeUnit.MILLISECONDS.sleep(2000);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "SHOW 0.0");
						TimeUnit.MILLISECONDS.sleep(500);
					}
				}
				break;
			case "Alt_Shift_W": case "Shift_L":
				processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "SHOW 0.0");
				switch(whatToProcess.split(",")[0]) {
				case "Shift_L":
					switch(whatToProcess.split(",")[2]) {
					case "MOST RUNS": case "MOST WICKETS":
						if(whichGraphicOnScreen.split(",")[2].equalsIgnoreCase("MOST RUNS") || 
								whichGraphicOnScreen.split(",")[2].equalsIgnoreCase("MOST WICKETS")) {
							//processAnimation(Constants.BACK, print_writers, "Change$Logo", "SHOW 0.0");
						}
						break;
					}
					break;
				}
				processAnimation(Constants.BACK, print_writers, "Change$Row_Col", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
			case "Control_Shift_F8":	
				processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "SHOW 0.0");
				
				switch(whatToProcess.split(",")[0]) {
				case "Control_Shift_F8":
					if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("z") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("x")) {
						processAnimation(Constants.BACK, print_writers, "Change$Logo", "SHOW 0.0");
					}
					break;
				}
				
				processAnimation(Constants.BACK, print_writers, "Change$Leader_Board", "SHOW 0.0");
				
				if(Integer.valueOf(whatToProcess.split(",")[2].split("_")[0]) > 0) {
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player"+whatToProcess.split(",")[2].split("_")[0], "SHOW 1.000");
				}
				processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2$Player"+whatToProcess.split(",")[2].split("_")[0], "SHOW 0.00");
				if(Integer.valueOf(prevLeaderHighlight) != Integer.valueOf(whatToProcess.split(",")[2].split("_")[0])) {
					processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player"+prevLeaderHighlight, "SHOW 0.00");
				}
				prevLeaderHighlight = whatToProcess.split(",")[2].split("_")[0];
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_F1": case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F2": case "Control_F11": case "F4": case "Control_Shift_F4":
			case "Shift_T":	case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q": case "Shift_F8": case "Control_p": case "Alt_Shift_J":
				switch(whichGraphicOnScreen.split(",")[0]) {
				case "F1": case "Control_Shift_A": case "Control_Shift_F1":
					if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F1")) {
						processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "SHOW 0.0");
					}else {
						processAnimation(Constants.BACK, print_writers, "Change$BattingCard", "SHOW 0.0");
					}
					break;
				case "F2": case "Control_Shift_F2":
					if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
						processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "SHOW 0.0");
					}else {
						processAnimation(Constants.BACK, print_writers, "Change$BowlingCard", "SHOW 0.0");
					}
					break;
				case "Alt_Shift_J":
					processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Manhattan", "SHOW 0.0");
					break;	
				case "Control_F11":
					processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
					break;
				case "F4": case "Control_Shift_F4":
					if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")) {
						processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "SHOW 0.0");
					}else {
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
					}
					break;
				case "Shift_T": case "Alt_z": case "Shift_F8":
					processAnimation(Constants.BACK, print_writers, "Change", "SHOW 0.0");
					break;	
				case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "Change$Profile", "SHOW 0.0");
					if(Integer.valueOf(whatToProcess.split(",")[4]) > 0) {
						if(Integer.valueOf(whatToProcess.split(",")[4]) == 5) {
							processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$7", "SHOW 0.0");
						}else {
							processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$"+whatToProcess.split(",")[4], "SHOW 0.0");
						}
						
					}
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
					break;
				}
				switch(whatToProcess.split(",")[0]) {
				case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Profile", "SHOW 3.0");
					if(Integer.valueOf(whatToProcess.split(",")[4]) > 0) {
						if(Integer.valueOf(whatToProcess.split(",")[4]) == 5) {
							processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$7", "START");
						}else {
							processAnimation(Constants.BACK, print_writers, "Profile_Highlight$Side1$"+whatToProcess.split(",")[4], "START");
						}
						
					}
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$LineUp_Image", "SHOW 3.0");
					break;
				case "F1": case "Control_Shift_A": case "Control_Shift_F1":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$BattingCard", "SHOW 3.0");
					processAnimation(Constants.BACK, print_writers, "Change$BattingCard", "SHOW 0.0");
					if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F1")) {
						processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "SHOW 0.0");
					}
					break;
				case "F2": case "Control_Shift_F2":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$BowlingCard", "SHOW 3.0");
					processAnimation(Constants.BACK, print_writers, "Change$BowlingCard", "SHOW 0.0");
					if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
						processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "SHOW 0.0");
					}
					break;
				case "Alt_Shift_J":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$BattingCard_Manhattan", "SHOW 3.0");
					processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Manhattan", "SHOW 0.0");
					break;		
				case "Control_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Summary", "SHOW 3.0");
					processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
					break;
				case "F4": case "Control_Shift_F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Partnership_List", "SHOW 3.0");
					processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
					if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")) {
						processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "SHOW 0.0");
					}
					break;
				case "Alt_z":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Teams", "SHOW 3.0");
					processAnimation(Constants.BACK, print_writers, "Change$Teams", "SHOW 0.0");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Main$Standings", "SHOW 3.0");
					processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
					break;
				}
				if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_p")) {
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "SHOW 3.000");
				}
				if(whatToProcess.split(",")[0].equalsIgnoreCase("Control_p")) {
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Logo", "SHOW 0.000");
				}
				processAnimation(Constants.BACK, print_writers, "Change$Logo", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$ExtraData", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$Footer", "SHOW 0.0");
				
				caption.captionWhichGfx = whatToProcess.split(",")[0];
				caption.this_fullFramesGfx.whichGFX = whatToProcess.split(",")[0];
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "SHOW 0.0");
				break;
			case "Shift_F":case "Alt_b": case "y": case "Shift_O":
				processAnimation(Constants.FRONT, print_writers, "Bug_In", "SHOW 0.714");
				TimeUnit.MILLISECONDS.sleep(100);
				processAnimation(Constants.FRONT, print_writers, "Bug_Change", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_O":
				processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI$Change", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Control_Shift_U": case "Control_Shift_V":
				processAnimation(Constants.FRONT, print_writers, "PopUps$Change", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_I":
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$HEADER", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$RIGHT_DATA", "SHOW 0.0");
				
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$HEADER", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_In$RIGHT_DATA", "SHOW 0.0");
				
				this.whichGraphicOnScreen = whatToProcess;
				System.out.println("this.whichGraphicOnScreen - " + this.whichGraphicOnScreen);
				break;
				
			case "F10": case "u": case "Control_h": case "Control_Shift_Q": case "Alt_F8": case "F8": case "Control_F5": case "F6": case "Control_F6": 
			case "Shift_F6": case "Control_F9": case "F5": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "F9":  case "d": case "e": case "F7": 
			case "F11": case "Control_s": case "Control_f":
				 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change", "SHOW 0.0");
				 
				if(whatToProcess.split(",")[0].equalsIgnoreCase("F10") && caption.this_lowerThirdGfx.setPriceMoney) {
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$Prize_Head", "SHOW 0.320");
					caption.this_lowerThirdGfx.setPriceMoney = true;
				}else {
					processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$Prize_Head", "SHOW 0.320");
					caption.this_lowerThirdGfx.setPriceMoney = false;
				}
				 
				 if(caption.this_lowerThirdGfx.isPrev_impact() == false) {
					 processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out$IMPACT", "SHOW 0.320"); 
				 }
				break;
			case "Control_Shift_M": case "Control_Shift_L":
				processAnimation(Constants.FRONT, print_writers, "LT_MatchID$Change$Change_Out$BOTTOM_DATA", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "LT_MatchID$Change$Change_In$BOTTOM_DATA", "SHOW 0.0");
				break;	
			}
			break;
		case Constants.ICC_U19_2023:

			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			
			switch(whatToProcess.split(",")[0]) {
			case "Shift_F1": case "Shift_F2":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "SHOW 0.0");
				break;
			case "Shift_F":case "Alt_b": case "y": case "Shift_O":
				processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo$Change", "SHOW 0.0");
				break;
			case "Alt_1":
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.ICC_U19_2023:
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_Bottom_Left", "SHOW 0.0");
					break;
				}
				break;
//			case "Alt_c":
//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage_Change", "SHOW 0.0");
//				break;
			case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_9": case "Alt_0":
				TimeUnit.MILLISECONDS.sleep(1000);
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.ICC_U19_2023:
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo", "SHOW 0.0");
					break;
				}
				
				break;
			case "Alt_7":
				switch (config.getBroadcaster().toUpperCase()) {

				case Constants.ICC_U19_2023:
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Change_RightInfo_BottomRightPart", "SHOW 0.0");
					break;
				}
				break;
			case "Alt_8":
				if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
					if(infobar.getRight_section()!= null && !infobar.getRight_section().isEmpty()) {
						//processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section_2$Change", "SHOW 0.0");
						TimeUnit.MILLISECONDS.sleep(1400);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "SHOW 0.0");
						TimeUnit.MILLISECONDS.sleep(600);
					}
				}
				break;
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_g": case "Control_h": case "Alt_F1": case "Alt_F2": case "Control_F5": case "Control_F9": 
			case "Control_a":  case "Control_F3": case "Alt_k": case "Control_F2": case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f": case "l": case "n": 
			case "a": case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "b": case "Alt_j":	case "Control_Shift_F":
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s":
				 processAnimation(Constants.FRONT, print_writers, "Anim_NameSuperChange", "SHOW 0.0");
					this.whichGraphicOnScreen = whatToProcess;
				break;
				
			 case "Control_x": case "Control_z": case "z": case "x": case "c": case "v": case "Control_c": case "Control_v":
				 if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					 processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
				 }
				 processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Leader_Board", "SHOW 3.000");
				 processAnimation(Constants.BACK, print_writers, "Change$Leader_Board", "SHOW 0.0");
				
				 for(int iPlyr = 1; iPlyr <= 5; iPlyr++) {
					 if(iPlyr == Integer.valueOf(prevWhichPlayer)) {
						 processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + iPlyr, "SHOW 2.700");
					 } else {
						 processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side1$Player" + iPlyr, "SHOW 0.00");
					 }
				 }
				processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight$Side2", "SHOW 0.0");
				 
				 setVariousAnimationsKeys("CUT-BACK", print_writers, config);
				 this.whichGraphicOnScreen = whatToProcess;
				 break;
				
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Shift_T": case "p": case "Control_p":
				processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$Footer", "SHOW 0.0");
				switch(whichGraphicOnScreen.split(",")[0]) {
				case "F1": case "Control_Shift_A":  
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "SHOW 3.000");
					processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "SHOW 0.0");
					break;
				case "F2":  
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "SHOW 3.000");
					processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "SHOW 0.0");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "SHOW 3.000");
					processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
					break;
				case "Control_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "SHOW 3.000");
					processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LineUp_Image", "SHOW 3.000");
					processAnimation(Constants.BACK, print_writers, "Change$LineUp_Image", "SHOW 0.0");
					break;
				case "p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "SHOW 3.000");
					processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "SHOW 0.0");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Standings", "SHOW 3.000");
					processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
					break;
				}
				if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					switch(whatToProcess.split(",")[0]) {
					case "F1": case "Control_Shift_A":  
						processAnimation(Constants.BACK, print_writers, "Header_Shrink", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "SHOW 3.000");
						processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "SHOW 0.0");
						break;
					case "F2":
						processAnimation(Constants.BACK, print_writers, "Header_Shrink", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "SHOW 3.000");
						processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "SHOW 0.0");
						break;
					case "F4":
						processAnimation(Constants.BACK, print_writers, "Header_Shrink", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "SHOW 3.000");
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
						break;
					case "Control_F11":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "SHOW 3.000");
						processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
						break;
					case "p":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "SHOW 3.000");
						processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "SHOW 0.0");
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Standings", "SHOW 3.000");
						processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
						break;
					}
				}
				setVariousAnimationsKeys("CUT-BACK", print_writers, config);
				processAnimation(Constants.BACK, print_writers, "ConcussExtend_Y", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			}
			break;
			
		case Constants.BENGAL_T20:

			if(!whatToProcess.contains(",")) {
				return CricketUtil.NO;
			}
			
			switch(whatToProcess.split(",")[0]) {
			 case "Control_Shift_U": case "Control_Shift_V":
					processAnimation(Constants.FRONT, print_writers, "Change_Popup", "SHOW 0.0");
					break;
			case "Shift_F12":
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Ident_Change", "SHOW 0.0");
				break;
			case "Shift_F1": case "Shift_F2":
				processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "SHOW 0.0");
				break;
			case "Shift_F":case "Alt_b": case "y": case "Shift_O":
				processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			case "Alt_1":
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section3_Change", "SHOW 0.0");
				break;
//			case "Alt_c":
//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage_Change", "SHOW 0.0");
//				break;
			case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_6": case "Alt_9": case "Alt_0": case "Control_Shift_(":
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics$Change", "SHOW 0.0");
				break;
			case "Alt_7":
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section1_Change", "SHOW 0.0");
				break;
			case "Alt_8": case "Alt_5":
				if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
					if(infobar.getRight_section()!= null && !infobar.getRight_section().isEmpty()) {
						TimeUnit.MILLISECONDS.sleep(1000);
						processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section_2$Change", "SHOW 0.0");
					}
				}
				break;
			
			case "d": case "e": case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Shift_F3": case "F5": case "F6": case "F7": case "F9": case "F11": 
			case "Control_g": case "Control_h": case "Alt_F1": case "Alt_F2": case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k": 
			case "Control_F2": case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f": case "l": case "n": case "a": case "Alt_F6": case "Shift_A":  
			case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "b": case "Alt_j": case "Control_Shift_F": case "Control_Shift_E":
			case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s": case "Alt_Shift_F3":
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Badge", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Sublines", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Topline", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LtChange$Dynamic", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
				
			 case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8":
				 if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					 processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
				 }
				 processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Leaderboard", "SHOW 3.000");
				 processAnimation(Constants.BACK, print_writers, "Change$Leaderboard", "SHOW 0.0");
				 processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "SHOW 2.500");
				 processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "SHOW 0.0");
				 
				 setVariousAnimationsKeys("CUT-BACK", print_writers, config);
				 this.whichGraphicOnScreen = whatToProcess;
				 break;
				
			case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Shift_T": case "p": case "Control_p": case "Control_Shift_F1": 
			case "Control_Shift_F2": case "Control_Shift_F4":
				processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change$Footer", "SHOW 0.0");
				switch(whichGraphicOnScreen.split(",")[0]) {
				case "F1": case "Control_Shift_A":  
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "SHOW 0.0");
					break;
				case "F2":  
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "SHOW 0.0");
					break;
					
				case "Control_Shift_F1":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "SHOW 0.0");
					
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "SHOW 0.0");
					
					switch(whatToProcess.split(",")[0]) {
					case "Control_Shift_F1":
						if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side1$" + 
									caption.this_fullFramesGfx.pervious_batperformer_id, "SHOW 0.500");
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side2$" + 
									caption.this_fullFramesGfx.pervious_batperformer_id, "SHOW 0.0");
						}
						else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side1", "SHOW 0.0");
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side2", "SHOW 0.0");
						}
						break;
					default:
						if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side1$" + 
									caption.this_fullFramesGfx.pervious_batperformer_id, "SHOW 0.0");
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side2$" + 
									caption.this_fullFramesGfx.pervious_batperformer_id, "SHOW 0.0");
						}
						else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side1", "SHOW 0.0");
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side2", "SHOW 0.0");
						}
						break;
					}
					break;	
				case "Control_Shift_F2":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "SHOW 0.0");
					
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "SHOW 0.0");
					
					switch(whatToProcess.split(",")[0]) {
					case "Control_Shift_F2":
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side1$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.500");
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side2$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.0");
						break;
					default:
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side1$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side2$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.0");
						break;
					}
					break;
				case "Control_Shift_F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
					
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "SHOW 0.0");
					
					switch(whatToProcess.split(",")[0]) {
					case "Control_Shift_F2":
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Partnership_List$Side1$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.500");
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Partnership_List$Side2$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.0");
						break;
					default:
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Partnership_List$Side1$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Partnership_List$Side2$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.0");
						break;
					}
					break;	
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
					break;
				case "Control_F11":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.500");
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$LineUp_Image", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$LineUp_Image", "SHOW 0.0");
					
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "SHOW 0.0");
					break;
				case "p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "SHOW 0.0");
					break;
				case "Control_p":
					processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Standings", "SHOW 2.500");
					processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
					break;
				}
				if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					switch(whatToProcess.split(",")[0]) {
					case "F1": case "Control_Shift_A":  
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "SHOW 0.0");
						break;
					case "F2":
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "SHOW 0.0");
						break;
						
					case "Control_Shift_F1":
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Batting_Card", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Batting_Card", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "SHOW 0.0");
						
						if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side1$" + 
									caption.this_fullFramesGfx.pervious_batperformer_id, "SHOW 0.500");
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side2$" + 
									caption.this_fullFramesGfx.pervious_batperformer_id, "SHOW 0.0");
						}
						else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side1", "SHOW 0.0");
							processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Batting_Card$Side2", "SHOW 0.0");
						}
						
						break;
					case "Control_Shift_F2":
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Bowling_Card", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Bowling_Card", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side1$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.500");
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Bowling_Card$Side2$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.0");
						break;
					case "Control_Shift_F4":
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Sponsor", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Sponsor", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Extra_Info", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Extra_Info", "SHOW 0.0");
						
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Partnership_List$Side1$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.500");
						processAnimation(Constants.BACK, print_writers, "Anim_Highlights$Partnership_List$Side2$" + 
								caption.this_fullFramesGfx.pervious_ballperformer_id, "SHOW 0.0");
						break;	
					case "F4":
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Partnership_List", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
						break;
					case "Control_F11":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Summary", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.500");
						break;
					case "p":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Group_Standings", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Group_Standings", "SHOW 0.0");
						break;
					case "Control_p":
						processAnimation(Constants.BACK, print_writers, "Anim_FullFrames$In_Out$Standings", "SHOW 2.500");
						processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
						processAnimation(Constants.BACK, print_writers, "Change$Footer$Dynamic", "SHOW 0.500");
						break;
					}
				}
				setVariousAnimationsKeys("CUT-BACK", print_writers, config);
				//processAnimation(Constants.BACK, print_writers, "ConcussExtend_Y", "SHOW 0.0");
				this.whichGraphicOnScreen = whatToProcess;
				break;
			}
			break;	
		}
		CricketFunctions.deletePreview();
		return CricketUtil.YES;
	}
	
	public String T20_MumbaiAnimateIn(String whatToProcess, List<PrintWriter> print_writers,Configuration config) throws InterruptedException, IOException {
		switch (whatToProcess.split(",")[0]) {
		case "ArrowUp":
			if(this.infobar.isInfobar_on_screen() == true && this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Push", "CONTINUE REVERSE");
				this.infobar.setInfobar_pushed(false);
			}
			break;
		case "ArrowDown":
			if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Push", "START");
				this.infobar.setInfobar_pushed(true);
				TimeUnit.MILLISECONDS.sleep(600);
			}
			break;
		case "ArrowLeft":
			if(this.infobar.isInfobar_on_screen() == true) {
				if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "START");
					this.infobar.setInfobar_status(Constants.FORCED + Constants.SHRUNK_INFOBAR);
				}
			}
			break;
		case "ArrowRight":
			if(this.infobar.isInfobar_on_screen() == true) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "CONTINUE REVERSE");
				this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
			}
			break;
			
		case Constants.SHRUNK_INFOBAR:
			if(this.infobar.isInfobar_on_screen() == true) {
				if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "START");
					this.infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.SHRUNK_INFOBAR)) {
					processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "CONTINUE REVERSE");
					this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
				}
			}
			break;
			
		case "Alt_0":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$OutForAnalytics$In_Out", "START");
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Promo_Graphics$In_Out", "START");
			infobar.setFull_promo_section(whatToProcess.split(",")[2]);
			break;
		case "Alt_5":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section4$In_Out", "START");
			infobar.setRight_full_section(whatToProcess.split(",")[2]);
			break;
		case "Alt_3": case "Alt_4": case "Alt_6": case "Alt_9":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$OutForAnalytics$In_Out", "START");
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics$In_Out", "START");
			infobar.setFull_section(whatToProcess.split(",")[2]);
			break;
		case "Alt_8":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section3$In_Out", "START");
			infobar.setRight_section(whatToProcess.split(",")[2]);
			break;
	
		case "5":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$object$LeftPlayer*ACTIVE SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$object$RightPlayer*ACTIVE SET 1\0", print_writers);
			
			processAnimation(Constants.FRONT, print_writers, "PlayerTicker", "CONTINUE");
			if(bigScoreBug_On_Screen) {
				bigScoreBug_On_Screen = false;
				TimeUnit.MILLISECONDS.sleep(400);
				processAnimation(Constants.FRONT, print_writers, "PlayerTicker", "SHOW 0.0");
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$object$LeftPlayer*ACTIVE SET 0\0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$object$RightPlayer*ACTIVE SET 0\0", print_writers);
			}else {
				bigScoreBug_On_Screen = true;
			}
			break;
			
		case "Alt_e":
			if(caption.this_infobarGfx.infobar.isPowerplay_on_screen() == false) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$PowerPlay", "START");
				caption.this_infobarGfx.infobar.setPowerplay_on_screen(true);
				caption.this_infobarGfx.infobar.setForced_powerplay_out(false);
			}else {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$PowerPlay", "CONTINUE REVERSE");
				caption.this_infobarGfx.infobar.setPowerplay_on_screen(false);
				caption.this_infobarGfx.infobar.setForced_powerplay_out(true);
			}
			break;
			
		case "i":
			if(this.infobar.isFreeHit_on_screen() == false) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$All$Normal$DisruptiveAnimations$select_Style"
						+ "*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				caption.this_infobarGfx.TeamWipeColor(print_writers, whatToProcess.split(",")[0]);
				for(int i=1;i<=10;i++) {
					if(i<=6) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$All$Normal$DisruptiveAnimations$FreeHit$BigText$" + i 
								+ "$txt_Event*GEOM*TEXT SET " + "FREE-HIT" + "\0", print_writers);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$All$Normal$DisruptiveAnimations$FreeHit$BigText$" + i 
								+ "$txt_EventOutline*GEOM*TEXT SET " + "FREE-HIT" + "\0", print_writers);
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$All$Normal$DisruptiveAnimations$FreeHit$SmallText$txt_Event_"
							+ i + "*GEOM*TEXT SET " + "FREE-HIT" + "\0", print_writers);
				}
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "Disruptives$InOut", "START");
					this.infobar.setFreeHit_on_screen(true);
				}
			}else {
				processAnimation(Constants.FRONT, print_writers, "Disruptives$InOut", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(2000);
				processAnimation(Constants.FRONT, print_writers, "Disruptives", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "DisruptivesLoop", "SHOW 0.0");
				this.infobar.setFreeHit_on_screen(false);
			}
			break;
			
		case "w": case "f": case "s": case "0": case "8":
			String whichData = Map.of("s","SIX", "w","WICKET", "f","FOUR", "0","HAT-TRICK", "8","ON A HAT-TRICK").getOrDefault(whatToProcess.split(",")[0], "");
			caption.this_infobarGfx.TeamWipeColor(print_writers, whatToProcess.split(",")[0]);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$All$Normal$DisruptiveAnimations$select_Style"
					+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$All$Normal$DisruptiveAnimations$Normal$BigText$EventTextBig"
					+ "$txt_Event*GEOM*TEXT SET " + whichData + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$All$Normal$DisruptiveAnimations$Normal$BigText$EventTextBig"
					+ "$txt_EventOutline*GEOM*TEXT SET " + whichData + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$All$Normal$DisruptiveAnimations$Normal$BigText$Left"
					+ "$txt_Event*GEOM*TEXT SET " + whichData + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Infobar$All$Normal$DisruptiveAnimations$Normal$BigText$Right"
					+ "$txt_Event*GEOM*TEXT SET " + whichData + "\0", print_writers);
			
			if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "Disruptives$InOut", "START");
				TimeUnit.MILLISECONDS.sleep(3000);
				processAnimation(Constants.FRONT, print_writers, "Disruptives$InOut", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(2000);
				processAnimation(Constants.FRONT, print_writers, "Disruptives", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "DisruptivesLoop", "SHOW 0.0");
			}
			break;
		case "Control_F12":
			if(this.infobar.isInfobar_on_screen()) {
				
			}else {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Essentials", "START");
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$ColourAndLogos", "START");
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Ident", "START");
			}

			processAnimation(Constants.FRONT, print_writers, "Loop", "START");
			this.infobar.setInfobar_on_screen(true);
			caption.this_infobarGfx.infobar.setInfobar_on_screen(true);
			break;
		case "F12":
			processAnimation(Constants.FRONT, print_writers, "BatterStrike", "START");
			if(this.infobar.isInfobar_on_screen()) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$IdentToNormal", "START");
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Change_ColourAndLogos", "START");
				
				TimeUnit.MILLISECONDS.sleep(1500);
				caption.this_infobarGfx.TeamColor(true, print_writers,IndexController.session_match, caption.this_infobarGfx.inning, 1);
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Change_ColourAndLogos", "SHOW 0.0");
				
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Ident", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Normal", "SHOW 2.900");
			}else {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Essentials", "START");
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$ColourAndLogos", "START");
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Normal", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
			}
			
			this.infobar.setInfobar_on_screen(true);
			caption.this_infobarGfx.infobar.setInfobar_on_screen(true);
			this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
			break;
				
		case "Alt_p": 
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_TossBug", "START");
			this.specialBugOnScreen = CricketUtil.TOSS;
			break;
		
		case "Shift_F1":  case "Shift_F2":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_Minis$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Alt_F7":
			processAnimation(Constants.FRONT, print_writers, "MiniPointsTable", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
			
		case "Alt_Shift_E":
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.BACK, print_writers, "Powerplay", "START");
			processAnimation(Constants.BACK, print_writers, "BackVeil$Director", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
				
		case "Shift_C":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
			}
			processAnimation(Constants.FRONT, print_writers, "anim_SixDistance$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Control_Shift_F3":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
			}
			processAnimation(Constants.FRONT, print_writers, "anim_TargetBug$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "r":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "Anim_DRS_Bug$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "h": case "Control_y": case "Control_k": case "Shift_F4": case "Shift_O": case "y": case "g": case "k":
		case "Control_Shift_R": case "Control_Shift_J":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Control_Shift_U": case "Control_Shift_V":
			
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
			}
			
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_Pop_Up", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "6": case "Control_4":
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
			}
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_BoundaryCounter$In_Out", "START");
			TimeUnit.MILLISECONDS.sleep(1500);
			this.whichGraphicOnScreen = whatToProcess;
			if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[0].
					equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[0])) {
				processAnimation(Constants.FRONT, print_writers, "Change$Units", "START");
				processAnimation(Constants.FRONT, print_writers, "Change$Tenths", "START");
				processAnimation(Constants.FRONT, print_writers, "Change$Hundredths", "START");
			}
			else if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[1].
					equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[1])) {
				processAnimation(Constants.FRONT, print_writers, "Change$Tenths", "START");
				processAnimation(Constants.FRONT, print_writers, "Change$Units", "START");
			}
			else if(!caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-2).split(",")[2].
					equalsIgnoreCase(caption.this_bugsAndMiniGfx.this_data_str.get(caption.this_bugsAndMiniGfx.this_data_str.size()-1).split(",")[2])) {
				processAnimation(Constants.FRONT, print_writers, "Change$Units", "START");
			}
			break;
			
		case "Shift_I":
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Impact$Move_X_ForShrink"
						+ "*TRANSFORMATION*POSITION*X SET 0\0", print_writers);
				
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Impact$Move_X_ForShrink"
						+ "*TRANSFORMATION*POSITION*X SET -600\0", print_writers);
			}
			
			if(whichGraphicOnScreen.equalsIgnoreCase("Shift_I")) {
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
				}
				processAnimation(Constants.FRONT, print_writers, "anim_Impact$SubToImpact", "START");
				this.whichGraphicOnScreen = whatToProcess;
			}else {
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
				}
				processAnimation(Constants.FRONT, print_writers, "anim_Impact$In_Out", "START");
				this.whichGraphicOnScreen = whatToProcess.split(",")[0];
			}
			break;
		case "Control_F6": case "F6": case "Shift_F6":
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_LtHowOut$Move_X_ForShrink"
						+ "*TRANSFORMATION*POSITION*X SET 0\0", print_writers);
				
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					TimeUnit.MILLISECONDS.sleep(1000);
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_LtHowOut$Move_X_ForShrink"
						+ "*TRANSFORMATION*POSITION*X SET -495\0", print_writers);
			}
			
			
			if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "START");
			}
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_LT_HowOut$InOut", "START");
			
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "F8": case "Alt_F8": case "F10": case "Control_F9":case "Control_F5":
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_NameSuper$Move_X_ForShrink"
						+ "*TRANSFORMATION*POSITION*X SET 0\0", print_writers);
				
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_NameSuper$Move_X_ForShrink"
						+ "*TRANSFORMATION*POSITION*X SET -495\0", print_writers);
			}
			
			if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
				processAnimation(Constants.FRONT, print_writers, "MoveForNameSuper", "START");
			}
			
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_LT_NameSuper$InOut", "START");
			
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "F5":case "F9":case "l":case "Shift_F5":case "Shift_F9":case "Control_h":case "Alt_F12": case "F7": case "F11": case "Control_a": case "q": 
		case "u": case "Control_q": case "Shift_F3": case "Control_F3": case "Shift_B": case "Alt_Shift_F3": case "Control_Shift_Q": case "Control_s":
		case "Alt_Shift_O": case "d": case "e":case "Alt_F1": case "Alt_F2": case "Control_f":
		case "Control_i":
			if(this.infobar.isInfobar_on_screen() == true) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_LowerThird$Move_X_ForShrink"
						+ "*TRANSFORMATION*POSITION*X SET 0\0", print_writers);
				
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED+Constants.SHRUNK_INFOBAR)) {
					AnimateIn("ArrowLeft" + ",", print_writers, config); // Shrink infobar
					infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
				}
				
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_LowerThird$Move_X_ForShrink"
						+ "*TRANSFORMATION*POSITION*X SET -286\0", print_writers);
			}
			
			if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Shrink", "START");
			}
			
			TimeUnit.MILLISECONDS.sleep(1000);
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Essentials", "START");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Colours", "START");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Logo", "START");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$TopLine", "START");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Subline", "START");
			
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "j":
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			if(this.infobar.isInfobar_on_screen() == true ||this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
				processAnimation(Constants.FRONT, print_writers, "PositionForInfobar$ForShrinK", "START");
			}else {
				processAnimation(Constants.FRONT, print_writers, "PositionForInfobar$NoInfobar", "START");
			}
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "NameSuper", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
			
		case "Control_Shift_M": case "Control_Shift_L":
			T20_MumbaiAnimateIn("ArrowDown,", print_writers, config); // Push infobar
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_LT_Ident", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Alt_Shift_N":case "Alt_Shift_M":
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.BACK, print_writers, "PlayerBio", "START");
			processAnimation(Constants.BACK, print_writers, "BackVeil$Director", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Alt_Shift_K": case "Alt_Shift_X": case "Alt_Shift_T": case "Alt_Shift_V":
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.BACK, print_writers, "OTS_Leaderboard", "START");
			processAnimation(Constants.BACK, print_writers, "BackVeil$Director", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
			
		case "Control_Shift_O":
			T20_MumbaiAnimateIn("ArrowDown,", print_writers, config); // Push infobar
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_In", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_Lt_BattingCard$InOut", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
			
		case "m": case "Control_m":
			T20_MumbaiAnimateIn("ArrowDown,", print_writers, config); // Push infobar
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_In", "START");
			}
			processAnimation(Constants.BACK, print_writers, "anim_Ident$In_Out", "START");
			processAnimation(Constants.BACK, print_writers, "Loop", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		
		case "Shift_D":
			T20_MumbaiAnimateIn("ArrowDown,", print_writers, config); // Push infobar
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_In", "START");
			}
			processAnimation(Constants.BACK, print_writers, "anim_Target$In_Out", "START");
			processAnimation(Constants.BACK, print_writers, "Loop", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		
		case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			
			processAnimation(Constants.BACK, print_writers, "anim_Profile$In_Out$In", "START");
			processAnimation(Constants.BACK, print_writers, "Loop", "START");
			this.whichGraphicOnScreen = whatToProcess;
			TimeUnit.MILLISECONDS.sleep(2700);
			processAnimation(Constants.BACK, print_writers, "anim_Profile$In_Out", "SHOW 2.520");
			break;
		case "Control_b":
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			
			processAnimation(Constants.BACK, print_writers, "anim_Profile$In_Out", "START");
			processAnimation(Constants.BACK, print_writers, "Loop", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
			
		case "Shift_T":
			if(whichGraphicOnScreen.contains("Shift_T")){
				footercount++;
				if(footercount == 1) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_ImageLineup$All_Graphics$Side2$select_PlayerNumber"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				}else if(footercount == 2) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_ImageLineup$All_Graphics$Side2$select_PlayerNumber"
							+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
				}
				
				processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Image_Lineup", "CONTINUE");
				if(footercount == 2 && !caption.this_fullFramesGfx.containerName_2.isEmpty()) {
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$Fullframers$Body$Side1$PlayingXI$Footer$Select2"
//							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
//					processAnimation(Constants.BACK, print_writers, "PlayingXI_FooterChgOn", "CONTINUE");
				}
				
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_Change", "START");
				}
				TimeUnit.MILLISECONDS.sleep(2300);
				if(footercount == 1) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_ImageLineup$All_Graphics$Side1$select_PlayerNumber"
							+ "*FUNCTION*Omo*vis_con SET 1\0", print_writers);
				}else if(footercount == 2) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_ImageLineup$All_Graphics$Side1$select_PlayerNumber"
							+ "*FUNCTION*Omo*vis_con SET 2\0", print_writers);
				}
				
				processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Image_Lineup", "SHOW 0.0");
			}else {
				footercount = 0;
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_In", "START");
				}
				
				T20_MumbaiAnimateIn("ArrowDown,", print_writers, config); // Push infobar
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Essentials", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Elemnets", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$ColourBase", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$VerticalText", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Logo", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Header", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$SubHeader", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Image_Lineup", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Footer", "START");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Wipe", "START");
				
				processAnimation(Constants.BACK, print_writers, "Loop", "START");
				this.whichGraphicOnScreen = whatToProcess;
			}
			break;
			
		case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
			T20_MumbaiAnimateIn("ArrowDown,", print_writers, config); // Push infobar
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "sfx_FF_In", "START");
			}
			processAnimation(Constants.BACK, print_writers, "FF_Leaderboard$FullFramers$Inout", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
			
		case "F1": case "Control_Shift_F1": case "F2": case "Control_F11": case "Shift_F11": case "F4": case "Shift_K": 
		case "Control_F7": case "Shift_F10": case "p": case "Alt_Shift_J": case "Control_F1": case "Alt_F9": case "Shift_F8":
		case "Control_F10": case "Control_Shift_F2": case "Alt_Shift_F10": case "Alt_Shift_F12": case "Alt_Shift_F9":
		case "Alt_Shift_F2":
			T20_MumbaiAnimateIn("ArrowDown,", print_writers, config); // Push infobar
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_In", "START");
			}
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Essentials", "START");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Elemnets", "START");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$ColourBase", "START");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$VerticalText", "START");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Logo", "START");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Header", "START");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$SubHeader", "START");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Footer", "START");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Wipe", "START");
			switch(whatToProcess.split(",")[0]) {
			case "F1":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$BattingCard", "START");
				break;
			case "Control_Shift_F1": case "Control_Shift_F2":
				processAnimation(Constants.BACK, print_writers, "MoveForSplitCard", "START");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$SplitCard", "START");
				break;
			case "F2":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$BowlingCard", "START");
				break;
			case "Control_F11": case "Shift_F11":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Summary", "START");
				break;
			case "Alt_Shift_F10":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$InningsSummary", "START");
				break;
			case "Alt_Shift_F12":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$PhasewiseRunRates", "START");
				break;
			case "Alt_Shift_F9":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$PhasewiseSummary", "START");
				break;
			case "Alt_Shift_F2":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$BattingComparison", "START");
				break;
			case "F4":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$PartnershipList", "START");
				break;
			case "Shift_K":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Partnership", "START");
				break;
			case "Alt_F9":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Team", "START");
				break;
			case "Shift_F8":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$TeamWithSub", "START");
				break;
			case "Control_F7":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Teams", "START");
				break;
			case "Shift_F10":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Worm", "START");
				break;
			case "Control_F10":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Manhattan", "START");
				break;
			case "p":
				processAnimation(Constants.BACK, print_writers, "FullFramers$Main$PointsTable", "START");
				break;
			case "Alt_Shift_J":
				processAnimation(Constants.BACK, print_writers, "FullFramers$Main$BatManhatton", "START");
				break;
			case "Control_F1":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$ImageBattingCard", "START");
				break;
				
			}
			processAnimation(Constants.BACK, print_writers, "Loop", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		}
		return CricketUtil.YES;
	}
	public String T20_MumbaiAnimateOut(String whatToProcess, List<PrintWriter> print_writers,Configuration config) throws InterruptedException, IOException {
		switch (whatToProcess.split(",")[0]) {
		case "Control_F12":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Essentials", "CONTINUE");
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$ColourAndLogos", "CONTINUE");
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Ident", "CONTINUE");
			
			TimeUnit.MILLISECONDS.sleep(1800);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar", "SHOW 0.0");
			infobar.setMiddle_section("");
			infobar.setFull_section("");
			infobar.setRight_bottom("");
			infobar.setRight_section("");
			
			this.infobar.setInfobar_on_screen(false);
			caption.this_infobarGfx.infobar.setInfobar_on_screen(false);
			this.whichGraphicOnScreen = "";
			break;
		case "F12":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Essentials", "CONTINUE");
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$ColourAndLogos", "CONTINUE");
			
			System.out.println(caption.this_infobarGfx.infobar.isResult_on_screen());
			
			if(caption.this_infobarGfx.infobar.isResult_on_screen() == true) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$ResultOut", "CONTINUE");
			}else {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar$InOut$Normal", "CONTINUE");
			}
			
			TimeUnit.MILLISECONDS.sleep(1800);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar", "SHOW 0.0");
			
			this.infobar.setInfobar_on_screen(false);
			caption.this_infobarGfx.infobar.setInfobar_on_screen(false);
			ExtraInfoOnScreen = false;
			break;
			
		case "Alt_0":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Promo_Graphics$In_Out", "CONTINUE");
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$OutForAnalytics$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(700);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Promo_Graphics", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$OutForAnalytics", "SHOW 0.0");
			infobar.setFull_promo_section(null);
			break;
		case "Alt_5":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section4$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(700);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section4", "SHOW 0.0");
			infobar.setRight_full_section(null);
			break;
		case "Alt_3": case "Alt_4": case "Alt_6": case "Alt_9":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics$In_Out", "CONTINUE");
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$OutForAnalytics$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(700);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$OutForAnalytics", "SHOW 0.0");
			infobar.setFull_section(null);
			break;
		case "Alt_8":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section3$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(700);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Section3", "SHOW 0.0");
			infobar.setRight_section("");
			break;
			
		case "Alt_p":
			if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
				if(audioenabled.equalsIgnoreCase("TRUE")) {
					processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
				}
				processAnimation(Constants.FRONT, print_writers, "anim_TossBug", "CONTINUE");
				this.specialBugOnScreen = "";
				TimeUnit.MILLISECONDS.sleep(2500);
				processAnimation(Constants.FRONT, print_writers, "anim_TossBug", "SHOW 0.0");
			}
			break;
		case "Control_Shift_U": case "Control_Shift_V":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_Pop_Up", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(1000);
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
					T20_MumbaiAnimateIn("ArrowRight,", print_writers, config);
				}
			}
			TimeUnit.MILLISECONDS.sleep(2500);
			processAnimation(Constants.FRONT, print_writers, "anim_Pop_Up", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Change_PopUp", "SHOW 0.0");
			this.whichGraphicOnScreen = "";
			break;
		case "6": case "Control_4":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_BoundaryCounter$In_Out", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(2500);
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
					T20_MumbaiAnimateIn("ArrowRight,", print_writers, config);
				}
			}
			
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.FRONT, print_writers, "Change", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_BoundaryCounter", "SHOW 0.0");
			break;
		case "Shift_C":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_SixDistance$In_Out", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1800);
			if(!this.infobar.isInfobar_pushed()) {
				T20_MumbaiAnimateIn("ArrowUp,", print_writers, config); // Push infobar
			}
			TimeUnit.MILLISECONDS.sleep(2500);
			processAnimation(Constants.FRONT, print_writers, "anim_SixDistance$In_Out", "SHOW 0.0");
			break;
		case "Control_Shift_F3":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_TargetBug$In_Out", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1000);
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
					T20_MumbaiAnimateIn("ArrowRight,", print_writers, config);
				}
			}
			TimeUnit.MILLISECONDS.sleep(2500);
			processAnimation(Constants.FRONT, print_writers, "anim_TargetBug$In_Out", "SHOW 0.0");
			break;
			
		case "r":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "Anim_DRS_Bug$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(800);
			processAnimation(Constants.FRONT, print_writers, "Anim_DRS_Bug", "SHOW 0.0");
			this.whichGraphicOnScreen = "";
			break;
			
		case "h": case "Control_y": case "Control_k": case "Shift_F4": case "Shift_O": case "y": case "g": case "k":
		case "Control_Shift_R": case "Control_Shift_J":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(800);
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$In_Out", "SHOW 0.0");
			this.whichGraphicOnScreen = "";
			break;
			
		case "Alt_Shift_E":
			processAnimation(Constants.BACK, print_writers, "BackVeil$Director", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "Powerplay", "CONTINUE");
			this.whichGraphicOnScreen = "";
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			break;
		
		case "Shift_F1":  case "Shift_F2":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_Minis", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "anim_Minis", "SHOW 0.0");
			break;
		case "Alt_F7":
			processAnimation(Constants.FRONT, print_writers, "MiniPointsTable", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.FRONT, print_writers, "MiniPointsTable", "SHOW 0.0");
			break;
			
		case "Shift_I":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_Impact$In_Out", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1800);
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
					T20_MumbaiAnimateIn("ArrowRight,", print_writers, config);
				}
			}
			processAnimation(Constants.FRONT, print_writers, "anim_Impact", "SHOW 0.0");
			break;	
		
		case "Control_F6": case "F6": case "Shift_F6":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_LT_HowOut$InOut", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(1400);
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
					T20_MumbaiAnimateIn("ArrowRight,", print_writers, config);
				}
			}
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(2500);
			processAnimation(Constants.FRONT, print_writers, "anim_LT_HowOut$InOut", "SHOW 0.0");
			break;
		case "F8": case "Alt_F8": case "F10": case "Control_F9":case "Control_F5":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_LT_NameSuper$InOut", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(1400);
			if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
				processAnimation(Constants.FRONT, print_writers, "MoveForNameSuper", "CONTINUE REVERSE");
			}
			
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
					T20_MumbaiAnimateIn("ArrowRight,", print_writers, config);
				}
			}
			
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1500);
			processAnimation(Constants.FRONT, print_writers, "anim_LT_NameSuper", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "MoveForNameSuper", "SHOW 0.0");
			break;
		case "F5":case "F9":case "l":case "Shift_F5":case "Shift_F9":case "Control_h":case "Alt_F12": case "F7": case "F11": case "Control_a": case "q": 
		case "u": case "Control_q": case "Shift_F3": case "Control_F3": case "Shift_B": case "Alt_Shift_F3": case "Control_Shift_Q": case "Control_s": case "Control_f":
		case "Alt_Shift_O":case "d": case "e":case "Alt_F1": case "Alt_F2":
		case "Control_i":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Essentials", "CONTINUE");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Colours", "CONTINUE");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Logo", "CONTINUE");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$TopLine", "CONTINUE");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Subline", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(1800);
			if(infobar.getInfobar_status() != null) {
				if(!infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
					T20_MumbaiAnimateIn("ArrowRight,", print_writers, config);
				}
			}
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(2500);
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird", "SHOW 0.0");
			break;
			
		case "j":
			processAnimation(Constants.FRONT, print_writers, "NameSuper", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(500);
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			processAnimation(Constants.FRONT, print_writers, "PositionForInfobar", "SHOW 0.0");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(2000);
			processAnimation(Constants.FRONT, print_writers, "NameSuper", "SHOW 0.0");
			break;

		case "Control_Shift_M": case "Control_Shift_L":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_LT_Ident", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(2500);
			
			if(!this.infobar.isInfobar_pushed()) {
				T20_MumbaiAnimateIn("ArrowUp,", print_writers, config); // Push infobar
			}
			
			processAnimation(Constants.FRONT, print_writers, "anim_LT_Ident", "SHOW 0.0");
			break;
			
		case "Alt_Shift_N":case "Alt_Shift_M":
			processAnimation(Constants.BACK, print_writers, "BackVeil$Director", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "PlayerBio", "CONTINUE");
			this.whichGraphicOnScreen = "";
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			break;
		case "Alt_Shift_K": case "Alt_Shift_X": case "Alt_Shift_T": case "Alt_Shift_V":
			processAnimation(Constants.BACK, print_writers, "BackVeil$Director", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "OTS_Leaderboard", "CONTINUE");
			this.whichGraphicOnScreen = "";
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			break;
				
		case "Control_Shift_O":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Out", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "anim_Lt_BattingCard$InOut", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(500);
			if(!this.infobar.isInfobar_pushed()) {
				T20_MumbaiAnimateIn("ArrowUp,", print_writers, config); // Push infobar
			}
			processAnimation(Constants.FRONT, print_writers, "anim_Lt_BattingCard", "SHOW 0.0");
			break;
			
		case "m": case "Control_m":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_Out", "START");
			}
			processAnimation(Constants.BACK, print_writers, "anim_Ident$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(1000);
			T20_MumbaiAnimateIn("ArrowUp,", print_writers, config); // Push infobar
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.BACK, print_writers, "anim_Ident", "SHOW 0.0");
			break;
			
		case "Shift_D":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_Out", "START");
			}
			processAnimation(Constants.BACK, print_writers, "anim_Target$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(1000);
			T20_MumbaiAnimateIn("ArrowUp,", print_writers, config); // Push infobar
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.BACK, print_writers, "anim_Target", "SHOW 0.0");
			break;
			
		case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
			processAnimation(Constants.BACK, print_writers, "anim_Profile$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(1000);
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			processAnimation(Constants.BACK, print_writers, "anim_Profile", "SHOW 0.0");
			this.whichGraphicOnScreen = "";
			break;
			
		case "Control_b":
			processAnimation(Constants.BACK, print_writers, "anim_Profile$In_Out", "CONTINUE");
			TimeUnit.MILLISECONDS.sleep(1000);
			T20_MumbaiAnimateIn(Constants.SHRUNK_INFOBAR, print_writers, config); // Push infobar
			processAnimation(Constants.BACK, print_writers, "anim_Profile", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "InAt_To_Profile", "SHOW 0.0");
			this.whichGraphicOnScreen = "";
			break;
			
		case "Shift_T":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_Out", "START");
			}
			
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Essentials", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Elemnets", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$ColourBase", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$VerticalText", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Logo", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Header", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$SubHeader", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Image_Lineup", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Footer", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup$In_Out$Wipe", "CONTINUE");
			
			TimeUnit.MILLISECONDS.sleep(1000);
			T20_MumbaiAnimateIn("ArrowUp,", print_writers, config); // Push infobar
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1000);
			processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup", "SHOW 0.0");
			break;
			
		case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "sfx_FF_Out", "START");
			}
			processAnimation(Constants.BACK, print_writers, "FF_Leaderboard$FullFramers$Inout", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1000);
			T20_MumbaiAnimateIn("ArrowUp,", print_writers, config); // Push infobar
			processAnimation(Constants.BACK, print_writers, "FF_Leaderboard", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "PulseAnim1", "SHOW 0.0");
			break;
			
		case "F1": case "Control_Shift_F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Control_F7":
		case "Shift_F10": case "Shift_F11": case "p": case "Alt_Shift_J": case "Control_F1": case "Alt_F9": case "Shift_F8": case "Control_F10":
		case "Control_Shift_F2": case "Alt_Shift_F10": case "Alt_Shift_F12": case "Alt_Shift_F9": case "Alt_Shift_F2":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_Out", "START");
			}
			switch(whatToProcess.split(",")[0]) {
			case "F1":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$BattingCard", "CONTINUE");
				break;
			case "Control_Shift_F1": case "Control_Shift_F2":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$SplitCard", "CONTINUE");
				break;
			case "F2":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$BowlingCard", "CONTINUE");
				break;
			case "Control_F11": case "Shift_F11":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Summary", "CONTINUE");
				break;
			case "Alt_Shift_F10":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$InningsSummary", "CONTINUE");
				break;
			case "Alt_Shift_F12":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$PhasewiseRunRates", "CONTINUE");
				break;
			case "Alt_Shift_F9":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$PhasewiseSummary", "CONTINUE");
				break;
			case "Alt_Shift_F2":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$BattingComparison", "CONTINUE");
				break;
			case "F4":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$PartnershipList", "CONTINUE");
				break;
			case "Shift_K":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Partnership", "CONTINUE");
				break;
			case "Alt_F9":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Team", "CONTINUE");
				break;
			case "Shift_F8":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$TeamWithSub", "CONTINUE");
				break;
			case "Shift_T":
				processAnimation(Constants.BACK, print_writers, "FullFramers$Main$PlayingXI", "CONTINUE");
				break;
			case "Control_F7": 
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Teams", "CONTINUE");
				break;
			case "Shift_F10":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Worm", "CONTINUE");
				break;
			case "Control_F10":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Manhattan", "CONTINUE");
				break;
			case "p":
				processAnimation(Constants.BACK, print_writers, "FullFramers$Main$PointsTable", "CONTINUE");
				break;
			case "Alt_Shift_J":
				processAnimation(Constants.BACK, print_writers, "FullFramers$Main$BatManhatton", "CONTINUE");
				break;
			case "Control_F1":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$ImageBattingCard", "CONTINUE");
				break;
			}
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Essentials", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Elemnets", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$ColourBase", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$VerticalText", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Logo", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Header", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$SubHeader", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Footer", "CONTINUE");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Wipe", "CONTINUE");
			this.whichGraphicOnScreen = "";
			TimeUnit.MILLISECONDS.sleep(1000);
			T20_MumbaiAnimateIn("ArrowUp,", print_writers, config); // Push infobar
			processAnimation(Constants.BACK, print_writers, "MoveForSplitCard", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrames", "SHOW 0.0");
			break;
		}
		return CricketUtil.YES;
	}
	public String T20_MumbaiChangeOn(String whatToProcess, List<PrintWriter> print_writers,Configuration config) throws InterruptedException, IOException {
		switch (whatToProcess.split(",")[0]) {
		case "Shift_F12":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Change_Ident", "START");
			TimeUnit.MILLISECONDS.sleep(1000);
			break;
		case "Alt_7":
			processAnimation(Constants.FRONT, print_writers, "Change_Section2", "START");
			caption.this_infobarGfx.infobar.setLast_right_bottom(caption.this_infobarGfx.infobar.getRight_bottom());
			break;
		case "Alt_2":
			processAnimation(Constants.FRONT, print_writers, "Change_Section1", "START");
			caption.this_infobarGfx.infobar.setLast_middle_section(caption.this_infobarGfx.infobar.getMiddle_section());
			break;
		case "Alt_0":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Promo_Graphics$Change", "START");
			infobar.setFull_promo_section(whatToProcess.split(",")[2]);
			break;
		case "Alt_5":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Change_Section4", "START");
			infobar.setRight_full_section(whatToProcess.split(",")[2]);
			break;
		case "Alt_3": case "Alt_4": case "Alt_6": case "Alt_9":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics$Change", "START");
			infobar.setFull_section(whatToProcess.split(",")[2]);
			break;
		case "Alt_8":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Change_Section3", "START");
			infobar.setRight_section(whatToProcess.split(",")[2]);
			break;
			
		case "Control_Shift_U": case "Control_Shift_V":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Change", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "Change_PopUp", "START");
			break;
		case "Control_Shift_U_change_on": case "Control_Shift_V_change_on":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Change", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "Change_PopUp$Data", "START");
			break;
			
		case "r":
			processAnimation(Constants.FRONT, print_writers, "DRS_Change", "START");
			break;
			
		case "F8": case "Alt_F8": case "F10": case "Control_F9":case "Control_F5":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Change", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "Change_LT_NameSuper", "START");
			break;
		case "F5":case "F9":case "l":case "Shift_F5":case "Shift_F9":case "Control_h":case "Alt_F12": case "F7": case "F11": case "q":case "u":case "Control_q": 
		case "Shift_F3": case "Control_a": case "Control_F3": case "Shift_B": case "Alt_Shift_F3":case "Alt_Shift_O": case "Control_s": case "Control_f":
		case "d": case "e":case "Alt_F1": case "Alt_F2": case "Control_i":
//			if(whatToProcess.contains("Player_ChangeON")) {
//				processAnimation(Constants.FRONT, print_writers, "Body$Side2", "START");
//			}else {
//				processAnimation(Constants.FRONT, print_writers, "HeaderChange$Side2", "START");
//				processAnimation(Constants.FRONT, print_writers, "Body$Side2", "START");
//				processAnimation(Constants.FRONT, print_writers, "BaseWidth", "START");	
//			}
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.FRONT, print_writers, "sfx_Change", "START");
			}
			processAnimation(Constants.FRONT, print_writers, "ChangeLowerThird", "START");
			
			break;
		 case "j":
			processAnimation(Constants.FRONT, print_writers, "NameSuperChange", "START");
			break;
			
		case "Control_b":
			processAnimation(Constants.BACK, print_writers, "anim_Profile$InAt_To_Profile", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Shift_T":
			footercount = 0;
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_Change", "START");
			}
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Elements", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$ColourBase", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$VerticalText", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Logo", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Header", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$SubHeader", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Footer", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Wipe", "START");
			
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Image_Lineup", "START");
			break;
			
		case "F1": case "Control_Shift_F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Control_F7":
		case "Shift_F10": case "Shift_F11": case "p": case "Alt_Shift_J": case "Control_F1": case "Alt_F9": case "Shift_F8": case "Control_F10":
		case "Control_Shift_F2": case "Alt_Shift_F10": case "Alt_Shift_F12": case "Alt_Shift_F9": case "Alt_Shift_F2":
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "AUDIO$sfx_FF_Change", "START");
			}
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Elements", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$ColourBase", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$VerticalText", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Logo", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Header", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$SubHeader", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Footer", "START");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Wipe", "START");
			
			if(whatToProcess.split(",")[0].equalsIgnoreCase("Shift_T")) {
				processAnimation(Constants.BACK, print_writers, "PlayingXI_FooterChgOn", "SHOW 0.0");
			}
			
			if(whichGraphicOnScreen.contains(",")) {
				switch (whichGraphicOnScreen.split(",")[0]) {
				case "F1":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BattingCard", "START");
					break;
				case "Control_Shift_F1": case "Control_Shift_F2":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$SplitCard", "START");
					if(!whatToProcess.split(",")[0].split(",")[0].equalsIgnoreCase("Control_Shift_F1") && 
							!whatToProcess.split(",")[0].split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
							processAnimation(Constants.BACK, print_writers, "MoveForSplitCard", "CONTINUE REVERSE");
						}
					}
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BowlingCard", "START");
					break;
				case "Control_F11": case "Shift_F11":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Summary", "START");
					break;
				case "Alt_Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$InningsSummary", "START");
					break;
				case "Alt_Shift_F12":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PhasewiseRunRates", "START");
					break;
				case "Alt_Shift_F9":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PhasewiseSummary", "START");
					break;
				case "Alt_Shift_F2":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BattingComparison", "START");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PartnershipList", "START");
					break;
				case "Alt_F9":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Team", "START");
					break;
				case "Shift_F8":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$TeamWithSub", "START");
					break;
				case "Shift_T":
					footercount = 0;
					processAnimation(Constants.BACK, print_writers, "Change$PlayingXI", "START");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Partnership", "START");
					break;
				case "Control_F7": 
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Teams", "START");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Worm", "START");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Manhattan", "START");
					break;
				case "p":
					processAnimation(Constants.BACK, print_writers, "Change$PointsTable", "START");
					break;
				case "Alt_Shift_J":
					processAnimation(Constants.BACK, print_writers, "Change$BatManhatton", "START");
					break;
				case "Control_F1":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$ImageBattingCard", "START");
					break;
				}
			}
			TimeUnit.MILLISECONDS.sleep(500);
			if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
				switch (whatToProcess.split(",")[0]) {
				case "F1":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BattingCard", "START");
					break;
				case "Control_Shift_F1": case "Control_Shift_F2":
					if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F1") && 
							!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
						processAnimation(Constants.BACK, print_writers, "MoveForSplitCard", "START");
						processAnimation(Constants.BACK, print_writers, "Change_Fullframes$SplitCard", "START");
					}
					break;
				case "F2":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BowlingCard", "START");
					break;
				case "Control_F11": case "Shift_F11":
					if(!whatToProcess.equalsIgnoreCase("Control_F11") && !whatToProcess.equalsIgnoreCase("Shift_F11")) {
						processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Summary", "START");
					}
					break;
				case "Alt_Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$InningsSummary", "START");
					break;
				case "Alt_Shift_F12":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PhasewiseRunRates", "START");
					break;
				case "Alt_Shift_F9":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PhasewiseSummary", "START");
					break;
				case "Alt_Shift_F2":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BattingComparison", "START");
					break;
				case "F4":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PartnershipList", "START");
					break;
				case "Shift_K":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Partnership", "START");
					break;
				case "Alt_F9":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Team", "START");
					break;
				case "Shift_F8":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$TeamWithSub", "START");
					break;
				case "Control_F7": 
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Teams", "START");
					break;
				case "Shift_T":
					processAnimation(Constants.BACK, print_writers, "Change$PlayingXI", "START");
					break;
				case "Shift_F10":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Worm", "START");
					break;
				case "Control_F10":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Manhattan", "START");
					break;
				case "p":
					processAnimation(Constants.BACK, print_writers, "Change$PointsTable", "START");
					break;
				case "Alt_Shift_J":
					processAnimation(Constants.BACK, print_writers, "Change$BatManhatton", "START");
					break;
				case "Control_F1":
					processAnimation(Constants.BACK, print_writers, "Change_Fullframes$ImageBattingCard", "START");
					break;
				}
			}
			break;
		}
		return CricketUtil.YES;
	}
	public String T20_MumbaiCutBack(String whatToProcess, List<PrintWriter> print_writers,Configuration config) throws InterruptedException, IOException {
		switch (whatToProcess.split(",")[0]) {
		case "Shift_F12":
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Change_Ident", "SHOW 0.0");
			break;
		case "Alt_7":
			processAnimation(Constants.FRONT, print_writers, "Change_Section2", "SHOW 0.0");
			break;
		case "Alt_2":
			processAnimation(Constants.FRONT, print_writers, "Change_Section1", "SHOW 0.0");
			break;
		case "Alt_0":
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Promo_Graphics$Change", "SHOW 0.0");
			break;
		case "Alt_5":
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Change_Section4", "SHOW 0.0");
			break;
		case "Alt_3": case "Alt_4": case "Alt_6": case "Alt_9":
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Analytics$Change", "SHOW 0.0");
			break;
		case "Alt_8":
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.FRONT, print_writers, "anim_Infobar$Change_Section3", "SHOW 0.0");
			break;
			
		case "Control_Shift_U": case "Control_Shift_V":
			processAnimation(Constants.FRONT, print_writers, "Change_PopUp", "SHOW 0.0");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Control_Shift_U_change_on": case "Control_Shift_V_change_on":
			processAnimation(Constants.FRONT, print_writers, "Change_PopUp$Data", "SHOW 0.0");
			this.whichGraphicOnScreen = whatToProcess.replace("_change_on", "");
			break;
			
		case "r":
			processAnimation(Constants.FRONT, print_writers, "DRS_Change", "SHOW 0.0");
			this.whichGraphicOnScreen = whatToProcess;
			break;
			
		case "F8": case "Alt_F8": case "F10": case "Control_F9":case "Control_F5":
			processAnimation(Constants.FRONT, print_writers, "Change_LT_NameSuper", "SHOW 0.0");
			break;
			
		case "F5":case "F9":case "l":case "Shift_F5":case "Shift_F9":case "Control_h":case "Alt_F12":case "F7": case "F11":
		case "Control_a": case "u": case "q": case "Control_q": case "Shift_F3": case "Control_F3": case "Shift_B":
		case "Alt_Shift_F3":case "Alt_Shift_O":case "d": case "e": case "Control_i":
		case "Alt_F1": case "Alt_F2": case "Control_s": case "Control_f":
//			if(whatToProcess.contains("Player_ChangeON")) {
//				processAnimation(Constants.FRONT, print_writers, "Body$Side2", "SHOW 0.0");
//			}else {
//				processAnimation(Constants.FRONT, print_writers, "HeaderChange$Side2", "SHOW 0.0");
//				processAnimation(Constants.FRONT, print_writers, "Body$Side2", "SHOW 0.0");
//				processAnimation(Constants.FRONT, print_writers, "Body$Side1$In", "SHOW 0.438");
//				processAnimation(Constants.FRONT, print_writers, "BaseWidth", "START");
//				processAnimation(Constants.FRONT, print_writers, "HeaderChange$Side1$In", "SHOW 1.160");
//			}
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut", "SHOW 2.080");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Essentials", "SHOW 2.080");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Colours", "SHOW 2.080");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Logo", "SHOW 2.080");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$TopLine", "SHOW 2.080");
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird$InOut$Subline", "SHOW 2.080");
			
			TimeUnit.MILLISECONDS.sleep(2000);
			processAnimation(Constants.FRONT, print_writers, "ChangeLowerThird", "SHOW 0.0");
			break;
		 case "j":
			processAnimation(Constants.FRONT, print_writers, "NameSuperChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "NameSuper", "SHOW 1.640");
			break;
			
		 case "Shift_T":
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Elements", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$ColourBase", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$VerticalText", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Logo", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Header", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$SubHeader", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Footer", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Wipe", "SHOW 0.0");
			
			processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup$Image_Lineup", "SHOW 0.0");
			this.whichGraphicOnScreen = whatToProcess;
			break;
			
		case "F1": case "Control_Shift_F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Control_F7": 
		case "Shift_F11": case "Shift_F10": case "p": case "Control_F1": case "Alt_F9": case "Shift_F8": case "Control_F10": 
		case "Control_Shift_F2": case "Alt_Shift_F10": case "Alt_Shift_F12": case "Alt_Shift_F9": case "Alt_Shift_F2":
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Elements", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$ColourBase", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$VerticalText", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Logo", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Header", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$SubHeader", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Footer", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Wipe", "SHOW 0.0");
			switch(whichGraphicOnScreen.split(",")[0]) {
			case "F1":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BattingCard", "SHOW 0.0");
				break;
			case "Control_Shift_F1": case "Control_Shift_F2":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$SplitCard", "SHOW 0.0");
				break;
			case "F2":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BowlingCard", "SHOW 0.0");
				break;
			case "Control_F11": case "Shift_F11":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Summary", "SHOW 0.0");
				break;
			case "Alt_Shift_F10":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$InningsSummary", "SHOW 0.0");
				break;
			case "Alt_Shift_F12":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PhasewiseRunRates", "SHOW 0.0");
				break;
			case "Alt_Shift_F9":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PhasewiseSummary", "SHOW 0.0");
				break;
			case "Alt_Shift_F2":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BattingComparison", "SHOW 0.0");
				break;
			case "F4":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Partnership", "SHOW 0.0");
				break;
			case "Alt_F9":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Team", "SHOW 0.0");
				break;
			case "Shift_F8":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$TeamWithSub", "SHOW 0.0");
				break;
			case "Shift_K":
				processAnimation(Constants.BACK, print_writers, "Change$Partnership", "SHOW 0.0");
				break;
			case "Control_F7":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Teams", "SHOW 0.0");
				break;
			case "Shift_F10":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Worm", "SHOW 0.0");
				break;
			case "Control_F10":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Manhattan", "SHOW 0.0");
				break;
			case "p":
				processAnimation(Constants.BACK, print_writers, "Change$PointsTable", "SHOW 0.0");
				break;
			case "Alt_Shift_J":
				processAnimation(Constants.BACK, print_writers, "Change$BatManhatton", "SHOW 0.0");
				break;
			case "Control_F1":
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$ImageBattingCard", "SHOW 0.0");
				break;
			}
			
			switch(whatToProcess.split(",")[0]) {
			case "F1":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$BattingCard", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BattingCard", "SHOW 0.0");
				break;
			case "Control_Shift_F1": case "Control_Shift_F2":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$SplitCard", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$SplitCard", "SHOW 0.0");
				break;
			case "F2":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$BowlingCard", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BowlingCard", "SHOW 0.0");
				break;
			case "Control_F11": case "Shift_F11":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Summary", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Summary", "SHOW 0.0");
				break;
			case "Alt_Shift_F10":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$InningsSummary", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$InningsSummary", "SHOW 0.0");
				break;
			case "Alt_Shift_F12":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$PhasewiseRunRates", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PhasewiseRunRates", "SHOW 0.0");
				break;
			case "Alt_Shift_F9":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$PhasewiseSummary", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PhasewiseSummary", "SHOW 0.0");
				break;
			case "Alt_Shift_F2":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$BattingComparison", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$BattingComparison", "SHOW 0.0");
				break;
			case "F4":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$PartnershipList", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$PartnershipList", "SHOW 0.0");
				break;
			case "Alt_F9":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Team", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Team", "SHOW 0.0");
				break;
			case "Shift_F8":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$TeamWithSub", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$TeamWithSub", "SHOW 0.0");
				break;
			case "Shift_K":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Partnership", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Partnership", "SHOW 0.0");
				break;
			case "Control_F7": 
				processAnimation(Constants.BACK, print_writers, "FullFramers$Main$TeamSquad", "SHOW 1.480");
				processAnimation(Constants.BACK, print_writers, "Change$TeamSquad", "SHOW 0.0");
				break;
			case "Shift_F10":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Worm", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Worm", "SHOW 0.0");
				break;
			case "Control_F10":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$Manhattan", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$Manhattan", "SHOW 0.0");
				break;
			case "p":
				processAnimation(Constants.BACK, print_writers, "FullFramers$Main$PointsTable", "SHOW 1.480");
				processAnimation(Constants.BACK, print_writers, "Change$PointsTable", "SHOW 0.0");
				break;
			case "Alt_Shift_J":
				processAnimation(Constants.BACK, print_writers, "FullFramers$Main$BatManhatton", "SHOW 1.480");
				processAnimation(Constants.BACK, print_writers, "Change$BatManhatton", "SHOW 0.0");
				break;
			case "Control_F1":
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames$In_Out$Main$ImageBattingCard", "SHOW 2.820");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes$ImageBattingCard", "SHOW 0.0");
				break;
			}
			this.whichGraphicOnScreen = whatToProcess;
			break;
		}
		return CricketUtil.YES;
	}
	
	public String Lof_ISPL_AnimateIn(String whatToProcess, List<PrintWriter> print_writers,Configuration config) throws InterruptedException, IOException {
		
	switch (whatToProcess.split(",")[0]) {
		case "Alt_x":
			Lof_ISPL_AnimateIn("ArrowDown,", print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation("", print_writers, "anim_MVP$In_Out$Essentilas", "START");
			switch (whatToProcess.split(",")[2]) {
			case "PERFORMANCE":
				processAnimation("", print_writers, "anim_MVP$In_Out$PerformanceSubTitle", "START");
				processAnimation("", print_writers, "anim_MVP$In_Out$Performance", "START");
				break;
			default:
				processAnimation("", print_writers, "anim_MVP$In_Out$LeaderBoard", "START");
				break;
			}
			processAnimation("", print_writers, "Loop", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Shift_I":
			processAnimation(Constants.FRONT, print_writers, "Anim_GooglySub$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess.split(",")[0];
			break;
		case "Control_Shift_F10":
			processAnimation(Constants.FRONT, print_writers, "Anim_ROF_Manhattan$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Alt_p":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_Toss_Bug$obj_Position_Y$"
					+ "*TRANSFORMATION*POSITION*Y SET 0\0",print_writers);
			processAnimation(Constants.FRONT, print_writers, "Anim_Toss_Bug", "START");
			this.specialBugOnScreen = CricketUtil.TOSS;
			break;
		case "y": case "g": case "Shift_F": case "Shift_O": case "Control_k": case "Shift_F4": case "h": case "k": case "Control_Shift_F3":
		case "Control_Shift_R":	case "/": case "Control_y":
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Shift_C":	
			processAnimation(Constants.FRONT, print_writers, "Anim_SixDistanceBug$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;	
		case ".":
			processAnimation(Constants.FRONT, print_writers, "Anim_ChallengeBug$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Control_Shift_F11":
			processAnimation(Constants.FRONT, print_writers, "Anim_DRS_Bug$In_Out", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a":
		case "Control_F3": case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": 
		case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": 
		case "F8": case "F10": case "a": case "Alt_Shift_O": case "Alt_Shift_B": case "l":
			if(this.infobar.isBottom_infobar_on_screen()) {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_LowerThirds$Position_With_Graphics$"
						+ "*TRANSFORMATION*POSITION*X SET 40\0",print_writers);
			}else {
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_LowerThirds$Position_With_Graphics$"
						+ "*TRANSFORMATION*POSITION*X SET 0\0",print_writers);
			}
			
			if(!this.specialBugOnScreen.isEmpty() && (whatToProcess.split(",")[0].equalsIgnoreCase("F8") || 
					whatToProcess.split(",")[0].equalsIgnoreCase("Alt_F8") || whatToProcess.split(",")[0].equalsIgnoreCase("F10"))) {
				processAnimation(Constants.FRONT, print_writers, "TossBugPosition", "START");
				TimeUnit.MILLISECONDS.sleep(500);
			}
			
			processAnimation(Constants.FRONT, print_writers, "Anim_Lower_Third", "START");
			this.whichGraphicOnScreen = whatToProcess;
			break;
	
		case "m": case "Control_m":
			Lof_ISPL_AnimateIn("ArrowDown,", print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.BACK, print_writers, "anim_MatchId$In_Out", "START");
			processAnimation(Constants.BACK, print_writers, "Loop", "START");
			
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "Audio", "START");
			}
			
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Shift_F8":
			Lof_ISPL_AnimateIn("ArrowDown,", print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.BACK, print_writers, "anim_BigImageLineUp", "START");
			processAnimation(Constants.BACK, print_writers, "Loop", "START");
			
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "Audio", "START");
			}
			this.whichScorecard = caption.this_fullFramesGfx.WhichScoreCard;
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "F1": case "Shift_T": case "F4": case "Control_F7": case "Shift_K": case "Control_F10": case "Shift_F10": case "Control_F11": 
		case "Shift_F11": case "p": case "F2": case "Alt_F11": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
		case "Control_Shift_Z": case "Control_Shift_Y":	case "Alt_F5": case "Control_Shift_F7": case "Alt_Shift_F11": case "Control_c":
		case "Control_v": case "Control_Shift_F8":
			Lof_ISPL_AnimateIn("ArrowDown,", print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.BACK, print_writers, "Logo_WipeStart", "START");
			processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Essentials", "START");
			processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Header", "START");
			processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$SubHeader", "START");
			processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main", "START");
			if(!whatToProcess.split(",")[0].contains("Shift_T") && !whatToProcess.split(",")[0].contains("Control_Shift_F7")
					&& !whatToProcess.split(",")[0].contains("Shift_F8")) {
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Footer", "START");
			}
			processAnimation(Constants.BACK, print_writers, "Loop", "START");
			
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "Audio", "START");
			}
			this.whichScorecard = caption.this_fullFramesGfx.WhichScoreCard;
			this.whichBowlingCard = caption.this_fullFramesGfx.WhichBallCard;
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Shift_D":
			Lof_ISPL_AnimateIn("ArrowDown,", print_writers, config); // Push infobar
			TimeUnit.MILLISECONDS.sleep(500);
			processAnimation(Constants.BACK, print_writers, "anim_Target$In_Out", "START");
			processAnimation(Constants.BACK, print_writers, "Loop", "START");
			
			if(audioenabled.equalsIgnoreCase("TRUE")) {
				processAnimation(Constants.BACK, print_writers, "Audio", "START");
			}
			
			this.whichGraphicOnScreen = whatToProcess;
			break;
				
		case "Control_1":
			processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Bonus_In", "START");
			break;	
		case "Control_F12":
			caption.this_lofInfobarGfx.setPositionOfScoreBug("Control_F12,", 2, config, 0);
			if(this.infobar.isInfobar_on_screen() == true) {
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$IdentSubline", "START");
				
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "CONTINUE");
				
				if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
					processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
					this.infobar.setTarget_pushed(false);
				}
				
				TimeUnit.MILLISECONDS.sleep(1000);
				switch (whatToProcess.split(",")[2]) {
				case CricketUtil.TOSS:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side1" + 
							"$Select_Subline*FUNCTION*Omo*vis_con SET 1\0", print_writers);
					break;
				case "VENUE": case "SUPEROVER":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side1" + 
							"$Select_Subline*FUNCTION*Omo*vis_con SET 2\0", print_writers);
					break;
				}
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$IdentSubline", "SHOW 0.0");
				
			}else {
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Essentials", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$WaterMark", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Ident", "START");
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				
			}
			
			infobar.setMiddle_section("");
			infobar.setFull_section("");
			infobar.setRight_bottom("");
			infobar.setRight_section("");
			
			this.infobar.setInfobar_on_screen(true);
			caption.this_lofInfobarGfx.infobar.setInfobar_on_screen(true);
			
			this.infobar.setBottom_infobar_on_screen(false);
			caption.this_lofInfobarGfx.infobar.setBottom_infobar_on_screen(false);
			
			TimeUnit.MILLISECONDS.sleep(1000);
			caption.this_lofInfobarGfx.setPositionOfScoreBug("Control_F12,", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "SHOW 0.0");
			break;
		case "Alt_y":
			if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == false) {
				processAnimation(Constants.FRONT, print_writers, "Target", "START");
				this.infobar.setTarget_pushed(true);
			}else if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
				processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
				this.infobar.setTarget_pushed(false);
			}
			break;
			
		case "Alt_1": case "Control_5": case "Alt_5": case "Alt_9": case "Alt_0": case "Control_Shift_(": case "6": case "Control_4":
		case "Control_Alt_3":
			if(caption.this_lofInfobarGfx.infobar.isInfobar_on_screen()) {
				switch(caption.this_lofInfobarGfx.infobar.getFull_section().toUpperCase()) {
				case CricketUtil.PROJECTED:
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ProjectedScore", "START");
					break;
				case CricketUtil.BOUNDARY:
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Boundaries", "START");
					break;
				case CricketUtil.EXTRAS:
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Extras", "START");
					break;
				case "LAST_WICKET":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastWicket", "START");
					break;
				case "BALLS_SINCE_LAST_BOUNDARY":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$BallSince", "START");
					break;
				case "THIS_OVER":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ThisOver", "START");
					processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side1$Offset", "START");
					if(caption.this_lofInfobarGfx.this_over_balls > 6) {
						for(int i=1;i<=caption.this_lofInfobarGfx.this_over_balls;i++) {
							processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side1$Ball" + i, "START");
						}
					}else {
						for(int i=1;i<=6;i++) {
							processAnimation(Constants.FRONT, print_writers, "BottomBalls$Side1$Ball" + i, "START");
						}
					}
					//processAnimation(Constants.FRONT, print_writers, "BottomBalls", "START");
					break;
				case "THIS_OVER_RUNS":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$CumullativeThisOverSmall", "START");
					break;
				case "CRR": case "RRR": case "REVIEWS_REMAINING":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRates", "START");
					break;
				case "LINE_UP":
					processAnimation(Constants.FRONT, print_writers, "OutFor11", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Playing11", "START");
					break;
				case "LAST_X_BALLS":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRateInnings1", "START");
					break;
				case "LAST_X_BALLS_WITHOUT_CRR":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastX", "START");
					break;
				case "COMPARE":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Comparison", "START");
					break;
				case "EQUATION": case CricketUtil.RESULT:
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Equation", "START");
					break;
				case "COMMENTATORS": case "FREE_TEXT": case CricketUtil.TOSS:
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$GenericText1Line", "START");
					break;
				case "OVER_TIMELINE":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$TimeLine", "START");
					break;
				case "TIMELINE":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$14BallTimeline", "START");
					break;
				case "SIXES_COUNTER": case "FOUR_COUNTER": case "NINE_COUNTER":
					processAnimation(Constants.FRONT, print_writers, "BoundaryCounter$In_Out", "START");
					TimeUnit.MILLISECONDS.sleep(1300);
					if(!caption.this_lofInfobarGfx.this_data_str.get(caption.this_lofInfobarGfx.this_data_str.size()-2).split(",")[0].
							equalsIgnoreCase(caption.this_lofInfobarGfx.this_data_str.get(caption.this_lofInfobarGfx.this_data_str.size()-1).split(",")[0])) {
						processAnimation(Constants.FRONT, print_writers, "BoundaryCounter$Change$Hundredths", "START");
						processAnimation(Constants.FRONT, print_writers, "BoundaryCounter$Change$Tenths", "START");
						processAnimation(Constants.FRONT, print_writers, "BoundaryCounter$Change$Units", "START");
					}
					else if(!caption.this_lofInfobarGfx.this_data_str.get(caption.this_lofInfobarGfx.this_data_str.size()-2).split(",")[1].
							equalsIgnoreCase(caption.this_lofInfobarGfx.this_data_str.get(caption.this_lofInfobarGfx.this_data_str.size()-1).split(",")[1])) {
						processAnimation(Constants.FRONT, print_writers, "BoundaryCounter$Change$Tenths", "START");
						processAnimation(Constants.FRONT, print_writers, "BoundaryCounter$Change$Units", "START");
					}
					else if(!caption.this_lofInfobarGfx.this_data_str.get(caption.this_lofInfobarGfx.this_data_str.size()-2).split(",")[2].
							equalsIgnoreCase(caption.this_lofInfobarGfx.this_data_str.get(caption.this_lofInfobarGfx.this_data_str.size()-1).split(",")[2])) {
						processAnimation(Constants.FRONT, print_writers, "BoundaryCounter$Change$Units", "START");
					}
					break;
				case "EQUATION_SHORT_SB":
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ShortEquation", "START");
					break;
				}
				caption.this_lofInfobarGfx.infobar.setLast_full_section(caption.this_lofInfobarGfx.infobar.getFull_section());
			}
			break;
		
		case "Control_Alt_8":
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
			processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$MVP_LeaderBoard", "START");
			
			caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
			TimeUnit.MILLISECONDS.sleep(1200);
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			break;
			
		case "Alt_c":
//			if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
//				processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
//				this.infobar.setTarget_pushed(false);
//			}
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
			
			if(whatToProcess.split(",")[3].equalsIgnoreCase("CHALLENGED_IDENT")) {
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOverStart", "START");
			}else if(whatToProcess.split(",")[3].equalsIgnoreCase("CHALLENGED_RUNS")) {
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOver", "START");
				processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side1$Offset", "START");
				if(caption.this_lofInfobarGfx.cr_balls > 6) {
					for(int i=1;i<=caption.this_lofInfobarGfx.cr_balls;i++) {
						processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side1$Ball" + i, "START");
					}
				}else {
					for(int i=1;i<=6;i++) {
						processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side1$Ball" + i, "START");
					}
				}
			}else if(whatToProcess.split(",")[3].equalsIgnoreCase("CHALLENGED_RUNS_CUMM")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOver", "START");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CahllengeCumulative", "START");
			}
			caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
			TimeUnit.MILLISECONDS.sleep(1200);
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			break;
		case "Control_F8":
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
			if(this.infobar.isInfobar_on_screen() == true) {
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				if(whatToProcess.split(",")[2].equalsIgnoreCase("TAPE_BALL_FULL")) {
					
					if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
						processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
						this.infobar.setTarget_pushed(false);
					}
					
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOver", "START");
				}
				else if(whatToProcess.split(",")[2].equalsIgnoreCase("TAPE_BALL_SHORT")){
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOverStatic", "START");
				}
				caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
				TimeUnit.MILLISECONDS.sleep(1200);
				caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			}
			break;
		case "Alt_/":
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
			if(this.infobar.isInfobar_on_screen() == true) {
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				if(whatToProcess.split(",")[2].equalsIgnoreCase("SUPER_OVER_FULL")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "CONTINUE");
					
					if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
						processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
						this.infobar.setTarget_pushed(false);
					}
					
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStart", "START");
				}
				else if(whatToProcess.split(",")[2].equalsIgnoreCase("SUPER_OVER_SHORT") || 
						whatToProcess.split(",")[2].equalsIgnoreCase("SUPER_OVER_THIS_OVER")){
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStatic", "START");
					
					if(whatToProcess.split(",")[2].equalsIgnoreCase("SUPER_OVER_THIS_OVER")){
						processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side1$Offset", "START");
						if(caption.this_lofInfobarGfx.cr_balls > 6) {
							for(int i=1;i<=caption.this_lofInfobarGfx.cr_balls;i++) {
								processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side1$Ball" + i, "START");
							}
						}else {
							for(int i=1;i<=6;i++) {
								processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side1$Ball" + i, "START");
							}
						}
					}
				}
				caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
				TimeUnit.MILLISECONDS.sleep(1200);
				caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			}
			break;
		
		case "Alt_2": case "Control_6": case "Control_7": case "Control_8": case "Control_9": case "Alt_F1": case "Alt_F2": case "Control_0":
		case "Shift_@": case "Shift_$": case "Control_Shift_@": case "Control_Alt_5": case "Alt_Shift_@": case "Control_Alt_6": case "Control_Alt_9": 
		case "Control_Alt_0": case "Control_Alt_7":
			
			switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
			case "BATTINGCARD": case "BOWLINGCARD": case "HOWOUT": case "CURR_PARTNERSHIP": case "TARGET": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
			case "SB_MATCH_PROMO": case "BAT_GRIFF": case "BALL_GRIFF": case "POINTS_TABLE": case "TEAM_FORMGUIDE": case "LB_MOST_RUNS": case "LB_MOST_WICKETS":
			case "LB_MOST_FOURS": case "LB_MOST_SIXES":	case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE": case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY":
			case "LB_TAPE_BALL_OVER":
				
				if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
					processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
					this.infobar.setTarget_pushed(false);
				}
				break;
			}
			
			switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
			case "BATTINGCARD": case "BOWLINGCARD": case "HOWOUT": case "IDENT": case "TARGET": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
			case "BAT_GRIFF": case "BALL_GRIFF":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "CONTINUE");
				break;
			case CricketUtil.BATSMAN:
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
				break;
			}
			
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
			
			switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
			case "BATTINGCARD": case "BAT_GRIFF":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BattingCard", "START");
				break;
			case "BOWLINGCARD": case "BALL_GRIFF":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BowlingCard", "START");
				break;
			case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$OutStat", "START");
				break;
			case "CURR_PARTNERSHIP":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CurrentPartnership", "START");
				break;
			case "TARGET":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BigTarget", "START");
				break;
			case "SB_MATCH_PROMO":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$NextMatch", "START");
				break;
			case "POINTS_TABLE":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Standings", "START");
				break;
			case "TEAM_FORMGUIDE":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$FormGuide", "START");
				break;
			case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": 
			case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$LeaderBoard", "START");
				break;
			}
			
			caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
			TimeUnit.MILLISECONDS.sleep(1200);
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			break;
			
		case "Alt_3": case "Alt_4":
			if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
				processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
				this.infobar.setTarget_pushed(false);
			}
			
			if(caption.this_lofInfobarGfx.infobar.getLast_middle_section() != null && 
					!caption.this_lofInfobarGfx.infobar.getLast_middle_section().isEmpty()) {
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "CONTINUE");
			}else {
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$IdentSubline", "START");
			}
			
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
			processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "START");
			switch (whatToProcess.split(",")[0]) {
			case "Alt_3":
				caption.this_lofInfobarGfx.infobar.setLast_middle_section("BAT_PROFILE_CAREER");
				break;
			case "Alt_4":
				caption.this_lofInfobarGfx.infobar.setLast_middle_section("BALL_PROFILE_CAREER");
				break;
			}
			TimeUnit.MILLISECONDS.sleep(1000);
			
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side1" + 
					"$Select_Subline*FUNCTION*Omo*vis_con SET 0\0", print_writers);
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$IdentSubline", "SHOW 0.0");
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",",1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			break;	
		case "F12": //Infobar
			if(this.infobar.isInfobar_on_screen()) {
				if(caption.this_lofInfobarGfx.infobar.getLast_middle_section() != null && 
						!caption.this_lofInfobarGfx.infobar.getLast_middle_section().isEmpty()) {
					
					if(!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase(CricketUtil.BATSMAN)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "CONTINUE");
					}else {
						processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$IdentSubline", "START");
					}
				}else {
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$IdentSubline", "START");
				}
				
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Essentials", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				
				TimeUnit.MILLISECONDS.sleep(2000);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Ident$SublineGrp$Side1$"
						+ "Select_Subline*FUNCTION*Omo*vis_con SET 0\0", print_writers);
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$IdentSubline", "SHOW 0.0");
				
			}else {
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Essentials", "START");
				processAnimation(Constants.FRONT, print_writers, "Loop", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Essentials", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$WaterMark", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Ident", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				
			}
			
			TimeUnit.MILLISECONDS.sleep(500);
			
			if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == false) {
				processAnimation(Constants.FRONT, print_writers, "Target", "START");
				this.infobar.setTarget_pushed(true);
			}
			
			this.infobar.setInfobar_on_screen(true);
			caption.this_lofInfobarGfx.infobar.setInfobar_on_screen(true);
			this.infobar.setInfobar_pushed(false);
			
			this.infobar.setBottom_infobar_on_screen(true);
			caption.this_lofInfobarGfx.infobar.setBottom_infobar_on_screen(true);
			
			caption.this_lofInfobarGfx.infobar.setLast_middle_section(CricketUtil.BATSMAN);
			caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "SHOW 0.0");
			break;
			
		case "ArrowUp":
			if(this.infobar.isInfobar_on_screen() == true && this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "Shrink_FF", "CONTINUE REVERSE");
				this.infobar.setInfobar_pushed(false);
			}
			
			TimeUnit.MILLISECONDS.sleep(500);
			if(caption.this_lofInfobarGfx.infobar.getLast_middle_section() != null) {
				switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
				case "CHALLENGED_RUNS": case "CHALLENGED_RUNS_CUMM": case "CHALLENGED_IDENT": case CricketUtil.BATSMAN:
				case "TAPE_BALL_SHORT": case "SUPER_OVER_SHORT":
					if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == false) {
						processAnimation(Constants.FRONT, print_writers, "Target", "START");
						this.infobar.setTarget_pushed(true);
					}
					break;
				}
			}
			
			break;
		case "ArrowDown":
			if(caption.this_lofInfobarGfx.infobar.getLast_middle_section() != null) {
				switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
				case "CHALLENGED_RUNS": case "CHALLENGED_RUNS_CUMM": case "CHALLENGED_IDENT": case CricketUtil.BATSMAN: 
				case "TAPE_BALL_SHORT": case "SUPER_OVER_SHORT":
					if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
						processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
						this.infobar.setTarget_pushed(false);
					}
					break;
				}
			}
			
			TimeUnit.MILLISECONDS.sleep(500);
			if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
				processAnimation(Constants.FRONT, print_writers, "Shrink_FF", "START");
				this.infobar.setInfobar_pushed(true);
				TimeUnit.MILLISECONDS.sleep(800);
			}
			break;
			
		case "i":
			switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
			case "CHALLENGED_RUNS": case "CHALLENGED_RUNS_CUMM":
				if(infobar.isFreeHit_on_screen() == false) {
					caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 2, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
					System.out.println("LAST : " + caption.this_lofInfobarGfx.infobar.getLast_middle_section());
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOver", "CONTINUE REVERSE");
					if(caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("CHALLENGED_RUNS_CUMM")) {
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CahllengeCumulative", "CONTINUE REVERSE");
					}
					
					TimeUnit.MILLISECONDS.sleep(500);
					processAnimation(Constants.FRONT, print_writers, "FreeHit", "START");
					TimeUnit.MILLISECONDS.sleep(1200);
					caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 1, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
					infobar.setFreeHit_on_screen(true);
				}else if(infobar.isFreeHit_on_screen() == true) {
					caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getLast_middle_section()+",", 2, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
					processAnimation(Constants.FRONT, print_writers, "FreeHit", "CONTINUE REVERSE");
					
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOver", "START");
					if(caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("CHALLENGED_RUNS_CUMM")) {
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CahllengeCumulative", "START");
					}
					
					TimeUnit.MILLISECONDS.sleep(1200);
					caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getLast_middle_section()+",", 1, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
					infobar.setFreeHit_on_screen(false);
				}
				break;
			case "TAPE_BALL_SHORT": case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER":
				if(infobar.isFreeHit_on_screen() == false) {
					caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 2, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
					switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
					case "TAPE_BALL_SHORT":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOverStatic", "CONTINUE REVERSE");
						break;
					case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStatic", "CONTINUE REVERSE");
						break;
					}
					
					processAnimation(Constants.FRONT, print_writers, "FreeHit", "START");
					TimeUnit.MILLISECONDS.sleep(1200);
					caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 1, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
					infobar.setFreeHit_on_screen(true);
				}else if(infobar.isFreeHit_on_screen() == true) {
					caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getLast_middle_section()+",", 2, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
					processAnimation(Constants.FRONT, print_writers, "FreeHit", "CONTINUE REVERSE");
					
					switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
					case "TAPE_BALL_SHORT":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOverStatic", "START");
						break;
					case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStatic", "START");
						break;
					}
					
					TimeUnit.MILLISECONDS.sleep(1200);
					caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getLast_middle_section()+",", 1, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
					infobar.setFreeHit_on_screen(false);
				}
				break;
			default:
				if(infobar.isFreeHit_on_screen() == false) {
					caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 2, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
					processAnimation(Constants.FRONT, print_writers, "FreeHit", "START");
					infobar.setFreeHit_on_screen(true);
				}else if(infobar.isFreeHit_on_screen() == true) {
					processAnimation(Constants.FRONT, print_writers, "FreeHit", "CONTINUE REVERSE");
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "CONTINUE REVERSE");
					infobar.setFreeHit_on_screen(false);
				}
				break;
			}
			break;
		case ";":
			switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
			case "CHALLENGED_RUNS": case "CHALLENGED_RUNS_CUMM":
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
//						+ "ChallengeOver$EventAnimations*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
//					processAnimation(Constants.FRONT, print_writers, "LeftEventAnimation$9_Animation$Side1", "START");
					processAnimation(Constants.FRONT, print_writers, "EventAnimation_Top$9", "START");
				}
				break;
			case "TAPE_BALL_SHORT": case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER":
				processAnimation(Constants.FRONT, print_writers, "EventAnimation_Top$9", "START");
				
//				caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 2, config, 0);
//				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
//				processAnimation(Constants.FRONT, print_writers, "9_Animation", "START");
//				switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
//				case "TAPE_BALL_SHORT":
//					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOverStatic", "CONTINUE REVERSE");
//					break;
//				case "SUPER_OVER_SHORT":
//					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStatic", "CONTINUE REVERSE");
//					break;
//				}
//				TimeUnit.MILLISECONDS.sleep(4500);
//				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "CONTINUE REVERSE");
//				switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
//				case "TAPE_BALL_SHORT":
//					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOverStatic", "START");
//					break;
//				case "SUPER_OVER_SHORT":
//					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStatic", "START");
//					break;
//				}
//				TimeUnit.MILLISECONDS.sleep(1200);
//				caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getLast_middle_section()+",", 1, config, 0);
//				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
				break;
			default:
//				caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 2, config, 0);
//				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
//				processAnimation(Constants.FRONT, print_writers, "9_Animation", "START");
//				
//				TimeUnit.MILLISECONDS.sleep(4500);
//				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "CONTINUE REVERSE");
				
				processAnimation(Constants.FRONT, print_writers, "EventAnimation_Top$9", "START");
				break;
			}
			break;
			
		case "0": case "8":
			switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
			case "CHALLENGED_RUNS": case "CHALLENGED_RUNS_CUMM":
				if(whatToProcess.split(",")[0].equalsIgnoreCase("0")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
							+ "ChallengeOver$EventAnimations*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("8")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
							+ "ChallengeOver$EventAnimations*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
				}
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "LeftEventAnimation$Side1", "START");
				}
				break;
			case "SUPER_OVER_THIS_OVER":
				if(whatToProcess.split(",")[0].equalsIgnoreCase("0")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
							+ "SuperOverStatic$EventAnimations*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("8")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
							+ "SuperOverStatic$EventAnimations*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
				}
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "SuperOverAnimation$Side1", "START");
				}
				break;
			default:
				if(whatToProcess.split(",")[0].equalsIgnoreCase("0")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$"
							+ "Select_Animation*FUNCTION*Omo*vis_con SET 3 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("8")) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$"
							+ "Select_Animation*FUNCTION*Omo*vis_con SET 4 \0", print_writers);
				}
				
				caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 2, config, 0);
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				
				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
					processAnimation(Constants.FRONT, print_writers, "EventAnimation", "START");
				}
				TimeUnit.MILLISECONDS.sleep(2000);
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "CONTINUE REVERSE");
				break;
			}
			break;
			
		case "w":  case "f": case "s":
			switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
			case "CHALLENGED_RUNS": case "CHALLENGED_RUNS_CUMM":
				if(whatToProcess.split(",")[0].equalsIgnoreCase("w")) {
					processAnimation(Constants.FRONT, print_writers, "EventAnimation_Top$Wickets", "START");
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
//							+ "ChallengeOver$EventAnimations*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("f")) {
					processAnimation(Constants.FRONT, print_writers, "EventAnimation_Top$Boundaries", "START");
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$BoundaryValue"
							+ "$txt_BoundaryTop*GEOM*TEXT SET " + "4" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$BoundaryValue"
							+ "$txt_Boundary*GEOM*TEXT SET " + "4" + "\0", print_writers);
					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
//							+ "ChallengeOver$EventAnimations$Boundaries$txt_Boundaries*GEOM*TEXT SET " + "4" + "\0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
//							+ "ChallengeOver$EventAnimations*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("s")) {
					processAnimation(Constants.FRONT, print_writers, "EventAnimation_Top$Boundaries", "START");
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$BoundaryValue"
							+ "$txt_BoundaryTop*GEOM*TEXT SET " + "6" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$BoundaryValue"
							+ "$txt_Boundary*GEOM*TEXT SET " + "6" + "\0", print_writers);
					
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
//							+ "ChallengeOver$EventAnimations$Boundaries$txt_Boundaries*GEOM*TEXT SET " + "6" + "\0", print_writers);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$AllSections$Section2$Side1$"
//							+ "ChallengeOver$EventAnimations*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
				}
//				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
//					processAnimation(Constants.FRONT, print_writers, "LeftEventAnimation$Side1", "START");
//				}
				break;
			default:
				if(whatToProcess.split(",")[0].equalsIgnoreCase("w")) {
					processAnimation(Constants.FRONT, print_writers, "EventAnimation_Top$Wickets", "START");
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("f")) {
					processAnimation(Constants.FRONT, print_writers, "EventAnimation_Top$Boundaries", "START");
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$BoundaryValue"
							+ "$txt_BoundaryTop*GEOM*TEXT SET " + "4" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$BoundaryValue"
							+ "$txt_Boundary*GEOM*TEXT SET " + "4" + "\0", print_writers);
				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("s")) {
					processAnimation(Constants.FRONT, print_writers, "EventAnimation_Top$Boundaries", "START");
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$BoundaryValue"
							+ "$txt_BoundaryTop*GEOM*TEXT SET " + "6" + "\0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$NewEventAnimation$Boundaries$BoundaryValue"
							+ "$txt_Boundary*GEOM*TEXT SET " + "6" + "\0", print_writers);
				}
				
//				if(whatToProcess.split(",")[0].equalsIgnoreCase("w")) {
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$"
//							+ "Select_Animation*FUNCTION*Omo*vis_con SET 2 \0", print_writers);
//				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("f")) {
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$"
//							+ "Select_Animation*FUNCTION*Omo*vis_con SET 0 \0", print_writers);
//				}else if(whatToProcess.split(",")[0].equalsIgnoreCase("s")) {
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$gfx_L_BandInfo$Left_DataGrp$Main$Animations$"
//							+ "Select_Animation*FUNCTION*Omo*vis_con SET 1 \0", print_writers);
//				}
				
//				caption.this_lofInfobarGfx.setPositionOfScoreBug(whatToProcess.split(",")[0]+",", 2, config, 0);
//				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
//				
//				if(this.infobar.isInfobar_on_screen() == true && !this.infobar.isInfobar_pushed()) {
//					processAnimation(Constants.FRONT, print_writers, "EventAnimation", "START");
//				}
//				TimeUnit.MILLISECONDS.sleep(2000);
//				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "CONTINUE REVERSE");
				break;
			}
			break;
		case "Control_2":
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$InfoBar$CenterGRp$Main$BattingTeamGrp$PowerPlay$txt_PP*GEOM*TEXT SET " + 
//							"POWERPLAY" + "\0", print_writers);
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_InfoBar$Main$PowerPlay_In START \0", print_writers);
			break;
		case "Control_3":
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_InfoBar$Main$PowerPlay_In CONTINUE REVERSE \0", print_writers);
			break;	
		case "Alt_f": case "Alt_g": case Constants.SHRUNK_INFOBAR: case Constants.MIDDLE + Constants.SHRUNK_INFOBAR:
//					System.out.println("this.infobar.isInfobar_on_screen() = " + this.infobar.isInfobar_on_screen());
//					System.out.println("whatToProcess = " + whatToProcess);
//					System.out.println("this.infobar.setInfobar_status = " + this.infobar.getInfobar_status());
			if(this.infobar.isInfobar_on_screen() == true) {
				switch (whatToProcess.split(",")[0]) {
				case "Alt_f":
					if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
//								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
//									+ "ANIMATION*KEY*$Shrink_In*VALUE SET 120.0 -436.0 0.0\0",print_writers);
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "START");
						this.infobar.setInfobar_status(Constants.FORCED + Constants.SHRUNK_INFOBAR);
					} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.SHRUNK_INFOBAR)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$Out", "START");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
					}
					break;
				case "Alt_g":
					if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
//								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
//									+ "ANIMATION*KEY*$Shrink_In*VALUE SET 183.0 -436.0 0.0\0",print_writers);
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "START");
						this.infobar.setInfobar_status(Constants.FORCED + Constants.MIDDLE + Constants.SHRUNK_INFOBAR);
					} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.FORCED + Constants.MIDDLE + Constants.SHRUNK_INFOBAR)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$Out", "START");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
					}
					break;
				case Constants.SHRUNK_INFOBAR:
					if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
//								CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
//									+ "ANIMATION*KEY*$Shrink_In*VALUE SET 120.0 -436.0 0.0\0",print_writers);
						if(infobar.isChallengeRunOnScreen() == true) {
							processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TopStage", "CONTINUE");
							infobar.setChallengeRunOnScreen(false);
						}
						
						if(this.targetOnScreen.equalsIgnoreCase("TARGET")) {
							processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Target_Out", "START");
							this.targetOnScreen = "";
						}
						
						
						if(this.tapeballOnScreen.equalsIgnoreCase("TAPE")) {
							processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TapedBall_Out", "START");
							this.tapeballOnScreen = "";
						}
						
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "START");
						this.infobar.setInfobar_status(Constants.SHRUNK_INFOBAR);
					} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.SHRUNK_INFOBAR)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$Out", "START");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
					}
					break;
				case Constants.MIDDLE + Constants.SHRUNK_INFOBAR:
					if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.TWO_LINER_INFOBAR)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*TREE*$Infobar$Overall_Transformation*"
							+ "ANIMATION*KEY*$Shrink_In*VALUE SET 183.0 -436.0 0.0\0",print_writers);
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small$In", "START");
						this.infobar.setInfobar_status(Constants.MIDDLE + Constants.SHRUNK_INFOBAR);
					} else if(this.infobar.getInfobar_status().equalsIgnoreCase(Constants.MIDDLE + Constants.SHRUNK_INFOBAR)) {
						processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Small$Out", "START");
						this.infobar.setInfobar_status(Constants.TWO_LINER_INFOBAR);
					}
					break;
				}
			}
			break;
		}
		return CricketUtil.YES;
	}
	public String Lof_ISPL_AnimateOut(String whatToProcess, List<PrintWriter> print_writers, Configuration config) throws InterruptedException, IOException
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.ISPL:
			switch (whatToProcess.split(",")[0]) {
			case "Alt_x":
				switch (whatToProcess.split(",")[2]) {
				case "PERFORMANCE":
					processAnimation("", print_writers, "anim_MVP$In_Out$PerformanceSubTitle", "CONTINUE");
					processAnimation("", print_writers, "anim_MVP$In_Out$Performance", "CONTINUE");
					break;
				default:
					processAnimation("", print_writers, "anim_MVP$In_Out$LeaderBoard", "CONTINUE");
					break;
				}
				processAnimation("", print_writers, "anim_MVP$In_Out$Essentilas", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(500);
				Lof_ISPL_AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				
				TimeUnit.MILLISECONDS.sleep(2500);
				processAnimation("", print_writers, "anim_MVP", "SHOW 0.0");
				break;
			case "Shift_I":
				processAnimation(Constants.FRONT, print_writers, "Anim_GooglySub$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				
				processAnimation(Constants.FRONT, print_writers, "Anim_GooglySub", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Change_GooglySub", "SHOW 0.0");
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_F10":
				processAnimation(Constants.FRONT, print_writers, "Anim_ROF_Manhattan$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(1000);
				
				processAnimation(Constants.FRONT, print_writers, "Anim_ROF_Manhattan", "SHOW 0.0");
				this.whichGraphicOnScreen = "";
				break;
			case "Alt_p":
				if(this.specialBugOnScreen.equalsIgnoreCase(CricketUtil.TOSS)) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Toss_Bug", "CONTINUE");
					this.specialBugOnScreen = "";
				}
				break;
			case "y": case "g": case "Shift_F": case "Shift_O": case "Control_k": case "Shift_F4": case "h": case "k": case "Control_Shift_F3":
			case "Control_Shift_R":	case "/": case "Control_y":
				processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$In_Out", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_C":
				processAnimation(Constants.FRONT, print_writers, "Anim_SixDistanceBug$In_Out", "CONTINUE");
				this.whichGraphicOnScreen = "";
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_SixDistanceBug$In_Out", "SHOW 0.0");
				break;	
			case ".":
				processAnimation(Constants.FRONT, print_writers, "Anim_ChallengeBug$In_Out", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			case "Control_Shift_F11":
				processAnimation(Constants.FRONT, print_writers, "Anim_DRS_Bug$In_Out", "CONTINUE");
				caption.this_bugsAndMiniGfx.isVisited = false;
				this.whichGraphicOnScreen = "";
				break;
			case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Alt_Shift_O": case "Alt_Shift_B": case "Control_F5": case "Control_F9": 
			case "Control_a": case "Control_F3": case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": 
			case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": case "F8": case "F10": 
			case "a": case "l":
				processAnimation(Constants.FRONT, print_writers, "Anim_Lower_Third", "CONTINUE");
				
				if(!this.specialBugOnScreen.isEmpty()) {
					processAnimation(Constants.FRONT, print_writers, "TossBugPosition", "CONTINUE REVERSE");
				}
				this.whichGraphicOnScreen = "";
				
				TimeUnit.MILLISECONDS.sleep(1000);
				processAnimation(Constants.FRONT, print_writers, "Anim_Lower_Third", "SHOW 0.0");
				break;
			
			case "m": case "Control_m":
				processAnimation(Constants.BACK, print_writers, "anim_MatchId$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(500);
				Lof_ISPL_AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
				
			case "Control_F8":
				caption.this_lofInfobarGfx.setPositionOfScoreBug(CricketUtil.BATSMAN+",", 2, config, 0);
//				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				
				if(!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("TAPE_BALL_SHORT")) {
					if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == false) {
						processAnimation(Constants.FRONT, print_writers, "Target", "START");
						this.infobar.setTarget_pushed(true);
					}
				}
				
//				if(caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("SUPER_OVER_FULL")) {
//					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
//				}
				
				switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section().toUpperCase()) {
				case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "CONTINUE");
					break;
				case "BATTINGCARD": case "BAT_GRIFF":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BattingCard", "CONTINUE");
					break;
				case "BOWLINGCARD": case "BALL_GRIFF":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BowlingCard", "CONTINUE");
					break;	
				case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$OutStat", "CONTINUE");
					break;
				case "SB_MATCH_PROMO":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$NextMatch", "CONTINUE");
					break;
				case "CURR_PARTNERSHIP":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CurrentPartnership", "CONTINUE");
					break;
				case "TARGET":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BigTarget", "CONTINUE");
					break;
				case "POINTS_TABLE":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Standings", "CONTINUE");
					break;
				case "TEAM_FORMGUIDE":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$FormGuide", "CONTINUE");
					break;
				case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": 
				case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$LeaderBoard", "CONTINUE");
					break;
				case "MVP_LB_IDENT": case "MVP_LB_SINGLE_PLAYER": case "MVP_LB_ALL_PLAYER":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$MVP_LeaderBoard", "CONTINUE");
					break;
				}
				
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
				case "CURR_PARTNERSHIP": case "SB_MATCH_PROMO": case "POINTS_TABLE": case "TEAM_FORMGUIDE": case "LB_MOST_RUNS": case "LB_MOST_WICKETS": 
				case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": 
				case "LB_TAPE_BALL_OVER": case "MVP_LB_IDENT": case "MVP_LB_SINGLE_PLAYER": case "MVP_LB_ALL_PLAYER": case "CHALLENGED_IDENT": 
				case "CHALLENGED_RUNS": case "CHALLENGED_RUNS_CUMM": case "TAPE_BALL_FULL": case "TAPE_BALL_SHORT": case "SUPER_OVER_FULL": 
				case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER":
					break;
				default:
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
					break;
				}
								
				TimeUnit.MILLISECONDS.sleep(1200);
				
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BattingCard", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BowlingCard", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$OutStat", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CurrentPartnership", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BigTarget", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$NextMatch", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Standings", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$FormGuide", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$LeaderBoard", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$MVP_LeaderBoard", "SHOW 0.0");
				
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CahllengeCumulative", "SHOW 0.0");
				
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStart", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStatic", "SHOW 0.0");
				
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOver", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOver", "SHOW 0.0");
				
				processAnimation(Constants.FRONT, print_writers, "LeftSideBalls", "SHOW 0.0");
				
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOverStart", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOverStatic", "SHOW 0.0");
				
				caption.this_lofInfobarGfx.infobar.setLast_middle_section(CricketUtil.BATSMAN);
				caption.this_lofInfobarGfx.infobar.setMiddle_section(CricketUtil.BATSMAN);
				caption.this_lofInfobarGfx.setPositionOfScoreBug(CricketUtil.BATSMAN+",", 1, config, 0);
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
				break;
			case "Shift_F8":
				Lof_ISPL_AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				processAnimation(Constants.BACK, print_writers, "anim_BigImageLineUp$In_Out", "SHOW 6.120");
				processAnimation(Constants.BACK, print_writers, "anim_BigImageLineUp$In_Out", "CONTINUE");
				this.whichGraphicOnScreen = "";
				break;
			case "F1": case "Shift_T": case "F4": case "Control_F7": case "Shift_K": case "Control_F10": case "Shift_F10": case "Control_F11": 
			case "Shift_F11": case "p": case "F2": case "z": case "x": case "c": case "v": case "Alt_F11": case "Control_z": case "Control_x":
			case "Control_Shift_Z": case "Control_Shift_Y": case "Alt_F5": case "Control_Shift_F7": case "Alt_Shift_F11": case "Control_c":
			case "Control_v": case "Control_Shift_F8":
				processAnimation(Constants.BACK, print_writers, "LogoWipeEnd", "SHOW 2.500");
				processAnimation(Constants.BACK, print_writers, "LogoWipeEnd", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Header", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$SubHeader", "CONTINUE");
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main", "CONTINUE");
				if(!whatToProcess.split(",")[0].contains("Shift_T") && !whatToProcess.split(",")[0].contains("Control_Shift_F7")
						&& !whatToProcess.split(",")[0].contains("Shift_F8")) {
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Footer", "CONTINUE");
				}
				TimeUnit.MILLISECONDS.sleep(500);
				Lof_ISPL_AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				break;
			case "Shift_D":
				processAnimation(Constants.BACK, print_writers, "anim_Target$In_Out", "CONTINUE");
				TimeUnit.MILLISECONDS.sleep(500);
				Lof_ISPL_AnimateIn("ArrowUp,", print_writers, config); // Push infobar
				this.whichGraphicOnScreen = "";
				
				processAnimation(Constants.BACK, print_writers, "anim_Target", "SHOW 0.0");
				break;
					
			case "Alt_y":
				if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
					processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
					this.infobar.setTarget_pushed(false);
					caption.this_lofInfobarGfx.infobar.setTarget_on_screen(false);
				}
				break;
				
			case "Shift_+":
				if(caption.this_lofInfobarGfx.infobar.getLast_full_section() != null && !caption.this_lofInfobarGfx.infobar.getLast_full_section().isEmpty()) {
					switch(caption.this_lofInfobarGfx.infobar.getLast_full_section().toUpperCase()) {
					case CricketUtil.PROJECTED:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ProjectedScore", "CONTINUE");
						break;
					case CricketUtil.BOUNDARY:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Boundaries", "CONTINUE");
						break;
					case CricketUtil.EXTRAS:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Extras", "CONTINUE");
						break;
					case "LAST_WICKET":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastWicket", "CONTINUE");
						break;
					case "BALLS_SINCE_LAST_BOUNDARY":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$BallSince", "CONTINUE");
						break;
					case "THIS_OVER":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ThisOver", "CONTINUE");
						break;
					case "THIS_OVER_RUNS":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$CumullativeThisOverSmall", "CONTINUE");
						break;
					case "CRR": case "RRR": case "REVIEWS_REMAINING":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRates", "CONTINUE");
						break;
					case "LINE_UP":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Playing11", "CONTINUE");
						processAnimation(Constants.FRONT, print_writers, "OutFor11", "CONTINUE REVERSE");
						break;
					case "LAST_X_BALLS":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRateInnings1", "CONTINUE");
						break;
					case "LAST_X_BALLS_WITHOUT_CRR":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastX", "CONTINUE");
						break;
					case "COMPARE":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Comparison", "CONTINUE");
						break;
					case "EQUATION": case CricketUtil.RESULT:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Equation", "CONTINUE");
						break;
					case "COMMENTATORS": case "FREE_TEXT": case CricketUtil.TOSS:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$GenericText1Line", "CONTINUE");
						break;
					case "OVER_TIMELINE":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$TimeLine", "CONTINUE");
						break;
					case "TIMELINE":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$14BallTimeline", "CONTINUE");
						break;
					case "SIXES_COUNTER": case "FOUR_COUNTER": case "NINE_COUNTER":
						processAnimation(Constants.FRONT, print_writers, "BoundaryCounter$In_Out", "CONTINUE");
						TimeUnit.MILLISECONDS.sleep(1200);
						processAnimation(Constants.FRONT, print_writers, "BoundaryCounter", "SHOW 0.0");
						break;
					case "EQUATION_SHORT_SB":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ShortEquation", "CONTINUE");
						break;
					}
					
					TimeUnit.MILLISECONDS.sleep(1000);
					switch(caption.this_lofInfobarGfx.infobar.getLast_full_section().toUpperCase()) {
					case CricketUtil.PROJECTED:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ProjectedScore", "SHOW 0.0");
						break;
					case CricketUtil.BOUNDARY:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Boundaries", "SHOW 0.0");
						break;
					case CricketUtil.EXTRAS:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Extras", "SHOW 0.0");
						break;
					case "LAST_WICKET":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastWicket", "SHOW 0.0");
						break;
					case "BALLS_SINCE_LAST_BOUNDARY":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$BallSince", "SHOW 0.0");
						break;
					case "THIS_OVER":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ThisOver", "SHOW 0.0");
						break;
					case "THIS_OVER_RUNS":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$CumullativeThisOverSmall", "SHOW 0.0");
						break;
					case "CRR": case "RRR": case "REVIEWS_REMAINING":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRates", "SHOW 0.0");
						break;
					case "LINE_UP":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Playing11", "SHOW 0.0");
						break;
					case "LAST_X_BALLS":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRateInnings1", "SHOW 0.0");
						break;
					case "LAST_X_BALLS_WITHOUT_CRR":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastX", "SHOW 0.0");
						break;
					case "COMPARE":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Comparison", "SHOW 0.0");
						break;
					case "EQUATION": case CricketUtil.RESULT:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Equation", "SHOW 0.0");
						break;
					case "COMMENTATORS": case "FREE_TEXT": case CricketUtil.TOSS:
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$GenericText1Line", "SHOW 0.0");
						break;
					case "OVER_TIMELINE":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$TimeLine", "SHOW 0.0");
						break;
					case "TIMELINE":
						processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$14BallTimeline", "SHOW 0.0");
						break;
					case "SIXES_COUNTER": case "FOUR_COUNTER":
						processAnimation(Constants.FRONT, print_writers, "BoundaryCounter$In_Out", "SHOW 0.0");
						break;
					}
				}else {
					caption.this_lofInfobarGfx.ResultAnimation("ANIMATE_OUT");
				}
				
				caption.this_lofInfobarGfx.infobar.setFull_section("");
				caption.this_lofInfobarGfx.infobar.setLast_full_section("");
				break;
			
			case "Alt_2":
				if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BATSMAN)) {
					
					if(caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("BAT_PROFILE_CAREER") || 
							caption.this_infobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("BALL_PROFILE_CAREER")) {
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "CONTINUE");
						processAnimation(Constants.FRONT, print_writers, "ExpandForData", "CONTINUE REVERSE");
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
						
						caption.this_infobarGfx.infobar.setLast_middle_section(CricketUtil.BATSMAN);
					}
				}else {
					if(infobar.getFull_section()!= null && !infobar.getFull_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Out", "START");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setFull_section("");
					}
					
					if(infobar.getMiddle_section() != null && !infobar.getMiddle_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_Change", "START");
					}else {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_In", "START");
					}
					infobar.setMiddle_section(whatToProcess.split(",")[2]);
				}
				break;
			case "Control_F12":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Essentials", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$WaterMark", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Ident", "CONTINUE");
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				
				this.infobar.setInfobar_on_screen(false);
				caption.this_lofInfobarGfx.infobar.setInfobar_on_screen(false);
				this.infobar.setBottom_infobar_on_screen(false);
				caption.this_lofInfobarGfx.infobar.setBottom_infobar_on_screen(false);
				break;
			case "F12": //Infobar
				if(infobar.isInfobar_on_screen() == true) {
					
					if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == true) {
						processAnimation(Constants.FRONT, print_writers, "Target", "CONTINUE REVERSE");
						this.infobar.setTarget_pushed(false);
					}
					
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Essentials", "CONTINUE");
					
					if(caption.this_lofInfobarGfx.infobar.getLast_full_section() != null && !caption.this_lofInfobarGfx.infobar.getLast_full_section().isEmpty()) {
						switch(caption.this_lofInfobarGfx.infobar.getLast_full_section().toUpperCase()) {
						case CricketUtil.PROJECTED:
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ProjectedScore", "CONTINUE");
							break;
						case CricketUtil.BOUNDARY:
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Boundaries", "CONTINUE");
							break;
						case CricketUtil.EXTRAS:
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Extras", "CONTINUE");
							break;
						case "LAST_WICKET":
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastWicket", "CONTINUE");
							break;
						case "BALLS_SINCE_LAST_BOUNDARY":
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$BallSince", "CONTINUE");
							break;
						case "THIS_OVER":
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ThisOver", "CONTINUE");
							break;
						case "CRR": case "RRR": case "REVIEWS_REMAINING":
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRates", "CONTINUE");
							break;
						case "LINE_UP":
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Playing11", "CONTINUE");
							processAnimation(Constants.FRONT, print_writers, "OutFor11", "CONTINUE REVERSE");
							break;
						case "LAST_X_BALLS":
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRateInnings1", "CONTINUE");
							break;
						case "LAST_X_BALLS_WITHOUT_CRR":
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastX", "CONTINUE");
							break;
						case "COMPARE":
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Comparison", "CONTINUE");
							break;
						case "EQUATION": case CricketUtil.RESULT:
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Equation", "CONTINUE");
							break;
						case "COMMENTATORS": case "FREE_TEXT":
							processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$GenericText1Line", "CONTINUE");
							break;
						}
					}else {
						caption.this_lofInfobarGfx.ResultAnimation("ANIMATE_OUT");
					}
					
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Essentials", "CONTINUE");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$WaterMark", "CONTINUE");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Ident", "CONTINUE");
					
					switch(caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
					case CricketUtil.BATSMAN:
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "CONTINUE");
						break;
					case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "CONTINUE");
						break;
					case "BATTINGCARD":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BattingCard", "CONTINUE");
						break;
					case "BOWLINGCARD":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BowlingCard", "CONTINUE");
						break;
						
					}
					
					TimeUnit.MILLISECONDS.sleep(1000);
					
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo", "SHOW 0.0");
//					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Main_In", "SHOW 0.0");
					
					caption.this_lofInfobarGfx.infobar.setMiddle_section("");
					caption.this_lofInfobarGfx.infobar.setFull_section("");
					
					caption.this_lofInfobarGfx.infobar.setLast_middle_section("");
					caption.this_lofInfobarGfx.infobar.setLast_full_section("");
					caption.this_lofInfobarGfx.infobar.setTarget_on_screen(false);
					
					infobar.setRight_bottom("");
					infobar.setRight_section("");
					
					this.infobar.setBottom_infobar_on_screen(false);
					caption.this_lofInfobarGfx.infobar.setBottom_infobar_on_screen(false);
					this.infobar.setInfobar_on_screen(false);
					caption.this_lofInfobarGfx.infobar.setInfobar_on_screen(false);
				}
				break;
			}
			break;	
		}
		return CricketUtil.YES;
	}	
	
	public String Lof_ISPL_CutBack(String whatToProcess, List<PrintWriter> print_writers,Configuration config) throws InterruptedException, IOException {
		
		if(!whatToProcess.contains(",")) {
			return CricketUtil.NO;
		}
		//FULL FRAMES
		switch(whatToProcess.split(",")[0]) {
		case "Alt_x":
			processAnimation("", print_writers, "Change_MVP$Performance", "SHOW 0.0");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "y": case "g": case "Shift_F": case "Control_k": case "Shift_O": case "Shift_F4": case "h": case "k": case "Shift_C": case "Control_Shift_F3":
		case "Control_Shift_R":	
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange$Logo", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange$Text", "SHOW 0.0");
			break;
		case "F1": case "Shift_T": case "Control_F10": case "F4": case "Shift_K": case "Control_F7": case "Shift_F10": case "Control_F11": case "Shift_F11": case "F2":
		case "Alt_F11": case "z": case "x": case "c": case "v": case "p": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Alt_F5":
		case "Control_Shift_F7": case "Shift_F8": case "Control_c": case "Control_v": case "Control_Shift_F8":
			switch(whatToProcess.split(",")[0]) {
			case "F1":
				if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("NORMAL")) {
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BatStyle2", "SHOW 3.200");
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle2", "SHOW 0.0");
				}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("SPLIT")) {
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$SplitBatBall_Card", "SHOW 3.200");
					processAnimation(Constants.BACK, print_writers, "Change$SplitBatBall_Card", "SHOW 0.0");
				}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("TRADITIONAL")) {
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BattingCard_Normal", "SHOW 3.200");
					processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Normal", "SHOW 0.0");
				}else {
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BatStyle3", "SHOW 3.200");
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "SHOW 0.0");
				}
				break;
			case "F2":
				if(caption.this_fullFramesGfx.WhichBallCard.equalsIgnoreCase("TRADITIONAL")) {
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BowlingCard_Normal", "SHOW 3.200");
					processAnimation(Constants.BACK, print_writers, "Change$BowlingCard_Normal", "SHOW 0.0");
				}else {
					processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$BatStyle3", "SHOW 3.200");
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "SHOW 0.0");
				}
				break;
			case "Shift_F10":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Worm", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$Worm", "SHOW 0.0");
				break;
			case "Shift_T":
				//processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$PlayingXI", "SHOW 3.200");
				//processAnimation(Constants.BACK, print_writers, "Change$PlayingXI", "SHOW 0.0");
				break;
			case "Shift_F8":
				processAnimation(Constants.BACK, print_writers, "anim_BigImageLineUp", "SHOW 2.900");
				break;
			case "Control_Shift_F7":
				//processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$LineUp_Single", "SHOW 3.200");
				//processAnimation(Constants.BACK, print_writers, "Change$LineUp_Single", "SHOW 0.0");
				break;
			case "Control_F10":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Manhattan", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$Manhattan", "SHOW 0.0");
				break;
			case "F4":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Partnership_List", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
				break;
			case "Shift_K":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Partnership", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$Partnership", "SHOW 0.0");
				break;
			case "Control_F7":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Both_Team", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$Both_Team", "SHOW 0.0");
				break;
			case "Control_F11": case "Shift_F11":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Summary", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
				break;
			case "Alt_F11":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Doublemanhattan", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$Doublemanhattan", "SHOW 0.0");
				break;
			case "Alt_F5":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$DoubleTeamManhattan", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$DoubleTeamManhattan", "SHOW 0.0");
				break;
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
			case "Control_c": case "Control_v": case "Control_Shift_F8":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$LeaderBoard_3Col", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard_3Col", "SHOW 0.0");
				break;
			case "p":
				processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main$Standings", "SHOW 3.200");
				processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
				break;
			}
			
			switch(whichGraphicOnScreen.split(",")[0]) {
			case "F1":
				if(whichScorecard.equalsIgnoreCase("NORMAL")) {
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle2", "SHOW 0.0");
				}else if(whichScorecard.equalsIgnoreCase("SPLIT")) {
					processAnimation(Constants.BACK, print_writers, "Change$SplitBatBall_Card", "SHOW 0.0");
				}else if(whichScorecard.equalsIgnoreCase("TRADITIONAL")) {
					processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Normal", "SHOW 0.0");
				}else {
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "SHOW 0.0");
				}
				whichScorecard = caption.this_fullFramesGfx.WhichScoreCard;
				break;
			case "F2":
				if(whichBowlingCard.equalsIgnoreCase("TRADITIONAL")) {
					processAnimation(Constants.BACK, print_writers, "Change$BowlingCard_Normal", "SHOW 0.0");
				}else {
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "SHOW 0.0");
				}
				whichBowlingCard = caption.this_fullFramesGfx.WhichBallCard;
				break;
			case "Shift_F10":
				processAnimation(Constants.BACK, print_writers, "Change$Worm", "SHOW 0.0");
				break;
			case "Shift_T":
				processAnimation(Constants.BACK, print_writers, "Change$PlayingXI", "SHOW 0.0");
				break;
			case "Shift_F8":
				processAnimation(Constants.BACK, print_writers, "Change_BigImageLineUp", "SHOW 0.0");
				break;	
			case "Control_Shift_F7":
				processAnimation(Constants.BACK, print_writers, "Change$LineUp_Single", "SHOW 0.0");
				break;
			case "Control_F10":
				processAnimation(Constants.BACK, print_writers, "Change$Manhattan", "SHOW 0.0");
				break;
			case "F4":
				processAnimation(Constants.BACK, print_writers, "Change$Partnership_List", "SHOW 0.0");
				break;
			case "Shift_K":
				processAnimation(Constants.BACK, print_writers, "Change$Partnership", "SHOW 0.0");
				break;
			case "Control_F7":
				processAnimation(Constants.BACK, print_writers, "Change$Both_Team", "SHOW 0.0");
				break;
			case "Control_F11": case "Shift_F11":
				processAnimation(Constants.BACK, print_writers, "Change$Summary", "SHOW 0.0");
				break;
			case "Alt_F11":
				processAnimation(Constants.BACK, print_writers, "Change$Doublemanhattan", "SHOW 0.0");
				break;
			case "Alt_F5":
				processAnimation(Constants.BACK, print_writers, "Change$DoubleTeamManhattan", "SHOW 0.0");
				break;
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
			case "Control_c": case "Control_v": case "Control_Shift_F8":
				processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard_3Col", "SHOW 0.0");
				break;
			case "p":
				processAnimation(Constants.BACK, print_writers, "Change$Standings", "SHOW 0.0");
				break;
			}
			
			processAnimation(Constants.BACK, print_writers, "Change$Header", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change$Footer", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change$LogoWipeChange", "SHOW 0.0");
			
			caption.captionWhichGfx = whatToProcess.split(",")[0];
			caption.this_fullFramesGfx.whichGFX = whatToProcess.split(",")[0];
			this.whichGraphicOnScreen = whatToProcess;
			break;
		case "Control_Shift_F11":
			processAnimation(Constants.FRONT, print_writers, "DRS_Change", "SHOW 0.0");
			break;
		case "Shift_I":
			processAnimation(Constants.FRONT, print_writers, "Change_GooglySub$Data", "SHOW 0.0");
			this.whichGraphicOnScreen = whatToProcess;
			break;

		case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k":
		case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": 
		case "Control_f":  case "Shift_E": case "Alt_F8": case "F8": case "F10": case "a": case "Alt_Shift_O": case "Alt_Shift_B": case "l":
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
			this.whichGraphicOnScreen = whatToProcess;
			break;
		
		case "Shift_F12":
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$IdentSubline", "SHOW 0.0");
			if(caption.this_lofInfobarGfx.infobar.getLast_middle_section() != null && !caption.this_lofInfobarGfx.infobar.getLast_middle_section().isEmpty()) {
				caption.this_lofInfobarGfx.setPositionOfScoreBug("Shift_F12,", 1, config, 0);
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "SHOW 0.0");
				caption.this_lofInfobarGfx.infobar.setLast_middle_section("");
			}
			break;
			
		case "Control_Alt_8":
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$MVP_LeaderBoard", "SHOW 0.0");
			caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
			break;
			
		case "Alt_c":
			if(whatToProcess.split(",")[3].equalsIgnoreCase("CHALLENGED_IDENT")) {
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOverStart", "SHOW 1.600");
			}else if(whatToProcess.split(",")[3].equalsIgnoreCase("CHALLENGED_RUNS")) {
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOver", "SHOW 1.600");
				
				if(caption.this_lofInfobarGfx.cr_balls > 6) {
					for(int i=1;i<=caption.this_lofInfobarGfx.cr_balls;i++) {
						processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + caption.whichSide + "$Ball" + i, "SHOW 0.500");
						processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + (3-caption.whichSide) + "$Ball" + i, "SHOW 0.0");
					}
				}else {
					for(int i=1;i<=6;i++) {
						processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + caption.whichSide + "$Ball" + i, "SHOW 0.500");
						processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + (3-caption.whichSide) + "$Ball" + i, "SHOW 0.0");
					}
				}
			}else {
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$ChallengeOver", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CahllengeCumulative", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$CahllengeCumulative", "SHOW 0.0");
			}
		
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$ChallengeOverStart", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$ChallengeOver", "SHOW 0.0");
			
			caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
			TimeUnit.MILLISECONDS.sleep(1200);
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			break;
			
		case "Control_F8":
			if(whatToProcess.split(",")[2].equalsIgnoreCase("TAPE_BALL_FULL")) {
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOver", "SHOW 1.600");
			}else if(whatToProcess.split(",")[2].equalsIgnoreCase("TAPE_BALL_SHORT")) {
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$TapeBallOverStatic", "SHOW 1.600");
			}
			
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$TapeBallOverStatic", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$TapeBallOver", "SHOW 0.0");
			
			caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
			TimeUnit.MILLISECONDS.sleep(1200);
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			break;
			
		case "Alt_/":
			switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
			case "SUPER_OVER_FULL":
				switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
				case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStatic", "SHOW 1.600");
					break;
				}
				break;
			case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER":
				switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
				case "SUPER_OVER_FULL":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$SuperOverStart", "SHOW 1.600");
					break;
				case "SUPER_OVER_SHORT":
					break;
				}
				
				break;
			}
			
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$SuperOverStatic", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$SuperOverStart", "SHOW 0.0");
			
			if(whatToProcess.split(",")[2].equalsIgnoreCase("SUPER_OVER_THIS_OVER")) {
				processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + caption.whichSide, "SHOW 0.500");
				processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + (3-caption.whichSide), "SHOW 0.0");
				
				if(caption.this_lofInfobarGfx.cr_balls > 6) {
					for(int i=1;i<=caption.this_lofInfobarGfx.cr_balls;i++) {
						processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + caption.whichSide + "$Ball" + i, "SHOW 0.500");
						processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + (3-caption.whichSide) + "$Ball" + i, "SHOW 0.0");
					}
				}else {
					for(int i=1;i<=6;i++) {
						processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + caption.whichSide + "$Ball" + i, "SHOW 0.500");
						processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + (3-caption.whichSide) + "$Ball" + i, "SHOW 0.0");
					}
				}
			}
			
			caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
			TimeUnit.MILLISECONDS.sleep(1200);
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			break;
		
		case "Alt_1": case "Control_5": case "Alt_5": case "Alt_9": case "Alt_0":
			
			if(!caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase(caption.this_lofInfobarGfx.infobar.getFull_section())) {
				switch(caption.this_lofInfobarGfx.infobar.getLast_full_section().toUpperCase()) {
				case CricketUtil.PROJECTED:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ProjectedScore", "SHOW 0.0");
					break;
				case CricketUtil.BOUNDARY:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Boundaries", "SHOW 0.0");
					break;
				case CricketUtil.EXTRAS:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Extras", "SHOW 0.0");
					break;
				case "LAST_WICKET":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastWicket", "SHOW 0.0");
					break;
				case "BALLS_SINCE_LAST_BOUNDARY":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$BallSince", "SHOW 0.0");
					break;
				case "THIS_OVER":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ThisOver", "SHOW 0.0");
					break;
				case "THIS_OVER_RUNS":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$CumullativeThisOverSmall", "SHOW 0.0");
					break;
				case "CRR": case "RRR": case "REVIEWS_REMAINING":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRates", "SHOW 0.0");
					break;
				case "LINE_UP":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Playing11", "SHOW 0.0");
					break;
				case "LAST_X_BALLS":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRateInnings1", "SHOW 0.0");
					break;
				case "LAST_X_BALLS_WITHOUT_CRR":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastX", "SHOW 0.0");
					break;
				case "COMPARE":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Comparison", "SHOW 0.0");
					break;
				case "EQUATION": case CricketUtil.RESULT:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Equation", "SHOW 0.0");
					break;
				case "COMMENTATORS": case "FREE_TEXT": case CricketUtil.TOSS:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$GenericText1Line", "SHOW 0.0");
					break;
				case "OVER_TIMELINE":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$TimeLine", "SHOW 0.0");
					break;
				case "TIMELINE":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$14BallTimeline", "SHOW 0.0");
					break;
				case "EQUATION_SHORT_SB":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ShortEquation", "SHOW 0.0");
					break;
				}
			}
			
			switch(caption.this_lofInfobarGfx.infobar.getFull_section().toUpperCase()) {
			case CricketUtil.PROJECTED:
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ProjectedScore", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ProjectedScore", "SHOW 0.0");
				break;
			case CricketUtil.BOUNDARY:
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Boundaries", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Boundaries", "SHOW 0.0");
				break;
			case CricketUtil.EXTRAS:
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Extras", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Extras", "SHOW 0.0");
				break;
			case "LAST_WICKET":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastWicket", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastWicket", "SHOW 0.0");
				break;
			case "BALLS_SINCE_LAST_BOUNDARY":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$BallSince", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$BallSince", "SHOW 0.0");
				break;
			case "THIS_OVER":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ThisOver", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ThisOver", "SHOW 0.0");
				break;
			case "THIS_OVER_RUNS":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$CumullativeThisOverSmall", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$CumullativeThisOverSmall", "SHOW 0.0");
				break;
			case "CRR": case "RRR": case "REVIEWS_REMAINING":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRates", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRates", "SHOW 0.0");
				break;
			case "LINE_UP":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Playing11", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Playing11", "SHOW 0.0");
				break;
			case "LAST_X_BALLS":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$RunRateInnings1", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRateInnings1", "SHOW 0.0");
				break;
			case "LAST_X_BALLS_WITHOUT_CRR":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$LastX", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastX", "SHOW 0.0");
				break;
			case "COMPARE":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Comparison", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Comparison", "SHOW 0.0");
				break;
			case "EQUATION": case CricketUtil.RESULT:
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$Equation", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Equation", "SHOW 0.0");
				break;	
			case "COMMENTATORS": case "FREE_TEXT": case CricketUtil.TOSS:
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$GenericText1Line", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$GenericText1Line", "SHOW 0.0");
				break;
			case "OVER_TIMELINE":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$TimeLine", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$TimeLine", "SHOW 0.0");
				break;
			case "TIMELINE":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$14BallTimeline", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$14BallTimeline", "SHOW 0.0");
				break;
			case "EQUATION_SHORT_SB":
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo$In_Out$ShortEquation", "SHOW 2.180");
				processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ShortEquation", "SHOW 0.0");
				break;
			}
			
			caption.this_lofInfobarGfx.infobar.setLast_full_section(caption.this_lofInfobarGfx.infobar.getFull_section());
			break;
		case "Alt_6": 
			TimeUnit.MILLISECONDS.sleep(1000);
			if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BATSMAN) && 
					infobar.getMiddle_section() != null && !infobar.getMiddle_section().isEmpty()) {
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage1_Change", "SHOW 0.0");
			}
			break;
		case "Alt_7":
			if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER) && 
					infobar.getRight_bottom() != null && !infobar.getRight_bottom().isEmpty()) {
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_Change", "SHOW 0.0");
			}
			break;
		case "Alt_8":
//			if(!whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
//				if(infobar.getRight_section()!= null && !infobar.getRight_section().isEmpty()) {
//					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$Bowl_All$Change", "SHOW 0.0");
//				}
//			}
			break;
		case "Alt_2": case "Control_6": case "Control_7": case "Control_8": case "Control_9": case "Control_0":
			
			if(!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase(caption.this_lofInfobarGfx.infobar.getMiddle_section())) {
				switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
				case "BATTINGCARD":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BattingCard", "SHOW 0.0");
					break;
				case "BOWLINGCARD":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BowlingCard", "SHOW 0.0");
					break;
				case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Profile", "SHOW 0.0");
					break;
				case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$OutStat", "SHOW 0.0");
					break;
				case "TARGET":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BigTarget", "SHOW 0.0");
					break;
				case "CURR_PARTNERSHIP":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$CurrentPartnership", "SHOW 0.0");
					break;
				case "SB_MATCH_PROMO":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$NextMatch", "SHOW 0.0");
					break;
				case "POINTS_TABLE":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Standings", "SHOW 0.0");
					break;
				case "TEAM_FORMGUIDE":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$FormGuide", "SHOW 0.0");
					break;
				case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES":
				case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE": case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$LeaderBoard", "SHOW 0.0");
					break;
				}
			}
			
			switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
			case "BATTINGCARD":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BattingCard", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BattingCard", "SHOW 0.0");
				break;
			case "BOWLINGCARD":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BowlingCard", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BowlingCard", "SHOW 0.0");
				break;
			case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$OutStat", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$OutStat", "SHOW 0.0");
				break;
			case "CURR_PARTNERSHIP":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CurrentPartnership", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$CurrentPartnership", "SHOW 0.0");
				break;
			case "TARGET":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BigTarget", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BigTarget", "SHOW 0.0");
				break;
			case "SB_MATCH_PROMO":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$NextMatch", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$NextMatch", "SHOW 0.0");
				break;
			case "POINTS_TABLE":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Standings", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Standings", "SHOW 0.0");
				break;
			case "TEAM_FORMGUIDE":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$FormGuide", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$FormGuide", "SHOW 0.0");
				break;
			case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES":
			case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY":
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$LeaderBoard", "SHOW 1.600");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$LeaderBoard", "SHOW 0.0");
				break;
			}
			
			caption.this_lofInfobarGfx.infobar.setLast_middle_section(caption.this_lofInfobarGfx.infobar.getMiddle_section());
			TimeUnit.MILLISECONDS.sleep(1200);
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			break;
		case "Alt_3": case "Alt_4":
			switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
			case "BATTINGCARD":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BattingCard", "SHOW 0.0");
				break;
			case "BOWLINGCARD":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BowlingCard", "SHOW 0.0");
				break;
			case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$OutStat", "SHOW 0.0");
				break;
			case "CURR_PARTNERSHIP":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$CurrentPartnership", "SHOW 0.0");
				break;
			case "TARGET":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BigTarget", "SHOW 0.0");
				break;
			case "POINTS_TABLE":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Standings", "SHOW 0.0");
				break;
			case "TEAM_FORMGUIDE":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$FormGuide", "SHOW 0.0");
				break;
			case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES":
			case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$LeaderBoard", "SHOW 0.0");
				break;
			}
			
			processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "SHOW 1.600");
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Profile", "SHOW 0.0");
			TimeUnit.MILLISECONDS.sleep(1200);
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
			switch (whatToProcess.split(",")[0]) {
			case "Alt_3":
				caption.this_lofInfobarGfx.infobar.setLast_middle_section("BAT_PROFILE_CAREER");
				break;
			case "Alt_4":
				caption.this_lofInfobarGfx.infobar.setLast_middle_section("BALL_PROFILE_CAREER");
				break;
			}
			break;
		}
		//this.whichGraphicOnScreen = whatToProcess;
		return CricketUtil.YES;
	}
	public String Lof_ISPL_ChangeOn(String whatToProcess, List<PrintWriter> print_writers,Configuration config) throws InterruptedException {		
		if(!whatToProcess.contains(",")) {
			return CricketUtil.NO;
		}
		switch(whatToProcess.split(",")[0]) {
		case "Alt_x":
			processAnimation("", print_writers, "Change_MVP$Performance", "START");
			break;
		case "Shift_I":
			processAnimation(Constants.FRONT, print_writers, "Change_GooglySub", "START");
			break;
		case "Shift_F8":
			IndexController.this_animation.lineUpCount = 0;
			processAnimation(Constants.BACK, print_writers, "Change_BigImageLineUp", "START");
			break;
		case "Shift_T": case "F1": case "Control_F10": case "F4": case "Shift_K": case "Control_F7": case "Shift_F10": case "Control_F11": 
		case "Shift_F11": case "F2": case "Alt_F11":  case "z": case "x": case "c": case "v": case "p": case "Control_z": case "Control_x": 
		case "Control_Shift_Z": case "Control_Shift_Y": case "Alt_F5": case "Control_Shift_F7": case "Control_c": case "Control_v":
		case "Control_Shift_F8":
			processAnimation(Constants.BACK, print_writers, "Change$Header", "START");
			processAnimation(Constants.BACK, print_writers, "Change$SubHeader", "START");
			processAnimation(Constants.BACK, print_writers, "Change$Footer", "START");
			processAnimation(Constants.BACK, print_writers, "Change$LogoWipeChange", "START");
			switch(whichGraphicOnScreen.split(",")[0]) {
			case "F1":
				if(whichScorecard.equalsIgnoreCase("NORMAL")) {
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle2", "START");
				}else if(whichScorecard.equalsIgnoreCase("SPLIT")) {
					processAnimation(Constants.BACK, print_writers, "Change$SplitBatBall_Card", "START");
				}else if(whichScorecard.equalsIgnoreCase("TRADITIONAL")) {
					processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Normal", "START");
				}else {
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "START");
				}
				break;
			case "F2":
				if(whichBowlingCard.equalsIgnoreCase("TRADITIONAL")) {
					processAnimation(Constants.BACK, print_writers, "Change$BowlingCard_Normal", "START");
				}else {
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "START");
				}
				break;
			case "Shift_F10":
				processAnimation(Constants.BACK, print_writers, "Change$Worm$Change_Out", "START");
				break;
			case "Shift_T":
				processAnimation(Constants.BACK, print_writers, "Change$PlayingXI$Change_Out", "START");
				break;
			case "Control_Shift_F7":
				processAnimation(Constants.BACK, print_writers, "Change$LineUp_Single$Change_Out", "START");
				break;
			case "Control_F10":
				processAnimation(Constants.BACK, print_writers, "Change$Manhattan$Change_Out", "START");
				break;
			case "F4":
				processAnimation(Constants.BACK, print_writers, "Change$Partnership_List$Change_Out", "START");
				break;
			case "Shift_K":
				processAnimation(Constants.BACK, print_writers, "Change$Partnership$Change_Out", "START");
				break;
			case "Control_F7":
				processAnimation(Constants.BACK, print_writers, "Change$Both_Team$Change_Out", "START");
				break;
			case "Control_F11": case "Shift_F11":
				processAnimation(Constants.BACK, print_writers, "Change$Summary$Change_Out", "START");
				break;
			case "Alt_F11":
				processAnimation(Constants.BACK, print_writers, "Change$Doublemanhattan$Change_Out", "START");
				break;
			case "Alt_F5":
				processAnimation(Constants.BACK, print_writers, "Change$DoubleTeamManhattan$Change_Out", "START");
				break;
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
			case "Control_c": case "Control_v": case "Control_Shift_F8":
				processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard_3Col$Change_Out", "START");
				break;
			case "p":
				processAnimation(Constants.BACK, print_writers, "Change$Standings$Change_Out", "START");
				break;
			}
			TimeUnit.MILLISECONDS.sleep(500);
			switch(whatToProcess.split(",")[0]) {
			case "F1":
				if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("NORMAL")) {
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle2", "START");
				}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("SPLIT")) {
					processAnimation(Constants.BACK, print_writers, "Change$SplitBatBall_Card", "START");
				}else if(caption.this_fullFramesGfx.WhichScoreCard.equalsIgnoreCase("TRADITIONAL")) {
					processAnimation(Constants.BACK, print_writers, "Change$BattingCard_Normal", "START");
				}else {
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "START");
				}
				break;
			case "F2":
				if(caption.this_fullFramesGfx.WhichBallCard.equalsIgnoreCase("TRADITIONAL")) {
					processAnimation(Constants.BACK, print_writers, "Change$BowlingCard_Normal", "START");
				}else {
					processAnimation(Constants.BACK, print_writers, "Change$BatStyle3", "START");
				}
				break;	
			case "F4":
				processAnimation(Constants.BACK, print_writers, "Change$Partnership_List$Change_In", "START");
				break;
			case "Shift_F10":
				processAnimation(Constants.BACK, print_writers, "Change$Worm$Change_In", "START");
				break;	
			case "Shift_K":
				processAnimation(Constants.BACK, print_writers, "Change$Partnership$Change_In", "START");
				break;
			case "Shift_T":
				processAnimation(Constants.BACK, print_writers, "Change$PlayingXI$Change_In", "START");
				break;
			case "Control_Shift_F7":
				processAnimation(Constants.BACK, print_writers, "Change$LineUp_Single$Change_In", "START");
				break;
			case "Control_F10":
				processAnimation(Constants.BACK, print_writers, "Change$Manhattan$Change_In", "START");
				break;
			case "Control_F7":
				processAnimation(Constants.BACK, print_writers, "Change$Both_Team$Change_In", "START");
				break;
			case "Control_F11": case "Shift_F11":
				processAnimation(Constants.BACK, print_writers, "Change$Summary$Change_In", "START");
				break;
			case "Alt_F11":
				processAnimation(Constants.BACK, print_writers, "Change$Doublemanhattan$Change_In", "START");
				break;
			case "Alt_F5":
				processAnimation(Constants.BACK, print_writers, "Change$DoubleTeamManhattan$Change_In", "START");
				break;
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
			case "Control_c": case "Control_v": case "Control_Shift_F8":
				processAnimation(Constants.BACK, print_writers, "Change$LeaderBoard_3Col$Change_In", "START");
				break;
			case "p":
				processAnimation(Constants.BACK, print_writers, "Change$Standings$Change_In", "START");
				break;
			}
		break;
		case "Control_Shift_F11":
			processAnimation(Constants.FRONT, print_writers, "DRS_Change", "START");
			break;
		
		case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a": 
		case "Control_F3": case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
		case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": 
		case "F8": case "F10": case "a": case "Alt_Shift_O": case "Alt_Shift_B": case "l":
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "START");
			break;
		
		case "Shift_F12":
			if(caption.this_lofInfobarGfx.infobar.getLast_middle_section() != null && !caption.this_lofInfobarGfx.infobar.getLast_middle_section().isEmpty()) {
				caption.this_lofInfobarGfx.setPositionOfScoreBug("Shift_F12,", 2, config, 0);
				processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "CONTINUE");
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
			}
			
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$IdentSubline", "START");
			break;
			
		case "Control_Alt_8":
			if(!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase(whatToProcess.split(",")[2])) {
				if (caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("MVP_LB_SINGLE_PLAYER") 
						&& whatToProcess.split(",")[2].equalsIgnoreCase("MVP_LB_ALL_PLAYER") 
						|| caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("MVP_LB_ALL_PLAYER") 
						&& whatToProcess.split(",")[2].equalsIgnoreCase("MVP_LB_SINGLE_PLAYER")) {
				    processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$MVP_LeaderBoard$Data", "START");
				}else {
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$MVP_LeaderBoard", "START");
				}
			}else {
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$MVP_LeaderBoard$Data", "START");
			}
			break;
			
		case "Alt_c":
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$ChallengeOverStart", "START");
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$ChallengeOver", "START");
			
			if(whatToProcess.split(",")[3].equalsIgnoreCase("CHALLENGED_RUNS")) {
				processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + caption.whichSide + "$Offset", "START");
				processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + (3-caption.whichSide) + "$Offset", "START");
				if(caption.this_lofInfobarGfx.cr_balls > 6) {
					for(int i=1;i<=caption.this_lofInfobarGfx.cr_balls;i++) {
						processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + caption.whichSide + "$Ball" + i, "START");
					}
				}else {
					for(int i=1;i<=6;i++) {
						processAnimation(Constants.FRONT, print_writers, "LeftSideBalls$Side" + caption.whichSide + "$Ball" + i, "START");
					}
				}
			}if(whatToProcess.split(",")[3].equalsIgnoreCase("CHALLENGED_RUNS_CUMM")) {
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$CahllengeCumulative", "START");
			}
			break;
			
		case "Control_F8":
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$TapeBallOverStatic", "START");
			processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$TapeBallOver", "START");
			
			if(whatToProcess.split(",")[2].equalsIgnoreCase("TAPE_BALL_SHORT")) {
				if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == false) {
					processAnimation(Constants.FRONT, print_writers, "Target", "START");
					this.infobar.setTarget_pushed(true);
				}
			}
			break;
			
		case "Alt_/":
			caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
			processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
			
			switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
			case "SUPER_OVER_FULL":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$SuperOverStart", "START");
				switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
				case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$SuperOverStatic", "START");
					break;
				}
				break;
			case "SUPER_OVER_SHORT": case "SUPER_OVER_THIS_OVER":
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$SuperOverStatic", "START");
				switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
				case "SUPER_OVER_FULL":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "CONTINUE");
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$SuperOverStart", "START");
					break;
				case "SUPER_OVER_SHORT":
					processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + caption.whichSide, "CONTINUE REVERSE");
					break;
				}
				break;
			}
			
			if(whatToProcess.split(",")[2].equalsIgnoreCase("SUPER_OVER_THIS_OVER")) {
				processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + caption.whichSide + "$Offset", "START");
				processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + (3-caption.whichSide) + "$Offset", "START");
				if(caption.this_lofInfobarGfx.cr_balls > 6) {
					for(int i=1;i<=caption.this_lofInfobarGfx.cr_balls;i++) {
						processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + caption.whichSide + "$Ball" + i, "START");
					}
				}else {
					for(int i=1;i<=6;i++) {
						processAnimation(Constants.FRONT, print_writers, "SuperOverBalls$Side" + caption.whichSide + "$Ball" + i, "START");
					}
				}
			}
			break;
			
		case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": case "Alt_9": case "Alt_0":
		case "Control_5": case "Control_6": case "Control_7": case "Control_8": case "Control_9": case "Control_0":
			switch(whatToProcess.split(",")[0]) {
			case "Alt_1": case "Control_5": case "Alt_5": case "Alt_9": case "Alt_0":
				switch(caption.this_lofInfobarGfx.infobar.getLast_full_section().toUpperCase()) {
				case CricketUtil.PROJECTED:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ProjectedScore", "START");
					break;
				case CricketUtil.BOUNDARY:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Boundaries", "START");
					break;
				case CricketUtil.EXTRAS:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Extras", "START");
					break;
				case "LAST_WICKET":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastWicket", "START");
					break;
				case "BALLS_SINCE_LAST_BOUNDARY":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$BallSince", "START");
					break;
				case "THIS_OVER":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ThisOver", "START");
					break;
				case "THIS_OVER_RUNS":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$CumullativeThisOverSmall", "START");
					break;
				case "CRR": case "RRR": case "REVIEWS_REMAINING":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRates", "START");
					break;
				case "LINE_UP":
					processAnimation(Constants.FRONT, print_writers, "OutFor11", "CONTINUE REVERSE");
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Playing11", "START");
					break;
				case "LAST_X_BALLS":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRateInnings1", "START");
					break;
				case "LAST_X_BALLS_WITHOUT_CRR":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastX", "START");
					break;
				case "COMPARE":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Comparison", "START");
					break;
				case "EQUATION": case CricketUtil.RESULT:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Equation", "START");
					break;
				case "COMMENTATORS": case "FREE_TEXT": case CricketUtil.TOSS:
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$GenericText1Line", "START");
					break;
				case "OVER_TIMELINE":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$TimeLine", "START");
					break;
				case "TIMELINE":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$14BallTimeline", "START");
					break;
				case "EQUATION_SHORT_SB":
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ShortEquation", "START");
					break;
				}
				
				if(!caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase(caption.this_lofInfobarGfx.infobar.getFull_section())) {
					switch(caption.this_lofInfobarGfx.infobar.getFull_section().toUpperCase()) {
					case CricketUtil.PROJECTED:
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ProjectedScore", "START");
						break;
					case CricketUtil.BOUNDARY:
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Boundaries", "START");
						break;
					case CricketUtil.EXTRAS:
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Extras", "START");
						break;
					case "LAST_WICKET":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastWicket", "START");
						break;
					case "BALLS_SINCE_LAST_BOUNDARY":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$BallSince", "START");
						break;
					case "THIS_OVER":
						processAnimation(Constants.FRONT, print_writers, "BottomBalls", "START");
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ThisOver", "START");
						break;
					case "THIS_OVER_RUNS":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$CumullativeThisOverSmall", "START");
						break;
					case "CRR": case "RRR": case "REVIEWS_REMAINING":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRates", "START");
						break;
					case "LINE_UP":
						processAnimation(Constants.FRONT, print_writers, "OutFor11", "START");
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Playing11", "START");
						break;
					case "LAST_X_BALLS":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$RunRateInnings1", "START");
						break;
					case "LAST_X_BALLS_WITHOUT_CRR":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$LastX", "START");
						break;
					case "COMPARE":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Comparison", "START");
						break;
					case "EQUATION": case CricketUtil.RESULT:
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$Equation", "START");
						break;
					case "COMMENTATORS": case "FREE_TEXT": case CricketUtil.TOSS:
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$GenericText1Line", "START");
						break;
					case "OVER_TIMELINE":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$TimeLine", "START");
						break;
					case "TIMELINE":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$14BallTimeline", "START");
						break;
					case "EQUATION_SHORT_SB":
						processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo$ShortEquation", "START");
						break;
					}
				}
				break;
			case "Alt_3": case "Alt_4":
				switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
				case "BATTINGCARD":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BattingCard", "START");
					break;
				case "BOWLINGCARD":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BowlingCard", "START");
					break;
				case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$OutStat", "START");
					break;
				case "TARGET":
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BigTarget", "START");
					break;
				case "CURR_PARTNERSHIP":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$CurrentPartnership", "START");
					break;
				case "POINTS_TABLE":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Standings", "START");
					break;
				case "TEAM_FORMGUIDE":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$FormGuide", "START");
					break;
				case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": 
				case "LB_BEST_FIGURE": case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$LeaderBoard", "START");
					break;
				}
				
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Profile", "START");
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
				break;
			case "Alt_2": case "Control_6": case "Control_7": case "Control_8": case "Control_9": case "Alt_F1": case "Alt_F2": case "Control_0":
				
				if(!caption.this_lofInfobarGfx.infobar.getMiddle_section().equalsIgnoreCase(CricketUtil.BATSMAN)) {
					caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
					
					switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
					case "BATTINGCARD": case "BAT_GRIFF":
						processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BattingCard", "START");
						break;
					case "BOWLINGCARD": case "BALL_GRIFF":
						processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BowlingCard", "START");
						break;
					case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER":
						processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Profile", "START");
						break;
					case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
						processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$OutStat", "START");
						break;
					case "TARGET":
						processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BigTarget", "START");
						break;
					case "CURR_PARTNERSHIP": case "SB_MATCH_PROMO": case "POINTS_TABLE": case "TEAM_FORMGUIDE": 
					case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": 
					case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
						
						switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
						case "BATTINGCARD": case "BOWLINGCARD": case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER":
						case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH": case "TARGET":
							processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "CONTINUE");
							break;
						}
						
						switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
						case "CURR_PARTNERSHIP":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$CurrentPartnership", "START");
							break;
						case "SB_MATCH_PROMO":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$NextMatch", "START");
							break;
						case "POINTS_TABLE":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Standings", "START");
							break;
						case "TEAM_FORMGUIDE":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$FormGuide", "START");
							break;
						case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": 
						case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$LeaderBoard", "START");
							break;
						}
						break;
					}
				}
				
				if(caption.this_lofInfobarGfx.infobar.getMiddle_section().equalsIgnoreCase(CricketUtil.BATSMAN)) {
					
					caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 2, config, 0);
					
					switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section().toUpperCase()) {
					case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "CONTINUE");
						break;
					case "BATTINGCARD": case "BAT_GRIFF":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BattingCard", "CONTINUE");
						break;
					case "BOWLINGCARD": case "BALL_GRIFF":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BowlingCard", "CONTINUE");
						break;	
					case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$OutStat", "CONTINUE");
						break;
					case "SB_MATCH_PROMO":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$NextMatch", "CONTINUE");
						break;
					case "CURR_PARTNERSHIP":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CurrentPartnership", "CONTINUE");
						break;
					case "TARGET":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BigTarget", "CONTINUE");
						break;
					case "POINTS_TABLE":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Standings", "CONTINUE");
						break;
					case "TEAM_FORMGUIDE":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$FormGuide", "CONTINUE");
						break;
					case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": 
					case "LB_BEST_FIGURE": case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$LeaderBoard", "CONTINUE");
						break;
					
					}
					
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
					switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
					case "CURR_PARTNERSHIP": case "SB_MATCH_PROMO": case "POINTS_TABLE": case "TEAM_FORMGUIDE": 
					case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": 
					case "LB_BEST_FIGURE": case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
						break;
					default:
						processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
						break;
					}
					
					if(caption.this_lofInfobarGfx.infobar.isTarget_on_screen() == true && this.infobar.isTarget_pushed() == false) {
						processAnimation(Constants.FRONT, print_writers, "Target", "START");
						this.infobar.setTarget_pushed(true);
					}
					
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo", "SHOW 0.0");
					caption.this_lofInfobarGfx.infobar.setLast_middle_section(CricketUtil.BATSMAN);
					
					TimeUnit.MILLISECONDS.sleep(2000);
					caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
					
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BattingCard", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BowlingCard", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$OutStat", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CurrentPartnership", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BigTarget", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$NextMatch", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Standings", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$FormGuide", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$LeaderBoard", "SHOW 0.0");
				}
				else if(caption.this_lofInfobarGfx.infobar.getMiddle_section().equalsIgnoreCase("IDENT")) {
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
					TimeUnit.MILLISECONDS.sleep(2000);
					caption.this_lofInfobarGfx.setPositionOfScoreBug(caption.this_lofInfobarGfx.infobar.getMiddle_section()+",", 1, config, 0);
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
					
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Profile", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BattingCard", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BowlingCard", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$OutStat", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$CurrentPartnership", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$BigTarget", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$NextMatch", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Standings", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$FormGuide", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$LeaderBoard", "SHOW 0.0");
				}else {
					if(!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase(caption.this_lofInfobarGfx.infobar.getMiddle_section())) {
						switch(caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
						case "BATTINGCARD":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BattingCard", "START");
							break;
						case "BOWLINGCARD":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BowlingCard", "START");
							break;
						case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$OutStat", "START");
							break;
						case "CURR_PARTNERSHIP":
							processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$CurrentPartnership", "START");
							break;
						case "TARGET":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$BigTarget", "START");
							break;
						case "SB_MATCH_PROMO":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$NextMatch", "START");
							break;
						case "POINTS_TABLE":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$Standings", "START");
							break;
						case "TEAM_FORMGUIDE":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$FormGuide", "START");
							break;
						case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": 
						case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
							processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo$LeaderBoard", "START");
							break;
						}
						processAnimation(Constants.FRONT, print_writers, "ExpandForData", "START");
					}
				}
				break;
			case "Alt_7":
				if(whatToProcess.split(",")[2].equalsIgnoreCase(CricketUtil.BOWLER)) {
					if(infobar.getFull_section() != null && !infobar.getFull_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Out", "START");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setFull_section("");
						infobar.setRight_bottom("");
					}else {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_Out", "START");
						TimeUnit.MILLISECONDS.sleep(500);
						infobar.setFull_section("");
						infobar.setRight_bottom("");
					}
				}else {
					if(infobar.getFull_section()!= null && !infobar.getFull_section().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage3_Out", "START");
						infobar.setFull_section("");
					}
					
					if(infobar.getRight_bottom() != null && !infobar.getRight_bottom().trim().isEmpty()) {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_Change", "START");
					}else {
						processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Stage2_In", "START");
					}
					infobar.setRight_bottom(whatToProcess.split(",")[2]);
				}
				
				break;
			case "Alt_8":
				if(whatToProcess.split(",")[2].equalsIgnoreCase("TARGET")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Target_Out", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$Target_In", "START");
//					infobar.setRight_section("");
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("TAPED_BALL")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TapedBall_In", "START");
//					infobar.setRight_section("");
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("EQUATION")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$ToWin$In", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$ToWin$In", "START");
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("TIMELINE")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TimeLine", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$TimeLine", "START");
				}else if(whatToProcess.split(",")[2].equalsIgnoreCase("SUPER_OVER")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TOPRIGHT_FREETEXT_In", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Main$TOPRIGHT_FREETEXT_In", "START");
				}
				infobar.setRight_section(whatToProcess.split(",")[2]);
				infobar.setLast_right_section(whatToProcess.split(",")[2]);
				break;
			}
			break;
		case "y": case "g": case "Shift_F": case "Control_k": case "Shift_O": case "Shift_F4": case "h": case "k": case "Shift_C": case "Control_Shift_F3":
		case "Control_Shift_R":	
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange$Logo", "START");
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange$Text", "START");
			break;
		}
		return CricketUtil.YES;	
	}
	
	public String ResetAnimation(String whatToProcess, List<PrintWriter> print_writers, Configuration config) throws InterruptedException
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.VIDARBHA:

			processAnimation(Constants.BACK, print_writers, "Anim_MatchId", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_FullFrames", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Leader3_Highlight", "SHOW 0.0");

			processAnimation(Constants.BACK, print_writers, "Anim_Target", "SHOW 0.0");
			
			processAnimation("", print_writers, "Plotter", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Change", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "ConcussExtend_Y", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Header_Shrink", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Profile_Highlight", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Base_Gradient", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Target", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "TargetLoop", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "In_At", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "In_At_Loop", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Milestone", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "MilestoneLoop", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Manhattan_Comparison", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Anim_Squad", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "SquadFlare_Loop", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Anim_SquadDataChange", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "Anim_POTT", "SHOW 0.0");
//			processAnimation(Constants.BACK, print_writers, "POTT_Loop", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
			
			
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Image_LT", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Image_LtChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_Impact", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Change", "SHOW 0.0");
//			processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_PlayingXIChange", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_MiniPoints", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "PopUps", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Image_LT", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Image_LtChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs", "SHOW 0.0");
//			processAnimation(Constants.FRONT, print_writers, "Anim_MiniPoints$In_Out", "Show 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_MiniPoints", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Mini", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Sponsor_LT", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Sponsor_Pop", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Sponsor_Bug", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Sponsor", "SHOW 0.0");
			
			
			if(whatToProcess.contains("CLEAR-ALL")) {
//				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Small$In", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$Reset", "START");
				processAnimation(Constants.FRONT, print_writers, "Anim_InfoBar$IdentInfo", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anin_LeftInfo", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "LowLight", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
				this.infobar.setInfobar_on_screen(false);
				this.infobar.setInfobar_status("");
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
			}
			this.whichGraphicOnScreen = "";
			this.specialBugOnScreen = "";
			break;	
		case Constants.T20_MUMBAI:
			if(whatToProcess.contains("CLEAR-ALL")) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_LT_Ident", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Lt_BattingCard", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_LT_HowOut", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_LowerThird", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "ChangeLowerThird", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Pop_Up", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Change_PopUp", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_TossBug", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_LT_NameSuper", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Change_LT_NameSuper", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "MoveForNameSuper", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Loop", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Impact", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_TossBug", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "MoveForNameSuper", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_Bugs", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_TargetBug", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_BoundaryCounter", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_SixDistance", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Minis", "SHOW 0.0");
				
				processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "AUDIO", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "anim_Ident", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "anim_Profile", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "anim_FullFrames", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "MoveForSplitCard", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change_Fullframes", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "anim_Target", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "anim_Image_Lineup", "SHOW 0.0");
				processAnimation(Constants.BACK, print_writers, "Change_Image_Lineup", "SHOW 0.0");
				
				
				this.infobar.setInfobar_on_screen(false);
				this.infobar.setFull_section(null);
				this.infobar.setRight_full_section(null);
				this.infobar.setRight_section(null);
				ExtraInfoOnScreen = false;
				MiddleSectionInfoOnScreen = false;
				bigScoreBug_On_Screen = false;
			}else if(whatToProcess.contains("CLEAR-INFOBAR_DATA")) {
				caption.this_infobarGfx.infobar.setInfobar_on_screen(false);
				caption.this_infobarGfx.infobar.setFull_section(null);
				caption.this_infobarGfx.infobar.setRight_full_section(null);
				caption.this_infobarGfx.infobar.setRight_section(null);
				caption.this_infobarGfx.infobar.setMiddle_section(null);
				caption.this_infobarGfx.infobar.setLast_right_section(null);
				caption.this_infobarGfx.infobar.setRight_bottom(null);
				caption.this_infobarGfx.infobar.setLast_right_full_section(null);
				caption.this_infobarGfx.infobar.setLast_full_section(null);
			}
			this.whichGraphicOnScreen = "";
			break;
		case Constants.BENGAL_T20:
			processAnimation(Constants.BACK, print_writers, "Anim_FullFrames", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_Ident", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_DoubleIdent", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "anim_Profile", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_Target", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_Profile", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_Highlights", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_Teams", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_Lineup_Image_Big", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Popup", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Change_Popup", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Bug_1Line", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Bug_2Line", "SHOW 0.0");
			processAnimation(Constants.MIDDLE, print_writers, "Plotter", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Counter", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Counter$In_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Extra_PopUps", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "CrackerLoop", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Change", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Hundredths", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Tenths", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Units", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "anim_Fullframes$In_Out$Main", "SHOW 0.0");
			
			if(whatToProcess.contains("CLEAR-ALL")) {
				processAnimation(Constants.FRONT, print_writers, "anim_Infobar", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Ident", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Projected_LT", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Substitute", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_BatsmanScore_LT", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_BowlerFigure_LT", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "anim_Toss", "SHOW 0.0");
//				processAnimation(Constants.FRONT, print_writers, "anim_LtChange", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Minis", "SHOW 0.0");
				this.infobar.setInfobar_on_screen(false);
				this.infobar.setInfobar_status("");
			}
			this.whichGraphicOnScreen = "";
			this.specialBugOnScreen = "";
			break;
		case Constants.ICC_U19_2023: 

			processAnimation(Constants.BACK, print_writers, "Anim_FullFrames", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "ConcussExtend_Y", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Header_Shrink", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Profile_Highlight", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Base_Gradient", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Target", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "TargetLoop", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "In_At", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "In_At_Loop", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Milestone", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "MilestoneLoop", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Manhattan_Comparison", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_Squad", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "SquadFlare_Loop", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_SquadDataChange", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_POTT", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "POTT_Loop", "SHOW 0.0");

			processAnimation(Constants.FRONT, print_writers, "anim_Lower_Third", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_Boundary_LT", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Boundary_LTChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "anim_POTT", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_NameSupers", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_NameSuperChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Center_Bug", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Mini", "SHOW 0.0");
//			processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "SHOW 0.0");
			
			if(whatToProcess.contains("CLEAR-ALL-WITH-INFOBAR")) {
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar", "SHOW 0.0");
				this.infobar.setInfobar_on_screen(false);
				this.infobar.setInfobar_status("");
			}
			this.whichGraphicOnScreen = "";
			this.specialBugOnScreen = "";
			break;
		case Constants.NPL: case Constants.MPL: case Constants.APL:

			processAnimation(Constants.MIDDLE, print_writers, "Plotter", "SHOW 0.0");
			
			processAnimation(Constants.BACK, print_writers, "Anim_FullFrames", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_Ident", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "BG_Scale", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Profile_Highlight", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Loop", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "LeaderBoardHighlight", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_Target", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Anim_Milestone", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Bug_In", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Bug_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Bug_Change", "SHOW 0.0");
			
			
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs$Essentials", "SHOW 0.0");
//			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "PopUps", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "PopUps$InOut$Out", "SHOW 3.700");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_Mini", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Lower_Third$In_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Lower_Third$Change$Change_Out", "SHOW 1.020");
			
			processAnimation(Constants.FRONT, print_writers, "LT_Comparison$In_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_Comparison$Change$Change_Out", "SHOW 50.0");
			
			processAnimation(Constants.FRONT, print_writers, "LT_MatchID$In_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_MatchID$Change$Change_Out", "SHOW 50.0");
			
			processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI$In_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI$Change$Change_Out", "SHOW 0.820");
			
			processAnimation(Constants.FRONT, print_writers, "LT_PlayingXII$In_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_PlayingXII$Change$Change_Out", "SHOW 0.820");
			
			processAnimation(Constants.FRONT, print_writers, "LT_NextToBat$In_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_NextToBat$Change$Change_Out", "SHOW 0.700");
			
			processAnimation(Constants.FRONT, print_writers, "LT_Manhattan$In_Out", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "LT_Weather", "SHOW 0.0");
			if(whatToProcess.contains("CLEAR-ALL")) {
				
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "SHOW 0.0");
				
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "SHOW 0.0");
				this.infobar.setInfobar_on_screen(false);
				this.infobar.setRight_bottom_play(false);
				this.infobar.setTarget_on_screen(false);
				this.infobar.setRight_section_play(false);
				this.infobar.setInfobar_status("");
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				infobar.setLast_right_section("");
				infobar.setLast_right_bottom("");
			}
			this.whichGraphicOnScreen = "";
			this.specialBugOnScreen = "";
			if(caption != null) {
				caption.captionWhichGfx = "";
				caption.this_lowerThirdGfx.chnageOn = false;
			}
			break;
		case Constants.LEGENDS:
			processAnimation(Constants.FRONT, print_writers, "Extra_PopUps", "SHOW 0.0");
			processAnimation(Constants.MIDDLE, print_writers, "Plotter", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "anim_FullFrame", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "anim_Change", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "anim_Team_BigImage", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "anim_TeamBigImageChange", "SHOW 0.0");
			
			processAnimation(Constants.BACK, print_writers, "Sponsor", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Bug_Right_Change", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_Mini", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_MiniChange", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "LT_Comparison", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_Manhattan", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "LT_PlayingXI", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "PopUps$Change", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "PopUps$InOut", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Mini", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Bug_In", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Bug_Out", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Bug_Change", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Bug_Out2", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "anim_LowerThird", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_LT_Change", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "anim_LT_Ident", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "anim_LT_Ident_Change", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "anim_ImpactLt", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Sponsor", "SHOW 0.0");
			
			if(whatToProcess.contains("CLEAR-ALL")) {
				processAnimation(Constants.FRONT, print_writers, "Anim_Infobar", "SHOW 0.0");
				processAnimation(Constants.FRONT, print_writers, "Anim_Ident", "SHOW 0.0");
				
				this.infobar.setInfobar_on_screen(false);
				this.infobar.setRight_bottom_play(false);
				this.infobar.setTarget_on_screen(false);
				this.infobar.setRight_section_play(false);
				this.infobar.setInfobar_status("");
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_bottom("");
				infobar.setRight_section("");
				infobar.setLast_right_section("");
				infobar.setLast_right_bottom("");
			}
			this.whichGraphicOnScreen = "";
			this.specialBugOnScreen = "";
			if(caption != null) {
				caption.captionWhichGfx = "";
			}
			 break;
		case Constants.ISPL:
			
			processAnimation("", print_writers, "anim_MVP", "SHOW 0.0");

			processAnimation(Constants.BACK, print_writers, "anim_Fullframes", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Start_End", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "anim_MatchId", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "anim_BigImageLineUp", "SHOW 0.0");
			processAnimation(Constants.BACK, print_writers, "Change_BigImageLineUp", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_Lower_Third", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_LtChange", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_DRS_Bug", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "DRS_Change", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_ChallengeBug", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_Bugs", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_BugsChange", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_Toss_Bug", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_SixDistanceBug", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_GooglySub", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Change_GooglySub", "SHOW 0.0");
			
			processAnimation(Constants.FRONT, print_writers, "Anim_LT_Manhattan", "SHOW 0.0");
			processAnimation(Constants.FRONT, print_writers, "Anim_ROF_Manhattan", "SHOW 0.0");
			
			if(whatToProcess.contains("CLEAR-ALL")) {
				if(config.getWhichInfobar().equalsIgnoreCase("TRADITIONAL_INFOBAR")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_Infobar$In_Out", "SHOW 0.0");
					
					processAnimation(Constants.FRONT, print_writers, "Anim_Ident", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_Ident$In_Out", "SHOW 0.0");
				}
				else if(config.getWhichInfobar().equalsIgnoreCase("LOF_INFOBAR")) {
					processAnimation(Constants.FRONT, print_writers, "Anim_BottomInfo", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "OutFor11", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Challenge_CumRuns", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "BottomBalls", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Change_BottomInfo", "SHOW 0.0");
					
					processAnimation(Constants.FRONT, print_writers, "Target", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "BoundaryCounter", "SHOW 0.0");
					
					processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "ExpandForData", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Change_LeftInfo", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "LeftWipe", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "LowLight", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "ProfileDataChange", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "LeftSideBalls", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "LeftEventAnimation", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "SuperOverAnimation", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "SuperOverBalls", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Speed", "SHOW 0.0");
					
					processAnimation(Constants.FRONT, print_writers, "EventAnimation", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "FreeHit", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Shrink_FF", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Shrink_Lt", "SHOW 0.0");
					processAnimation(Constants.FRONT, print_writers, "Anim_LtIdent", "SHOW 0.0");
					
				}
				
				this.infobar.setInfobar_on_screen(false);
				this.infobar.setInfobar_status("");
				this.infobar.setTarget_pushed(false);
				
				infobar.setMiddle_section("");
				infobar.setFull_section("");
				infobar.setRight_section("");
				
				if(caption != null) {
					caption.this_lofInfobarGfx.infobar.setMiddle_section("");
					caption.this_lofInfobarGfx.infobar.setLast_middle_section("");
					caption.this_lofInfobarGfx.infobar.setLast_full_section("");
					caption.this_lofInfobarGfx.infobar.setFull_section("");
					caption.this_lofInfobarGfx.infobar.setRight_bottom("");
					caption.this_lofInfobarGfx.infobar.setRight_section("");
					caption.this_lofInfobarGfx.infobar.setInfobar_on_screen(false);
					caption.this_lofInfobarGfx.infobar.setTarget_on_screen(false);
					caption.this_lofInfobarGfx.infobar.setPowerplay_on_screen(false);
					caption.this_bugsAndMiniGfx.isVisited = false;
				}
			}
			this.whichGraphicOnScreen = "";
			this.specialBugOnScreen = "";
			break;	
		}
		return CricketUtil.YES;
	}
	public void processAnimation(String whichLayer, List<PrintWriter> print_writers,
		String animationDirectorName, String animationCommand)
	{
		if(!whichLayer.isEmpty()) {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*" + whichLayer + "_LAYER*STAGE*DIRECTOR*"
				+ animationDirectorName + " " + animationCommand +"\0", print_writers);
		} else {
			CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*STAGE*DIRECTOR*"
				+ animationDirectorName + " " + animationCommand +"\0", print_writers);
		}
	}
	
	public void processQuidichCommands(String whatToProcess, List<PrintWriter> print_writers, Configuration config) throws InterruptedException
	{
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.ICC_U19_2023:
			switch(whatToProcess) {
			case "5": // Reset
				print_writers.get(print_writers.size()-1).printf("%s","F4");
				break;
			case "6": // Stand-By
				print_writers.get(print_writers.size()-1).printf("%s","F6");
				break;
			case "7": //Animate-In
				print_writers.get(print_writers.size()-1).printf("%s","F6");
				TimeUnit.MILLISECONDS.sleep(100);
				print_writers.get(print_writers.size()-1).printf("%s","F7");
				break;
			case "8": //Animate-Out
				print_writers.get(print_writers.size()-1).printf("%s","F9");
				TimeUnit.MILLISECONDS.sleep(1000);
				print_writers.get(print_writers.size()-1).printf("%s","F4");
				break;
			case "9": //Load	
				print_writers.get(print_writers.size()-1).printf("%s","LOAD");
				break;
			}
			break;
		}
	}
	
	public void setVariousAnimationsKeys(String whatToProcess, List<PrintWriter> print_writers, Configuration config) 
	{
		switch (config.getBroadcaster()) {
		case Constants.ICC_U19_2023: case Constants.ISPL:
			
			float MoveForExtraData, BasePositionY = 0f, obj_BiggerBase = 0f, obj__Mask_6_ = 0f, PositionY = 0f, Sponsor = 0f;
			
			switch(caption.this_fullFramesGfx.numberOfRows) {
			case 10:
				MoveForExtraData = -25f;
				BasePositionY = 25f;
				obj_BiggerBase = 690f;
				obj__Mask_6_ = 690f;
				PositionY = 50f;
				Sponsor = -330f;
				break;
			case 12:
				MoveForExtraData = 25f;
				BasePositionY = -25f;
				obj_BiggerBase = 790f;
				obj__Mask_6_ = 790f;
				PositionY = -50f;
				Sponsor = -430f;
				break;
			case 13:
				MoveForExtraData = 50f;
				BasePositionY = -50f;
				obj_BiggerBase = 840f;
				obj__Mask_6_ = 840f;
				PositionY = -100f;
				Sponsor = -480f;
				break;
			default: // 11 straps
				MoveForExtraData = 0f;
				BasePositionY = 0f;
				obj_BiggerBase = 740f;
				obj__Mask_6_ = 740f;
				PositionY = 0f;
				Sponsor = -380f;
			}
			switch (whatToProcess) {
			case "ANIMATE-IN":
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$MoveForExtraData"
					+ "*ANIMATION*KEY*$ED_In_1*VALUE SET 0.0 " + MoveForExtraData + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$MoveForExtraData"
					+ "*ANIMATION*KEY*$ED_Out_1*VALUE SET 0.0 " + MoveForExtraData + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$BasePositionY"
					+ "*ANIMATION*KEY*$E_In_1*VALUE SET 0.0 " + BasePositionY + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$BasePositionY"
					+ "*ANIMATION*KEY*$E_Out_1*VALUE SET 0.0 " + BasePositionY + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj_BiggerBase"
					+ "*ANIMATION*KEY*$BB_In_1*VALUE SET " + obj_BiggerBase + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj_BiggerBase"
					+ "*ANIMATION*KEY*$BB_Out_1*VALUE SET " + obj_BiggerBase + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj__Mask_6_"
					+ "*ANIMATION*KEY*$MA_In_1*VALUE SET " + obj__Mask_6_ + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj__Mask_6_"
					+ "*ANIMATION*KEY*$MA_Out_1*VALUE SET " + obj__Mask_6_ + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$FooterAll$Footer$PositionY"
					+ "*ANIMATION*KEY*$F_In_1*VALUE SET 0.0 " + PositionY + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$FooterAll$Footer$PositionY"
					+ "*ANIMATION*KEY*$F_Out_1*VALUE SET 0.0 " + PositionY + " 0.0 \0", print_writers);

				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$S_In_1*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$In_1*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$In_2*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$Out_1*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$Out_2*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$MoveForExtraData"
					+ "*ANIMATION*KEY*$In_1*VALUE SET 0.0 " + MoveForExtraData + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$BasePositionY"
					+ "*ANIMATION*KEY*$In_1*VALUE SET 0.0 " + BasePositionY + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj_BiggerBase"
					+ "*ANIMATION*KEY*$In_1*VALUE SET " + obj_BiggerBase + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj__Mask_6_"
					+ "*ANIMATION*KEY*$In_1*VALUE SET " + obj__Mask_6_ + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$FooterAll$Footer$PositionY"
					+ "*ANIMATION*KEY*$In_1*VALUE SET 0.0 " + PositionY + " 0.0 \0", print_writers);
				
				break;
			case "CHANGE-ON":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$MoveForExtraData"
					+ "*ANIMATION*KEY*$In_2*VALUE SET 0.0 " + MoveForExtraData + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$BasePositionY"
					+ "*ANIMATION*KEY*$In_2*VALUE SET 0.0 " + BasePositionY + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj_BiggerBase"
					+ "*ANIMATION*KEY*$In_2*VALUE SET " + obj_BiggerBase + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj__Mask_6_"
					+ "*ANIMATION*KEY*$In_2*VALUE SET " + obj__Mask_6_ + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$FooterAll$Footer$PositionY"
					+ "*ANIMATION*KEY*$In_2*VALUE SET 0.0 " + PositionY + " 0.0 \0", print_writers);
				if(caption.this_fullFramesGfx.numberOfRows != lastNumberOfRows) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
							+ "*ANIMATION*KEY*$S_In_1*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
						+ "*ANIMATION*KEY*$Out_1*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
						+ "*ANIMATION*KEY*$In_2*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				}
				break;
			case "CUT-BACK":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$MoveForExtraData"
					+ "*ANIMATION*KEY*$In_1*VALUE SET 0.0 " + MoveForExtraData + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$BasePositionY"
					+ "*ANIMATION*KEY*$In_1*VALUE SET 0.0 " + BasePositionY + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj_BiggerBase"
					+ "*ANIMATION*KEY*$In_1*VALUE SET " + obj_BiggerBase + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$AllGraphics$obj__Mask_6_"
					+ "*ANIMATION*KEY*$In_1*VALUE SET " + obj__Mask_6_ + " \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$FooterAll$Footer$PositionY"
					+ "*ANIMATION*KEY*$In_1*VALUE SET 0.0 " + PositionY + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$S_In_1*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$In_1*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$In_2*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$Out_1*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*TREE*$gfx_Full_Frame$Sponsor"
					+ "*ANIMATION*KEY*$Out_2*VALUE SET 0.0 " + Sponsor + " 0.0 \0", print_writers);
				break;
			}
			break;
		}
	}
	
	
	public void Lof_ISPL_FullFramesPreview(String whatToProcess, List<PrintWriter> print_writer, int whichside, 
			Configuration config,String whichGraphicOnScreen) 
		{
		if(config.getPreview().equalsIgnoreCase("WITH_PREVIEW")) {
			String previewCommand = "";
			
			if(whichside == 1) {
				switch (whatToProcess.split(",")[0]) {
					case "Alt_x":
						previewCommand = "anim_MVP$In_Out 2.700 anim_MVP$In_Out$Essentilas$In 2.680 ";
						switch (whatToProcess.split(",")[2]) {
						case "PERFORMANCE":
							previewCommand = previewCommand + "anim_MVP$In_Out$PerformanceSubTitle$In 2.500 anim_MVP$In_Out$Performance$In 2.500";
							break;
						default:
							previewCommand = previewCommand + "anim_MVP$In_Out$LeaderBoard$In 2.700";
							break;
						}
						break;
					case "m": case "Control_m":
						previewCommand = "Shrink_FF 1.300 anim_MatchId$In_Out 2.500 anim_MatchId$In_Out$Essentials$In 2.280 "
								+ "anim_MatchId$In_Out$Header$In 1.500 anim_MatchId$In_Out$Logo$In 2.100 anim_MatchId$In_Out$Footer$In 2.500";
						break;
					case "Shift_D":
						previewCommand = "Shrink_FF 1.300 anim_Target$In_Out 2.500 anim_Target$In_Out$In 2.500";
						break;
					case "Shift_F8":
						previewCommand = "Shrink_FF 1.300 anim_BigImageLineUp$In_Out 2.900 anim_BigImageLineUp$In_Out$Essentials 2.900 anim_BigImageLineUp$In_Out$Essentials$In 2.807 "
			            		+ "anim_BigImageLineUp$In_Out$Header 2.900 anim_BigImageLineUp$In_Out$Header$In 2.400 anim_BigImageLineUp$In_Out$LineUp_BigImage 2.900 "
			            		+ "anim_BigImageLineUp$In_Out$LineUp_BigImage$In 2.900 anim_BigImageLineUp$In_Out$Footer 2.900 anim_BigImageLineUp$In_Out$Footer$In 2.380";
						break;
				    case "F1": case "Shift_T":  case "F4":  case "Control_F7": case "Shift_K": case "Control_F10": case "Alt_F11": case "p": case "F2": case "Shift_F10": 
				    case "Control_F11": case "Shift_F11": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": 
				    case "Control_Shift_Y": case "Control_Shift_F8": case "Alt_F5": case "Control_Shift_F7": case "Alt_Shift_F11": case "Control_c": case "Control_v":
				    	
				    	previewCommand = "Shrink_FF 1.300 anim_Fullframes$In_Out 3.200 anim_Fullframes$In_Out$Essentials 3.200 anim_Fullframes$In_Out$Essentials$In 1.740 "
			            		+ "anim_Fullframes$In_Out$Header 3.200 anim_Fullframes$In_Out$Header$In 2.400 anim_Fullframes$In_Out$SubHeader 3.200 "
			            		+ "anim_Fullframes$In_Out$SubHeader$In 2.200 anim_Fullframes$In_Out$Footer 3.200 anim_Fullframes$In_Out$Footer$In 3.200 ";
			            switch (whatToProcess.split(",")[0]) {
			            case "F1":
			            	if(whatToProcess.contains("NORMAL")) {
				            	 previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$BatStyle2 3.200 "
				            	 		+ "anim_Fullframes$In_Out$Main$BatStyle2$In 2.980";
							}else if(whatToProcess.contains("SPLIT")) {
								previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$SplitBatBall_Card 3.200 "
										+ "anim_Fullframes$In_Out$Main$SplitBatBall_Card$In 3.180";
							}else if(whatToProcess.contains("TRADITIONAL")) {
								previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$BattingCard_Normal 3.200 "
										+ "anim_Fullframes$In_Out$Main$BattingCard_Normal$In 3.200";
							}else if(whatToProcess.contains("BATTING_CHANGE_ON")) {
								previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$BatStyle3 3.200 "
										+ "anim_Fullframes$In_Out$$Main$BatStyle3$In 2.740";
							}
			            	break;
			            case "F2":
			            	if(whatToProcess.contains("TRADITIONAL")) {
			            		previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$BowlingCard_Normal 3.200 "
			            				+ "anim_Fullframes$In_Out$Main$BowlingCard_Normal$In 3.200";
							}else if(whatToProcess.contains("BOWLING_CHANGE_ON")) {
								previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$BatStyle3 3.200 "
										+ "anim_Fullframes$In_Out$$Main$BatStyle3$In 2.740";
							}
			            	break;
			            case "Shift_F10":
			            	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$Worm 3.200 anim_Fullframes$In_Out$Main$Worm$In 3.220 "
			            			+ "anim_Fullframes$In_Out$Main$Worm$In$Runs 3.220";	
			            	break;
			            case "Shift_F11": case "Control_F11":
			            	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$Summary 3.200 anim_Fullframes$In_Out$Main$Summary$In 3.040";	
			            	break;
			            case "Alt_Shift_F11":
			            	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$GlobalStats 3.200 anim_Fullframes$In_Out$Main$GlobalStats$In 2.600";	
			            	break;
			            case "Shift_T":
			            	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$PlayingXI 3.200 anim_Fullframes$In_Out$Main$PlayingXI$In 3.000";	
			            	break;
			            case "Control_Shift_F7":
			            	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$LineUp_Single 3.200 anim_Fullframes$In_Out$Main$LineUp_Single$In 3.200";	
			            	break;
			            case "F4":  
			            	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$Partnership_List 3.200 anim_Fullframes$In_Out$Main$Partnership_List$In 2.980";
			            	break;
			            case "Control_F7": 
			            	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$Both_Team 3.200 anim_Fullframes$In_Out$Main$Both_Team$In 3.180";
			            	break;
			            case "Shift_K": 
			            	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$Partnership 3.200 anim_Fullframes$In_Out$Main$Partnership$In 2.700";
			            	break;
			            case "Control_F10": 
			            	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$Manhattan 3.200 anim_Fullframes$In_Out$Main$Manhattan$In 3.280";
			            	break;
					    case "p": 
					    	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$Standings 3.200 anim_Fullframes$In_Out$Main$Standings$In 2.680";
					    	break;
					    case "Alt_F11":
					    	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$Doublemanhattan 3.200 anim_Fullframes$In_Out$Main$Doublemanhattan$In 3.280";
					    	break;
					    case "Alt_F5":
					    	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$DoubleTeamManhattan 3.200 anim_Fullframes$In_Out$Main$DoubleTeamManhattan$In 3.280";
					    	break;
					    case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_c": case "Control_v": case "Shift_V": case "Control_Shift_Z": 
					    case "Control_Shift_Y": case "Control_Shift_F8":
					    	previewCommand = previewCommand + "anim_Fullframes$In_Out$Main$LeaderBoard 3.200 anim_Fullframes$In_Out$Main$LeaderBoard$In 2.700";
					    	break;	
					    	
			            }
				        break;
				}
	
			}else {
				switch(whichGraphicOnScreen.split(",")[0]){
				case "Alt_x":
					previewCommand = "Change_MVP$Performance 1.620 Change_MVP$Performance$Change_Out 1.080 Change_MVP$Performance$Change_In 1.620";
					
					break;
				case "Shift_F8":
					previewCommand = "Change_BigImageLineUp$Header 1.700 Change_BigImageLineUp$Header$Change_Out 0.800 Change_BigImageLineUp$Header$Change_In 1.700 "
							+ "Change_BigImageLineUp$LineUp_BigImage 1.900 Change_BigImageLineUp$LineUp_BigImage$Out 0.840 Change_BigImageLineUp$LineUp_BigImage$In 1.900 "
							+ "Change_BigImageLineUp$Footer 1.300 Change_BigImageLineUp$Footer$Change_Out 0.500 Change_BigImageLineUp$Footer$Change_In 1.300";
					
					break;
				default:
					previewCommand = "Change$Header 1.700 Change$Header$Change_Out 1.000 Change$Header$Change_In 1.700 Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.600 "
							+ "Change$SubHeader$Change_In 1.100 Change$Footer 1.300 Change$Footer$Change_Out 0.500 Change$Footer$Change_In 1.300";
					break;
				}
				
				switch(whichGraphicOnScreen.split(",")[0]){
				case "F1":
					if(whichGraphicOnScreen.contains("NORMAL")) {
						previewCommand = previewCommand + " Change$BatStyle2$Change_Out 1.000";
					}else if(whichGraphicOnScreen.contains("SPLIT")) {
						previewCommand = previewCommand + " Change$SplitBatBall_Card$Change_Out 0.700";
					}else if(whichGraphicOnScreen.contains("TRADITIONAL")) {
						previewCommand = previewCommand + " Change$BattingCard_Normal$Change_Out 0.900";
					}else if(whichGraphicOnScreen.contains("BATTING_CHANGE_ON")) {
						previewCommand = previewCommand + " Change$BatStyle3$Change_Out 0.640";
					}
					break;
				case "F2":
					if(whichGraphicOnScreen.contains("TRADITIONAL")) {
						previewCommand = previewCommand + " Change$BowlingCard_Normal$Change_Out 0.900";
					}else if(whichGraphicOnScreen.contains("BOWLING_CHANGE_ON")) {
						previewCommand = previewCommand + " Change$BatStyle3$Change_Out 0.640";
					}
					break;
				case "Shift_F10":
					previewCommand = previewCommand + " Change$Worm$Change_Out 0.620";	
	            	break;
	            case "Shift_F11": case "Control_F11":
	            	previewCommand = previewCommand + " Change$Summary$Change_Out 0.620";
	            	break;	
				case "Shift_T":
					previewCommand = previewCommand + " Change$PlayingXI$Change_Out 1.000";
					break;
				case "Control_Shift_F7":
					previewCommand = previewCommand + " Change$LineUp_Single$Change_Out 1.000";
					break;
				case "Control_F7":
					previewCommand = previewCommand + " Change$Both_Team$Change_Out 0.900";
					break;
				case "Shift_K": 
					previewCommand = previewCommand + " Change$Partnership$Change_Out 0.600";
					break;
				case "F4": 
					previewCommand = previewCommand + " Change$Partnership_List$Change_Out 0.860";
					break;
				case "Control_F10":
					previewCommand = previewCommand + " Change$Manhattan$Change_Out 0.360";
					break;
				case "p":
					previewCommand = previewCommand + " Change$Standings$Change_Out 0.480";
					break;
				case "Alt_F11":
					previewCommand = previewCommand + " Change$Doublemanhattan$Change_Out 0.360";
					break;
				case "Alt_F5":
					previewCommand = previewCommand + " Change$DoubleTeamManhattan$Change_Out 0.372";
					break;
				case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_c": case "Control_v":
				case "Shift_V": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8": 
			    	previewCommand = previewCommand + " Change$LeaderBoard$Title$Change_Out 0.360 Change$LeaderBoard$Data$Change_Out 0.540";
			    	break;	
				}
				switch(whatToProcess.split(",")[0]){
				case "F1":
					if(whatToProcess.contains("NORMAL")) {
						previewCommand = previewCommand + " Change$BatStyle2$Change_In 1.840";
					}else if(whatToProcess.contains("SPLIT")) {
						previewCommand = previewCommand + " Change$SplitBatBall_Card$Change_In 2.200";
					}else if(whatToProcess.contains("TRADITIONAL")) {
						previewCommand = previewCommand + " Change$BattingCard_Normal$Change_In 2.380";
					}else if(whatToProcess.contains("BATTING_CHANGE_ON")) {
						previewCommand = previewCommand + " Change$BatStyle3$Change_In 1.840";
					}
					break;
				case "F2":
					if(whatToProcess.contains("TRADITIONAL")) {
						previewCommand = previewCommand + " Change$BowlingCard_Normal$Change_In 2.400";
					}else if(whatToProcess.contains("BOWLING_CHANGE_ON")) {
						previewCommand = previewCommand + " Change$BatStyle3$Change_In 1.840";
					}
					break;
				case "Shift_F10":
					previewCommand = previewCommand + " Change$Worm$Change_In 1.940 Change$Worm$Change_In$Runs 1.940";	
	            	break;
	            case "Shift_F11": case "Control_F11":
	            	previewCommand = previewCommand + " Change$Summary$Change_In 1.840";
	            	break;	
				case "Shift_T":
					previewCommand = previewCommand + " Change$PlayingXI$Change_In 2.220";
					break;
				case "Control_Shift_F7":
					previewCommand = previewCommand + " Change$LineUp_Single$Change_In 2.500";
					break;
				case "Control_F7":
					previewCommand = previewCommand + " Change$Both_Team$Change_In 2.280";
					break;
				case "Shift_K": 
					previewCommand = previewCommand + " Change$Partnership$Change_In 1.520";
					break;
				case "F4": 
					previewCommand = previewCommand + " Change$Partnership_List$Change_In 1.900";
					break;
				case "Control_F10":
					previewCommand = previewCommand + " Change$Manhattan$Change_In 2.000";
					break;
				case "p":
					previewCommand = previewCommand + " Change$Standings$Change_In 1.600";
					break;
				case "Alt_F11":
					previewCommand = previewCommand + " Change$Doublemanhattan$Change_In 2.500";
					break;
				case "Alt_F5":
					previewCommand = previewCommand + " Change$DoubleTeamManhattan$Change_In 2.100";
					break;
				case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_c": case "Control_v":
				case "Shift_V": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8":
					previewCommand = previewCommand + " Change$LeaderBoard$Title$Change_In 1.040 Change$LeaderBoard$Data$Change_In 1.420";
			    	break;
				}
			}
			
			switch (whatToProcess.split(",")[0]) {
			case "Alt_x":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/MVP "
				    	+ "C:/Temp/Preview.tga " + previewCommand + " \0", print_writer);
				break;
			default:
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FullFrames "
				    	+ "C:/Temp/Preview.tga " + previewCommand + " \0", print_writer);
				break;
			}
		}
	}
	
	public void processT20_MumbaiFullFramesPreview(String whatToProcess, List<PrintWriter> print_writer, int whichside, Configuration config,String whichGraphicOnScreen) {
		
		String previewCommand = "";
		if(whichside == 1) {
			switch(whatToProcess.split(",")[0]) {
			case "m": case "Control_m":
				previewCommand = "anim_Ident$In_Out$In 2.500";
				break;
			case "Shift_D":
				previewCommand = "anim_Target$In_Out$In 2.720";
				break;
			case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
				previewCommand = "anim_Profile$In_Out$In 2.500";
				break;
			case "Control_b":
				previewCommand = "anim_Profile$In_Out$In 2.500";
				break;
			case "Shift_T":
				previewCommand = "anim_Infobar$Push 0.500 anim_Image_Lineup$In_Out$Essentials$In 2.300 anim_Image_Lineup$In_Out$Elemnets$In 2.500 anim_Image_Lineup$In_Out$ColourBase$In 2.400 "
						+ "anim_Image_Lineup$In_Out$VerticalText$In 2.200 anim_Image_Lineup$In_Out$Logo$In 2.000 anim_Image_Lineup$In_Out$Header$In 2.300 anim_Image_Lineup$In_Out$SubHeader$In 2.500 "
						+ "anim_Image_Lineup$In_Out$Footer$In 2.140 anim_Image_Lineup$In_Out$Image_Lineup$In 2.800 ";
				break;
			
			case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
				previewCommand = "InfoBar$Push 0.400 FF_Leaderboard$FullFramers$Inout 2.460 FF_Leaderboard$FullFramers$Inout$In 2.460";
				break;
				
			case "F1": case "Control_Shift_F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Control_F7": case "Shift_F10": case "Shift_F11": case "p": case "Alt_Shift_J": 
			case "Control_F1": case "Alt_F9": case "Shift_F8": case "Control_F10": case "Control_Shift_F2": case "Alt_Shift_F10": case "Alt_Shift_F12": case "Alt_Shift_F9": case "Alt_Shift_F2":
				
				previewCommand = "anim_Infobar$Push 0.500 anim_FullFrames$In_Out$Essentials$In 2.300 anim_FullFrames$In_Out$Elemnets$In 2.500 anim_FullFrames$In_Out$ColourBase$In 2.400 "
						+ "anim_FullFrames$In_Out$VerticalText$In 2.200 anim_FullFrames$In_Out$Logo$In 2.000 anim_FullFrames$In_Out$Header$In 2.300 anim_FullFrames$In_Out$SubHeader$In 2.500 "
						+ "anim_FullFrames$In_Out$Footer$In 2.140 ";
				switch(whatToProcess.split(",")[0]) {
				case "F1":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$BattingCard$In 2.180";
					break;
				case "Control_Shift_F1": case "Control_Shift_F2":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$SplitCard$In 2.200 MoveForSplitCard 0.603";
					break;
				case "F2":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$BowlingCard$In 2.180";
					break;
				case "Control_F11": case "Shift_F11":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$Summary$In 2.060";
					break;
				case "Alt_Shift_F10":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$InningsSummary$In 1.900";
					break;
				case "Alt_Shift_F12":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$PhasewiseRunRates$In 2.580";
					break;
				case "Alt_Shift_F9":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$PhasewiseSummary$In 1.920";
					break;
				case "Alt_Shift_F2":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$BattingComparison$In 2.580";
					break;
				case "F4":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$PartnershipList$In 2.180";
					break;
				case "Shift_K":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$Partnership$In 2.260";
					break;
				case "Alt_F9":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$Team$In 2.260";
					break;
				case "Shift_F8":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$TeamWithSub$In 2.180";
					break;
				case "Control_F7": 
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$Teams$In 2.220";
					break;
				case "Shift_F10":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$Worm$In 2.580 anim_FullFrames$In_Out$Main$Worm$In$Runs 2.580";
					break;
				case "Control_F10":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$Manhattan$In 2.580 anim_FullFrames$In_Out$Main$Manhattan$In$Runs 2.580";
					break;
				case "p":
					
					break;
				case "Alt_Shift_J":
					
					break;
				case "Control_F1":
					previewCommand = previewCommand + "anim_FullFrames$In_Out$Main$ImageBattingCard$In 2.566";
					break;
				}
				break;
			}
		}else if(whichside == 2){
			switch(whatToProcess.split(",")[0]) {
			case "Control_b":
				previewCommand = "anim_Profile$InAt_To_Profile 1.780 anim_Profile$InAt_To_Profile$Wipe 1.260 anim_Profile$InAt_To_Profile$InAt_Out 1.200 "
						+ "anim_Profile$InAt_To_Profile$ProfileIn 1.780";
				break;
			case "Shift_T":
				previewCommand = "Change_Image_Lineup$Elements 1.700 Change_Image_Lineup$Elements$Change_Out 0.680 Change_Image_Lineup$Elements$Change_In 1.700 "
						+ "Change_Image_Lineup$ColourBase 1.500 Change_Image_Lineup$ColourBase$Change_Out 0.800 Change_Image_Lineup$ColourBase$Change_In 1.500 "
						+ "Change_Image_Lineup$VerticalText 1.000 Change_Image_Lineup$VerticalText$Change_Out 0.600 Change_Image_Lineup$VerticalText$Change_In 1.000 "
						+ "Change_Image_Lineup$Logo 1.320 Change_Image_Lineup$Logo$Change_Out 0.400 Change_Image_Lineup$Logo$Change_In 1.320 "
						+ "Change_Image_Lineup$Header 1.300 Change_Image_Lineup$Header$Change_Out 0.580 Change_Image_Lineup$Header$Change_In 1.300 "
						+ "Change_Image_Lineup$SubHeader 1.300 Change_Image_Lineup$SubHeader$Change_Out 0.580 Change_Image_Lineup$SubHeader$Change_In 1.300 "
						+ "Change_Image_Lineup$Footer 1.000 Change_Image_Lineup$Footer$Change_Out 0.300 Change_Image_Lineup$Footer$Change_In 1.000 "
						+ "Change_Image_Lineup$Image_Lineup 2.120 Change_Image_Lineup$Image_Lineup$Change_Out 0.860 Change_Image_Lineup$Image_Lineup$Change_In 2.120";
				break;
				
			case "F1": case "Control_Shift_F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Control_F7": case "Shift_F10": case "Shift_F11": case "p": 
			case "Alt_Shift_J": case "Control_F1": case "Alt_F9": case "Shift_F8": case "Control_F10": case "Control_Shift_F2": case "Alt_Shift_F10": case "Alt_Shift_F12": 
			case "Alt_Shift_F9": case "Alt_Shift_F2":
				
				previewCommand = "Change_Fullframes$Elements 1.700 Change_Fullframes$Elements$Change_Out 0.680 Change_Fullframes$Elements$Change_In 1.700 "
						+ "Change_Fullframes$ColourBase 1.500 Change_Fullframes$ColourBase$Change_Out 0.800 Change_Fullframes$ColourBase$Change_In 1.500 "
						+ "Change_Fullframes$VerticalText 1.000 Change_Fullframes$VerticalText$Change_Out 0.600 Change_Fullframes$VerticalText$Change_In 1.000 "
						+ "Change_Fullframes$Logo 1.320 Change_Fullframes$Logo$Change_Out 0.400 Change_Fullframes$Logo$Change_In 1.320 "
						+ "Change_Fullframes$Header 1.300 Change_Fullframes$Header$Change_Out 0.580 Change_Fullframes$Header$Change_In 1.300 "
						+ "Change_Fullframes$SubHeader 1.300 Change_Fullframes$SubHeader$Change_Out 0.580 Change_Fullframes$SubHeader$Change_In 1.300 "
						+ "Change_Fullframes$Footer 1.000 Change_Fullframes$Footer$Change_Out 0.300 Change_Fullframes$Footer$Change_In 1.000";
					
				switch (whichGraphicOnScreen.split(",")[0]) {
				case "F1":
					previewCommand = previewCommand + " Change_Fullframes$BattingCard 1.280 Change_Fullframes$BattingCard$Change_Out 0.540 Change_Fullframes$BattingCard$Change_In 1.280";
					break;
				case "Control_Shift_F1": case "Control_Shift_F2":
					previewCommand = previewCommand + " Change_Fullframes$SplitCard 1.300 Change_Fullframes$SplitCard$Change_Out 0.540 Change_Fullframes$SplitCard$Change_In 1.300";
					if(!whatToProcess.split(",")[0].split(",")[0].equalsIgnoreCase("Control_Shift_F1") && !whatToProcess.split(",")[0].split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
							previewCommand = previewCommand + " MoveForSplitCard 0.0";
						}
					}
					break;
				case "F2":
					previewCommand = previewCommand + " Change_Fullframes$BowlingCard 1.280 Change_Fullframes$BowlingCard$Change_Out 0.540 Change_Fullframes$BowlingCard$Change_In 1.280";
					break;
				case "Control_F11": case "Shift_F11":
					previewCommand = previewCommand + " Change_Fullframes$Summary 1.160 Change_Fullframes$Summary$Change_Out 0.480 Change_Fullframes$Summary$Change_In 1.160";
					break;
				case "Alt_Shift_F10":
					previewCommand = previewCommand + " Change_Fullframes$InningsSummary 1.000 Change_Fullframes$InningsSummary$Change_Out 0.340 Change_Fullframes$InningsSummary$Change_In 1.000";
					break;
				case "Alt_Shift_F12":
					previewCommand = previewCommand + " Change_Fullframes$PhasewiseRunRates 1.680 Change_Fullframes$PhasewiseRunRates$Change_Out 0.600 Change_Fullframes$PhasewiseRunRates$Change_In 1.680";
					break;
				case "Alt_Shift_F9":
					previewCommand = previewCommand + " Change_Fullframes$PhasewiseSummary 1.000 Change_Fullframes$PhasewiseSummary$Change_Out 0.500 Change_Fullframes$PhasewiseSummary$Change_In 1.000";
					break;
				case "Alt_Shift_F2":
					previewCommand = previewCommand + " Change_Fullframes$BattingComparison 1.680 Change_Fullframes$BattingComparison$Change_Out 0.600 Change_Fullframes$BattingComparison$Change_In 1.680";
					break;
				case "F4":
					previewCommand = previewCommand + " Change_Fullframes$PartnershipList 1.280 Change_Fullframes$PartnershipList$Change_Out 0.540 "
							+ "Change_Fullframes$PartnershipList$Change_In 1.280";
					break;
				case "Alt_F9":
					previewCommand = previewCommand + " Change_Fullframes$Team 1.240 Change_Fullframes$Team$Change_Out 0.520 Change_Fullframes$Team$Change_In 1.240";
					break;
				case "Shift_F8":
					previewCommand = previewCommand + " Change_Fullframes$TeamWithSub 1.280 Change_Fullframes$TeamWithSub$Change_Out 0.540 Change_Fullframes$TeamWithSub$Change_In 1.280";
					break;
				case "Shift_T":
					
					break;
				case "Shift_K":
					previewCommand = previewCommand + " Change_Fullframes$Partnership 1.280 Change_Fullframes$Partnership$Change_Out 0.540 Change_Fullframes$Partnership$Change_In 1.280";
					break;
				case "Control_F7":
					previewCommand = previewCommand + " Change_Fullframes$Teams 1.320 Change_Fullframes$Teams$Change_Out 0.520 Change_Fullframes$Teams$Change_In 1.320";
					break;
				case "Shift_F10":
					previewCommand = previewCommand + " Change_Fullframes$Worm 1.680 Change_Fullframes$Worm$Change_Out 0.600 Change_Fullframes$Worm$Change_In 1.680 "
							+ "Change_Fullframes$Worm$Change_In$Runs 1.680";
					break;
				case "Control_F10":
					previewCommand = previewCommand + " Change_Fullframes$Manhattan 1.680 Change_Fullframes$Manhattan$Change_Out 0.600 Change_Fullframes$Manhattan$Change_In 1.680 "
							+ "Change_Fullframes$Manhattan$Change_In$Runs 1.680";
					break;
				case "p":
					
					break;
				case "Alt_Shift_J":
					
					break;
				case "Control_F1":
					previewCommand = previewCommand + " Change_Fullframes$ImageBattingCard 1.666 Change_Fullframes$ImageBattingCard$Change_Out 0.620 Change_Fullframes$ImageBattingCard$Change_In 1.666";
					break;
				}
				
				if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
					switch (whatToProcess.split(",")[0]) {
					case "F1":
						previewCommand = previewCommand + " Change_Fullframes$BattingCard 1.280 Change_Fullframes$BattingCard$Change_Out 0.540 Change_Fullframes$BattingCard$Change_In 1.280";
						break;
					case "Control_Shift_F1": case "Control_Shift_F2":
						previewCommand = previewCommand + " Change_Fullframes$SplitCard 1.300 Change_Fullframes$SplitCard$Change_Out 0.540 Change_Fullframes$SplitCard$Change_In 1.300";
						if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F1") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
							previewCommand = previewCommand + " MoveForSplitCard 0.603";
						}
						break;
					case "F2":
						previewCommand = previewCommand + " Change_Fullframes$BowlingCard 1.280 Change_Fullframes$BowlingCard$Change_Out 0.540 Change_Fullframes$BowlingCard$Change_In 1.280";
						break;
					case "Control_F11": case "Shift_F11":
						if(!whatToProcess.equalsIgnoreCase("Control_F11") && !whatToProcess.equalsIgnoreCase("Shift_F11")) {
							previewCommand = previewCommand + " Change_Fullframes$Summary 1.160 Change_Fullframes$Summary$Change_Out 0.480 Change_Fullframes$Summary$Change_In 1.160";
						}
						break;
					case "Alt_Shift_F10":
						previewCommand = previewCommand + " Change_Fullframes$InningsSummary 1.000 Change_Fullframes$InningsSummary$Change_Out 0.340 Change_Fullframes$InningsSummary$Change_In 1.000";
						break;
					case "Alt_Shift_F12":
						previewCommand = previewCommand + " Change_Fullframes$PhasewiseRunRates 1.680 Change_Fullframes$PhasewiseRunRates$Change_Out 0.600 Change_Fullframes$PhasewiseRunRates$Change_In 1.680";
						break;
					case "Alt_Shift_F9":
						previewCommand = previewCommand + " Change_Fullframes$PhasewiseSummary 1.000 Change_Fullframes$PhasewiseSummary$Change_Out 0.500 Change_Fullframes$PhasewiseSummary$Change_In 1.000";
						break;
					case "Alt_Shift_F2":
						previewCommand = previewCommand + " Change_Fullframes$BattingComparison 1.680 Change_Fullframes$BattingComparison$Change_Out 0.600 Change_Fullframes$BattingComparison$Change_In 1.680";
						break;
					case "F4":
						previewCommand = previewCommand + " Change_Fullframes$PartnershipList 1.280 Change_Fullframes$PartnershipList$Change_Out 0.540 Change_Fullframes$PartnershipList$Change_In 1.280";
						break;
					case "Alt_F9":
						previewCommand = previewCommand + " Change_Fullframes$Team 1.240 Change_Fullframes$Team$Change_Out 0.520 Change_Fullframes$Team$Change_In 1.240";
						break;
					case "Shift_F8":
						previewCommand = previewCommand + " Change_Fullframes$TeamWithSub 1.280 Change_Fullframes$TeamWithSub$Change_Out 0.540 Change_Fullframes$TeamWithSub$Change_In 1.280";
						break;
					case "Shift_T":
						
						break;
					
					case "Shift_K":
						previewCommand = previewCommand + " Change_Fullframes$Partnership 1.280 Change_Fullframes$Partnership$Change_Out 0.540 Change_Fullframes$Partnership$Change_In 1.280";
						break;
					case "Control_F7":
						
						break;
					case "Shift_F10":
						previewCommand = previewCommand + " Change_Fullframes$Worm 1.680 Change_Fullframes$Worm$Change_Out 0.600 Change_Fullframes$Worm$Change_In 1.680 "
								+ "Change_Fullframes$Worm$Change_In$Runs 1.680";
						break;
					case "Control_F10":
						previewCommand = previewCommand + " Change_Fullframes$Manhattan 1.680 Change_Fullframes$Manhattan$Change_Out 0.600 Change_Fullframes$Manhattan$Change_In 1.680 "
								+ "Change_Fullframes$Manhattan$Change_In$Runs 1.680";
						break;
					case "p":
						
						break;
					case "Alt_Shift_J":
						
						break;
					case "Control_F1":
						previewCommand = previewCommand + " Change_Fullframes$ImageBattingCard 1.666 Change_Fullframes$ImageBattingCard$Change_Out 0.620 Change_Fullframes$ImageBattingCard$Change_In 1.666";
						break;
					}
				}
			}
		}
		CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/gfx_Fullframes " + "C:/Temp/Preview.tga " + previewCommand + "\0", print_writer);
	}
	
	public void processFullFramesPreview(String whatToProcess, List<PrintWriter> print_writer, int whichside, 
			Configuration config,String whichGraphicOnScreen) 
		{
		if(config.getPreview().equalsIgnoreCase("WITH_PREVIEW")) {
			String previewCommand = "";
			switch (config.getBroadcaster().toUpperCase()) {
			case Constants.T20_MUMBAI:
				processT20_MumbaiFullFramesPreview(whatToProcess, print_writer, whichside, config, whichGraphicOnScreen);
				break;
			 case Constants.VIDARBHA:
		            if(whichside == 1) {
		                if(whatToProcess.contains(",")) {
		                    switch(whatToProcess.split(",")[0]) {
		                    case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Control_F7": case "Shift_F8":
		                    case "Shift_K": case "Control_p": case "Shift_F11": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
		                    case "Control_c": case "Control_v": case "Shift_V": case "Control_F10":  case "Shift_T": case "Control_d": case "Control_e":
		                        previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.140 Anim_FullFrames$In_Out$Header$In 2.100";
		                        break;
		                    case "m": case "Control_m":
		                        previewCommand = "Anim_MatchId$In_Out$In 1.700";
		                        break;
		                    case "Shift_D":
		                        previewCommand = "Anim_Target$In_Out$In 1.500";
		                        break;
		                    }
		                    switch(whatToProcess.split(",")[0]) {
		                    case "F1": case "Control_Shift_A":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Batting_Card$In 2.200";
		                        break;
		                    case "F2":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Bowling_Card$In 2.120";
		                        break;
		                    case "Control_d": case "Control_e":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Profile$In 1.843";
		                        break;
		                    case "F4":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership_List$In 2.200";
		                        break;
		                    case "Control_F11": case "Shift_F11":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Summary$In 1.880";
		                        break;
		                    case "Control_F7":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Teams$In 2.220";
		                        break;
		                    case "Shift_T":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Team_Single$In 2.240";
		                        break;
		                    case "Shift_F8":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LineUp_Image$In 2.240";
		                        break;
		                    case "Shift_K":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership$In 2.200";
		                        break;
		                    case "Control_F10":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan$In 2.220";
		                        break;
		                    case "Control_p":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Standings$In 1.843";
		                        break;
		                    case "z": case "x": case "c": case "v": case "Control_c": case "Control_v": case "Control_z": case "Control_x":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LeaderBoard$In 2.220";
		                        previewCommand = previewCommand + " LeaderBoardHighlight$Side1$Player" + whatToProcess.split(",")[2].split("_")[0] + " 1.574";
		                        break;
		                    case "Shift_V":
		                        previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LeaderBoard$In 2.220";
		                        break;
		                    }
		                }
		            } else if(whichside == 2) {
		                if(whatToProcess.contains(",")) {
		                    switch(whatToProcess.split(",")[0]) {
		                    case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Shift_T": case "Shift_F8": case "Shift_K":
		                    case "Control_p": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
		                        previewCommand = previewCommand + "Change$Header 1.600 Change$Header$Change_In 1.600 Change$Header$Change_Out 0.420";
		                        if(whichGraphicOnScreen.contains(",")) {
		                            switch(whichGraphicOnScreen.split(",")[0]) {
		                            case "F1": case "Control_Shift_A":
		                                previewCommand = previewCommand + " Change$Batting_Card 1.900 Change$Batting_Card$Change_Out 0.860 Change$Batting_Card$Change_In 1.900";
		                                break;
		                            case "F2":
		                                previewCommand = previewCommand + " Change$Bowling_Card 1.820 Change$Bowling_Card$Change_Out 0.760 Change$Bowling_Card$Change_In 1.820";
		                                break;
		                            case "F4":
		                                previewCommand = previewCommand + " Change$Partnership_List 1.900 Change$Partnership_List$Change_Out 0.860 "
		                                        + "Change$Partnership_List$Change_In 1.900";
		                                break;
		                            case "Control_F11":
		                                previewCommand = previewCommand + " Change$Summary 1.580 Change$Summary$Change_Out 0.760 Change$Summary$Change_In 1.580";
		                                break;
		                            case "Shift_T":
		                                previewCommand = previewCommand + " Change$Team_Single 1.940 Change$Team_Single$Change_Out 0.820 Change$Team_Single$Change_In 1.940";
		                                break;
		                            case "Shift_F8":
		                                previewCommand = previewCommand + " Change$LineUp_Image 1.940 Change$LineUp_Image$Change_Out 0.820 Change$LineUp_Image$Change_In 1.940";
		                                break;
		                            case "Shift_K":
		                                previewCommand = previewCommand + " Change$Partnership 1.900 Change$Partnership$Change_Out 0.860 "
		                                        + "Change$Partnership$Change_In 1.900";
		                                break;
		                            case "Control_p":
		                                previewCommand = previewCommand + " Change$Standings 1.543 Change$Standings$Change_Out 0.760 "
		                                        + "Change$Standings$Change_In 1.543";
		                                break;
		                            case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
		                                previewCommand = previewCommand + " Change$LeaderBoard 2.200 Change$LeaderBoard$Change_Out 0.760 "
		                                        + "Change$LeaderBoard$Change_In 2.200";
		                                previewCommand = previewCommand + " LeaderBoardHighlight$Side2$Player" + whatToProcess.split(",")[2].split("_")[0] + " 1.574";
		                                break;
		                            }
		                        }
		                        if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
		                            switch(whatToProcess.split(",")[0]) {
		                            case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Shift_K": case "Control_p":
		                            case "Shift_T": case "Shift_F8": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
		                                previewCommand = previewCommand + " Header_Shrink 0.000 Header_Shrink$In 0.000";
		                                break;
		                            }
		                            switch(whatToProcess.split(",")[0]) {
		                            case "F1": case "Control_Shift_A":
		                                previewCommand = previewCommand + " Change$Batting_Card 1.900 Change$Batting_Card$Change_Out 0.860 Change$Batting_Card$Change_In 1.900";
		                                break;
		                            case "F2":
		                                previewCommand = previewCommand + " Change$Bowling_Card 1.820 Change$Bowling_Card$Change_Out 0.760 Change$Bowling_Card$Change_In 1.820";
		                                break;
		                            case "F4":
		                                previewCommand = previewCommand + " Change$Partnership_List 1.900 Change$Partnership_List$Change_Out 0.860 "
		                                        + "Change$Partnership_List$Change_In 1.900";
		                                break;
		                            case "Control_F11":
		                                previewCommand = previewCommand + " Change$Summary 1.580 Change$Summary$Change_Out 0.760 Change$Summary$Change_In 1.580";
		                                break;
		                            case "Shift_T":
		                                previewCommand = previewCommand + " Change$Team_Single 1.940 Change$Team_Single$Change_Out 0.820 Change$Team_Single$Change_In 1.940";
		                                break;
		                            case "Shift_F8":
		                                previewCommand = previewCommand + " Change$LineUp_Image 1.940 Change$LineUp_Image$Change_Out 0.820 Change$LineUp_Image$Change_In 1.940";
		                                break;
		                            case "Shift_K":
		                                previewCommand = previewCommand + " Change$Partnership 1.900 Change$Partnership$Change_Out 0.860 "
		                                        + "Change$Partnership$Change_In 1.900";
		                                break;
		                            case "Control_p":
		                                previewCommand = previewCommand + " Change$Standings 1.543 Change$Standings$Change_Out 0.760 "
		                                        + "Change$Standings$Change_In 1.543";
		                                break;
		                            }
		                        }
		                        break;
		                    }
		                }
		            }
		            CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FullFrames "
		                    + "C:/Temp/Preview.tga " + previewCommand + " \0", print_writer);
		            break;
			case Constants.BENGAL_T20:
				if(whichside == 1) {
					if(whatToProcess.contains(",")) {
						switch(whatToProcess.split(",")[0]) {
						case "m": case "Control_m":
							previewCommand = "anim_Infobar$Push 1.000 Anim_Ident$In 2.000 ";
							break;
						case "Control_Shift_D":
							previewCommand = "anim_Infobar$Push 1.000 Anim_DoubleIdent$In 2.000";
							break;
						case "Alt_Shift_Z":
							previewCommand = "anim_Infobar$Push 1.000 Anim_Teams$In_Out$In 2.200";
							break;
						case "Control_Shift_F7":
							previewCommand = "anim_Infobar$Push 1.000 Anim_Lineup_Image_Big$In_Out$In 2.480";
							break;
						
						case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
							previewCommand = "anim_Infobar$Push 1.000 anim_Profile 1.700 anim_Profile$Essentials$In 1.140 anim_Profile$Main$In 1.140";
							break;
						
						case "Alt_Shift_R":
							previewCommand = "Anim_Infobar$Push 1.000 Anim_FullFrames$In_Out$Essentials$In 2.200 Anim_FullFrames$In_Out$Header$In 1.900 "
									+ "Anim_FullFrames$In_Out$Team_Fixtures$In 2.060";
							break;
							
						case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F1": case "Shift_F10": case "Control_F11": case "Shift_F11": case "p": case "Control_p":
						case "Shift_T": case "Control_F7": case "Control_F10": case "Alt_F9": case "Shift_K":case "z": case "x": case "c": case "v": case "Alt_F10": case "Control_Shift_F8":

						case "Alt_F11": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F1": case "Control_Shift_F2":

							
							if(whatToProcess.split(",")[0].equalsIgnoreCase("z") ||
									whatToProcess.split(",")[0].equalsIgnoreCase("x") || whatToProcess.split(",")[0].equalsIgnoreCase("c") ||
									whatToProcess.split(",")[0].equalsIgnoreCase("v") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_z") ||
									whatToProcess.split(",")[0].equalsIgnoreCase("Control_x") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_Z") ||
									whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_Y") || whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F8")) {
								previewCommand = "anim_Infobar$Push 1.000 Anim_FullFrames$In_Out$Essentials$In 2.200 Anim_FullFrames$In_Out$Header$In 1.900 "
										+ "Anim_FullFrames$In_Out$Sponsor$In 2.420";
								break;
							}else if(!whatToProcess.split(",")[0].equalsIgnoreCase("Shift_K")) {
								previewCommand = "anim_Infobar$Push 1.000 Anim_FullFrames$In_Out$Essentials$In 2.200 Anim_FullFrames$In_Out$Header$In 1.900 "
										+ "Anim_FullFrames$In_Out$Footer$In 2.200";
								break;
							}else {
								previewCommand = "Anim_Infobar$Push 1.000 Anim_FullFrames$In_Out$Essentials$In 2.200 Anim_FullFrames$In_Out$Header$In 1.900";
								break;
							}
						}
						switch(whatToProcess.split(",")[0]) {
						case "F1": case "Control_Shift_A"://battingCard
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Batting_Card$In 2.180 Anim_FullFrames$In_Out$Sponsor$In 2.420";
							break;
						case "Control_Shift_F1":
							if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
								previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Batting_Card$In 2.180 Anim_FullFrames$In_Out$Sponsor$In 2.420 "
										+ "Anim_FullFrames$In_Out$Extra_Info$In 2.500 Anim_Highlights$Batting_Card$Side1$" + caption.this_fullFramesGfx.batperformer_id + " 0.500";
							}
							else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
								previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Batting_Card$In 2.180 Anim_FullFrames$In_Out$Sponsor$In 2.420 "
										+ "Anim_FullFrames$In_Out$Extra_Info$In 2.500";
							}
							break;
						case "F2"://bowlingCard
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Bowling_Card$In 2.100 Anim_FullFrames$In_Out$Sponsor$In 2.420";
							break;
						case "Control_Shift_F2":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Bowling_Card$In 2.100 Anim_FullFrames$In_Out$Sponsor$In 2.420 "
									+ "Anim_FullFrames$In_Out$Extra_Info$In 2.500 Anim_Highlights$Bowling_Card$Side1$" + caption.this_fullFramesGfx.ballperformer_id + " 0.500";
							break;
							
						case "F4": //All Partnership
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership_List$In 2.180";
							if(whichside == 1 && caption.this_fullFramesGfx.whichSponsor != null && !caption.this_fullFramesGfx.whichSponsor.isEmpty()) {
								previewCommand = previewCommand + " Sponsor 0.900 Sponsor$In 0.900";
							}
							break;
						case "Control_F7":// Double Teams
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Sponsor$In 2.420 Anim_FullFrames$In_Out$Main$Teams$In 2.140 Change$Footer$Dynamic 0.500";
							break;
						case "Shift_T": //Playing XI
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LineUp_Image$In 1.960 Anim_FullFrames$In_Out$Sponsor$In 2.420 "
									+ "Change$Footer$Dynamic 0.500";
							break;
						case "Control_F1":// Photo ScoreCard
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Batting_Card_Image$In 2.040";
							break;
						case "Control_F10"://Manhattan
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Sponsor$In 2.420 Anim_FullFrames$In_Out$Main$Manhattan$In 2.500 Anin_Trophy$In_Out$In 3.000";
							break;
						case "Shift_F10"://WORMS
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Worms$In 2.500 Anim_FullFrames$In_Out$Worms$In$Runs 2.500 Anin_Trophy$In_Out$In 3.000"
									+ " Change$Footer$Dynamic 0.500";
							break;
						case "Control_F11": case "Shift_F11": //MATCH SUMMARY
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Summary$In 2.200 Change$Footer$Dynamic 0.500";
							switch (whatToProcess.split(",")[0]) {
							case "Control_F11":
								if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("trophy")) {
									previewCommand = previewCommand + " Anin_Trophy$In_Out$In 3.000";
								}
								break;
							}
							break;
						case "p": // PointsTable
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Group_Standings$In 1.540";
							break;
						case "Control_p": // PointsTable
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Standings$In 2.020 Change$Footer$Dynamic 0.500 Anin_Trophy$In_Out$In 3.000";
							break;
						case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8"://LeaderBoard Most - Runs,Wickets,Fours,Sixes 
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Leaderboard$In 1.940 Anim_FullFrames$In_Out$Extra_Info$In 2.500";
							break;
						case "Shift_P": case "Shift_Q"://PlayerProfile
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Profile$In 2.300";
//							if(Integer.valueOf(whatToProcess.split(",")[4]) > 0) {
//								previewCommand = previewCommand + " Profile_Highlight$Side1$" + whatToProcess.split(",")[4] + " 1.000";
//							}	
							break;
						case "Shift_K"://FFCurrPartnership
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Partnership$In 3.000";
							break;
						case "Alt_F9": case "Alt_F10": // Single Teams
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Team_Single$In 3.000";
							break;
						case "Shift_D": // target
							previewCommand = previewCommand + " Anim_Target 2.000 Anim_Target$In 2.000";
							break;
						case "Control_b": // target
							previewCommand = previewCommand + " In_At 2.140 In_At$In 2.140 In_At$In$Data 2.140";
							break;
						case "Alt_m": case "Alt_n":// target
							previewCommand = previewCommand + " Milestone 2.140 Milestone$In 2.140 Milestone$In$Data 2.140";
							break;
						case "Alt_F11":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan_Comparison$In 3.000";
							break;
						case "Alt_z":
							previewCommand = previewCommand + " Anim_Squad$In_Out 2.200 Anim_Squad$In_Out$In 2.200";
							if(!caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("role")) {
								previewCommand = previewCommand + " Anim_SquadDataChange 0.500";
							}
							break;
						case "r":
							previewCommand = previewCommand + " Anim_POTT$In_Out$In 2.140";
							break;
							
						}
					}
				} else if(whichside == 2) {
					if(whatToProcess.contains(",")) {
						switch(whatToProcess.split(",")[0]) {
						case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Shift_T": case "Control_p":
						case "Control_Shift_F1": case "Control_Shift_F2":
							previewCommand = previewCommand + "Change$Header 1.100 Change$Header$Change_In 1.100 Change$Header$Change_Out 0.500 ";
							if(whichGraphicOnScreen.contains(",")) {
								switch(whichGraphicOnScreen.split(",")[0]) {
								case "F1": case "Control_Shift_A":  
									previewCommand = previewCommand + "Change$Batting_Card 1.180 Change$Batting_Card$Change_Out 0.540 Change$Batting_Card$Change_In 1.180 ";
									break;
								case "F2":  
									previewCommand = previewCommand + "Change$Bowling_Card 1.100 Change$Bowling_Card$Change_Out 0.500 Change$Bowling_Card$Change_In 1.100 ";
									break;
									
								case "Control_Shift_F1":
									if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
										previewCommand = previewCommand + "Change$Batting_Card 1.180 Change$Batting_Card$Change_Out 0.540 Change$Batting_Card$Change_In 1.180 "
												+ "Change$Extra_Info 1.300 Change$Extra_Info$Change_Out 0.600 Change$Extra_Info$Change_In 1.300 Anim_Highlights 0.700 "
												+ "Anim_Highlights$Batting_Card 0.500 Anim_Highlights$Batting_Card$Side2 0.500 "
												+ "Anim_Highlights$Batting_Card$Side2$" + caption.this_fullFramesGfx.batperformer_id + " 0.500 ";
									}
									else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
										previewCommand = previewCommand + "Change$Batting_Card 1.180 Change$Batting_Card$Change_Out 0.540 Change$Batting_Card$Change_In 1.180 "
												+ "Change$Extra_Info 1.300 Change$Extra_Info$Change_Out 0.600 Change$Extra_Info$Change_In 1.300 ";
									}
									break;
									
								case "Control_Shift_F2":
									previewCommand = previewCommand + "Change$Bowling_Card 1.100 Change$Bowling_Card$Change_Out 0.500 Change$Bowling_Card$Change_In 1.100 "
											+ "Change$Extra_Info 1.300 Change$Extra_Info$Change_Out 0.600 Change$Extra_Info$Change_In 1.300 Anim_Highlights 0.700 "
											+ "Anim_Highlights$Bowling_Card 0.500 Anim_Highlights$Bowling_Card$Side2 0.500 "
											+ "Anim_Highlights$Bowling_Card$Side2$" + caption.this_fullFramesGfx.ballperformer_id + " 0.500 ";
									break;
									
								case "F4":
									previewCommand = previewCommand + "Change$Partnership_List 1.180 Change$Partnership_List$Change_Out 0.540 "
										+ "Change$Partnership_List$Change_In 1.180 ";
									break;
								case "Control_F11":
									previewCommand = previewCommand + "Change$Summary 1.200 Change$Summary$Change_Out 0.480 Change$Summary$Change_In 1.200 "
											+ "Change$Footer$Dynamic 0.500 ";
									if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("trophy")) {
										previewCommand = previewCommand + "Anin_Trophy$In_Out$In 3.000 ";
									}
									break;
								case "Shift_T":
									previewCommand = previewCommand + "Change$LineUp_Image 0.960 Change$LineUp_Image$Change_Out 0.440 Change$LineUp_Image$Change_In 0.960 "
											+ "Change$Sponsor 0.500 Change$Sponsor$Change_Out 0.300 Change$Sponsor$Change_In 0.500 ";
									break;
								case "Control_p":
									previewCommand = previewCommand + "Change$Standings 1.020 Change$Standings$Change_Out 0.460 Change$Standings$Change_In 1.020 "
											+ "Change$Footer$Dynamic 0.500 Anin_Trophy$In_Out$In 3.000 ";
									break;
								}
							}
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
								switch(whatToProcess.split(",")[0]) {
								case "F1": case "Control_Shift_A":  
									previewCommand = previewCommand + "Change$Batting_Card 1.180 Change$Batting_Card$Change_Out 0.540 Change$Batting_Card$Change_In 1.180 "
											+ "Change$Sponsor 0.500 Change$Sponsor$Change_Out 0.300 Change$Sponsor$Change_In 0.500 ";
									break;
								case "F2":  
									previewCommand = previewCommand + "Change$Bowling_Card 1.100 Change$Bowling_Card$Change_Out 0.500 Change$Bowling_Card$Change_In 1.100 "
											+ "Change$Sponsor 0.500 Change$Sponsor$Change_Out 0.300 Change$Sponsor$Change_In 0.500 ";
									break;
									
								case "Control_Shift_F1":
									if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("performer")) {
										previewCommand = previewCommand + "Change$Batting_Card 1.180 Change$Batting_Card$Change_Out 0.540 Change$Batting_Card$Change_In 1.180 "
												+ "Change$Sponsor 0.500 Change$Sponsor$Change_Out 0.300 Change$Sponsor$Change_In 0.500 "
												+ "Change$Extra_Info 1.300 Change$Extra_Info$Change_Out 0.600 Change$Extra_Info$Change_In 1.300 Anim_Highlights 0.700 "
												+ "Anim_Highlights$Batting_Card 0.500 Anim_Highlights$Batting_Card$Side2 0.500 "
												+ "Anim_Highlights$Batting_Card$Side2$" + caption.this_fullFramesGfx.batperformer_id + " 0.500 ";
									}
									else if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("partnership")) {
										previewCommand = previewCommand + "Change$Batting_Card 1.180 Change$Batting_Card$Change_Out 0.540 Change$Batting_Card$Change_In 1.180 "
												+ "Change$Sponsor 0.500 Change$Sponsor$Change_Out 0.300 Change$Sponsor$Change_In 0.500 "
												+ "Change$Extra_Info 1.300 Change$Extra_Info$Change_Out 0.600 Change$Extra_Info$Change_In 1.300 ";
									}
									break;
									
								case "Control_Shift_F2":
									previewCommand = previewCommand + "Change$Bowling_Card 1.100 Change$Bowling_Card$Change_Out 0.500 Change$Bowling_Card$Change_In 1.100 "
											+ "Change$Sponsor 0.500 Change$Sponsor$Change_Out 0.300 Change$Sponsor$Change_In 0.500 "
											+ "Change$Extra_Info 1.300 Change$Extra_Info$Change_Out 0.600 Change$Extra_Info$Change_In 1.300 Anim_Highlights 0.700 "
											+ "Anim_Highlights$Bowling_Card 0.500 Anim_Highlights$Bowling_Card$Side2 0.500 "
											+ "Anim_Highlights$Bowling_Card$Side2$" + caption.this_fullFramesGfx.ballperformer_id + " 0.500 ";
									break;
									
								case "F4":
									previewCommand = previewCommand + "Change$Partnership_List 1.180 Change$Partnership_List$Change_Out 0.540 "
										+ "Change$Partnership_List$Change_In 1.180 ";
									break;
								case "Control_F11":
									previewCommand = previewCommand + "Change$Summary 1.200 Change$Summary$Change_Out 0.480 Change$Summary$Change_In 1.200 "
											+ "Change$Footer$Dynamic 0.500 ";
									if(caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("trophy")) {
										previewCommand = previewCommand + "Anin_Trophy$In_Out$In 3.000 ";
									}
									break;
								case "Shift_T":
									previewCommand = previewCommand + "Change$LineUp_Image 0.960 Change$LineUp_Image$Change_Out 0.440 Change$LineUp_Image$Change_In 0.960 "
											+ "Change$Sponsor 0.500 Change$Sponsor$Change_Out 0.300 Change$Sponsor$Change_In 0.500 ";
									break;
								case "p":
									previewCommand = previewCommand + "Change$Group_Standings 1.040 Change$Group_Standings$Change_Out 0.624 "
											+ "Change$Group_Standings$Change_In 1.040 Anin_Trophy$In_Out$In 0.0 ";
									break;
								case "Control_p":
									previewCommand = previewCommand + "Change$Standings 1.020 Change$Standings$Change_Out 0.460 Change$Standings$Change_In 1.020 "
											+ "Change$Footer$Dynamic 0.500 Anin_Trophy$In_Out$In 3.000 ";
									break;
								}
							}
							previewCommand = previewCommand + "Change$Footer 0.700 Change$Footer$Change_In 0.700 Change$Footer$Change_Out 0.500";
							break;
						}
					}
				}
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FullFrames " + "C:/Temp/Preview.tga " + previewCommand + "\0", print_writer);
				break;
			case Constants.ICC_U19_2023:
				if(whichside == 1) {
					if(whatToProcess.contains(",")) {
						switch(whatToProcess.split(",")[0]) {
						case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F1": case "Shift_F10": case "Control_F11": case "Shift_F11": case "m": case "Control_m": case "p": case "Control_p":
						case "Shift_T": case "Control_d": case "Control_e": case "Control_F7": case "Control_F10": case "Alt_F9": case "Shift_K":
						case "z": case "x": case "c": case "v": case "Alt_F10": case "Shift_P": case "Shift_Q": case "Alt_F11": case "Control_z": case "Control_x":
							previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.140 Anim_FullFrames$In_Out$Header$In 1.800 "
								+ "Anim_FullFrames$In_Out$Footer$In 1.800";
							break;
						}
						switch(whatToProcess.split(",")[0]) {
						case "F1": case "Control_Shift_A"://battingCard
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Batting_Card$In 1.860";
							break;
						case "F2"://bowlingCard
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Bowling_Card$In 1.780";
							break;
						case "F4": //All Partnership
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership_List$In 1.820";
							if(whichside == 1 && caption.this_fullFramesGfx.whichSponsor != null && !caption.this_fullFramesGfx.whichSponsor.isEmpty()) {
								previewCommand = previewCommand + " Sponsor 0.900 Sponsor$In 0.900";
							}
							break;
						case "Control_F7":// Double Teams
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Teams$In 1.860";
							break;
						case "Shift_T": //Playing XI
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LineUp_Image$In 2.040";
							break;
						case "Control_F1":// Photo ScoreCard
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Batting_Card_Image$In 2.040";
							break;
						case "Control_F10"://Manhattan
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan$In 2.900";
							break;
						case "Shift_F10"://WORMS
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Worm$In 2.440 Anim_FullFrames$In_Out$Main$Worm$In$Runs 2.440";
							break;
						case "Control_F11": case "Shift_F11": //MATCH SUMMARY
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Summary$In 1.820";
							break;
						case "p": // PointsTable
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Group_Standings$In 1.540";
							break;
						case "Control_p": // PointsTable
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Standings$In 1.620";
							break;
						case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": //LeaderBoard Most - Runs,Wickets,Fours,Sixes 
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Leader_Board$In 2.300";
							previewCommand = previewCommand + " LeaderBoardHighlight$Side1$Player"+whatToProcess.split(",")[2].split("_")[0] + " 2.700";
							break;
						case "m"://Match id	
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Ident$In 1.920 Base_Gradient 0.500";
							break;
						case "Control_m": //MATCH PROMO
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Ident$In 1.920";
							break;
						case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q"://PlayerProfile
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Profile$In 2.300";
							if(Integer.valueOf(whatToProcess.split(",")[4]) > 0) {
								if(Integer.valueOf(whatToProcess.split(",")[4]) == 5) {
									previewCommand = previewCommand + " Profile_Highlight$Side1$7" + " 1.000";
								}else {
									previewCommand = previewCommand + " Profile_Highlight$Side1$" + whatToProcess.split(",")[4] + " 1.000";
								}
							}	
							break;
						case "Shift_K"://FFCurrPartnership
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership$In 3.000 Base_Gradient 0.500 Sponsor 0.900 Sponsor$In 0.900 Sponsor$Out 1.200";
							break;
						case "Alt_F9": case "Alt_F10": // Single Teams
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Team_Single$In 3.000";
							break;
						case "Shift_D": // target
							previewCommand = previewCommand + " Target 2.100 Target$In 2.100 Target$In$Data 2.100";
							break;
						case "Control_b": // target
							previewCommand = previewCommand + " In_At 2.140 In_At$In 2.140 In_At$In$Data 2.140";
							break;
						case "Alt_m": case "Alt_n":// target
							previewCommand = previewCommand + " Milestone 2.140 Milestone$In 2.140 Milestone$In$Data 2.140";
							break;
						case "Alt_F11":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan_Comparison$In 3.000";
							break;
						case "Alt_z":
							previewCommand = previewCommand + " Anim_Squad$In_Out 2.200 Anim_Squad$In_Out$In 2.200";
							if(!caption.this_fullFramesGfx.WhichType.equalsIgnoreCase("role")) {
								previewCommand = previewCommand + " Anim_SquadDataChange 0.500";
							}
							break;
						case "r":
							previewCommand = previewCommand + " Anim_POTT$In_Out$In 2.140";
							break;
							
						}
					}
				} else if(whichside == 2) {
					if(whatToProcess.contains(",")) {
						switch(whatToProcess.split(",")[0]) {
						case "F1": case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Shift_T": case "p": case "Control_p":
						case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
							previewCommand = previewCommand + " Change$Header 1.320  Change$Header$Change_In 1.320 Change$Header$Change_Out 0.420";
							if(whichGraphicOnScreen.contains(",")) {
								switch(whichGraphicOnScreen.split(",")[0]) {
								case "F1": case "Control_Shift_A":  
									previewCommand = previewCommand + " Change$Batting_Card 1.380 Change$Batting_Card$Change_Out 0.880 Change$Batting_Card$Change_In 1.380";
									break;
								case "F2":  
									previewCommand = previewCommand + " Change$Bowling_Card 1.300 Change$Bowling_Card$Change_Out 0.840 Change$Bowling_Card$Change_In 1.300";
									break;
								case "F4":
									previewCommand = previewCommand + " Change$Partnership_List 1.360 Change$Partnership_List$Change_Out 0.880 "
										+ "Change$Partnership_List$Change_In 1.360";
									break;
								case "Control_F11":
									previewCommand = previewCommand + " Change$Summary 1.340 Change$Summary$Change_Out 0.720 Change$Summary$Change_In 1.340";
									break;
								case "Shift_T":
									previewCommand = previewCommand + " Change$LineUp_Image 1.560 Change$LineUp_Image$Change_Out 0.500 Change$LineUp_Image$Change_In 1.560";
									break;
								case "p":
									previewCommand = previewCommand + " Change$Group_Standings 1.040 Change$Group_Standings$Change_Out 0.624 Change$Group_Standings$Change_In 1.040";
									break;
								case "Control_p":
									previewCommand = previewCommand + " Change$Standings 1.120 Change$Standings$Change_Out 0.624 Change$Standings$Change_In 1.120";
									break;
								case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": //LeaderBoard Most - Runs,Wickets,Fours,Sixes 
									previewCommand = previewCommand + " Change$Leader_Board 2.800 Change$Leader_Board$Change_Out 0.600 Change$Leader_Board$Change_In 2.800";
									previewCommand = previewCommand + " LeaderBoardHighlight$Side2$Player"+whatToProcess.split(",")[2].split("_")[0] + " 2.700";
									break;
								}
							}
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
								switch(whatToProcess.split(",")[0]) {
								case "F1": case "Control_Shift_A": case "F2": case "F4":
									previewCommand = previewCommand + " Header_Shrink 0.000 Header_Shrink$In 0.000";
									switch(whatToProcess.split(",")[0]) {
									case "F4":
										if(caption.this_fullFramesGfx.whichSponsor != null && !caption.this_fullFramesGfx.whichSponsor.isEmpty()) {
											previewCommand = previewCommand + " Change_Sponsor 1.000 Change_Sponsor$Change_Our 0.500 Change_Sponsor$Change_In 1.000";
										}
										break;
									}
									break;
								case "Control_F11": case "p": case "Control_p":
									previewCommand = previewCommand + " Header_Shrink 0.500 Header_Shrink$In 0.500";
									break;
								}
								switch(whatToProcess.split(",")[0]) {
								case "F1": case "Control_Shift_A": 
									previewCommand = previewCommand + " Change$Batting_Card 1.380 Change$Batting_Card$Change_Out 0.880 Change$Batting_Card$Change_In 1.380";
									break;
								case "F2":
									previewCommand = previewCommand + " Change$Bowling_Card 1.300 Change$Bowling_Card$Change_Out 0.840 Change$Bowling_Card$Change_In 1.300";
									break;
								case "F4":
									previewCommand = previewCommand + " Change$Partnership_List 1.360 Change$Partnership_List$Change_Out 0.880 Change$Partnership_List$Change_In 1.360";
									break;
								case "Control_F11":
									previewCommand = previewCommand + " Change$Summary 1.340 Change$Summary$Change_Out 0.720 Change$Summary$Change_In 1.340";
									break;
								case "p":
									previewCommand = previewCommand + " Change$Group_Standings 1.040 Change$Group_Standings$Change_Out 0.624 Change$Group_Standings$Change_In 1.040";
									break;
								case "Control_p":
									previewCommand = previewCommand + " Change$Standings 1.120 Change$Standings$Change_Out 0.624 Change$Standings$Change_In 1.120";
									break;
								}
							}
							previewCommand = previewCommand + " Change$Footer 1.700 Change$Footer$Change_In 1.700 Change$Footer$Chnage_Out 0.580";
//							System.out.println("Number of rows : " + caption.this_fullFramesGfx.numberOfRows);
//							System.out.println("L Number of rows : " + lastNumberOfRows);
//							if(caption.this_fullFramesGfx.numberOfRows != lastNumberOfRows) {
//								previewCommand = previewCommand + " ConcussExtend_Y 0.500 ConcussExtend_Y$In 0.500";
//							}
							break;
						}
					}
				}
//				System.out.println("previewCommand = " + previewCommand);
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FullFrames "
				    	+ "C:/Temp/Preview.tga " + previewCommand + " \0", print_writer);
				break;
			case Constants.LEGENDS: case Constants.NPL: case Constants.APL: case Constants.MPL: case Constants.ISPL:
				processLegendFullFramesPreview(whatToProcess,print_writer,whichside,config,whichGraphicOnScreen);
				break;
			}
		}
	}

	public void processL3Preview(String whatToProcess, List<PrintWriter> print_writer, int whichside, Configuration config,MatchAllData match) throws InterruptedException
	{
		System.out.println("coming here for preivew");
		if(config.getPreview().equalsIgnoreCase("WITH_PREVIEW")) {
			String previewCommands = "";
			switch (config.getBroadcaster().toUpperCase()) {
			case Constants.VIDARBHA:
				if(whichside == 1) {
					switch(whatToProcess.split(",")[0]) {
					case "Shift_I":
						previewCommands = "LT_Impact 2.200 LT_Impact$Essentials 2.200 LT_Impact$Essentials$In 1.980 LT_Impact$Row 2.200"
								+ " LT_Impact$Row$In 1.640";
						break;
					case "9": case "Alt_Shift_Q":
						previewCommands = "Plotter 1.000";
						break;
					case "Control_Shift_O":
						previewCommands = "LT_PlayingXI 2.200 LT_PlayingXI$Essentials 2.200 LT_PlayingXI$Essentials$In 2.100 LT_PlayingXI$Row 2.200"
								+ " LT_PlayingXI$Row$In 1.400";
						break;
					case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2":
					case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_o":
					case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
					case "Control_g": case "Control_h": case "Control_p": case "Control_F6": case "Shift_F6": case "Control_s":
					case "Alt_d": case "Control_f": case "l": case "a": case "Alt_F1": case "Alt_F2":case "Shift_E": case "Alt_Shift_L":
					case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_F8": case "F8": case "F10": case "j": 
					case "Alt_a": case "Alt_s":  case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i":
					case "Alt_Shift_F3":
					case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
					case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
						previewCommands = "Anim_Infobar$Push 0.560 "
								+ "Anim_LtChange$Top_Header 2.680  Anim_LtChange$Top_Header$In 1.920 "
								+ "anim_Lower_Third$Essentials 3.200 anim_Lower_Third$Essentials$In 1.900 "
								+ "anim_Lower_Third$Row 3.000 anim_Lower_Third$Row$In 1.900 "
								+ "Anim_LtChange$Lt_X_Position 1.140 Anim_LtChange$Lt_X_Position$MoveForShrink 1.820 ";
						break;
					 case "Alt_q":
						 previewCommands = "Anim_Infobar$Push 0.500 anim_POTT$In 1.400";
						break;
					case "q": case "Control_q":// Boundary L3rd
						previewCommands = "Anim_Infobar$Push 0.500 anim_Boundary_LT$Essentials 2.200 anim_Boundary_LT$Essentials$In 1.400 "
							+ "anim_Boundary_LT$Row 2.160 anim_Boundary_LT$Row$In 0.620";
						break;
					case "Shift_F7": case "Control_Shift_F9":
						previewCommands = "Anim_Infobar$Push 0.560 "
								+ "Anim_Image_LT$Top_Header 2.680  Anim_Image_LT$Top_Header$In 1.920 "
								+ "Anim_Image_LT$Essentials 3.200 Anim_Image_LT$Essentials$In 1.900 "
								+ "Anim_Image_LT$Row 3.000 Anim_Image_LT$Row$In 1.900 "
								+ "Anim_Image_LtChange$Lt_X_Position 1.140 Anim_Image_LtChange$Lt_X_Position$MoveForShrink 1.820 ";
						break;	
					}
				}else if(whichside == 2) {
					switch (whatToProcess.split(",")[0]) {
					case "F5": case "F6": case "F7": case "F9": case "F11": case "l": case "a": case "Control_F2": case "F8": case "F10":
					case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_o": case "Alt_F1": case "Alt_F2":
					case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":case "Shift_E":
					case "Control_g": case "Control_h": case "Control_p": case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f": 
					case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_F8":  case "j": case "Alt_a": case "Alt_s": case "Alt_w":
					case "Control_j": case "Alt_i": case "Alt_j":  case "b": case "Control_i": case "Alt_Shift_L": case "Alt_Shift_F3":
					//case "n":	
					case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
					case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
						previewCommands = previewCommands + " Anim_LtChange$Sublines 0.780 "
								+ "Anim_LtChange$Topline 0.900 Anim_LtChange$Lt_Y_Scale 0.900 "
								+ "Anim_LtChange$Lt_X_Position 0.900";
						break;
					case "q": case "Control_q":
						previewCommands = previewCommands + " Anim_Boundary_LtChange$Flag 1.300 Anim_Boundary_LtChange$Sublines 1.200 "
							+ "Anim_Boundary_LtChange$Topline 0.900 Anim_Boundary_LtChange$Lt_Position 0.940 Anim_Boundary_LtChange$HeaderDynamic 1.223 "
							+ "Anim_Boundary_LtChange$HeaderDynamic$Change_In 1.223 Anim_Boundary_LtChange$HeaderDynamic$Change_Out 0.600";
						break;
					case "Shift_F7": case "Control_Shift_F9":
						previewCommands = previewCommands + " Anim_Image_LtChange$Flag 1.300 Anim_Image_LtChange$Sublines 1.240 "
								+ "Anim_Image_LtChange$Topline 0.900 Anim_Image_LtChange$Lt_Position 0.940 Anim_Image_LtChange$HeaderDynamic 1.180 "
								+ "Anim_Image_LtChange$HeaderDynamic$Change_In 1.180 Anim_Image_LtChange$HeaderDynamic$Change_Out 0.560";
						break;	
					}
				}
				
				switch(whatToProcess.split(",")[0]) {
				 case "Alt_Shift_Q":
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays_SuperOver "
						    	+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FieldDimesnsion "
						    	+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
					}
					break;
				 case "9":
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays_SuperOver "
						    	+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FieldPlotter_LLC "
						    	+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
					}	
					break;
				default:
					if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays_SuperOver "
						    	+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
					}else {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays "
						    	+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
					}
					break;
				}
				
			    break;	
			case Constants.T20_MUMBAI:
				if(whichside == 1) {
					System.out.println("whatToProcess - " + whatToProcess);
					switch(whatToProcess.split(",")[0]) {
					case "Control_Shift_M": case "Control_Shift_L":
						previewCommands = "anim_Infobar$Push 0.500 anim_LT_Ident 2.900 anim_LT_Ident$InOut 2.900 anim_LT_Ident$InOut$In 2.680";
						break;
					case "Control_Shift_O":
						previewCommands = "anim_Lt_BattingCard$InOut$In 2.080";
						break;
					case "Shift_I":
						previewCommands = "anim_Infobar$Push 0.500 anim_Impact$In_Out$In 2.000";
						break;
					case "Control_F6": case "F6": case "Shift_F6":
						previewCommands = "anim_Infobar$Push 0.500 anim_LT_HowOut 2.080 anim_LT_HowOut$InOut 2.080 anim_LT_HowOut$InOut$In 2.080";
						break;
					case "F8": case "Alt_F8": case "F10": case "Control_F9":case "Control_F5":
						previewCommands = "anim_Infobar$Push 0.500 anim_LT_NameSuper 2.080 anim_LT_NameSuper$InOut 2.080 anim_LT_NameSuper$InOut$Essentials 2.080 "
								+ "anim_LT_NameSuper$InOut$Essentials$In 2.080 anim_LT_NameSuper$InOut$Colours 2.080 anim_LT_NameSuper$InOut$Colours$In 1.600 "
								+ "anim_LT_NameSuper$InOut$Logo 2.080 anim_LT_NameSuper$InOut$Logo$In 1.800 anim_LT_NameSuper$InOut$Name 2.080 anim_LT_NameSuper$InOut$Name$In 1.700 "
								+ "anim_LT_NameSuper$InOut$Info 2.080 anim_LT_NameSuper$InOut$Info$In 2.080";
						break;
					case "F5": case "F9": case "l": case "Shift_F5": case "Shift_F9": case "Control_h": case "Alt_F12":
					case "F7": case "F11": case "Control_a": case "q": case "u": case "Control_q": case "Shift_F3": case "Control_F3":
					case "Shift_B": case "Alt_Shift_F3":case "Alt_Shift_O": case "Control_s": case "Control_f": case "Control_i":
					case "d": case "e":case "Alt_F1": case "Alt_F2":
						previewCommands = "anim_Infobar$Push 0.500 anim_LowerThird 2.080 anim_LowerThird$InOut 2.080 anim_LowerThird$InOut$Essentials 2.080 "
								+ "anim_LowerThird$InOut$Essentials$In 2.080 anim_LowerThird$InOut$Colours 2.080 anim_LowerThird$InOut$Colours$In 1.600 "
								+ "anim_LowerThird$InOut$Logo 2.080 anim_LowerThird$InOut$Logo$In 1.800 anim_LowerThird$InOut$TopLine 2.080 anim_LowerThird$InOut$TopLine$In 1.700 "
								+ "anim_LowerThird$InOut$Subline 2.080 anim_LowerThird$InOut$Subline$In 2.080";
						break;
					 case "j":
						previewCommands = "SrinkInfobar 1.180 PositionForInfobar$ForShrinK 0.0 NameSuper 1.640";
						break;
					}
				}else if(whichside == 2) {
					switch(whatToProcess.split(",")[0]) {
					case "F5": case "F9": case "l": case "Shift_F5": case "Shift_F9": case "Control_h": case "Alt_F12":
					case "F7": case "F11": case "Control_a": case "q": case "u": case "Control_q": case "Shift_F3": case "Control_F3":
					case "Shift_B": case "Alt_Shift_F3":case "Alt_Shift_O": case "Control_s": case "Control_f": case "Control_i":
					case "d": case "e":case "Alt_F1": case "Alt_F2":
						previewCommands = "ChangeLowerThird 2.080 ChangeLowerThird$ExpandForSubline 2.080 "
								+ "ChangeLowerThird$InOut$Colours 2.080 ChangeLowerThird$Logo 2.080 ChangeLowerThird$TopLine 2.080 ChangeLowerThird$Subline 2.080 ";
						break;
					case "F8": case "Alt_F8": case "F10": case "Control_F9":case "Control_F5":
						previewCommands = "Change_LT_NameSuper 0.700 Change_LT_NameSuper$Name 0.700 "
								+ "Change_LT_NameSuper$InOut$Colours 0.700 Change_LT_NameSuper$Logo 0.700 Change_LT_NameSuper$Info 0.700";
						break;
					case "j": 
						previewCommands = "NameSuperChange 1.640";
						break;

					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/gfx_Overlays "
				    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
				break;
			case Constants.NPL:  case Constants.MPL: case Constants.APL:
				if(whichside == 1) {
					switch(whatToProcess.split(",")[0]) {
					case "9": case "Alt_Shift_Q":
						previewCommands = "Plotter 1.000";
						break;
					case "Control_Shift_F10":
						previewCommands = "Anim_Infobar$Push 0.500 LT_Manhattan$In_Out 1.420 LT_Manhattan$In 3.120 "
								+ "LT_Manhattan$In$LOGO 0.980 LT_Manhattan$In$BASE 0.840 LT_Manhattan$In$DataIn 1.636 LT_Manhattan$In$DataIn1 1.016";
						
						TimeUnit.MILLISECONDS.sleep(200);
						
						previewCommands = "Anim_Infobar$Push 0.500 LT_Manhattan$In_Out 1.420 LT_Manhattan$In 3.120 "
								+ "LT_Manhattan$In$LOGO 0.980 LT_Manhattan$In$BASE 0.840 LT_Manhattan$In$DataIn 1.636 LT_Manhattan$In$DataIn1 1.016";
						break;
					case "Alt_Shift_F3":
						previewCommands = "Anim_Infobar$Push 0.500 LT_PhaseComparison$In_Out 1.460 LT_PhaseComparison$In_Out$In 1.460";
						break;
					case "Control_Shift_B":
						previewCommands = "Anim_Infobar$Push 0.500 LT_NextToBat$In_Out 1.420 LT_NextToBat$In_Out$In 1.140 LT_NextToBat$In_Out$In$BASE 1.040 LT_NextToBat$In_Out$In$LOGO 1.000"
								+ " LT_NextToBat$In_Out$In$BOTTOM_DATA 1.140";
						break;
					case "Control_Shift_O":
						previewCommands = "Anim_Infobar$Push 0.500 Anim_Infobar$Small 0.820 LT_PlayingXI$In_Out 1.420 LT_PlayingXI$In_Out$In 1.380 LT_PlayingXI$In_Out$In$BASE 1.566 "
								+ "LT_PlayingXI$In_Out$In$LOGO 1.680 LT_PlayingXI$In_Out$In$BOTTOM_DATA 1.460";
						break;
					case "Control_6":
						previewCommands = "Anim_Infobar$Push 0.500 Anim_Infobar$Small 0.820 LT_Weather$In 1.320 LT_Weather$In$HEADER 1.320 LT_Weather$In$BASE 1.040 "
								+ "LT_Weather$In$LOGO 1.100 LT_Weather$In$IMPACT 1.120 ";
						break;
					case "Alt_Shift_F5":
						previewCommands = "Anim_Infobar$Push 0.500 Anim_Infobar$Small 0.820 LT_Pointers$In 1.320 LT_Pointers$In$HEADER 1.320 LT_Pointers$In$BASE 0.940 "
								+ "LT_Pointers$In$LOGO 1.100 ";
						break;
					case "Shift_I":
						previewCommands = "Anim_Infobar$Push 0.500 Anim_Infobar$Small 0.820 Lower_Third$In_Out 1.460 Lower_Third$In_Out$In 1.440 Lower_Third$In_Out$In$BASE 1.040 "
								+ "Lower_Third$In_Out$In$LOGO 1.100 Lower_Third$In_Out$In$HEADER 1.320 Lower_Third$In_Out$In$RIGHT_DATA 1.440";
						break;
					case "F8": case "Alt_F8": case "F10": case "F9": case "d": case "e": case "F7": case "F11": case "Control_s": case "Control_f":
					case "Control_F5": case "Control_F6": case "Shift_F6": case "F6": case "Control_F9": case "F5": case "Control_Shift_Q":
					case "Control_a": case "Shift_F3": case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h": case "Alt_Shift_O":
						previewCommands = "Anim_Infobar$Push 0.500 Anim_Infobar$Small 0.820 Lower_Third$In_Out 1.460 Lower_Third$In_Out$In 1.440 Lower_Third$In_Out$In$BASE 1.040 "
								+ "Lower_Third$In_Out$In$LOGO 1.100 Lower_Third$In_Out$In$HEADER 1.320 Lower_Third$In_Out$In$BOTTOM_DATA 1.320";
						
						if(whatToProcess.split(",")[0].equalsIgnoreCase("F10") && caption.this_lowerThirdGfx.setPriceMoney) {
							previewCommands = previewCommands + " Lower_Third$In_Out$In$Prize_Head 1.160";
						}
						break;
					case "Control_Shift_M": case "Control_Shift_L":
						previewCommands = "Anim_Infobar$Push 0.500 LT_MatchID$In_Out 1.420 LT_MatchID$In_Out$In 1.400 LT_MatchID$In_Out$In$BASE 1.200 LT_MatchID$In_Out$In$LOGO 1.100"
								+ " LT_MatchID$In_Out$In$HEADER 1.320 LT_MatchID$In_Out$In$BOTTOM_DATA 1.320 LT_MatchID$In_Out$In$SUB_DATA 1.400";
						break;
					case "Control_F3":
						previewCommands = "Anim_Infobar$Push 0.500 LT_Comparison$In_Out 3.060 LT_Comparison$In_Out$In 3.020 LT_Comparison$In_Out$In$BASE 1.200 LT_Comparison$In_Out$In$LOGO 3.020"
								+ " LT_Comparison$In_Out$In$HEADER 1.320 LT_Comparison$In_Out$In$BOTTOM_DATA 1.320 LT_Comparison$In_Out$In$SUB_DATA 1.400";
						break;
					}
					switch(whatToProcess.split(",")[0]) {
					case "Control_F5": case "Control_F6": case "Shift_F6": case "F6": case "Control_F9": case "F5": case "F9": case "F7":
					case "Control_a": case "Shift_F3": case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h":
					case "F11": case "Control_s": case "Control_f":	case "Control_Shift_Q": case "Alt_Shift_O":
						previewCommands = previewCommands +" Lower_Third$In_Out$In$RIGHT_DATA 1.440";
						break;
					}
					switch (whatToProcess.split(",")[0]) {
					case "Shift_F3": case "Control_a": case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "F5": case "F7": case "F11":
					case "Control_s": case "Control_f": case "Control_h": case "Alt_Shift_O":
						previewCommands = previewCommands +" Lower_Third$In_Out$In$SUB_DATA 1.400";
						break;
					}
				}else {
					switch(whatToProcess.split(",")[0]) {
					case "Control_Shift_O":
						previewCommands = "LT_PlayingXII$Change_In$BASE 1.200 LT_PlayingXII$Change_In$LOGO 1.000"
								+ " LT_PlayingXII$Change_In$BOTTOM_DATA 1.380 LT_PlayingXII$Change_Out$BASE 0.820 LT_PlayingXII$Change_Out$LOGO 0.427"
								+ " LT_PlayingXII$Change_Out$BOTTOM_DATA 0.415";
						break;
					case "F8": case "Alt_F8": case "F10": case "F9": case "d": case "e": case "F7": case "F11": case "Control_s": case "Control_f":
					case "Control_F5": case "Control_F6": case "Shift_F6": case "F6": case "Control_F9": case "F5": case "Control_Shift_Q":
					case "Control_a": case "Shift_F3": case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h": case "Shift_I":
						
						if(caption.this_lowerThirdGfx.isPrev_impact() == false) {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Lower_Third$Change$Change_Out SHOW 0.0 \0", print_writer);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Lower_Third$Change$Change_Out$IMPACT SHOW 0.320 \0", print_writer);

						}else {
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Lower_Third$Change$Change_Out SHOW 0.0 \0", print_writer);
							
						}
						
						previewCommands = "Lower_Third$Change_In$BASE 2.100 Lower_Third$Change_In$LOGO 2.160 Lower_Third$Change_In$HEADER 2.380"
								+ " Lower_Third$Change_In$BOTTOM_DATA 2.380 Lower_Third$Change_Out$BASE 1.020 Lower_Third$Change_Out$LOGO 0.481"
								+ " Lower_Third$Change_Out$HEADER 0.495 Lower_Third$Change_Out$BOTTOM_DATA 0.495";
						break;
					case "Control_Shift_M": case "Control_Shift_L":
						previewCommands = "LT_MatchID$Change 1.400 LT_MatchID$Change$Change_Out 1.020 LT_MatchID$Change$Change_Out$BOTTOM_DATA 0.495"
								+ " LT_MatchID$Change$Change_In$BOTTOM_DATA 1.320";
						break;
					}
					
					switch(whatToProcess.split(",")[0]) {
					case "Control_F5": case "Control_F6": case "Shift_F6": case "F6": case "Control_F9": case "F5": case "F9": case "F7": 
					case "Control_a": case "Shift_F3": case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_h":
					case "F11": case "Control_s": case "Control_f":	case "Control_Shift_Q":
						previewCommands = previewCommands +" Lower_Third$Change_Out$RIGHT_DATA 0.425 Lower_Third$Change_In$RIGHT_DATA 2.500";
						break;
					}
					switch (whatToProcess.split(",")[0]) {
					case "Shift_F3": case "Control_a": case "u": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "F5": case "F7": case "F11":
					case "Control_s": case "Control_f": case "Control_h":
						previewCommands = previewCommands +" Lower_Third$Change_Out$SUB_DATA 0.552 Lower_Third$Change_In$SUB_DATA 2.460";
						break;
					}
				}
				switch(whatToProcess.split(",")[0]) {
				case "Alt_Shift_Q":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FieldDimesnsion "
					    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
					break;
				case "9":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FieldPlotter_LLC "
					    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
					break;
				default:
					switch (config.getBroadcaster().toUpperCase()) {
					case Constants.MPL:
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays_New "
						    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
						break;
					default :
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays "
						    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
						break;
					}
					break;
				}
				
				break;
			case Constants.BENGAL_T20:
				if(whichside == 1) {
					switch(whatToProcess.split(",")[0]) {
					case "9": case "Alt_Shift_Q":
						previewCommands = "Plotter 1.000";
					case "Control_Shift_M": case "Control_Shift_L":
						previewCommands = "anim_Infobar$Push 1.000 anim_Ident 1.220 anim_Ident$In 1.220";
						break;
					case "F5": 
						if(whatToProcess.split(",")[3].toUpperCase().equalsIgnoreCase("SPONSOR")) {
							previewCommands = "anim_BatsmanScore_LT 2.200 anim_BatsmanScore_LT$In 1.500 anim_Infobar$Push 1.000";
						}else {
							previewCommands = "anim_Lower_Third 1.500 anim_Lower_Third$Essentials 1.500 anim_Lower_Third$Essentials$In 1.500 anim_Infobar$Push 1.000";
						}
						break;
					case "F9":
						if(whatToProcess.split(",")[3].toUpperCase().equalsIgnoreCase("SPONSOR")) {
							previewCommands = "anim_BowlerFigure_LT 2.200 anim_BowlerFigure_LT$In 1.200 anim_Infobar$Push 1.000";
						}else {
							previewCommands = "anim_Lower_Third 1.500 anim_Lower_Third$Essentials 1.500 anim_Lower_Third$Essentials$In 1.500 anim_Infobar$Push 1.000";
						}
						break;
					case "Control_a":
						previewCommands = "anim_Projected_LT 2.200 anim_Projected_LT$Essentials 1.500 anim_Projected_LT$Essentials$In 1.500 anim_Infobar$Push 1.000";
						break;
					case "Shift_F3":
						previewCommands = "anim_Fall_Of_Wickets 1.500 anim_Fall_Of_Wickets$Essentials 1.500 anim_Fall_Of_Wickets$Essentials$In 1.500 anim_Infobar$Push 1.000";
						break;
					case "Control_Shift_F10":
						previewCommands = "anim_Infobar$Manhattan 1.500 anim_Infobar$Manhattan$In_Out 1.500 anim_Infobar$Manhattan$In_Out$Main 1.500 anim_Infobar$Manhattan$In_Out$Main$Manhattan 1.500"
								+ " anim_Infobar$Manhattan$In_Out$Main$Manhattan$In 1.500 anim_Infobar$Manhattan$In_Out$Main$Manhattan$Out 0.000";
						break;	
					case "Alt_Shift_C":
						previewCommands = "anim_Captain_LT 1.200 anim_Captain_LT$In 1.200 anim_Infobar$Push 1.000";
						break;	
					case "Shift_I":
						previewCommands = "anim_Substitute 1.600 anim_Substitute$In_Out 1.600 anim_Substitute$In_Out$Base 1.600 anim_Substitute$In_Out$Sub 1.600 anim_Substitute$In_Out$Impact 1.600"
								+ " anim_Substitute$In_Out$Base$In 1.000 anim_Substitute$In_Out$Sub$In 1.620 anim_Substitute$In_Out$Impact$In 1.600";
						break;	
					case "Control_Shift_B":
						previewCommands = "anim_Next_To_Bat_LT 1.500 anim_Next_To_Bat_LT$Essentials 1.500 anim_Next_To_Bat_LT$Essentials$In 1.500 anim_Infobar$Push 1.000";
						break;
					case "F6": case "Control_F6": case "Shift_F6": case "F8": case "Alt_F8": case "F10": case "d": case "e": case "u": case "Shift_B":
					case "Shift_F5": case "Alt_k": case "Shift_F9": case "Control_F3": case "Control_F5": case "Control_F9": case "Alt_F12": case "Control_s": case "Control_f": case "F7": case "F11":
					case "Alt_Shift_F3": case "Control_h":
						previewCommands = "anim_Lower_Third 1.500 anim_Lower_Third$Essentials 1.500 anim_Lower_Third$Essentials$In 1.500 anim_Infobar$Push 1.000";
						break;
					}
				}else if(whichside == 2){
					switch (whatToProcess.split(",")[0]) {
					case "F6": case "Control_F6": case "Shift_F6": case "F5": case "F9": case "F8": case "Alt_F8": case "F10": case "d": case "e": case "u":
					case "Shift_F5": case "Alt_k": case "Shift_F9": case "Control_F3": case "Control_F5": case "Control_F9":  case "Alt_F12": case "Control_s": case "Control_f":case "F7": case "F11":
					case "Alt_Shift_F3": case "Control_h":
						previewCommands =  "Anim_LtChange$Badge 1.000 Anim_LtChange$Sublines 1.200 Anim_LtChange 1.200 Anim_LtChange$Topline 1.000 Anim_LtChange$Dynamic 0.560 "
								+ "Anim_LtChange$Dynamic$Change_In 0.560 Anim_LtChange$Dynamic$Change_Out 0.560";
						break;
					}
				}
				
				switch(whatToProcess.split(",")[0]) {
				case "Alt_Shift_Q":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FieldDimesnsion "
					    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
					break;
				case "9":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FieldPlotter_LLC "
					    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
					break;
				default:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays "
					    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
					break;
				}
				break;
			case Constants.ICC_U19_2023:
				if(whichside == 1) {
					switch(whatToProcess.split(",")[0]) {
					case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2":
					case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k":
					case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
					case "Control_g": case "Control_h": case "Control_p": case "Control_F6": case "Shift_F6": case "Control_s":
					case "Alt_d": case "Control_f": case "l": case "n": case "a": case "Alt_F1": case "Alt_F2":case "Shift_E":
					case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "b":
					case "Alt_j": case "Control_i": case "Alt_Shift_L": case "Shift_B": case "Control_Shift_F":
					case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
					case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
						previewCommands = "Anim_Infobar$Push 0.500 Anim_LtChange$HeaderDynamic 1.200 anim_Lower_Third$Essentials 2.200 anim_Lower_Third$Essentials$In 1.400 "
							+ "anim_Lower_Third$Row 2.160 anim_Lower_Third$Row$In 0.620";
						break;
					 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s": // Name super L3rd
						previewCommands = "Anim_Infobar$Push 0.500 anim_NameSupers$In 1.400";
						break;
					 case "Alt_q":
						 previewCommands = "Anim_Infobar$Push 0.500 anim_POTT$In 1.400";
						break;
					case "q": case "Control_q":// Boundary L3rd
						previewCommands = "Anim_Infobar$Push 0.500 anim_Boundary_LT$Essentials 2.200 anim_Boundary_LT$Essentials$In 1.400 "
							+ "anim_Boundary_LT$Row 2.160 anim_Boundary_LT$Row$In 0.620";
						break;
					case "Shift_F7":
						previewCommands = "Anim_Infobar$Push 0.500 Anim_Image_LtChange$HeaderDynamic 1.200 Anim_Image_LT$Essentials 2.200 Anim_Image_LT$Essentials$In 1.400 "
								+ "Anim_Image_LT$Row 2.160 Anim_Image_LT$Row$In 0.620";
						break;
					}
				}else if(whichside == 2) {
					switch (whatToProcess.split(",")[0]) {
					case "F5": case "F6": case "F7": case "F9": case "F11": case "l": case "n": case "a": case "Control_F2":
					case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": case "Alt_k": case "Alt_F1": case "Alt_F2":
					case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":case "Shift_E":
					case "Control_g": case "Control_h": case "Control_p": case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f": 
					case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_w": case "Control_j": case "Alt_i": case "b":
					case "Alt_j": case "Control_i": case "Alt_Shift_L":  case "Shift_B": case "Control_Shift_F":
					case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H":
					case "Control_u": case "Shift_G": case "Shift_W":case "Control_Shift_X":
						previewCommands = previewCommands + " Anim_LtChange$Flag 1.300 Anim_LtChange$Sublines 1.240 "
							+ "Anim_LtChange$Topline 0.900 Anim_LtChange$Lt_Position 0.940 Anim_LtChange$HeaderDynamic 1.180 "
							+ "Anim_LtChange$HeaderDynamic$Change_In 1.180 Anim_LtChange$HeaderDynamic$Change_Out 0.560";
						break;
					 case "Alt_F8": case "F8": case "F10": case "j": case "Alt_a": case "Alt_s":
						previewCommands = previewCommands + " Anim_NameSuperChange$Flag 1.300 Anim_NameSuperChange$Sublines 0.700 "
							+ "Anim_NameSuperChange$Topline 0.900 Anim_NameSuperChange$HeaderDynamic 1.220 "
							+ "Anim_NameSuperChange$HeaderDynamic$Change_In 1.220 Anim_NameSuperChange$HeaderDynamic$Change_Out 0.600";
						break;
					case "q": case "Control_q":
						previewCommands = previewCommands + " Anim_Boundary_LtChange$Flag 1.300 Anim_Boundary_LtChange$Sublines 1.200 "
							+ "Anim_Boundary_LtChange$Topline 0.900 Anim_Boundary_LtChange$Lt_Position 0.940 Anim_Boundary_LtChange$HeaderDynamic 1.223 "
							+ "Anim_Boundary_LtChange$HeaderDynamic$Change_In 1.223 Anim_Boundary_LtChange$HeaderDynamic$Change_Out 0.600";
						break;
					case "Shift_F7":
						previewCommands = previewCommands + " Anim_Image_LtChange$Flag 1.300 Anim_Image_LtChange$Sublines 1.240 "
								+ "Anim_Image_LtChange$Topline 0.900 Anim_Image_LtChange$Lt_Position 0.940 Anim_Image_LtChange$HeaderDynamic 1.180 "
								+ "Anim_Image_LtChange$HeaderDynamic$Change_In 1.180 Anim_Image_LtChange$HeaderDynamic$Change_Out 0.560";
						break;	
					}
				}
			    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays "
			    	+ "C:/Temp/Preview.png " + previewCommands + " \0", print_writer);
				break;
				
			case Constants.ISPL:
				if(whichside == 1) {
					switch(whatToProcess.split(",")[0]) {
					case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a":  case "Control_F3": 
					case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12": case "Control_g": case "Control_h": 
					case "Control_p": case "Control_F6": case "Shift_F6": case "Control_s": case "Alt_d": case "Control_f": case "l": case "a": case "Alt_F1": case "Alt_F2":
					case "Shift_E": case "Alt_Shift_L": case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_F8": case "F8": case "F10": case "j": 
					case "Alt_a": case "Alt_s":  case "Alt_w": case "Control_j": case "Alt_i": case "Alt_j": case "b": case "Control_i": case "Alt_Shift_F3": case "Alt_Shift_O": 
					case "Alt_Shift_B": case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F": case "Alt_Shift_G": case "Alt_Shift_H": case "Control_u": case "Shift_G": 
					case "Shift_W":case "Control_Shift_X":
						previewCommands = "Shrink_Lt 0.800 Anim_Lower_Third 1.320 Anim_Lower_Third$Essentials 1.340 Anim_Lower_Third$Essentials$In 1.320 "
								+ "Anim_Lower_Third$Row 1.320 Anim_Lower_Third$Row$In 0.620";
						break;
					case "Alt_q":
						 previewCommands = "Anim_Infobar$Push 0.500 anim_POTT$In 1.400";
						break;
					case "Shift_I":
						previewCommands = "Anim_GooglySub$In_Out 1.900 Anim_GooglySub$In_Out$In 1.900";
						break;
					case "Control_Shift_F10":
						previewCommands = "Anim_ROF_Manhattan$In_Out 1.300 Anim_ROF_Manhattan$In_Out$In 1.300";
						break;
					case "q": case "Control_q":// Boundary L3rd
						previewCommands = "Anim_Infobar$Push 0.500 anim_Boundary_LT$Essentials 2.200 anim_Boundary_LT$Essentials$In 1.400 "
							+ "anim_Boundary_LT$Row 2.160 anim_Boundary_LT$Row$In 0.620";
						break;
					case "Shift_F7": case "Control_Shift_F9":
						previewCommands = "Anim_Infobar$Push 0.560 Anim_Image_LT$Top_Header 2.680  Anim_Image_LT$Top_Header$In 1.920 "
								+ "Anim_Image_LT$Essentials 3.200 Anim_Image_LT$Essentials$In 1.900 Anim_Image_LT$Row 3.000 Anim_Image_LT$Row$In 1.900 "
								+ "Anim_Image_LtChange$Lt_X_Position 1.140 Anim_Image_LtChange$Lt_X_Position$MoveForShrink 1.820 ";
						break;	
					}
				}else if(whichside == 2) {
					switch (whatToProcess.split(",")[0]) {
					case "F5": case "F6": case "F7": case "F9": case "F11": case "l": case "a": case "Control_F2": case "F8": case "F10": case "Control_F5": case "Control_F9": 
					case "Control_a":  case "Control_F3": case "Alt_k": case "Alt_F1": case "Alt_F2": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": 
					case "Shift_F9": case "Alt_F12":case "Shift_E": case "Control_g": case "Control_h": case "Control_p": case "Control_F6": case "Shift_F6": case "Control_s": 
					case "Control_f": case "Alt_F6": case "Shift_A":  case "Shift_R": case "Shift_U": case "Alt_F8":  case "j": case "Alt_a": case "Alt_s": case "Alt_w":
					case "Control_j": case "Alt_i": case "Alt_j":  case "b": case "Control_i": case "Alt_Shift_L": case "Alt_Shift_F3": case "Alt_Shift_O": case "Alt_Shift_B":
					case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":case "Alt_Shift_H": case "Control_u": case "Shift_G": case "Shift_W":
					case "Control_Shift_X": //case "n":	
//						previewCommands = previewCommands + " Anim_LtChange$Sublines 0.780 "
//								+ "Anim_LtChange$Topline 0.900 Anim_LtChange$Lt_Y_Scale 0.900 "
//								+ "Anim_LtChange$Lt_X_Position 0.900";
						
						previewCommands = "Anim_LtChange 1.240 Anim_LtChange$Logo 0.820 Anim_LtChange$Sublines 1.240 Anim_LtChange$TopBase 0.900 "
								+ "Anim_LtChange$Topline 0.900 Anim_LtChange$Lt_Position 0.900";
						break;
					case "q": case "Control_q":
						previewCommands = previewCommands + " Anim_Boundary_LtChange$Flag 1.300 Anim_Boundary_LtChange$Sublines 1.200 "
							+ "Anim_Boundary_LtChange$Topline 0.900 Anim_Boundary_LtChange$Lt_Position 0.940 Anim_Boundary_LtChange$HeaderDynamic 1.223 "
							+ "Anim_Boundary_LtChange$HeaderDynamic$Change_In 1.223 Anim_Boundary_LtChange$HeaderDynamic$Change_Out 0.600";
						break;
					case "Shift_F7": case "Control_Shift_F9":
						previewCommands = previewCommands + " Anim_Image_LtChange$Flag 1.300 Anim_Image_LtChange$Sublines 1.240 "
								+ "Anim_Image_LtChange$Topline 0.900 Anim_Image_LtChange$Lt_Position 0.940 Anim_Image_LtChange$HeaderDynamic 1.180 "
								+ "Anim_Image_LtChange$HeaderDynamic$Change_In 1.180 Anim_Image_LtChange$HeaderDynamic$Change_Out 0.560";
						break;	
					}
				}
				if(match.getSetup().getMatchType().equalsIgnoreCase(CricketUtil.SUPER_OVER)) {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays "
					    	+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
//					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays_SuperOver "
//					    	+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
				}else {
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays "
					    	+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
				}
			    
				break;
			case Constants.LEGENDS:
				if(whichside == 1) {
					switch(whatToProcess.split(",")[0]) {
					case "9": case "Alt_Shift_Q":
						previewCommands = "Plotter 1.000 Plotter$In 1.000";
						break;
					case "Control_Shift_M": case "Control_Shift_L":
						previewCommands = "Anim_Infobar$Push 0.500 anim_LT_Ident$InOut 1.900 anim_LT_Ident$InOut$Essentials 1.900 "
								+ "anim_LT_Ident$InOut$Essentials$In 1.900";
						break;
					case "Shift_I":
						previewCommands = "Anim_Infobar$Push 0.500 anim_ImpactLt$InOut 1.060 anim_ImpactLt$InOut$In 0.948";
						break;
					
					case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a":
					case "Control_F3": case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
					case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": 
					case "F8": case "F10": case "a":	case "Alt_Shift_F5":case "Alt_Shift_F7":case "Alt_Shift_F6": case "Alt_d": case "Alt_Shift_F3":
						previewCommands = "Anim_Infobar$Push 0.500 anim_LowerThird 2.580 anim_LowerThird$InOut 1.900 anim_LowerThird$InOut$Essentials 1.900 "
								+ "anim_LowerThird$InOut$Essentials$In 1.900 anim_LT_Change$Lt_Position 1.000";
						break;
					}
				}else if(whichside == 2) {
					switch (whatToProcess.split(",")[0]) {
					case "F5": case "F6": case "F7": case "F9": case "F11": case "Control_F2": case "Control_F5": case "Control_F9": case "Control_a":
					case "Control_F3": case "Alt_k": case "Shift_F3": case "u": case "d": case "e": case "Shift_F5": case "Shift_F9": case "Alt_F12":
					case "Control_h":  case "Control_F6": case "Shift_F6": case "Control_s": case "Control_f":  case "Shift_E": case "Alt_F8": 
					case "F8": case "F10": case "a":	case "Alt_Shift_F5":case "Alt_Shift_F7":case "Alt_Shift_F6": case "Alt_d": case "Alt_Shift_F3":
						
						previewCommands = "anim_LT_Change 1.240 anim_LT_Change$Logo 1.000 anim_LT_Change$TopLine 1.000 anim_LT_Change$SubLine 1.000 "
								+ "anim_LT_Change$Lt_Position 1.000";
						break;
					}
				}
				switch(whatToProcess.split(",")[0]) {
				case "Alt_Shift_Q":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FieldDimesnsion "
					    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
					break;
				case "9":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FieldPlotter_LLC "
					    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
					break;
				default:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays "
					    	+ "C:/Temp/Preview.tga " + previewCommands + "\0", print_writer);
					break;
				}
				break;
			}
		}
	}

	public void processBugsPreview(String whatToProcess, List<PrintWriter> print_writer, int whichside, 
		Configuration config,String whichGraphicOnScreen) throws InterruptedException 
	{
		if(config.getPreview().equalsIgnoreCase("WITH_PREVIEW")) {
			String previewCommand = "",which_gfx="/Default/gfx_Overlays";
			switch (config.getBroadcaster().toUpperCase()) {
			case Constants.VIDARBHA:
	            if(whatToProcess.contains(",")) {
	                if(whichside == 1) {
	                    switch(whatToProcess.split(",")[0]) {
	                     case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F": case "Alt_b":
	                    case ".": case "/": case "Shift_C": case "Control_Shift_R": case "Control_Shift_F3": case "Control_Shift_J":
	                        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays" + " C:/Temp/Preview.tga Anim_Bugs 2.940 "
	                                + "Anim_Bugs$Essentials 2.940 Anim_Bugs$Essentials$In 0.960 Anim_Bugs$Essentials$In$Anim_Bugs 2.940 "
	                                + "Anim_Bugs$Essentials$In$Anim_Bugs$Essentials 2.940 Anim_Bugs$Essentials$In$Anim_Bugs$Essentials$Out 2.940 \0", print_writer);
	                        break;
	                    case "Alt_p": case "o": case "t":
	                        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
	                                + "/Default/Overlays" + " C:/Temp/Preview.png Anim_Center_Bug$In 0.700 \0", print_writer);
	                        break;
	                    case "Control_Shift_U": case "Control_Shift_V": case "6": case "Control_4":
	                        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PopUps$Out SHOW 1.100 \0", print_writer);
	                        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays" + " C:/Temp/Preview.tga "
	                                + "PopUps$In 1.700\0", print_writer);
	                        break;
	                    }
	                } else {
	                    switch(whatToProcess.split(",")[0]) {
	                    case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F": case "Alt_b":
	                    case ".": case "/": case "Control_Shift_F3": case "Control_Shift_J":
	                        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
	                                + "/Default/Overlays C:/Temp/Preview.png Anim_BugsChange 1.260 \0", print_writer);
	                        break;
	                    case "Control_Shift_U": case "Control_Shift_V":
	                        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays" + " C:/Temp/Preview.tga "
	                                + "PopUps$Change 1.000\0", print_writer);
	                        break;
	                    }
	                }
	            }
	            break;
			case Constants.T20_MUMBAI:
				if(whichside == 1) {
					switch(whatToProcess.split(",")[0]) {
//					case "r":
//						previewCommand = "SrinkInfobar 1.180 TeamBug 1.880";
//						break;
					case "Alt_p":
						previewCommand = "anim_TossBug 0.500 anim_TossBug$InOut 0.500 anim_TossBug$InOut$In 0.500";
						break;
					case "Control_Shift_F3":
						previewCommand = "anim_Infobar$Shrink 0.500 anim_TargetBug 1.300 anim_TargetBug$In_Out 1.300 anim_TargetBug$In_Out$In 1.300";
						break;
					case "Shift_C":
						previewCommand = "anim_SixDistance$In_Out$Essentials$In 0.300 anim_SixDistance$In_Out$Ball$In 1.480 anim_SixDistance$In_Out$Data$In 0.800";
						break;
					case "r":
						previewCommand = "Anim_DRS_Bug$In_Out$In 0.500";
						break;
					case "h": case "Control_y": case "Control_k": case "Shift_F4": case "Shift_O": case "y": case "g": case "k":
					case "Control_Shift_R": case "Control_Shift_J":
						previewCommand = "Anim_Bugs 0.500 Anim_Bugs$In_Out 0.500 Anim_Bugs$In_Out$In 0.500";
						break;
					case "Control_Shift_U": case "Control_Shift_V":
						previewCommand = "anim_Infobar$Shrink 0.500 anim_Pop_Up 1.300 anim_Pop_Up$In_Out 1.300 anim_Pop_Up$In_Out$Essentials 1.300 anim_Pop_Up$In_Out$Essentials$In 1.000 "
								+ "anim_Pop_Up$In_Out$ColouredBase 1.300 anim_Pop_Up$In_Out$ColouredBase$In 1.300 anim_Pop_Up$In_Out$Image 1.300 anim_Pop_Up$In_Out$Image$In 1.300 "
								+ "anim_Pop_Up$In_Out$Name 1.300 anim_Pop_Up$In_Out$Name$In 1.100 anim_Pop_Up$In_Out$Data 1.300 anim_Pop_Up$In_Out$Data$In 1.200";
						break;
					case "6": case "Control_4":
						previewCommand = "anim_Infobar$Shrink 0.500 anim_BoundaryCounter 1.300 anim_BoundaryCounter$In_Out 1.300 anim_BoundaryCounter$In_Out$In 1.300";
						break;
					case "Alt_Shift_N": case "Alt_Shift_M":
						previewCommand = "SrinkInfobar 1.180 PlayerBio 2.140";
						which_gfx = "/T20/Scenes/FF_FullFrames";
						break;
					case "Alt_Shift_K": case "Alt_Shift_X": case "Alt_Shift_T": case "Alt_Shift_V":
						previewCommand = "SrinkInfobar 1.180 OTS_Leaderboard 2.140";
						which_gfx = "/T20/Scenes/FF_FullFrames";
						break;
					case "Alt_Shift_E":
						previewCommand = "SrinkInfobar 1.180 Powerplay 2.140";
						which_gfx = "/T20/Scenes/FF_FullFrames";
						break;
					}
				}else if(whichside == 2) {
					switch(whatToProcess.split(",")[0]) {
					case "r":
						previewCommand = "DRS_Change 0.500";
						break;
					case "Control_Shift_U": case "Control_Shift_V": case "Control_Shift_U_change_on": case "Control_Shift_V_change_on":
						previewCommand = "anim_Infobar$Shrink 0.500 Change_PopUp 0.800 Change_PopUp$ColouredBase 0.800 Change_PopUp$Image 0.800 Change_PopUp$Name 0.800 Change_PopUp$Data 0.800";
						break;
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" + which_gfx + " C:/Temp/Preview.tga " + previewCommand + "\0", print_writer);
				break;
			case Constants.BENGAL_T20: 
				switch(whatToProcess.split(",")[0]) {
				case "Control_Shift_U": case "Control_Shift_V":
					if(whichside == 1) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
								+ "/Default/Overlays" + " C:/Temp/Preview.tga anim_Popup 1.620 anim_Popup$In_Out 1.620 anim_Popup$In_Out$Essentials 1.620 "
										+ "anim_Popup$In_Out$Essentials$In 1.620 anim_Popup$In_Out$Text 1.620 anim_Popup$In_Out$Text$In 1.620 \0", print_writer);
					}else if(whichside == 2) {
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
								+ "/Default/Overlays" + " C:/Temp/Preview.tga Change_Popup 1.040 Change_Popup$Tex 1.040 Change_Popup$Tex$Change_Out 0.380 "
										+ "Change_Popup$Tex$Change_In 1.040 \0", print_writer);
					}
					break;
				case "6": case "Control_4":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
							+ "/Default/Overlays" + " C:/Temp/Preview.tga anim_Counter 2.000 anim_Counter$In_Out 2.000 anim_Counter$In_Out$In 1.988 "
									+ "\0", print_writer);
					break;
				case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": 
				case "Shift_F4": case "Shift_F":case "Alt_b": case "Shift_C":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
							+ "/Default/Overlays" + " C:/Temp/Preview.tga anim_Bug_2Line 0.500\0", print_writer);
					break;
				case "Control_Shift_R": case "Shift_Y":
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
							+ "/Default/Overlays" + " C:/Temp/Preview.tga anim_Bug_1Line 0.500\0", print_writer);
					break;	
				}
				break;
			
			case Constants.NPL: case Constants.LEGENDS: case Constants.MPL: case Constants.APL:
				
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.MPL:
					bugs_pre = "Overlays";
					break;
				default :
					bugs_pre = "Overlays";
					break;
				}
				if(whatToProcess.contains(",")) {
					if(whichside == 1) {
						switch(whatToProcess.split(",")[0]) {
						case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b":
						case ".": case "/": case "Shift_C": case "Control_Shift_R": case "Control_Shift_F3": case "Control_Shift_J": case "Alt_p": case "o": case "t":
							
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/" + bugs_pre + " C:/Temp/Preview.tga "
									+ "Anim_Infobar$Push 0.500 Bug_In 0.714\0", print_writer);
							
							break;
						 case "Control_Shift_U": case "Control_Shift_V":
							 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/" + bugs_pre + " C:/Temp/Preview.tga "
										+ "PopUps$InOut 1.700 PopUps$InOut$In 1.700\0", print_writer);
							 break; 
						 case "6": case "Control_4":
							 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" + "/Default/" + bugs_pre + " C:/Temp/Preview.tga "
										+ "PopUps$InOut 1.700 PopUps$InOut$In 1.700\0", print_writer);
							 break; 	 
						}
					}else {
						switch(whatToProcess.split(",")[0]) {
						case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b":
						case ".": case "/":	case "Control_Shift_F3": case "Control_Shift_J":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*"
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Bug_Change 1.334 Bug_Change$Out 0.600 Bug_Change$Bug_In 1.334\0", print_writer);
							break;
						case "Control_Shift_U": case "Control_Shift_V":
							 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" + "/Default/" + bugs_pre + " C:/Temp/Preview.tga "
										+ "PopUps$Change 1.000 PopUps$Change$HeadAll_Change 1.000 PopUps$Change$HeadAll_Change$Head_Change 1.000 "
										+ "PopUps$Change$HeadAll_Change 1.000 PopUps$Change$HeadAll_Change$Head_Change 1.000 "
										+ "PopUps$Change$HeadAll_Change$Logo_Change 1.000 PopUps$Change$DataAll_Change 1.000 "
										+ "PopUps$Change$DataAll_Change$SubHead_Change 1.000 PopUps$Change$DataAll_Change$Data_Change 1.000\0", print_writer);
							 break;	
						}
					}
				}
				break;
				
			case Constants.ICC_U19_2023: case Constants.ISPL: //case Constants.NPL: case Constants.APL:
				if(whatToProcess.contains(",")) {
					if(whichside == 1) {
						switch(whatToProcess.split(",")[0]) {
						case "Shift_C":
							previewCommand = "Anim_SixDistanceBug 1.300 Anim_SixDistanceBug$In_Out 1.300 Anim_SixDistanceBug$In_Out$In 1.300";
							break;
						case "Control_Shift_F11":
							previewCommand = "Anim_DRS_Bug$In_Out 1.600 Anim_DRS_Bug$In_Out$Essentials 1.600 Anim_DRS_Bug$In_Out$In 1.600";
							break;
						case ".":
							previewCommand = "Anim_ChallengeBug$In_Out 0.500 Anim_ChallengeBug$In_Out$In 0.500";
							break;
							
						case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b":
						case "/": case "Control_Shift_R": case "Control_Shift_F3": case "Control_Shift_J": 
							switch (config.getBroadcaster()) {
							case Constants.ICC_U19_2023: 
								previewCommand = "Anim_Bugs 2.200 Anim_Bugs$Essentials 2.200 Anim_Bugs$Essentials$In 0.800";
								break;
							case Constants.ISPL:
								previewCommand = "Anim_Bugs 2.940 Anim_Bugs$Essentials 2.940 Anim_Bugs$Essentials$In 0.960 Anim_Bugs$Essentials$In$Anim_Bugs 2.940 "
										+ "Anim_Bugs$Essentials$In$Anim_Bugs$Essentials 2.940 Anim_Bugs$Essentials$In$Anim_Bugs$Essentials$Out 2.940";
								break;
							}
							break;
						 case "Alt_p": case "o": case "t":
							 switch (config.getBroadcaster()) {
								case Constants.ICC_U19_2023: 
									previewCommand = "Anim_Center_Bug$In 0.800";
									break;
								case Constants.ISPL:
									previewCommand = "Anim_Toss_Bug$In_Out 0.500 Anim_Toss_Bug$In_Out$In 0.500";
									break;
								}
							break;
						 case "Control_Shift_U": case "Control_Shift_V": case "6":
							 previewCommand = "PopUps$In 1.700";
							 CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PopUps$Out SHOW 1.100 \0", print_writer);
							 break; 
						}
					}else {
						switch(whatToProcess.split(",")[0]) {
						case "Control_Shift_F11":
							previewCommand = "DRS_Change 1.300";
							break;
						
						case "Shift_O": case "Control_k": case "k": case "g": case "y": case "Control_y": case "h": case "Shift_F4": case "Shift_F":case "Alt_b":
						case ".": case "/":	case "Control_Shift_F3": case "Control_Shift_J":
							switch (config.getBroadcaster()) {
							case Constants.ICC_U19_2023: 
								previewCommand = "Anim_BugsChange 1.860";
								break;
							case Constants.ISPL:
								previewCommand = "Anim_BugsChange 1.260 Anim_BugsChange$Logo 1.260 Anim_BugsChange$Text 1.260";
								break;
							}
							break;
						case "Control_Shift_U": case "Control_Shift_V":
							previewCommand = "PopUps$Change 1.000";
							 break;	
						}
					}
					
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays C:/Temp/Preview.tga " + previewCommand + "\0", print_writer);
				}
				break;
			}
		}
	}

	public void processMiniPreview(String whatToProcess, List<PrintWriter> print_writer, int whichside, Configuration config, String whichGraphicOnScreen) throws InterruptedException {
		if(config.getPreview().equalsIgnoreCase("WITH_PREVIEW")) {
			switch (config.getBroadcaster().toUpperCase()) {
			case Constants.VIDARBHA:
	            if(whatToProcess.contains(",")) {
	                switch(whatToProcess.split(",")[0]) {
	                case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2":
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*"
	                            + "/Default/Overlays C:/Temp/Preview.tga Anim_Mini$In_Out 1.200 Anim_Mini$In_Out$In 1.260 "
	                            + "Anim_Mini$In_Out$Out2 1.260\0", print_writer);

	                    TimeUnit.MILLISECONDS.sleep(500);
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*"
	                            + "/Default/Overlays C:/Temp/Preview.tga Anim_Mini$In_Out 1.200 Anim_Mini$In_Out$In 1.260 "
	                            + "Anim_Mini$In_Out$Out2 1.260\0", print_writer);
	                    break;

	                case "Alt_F7":
	                    CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*"
	                            + "/Default/Overlays" + " C:/Temp/Preview.tga Anim_Infobar$Push 0.500 Anim_MiniPoints$In_Out 0.940 Anim_MiniPoints$In_Out$In 0.940 \0", print_writer);
	                    break;
	                }

	                if(whichside == 2) {
	                    switch(whatToProcess.split(",")[0]) {
	                    case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":
	                        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*"
	                                + "/Default/Overlays" + " C:/Temp/Preview.tga Anim_MiniChange 1.860 Anim_MiniChange$Change_In 1.860 \0", print_writer);
	                        break;
	                    }
	                }
	            }
	            break;
			
			case Constants.T20_MUMBAI:
				if(whatToProcess.contains(",")) {
					switch(whatToProcess.split(",")[0]) {
					case "Shift_F1": case "Shift_F2":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/gfx_Overlays C:/Temp/Preview.tga anim_Minis 2.800 anim_Minis$In_Out$Essentials$In 1.780 "
								+ "anim_Minis$In_Out$Header$In 0.900 anim_Minis$In_Out$Data$In 1.360\0", print_writer);
						break;
//					case "Alt_F7":
//						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/gfx_Overlays C:/Temp/Preview.tga "
//								+ "anim_Minis 2.000 anim_Minis$In_Out 2.000 anim_Minis$In_Out$In 2.000\0", print_writer);
//						break;
					}
				}
				break;
			case Constants.ICC_U19_2023: case Constants.ISPL: case Constants.NPL: case Constants.LEGENDS:
			case Constants.MPL:	 case Constants.APL:
				
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.MPL:
					bugs_pre = "Overlays";
					break;
				default :
					bugs_pre = "Overlays";
					break;
				}
				
				System.out.println("whatToProcess = " + whatToProcess);
				if(whatToProcess.contains(",")) {
					switch(whatToProcess.split(",")[0]) {
					case "Alt_f":
						
						switch(whatToProcess.split(",")[2]) {
						case "MANHATTAN":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Mini$In_Out 1.260 Anim_Mini$In_Out$In 1.240 Anim_Mini$In_Out$In$Manhattan 1.100 Anim_Mini$In_Out$In$Manhattan$In 1.100 "
									+ "Anim_Mini$In_Out$Out2 1.240\0", print_writer);
							
							TimeUnit.MILLISECONDS.sleep(500);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Mini$In_Out 1.260 Anim_Mini$In_Out$In 1.240 Anim_Mini$In_Out$In$Manhattan 1.100 Anim_Mini$In_Out$In$Manhattan$In 1.100 "
									+ "Anim_Mini$In_Out$Out2 1.240\0", print_writer);
							break;
						case "WORM":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Mini$In_Out 1.260 Anim_Mini$In_Out$In 1.240 Anim_Mini$In_Out$In$Worm 1.200 Anim_Mini$In_Out$In$Worm$In 1.200 "
									+ "Anim_Mini$In_Out$Out2 1.240\0", print_writer);
							
							TimeUnit.MILLISECONDS.sleep(500);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Mini$In_Out 1.260 Anim_Mini$In_Out$In 1.240 Anim_Mini$In_Out$In$Worm 1.200 Anim_Mini$In_Out$In$Worm$In 1.200 "
									+ "Anim_Mini$In_Out$Out2 1.240\0", print_writer);
							break;
						default:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Mini$In_Out 1.260 Anim_Mini$In_Out$In 1.240 "
									+ "Anim_Mini$In_Out$Out2 1.240\0", print_writer);
							
							TimeUnit.MILLISECONDS.sleep(500);
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Mini$In_Out 1.260 Anim_Mini$In_Out$In 1.240 "
									+ "Anim_Mini$In_Out$Out2 1.240\0", print_writer);
							break;
						}
						break;
					case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2":case "Alt_Shift_F8":
						
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
								+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Mini$In_Out 1.260 Anim_Mini$In_Out$In 1.240 "
								+ "Anim_Mini$In_Out$Out2 1.240\0", print_writer);
						
						TimeUnit.MILLISECONDS.sleep(500);
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
								+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Mini$In_Out 1.260 Anim_Mini$In_Out$In 1.240 "
								+ "Anim_Mini$In_Out$Out2 1.240\0", print_writer);
						break;
					case "Alt_F7":
						switch (config.getBroadcaster()) {
						case Constants.NPL: case Constants.LEGENDS: case Constants.MPL: case Constants.APL:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Mini$In_Out 1.260 Anim_Mini$In_Out$In 1.240\0", print_writer);
							break;

						default:
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_Infobar$Push 0.500 Anim_MiniPoints$In_Out 0.940 Anim_MiniPoints$In_Out$In 0.940 \0", print_writer);
							break;
						}
						break;
					}
					if(whichside == 2) {
						switch(whatToProcess.split(",")[0]) {
						case "Shift_F1": case "Shift_F2": case "Alt_F1": case "Alt_F2": case "Alt_F7":case "Alt_Shift_F8": case "Alt_f":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/" + bugs_pre + " C:/Temp/Preview.tga Anim_MiniChange 1.860 Anim_MiniChange$Change_In 1.860 \0", print_writer);
							break;	
						}
					}
				}
				break;
			case Constants.BENGAL_T20:
				if(whatToProcess.contains(",")) {
					switch(whatToProcess.split(",")[0]) {
					case "Alt_F1": case "Alt_F2": case "Control_Shift_F": case "Control_Shift_E":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
								+ "/Default/Overlays" + " C:/Temp/Preview.tga Minis 1.120 Minis$Anim_Mini 1.120 Minis$Anim_Mini$In_Out 1.120 "
										+ "Minis$Anim_Mini$In_Out$Griff 1.120 Minis$Anim_Mini$In_Out$Griff$In 1.120\0", print_writer);
						break;
					case "Shift_F1":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
								+ "/Default/Overlays" + " C:/Temp/Preview.tga Minis 1.120 Minis$Anim_Mini 1.120 Minis$Anim_Mini$In_Out 1.120 "
										+ "Minis$Anim_Mini$In_Out$Batting_Card 1.120 Minis$Anim_Mini$In_Out$Batting_Card$In 1.120\0", print_writer);
						break;
					case "Shift_F2":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
								+ "/Default/Overlays" + " C:/Temp/Preview.tga Minis 1.120 Minis$Anim_Mini 1.120 Minis$Anim_Mini$In_Out 1.120 "
										+ "Minis$Anim_Mini$In_Out$Bowling_Card 1.120 Minis$Anim_Mini$In_Out$Bowling_Card$In 1.120\0", print_writer);
						break;
					case "Alt_F7":
						CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
								+ "/Default/Overlays" + " C:/Temp/Preview.tga Minis 0.960 Minis$Anim_Mini 0.960 Minis$Anim_Mini$In_Out 0.960 "
										+ "Minis$Anim_Mini$In_Out$Standings 0.960 Minis$Anim_Mini$In_Out$Standings$In 0.960\0", print_writer);
						break;
					}
					if(whichside == 2) {
						switch(whatToProcess.split(",")[0]) {
						case "Shift_F1":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/Overlays" + " C:/Temp/Preview.tga Minis$Change 1.420 Minis$Change$Batting_Card 1.420 "
											+ "Minis$Change$Batting_Card$In 1.420\0", print_writer);
							break;
						case "Shift_F2":
							CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*" 
									+ "/Default/Overlays" + " C:/Temp/Preview.tga Minis$Change 1.420 Minis$Change$Bowling_Card 1.420 "
											+ "Minis$Change$Bowling_Card$In 1.420\0", print_writer);
							break;	
						}
					}
				}
				break;	
			}
		}
	}
	
	public void processInfoBarPreview(String whatToProcess, List<PrintWriter> print_writer, int whichside, Configuration config, String whichGraphicOnScreen) throws InterruptedException {
		
		if(config.getPreview().equalsIgnoreCase("WITH_PREVIEW")) {
			String previewCommands = "";
			switch (config.getBroadcaster().toUpperCase()) {
			case Constants.T20_MUMBAI:
				if(whatToProcess.contains(",")) {
					if(whichside == 1) {
						switch(whatToProcess.split(",")[0]) {
						case "Control_F12":
							previewCommands = "anim_Infobar 4.700 anim_Infobar$InOut 2.900 anim_Infobar$InOut$Essentials$In 2.680 "
									+ "anim_Infobar$InOut$ColourAndLogos$In 2.100 anim_Infobar$InOut$Ident$In 2.300";
							break;
						}
					}else if(whichside == 2) {
						switch(whatToProcess.split(",")[0]) {
						case "Shift_F12":
							previewCommands = "anim_Infobar$Change_Ident 0.500";
							break;
						case "Alt_7":
							previewCommands = "Default 1.640 ChangeOn_BtmRight 1.000";
							break;
						case "Alt_2":
							previewCommands = "Default 1.640 Changeon_Centre 0.800";
							switch(caption.this_infobarGfx.infobar.getMiddle_section().toUpperCase()) {
							case "BLANK":
								previewCommands = previewCommands + " MoveForCentreInfo 0.0";
								break;
							default:
								previewCommands = previewCommands + " MoveForCentreInfo 0.400";
								break;
							}
							break;
						case "Alt_0": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_8": case "Alt_9":
							previewCommands = "Default 1.640 Changeon_Right 0.500";
							if(!caption.this_infobarGfx.matchType.equalsIgnoreCase(CricketUtil.SUPER_OVER) || caption.this_infobarGfx.matchType.trim().isEmpty()) {
								switch(caption.this_infobarGfx.infobar.getRight_section().toUpperCase()) {
								case "BOWLER": case "PARTNERSHIP": case CricketUtil.BOUNDARY: case "BALLS_SINCE_LAST_BOUNDARY": case CricketUtil.EXTRAS: 
								case "LAST_X_BALLS": case "REVIEWS_REMAINING": case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER": case "TARGET": 
								case "SMALL_EQUATION": case "LAST_WICKET":
									previewCommands = previewCommands + " Shift_Wide 0.0";
									break;
								case "TOSS": case CricketUtil.PROJECTED: case "FOW": case "EQUATION": case CricketUtil.RESULT: case "RESULTS": case "TIMELINE": 
								case "COMMENTATORS": case "FREE_TEXT": case "COMPARE": case "PHASE_WISE_SCORE": case "ST_BAT": case "ST_BALL":
									previewCommands = previewCommands + " Shift_Wide 0.500";
									break;
								}
							}
							break;
						}
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/gfx_Overlays " 
							+ "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
				}
				break;
			case Constants.ISPL:
				if(whatToProcess.contains(",")) {
					if(whichside == 1) {
						switch(whatToProcess.split(",")[0]) {
						case "Control_Alt_8":
							previewCommands = "ExpandForData 1.000 Anim_LeftInfo$In_Out$MVP_LeaderBoard$In 1.360";
							break;
						case "Control_5":
							previewCommands = "OutFor11 0.500 Anim_BottomInfo$In_Out$Playing11$In 2.180";
							break;
						case "Alt_9": case "Alt_0":
							previewCommands = "Anim_BottomInfo$In_Out$GenericText1Line$In 1.900";
							break;
						case "Control_4": case "6": case "Control_Alt_3":
							previewCommands = "BoundaryCounter$In_Out 1.200 BoundaryCounter$In_Out$In 1.200";
							break;
						case "Alt_1": case "Alt_5":
							switch (caption.this_lofInfobarGfx.infobar.getFull_section()) {
							case "EQUATION": case CricketUtil.RESULT:
								previewCommands = "Anim_BottomInfo$In_Out$Equation$In 1.900";
								break;
							case CricketUtil.TOSS:
								previewCommands = "Anim_BottomInfo$In_Out$GenericText1Line$In 1.900";
								break;
							case CricketUtil.PROJECTED:
								previewCommands = "Anim_BottomInfo$In_Out$ProjectedScore$In 1.900";
								break;
							case CricketUtil.BOUNDARY:
								previewCommands = "Anim_BottomInfo$In_Out$Boundaries$In 1.900";
								break;
							case CricketUtil.EXTRAS:
								previewCommands = "Anim_BottomInfo$In_Out$Extras$In 1.900";
								break;
							case "LAST_WICKET":
								previewCommands = "Anim_BottomInfo$In_Out$LastWicket$In 1.900";
								break;
							case "BALLS_SINCE_LAST_BOUNDARY":
								previewCommands = "Anim_BottomInfo$In_Out$BallSince$In 1.800";
								break;
							case "THIS_OVER":
								previewCommands = "Anim_BottomInfo$In_Out$ThisOver$In 1.800 BottomBalls$Side1$Offset 0.480";
								if(caption.this_lofInfobarGfx.this_over_balls > 6) {
									for(int i=1;i<=caption.this_lofInfobarGfx.this_over_balls;i++) {
										previewCommands = previewCommands + "BottomBalls$Side1$Ball" + i + " 0.500";
									}
								}else {
									for(int i=1;i<=6;i++) {
										previewCommands = previewCommands + "BottomBalls$Side1$Ball" + i + " 0.500";
									}
								}
								break;
							case "THIS_OVER_RUNS":
								previewCommands = "Anim_BottomInfo$In_Out$CumullativeThisOverSmall$In 1.800";
								break;
							case "CRR": case "RRR": case "REVIEWS_REMAINING":
								previewCommands = "Anim_BottomInfo$In_Out$RunRates$In 1.800";
								break;
							case "LAST_X_BALLS":
								previewCommands = "Anim_BottomInfo$In_Out$RunRateInnings1$In 1.900";
								break;
							case "LAST_X_BALLS_WITHOUT_CRR":
								previewCommands = "Anim_BottomInfo$In_Out$LastX$In 1.900";
								break;
							case "COMPARE":
								previewCommands = "Anim_BottomInfo$In_Out$Comparison$In 1.900";
								break;
							case "OVER_TIMELINE":
								previewCommands = "Anim_BottomInfo$In_Out$TimeLine$In 1.700";
								break;
							case "TIMELINE":
								previewCommands = "Anim_BottomInfo$In_Out$14BallTimeline$In 1.700";
								break;
							case "EQUATION_SHORT_SB":
								previewCommands = "Anim_BottomInfo$In_Out$ShortEquation$In 1.900";
								break;
							}
							break;
						
						case "Alt_3": case "Alt_4":
							previewCommands = "ExpandForData 1.000 Target 0.0 Anim_LeftInfo$In_Out$Score$In 0.0 Anim_LeftInfo$In_Out$Score$Out 2.200 "
									+ "Change_LeftInfo$IdentSubline 1.260 Anim_LeftInfo$In_Out$Profile$In 1.600";
							break;
						case "Control_6": case "Control_7": case "Control_8":
							previewCommands = "ExpandForData 1.000 Target 0.0 Anim_LeftInfo$In_Out$Score$In 0.0 Anim_LeftInfo$In_Out$Score$Out 2.200 "
									+ "Anim_LeftInfo$In_Out$OutStat$In 1.600";
							break;
						case "Control_9":
							previewCommands = "ExpandForData 1.000 Target 0.0 Anim_LeftInfo$In_Out$Score$In 0.0 Anim_LeftInfo$In_Out$Score$Out 2.200 "
									+ "Anim_LeftInfo$In_Out$NextMatch$In 1.500";
							break;
						case "Control_0":
							previewCommands = "ExpandForData 1.000 Target 0.0 Anim_LeftInfo$In_Out$FormGuide$In 1.420";
							break;
						case "Shift_@": case "Shift_$": case "Control_Shift_@": case "Control_Alt_5": case "Control_Alt_9": case "Control_Alt_0":
						case "Alt_Shift_@": case "Control_Alt_6": case "Control_Alt_7":
							previewCommands = "ExpandForData 1.000 Target 0.0 Anim_LeftInfo$In_Out$LeaderBoard$In 1.340";
							break;
						case "Alt_2": case "Alt_F1": case "Alt_F2":
							previewCommands = "ExpandForData 1.000 Target 0.0 ";
							switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
							case "BATTINGCARD": case "BAT_GRIFF":
								previewCommands = previewCommands + "Anim_LeftInfo$In_Out$Score$In 0.0 Anim_LeftInfo$In_Out$Score$Out 2.200 "
										+ "Anim_LeftInfo$In_Out$BattingCard$In 1.605";
								break;
							case "BOWLINGCARD": case "BALL_GRIFF":
								previewCommands = previewCommands + "Anim_LeftInfo$In_Out$Score$In 0.0 Anim_LeftInfo$In_Out$Score$Out 2.200 "
										+ "Anim_LeftInfo$In_Out$BowlingCard$In 1.600";
								break;
							case "CURR_PARTNERSHIP":
								previewCommands = previewCommands + "Anim_LeftInfo$In_Out$CurrentPartnership$In 1.500";
								break;
							case "TARGET":
								previewCommands = previewCommands + "Anim_LeftInfo$In_Out$BigTarget$In 1.420";
								break;
							case "POINTS_TABLE":
								previewCommands = previewCommands + "Anim_LeftInfo$In_Out$Standings$In 1.400";
								break;
							}
							break;
						}
					}else if(whichside == 2) {
						switch(whatToProcess.split(",")[0]) {
						case "Control_Alt_8":
							previewCommands = "Change_LeftInfo$MVP_LeaderBoard 0.940 Change_LeftInfo$MVP_LeaderBoard$Header 0.760 Change_LeftInfo$MVP_LeaderBoard$Header$Change_Out 0.660 "
									+ "Change_LeftInfo$MVP_LeaderBoard$Header$Change_In 0.760 Change_LeftInfo$MVP_LeaderBoard$Data 0.940 Change_LeftInfo$MVP_LeaderBoard$Data$Change_Out 0.680 "
									+ "Change_LeftInfo$MVP_LeaderBoard$Data$Change_In 0.940";
							break;
						case "Alt_1": case "Alt_2": case "Alt_3": case "Alt_4": case "Alt_5": case "Alt_6": case "Alt_7": case "Alt_8": case "Alt_9": case "Alt_0":
						case "Control_5": case "Control_6": case "Control_7": case "Control_8": case "Control_9": case "Control_0":
							switch(whatToProcess.split(",")[0]) {
							case "Alt_1": case "Control_5": case "Alt_5": case "Alt_9": case "Alt_0":
								switch(caption.this_lofInfobarGfx.infobar.getLast_full_section().toUpperCase()) {
								case CricketUtil.PROJECTED:
									previewCommands = "Change_BottomInfo$ProjectedScore 1.900 Change_BottomInfo$ProjectedScore$Change_Out 0.900 Change_BottomInfo$ProjectedScore$Change_In 1.900 ";
									break;
								case CricketUtil.BOUNDARY:
									previewCommands = "Change_BottomInfo$Boundaries 1.900 Change_BottomInfo$Boundaries$Change_Out 0.900 Change_BottomInfo$Boundaries$Change_In 1.900 ";
									break;
								case CricketUtil.EXTRAS:
									previewCommands = "Change_BottomInfo$Extras 1.900 Change_BottomInfo$Extras$Change_Out 0.900 Change_BottomInfo$Extras$Change_In 1.900 ";
									break;
								case "LAST_WICKET":
									previewCommands = "Change_BottomInfo$LastWicket 1.900 Change_BottomInfo$LastWicket$Change_Out 0.900 Change_BottomInfo$LastWicket$Change_In 1.900 ";
									break;
								case "BALLS_SINCE_LAST_BOUNDARY":
									previewCommands = "Change_BottomInfo$BallSince 1.800 Change_BottomInfo$BallSince$Change_Out 0.900 Change_BottomInfo$BallSince$Change_In 1.800 ";
									break;
								case "THIS_OVER":
									previewCommands = "Change_BottomInfo$ThisOver 1.500 Change_BottomInfo$ThisOver$Change_Out 0.900 Change_BottomInfo$ThisOver$Change_In 1.500 ";
									break;
								case "THIS_OVER_RUNS":
									previewCommands = "Change_BottomInfo$CumullativeThisOverSmall 1.800 Change_BottomInfo$CumullativeThisOverSmall$Change_Out 0.900 "
											+ "Change_BottomInfo$CumullativeThisOverSmall$Change_In 1.800";
									break;
								case "CRR": case "RRR": case "REVIEWS_REMAINING":
									previewCommands = "Change_BottomInfo$RunRates 1.800 Change_BottomInfo$RunRates$Change_Out 0.900 Change_BottomInfo$RunRates$Change_In 1.800 ";
									break;
								case "LINE_UP":
									previewCommands = "Change_BottomInfo$Playing11 2.180 Change_BottomInfo$Playing11$Change_Out 1.126 Change_BottomInfo$Playing11$Change_In 2.180 ";
									break;
								case "LAST_X_BALLS":
									previewCommands = "Change_BottomInfo$RunRateInnings1 1.900 Change_BottomInfo$RunRateInnings1$Change_Out 0.900 Change_BottomInfo$RunRateInnings1$Change_In 1.900 ";
									break;
								case "LAST_X_BALLS_WITHOUT_CRR":
									previewCommands = "Change_BottomInfo$LastX 1.900 Change_BottomInfo$LastX$Change_Out 0.900 Change_BottomInfo$LastX$Change_In 1.900 ";
									break;
								case "COMPARE":
									previewCommands = "Change_BottomInfo$Comparison 1.900 Change_BottomInfo$Comparison$Change_Out 0.900 Change_BottomInfo$Comparison$Change_In 1.900 ";
									break;
								case "EQUATION": case CricketUtil.RESULT:
									previewCommands = "Change_BottomInfo$Equation 1.900 Change_BottomInfo$Equation$Change_Out 0.900 Change_BottomInfo$Equation$Change_In 1.900 ";
									break;
								case "COMMENTATORS": case "FREE_TEXT": case CricketUtil.TOSS:
									previewCommands = "Change_BottomInfo$GenericText1Line 1.900 Change_BottomInfo$GenericText1Line$Change_Out 0.900 Change_BottomInfo$GenericText1Line$Change_In 1.900 ";
									break;
								case "OVER_TIMELINE":
									previewCommands = "Change_BottomInfo$TimeLine 1.700 Change_BottomInfo$TimeLine$Change_Out 0.900 Change_BottomInfo$TimeLine$Change_In 1.700 ";
									break;
								case "TIMELINE":
									previewCommands = "Change_BottomInfo$14BallTimeline 1.700 Change_BottomInfo$14BallTimeline$Change_Out 0.900 Change_BottomInfo$14BallTimeline$Change_In 1.700 ";
									break;
								case "EQUATION_SHORT_SB":
									previewCommands = "Change_BottomInfo$ShortEquation 1.900 Change_BottomInfo$ShortEquation$Change_Out 0.900 "
											+ "Change_BottomInfo$ShortEquation$Change_In 1.900 ";
									break;
								}
								
								if(!caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase(caption.this_lofInfobarGfx.infobar.getFull_section())) {
									switch(caption.this_lofInfobarGfx.infobar.getFull_section().toUpperCase()) {
									case CricketUtil.PROJECTED:
										previewCommands = previewCommands + "Change_BottomInfo$ProjectedScore 1.900 Change_BottomInfo$ProjectedScore$Change_Out 0.900 Change_BottomInfo$ProjectedScore$Change_In 1.900";
										break;
									case CricketUtil.BOUNDARY:
										previewCommands = previewCommands + "Change_BottomInfo$Boundaries 1.900 Change_BottomInfo$Boundaries$Change_Out 0.900 Change_BottomInfo$Boundaries$Change_In 1.900";
										break;
									case CricketUtil.EXTRAS:
										previewCommands = previewCommands + "Change_BottomInfo$Extras 1.900 Change_BottomInfo$Extras$Change_Out 0.900 Change_BottomInfo$Extras$Change_In 1.900";
										break;
									case "LAST_WICKET":
										previewCommands = previewCommands + "Change_BottomInfo$LastWicket 1.900 Change_BottomInfo$LastWicket$Change_Out 0.900 Change_BottomInfo$LastWicket$Change_In 1.900";
										break;
									case "BALLS_SINCE_LAST_BOUNDARY":
										previewCommands = previewCommands + "Change_BottomInfo$BallSince 1.800 Change_BottomInfo$BallSince$Change_Out 0.900 Change_BottomInfo$BallSince$Change_In 1.800";
										break;
									case "THIS_OVER":
										previewCommands = previewCommands + "Change_BottomInfo$ThisOver 1.500 Change_BottomInfo$ThisOver$Change_Out 0.900 Change_BottomInfo$ThisOver$Change_In 1.500";
										break;
									case "THIS_OVER_RUNS":
										previewCommands = previewCommands + "Change_BottomInfo$CumullativeThisOverSmall 1.800 Change_BottomInfo$CumullativeThisOverSmall$Change_Out 0.900 "
												+ "Change_BottomInfo$CumullativeThisOverSmall$Change_In 1.800";
										break;
									case "CRR": case "RRR": case "REVIEWS_REMAINING":
										if(!caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase("CRR") && !caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase("RRR") && 
												!caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase("REVIEWS_REMAINING")) {
											previewCommands = previewCommands + "Change_BottomInfo$RunRates 1.800 Change_BottomInfo$RunRates$Change_Out 0.900 Change_BottomInfo$RunRates$Change_In 1.800";
										}
										break;
									case "LINE_UP":
										previewCommands = previewCommands + "Change_BottomInfo$Playing11 2.180 Change_BottomInfo$Playing11$Change_Out 1.126 Change_BottomInfo$Playing11$Change_In 2.180";
										break;
									case "LAST_X_BALLS":
										previewCommands = previewCommands + "Change_BottomInfo$RunRateInnings1 1.900 Change_BottomInfo$RunRateInnings1$Change_Out 0.900 Change_BottomInfo$RunRateInnings1$Change_In 1.900";
										break;
									case "LAST_X_BALLS_WITHOUT_CRR":
										previewCommands = previewCommands + "Change_BottomInfo$LastX 1.900 Change_BottomInfo$LastX$Change_Out 0.900 Change_BottomInfo$LastX$Change_In 1.900";
										break;
									case "COMPARE":
										previewCommands = previewCommands + "Change_BottomInfo$Comparison 1.900 Change_BottomInfo$Comparison$Change_Out 0.900 Change_BottomInfo$Comparison$Change_In 1.900";
										break;
									case "EQUATION": case CricketUtil.RESULT:
										if(!caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase("EQUATION") && !caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase(CricketUtil.RESULT)) {
											previewCommands = previewCommands + "Change_BottomInfo$Equation 1.900 Change_BottomInfo$Equation$Change_Out 0.900 Change_BottomInfo$Equation$Change_In 1.900";
										}
										break;
									case "COMMENTATORS": case "FREE_TEXT": case CricketUtil.TOSS:
										if(!caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase("COMMENTATORS") && !caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase("FREE_TEXT") && 
												!caption.this_lofInfobarGfx.infobar.getLast_full_section().equalsIgnoreCase(CricketUtil.TOSS)) {
											previewCommands = previewCommands + "Change_BottomInfo$GenericText1Line 1.900 Change_BottomInfo$GenericText1Line$Change_Out 0.900 "
													+ "Change_BottomInfo$GenericText1Line$Change_In 1.900";
										}
										break;
									case "OVER_TIMELINE":
										previewCommands = previewCommands + "Change_BottomInfo$TimeLine 1.700 Change_BottomInfo$TimeLine$Change_Out 0.900 Change_BottomInfo$TimeLine$Change_In 1.700";
										break;
									case "TIMELINE":
										previewCommands = previewCommands + "Change_BottomInfo$14BallTimeline 1.700 Change_BottomInfo$14BallTimeline$Change_Out 0.900 Change_BottomInfo$14BallTimeline$Change_In 1.700";
										break;
									case "EQUATION_SHORT_SB":
										previewCommands = "Change_BottomInfo$ShortEquation 1.900 Change_BottomInfo$ShortEquation$Change_Out 0.900 "
												+ "Change_BottomInfo$ShortEquation$Change_In 1.900 ";
										break;
									}
								}
								break;
							case "Alt_3": case "Alt_4":
								switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
								case "BATTINGCARD":
									previewCommands = "Change_LeftInfo$BattingCard 1.625 Change_LeftInfo$BattingCard$Change_Out 0.700 Change_LeftInfo$BattingCard$Change_In 1.625 ";
									break;
								case "BOWLINGCARD":
									previewCommands = "Change_LeftInfo$BowlingCard 1.625 Change_LeftInfo$BowlingCard$Change_Out 0.700 Change_LeftInfo$BowlingCard$Change_In 1.625 ";
									break;
								case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
									previewCommands = "Change_LeftInfo$OutStat 1.480 Change_LeftInfo$OutStat$Change_Out 0.680 Change_LeftInfo$OutStat$Change_In 1.480 ";
									break;
								case "TARGET":
									previewCommands = "Change_LeftInfo$BigTarget 1.300 Change_LeftInfo$BigTarget$Change_Out 0.620 Change_LeftInfo$BigTarget$Change_In 1.300 ";
									break;
								case "CURR_PARTNERSHIP":
									previewCommands = "Change_LeftInfo$CurrentPartnership 1.280 Change_LeftInfo$CurrentPartnership$Change_Out 0.680 Change_LeftInfo$CurrentPartnership$Change_In 1.280 ";
									//processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
									break;
								case "POINTS_TABLE":
									previewCommands = "Change_LeftInfo$Standings 0.980 Change_LeftInfo$Standings$Change_Out 0.680 Change_LeftInfo$Standings$Change_In 0.980 ";
									//processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
									break;
								case "TEAM_FORMGUIDE":
									previewCommands = "Change_LeftInfo$FormGuide 1.000 Change_LeftInfo$FormGuide$Change_Out 0.680 Change_LeftInfo$FormGuide$Change_In 1.000 ";
									//processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
									break;
								case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE":
								case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
									previewCommands = "Change_LeftInfo$LeaderBoard 0.920 Change_LeftInfo$LeaderBoard$Change_Out 0.656 Change_LeftInfo$LeaderBoard$Change_In 0.920 ";
									//processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
									break;
								}
								
								previewCommands = previewCommands + " Change_LeftInfo$Profile 1.500 Change_LeftInfo$Profile$Change_Out 0.680 Change_LeftInfo$Profile$Change_In 1.500 "
										+ "ExpandForData 1.000";
								break;
								
							case "Alt_2": case "Control_6": case "Control_7": case "Control_8": case "Control_9": case "Alt_F1": case "Alt_F2": case "Control_0":
								
								if(!caption.this_lofInfobarGfx.infobar.getMiddle_section().equalsIgnoreCase(CricketUtil.BATSMAN)) {
									switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
									case "BATTINGCARD":
										previewCommands = "Change_LeftInfo$BattingCard 1.625 Change_LeftInfo$BattingCard$Change_Out 0.700 Change_LeftInfo$BattingCard$Change_In 1.625 ";
										break;
									case "BOWLINGCARD":
										previewCommands = "Change_LeftInfo$BowlingCard 1.625 Change_LeftInfo$BowlingCard$Change_Out 0.700 Change_LeftInfo$BowlingCard$Change_In 1.625 ";
										break;
									case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER":
										previewCommands = "Change_LeftInfo$Profile 1.500 Change_LeftInfo$Profile$Change_Out 0.680 Change_LeftInfo$Profile$Change_In 1.500 ";
										break;
									case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
										previewCommands = "Change_LeftInfo$OutStat 1.480 Change_LeftInfo$OutStat$Change_Out 0.680 Change_LeftInfo$OutStat$Change_In 1.480 ";
										break;
									case "TARGET":
										previewCommands = "Change_LeftInfo$BigTarget 1.300 Change_LeftInfo$BigTarget$Change_Out 0.620 Change_LeftInfo$BigTarget$Change_In 1.300 ";
										break;
									case "CURR_PARTNERSHIP": case "SB_MATCH_PROMO": case "POINTS_TABLE": case "TEAM_FORMGUIDE": case "LB_MOST_RUNS": case "LB_MOST_WICKETS": 
									case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE":	case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY":
									case "LB_TAPE_BALL_OVER":
										
										switch (caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
										case "BATTINGCARD": case "BOWLINGCARD": case "BAT_PROFILE_CAREER": case "BALL_PROFILE_CAREER":
										case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH": case "TARGET":
											//processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "CONTINUE");
											break;
										}
										
										switch (caption.this_lofInfobarGfx.infobar.getLast_middle_section()) {
										case "CURR_PARTNERSHIP":
											previewCommands = "Change_LeftInfo$CurrentPartnership 1.280 Change_LeftInfo$CurrentPartnership$Change_Out 0.680 Change_LeftInfo$CurrentPartnership$Change_In 1.280 ";
											break;
										case "SB_MATCH_PROMO":
											previewCommands = "Change_LeftInfo$NextMatch 1.380 Change_LeftInfo$NextMatch$Change_Out 0.740 Change_LeftInfo$NextMatch$Change_In 1.380 ";
											break;
										case "POINTS_TABLE":
											previewCommands = "Change_LeftInfo$Standings 0.980 Change_LeftInfo$Standings$Change_Out 0.680 Change_LeftInfo$Standings$Change_In 0.980 ";
											break;
										case "TEAM_FORMGUIDE":
											previewCommands = "Change_LeftInfo$FormGuide 1.000 Change_LeftInfo$FormGuide$Change_Out 0.680 Change_LeftInfo$FormGuide$Change_In 1.000 ";
											break;
										case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE": 
										case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
											previewCommands = "Change_LeftInfo$LeaderBoard 0.920 Change_LeftInfo$LeaderBoard$Change_Out 0.656 Change_LeftInfo$LeaderBoard$Change_In 0.920 ";
											break;
										}
										break;
									}
								}
								
								if(!caption.this_lofInfobarGfx.infobar.getMiddle_section().equalsIgnoreCase(CricketUtil.BATSMAN) && !caption.this_lofInfobarGfx.infobar.getMiddle_section().equalsIgnoreCase("IDENT")) {									
									
									if(!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase(caption.this_lofInfobarGfx.infobar.getMiddle_section())) {
										switch(caption.this_lofInfobarGfx.infobar.getMiddle_section()) {
										case "BATTINGCARD":
											previewCommands = previewCommands + "Change_LeftInfo$BattingCard 1.625 Change_LeftInfo$BattingCard$Change_Out 0.700 Change_LeftInfo$BattingCard$Change_In 1.625";
											break;
										case "BOWLINGCARD":
											previewCommands = previewCommands + "Change_LeftInfo$BowlingCard 1.625 Change_LeftInfo$BowlingCard$Change_Out 0.700 Change_LeftInfo$BowlingCard$Change_In 1.625";
											break;
										case "HOWOUT": case "BAT_THIS_MATCH": case "BALL_THIS_MATCH":
											if(!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("HOWOUT") && 
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("BAT_THIS_MATCH") && 
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("BALL_THIS_MATCH")) {
												previewCommands = previewCommands + "Change_LeftInfo$OutStat 1.480 Change_LeftInfo$OutStat$Change_Out 0.680 Change_LeftInfo$OutStat$Change_In 1.480";
											}
											break;
										case "CURR_PARTNERSHIP":
											previewCommands = previewCommands + "Change_LeftInfo$CurrentPartnership 1.280 Change_LeftInfo$CurrentPartnership$Change_Out 0.680 "
													+ "Change_LeftInfo$CurrentPartnership$Change_In 1.280";
											//processAnimation(Constants.FRONT, print_writers, "Anim_LeftInfo$In_Out$Score", "START");
											break;
										case "TARGET":
											previewCommands = previewCommands + "Change_LeftInfo$BigTarget 1.300 Change_LeftInfo$BigTarget$Change_Out 0.620 Change_LeftInfo$BigTarget$Change_In 1.300";
											break;
										case "SB_MATCH_PROMO":
											previewCommands = previewCommands + "Change_LeftInfo$NextMatch 1.380 Change_LeftInfo$NextMatch$Change_Out 0.740 Change_LeftInfo$NextMatch$Change_In 1.380";
											break;
										case "POINTS_TABLE":
											previewCommands = previewCommands + "Change_LeftInfo$Standings 0.980 Change_LeftInfo$Standings$Change_Out 0.680 Change_LeftInfo$Standings$Change_In 0.980";
											break;
										case "TEAM_FORMGUIDE":
											previewCommands = previewCommands + "Change_LeftInfo$FormGuide 1.000 Change_LeftInfo$FormGuide$Change_Out 0.680 Change_LeftInfo$FormGuide$Change_In 1.000";
											break;
										case "LB_MOST_RUNS": case "LB_MOST_WICKETS": case "LB_MOST_FOURS": case "LB_MOST_SIXES": case "LB_HIGHEST_SCORE": case "LB_BEST_FIGURE": 
										case "LB_HIGHEST_SR": case "LB_BEST_ECONOMY": case "LB_TAPE_BALL_OVER":
											if(!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("LB_MOST_RUNS") && 
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("LB_MOST_WICKETS") && 
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("LB_MOST_FOURS") && 
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("LB_MOST_SIXES") && 
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("LB_HIGHEST_SCORE") && 
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("LB_BEST_FIGURE") && 
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("LB_HIGHEST_SR") && 
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("LB_BEST_ECONOMY") &&
													!caption.this_lofInfobarGfx.infobar.getLast_middle_section().equalsIgnoreCase("LB_TAPE_BALL_OVER")) {
												
												previewCommands = previewCommands + "Change_LeftInfo$LeaderBoard 0.920 Change_LeftInfo$LeaderBoard$Change_Out 0.656 Change_LeftInfo$LeaderBoard$Change_In 0.920";
											}
											break;
										}
										previewCommands = previewCommands + " ExpandForData 1.000";
									}
								}
								break;
							}
						}
					}
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Overlays " + "C:/Temp/Preview.tga " + previewCommands + " \0", print_writer);
				}
				break;	
			}
		}
	}
	
	public void processLegendFullFramesPreview(String whatToProcess, List<PrintWriter> print_writer, int whichside, 
			Configuration config,String whichGraphicOnScreen) 
		{
		if(config.getPreview().equalsIgnoreCase("WITH_PREVIEW")) {
			String previewCommand = "";
			switch (config.getBroadcaster().toUpperCase()) {
			
			case Constants.LEGENDS:
				if(whichside == 1) {
					switch(whatToProcess.split(",")[0]) {
					case "Shift_D":
						previewCommand = "Anim_Infobar$Push 0.500 anim_FullFrame$In_Out$Essentials$In 1.300 anim_FullFrame$In_Out$Event_Logo$In 1.200 "
								+ "anim_FullFrame$In_Out$Target$In 2.000";
						break;
					case "Control_Shift_F7":
						previewCommand = "Anim_Infobar$Push 0.500 anim_Team_BigImage$In_Out$Essentials$In 1.600 anim_Team_BigImage$In_Out$Header$In 1.600 "
								+ "anim_Team_BigImage$In_Out$SubHeader$In 1.600 anim_Team_BigImage$In_Out$Logo_FF$In 1.600 "
								+ "anim_Team_BigImage$In_Out$Team_BigImage$In 1.600 anim_Team_BigImage$In_Out$Footer$In 1.600";
						break;
					case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
						previewCommand = "Anim_Infobar$Push 0.500 anim_FullFrame$In_Out$Essentials$In 1.300 anim_FullFrame$In_Out$Event_Logo$In 1.200 "
								+ "anim_FullFrame$In_Out$Header$In 1.500 anim_FullFrame$In_Out$SubHeader$In 1.600 anim_FullFrame$In_Out$Logo_FF$In 1.900 "
								+ "anim_FullFrame$In_Out$FF_Base$In 1.400 Sponsor$In_Out$In 1.760 anim_FullFrame$In_Out$PlayerProfile$In 2.000";
						break;
					case "m": case "Control_m": case "Shift_K": case "F4": case "Shift_T": case "F1": case "F2": case "Control_F7": case "Control_F11": case "Shift_F10": 
					case "Control_p": case "Shift_F11": case "Control_F10": case "Control_Shift_F5": case "Control_Shift_D": case "Alt_Shift_F4": case "z": case "x": 
					case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8":case "Control_Shift_K": case "Control_Shift_Z": case "Control_Shift_Y":
					case "Alt_F5": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J":
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Alt_Shift_F4")) {
							previewCommand = "Anim_Infobar$Push 0.500 anim_FullFrame$In_Out$Essentials$In 1.300 anim_FullFrame$In_Out$Event_Logo$In 1.200 "
									+ "anim_FullFrame$In_Out$Header$In 1.500 anim_FullFrame$In_Out$SubHeader$In 1.600 anim_FullFrame$In_Out$Logo_FF$In 1.900 "
									+ "Sponsor$In_Out$In 1.760 ";
						}else {
							previewCommand = "Anim_Infobar$Push 0.500 anim_FullFrame$In_Out$Essentials$In 1.300 anim_FullFrame$In_Out$Event_Logo$In 1.200 "
									+ "anim_FullFrame$In_Out$Header$In 1.500 anim_FullFrame$In_Out$SubHeader$In 1.600 anim_FullFrame$In_Out$Logo_FF$In 1.900 "
									+ "anim_FullFrame$In_Out$Footer$In 1.600 Sponsor$In_Out$In 1.760 ";	
						}
						switch(whatToProcess.split(",")[0]) {
						case "m": case "Control_m": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$Match_Id$In 2.000";
							break;
						case "Alt_Shift_F4":
							previewCommand = previewCommand + "anim_FullFrame$In_Out$TeamsAll$In 2.563";
							break;
						case "Control_Shift_D":
							previewCommand = previewCommand + "anim_FullFrame$In_Out$DoubleMatchId$In 2.000";
							break;
						case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8": case "Control_Shift_Z": 
						case "Control_Shift_Y":
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$Leaderboard$In 2.000";
							break;
						case "Control_Alt_F1": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$Batting_Bowling_Card$In 2.000";
							break;
						case "Control_Alt_F2": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$Batting_Bowling_Manhattan$In 2.000";
							break;
						case "Alt_Shift_J": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$BattingCard_Manhattan$In 2.000";
							break;
						case "F1": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$BattingCard$In 2.000";
							break;
						case "F2": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$BowlingCard$In 2.000";
							break;
						case "Control_F11": case "Shift_F11":case "Control_Shift_F5":
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$MatchSummary$In 2.000";
							break;
						case "Control_F10":
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$Manhattan$In 2.000";
							break;
						case "F4": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$ParnershipList$In 2.000";
							break;
						case "Shift_K": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$Partnership$In 2.000";
							break;
						case "Shift_T": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$TeamImage$In 2.000";
							break;
						case "Control_F7": 
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$Teams$In 2.000";
							break;
						case "Shift_F10":
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$Worm$In 2.000";
							break;
						case "Alt_F5":
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$Manhattan_Phase_Compare$In 1.960";
							break;
						case "Control_p":
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$Standings$In 2.000";
							break;
						case "Control_Shift_K":
							previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 anim_FullFrame$In_Out$PlayOff$In 2.000";
							break;
						}
						break;
					}
				}else {
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "m": case "Control_m":
						previewCommand = "anim_Change$MatchId 1.500 anim_Change$MatchId$Change_Out 0.700 anim_Change$MatchId$Change_In 1.500 ";
						break;
					case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8": case "Control_Shift_Z": 
					case "Control_Shift_Y":
						previewCommand = "anim_Change$LeaderBoard 1.400 anim_Change$LeaderBoard$Change_Out 0.600 anim_Change$LeaderBoard$Change_In 1.400 ";
						break;
					case "Alt_Shift_F4":
						previewCommand = "anim_Change$TeamsAll 1.540 anim_Change$TeamsAll$Change_Out 0.563 anim_Change$TeamsAll$Change_In 1.540 ";
						break;
					case "Control_Shift_D":
						previewCommand = "anim_Change$DoubleMatchId 1.500 anim_Change$DoubleMatchId$Change_Out 0.700 anim_Change$DoubleMatchId$Change_In 1.500 ";
						break;
					case "F1":
						previewCommand = "anim_Change$BattingCard 1.400 anim_Change$BattingCard$Change_Out 0.600 anim_Change$BattingCard$Change_In 1.400 ";
						break;
					case "F2":
						previewCommand = "anim_Change$BowlingCard 1.400 anim_Change$BowlingCard$Change_Out 0.600 anim_Change$BowlingCard$Change_In 1.400 ";
						break;
					case "Control_F10":
						previewCommand = "anim_Change$Manhattan 1.400 anim_Change$Manhattan$Change_Out 0.600 anim_Change$Manhattan$Change_In 1.400 ";
						break;
					case "Control_F11": case "Control_Shift_F5":
						previewCommand = "anim_Change$MatchSummary 1.400 anim_Change$MatchSummary$Change_Out 0.600 anim_Change$MatchSummary$Change_In 1.400 ";
						break;
					case "F4":
						previewCommand = "anim_Change$ParnershipList 1.400 anim_Change$ParnershipList$Change_Out 0.600 anim_Change$ParnershipList$Change_In 1.400 ";
						break;
					case "Shift_K":
						previewCommand = "anim_Change$Partnership 1.400 anim_Change$Partnership$Change_Out 0.600 anim_Change$Partnership$Change_In 1.400 ";
						break;
					case "Shift_T":
						previewCommand = "anim_Change$TeamImage 1.400 anim_Change$TeamImage$Change_Out 0.600 anim_Change$TeamImage$Change_In 1.400 ";
						break;
					case "Control_F7":
						previewCommand = "anim_Change$Teams 1.400 anim_Change$Teams$Change_Out 0.600 anim_Change$Teams$Change_In 1.400 ";
						break;
					case "Shift_F10":
						previewCommand = "anim_Change$Worm 1.400 anim_Change$Worm$Change_Out 0.600 anim_Change$Worm$Change_In 1.400 ";
						break;
					case "Control_p":
						previewCommand = "anim_Change$Standings 1.400 anim_Change$Standings$Change_Out 0.600 anim_Change$Standings$Change_In 1.400 ";
						break;
					case "Control_Shift_K":
						previewCommand = "anim_Change$PlayOff 1.400 anim_Change$PlayOff$Change_Out 0.600 anim_Change$PlayOff$In 1.400 ";
						break;
					}
					
					if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
						switch(whatToProcess.split(",")[0]) {
						case "F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Shift_T": case "Control_F7": 
						case "Shift_F10": case "Control_p":	case "Control_Shift_F5":case "Control_F10":case "Control_Shift_K":
						case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8":
						case "Control_Shift_Z": case "Control_Shift_Y":
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("m") && 
									!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_m")&&
									!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Alt_Shift_F4")&&
									!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_D")) {
								previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 1.400 ";
							}
							break;
						case "m": case "Control_m":case "Alt_Shift_F4":case "Control_Shift_D":
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("m") && 
									!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_m")&&
									!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Alt_Shift_F4")&&
									!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_D")) {
								previewCommand = previewCommand + "anim_FullFrame$In_Out$FF_Base$In 0.0 ";
							}
							break;
						}
					}
					
					switch(whatToProcess.split(",")[0]) {
					case "F1": case "F2": case "Control_F11": case "F4": case "Shift_K": case "Shift_T":  case "Control_F7":
					case "m": case "Control_m": case "Shift_F10": case "Control_p":case "Control_F10":case "Control_Shift_F5":
					case "Control_Shift_D":	case "Alt_Shift_F4":case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":
					case "Control_Shift_F8":case "Control_Shift_K": case "Control_Shift_Z": case "Control_Shift_Y":
						if(whatToProcess.split(",")[0].equalsIgnoreCase("Alt_Shift_F4")) {
							previewCommand = previewCommand + "anim_Change$Event_Logo 1.000 anim_Change$Logo_FF 1.000 anim_Change$Header 1.000 "
									+ "anim_Change$Header$Change_Out 0.500 anim_Change$Header$Change_In 1.000 anim_Change$SubHeader 1.000 "
									+ "anim_Change$SubHeader$Change_Out 0.500 anim_Change$SubHeader$Change_In 1.000 Sponsor$Change 1.760 "
									+ "Sponsor$Change$Change_Out 0.660 Sponsor$Change$Change_In 1.760 ";
						}else {
							previewCommand = previewCommand + "anim_Change$Event_Logo 1.000 anim_Change$Logo_FF 1.000 anim_Change$Header 1.000 "
									+ "anim_Change$Header$Change_Out 0.500 anim_Change$Header$Change_In 1.000 anim_Change$SubHeader 1.000 "
									+ "anim_Change$SubHeader$Change_Out 0.500 anim_Change$SubHeader$Change_In 1.000 anim_Change$Footer 1.000 "
									+ "anim_Change$Footer$Change_Out 0.500 anim_Change$Footer$Change_In 1.000 Sponsor$Change 1.760 "
									+ "Sponsor$Change$Change_Out 0.660 Sponsor$Change$Change_In 1.760 ";
						}
						switch(whatToProcess.split(",")[0]) {
						case "m": case "Control_m":
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("m") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_m")) {
								previewCommand = previewCommand + "anim_Change$MatchId 1.500 anim_Change$MatchId$Change_Out 0.700 anim_Change$MatchId$Change_In 1.500";
							}
							break;
						case "z": case "x": case "c": case "v":case "Control_z": case "Control_x":case "Control_Shift_F8": case "Control_Shift_Z": case "Control_Shift_Y":
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("z") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("x")
								&& !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("c")&& !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("v")
								&& !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_z")&& !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_x")&& 
								!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F8") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_Z") &&
								!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_Y")) {								
								previewCommand = "anim_Change$LeaderBoard 1.400 anim_Change$LeaderBoard$Change_Out 0.600 anim_Change$LeaderBoard$Change_In 1.400 ";
							}
							break;
						case "Alt_Shift_F4":
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Alt_Shift_F4")) {
								previewCommand = previewCommand + "anim_Change$TeamsAll 1.540 anim_Change$TeamsAll$Change_Out 0.563 anim_Change$TeamsAll$Change_In 1.540";
							}
							break;
						case "Control_Shift_D":
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_D")) {
								previewCommand = previewCommand + "anim_Change$DoubleMatchId 1.500 anim_Change$DoubleMatchId$Change_Out 0.700 anim_Change$DoubleMatchId$Change_In 1.500";
							}
							break;
						case "F1":
							previewCommand = previewCommand + "anim_Change$BattingCard 1.400 anim_Change$BattingCard$Change_Out 0.600 anim_Change$BattingCard$Change_In 1.400";
							break;
						case "F2":
							previewCommand = previewCommand + "anim_Change$BowlingCard 1.400 anim_Change$BowlingCard$Change_Out 0.600 anim_Change$BowlingCard$Change_In 1.400";
							break;
						case "Control_F10":
							previewCommand = previewCommand + "anim_Change$Manhattan 1.400 anim_Change$Manhattan$Change_Out 0.600 anim_Change$Manhattan$Change_In 1.400";
							break;
						case "Control_F11":case "Control_Shift_F5":
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_F11") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Control_Shift_F5")) {
								previewCommand = previewCommand + "anim_Change$MatchSummary 1.400 anim_Change$MatchSummary$Change_Out 0.600 anim_Change$MatchSummary$Change_In 1.400";
							}
							break;
						case "F4":
							previewCommand = previewCommand + "anim_Change$ParnershipList 1.400 anim_Change$ParnershipList$Change_Out 0.600 anim_Change$ParnershipList$Change_In 1.400";
							break;
						case "Shift_K":
							previewCommand = previewCommand + "anim_Change$Partnership 1.400 anim_Change$Partnership$Change_Out 0.600 anim_Change$Partnership$Change_In 1.400";
							break;
						case "Shift_T":
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("Shift_T")) {
								previewCommand = previewCommand + "anim_Change$TeamImage 1.400 anim_Change$TeamImage$Change_Out 0.600 anim_Change$TeamImage$Change_In 1.400";
							}
							break;
						case "Control_F7":
							previewCommand = previewCommand + "anim_Change$Teams 1.400 anim_Change$Teams$Change_Out 0.600 anim_Change$Teams$Change_In 1.400";
							break;
						case "Shift_F10":
							previewCommand = previewCommand + "anim_Change$Worm 1.400 anim_Change$Worm$Change_Out 0.600 anim_Change$Worm$Change_In 1.400";
							break;
						case "Control_p":
							previewCommand = previewCommand + "anim_Change$Standings 1.400 anim_Change$Standings$Change_Out 0.600 anim_Change$Standings$Change_In 1.400";
							break;
						case "Control_Shift_K":
							previewCommand = previewCommand + "anim_Change$PlayOff 1.400 anim_Change$PlayOff$Change_Out 0.600 anim_Change$PlayOff$In 1.400";
							break;
						}
						break;
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/Fullframes " + "C:/Temp/Preview.tga " + previewCommand + "\0", print_writer);
				break;
			case Constants.NPL: case Constants.APL:
				if(whichside == 1) {
					switch (whatToProcess.split(",")[0]) {
					case "Alt_m": case "Alt_n":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_Milestone$In_Out 2.760 Anim_Milestone$In_Out$In 2.560";
						break;
					case "Alt_Shift_R":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Main$Row_Col 3.000 Anim_FullFrames$In_Out$Main$TeamSchedule$In 2.780"
								+ " Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_Shift_K":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Main$Tree 3.000 Anim_FullFrames$In_Out$Main$Tree$In 2.960"
								+ " Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
						
					case "Control_Shift_P":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Main$Row_Col 3.000 Anim_FullFrames$In_Out$Main$Row_Col$In 2.780"
								+ " Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Shift_M":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Main$Leader_Board 3.000 Anim_FullFrames$In_Out$Main$Leader_Board$In 3.000 "
								+ "Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800 ";
						if(caption.this_fullFramesGfx.highlightplayer > 0) {
							previewCommand = previewCommand + " LeaderBoardHighlight$Side1$Player"+caption.this_fullFramesGfx.highlightplayer+" 1.000";
						}
						break;
					case "Control_F10": case "Shift_F10": case "Alt_F11": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z":
					case "Control_Shift_Y": case "Control_Shift_E": case "Control_Shift_F": case "Alt_Shift_W": case "Control_Shift_F8": case "Shift_L": case "Alt_F5":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000";
						switch(whatToProcess.split(",")[0]) {
						case "z": case "x":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Logo$In 2.500";
							break;
						case "Shift_L":
							if(whatToProcess.split(",")[2].equalsIgnoreCase("MOST RUNS") || whatToProcess.split(",")[2].equalsIgnoreCase("MOST WICKETS")) {
								//previewCommand = previewCommand + " Anim_FullFrames$In_Out$Logo$In 2.500";
							}
							break;
						}
						break;
					case "Shift_F11": case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q": case "Control_F7": case "Control_p":
					case "Control_F11": case "Alt_z": case "Shift_F8":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Footer 3.000 Anim_FullFrames$In_Out$Footer$In_Out 3.000 "
								+ "Anim_FullFrames$In_Out$Footer$In_Out$Essentials 3.000 Anim_FullFrames$In_Out$Footer$In_Out$Essentials$In 2.860 "
								+ "Anim_FullFrames$In_Out$Footer$In_Out$Data 3.000 Anim_FullFrames$In_Out$Footer$In_Out$Data$In 2.860";
						break;
					case "Control_Shift_I":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000";
						break;
					case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F1": case "Control_Shift_F2": case "F4": case "Control_Shift_F4": 
					case "Shift_K": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J": case "Shift_T":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000 Anim_FullFrames$In_Out$Logo$In 2.500"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Footer 3.000 Anim_FullFrames$In_Out$Footer$In_Out 3.000 "
								+ "Anim_FullFrames$In_Out$Footer$In_Out$Essentials 3.000 Anim_FullFrames$In_Out$Footer$In_Out$Essentials$In 2.860 Anim_FullFrames$In_Out$Footer$In_Out$Data 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Data$In 2.860";
						break;
					case "Control_Shift_D":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Footer 3.000 Anim_FullFrames$In_Out$Footer$In_Out 3.000 Anim_FullFrames$In_Out$Footer$In_Out$Essentials 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Essentials$In 2.860 Anim_FullFrames$In_Out$Footer$In_Out$Data 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Data$In 2.860";
						break;
					case "Alt_Shift_Z":
						previewCommand = "Anim_Infobar$Push 0.500 BG_Scale 0.800 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$ExtraData$In 3.000 Anim_FullFrames$In_Out$Main$Tournament_Teams$In 2.420";
						break;
					case "m": case "Control_m":
						previewCommand ="Anim_Ident$In_Out$In 2.760";
						break;
					case "Shift_D":
						previewCommand ="Anim_Target$In_Out$In 2.760";
						break;
					case "Alt_Shift_P":
						previewCommand ="Feild_Dimensions$In 1.560";
						break;
					}
					switch (whatToProcess.split(",")[0]) {
					case "Control_Alt_F1":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$Batting_Bowling_Card 3.000 "
								+ "Anim_FullFrames$In_Out$Main$Batting_Bowling_Card$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_Alt_F2":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$Batting_Bowling_Manhattan 3.000 "
								+ "Anim_FullFrames$In_Out$Main$Batting_Bowling_Manhattan$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Alt_Shift_J":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$BattingCard_Manhattan 3.000 Anim_FullFrames$In_Out$Main$BattingCard_Manhattan$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000";
						break;	
					case "F1": case "Control_Shift_A": case "Control_Shift_F1":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$BattingCard 3.000 Anim_FullFrames$In_Out$Main$BattingCard$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000";
						break;
					case "F2": case "Control_Shift_F2":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$BowlingCard 3.000 Anim_FullFrames$In_Out$Main$BowlingCard$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000";
						break;
					case "Control_F11": case "Shift_F11":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Summary 3.000 Anim_FullFrames$In_Out$Main$Summary$In 2.760 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;	
					case "F4": case "Control_Shift_F4":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership_List 3.000 Anim_FullFrames$In_Out$Main$Partnership_List$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_p":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$Standings 3.000 Anim_FullFrames$In_Out$Main$Standings$In 2.720 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_Shift_I":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$Innings_Story 3.000 Anim_FullFrames$In_Out$Main$Innings_Story$In 2.460 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Shift_T":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LineUp_Image 3.000 Anim_FullFrames$In_Out$Main$LineUp_Image$In 2.920 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;	
					case "Shift_K":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership 3.000 Anim_FullFrames$In_Out$Main$Partnership$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Alt_z":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Squad 3.000 Anim_FullFrames$In_Out$Main$Squad$In 2.800 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_F7":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Teams 3.000 Anim_FullFrames$In_Out$Main$Teams$In 2.840 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;	
					case "Shift_F8":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$TeamSingle 3.000 Anim_FullFrames$In_Out$Main$TeamSingle$In 2.840 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
						if(Integer.valueOf(whatToProcess.split(",")[4])>0) {
							if(Integer.valueOf(whatToProcess.split(",")[4]) == 5) {
								previewCommand = previewCommand + " Profile_Highlight$Side1$7" + " 1.780";
							}else {
								previewCommand = previewCommand + " Profile_Highlight$Side1$" + whatToProcess.split(",")[4] + " 1.780";
							}
							
						}
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Profile 3.000 Anim_FullFrames$In_Out$Main$Profile$In 2.680 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Shift_L":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Row_Col 3.000 Anim_FullFrames$In_Out$Main$Row_Col$In 2.780 "
								+ "Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Alt_Shift_W":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Leader_Board 3.000 Anim_FullFrames$In_Out$Main$Leader_Board$In 3.000 "
								+ "Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800 LeaderBoardHighlight$Side1$Player1 1.000";
						break;
					case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8":
						if(Integer.valueOf(whatToProcess.split(",")[2].split("_")[0])>0) {
							previewCommand = previewCommand + " LeaderBoardHighlight$Side1$Player"+whatToProcess.split(",")[2].split("_")[0]+" 1.000";
						}
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Leader_Board 3.000 Anim_FullFrames$In_Out$Main$Leader_Board$In 3.000 "
								+ "Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_F10":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan 3.000 Anim_FullFrames$In_Out$Main$Manhattan$In 2.840 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;	
					case "Shift_F10":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Worms 3.000 Anim_FullFrames$In_Out$Main$Worms$In 2.980 Anim_FullFrames$In_Out$Main$Worms$In$Runs 2.980 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Alt_F11":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan_Comparison 3.000 Anim_FullFrames$In_Out$Main$Manhattan_Comparison$In 3.000 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Alt_F5":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan_Phase_Compare 3.000 Anim_FullFrames$In_Out$Main$Manhattan_Phase_Compare$In 3.000 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_Shift_D":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Double_MatchId 3.000 Anim_FullFrames$In_Out$Main$Double_MatchId$In 2.480 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";	
						break;
					case "Control_Shift_E": case "Control_Shift_F":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Player_V_Player 3.000 Anim_FullFrames$In_Out$Main$Player_V_Player$In 2.480 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";	
						break;
					}
				}else {
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "z": case "x": case "c": case "v": case "Control_F10": case "Shift_F10": case "Control_z": case "Control_x": 
					case "Control_Shift_Z": case "Control_Shift_Y": case "Alt_Shift_W": case "Control_Shift_F8": case "Shift_L":
						previewCommand = "Change$Header 1.000 Change$Header$Change_Out 0.800 Change$Header$Change_In 1.000"
								+ " Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.500 Change$SubHeader$Change_In 1.100";
						
						switch(whatToProcess.split(",")[0]) {
						case "z": case "x":
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("z") && !whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("x")) {
								previewCommand = previewCommand + " Anim_FullFrames$In_Out$LogoBase$In 2.000 Anim_FullFrames$In_Out$Logo$In 2.500";
							}
							break;
						case "Control_Shift_F8":
							if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("z") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("x")) {
								previewCommand = previewCommand + " Change$Logo 1.600 Change$Logo$Change_Out 1.000 Change$Logo$Change_In 1.600";
							}
							break;
						case "Shift_L":
							if(whatToProcess.split(",")[2].equalsIgnoreCase("MOST RUNS") || whatToProcess.split(",")[2].equalsIgnoreCase("MOST WICKETS")) {
								//previewCommand = previewCommand + " Change$Logo 1.600 Change$Logo$Change_Out 1.000 Change$Logo$Change_In 1.600";
							}
							break;
						default:
							if(whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("z") || whichGraphicOnScreen.split(",")[0].equalsIgnoreCase("x")) {
								previewCommand = previewCommand + " Anim_FullFrames$In_Out$LogoBase$In 0.0 Anim_FullFrames$In_Out$Logo$In 0.0";
							}
							break;
						}
						break;
					case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q": case "Control_F7":
					case "Control_F11": case "Alt_z": case "Shift_F8":
					previewCommand = "Change$Header 1.000 Change$Header$Change_Out 0.800 Change$Header$Change_In 1.000"
							+ " Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.500 Change$SubHeader$Change_In 1.100 Change$Footer 1.100 Change$Footer$Change_Out 0.800 Change$Footer$Change_In 1.100";
						break;
					case "Control_Shift_I":
						previewCommand = "Change$Header 1.000 Change$Header$Change_Out 0.800 Change$Header$Change_In 1.000"
								+ " Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.500 Change$SubHeader$Change_In 1.100";
							break;	
					case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F1": case "Control_Shift_F2":case "F4": 
					case "Control_Shift_F4": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J": case "Shift_T":
					previewCommand = "Change$Header 1.000 Change$Header$Change_Out 0.800 Change$Header$Change_In 1.000 Change$Logo 1.340"
							+ " Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.500 Change$SubHeader$Change_In 1.100 Change$Footer 1.100 "
							+ "Change$Footer$Change_Out 0.800 Change$Footer$Change_In 1.100";
						break;
					}
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "Alt_Shift_J": 
						previewCommand = previewCommand + " Change$BattingCard_Manhattan 1.980 Change$BattingCard_Manhattan$Change_Out 0.740 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_Alt_F1":
						previewCommand = previewCommand + " Change$Batting_Bowling_Card 1.940 Change$Batting_Bowling_Card$Change_Out 0.740 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_Alt_F2":
						previewCommand = previewCommand + " Change$Batting_Bowling_Manhattan 2.060 Change$Batting_Bowling_Manhattan$Change_Out 0.740 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;	
					case "F1": case "Control_Shift_A": case "Control_Shift_F1":
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F1")) {
							previewCommand = previewCommand + " Change$BattingCard$Change_Out 0.740";
						}
						previewCommand = previewCommand + " Change$BattingCard 1.400 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "F2": case "Control_Shift_F2":
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
							previewCommand = previewCommand + " Change$BowlingCard$Change_Out 0.740";
						}
						previewCommand = previewCommand + " Change$BowlingCard 1.400 Change$Logo$Change_Out 1.000 Change$BowlingCard$Change_In 1.400 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_F11": case "Shift_F11":
						previewCommand = previewCommand + " Change$Summary 1.280 Change$Summary$Change_Out 0.680 Change$Summary$Change_In 1.280 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "F4": case "Control_Shift_F4":
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")) {
							previewCommand = previewCommand + " Change$Partnership_List$Change_Out 0.740";
						}
						previewCommand = previewCommand + " Change$Partnership_List 1.400 Change$Logo$Change_Out 1.000 Change$Partnership_List$Change_In 1.400 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_p":
						previewCommand = previewCommand + " Change$Standings 1.200 Change$Standings$Change_Out 0.660 Change$Standings$Change_In 1.200 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_Shift_I":
						previewCommand = previewCommand + " Change$Innings_Story 1.060 Change$Innings_Story$Change_Out 0.440 Change$Innings_Story$Change_In 1.060 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;	
					case "Shift_T":
						previewCommand = previewCommand + " Change$LineUp_Image 1.440 Change$LineUp_Image$Change_Out 0.620 Change$Logo$Change_Out 1.000 "
								+ "Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_F7":
						previewCommand = previewCommand + " Change$Teams 1.440 Change$Teams$Change_Out 0.620 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;	
					case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
						if(!prevHighlightDirector.isEmpty()) {
							previewCommand = previewCommand + " Profile_Highlight$Side1$"+prevHighlightDirector+" 1.000";
						}
						previewCommand = previewCommand + " Change$Profile 1.440 Change$Profile$Change_Out 0.500 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Alt_z":
						previewCommand = previewCommand + " Change$Squad 1.320 Change$Squad$Change_Out 0.700 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Shift_F8":
						previewCommand = previewCommand + " Change$TeamSingle 1.440 Change$TeamSingle$Change_Out 0.620 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8":
						previewCommand = previewCommand + " LeaderBoardHighlight$Side2$Player"+whatToProcess.split(",")[2].split("_")[0]+" 1.000";
						break;
					case "Control_F10":
						previewCommand = previewCommand + " Change$Manhattan 1.520 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Shift_F10":
						previewCommand = previewCommand + " Change$Worms 1.500 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;	
					}
					switch (whatToProcess.split(",")[0]) {
					case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F1": case "Control_Shift_F2": case "Control_F11": case "F4": case "Shift_F11": case "Control_Shift_F4":
					case "Shift_T":	case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q": case "Control_F7": case "Alt_z": case "Shift_F8": case "Alt_Shift_W": case "Shift_L":
					case "z": case "x": case "c": case "v": case "Control_F10": case "Shift_F10": case "Control_p": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
					case "Control_Shift_F8": case "Control_Shift_I": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J": 
							switch(whatToProcess.split(",")[0]) {
							case "Control_Alt_F1":
								previewCommand = previewCommand + " Change$BattingCard$Change_In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;
							case "Control_Alt_F2":
								previewCommand = previewCommand + " Change$Batting_Bowling_Manhattan$Change_In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;
							case "Alt_Shift_J":
								previewCommand = previewCommand + " Change$Batting_Bowling_Card$Change_In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;	
							case "F1": case "Control_Shift_A": case "Control_Shift_F1":
								previewCommand = previewCommand + " Change$BattingCard$Change_In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;
							case "F2": case "Control_Shift_F2":
								previewCommand = previewCommand + " Change$BowlingCard$Change_In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;
							case "Control_F11": case "Shift_F11":
								previewCommand = previewCommand + " Change$Summary 1.280 Change$Summary$Change_Out 0.680 Change$Summary$Change_In 1.280 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "F4": case "Control_Shift_F4":
								previewCommand = previewCommand + " Change$Partnership_List$Change_In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Control_p":
								previewCommand = previewCommand + " Change$Standings 1.200 Change$Standings$Change_Out 0.660 Change$Standings$Change_In 1.200 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Control_Shift_I":
								previewCommand = previewCommand + " Change$Innings_Story$Change_In 1.060 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;	
							case "Shift_T":
								previewCommand = previewCommand + " Change$LineUp_Image 1.440 Change$LineUp_Image$Change_In 1.440 Change$Logo$Change_In 1.340 "
										+ "Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Control_F7":
								previewCommand = previewCommand + " Change$Teams 1.440 Change$Teams$Change_In 1.440 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;	
							case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
								if(Integer.valueOf(whatToProcess.split(",")[4])>0) {
									if(Integer.valueOf(whatToProcess.split(",")[4]) == 5) {
										previewCommand = previewCommand + " Profile_Highlight$Side1$7"+" 0.500";
									}else {
										previewCommand = previewCommand + " Profile_Highlight$Side1$"+whatToProcess.split(",")[4]+" 0.500";
									}
									
								}
								previewCommand = previewCommand + " Change$Profile 1.440 Change$Profile$Change_In 2.680 Change$ExtraData$Change_In 0.720 BG_Scale 0.800";
								break;
							case "Alt_z":
								previewCommand = previewCommand + " Change$Squad 1.320 Change$Squad$Change_In 1.320 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Shift_F8":
								previewCommand = previewCommand + " Change$TeamSingle 1.440 Change$TeamSingle$Change_In 1.440 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Alt_Shift_W":	case "Shift_L":
								previewCommand = previewCommand + " Change$Row_Col 1.380 Change$Row_Col$Change_Out 0.740 Change$Row_Col$Change_In 1.380 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720"
										+ " Change$ExtraData$Change_In 1.000";
								break;	
							case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8":
								previewCommand = previewCommand + " Change$Leader_Board 1.500 Change$Leader_Board$Change_Out 0.600 Change$Leader_Board$Change_In 1.500 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720"
										+ " Change$ExtraData$Change_In 1.000";
								break;
							case "Control_F10":
								previewCommand = previewCommand + " Change$Manhattan 1.520 Change$Manhattan$Change_In 1.520 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Shift_F10":
								previewCommand = previewCommand + " Change$Worms 1.500 Change$Worms$Change_In 1.500 Change$Worms$Change_In$Runs 1.500 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;		
							}
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FullFrames " + "C:/Temp/Preview.tga " + previewCommand + "\0", print_writer);
				break;
			case Constants.MPL:
				if(whichside == 1) {
					switch (whatToProcess.split(",")[0]) {
					case "Alt_m": case "Alt_n":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_Milestone$In_Out 2.760 Anim_Milestone$In_Out$In 2.560";
						break;
					case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z":
					case "Control_Shift_Y": case "Control_Shift_E": case "Control_Shift_F": case "Alt_Shift_W": case "Control_Shift_F8": case "Control_Shift_K":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000";
						break;
					case "Control_d": case "Control_e": case "Shift_T": case "Shift_P": case "Shift_Q": case "Control_F7": case "Control_p":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Footer 3.000 Anim_FullFrames$In_Out$Footer$In_Out 3.000 Anim_FullFrames$In_Out$Footer$In_Out$Essentials 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Essentials$In 2.860 Anim_FullFrames$In_Out$Footer$In_Out$Data 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Data$In 2.860";
						break;
					case "Alt_F11": 
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000 Anim_FullFrames$In_Out$Logo$In 2.500"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000";
						break;
					case "Control_Shift_I":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000";
						break;
					case "Control_F10": case "Shift_F10":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000 Anim_FullFrames$In_Out$Logo$In 2.500"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000";
						break;
					case "Shift_F11": case "Control_F11": case "Alt_z": case "Shift_F8":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000 Anim_FullFrames$In_Out$Logo$In 2.500"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Footer 3.000 Anim_FullFrames$In_Out$Footer$In_Out 3.000 Anim_FullFrames$In_Out$Footer$In_Out$Essentials 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Essentials$In 2.860 Anim_FullFrames$In_Out$Footer$In_Out$Data 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Data$In 2.860";
						break;
					case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F1": case "Control_Shift_F2": case "F4": 
					case "Control_Shift_F4": case "Shift_K": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J": 
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000 Anim_FullFrames$In_Out$Logo$In 2.500"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Footer 3.000 Anim_FullFrames$In_Out$Footer$In_Out 3.000 Anim_FullFrames$In_Out$Footer$In_Out$Essentials 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Essentials$In 2.860 Anim_FullFrames$In_Out$Footer$In_Out$Data 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Data$In 2.860";
						break;
					case "Control_Shift_D":
						previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.800 Anim_FullFrames$In_Out$Header$In 2.000"
								+ " Anim_FullFrames$In_Out$SubHeader$In 2.000 Anim_FullFrames$In_Out$Footer 3.000 Anim_FullFrames$In_Out$Footer$In_Out 3.000 Anim_FullFrames$In_Out$Footer$In_Out$Essentials 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Essentials$In 2.860 Anim_FullFrames$In_Out$Footer$In_Out$Data 3.000"
								+ " Anim_FullFrames$In_Out$Footer$In_Out$Data$In 2.860";
						break;
					case "m": case "Control_m":
						previewCommand ="Anim_Ident$In_Out$In 2.760";
						break;
					case "Shift_D":
						previewCommand ="Anim_Target$In_Out$In 2.760";
						break;
					}
					switch (whatToProcess.split(",")[0]) {
					case "Control_Alt_F1":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$Batting_Bowling_Card 3.000 Anim_FullFrames$In_Out$Main$Batting_Bowling_Card$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000";
						break;
					case "Control_Alt_F2":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$Batting_Bowling_Manhattan 3.000 Anim_FullFrames$In_Out$Main$Batting_Bowling_Manhattan$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000";
						break;
					case "Alt_Shift_J":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$BattingCard_Manhattan 3.000 Anim_FullFrames$In_Out$Main$BattingCard_Manhattan$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000";
						break;
					case "F1": case "Control_Shift_A": case "Control_Shift_F1":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$BattingCard 3.000 Anim_FullFrames$In_Out$Main$BattingCard$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000";
						break;
					case "F2": case "Control_Shift_F2":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$BowlingCard 3.000 Anim_FullFrames$In_Out$Main$BowlingCard$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000";
						break;
					case "Control_F11": case "Shift_F11":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Summary 3.000 Anim_FullFrames$In_Out$Main$Summary$In 2.760 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;	
					case "F4": case "Control_Shift_F4":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership_List 3.000 Anim_FullFrames$In_Out$Main$Partnership_List$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_p":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$Standings 3.000 Anim_FullFrames$In_Out$Main$Standings$In 2.720 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_Shift_I":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main 3.000 Anim_FullFrames$In_Out$Main$Innings_Story 3.000 Anim_FullFrames$In_Out$Main$Innings_Story$In 2.460 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Shift_T":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LineUp_Image 3.000 Anim_FullFrames$In_Out$Main$LineUp_Image$In 2.920 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;	
					case "Shift_K":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership 3.000 Anim_FullFrames$In_Out$Main$Partnership$In 2.880 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Alt_z":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Squad 3.000 Anim_FullFrames$In_Out$Main$Squad$In 2.800 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_F7":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Teams 3.000 Anim_FullFrames$In_Out$Main$Teams$In 2.840 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;	
					case "Shift_F8":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$TeamSingle 3.000 Anim_FullFrames$In_Out$Main$TeamSingle$In 2.840 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
						if(Integer.valueOf(whatToProcess.split(",")[4])>0) {
							if(Integer.valueOf(whatToProcess.split(",")[4]) == 5) {
								previewCommand = previewCommand + " Profile_Highlight$Side1$7" + " 1.780";
							}else {
								previewCommand = previewCommand + " Profile_Highlight$Side1$" + whatToProcess.split(",")[4] + " 1.780";
							}
							
						}
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Profile 3.000 Anim_FullFrames$In_Out$Main$Profile$In 2.680 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
//					case "Alt_Shift_W":
//						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Row_Col 3.000 Anim_FullFrames$In_Out$Main$Row_Col$In 2.780 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
//						break;
					case "Control_Shift_K":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Playoff_Tree 3.000 Anim_FullFrames$In_Out$Main$Playoff_Tree$In 3.000 Anim_FullFrames$In_Out$Main$Playoff_Tree$In$Tree 2.960 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Alt_Shift_W":
						previewCommand = previewCommand + " LeaderBoardHighlight$Side1$Player1 1.000";
						
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Leader_Board 3.000 Anim_FullFrames$In_Out$Main$Leader_Board$In 3.000 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8":
						if(Integer.valueOf(whatToProcess.split(",")[2].split("_")[0])>0) {
							previewCommand = previewCommand + " LeaderBoardHighlight$Side1$Player"+whatToProcess.split(",")[2].split("_")[0]+" 1.000";
						}
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Leader_Board 3.000 Anim_FullFrames$In_Out$Main$Leader_Board$In 3.000 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_F10":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan 3.000 Anim_FullFrames$In_Out$Main$Manhattan$In 2.840 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;	
					case "Shift_F10":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Worms 3.000 Anim_FullFrames$In_Out$Main$Worms$In 2.980 Anim_FullFrames$In_Out$Main$Worms$In$Runs 2.980 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Alt_F11":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan_Comparison 3.000 Anim_FullFrames$In_Out$Main$Manhattan_Comparison$In 3.000 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";
						break;
					case "Control_Shift_D":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Double_MatchId 3.000 Anim_FullFrames$In_Out$Main$Double_MatchId$In 2.480 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";	
						break;
					case "Control_Shift_E": case "Control_Shift_F":
						previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Player_V_Player 3.000 Anim_FullFrames$In_Out$Main$Player_V_Player$In 2.480 Anim_FullFrames$In_Out$ExtraData$In 3.000 BG_Scale 0.800";	
						break;
					}
				}else {
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "z": case "x": case "c": case "v": case "Control_F10": case "Shift_F10": case "Control_z": case "Control_x": 
					case "Control_Shift_Z": case "Control_Shift_Y": case "Alt_Shift_W": case "Control_Shift_F8": case "Control_Shift_K":
						previewCommand = "Change$Header 1.000 Change$Header$Change_Out 0.800 Change$Header$Change_In 1.000"
								+ " Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.500 Change$SubHeader$Change_In 1.100";
						break;
					case "Shift_T": case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q": case "Control_F7":
					previewCommand = "Change$Header 1.000 Change$Header$Change_Out 0.800 Change$Header$Change_In 1.000"
							+ " Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.500 Change$SubHeader$Change_In 1.100 Change$Footer 1.100 Change$Footer$Change_Out 0.800 Change$Footer$Change_In 1.100";
						break;
					case "Control_Shift_I":
						previewCommand = "Change$Header 1.000 Change$Header$Change_Out 0.800 Change$Header$Change_In 1.000"
								+ " Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.500 Change$SubHeader$Change_In 1.100";
							break;
					case "Control_F11": case "Alt_z": case "Shift_F8":
						previewCommand = "Change$Header 1.000 Change$Header$Change_Out 0.800 Change$Header$Change_In 1.000 Change$Logo 1.340"
								+ " Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.500 Change$SubHeader$Change_In 1.100 Change$Footer 1.100 Change$Footer$Change_Out 0.800 Change$Footer$Change_In 1.100";
							break;
					case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F1": case "Control_Shift_F2":case "F4": 
					case "Control_Shift_F4": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J": 
					previewCommand = "Change$Header 1.000 Change$Header$Change_Out 0.800 Change$Header$Change_In 1.000 Change$Logo 1.340"
							+ " Change$SubHeader 1.100 Change$SubHeader$Change_Out 0.500 Change$SubHeader$Change_In 1.100 Change$Footer 1.100 Change$Footer$Change_Out 0.800 Change$Footer$Change_In 1.100";
						break;
					}
					switch(whichGraphicOnScreen.split(",")[0]) {
					case "Alt_Shift_J": 
						previewCommand = previewCommand + " Change$BattingCard_Manhattan 1.980 Change$BattingCard_Manhattan$Change_Out 0.740 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_Alt_F1":
						previewCommand = previewCommand + " Change$Batting_Bowling_Card 1.940 Change$Batting_Bowling_Card$Change_Out 0.740 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_Alt_F2":
						previewCommand = previewCommand + " Change$Batting_Bowling_Manhattan 2.060 Change$Batting_Bowling_Manhattan$Change_Out 0.740 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "F1": case "Control_Shift_A": case "Control_Shift_F1":
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F1")) {
							previewCommand = previewCommand + " Change$BattingCard$Change_Out 0.740";
						}
						previewCommand = previewCommand + " Change$BattingCard 1.400 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "F2": case "Control_Shift_F2":
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F2")) {
							previewCommand = previewCommand + " Change$BowlingCard$Change_Out 0.740";
						}
						previewCommand = previewCommand + " Change$BowlingCard 1.400 Change$Logo$Change_Out 1.000 Change$BowlingCard$Change_In 1.400 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_F11": case "Shift_F11":
						previewCommand = previewCommand + " Change$Summary 1.280 Change$Logo$Change_Out 1.000 Change$Summary$Change_Out 0.680 Change$Summary$Change_In 1.280 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "F4": case "Control_Shift_F4":
						if(!whatToProcess.split(",")[0].equalsIgnoreCase("Control_Shift_F4")) {
							previewCommand = previewCommand + " Change$Partnership_List$Change_Out 0.740";
						}
						previewCommand = previewCommand + " Change$Partnership_List 1.400 Change$Logo$Change_Out 1.000 Change$Partnership_List$Change_In 1.400 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_p":
						previewCommand = previewCommand + " Change$Standings 1.200 Change$Standings$Change_Out 0.660 Change$Standings$Change_In 1.200 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_Shift_I":
						previewCommand = previewCommand + " Change$Innings_Story 1.060 Change$Innings_Story$Change_Out 0.440 Change$Innings_Story$Change_In 1.060 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;	
					case "Shift_T":
						previewCommand = previewCommand + " Change$LineUp_Image 1.440 Change$LineUp_Image$Change_Out 0.620 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_F7":
						previewCommand = previewCommand + " Change$Teams 1.440 Change$Teams$Change_Out 0.620 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;	
					case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
						if(!prevHighlightDirector.isEmpty()) {
							previewCommand = previewCommand + " Profile_Highlight$Side1$"+prevHighlightDirector+" 1.000";
						}
						previewCommand = previewCommand + " Change$Profile 1.440 Change$Profile$Change_Out 0.500 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Alt_z":
						previewCommand = previewCommand + " Change$Squad 1.320 Change$Logo$Change_Out 1.000 Change$Squad$Change_Out 0.700 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Shift_F8":
						previewCommand = previewCommand + " Change$TeamSingle 1.440 Change$Logo$Change_Out 1.000 Change$TeamSingle$Change_Out 0.620 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Control_Shift_K":
						previewCommand = previewCommand + " Change$Playoff_Tree 2.000 Change$Playoff_Tree$Change_Out 0.500 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8":
						previewCommand = previewCommand + " LeaderBoardHighlight$Side2$Player"+whatToProcess.split(",")[2].split("_")[0]+" 1.000";
						break;
					case "Control_F10":
						previewCommand = previewCommand + " Change$Manhattan 1.520 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;
					case "Shift_F10":
						previewCommand = previewCommand + " Change$Worms 1.500 Change$Logo$Change_Out 1.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720";
						break;	
					}
					switch (whatToProcess.split(",")[0]) {
					case "F1": case "Control_Shift_A": case "F2": case "Control_Shift_F1": case "Control_Shift_F2": case "Control_F11": case "F4": case "Shift_F11": case "Control_Shift_F4":
					case "Shift_T":	case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q": case "Control_F7": case "Alt_z": case "Shift_F8": case "Alt_Shift_W":
					case "z": case "x": case "c": case "v": case "Control_F10": case "Shift_F10": case "Control_p": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y":
					case "Control_Shift_F8": case "Control_Shift_I": case "Control_Alt_F1": case "Control_Alt_F2": case "Alt_Shift_J": case "Control_Shift_K":
							switch(whatToProcess.split(",")[0]) {
							case "Control_Alt_F1":
								previewCommand = previewCommand + " Change$BattingCard$Change_In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;
							case "Control_Alt_F2":
								previewCommand = previewCommand + " Change$Batting_Bowling_Manhattan$Change_In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;
							case "Alt_Shift_J":
								previewCommand = previewCommand + " Change$Batting_Bowling_Card$Change_In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;
							case "F1": case "Control_Shift_A": case "Control_Shift_F1":
								previewCommand = previewCommand + " Change$BattingCard$Change_In 1.400 Change$BattingCard$Change_In$In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;
							case "F2": case "Control_Shift_F2":
								previewCommand = previewCommand + " Change$BowlingCard$Change_In 1.400 Change$BowlingCard$Change_In$In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000";
								break;
							case "Control_F11": case "Shift_F11":
								previewCommand = previewCommand + " Change$Summary 1.280 Change$Summary$Change_Out 0.680 Change$Summary$Change_In 1.300 Change$Summary$Change_In$In 1.300 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "F4": case "Control_Shift_F4":
								previewCommand = previewCommand + " Change$Partnership_List$Change_In 1.400 Change$Partnership_List$Change_In$In 1.400 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Control_p":
								previewCommand = previewCommand + " Change$Standings 1.200 Change$Standings$Change_Out 0.660 Change$Standings$Change_In 1.240 Change$Standings$Change_In$In 1.240 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Control_Shift_I":
								previewCommand = previewCommand + " Change$Innings_Story$Change_In 1.100 Change$Innings_Story$Change_In$In 1.100 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;	
							case "Shift_T":
								previewCommand = previewCommand + " Change$LineUp_Image 1.440 Change$LineUp_Image$Change_In 1.480 Change$LineUp_Image$Change_In$In 1.480 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Control_F7":
								previewCommand = previewCommand + " Change$Teams 1.440 Change$Teams$Change_In 1.440 Change$Teams$Change_In$In 1.440 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;	
							case "Control_d": case "Control_e": case "Shift_P": case "Shift_Q":
								if(Integer.valueOf(whatToProcess.split(",")[4])>0) {
									if(Integer.valueOf(whatToProcess.split(",")[4]) == 5) {
										previewCommand = previewCommand + " Profile_Highlight$Side1$7"+" 0.500";
									}else {
										previewCommand = previewCommand + " Profile_Highlight$Side1$"+whatToProcess.split(",")[4]+" 0.500";
									}
									
								}
								previewCommand = previewCommand + " Change$Profile 1.440 Change$Profile$Change_In 2.680 Change$Profile$Change_In$In 2.680 Change$ExtraData$Change_In 0.720 BG_Scale 0.800";
								break;
							case "Alt_z":
								previewCommand = previewCommand + " Change$Squad 1.320 Change$Squad$Change_In 1.320 Change$Squad$Change_In$In 1.320 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Shift_F8":
								previewCommand = previewCommand + " Change$TeamSingle 1.440 Change$TeamSingle$Change_In 1.440 Change$TeamSingle$Change_In$In 1.440 Change$Logo$Change_In 1.340 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Alt_Shift_W":	
								previewCommand = previewCommand + " Change$Row_Col 1.380 Change$Row_Col$Change_Out 0.740 Change$Row_Col$Change_In 1.380 Change$Row_Col$Change_In$In 1.380 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720"
										+ " Change$ExtraData$Change_In 1.000";
								break;
							case "Control_Shift_K":
								previewCommand = previewCommand + " Change$Playoff_Tree 2.000 Change$Playoff_Tree$Change_Out 0.500 Change$Playoff_Tree$Change_In 2.000 Change$Playoff_Tree$Change_In$In 2.000 Change$Playoff_Tree$Change_In$In$Tree 2.000 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720"
										+ " Change$ExtraData$Change_In 1.000";
								break;
							case "z": case "x": case "c": case "v": case "Control_z": case "Control_x": case "Control_Shift_Z": case "Control_Shift_Y": case "Control_Shift_F8":
								previewCommand = previewCommand + " Change$Leader_Board 1.500 Change$Leader_Board$Change_Out 0.600 Change$Leader_Board$Change_In 1.500 Change$Leader_Board$Change_In$In 1.500 Change$ExtraData 1.000 Change$ExtraData$Change_Out 0.720"
										+ " Change$ExtraData$Change_In 1.000";
								break;
							case "Control_F10":
								previewCommand = previewCommand + " Change$Manhattan 1.520 Change$Manhattan$Change_In 1.520 Change$Manhattan$Change_In$In 1.520 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;
							case "Shift_F10":
								previewCommand = previewCommand + " Change$Worms 1.500 Change$Worms$Change_In 1.500 Change$Worms$Change_In$In 1.500 Change$Worms$Change_In$In$Runs 1.500 Change$ExtraData$Change_In 1.000 BG_Scale 0.800";
								break;		
							}
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FullFrames " + "C:/Temp/Preview.tga " + previewCommand + "\0", print_writer);
				break;	
			
			case Constants.ISPL:
				if(whichside == 1) {
					if(whatToProcess.contains(",")) {
						switch(whatToProcess.split(",")[0]) {
						/* case "F1": */ case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Control_F7": case "Shift_F8":
						case "Shift_K":	case "Control_p": case "Shift_F11": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
						case "Control_c": case "Control_v": case "Shift_V": case "Control_F10": 
							previewCommand = "Anim_Infobar$Push 0.500 Anim_FullFrames$In_Out$Essentials$In 2.140 Anim_FullFrames$In_Out$Header$In 2.100";
							break;
						case "m": case "Control_m":
							previewCommand = "Start_End$Essentials$In 1.720 anim_MatchId$In_Out$In 2.560";
							break;
						case "Shift_D": // target
							previewCommand = "Anim_Target$In_Out$In 1.500";
							break;	
						}
						switch(whatToProcess.split(",")[0]) {
						/* case "F1": */ case "Control_Shift_A"://battingCard
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Batting_Card$In 2.200";
							break;
						case "F2"://bowlingCard
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Bowling_Card$In 2.120";
							break;
						case "F4":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership_List$In 2.200";
							break;
						case "Control_F11": case "Shift_F11":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Summary$In 1.880";
							break;
						case "Control_F7":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Teams$In 2.220";
							break;
						case "Shift_T": //Playing XI
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Team_Single$In 2.240";
							break;
						case "Shift_F8":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LineUp_Image$In 2.240";
							break;
						case "Shift_K":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Partnership$In 2.200";
							break;
						case "Control_F10":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Manhattan$In 2.220";
							break;
						case "Control_p":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$Standings$In 1.843";
							break;
						case "z": case "x": case "c": case "v":	case "Control_c": case "Control_v": case "Control_z": case "Control_x":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LeaderBoard$In 2.220";
							previewCommand = previewCommand + " LeaderBoardHighlight$Side1$Player" + whatToProcess.split(",")[2].split("_")[0] + " 1.574";
							break;
						case "Shift_V":
							previewCommand = previewCommand + " Anim_FullFrames$In_Out$Main$LeaderBoard$In 2.220";
							break;
						}
					}
				}else if(whichside == 2) {
					if(whatToProcess.contains(",")) {
						switch(whatToProcess.split(",")[0]) {
						/* case "F1": */ case "Control_Shift_A": case "F2": case "F4": case "Control_F11": case "Shift_T": case "Shift_F8": case "Shift_K":
						case "Control_p": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
							previewCommand = previewCommand + "Change$Header 1.600 Change$Header$Change_In 1.600 Change$Header$Change_Out 0.420";
							if(whichGraphicOnScreen.contains(",")) {
								switch(whichGraphicOnScreen.split(",")[0]) {
								/* case "F1": */case "Control_Shift_A":  
									previewCommand = previewCommand + " Change$Batting_Card 1.900 Change$Batting_Card$Change_Out 0.860 Change$Batting_Card$Change_In 1.900";
									break;
								case "F2":  
									previewCommand = previewCommand + " Change$Bowling_Card 1.820 Change$Bowling_Card$Change_Out 0.760 Change$Bowling_Card$Change_In 1.820";
									break;
								case "F4":  
									previewCommand = previewCommand + " Change$Partnership_List 1.900 Change$Partnership_List$Change_Out 0.860 "
											+ "Change$Partnership_List$Change_In 1.900";
									break;
								case "Control_F11":  
									previewCommand = previewCommand + " Change$Summary 1.580 Change$Summary$Change_Out 0.760 Change$Summary$Change_In 1.580";
									break;
								case "Shift_T":
									previewCommand = previewCommand + " Change$Team_Single 1.940 Change$Team_Single$Change_Out 0.820 Change$Team_Single$Change_In 1.940";
									break;
								case "Shift_F8":
									previewCommand = previewCommand + " Change$LineUp_Image 1.940 Change$LineUp_Image$Change_Out 0.820 Change$LineUp_Image$Change_In 1.940";
									break;
								case "Shift_K":
									previewCommand = previewCommand + " Change$Partnership 1.900 Change$Partnership$Change_Out 0.860 "
											+ "Change$Partnership$Change_In 1.900";
									break;
								case "Control_p":
									previewCommand = previewCommand + " Change$Standings 1.543 Change$Standings$Change_Out 0.760 "
											+ "Change$Standings$Change_In 1.543";
									break;
								case "z": case "x": case "c": case "v":	case "Control_z": case "Control_x":
									previewCommand = previewCommand + " Change$LeaderBoard 2.200 Change$LeaderBoard$Change_Out 0.760 "
											+ "Change$LeaderBoard$Change_In 2.200";
									previewCommand = previewCommand + " LeaderBoardHighlight$Side2$Player" + whatToProcess.split(",")[2].split("_")[0] + " 1.574";
									break;	
								}
							}
							if(!whichGraphicOnScreen.split(",")[0].equalsIgnoreCase(whatToProcess.split(",")[0])) {
								switch(whatToProcess.split(",")[0]) {
								/* case "F1": */ case "Control_Shift_A": case "F2":case "F4": case "Control_F11": case "Shift_K": case "Control_p":
								case "Shift_T":	case "Shift_F8": case "z": case "x": case "c": case "v": case "Control_z": case "Control_x":
									previewCommand = previewCommand + " Header_Shrink 0.000 Header_Shrink$In 0.000";
									break;
								}
								switch(whatToProcess.split(",")[0]) {
								/* case "F1": */ case "Control_Shift_A":  
									previewCommand = previewCommand + " Change$Batting_Card 1.900 Change$Batting_Card$Change_Out 0.860 Change$Batting_Card$Change_In 1.900";
									break;
								case "F2":  
									previewCommand = previewCommand + " Change$Bowling_Card 1.820 Change$Bowling_Card$Change_Out 0.760 Change$Bowling_Card$Change_In 1.820";
									break;
								case "F4":
									previewCommand = previewCommand + " Change$Partnership_List 1.900 Change$Partnership_List$Change_Out 0.860 "
											+ "Change$Partnership_List$Change_In 1.900";
									break;
								case "Control_F11":  
									previewCommand = previewCommand + " Change$Summary 1.580 Change$Summary$Change_Out 0.760 Change$Summary$Change_In 1.580";
									break;
								case "Shift_T":
									previewCommand = previewCommand + " Change$Team_Single 1.940 Change$Team_Single$Change_Out 0.820 Change$Team_Single$Change_In 1.940";
									break;
								case "Shift_F8":
									previewCommand = previewCommand + " Change$LineUp_Image 1.940 Change$LineUp_Image$Change_Out 0.820 Change$LineUp_Image$Change_In 1.940";
									break;
								case "Shift_K":
									previewCommand = previewCommand + " Change$Partnership 1.900 Change$Partnership$Change_Out 0.860 "
											+ "Change$Partnership$Change_In 1.900";
									break;
								case "Control_p":
									previewCommand = previewCommand + " Change$Standings 1.543 Change$Standings$Change_Out 0.760 "
											+ "Change$Standings$Change_In 1.543";
									break;
								}
							}
//							previewCommand = previewCommand + " Change$Footer 1.700 Change$Footer$Change_In 1.700 Change$Footer$Chnage_Out 0.580";
//							System.out.println("Number of rows : " + caption.this_fullFramesGfx.numberOfRows);
//							System.out.println("L Number of rows : " + lastNumberOfRows);
//							if(caption.this_fullFramesGfx.numberOfRows != lastNumberOfRows) {
//								previewCommand = previewCommand + " ConcussExtend_Y 0.500 ConcussExtend_Y$In 0.500";
//							}
							break;
						}
					}
				}
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER PREVIEW SCENE*/Default/FullFrames "
				    	+ "C:/Temp/Preview.png " + previewCommand + " \0", print_writer);
				break;
			}
		}
	}

	public String getWhichGraphicOnScreen() {
		return whichGraphicOnScreen;
	}

	public void setWhichGraphicOnScreen(String whichGraphicOnScreen) {
		this.whichGraphicOnScreen = whichGraphicOnScreen;
	}

	public String getSpecialBugOnScreen() {
		return specialBugOnScreen;
	}

	public void setSpecialBugOnScreen(String specialBugOnScreen) {
		this.specialBugOnScreen = specialBugOnScreen;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Infobar getInfobar() {
		return infobar;
	}

	public void setInfobar(Infobar infobar) {
		this.infobar = infobar;
	}

	@Override
	public String toString() {
		return "Animation [whichGraphicOnScreen=" + whichGraphicOnScreen + ", specialBugOnScreen=" + specialBugOnScreen
				+ ", status=" + status + ", caption=" + caption + ", lastNumberOfRows="
				+ lastNumberOfRows + "]";
	}

	
}
