package com.comp3102.backend.color.dto.converter;

import com.comp3102.backend.color.dto.response.ColorResponseDto;
import com.comp3102.backend.color.Color;
import org.springframework.stereotype.Component;

@Component
public class ColorResponseDto2ColorConverter {
    public Color convert(ColorResponseDto from) {
        return new Color(from.getId(), from.getColorName());
    }
}
