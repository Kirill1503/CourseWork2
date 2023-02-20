package com.example.coursework2;

import com.example.coursework2.exception.IncorrectAmountOfQuestionException;
import com.example.coursework2.model.Question;
import com.example.coursework2.service.Impl.ExaminerServiceImpl;
import com.example.coursework2.service.Impl.JavaQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceTest {

    @Mock
    private JavaQuestionService javaQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    public void beforeEach () {
        Collection<Question> questions = Stream.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3"),
                new Question("Q4", "A4"),
                new Question("Q5", "A5")
        ).collect(Collectors.toSet());

        when(javaQuestionService.getAll()).thenReturn(questions);
    }
    @Test
    public void getQuestionsNegativeTest () {
        assertThatExceptionOfType(IncorrectAmountOfQuestionException.class)
                .isThrownBy(() -> examinerService.getQuestions(-1));

        assertThatExceptionOfType(IncorrectAmountOfQuestionException.class)
                .isThrownBy(() -> examinerService.getQuestions(7));
    }

    @Test
    public void getQuestionsTest () {
        List<Question> questions = new ArrayList<>(javaQuestionService.getAll());

        when(javaQuestionService.getRandomQuestion()).thenReturn(
                questions.get(0),
                questions.get(0),
                questions.get(2),
                questions.get(1)
        );

        assertThat(examinerService.getQuestions(3)).containsExactly(questions.get(0),
                questions.get(2), questions.get(1));

        when(javaQuestionService.getRandomQuestion()).thenReturn(
                questions.get(3),
                questions.get(2),
                questions.get(0),
                questions.get(0),
                questions.get(1),
                questions.get(0),
                questions.get(4)
        );

        assertThat(examinerService.getQuestions(5)).containsExactly(questions.get(3),
                questions.get(2), questions.get(0), questions.get(1), questions.get(4));
    }
}
