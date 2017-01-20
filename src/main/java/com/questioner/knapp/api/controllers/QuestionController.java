package com.questioner.knapp.api.controllers;

import com.questioner.knapp.api.services.QuestionService;
import com.questioner.knapp.core.models.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/knapp/question")
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Question> get(@PathVariable(value = "id") String id) {
        logger.info("get({})", id);
        Question question = questionService.get(Long.valueOf(id));
        return (question == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(question, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Question> create(@RequestBody Question data) {
        logger.info("create({})", data);
        Question question = questionService.create(data);
        return (question != null) ? new ResponseEntity<>(question, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Question> update(@PathVariable(value = "id") String id, @RequestBody Question data) {
        logger.info("update({},{})", id, data);

        Question question = questionService.update(Long.valueOf(id), data);
        if (question == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(question, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") String id) {
        logger.info("delete({})", id);
        boolean deleted = questionService.delete(Long.valueOf(id));
        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Question>> findAll() {
        logger.info("findAll()");
        List<Question> questions = questionService.findAll();
        if (questions.size() == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(questions, HttpStatus.OK);
    }

}
