package com.studyveloper.overtheflow.mapper;

import java.util.List;
import java.util.Map;

import com.studyveloper.overtheflow.vo.MusicVO;

public interface MusicMapper {
	public int addMusic(MusicVO musicVO);
	public int deleteAllMusic();
	public int modifyMusic(MusicVO musicVO);
	public boolean deleteMusic(String musicId);
	public List<MusicVO> getAllMusicList();
	public MusicVO searchMusicByMusicId(String musicId);
	public int maxSearchMusicByMusicTitle(String title);
	public List<MusicVO> searchMusicByMusicTitle(Map<String, Object> searchCondition);
}
