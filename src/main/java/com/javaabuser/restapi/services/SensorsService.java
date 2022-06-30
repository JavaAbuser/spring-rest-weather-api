package com.javaabuser.restapi.services;

import com.javaabuser.restapi.models.Sensor;
import com.javaabuser.restapi.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll(){
        return sensorsRepository.findAll();
    }

    public Optional<Sensor> findByName(String sensorName){
        return sensorsRepository.findByName(sensorName);
    }

    @Transactional
    public void save(Sensor sensor){
        sensorsRepository.save(sensor);
    }

    @Transactional
    public void delete(Sensor sensor){
        sensorsRepository.delete(sensor);
    }
}
