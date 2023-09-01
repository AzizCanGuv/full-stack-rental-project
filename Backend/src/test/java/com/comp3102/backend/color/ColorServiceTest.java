package com.comp3102.backend.color;

import com.comp3102.backend.color.dto.converter.Color2ColorResponseDtoConverter;
import com.comp3102.backend.color.dto.converter.ColorRequestDto2ColorConverter;
import com.comp3102.backend.color.dto.request.ColorRequestDto;
import com.comp3102.backend.color.dto.response.ColorResponseDto;
import com.comp3102.backend.core.exceptions.EntityAlreadyExistsException;
import com.comp3102.backend.core.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ColorServiceTest {

    private ColorRepository colorRepository;
    private Color2ColorResponseDtoConverter color2ColorResponseDtoConverter;
    private ColorRequestDto2ColorConverter colorRequestDto2ColorConverter;
    private ColorService colorService;

    @BeforeEach
    void setUp() {
        colorRepository = mock(ColorRepository.class);
        color2ColorResponseDtoConverter = mock(Color2ColorResponseDtoConverter.class);
        colorRequestDto2ColorConverter = mock(ColorRequestDto2ColorConverter.class);
        colorService = new ColorService(colorRepository, color2ColorResponseDtoConverter, colorRequestDto2ColorConverter);
    }

    public Color createColor(int id, String name) {
        return new Color(id, name);
    }

    public ColorResponseDto createColorResponseDto(int id, String name) {
        return new ColorResponseDto(id, name);
    }

    public ColorRequestDto createColorRequestDto(String name) {
        return new ColorRequestDto(name);
    }

    @Test
    @DisplayName("Get All Colors")
    public void testGetAllColors_whenColorColorExistsInDB_shouldReturnColors() {
        List<Color> colors = Arrays.asList(
                createColor(1, "Color 1"),
                createColor(2, "Color 2")
        );
        List<ColorResponseDto> colorResponseDtos = Arrays.asList(
                createColorResponseDto(1, "Color 1"),
                createColorResponseDto(2, "Color 2")
        );

        when(colorRepository.findAll()).thenReturn(colors);
        when(color2ColorResponseDtoConverter.convert(colors)).thenReturn(colorResponseDtos);

        List<ColorResponseDto> result = colorService.getAll();

        assertEquals(colorResponseDtos, result);
    }

    @Test
    @DisplayName("Add New Color Should Save")
    public void testAddNewColor_whenColorNameDoesNotExist_shouldSaveNewColor() {
        Color color = createColor(1, "color");
        ColorRequestDto colorRequestDto = createColorRequestDto("color");
        ColorResponseDto colorResponseDto = createColorResponseDto(1, "color");

        when(colorRepository.findByColorName("color")).thenReturn(Optional.empty());
        when(colorRepository.save(color)).thenReturn(color);
        when(colorRequestDto2ColorConverter.convert(colorRequestDto)).thenReturn(color);
        when(color2ColorResponseDtoConverter.convert(color)).thenReturn(colorResponseDto);

        ColorResponseDto result = colorService.addNewColor(colorRequestDto);

        assertEquals(colorResponseDto, result);
    }

    @Test
    @DisplayName("Add New Color Throws Entity Already Exists Exception")
    public void testAddNewColor_whenColorNameDoesExists_shouldReturnEntityAlreadyExistsException() {
        Color color = createColor(1, "color");
        ColorRequestDto colorRequestDto = createColorRequestDto("color");

        when(colorRepository.findByColorName("color")).thenReturn(Optional.of(color));

        assertThrows(EntityAlreadyExistsException.class, () -> colorService.addNewColor(colorRequestDto));
        Mockito.verifyNoInteractions(colorRequestDto2ColorConverter, color2ColorResponseDtoConverter);
    }

    @Test
    @DisplayName("Get Color By Id")
    public void testGetColorById_whenColorIdExistsInDB_shouldReturnColor() {
        Color color = createColor(1, "color");
        ColorResponseDto colorResponseDto = createColorResponseDto(1, "color");

        when(colorRepository.findById(1)).thenReturn(Optional.of(color));
        when(color2ColorResponseDtoConverter.convert(color)).thenReturn(colorResponseDto);

        ColorResponseDto result = colorService.getColorById(1);

        assertEquals(colorResponseDto, result);
    }

    @Test
    @DisplayName("Get Color By Id Should Throw Entity Not Found Exception")
    public void testGetByColorId_whenColorIdDoesNotExist_shouldThrowEntityNotFoundException() {
        when(colorRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> colorService.getColorById(1));

        Mockito.verifyNoInteractions(colorRequestDto2ColorConverter, color2ColorResponseDtoConverter);
    }

    @Test
    @DisplayName("Get Color By Name")
    public void testGetColorByName_whenColorNameExistsInDB_shouldReturnColor() {
        Color color = createColor(1, "color");
        ColorResponseDto colorResponseDto = createColorResponseDto(1, "color");

        when(colorRepository.findByColorName("color")).thenReturn(Optional.of(color));
        when(color2ColorResponseDtoConverter.convert(color)).thenReturn(colorResponseDto);

        ColorResponseDto result = colorService.getColorByName("color");

        assertEquals(colorResponseDto, result);
    }

    @Test
    @DisplayName("Get Color By Name Should Throw Entity Not Found Exception")
    public void testGetByColorName_whenColorNameDoesNotExist_shouldThrowEntityNotFoundException() {
        when(colorRepository.findByColorName("color")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> colorService.getColorByName("color"));
        Mockito.verifyNoInteractions(colorRequestDto2ColorConverter, color2ColorResponseDtoConverter);
    }

    @Test
    @DisplayName("Delete Color By Id")
    public void testDeleteColorById_whenColorIdExistsInDB_shouldDeleteColor() {
        int id = 1;
        Color color = createColor(id, "color");

        when(colorRepository.findById(id)).thenReturn(Optional.of(color));

        colorService.deleteColor(id);

        verify(colorRepository).findById(id);
        verify(colorRepository).deleteById(id);
    }

    @Test
    @DisplayName("Delete Color By Id Should Throw Entity Not Found Exception")
    public void testDeleteByColorId_whenColorIdDoesNotExist_shouldThrowEntityNotFoundException() {
        when(colorRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> colorService.getColorByName("color"));
        Mockito.verifyNoInteractions(colorRequestDto2ColorConverter, color2ColorResponseDtoConverter);
    }

    @Test
    @DisplayName("Update Color By Id")
    public void testUpdateColor_whenColorExistsInDB_shouldUpdateColor() {
        int id = 1;
        ColorRequestDto colorRequestDto = createColorRequestDto("color");
        ColorResponseDto colorResponseDto = createColorResponseDto(id, "color");
        Color color = createColor(id, "color");

        when(colorRepository.findByColorName("color")).thenReturn(Optional.empty());
        when(colorRepository.findById(id)).thenReturn(Optional.of(color));
        when(colorRepository.save(any(Color.class))).thenReturn(color);
        when(colorRequestDto2ColorConverter.convert(id, colorRequestDto)).thenReturn(color);
        when(color2ColorResponseDtoConverter.convert(color)).thenReturn(colorResponseDto);


        ColorResponseDto result = colorService.updateColor(id, colorRequestDto);

        assertNotNull(result);
        assertEquals(colorResponseDto, result);
    }

    @Test
    @DisplayName("Update Color By Id Should Throw Entity Not Found Exception")
    public void testUpdateColorById_whenColorIdDoesNotExist_shouldThrowEntityNotFoundException() {
        Integer id = 1;
        ColorRequestDto colorRequestDto = new ColorRequestDto("color");

        when(colorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> colorService.updateColor(id, colorRequestDto));
        Mockito.verifyNoInteractions(colorRequestDto2ColorConverter, color2ColorResponseDtoConverter);
    }


    @Test
    @DisplayName("Update Color By Id With Existent Name Should Throw Entity Already Exists Exception")
    public void testUpdateColor_whenColorNameDoesExists_shouldThrowEntityAlreadyExistsException() {

        Color color = createColor(1, "color");

        ColorRequestDto colorRequestDto = createColorRequestDto("color");

        when(colorRepository.findById(1)).thenReturn(Optional.of(color));
        when(colorRepository.findByColorName("color")).thenReturn(Optional.of(color));

        assertThrows(EntityAlreadyExistsException.class, () -> colorService.updateColor(1, colorRequestDto));
        Mockito.verifyNoInteractions(colorRequestDto2ColorConverter, color2ColorResponseDtoConverter);
    }

}