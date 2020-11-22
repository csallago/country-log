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
    private static final String NUM = "NUM";
    private static final String DEN = "DEN";

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
        Optional<Stat> opNum = statRepo.findById(NUM);
        Optional<Stat> opDen = statRepo.findById(DEN);
        if (!opNum.isPresent() || !opDen.isPresent())
            return 0d;
        double num = Double.parseDouble(opNum.get().getValue());
        double den = Double.parseDouble(opDen.get().getValue());
        
        return num / den;
    }

    public void updateStats(Double distance, String countryName) {
        updateFarthest(distance, countryName);
        updateNearthest(distance, countryName);
        updateAverage(distance);
    }

    private void updateFarthest(Double distance, String countryName) {
        Optional<Stat> farthestOp = statRepo.findById(FARTHEST);

        if (!farthestOp.isPresent())
            statRepo.save(new Stat(FARTHEST, distance.toString(), countryName));
        else if (Double.parseDouble(farthestOp.get().getValue()) < distance) {
            farthestOp.get().setCountryName(countryName);
            farthestOp.get().setValue(String.valueOf(distance));
            statRepo.save(farthestOp.get());
        }
            
        System.out.println("Farthest value: "+statRepo.findById(FARTHEST).get().getValue());
    }

    private void updateNearthest(Double distance, String countryName) {
        Optional<Stat> nearthestOp = statRepo.findById(NEARTHEST);

        if (!nearthestOp.isPresent())
            statRepo.save(new Stat(NEARTHEST, distance.toString(), countryName));
        else if (Double.parseDouble(nearthestOp.get().getValue()) > distance) {
            nearthestOp.get().setCountryName(countryName);
            nearthestOp.get().setValue(String.valueOf(distance));
            statRepo.save(nearthestOp.get());
        }
            
        System.out.println("Nearthest value: "+statRepo.findById(NEARTHEST).get().getValue());
    }

    private void updateAverage(Double distance) {
        Optional<Stat> opNum = statRepo.findById(NUM);
        Optional<Stat> opDen = statRepo.findById(DEN);
        
        //update numerator
        if (!opNum.isPresent())
            statRepo.save(new Stat(NUM, distance.toString()));
        else {
            Stat num = opNum.get();
            double numValue = Double.parseDouble(num.getValue());
            numValue += distance;
            num.setValue(String.valueOf(numValue));
            statRepo.save(num);
        }

        //update denominator
        if (!opDen.isPresent())
            statRepo.save(new Stat(DEN, String.valueOf("1")));
        else {
            Stat den = opDen.get();
            double denValue = Double.parseDouble(den.getValue());
            denValue++;
            den.setValue(String.valueOf(denValue));
            statRepo.save(den);
        }
        
        System.out.println("numerator value: "+statRepo.findById(NUM).get().getValue());
        System.out.println("denominator value: "+statRepo.findById(DEN).get().getValue());
    }
}
