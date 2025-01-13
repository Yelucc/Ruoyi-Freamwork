package com.autoparams.business.loya.enums.convert;


import com.autoparams.business.loya.domain.LoyaJewel;
import com.autoparams.business.loya.enums.ReservationStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToReservationStatusConverter implements Converter<String, ReservationStatus> {

    @Override
    public ReservationStatus convert(String source) {
        return ReservationStatus.fromCode(source);
    }
}