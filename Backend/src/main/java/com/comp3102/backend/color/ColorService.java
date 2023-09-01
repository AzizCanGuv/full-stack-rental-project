package com.comp3102.backend.color;

import com.comp3102.backend.core.exceptions.EntityAlreadyExistsException;
import com.comp3102.backend.core.exceptions.EntityNotFoundException;
import com.comp3102.backend.color.dto.request.ColorRequestDto;
import com.comp3102.backend.color.dto.response.ColorResponseDto;
import com.comp3102.backend.color.dto.converter.Color2ColorResponseDtoConverter;
import com.comp3102.backend.color.dto.converter.ColorRequestDto2ColorConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.comp3102.backend.core.constant.ErrorConstant.*;

@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;
    private final Color2ColorResponseDtoConverter color2ColorResponseDtoConverter;
    private final ColorRequestDto2ColorConverter colorRequestDto2ColorConverter;

    public List<ColorResponseDto> getAll() {
        return color2ColorResponseDtoConverter.convert(colorRepository.findAll());
    }

    public ColorResponseDto addNewColor(ColorRequestDto color) {
        colorRepository.findByColorName(color.getColorName()).ifPresent(s -> {
            throw new EntityAlreadyExistsException(errorMessageParser(COLOR_ALREADY_EXISTS_MESSAGE, color.getColorName()));
        });
        Color colorInDb = colorRepository.save(colorRequestDto2ColorConverter.convert(color));
        return color2ColorResponseDtoConverter.convert(colorInDb);
    }

    public ColorResponseDto getColorById(Integer id) {
        Color color = colorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(COLOR_NOT_FOUND_MESSAGE, id)));
        return color2ColorResponseDtoConverter.convert(color);
    }

    public ColorResponseDto getColorByName(String name) {
        Color color = colorRepository.findByColorName(name).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(COLOR_NOT_FOUND_MESSAGE, name)));
        return color2ColorResponseDtoConverter.convert(color);
    }

    public void deleteColor(Integer id) {
        colorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(COLOR_NOT_FOUND_MESSAGE, id)));
        colorRepository.deleteById(id);
    }

    public ColorResponseDto updateColor(Integer id, ColorRequestDto color) {
        if (!colorRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Entity Not Found!!");
        }
        colorRepository.findByColorName(color.getColorName()).ifPresent(s -> {
            throw new EntityAlreadyExistsException(errorMessageParser(COLOR_ALREADY_EXISTS_MESSAGE, color.getColorName()));
        });
        Color colorToUpdate = colorRepository.save(colorRequestDto2ColorConverter.convert(id, color));
        return color2ColorResponseDtoConverter.convert(colorToUpdate);
    }
}