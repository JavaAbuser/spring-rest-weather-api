package com.javaabuser.restapi.services;

import com.javaabuser.restapi.models.Measurement;
import com.javaabuser.restapi.repositories.MeasurementsRepository;
import com.javaabuser.restapi.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    public void enrichMeasurement(Measurement measurement){
        measurement.setSensor(sensorsRepository.findByName(measurement.getSensor().getName()).get());
        measurement.setCreatedAt(new Date());
    }
}
