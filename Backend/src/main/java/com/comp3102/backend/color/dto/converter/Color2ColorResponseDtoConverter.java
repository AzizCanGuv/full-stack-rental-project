package com.comp3102.backend.color.dto.converter;

import com.comp3102.backend.color.dto.response.ColorResponseDto;
import com.comp3102.backend.color.Color;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Color2ColorResponseDtoConverter {
    public ColorResponseDto convert(Color from) {
        return new ColorResponseDto(from.getColorId(), from.getColorName());
    }

    public List<ColorResponseDto> convert(List<Color> from) {
        return from.stream().map(b -> new ColorResponseDto(b.getColorId(), b.getColorName())).collect(Collectors.toList());
    }
}
