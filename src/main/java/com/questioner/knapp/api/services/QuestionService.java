package com.questioner.knapp.api.services;

import com.questioner.knapp.api.repositories.QuestionRepository;
import com.questioner.knapp.core.models.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class QuestionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QTypeService qTypeService;

    public Question create(Question data) {
        logger.info("create({})", data);
        Question question = new Question(data.getText(), data.getDescription());
        question.setQtype(qTypeService.get(data.getqTypeId()));
        question.populateCreateAttributes();
        return questionRepository.save(question);
    }

    public Question get(Long id) {
        logger.info("get({})", id);
        return questionRepository.findOne(id);
    }

    public Question update(Long id, Question data) {
        logger.info("update({},{})", id, data);
        Question question= questionRepository.findOne(id);
        if (question == null)
            return null;
        else {
            question.setText(data.getText());
            question.setDescription(data.getDescription());
            question.setqTypeId(data.getqTypeId());
            question.setQtype(qTypeService.get(data.getqTypeId()));
            question.populateUpdateAttributes();
            question = questionRepository.save(question);
            return question;
        }
    }

    public boolean delete(Long id) {
        logger.info("delete({})", id);
        questionRepository.delete(id);
        Question question= questionRepository.findOne(id);
        return question == null;
    }

    public List<Question> findAll() {
        logger.info("findAll()");
        List<Question> questions = new ArrayList<>();
        questionRepository.findAll().forEach(questions::add);
        return questions;
    }
}
