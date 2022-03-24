package com.override.service;

import com.override.mappers.QuestionMapper;
import com.override.models.PlatformUser;
import com.override.models.Question;
import com.override.repositories.QuestionRepository;
import dtos.QuestionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @InjectMocks
    private QuestionService questionService;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private PlatformUserService platformUserService;

    @Test
    void save() {
        final QuestionDTO questionDTO = generateTestQuestionDTO();

        questionService.save(questionDTO);
        verify(questionRepository, times(1)).save(any());
        verify(questionMapper, times(1)).dtoToEntity(any(), any());
    }

    @Test
    void findAllByUserAndChapter() {
        PlatformUser user = generateTestUser();
        Question question = generateTestQuestion();
        QuestionDTO questionDTO = generateTestQuestionDTO();
        when(questionRepository.findAllByUserAndChapter(user, "core 3"))
                .thenReturn(Collections.singletonList(question));
        when(platformUserService.findPlatformUserByLogin(user.getLogin())).thenReturn(user);

        final List<Question> allByUserAndChapter = questionService.findAllByUserAndChapter(questionDTO.getLogin(),
                questionDTO.getChapter());
        Assertions.assertTrue(allByUserAndChapter.contains(question));
    }

    @Test
    void delete() {
        questionService.delete(1L);
        verify(questionRepository,times(1)).deleteById(1L);
    }

    @Test
    void patch() {
        QuestionDTO question = generateTestQuestionDTO();

        questionService.patch(question);

        verify(questionRepository, times(1)).save(any());
    }
}
