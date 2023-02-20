package com.example.coursework2;

import com.example.coursework2.exception.QuestionAlreadyExistsException;
import com.example.coursework2.exception.QuestionNotFoundException;
import com.example.coursework2.model.Question;
import com.example.coursework2.service.Impl.JavaQuestionService;
import com.example.coursework2.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();


    @AfterEach
    public void afterEach() {
        questionService.getAll().forEach(questionService::remove);
    }
    @Test
    public void addTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question expected1 = new Question("Q1", "A1");
        Question expected2 = new Question("Q2", "A2");
        questionService.add(expected1);
        questionService.add(expected2.getQuestion(), expected2.getAnswer());

        assertThat(questionService.getAll()).hasSize(2);
        assertThat(questionService.getAll()).contains(expected1, expected2);
    }

    @Test
    public void addNegativeTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question expected = new Question("Q1", "A1");
        questionService.add(expected);

        assertThatExceptionOfType(QuestionAlreadyExistsException.class)
                .isThrownBy(() -> questionService.add(expected));

        assertThatExceptionOfType(QuestionAlreadyExistsException.class)
                .isThrownBy(() -> questionService.add(expected.getQuestion(), expected.getAnswer()));
    }

    @Test
    public void removeTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question expected = new Question("Q1", "A1");
        questionService.add(expected);

        assertThat(questionService.getAll()).hasSize(1);
        assertThat(questionService.getAll()).contains(expected);

        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(new Question("Q2", "A2")));

        questionService.remove(expected);
        questionService.getAll().isEmpty();
    }

    @Test
    public void getRandomQuestionTest() {
        assertThat(questionService.getAll()).isEmpty();

        questionService.add("Q1", "A1");
        questionService.add("Q2", "A2");
        questionService.add("Q3", "A3");
        questionService.add("Q4", "A4");
        questionService.add("Q5", "A5");

        assertThat(questionService.getAll()).hasSize(5);
        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());
    }
}
