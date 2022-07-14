package com.reea.fantestic.level02.controller;

import com.reea.fantestic.level02.dto.CloudDTO;
import com.reea.fantestic.level02.dto.SkyDTO;
import com.reea.fantestic.level02.exception.SkyNotFoundException;
import com.reea.fantestic.level02.service.SkyService;
import com.reea.fantestic.level02.utils.CloudMapper;
import com.reea.fantestic.level02.utils.SkyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sky")
@Slf4j
@RequiredArgsConstructor
public class SkyController {

    private final SkyService skyService;

    @GetMapping("/{name}")
    public ResponseEntity<SkyDTO> findSkyByName(@PathVariable String name) throws SkyNotFoundException {
        SkyDTO skyDTO = SkyMapper.from(skyService.findByName(name));
        return new ResponseEntity<>(skyDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SkyDTO> createSky(@RequestBody SkyDTO skyDTO) throws SkyNotFoundException {
        SkyDTO result = SkyMapper.from(skyService.createSky(SkyMapper.from(skyDTO)));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
