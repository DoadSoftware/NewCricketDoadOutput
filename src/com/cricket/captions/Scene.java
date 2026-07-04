package com.cricket.captions;

import java.io.PrintWriter;
import java.util.List;
import com.cricket.model.Configuration;
import com.cricket.util.CricketFunctions;

public class Scene 
{
	public void LoadScene(String whatToProcess, List<PrintWriter> print_writers, 
		Configuration config) throws InterruptedException
	{
		CricketFunctions.DoadWriteCommandToAllViz("-1 SCENE CLEANUP\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 IMAGE CLEANUP\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 GEOM CLEANUP\0", print_writers);
		CricketFunctions.DoadWriteCommandToAllViz("-1 FONT CLEANUP\0", print_writers);
        CricketFunctions.DoadWriteCommandToAllViz("-1 IMAGE INFO\0", print_writers);
		
		switch (config.getBroadcaster().toUpperCase()) {
		case Constants.T20_MUMBAI:
			switch (whatToProcess) {
			case "OVERLAYS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/gfx_Overlays \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0 \0", print_writers);
				break;
//			case "OVERLAYS_SUPER_OVER":
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/T20/Scenes/Ticker-SuperOver \0", print_writers);
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
//		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0 \0", print_writers);
//				break;
			case "FULL-FRAMERS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*/Default/gfx_Fullframes \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*STAGE SHOW 0.0 \0", print_writers);
				break;
			}
			break;
		case Constants.VIDARBHA:
			switch (whatToProcess) {
			case "FULL-FRAMERS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*/Default/FullFrames \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*STAGE SHOW 0.0 \0", print_writers);
				break;
			case "OVERLAYS":
				switch (config.getBroadcaster().toUpperCase()) {
				case "ICC-U19-2023": case Constants.NPL: case Constants.APL: case Constants.VIDARBHA:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/Overlays \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
			        //CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0 \0", print_writers);
			        break;
				}
				break;
			case "PLOTTER":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER SET_OBJECT SCENE*/Default/FieldPlotter_LLC \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*STAGE SHOW 0.0 \0", print_writers);
				break;
			case "LOF_PLOTTER":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER SET_OBJECT SCENE*/Default/FieldDimesnsion \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*STAGE SHOW 0.0 \0", print_writers);
				break;
			}
			break;
		case "ICC-U19-2023": case Constants.NPL: case Constants.MPL: case Constants.APL:

			switch (whatToProcess) {
			case "FULL-FRAMERS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*/Default/FullFrames \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*STAGE SHOW 0.0 \0", print_writers);
				break;
			case "OVERLAYS":
				switch (config.getBroadcaster().toUpperCase()) {
				case "ICC-U19-2023": case Constants.NPL: case Constants.APL:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/Overlays \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
			        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0 \0", print_writers);
			        break;
			    default:
			    	CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/Overlays_New \0", print_writers);
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
			        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0 \0", print_writers);
			        
			        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Lower_Third$Change$Change_Out SHOW 50.0 \0", print_writers);
			        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_MatchID$Change$Change_Out SHOW 50.0 \0", print_writers);
			        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_Comparison$Change$Change_Out SHOW 50.0 \0", print_writers);
			        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_PlayingXII$In_Out SHOW 0.0 \0", print_writers);
			        //CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*PopUps$Out SHOW 1.200 \0", print_writers);
			        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*LT_NextToBat$Change$Change_Out SHOW 0.700 \0", print_writers);
			    	break;
				}
				break;
			case "PLOTTER":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER SET_OBJECT SCENE*/Default/FieldPlotter_LLC \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*STAGE SHOW 0.0 \0", print_writers);
				break;
			case "LOF_PLOTTER":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER SET_OBJECT SCENE*/Default/FieldDimesnsion \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*STAGE SHOW 0.0 \0", print_writers);
				break;	
			}
			break;
		case Constants.BENGAL_T20: case Constants.LEGENDS: case Constants.ASSAM: case Constants.AFG_T20:
			switch (whatToProcess) {
			case "FULL-FRAMERS":
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.BENGAL_T20: case Constants.AFG_T20:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*/Default/FullFrames \0", print_writers);
					break;
				case Constants.LEGENDS: case Constants.ASSAM:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*/Default/Fullframes \0", print_writers);
					break;	
				}
				
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*STAGE SHOW 0.0 \0", print_writers);
				break;
			case "OVERLAYS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/Overlays \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0 \0", print_writers);
				break;
			case "PLOTTER":
				switch (config.getBroadcaster().toUpperCase()) {
				case Constants.AFG_T20:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER SET_OBJECT SCENE*/Default/FieldPlotter \0", print_writers);
					break;
				default:
					CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER SET_OBJECT SCENE*/Default/FieldPlotter_LLC \0", print_writers);
					break;	
				}
				
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*STAGE SHOW 0.0 \0", print_writers);
				break;
			case "LOF_PLOTTER":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER SET_OBJECT SCENE*/Default/FieldDimesnsion \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*STAGE SHOW 0.0 \0", print_writers);
				break;	
			}
			break;	
		case "ISPL":

			switch (whatToProcess) {
			case "FULL-FRAMERS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER SET_OBJECT SCENE*/Default/FullFrames \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
		        CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*BACK_LAYER*STAGE SHOW 0.0 \0", print_writers);
				break;
			case "OVERLAYS":
//				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/ScoreAnimTest \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/Overlays \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
		      //  CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0 \0", print_writers);
				break;
			case "MVP":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER SET_OBJECT SCENE*/Default/MVP \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER SCENE_DATA INITIALIZE \0", print_writers);
				break;
			case "SO_OVERLAYS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/Overlays_SuperOver \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
		      //  CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*STAGE SHOW 0.0 \0", print_writers);
				break;	
			case "TRADITIONAL_OVERLAYS":
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*/Default/Overlays_Traditional \0", print_writers);
				CricketFunctions.DoadWriteCommandToAllViz("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0", print_writers);
				break;
			}
			break;
		}
	}	
 
}