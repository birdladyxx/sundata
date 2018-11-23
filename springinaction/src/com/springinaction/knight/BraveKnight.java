package com.springinaction.knight;
/**
 * 勇敢骑士类。可以进行各种探险活动。
 * @author qinzh
 *
 */
public class BraveKnight implements Knight {

	private Quest quest;
	
	
	public BraveKnight(Quest quest) {// Quest被注入进来
		this.quest = quest;
	}


	@Override
	public void embarkOnQuest() {
		quest.embark();
		
	}

}
