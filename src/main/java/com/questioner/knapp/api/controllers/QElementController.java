package com.questioner.knapp.api.controllers;

import com.questioner.knapp.api.services.QElementService;
import com.questioner.knapp.core.models.QElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/knapp/qelement")
public class QElementController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QElementService qElementService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<QElement> get(@PathVariable(value = "id") String id) {
        logger.info("get({})", id);
        QElement qElement = qElementService.get(Long.valueOf(id));
        return (qElement == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(qElement, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<QElement> create(@RequestBody QElement data) {
        logger.info("create({})", data);
        QElement qElement = qElementService.create(data);
        return (qElement != null) ? new ResponseEntity<>(qElement, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<QElement> update(@PathVariable(value = "id") String id, @RequestBody QElement data) {
        logger.info("update({},{})", id, data);

        QElement qElement = qElementService.update(Long.valueOf(id), data);
        if (qElement == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(qElement, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") String id) {
        logger.info("delete({})", id);
        boolean deleted = qElementService.delete(Long.valueOf(id));
        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<QElement>> findAll() {
        logger.info("findAll()");
        List<QElement> qElements = qElementService.findAll();
        if (qElements.size() == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(qElements, HttpStatus.OK);
    }

}
