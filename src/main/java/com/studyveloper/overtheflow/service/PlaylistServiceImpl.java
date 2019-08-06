package com.studyveloper.overtheflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyveloper.overtheflow.bean.PlaylistBean;
import com.studyveloper.overtheflow.mapper.PlaylistMapper;
import com.studyveloper.overtheflow.mapper.PlaylistTagMapper;
import com.studyveloper.overtheflow.util.IdentifierGenerator;
import com.studyveloper.overtheflow.util.option.OptionIntent;
import com.studyveloper.overtheflow.util.option.PlaylistUnit;
import com.studyveloper.overtheflow.vo.PlaylistVO;
import com.studyveloper.overtheflow.vo.TagVO;

@Service
public class PlaylistServiceImpl implements PlaylistService {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(PlaylistServiceImpl.class);
		
	@Autowired
	private PlaylistMapper playlistMapper;
	
	@Autowired
	private PlaylistTagMapper playlistTagMapper;
	
	public PlaylistVO createPlaylist(PlaylistVO playlistVO) {
		// 파라미터 널 체크
		if (playlistVO == null) {
			logger.error("전달인자 오류.");
			return null;
		}
		
		// 데이터 검증
		if (playlistVO.getTitle() == null 
			|| playlistVO.getVisibility() == null
			|| playlistVO.getDescription() == null
			|| playlistVO.getMemberId() == null) {
			logger.error("데이터가 비어 있습니다.");
			return null;
		}
	
		// 식별키 생성
		String id = IdentifierGenerator.generateId(playlistVO.getTitle());
		playlistVO.setId(id);
		
		// 날짜 정보 삽입
		playlistVO.setRegisterDate(new Date());
		
		// 플레이리스트 정보 등록
		try {
			playlistMapper.addPlaylist(playlistVO);
		} catch (Exception e) {
			// 예외 처리
			logger.error(e.getMessage());
		}
		
		// 태그 정보 등록
		List<String> tags = playlistVO.getTags();
		try {
			if (tags != null && tags.size() > 0) {
				for (String tag : tags) {
					TagVO tagVO = new TagVO();
					tagVO.setId(id);
					tagVO.setTagName(tag);
					playlistTagMapper.addPlaylistTag(tagVO);
				}
			}
		}  catch (Exception e) {
			// 예외 처리
			logger.error(e.getMessage());
			return null;
		}
		
		return playlistVO;
	}

	public boolean deletePlaylist(String playlistId, String loginId) {
		// 전달인자 null 체크
		if (playlistId == null) {
			logger.error("삭제할 대상이 없습니다.");
			return false;
		}
				
		logger.info("플레이리스트 삭제 요청 (" + playlistId + ")");
		
		try {
			// 플레이리스트 정보 제거
			playlistMapper.deletePlaylists(new OptionIntent.Builder()
					.appendEqualSearchOption(PlaylistUnit.MEMBER_ID, loginId, true)
					.appendEqualSearchOption(PlaylistUnit.ID, playlistId, true)
					.build());
			
		} catch (Exception e) {
			// 예외 처리
			logger.error(e.getMessage());
			return false;
		}
		
		return true;
	}

	public PlaylistVO modifyPlaylist(PlaylistVO playlistVO) {
		// 전달인자 null 체크
		if (playlistVO == null) {
			logger.error("수정할 대상이 없습니다.");
			return null;
		}
		
		logger.info("플레이리스트 수정 (" + playlistVO.getId() + ")");
		
		// 데이터 체크
		if (playlistVO.getVisibility() == null 
			|| playlistVO.getDescription() == null
			|| playlistVO.getId() == null
			|| playlistVO.getMemberId() == null
			|| playlistVO.getRegisterDate() == null
			|| playlistVO.getTitle() == null) {
			logger.error("누락된 정보가 있습니다.");
		}
		
		try {
			// 플레이리스트 정보 수정
			playlistMapper.modifyPlaylist(playlistVO);
			
			// 태그 정보 수정
			// 기존 태그 정보 모두 삭제
			playlistTagMapper.deletePlaylistTag(playlistVO.getId());
			
			// 태그 정보 등록
			List<String> tags = playlistVO.getTags();
			if (tags != null && tags.size() > 0) {
				for (String tag : tags) {
					TagVO newTag = new TagVO();
					newTag.setId(playlistVO.getId());
					newTag.setTagName(tag);
					playlistTagMapper.addPlaylistTag(newTag);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
		return playlistVO;
	}
	
	public PlaylistVO getPlaylist(String playlistId, String loginId) {
		if (playlistId == null || loginId == null) {
			return null;
		}
		
		PlaylistVO playlistVO = null;
		
		try {
			playlistVO = playlistMapper.searchPlaylist(playlistId);
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
		
		if (playlistVO.getMemberId().equals(loginId.trim())) {
			return playlistVO;
		} else {
			return null;
		}
	}
}
