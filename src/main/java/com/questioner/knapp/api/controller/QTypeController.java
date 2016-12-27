package com.questioner.knapp.api.controller;

import com.questioner.knapp.core.model.QType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/knapp/qtype")
public class QTypeController {

    private HashMap<String, QType> cache = new HashMap<>();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public QType get(@PathVariable(value = "id") String id) {
        return new QType("TestDescription", "TestComments");
    }

    @RequestMapping(method = RequestMethod.POST)
    public QType create(@RequestBody QType data) {
        QType qType = new QType(data.getDescription(), data.getComments());
        cache.put(qType.getId(), qType);
        return qType;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public QType update(@RequestParam(value = "id") String id, @RequestBody QType data) {
        Optional.of(cache.get(id)).orElseThrow((() -> new RuntimeException(id)));
        cache.put(id, data);
        return cache.get(id);
    }
}
