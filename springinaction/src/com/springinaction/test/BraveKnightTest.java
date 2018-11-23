package com.springinaction.test;

import org.junit.Test;

import static org.mockito.Mockito.*;

import com.springinaction.knight.BraveKnight;
import com.springinaction.knight.Quest;

/**
 * 勇敢骑士测试
 * @author qinzh
 *
 */
public class BraveKnightTest {

	@Test
	public void knightShouldEmbarkOnQuest() {
		Quest mockQuest = mock(Quest.class);
		BraveKnight knight = new BraveKnight(mockQuest);
		knight.embarkOnQuest();
		verify(mockQuest, times(1)).embark();
	}
}
