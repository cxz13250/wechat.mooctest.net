package com.mooctest.weixin.message;

/**
 * 音乐消息
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class MusicMessage extends BaseMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}