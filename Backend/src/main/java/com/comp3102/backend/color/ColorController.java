package com.comp3102.backend.color;

import com.comp3102.backend.color.dto.request.ColorRequestDto;
import com.comp3102.backend.color.dto.response.ColorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
@CrossOrigin
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    @Operation(
            description = "Get all colors",
            summary = "Get all colors",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<ColorResponseDto>> getAll(){
        return new ResponseEntity<>(colorService.getAll(), HttpStatus.OK);
    }
    @PostMapping
    @Operation(
            description = "Add a new color",
            summary = "Add new color",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ColorResponseDto> addNewColor(@RequestBody @Valid ColorRequestDto color){
        return new ResponseEntity<>(colorService.addNewColor(color), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get a color by ID",
            summary = "Get color by ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ColorResponseDto> getColorById(@PathVariable Integer id){
        return new ResponseEntity<>(colorService.getColorById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete a color by ID",
            summary = "Delete color by ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public void deleteColor(@PathVariable Integer id){
        colorService.deleteColor(id);
    }
    @PutMapping("/{id}")
    @Operation(
            description = "Update a color by ID",
            summary = "Update color by ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ColorResponseDto> updateColor(@PathVariable Integer id, @RequestBody @Valid ColorRequestDto color){
        return new ResponseEntity<>(colorService.updateColor(id, color), HttpStatus.OK);
    }
}