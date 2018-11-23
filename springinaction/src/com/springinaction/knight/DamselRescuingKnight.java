package com.springinaction.knight;
/**
 * 拯救遇险少女骑士类
 * @author qinzh
 *
 */
public class DamselRescuingKnight implements Knight {

	private RescueDamselQuest quest;
	
	@Override
	public void embarkOnQuest() {
		quest.embark();
		
	}

	public DamselRescuingKnight() {
		this.quest = new RescueDamselQuest();  //此时骑士类就和探险类发生了紧密的耦合，该骑士只能去救少女，不能进行其他的探险
	}
	
	

}
