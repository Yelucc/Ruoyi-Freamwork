package com.autoparams.business.loya.enums.convert;

import com.autoparams.business.loya.domain.LoyaJewel;

import com.autoparams.business.loya.enums.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Component
public class StringToCategoryEnumConverter implements Converter<String, Category> {

    @Override
    public Category convert(String source) {
        return Category.fromCode(source);
    }
}