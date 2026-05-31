var session_match, session_caption, session_animation,isSplitScorecard = false;
var selected_options = [];
let TeamScore = "";
function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START_WAIT_TIMER': 
		$('.spinner-border').show();
		$(':button').prop('disabled', true);
		break;
	case 'END_WAIT_TIMER': 
		$('.spinner-border').hide();
		$(':button').prop('disabled', false);
		break;
	}
}
function onPageLoadEvent(whichPage){
	switch(whichPage){
	case 'OUTPUT':
		$("#select_graphic_options_div").empty();
		document.getElementById('selected_inning').innerHTML = 'Selected Innings: ' + document.getElementById('which_inning').value;
		initialiseSelectedOptionsList();
		
		document.getElementById('inning1_teamScore_lbl').setAttribute('style', 'font-family: "Bungee-Regular", sans-serif !important;');
		document.getElementById('inning2_teamScore_lbl').setAttribute('style', 'font-family: "Bungee-Regular", sans-serif !important;');
		
		if(document.getElementById('which_inning').value == 1||document.getElementById('which_inning').value == 3){
			
			document.getElementById('inning1_teamName').style.backgroundColor ='#990000';
			document.getElementById('inning1_teamName').style.color ='white';
			
			document.getElementById('inning2_teamScore_lbl').style.backgroundColor ='';
			document.getElementById('inning2_teamScore_lbl').style.color ='';
			
			document.getElementById('inning1_teamScore_lbl').style.backgroundColor ='#990000';
			document.getElementById('inning1_teamScore_lbl').style.color ='white';
			
			document.getElementById('inning2_teamName').style.backgroundColor ='';
			document.getElementById('inning2_teamName').style.color ='';
				
		}else if(document.getElementById('which_inning').value == 2|| document.getElementById('which_inning').value == 4){
			document.getElementById('inning2_teamName').style.backgroundColor ='#990000';
			document.getElementById('inning2_teamName').style.color ='white';
			
			document.getElementById('inning1_teamScore_lbl').style.backgroundColor ='';
			document.getElementById('inning1_teamScore_lbl').style.color ='';
			
			document.getElementById('inning2_teamScore_lbl').style.backgroundColor ='#990000';
			document.getElementById('inning2_teamScore_lbl').style.color ='white';
			
			document.getElementById('inning1_teamName').style.color = '';
			document.getElementById('inning1_teamName').style.backgroundColor = '';

		}
		//addItemsToList('HELP-FILE',session_match);
		break;
	}
}
function initialiseSelectedOptionsList()
{
	selected_options = [];
	for(var i = 1; i <= 5; i++) {
	    selected_options.push('');
	}
}
function initialiseForm(whatToProcess,dataToProcess)
{
	switch (whatToProcess) {
	case 'UPDATE-CONFIG':
		
		document.getElementById('configuration_file_name').value = $('#select_configuration_file option:selected').val();
		document.getElementById('select_cricket_matches').value = dataToProcess.filename;
		
		document.getElementById('select_broadcaster').value = dataToProcess.broadcaster;
		document.getElementById('select_second_broadcaster').value = dataToProcess.secondaryBroadcaster;
		document.getElementById('qtIPAddress').value = dataToProcess.qtIpAddress;
		document.getElementById('qtPortNumber').value = dataToProcess.qtPortNumber;
		document.getElementById('vizIPAddress').value = dataToProcess.primaryIpAddress;
		document.getElementById('vizPortNumber').value = dataToProcess.primaryPortNumber;
		document.getElementById('primaryVariousOptions').value = dataToProcess.primaryVariousOptions;
		document.getElementById('vizSecondaryIPAddress').value = dataToProcess.secondaryIpAddress;
		document.getElementById('vizSecondaryPortNumber').value = dataToProcess.secondaryPortNumber;
		document.getElementById('vizTertiaryIPAddress').value = dataToProcess.tertiaryIpAddress;
		document.getElementById('vizTertiaryPortNumber').value = dataToProcess.tertiaryPortNumber;
		document.getElementById('selectInfobar').value = dataToProcess.whichInfobar;
		document.getElementById('Category').value = dataToProcess.category;
		processUserSelection($('#select_second_broadcaster'));
		break;
		
	case 'UPDATE-MATCH-ON-OUTPUT-FORM':
	
		if(dataToProcess.match.matchStats != null && dataToProcess.match.matchStats.overData != null) {
			thisOverArr = dataToProcess.match.matchStats.overData.thisOverTxt.split(",");
		}

		dataToProcess.match.inning.forEach(function(inn){
			if(inn.inningNumber<=2){
				//ChallengeScore
				if ($('#selected_broadcaster').val().toUpperCase() === 'ISPL') {
				    document.getElementById('inning1_teamName').innerHTML = dataToProcess.match.inning[0].batting_team.teamName1;
				    document.getElementById('inning1_teamScore_lbl').innerText = ChallengeScoreRUNS(dataToProcess, dataToProcess.match.inning[0]) + 
				        (parseInt(dataToProcess.match.inning[0].totalWickets) < 10 ? "-" + dataToProcess.match.inning[0].totalWickets : "") + 
				        ' [' + parseInt(dataToProcess.match.inning[0].totalOvers) + '.' + parseInt(dataToProcess.match.inning[0].totalBalls) + ']';
					
				    document.getElementById('inning2_teamName').innerHTML = dataToProcess.match.inning[1].batting_team.teamName1;
				    document.getElementById('inning2_teamScore_lbl').innerText = ChallengeScoreRUNS(dataToProcess, dataToProcess.match.inning[1]) + 
				        (parseInt(dataToProcess.match.inning[1].totalWickets) < 10 ? "-" + dataToProcess.match.inning[1].totalWickets : "") + 
				        ' [' + parseInt(dataToProcess.match.inning[1].totalOvers) + '.' + parseInt(dataToProcess.match.inning[1].totalBalls) + ']';
				} else {
				    document.getElementById('inning1_teamName').innerHTML = dataToProcess.match.inning[0].batting_team.teamName1;
				    document.getElementById('inning1_teamScore_lbl').innerText = parseInt(dataToProcess.match.inning[0].totalRuns) + 
				        (parseInt(dataToProcess.match.inning[0].totalWickets) < 10 ? "-" + dataToProcess.match.inning[0].totalWickets : "") + 
				        ' [' + parseInt(dataToProcess.match.inning[0].totalOvers) + '.' + parseInt(dataToProcess.match.inning[0].totalBalls) + ']';
				
				    document.getElementById('inning2_teamName').innerHTML = dataToProcess.match.inning[1].batting_team.teamName1;
				    document.getElementById('inning2_teamScore_lbl').innerText = parseInt(dataToProcess.match.inning[1].totalRuns) + 
				        (parseInt(dataToProcess.match.inning[1].totalWickets) < 10 ? "-" + dataToProcess.match.inning[1].totalWickets : "") + 
				        ' [' + parseInt(dataToProcess.match.inning[1].totalOvers) + '.' + parseInt(dataToProcess.match.inning[1].totalBalls) + ']';
				}
			 }else if (inn.inningNumber > 2 && inn.inningNumber <= 4 && inn.isCurrentInning=='YES') {
				    document.getElementById('inning1_teamName').innerHTML = dataToProcess.match.inning[2].batting_team.teamName1;
				    var team1 ; var team2 ;
				    if(dataToProcess.match.inning[2].batting_team.teamName1==dataToProcess.match.inning[0].batting_team.teamName1){
					   team1 = parseInt(dataToProcess.match.inning[0].totalRuns) + (parseInt(dataToProcess.match.inning[0].totalWickets) < 10 
					  				? "-" + dataToProcess.match.inning[0].totalWickets : "") + " & ";
				       team2 = parseInt(dataToProcess.match.inning[1].totalRuns) + (parseInt(dataToProcess.match.inning[1].totalWickets) < 10 
				            ? "-" + dataToProcess.match.inning[1].totalWickets : "") + " & ";
				    }else{
					   team2 = parseInt(dataToProcess.match.inning[0].totalRuns) + (parseInt(dataToProcess.match.inning[0].totalWickets) < 10 
				            ? "-" + dataToProcess.match.inning[0].totalWickets : "") + " & ";
				       team1 = parseInt(dataToProcess.match.inning[1].totalRuns) + (parseInt(dataToProcess.match.inning[1].totalWickets) < 10 
				            ? "-" + dataToProcess.match.inning[1].totalWickets : "") + " & ";
				    }
				    document.getElementById('inning1_teamScore_lbl').innerText = team1 + parseInt(dataToProcess.match.inning[2].totalRuns) + 
				        (parseInt(dataToProcess.match.inning[2].totalWickets) < 10 ? "-" + dataToProcess.match.inning[2].totalWickets : "") + 
				        ' [' + parseInt(dataToProcess.match.inning[2].totalOvers) + '.' + parseInt(dataToProcess.match.inning[2].totalBalls) + ']';
				
				    document.getElementById('inning2_teamName').innerHTML = dataToProcess.match.inning[3].batting_team.teamName1;
				    document.getElementById('inning2_teamScore_lbl').innerText = team2 +  parseInt(dataToProcess.match.inning[3].totalRuns) + 
				        (parseInt(dataToProcess.match.inning[3].totalWickets) < 10 ? "-" + dataToProcess.match.inning[3].totalWickets : "") + 
				        ' [' + parseInt(dataToProcess.match.inning[3].totalOvers) + '.' + parseInt(dataToProcess.match.inning[3].totalBalls) + ']';
				}

			if(inn.isCurrentInning == 'YES'){
				inn.battingCard.forEach(function(bc){
					if(inn.partnerships != null && inn.partnerships.length > 0) {
						inn.partnerships.forEach(function(par){
							if(bc.playerId == par.firstBatterNo) {
								if(bc.status == 'OUT'){
									document.getElementById('batter1_text').innerHTML = bc.player.full_name + ' ' + bc.runs + '(' + bc.balls + ')' ;
									document.getElementById('batter1_text').style.color = 'red';
								}else{
									if(bc.onStrike == 'YES'){
										document.getElementById('batter1_text').innerHTML = bc.player.full_name +
										'&nbsp;<i class="fas fa-caret-right arrow"></i> ' +' ' + bc.runs + '(' + bc.balls + ')';
									}else{
										document.getElementById('batter1_text').innerHTML = bc.player.full_name + ' ' + bc.runs + '(' + bc.balls + ')' ;
									}
									document.getElementById('batter1_text').style.color = 'green';
								}
							}
							else if(bc.playerId == par.secondBatterNo) {
								if(bc.status == 'OUT'){
									document.getElementById('batter2_text').innerHTML = bc.player.full_name + ' ' + bc.runs + '(' + bc.balls + ')' ;
									document.getElementById('batter2_text').style.color = 'red';
								}else{
									if(bc.onStrike == 'YES'){
										document.getElementById('batter2_text').innerHTML = bc.player.full_name + ' '+
										'&nbsp;<i class="fas fa-caret-right arrow"></i> '  + bc.runs + '(' + bc.balls + ')' ;
									}else{
										document.getElementById('batter2_text').innerHTML = bc.player.full_name + ' ' + bc.runs + '(' + bc.balls + ')' ;
									}
									document.getElementById('batter2_text').style.color = 'green';
								}
							}
						});
					}
				});
				inn.bowlingCard.forEach(function(boc){
					if(boc.status == 'CURRENTBOWLER' || boc.status == 'LASTBOWLER'){
						document.getElementById('bowler_text').innerHTML = boc.player.full_name + ' ' + boc.wickets 
									+ '-' + boc.runs + ' [' + boc.overs + '.' + boc.balls + ']'+'&emsp;&ensp;';
						document.getElementById('thisover_text').innerHTML = 'THIS OVER : ' + thisOverArr.map(s => s.replace("WIDE", "WD")
								.replace("NO_BALL", "NB").replace("LEG_BYE", "LB").replace("BYE", "B").replace("PENALTY", "PN")
								.replace("LOG_WICKET", "W").replace("WICKET", "W").replace("BOUNDARY","")).reverse().join(" , ")			
					}
				});	
			}
		});
		break;
	}
}
function processUserSelection(whichInput)
{
  switch ($(whichInput).attr('name')) {
	case 'load_scene_btn':
      	document.initialise_form.method = 'post';
      	document.initialise_form.action = 'output';
      	document.initialise_form.submit();
		break;
	case 'headToHead_file':
		processCricketProcedures('HEAD_TO_HEAD_FILE');
		break;
	case 'cancel_graphics_btn':
		processCricketProcedures("CANCLE-GRAPHICS");
		$("#select_graphic_options_div").empty();
		document.getElementById('select_graphic_options_div').style.display = 'none';
		$("#captions_div").show();
		document.getElementById("stats-container").innerHTML = "";
		break;
	case 'checkPlayerData':
		processCricketProcedures("GRAPHICS_PREVIEW-OPTIONS", $('#which_keypress').val() + ',' + selected_options.toString());
		break;
	case 'populate_btn': 
		if($(key_press_hidden_input)) {
			processCricketProcedures("POPULATE-GRAPHICS", $('#key_press_hidden_input').val() + ',' + selected_options.toString());
		} else {
			processCricketProcedures("POPULATE-GRAPHICS", $('#which_keypress').val() + ',' + selected_options.toString());
		}
		break;
	case 'change_on':
		if($(key_press_hidden_input)) {
			processUserSelectionData('IMPACT-CHANGE-ON', 'Shift_I');
			processUserSelection($('#cancel_graphics_btn').attr('value','cancel_graphics_btn'));
		}else if($('#which_keypress').val() == 'Shift_T' && $('#selected_broadcaster').val().toUpperCase() == 'T20_MUMBAI'){
			processCricketProcedures('PLAYING-XI-CHANGE-ON');
		}else if($('#which_keypress').val() == 'Shift_I'){
			processUserSelectionData('IMPACT-CHANGE-ON', 'Shift_I');
			processUserSelection($('#cancel_graphics_btn').attr('value','cancel_graphics_btn'));	
		}		
		break;
	case 'change_on_profile':
		processCricketProcedures('POPULATE-PROFILE_IN_AT');
		break;
	case 'pop_up_change_on':
		dataToProcess = $('#which_keypress').val() + '_change_on' + ',' + selected_options.toString();
		processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
		//alert($('#which_keypress').val() + '_change_on' + ',' + selected_options.toString());
		//processCricketProcedures($('#which_keypress').val() + '_change_on' + ',' + selected_options.toString(), null);
		break;
	case 'highlightProfile':
		processCricketProcedures('highlightProfile' + ',' + selected_options.toString(), null);
		break;
	case 'L3_Player_ChangeON':
		dataToProcess =$('#which_keypress').val() + ',' + selected_options.toString()+",Player_ChangeON";
		processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
		break;
	case 'highlightLeader':
		processCricketProcedures('highlightLeader' + ',' + selected_options.toString(), null);
		break;
	case 'speedOnOrOff':
		processCricketProcedures('TURN_ON_OR_OFF_SPEED');
		break;
	case 'audioOnOrOff':
		processCricketProcedures('TURN_ON_OR_OFF_AUDIO');
		break;		
	}	
}

function processUserSelectionData(whatToProcess,dataToProcess)
{
	switch (whatToProcess) {
	case 'IMPACT-CHANGE-ON':
		processCricketProcedures('IMPACT-CHANGE-ON');
		break;
	case 'LOGGER_FORM_KEYPRESS':
		document.getElementById('which_keypress').value = dataToProcess;
		//alert('dataToProcess = ' + dataToProcess);
		switch(dataToProcess) {
		case 'Escape':
			$("#select_graphic_options_div").empty();
			document.getElementById('select_graphic_options_div').style.display = 'none';
			$("#captions_div").show();
			break;
		case 'Alt_r':
			processCricketProcedures('RE_READ_DATA');
			break;
		case 'Alt_v':
			processCricketProcedures('DB_DATA_READ');
			break;	
		case 'Control_ ':
			processCricketProcedures('CLEAR-ALL-WITH-INFOBAR');
			break;
		case ' ':
			processCricketProcedures('CLEAR-ALL');
			break;
		case '`':
			processCricketProcedures('ANIMATE-OUT-ALL_INFOBAR_PART');
			break;
		case '=':
			processCricketProcedures('ANIMATE-OUT-SECOND_PLAYING');
			break;
		case '1': case '2': case '3': case '4':
			if(session_match.setup.maxOvers > 0 && session_match.setup.matchType !='TEST'&& session_match.setup.matchType != 'FC' ){
				switch (dataToProcess) {
				case '3': case '4': // Key 1 to 4
					alert("3rd and 4th inning NOT available in a limited over match");
					return false;
				}				
			}
			document.getElementById('which_inning').value = dataToProcess;
			document.getElementById('selected_inning').innerHTML = 'Selected Innings: ' + dataToProcess;
			
			document.getElementById('inning1_teamScore_lbl').setAttribute('style', 'font-family: "Bungee-Regular", sans-serif !important;');
			document.getElementById('inning2_teamScore_lbl').setAttribute('style', 'font-family: "Bungee-Regular", sans-serif !important;');
				
			if(dataToProcess == 1 ||dataToProcess == 3){
				document.getElementById('inning1_teamName').style.backgroundColor ='#990000';
				document.getElementById('inning1_teamName').style.color ='white';
				
				document.getElementById('inning2_teamName').style.backgroundColor ='';
				document.getElementById('inning2_teamName').style.color ='';
				
				document.getElementById('inning2_teamScore_lbl').style.backgroundColor ='';
				document.getElementById('inning2_teamScore_lbl').style.color ='';
				
				document.getElementById('inning1_teamScore_lbl').style.backgroundColor ='#990000';
				document.getElementById('inning1_teamScore_lbl').style.color ='white';
				
				
			}else if(dataToProcess == 2 ||dataToProcess == 4){
				document.getElementById('inning2_teamName').style.backgroundColor = '#990000';
				document.getElementById('inning2_teamName').style.color = 'white';
								
				document.getElementById('inning1_teamName').style.backgroundColor ='';
				document.getElementById('inning1_teamName').style.color ='';
				
				document.getElementById('inning1_teamScore_lbl').style.backgroundColor ='';
				document.getElementById('inning1_teamScore_lbl').style.color ='';
				
				document.getElementById('inning2_teamScore_lbl').style.backgroundColor ='#990000';
				document.getElementById('inning2_teamScore_lbl').style.color ='white';
			}
			break;
			
		case '-':
			if(confirm('It will Also Delete Your Preview from Directory...\r\n \r\nAre You Sure To Animate Out? ') == true){
				processCricketProcedures('ANIMATE-OUT-GRAPHICS');
			}
			break;
			
		case 'Shift_+':
			processCricketProcedures('ANIMATE-OUT-BOTTOM');
			break;
		
		case 'Control_-':
			processCricketProcedures('ANIMATE-OUT-TAPE');
			break;
			
		case 'Alt_u':
			processCricketProcedures('ANIMATE-OUT-TARGET');
			break;
			
		case 'Alt_-':
			if(confirm('Animate Out Infobar? ') == true){
				processCricketProcedures('ANIMATE-OUT-INFOBAR');
			}
			break;
		case 'Alt_=':
			if(confirm('Animate Out Ident? ') == true){
				processCricketProcedures('ANIMATE-OUT-IDENT');
			}
			break;
		default:
			
			switch($('#which_inning').val()) {
			case 1: case 2: case 3: case 4:
				alert('Selected inning must be between 1 to 4 [found ' 
					+ document.getElementById('which_inning').value + ']');
				return false;
			}
			switch(dataToProcess) {
			case 'F1':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ISPL': case 'VIDARBHA':
					addItemsToList(dataToProcess,null);
					break;
				case 'ICC-U19-2023': case 'BENGAL-T20': case 'NPL': case 'LEGENDS-90': case 'MPL': case 'T20_MUMBAI': case 'APL': 
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
					break;
				}
				break;
			case 'F2':	
		      switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ISPL':
					addItemsToList(dataToProcess,null);
					break;
				case 'ICC-U19-2023': case 'BENGAL-T20': case 'NPL': case 'LEGENDS-90': case 'MPL': case 'T20_MUMBAI': case 'APL': case 'VIDARBHA':
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
					break;
				}
				break;
			case 'F4': case 'Shift_K': case "Alt_Shift_J":
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ISPL': case 'BENGAL-T20': case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'T20_MUMBAI': case 'APL': case 'VIDARBHA':
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
					break;
				case 'ICC-U19-2023':
					addItemsToList(dataToProcess,null);
					break;
				}
				break;
			case 'Control_F11':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ICC-U19-2023': case 'LEGENDS-90': case 'VIDARBHA':
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
					break;
				case 'BENGAL-T20': case 'NPL': case 'ISPL':  case 'MPL': case 'APL': case 'T20_MUMBAI':
					addItemsToList(dataToProcess,null);
					break;
				}
				break;
			case 'p':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI': case 'ISPL': 
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
					break;
				case 'ICC-U19-2023': case 'BENGAL-T20': case 'NPL': case 'MPL': case 'LEGENDS-90': case 'APL':
					addItemsToList(dataToProcess,null);
					break;
				}
				break;
			case 'Control_p': case 'Alt_F7':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ISPL': case 'BENGAL-T20': case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'T20_MUMBAI': 
				case 'APL': case 'VIDARBHA':
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
					break;
				case 'ICC-U19-2023': 
					addItemsToList(dataToProcess,null);
					break;
				}
				break;
			case 'Control_h':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'BENGAL-T20': case 'APL': case 'VIDARBHA':
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value
					processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
					break;
				case 'ICC-U19-2023': case 'ISPL': case 'T20_MUMBAI':
					addItemsToList(dataToProcess,null); 
					break;
				}
				break;
			case 'F7':
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'BENGAL-T20': case 'NPL': case 'ISPL': case 'LEGENDS-90':  case 'MPL': case 'T20_MUMBAI': case 'APL': case 'VIDARBHA':
					addItemsToList(dataToProcess,null); 
					break;
				}
			break;		
			case 'F11':
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'BENGAL-T20': case 'NPL': case 'ISPL': case 'LEGENDS-90':  case 'MPL': case 'T20_MUMBAI': case 'APL': case 'VIDARBHA':
					addItemsToList(dataToProcess,null); 
					break;
				}
			break;
			case "p":
			  switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ISPL':
					processCricketProcedures("POPULATE-GRAPHICS", 
					dataToProcess + ',' + document.getElementById('which_inning').value);
					break;
				default:
					addItemsToList(dataToProcess,null); 
					break;
			 	}
				break;
			case 'Control_7': case 'Control_8':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ISPL':
					addItemsToList(dataToProcess,null);
					break;
			 	}
				break;
			case 'Control_Shift_M':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'XYZ':
					processCricketProcedures("POPULATE-GRAPHICS",dataToProcess + ',' + document.getElementById('which_inning').value);
					break;
				default :
					 addItemsToList(dataToProcess,null); 
					break;
			 	}
				break;
			case 'r':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI':
					addItemsToList(dataToProcess,null); 
					break;
				default :
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
					break;
			 	}
				break;
			case 'Alt_5':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ICC-U19-2023': case 'BENGAL-T20': case 'NPL': case 'MPL': case 'LEGENDS-90': case 'APL': case 'ISPL':
				case 'T20_MUMBAI':
					addItemsToList(dataToProcess,null); 
					break;
				 }
				break;
			case 'Alt_6':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ICC-U19-2023': case 'T20_MUMBAI':
					addItemsToList(dataToProcess,null); 
					break;
				 }
				break;
			case 'Alt_1':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI':
					if(session_match.setup.matchType == 'SUPER_OVER'){
						session_match.match.inning.forEach(function(inn){
							if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
								if(inn.inningNumber == 2){
									addItemsToList(dataToProcess,null);
								}
							}
						});
					} 
					break;
				default:
					addItemsToList(dataToProcess,null); 
					break;
				}
				break;
			case 'Alt_F9':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ICC-U19-2023': case 'T20_MUMBAI':
					addItemsToList(dataToProcess,null); 
					break;
				 }
				break;
			case 'Control_F7':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI':
					addItemsToList(dataToProcess,null); 
					break;
				default :
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
					break;
			 	}
				break;
					
			case 'Shift_C': case 'Control_Shift_Q': case 'h':
			case 'F12': case 'Alt_1': case 'Alt_2': case 'Alt_7': // case 'Alt_8': case 'Alt_3': case 'Alt_4': case 'F7': case 'F11':
			case 'Control_F5': case 'Shift_T': case 'Control_F9': case 'F5': case 'F6': case 'Alt_w':  case 'Control_j': case 'Alt_F8':
			case 'F8': case 'F9':  case 'u': case 'q': case 'Shift_F5': case 'Shift_F9': case 'Shift_F6': case 'Control_y': case 'Shift_F8':
			case 'Shift_O': case 'g': case 'y': case 'Control_g': case 'Control_s': case 'Control_f': // case 'Control_h':
			case 'Alt_F12': case 'l': case 'Alt_m': case 'Alt_n': case 'Control_b': case 'Alt_F10': case 'Alt_d':
			case 'Control_p': case 'Shift_F4': case 'Alt_F1': case 'Alt_F2': case 'Shift_E': case 'Shift_P': case 'Shift_Q': case 'Shift_F':
			case 'Alt_F6': case 'Shift_R': case 'Shift_A': case 'Alt_c': case 'Control_F12': case 'Shift_F12': case 'Shift_F7': case 'Control_Shift_F9':
			case 'Shift_Z': case 'Shift_X': case 'Control_i': case 'Control_Shift_E': case 'Control_Shift_F': case 'Control_Shift_P': case 'Control_Shift_F1': 
			case 'Control_Shift_D': case 'Alt_Shift_Z': case 'Control_Shift_F7': case 'Shift_I': case 'Alt_Shift_C': case 'Control_Shift_F2':  case 'Control_Shift_F4':
			case 'Control_Shift_U': case 'Control_Shift_V': case 'Control_Shift_O':case 'Control_u':case 'Shift_W':case "Alt_b": case "Shift_G": case 'Control_5': 
			case 'Control_F8': case 'Control_Shift_F11': case 'Alt_/': case "Alt_Shift_B":case 'Alt_Shift_F4':case 'Alt_Shift_F6': case 'Alt_Shift_F7': case 'Shift_L':
			case 'Alt_x':
				addItemsToList(dataToProcess,null); 
				break;
				
			//changed shift_f11 to control_f11
			case 'Shift_F10': case 'm': case 'Control_F1': case 'Control_a': case "Control_Shift_F10": case 'Alt_o':  case 'Shift_F3': case 'd': case 'e': case 'Control_F6': 
			case 'Control_k': case 'Control_F10': case 'Control_F3':  case 'a': case 't': case 'n': case 'Shift_F1': case 'Shift_F2': case 'Shift_D': 
			case 'Control_q': case 'Control_b': case 'o': case 'Control_F2': case 'b': case 'Alt_F11': case 'Shift_U': case 'Alt_j': case 'Alt_h': case 'Alt_Shift_L':	 
			//case 'Shift_F':
			case '.': case '/': case 'Shift_V': case 'Alt_i': case 'b': case 'Shift_B': case 'Control_Shift_B': case 'Alt_Shift_F3': case 'Control_Shift_R': 
			case 'Control_Shift_F3': case 'Control_Shift_H': case "Control_Shift_I": case "Alt_Shift_D":case "Alt_Shift_E":case "Alt_Shift_F":case "Alt_Shift_G":
			case "Alt_Shift_H": case "Control_Shift_A": case 'Control_6': case "Alt_Shift_O": case 'Alt_F5': case 'Control_Alt_F1': case 'Control_Alt_F2': 
			case 'Control_Shift_K': case "Alt_Shift_F8": case "Alt_k": case 'Alt_Shift_F11': case 'Alt_Shift_F10': case 'Alt_Shift_F12': case 'Alt_Shift_F9':
			case 'Alt_Shift_F2':
				/*switch(dataToProcess){
				case 'Shift_F':
					count++;
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value + ',' + count;
				break;
				default:
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
				}*/
				dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
				processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
				break;
			//These buttons will animate in & animate out graphics
			case 'Alt_p':
				if(session_animation != null && session_animation.specialBugOnScreen == 'TOSS') {
					processCricketProcedures("ANIMATE-OUT-GRAPHICS", dataToProcess);
				} else {
					addItemsToList(dataToProcess,null);
					//processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
				}
				break;
			case 'Control_Shift_(':
				addItemsToList(dataToProcess,null);
				break;	
			case 'Control_4': 
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'NPL': case 'LEGENDS-90': case 'ISPL':  case 'MPL': case 'T20_MUMBAI': case 'BENGAL-T20': case 'APL': case 'VIDARBHA':
					switch(dataToProcess) {
						case 'Control_4':
							dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
							processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
							break;	
					}
					
					break;
				default:
					addItemsToList(dataToProcess,null);
					break;	
				}
				break;
			case 'Control_Alt_3':
				dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
				processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
				break;
				
			//All key presses which doesn't require graphics population will come here
			case '5': case '6': case '7': case '8': case '9': case 'Alt_Shift_Q': case 'Alt_Shift_P':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'NPL': case 'LEGENDS-90': case 'ISPL':  case 'MPL': case 'T20_MUMBAI': case 'BENGAL-T20': case 'APL': case 'VIDARBHA':
					switch(dataToProcess) {
						case '6': case '9': 
							dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
							processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
							break;
						case 'Alt_Shift_Q': case 'Alt_Shift_P':
							switch($('#selected_broadcaster').val().toUpperCase()){
								case 'MPL': case 'LEGENDS-90': case 'BENGAL-T20': case 'NPL': case 'APL': case 'VIDARBHA':
									addItemsToList(dataToProcess,null);
									break;
								case 'ISPL':
									dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
									processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
									break;
							}
							break;
						case '8':
							dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
							processCricketProcedures("ANIMATE-IN-GRAPHICS", dataToProcess);
							break;	
						case '5':
							processCricketProcedures("ANIMATE-IN-GRAPHICS", dataToProcess);
							break;
					}
					break;
				case 'ISPL':
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("ANIMATE-IN-GRAPHICS", dataToProcess);
					break;
				case 'ICC-U19-2023':
					processCricketProcedures("QUIDICH-COMMANDS", dataToProcess);
					break;
				}
				
				break;
			case 'Alt_f':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'LEGENDS-90':
					addItemsToList(dataToProcess,null); 
					break;
				default:
					dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
					processCricketProcedures("ANIMATE-IN-GRAPHICS", dataToProcess);
					break;	
				}
				break;	
			case 'Alt_g': case 'ArrowDown': case 'ArrowUp': case 'w': case 'i': case 'f': case 's': case '0': case ';': case 'Alt_e': case 'Shift_)':
			case 'Control_2': case 'Control_3': case 'ArrowLeft': case 'ArrowRight':case 'Shift_(':case 'Shift_*': case 'Control_Shift_*': case 'Alt_y':
				dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
				processCricketProcedures("ANIMATE-IN-GRAPHICS", dataToProcess);
				break;
			case 'Control_1':
				dataToProcess = dataToProcess + ',' + document.getElementById('which_inning').value;
				processCricketProcedures("POPULATE-GRAPHICS", dataToProcess);
				break;
			default:
				processCricketProcedures("GRAPHICS-OPTIONS", dataToProcess);
				break;
			}
		}
		break;
	}
}
function processCricketProcedures(whatToProcess,dataToProcess)
{
	var valueToProcess = dataToProcess;
	switch(whatToProcess) {
	
	case 'TURN_ON_OR_OFF_SPEED':
		valueToProcess = $('#speedOnOrOff').is(":checked");
		break;	
	case 'TURN_ON_OR_OFF_AUDIO':
		valueToProcess = $('#audioOnOrOff').is(":checked");
		break;	
	case 'QUIDICH-COMMANDS':
		valueToProcess = dataToProcess;
		break;
	case 'GET-CONFIG-DATA':
		valueToProcess = $('#select_configuration_file option:selected').val();
		break;
	case 'READ-MATCH-AND-POPULATE':
		valueToProcess = $('#matchFileTimeStamp').val();
		break;
	case 'GET-CATEGORY-DATA':
		valueToProcess = $('#Category option:selected').val();
		break;

	}		
	$.ajax({    
        type : 'Get',     
        url : 'processCricketProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + valueToProcess, 
        dataType : 'json',
        success : function(data) {
			switch(whatToProcess) {
			case 'HEAD_TO_HEAD_FILE':
				alert(data.match.matchFileName + ' H2H FILE IS CREATED');
				break;
			case 'GET-CONFIG-DATA':
				initialiseForm('UPDATE-CONFIG',data);
				break;
			case 'GET-CATEGORY-DATA':
			    var matchSelect = document.getElementById('select_cricket_matches');
			    matchSelect.innerHTML = '';
			    if (data.matchFiles && data.matchFiles.length > 0) {
			        data.matchFiles.forEach(function(fileName) {
			            var option = document.createElement('option');
			            option.value = fileName;
			            option.text = fileName;
			            matchSelect.appendChild(option);
			        });
			    } else {
			        var option = document.createElement('option');
			        option.value = '';
			        option.text = '-- No matches found --';
			        matchSelect.appendChild(option);
			    }
			    break;
			case 'READ-MATCH-AND-POPULATE': case "RE_READ_DATA": case 'DB_DATA_READ':
				if(data){
					session_match = data;
					initialiseForm('UPDATE-MATCH-ON-OUTPUT-FORM',data);
				}
				if(whatToProcess == "RE_READ_DATA" || whatToProcess == 'DB_DATA_READ'){
					alert("Data is Loaded");
				}
				break;
			case 'SHOW_SPEED':
				if(data == true){
					document.getElementById('speed_lbl').innerHTML = 'Show Speed: ' + 'YES';
				}else if(data == false){
					document.getElementById('speed_lbl').innerHTML = 'Show Speed: ' + 'NO';
				}
				break;
			case 'GRAPHICS_PREVIEW-OPTIONS':
				displayStats(data);
				break;
			default:
				switch(whatToProcess) {	
				case 'POPULATE-GRAPHICS': case 'POPULATE-PROFILE_IN_AT':
					if(data == 'OK') {
						session_caption = data;
						if(confirm('Animate In?') == true) {
							$('.my_waiting_modal').modal('hide');
							//setTimeout(function(){$('.my_waiting_modal').modal('hide');},3000);
							processCricketProcedures(whatToProcess.replace('POPULATE-', 'ANIMATE-IN-'),dataToProcess);
							if(dataToProcess.split(',')[0] == 'Shift_F') {
									if($('#selectWicketSequence option:last').val()){
										
									}
									$('#selectWicketSequence option:selected').next().prop('selected', true);
									document.getElementById('selectWicketSequence').setAttribute('onchange','setDropdownOptionForWicketSequence(0)');
									setDropdownOptionForWicketSequence(0);
							}else if(dataToProcess.split(',')[0] == 'Alt_b') {
								if($('#selectWicketplayer option:last').val()){
									
								}
								document.getElementById('selectWicketSequence').setAttribute('onchange','setDropdownOptionForWicketBowlerSequence(0)');
								setDropdownOptionForWicketBowlerSequence(0);
								$('#selectWicketplayer option:selected').next().prop('selected', true);
								document.getElementById('selectWicketplayer').setAttribute('onchange','setDropdownOptionForWicketBowlerSequence(1)');
								setDropdownOptionForWicketBowlerSequence(1);
							}
						}else {
							processUserSelection($('#cancel_graphics_btn').attr('value','cancel_graphics_btn'));
						}
					} else {
						if(data != 'YES' && typeof data !== 'object'){
						      
						        alert(data);
						    }	
						/*$("#select_graphic_options_div").empty();
						document.getElementById('select_graphic_options_div').style.display = 'none';
						$("#captions_div").show();*/
					}
					document.activeElement.blur();
					break;
				case 'GRAPHICS-OPTIONS':
					addItemsToList(dataToProcess,data);
					break;
				case "GRAPHICS-OPTIONS_DATA":
				    console.log("valueToProcess =", valueToProcess);
				    // Player dropdown cases
				    if (valueToProcess.includes("Control_Shift_X") || valueToProcess.includes("Control_u") ||
				        valueToProcess.includes("Shift_W")) {
				        setPlayerDropdown(data);
				        return;
				    }
				    // Promo case
				    if (valueToProcess.includes("Alt_2")) {
				        if (valueToProcess.includes("PROMO")) {
				            setPromoDropdown("PROMO", data);
				        }
				        return;
				    }
					if (valueToProcess.includes("Alt_0")) {
				        if (valueToProcess.includes("LEAGUE_PROMOTION")) {
							setInfoBarStatsDropdown("LEAGUE_PROMOTION", data);
				        }
						if (valueToProcess.includes("PLAYER_BUILDUP_BAT")) {
							setInfoBarStatsDropdown("PLAYER_BUILDUP_BAT", data);
				        }
						if (valueToProcess.includes("PLAYER_BUILDUP_BALL")) {
							setInfoBarStatsDropdown("PLAYER_BUILDUP_BALL", data);
				        }
				        return;
				    }
				    // Commentators / FreeTextDb
				    if (valueToProcess.includes("Alt_6")) {
				        let processValue = valueToProcess.toUpperCase();
				        if (processValue.includes("COMMENTATORS")) {
				            setCommentators("Commentators", data);
				            return; // IMPORTANT
				        }
				        if (processValue.includes("FREETEXTDB")) {
				            setInfoBarStatsDropdown("FreeTextDb", data);
				            return; // IMPORTANT
				        }
						if (processValue.includes("PROMO")) {
							console.log("HELLO");
				            setPromoDropdown("PROMO", data);
							return; // IMPORTANT
				        }
				    }
				    setPlayerInDropdown(data);
				    break;	
				default:
					if(whatToProcess.includes("ANIMATE-IN-") || whatToProcess.includes("ANIMATE-OUT-")) {
						session_animation = data;
					}
					document.activeElement.blur();
					break;
				}
				break;
			}
		}
	});
}

function displayStats(data) {
    let container = document.getElementById("stats-container");

    if (!container) {
        container = document.createElement("div");
        container.id = "stats-container";
        document.body.appendChild(container);
    }

    container.innerHTML = ""; // Clear old data

    if (!data || !Array.isArray(data) || data.length === 0) {
        container.innerHTML = "<div>No stats available</div>";
        return;
    }

    // Player name
    const playerName = data[0] || "Unknown Player";
    const nameDiv = document.createElement("div");
    nameDiv.id = "player-name";
    nameDiv.textContent = playerName;
    container.appendChild(nameDiv);

    // Stats line
    const statsLine = document.createElement("div");
    statsLine.id = "stats-line";

    const statsText = data.slice(1)
        .filter(item => item && !item.toLowerCase().includes("undefined"))
        .map(item => {
            const [label, value] = item.split(",");
            return `<strong>${label}</strong> - ${value}`;
        })
        .join(" &nbsp; ");

    statsLine.innerHTML = statsText;
    container.appendChild(statsLine);
}

function removeSelectDuplicates(select_id)
{
	var this_list = {};
	$("select[id='" + select_id + "'] > option").each(function () {
	    if(this_list[this.text]) {
	        $(this).remove();
	    } else {
	        this_list[this.text] = this.value;
	    }
	});
}

function setCommentators(type, data) {
  if (type === "Commentators" && Array.isArray(data)) {
    const commentatorCells = [
      document.getElementById('Player1'),
      document.getElementById('Player2'),
      document.getElementById('Player3')
    ];

    // Clear each cell and add a commentator dropdown
    commentatorCells.forEach((cell, index) => {
      if (!cell) {
        console.warn(`Cell Player${index+1} not found`);
        return;
      }

      cell.innerHTML = '';

      const commSelect = document.createElement('select');
      commSelect.id = `commentatorDropdown${index + 1}`;

      // Add a default empty option
      const defaultOption = document.createElement('option');
      defaultOption.value = '0';
      defaultOption.text = '';
      commSelect.appendChild(defaultOption);

      // Use the passed data array here
      data.forEach(comm => {
        if (comm.useThis === 'Yes') {
          const option = document.createElement('option');
          option.value = comm.commentatorId;
          option.text = comm.commentatorName;
          commSelect.appendChild(option);
        }
      });
      commSelect.selectedIndex = 0;

      $(commSelect).on('change', function() {
        setDropdownOptionToSelectOptionArray($(this), index + 1);
      });

      cell.appendChild(commSelect);

      // Initialize selection
      setDropdownOptionToSelectOptionArray($(commSelect), index + 1);
      $(commSelect).trigger('change');
    });
  }
}

function setInfoBarStatsDropdown(type, data) {
  if ((type === "FreeTextDb" || type === "LEAGUE_PROMOTION" || type === "PLAYER_BUILDUP_BAT" 
		|| type === "PLAYER_BUILDUP_BALL") && Array.isArray(data)) {
	
	const freeTextCells = [
	      document.getElementById('FreeText')
	    ];

    // Clear each cell and add a commentator dropdown
    freeTextCells.forEach((cell, index) => {
      if (!cell) {
        console.warn(`Cell Player${index+1} not found`);
        return;
      }

      cell.innerHTML = '';

      const freeTextSelect = document.createElement('select');
      freeTextSelect.id = `commentatorDropdown${index + 1}`;

      // Add a default empty option
      const defaultOption = document.createElement('option');
      defaultOption.value = '0';
      defaultOption.text = '';
      freeTextSelect.appendChild(defaultOption);

      // Use the passed data array here
      data.forEach(pro1 => {
        const option = document.createElement('option');
          option.value = pro1.order;  
          option.text = pro1.prompt;
          freeTextSelect.appendChild(option);
      });
      freeTextSelect.selectedIndex = 0;

      $(freeTextSelect).on('change', function() {
        setDropdownOptionToSelectOptionArray($(this), index + 1);
      });

      cell.appendChild(freeTextSelect);

      // Initialize selection
      setDropdownOptionToSelectOptionArray($(freeTextSelect), index + 1);
      $(freeTextSelect).trigger('change');
    });
  }
}

function setPromoDropdown(type, data) {
  if (type === "PROMO" && Array.isArray(data)) {
	
	const promoCells = [document.getElementById('Promo')];

    // Clear each cell and add a commentator dropdown
    promoCells.forEach((cell, index) => {
      if (!cell) {
        console.warn(`Cell Player${index+1} not found`);
        return;
      }

      cell.innerHTML = '';

      const promoSelect = document.createElement('select');
      promoSelect.id = `commentatorDropdown${index + 1}`;

      // Use the passed data array here
      data.forEach(pro1 => {
        const option = document.createElement('option');
          option.value = pro1.matchnumber;  
          option.text = pro1.matchnumber + ' - ' +pro1.home_Team.teamName1 + ' Vs ' + pro1.away_Team.teamName1;
          promoSelect.appendChild(option);
      });
      promoSelect.selectedIndex = 0;

      $(promoSelect).on('change', function() {
        setDropdownOptionToSelectOptionArray($(this), index + 1);
      });

      cell.appendChild(promoSelect);

      // Initialize selection
      setDropdownOptionToSelectOptionArray($(promoSelect), index + 1);
      $(promoSelect).trigger('change');
    });
  }
}

function setPlayerInDropdown(dataToProcess) {
 	const playerCell = document.getElementById('Player');
 	playerCell.innerHTML = ''; 	
    const playerSelect = document.createElement('select');
    playerSelect.id = 'playerDropdown';
    
    let numb = 0;
	dataToProcess.forEach(player => {
		numb++;
		const option = document.createElement('option');
        option.value = numb + '_' + player.playerId;  
        option.text = player.player.full_name; 
        playerSelect.appendChild(option);
  	});
    
	playerSelect.selectedIndex = 0;
    $(playerSelect).on('change', function() {
		setDropdownOptionToSelectOptionArray($(this), 0);
    });        
    playerCell.appendChild(playerSelect);
	   setDropdownOptionToSelectOptionArray($(this), 0);
	$(playerSelect).trigger('change');
}
function setDropdownOptionToSelectOptionArray(whichInput, whichIndex)
{
	switch($('#selected_broadcaster').val().toUpperCase()){
		case 'ISPL':
			isSplitScorecard = false;
			if($('#' + $(whichInput).attr('id') + ' option:selected').val() == 'SPLIT'){
				isSplitScorecard = true;
				addItemsToList('F1')
				selected_options[selected_options.length] = 'SPLIT';
			}
			break;
		}
	selected_options[0] = document.getElementById('which_inning').value;
	selected_options[whichIndex+1] = $('#' + $(whichInput).attr('id') + ' option:selected').val();
}

function setTextBoxOptionToSelectOptionArray(whichIndex)
{
	selected_options[0] = document.getElementById('which_inning').value;
	selected_options[whichIndex+1] = $('#selectFreeText').val();
}
function setTextBoxOptionToSelectOptionArray1(whichIndex)
{
	selected_options[0] = document.getElementById('which_inning').value;
	selected_options[whichIndex+1] = $('#selectFreeText1').val();
}

function setTextBoxOptionForSixDistanceToSelectOptionArray(whichIndex)
{
	selected_options[0] = document.getElementById('which_inning').value+','+$('#selectFreeText').val();
	selected_options[whichIndex+1] = $('#sixOrNine option:selected').val();
}
function setDropdownOptionForWicketSequence(whichIndex)
{
	selected_options[0] = document.getElementById('which_inning').value;
	selected_options[whichIndex+1] = $('#selectWicketSequence option:selected').val();
}
function setDropdownOptionForWicketBowlerSequence(whichIndex)
{
	selected_options[0] = document.getElementById('which_inning').value+ ","+
		$('#selectWicketSequence option:selected').val();
	selected_options[whichIndex+1] = $('#selectWicketplayer option:selected').val();
}
function getStrikeRate(totalRunsScored, totalBallsFaced, numberOfDecimals, defaultValue) {
    if (totalBallsFaced <= 0) {
        return defaultValue;
    } else {
        if (numberOfDecimals > 0) {
            return ((totalRunsScored*100) / totalBallsFaced).toFixed(numberOfDecimals);
        } else {
            return defaultValue;
        }
    }
}
function getEconomy(totalRunsConceded, totalBallsBowled, numberOfDecimals, defaultValue) {
    if (totalBallsBowled <= 0) {
        return defaultValue;
    } else {	
        if (numberOfDecimals > 0) {
            return ((totalRunsConceded / totalBallsBowled) * 6).toFixed(numberOfDecimals);
        } else {
            return defaultValue;
		}
 	}
 }
function addItemsToList(whatToProcess,dataToProcess)
{
	var select,option,header_text,div,table,table_data,tbody,row;
	var cellCount = 0,row_count=0;
	
	switch(whatToProcess) {
	case 'HELP-FILE':
		$("#captions_div").hide();
		$('#select_graphic_options_div').empty();
		
		let sections = [
		    { title: 'INFOBAR', captions: [
		        ['FOUR DIRECTOR', 'F'],
		        ['FREE HIT DIRECTOR', 'I'],
		        ['SIX DIRECTOR', 'S'],
		        ['POINTS TABLE', 'CTRL+S'],
		        ['WICKET DIRECTOR', 'W'],
		        ['NINE DIRECTOR', '0'],
		        ['INFOBAR IN', 'F12'],
		        ['INFOBAR_SPONSOR', ']'],
		        ['HAT-TRICK DIRECTOR', ';'],
		        ['TICKER PUSH OUT', 'PageDown/ARROW DOWN'],
		        ['TICKER PUSH IN', 'PageUp/ARROW UP'],
		        ['CHALLENGE RUNS', 'ALT+C'],
		        ['POWER PLAY IN/OUT', 'ALT+E'],
		        ['TICKER SHRINK', 'ALT+F'],
		        ['COMMENTATOR', 'ALT+0'],
		        ['BOTTOM LEFT SECTION', 'ALT+1'],
		        ['MIDDLE SECTION', 'ALT+2'],
		        ['BAT PROFILE', 'ALT+3'],
		        ['BOWL PROFILE', 'ALT+4'],
		        ['LAST X BALLS', 'ALT+5'],
		        ['BAT + SPONSOR', 'ALT+6'],
		        ['BOTTOM RIGHT SECTION', 'ALT+7'],		        
		        ['RIGHT SECTION, RIGHT TOP, TICKER TIMELINE', 'ALT+8'],
		        ['FREE TEXT INFO DB', 'ALT+9'],
		        ['INFOBAR IDENT DATA', 'SHIFT+F12'],
		        ['INFOBAR IDENT', 'CTRL+F12'],
		        ['AnimateOut Infobar-Right', 'CTRL+0'],
		        ['BONUS', 'CTRL+1'],
		        ['POWERPLAY IN', 'CTRL+2'],
		        ['POWERPLAY OUT', 'CTRL+3'],
		        ['FREE TEXT USING INPUT BOX', 'CTRL+4'],
		        ['RIGHT TOP INFOBAR OUT', 'CTRL+-'],
		        ['CHALLENGE RUNS OUT', 'CTRL+='],
		        ['INFOBAR OUT', '-'],
		        ['INFOBAR IDENT OUT', '='],
		        
		    ]},
		    { title: 'FULL FRAME', captions: [
		        ['MOST FOURS', 'F'],
		        ['POTT ', 'R'],
		        ['MOST SIXES', 'V'],
		        ['MOST WICKETS', 'X'],
		        ['MOST RUNS', 'Z'],
		        ['BATTING CARD', 'F1'],
		        ['BOWLING CARD', 'F2'],
		        ['ALL PARTNERSHIP', 'F4'],
		        ['BATTING BY POSITION ', 'ALT+G'],
		        ['BATTING BY YEARS ', 'ALT+H'],
		        ["TODAY'S MATCH", 'ALT+J'],
		        ['BATTING BARS ', 'ALT+K'],
		        ['BATTING BY VENUE IN A COUNTRY ', 'ALT+L'],
		        ['BAT MILESTONE', 'ALT+M'],
		        ['BOWL MILESTONE', 'ALT+N'],
		        ['BATSMAN v ALL BOWLERS', 'ALT+T'],
		        ['BOWLER v ALL BATTERS', 'ALT+U'],
		        ['BATTING BY COUNTRY ', 'ALT+V'],
		        ['SQUAD ', 'ALT+Z'],
		        ['FOW ', 'ALT+F3'],
		        ["RICHIE'S CAPTION", 'ALT+F5'],
		        ['SINGLE TEAM (CAREER)', 'ALT+F9'],
		        ['SINGLE TEAM (THIS SERIES)', 'ALT+F10'],
		        ['DOUBLE MANHATTAN', 'ALT+F11'],
		        ['TARGET', 'SHIFT+D'],
		        ['ECONOMY BARS ', 'SHIFT+G'],
		        ['BOWLING BARS ', 'SHIFT+H'],
		        ['IMPACT PLAYER ', 'SHIFT+I'],
		        ['NEXT TO BAT ', 'SHIFT+J'],
		        ['CURR PARTNERHIP ', 'SHIFT+K'],
		        ['BOWLING BY YEARS ', 'M'],
		        ['WICKETS TAKEN IN AN INNINGS ', 'SHIFT+N'],
		        ['BAT PROFILE (THIS SERIES)', 'SHIFT+P'],
		        ['BOWL PROFILE (THIS SERIES)', 'SHIFT+Q'],
		        ['TEAMS WITH PHOTOS', 'SHIFT+T'],
		        ['BOWLING BY COUNTRY', 'SHIFT+V'],
		        ['Bowler Best Figures FF(THIS SERIES)', 'SHIFT+X'],
		        ['Bowler Best Figures FF(THIS SERIES)', 'SHIFT+Z'],
		        ['Bowler Best Figures FF(THIS SERIES)', 'SHIFT+D'],
		        ['PREV MATCH SUMMARY', 'SHIFT+F10'],
		        ['PREV MATCH SUMMARY', 'SHIFT+F11'],
		        ['TAPE BALL LEADERBOARD', 'CTRL+C'],
		        ['BAT PROFILE (DB)', 'CTRL+D'],
		        ['BOWL PROFILE (DB)', 'CTRL+E'],
		        ['BOWLING BY VENUE IN A COUNTRY ', 'CTRL+L'],
		        ['MATCH PROMO', 'CTRL+M'],
		        ['POINTS TABLE (6 TEAMS)', 'CTRL+P'],
		        ['BEST BOWLING FIG', 'CTRL+X'],
		        ['HIGHEST SCORE', 'CTRL+Z'],
		        ['PHOTO SCORECARD', 'CTRL+F1'],
		        ['DOUBLE TEAMS', 'CTRL+F7'],
		        ['MANHATTAN', 'CTRL+F10'],
		        ['MATCH SUMMARY', 'CTRL+F11'],
		        ['BATSMAN CAREER MANHATTAN ', 'ALT + SHIFT+M'],
		        ['HALF FRAME WAGON WHEEL', 'ALT + SHIFT+W'],
		        ['FULL FRAME WAGON WHEEL', 'ALT + SHIFT+Y'],
		    ]},
		    { title: 'LOWER THIRDS', captions: [
		        ['LT ALL POWERPLAY SUMM', 'A'],
		        ['MATCH STATISTICS ', 'B'],
		        ['LT TARGET', 'D'],
		        ['EQUATION', 'E'],
		        ['MOST RUNS', 'Z'],
		        ['BATTING CARD', 'F1'],
		        ['BOWLING CARD', 'F2'],
		        ['ALL PARTNERSHIP', 'F4'],
		        ['BATTING BY POSITION ', 'ALT+G'],
		        ['BATTING BY YEARS ', 'ALT+H'],
		        ["TODAY'S MATCH", 'ALT+J'],
		        ['BATTING BARS ', 'ALT+K'],
		        ['BATTING BY VENUE IN A COUNTRY ', 'ALT+L'],
		        ['BAT MILESTONE', 'ALT+M'],
		        ['BOWL MILESTONE', 'ALT+N'],
		        ['BATSMAN v ALL BOWLERS', 'ALT+T'],
		        ['BOWLER v ALL BATTERS', 'ALT+U'],
		        ['BATTING BY COUNTRY ', 'ALT+V'],
		        ['SQUAD ', 'ALT+Z'],
		        ['FOW ', 'ALT+F3'],
		        ["RICHIE'S CAPTION", 'ALT+F5'],
		        ['SINGLE TEAM (CAREER)', 'ALT+F9'],
		        ['SINGLE TEAM (THIS SERIES)', 'ALT+F10'],
		        ['DOUBLE MANHATTAN', 'ALT+F11'],
		        ['TARGET', 'SHIFT+D'],
		        ['ECONOMY BARS ', 'SHIFT+G'],
		        ['BOWLING BARS ', 'SHIFT+H'],
		        ['IMPACT PLAYER ', 'SHIFT+I'],
		        ['NEXT TO BAT ', 'SHIFT+J'],
		        ['CURR PARTNERHIP ', 'SHIFT+K'],
		        ['BOWLING BY YEARS ', 'M'],
		        ['WICKETS TAKEN IN AN INNINGS ', 'SHIFT+N'],
		        ['BAT PROFILE (THIS SERIES)', 'SHIFT+P'],
		        ['BOWL PROFILE (THIS SERIES)', 'SHIFT+Q'],
		        ['TEAMS WITH PHOTOS', 'SHIFT+T'],
		        ['BOWLING BY COUNTRY', 'SHIFT+V'],
		        ['Bowler Best Figures FF(THIS SERIES)', 'SHIFT+X'],
		        ['Bowler Best Figures FF(THIS SERIES)', 'SHIFT+Z'],
		        ['Bowler Best Figures FF(THIS SERIES)', 'SHIFT+D'],
		        ['PREV MATCH SUMMARY', 'SHIFT+F10'],
		        ['PREV MATCH SUMMARY', 'SHIFT+F11'],
		        ['TAPE BALL LEADERBOARD', 'CTRL+C'],
		        ['BAT PROFILE (DB)', 'CTRL+D'],
		        ['BOWL PROFILE (DB)', 'CTRL+E'],
		        ['BOWLING BY VENUE IN A COUNTRY ', 'CTRL+L'],
		        ['MATCH PROMO', 'CTRL+M'],
		        ['POINTS TABLE (6 TEAMS)', 'CTRL+P'],
		        ['BEST BOWLING FIG', 'CTRL+X'],
		        ['HIGHEST SCORE', 'CTRL+Z'],
		        ['PHOTO SCORECARD', 'CTRL+F1'],
		        ['DOUBLE TEAMS', 'CTRL+F7'],
		        ['MANHATTAN', 'CTRL+F10'],
		        ['MATCH SUMMARY', 'CTRL+F11'],
		        ['BATSMAN CAREER MANHATTAN ', 'ALT + SHIFT+M'],
		        ['HALF FRAME WAGON WHEEL', 'ALT + SHIFT+W'],
		        ['FULL FRAME WAGON WHEEL', 'ALT + SHIFT+Y'],
		    ]},
		    { title: "MINI'S AND BUGS", captions: [
		        ['BOWL FIG', 'G'],
		        ['HIGHLIGHTS', 'H'],
		        ['PLAYER OF THE MATCH', 'O'],
		        ['THIRD UMPIRE', 'T'],
		        ['BAT SCORE', 'Y'],
		        ['TOURNAMENT SIX COUNTER', '6'],
		        ['BUG 50-50', '.'],
		        ['WICKET SEQUENCE BOWLER', 'ALT+B'],
		        ['SIX DISTANCE', 'SHIFT+C'],
		        ['WICKET SEQUENCE', 'SHIFT+F'],
		        ['BAT DISMISSAL', 'SHIFT+O'],
		        ['BUG MULTI PARTNERSHIP', 'SHIFT+F4'],
		        ['MINI BATTING CARD', 'SHIFT+F1'],
		        ['MINI BOWLING CARD', 'SHIFT+F2'],
		        ['CURR PARTNERSHIP (BUGS)', 'CTRL+K'],
		        ['POWERPLAY', 'CTRL+Y'],
		    ]},
		    { title: "POP UP", captions: [
		        ['BAT POPUP', 'CTRL + SHIFT+U'],
		        ['BOWL POPUP', 'CTRL + SHIFT+V'],
		    ]}
		];
		
    		sections.forEach(section => {
		    table = document.createElement('table');
		    table.setAttribute('class', 'table table-bordered');
		
		    tbody = document.createElement('tbody');
		    table.appendChild(tbody);
		
		    headerRow = document.createElement('tr');
		    headerText = document.createElement('th');
		    headerText.setAttribute('class', 'thead-dark');
		    headerText.setAttribute('colspan', '2');
		    headerText.setAttribute('style', 'color: red;');
		    headerText.innerHTML = `<b>${section.title}</b>`;
		    headerRow.appendChild(headerText);
		    tbody.appendChild(headerRow);
		
        for (let i = 0; i < Math.ceil(section.captions.length / 3); i++) {
		        let row = document.createElement('tr');
		
		        for (let j = 0; j < 3; j++) {
		            let index = i * 3 + j;
		            if (index < section.captions.length) {
		                cell1 = document.createElement('td');
		                cell2 = document.createElement('td');
		                cell1.innerHTML = `<b>${section.captions[index][0]}</b>`;
		                if (section.captions[index][1].includes('/')) {
        					cell2.innerHTML = `<b>${section.captions[index][1].replace('/', '/<br>')}</b>`;
					    } else {
					        cell2.innerHTML = `<b>${section.captions[index][1]}</b>`;
					    }

		                cell2.setAttribute('style', 'color: blue;');
		                row.appendChild(cell1);
		                row.appendChild(cell2);
		            } else {
		                cell1 = document.createElement('td');
		                cell2 = document.createElement('td');
		                row.appendChild(cell1);
		                row.appendChild(cell2);
		            }
		        }
		
		        tbody.appendChild(row);
		    }
		
		    $('#select_graphic_options_div').append(table);
		    $('#select_graphic_options_div').append("<br>");
		});
		
		$("#select_graphic_options_div").show();
	break;
/*	case 'HELP-FILE':
		
		$('#help_file_div').empty();

		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
				
		tbody = document.createElement('tbody');

		table.appendChild(tbody);
		document.getElementById('help_file_div').appendChild(table);
		
		for(var iRow=0; iRow<=1; iRow++){
			row = tbody.insertRow(tbody.rows.length);
			for(var iCol=0; iCol<=2; iCol++){
				header_text = document.createElement('h6');
				switch(iRow){
				case 0:
					switch(iCol){
					case 0:
						header_text.innerHTML = 'Ful Framers';
						break;
					case 1:
						header_text.innerHTML = 'Lower Thirds';
						break;
					case 2:
						header_text.innerHTML = 'Infobar';
						break;
					}
					break;
				case 1:
					switch(iCol){
					case 0:
						header_text.innerHTML = 'F1: Scorecard';
						break;
					case 1:
						header_text.innerHTML = 'F4: partnership';
						break;
					case 2:
						header_text.innerHTML = 'Alt+1: Bottom left options';
						break;
					}
					break;
				}
				row.insertCell(iCol).appendChild(header_text);
			}
		}
		break;*/
	/*case '8':
		$("#captions_div").hide();
		$('#select_graphic_options_div').empty();
		
		header_text = document.createElement('h6');
		header_text.innerHTML = 'Select Graphic Options';
		document.getElementById('select_graphic_options_div').appendChild(header_text);
		
		table_data = document.createElement('table');
		table_data.setAttribute('class', 'table table-bordered');
		tbody = document.createElement('tbody');
		table_data.appendChild(tbody);
		
		dataToProcess.forEach(function(playerstats1){
			row = tbody.insertRow(tbody.rows.length);
			for(var j = 1; j <= 2; j++){
				text = document.createElement('text');
				switch(j){
					case 1:
						alert(playerstats.playerId);
						text.innerHTML = playerstats.playerId ;
						break;
				}
				row.insertCell(j-1).appendChild(text);
			}
			//alert(playerstats.playerId);
		});
		
		document.getElementById('select_graphic_options_div').appendChild(table_data);
		break;*/
		
	case 'Shift_C': case 'Control_Shift_Q': case 'Control_Shift_O': case 'Control_Shift_J':case 'Control_Shift_F5':
	case 'Control_m': case 'F4': case 'F5': case 'F6': case 'Alt_w': case 'Control_j': case 'F8': case 'F9': case 'F10': case 'F7': case 'F11':
	case 'Control_F5': case 'Control_F9': case 'Shift_T': case 'u': case 'p': case 'Control_p': case 'Control_d': case 'Control_e': case 'Shift_F8':
	case 'z': case 'x': case 'c': case 'v': case 'Shift_F11': case 'Control_y': case 'Alt_F8': case 'Alt_F1': case 'Alt_F2':
	case 'Shift_K': case 'Shift_O': case 'k': case 'Shift_Y': case 'g': case 'y': case 'Shift_F5': case 'Shift_F9': case 'Control_h': case 'Control_g': case 'q':
	case 'j': case 'Shift_F6': case 'Control_s':  case 'Control_f': case 'Alt_F12': case 'l': case 'Shift_E': case 'Alt_F9':
	case 'F12': case 'Alt_1': case 'Alt_2': case 'Alt_3': case 'Alt_4': case 'Alt_5': case 'Alt_6': case 'Alt_7': case 'Alt_8': case 'Alt_9': case 'Alt_0':
	case 'Alt_m': case 'Alt_n': case 'Control_b': case 'Alt_p': case 'Alt_F10': case 'Alt_d': case 'Shift_F4': case 'Alt_a': case 'Alt_s': 
	case 'Shift_P': case 'Shift_Q': case 'Alt_z': case 'Control_c': case 'Control_v': case 'Control_z': case 'Control_x': case 'Alt_q': case 'Shift_F': 
	case 'Alt_F6': case 'Shift_A': case 'Shift_R': case 'Control_Shift_F1': case 'Control_Shift_D': case 'Alt_Shift_Z': case 'Control_Shift_F7': case 'Control_Shift_F2':
	case 'Alt_c': case 'Control_F12': case 'Shift_F12': case 'F1': case 'F2': case 'Shift_F7': case 'Control_Shift_F9': case 'Alt_Shift_C': case 'Control_Shift_L':
	case 'Shift_Z': case 'Shift_X': case 'Control_i': case 'Control_Shift_E': case 'Control_Shift_F': case 'Control_Shift_P': case 'Shift_I': case 'Control_F11': case 'Control_Shift_M':
	case 'Alt_Shift_R': case 'Control_Shift_U': case 'Control_Shift_V': case 'Control_4': case 'Shift_~': case 'Shift_!': case 'Control_Shift_F4': case 'Control_Shift_Z':
	case 'Control_Shift_Y': case 'Alt_Shift_W':case "Control_Shift_F8": case 'Control_Shift_X':case 'Control_u': case 'Shift_W':case "Shift_G": case 'Control_5': case 'Control_F8':
	case 'Control_7': case 'Control_8': case 'Control_Shift_F11': case 'Alt_/': case 'Control_9': case "Alt_Shift_B": case 'Control_0': case 'r':
	case "Shift_@": case "Shift_$": case "Control_Shift_@": case "Control_Alt_5": case "Alt_Shift_@": case "Control_Alt_6": case "Control_Alt_9": case "Control_Alt_0":
	case "Alt_Shift_N": case "Alt_Shift_M":case 'Alt_Shift_Q': case 'Alt_Shift_P': case 'Alt_Shift_K': case 'Alt_Shift_X': case 'Alt_Shift_T': case 'Alt_Shift_V':
	case 'Alt_Shift_F4':case "Alt_Shift_F5":case 'Alt_Shift_F6':case 'Alt_Shift_F7': case 'Shift_L': case 'h': case 'Control_Shift_(': case 'Shift_M': case 'Control_Alt_7':
	case 'Control_Alt_8': case 'Alt_x': case 'Alt_f': case 'Control_F7':
	//Shift+2 and Shift+4
	 //InfoBar LeftBottom-Middle-BatPP-BallPP-LastXBalls-Batsman/Sponsor-RightBottom
	case "Alt_b": 
		$("#captions_div").hide();
		$('#select_graphic_options_div').empty();
   		initialiseSelectedOptionsList();
		header_text = document.createElement('h6');
		header_text.innerHTML = 'Select Graphic Options';
		document.getElementById('select_graphic_options_div').appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
				
		tbody = document.createElement('tbody');

		table.appendChild(tbody);
		document.getElementById('select_graphic_options_div').appendChild(table);
		
		row = tbody.insertRow(tbody.rows.length);
		
		switch(whatToProcess) {
		case 'Shift_!':
			header_text.innerHTML = 'PLAYER CAREER STATS';
			
			thead = document.createElement('thead');
			//tbody = document.createElement('tbody');
			tr = document.createElement('tr');
			for (var j = 0; j <= 4; j++) {
			    th = document.createElement('th'); // Column
				th.scope = 'col';
			    switch (j) {
				case 0:
				    th.innerHTML = 'TEAM/PLAYER';
					break;
				case 1:
					th.innerHTML = 'BAT T20I STATS (M|R|50/100|SR)';
					break;
				case 2:
					th.innerHTML = 'BALL T20I STATS (M|W|R|Econ.)';
					break;
				case 3:
					th.innerHTML = 'BAT ODI STATS (M|R|50/100|SR)';
					break;
				case 4:
					th.innerHTML = 'BALL ODI STATS (M|W|R|Econ.)';
					break;
				}	
				tr.appendChild(th);
			}
			thead.appendChild(tr);
			table.appendChild(thead);
			for(var i = 0; i <= dataToProcess.length - 1; i++){
				row = tbody.insertRow(tbody.rows.length);
				for(var j = 0; j <= 4; j++){
					text = document.createElement('text');
					switch(j){
					case 0:
						text.innerHTML = dataToProcess[i].playerId + ' - ' + dataToProcess[i].player.full_name + ' (' + dataToProcess[i].team.teamName4 + ')';
						break;
					case 1: case 2: case 3: case 4:
						text.innerHTML = 'NO DATA IN DB' ;
						break;
					}
					row.insertCell(j).appendChild(text);
				}
			}
			break;
		case 'Shift_~':
			header_text.innerHTML = 'PLAYER STATS';
			
			thead = document.createElement('thead');
			//tbody = document.createElement('tbody');
			tr = document.createElement('tr');
			for (var j = 0; j <= 4; j++) {
			    th = document.createElement('th'); // Column
				th.scope = 'col';
			    switch (j) {
				case 0:
				    th.innerHTML = 'TEAM/PLAYER';
					break;
				case 1:
					th.innerHTML = 'BAT CAREER STATS (M|R|50/100|SR)';
					break;
				case 2:
					th.innerHTML = 'BALL CAREER STATS (M|W|R|Econ.)';
					break;
				case 3:
					th.innerHTML = 'BAT THIS SERIES (M|R|50/100|SR)';
					break;
				case 4:
					th.innerHTML = 'BALL THIS SERIES (M|W|R|Econ.)';
					break;
				}	
				tr.appendChild(th);
			}
			thead.appendChild(tr);
			table.appendChild(thead);
			
			for(var i = 0; i <= dataToProcess.length - 1; i++){
				row = tbody.insertRow(tbody.rows.length);
				for(var j = 0; j <= 4; j++){
					text = document.createElement('text');
					switch(j){
					case 0:
						text.innerHTML = dataToProcess[i].playerId + ' - ' + dataToProcess[i].player.full_name + ' (' + dataToProcess[i].team.teamName4 + ')';
						break;
					case 1:
						if(dataToProcess[i].stats != null) {
							text.innerHTML = dataToProcess[i].stats.matches + ' | ' + dataToProcess[i].stats.runs + ' | ' + dataToProcess[i].stats.fifties + '/' +
								dataToProcess[i].stats.hundreds + ' | ' + getStrikeRate(dataToProcess[i].stats.runs,dataToProcess[i].stats.balls_faced,1,'-') ;
						}else {
							text.innerHTML = 'NO DATA IN DB' ;
						}
						break;
					case 2:
						if(dataToProcess[i].stats != null) {
							text.innerHTML = dataToProcess[i].stats.matches + ' | ' + dataToProcess[i].stats.wickets + ' | ' + dataToProcess[i].stats.runs_conceded + 
								' | ' + getEconomy(dataToProcess[i].stats.runs_conceded,dataToProcess[i].stats.balls_bowled,2,'-');
						}else {
							text.innerHTML = 'NO DATA IN DB' ;
						}
						break;
					case 3:
						if(dataToProcess[i].tournament != null) {
							text.innerHTML = dataToProcess[i].tournament.matches + ' | ' + dataToProcess[i].tournament.runs + ' | ' + dataToProcess[i].tournament.fifty + '/' 
								+ dataToProcess[i].tournament.hundreds + ' | ' + getStrikeRate(dataToProcess[i].tournament.runs,dataToProcess[i].tournament.ballsFaced,1,'-');
						}else {
							text.innerHTML = 'NO DATA' ;
						}
						break;
					case 4:
						if(dataToProcess[i].tournament != null) {
							text.innerHTML = dataToProcess[i].tournament.matches + ' | ' + dataToProcess[i].tournament.wickets + ' | ' + dataToProcess[i].tournament.runsConceded + 
								' | ' + getEconomy(dataToProcess[i].tournament.runsConceded,dataToProcess[i].tournament.ballsBowled,2,'-');
						}else {
							text.innerHTML = 'NO DATA' ;
						}
						break;
					}
					row.insertCell(j).appendChild(text);
				}
			}
			break;
		case "Shift_G":
	    let label = document.createElement('label');
	    label.setAttribute('for', 'selectFreeText'); 
	    label.innerHTML = 'Overs:';	
	    header_text.innerHTML = 'LAST FEW OVERS';
	
	    select = document.createElement('input');
	    select.type = "text";
	    select.id = 'selectFreeText';
	    select.value = '5';
	    select.setAttribute('onchange', "setTextBoxOptionToSelectOptionArray(0)");
		let cell = row.insertCell(cellCount);
	    cell.appendChild(label); 
	    cell.appendChild(document.createTextNode(' '));
	    cell.appendChild(select); 	
	    setTextBoxOptionToSelectOptionArray(0);
   		cellCount = cellCount + 1;
   		 break;
		case 'Alt_Shift_Q': case 'Alt_Shift_P':
			select = document.createElement('select');
			select.id = 'selectGround';
			select.name = select.id;
			[{ value: 'm', text: 'METER' },
			 { value: 'y', text: 'YARD' }
				].forEach(({ value, text }) => {
					  option = document.createElement('option');
					  option.value = value;
					  option.text = text;
					  select.appendChild(option);
				});
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			
		break;
		case 'Control_Shift_X':case 'Control_u': case 'Shift_W':
		switch(whatToProcess) {
			 case 'Control_Shift_X':
			 	header_text.innerHTML = 'Bowler vs Batsman';
			  break;
			 case 'Control_u':
				 header_text.innerHTML = 'Bowler vs Batsman[LHS vs RHS]';
			  break;
			 case 'Shift_W':
			 	header_text.innerHTML = 'Player matches and catches';
			 	break;
			}
			
			let selectH2HTeam = document.createElement('select');
				selectH2HTeam.id = 'selectTeam';
				selectH2HTeam.name = selectH2HTeam.id;
				
			    option = document.createElement('option');
				option.value = session_match.setup.homeTeam.teamId;
				option.text = session_match.setup.homeTeam.teamName2;
				selectH2HTeam.appendChild(option);
				
				option = document.createElement('option');
				option.value = session_match.setup.awayTeam.teamId;
				option.text = session_match.setup.awayTeam.teamName2;
				selectH2HTeam.appendChild(option);
				
				selectH2HTeam.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(selectH2HTeam);
				setDropdownOptionToSelectOptionArray($(selectH2HTeam),0);
				
				cellCount = cellCount + 1;
				
				let selection = document.createElement('select');
				selection.id = 'selectTeamids';
				selection.name = selection.id;
				selection.style.width = 'auto';
				selection.style.height = 'auto';
				
				selectH2HTeam.addEventListener('change', function() {
	           	 selection.innerHTML = '';  
	           	  if (parseInt(this.value, 10) === session_match.setup.homeTeam.teamId) {
					session_match.setup.homeSquad.forEach(function(hs){
						option = document.createElement('option');
						option.value = hs.playerId;
						option.text =  hs.full_name;
						selection.appendChild(option);
					});
				  }else if (parseInt(this.value, 10) === session_match.setup.awayTeam.teamId) {
						session_match.setup.awaySquad.forEach(function(hs){
						option = document.createElement('option');
						option.value = hs.playerId;
						option.text = hs.full_name;
						selection.appendChild(option);
					});
				  }
				  	$(selection).trigger('change');
	            });
	        	selectH2HTeam.dispatchEvent(new Event('change'));
				selection.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
				row.insertCell(cellCount).appendChild(selection);
				setDropdownOptionToSelectOptionArray($(selection),1);
				
				cellCount = cellCount + 1;
				
				switch (whatToProcess) {
			        case 'Control_Shift_X':
				        select = document.createElement('select');
						select.id = 'selectDirection';
						select.name = select.id;
						['BowlerVsBatsman','BatsmanVsBowler'].forEach(function(teams){
							option = document.createElement('option');
							option.value = teams;
							option.text = teams;
							select.appendChild(option);
						});
						row.insertCell(cellCount).appendChild(select);
						cellCount = cellCount + 1;
						
			            row.insertCell(cellCount).id = 'Player';
			            cellCount++;	
			            	 let directionValue = select.value;	
			            	   $(select).on('change', function() {
						        directionValue = select.value; 						
						        processCricketProcedures(
						            "GRAPHICS-OPTIONS_DATA",whatToProcess + "," +
			                		(selected_options[1] || $(selection).find('option').first().val()) + "," 
						             + document.getElementById('which_inning').value + "," + directionValue 
						        );
						    });
			                 $(selection).on('change', function () {
				                let selectedPlayerId = $(this).val();				
				                processCricketProcedures(
				                    "GRAPHICS-OPTIONS_DATA",
				                    whatToProcess + "," + selectedPlayerId + "," +
				                    document.getElementById('which_inning').value+","+
				                    directionValue );
			            });			
			            processCricketProcedures(
			                "GRAPHICS-OPTIONS_DATA",
			                whatToProcess + "," +
			                (selected_options[1] || $(selection).find('option').first().val()) + "," +
			                document.getElementById('which_inning').value+","+directionValue);
			            break;
			           case 'Shift_W':
			           	select = document.createElement('select');
						select.id = 'selectProfile';
						select.name = select.id;
						const Caption = [
						    { value: 'U19ODI', text: 'U19 ODI' },
						    { value: 'LIST A', text: 'LIST A' },
						    { value: 'ACCU19', text: 'ACC U19' },
						    { value: 'SA TRI-NATION 2023-24', text: 'SA TRI-NATION' }
						];
						
						Caption.forEach(optionData => {
						    option = document.createElement('option');
						    option.value = optionData.value;
						    option.text = optionData.text;
						    select.appendChild(option);
						});
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
						row.insertCell(cellCount).appendChild(select);
						removeSelectDuplicates(select.id);
						setDropdownOptionToSelectOptionArray($(select),2);
						cellCount = cellCount + 1;
						
					break;
			    }
			break;
		case 'Alt_Shift_R':
			header_text.innerHTML = 'TEAM FIXTURES/RESULTS';
			
			select = document.createElement('select');
			select.id = 'selectTeams';
			select.name = select.id;

			dataToProcess.forEach(function(teams1){
				option = document.createElement('option');
				option.value = teams1.teamId;
				option.text = teams1.teamName1;
				select.appendChild(option);
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;	
		case "Control_Shift_F8":
				header_text.innerHTML = 'TOURNAMENT TEAM TOP 5';
			    select = document.createElement('select');
			    select.id = 'selectTeam';
			    
				dataToProcess.forEach(function(teams){
					option = document.createElement('option');
					option.value = teams.teamId;
					option.text = teams.teamName1;
					select.appendChild(option);
				});
				
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'BENGAL-T20':
						$(select).on('change', function() {
					        setDropdownOptionToSelectOptionArray(this, 2);
					        processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," +$('#selectTeam').val() + "," + 
					            ($('#selectStats').val() || $('#selectStats').find('option').first().val()) + "," +$('#selectPic').val());
					    });
					    row.insertCell(cellCount).appendChild(select);
					    setDropdownOptionToSelectOptionArray($(select), 2);
						break;
					default:
						$(select).on('change', function() {
					        setDropdownOptionToSelectOptionArray(this, 2);
					        processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," +$('#selectTeam').val() + "," + 
					            ($('#selectStats').val() || $('#selectStats').find('option').first().val()));
					    });
					    row.insertCell(cellCount).appendChild(select);
					    setDropdownOptionToSelectOptionArray($(select), 2);
						break;
				}
			     
			    cellCount++;
			
			    select = document.createElement('select');
			    select.id = 'selectStats';
			    select.name = select.id;
			    ['MOST RUNS', 'MOST WICKETS', 'MOST FOURS', 'MOST SIXES'].forEach(stat => {
			        option = document.createElement('option');
			        option.value = stat;
			        option.text = stat;
			        select.appendChild(option);
			    });
			    select.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 1)");
			     $('#selectStats').on('change', function() {
			            setDropdownOptionToSelectOptionArray($(select), 1);
			       });
			    row.insertCell(cellCount).appendChild(select);
			    cellCount++;
			    document.getElementById('selectStats').dispatchEvent(new Event('change'));
			    row.insertCell(cellCount).id = 'Player';
			    cellCount++;
			    
			    switch($('#selected_broadcaster').val().toUpperCase()){
					case 'BENGAL-T20':
						select = document.createElement('select');
						select.id = 'selectPic';
						select.name = select.id;
			
						option = document.createElement('option');
						option.value = 'withoutphoto';
						option.text = 'WithOut Photo';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'withphoto';
						option.text = 'With Photo';
						select.appendChild(option);
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 3)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),3);
						removeSelectDuplicates(select.id);
						cellCount = cellCount + 1;
			
						$('#selectStats').on('change', function() {
			    			processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," + $('#selectTeam').val()+","+
			    				($('#selectStats').val() || $(this).find('option').first().val()) + "," + $('#selectPic').val());
					    });
						break;
					default:
						$('#selectStats').on('change', function() {
			    			processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," + $('#selectTeam').val()+","+
			    				($('#selectStats').val() || $(this).find('option').first().val()));
					    });
						break;
				}
				
			    $('#selectStats').trigger('change');
			    break;
		case 'Control_Shift_M':
			header_text.innerHTML = 'LT MATCH IDENT';
			
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'T20_MUMBAI':
				select = document.createElement('select');
				select.id = 'selectMatchData';
				select.name = select.id;

				
				session_match.match.inning.forEach(function(inn){
					if(inn.isCurrentInning == 'YES'){
						if(inn.inningNumber == 1){	
							option = document.createElement('option');
							option.value = 'VENUE';
							option.text = 'VENUE';
							select.appendChild(option);
				
							option = document.createElement('option');
							option.value = 'SCORE';
							option.text = 'INNING SCORE';
							select.appendChild(option);
						}
						else{
							option = document.createElement('option');
							option.value = 'VENUE';
							option.text = 'VENUE';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'EQUATION';
							option.text = 'EQUATION';
							select.appendChild(option);
						}
					}
				});
				break;
			default :
				select = document.createElement('select');
				select.id = 'selectMatchData';
				select.name = select.id;

				option = document.createElement('option');
				option.value = 'Venue';
				option.text = 'Venue' ;
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Toss';
				option.text = 'Toss' ;
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Target';
				option.text = 'Target' ;
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Result';
				option.text = 'Result' ;
				select.appendChild(option);
				break;
		 	}
			
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		case 'Alt_Shift_Z':
			header_text.innerHTML = 'TEAMS LOGOS/CAPTAINS';
			
			select = document.createElement('select');
			select.id = 'selectData';
			select.name = select.id;

			option = document.createElement('option');
			option.value = 'logo';
			option.text = 'Logo' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'captain';
			option.text = 'Captain' ;
			select.appendChild(option);
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		case 'Shift_I':
		    header_text.innerHTML = 'IMPACT PLAYER';
		
		    // Team select
		    select = document.createElement('select');
		    select.id = 'selectTeams';
		    select.name = select.id;
		
		    option = document.createElement('option');
		    option.value = session_match.setup.homeTeam.teamId;
		    option.text = session_match.setup.homeTeam.teamName4;
		    select.appendChild(option);
		
		    option = document.createElement('option');
		    option.value = session_match.setup.awayTeam.teamId;
		    option.text = session_match.setup.awayTeam.teamName4;
		    select.appendChild(option);
		
		    select.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 0)");
		    row.insertCell(cellCount).appendChild(select);
		    setDropdownOptionToSelectOptionArray($(select), 0);
		    removeSelectDuplicates(select.id);
		    cellCount++;
		
		    // OUT and IN dropdowns
		    let selectionsOut = document.createElement('select');
		    selectionsOut.id = 'selectTeamids_out';
		    selectionsOut.name = selectionsOut.id;
		    selectionsOut.style.width = 'auto';
		    selectionsOut.style.height = 'auto';
		
		    let selectionsIn = document.createElement('select');
		    selectionsIn.id = 'selectTeamids_in';
		    selectionsIn.name = selectionsIn.id;
		    selectionsIn.style.width = 'auto';
		    selectionsIn.style.height = 'auto';
		
		    // SINGLE combined listener for both OUT and IN dropdowns
		    select.addEventListener('change', function () {
		        let teamId = parseInt(this.value, 10);
		        let isHome = teamId === session_match.setup.homeTeamId;
		
		        // Clear both dropdowns
		        selectionsOut.innerHTML = '';
		        selectionsIn.innerHTML = '';
		
		        // OUT header
		        let outOption = document.createElement('option');
		        outOption.value = 'out';
		        outOption.text = '-- SELECT OUT PLAYER --';
		        selectionsOut.appendChild(outOption);
		
		        // IN header
		        let inOption = document.createElement('option');
		        inOption.value = 'in';
		        inOption.text = '-- SELECT IN PLAYER --';
		        selectionsIn.appendChild(inOption);
		
		        // Choose team
		        let squad = isHome ? session_match.setup.homeSquad : session_match.setup.awaySquad;
		        let substitutes = isHome ? session_match.setup.homeSubstitutes : session_match.setup.awaySubstitutes;
		
		        function addPlayerOption(selectElement, player, isOther = false) {
				    let suffix = isOther ? ' (OTHER)' : '';
				    let opt = document.createElement('option');
				    opt.value = player.playerId;
				    opt.text = player.full_name + suffix;
				    selectElement.appendChild(opt);
				}
				
				// First add substitutes to selectionsIn
				substitutes.forEach(function (player) {
				    addPlayerOption(selectionsIn, player, true);
				});
				
				// Then add squad to selectionsIn
				squad.forEach(function (player) {
				    addPlayerOption(selectionsIn, player, false);
				});
				
				// For selectionsOut, add squad first
				squad.forEach(function (player) {
				    addPlayerOption(selectionsOut, player, false);
				});
				
				// Then add substitutes after squad
				substitutes.forEach(function (player) {
				    addPlayerOption(selectionsOut, player, true);
				});
		
		        $(selectionsOut).trigger('change');
		        $(selectionsIn).trigger('change');
		    });
		
		    select.dispatchEvent(new Event('change'));
		
		    // OUT player dropdown
		    selectionsOut.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 1)");
		    row.insertCell(cellCount).appendChild(selectionsOut);
		    setDropdownOptionToSelectOptionArray($(selectionsOut), 1);
		    cellCount++;
		
		    // IN player dropdown
		    selectionsIn.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 2)");
		    row.insertCell(cellCount).appendChild(selectionsIn);
		    setDropdownOptionToSelectOptionArray($(selectionsIn), 2);
		    cellCount++;
		    
		    if($('#selected_broadcaster').val() != 'ISPL'){
				select = document.createElement('select');
				select.id = 'selectPhotoImpact';
				select.name = select.id;
	
				option = document.createElement('option');
				option.value = 'WITH_PHOTO';
				option.text = 'WITH PHOTO' ;
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'WITHOUT_PHOTO';
				option.text = 'WITHOUT PHOTO' ;
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 3)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),3);
				removeSelectDuplicates(select.id);
				cellCount = cellCount + 1;
			}
		    break;
		case 'Control_Shift_D':
			header_text.innerHTML = 'DOUBLE MATCH IDENT/PROMO';
			
			select = document.createElement('select');
			select.id = 'selectTieID';
			select.name = select.id;

			option = document.createElement('option');
			option.value = 'today';
			option.text = 'Today' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'tomorrow';
			option.text = 'Tomorrow' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'day_after_tomorrow';
			option.text = 'Day After Tomorrow' ;
			select.appendChild(option);
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		case 'Control_Shift_F4':
			select = document.createElement('select');
			select.id = 'selectPlayer';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.partnerships.sort(function(a, b) {
				      if (b.totalRuns === a.totalRuns) {
						 return a.totalBalls - b.totalBalls; 
				      }
				      return b.totalRuns - a.totalRuns;
				    });
					
					inn.partnerships.forEach(function(p,p_index,p_arr){
						option = document.createElement('option');
						option.value = p.partnershipNumber;
						option.text = p.partnershipNumber+" "+p.firstPlayer.ticker_name+"-"+p.secondPlayer.ticker_name;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		
		case 'Control_F8':	
			header_text.innerHTML = 'TAPE BALL';
			
			select = document.createElement('select');
			select.id = 'selectTapeBall';
			select.name = select.id;
			
			option = document.createElement('option');
            option.value = 'TAPE_BALL_FULL';
            option.text = 'FULL';
            select.appendChild(option);
            
            option = document.createElement('option');
            option.value = 'TAPE_BALL_SHORT';
            option.text = 'SHORT';
            select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;
			
		case 'Alt_/':
			header_text.innerHTML = 'SUPER OVER';
			
			select = document.createElement('select');
			select.id = 'selectTapeBall';
			select.name = select.id;
			
			option = document.createElement('option');
            option.value = 'SUPER_OVER_FULL';
            option.text = 'FULL';
            select.appendChild(option);
            
            option = document.createElement('option');
            option.value = 'SUPER_OVER_SHORT';
            option.text = 'SHORT';
            select.appendChild(option);
            
            option = document.createElement('option');
            option.value = 'SUPER_OVER_THIS_OVER';
            option.text = 'SHORT THIS OVER';
            select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;
			
		case 'Control_Shift_O': case 'Control_5':
			switch(whatToProcess){
				case 'Control_Shift_O':
					header_text.innerHTML = 'LINEUP';
					break;
				
				case 'Control_5':
					header_text.innerHTML = 'INFOBAR LINEUP';
					break;
			}
		
			select = document.createElement('select');
			select.id = 'selectTeams';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = session_match.setup.homeTeamId;
			option.text = session_match.setup.homeTeam.teamName1;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = session_match.setup.awayTeamId;
			option.text = session_match.setup.awayTeam.teamName1;
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectlineUp';
			select.name = select.id;
			
			option = document.createElement('option');
            option.value = 'BATTING_CARD';
            option.text = 'BATTING CARD';
            select.appendChild(option);
            
            option = document.createElement('option');
            option.value = 'ROLES';
            option.text = 'ROLES';
            select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1;
			break;	
		case 'Control_Shift_F2': case 'Control_Shift_V':
			

			select = document.createElement('select');
			select.id = 'selectPlayer';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.sort(function(a, b) {
				      if (b.wickets === a.wickets) {
						if(a.economyRate === b.economyRate){
							return b.dots - a.dots;
					  	}
				        return a.economyRate - b.economyRate;
				      }
				      return b.wickets - a.wickets;
				    });
					
					inn.bowlingCard.forEach(function(boc,boc_index,bc_arr){
						option = document.createElement('option');
						option.value = boc.playerId;
						option.text = boc.player.full_name;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectStatsType';
			select.name = select.id;
			switch(whatToProcess){
				case 'Control_Shift_V':
				header_text.innerHTML = 'BALL POP UP';
				option = document.createElement('option');
				option.value = 'figure';
				option.text = 'Bowler Figure';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'economy';
				option.text = 'Economy';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Dot_percent';
				option.text = 'Dot percent';
				select.appendChild(option);
								
				break;
				case 'Control_Shift_F2':
				header_text.innerHTML = 'BALL PERFORMER';
				option = document.createElement('option');
				option.value = 'performer';
				option.text = 'Performer';
				select.appendChild(option);
				break;
			}
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1;
			break;
		case 'Control_Shift_F1': case 'Control_Shift_U':
			select = document.createElement('select');
			select.id = 'selectPlayer';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.sort(function(a, b) {
				      if (b.runs === a.runs) {
						if(a.balls === b.balls){
							return b.fours - a.fours
					  	}
				        // If totalRuns are equal, sort by totalBalls (ascending)
				        return a.balls - b.balls;
				      }
				      // Otherwise, sort by totalRuns (descending)
				      return b.runs - a.runs;
				    });
					
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						option = document.createElement('option');
						option.value = bc.playerId;
						option.text = bc.player.full_name + " - " + bc.status;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectStatsType';
			select.name = select.id;
			switch(whatToProcess){
				case 'Control_Shift_U':
				header_text.innerHTML = 'BAT POP UP';
				option = document.createElement('option');
				option.value = 'score';
				option.text = 'Score';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'strikeRate';
				option.text = 'Strike Rate';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'boundary';
				option.text = 'Boundary';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'boundary_percent';
				option.text = 'Boundary Percent';
				select.appendChild(option);
								
				break;
				
				case 'Control_Shift_F1':
				header_text.innerHTML = 'BAT PERFORMER/PARTNERSHIP';
				option = document.createElement('option');
				option.value = 'performer';
				option.text = 'Performer';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'partnership';
				option.text = 'Partnership';
				select.appendChild(option);
				break;
			}
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1;
			break;
		case 'Control_Shift_E':
			header_text.innerHTML = 'BOWLER VS ALL BATSMAN';
			select = document.createElement('select');
			select.id = 'selectPlayer';
			select.name = select.id;
				select = document.createElement('select');
				select.id = 'selectBowler';
				select.name = select.id;
				
				session_match.match.inning.forEach(function(inn){
					if(inn.inningNumber == document.getElementById('which_inning').value){
						inn.bowlingCard.forEach(function(boc){
							if(boc.status == 'CURRENTBOWLER'){
								option = document.createElement('option');
								option.value = boc.player.playerId;
								option.text = boc.player.full_name;
								select.appendChild(option);
							}
						});
						
						inn.bowlingCard.forEach(function(boc,boc_index,bc_arr){
							option = document.createElement('option');
							option.value = boc.playerId;
							option.text = boc.player.full_name;	
							select.appendChild(option);
						});
					}
				});
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				removeSelectDuplicates(select.id);
				cellCount = cellCount + 1;
		break;
		case 'Control_Shift_F':
			header_text.innerHTML = 'BATSMAN VS ALL BOWLERS';
			select = document.createElement('select');
			select.id = 'selectPlayer';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.forEach(function(bc){
						if(bc.status == 'NOT OUT'){
							if(bc.onStrike == 'YES'){
								option = document.createElement('option');
								option.value = bc.playerId;
								option.text = bc.player.full_name + " - " + bc.status;
								select.appendChild(option);
							}else{
								option = document.createElement('option');
								option.value = bc.playerId;
								option.text = bc.player.full_name + " - " + bc.status;
								select.appendChild(option);
							}
						}
					});
					
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						option = document.createElement('option');
						option.value = bc.playerId;
						option.text = bc.player.full_name + " - " + bc.status;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'BENGAL-T20': case 'NPL':  case 'MPL': case 'APL':
				break;
				default:
				select = document.createElement('select');
				select.id = 'selectBowler';
				select.name = select.id;
				
				session_match.match.inning.forEach(function(inn){
					if(inn.inningNumber == document.getElementById('which_inning').value){
						inn.bowlingCard.forEach(function(boc){
							if(boc.status == 'CURRENTBOWLER'){
								option = document.createElement('option');
								option.value = boc.player.playerId;
								option.text = boc.player.full_name;
								select.appendChild(option);
							}
						});
						
						inn.bowlingCard.forEach(function(boc,boc_index,bc_arr){
							option = document.createElement('option');
							option.value = boc.playerId;
							option.text = boc.player.full_name;	
							select.appendChild(option);
						});
					}
				});
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),1);
				removeSelectDuplicates(select.id);
				cellCount = cellCount + 1;
			}
		break;
		case 'Control_i':
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'T20_MUMBAI':
				header_text.innerHTML = 'PLAYER INNING BUILDER';
								
				select = document.createElement('select');
				select.id = 'selectType';
				select.name = select.id;
				
				session_match.match.inning.forEach(function(inn){
				if(inn.isCurrentInning == 'YES'){
					inn.battingCard.forEach(function(bc){
					if(bc.status == 'NOT OUT'){
						option = document.createElement('option');
						option.value = bc.playerId;
						option.text = bc.player.full_name + " - " + bc.status;	
						select.appendChild(option);	
					}
				});
				}
				});
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount++;
				break;
			default:
				header_text.innerHTML = 'BATSMAN SCORE SPLIT';
				select = document.createElement('select');
				select.id = 'selectPlayer';
				select.name = select.id;
				
				session_match.match.inning.forEach(function(inn){
					if(inn.inningNumber == document.getElementById('which_inning').value){
						inn.battingCard.forEach(function(bc){
							if(bc.status == 'NOT OUT'){
								if(bc.onStrike == 'YES'){
									option = document.createElement('option');
									option.value = bc.playerId;
									option.text = bc.player.full_name + " - " + bc.status;
									select.appendChild(option);
								}else{
									option = document.createElement('option');
									option.value = bc.playerId;
									option.text = bc.player.full_name + " - " + bc.status;
									select.appendChild(option);
								}
							}
						});
						
						inn.battingCard.forEach(function(bc,bc_index,bc_arr){
							option = document.createElement('option');
							option.value = bc.playerId;
							option.text = bc.player.full_name + " - " + bc.status;	
							select.appendChild(option);
						});
					}
				});
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				removeSelectDuplicates(select.id);
				cellCount = cellCount + 1;
				break;
			}
		break;	
		case 'Control_F11':
			header_text.innerHTML = 'SUMMARY';
			select = document.createElement('select');
			select.id = 'selectSummary';
			select.name = select.id;
			
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'ISPL': case 'T20_MUMBAI':
				option = document.createElement('option');
	            option.value = 'normalsummary';
	            option.text = 'Normal Summary';
	            select.appendChild(option);
	            
				option = document.createElement('option');
	            option.value = 'photosummary';
	            option.text = 'Photo Summary';
	            select.appendChild(option);
				break;
			default:
				option = document.createElement('option');
	            option.value = 'captain';
	            option.text = 'Captain';
	            select.appendChild(option);
	            
				option = document.createElement('option');
	            option.value = 'logo';
	            option.text = 'logo';
	            select.appendChild(option);
	            
	            switch($('#selected_broadcaster').val().toUpperCase()){
					case 'BENGAL-T20':
					option = document.createElement('option');
		            option.value = 'trophy';
		            option.text = 'trophy';
		            select.appendChild(option);
					break;
				}
			}
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;
			case 'F1':
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'ISPL':
						if(isSplitScorecard){
						select = document.createElement('select');
						select.id = 'selectBatter';
						select.name = select.id;
						
						session_match.match.inning.forEach(function(inn){
							if(inn.inningNumber == document.getElementById('which_inning').value){
								inn.battingCard.sort(function(a, b) {
							      if (b.runs === a.runs) {
									if(a.balls === b.balls){
										return b.fours - a.fours
								  	}
							        // If totalRuns are equal, sort by totalBalls (ascending)
							        return a.balls - b.balls;
							      }
							      // Otherwise, sort by totalRuns (descending)
							      return b.runs - a.runs;
							    });
								
								inn.battingCard.forEach(function(bc,bc_index,bc_arr){
									option = document.createElement('option');
									option.value = bc.playerId;
									option.text = bc.player.full_name + " - " + bc.status;	
									select.appendChild(option);
								});
							}
						});
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),1);
						removeSelectDuplicates(select.id);
						cellCount = cellCount + 1;
						
						select = document.createElement('select');
						select.id = 'selectBowler';
						select.name = select.id;
						
						session_match.match.inning.forEach(function(inn){
							if(inn.inningNumber == document.getElementById('which_inning').value){
								inn.bowlingCard.sort(function(a, b) {
							      if (b.wickets === a.wickets) {
									if(a.economyRate === b.economyRate){
										return b.dots - a.dots;
								  	}
							        return a.economyRate - b.economyRate;
							      }
							      return b.wickets - a.wickets;
							    });
								
								inn.bowlingCard.forEach(function(boc,boc_index,bc_arr){
									option = document.createElement('option');
									option.value = boc.playerId;
									option.text = boc.player.full_name;	
									select.appendChild(option);
								});
							}
						});
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),2);
						removeSelectDuplicates(select.id);
						cellCount = cellCount + 1;
						
						select = document.createElement('select');
						select.id = 'manhattanOrNot';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = '';
						option.text = '';	
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'MANHATTAN';
						option.text = 'MANHATTAN';	
						select.appendChild(option);
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 3)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),3);
						cellCount = cellCount + 1;
					}else{
						header_text.innerHTML = 'SCORECARD';
						select = document.createElement('select');
						select.id = 'selectScoreCard';
						select.name = select.id;
						
						option = document.createElement('option');
			            option.value = 'TRADITIONAL';
			            option.text = 'TRADITIONAL';
			            select.appendChild(option);
										
						option = document.createElement('option');
			            option.value = 'NORMAL';
			            option.text = 'NORMAL';
			            select.appendChild(option);
						
						option = document.createElement('option');
			            option.value = 'SPLIT';
			            option.text = 'SPLIT';
			            select.appendChild(option);
			            
			            option = document.createElement('option');
			            option.value = 'BATTING_CHANGE_ON';
			            option.text = 'BATTING CHANGE ON';
			            select.appendChild(option);
			            
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),0);
						cellCount = cellCount + 1;
								}
						break;
					case 'VIDARBHA':
						header_text.innerHTML = 'SCORECARD';
						select = document.createElement('select');
						select.id = 'selectScoreCard';
						select.name = select.id;
						
						option = document.createElement('option');
			            option.value = 'SPLIT';
			            option.text = 'SPLIT';
			            select.appendChild(option);
			            
						option = document.createElement('option');
			            option.value = 'NORMAL';
			            option.text = 'NORMAL';
			            select.appendChild(option);
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),0);
						cellCount = cellCount + 1;
				break;
			}
			
			break;
		case 'F2':
	    	header_text.innerHTML = 'BALL CARD';
			select = document.createElement('select');
			select.id = 'selectScoreCard';
			select.name = select.id;
			
			option = document.createElement('option');
            option.value = 'TRADITIONAL';
            option.text = 'TRADITIONAL';
            select.appendChild(option);
							
            option = document.createElement('option');
            option.value = 'BOWLING_CHANGE_ON';
            option.text = 'BOWLING CHANGE ON';
            select.appendChild(option);
            
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
		 	break;
				
		case 'Alt_c':
			header_text.innerHTML = 'CHALLENGED RUNS';
		
			select = document.createElement('input');
			select.type = "text";
			select.id = 'selectFreeText';
			select.value = '10';
			
			select.setAttribute('onchange',"setTextBoxOptionToSelectOptionArray(0)");
			row.insertCell(cellCount).appendChild(select);
			setTextBoxOptionToSelectOptionArray(0);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectCRType';
			select.name = select.id;
			
			option = document.createElement('option');
            option.value = 'CHALLENGED_IDENT';
            option.text = 'Ident';
            select.appendChild(option);
            
            option = document.createElement('option');
            option.value = 'CHALLENGED_RUNS';
            option.text = 'SCORES';
            select.appendChild(option);
            
            option = document.createElement('option');
            option.value = 'CHALLENGED_RUNS_CUMM';
            option.text = 'Cummulative Scores';
            select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1;
			break;
		case 'Shift_F':
			header_text.innerHTML = 'WICKET SEQUENCE';
			select = document.createElement('select');
			select.id = 'selectWicketSequence';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.fallsOfWickets.forEach(function(fow,fow_index,fow_arr){
						inn.battingCard.forEach(function(bc,bc_index,bc_arr){
							if(fow.fowPlayerID == bc.playerId){
								option = document.createElement('option');
								option.value = bc.playerId;
								option.text = bc.player.full_name + " - " + bc.status;	
								select.appendChild(option);
							}
						});
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionForWicketSequence(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionForWicketSequence(0);
			cellCount = cellCount + 1;
		break;
		case "Alt_b": 
		    header_text.innerHTML = 'BOWLER WICKET SEQUENCE';
		    select = document.createElement('select');
		    select.id = 'selectWicketSequence';
		    select.name = select.id;
		    session_match.match.inning.forEach(function(inn) {
		        if (inn.inningNumber == document.getElementById('which_inning').value) {
		            inn.bowlingCard.forEach(function(bc) {
		                option = document.createElement('option');
		                option.value = bc.playerId;
		                option.text = bc.player.full_name;    
		                select.appendChild(option);
		            });
		        }
		    });		
		    select.setAttribute('onchange',"setDropdownOptionForWicketBowlerSequence(this, 0)");
			row.insertCell(0).appendChild(select);
			setDropdownOptionForWicketBowlerSequence(0);
			cellCount = cellCount + 1;	
				    
		    select = document.createElement('select');
		    select.id = 'selectWicketplayer';
		    select.name = select.id;
		    
		    $('#selectWicketSequence').on('change', function() { 
				$('#selectWicketplayer').empty()
			    session_match.match.inning.forEach(function(inn) {
			        if (inn.inningNumber == document.getElementById('which_inning').value) {
			             inn.battingCard.forEach(function(bc) {
						if (bc.howOutPartTwo && $('#selectWicketSequence option:selected').text().toUpperCase().includes(bc.howOutPartTwo.split(' ')[1])) {
							option = document.createElement('option');
			                option.value = bc.playerId;
			                option.text = bc.player.full_name;    
			                select.appendChild(option);
						}   
			          });
			        }
			    });
			    select.setAttribute('onchange',"setDropdownOptionForWicketBowlerSequence(this, 1)");
				row.insertCell(1).appendChild(select);
				setDropdownOptionForWicketBowlerSequence(1);
			}).trigger('change');
			cellCount = 2;
		    break;
		case 'Alt_q':
			header_text.innerHTML = 'POTT';
			select = document.createElement('select');
			select.id = 'selectPott';
			select.name = select.id;
			dataToProcess.forEach(function(pott1){	
				option = document.createElement('option');
	            option.value = pott.playerId1;
	            option.text = pott.player1.full_name;
	            select.appendChild(option);
	            
	            option = document.createElement('option');
	            option.value = pott.playerId2;
	            option.text = pott.player2.full_name;
	            select.appendChild(option);
	            
	            option = document.createElement('option');
	            option.value = pott.playerId3;
	            option.text = pott.player3.full_name;
	            select.appendChild(option);
	            
	            option = document.createElement('option');
	            option.value = pott.playerId4;
	            option.text = pott.player4.full_name;
	            select.appendChild(option);
	        });
	        
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			
			cellCount = cellCount + 1;
			break;
		case 'Shift_F4':
			header_text.innerHTML = 'BUG MULTI PARTNERSHIP';
			select = document.createElement('select');
			select.id = 'selectPartnership';
			select.name = select.id;
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					for(var i=1; i<=inn.partnerships.length; i++) {
			            option = document.createElement('option');
						option.value = i;
						option.text = i;
						select.appendChild(option);
					}
				}
			});
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
		break;
		case 'Control_F12': case 'Shift_F12':
			
			switch(whatToProcess) {
			case 'Control_F12':
				header_text.innerHTML = 'INFOBAR IDENT';
				break;
			case 'Shift_F12':
				header_text.innerHTML = 'INFOBAR IDENT CHANGE ON';
				break;
			}
			
			select = document.createElement('select');
			select.id = 'selectIdentInfo';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.isCurrentInning == 'YES'){
					if(inn.inningNumber == 1){
						/*option = document.createElement('option');
						option.value = 'TOURNAMENT';
						option.text = 'Tournament';
						select.appendChild(option);*/
						
						option = document.createElement('option');
						option.value = 'TOSS';
						option.text = 'Toss';
						select.appendChild(option);
												
						option = document.createElement('option');
						option.value = 'VENUE';
						option.text = 'Venue';
						select.appendChild(option);
			
						option = document.createElement('option');
						option.value = 'SUPEROVER';
						option.text = 'Super Over';
						select.appendChild(option);
					}
					else{
						option = document.createElement('option');
						option.value = 'TARGET';
						option.text = 'Target';
						select.appendChild(option);
						
						/*option = document.createElement('option');
						option.value = 'RESULT';
						option.text = 'Result';
						select.appendChild(option);
									
						option = document.createElement('option');
						option.value = 'TOURNAMENT';
						option.text = 'Tournament';
						select.appendChild(option);*/
						
						option = document.createElement('option');
						option.value = 'VENUE';
						option.text = 'Venue';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'SUPEROVER';
						option.text = 'Super Over';
						select.appendChild(option);
					}
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
		break;
		case 'F12':
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'VIDARBHA':
								
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'BATSMAN';
				option.text = 'Batsman/Bowler';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
							
				break;
			case 'T20_MUMBAI':
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
				
				if(session_match.setup.matchType == 'SUPER_OVER'){
					option = document.createElement('option');
					option.value = 'SUPER_OVER';
					option.text = 'Super Over';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'CRR';
					option.text = 'Current Run Rate';
					select.appendChild(option);
					
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
							if(inn.inningNumber == 1){
								
							}
							else{
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
							}
						}
					});
				}else{
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
							if(inn.inningNumber == 1){
								option = document.createElement('option');
								option.value = 'TOSS';
								option.text = 'Toss';
								select.appendChild(option);
							}
							else{
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'RRR';
								option.text = 'Required Run Rate';
								select.appendChild(option);
							}
						}
					});
					
					option = document.createElement('option');
					option.value = 'VENUE';
					option.text = 'Venue';
					select.appendChild(option);
		
					option = document.createElement('option');
					option.value = 'CRR';
					option.text = 'Current Run Rate';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'SUPER_OVER';
					option.text = 'Super Over';
					select.appendChild(option);
				}
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
				break;
			case 'LEGENDS-90':
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'BATSMAN';
				option.text = 'Batsman/Bowler';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
				break;
			case 'MPL': case 'NPL': case 'APL': 
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'BATSMAN';
				option.text = 'Batsman/Bowler';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'PHOTO BATSMAN';
				option.text = 'Photo Batsman/Bowler';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
				break;	
			case 'ISPL':
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'BATSMAN';
				option.text = 'Batsman/Bowler';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
				break;
			case 'BENGAL-T20': case 'DOAD-BILATERAL':
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'BATSMAN';
				option.text = 'Batsman/Bowler';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectLeftBottom';
				select.name = select.id;
	
				session_match.match.inning.forEach(function(inn){
					if(inn.isCurrentInning == 'YES'){
						if(inn.inningNumber == 1){
							option = document.createElement('option');
							option.value = 'TOSS';
							option.text = 'Toss';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'CRR';
							option.text = 'Run Rate';
							select.appendChild(option);
						}
						else{
							option = document.createElement('option');
							option.value = 'TARGET';
							option.text = 'Target';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'RRR';
							option.text = 'Required Rate';
							select.appendChild(option);
						}
					}
				});
				
				option = document.createElement('option');
				option.value = 'SUPER_OVER';
				option.text = 'Super Over';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),1);
				cellCount = cellCount + 1;
				break; 	
				
			case 'ICC-U19-2023':
				
			select = document.createElement('select');
			select.id = 'selectMiddleStat';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'IDENT_TEAM';
			option.text = 'Ident & Team';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'IDENT_TOURNAMENT';
			option.text = 'Ident & tournament';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'BATSMAN';
			option.text = 'Batsman/Bowler';
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectLeftBottom';
			select.name = select.id;

			option = document.createElement('option');
			option.value = 'GROUP';
			option.text = 'Group';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'VENUE';
			option.text = 'Venue Name';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'CRR';
			option.text = 'Run Rate';
			select.appendChild(option);

			session_match.match.inning.forEach(function(inn){
				if(inn.isCurrentInning == 'YES'){
					if(inn.inningNumber == 1){
						option = document.createElement('option');
						option.value = 'TOSS';
						option.text = 'Toss';
						select.appendChild(option);
					}
					else{
						option = document.createElement('option');
						option.value = 'TARGET';
						option.text = 'Target';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'RRR';
						option.text = 'Required Rate';
						select.appendChild(option);
					}
				}
			});
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1;
				break;
			}
			header_text.innerHTML = 'MAIN INFOBAR';
			break;
		case 'Alt_Shift_F6':case 'Alt_Shift_F7':
			switch(whatToProcess) {
			case 'Alt_Shift_F6':
				header_text.innerHTML = 'BOWLER VS BATTER';
				break;
			case 'Alt_Shift_F7':
				header_text.innerHTML = 'BATTER VS BOWLER';
				break;
			}
			// === 1. Scope Dropdown: This Match / This Series ===
			select = document.createElement('select');
			select.id = 'selectScope';
			select.name = select.id;
	
			option = document.createElement('option');
			option.value = 'THIS_MATCH';
			option.text = 'This Match';
			select.appendChild(option);
	
			option = document.createElement('option');
			option.value = 'THIS_SERIES';
			option.text = 'This Series';
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			
			cellCount++;
			
			let teamSelect = document.createElement('select'); // <-- Fixed name
			teamSelect.id = 'selectTeams';
			teamSelect.name = teamSelect.id;
			
			option = document.createElement('option');
			option.value = session_match.setup.homeTeam.teamId;
			option.text = session_match.setup.homeTeam.teamName3;
			teamSelect.appendChild(option);
			
			option = document.createElement('option');
			option.value = session_match.setup.awayTeam.teamId;
			option.text = session_match.setup.awayTeam.teamName3;
			teamSelect.appendChild(option);
			
			teamSelect.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(teamSelect);
			setDropdownOptionToSelectOptionArray($(teamSelect),1);
			cellCount++;
			
			// === 3. Player Dropdown ===
			let playerSelect = document.createElement('select');
			playerSelect.id = 'selectTeamids_in';
			playerSelect.name = playerSelect.id;
			playerSelect.style.width = 'auto';
			playerSelect.style.height = 'auto';
			
			row.insertCell(cellCount).appendChild(playerSelect);
			cellCount++;
			
			// === Update Player Dropdown based on Team ===
			teamSelect.addEventListener('change', function () {
				let teamId = parseInt(this.value, 10);
				let isHome = teamId === session_match.setup.homeTeam.teamId;
			
				playerSelect.innerHTML = ''; // Clear existing options
			
				let squad = isHome ? session_match.setup.homeSquad : session_match.setup.awaySquad;
				let substitutes = isHome ? session_match.setup.homeSubstitutes : session_match.setup.awaySubstitutes;
			
				[...squad, ...substitutes].forEach(function (player) {
					let opt = document.createElement('option');
					opt.value = player.playerId;
					opt.text = player.full_name + (substitutes.includes(player) ? ' (OTHER)' : '');
					playerSelect.appendChild(opt);
				});
				setDropdownOptionToSelectOptionArray($(playerSelect), 2);
				playerSelect.addEventListener('change', function () {
					setDropdownOptionToSelectOptionArray($(playerSelect), 2);
				});
			});
			teamSelect.dispatchEvent(new Event('change'));
		break;
		case 'Alt_1':
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI':
					header_text.innerHTML = 'INFOBAR LEFT';
					
					select = document.createElement('select');
					select.id = 'selectMiddleStat';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'SUPER_OVER';
					option.text = 'SUPER_OVER';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BALL_LEFT';
					option.text = 'BALL_LEFT';
					select.appendChild(option);
												
					break;
				case 'VIDARBHA':
					header_text.innerHTML = 'INFOBAR MIDDLE';
					select = document.createElement('select');
					select.id = 'selectMiddleStat';
					select.name = select.id;
					
					/*option = document.createElement('option');
					option.value = 'CURR_PARTNERSHIP';
					option.text = 'Current Partnership';
					select.appendChild(option);*/
					if(session_match.setup.matchType == 'SUPER_OVER'){
						option = document.createElement('option');
						option.value = 'THIS_OVER';
						option.text = 'This Over';
						select.appendChild(option);
					}
					
					option = document.createElement('option');
					option.value = 'BOUNDARY';
					option.text = 'Inning Boundaries';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'REVIEW';
					option.text = 'Review';
					select.appendChild(option);
				
					option = document.createElement('option');
					option.value = 'BALLS_SINCE_LAST_BOUNDARY';
					option.text = 'Ball Since Last Boundary';
					select.appendChild(option);	
					
					option = document.createElement('option');
					option.value = 'LAST_X_BALLS';
					option.text = 'Last x Balls';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'AT_THIS_STAGE';
					option.text = 'At This Stage';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'EXTRAS';
					option.text = 'Extras';
					select.appendChild(option);
		
					option = document.createElement('option');
					option.value = 'LAST_WICKET';
					option.text = 'Last Wicket';
					select.appendChild(option);
					
					session_match.match.inning.forEach(function(inn,index,arr){
						if(inn.isCurrentInning == 'YES'){
							if(inn.inningNumber == 1){
								
								option = document.createElement('option');
								option.value = 'PROJECTED';
								option.text = 'Projected Score';
								select.appendChild(option);
								
							}
							else{
								/*option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);*/
								
								option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'RESULT';
								option.text = 'Result';
								select.appendChild(option);
							}
						}
					});
					break;
				case 'ISPL':
					header_text.innerHTML = 'INFOBAR MIDDLE';
					select = document.createElement('select');
					select.id = 'selectMiddleStat';
					select.name = select.id;
				
					if($('#selected_infobar').val() == 'LOF_INFOBAR'){
						option = document.createElement('option');
						option.value = 'THIS_OVER';
						option.text = 'This Over';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'THIS_OVER_RUNS';
						option.text = 'This Over Cummulative Runs';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'OVER_TIMELINE';
						option.text = 'Over TimeLine';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'TIMELINE';
						option.text = 'TimeLine';
						select.appendChild(option);
						
						/*option = document.createElement('option');
						option.value = 'CRR';
						option.text = 'Run Rate';
						select.appendChild(option);*/
						
						option = document.createElement('option');
						option.value = 'BOUNDARY';
						option.text = 'Inning Boundaries';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'EXTRAS';
						option.text = 'Extras';
						select.appendChild(option);
			
						option = document.createElement('option');
						option.value = 'LAST_WICKET';
						option.text = 'Last Wicket';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'BALLS_SINCE_LAST_BOUNDARY';
						option.text = 'Ball Since Last Boundary';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'REVIEWS_REMAINING';
						option.text = 'Reviews Remaining';
						select.appendChild(option);
						
						session_match.match.inning.forEach(function(inn){
							if(inn.isCurrentInning == 'YES'){
								if(inn.inningNumber == 1){
									
									option = document.createElement('option');
									option.value = 'TOSS';
									option.text = 'Toss';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'PROJECTED';
									option.text = 'Projected Score';
									select.appendChild(option);
									
								}else{
									option = document.createElement('option');
									option.value = 'EQUATION_SHORT_SB';
									option.text = 'Short Equation';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'RRR';
									option.text = 'Required Run Rate';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'COMPARE';
									option.text = 'Comparison';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'EQUATION';
									option.text = 'Equation';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'RESULT';
									option.text = 'Result';
									select.appendChild(option);
								}
							}
						});
					}else{
						if(session_match.setup.matchType == 'SUPER_OVER'){
							option = document.createElement('option');
							option.value = 'THIS_OVER';
							option.text = 'This Over';
							select.appendChild(option);
						}
						
						option = document.createElement('option');
						option.value = 'BOUNDARY';
						option.text = 'Inning Boundaries';
						select.appendChild(option);
						
						
						option = document.createElement('option');
						option.value = 'EXTRAS';
						option.text = 'Extras';
						select.appendChild(option);
			
						option = document.createElement('option');
						option.value = 'LAST_WICKET';
						option.text = 'Last Wicket';
						select.appendChild(option);
						
						session_match.match.inning.forEach(function(inn){
							if(inn.isCurrentInning == 'YES'){
								if(inn.inningNumber == 1){
									
									option = document.createElement('option');
									option.value = 'PROJECTED';
									option.text = 'Projected Score';
									select.appendChild(option);
									
								}
								else{
									/*option = document.createElement('option');
									option.value = 'TARGET';
									option.text = 'Target';
									select.appendChild(option);*/
									
									option = document.createElement('option');
									option.value = 'EQUATION';
									option.text = 'Equation';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'RESULT';
									option.text = 'Result';
									select.appendChild(option);
								}
							}
						});
					}
					break;
				case 'ICC-U19-2023':
				header_text.innerHTML = 'LEFT BOTTON INFOBAR SECTION';
				select = document.createElement('select');
				select.id = 'selectLeftBottom';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'GROUP';
				option.text = 'Group';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'VENUE';
				option.text = 'Venue Name';
				select.appendChild(option);
	
				option = document.createElement('option');
				option.value = 'CRR';
				option.text = 'Run Rate';
				select.appendChild(option);
	
				session_match.match.inning.forEach(function(inn){
					if(inn.isCurrentInning == 'YES'){
						if(inn.inningNumber == 1){
							option = document.createElement('option');
							option.value = 'TOSS';
							option.text = 'Toss';
							select.appendChild(option);
						}
						else{
							
							option = document.createElement('option');
							option.value = 'RRR';
							option.text = 'Required Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'TARGET';
							option.text = 'Target';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'INNINGS_SCORE';
							option.text = '1st Inning Score';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'EQUATION';
							option.text = 'Equation';
							select.appendChild(option);
						}
					}
				});
				
				if(session_match.setup.matchType == 'TEST' ||session_match.setup.matchType == 'FC'){
					
					option = document.createElement('option');
					option.value = 'REMAINING_OVERS';
					option.text = 'Remaining Overs';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BOWLING_TEAM_TARGET';
					option.text = 'Bowling Team Target';
					select.appendChild(option);
		
					option = document.createElement('option');
					option.value = 'FOLLOW_ON';
					option.text = 'Follow On';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'DAY_SESSION';
					option.text = 'Day Session';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'WHICH_INNING';
					option.text = 'Which inning';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'CURRENT_INNING_OVER';
					option.text = 'current inning Over';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'FIRST_INNING_SCORE';
					option.text = 'First Inning Score';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'LOCAL-TIME';
					option.text = 'Local Time';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'CURRENT_SESSION';
					option.text = 'Current Session Run Rate';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BOTH_TEAMS_SCORE';
					option.text = 'Both Team score';
					select.appendChild(option);
					
				}
					break;
				
				case 'BENGAL-T20':
					header_text.innerHTML = 'LEFT BOTTON INFOBAR SECTION';
					select = document.createElement('select');
					select.id = 'selectLeftBottom';
					select.name = select.id;
		
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
							if(inn.inningNumber == 1){
								option = document.createElement('option');
								option.value = 'TOSS';
								option.text = 'Toss';
								select.appendChild(option);
								
							}
							else{
								
								option = document.createElement('option');
								option.value = 'RRR';
								option.text = 'Required Rate';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
							}
						}
					});
					break;	
				}
				
				if($('#selected_broadcaster').val() != 'ISPL' && $('#selected_broadcaster').val() != 'VIDARBHA' 
					&& $('#selected_broadcaster').val() != 'T20_MUMBAI'){
					option = document.createElement('option');
					option.value = 'CRR';
					option.text = 'Run Rate';
					select.appendChild(option);
									
					option = document.createElement('option');
					option.value = 'SUPER_OVER';
					option.text = 'Super Over';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'CURR_PARTNERSHIP';
					option.text = 'CURRENT PARTNERSHIP';
					select.appendChild(option);
				}
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
				
				select.addEventListener('change', function () {
			    if (document.getElementById('selectFreeText')) {
			        document.getElementById('selectFreeText').parentElement.remove();
			    }
			    if (this.value === 'LAST_X_BALLS') {
			        let label = document.createElement('label');
			        label.setAttribute('for', 'selectFreeText');
			        label.innerHTML = 'BALLS : ';

			        let xballselect = document.createElement('input');
			        xballselect.type = "text";
			        xballselect.id = 'selectFreeText';
			        xballselect.value = '10';

			        xballselect.size = 3;
			        xballselect.maxLength = 3;
			        xballselect.style.width = "50px";

			        xballselect.setAttribute('onchange', "setTextBoxOptionToSelectOptionArray(1)");
			        let cell = row.insertCell(1);
			        cell.style.cssText = "display:flex; align-items:center; text-align:center; vertical-align:middle; gap:5px;";
			        cell.appendChild(label);
			        cell.appendChild(xballselect);

			        setTextBoxOptionToSelectOptionArray(1);

			        cellCount++;
			    }
			});

			// Trigger once initially
			select.dispatchEvent(new Event('change'));
			break;
		case 'h':
			header_text.innerHTML = 'HIGHLIGHT BUG';
			
			select = document.createElement('select');
			select.id = 'selectHighlightBug';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'WITHOUT_SPONSOR';
			option.text = 'Without Sponsor';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'WITH_SPONSOR';
			option.text = 'With Sponsor';
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;	
		case 'Alt_2':
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'VIDARBHA':
				header_text.innerHTML = 'MIDDLE INFOBAR SECTION';
			
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
	
				option = document.createElement('option');
				option.value = 'BATSMAN';
				option.text = 'Batsman/Bowler';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'CURR_PARTNERSHIP';
				option.text = 'Current Partnership';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'CRR';
				option.text = 'Run Rate';
				select.appendChild(option);
				
				session_match.match.inning.forEach(function(inn,index,arr){
					if(inn.isCurrentInning == 'YES'){
						if(inn.inningNumber == 1){
							
							/*option = document.createElement('option');
							option.value = 'PROJECTED';
							option.text = 'Projected Score';
							select.appendChild(option);*/
							
						}
						else{
							option = document.createElement('option');
							option.value = 'RRR';
							option.text = 'Required Rate';
							select.appendChild(option);
							
						}
					}
				});
				break;
								
				case 'ISPL':
				header_text.innerHTML = 'MIDDLE INFOBAR SECTION';
			
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
				
				if($('#selected_infobar').val() == 'LOF_INFOBAR'){
					
					option = document.createElement('option');
					option.value = 'BATSMAN';
					option.text = 'Batsman/Bowler';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'IDENT';
					option.text = 'IDENT';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BATTINGCARD';
					option.text = 'Batting Card';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BOWLINGCARD';
					option.text = 'Bowling Card';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'CURR_PARTNERSHIP';
					option.text = 'Current Partnership';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'POINTS_TABLE';
					option.text = 'Points Table';
					select.appendChild(option);
					
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
							if(inn.inningNumber == 1){
								
								/*option = document.createElement('option');
								option.value = 'PROJECTED';
								option.text = 'Projected Score';
								select.appendChild(option);*/
								
							}
							else{
								/*option = document.createElement('option');
								option.value = 'RRR';
								option.text = 'Required Rate';
								select.appendChild(option);*/
								
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
								
								/*option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);*/
							}
						}
					});
				}else{
					option = document.createElement('option');
					option.value = 'BATSMAN';
					option.text = 'Batsman/Bowler';
					select.appendChild(option);
					
					/*option = document.createElement('option');
					option.value = 'BATSMAN_TOURNAMENT';
					option.text = 'Batsman/Tournament';
					select.appendChild(option);*/
					
					option = document.createElement('option');
					option.value = 'IDENT_TEAM';
					option.text = 'Ident & team';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'IDENT_TOURNAMENT';
					option.text = 'Ident & Tournament';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'CURR_PARTNERSHIP';
					option.text = 'Current Partnership';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'EXTRAS';
					option.text = 'Extras';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'FOW';
					option.text = 'Fall Of Wickets';
					select.appendChild(option);
		
					option = document.createElement('option');
					option.value = 'LAST_WICKET';
					option.text = 'Last Wicket';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BALLS_SINCE_LAST_BOUNDARY';
					option.text = 'Balls Since Last Boundary';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'THIS_MATCH_FOURS';
					option.text = 'This Match Fours';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'THIS_MATCH_SIXES';
					option.text = 'This Match Sixes';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'TOURNAMENT_SIXES';
					option.text = 'Tournament Sixes';
					select.appendChild(option);
					
					/*option = document.createElement('option');
					option.value = 'REVIEWS_REMAINING';
					option.text = 'REVIEWS REMAINING';
					select.appendChild(option);*/
					
					option = document.createElement('option');
					option.value = 'CRR';
					option.text = 'Current Run Rate';
					select.appendChild(option);
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
							if(inn.inningNumber == 1){
					
								option = document.createElement('option');
								option.value = 'TOSS';
								option.text = 'Toss';
								select.appendChild(option);
					
								option = document.createElement('option');
								option.value = 'PROJECTED';
								option.text = 'Projected Score';
								select.appendChild(option);
								
							}
							else{
								option = document.createElement('option');
								option.value = 'RRR';
								option.text = 'Required Run Rate';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'CRR_RRR';
								option.text = 'Current and Required Run Rate';
								select.appendChild(option);
					
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'RESULT';
								option.text = 'Result';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'DLS_PAR_SCORE';
								option.text = 'D/L Par Score';
								select.appendChild(option);
							}
						}
					});
				}
				break;
				case 'T20_MUMBAI':
					header_text.innerHTML = 'MIDDLE INFOBAR SECTION';
			
					select = document.createElement('select');
					select.id = 'selectMiddleStat';
					select.name = select.id;
				
					option = document.createElement('option');
					option.value = 'CRR';
					option.text = 'Current Run Rate';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'VENUE';
					option.text = 'Venue';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'IDENT';
					option.text = 'IDENT';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'EXTRAS';
					option.text = 'Extras';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BOUNDARY';
					option.text = 'Boundary';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'CURR_PARTNERSHIP';
					option.text = 'Current Partnership';
					select.appendChild(option);
					
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST'&& session_match.setup.matchType != 'FC'){
							if(inn.inningNumber == 1){
								option = document.createElement('option');
								option.value = 'TOSS';
								option.text = 'Toss';
								select.appendChild(option);
							}
							else{
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'RRR';
								option.text = 'Required Run Rate';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'FIRST_INNING_SCORE';
								option.text = 'First Inning Score';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'DLS_PAR_SCORE';
								option.text = 'D/L Par Score';
								select.appendChild(option);
								
								
							}
						}
					});
					
					option = document.createElement('option');
					option.value = 'SUPER_OVER';
					option.text = 'Super Over';
					select.appendChild(option);
					
					/*option = document.createElement('option');
					option.value = 'BLANK';
					option.text = 'No Data(Only TeamName)';
					select.appendChild(option);*/
					break;
				
				case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'APL':
					header_text.innerHTML = 'MIDDLE INFOBAR SECTION';
				
					select = document.createElement('select');
					select.id = 'selectMiddleStat';
					select.name = select.id;
		
					option = document.createElement('option');
					option.value = 'BATSMAN';
					option.text = 'Batsman/Bowler';
					select.appendChild(option);
					
					switch($('#selected_broadcaster').val().toUpperCase()){
						 case 'MPL': case 'NPL': //case 'APL':
							option = document.createElement('option');
							option.value = 'PHOTO BATSMAN';
							option.text = 'Photo Batsman/Bowler';
							select.appendChild(option);
						 break;
						/* case 'LEGENDS-90':
							option = document.createElement('option');
							option.value = 'BowlerVsBatsman';
							option.text = 'BowlerVsBatsman';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'BatsmanVsBowler';
							option.text = 'BatsmanVsBowler';
							select.appendChild(option);
						 break;*/
					}
					
					/*option = document.createElement('option');
					option.value = 'BATSMAN_TOURNAMENT';
					option.text = 'Batsman/Tournament';
					select.appendChild(option);*/
					
					option = document.createElement('option');
					option.value = 'IDENT_TEAM';
					option.text = 'Ident & team';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'IDENT_TOURNAMENT';
					option.text = 'Ident & Tournament';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BALLS_SINCE_LAST_BOUNDARY';
					option.text = 'Balls Since Last Boundary';
					select.appendChild(option);
										
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
							if(inn.inningNumber == 1){
					
								option = document.createElement('option');
								option.value = 'TOSS';
								option.text = 'Toss';
								select.appendChild(option);
					
								option = document.createElement('option');
								option.value = 'PROJECTED';
								option.text = 'Projected Score';
								select.appendChild(option);
								
							}
							else{
								option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'RRR';
								option.text = 'Required Run Rate';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'CRR_RRR';
								option.text = 'Current and Required Run Rate';
								select.appendChild(option);
					
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'RESULT';
								option.text = 'Result';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'DLS_PAR_SCORE';
								option.text = 'D/L Par Score';
								select.appendChild(option);
							}
						}
					});
							
					option = document.createElement('option');
					option.value = 'CURR_PARTNERSHIP';
					option.text = 'Current Partnership';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'EXTRAS';
					option.text = 'Extras';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'FOW';
					option.text = 'Fall Of Wickets';
					select.appendChild(option);
		
					option = document.createElement('option');
					option.value = 'LAST_WICKET';
					option.text = 'Last Wicket';
					select.appendChild(option);
													
					option = document.createElement('option');
					option.value = 'PROMO';
					option.text = 'Match Promo';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'RECENT_FORM';
					option.text = 'RECENT FORM';
					select.appendChild(option);
					
					switch($('#selected_broadcaster').val().toUpperCase()){
					case 'MPL': case 'NPL': //case 'APL':
						option = document.createElement('option');
						option.value = 'TIMELINE';
						option.text = 'TIMELINE';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'NEXT_TO_BAT';
						option.text = 'Next To Bat';
						select.appendChild(option);
						break;
					}
					
					/*option = document.createElement('option');
					option.value = 'SPEED_THIS_OVER';
					option.text = 'Speed This Over';
					select.appendChild(option);*/
					
					option = document.createElement('option');
					option.value = 'INNING_DOTS';
					option.text = 'Innings Dots';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'THIS_MATCH_FOURS';
					option.text = 'This Match Fours';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'THIS_MATCH_SIXES';
					option.text = 'This Match Sixes';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'TOURNAMENT_SIXES';
					option.text = 'Tournament Sixes';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'TOURNAMENT_FOURS';
					option.text = 'Tournament Fours';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'PHASE_WISE';
					option.text = 'Phase Wise';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'PHASE_WISE_RUNRATE';
					option.text = 'PhaseWise RunRate';
					select.appendChild(option);
					
					/*option = document.createElement('option');
					option.value = 'REVIEWS_REMAINING';
					option.text = 'REVIEWS REMAINING';
					select.appendChild(option);*/
					
					option = document.createElement('option');
					option.value = 'CRR';
					option.text = 'Current Run Rate';
					select.appendChild(option);
					break;
				
				case 'ICC-U19-2023':
				header_text.innerHTML = 'MIDDLE INFOBAR SECTION';
			
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
	
				option = document.createElement('option');
				option.value = 'BATSMAN';
				option.text = 'Batsman/Bowler';
				select.appendChild(option);
				
				/*option = document.createElement('option');
				option.value = 'BATSMAN_TOURNAMENT';
				option.text = 'Batsman/Tournament';
				select.appendChild(option);*/
				
				option = document.createElement('option');
				option.value = 'IDENT_TEAM';
				option.text = 'Ident & team';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'IDENT_TOURNAMENT';
				option.text = 'Ident & Tournament';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'CURR_PARTNERSHIP';
				option.text = 'Current Partnership';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'EXTRAS';
				option.text = 'Extras';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'FOW';
				option.text = 'Fall Of Wickets';
				select.appendChild(option);
	
				option = document.createElement('option');
				option.value = 'LAST_WICKET';
				option.text = 'Last Wicket';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BALLS_SINCE_LAST_BOUNDARY';
				option.text = 'Balls Since Last Boundary';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'ARAMCO_POTD';
				option.text = 'Aramco POTD';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'CRICTOS';
				option.text = 'Crictos';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'THIS_MATCH_SIXES';
				option.text = 'This Match Sixes';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'TOURNAMENT_SIXES';
				option.text = 'Tournament Sixes';
				select.appendChild(option);
				
				session_match.match.inning.forEach(function(inn){
					if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
						if(inn.inningNumber == 1){
							
							option = document.createElement('option');
							option.value = 'PROJECTED';
							option.text = 'Projected Score';
							select.appendChild(option);
							
						}
						else{
							/*option = document.createElement('option');
							option.value = 'TARGET';
							option.text = 'Target';
							select.appendChild(option);*/
							
							option = document.createElement('option');
							option.value = 'EQUATION';
							option.text = 'Equation';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'RESULTS';
							option.text = 'Result';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'DLS_PAR_SCORE';
							option.text = 'D/L Par Score';
							select.appendChild(option);
						}
					}
				});
				if(session_match.setup.matchType == 'TEST'||session_match.setup.matchType == 'FC'){
					
					option = document.createElement('option');
					option.value = 'LEAD_TRAIL_EQUATION';
					option.text = 'Lead Trail Equation';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'EQUATION';
					option.text = 'Equation';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'CURRENT_SESSION';
					option.text = 'This Session';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'DAY_PLAY';
					option.text = 'ToDay Play';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'OVER_RATE';
					option.text = 'Over Rate';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BOWLING_TEAM_TARGET';
					option.text = 'Bowling Team Target';
					select.appendChild(option);
		
				}
				break;
			case 'BENGAL-T20':
				header_text.innerHTML = 'MIDDLE INFOBAR SECTION';
			
				select = document.createElement('select');
				select.id = 'selectMiddleStat';
				select.name = select.id;
	
				option = document.createElement('option');
				option.value = 'BATSMAN';
				option.text = 'Batsman/Bowler';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'IDENT_TEAM';
				option.text = 'Ident & team';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'IDENT_TOURNAMENT';
				option.text = 'Ident & Tournament';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'CURR_PARTNERSHIP';
				option.text = 'Current Partnership';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'EXTRAS';
				option.text = 'Extras';
				select.appendChild(option);
	
				option = document.createElement('option');
				option.value = 'LAST_WICKET';
				option.text = 'Last Wicket';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'REVIEWS_REMAINING';
				option.text = 'REVIEWS REMAINING';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'DLS_PAR_SCORE';
				option.text = 'DLS PAR SCORE';
				select.appendChild(option);
				
				session_match.match.inning.forEach(function(inn){
					if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
						if(inn.inningNumber == 1){
							
							option = document.createElement('option');
							option.value = 'PROJECTED';
							option.text = 'Projected Score';
							select.appendChild(option);
							
						}
						else{
							/*option = document.createElement('option');
							option.value = 'TARGET';
							option.text = 'Target';
							select.appendChild(option);*/
							option = document.createElement('option');
							option.value = 'CRR_RRR';
							option.text = 'Current and Required Run Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'EQUATION';
							option.text = 'Equation';
							select.appendChild(option);
						}
					}
				});
				break;	
			}
			row.insertCell(cellCount).appendChild(select);
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			
			select.addEventListener('change', function () {
				const selectedValue = this.value;
				// 🔁 Clean up any previously added special dropdowns
				['selectScope', 'selectTeams', 'selectTeamids_in', 'Promo'].forEach(id => {
					let existing = document.getElementById(id);
					if (existing) existing.parentElement.remove();
				});
				if (selectedValue === 'BowlerVsBatsman' || selectedValue === 'BatsmanVsBowler') {
					// === 1. Scope Dropdown: This Match / This Series ===
					let scopeSelect = document.createElement('select');
					scopeSelect.id = 'selectScope';
					scopeSelect.name = scopeSelect.id;
			
					let matchOption = document.createElement('option');
					matchOption.value = 'THIS_MATCH';
					matchOption.text = 'This Match';
					scopeSelect.appendChild(matchOption);
			
					let seriesOption = document.createElement('option');
					seriesOption.value = 'THIS_SERIES';
					seriesOption.text = 'This Series';
					scopeSelect.appendChild(seriesOption);
			
					row.insertCell(1).appendChild(scopeSelect);
					cellCount++;
			
					// === 2. Team Dropdown ===
					let teamSelect = document.createElement('select');
					teamSelect.id = 'selectTeams';
					teamSelect.name = teamSelect.id;
			
					let homeOption = document.createElement('option');
					homeOption.value = session_match.setup.homeTeam.teamId;
					homeOption.text = session_match.setup.homeTeam.teamName3;
					teamSelect.appendChild(homeOption);
			
					let awayOption = document.createElement('option');
					awayOption.value = session_match.setup.awayTeam.teamId;
					awayOption.text = session_match.setup.awayTeam.teamName3;
					teamSelect.appendChild(awayOption);
			
					row.insertCell(2).appendChild(teamSelect);
					cellCount++;
			
					// === 3. Player Dropdown ===
					let playerSelect = document.createElement('select');
					playerSelect.id = 'selectTeamids_in';
					playerSelect.name = playerSelect.id;
					playerSelect.style.width = 'auto';
					playerSelect.style.height = 'auto';
			
					row.insertCell(3).appendChild(playerSelect);
					cellCount++;
			
					// === Update Player Dropdown based on Team ===
					teamSelect.addEventListener('change', function () {
						let teamId = parseInt(this.value, 10);
						let isHome = teamId === session_match.setup.homeTeam.teamId;
			
						playerSelect.innerHTML = ''; // Clear existing options
			
						let squad = isHome ? session_match.setup.homeSquad : session_match.setup.awaySquad;
						let substitutes = isHome ? session_match.setup.homeSubstitutes : session_match.setup.awaySubstitutes;
			
						[...squad, ...substitutes].forEach(function (player) {
							let opt = document.createElement('option');
							opt.value = player.playerId;
							opt.text = player.full_name + (substitutes.includes(player) ? ' (OTHER)' : '');
							playerSelect.appendChild(opt);
						});
			
						$(playerSelect).trigger('change');
						setDropdownOptionToSelectOptionArray($(playerSelect), 2);
					});
			
					// Initial trigger to populate players
					teamSelect.dispatchEvent(new Event('change'));
			
					// === Set onchange logic for each dropdown if needed ===
					scopeSelect.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 1)");
					teamSelect.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 2)");
					playerSelect.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 3)");
			
					setDropdownOptionToSelectOptionArray($(scopeSelect), 1);
					setDropdownOptionToSelectOptionArray($(teamSelect), 2);
					setDropdownOptionToSelectOptionArray($(playerSelect), 3);
				}else if(selectedValue === 'RECENT_FORM'){
					// === 2. Team Dropdown ===
					let teamSelect = document.createElement('select');
					teamSelect.id = 'selectTeams';
					teamSelect.name = teamSelect.id;
			
					let homeOption = document.createElement('option');
					homeOption.value = session_match.setup.homeTeam.teamId;
					homeOption.text = session_match.setup.homeTeam.teamName3;
					teamSelect.appendChild(homeOption);
			
					let awayOption = document.createElement('option');
					awayOption.value = session_match.setup.awayTeam.teamId;
					awayOption.text = session_match.setup.awayTeam.teamName3;
					teamSelect.appendChild(awayOption);
			
					row.insertCell(1).appendChild(teamSelect);
					cellCount++;
					// === Set onchange logic for each dropdown if needed ===1
					teamSelect.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 1)");1
			
					setDropdownOptionToSelectOptionArray($(teamSelect), 1);
				}else if(selectedValue == 'PROMO'){
					row.insertCell(1).id = 'Promo';
		 			cellCount++;
					processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," +
    				(selectedValue || $(this).find('option').first().val()));
				}
				
			});
			break;
		case 'Control_Shift_(':
			header_text.innerHTML = 'INFOBAR FREE TEXT';
		
			select = document.createElement('input');
			select.type = "text";
			select.id = 'selectFreeText';
			select.value = '';
			select.style.width = '500px';
			
			select.setAttribute('onchange',"setTextBoxOptionToSelectOptionArray(0)");
			row.insertCell(cellCount).appendChild(select);
			setTextBoxOptionToSelectOptionArray(0);
			cellCount = cellCount + 1;
			break;
		
		case 'Alt_5':
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'T20_MUMBAI':
				header_text.innerHTML = 'RIGHT FULL INFOBAR SECTION';
				
				select = document.createElement('select');
				select.id = 'selectRightFullSection';
				select.name = select.id;
				
				if(session_match.setup.matchType == 'SUPER_OVER'){
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
							if(inn.inningNumber == 1){
								
							}
							else{
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);
							}
						}
					});
					
					option = document.createElement('option');
					option.value = 'OVER';
					option.text = 'This Over';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'REVIEWS_REMAINING';
					option.text = 'Reviews Remaining';
					select.appendChild(option);
					
				}else{
					option = document.createElement('option');
					option.value = 'BLANK';
					option.text = 'blank';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'PARTNERSHIP';
					option.text = 'Partnership';
					select.appendChild(option);
				
					option = document.createElement('option');
					option.value = 'LAST_X_BALLS';
					option.text = 'Last X Balls';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BOUNDARY';
					option.text = 'Innings Boundaries';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BALLS_SINCE_LAST_BOUNDARY';
					option.text = 'Ball Since Last Boundary';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'REVIEWS_REMAINING';
					option.text = 'Reviews Remaining';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'LAST_WICKET';
					option.text = 'Last Wicket';
					select.appendChild(option);
					
					/*option = document.createElement('option');
					option.value = 'NEXT_TO_BAT';
					option.text = 'Next To Bat';
					select.appendChild(option);*/
					
					option = document.createElement('option');
					option.value = 'TEAMS_STANDINGS';
					option.text = 'Standings';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'TEAMS_RUNS';
					option.text = 'Team 0s,4s,6s,';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'FreeText';
					option.text = 'FreeText';
					select.appendChild(option);
					
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
							if(inn.inningNumber == 1){
								option = document.createElement('option');
								option.value = 'Projected';
								option.text = 'Projected Score';
								select.appendChild(option);
							}
							else if(inn.inningNumber == 2){
								
								option = document.createElement('option');
								option.value = 'RUNRATE';
								option.text = 'Run-Rate';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'COMPARE';
								option.text = 'Compare';
								select.appendChild(option);
							}
						}
					});	
				}
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1
				
				select.addEventListener('change', function () {
					['selectFreeText', 'selectFreeText1'].forEach(id => {
					    const el = document.getElementById(id);
					    if (el) {
					        id === 'selectFreeText' || id === 'selectFreeText1' ? el.parentElement.remove() : el.remove();
					    }
					});
				    if (this.value === 'LAST_X_BALLS') {
				        let label = document.createElement('label');
				        label.setAttribute('for', 'selectFreeText');
				        label.innerHTML = 'BALLS : ';

				        let xballselect = document.createElement('input');
				        xballselect.type = "text";
				        xballselect.id = 'selectFreeText';
				        xballselect.value = '10';

				        xballselect.size = 3;
				        xballselect.maxLength = 3;
				        xballselect.style.width = "50px";

				        xballselect.setAttribute('onchange', "setTextBoxOptionToSelectOptionArray(1)");
				        let cell = row.insertCell(1);
				        cell.style.cssText = "display:flex; align-items:center; text-align:center; vertical-align:middle; gap:5px;";
				        cell.appendChild(label);
				        cell.appendChild(xballselect);

				        setTextBoxOptionToSelectOptionArray(1);
				        cellCount++;
				    }else if(this.value == 'FreeText'){
						let label1 = document.createElement('label');
					    label1.setAttribute('for', 'selectFreeText'); 
					    label1.innerHTML = 'Line 1';	
					    					    	    			
						let ftheader1 = document.createElement('input');
						ftheader1.type = "text";
						ftheader1.id = 'selectFreeText';
						ftheader1.value = '';
						
						ftheader1.setAttribute('onchange',"setTextBoxOptionToSelectOptionArray(1)");
						row.insertCell(1).appendChild(label1).appendChild(ftheader1);
					    setTextBoxOptionToSelectOptionArray(1);
					    
					    let label2 = document.createElement('label');
					    label2.setAttribute('for', 'selectFreeText1'); 
					    label2.innerHTML = 'Line 2';	
					    					    	    			
						let ftheader2 = document.createElement('input');
						ftheader2.type = "text";
						ftheader2.id = 'selectFreeText1';
						ftheader2.value = '';
						
						ftheader2.setAttribute('onchange',"setTextBoxOptionToSelectOptionArray1(2)");
						row.insertCell(2).appendChild(label2).appendChild(ftheader2);
					    setTextBoxOptionToSelectOptionArray1(2);
				   		cellCount = 3;
					}
				});

				// Trigger once initially
				select.dispatchEvent(new Event('change'));
				break;
			default:
				header_text.innerHTML = 'MIDDLE INFOBAR SECTION - LAST x BALLS';
						
				select = document.createElement('input');
				select.type = "text";
				select.id = 'selectFreeText';
				select.value = '10';
				
				select.setAttribute('onchange',"setTextBoxOptionToSelectOptionArray(0)");
				row.insertCell(cellCount).appendChild(select);
				setTextBoxOptionToSelectOptionArray(0);
				cellCount = cellCount + 1;
				
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ISPL':
					select = document.createElement('select');
					select.id = 'selectMiddleStat';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'WITH';
					option.text = 'With CRR';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'WITHOUT';
					option.text = 'Without CRR';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),1);
					cellCount = cellCount + 1;
					break;
				}
				break;	
			}
			break;
		case 'Shift_C':
			header_text.innerHTML = 'SIX DISTANCE';
		
			select = document.createElement('input');
			select.type = "text";
			select.id = 'selectFreeText';
			
			select.setAttribute('onchange',"setTextBoxOptionForSixDistanceToSelectOptionArray(0)");
			row.insertCell(cellCount).appendChild(select);
			setTextBoxOptionToSelectOptionArray(0);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'sixOrNine';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'six';
			option.text = 'Six';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'nine';
			option.text = 'Nine';
			select.appendChild(option);
			
			select.setAttribute('onchange',"setTextBoxOptionForSixDistanceToSelectOptionArray(0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1
		break;
		case 'Control_Shift_Q':
		
			header_text.innerHTML = 'Generic';
		
			select = document.createElement('select');
			select.id = 'selectGeneric';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'Curr_Part';
			option.text = 'Current Partnership';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'last_wicket';
			option.text = 'Last Wicket';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Boundaries';
			option.text = 'Boundaries';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'CRR';
			option.text = 'Current Run rate';
			select.appendChild(option);
			
			session_match.match.inning.forEach(function(inn){
				if(inn.isCurrentInning == 'YES'){
					if(inn.inningNumber == 2){
						option = document.createElement('option');
						option.value = 'RRR';
						option.text = 'Required Run Rate';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'CRR_RRR';
						option.text = 'Current and Required Run Rate';
						select.appendChild(option);
						
						/*option = document.createElement('option');
						option.value = 'Runs_Balls';
						option.text = 'Runs And Balls';
						select.appendChild(option);*/
						
						option = document.createElement('option');
						option.value = 'Comparison';
						option.text = 'Comparison';
						select.appendChild(option);
					}
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1
		break;
			
		case 'Alt_6':
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'T20_MUMBAI':
				header_text.innerHTML = 'FULL INFOBAR SECTION';
				
				select = document.createElement('select');
				select.id = 'selectFullSection';
				select.name = select.id;
				
				if(session_match.setup.matchType == 'SUPER_OVER'){
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
							if(inn.inningNumber == 1){
								
							}
							else{
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);
							}
						}
					});
					
					option = document.createElement('option');
					option.value = 'OVER';
					option.text = 'This Over';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'REVIEWS_REMAINING';
					option.text = 'Reviews Remaining';
					select.appendChild(option);
					
				}else{
					option = document.createElement('option');
					option.value = 'BLANK';
					option.text = 'blank';
					select.appendChild(option);
								
					option = document.createElement('option');
					option.value = 'IDENT';
					option.text = 'Ident';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'PROMO';
					option.text = 'Promo';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'FreeText';
					option.text = 'FreeText';
					select.appendChild(option);
					
					/*option = document.createElement('option');
					option.value = 'FreeTextDb';
					option.text = 'FreeTextDb';
					select.appendChild(option);*/
					
					option = document.createElement('option');
					option.value = 'ST_BAT';
					option.text = 'Strategic TimeOut Bat';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'ST_BALL';
					option.text = 'Strategic TimeOut Ball';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'Commentators';
					option.text = 'Commentators';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'EXTRAS';
					option.text = 'Extras';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'TIMELINE';
					option.text = 'TimeLine';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BATSMANTIMELINE';
					option.text = 'BatsMan TimeLine';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BOWLERTIMELINE';
					option.text = 'Bowler TimeLine';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'INNINGSBUILDER';
					option.text = 'Player Innings Builder';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'PHASE_WISE_SCORE';
					option.text = 'PhaseWise Score';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'PHASE_WISE_RUNRATE';
					option.text = 'PhaseWise Run-Rate';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BatMileStone';
					option.text = 'Batter MileStone';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BallMileStone';
					option.text = 'Bowler MileStone';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'RECENT_FORM';
					option.text = 'Last 3 Matches';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'TEAM_TOP_THREE';
					option.text = 'Team Top 3 Data';
					select.appendChild(option);
					
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
							if(inn.inningNumber == 1){
								option = document.createElement('option');
								option.value = 'Projected';
								option.text = 'Projected Score';
								select.appendChild(option);
							}
							else if(inn.inningNumber == 2){
								option = document.createElement('option');
								option.value = 'EQUATION_BIG';
								option.text = 'Equation Big';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);
							}
						}
					});	
				}
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1
				
				select.addEventListener('change', function () {
					['selectFreeText', 'selectFreeText1', 'Player1', 'Player2', 'Player3', 'selectPhoto', 'FreeText', 'SponsorValue', 
						'Promo', 'selectTeams'].forEach(id => {
					    const el = document.getElementById(id);
					    if (el) {
					        id === 'selectFreeText' || id === 'selectFreeText1' ? el.parentElement.remove() : el.remove();
					    }
					});
 
					//it will show text value (UI VALUE):this.options[this.selectedIndex].text.toUpperCase()
					if(this.value == 'FreeText'){
						let label1 = document.createElement('label');
					    label1.setAttribute('for', 'selectFreeText'); 
					    label1.innerHTML = 'Line 1';	
					    					    	    			
						let ftheader1 = document.createElement('input');
						ftheader1.type = "text";
						ftheader1.id = 'selectFreeText';
						ftheader1.value = '';
						
						ftheader1.setAttribute('onchange',"setTextBoxOptionToSelectOptionArray(1)");
						row.insertCell(1).appendChild(label1).appendChild(ftheader1);
					    setTextBoxOptionToSelectOptionArray(1);
					    
					    let label2 = document.createElement('label');
					    label2.setAttribute('for', 'selectFreeText1'); 
					    label2.innerHTML = 'Line 2';	
					    					    	    			
						let ftheader2 = document.createElement('input');
						ftheader2.type = "text";
						ftheader2.id = 'selectFreeText1';
						ftheader2.value = '';
						
						ftheader2.setAttribute('onchange',"setTextBoxOptionToSelectOptionArray1(2)");
						row.insertCell(2).appendChild(label2).appendChild(ftheader2);
					    setTextBoxOptionToSelectOptionArray1(2);
				   		cellCount = 3;
					}else if(this.value == 'Commentators'){
						row.insertCell(1).id = 'Player1';
			 			row.insertCell(2).id = 'Player2';
			 			row.insertCell(3).id = 'Player3';
			 			cellCount = 4;
						processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," +
	    				(this.value || $(this).find('option').first().val()));
					}else if(this.value == 'FreeTextDb'){
						row.insertCell(1).id = 'FreeText';
			 			cellCount = 2;
						processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," +
	    				(this.value || $(this).find('option').first().val()));
					}else if(this.value == 'PROMO'){
						row.insertCell(1).id = 'Promo';
			 			cellCount = 2;
						processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," +
	    				(this.value || $(this).find('option').first().val()));
					}
					else if(this.value === 'RECENT_FORM'){
						// === 2. Team Dropdown ===
						let teamSelect = document.createElement('select');
						teamSelect.id = 'selectTeams';
						teamSelect.name = teamSelect.id;
				
						let homeOption = document.createElement('option');
						homeOption.value = session_match.setup.homeTeam.teamId;
						homeOption.text = session_match.setup.homeTeam.teamName3;
						teamSelect.appendChild(homeOption);
				
						let awayOption = document.createElement('option');
						awayOption.value = session_match.setup.awayTeam.teamId;
						awayOption.text = session_match.setup.awayTeam.teamName3;
						teamSelect.appendChild(awayOption);
				
						row.insertCell(1).appendChild(teamSelect);
						teamSelect.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 1)");
						setDropdownOptionToSelectOptionArray($(teamSelect), 1);
						cellCount = 2;
					}
					else if(this.value === 'TEAM_TOP_THREE'){
						// === 2. Team Dropdown ===
						let teamSelect = document.createElement('select');
						teamSelect.id = 'selectTeams';
						teamSelect.name = teamSelect.id;
				
						let homeOption = document.createElement('option');
						homeOption.value = session_match.setup.homeTeam.teamId;
						homeOption.text = session_match.setup.homeTeam.teamName3;
						teamSelect.appendChild(homeOption);
				
						let awayOption = document.createElement('option');
						awayOption.value = session_match.setup.awayTeam.teamId;
						awayOption.text = session_match.setup.awayTeam.teamName3;
						teamSelect.appendChild(awayOption);
				
						row.insertCell(1).appendChild(teamSelect);
						teamSelect.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 1)");
						setDropdownOptionToSelectOptionArray($(teamSelect), 1);
						
						let typeselect  = document.createElement('select');
						typeselect.id = 'selectPhoto';
						typeselect.name = typeselect.id;
						[{ value: 'Runs', text: 'Runs' },{ value: 'wickets', text: 'wickets' },
						{ value: 'fours', text: 'fours' }, { value: 'sixes', text: 'sixes' }].forEach(({ value, text }) => {
							  option = document.createElement('option');
							  option.value = value;
							  option.text = text;
							  typeselect.appendChild(option);
						});
						typeselect.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
						row.insertCell(2).appendChild(typeselect);
						setDropdownOptionToSelectOptionArray($(typeselect),2);
						cellCount = 3;
					}
					else if(this.value == 'BatMileStone' || this.value == 'BallMileStone'){
						let xballselect  = document.createElement('select');
						xballselect.id = 'selectFreeText';
						xballselect.name = xballselect.id;
						
						let selectedValue = this.value; 
						
						session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
								if(selectedValue == 'BatMileStone'){
									inn.battingCard.forEach(function(bc){
										if(bc.status == 'NOT OUT'){
											option = document.createElement('option');
											option.value = bc.playerId;
											option.text = bc.player.full_name + " - " + bc.status;	
											xballselect.appendChild(option);	
										}
									});
								}else{
									inn.bowlingCard.forEach(function(boc){
										option = document.createElement('option');
										option.value = boc.playerId;
										option.text = boc.player.full_name;	
										xballselect.appendChild(option);
									});
								}
							}
						});
						xballselect.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
						row.insertCell(1).appendChild(xballselect);
						setDropdownOptionToSelectOptionArray($(xballselect),1);

						let ballselect  = document.createElement('select');
						ballselect.id = 'selectPhoto';
						ballselect.name = ballselect.id;
						if(this.value == 'BatMileStone'){
							[{ value: 'Runs', text: 'Runs' },{ value: '50', text: '50s' },
							{ value: '100', text: '100s' }].forEach(({ value, text }) => {
								  option = document.createElement('option');
								  option.value = value;
								  option.text = text;
								  ballselect.appendChild(option);
							});
						}else{
							[{ value: 'Wickets', text: 'Wickets' },{ value: '3WI', text: '3WI' },
							{ value: '5WI', text: '5WI' }].forEach(({ value, text }) => {
								  option = document.createElement('option');
								  option.value = value;
								  option.text = text;
								  ballselect.appendChild(option);
							});
						}
						ballselect.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
						row.insertCell(2).appendChild(ballselect);
						setDropdownOptionToSelectOptionArray($(ballselect),2);
						cellCount = 3;
					}else if(this.value == 'BATSMANTIMELINE' || this.value == 'BOWLERTIMELINE' || this.value == 'INNINGSBUILDER'){
						let xballselect  = document.createElement('select');
						xballselect.id = 'selectFreeText';
						xballselect.name = xballselect.id;
						
						let selectedValue = this.value; 
						
						session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
							if(selectedValue == 'BATSMANTIMELINE' || selectedValue == 'INNINGSBUILDER'){
								inn.battingCard.forEach(function(bc){
									if(bc.status == 'NOT OUT'){
										option = document.createElement('option');
										option.value = bc.playerId;
										option.text = bc.player.full_name + " - " + bc.status;	
										xballselect.appendChild(option);	
									}
								});
							}else{
								inn.bowlingCard.forEach(function(boc){
									option = document.createElement('option');
									option.value = boc.playerId;
									option.text = boc.player.full_name;	
									xballselect.appendChild(option);
								});
							}
						}
						});
						xballselect.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
						row.insertCell(1).appendChild(xballselect);
						setDropdownOptionToSelectOptionArray($(xballselect),1);
						cellCount = 2;
					}
				});
				select.dispatchEvent(new Event('change'));
				break;
			case 'ICC-U19-2023':
					header_text.innerHTML = 'MIDDLE INFOBAR SECTION - BAT & SPONSOR';
		
					select = document.createElement('select');
					select.id = 'selectWhichSponsor';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = '1';
					option.text = 'DP World';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '2';
					option.text = 'IndusInd Bank';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '3';
					option.text = 'Emirates';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '4';
					option.text = 'Coco Cola';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '5';
					option.text = 'Aramco';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1
					break;
			}
			
			break;
			
		case 'Alt_7':
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'VIDARBHA':			
				select = document.createElement('select');
				select.id = 'selectRightBottom';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'BOWLER';
				option.text = 'Bowler';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'OVER';
				option.text = 'This Over';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLING_END';
				option.text = 'Bowling End';
				select.appendChild(option);
				header_text.innerHTML = 'RIGHT BOTTOM INFOBAR SECTION';
				
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1
				break;
			case 'ISPL':
				
				select = document.createElement('select');
				select.id = 'selectRightBottom';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'BOWLER';
				option.text = 'Bowler';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'OVER';
				option.text = 'This Over';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLING_END';
				option.text = 'Bowling End';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'LAST_BOWLER';
				option.text = 'Last Bowler';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLING_STYLE';
				option.text = 'Bowling Style';
				select.appendChild(option);
				
				break;	
			case 'T20_MUMBAI':
				select = document.createElement('select');
				select.id = 'selectRightBottom';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'OVER';
				option.text = 'This Over';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLING_END';
				option.text = 'Bowling End';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'ECONOMY';
				option.text = 'Economy';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLER_REPLACE';
				option.text = 'Bowler Replace';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'LAST_BOWLER';
				option.text = 'Last Bowler';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLING_STYLE';
				option.text = 'Bowling Style';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'FreeText';
				option.text = 'FreeText';
				select.appendChild(option);
				
				session_match.match.inning.forEach(function(inn){
					if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
						if(inn.inningNumber == 1){
							option = document.createElement('option');
							option.value = 'CRR';
							option.text = 'Current Run Rate';
							select.appendChild(option);
						}
						else{
							option = document.createElement('option');
							option.value = 'CRR';
							option.text = 'Current Run Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'RRR';
							option.text = 'Required Run Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'TARGET';
							option.text = 'Target';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'EQUATION';
							option.text = 'Equation';
							select.appendChild(option);
						}
					}
				});
				break;
			case 'NPL': case 'LEGENDS-90': case 'MPL': case 'APL':
				select = document.createElement('select');
				select.id = 'selectRightBottom';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'OVER';
				option.text = 'This Over';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLING_END';
				option.text = 'Bowling End';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLER_REPLACE';
				option.text = 'Bowler Replace';
				select.appendChild(option);
				
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'MPL':
					option = document.createElement('option');
					option.value = 'BOWLING_ECONOMY';
					option.text = 'Bowling Economy';
					select.appendChild(option);
					break;
				}
				break;	
			case 'ICC-U19-2023':
				
				select = document.createElement('select');
				select.id = 'selectRightBottom';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'OVER';
				option.text = 'This Over';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLING_END';
				option.text = 'Bowling End';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLER_REPLACE';
				option.text = 'Bowler Replace';
				select.appendChild(option);
				break;
			case 'BENGAL-T20':
				
				select = document.createElement('select');
				select.id = 'selectRightBottom';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'OVER';
				option.text = 'This Over';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLING_END';
				option.text = 'Bowling End';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'BOWLING_ECONOMY';
				option.text = 'Bowling Economy';
				select.appendChild(option);
				
				/*option = document.createElement('option');
				option.value = 'BOWLER_REPLACE';
				option.text = 'Bowler Replace';
				select.appendChild(option);*/
				break;	
			}
			header_text.innerHTML = 'RIGHT BOTTOM INFOBAR SECTION';
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1
			
			select.addEventListener('change', function () {
				['selectFreeText'].forEach(id => {
				    const el = document.getElementById(id);
				    if (el) {
				        id === 'selectFreeText' ? el.parentElement.remove() : el.remove();
				    }
				});
 
				//it will show text value (UI VALUE):this.options[this.selectedIndex].text.toUpperCase()
				if(this.value == 'FreeText'){
					let label1 = document.createElement('label');
				    label1.setAttribute('for', 'selectFreeText'); 
				    label1.innerHTML = 'Line 1';	
				    					    	    			
					let ftheader1 = document.createElement('input');
					ftheader1.type = "text";
					ftheader1.id = 'selectFreeText';
					ftheader1.value = '';
						
					ftheader1.setAttribute('onchange',"setTextBoxOptionToSelectOptionArray(1)");
					row.insertCell(1).appendChild(label1).appendChild(ftheader1);
				    setTextBoxOptionToSelectOptionArray(1);
			  		cellCount = 2;
				}
			});
			select.dispatchEvent(new Event('change'));
			break;
			
		case 'Alt_8':
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'VIDARBHA':
					header_text.innerHTML = 'RIGHT TOP INFOBAR SECTION';
		
					select = document.createElement('select');
					select.id = 'selectRightSection';
					select.name = select.id;
					
					
					session_match.match.inning.forEach(function(inn,index,arr){
						if(inn.isCurrentInning == 'YES'){
							if(inn.inningNumber == 2){
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'EQUATION';
								option.text = 'Equation';
								select.appendChild(option);
							}
						}
					});
					
					option = document.createElement('option');
					option.value = 'TIMELINE';
					option.text = 'Timeline';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'SUPER_OVER';
					option.text = 'Super Over';
					select.appendChild(option);
					
					break;
				case 'T20_MUMBAI':
					header_text.innerHTML = 'RIGHT INFOBAR SECTION';
		
					select = document.createElement('select');
					select.id = 'selectRightSection';
					select.name = select.id;
					
					if(session_match.setup.matchType == 'SUPER_OVER'){
						session_match.match.inning.forEach(function(inn){
							if(inn.isCurrentInning == 'YES' && session_match.setup.matchType != 'TEST' && session_match.setup.matchType != 'FC'){
								if(inn.inningNumber == 1){
									
								}
								else{
									option = document.createElement('option');
									option.value = 'TARGET';
									option.text = 'Target';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'EQUATION';
									option.text = 'Equation';
									select.appendChild(option);
								}
							}
						});
						
						option = document.createElement('option');
						option.value = 'OVER';
						option.text = 'This Over';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'REVIEWS_REMAINING';
						option.text = 'Reviews Remaining';
						select.appendChild(option);
						
					}else{
						option = document.createElement('option');
						option.value = 'BOWLER';
						option.text = 'Bowler';
						select.appendChild(option);
						
						/*option = document.createElement('option');
						option.value = 'PARTNERSHIP';
						option.text = 'Partnership';
						select.appendChild(option);*/
					
						option = document.createElement('option');
						option.value = 'LAST_X_BALLS';
						option.text = 'Last X Balls';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'BOUNDARY';
						option.text = 'Innings Boundaries';
						select.appendChild(option);
						
						/*option = document.createElement('option');
						option.value = 'BALLS_SINCE_LAST_BOUNDARY';
						option.text = 'Ball Since Last Boundary';
						select.appendChild(option);*/
						
						option = document.createElement('option');
						option.value = 'EXTRAS';
						option.text = 'Extras';
						select.appendChild(option);
						
						/*option = document.createElement('option');
						option.value = 'FOW';
						option.text = 'Fall Of Wickets';
						select.appendChild(option);*/
						
						/*option = document.createElement('option');
						option.value = 'REVIEWS_REMAINING';
						option.text = 'Reviews Remaining';
						select.appendChild(option);*/
						
						/*option = document.createElement('option');
						option.value = 'TIMELINE';
						option.text = 'Timeline';
						select.appendChild(option);*/
						
						option = document.createElement('option');
						option.value = 'LAST_WICKET';
						option.text = 'Last Wicket';
						select.appendChild(option);
						
						/*option = document.createElement('option');
						option.value = 'PHASE_WISE_SCORE';
						option.text = 'Phase Wise Score';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'ST_BAT';
						option.text = 'Strategic TimeOut Bat';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'ST_BALL';
						option.text = 'Strategic TimeOut Ball';
						select.appendChild(option);*/
						
						session_match.match.inning.forEach(function(inn){
							if(inn.isCurrentInning == 'YES'){
								if(inn.inningNumber == 1){
									/*option = document.createElement('option');
									option.value = 'Toss';
									option.text = 'Toss';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'Projected';
									option.text = 'Projected Score';*/
									select.appendChild(option);
								}
								else if(inn.inningNumber == 2){
									
									/*option = document.createElement('option');
									option.value = 'TARGET';
									option.text = 'Target';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'EQUATION';
									option.text = 'Equation';
									select.appendChild(option);
									
									option = document.createElement('option');
									option.value = 'SMALL_EQUATION';
									option.text = 'Small Equation';
									select.appendChild(option);*/
									
									option = document.createElement('option');
									option.value = 'COMPARE';
									option.text = 'Compare';
									select.appendChild(option);
								}
							}
						});	
					}
					break;
					
				case 'ICC-U19-2023': case 'NPL': case 'ISPL': case 'LEGENDS-90': case 'MPL': case 'APL':
					header_text.innerHTML = 'RIGHT INFOBAR SECTION';
		
					select = document.createElement('select');
					select.id = 'selectRightSection';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'BOWLER';
					option.text = 'Bowler';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BOUNDARY';
					option.text = 'Innings Boundaries';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'TEAMS_STANDINGS';
					option.text = 'Standings';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'REVIEWS_REMAINING';
					option.text = 'Review';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'VENUE';
					option.text = 'Venue';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'FreeText';
					option.text = 'Free Text';
					select.appendChild(option);
					
					if($('#selected_broadcaster').val() == 'ISPL'){
						option = document.createElement('option');
						option.value = 'over';
						option.text = 'This Over';
						select.appendChild(option);
					}
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
							if(inn.inningNumber == 1){
								option = document.createElement('option');
								option.value = 'Toss';
								option.text = 'Toss';
								select.appendChild(option);
								
								switch($('#selected_broadcaster').val().toUpperCase()){
									case 'ICC-U19-2023': case 'NPL': case 'ISPL': case 'LEGENDS-90': case 'MPL':
									case 'APL':
										option = document.createElement('option');
										option.value = 'CRR';
										option.text = 'Current Run Rate';
										select.appendChild(option);
									break;
								}
								
								option = document.createElement('option');
								option.value = 'Projected';
								option.text = 'Projected Score';
								select.appendChild(option);
							}
							else if(inn.inningNumber == 2){
								option = document.createElement('option');
								option.value = 'RRR';
								option.text = 'Required Run Rate';
								select.appendChild(option);
								
								switch($('#selected_broadcaster').val().toUpperCase()){
									case 'ICC-U19-2023': case 'ISPL': case 'LEGENDS-90': case 'MPL':
										option = document.createElement('option');
										option.value = 'CRR_RRR';
										option.text = 'Current and Required Run Rate';
										select.appendChild(option);
									break;
								}
								
								option = document.createElement('option');
								option.value = 'TARGET';
								option.text = 'Target';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'COMPARE';
								option.text = 'Compare';
								select.appendChild(option);
							}
						}
					});
					break;
				case 'BENGAL-T20':
					header_text.innerHTML = 'RIGHT INFOBAR SECTION';
		
					select = document.createElement('select');
					select.id = 'selectRightSection';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'BOWLER';
					option.text = 'Bowler';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BOUNDARY';
					option.text = 'Innings Boundaries';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'THIS_MATCH_SIXES';
					option.text = 'This Match Sixes';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'THIS_MATCH_FOURS';
					option.text = 'This Match Fours';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'TOURNAMENT_SIXES';
					option.text = 'Tournament Sixes';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'TOURNAMENT_FOURS';
					option.text = 'Tournament Fours';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'BALLS_SINCE_LAST_BOUNDARY';
					option.text = 'Ball Since Last Boundary';
					select.appendChild(option);
					
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
							if(inn.inningNumber == 2){
								option = document.createElement('option');
								option.value = 'COMPARE';
								option.text = 'Compare';
								select.appendChild(option);
							}
						}
					});
					break;	
			}	
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1
			
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'T20_MUMBAI': 
				select.addEventListener('change', function () {
				    if (document.getElementById('selectFreeText')) {
				        document.getElementById('selectFreeText').parentElement.remove();
				    }
				    if (this.value === 'LAST_X_BALLS') {
				        let label = document.createElement('label');
				        label.setAttribute('for', 'selectFreeText');
				        label.innerHTML = 'BALLS : ';

				        let xballselect = document.createElement('input');
				        xballselect.type = "text";
				        xballselect.id = 'selectFreeText';
				        xballselect.value = '10';

				        xballselect.size = 3;
				        xballselect.maxLength = 3;
				        xballselect.style.width = "50px";

				        xballselect.setAttribute('onchange', "setTextBoxOptionToSelectOptionArray(1)");
				        let cell = row.insertCell(1);
				        cell.style.cssText = "display:flex; align-items:center; text-align:center; vertical-align:middle; gap:5px;";
				        cell.appendChild(label);
				        cell.appendChild(xballselect);

				        setTextBoxOptionToSelectOptionArray(1);

				        cellCount++;
				    }
				});

				// Trigger once initially
				select.dispatchEvent(new Event('change'));
				break;
			}
			break;
			
		case 'Alt_9':
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'ICC-U19-2023': case 'BENGAL-T20': case 'NPL': case 'MPL': case 'LEGENDS-90': case 'APL': case 'ISPL': case 'VIDARBHA':
			case 'T20_MUMBAI':
				header_text.innerHTML = 'INFOBAR SECTION - FREE TEXT';
						
				select = document.createElement('select');
				select.id = 'selectInfoBarStats';
				select.name = select.id;
				
				dataToProcess.forEach(function(pro1){
					option = document.createElement('option');
					option.value = pro1.order;
					option.text = pro1.order + '-' + pro1.prompt ;
					select.appendChild(option);
				});
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1
				break;
			}
			break;
			
		case 'Alt_0':
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'T20_MUMBAI':
				header_text.innerHTML = 'FULL PROMO INFOBAR SECTION';
						
				select = document.createElement('select');
				select.id = 'selectRightSection';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'BLANK';
				option.text = 'Blank';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'LEAGUE_PROMOTION';
				option.text = 'League Promotion';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'PLAYER_BUILDUP_BAT';
				option.text = 'Player Build-Up Bat';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'PLAYER_BUILDUP_BALL';
				option.text = 'Player Build-Up Ball';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'TEAM_BUILDUP_HOME';
				option.text = 'team Build-Up Home';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'TEAM_BUILDUP_AWAY';
				option.text = 'team Build-Up Away';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1
				
				select.addEventListener('change', function () {
					['selectFreeText', 'selectFreeText1', 'FreeText'].forEach(id => {
					    const el = document.getElementById(id);
					    if (el) {
					        id === 'selectFreeText' || id === 'selectFreeText1' ? el.parentElement.remove() : el.remove();
					    }
					});
 
					//it will show text value (UI VALUE):this.options[this.selectedIndex].text.toUpperCase()
					if(this.value == 'LEAGUE_PROMOTION'){
						row.insertCell(1).id = 'FreeText';
			 			cellCount = 2;
						processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," +
	    				(this.value || $(this).find('option').first().val()));
					}
					else if(this.value == 'PLAYER_BUILDUP_BAT' || this.value == 'PLAYER_BUILDUP_BALL'){
						row.insertCell(1).id = 'FreeText';
			 			cellCount = cellCount + 1;
						processCricketProcedures("GRAPHICS-OPTIONS_DATA", whatToProcess + "," +
	    				(this.value || $(this).find('option').first().val()));
						
						let xballselect  = document.createElement('select');
						xballselect.id = 'selectFreeText';
						xballselect.name = xballselect.id;
						
						let selectedValue = this.value; 
						
						session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
							   if(selectedValue == 'PLAYER_BUILDUP_BAT'){
									inn.battingCard.forEach(function(bc){
										option = document.createElement('option');
										option.value = bc.playerId;
										option.text = bc.player.full_name + " - " + bc.status;	
										xballselect.appendChild(option);
									});
								}else{
									inn.bowlingCard.forEach(function(boc){
										option = document.createElement('option');
										option.value = boc.playerId;
										option.text = boc.player.full_name;	
										xballselect.appendChild(option);
									});
								}
							}
						});
						xballselect.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
						row.insertCell(2).appendChild(xballselect);
						cellCount = cellCount + 1;
						setDropdownOptionToSelectOptionArray($(xballselect),2);
						
						let imageselect  = document.createElement('select');
						imageselect.id = 'selectimage';
						imageselect.name = imageselect.id;
						
						option = document.createElement('option');
						option.value = 'WITHOUT';
						option.text = 'Without Player';	
						imageselect.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'WITH';
						option.text = 'With Player';	
						imageselect.appendChild(option);
						
						imageselect.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 3)");
						row.insertCell(3).appendChild(imageselect);
						cellCount = cellCount + 1;
						setDropdownOptionToSelectOptionArray($(imageselect),3);
					}
				});
				select.dispatchEvent(new Event('change'));
				break;
			case 'ICC-U19-2023': case 'BENGAL-T20': case 'NPL': case 'MPL': case 'LEGENDS-90': case 'APL': case 'ISPL': case "VIDARBHA":
				header_text.innerHTML = 'MIDDLE INFOBAR SECTION - COMMANTATORS';
						
				select = document.createElement('select');
				select.id = 'selectInfoBarComm1';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = '0';
				option.text = "";
				select.appendChild(option);
				
				dataToProcess.forEach(function(comm){
					if(comm.useThis == 'Yes'){
						option = document.createElement('option');
						option.value = comm.commentatorId;
						option.text = comm.commentatorName;
						select.appendChild(option);
					}
				});
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1
				
				select = document.createElement('select');
				select.id = 'selectInfoBarComm2';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = '0';
				option.text = "";
				select.appendChild(option);
				
				dataToProcess.forEach(function(comm){
					if(comm.useThis == 'Yes'){
						option = document.createElement('option');
						option.value = comm.commentatorId;
						option.text = comm.commentatorName;
						select.appendChild(option);
					}
				});
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),1);
				cellCount = cellCount + 1
				
				select = document.createElement('select');
				select.id = 'selectInfoBarComm3';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = '0';
				option.text = "";
				select.appendChild(option);
				
				dataToProcess.forEach(function(comm){
					if(comm.useThis == 'Yes'){
						option = document.createElement('option');
						option.value = comm.commentatorId;
						option.text = comm.commentatorName;
						select.appendChild(option);
					}
				});
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),2);
				cellCount = cellCount + 1
				break;
			}
			break;
		case 'Alt_Shift_F4':
			header_text.innerHTML = 'TEAMS';
			select = document.createElement('select');
			select.id = 'selectPlayer';
			select.name = select.id;
			
			 [{ value: "TEAMS LOGOS", text: "LOGOS" },
			  { value: "TEAMS LOGOS WITH CAPTAINS", text: "LOGOS + CAPTAINS" }
			  ].forEach(function(inn){
				option = document.createElement('option');
				option.value = inn.value;
				option.text = inn.text;	
				select.appendChild(option);
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		case "Alt_Shift_B":
			header_text.innerHTML = 'Bowler Speed ';
			select = document.createElement('select');
			select.id = 'selectPlayer';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.forEach(function(boc){
						option = document.createElement('option');
						option.value = boc.playerId;
						option.text = boc.player.full_name;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		case 'Shift_E':
		 	header_text.innerHTML = 'LT - EXTRAS';
		
			select = document.createElement('select');
			select.id = 'selectExtras';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = '1';
			option.text = '1st Inning';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '2';
			option.text = '2nd Inning';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'totalExtras';
			option.text = 'Total Extras';
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1
			break;
		case 'Alt_d':
			header_text.innerHTML = 'LT - DLS PAR SCORE';
		
			select = document.createElement('select');
			select.id = 'selectSponsor';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'currentOver';
			option.text = 'Current Over';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'nextBall';
			option.text = 'Next Ball';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'nextOver';
			option.text = 'Next Over';
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1
			break;
			
		case 'Shift_K': case 'F4':
			switch(whatToProcess) {
			case 'F4':
				header_text.innerHTML = 'FF ALL PARTNERSHIP';
				break;
			case 'Shift_K':
				header_text.innerHTML = 'FF CURRENT PARTNERSHIP';
				break;
			}
		
			select = document.createElement('select');
			select.id = 'selectSponsor';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'cocaCola';
			option.text = 'Coca Cola';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'noSponsor';
			option.text = 'withoutSponsor';
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1
			break;

		case 'Control_g': case 'Control_y':
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ICC-U19-2023':
					header_text.innerHTML = 'POWERPLAY';
	
					select = document.createElement('select');
					select.id = 'selectPowerplay';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'p1';
					option.text = 'Powerplay 1';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'p2';
					option.text = 'Powerplay 2';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'p3';
					option.text = 'Powerplay 3';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1
					break;
				case 'NPL':  case 'MPL': case 'T20_MUMBAI': case 'BENGAL-T20': case 'APL': case 'VIDARBHA':
					header_text.innerHTML = 'POWERPLAY';
	
					select = document.createElement('select');
					select.style = 'width:130px';
					select.id = 'selectTeam';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = '1';
					option.text = '1st Inning';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '2';
					option.text = '2nd Inning';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					
					cellCount = cellCount + 1;
					break;
				case 'ISPL':
					header_text.innerHTML = 'POWERPLAY';
	
					select = document.createElement('select');
					select.id = 'selectPowerplay';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'p1';
					option.text = 'Powerplay 1';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'p2';
					option.text = 'Powerplay 2';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1
					break;
				case 'LEGENDS-90':
					header_text.innerHTML = 'POWERPLAY';
	
					select = document.createElement('select');
					select.style = 'width:130px';
					select.id = 'selectTeam';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = session_match.setup.homeTeamId;
					option.text = session_match.setup.homeTeam.teamName1;
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = session_match.setup.awayTeamId;
					option.text = session_match.setup.awayTeam.teamName1;
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					
					cellCount = cellCount + 1;
					break;	
			}
			
			break;
			case 'Control_h':
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI':
					header_text.innerHTML = 'PHASE';
	
					select = document.createElement('select');
					select.style = 'width:130px';
					select.id = 'selectphase';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'PHASE_WISE';
					option.text = 'PHASE WISE';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'RUN_RATE';
					option.text = 'RUN RATE';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					
					cellCount = cellCount + 1;
					break;
			}
			
			break;			
			
		case 'u':
			
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ISPL': case 'BENGAL-T20': case 'NPL': case 'LEGENDS-90':  case 'MPL':case 'T20_MUMBAI':
				case 'APL': case 'VIDARBHA':
					header_text.innerHTML = '30-50 SPLIT';
			
					select = document.createElement('select');
					select.id = 'selectSplit';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = '30';
					option.text = '30-Split';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '50';
					option.text = '50-Split';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1
					break;
				case 'ICC-U19-2023':
					header_text.innerHTML = '50-100 SPLIT';
			
					select = document.createElement('select');
					select.id = 'selectSplit';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = '50';
					option.text = '50-Split';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '100';
					option.text = '100-Split';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1
					break;
			}
			break;
			
		case 'Control_F5': case 'Control_b': case 'Control_s': case 'Shift_F7'://Batsman Style

			switch(whatToProcess) {
			case 'Control_F5':
				header_text.innerHTML = 'BAT STYLE';
				break;
			case 'Control_b':
				header_text.innerHTML = 'BATTER IN AT';
				break;
			case 'Control_s':
				header_text.innerHTML = 'LT THIS SERIES BAT';
				break;
			case 'Shift_F7':
				header_text.innerHTML = 'BAT STYLE WITH PHOTO';
				break;	
			}
			select = document.createElement('select');
			select.id = 'selectPlayerName';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
			    if(inn.inningNumber == document.getElementById('which_inning').value){
			        let addedPlayers = new Set();
			        // Add NOT OUT batters with striker first
			        inn.battingCard
			            .filter(bc => bc.status == 'NOT OUT')
			            .sort((a,b) => a.onStrike == 'YES' ? -1 : 1)
			            .forEach(function(bc){
			                let option = document.createElement('option');
			                option.value = bc.player.playerId;
			                option.text = bc.player.full_name;
			                select.appendChild(option);
			                addedPlayers.add(bc.player.playerId);
			            });

			        // Add squad players
			        if(inn.battingTeamId == session_match.setup.homeTeamId){
			            session_match.setup.homeSquad.forEach(function(hs){
			                if(!addedPlayers.has(hs.playerId)){
			                    let option = document.createElement('option');
			                    option.value = hs.playerId;
			                    option.text = hs.full_name;
			                    select.appendChild(option);
			                }
			            });
			            session_match.setup.homeOtherSquad.forEach(function(hos){
			                if(!addedPlayers.has(hos.playerId)){
			                    let option = document.createElement('option');
			                    option.value = hos.playerId;
			                    option.text = hos.full_name + ' (OTHER)';
			                    select.appendChild(option);
			                }
			            });
			        } else {
			            session_match.setup.awaySquad.forEach(function(as){
			                if(!addedPlayers.has(as.playerId)){
			                    let option = document.createElement('option');
			                    option.value = as.playerId;
			                    option.text = as.full_name;
			                    select.appendChild(option);
			                }
			            });
			            session_match.setup.awayOtherSquad.forEach(function(aos){
			                if(!addedPlayers.has(aos.playerId)){
			                    let option = document.createElement('option');
			                    option.value = aos.playerId;
			                    option.text = aos.full_name + ' (OTHER)';
			                    select.appendChild(option);
			                }
			            });
			        }
			    }
			});

			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			
			switch(whatToProcess) {
			case 'Control_b':
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI':
					select = document.createElement('select');
					select.id = 'selectProfile';
					select.name = select.id;
					
					[ { value: 'T20 MUMBAI', text: 'T20 MUMBAI' },
						  { value: 'DT20', text: 'T20' },
						  { value: 'IT20', text: 'T20I' },
						  { value: 'MT20 SEASON 3', text: 'MT20 SEASON 3' },
						  { value: 'IPL 2026', text: 'IPL 2026' },
						  { value: 'IPL', text: 'IPL' }
						].forEach(({ value, text }) => {
							  option = document.createElement('option');
							  option.value = value;
							  option.text = text;
							  select.appendChild(option);
						});
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),1);
					cellCount = cellCount + 1
					break;
				}
				break;	
			case 'Control_s':
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'BENGAL-T20': //case 'NPL': case 'APL':
					break;
					default:
						select = document.createElement('select');
						select.id = 'selectType';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = 'WITH_CURRENT';
						option.text = 'With Current Match';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'WITHOUT_CURRENT';
						option.text = 'Without Current Match';
						select.appendChild(option);
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),1);
						cellCount = cellCount + 1;
				}
				break;
			}
			break;
		 case 'Alt_m':  case 'Shift_P':
			switch(whatToProcess) {	
			case 'Alt_m':
				header_text.innerHTML = 'BATSMAN MILESTONE';
				break;
			case 'Shift_P':
				header_text.innerHTML = 'BATSMAN THIS SERIES';
				break;
			}
			select = document.createElement('select');
			select.id = 'selectPlayerName';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.forEach(function(bc){
						if(bc.status == 'NOT OUT'){
							if(bc.onStrike == 'YES'){
								option = document.createElement('option');
								option.value = bc.player.playerId;
								option.text = bc.player.full_name;
								select.appendChild(option);
							}else{
								option = document.createElement('option');
								option.value = bc.player.playerId;
								option.text = bc.player.full_name;
								select.appendChild(option);
							}
						}
					});
					
					if(inn.battingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name;
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name;
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}
				}
			});

			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			switch(whatToProcess){
				case 'Shift_P':
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'ICC-U19-2023': case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'APL':
					select = document.createElement('select');
					select.id = 'selectStatType';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = '0';
					option.text = '';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '1';
					option.text = 'Matches';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '2';
					option.text = 'Runs';
					select.appendChild(option);
					
					switch($('#selected_broadcaster').val().toUpperCase()){
						case 'ICC-U19-2023':
							option = document.createElement('option');
							option.value = '3';
							option.text = 'Fifties';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = '4';
							option.text = 'Hundreds';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = '5';
							option.text = 'Average';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = '6';
							option.text = 'Strike Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = '7';
							option.text = 'Best';
							select.appendChild(option);
							break;
						case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'APL':
							option = document.createElement('option');
							option.value = '3';
							option.text = 'Strike Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = '4';
							option.text = '30s/50s';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = '5';
							option.text = 'Best';
							select.appendChild(option);
							break;
					}
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),1);
					cellCount = cellCount + 1
					break;
				}
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'APL': case 'T20_MUMBAI':
						select = document.createElement('select');
						select.id = 'selectType';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = 'WITH_CURRENT';
						option.text = 'With Current Match';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'WITHOUT_CURRENT';
						option.text = 'Without Current Match';
						select.appendChild(option);
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),2);
						cellCount = cellCount + 1;
						break;
				}
				break;
			}
			break;	
		case 'Alt_p':
		
			header_text.innerHTML = 'BUG TOSS';
			
			select = document.createElement('select');
			select.id = 'selectTeams';
			select.name = select.id;
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ISPL':
					option = document.createElement('option');
					option.value = session_match.setup.homeTeam.teamName4 + '-' + 'BAT';
					option.text = session_match.setup.homeTeam.teamName4 + '-' + 'BAT';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = session_match.setup.homeTeam.teamName4 + '-' + 'FIELD';
					option.text = session_match.setup.homeTeam.teamName4 + '-' + 'FIELD';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = session_match.setup.awayTeam.teamName4 + '-' + 'BAT';
					option.text = session_match.setup.awayTeam.teamName4 + '-' + 'BAT';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = session_match.setup.awayTeam.teamName4 + '-' + 'FIELD';
					option.text = session_match.setup.awayTeam.teamName4 + '-' + 'FIELD';
					select.appendChild(option);
					break;
				case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'BENGAL-T20': case 'APL': case 'VIDARBHA': case 'T20_MUMBAI':
				option = document.createElement('option');
				option.value = session_match.setup.homeTeam.teamName1 + '-' + 'BAT';
				option.text = session_match.setup.homeTeam.teamName1 + '-' + 'BAT';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = session_match.setup.homeTeam.teamName1 + '-' + 'FIELD';
				option.text = session_match.setup.homeTeam.teamName1 + '-' + 'FIELD';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = session_match.setup.awayTeam.teamName1 + '-' + 'BAT';
				option.text = session_match.setup.awayTeam.teamName1 + '-' + 'BAT';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = session_match.setup.awayTeam.teamName1 + '-' + 'FIELD';
				option.text = session_match.setup.awayTeam.teamName1 + '-' + 'FIELD';
				select.appendChild(option);
			break;
			}
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;
			
		case 'Alt_a': case 'Alt_s':
			switch(whatToProcess) {
			case 'Alt_a':
				header_text.innerHTML = 'LT HOME TEAM STAFF';
				break;
			case 'Alt_s':
				header_text.innerHTML = 'LT AWAY TEAM STAFF';
				break;
			}
			
			select = document.createElement('select');
			select.id = 'selectTeams';
			select.name = select.id;
			
			dataToProcess.forEach(function(st1){
				option = document.createElement('option');
				option.value = st.staffId;
				option.text = st.staffName + ' - ' + st.role + ' (' + st.team.teamName1 + ')';
				select.appendChild(option);
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			
			break;
		case 'Alt_x':
			header_text.innerHTML = 'MVP LEADERBOARD'; 
		
			select = document.createElement('select');
			select.id = 'selectType';
			select.name = select.id;
			
			['LEADERBOARD', 'PERFORMANCE'].forEach(text => {
		        const option = new Option(text, text);
		        select.add(option);
		    });
			
			select.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select), 0);
			cellCount++;
			
			select = document.createElement('select');
			select.id = 'selectNumber';
			select.name = select.id;
			
			['1 TO 3', '4 TO 6'].forEach(text => {
		        const option = new Option(text, text);
		        select.add(option);
		    });
			
			select.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select), 1);
			cellCount++;
			break;
		case 'Shift_L':
			header_text.innerHTML = 'MOST 10'; 
		
			select = document.createElement('select');
			select.id = 'selectType';
			select.name = select.id;
			
			['MOST RUNS', 'MOST WICKETS', 'MOST FOURS', 'MOST SIXES', 'HIGHEST SR', 'BEST ECONOMY']
			    .forEach(text => {
			        const option = new Option(text, text);
			        select.add(option);
			    });
			
			select.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select), 0);
			cellCount++;
			break;
		case 'Alt_f':
			header_text.innerHTML = 'Mini Options'; 
					
			select = document.createElement('select');
			select.id = 'selectType';
			select.name = select.id;
			
			['MANHATTAN', 'WORM','PARTNRSHIP','PHASE']
			    .forEach(text => {
			        const option = new Option(text, text);
			        select.add(option);
			    });
			
			select.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select), 0);
			cellCount++;
			break;	
			
		case 'Alt_z': case 'Alt_Shift_W': case 'Control_0':
			switch(whatToProcess) {
				case 'Alt_z':
					header_text.innerHTML = 'SQUAD';
					break;
				case 'Alt_Shift_W':
					header_text.innerHTML = 'MOST';
					break;
				case 'Control_0':
					header_text.innerHTML = 'TEAMS';
					break;
			}
			
			let num1 = 0;
			
			switch(whatToProcess) {
				case 'Alt_z': case 'Alt_Shift_W': case 'Control_0':
					select = document.createElement('select');
					select.id = 'selectTeams';
					select.name = select.id;
					
					dataToProcess.forEach(function(teams1){
						option = document.createElement('option');
						option.value = teams1.teamId;
						option.text = teams1.teamName1;
						select.appendChild(option);
					});
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1;
					break;
			}
			
			switch(whatToProcess) {
				case 'Alt_z':
					select = document.createElement('select');
					select.id = 'selectType';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'role';
					option.text = 'Role';
					select.appendChild(option);
					
					/*option = document.createElement('option');
					option.value = 'matches';
					option.text = 'Matches';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'runs';
					option.text = 'Runs';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'wickets';
					option.text = 'Wickets';
					select.appendChild(option);*/
					//select.setAttribute('onchange', "processUserSelection(this)");
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),1);
					cellCount = cellCount + 1;
					break;
				case 'Alt_Shift_W':
					select = document.createElement('select');
					select.id = 'selectType';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'most_runs';
					option.text = 'MOST RUNS';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'most_wickets';
					option.text = 'MOST WICKETS';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'most_fours';
					option.text = 'MOST FOURS';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'most_sixes';
					option.text = 'MOST SIXES';
					select.appendChild(option);
					
					//select.setAttribute('onchange', "processUserSelection(this)");
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),1);
					cellCount = cellCount + 1;
					break;
			}
			break;
		case 'Shift_M':
			header_text.innerHTML = 'FF LEADERBOARD DB';
			select = document.createElement('select');
			select.id = 'selectTeams';
			select.name = select.id;
			
			dataToProcess.forEach(function(oop){	
				option = document.createElement('option');
	            option.value = oop.leaderboardId;
	            option.text = oop.header1 + ' ' + oop.header2;
	            select.appendChild(option);
	        });
	        
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;
		case "Alt_Shift_F5":
			header_text.innerHTML = 'LT POINTER';
			select = document.createElement('select');
			select.id = 'selectTeams';
			select.name = select.id;
			
			dataToProcess.forEach(function(oop){	
				option = document.createElement('option');
	            option.value = oop.pointersId;
	            option.text = oop.prompt  ;
	            select.appendChild(option);
	        });
	        
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;
		case 'Control_F7':
			header_text.innerHTML = 'Both Teams - (PlayingXi / Subs)';
			
			select = document.createElement('select');
			select.id = 'selectType';
			select.name = select.id;
			
			['Playing_11','Subs'].forEach(stat => {
		        const option = document.createElement('option');
		        option.value = stat;
		        option.text = stat
		        select.appendChild(option);
		    });
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1
			break;
		case 'Shift_T': case 'Alt_F9': case 'Alt_F12': case 'Alt_F10': case 'Shift_F8': 
		case 'Control_Shift_F7': case 'Control_Shift_F11': case 'r':
			switch(whatToProcess) {
			case 'Shift_T': case 'Control_Shift_F7':
				header_text.innerHTML = 'FF PLAYING XI';
				break;
			case 'Alt_F12':
				header_text.innerHTML = 'TEAM 0,1,2';
				break;
			case 'Alt_F9':
				header_text.innerHTML = 'SINGLE TEAMS CAREER';
				break;		
			case 'Alt_F10':
				header_text.innerHTML = 'SINGLE TEAM (THIS SERIES)';
				break;
			case 'Control_Shift_F11':
				header_text.innerHTML = 'DRS DECISION';
				break;
			case 'r':
				header_text.innerHTML = 'Bug Review';
				break;
			}
			select = document.createElement('select');
			select.id = 'selectTeams';
			select.name = select.id;
			session_match.match.inning.forEach(function(inn){
				if(inn.isCurrentInning == 'YES'){
					option = document.createElement('option');
					option.value = inn.battingTeamId;
					option.text = inn.batting_team.teamName1;
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = inn.bowlingTeamId;
					option.text = inn.bowling_team.teamName1;
					select.appendChild(option);
				}
			});
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			
			switch(whatToProcess){
			case 'r':
				select = document.createElement('select');
				select.id = 'selectType';
				select.name = select.id;
				
				['Original Decision - Out','Original Decision - Not Out', 'Wide Not Given', 'No-Ball Not Given', 'Wide Given', 
				 'No-Ball Given', 'Decision Overturned', 'Decision Upheld', 'Review lost', 'Review Retained'].forEach(stat => {
			        const option = document.createElement('option');
			        option.value = stat;
			        option.text = stat
			        select.appendChild(option);
			    });
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),1);
				cellCount = cellCount + 1
				break;
			case 'Control_Shift_F11':
				select = document.createElement('select');
				select.id = 'selectType';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'out';
				option.text = 'Decision Out';
				select.appendChild(option);

				option = document.createElement('option');
				option.value = 'notout';
				option.text = 'Decision Not Out';
				select.appendChild(option);

				option = document.createElement('option');
				option.value = 'reversednotout';
				option.text = 'Reversed Not Out';
				select.appendChild(option);

				option = document.createElement('option');
				option.value = 'upheldnotout';
				option.text = 'Upheld Not Out';
				select.appendChild(option);

				option = document.createElement('option');
				option.value = 'reversedout';
				option.text = 'Reversed Out';
				select.appendChild(option);

				option = document.createElement('option');
				option.value = 'upheldout';
				option.text = 'Upheld Out';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),1);
				cellCount = cellCount + 1
				break;
			case 'Alt_F9': case 'Alt_F10':
				
				select = document.createElement('select');
				select.id = 'selectStyle';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'age';
				option.text = 'Age';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'batting';
				option.text = 'Batting';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'bowling';
				option.text = 'Bowling';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),1);
				cellCount = cellCount + 1
				
				select = document.createElement('select');
				select.id = 'selectType';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'runs';
				option.text = 'Runs';
				select.appendChild(option);
				
				/*option = document.createElement('option');
				option.value = 'average';
				option.text = 'Average';
				select.appendChild(option);*/
				
				option = document.createElement('option');
				option.value = 'strike Rate';
				option.text = 'Strike Rate';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'wickets';
				option.text = 'Wickets';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'economy';
				option.text = 'Economy';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),2);
				cellCount = cellCount + 1
				break;
			}
			break;
		
		case 'Control_Alt_8':
		    header_text.innerHTML = 'LEADERBOARD - MVP';
		    
		    select = document.createElement('select');
		    select.id = 'selectType';
		    select.name = select.id;
		    
		    option = document.createElement('option');
		    option.value = 'MVP_LB_IDENT';
		    option.text = 'IDENT';
		    select.appendChild(option);
		
		    option = document.createElement('option');
		    option.value = 'MVP_LB_SINGLE_PLAYER';
		    option.text = 'Single Player';
		    select.appendChild(option);
		
		    option = document.createElement('option');
		    option.value = 'MVP_LB_ALL_PLAYER';
		    option.text = 'All Player';
		    select.appendChild(option);
		
		    // 🔹 ONLY CHANGE: added filterPlayerDropdown call (original call untouched)
		    select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0); filterPlayerDropdown(this.value);");
		
		    row.insertCell(cellCount).appendChild(select);
		    setDropdownOptionToSelectOptionArray($(select), 0);
		    cellCount = cellCount + 1;
		    
		    select = document.createElement('select');
		    select.id = 'selectPlayer';
		    select.name = select.id;
		    
		    // 🔹 ORIGINAL LOOP (UNCHANGED)
		    for (i = 1; i <= dataToProcess.length; i++) {
		        option = document.createElement('option');
		        option.value = i + "_" + dataToProcess[i - 1].playerId;
		        option.text = i + " - " + dataToProcess[i - 1].full_name;
		        select.appendChild(option);
		    }
		
		    select.setAttribute('onchange', "setDropdownOptionToSelectOptionArray(this, 1)");
		    row.insertCell(cellCount).appendChild(select);
		    setDropdownOptionToSelectOptionArray($(select), 1);
		    cellCount = cellCount + 1;
		
		    // 🔹 Ensure default (IDENT) shows full list
		    filterPlayerDropdown('MVP_LB_IDENT');
		    break;

		
		case 'z': case 'x': case 'c': case 'v': case 'Control_z': case 'Control_x': case 'Control_c': case 'Control_v': case 'Control_Shift_Z': case 'Control_Shift_Y':
		case "Shift_@": case "Shift_$": case "Control_Shift_@": case "Control_Alt_5": case "Alt_Shift_@": case "Control_Alt_6": case "Control_Alt_9": case "Control_Alt_0":
		case 'Alt_Shift_K': case 'Alt_Shift_X': case 'Alt_Shift_T': case 'Alt_Shift_V': case 'Control_Alt_7':
			switch(whatToProcess) {
			case 'z': case "Shift_@": case 'Alt_Shift_K':
				header_text.innerHTML = 'LEADERBOARD - MOST RUNS';
				break;
			case 'x': case "Shift_$": case 'Alt_Shift_X':
				header_text.innerHTML = 'LEADERBOARD - MOST WICKETS';
				break;	
			case 'c': case "Control_Shift_@": case 'Alt_Shift_T':
				header_text.innerHTML = 'LEADERBOARD - MOST FOURS';
				break;	
			case 'v': case "Control_Alt_5": case 'Alt_Shift_V':
				header_text.innerHTML = 'LEADERBOARD - MOST SIXES';
				break;	
			case 'Control_z': case "Alt_Shift_@":
			    header_text.innerHTML = 'LEADERBOARD - HIGHEST SCORES';
				break;		
			case 'Control_x': case "Control_Alt_6":
			    header_text.innerHTML = 'LEADERBOARD - BEST FIGURES';
				break;	
			case 'Control_c': case 'Control_Alt_7':
				header_text.innerHTML = 'LEADERBOARD - TAPE BALL';
				break;
			case 'Control_v':
				header_text.innerHTML = 'LEADERBOARD - MOST ECONOMICAL 50-50 OVER';
				break;
			case 'Control_Shift_Z': case "Control_Alt_9":
				header_text.innerHTML = 'LEADERBOARD - BEST STRIKE RATE'
				break;
			case 'Control_Shift_Y': case "Control_Alt_0":
				header_text.innerHTML = 'LEADERBOARD - BEST ECONOMY'
				break;
			}
			let num = 0;
			switch(whatToProcess){
				case 'Control_Shift_Y': case "Control_Alt_0":
					select = document.createElement('select');
					select.id = 'selectPlayerName';
					select.name = select.id;
					num = 0;
					
					switch($('#selected_broadcaster').val().toUpperCase()){
					case 'ISPL':
						for(i=0;i<dataToProcess.length;i++){
							if(dataToProcess[i].ballsBowled>=36){
								if(num<5){
									option = document.createElement('option');
						            option.value = (num+1)+ "_" + dataToProcess[i].playerId;
						            option.text = dataToProcess[i].player.full_name;
						            select.appendChild(option);
						            num++;
								}
							}
						}
						break;
					case 'NPL': case 'APL':
						for(i=0;i<dataToProcess.length;i++){
							if(dataToProcess[i].ballsBowled>=30){
								if(num<5){
									option = document.createElement('option');
						            option.value = (num+1)+ "_" + dataToProcess[i].playerId;
						            option.text = dataToProcess[i].player.full_name;
						            select.appendChild(option);
						            num++;
								}
							}
						}
						break;
					default:
						for(i=0;i<dataToProcess.length;i++){
							if(dataToProcess[i].ballsBowled>=12){
								if(num<5){
									option = document.createElement('option');
						            option.value = (num+1)+ "_" + dataToProcess[i].playerId;
						            option.text = dataToProcess[i].player.full_name;
						            select.appendChild(option);
						            num++;
								}
							}
						}
					break;
					}	
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1
					break;
				case 'Control_Shift_Z': case "Control_Alt_9":
					select = document.createElement('select');
					select.id = 'selectPlayerName';
					select.name = select.id;
					num = 0;
					for(i=0;i<dataToProcess.length;i++){
						if(dataToProcess[i].ballsFaced>=30){
							if(num<5){
								option = document.createElement('option');
					            option.value = (num+1)+ "_" + dataToProcess[i].playerId;
					            option.text = dataToProcess[i].player.full_name;
					            select.appendChild(option);
					            num++;
							}
						}
					}
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1
				
					switch($('#selected_broadcaster').val().toUpperCase()){
						case 'ICC-U19-2023': case 'NPL':  case 'MPL': case 'APL':
							select = document.createElement('select');
							select.id = 'selectType';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'withoutsponsor';
							option.text = 'WITHOUT SPONSOR';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'withsponsor';
							option.text = 'WITH SPONSOR';
							select.appendChild(option);
							
							select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
							row.insertCell(cellCount).appendChild(select);
							setDropdownOptionToSelectOptionArray($(select),1);
							cellCount = cellCount + 1;
						break;
					}
				break;
				default:
					select = document.createElement('select');
					select.id = 'selectPlayerName';
					select.name = select.id;
					num = 0;
					for(i=0;i<dataToProcess.length;i++){
						if(num<5){
							option = document.createElement('option');
				            option.value = (num+1)+ "_" + dataToProcess[i].playerId;
				            option.text = dataToProcess[i].player.full_name;
				            select.appendChild(option);
				            num++;
						}
					}
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1
				break;
			}
			
			select = document.createElement('select');
			select.id = 'selectmtch';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'WITH_CURRENT';
			option.text = 'WITH CURRENT MATCH';
			select.appendChild(option);
						
			option = document.createElement('option');
			option.value = 'WITHOUT_CURRENT';
			option.text = 'WITHOUT CURRENT MATCH';
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1;
			
			switch(whatToProcess){
			case 'z': case 'x':
				if($('#selected_broadcaster').val() === 'NPL' || $('#selected_broadcaster').val() === 'APL'){
					select = document.createElement('select');
					select.id = 'selectLogo';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'TLogo';
					option.text = 'TLogo';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'Agarkhanchi';
					option.text = 'Agarkhanchi';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'PresidentialSchool';
					option.text = 'PresidentialSchool';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),2);
					cellCount = cellCount + 1;
				}
				break;
			}
			
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'BENGAL-T20':
					select = document.createElement('select');
					select.id = 'selectPhoto';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'withoutphoto';
					option.text = 'WITHOUT PHOTO';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'withphoto';
					option.text = 'WITH PHOTO';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),2);
					cellCount = cellCount + 1;
				break;
			}
			
			break;
		
		case 'Control_Shift_P':
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'NPL': case 'APL':
					header_text.innerHTML = 'Fair Play Points Table';
					
					select = document.createElement('select');
					select.id = 'selectType';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'WITH_CURRENT';
					option.text = 'With Current Team';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'WITHOUT_CURRENT';
					option.text = 'Without Current Team';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1;
					break;
				default:
					switch(whatToProcess) {
					case 'Control_Shift_P':
						header_text.innerHTML = 'LT BOWLER SPELL';
						break;		
					}
					
					select = document.createElement('select');
					select.id = 'selectPlayerName';
					select.name = select.id;
					
					session_match.match.inning.forEach(function(inn){
						if(inn.inningNumber == document.getElementById('which_inning').value){
							inn.bowlingCard.forEach(function(boc){
								option = document.createElement('option');
								option.value = boc.player.playerId;
								option.text = boc.player.full_name;
								select.appendChild(option);
							});
						}
					});
		
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					removeSelectDuplicates(select.id);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1;
					break;
			}
			break;
		
		case 'Control_f':
			switch(whatToProcess) {
			case 'Control_f':
				header_text.innerHTML = 'LT BALL THIS SERIES';
				break;		
			}
			
			select = document.createElement('select');
			select.id = 'selectPlayerName';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.forEach(function(boc){
						if(boc.status == 'CURRENTBOWLER'){
							option = document.createElement('option');
							option.value = boc.player.playerId;
							option.text = boc.player.full_name;
							select.appendChild(option);
						}
					});
					
					if(inn.bowlingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name;
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name;
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}
				}
			});

			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'BENGAL-T20': //case 'NPL': case 'APL':
				break;
			default:
				select = document.createElement('select');
				select.id = 'selectType';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'WITH_CURRENT';
				option.text = 'With Current Match';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'WITHOUT_CURRENT';
				option.text = 'Without Current Match';
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),1);
				cellCount = cellCount + 1;
			}
			break;
				
		case 'Control_F9':  case 'Control_Shift_F9'://BowlerStyle
			switch(whatToProcess) {
			case 'Control_F9':
				header_text.innerHTML = 'BALL STYLE';
				break;	
			case 'Control_Shift_F9':
				header_text.innerHTML = 'BALL STYLE With Photo';
				break;		
			}
			
			select = document.createElement('select');
			select.id = 'selectPlayerName';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.forEach(function(boc){
						if(boc.status == 'CURRENTBOWLER'){
							option = document.createElement('option');
							option.value = boc.player.playerId;
							option.text = boc.player.full_name;
							select.appendChild(option);
						}
					});
					
					if(inn.bowlingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name;
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name;
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}
				}
			});

			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			
			switch(whatToProcess) {
			case 'Control_F9': case 'Control_Shift_F9':
				select = document.createElement('select');
				select.id = 'selectBowlingEnd';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'WITHOUTEND';
				option.text = 'WITHOUT END';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = session_match.setup.ground.first_bowling_end;
				option.text = session_match.setup.ground.first_bowling_end;
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = session_match.setup.ground.second_bowling_end;
				option.text = session_match.setup.ground.second_bowling_end;
				select.appendChild(option);
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),1);
				cellCount = cellCount + 1
				break;			
			}
			
			break;
		case 'Alt_n': case 'Shift_Q'://Bowler milestone
			
			switch(whatToProcess) {
			case 'Alt_n':
				header_text.innerHTML = 'BOWLER MILESTONE';
				break;	
			case 'Shift_Q':
				header_text.innerHTML = 'BOWLER THIS SERIES';
				break;		
			}
			
			select = document.createElement('select');
			select.id = 'selectPlayerName';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.forEach(function(boc){
						if(boc.status == 'CURRENTBOWLER'){
							option = document.createElement('option');
							option.value = boc.player.playerId;
							option.text = boc.player.full_name;
							select.appendChild(option);
						}
					});
					
					if(inn.bowlingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name;
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name;
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}
				}
			});

			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			switch(whatToProcess){
			case 'Shift_Q':
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'ICC-U19-2023': case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'APL':
					select = document.createElement('select');
					select.id = 'selectStatType';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = '0';
					option.text = '';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '1';
					option.text = 'Matches';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '2';
					option.text = 'Wickets';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '3';
					option.text = 'Economy';
					select.appendChild(option);
					
					switch($('#selected_broadcaster').val().toUpperCase()){
						case 'ICC-U19-2023':
						option = document.createElement('option');
						option.value = '4';
						option.text = 'Average';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '5';
						option.text = 'Strike Rate';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '6';
						option.text = '5WI';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '7';
						option.text = 'Best Fig';
						select.appendChild(option);
						break;
					case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'APL':
						option = document.createElement('option');
						option.value = '4';
						option.text = '3WI';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '5';
						option.text = 'Best Fig';
						select.appendChild(option);
						break;
					}
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),1);
					cellCount = cellCount + 1
					break;
				}
				
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'NPL': case 'LEGENDS-90':  case 'MPL': case 'APL': case 'T20_MUMBAI':
						select = document.createElement('select');
						select.id = 'selectType';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = 'WITH_CURRENT';
						option.text = 'With Current Match';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'WITHOUT_CURRENT';
						option.text = 'Without Current Match';
						select.appendChild(option);
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),2);
						cellCount = cellCount + 1;
						break;
				}
			}
			
			break;
			
		case 'Control_m': case 'Shift_F11': case 'Control_Shift_L': case 'Control_9': //MATCH-PROMO - PreviousMatchSummary 
			
			switch(whatToProcess) {
			case 'Control_Shift_L':
				header_text.innerHTML = 'MATCH PROMO';
				break;	
			case 'Control_m':
				header_text.innerHTML = 'FF MATCH PROMO';
				break;
			case 'Shift_F11':
				header_text.innerHTML = 'FF PREVIOUS MATCH SUMMARY';
				break;
			case 'Control_9':
				header_text.innerHTML = 'ScoreBug MATCH PROMO';
				break;
			}
			select = document.createElement('select');
			select.id = 'selectMatchPromo';
			select.name = select.id;
			console.log(Array.isArray(dataToProcess), dataToProcess.length);

			dataToProcess.forEach(function(oop){	
				option = document.createElement('option');
	            option.value = oop.matchnumber;
	            option.text = oop.matchnumber + ' - ' +oop.home_Team.teamName1 + ' Vs ' + oop.away_Team.teamName1 ;
	            select.appendChild(option);
	        });
	        
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			
			cellCount = cellCount + 1;
			
			switch(whatToProcess) {
				case 'Shift_F11':
					switch($('#selected_broadcaster').val().toUpperCase()){
					case 'ISPL': case 'T20_MUMBAI':
						select = document.createElement('select');
						select.id = 'selectpreSummary';
						select.name = select.id;
					
						option = document.createElement('option');
			            option.value = 'normalsummary';
			            option.text = 'Normal Summary';
			            select.appendChild(option);
			            
						option = document.createElement('option');
			            option.value = 'photosummary';
			            option.text = 'Photo Summary';
			            select.appendChild(option);
			            
			            select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),1);
						cellCount = cellCount + 1;
					    break;
							
					case 'ICC-U19-2023': case 'BENGAL-T20': case 'NPL':  case 'MPL': case 'APL': 
						select = document.createElement('select');
						select.id = 'selectpreSummary';
						select.name = select.id;
					
						option = document.createElement('option');
			            option.value = 'captain';
			            option.text = 'Captain';
			            select.appendChild(option);
			            
						option = document.createElement('option');
			            option.value = 'logo';
			            option.text = 'logo';
			            select.appendChild(option);
			            
			            select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),1);
						cellCount = cellCount + 1;
			            break;
					}
					break;
			}
			break;
		case 'y':
			select = document.createElement('select');
			select.id = 'selectBatsmanThisMatch';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.forEach(function(bc){
						if(bc.status == 'NOT OUT'){
							if(bc.onStrike == 'YES'){
								option = document.createElement('option');
								option.value = bc.player.playerId;
								option.text = bc.player.full_name + " - " + bc.status;
								select.appendChild(option);
							}else{
								option = document.createElement('option');
								option.value = bc.player.playerId;
								option.text = bc.player.full_name + " - " + bc.status;
								select.appendChild(option);
							}
						}
					});
					
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						option = document.createElement('option');
						option.value = bc.playerId;
						option.text = bc.player.full_name + " - " + bc.status;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
			
		case 'Alt_F1':
			switch(whatToProcess) {
			case 'Alt_F1':
				header_text.innerHTML = 'BAT GRIFF';
				break;						
			}
			select = document.createElement('select');
			select.id = 'selectBatsmanThisMatch';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.forEach(function(bc){
						if(bc.status == 'NOT OUT'){
							if(bc.onStrike == 'YES'){
								option = document.createElement('option');
								option.value = bc.player.playerId;
								option.text = bc.player.full_name;
								select.appendChild(option);
							}else{
								option = document.createElement('option');
								option.value = bc.player.playerId;
								option.text = bc.player.full_name;
								select.appendChild(option);
							}
						}
					});
					
					if(inn.battingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name;
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name;
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
			
		case 'Alt_F2':
			switch(whatToProcess) {
			case 'Alt_F2':
				header_text.innerHTML = 'BALL GRIFF';
				break;						
			}
			select = document.createElement('select');
			select.id = 'selectBatsmanThisMatch';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.forEach(function(boc){
						if(boc.status == 'CURRENTBOWLER'){
							option = document.createElement('option');
							option.value = boc.player.playerId;
							option.text = boc.player.full_name;
							select.appendChild(option);
						}
					});
					
					if(inn.bowlingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name;
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name;
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;	
		case 'F5': case 'Shift_A': case 'Control_7':
			switch(whatToProcess) {
			case 'F5': case 'Shift_A':
				header_text.innerHTML = 'BAT THIS MATCH';
				break;
			case 'Control_7':
				header_text.innerHTML = 'SCOREBUG BAT THIS MATCH';
				break;
			}
			select = document.createElement('select');
			select.id = 'selectBatsmanThisMatch';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.forEach(function(bc){
						if(bc.status == 'NOT OUT'){
							if(bc.onStrike == 'YES'){
								option = document.createElement('option');
								option.value = bc.playerId;
								option.text = bc.player.full_name + " - " + bc.status;
								select.appendChild(option);
							}else{
								option = document.createElement('option');
								option.value = bc.playerId;
								option.text = bc.player.full_name + " - " + bc.status;
								select.appendChild(option);
							}
						}
					});
					
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						option = document.createElement('option');
						option.value = bc.playerId;
						option.text = bc.player.full_name + " - " + bc.status;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'BENGAL-T20':
					select = document.createElement('select');
					select.id = 'sponsorOrNot';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'nosponsor';
					option.text = 'Without Sponsor';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'sponsor';
					option.text = 'With Sponsor';
					select.appendChild(option);
				
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),1);
					cellCount = cellCount + 1
					
					select = document.createElement('select');
					select.id = 'statType';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'noStats';
					option.text = '';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'DT20';
					option.text = 'DT20';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'THISSERIES';
					option.text = 'THIS SERIES';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'KAPL';
					option.text = 'KAPL';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),2);
					cellCount = cellCount + 1
				break;
			}
			break;
		case 'Shift_F5': case 'q'://BatThisMatch
			switch(whatToProcess) {
			case 'Shift_F5':
				header_text.innerHTML = 'BAT 0,1,2';
				break;
			case 'Q':
				header_text.innerHTML = 'PLAYER BOUNDARY';
				break;				
			}
			select = document.createElement('select');
			select.id = 'selectBatsmanThisMatch';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.forEach(function(bc){
						if(bc.status == 'NOT OUT'){
							if(bc.onStrike == 'YES'){
								option = document.createElement('option');
								option.value = bc.playerId;
								option.text = bc.player.full_name + " - " + bc.status;
								select.appendChild(option);
							}else{
								option = document.createElement('option');
								option.value = bc.playerId;
								option.text = bc.player.full_name + " - " + bc.status;
								select.appendChild(option);
							}
						}
					});
					
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						option = document.createElement('option');
						option.value = bc.playerId;
						option.text = bc.player.full_name + " - " + bc.status;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		
		case 'g':
			select = document.createElement('select');
			select.id = 'selectBatamanThisMatch';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.forEach(function(boc){
						if(boc.status == 'CURRENTBOWLER'){
							option = document.createElement('option');
							option.value = boc.player.playerId;
							option.text = boc.player.full_name;
							select.appendChild(option);
						}
					});
					
					inn.bowlingCard.forEach(function(boc,boc_index,bc_arr){
						option = document.createElement('option');
						option.value = boc.playerId;
						option.text = boc.player.full_name;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		
		case 'F9': case 'Shift_R': case 'Control_8':
			switch(whatToProcess) {
			case 'F9': case 'Shift_R':
				header_text.innerHTML = 'BALL THIS MATCH';
				break;
			case 'Control_8':
				header_text.innerHTML = 'SCOREBUG BALL THIS MATCH';
				break;		
			}
			
			select = document.createElement('select');
			select.id = 'selectBatamanThisMatch';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.forEach(function(boc){
						if(boc.status == 'CURRENTBOWLER'){
							option = document.createElement('option');
							option.value = boc.player.playerId;
							option.text = boc.player.full_name;
							select.appendChild(option);
						}
					});
					
					inn.bowlingCard.forEach(function(boc,boc_index,bc_arr){
						option = document.createElement('option');
						option.value = boc.playerId;
						option.text = boc.player.full_name;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'BENGAL-T20':
					select = document.createElement('select');
					select.id = 'sponsorOrNot';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'nosponsor';
					option.text = 'Without Sponsor';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'sponsor';
					option.text = 'With Sponsor';
					select.appendChild(option);
				
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),1);
					cellCount = cellCount + 1
					
					select = document.createElement('select');
					select.id = 'statType';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = 'noStats';
					option.text = '';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'DT20';
					option.text = 'DT20';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'THISSERIES';
					option.text = 'THIS SERIES';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'KAPL';
					option.text = 'KAPL';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),2);
					cellCount = cellCount + 1
				break;
			}
			break;
		case 'Shift_F9': //BallThisMatch
			
			switch(whatToProcess) {
			case 'Shift_F9':
				header_text.innerHTML = 'BALL 0,1,2';
				break;				
			}
			
			select = document.createElement('select');
			select.id = 'selectBatamanThisMatch';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.forEach(function(boc){
						if(boc.status == 'CURRENTBOWLER'){
							option = document.createElement('option');
							option.value = boc.player.playerId;
							option.text = boc.player.full_name;
							select.appendChild(option);
						}
					});
					
					inn.bowlingCard.forEach(function(boc,boc_index,bc_arr){
						option = document.createElement('option');
						option.value = boc.playerId;
						option.text = boc.player.full_name;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		case 'Control_p':
			select = document.createElement('select');
			select.id = 'selectGroups';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'Group1';
			option.text = 'Group 1';	
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Group2';
			option.text = 'Group 2';	
			select.appendChild(option);
			
			select.setAttribute('onchange','setDropdownOptionToSelectOptionArray(this, 0)');
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;
		case 'p':
			select = document.createElement('select');
			select.id = 'selectGroups';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'GroupA';
			option.text = 'Group A';	
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'GroupB';
			option.text = 'Group B';	
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'GroupC';
			option.text = 'Group C';	
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'GroupD';
			option.text = 'Group D';	
			select.appendChild(option);
			
			select.setAttribute('onchange','setDropdownOptionToSelectOptionArray(this, 0)');
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;
		case 'Shift_O':
			select = document.createElement('select');
			select.id = 'selectHowoutPlayers';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						option = document.createElement('option');
						option.value = bc.playerId;
						option.text = bc.player.full_name + " - " + bc.status;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange','setDropdownOptionToSelectOptionArray(this, 0)');
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			break;
		case 'Control_j':
			header_text.innerHTML = 'SESSION';
			
			select = document.createElement('select');
			select.id = 'selectDays';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = '1';
			option.text = 'Day 1' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '2';
			option.text = 'Day 2' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '3';
			option.text = 'Day 3' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '4';
			option.text = 'Day 4' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '5';
			option.text = 'Day 5' ;
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;	
		case 'Alt_w':
			header_text.innerHTML = 'SESSION';
			
			select = document.createElement('select');
			select.id = 'selectday';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = '1';
			option.text = 'Day 1' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '2';
			option.text = 'Day 2' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '3';
			option.text = 'Day 3' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '4';
			option.text = 'Day 4' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '5';
			option.text = 'Day 5' ;
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectsession';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = '1';
			option.text = 'Session 1' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '2';
			option.text = 'Session 2' ;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = '3';
			option.text = 'Session 3' ;
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1;
			break;
		case 'Shift_Z':
			switch(whatToProcess) {
			case 'Shift_Z':
				header_text.innerHTML = 'BEST STATS - THIS SERIES';
				break;		
			}
			
			select = document.createElement('select');
			select.id = 'selectHowoutPlayers';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						option = document.createElement('option');
						option.value = bc.playerId;
						option.text = bc.player.full_name;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange','setDropdownOptionToSelectOptionArray(this, 0)');
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
			
		case 'Shift_X':
			switch(whatToProcess) {
			case 'Shift_X':
				header_text.innerHTML = 'BEST FIGURE - THIS SERIES';
				break;			
			}
			
			select = document.createElement('select');
			select.id = 'selectHowoutPlayers';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == (3 - document.getElementById('which_inning').value)){
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						option = document.createElement('option');
						option.value = bc.playerId;
						option.text = bc.player.full_name;	
						select.appendChild(option);
					});
				}
			});
			
			select.setAttribute('onchange','setDropdownOptionToSelectOptionArray(this, 0)');
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
			
		case 'Alt_Shift_C'://HowOut //how out w/o fielder // how out both
			switch(whatToProcess) {
			case 'Alt_Shift_C':
				header_text.innerHTML = 'CAPTAIN';
				break;				
			}
			select = document.createElement('select');
			select.id = 'selectCaptainPlayers';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					
					if(inn.battingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name;
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name;
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}
				}
			});
			
			select.setAttribute('onchange','setDropdownOptionToSelectOptionArray(this, 0)');
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
				
		case 'F6': case 'Shift_F6': case 'Alt_F6'://HowOut //how out w/o fielder // how out both
			switch(whatToProcess) {
			case 'F6':
				header_text.innerHTML = 'HOW OUT';
				break;
			case 'Shift_F6':
				header_text.innerHTML = 'HOW OUT WITHOUT FIELDER';
				break;	
			case 'Alt_F6':
				header_text.innerHTML = 'HOW OUT BOTH';
				break;				
			}
			select = document.createElement('select');
			select.id = 'selectHowoutPlayers';
			select.name = select.id;
			
			switch(whatToProcess) {
			case 'Shift_F6':
				session_match.match.inning.forEach(function(inn){
					if(inn.inningNumber == document.getElementById('which_inning').value){
						inn.battingCard.forEach(function(bc,bc_index,bc_arr){
							option = document.createElement('option');
							option.value = bc.playerId;
							option.text = bc.player.full_name + " - " + bc.status;	
							select.appendChild(option);
						});
					}
				});
				break;
			default:
				session_match.match.inning.forEach(function(inn){
					if(inn.inningNumber == document.getElementById('which_inning').value){
						inn.battingCard.forEach(function(bc,bc_index,bc_arr){
							if(inn.fallsOfWickets.length > 0){
								if(bc.playerId == inn.fallsOfWickets[inn.fallsOfWickets.length-1].fowPlayerID){
									option = document.createElement('option');
									option.value = bc.playerId;
									option.text = bc.player.full_name + " - " + bc.status;	
									select.appendChild(option);
								}
							}
							
						});
						
						inn.battingCard.forEach(function(bc,bc_index,bc_arr){
							option = document.createElement('option');
							option.value = bc.playerId;
							option.text = bc.player.full_name + " - " + bc.status;	
							select.appendChild(option);
						});
					}
				});
				break;				
			}
			
			select.setAttribute('onchange','setDropdownOptionToSelectOptionArray(this, 0)');
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			break;
		case 'l':
		
			select = document.createElement('select');
			select.id = 'selectType';
			select.name = select.id;
			switch($('#selected_broadcaster').val().toUpperCase()){
				/*case 'T20_MUMBAI':
					option = document.createElement('option');
					option.value = 'Economy';
					option.text = 'This match - First (Economy)';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'Catches';
					option.text = 'This Match - Second (Catches)';
					select.appendChild(option);
				 	break;*/
				 case 'ISPL': case 'T20_MUMBAI':
				 	option = document.createElement('option');
					option.value = 'THIS_MATCH';
					option.text = 'This Match';
					select.appendChild(option);
				 
				 	option = document.createElement('option');
					option.value = 'Tournament';
					option.text = 'This Series';
					select.appendChild(option);
				 
				 	/*option = document.createElement('option');
					option.value = 'Career';
					option.text = 'Career';
					select.appendChild(option);*/
				 	break;
				default:
					option = document.createElement('option');
					option.value = 'Economy';
					option.text = 'This match - First (Economy)';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'Catches';
					option.text = 'This Match - Second (Catches)';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'Tournament';
					option.text = 'Tournament';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'Career';
					option.text = 'Career';
					select.appendChild(option);
				 break;
			}
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectPlayerName';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					if(inn.battingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name +' -('+session_match.setup.homeTeam.teamName4+')';
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)'+' -('+session_match.setup.homeTeam.teamName4+')';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name+' -('+session_match.setup.awayTeam.teamName4+')';
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)'+' -('+session_match.setup.awayTeam.teamName4+')';
							select.appendChild(option);
						});
					}
				}
			});
			
			/*session_match.setup.homeSquad.forEach(function(hs){
				option = document.createElement('option');
					option.value = hs.playerId;
					option.text = hs.full_name+' -('+session_match.setup.homeTeam.teamName4+')';
					select.appendChild(option);	
			});

			session_match.setup.homeOtherSquad.forEach(function(hos){
				option = document.createElement('option');
				option.value = hos.playerId;
				option.text = hos.full_name  + ' (OTHER)'+' -('+session_match.setup.homeTeam.teamName4+')';
				select.appendChild(option);
			});
			session_match.setup.awaySquad.forEach(function(as){
				option = document.createElement('option');
				option.value = as.playerId;
				option.text = as.full_name+' -('+session_match.setup.awayTeam.teamName4+')';
				select.appendChild(option);
			});
			session_match.setup.awayOtherSquad.forEach(function(aos){
				option = document.createElement('option');
				option.value = aos.playerId;
				option.text = aos.full_name  + ' (OTHER)'+' -(' + session_match.setup.awayTeam.teamName4 + ')';
				select.appendChild(option);
			});*/
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1;
			break;
		case 'F7': case 'Control_d': case "Alt_Shift_N"://Lt Bat Profile
			header_text.innerHTML = 'BAT PLAYER PROFILE';
			
			select = document.createElement('select');
			select.id = 'selectPlayerName';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.battingCard.forEach(function(bc){
						if(bc.status == 'NOT OUT'){
							if(bc.onStrike == 'YES'){
								option = document.createElement('option');
								option.value = bc.player.playerId;
								option.text = bc.player.full_name;
								select.appendChild(option);
							}else{
								option = document.createElement('option');
								option.value = bc.player.playerId;
								option.text = bc.player.full_name;
								select.appendChild(option);
							}
						}
					});
					
					if(inn.battingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name;
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name;
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}
				}
			});

			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectProfile';
			select.name = select.id;
			
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI':
					[ { value: 'T20 MUMBAI', text: 'T20 MUMBAI' },
					  { value: 'DT20', text: 'T20' },
					  { value: 'IT20', text: 'T20I' },
					  { value: 'MT20 SEASON 3', text: 'MT20 SEASON 3' },
					  { value: 'IPL 2026', text: 'IPL 2026' },
					  { value: 'IPL', text: 'IPL' },
					  { value: 'WPL', text: 'WPL' }
					].forEach(({ value, text }) => {
						  option = document.createElement('option');
						  option.value = value;
						  option.text = text;
						  select.appendChild(option);
					});
					break;
				case 'LEGENDS-90':
					[ { value: 'DT20', text: 'T20' },
					 { value: 'IT20', text: 'T20i' }
					].forEach(({ value, text }) => {
						  option = document.createElement('option');
						  option.value = value;
						  option.text = text;
						  select.appendChild(option);
					});
					break;
				case 'NPL': case 'MPL': case 'APL':	case 'VIDARBHA':		
					if($('#selected_broadcaster').val().toUpperCase()=='MPL'){
						option = document.createElement('option');
						option.value = 'MPL';
						option.text = 'MPL';
						select.appendChild(option);	
					}else if($('#selected_broadcaster').val().toUpperCase()=='NPL'){
						option = document.createElement('option');
						option.value = 'NPL S1';
						option.text = 'NPL S1';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'NPL_CAREER';
						option.text = 'NPL CAREER';
						select.appendChild(option);
					}else if($('#selected_broadcaster').val().toUpperCase()=='APL'){
						option = document.createElement('option');
						option.value = 'APL_CAREER';
						option.text = 'APL CAREER';
						select.appendChild(option);
	                }else if($('#selected_broadcaster').val().toUpperCase()=='VIDARBHA'){
							option = document.createElement('option');
							option.value = 'VIDARBHA_CAREER';
							option.text = 'VIDARBHA CAREER';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'IPL';
							option.text = 'IPL';
							select.appendChild(option);
						}
					
					option = document.createElement('option');
					option.value = 'DT20';
					option.text = 'DT20';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'IT20';
					option.text = 'IT20';
					select.appendChild(option);
					break;
				case 'ISPL':
					option = document.createElement('option');
					option.value = 'ISPL S1';
					option.text = 'ISPL S1';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'ISPL S2';
					option.text = 'ISPL S2';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'ISPL_CAREER';
					option.text = 'ISPL CAREER';
					select.appendChild(option);
					break;
				case 'BENGAL-T20':
									
					option = document.createElement('option');
					option.value = 'DT20';
					option.text = 'DT20';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'KAPL';
					option.text = 'KAPL';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'IPL';
					option.text = 'IPL';
					select.appendChild(option);
				break;
				default:
					option = document.createElement('option');
					option.value = 'U19ODI';
					option.text = 'U19 ODI';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'LIST A';
					option.text = 'LIST A';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'ACCU19';
					option.text = 'ACC U19';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'SA TRI-NATION 2023-24';
					option.text = 'SA TRI-NATION';
					select.appendChild(option);
			}
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1
			
			switch(whatToProcess){
			case 'Control_d':
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'ICC-U19-2023':
					select = document.createElement('select');
					select.id = 'selectStatType';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = '0';
					option.text = '';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '1';
					option.text = 'Matches';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '2';
					option.text = 'Runs';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '3';
					option.text = 'Fifties';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '4';
					option.text = 'Hundreds';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '5';
					option.text = 'Average';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '6';
					option.text = 'Strike Rate';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '7';
					option.text = 'Best';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),2);
					cellCount = cellCount + 1
					break;
				case 'NPL':  case 'MPL': case 'APL':
					select = document.createElement('select');
					select.id = 'selectStatType';
					select.name = select.id;
					
					option = document.createElement('option');
					option.value = '0';
					option.text = '';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '1';
					option.text = 'Matches';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '2';
					option.text = 'Runs';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '3';
					option.text = 'Strike Rate';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '4';
					option.text = '50s/100s';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = '7';
					option.text = 'Best';
					select.appendChild(option);
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),2);
					cellCount = cellCount + 1
					break;
				}
				break;
			}
			break;		
		case 'Alt_3':
			switch($('#selected_broadcaster').val().toUpperCase()){
			case 'ICC-U19-2023': case 'NPL': case "ISPL": case 'LEGENDS-90':  case 'MPL': case 'T20_MUMBAI':
			case 'APL': case "VIDARBHA":
				switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI':
					header_text.innerHTML = 'FULL INFOBAR SECTION - BAT PLAYER PROFILE';
					break;
				default:
					header_text.innerHTML = 'MIDDLE INFOBAR SECTION - BAT PLAYER PROFILE';
					break;	
				}
				
				select = document.createElement('select');
				select.id = 'selectPlayerName';
				select.name = select.id;
				
				session_match.match.inning.forEach(function(inn){
					if(inn.isCurrentInning == 'YES'){
						inn.battingCard.forEach(function(bc){
							if(bc.status == 'NOT OUT'){
								if(bc.onStrike == 'YES'){
									option = document.createElement('option');
									option.value = bc.player.playerId;
									option.text = bc.player.full_name;
									select.appendChild(option);
								}else{
									option = document.createElement('option');
									option.value = bc.player.playerId;
									option.text = bc.player.full_name;
									select.appendChild(option);
								}
							}
						});
						
						if(inn.battingTeamId == session_match.setup.homeTeamId){
							session_match.setup.homeSquad.forEach(function(hs){
								option = document.createElement('option');
								option.value = hs.playerId;
								option.text = hs.full_name;
								select.appendChild(option);
							});
							session_match.setup.homeOtherSquad.forEach(function(hos){
								option = document.createElement('option');
								option.value = hos.playerId;
								option.text = hos.full_name  + ' (OTHER)';
								select.appendChild(option);
							});
						}else {
							session_match.setup.awaySquad.forEach(function(as){
								option = document.createElement('option');
								option.value = as.playerId;
								option.text = as.full_name;
								select.appendChild(option);
							});
							session_match.setup.awayOtherSquad.forEach(function(aos){
								option = document.createElement('option');
								option.value = aos.playerId;
								option.text = aos.full_name  + ' (OTHER)';
								select.appendChild(option);
							});
						}
					}
				});
	
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				removeSelectDuplicates(select.id);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectProfile';
				select.name = select.id;
				
				const broadcaster = $('#selected_broadcaster').val().toUpperCase();

				const addOption = (value, text = value) => {
					const option = document.createElement('option');
					option.value = value;
					option.text = text;
					select.appendChild(option);
				};

				switch (broadcaster) {

					case 'ICC-U19-2023':
						[['U19ODI', 'U19 ODI'],
							['LIST A', 'LIST A'],
							['ACCU19', 'ACC U19'],
							['SA TRI-NATION 2023-24', 'SA TRI-NATION']
						].forEach(([value, text]) => addOption(value, text));
						break;
					case "VIDARBHA":
						addOption('DT20');
						addOption('IT20', 'T20I');
						break;

					case 'NPL':
						addOption('NPL S1');
						addOption('NPL_CAREER', 'NPL CAREER');
						addOption('DT20');
						addOption('IT20');
						break;

					case 'MPL':
						addOption('MPL', 'MPL CAREER');
						addOption('DT20');
						addOption('IT20');
						addOption('MPL_BOUNDARY_CAREER', 'MPL BOUNDARY CAREER');
						addOption('MPL_BOUNDARY', 'THIS SEASON BOUNDARY');
						addOption('RECENT_FORM_BAT', 'RECENT FORM');
						break;

					case 'APL':
						addOption('DT20');
						addOption('APL_CAREER', 'APL CAREER');
						addOption('APL_MILESTONE_BAT', 'APL MILESTONE');
						addOption('APL_BOUNDARY_CAREER', 'APL BOUNDARY CAREER');
						addOption('APL_BOUNDARY', 'THIS SEASON BOUNDARY');
						addOption('RECENT_FORM_BAT', 'RECENT FORM');
						break;

					case 'LEGENDS-90':
						addOption('DT20');
						addOption('BPL_CAREER', 'BPL CAREER');
						addOption('BPL_MILESTONE_BAT', 'BPL MILESTONE');
						addOption('BPL_BOUNDARY_CAREER', 'BPL BOUNDARY CAREER');
						addOption('BPL_BOUNDARY', 'THIS SEASON BOUNDARY');
						addOption('RECENT_FORM_BAT', 'RECENT FORM');
						break;

					case 'T20_MUMBAI':
						[['T20 MUMBAI', 'T20 Mumbai'],
							['THIS_SERIES', 'This Season'],
							['T20_MUMBAI_BOUNDARY_CAREER', 'T20 MUMBAI BOUNDARY CAREER'],
							['T20_MUMBAI_BOUNDARY', 'T20 MUMBAI BOUNDARY'],
							['DT20', 'T20'],
							['IT20', 'T20I'],
							['MT20 SEASON 3', 'MT20 SEASON 3'],
							['IPL 2026', 'IPL 2026'],
							['IPL', 'IPL'],
							['WPL', 'WPL']
						].forEach(([value, text]) => addOption(value, text));
						break;

					case 'ISPL':
						[['THIS_SERIES', 'THIS SERIES'],
							['SINGLE_DATA', 'SINGLE DATA'],
							['ISPL S2', 'ISPL S2'],
							['ISPL S1', 'ISPL S1'],
							['ISPL_CAREER', 'ISPL CAREER']
						].forEach(([value, text]) => addOption(value, text));
						break;
				}
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),1);
				cellCount = cellCount + 1
				
				row.insertCell(cellCount).id = 'Player';
		        cellCount++;
				if($('#selected_broadcaster').val().toUpperCase()== "ISPL"){						
				 	$('#selectProfile').on('change', function() {
				        if($('#selectProfile').val() == 'SINGLE_DATA'){
							select = document.createElement('select');
							select.id = 'selectStyle';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'BATTING_STYLE';
							option.text = 'BATTING STYLE';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'STRIKE_RATE';
							option.text = 'STRIKE RATE';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'BOUNDARY';
							option.text = 'BOUNDARY';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'RUNS_BALLS';
							option.text = 'RUNS & BALLS';
							select.appendChild(option);
							
							select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
							row.insertCell(2).appendChild(select);
							setDropdownOptionToSelectOptionArray($(select),2);
							cellCount = cellCount + 1
						}
				    });
				    $('#selectProfile').trigger('change');
				 }
				break;	
			}
			break;
			
		case 'F8': case 'Alt_F8'://NameSuper Player
			switch(whatToProcess){
			case 'F8':
				header_text.innerHTML = 'HOME TEAM NAMESUPER PLAYER';
				
				select = document.createElement('select');
				select.style = 'width:100px';
				select.id = 'selectPlayer';
				select.name = select.id;
				
				session_match.setup.homeSquad.forEach(function(hs){
					option = document.createElement('option');
					option.value = hs.playerId;
					option.text = hs.full_name + ' - ' + session_match.setup.homeTeam.teamName4;
					select.appendChild(option);
				});
				session_match.setup.homeOtherSquad.forEach(function(hos){
					option = document.createElement('option');
					option.value = hos.playerId;
					option.text = hos.full_name + ' - ' + session_match.setup.homeTeam.teamName4 + ' (OTHER)';
					select.appendChild(option);
				});
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
				break;
			case 'Alt_F8':
				header_text.innerHTML = 'AWAY TEAM NAMESUPER PLAYER';
				
				select = document.createElement('select');
				select.style = 'width:100px';
				select.id = 'selectPlayer';
				select.name = select.id;
				
				session_match.setup.awaySquad.forEach(function(as){
					option = document.createElement('option');
					option.value = as.playerId;
					option.text = as.full_name + ' - ' + session_match.setup.awayTeam.teamName4;
					select.appendChild(option);
				});
				session_match.setup.awayOtherSquad.forEach(function(aos){
					option = document.createElement('option');
					option.value = aos.playerId;
					option.text = aos.full_name + ' - ' + session_match.setup.awayTeam.teamName4 + ' (OTHER)';
					select.appendChild(option);
				});
				
				select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
				row.insertCell(cellCount).appendChild(select);
				setDropdownOptionToSelectOptionArray($(select),0);
				cellCount = cellCount + 1;
				break;
				
			}
			
			select = document.createElement('select');
			select.style = 'width:100px';
			select.id = 'selectCaptainWicketKeeper';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'Captain';
			option.text = 'Captain';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Captain Wicket-Keeper';
			option.text = 'Captain-WicketKeeper';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Team';
			option.text = 'Team';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Gully to Glory Award';
			option.text = 'Gully to Glory Award';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Player Of The Match';
			option.text = 'Player Of The Match';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Player Of The Tournament';
			option.text = 'Player Of The Tournament';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Wicket-Keeper';
			option.text = 'WicketKeeper';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'CATCH OF THE MATCH';
			option.text = 'CATCH OF THE MATCH';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'POWERFUL STRIKER OF THE MATCH';
			option.text = 'POWERFUL STRIKER OF THE MATCH';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'BEST ECONOMY';
			option.text = 'BEST ECONOMY';
			select.appendChild(option);
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1;
			break;
		case 'F10': case 'j': //NameSuperDB
			switch(whatToProcess){
			case 'F10':
				header_text.innerHTML = 'NAMESUPER DATABASE';
				break;
			case 'j':
				header_text.innerHTML = 'NAMESUPER SINGLE LINE';
				break;
			}
			select = document.createElement('select');
			select.style = 'width:130px';
			select.id = 'selectNameSuper';
			select.name = select.id;
			
			dataToProcess.forEach(function(ns){
				option = document.createElement('option');
				option.value = ns.namesuperId;
				option.text = ns.prompt ;
				select.appendChild(option);
			});
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			
			cellCount = cellCount + 1;
			break;
		case 'Alt_4': 
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'ICC-U19-2023': case 'NPL':case "ISPL": case 'LEGENDS-90':  case 'MPL': case 'T20_MUMBAI': case 'APL':
					switch($('#selected_broadcaster').val().toUpperCase()){
					case 'T20_MUMBAI':
						header_text.innerHTML = 'FULL INFOBAR SECTION - BALL PLAYER PROFILE';
						break;
					default:
						header_text.innerHTML = 'MIDDLE INFOBAR SECTION - BALL PLAYER PROFILE';
						break;
					}
				
					select = document.createElement('select');
					select.id = 'selectPlayerName';
					select.name = select.id;
					
					session_match.match.inning.forEach(function(inn){
						if(inn.isCurrentInning == 'YES'){
							inn.bowlingCard.forEach(function(boc){
								if(boc.status == 'CURRENTBOWLER'){
									option = document.createElement('option');
									option.value = boc.player.playerId;
									option.text = boc.player.full_name;
									select.appendChild(option);
								}
							});
							
							if(inn.bowlingTeamId == session_match.setup.homeTeamId){
								session_match.setup.homeSquad.forEach(function(hs){
									option = document.createElement('option');
									option.value = hs.playerId;
									option.text = hs.full_name;
									select.appendChild(option);
								});
								session_match.setup.homeOtherSquad.forEach(function(hos){
									option = document.createElement('option');
									option.value = hos.playerId;
									option.text = hos.full_name  + ' (OTHER)';
									select.appendChild(option);
								});
							}else {
								session_match.setup.awaySquad.forEach(function(as){
									option = document.createElement('option');
									option.value = as.playerId;
									option.text = as.full_name;
									select.appendChild(option);
								});
								session_match.setup.awayOtherSquad.forEach(function(aos){
									option = document.createElement('option');
									option.value = aos.playerId;
									option.text = aos.full_name  + ' (OTHER)';
									select.appendChild(option);
								});
							}
						}
					});
		
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
					row.insertCell(cellCount).appendChild(select);
					removeSelectDuplicates(select.id);
					setDropdownOptionToSelectOptionArray($(select),0);
					cellCount = cellCount + 1;
					
					select = document.createElement('select');
					select.id = 'selectProfile';
					select.name = select.id;
					
					const broadcaster = $('#selected_broadcaster').val().toUpperCase();

					const addOption = (value, text = value) => {
						const option = document.createElement('option');
						option.value = value;
						option.text = text;
						select.appendChild(option);
					};

					switch (broadcaster) {

						case 'ICC-U19-2023':
							[
								['U19ODI', 'U19 ODI'],
								['LIST A', 'LIST A'],
								['ACCU19', 'ACC U19'],
								['SA TRI-NATION 2023-24', 'SA TRI-NATION']
							].forEach(([value, text]) => addOption(value, text));
							break;

						case 'NPL':
							addOption('NPL S1');
							addOption('NPL_CAREER', 'NPL CAREER');
							addOption('DT20');
							addOption('IT20');
							break;

						case 'MPL':
							addOption('KCL', 'KCL CAREER');
							addOption('DT20');
							addOption('IT20');
							break;
						case 'APL':
							addOption('DT20');
							addOption('APL_CAREER', 'APL CAREER');
							addOption('APL_MILESTONE_BALL', 'APL MILESTONE');
							addOption('RECENT_FORM_BALL', 'RECENT FORM');
							break;		

						case 'LEGENDS-90':
							addOption('DT20');
							addOption('BPL_CAREER', 'BPL CAREER');
							addOption('BPL_MILESTONE_BALL', 'BPL MILESTONE');
							addOption('RECENT_FORM_BALL', 'RECENT FORM');
							break;
							
						case 'T20_MUMBAI':
							[['T20 MUMBAI', 'T20 Mumbai'],
								['THIS_SERIES', 'This Season'],
								['DT20', 'T20'],
								['IT20', 'T20I'],
								['MT20 SEASON 3', 'MT20 SEASON 3'],
								['IPL 2026', 'IPL 2026'],
								['IPL', 'IPL'],
								['WPL', 'WPL']
							].forEach(([value, text]) => addOption(value, text));
							break;

						case 'ISPL':
							[
								['THIS_SERIES', 'THIS SERIES'],
								['THIS_SERIES_TAPE_BALL', 'THIS SERIES ISPL BALL'],
								['SINGLE_DATA', 'SINGLE DATA'],
								['ISPL S2', 'ISPL S2'],
								['ISPL S1', 'ISPL S1'],
								['ISPL_CAREER', 'ISPL CAREER']
							].forEach(([value, text]) => addOption(value, text));
							break;
					}
					
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),1);
					cellCount = cellCount + 1;
					
					row.insertCell(cellCount).id = 'Player';
			        cellCount++;
					if($('#selected_broadcaster').val().toUpperCase()== "ISPL"){						
					 	$('#selectProfile').on('change', function() {
					        if($('#selectProfile').val() == 'SINGLE_DATA'){
								select = document.createElement('select');
								select.id = 'selectStyle';
								select.name = select.id;
								
								option = document.createElement('option');
								option.value = 'BOWLING_STYLE';
								option.text = 'BOWLING STYLE';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'ECONOMY';
								option.text = 'ECONOMY';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'FIGURES';
								option.text = 'FIGURES';
								select.appendChild(option);
								
								select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
								row.insertCell(2).appendChild(select);
								setDropdownOptionToSelectOptionArray($(select),2);
								cellCount = cellCount + 1
							}
					    });
					    $('#selectProfile').trigger('change');
					 }
					break;
			}			
			break;
		case 'Control_Shift_F5':
			header_text.innerHTML = 'FF EXCEL SUMMARY';
			
			select = document.createElement('select');
			select.style.width = '200px';
			select.id = 'selectGraphics';

			select.name = select.id;
			for (let i = 0; i < dataToProcess.length; i++) {
				option = document.createElement('option');
				option.value = dataToProcess[i];
				option.text = dataToProcess[i];
				select.appendChild(option);
			}
			row.insertCell(cellCount).appendChild(select);
			option.setAttribute('onclick', "processUserSelection(this)");
			cellCount++;
			$(document).ready(function() {
				$('#selectGraphics').select2({
					placeholder: 'Select an option',
					allowClear: true
				});
			});
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
		break;
		case 'F11': case 'Control_e': case "Alt_Shift_M"://Lt Ball Profile
			switch(whatToProcess){
			case 'F11': case "Alt_Shift_M":
				header_text.innerHTML = 'BALL PLAYER PROFILE';
				break;
			}
		
			select = document.createElement('select');
			select.id = 'selectPlayerName';
			select.name = select.id;
			
			session_match.match.inning.forEach(function(inn){
				if(inn.inningNumber == document.getElementById('which_inning').value){
					inn.bowlingCard.forEach(function(boc){
						if(boc.status == 'CURRENTBOWLER'){
							option = document.createElement('option');
							option.value = boc.player.playerId;
							option.text = boc.player.full_name;
							select.appendChild(option);
						}
					});
					
					if(inn.bowlingTeamId == session_match.setup.homeTeamId){
						session_match.setup.homeSquad.forEach(function(hs){
							option = document.createElement('option');
							option.value = hs.playerId;
							option.text = hs.full_name;
							select.appendChild(option);
						});
						session_match.setup.homeOtherSquad.forEach(function(hos){
							option = document.createElement('option');
							option.value = hos.playerId;
							option.text = hos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}else {
						session_match.setup.awaySquad.forEach(function(as){
							option = document.createElement('option');
							option.value = as.playerId;
							option.text = as.full_name;
							select.appendChild(option);
						});
						session_match.setup.awayOtherSquad.forEach(function(aos){
							option = document.createElement('option');
							option.value = aos.playerId;
							option.text = aos.full_name  + ' (OTHER)';
							select.appendChild(option);
						});
					}
				}
			});

			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			//removeSelectDuplicates(select.id);
			setDropdownOptionToSelectOptionArray($(select),0);
			removeSelectDuplicates(select.id);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectProfile';
			select.name = select.id;
			
			switch($('#selected_broadcaster').val().toUpperCase()){
				case 'T20_MUMBAI':
					[ { value: 'T20 MUMBAI', text: 'T20 MUMBAI' },
					  { value: 'DT20', text: 'T20' },
					  { value: 'IT20', text: 'T20I' },
					  { value: 'MT20 SEASON 3', text: 'MT20 SEASON 3' },
					  { value: 'IPL 2026', text: 'IPL 2026' },
					  { value: 'IPL', text: 'IPL' },
					  { value: 'WPL', text: 'WPL' }
					].forEach(({ value, text }) => {
						  option = document.createElement('option');
						  option.value = value;
						  option.text = text;
						  select.appendChild(option);
					});
					break;
				case 'LEGENDS-90':
					[ { value: 'DT20', text: 'T20' },
					  { value: 'IT20', text: 'T20i' }
					].forEach(({ value, text }) => {
						  option = document.createElement('option');
						  option.value = value;
						  option.text = text;
						  select.appendChild(option);
					});
					break;
				case 'NPL': case 'MPL': case 'APL': case 'VIDARBHA':
					if($('#selected_broadcaster').val().toUpperCase()=='MPL'){
						option = document.createElement('option');
						option.value = 'KCL';
						option.text = 'KCL';
						select.appendChild(option);	
					}else if($('#selected_broadcaster').val().toUpperCase()=='NPL'){
						option = document.createElement('option');
						option.value = 'NPL S1';
						option.text = 'NPL S1';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'NPL_CAREER';
						option.text = 'NPL CAREER';
						select.appendChild(option);
					}
					else if($('#selected_broadcaster').val().toUpperCase()=='APL'){
						option = document.createElement('option');
						option.value = 'APL_CAREER';
						option.text = 'APL CAREER';
						select.appendChild(option);
					}else if($('#selected_broadcaster').val().toUpperCase()=='VIDARBHA'){
						option = document.createElement('option');
						option.value = 'VIDARBHA_CAREER';
						option.text = 'VIDARBHA CAREER';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'IPL';
						option.text = 'IPL';
						select.appendChild(option);
					}
					
					option = document.createElement('option');
					option.value = 'DT20';
					option.text = 'DT20';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'IT20';
					option.text = 'IT20';
					select.appendChild(option);
					break;
				case 'ISPL':
					option = document.createElement('option');
					option.value = 'ISPL S1';
					option.text = 'ISPL S1';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'ISPL S2';
					option.text = 'ISPL S2';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'ISPL_CAREER';
					option.text = 'ISPL CAREER';
					select.appendChild(option);
					break;
				case 'BENGAL-T20':
					
					option = document.createElement('option');
					option.value = 'DT20';
					option.text = 'DT20';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'KAPL';
					option.text = 'KAPL';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'IPL';
					option.text = 'IPL';
					select.appendChild(option);
				break;
				default:
					option = document.createElement('option');
					option.value = 'U19ODI';
					option.text = 'U19 ODI';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'LIST A';
					option.text = 'LIST A';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'ACCU19';
					option.text = 'ACC U19';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'SA TRI-NATION 2023-24';
					option.text = 'SA TRI-NATION';
					select.appendChild(option);
			}
			
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 1)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),1);
			cellCount = cellCount + 1
			
			switch(whatToProcess){
			case 'Control_e':
				switch($('#selected_broadcaster').val().toUpperCase()){
					case 'ICC-U19-2023': 
						select = document.createElement('select');
						select.id = 'selectStatType';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = '0';
						option.text = '';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '1';
						option.text = 'Matches';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '2';
						option.text = 'Wickets';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '3';
						option.text = '3WI';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '4';
						option.text = '5WI';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '5';
						option.text = 'Average';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '6';
						option.text = 'Economy';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '7';
						option.text = 'Best Fig';
						select.appendChild(option);
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),2);
						cellCount = cellCount + 1
					break;
					case 'NPL':  case 'MPL': case 'APL':
						select = document.createElement('select');
						select.id = 'selectStatType';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = '0';
						option.text = '';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '1';
						option.text = 'Matches';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '2';
						option.text = 'Wickets';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '3';
						option.text = 'Economy';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '4';
						option.text = '3WI';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = '7';
						option.text = 'Best Fig';
						select.appendChild(option);
						
						select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 2)");
						row.insertCell(cellCount).appendChild(select);
						setDropdownOptionToSelectOptionArray($(select),2);
						cellCount = cellCount + 1
					break;
					}
					break;
			}
			break;
			
		case 'k': case 'Shift_Y':
			select = document.createElement('select');
			select.style = 'width:400px';
			select.id = 'selectBugdb';
			select.name = select.id;
			
			dataToProcess.forEach(function(bug){
				option = document.createElement('option');
				option.value = bug.bugId;
				option.text = bug.prompt;
				select.appendChild(option);
			});
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			$('#selectBugdb').select2();
			break;
		case 'Control_Shift_J':
			select = document.createElement('select');
			select.style = 'width:400px';
			select.id = 'selectPerformanceBugdb';
			select.name = select.id;
			
			dataToProcess.forEach(function(bug){
				option = document.createElement('option');
				option.value = bug.bugId;
				option.text = bug.prompt;
				select.appendChild(option);
			});
			select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, 0)");
			row.insertCell(cellCount).appendChild(select);
			setDropdownOptionToSelectOptionArray($(select),0);
			cellCount = cellCount + 1;
			$('#selectPerformanceBugdb').select2();
			break;
		}
		switch($('#selected_broadcaster').val().toUpperCase()){
			case 'T20_MUMBAI':
			switch(whatToProcess){
				case "F5":case "F9":case "l":case "Shift_F5":case "Shift_F9":case "F6":case "Control_F6":case "Shift_F6":
				case "F7":case "F11": case "F8": case "Alt_F8":case "q":case "Control_F5":case "Control_F9":case "Alt_F1": case "Alt_F2": 
					select = document.createElement('select');
					select.id = 'selectPhoto';
					select.name = select.id;
					
					['without', 'with'].forEach(item => {
					    option = document.createElement('option');
					    option.value = item+"_photo";
					    option.text = item.toUpperCase() + ' PHOTO';
					    select.appendChild(option);
					});
					select.setAttribute('onchange',"setDropdownOptionToSelectOptionArray(this, selected_options.length-2)");
					row.insertCell(cellCount).appendChild(select);
					setDropdownOptionToSelectOptionArray($(select),selected_options.length-2);
					cellCount = cellCount + 1;
				break;
			}
			break;
		}
		if(whatToProcess == 'Shift_I' && $('#selected_broadcaster').val().toUpperCase() != 'MPL'){
			option = document.createElement('input');
			option.type = 'button';
			option.name = 'change_on';
			option.value = 'Change On';
		    option.id = option.name;
		    option.setAttribute('onclick','processUserSelection(this)');
		    
		    div = document.createElement('div');
		    div.append(option);
		    
		    option = document.createElement('input');
			option.type = 'hidden';
			option.name = 'key_press_hidden_input';
			option.id = option.name;
			option.value = whatToProcess;
	
		    div.append(option);
		    
		    row.insertCell(cellCount).appendChild(div);
	    	cellCount = cellCount + 1;
		}
		
		if((whatToProcess == 'Control_Shift_U' || whatToProcess == 'Control_Shift_V') && $('#selected_broadcaster').val().toUpperCase()== 'T20_MUMBAI'){
			option = document.createElement('input');
			option.type = 'button';
			option.name = 'pop_up_change_on';
			option.value = 'Change On';
		    option.id = option.name;
		    option.setAttribute('onclick','processUserSelection(this)');
		    
		    div = document.createElement('div');
		    div.append(option);
		    row.insertCell(cellCount).appendChild(div);
	    	cellCount = cellCount + 1;
		}
		
		if(whatToProcess == 'Shift_T' && $('#selected_broadcaster').val().toUpperCase()== 'T20_MUMBAI'){
			option = document.createElement('input');
			option.type = 'button';
			option.name = 'change_on';
			option.value = 'Change On';
		    option.id = option.name;
		    option.setAttribute('onclick','processUserSelection(this)');
		    
		    div = document.createElement('div');
		    div.append(option);
		    row.insertCell(cellCount).appendChild(div);
	    	cellCount = cellCount + 1;
		}
		
		if(whatToProcess == 'Control_b' && $('#selected_broadcaster').val().toUpperCase()== 'T20_MUMBAI'){
			option = document.createElement('input');
			option.type = 'button';
			option.name = 'change_on_profile';
			option.value = 'Change On Profile';
		    option.id = option.name;
		    option.setAttribute('onclick','processUserSelection(this)');
		    
		    div = document.createElement('div');
		    div.append(option);
		    row.insertCell(cellCount).appendChild(div);
	    	cellCount = cellCount + 1;
		}
		
		if(whatToProcess == 'Alt_3' || whatToProcess == 'Alt_4'){
			//if($('#selected_broadcaster').val() != 'ISPL'){
				option = document.createElement('input');
				option.type = 'button';
				option.name = 'checkPlayerData';
				option.value = 'Preview';
			    option.id = option.name;
			    option.setAttribute('onclick','processUserSelection(this)');
			    
			    div = document.createElement('div');
			    div.append(option);
			    row.insertCell(cellCount).appendChild(div);
		    	cellCount = cellCount + 1;
			//}
		}
		
		/*switch($('#selected_broadcaster').val().toUpperCase()){
			case 'T20_MUMBAI':
			if(whatToProcess == 'F7' || whatToProcess == 'F11'){
				option = document.createElement('input');
				option.type = 'button';
				option.name = 'L3_Player_ChangeON';
				option.value = 'Player ChangeON';
			    option.id = option.name;
			    option.setAttribute('onclick','processUserSelection(this)');
			    
			    div = document.createElement('div');
			    div.append(option);
			    row.insertCell(cellCount).appendChild(div);
		    	cellCount = cellCount + 1;
			}
			break;
		}*/
		switch($('#selected_broadcaster').val().toUpperCase()){
			case 'NPL':  case 'MPL': case 'APL':
				if(whatToProcess == 'Control_d' || whatToProcess == 'Control_e' || whatToProcess == 'Shift_P' || whatToProcess == 'Shift_Q'){
					option = document.createElement('input');
					option.type = 'button';
					option.name = 'highlightProfile';
					option.value = 'Highlight Profile';
				    option.id = option.name;
				    option.setAttribute('onclick','processUserSelection(this)');
				    
				    div = document.createElement('div');
				    div.append(option);
				    row.insertCell(cellCount).appendChild(div);
			    	cellCount = cellCount + 1;
				}
			
				if(whatToProcess == 'z' || whatToProcess == 'x' || whatToProcess == 'c' || whatToProcess == 'v' || 
					whatToProcess == 'Control_z' || whatToProcess == 'Control_x' || whatToProcess == 'Control_Shift_Z' || whatToProcess == 'Control_Shift_Y' || 
					whatToProcess == 'Control_Shift_F8'){
					option = document.createElement('input');
					option.type = 'button';
					option.name = 'highlightLeader';
					option.value = 'Player ChangeON';
				    option.id = option.name;
				    option.setAttribute('onclick','processUserSelection(this)');
				    
				    div = document.createElement('div');
				    div.append(option);
				    row.insertCell(cellCount).appendChild(div);
			    	cellCount = cellCount + 1;
				}
			break;
		}
		
		switch(whatToProcess){
			case 'Shift_C': case 'Control_Shift_Q': case 'Control_Shift_O': case 'Control_Shift_J':case "Control_Shift_F8":
			case 'Control_m': case 'F4': case 'F5': case 'F6': case 'Alt_w': case 'Control_j': case 'F8': case 'F9': case 'F10': case 'F7': case 'F11':
			case 'Control_F5': case 'Control_F9': case 'Shift_T': case 'u': case 'p': case 'Control_p': case 'Control_d': case 'Control_e': case 'z': 
			case 'x': case 'c': case 'v': case 'Shift_F11': case 'Control_y': case 'Alt_F8': case 'Alt_F1': case 'Alt_F2': case 'Shift_K': case 'Shift_O': 
			case 'k': case 'Shift_Y': case 'g': case 'y': case 'Shift_F5': case 'Shift_F9': case 'Control_h': case 'Control_g': case 'q': case 'j': case 'Shift_F6': case 'Shift_F8':
			case 'Control_s':  case 'Control_f': case 'Alt_F12': case 'l': case 'Shift_E': case 'Alt_F9':  
			case 'F12': case 'Alt_1': case 'Alt_2': case 'Alt_3': case 'Alt_4': case 'Alt_5': case 'Alt_6': case 'Alt_7': case 'Alt_8': case 'Alt_9': case 'Alt_0':
			case 'Alt_m': case 'Alt_n': case 'Control_b': case 'Alt_p': case 'Alt_F10': case 'Alt_d': case 'Shift_F4': case 'Alt_a': case 'Alt_s': case 'Shift_P': 
			case 'Shift_Q': case 'Alt_z': case 'Control_c': case 'Control_v': case 'Control_z': case 'Control_x': case 'Alt_q': case 'Shift_F': case 'Alt_F6': 
			case 'Shift_A': case 'Shift_R': case 'Control_Shift_F1': case 'Control_Shift_D': case 'Alt_Shift_Z': case 'Control_Shift_F7': case 'Control_Shift_F2':
			case 'Alt_c': case 'Control_F12': case 'Shift_F12': case 'F1': case 'F2': case 'Shift_F7': case 'Control_Shift_F9': case 'Alt_Shift_C': case 'Control_Shift_L':
			case 'Shift_Z': case 'Shift_X': case 'Control_i': case 'Control_Shift_E': case 'Control_Shift_F': case 'Control_Shift_P': case 'Shift_I': 
			case 'Control_F11': case 'Control_Shift_M': case 'Alt_Shift_R': case 'Control_Shift_U': case 'Control_Shift_V': case 'Control_4': case 'Control_Shift_F4':
			case 'Control_Shift_Z': case 'Control_Shift_Y': case 'Alt_Shift_W':  case 'Control_Shift_X':case 'Control_u': case 'Shift_W':case "Alt_b":
			case "Shift_G": case 'Control_5': case 'Control_F8': case 'Control_7': case 'Control_8': case 'Control_Shift_F11': case 'Alt_/': case 'Control_9':
			case "Alt_Shift_B":	case 'Control_0': case "Shift_@": case "Shift_$": case "Control_Shift_@": case "Control_Alt_5": case "Alt_Shift_@": case "Control_Alt_6":
			case "Control_Alt_9": case "Control_Alt_0":	case 'r': case "Alt_Shift_N": case "Alt_Shift_M":case 'Alt_Shift_Q': case 'Alt_Shift_P': case 'Alt_Shift_K': 
			case 'Alt_Shift_X': case 'Alt_Shift_T': case 'Alt_Shift_V':case 'Control_Shift_F5':case "Alt_Shift_F5":case 'Alt_Shift_F4':case 'Alt_Shift_F6':case 'Alt_Shift_F7':
			case 'Shift_L': case 'h': case 'Control_Shift_(': case 'Shift_M': case 'Control_Alt_7': case 'Control_Alt_8': case 'Alt_x': case 'Alt_f': case 'Control_F7':

				option = document.createElement('input');
				option.type = 'button';
				option.name = 'populate_btn';
				option.value = 'Populate Data';
			    option.id = option.name;
			    option.setAttribute('onclick','processUserSelection(this)');
			    
			    div = document.createElement('div');
			    div.append(option);
			    
		
				option = document.createElement('input');
				option.type = 'button';
				option.name = 'cancel_graphics_btn';
				option.id = option.name;
				option.value = 'Cancel';
				option.setAttribute('onclick','processUserSelection(this)');
			    div.append(option);
		
				option = document.createElement('input');
				option.type = 'hidden';
				option.name = 'key_press_hidden_input';
				option.id = option.name;
				option.value = whatToProcess;
		
			    div.append(option);
			    
			    row.insertCell(cellCount).appendChild(div);
			    cellCount = cellCount + 1;
			    break;
		}
		
		document.getElementById('select_graphic_options_div').style.display = '';
		break;
	}
}

function setPlayerDropdown(dataToProcess) {
 	const playerCell = document.getElementById('Player');
 	playerCell.innerHTML = ''; 	
    const playerSelect = document.createElement('select');
    playerSelect.id = 'playerDropdown';
    
    dataToProcess.forEach(player => {
        const option = document.createElement('option');
        option.value = player.id;  
        option.text = player.name; 
        playerSelect.appendChild(option);
      });

	playerSelect.selectedIndex = 0;
    $(playerSelect).on('change', function() {
		setDropdownOptionToSelectOptionArray($(this), 3);
    });        
    playerCell.appendChild(playerSelect);
	setDropdownOptionToSelectOptionArray($(this), 3);
	$(playerSelect).trigger('change');
}
function filterPlayerDropdown(typeValue) {
    var selectPlayer = document.getElementById('selectPlayer');
    if (!selectPlayer) return;

    var options = selectPlayer.options;

    if (typeValue === 'MVP_LB_ALL_PLAYER') {
        // Show TOP 5 only
        for (var i = 0; i < options.length; i++) {
            options[i].style.display = (i < 5) ? '' : 'none';
        }
    } else {
        // Show FULL list
        for (var i = 0; i < options.length; i++) {
            options[i].style.display = '';
        }
    }
}

function ChallengeScoreRUNS(match, inn) {
    let InningRun = 0;
    
    for (var i = match.eventFile.events.length - 1; i >= 0; i--) {
	  if (match.eventFile.events[i].eventExtra != null && match.eventFile.events[i].eventExtra.toUpperCase() == 'CHALLENGE' && 
	  		match.eventFile.events[i].eventInningNumber == inn.inningNumber && inn.totalOvers >= parseInt(match.eventFile.events[i].eventOverNo + 1)) {
		if(inn.specialRuns != null) {
			if (inn.specialRuns.startsWith('+')) {
				InningRun = parseInt(inn.totalRuns + parseInt(inn.specialRuns.replace('+', '')));
			}else if (inn.specialRuns.startsWith('-')) {
				InningRun = parseInt(inn.totalRuns - parseInt(inn.specialRuns.replace('-', '')));
			}
		}else {
			InningRun = parseInt(inn.totalRuns);
		}
    	break;
	  }else {
		InningRun = parseInt(inn.totalRuns);
		}
	} 
    return InningRun;
}
