<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Initialise Screen</title>

  <script type="text/javascript" src="<c:url value="/webjars/jquery/3.7.1/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/5.3.2/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/5.3.2/css/bootstrap.min.css"/>"/>  
  <link href="<c:url value="/webjars/font-awesome/6.5.1/css/all.css"/>" rel="stylesheet">
  
	<style type="text/css">
	/* Card container */
	.card {
	    background-color: #ffffff;
	    border: 1px solid #ccc;
	    border-radius: 6px;
	    padding: 3px 40px;
	    font-weight:bold;
	    margin-bottom: 20px;
	    box-shadow: 0 0 10px rgba(0,0,0,0.05);
	}
	
	/* Card header bar */
	.card-header {
	    background-color: #2e008b;
	    color: white;
	    font-weight: 900;
	    padding: 4px 10px;
	    font-size: clamp(0.8rem, 2vw, 1.5rem);
	    border-radius: 6px 6px 0 0;
	    margin: -30px -40px 30px;
	}
	body{
	 font-size: clamp(0.8rem, 2vw, 0.2rem);
	 font-weight: 850;
	 text-transform: uppercase;
	 height: auto;
     overflow: auto;
     background: url('<c:url value="/resources/Images/bg.png"/>') no-repeat center center fixed;
	 background-size: cover;
	}
	/* Labels */
	label {
	    font-weight: bold;
	    font-size: 1rem;
	}
	
	/* Inputs and selects */
	input[type="text"],
	input[type="number"],
	select button{
	    font-size: clamp(0.8rem, 2vw, 1rem);
	    font-weight:bold;
	}
	.btn{
	    font-size: clamp(0.8rem, 2vw, 1.5rem);
	    font-weight:bold;
	}
	.mb-0 {
      font-weight: bold;
      font-family: 'Arial Black', sans-serif;
      text-transform: uppercase;
      font-size: clamp(0.8rem, 2vw, 2.5rem);
      -webkit-background-clip: text;
      color: transparent;
	  text-shadow: 
	    2px 2px 0 white,   /* yellow shadow */
	    4px 4px 0 #33ccff,   /* blue shadow */
	    6px 6px 0 #000000;   /* black outer shadow */
    }
    .header-container {
	position: fixed;
 	z-index: 1000;
    background-color: #0080FE;
    color: white;
    padding: 1px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: fixed;
    top: 0px;
    left: 0;
    width: calc(100% - 78px);
    z-index: 1000; /* Ensure header is above other content */
    margin-left: 34px;
    box-shadow: 10px 4px 4px #9AA2A2;
  }
  .header-container img {
    max-width: 60px;
    max-height: 60px;
    width: calc(100% - 40px);
    margin-right: 10px;
    top: 0;
    bottom: 0;
    left: 0;
    
  }
  /* Styling for individual options inside the dropdown */
	select option {
	  padding: 2px; 
	  font-size: 1rem;
	  text-transform: uppercase;
	  font-weight:bold;
	}
	
	/* Optional: Customize the dropdown arrow */
	select::-ms-expand {
	  display: none; /* Hide default dropdown arrow in Internet Explorer */
	}
	
	select {
	  appearance: none; /* Remove default dropdown styling */
	  -webkit-appearance: none;
	  -moz-appearance: none;
	  font-weight:bold;
	  text-transform: uppercase;
	  background-position: right center;
	  background-repeat: no-repeat;
	  padding-right: 30px; /* Space for custom arrow */
	}
  .header-container h2 {
	  margin: 0;
	  font-family: 'Arial Black', sans-serif;
	  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
	  font-size:  clamp(1.6em, 4vw, 3em); /* scales with screen size */
	  text-transform: uppercase;
	  letter-spacing: 3px;
	  white-space: nowrap;
	  margin-left: auto;
	  text-align: center;
	  margin-right: calc(30% + 2vw); /* responsive margin */
	}
    .content {
	  position: relative;
	  z-index: 1; /* must be lower than header */
	  margin-top: 70px; /* enough to sit below the fixed header */
	  height: calc(100vh - 70px); /* Fill remaining viewport without scroll */
	  overflow: auto;
	}
	
	/* Style for input[type="button"] */
	.btn{
	  background-color: #007bff;
	  color: white;
	  padding: 10px 18px;
	  font-size:  clamp(1em, 2vw,0.8em);
	  font-weight:bold;
	  border: none;
	  text-transform: uppercase;
	  border-radius: 6px;
	  cursor: pointer;
	  transition: background-color 0.3s ease;
	  margin-right: 10px; /* Add space between buttons */
	  box-shadow: 0 5px 0 #2E8B57, 0 10px 15px rgba(0, 0, 0, 0.1); /* Initial shadow for 3D effect */
  	  transition: all 0.2s ease;
	  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	}
	
	/* Hover effect for input[type="button"] */
	.btn:hover{
	  background-color: #0056b3;
	  box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1), 0 3px 0 #2E8B57; /* Shadow changes when hovered */
  	  transform: translateY(-3px); /* Lift the button upwards */
	}
	/* Active (clicked) button effect */
	.btn:active {
	  box-shadow: 0 3px 0 #2E8B57, 0 6px 5px rgba(0, 0, 0, 0.15); /* Pressed effect */
	  transform: translateY(2px); /* Button moves down slightly to simulate pressing */
	}
	/* Disabled input[type="button"] styling */
	.btn:disabled{
	  background-color: #d3d3d3;
	  color: #a1a1a1;
	  cursor: not-allowed;
	  box-shadow: none; /* No shadow when disabled */
	}
	.card-body{
		font-size:  clamp(1em, 2vw,0.8em);
	}
	
	</style>	
</head>
<body>
<div class="header-container">
    <img src="<c:url value='/resources/Images/Design.jpg'/>" alt="Logo">
    <h2>DESIGN ON A DIME</h2>
  </div>
<form:form name="initialise_form" method="POST" action="output">
<div class="content py-5">
  <div class="container">
	<div class="row">
	 <div class="col-auto col-lg-12">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
           <div class="card-header">
             <h1 class="mb-0"><center> Initialise </center></h1>
           </div>
          <div class="card-body">
            <div id="initialise_div" style="display:none;">
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			    <label for="select_configuration_file" class="col-sm-4 col-form-label text-left">Select Configuration </label>
			    <div class="col-sm-6 col-md-6">
			      <select id="select_configuration_file" name="select_configuration_file" 
			      		class="brower-default custom-select custom-select-sm" 
			      		onchange="processCricketProcedures('GET-CONFIG-DATA')">
			          	<option value=""></option>
						<c:forEach items = "${configuration_files}" var = "config">
				          	<option value="${config.name}">${config.name}</option>
						</c:forEach>
			      </select>
			    </div>
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			    <label for="configuration_file_name" class="col-sm-4 col-form-label text-left">Configuration File Name </label>
			    <div class="col-sm-6 col-md-6">
		             <input type="text" id="configuration_file_name" name="configuration_file_name"
		             	class="form-control form-control-sm floatlabel"></input>
			    </div>
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			    <label for="select_type" class="col-sm-4 col-form-label text-left">Select Past Matches </label>
			    <div class="col-sm-6 col-md-6">
			      <select id="select_type" name="select_type" class="browser-default custom-select custom-select-sm">
			      		<option value=""></option>
			      		<option value="IT20">IT20</option>
			      		<option value="ODI">ODI</option>
			      		<option value="TEST">TEST</option>
			      </select>
			    </div>
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			    <label for="select_cricket_matches" class="col-sm-4 col-form-label text-left">Select Cricket Match </label>
			    <div class="col-sm-6 col-md-6">
			      <select id="select_cricket_matches" name="select_cricket_matches" 
			      		class="brower-default custom-select custom-select-sm">
						<c:forEach items = "${match_files}" var = "match">
				          	<option value="${match.name}">${match.name}</option>
						</c:forEach>
			      </select>
			    </div>
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			    <label for="select_broadcaster" class="col-sm-4 col-form-label text-left">Select Broadcaster </label>
			    <div class="col-sm-6 col-md-6">
			      <select id="select_broadcaster" name="select_broadcaster" class="browser-default custom-select custom-select-sm">
			      		<option value="MPL">MPL</option>
			      		<option value="APL">APL</option>
			      		<option value="ISPL">ISPL</option>
			      		<option value="LEGENDS-90">LEGENDS 90</option>
			      		<option value="T20_MUMBAI">T20 MUMBAI</option>
			      		<option value="NPL">NPL 2024</option>
			      		<option value="DOAD-BILATERAL">AFG v BAN</option>	
			      		<option value="BENGAL-T20">MAHARAJA T20</option>
			      		<option value="ICC-U19-2023">ICC Under-19 WC 2023</option>
			      </select>
			    </div>
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			    <label for="select_second_broadcaster" class="col-sm-4 col-form-label text-left">Select 2nd Broadcaster </label>
			    <div class="col-sm-6 col-md-6">
			      <select id="select_second_broadcaster" name="select_second_broadcaster" 
			      		class="browser-default custom-select custom-select-sm" onchange="processUserSelection(this)">
			      		<option value=""></option>
			      		<option value="quidich">Quidich</option>
			      </select>
			    </div>
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			    <label for="generateInteractiveFile" class="col-sm-4 col-form-label text-left">Interactive File </label>
			    <div class="col-sm-6 col-md-6">
			      <select id="generateInteractiveFile" name="generateInteractiveFile" class="browser-default custom-select custom-select-sm">
			          <option value="yes">Yes</option>
			          <option value="no">No</option>
			      </select>
			    </div>
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			  	<label for="previewOnOrOff" class="col-sm-4 col-form-label text-left">PREVIEW</label>	
			  	<div class="col-sm-6 col-md-6">
				  	<select id="previewOnOrOff" name="previewOnOrOff" 
				      		class="browser-default custom-select custom-select-sm">
			          <option value="WITH_PREVIEW">PREVIEW</option>
			          <option value="WITHOUT_PREVIEW">NO PREVIEW</option>
				     </select>
			  	</div>	
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			  	<label for="selectInfobar" class="col-sm-4 col-form-label text-left">SELECT INFOBAR</label>	
			  	<div class="col-sm-6 col-md-6">
				  	<select id="selectInfobar" name="selectInfobar" class="browser-default custom-select custom-select-sm">
			          <option value="TRADITIONAL_INFOBAR">TRADITIONAL INFOBAR</option>
			          <option value="LOF_INFOBAR">LOF INFOBAR</option>
				     </select>
			  	</div>	
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:0.8px;">
			    <label for="Category" class="col-sm-4 col-form-label text-left">Select Category </label>
			    <div class="col-sm-6 col-md-6">
			      <select id="Category" name="Category" class="browser-default custom-select custom-select-sm"
			      		onchange="processCricketProcedures('GET-CATEGORY-DATA')">
			      		<option value=" "> </option>
			      		<option value="MEN">MEN</option>
			      		<option value="WOMEN">WOMEN</option>
			      </select>
			    </div>
			  </div>
			<div class="row">
			<table class="table table-bordered table-responsive">
			  <tbody>
			  	<tr>
			      <td>
				    <label for="qtIPAddress" class="col-sm-4 col-form-label text-left">QT IP</label>				    
		             <input type="text" id="qtIPAddress" name="qtIPAddress" value="${session_configuration.qtIpAddress}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			      <td>
				    <label for="qtPortNumber" class="col-sm-4 col-form-label text-left">QT Port</label>				    
		             <input type="text" id="qtPortNumber" name="qtPortNumber" value="${session_configuration.qtPortNumber}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			    </tr>
			    <tr>
			      <td>
				    <label for="vizIPAddress" class="col-sm-4 col-form-label text-left">1st IP</label>				    
		             <input type="text" id="vizIPAddress" name="vizIPAddress" value="${session_configuration.primaryIpAddress}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			      <td>
				    <label for="vizPortNumber" class="col-sm-4 col-form-label text-left">1st Port</label>				    
		             <input type="text" id="vizPortNumber" name="vizPortNumber" value="${session_configuration.primaryPortNumber}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			      <td>
				    <label for="primaryVariousOptions" class="col-sm-4 col-form-label text-left">Options</label>			    
				      <select id="primaryVariousOptions" name="primaryVariousOptions" 
				      		class="browser-default custom-select custom-select-sm">
				          <option value="FULL_FRAMER_OVERLAYS">FFs & Overlays both</option>
				          <option value="OVERLAYS">Overlays Only</option>
				      </select>
			      </td>
			      <td>
				    <label for="vizLanguage" class="col-sm-4 col-form-label text-left">1st Language</label>			    
				      <select id="vizLanguage" name="vizLanguage" 
				      		class="browser-default custom-select custom-select-sm">
				          <option value="english">English</option>
				          <option value="hindi">Hindi</option>
				          <option value="telugu">Telugu</option>
				          <option value="tamil">Tamil</option>
				      </select>
			      </td>
			    </tr>
			    <tr>
			      <td>
				    <label for="vizSecondaryIPAddress" class="col-sm-4 col-form-label text-left">2nd IP</label>				    
		             <input type="text" id="vizSecondaryIPAddress" name="vizSecondaryIPAddress" value="${session_configuration.secondaryIpAddress}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			      <td>
				    <label for="vizSecondaryPortNumber" class="col-sm-4 col-form-label text-left">2nd Port</label>				    
		             <input type="text" id="vizSecondaryPortNumber" name="vizSecondaryPortNumber" value="${session_configuration.secondaryPortNumber}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			      <td>
				    <label for="vizSecondaryScene" class="col-sm-4 col-form-label text-left">2nd Scene</label>				    
		             <input type="text" id="vizSecondaryScene" name="vizSecondaryScene" value="${session_configuration.secondaryScene}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			      <td>
				    <label for="vizSecondaryLanguage" class="col-sm-4 col-form-label text-left">2nd Language</label>			    
				      <select id="vizSecondaryLanguage" name="vizSecondaryLanguage" 
				      		class="browser-default custom-select custom-select-sm">
				          <option value="english">English</option>
				          <option value="hindi">Hindi</option>
				          <option value="telugu">Telugu</option>
				          <option value="tamil">Tamil</option>
				      </select>
			      </td>
			    </tr>
			    <tr>
			      <td>
				    <label for="vizTertiaryIPAddress" class="col-sm-4 col-form-label text-left">3rd IP</label>				    
		             <input type="text" id="vizTertiaryIPAddress" name="vizTertiaryIPAddress" value="${session_configuration.tertiaryIpAddress}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			      <td>
				    <label for="vizTertiaryPortNumber" class="col-sm-4 col-form-label text-left">3rd Port</label>				    
		             <input type="text" id="vizTertiaryPortNumber" name="vizTertiaryPortNumber" value="${session_configuration.tertiaryPortNumber}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			      <td>
				    <label for="vizTertiaryScene" class="col-sm-4 col-form-label text-left">3rd Scene</label>				    
		             <input type="text" id="vizTertiaryScene" name="vizTertiaryScene" value="${session_configuration.tertiaryScene}"
		             	class="form-control form-control-sm floatlabel"></input>
			      </td>
			      <td>
				    <label for="vizTertiaryLanguage" class="col-sm-4 col-form-label text-left">3rd Language</label>				    
				      <select id="vizTertiaryLanguage" name="vizTertiaryLanguage" 
				      		class="browser-default custom-select custom-select-sm">
				          <option value="english">English</option>
				          <option value="hindi">Hindi</option>
				          <option value="telugu">Telugu</option>
				          <option value="tamil">Tamil</option>
				      </select>
			      </td>
			    </tr>
			  </tbody>
		    </table>
		    </div>		  
		    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  		name="load_scene_btn" id="load_scene_btn" onclick="processUserSelection(this)"> Load Scene</button>
	       </div>
	    </div>
       </div>
    </div>
  </div>
</div>
</form:form>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  $(document).ready(function() {
    $('#select_type').on('change', function() {
      var type = $(this).val();
      var $matchDropdown = $('#select_cricket_matches');

      // Clear existing options
      $matchDropdown.empty().append('<option>Loading...</option>');

      $.ajax({
        url: 'getMatchesByType',
        type: 'GET',
        data: { select_type: type },  // send empty string if blank
        success: function(data) {
          $matchDropdown.empty().append('<option value="">Select Match</option>');
          $.each(data, function(index, matchName) {
            $matchDropdown.append('<option value="' + matchName + '">' + matchName + '</option>');
          });

          if (data.length === 0) {
            $matchDropdown.append('<option disabled>No matches found</option>');
          }
        },
        error: function() {
          $matchDropdown.empty().append('<option disabled>Error loading matches</option>');
        }
      });
    });
  });
</script>
</body>
</html>