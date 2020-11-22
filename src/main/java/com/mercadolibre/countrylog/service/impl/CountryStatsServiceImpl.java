package com.mercadolibre.countrylog.service.impl;

import java.util.Optional;

import com.mercadolibre.countrylog.domain.CountryDistance;
import com.mercadolibre.countrylog.domain.Stat;
import com.mercadolibre.countrylog.repository.StatRepository;
import com.mercadolibre.countrylog.service.CountryStatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryStatsServiceImpl implements CountryStatsService{
    private static final String FARTHEST = "FARTHEST";
    private static final String NEARTHEST = "NEARTHEST";
    private static final String AVERAGE = "AVERAGE";

    private static final String COUNTRY_NA = "N/A";
    
    @Autowired
    StatRepository statRepo;

    public CountryDistance getFarthestCountry() {
        Optional<Stat> op = statRepo.findById(FARTHEST);
        CountryDistance countryDistance = null;
        if (op.isPresent())
            countryDistance = new CountryDistance(op.get().getCountryName(), 
                                                    Double.parseDouble(op.get().getValue()));
        else
            countryDistance = new CountryDistance(COUNTRY_NA, 0d);
        return countryDistance;
    }

    public CountryDistance getNearthestCountry(){
        Optional<Stat> op = statRepo.findById(NEARTHEST);
        CountryDistance countryDistance = null;
        if (op.isPresent())
            countryDistance = new CountryDistance(op.get().getCountryName(), 
                                                    Double.parseDouble(op.get().getValue()));
        else
            countryDistance = new CountryDistance(COUNTRY_NA, 0d);
        return countryDistance;
    }

    public Double getAverageDistance(){
        return null;
    }

    public void updateStats(Double distance, String countryName) {
        Optional<Stat> farthestOp = statRepo.findById(FARTHEST);
        Stat newDistance = new Stat(FARTHEST, distance.toString(), countryName);

        if (!farthestOp.isPresent())
            statRepo.save(newDistance);
        else if (Double.parseDouble(farthestOp.get().getValue())  < distance)
            statRepo.save(newDistance);

        System.out.println("Farthest value: "+statRepo.findById(FARTHEST).get().getValue());
    }

    private void updateFarthest(Double distance, String countryName) {
        Optional<Stat> farthestOp = statRepo.findById(FARTHEST);
        Stat newDistance = new Stat(FARTHEST, distance.toString(), countryName);

        if (!farthestOp.isPresent())
            statRepo.save(newDistance);
        else if (Double.parseDouble(farthestOp.get().getValue()) < distance)
            statRepo.save(newDistance);

        System.out.println("Farthest value: "+statRepo.findById(FARTHEST).get().getValue());
    }

    private void updateNearthest(Double distance, String countryName) {
        Optional<Stat> farthestOp = statRepo.findById(NEARTHEST);
        Stat newDistance = new Stat(NEARTHEST, distance.toString(), countryName);

        if (!farthestOp.isPresent())
            statRepo.save(newDistance);
        else if (Double.parseDouble(farthestOp.get().getValue()) > distance)
            statRepo.save(newDistance);

        System.out.println("Nearthest value: "+statRepo.findById(NEARTHEST).get().getValue());
    }

    private void updateAverage(Double distance, String countryName) {
        Optional<Stat> farthestOp = statRepo.findById(NEARTHEST);
        Stat newDistance = new Stat(NEARTHEST, distance.toString(), countryName);

        if (!farthestOp.isPresent())
            statRepo.save(newDistance);
        else if (Double.parseDouble(farthestOp.get().getValue()) > distance)
            statRepo.save(newDistance);
        
        System.out.println("Nearthest value: "+statRepo.findById(NEARTHEST).get().getValue());
    }
}
