package net;

import java.util.HashMap;

import controller.game.GameController;

public class CoopManager {
	
	//Э������(ӵ��)��,����Э��������(����������)
	static HashMap<String,HashMap<String, perServer>> coopList = new HashMap<String,HashMap<String, perServer>>();
	
	static HashMap<String,HashMap<String, perServer>> pkList = new HashMap<String,HashMap<String, perServer>>();

	static HashMap<String,GameController> coopController =new HashMap<String,GameController>();
	
	
	
}
