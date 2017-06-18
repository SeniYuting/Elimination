package net;

import java.util.HashMap;

import controller.game.GameController;

public class CoopManager {
	
	//协作发起(拥有)者,所有协作参与者(包括发起者)
	static HashMap<String,HashMap<String, perServer>> coopList = new HashMap<String,HashMap<String, perServer>>();
	
	static HashMap<String,HashMap<String, perServer>> pkList = new HashMap<String,HashMap<String, perServer>>();

	static HashMap<String,GameController> coopController =new HashMap<String,GameController>();
	
	
	
}
