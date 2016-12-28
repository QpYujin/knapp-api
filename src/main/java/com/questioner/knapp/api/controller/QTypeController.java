package com.questioner.knapp.api.controller;

import com.questioner.knapp.core.model.QType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/knapp/qtype")
public class QTypeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static HashMap<String, QType> cache = new HashMap<>();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<QType> get(@PathVariable(value = "id") String id) {
        logger.info("get({})", id);
        if (cache.get(id) != null)
            return new ResponseEntity<>(cache.get(id), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<QType> create(@RequestBody QType data) {
        logger.info("create({})", data);
        QType qType = new QType(data.getDescription(), data.getComments());
        cache.put(qType.getId(), qType);
        return new ResponseEntity<>(cache.get(qType.getId()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<QType> update(@RequestParam(value = "id") String id, @RequestBody QType data) {
        logger.info("update({},{})", id, data);
        if (Optional.of(cache.get(id)).isPresent()) {
            cache.put(id, data);
            return new ResponseEntity<>(cache.get(id), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
