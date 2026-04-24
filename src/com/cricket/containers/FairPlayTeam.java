package com.cricket.containers;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FairPlayTeam")
@XmlAccessorType(XmlAccessType.FIELD)
public class FairPlayTeam {

	@XmlElement(name="TeamName")
	private String TeamName;
	
	@XmlElement(name="Matches")
	private int Matches;
	
	@XmlElement(name="Average")
	private double Average;
	
	@XmlElement(name="Points")
	private int Points;
	

	public String getTeamName() {
		return TeamName;
	}

	public void setTeamName(String teamName) {
		TeamName = teamName;
	}

	public int getMatches() {
		return Matches;
	}

	public void setMatches(int matches) {
		Matches = matches;
	}

	public double getAverage() {
		return Average;
	}

	public void setAverage(double average) {
		Average = average;
	}

	public int getPoints() {
		return Points;
	}

	public void setPoints(int points) {
		Points = points;
	}
	
}
