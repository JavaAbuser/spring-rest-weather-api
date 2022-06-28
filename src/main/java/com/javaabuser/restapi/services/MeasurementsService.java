package com.javaabuser.restapi.services;

import com.javaabuser.restapi.models.Measurement;
import com.javaabuser.restapi.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        measurementsRepository.save(measurement);
    }
}
