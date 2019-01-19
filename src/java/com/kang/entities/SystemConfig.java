package com.kang.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.kang.util.NetWorkUtil;

public class SystemConfig implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String[] versusValues = {"networkModel", "localModel"};
	private int versusIndex = 1;
	
	private String ipAddr = NetWorkUtil.getIPAddr();
	
	private String[] languageValues = {"chinese", "english", "autoLanguage"};
	private int languageIndex = 2;
	
	public String[] getVersusValues() {
		return versusValues;
	}

	public int getVersusIndex() {
		return versusIndex;
	}

	public String[] getLanguageValues() {
		return languageValues;
	}

	public int getLanguageIndex() {
		return languageIndex;
	}

	public void setVersus(String versus) {
		List<String> versusList = Arrays.asList(versusValues);
		if (versusList.contains(versus)) {
			this.versusIndex = versusList.indexOf(versus);
		}
	}
	public String getVersus() {
		return versusValues[versusIndex];
	}
	public void setLanguage(String language) {
		List<String> languageList = Arrays.asList(languageValues);
		if (languageList.contains(language)) {
			this.languageIndex = languageList.indexOf(language);
		}
	}
	public String getLanguage() {
		return languageValues[languageIndex];
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	
}
