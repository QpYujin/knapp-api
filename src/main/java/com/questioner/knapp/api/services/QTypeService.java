package com.questioner.knapp.api.services;

import com.questioner.knapp.api.repositories.QTypeRepository;
import com.questioner.knapp.core.models.QType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class QTypeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QTypeRepository qTypeRepository;

    public QType create(QType data) {
        logger.info("create({})", data);
        QType qType = new QType(data.getDescription(), data.getComments());
        qType.populateCreateAtrributes();
        return qTypeRepository.save(qType);
    }

    public QType get(Long id) {
        logger.info("get({})", id);
        return qTypeRepository.findOne(id);
    }

    public QType update(Long id, QType data) {
        logger.info("update({},{})", id, data);
        QType qType = qTypeRepository.findOne(id);
        if (qType == null)
            return null;
        else {
            qType.setDescription(data.getDescription());
            qType.setComments(data.getComments());
            qType.populateUpdateAtrributes();
            qType = qTypeRepository.save(qType);
            return qType;
        }
    }

    public boolean delete(Long id) {
        logger.info("delete({})", id);
        qTypeRepository.delete(id);
        QType qType = qTypeRepository.findOne(id);
        return qType == null;
    }

    public List<QType> findAll() {
        logger.info("findAll()");
        List<QType> qTypes = new ArrayList<>();
        qTypeRepository.findAll().forEach(qTypes::add);
        return qTypes;
    }
}
