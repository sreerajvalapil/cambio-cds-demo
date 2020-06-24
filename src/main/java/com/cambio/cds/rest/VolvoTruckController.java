package com.cambio.cds.rest;

import com.cambio.cds.domain.VolvoTruckService;
import com.cambio.cds.rest.dto.TruckDataModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/volvo")
public class VolvoTruckController {

    private final VolvoTruckService volvoTruckService;

    public VolvoTruckController(VolvoTruckService volvoTruckService) {
        this.volvoTruckService = volvoTruckService;
    }

    @PostMapping(value = "/uploadTruckData")
    public ResponseEntity<String> uploadTruckData(@RequestBody TruckDataModel truckDataModel)  {
        String modelId = volvoTruckService.persistTruckData(truckDataModel);
        String response = "Successfully saved the data forTruck : " + modelId ;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
