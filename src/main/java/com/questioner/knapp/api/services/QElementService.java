package com.questioner.knapp.api.services;

import com.questioner.knapp.api.repositories.QElementRepository;
import com.questioner.knapp.core.models.QElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class QElementService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QElementRepository qElementRepository;

    public QElement create(QElement data) {
        logger.info("create({})", data);
        QElement qElement = new QElement(data.getElementtype(), data.getUiview(), data.getComments());
        qElement.populateCreateAtrributes();
        return qElementRepository.save(qElement);
    }

    public QElement get(Long id) {
        logger.info("get({})", id);
        return qElementRepository.findOne(id);
    }

    public QElement update(Long id, QElement data) {
        logger.info("update({},{})", id, data);
        QElement qElement = qElementRepository.findOne(id);
        if (qElement == null)
            return null;
        else {
            qElement.setElementtype(data.getElementtype());
            qElement.setUiview(data.getUiview());
            qElement.setComments(data.getComments());
            qElement.populateUpdateAtrributes();
            qElement = qElementRepository.save(qElement);
            return qElement;
        }
    }

    public boolean delete(Long id) {
        logger.info("delete({})", id);
        qElementRepository.delete(id);
        QElement qElement = qElementRepository.findOne(id);
        return qElement == null;
    }

    public List<QElement> findAll() {
        logger.info("findAll()");
        List<QElement> qElements = new ArrayList<>();
        qElementRepository.findAll().forEach(qElements::add);
        return qElements;
    }
}
