package com.swcamp9th.bangflixbackend.domain.eventPost.service;

import com.swcamp9th.bangflixbackend.domain.eventPost.dto.EventPostCreateDTO;
import com.swcamp9th.bangflixbackend.domain.eventPost.dto.EventPostUpdateDTO;
import com.swcamp9th.bangflixbackend.domain.eventPost.entity.EventFile;
import com.swcamp9th.bangflixbackend.domain.eventPost.entity.EventPost;
import com.swcamp9th.bangflixbackend.domain.eventPost.repository.EventFileRepository;
import com.swcamp9th.bangflixbackend.domain.eventPost.repository.EventPostRepository;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Theme;
import com.swcamp9th.bangflixbackend.domain.theme.repository.ThemeRepository;
import com.swcamp9th.bangflixbackend.domain.user.entity.Member;
import com.swcamp9th.bangflixbackend.domain.user.repository.UserRepository;
import com.swcamp9th.bangflixbackend.exception.InvalidUserException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("eventPostService")
public class EventPostServiceImpl implements EventPostService {

    private final ModelMapper modelMapper;
    private final EventPostRepository eventPostRepository;
    private final EventFileRepository eventFileRepository;
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;

    @Autowired
    public EventPostServiceImpl(ModelMapper modelMapper,
                                EventPostRepository eventPostRepository,
                                EventFileRepository eventFileRepository,
                                UserRepository userRepository,
                                ThemeRepository themeRepository) {
        this.modelMapper = modelMapper;
        this.eventPostRepository = eventPostRepository;
        this.eventFileRepository = eventFileRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
    }

    @Transactional
    @Override
    public void createEventPost(String loginId, EventPostCreateDTO newEvent, List<MultipartFile> images) throws IOException {

        // 관리자 회원이 아니라면 예외 발생
        Member admin = userRepository.findByIdAndIsAdminTrue(loginId)
                .orElseThrow(() -> new InvalidUserException("관리자 회원만 접근 가능합니다."));

        Theme selectedTheme = themeRepository.findById(newEvent.getThemeCode()).orElse(null);

        EventPost createdEventPost = new EventPost();
        createdEventPost.setActive(true);
        createdEventPost.setCreatedAt(LocalDateTime.now());
        createdEventPost.setTitle(newEvent.getTitle());
        createdEventPost.setContent(newEvent.getContent());
        createdEventPost.setCategory(newEvent.getCategory());
        createdEventPost.setTheme(selectedTheme);
        createdEventPost.setMember(admin);

        // 게시글 저장
        eventPostRepository.save(createdEventPost);

        // 첨부파일 있으면 저장
        if (images != null) {
            List<EventFile> addedImages = saveFiles(images, createdEventPost);
            createdEventPost.setEventFiles(addedImages);
        }
    }

    private List<EventFile> saveFiles(List<MultipartFile> images, EventPost createdEventPost) throws IOException {
        List<EventFile> eventFiles = new ArrayList<>();

        for (MultipartFile file : images) {
            String fileName = file.getOriginalFilename();

            // 파일이름만 남김
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            // UUID 생성
            String uuid = UUID.randomUUID().toString();
            // 저장 경로
            String filePath = "src/main/resources/static/eventFiles/" + uuid + fileName;
            Path path = Paths.get(filePath);
            // DB 저장명
            String dbUrl = "/eventFiles/" + uuid + fileName;

            //저장
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            EventFile addedImage = eventFileRepository.save(EventFile.builder()
                    .url(dbUrl)
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .eventPost(createdEventPost)
                    .build()
            );

            eventFiles.add(addedImage);
        }

        return eventFiles;
    }

    @Transactional
    @Override
    public void updateEventPost(String loginId, int eventPostCode,
                                EventPostUpdateDTO modifiedEvent, List<MultipartFile> images) {

        EventPost foundPost = eventPostRepository.findById(eventPostCode)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 관리자 회원이 아니라면 예외 발생
        Member admin = userRepository.findByIdAndIsAdminTrue(loginId)
                .orElseThrow(() -> new InvalidUserException("관리자 회원만 접근 가능합니다."));

        // 게시글 작성자가 아니라면 예외 발생
        if (!foundPost.getMember().getMemberCode().equals(admin.getMemberCode())) {
            throw new InvalidUserException("게시글 수정 권한이 없습니다.");
        }

        Theme selectedTheme = themeRepository.findById(modifiedEvent.getThemeCode()).orElse(null);

        foundPost.setTitle(modifiedEvent.getTitle());
        foundPost.setContent(modifiedEvent.getContent());
        foundPost.setCategory(modifiedEvent.getCategory());
        foundPost.setTheme(selectedTheme);

        // 수정된 게시글 저장
        eventPostRepository.save(foundPost);
    }

    @Transactional
    @Override
    public void deleteEventPost(String loginId, int eventPostCode) {
        EventPost foundPost = eventPostRepository.findById(eventPostCode)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 관리자 회원이 아니라면 예외 발생
        Member admin = userRepository.findByIdAndIsAdminTrue(loginId)
                .orElseThrow(() -> new InvalidUserException("관리자 회원만 접근 가능합니다."));

        // 게시글 작성자가 아니라면 예외 발생
        if (!foundPost.getMember().getMemberCode().equals(admin.getMemberCode())) {
            throw new InvalidUserException("게시글 수정 권한이 없습니다.");
        }

        foundPost.setActive(false);
        eventPostRepository.save(foundPost);
    }
}
