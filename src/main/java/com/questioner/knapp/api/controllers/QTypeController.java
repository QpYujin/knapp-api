package com.questioner.knapp.api.controllers;

import com.questioner.knapp.api.services.QTypeService;
import com.questioner.knapp.core.models.QType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/knapp/qtype")
public class QTypeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QTypeService qTypeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<QType> get(@PathVariable(value = "id") String id) {
        logger.info("get({})", id);
        QType qType = qTypeService.get(Long.valueOf(id));
        return (qType == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(qType, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<QType> create(@RequestBody QType data) {
        logger.info("create({})", data);
        QType qType = qTypeService.create(data);
        return (qType != null) ? new ResponseEntity<>(qType, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<QType> update(@PathVariable(value = "id") String id, @RequestBody QType data) {
        logger.info("update({},{})", id, data);
        QType qType = qTypeService.update(Long.valueOf(id), data);
        if (qType == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(qType, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") String id) {
        logger.info("delete({})", id);
        boolean deleted = qTypeService.delete(Long.valueOf(id));
        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<QType>> findAll() {
        logger.info("findAll()");
        List<QType> qTypes = qTypeService.findAll();
        if (qTypes.size() == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(qTypes, HttpStatus.OK);
    }

}
