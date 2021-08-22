package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.constants.Constants;
import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.repository.FeeRepository;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The type Fee service.
 */
@Service
public class FeeService {

    private final Logger LOGGER = LoggerFactory.getLogger(FeeService.class);

    @Autowired
    private FeeRepository feeRepository;

    /**
     * Gets fees.
     *
     * @return the fees
     */
    public Iterable<Fee> getFees() {
        LOGGER.info("Processing to get all fees");
        return feeRepository.findAll(Sort.by(Sort.Direction.DESC,"date"));
    }

    /**
     * Save fee.
     *
     * @param fee the fee
     */
    public void saveFee(Fee fee){
        LOGGER.info("Processing to save new fee");

        fee.setDate(LocalDateTime.now());
        fee.setRate100(Constants.RATE100);

        feeRepository.save(fee);
    }

    /**
     * Gets total fee balance.
     *
     * @return the total fee balance
     */
    public double getTotalFeeBalance() {
        AtomicReference<Double> result = new AtomicReference<>((double) 0);

        List<Fee> fees = feeRepository.findAll();

        if (fees == null) return result.get();

        fees.iterator().forEachRemaining(fee -> {
            result.updateAndGet(v -> new Double((double) (v + fee.getAmount())));
        });

        return Precision.round(result.get(),2);
    }
}
