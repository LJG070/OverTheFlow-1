package com.studyveloper.overtheflow.service;

public interface PlaylistLikeService {
	public Boolean likePlaylist(String playlistId, String memberId);
	
	public Boolean cancelLikePlaylist(String playlistId, String memberId);
}
