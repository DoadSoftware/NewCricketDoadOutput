package com.cricket.containers;

import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FairPlayTable")
@XmlAccessorType(XmlAccessType.FIELD)
public class FairPlayTable {
	
	@XmlElementWrapper(name = "FairPlayTeams")
	@XmlElement(name = "FairPlayTeam")
	private List<FairPlayTeam> FairPlayTeams;

	public List<FairPlayTeam> getFairPlayTeams() {
		return FairPlayTeams;
	}

	public void setFairPlayTeams(List<FairPlayTeam> fairPlayTeams) {
		FairPlayTeams = fairPlayTeams;
	}

}

