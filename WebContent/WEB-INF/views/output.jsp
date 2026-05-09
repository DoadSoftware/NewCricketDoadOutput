<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  
  <title>Output</title>
	
  <script type="text/javascript" src="<c:url value="/webjars/jquery/3.7.1/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/5.3.2/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    
  <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
  <link rel="stylesheet" href="<c:url value="/resources/css/output.css"/>">
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/5.3.2/css/bootstrap.min.css"/>"/>  
  <link href="<c:url value="/webjars/font-awesome/6.5.1/css/all.css"/>" rel="stylesheet">
  
  <style>
	#stats-container {
	    background: white;
	    border-radius: 8px;
	    padding: 15px 20px;
	    box-shadow: 0 4px 10px rgba(0,0,0,0.15);
	    font-family: Arial, sans-serif;
	    width: fit-content;
	    margin: 15px auto;
	    text-align: center;
	}
	
	#player-name {
	    font-size: 24px;
	    font-weight: bold;
	    color: #0056b3;
	    margin-bottom: 8px;
	    text-transform: uppercase;
	}
	
	#stats-container div {
	    font-size: 22px;
	    margin-bottom: 5px;
	}
	
	#stats-container strong {
	    font-weight: bold;
	}
	</style>
  
  <script type="text/javascript">
  $(document).on("keydown", function(e){
	  
	  if($('#waiting_modal').hasClass('show')) {
		  e.cancelBubble = true;
		  e.stopImmediatePropagation();
    	  e.preventDefault();
		  return false;
	  }
	  
      var evtobj = window.event? event : e;
      
      switch(e.target.tagName.toLowerCase())
      {
      case "input": case "textarea":
    	 break;
      default:
    	  e.preventDefault();
	      var whichKey = '';
		  var validKeyFound = false;
	    
	      if(evtobj.ctrlKey) {
	    	  whichKey = 'Control';
	      }
	      if(evtobj.altKey) {
	    	  if(whichKey) {
	        	  whichKey = whichKey + '_Alt';
	    	  } else {
	        	  whichKey = 'Alt';
	    	  }
	      }
	      if(evtobj.shiftKey) {
	    	  if(whichKey) {
	        	  whichKey = whichKey + '_Shift';
	    	  } else {
	        	  whichKey = 'Shift';
	    	  }
	      }
	      
		  if(evtobj.keyCode) {
	    	  if(whichKey) {
	    		  if(!whichKey.includes(evtobj.key)) {
	            	  whichKey = whichKey + '_' + evtobj.key;
	    		  }
	    	  } else {
	        	  whichKey = evtobj.key;
	    	  }
		  }
		  validKeyFound = false;
		  if (whichKey.includes('_')) {
			  whichKey.split("_").forEach(function (this_key) {
				  switch (this_key) {
				  case 'Control': case 'Shift': case 'Alt':
					break;
				  default:
					validKeyFound = true;
					break;
				  }
			  });
		   } else {
			  if(whichKey != 'Control' && whichKey != 'Alt' && whichKey != 'Shift') {
				  validKeyFound = true;
			  }
		   }
			  
		   if(validKeyFound == true) {
			   console.log('whichKey = ' + whichKey);
			   processUserSelectionData('LOGGER_FORM_KEYPRESS',whichKey);
		   }
	      }
	  }); 
   	  setInterval(() => {processCricketProcedures('READ-MATCH-AND-POPULATE');}, 1000);
  </script> 
  </head>
<div class="header-container">
    <img src="<c:url value='/resources/Images/Design.jpg'/>" alt="Logo">
    <h2>DESIGN ON A DIME</h2>
    <h3 align="right" style = "text-transform: uppercase;font-weight: bold;">${expiryDate} Days Left &nbsp;</h3>
</div>
<body onload="onPageLoadEvent('OUTPUT')">
<div id="waiting_modal" class="modal my_waiting_modal fade bd-example-modal-lg" data-backdrop="static" 
	data-keyboard="false" data-bs-backdrop="static" tabindex="-1">
    <div id="waiting_modal_body" class="modal-dialog modal-sm">
        <div class="modal-content" style="width: 48px">
	    	<h5 style="color:white">Processing...</h5> <br>
            <span class="fa fa-spinner fa-spin fa-4x" style="color:white"></span>
        </div>
    </div>
</div>
<form:form name="output_form" method="POST" action="output">
<div class="content py-5" style="background-size: cover; background-position: center; width: 100%;">
  <div class="container-fluid px-0">
    <div class="card-body mx-auto" style="width: 85%; max-width: 100%; background: white; border-radius: 10px;">
		<div id="select_graphic_options_div" style="display:none;">
		 </div>
		 <div id="captions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px; text-transform: uppercase;">
			<label class="col-sm-4 col-form-label text-left">
			    <i class="fas fa-file-video"  style="font-size: 30px;"></i> <b>Match:</b> ${session_match.match.matchFileName.replace(".json"," ")}</label>
			
			<label class="col-sm-4 col-form-label text-left">
			    <i class="fas fa-broadcast-tower"  style="font-size: 30px;"></i> <b>Broadcaster:</b> ${session_configuration.broadcaster.replace("_"," ").replace("LEGENDS-90","BPL")}
			</label>
			
			<c:if test="${not empty session_configuration.secondaryBroadcaster}">
			    <label class="col-sm-4 col-form-label text-left">
			        <i class="fas fa-broadcast-tower"  style="font-size: 30px;"></i> <b>2nd Broadcaster:</b> ${session_configuration.secondaryBroadcaster.replace("_"," ")}
			    </label>
			</c:if>

			<label class="col-sm-4 col-form-label text-left">
			    <i class="fas fa-trophy" style="font-size: 30px;"></i> <b>Tournament:</b> ${session_match.setup.tournament}
			</label>
			
			<label id="selected_inning" class="col-sm-4 col-form-label text-left" style="font-weight: bolder;">Selected Innings	:</label>
			
			<div class="row">
				<table class="fantasy-table">
				  <!-- Team Scores -->
				  <tr>
				    <td class="score-card team" id ="team1">
				      <label id="inning1_teamName"> </label>
				    </td>
				    <td class="score-card team1" id ="score">
				      <label id="inning1_teamScore_lbl"> </label>
				    </td>
				  </tr>
				  <tr>
				  	<td class="score-card team" id ="team2">
				      <label id="inning2_teamName"> </label>
				    </td>
				    <td class="score-card team2" id ="score">
				      <label id="inning2_teamScore_lbl"> </label>
				    </td>
				  </tr>
				
				  <!-- Batting Cards -->
				  <tr>
				    <td class="score-card team">
				      <label id="inning1_battingcard1_lbl">
				        <img src="<c:url value='/resources/Images/batter.png' />" alt="Batter">
				        <span id="batter1_text"> </span>
				      </label>
				    </td>
				    <td class="score-card team">
				      <label id="inning1_battingcard2_lbl">
				        <img src="<c:url value='/resources/Images/batter.png' />" alt="Batter">
				        <span id="batter2_text"> </span>
				      </label>
				    </td>
				  </tr>
				
				  <!-- Spacer -->
				  <tr><td colspan="2" style="height:40px;"></td></tr>
				
				  <!-- Bowling Card -->
				  <tr>
				    <td class="score-card" colspan="2" style="text-align: center;">
				      <label id="inning1_bowlingcard_lbl">
				        <img id = "bowler" src="<c:url value='/resources/Images/bowler.png' />" alt="Bowler">
				        <span id="bowler_text"> </span>
				      </label>
				    </td>
				  </tr>
				  <tr>
				    <td class="score-card" colspan="2" style="text-align: center;">
				      <label id="inning1_bowlingcard_lbl">
				        <span id="thisover_text"> </span>
				      </label>
				    </td>
				  </tr>
				</table>
			</div>
	      	<c:if test="${(session_configuration.broadcaster == 'ISPL' || 
	      					session_configuration.broadcaster == 'T20_MUMBAI' || 
	      					session_configuration.broadcaster == 'NPL' ||
	      					session_configuration.broadcaster == 'APL' ||
	      					session_configuration.broadcaster == 'LEGENDS-90')}">
			    <br>
			   <div class="custom-toggle-wrapper">
				    <div class="custom-toggle">
				        <label for="speedOnOrOff">
				            <i class="fas fa-tachometer-alt"></i> Speed
				            <input type="checkbox" id="speedOnOrOff" name="speedOnOrOff" onclick="processUserSelection(this)">
				            <span class="slider"></span>
				        </label>
				    </div>
				    <div class="custom-toggle">
				        <label for="audioOnOrOff">
				            <i class="fas fa-volume-up"></i> Audio
				            <input type="checkbox" id="audioOnOrOff" name="audioOnOrOff" onclick="processUserSelection(this)">
				            <span class="slider"></span>
				        </label>
				    </div>
				    <br>
				   	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="headToHead_file" id="headToHead_file" onclick="processUserSelection(this)"> Head To Head </button> 
				  </div>
			</c:if>
			<c:if test="${(session_configuration.broadcaster == 'MPL')}">
			    <br>
			   <div class="custom-toggle-wrapper">
				    <div class="custom-toggle">
				        <label for="audioOnOrOff">
				            <i class="fas fa-volume-up"></i> Audio
				            <input type="checkbox" id="audioOnOrOff" name="audioOnOrOff" onclick="processUserSelection(this)">
				            <span class="slider"></span>
				        </label>
				    </div>
			  </div>
			</c:if>
		  </div>
	  </div>
     </div>
  </div>
<input type="hidden" id="which_keypress" name="which_keypress"/>
<input type="hidden" id="which_inning" name="which_inning" value="${which_inning}"/>
<input type="hidden" name="selected_broadcaster" id="selected_broadcaster" value="${session_configuration.broadcaster}"/>
<input type="hidden" name="selected_infobar" id="selected_infobar" value="${session_configuration.whichInfobar}"/>
<input type="hidden" name="selected_second_broadcaster" id="selected_second_broadcaster" value="${session_configuration.secondaryBroadcaster}"/>
<input type="hidden" name="selected_match_max_overs" id="selected_match_max_overs" value="${session_match.setup.maxOvers}"/>
<input type="hidden" id="matchFileTimeStamp" name="matchFileTimeStamp" value="${session_match.setup.matchFileTimeStamp}"></input>
</form:form>
 <script type="text/javascript">
    var helpPageOpened = false, helpWindow = null; 
    document.addEventListener('keydown', function(event) {
        if (event.ctrlKey && event.shiftKey && event.key === 'H') {
            event.preventDefault();           
            var helpPageUrl = '<c:url value="/Help"/>';
            if (!helpPageOpened || (helpWindow && helpWindow.closed)) {
                helpWindow = window.open(helpPageUrl, '_blank'); 
                helpPageOpened = true; 
                if (helpWindow) {
                    helpWindow.onbeforeunload = function() {
                        helpPageOpened = false; 
                    };
                }
            } else {
                helpWindow.focus();
                helpWindow.location.reload();
            }
        }
    });
</script>

</body>
</html>