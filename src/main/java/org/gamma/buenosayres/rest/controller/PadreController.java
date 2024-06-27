package org.gamma.buenosayres.rest.controller;

import java.util.UUID;

import org.gamma.buenosayres.dto.PadreDTO;
import org.gamma.buenosayres.mapper.PadreMapper;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.gamma.buenosayres.service.implementation.PadreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/padres")
@CrossOrigin(origins = "*")
public class PadreController {
    private PadreService padreService;
    private PadreMapper padreMapper;

    public PadreController(PadreService padreService, PadreMapper padreMapper) {
        this.padreService = padreService;
        this.padreMapper = padreMapper;
    }

    @GetMapping
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(padreService.read().stream().map(padreMapper::map).toList());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PadreDTO padreDTO) {
        try {
            return ResponseEntity.ok(padreService.create(padreMapper.map(padreDTO)));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") UUID idPadre) {
        try {
            return ResponseEntity.ok(padreMapper.map(padreService.read(idPadre)));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") UUID idPadre, @RequestBody PadreDTO padreDTO) {
        padreDTO.setId(idPadre);
        return ResponseEntity.ok(padreService.update(padreMapper.map(padreDTO)));
    }
}
