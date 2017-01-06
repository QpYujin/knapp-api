package com.questioner.knapp.api.controllers;

import com.questioner.knapp.api.repositories.QTypeRepository;
import com.questioner.knapp.core.models.QType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/knapp/qtype")
public class QTypeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QTypeRepository qTypeRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<QType> get(@PathVariable(value = "id") String id) {
        logger.info("get({})", id);
        QType qType = qTypeRepository.findOne(Long.valueOf(id));
        return (qType == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(qType, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<QType> create(@RequestBody QType data) {
        logger.info("create({})", data);

        QType qType = new QType(data.getDescription(), data.getComments());
        qTypeRepository.save(qType);
        return new ResponseEntity<>(qType, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<QType> update(@PathVariable(value = "id") String id, @RequestBody QType data) {
        logger.info("update({},{})", id, data);
        QType qType = qTypeRepository.findOne(Long.valueOf(id));
        if (qType == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            qType.setDescription(data.getDescription());
            qType.setComments(data.getComments());
            qType = qTypeRepository.save(qType);
            return new ResponseEntity<>(qType, HttpStatus.OK);
        }
    }

}
