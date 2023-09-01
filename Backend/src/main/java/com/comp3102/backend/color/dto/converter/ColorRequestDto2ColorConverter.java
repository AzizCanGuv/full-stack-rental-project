package com.comp3102.backend.color.dto.converter;

import com.comp3102.backend.color.dto.request.ColorRequestDto;
import com.comp3102.backend.color.Color;
import org.springframework.stereotype.Component;

@Component
public class ColorRequestDto2ColorConverter {
    public Color convert(ColorRequestDto from) {
        return new Color(null, from.getColorName());
    }

    public Color convert(Integer id, ColorRequestDto from) {
        return new Color(id, from.getColorName());
    }
}
