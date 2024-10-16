package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.AlarmDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SubscribeService {

    // 구독 관리 저장소
    /*
    * 설명. map 구조: 사용자 별 구독 게시글 목록
    *  {
    *     loginId: {
     *         {postId1: emitter1},
    *     },
    *  }
    *
     * 설명. map 구조: 게시글 별 사용자 목록
     *  {
     *     postId: [loginId, ...]
     *  }
    *
     */
    private final Map<String, Map<Integer, SseEmitter>> userEmitters = new ConcurrentHashMap<>();
    private final Map<Integer, List<String>> postSubscribers = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String loginId, Integer postId) {
        SseEmitter emitter = new SseEmitter(10 * 60 * 1000L);

        // 사용자 별 구독 저장
        userEmitters.computeIfAbsent(loginId, key -> new ConcurrentHashMap<>()).put(postId, emitter);

        // 게시글 별 사용자 목록 저장
        postSubscribers.computeIfAbsent(postId, key -> new ArrayList<>()).add(loginId);

        // emitter 완료
        emitter.onCompletion(() -> removeEmitters(loginId, postId));
        emitter.onTimeout(() -> removeEmitters(loginId, postId));

        return emitter;
    }

    public void sendToMembersOnPostEvent(AlarmDTO alarmDTO) {
        Integer postId = alarmDTO.getPostId();
        List<String> subscribers = postSubscribers.get(postId);
        if(subscribers != null && !subscribers.isEmpty()) {
            subscribers.forEach(loginId -> {
                SseEmitter emitter = userEmitters.get(loginId).get(postId);
                if(emitter != null) {
                    try {
                        emitter.send(
                                SseEmitter.event()
                                        .name("comment-created")
                                        .data(alarmDTO)
                        );
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                }
            });
        }
    }



    public Map<String, Map<Integer, SseEmitter>> getAllSubscribesByMember() {
        return new HashMap<>(userEmitters);
    }

    private void removeEmitters(String loginId, Integer postId) {
        userEmitters.getOrDefault(loginId, new ConcurrentHashMap<>()).remove(postId);
        postSubscribers.getOrDefault(postId, new ArrayList<>()).remove(loginId);
    }
}
