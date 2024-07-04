package com.example.naluri.pi;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

import ch.obermuhlner.math.big.BigDecimalMath;

@Service
public class PiServices {
    private final PiRepository piRepository;
    MathContext mc = new MathContext(1000);

    @Autowired
    public PiServices(PiRepository piRepository) {
        this.piRepository = piRepository;
    }

    public static BigDecimal factorial(BigDecimal number) {
        BigDecimal result = BigDecimal.valueOf(1);

        for (long factor = 2; factor <= number.longValue(); factor++) {
            result = result.multiply(BigDecimal.valueOf(factor));
        }

        return result;
    }

    public String updateSummation(int iteration){

        //------------------
        BigDecimal numeratorConstant1 = new BigDecimal("6");
        BigDecimal numeratorConstant4 = new BigDecimal("-1");
        BigDecimal iterationValue = new BigDecimal(Integer.toString(iteration));
        BigDecimal numeratorVal3 = BigDecimalMath.pow(numeratorConstant4, iterationValue, mc);
        BigDecimal numeratorVal1 = iterationValue.multiply(numeratorConstant1);

        BigDecimal numeratorConstant2 = new BigDecimal(Integer.toString(545140134));
        BigDecimal numeratorConstant3 = new BigDecimal(Integer.toString(13591409));

        BigDecimal numeratorVal2 = numeratorConstant2.multiply(iterationValue).add(numeratorConstant3);

        //------------------
        BigDecimal denominatorConstant1 = new BigDecimal("3");
        BigDecimal denominatorVal1 = iterationValue.multiply(denominatorConstant1);
        BigDecimal denominatorConstant2 = new BigDecimal("640320");
        BigDecimal denominatorConstant3 = new BigDecimal("1.5");
        BigDecimal denominatorVal2 = denominatorConstant1.multiply(iterationValue).add(denominatorConstant3);

        BigDecimal denominatorVal3 = BigDecimalMath.pow(denominatorConstant2, denominatorVal2, mc);

        BigDecimal numerator = numeratorVal3.multiply(factorial(numeratorVal1)).multiply(numeratorVal2);
        BigDecimal denominator = (factorial(denominatorVal1)).multiply((factorial(iterationValue)).pow(3)).multiply(denominatorVal3);

        BigDecimal totalValue = numerator.divide(denominator, mc);

        return totalValue.toString();
    }

    public String[] calculatePi(int iteration, String currentSummation){

        BigDecimal constant = new BigDecimal("1");
        BigDecimal constant1 = new BigDecimal("12");
        BigDecimal currentSummation1 = new BigDecimal(currentSummation);

        if(iteration == 0) {
            return new String[]{new BigDecimal(constant.divide(constant1.multiply(currentSummation1), mc).toString()).toString(), currentSummation1.toString()};
        }
        else {
            BigDecimal sumForNewK = new BigDecimal(updateSummation(iteration));
            BigDecimal updatedSummation = currentSummation1.add(sumForNewK);
            return new String[]{new BigDecimal(constant.divide(constant1.multiply(updatedSummation), mc).toString()).toString(), updatedSummation.toString()};
        }

    };

    public Pi getPi(int id){
        return piRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pi not found"));
    }

    @Transactional
    public void updatePi(int id){

        Optional<Pi> piById = piRepository.findById(id);
        int iteration = -1;
        int accuracyPlaces = 13;

        if(piById.isPresent()) {
            if(piById.get().getIteration() == -1){
                iteration = 0;
            }else{
                iteration = piById.get().getIteration()+1;
            }
        }
        BigDecimal initialSummation = new BigDecimal(updateSummation(iteration));
        String[] x;
        String piValue;
        BigDecimal sunRadius = new BigDecimal("696340");
        BigDecimal const1 = new BigDecimal("2");

        if(iteration == 0) {
            x = calculatePi(iteration, initialSummation.toString());
            piValue = x[0].substring(0, accuracyPlaces+2);
            piById.get().setPi(piValue);
            piById.get().setSunCircumference(const1.multiply(sunRadius).multiply(new BigDecimal(piValue)).toString());
        }
        else {
            x = calculatePi(iteration, piById.get().getSummation());
            piValue = x[0].substring(0, iteration * accuracyPlaces + accuracyPlaces+2);
            piById.get().setPi(piValue);
            piById.get().setSunCircumference(const1.multiply(sunRadius).multiply(new BigDecimal(piValue)).toString());
        }
        piById.get().setAccuracyValue(Integer.toString(piValue.length()-2));
        piById.get().setIteration(iteration);
        piById.get().setSummation(x[1]);


    }
}