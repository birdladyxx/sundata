package com.springinaction.knight;

import java.io.PrintStream;

/**
 * 击杀恶龙探险
 * @author qinzh
 *
 */
public class SlayDragonQuest implements Quest {

	private PrintStream stream;
	
	
	public SlayDragonQuest(PrintStream stream) {
		this.stream = stream;
	}


	@Override
	public void embark() {
		stream.println("Embarking on quest to slay the dragon!");
		
	}

}
