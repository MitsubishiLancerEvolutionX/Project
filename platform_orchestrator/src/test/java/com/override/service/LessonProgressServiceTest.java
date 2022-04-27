package com.override.service;

import com.override.models.LessonProgress;
import com.override.models.PlatformUser;
import com.override.repositories.LessonProgressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LessonProgressServiceTest {

    @InjectMocks
    private LessonProgressService lessonProgressService;

    @Mock
    private LessonProgressRepository lessonProgressRepository;

    @Test
    public void testSaveLessonProgress() {
        PlatformUser platformUser = new PlatformUser();

        String lesson = "test-1-1-1";

        lessonProgressService.checkLesson(platformUser, lesson);

        verify(lessonProgressRepository, times(1)).save(any());
    }

    @Test
    public void testSaveSameLessonProgress() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setId(1L);
        platformUser.setLogin("login");
        LessonProgress lessonProgress = LessonProgress.builder().lesson("test-1-1-1").user(platformUser).build();

        when(lessonProgressRepository.findAllByUserId(1L)).thenReturn(List.of(lessonProgress));

        lessonProgressService.checkLesson(platformUser, "test-1-1-1");

        verify(lessonProgressRepository, never()).save(any());
    }

    @Test
    public void testGettingLessonProgress() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setId(1L);
        platformUser.setLogin("login");
        LessonProgress lessonProgress1 = LessonProgress.builder().lesson("test-1-1-1").user(platformUser).build();
        LessonProgress lessonProgress2 = LessonProgress.builder().lesson("test-2-2-2").user(platformUser).build();

        when(lessonProgressRepository.findAllByUserId(1L)).thenReturn(List.of(lessonProgress1, lessonProgress2));

        lessonProgressService.checkLesson(platformUser, "test-1-1-1");
        lessonProgressService.checkLesson(platformUser, "test-2-2-2");

        List<String> progress = new ArrayList<>();
        progress.add("test-1-1-1");
        progress.add("test-2-2-2");

        List<String> userProgress = lessonProgressService.getPassedLessons(platformUser);

        Assertions.assertEquals(progress, userProgress);

    }

}
