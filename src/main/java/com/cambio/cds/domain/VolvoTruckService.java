package com.cambio.cds.domain;

import com.cambio.cds.persistence.entity.VolvoTruck;
import com.cambio.cds.persistence.repository.VolvoTruckRepository;
import com.cambio.cds.rest.dto.TruckDataModel;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Service
public class VolvoTruckService {

    final VolvoTruckRepository volvoTruckRepository ;

    public VolvoTruckService(VolvoTruckRepository volvoTruckRepository) {
        this.volvoTruckRepository = volvoTruckRepository;
    }

    public String persistTruckData(TruckDataModel truckDataModel) {

        VolvoTruck volvoTruck =
                VolvoTruck.builder().modelId(truckDataModel.getModelId())
                .kmDriven(truckDataModel.getKmDriven())
                .regionName(truckDataModel.getRegionName())
                .regionCode(truckDataModel.getRegionCode())
                .startTime(truckDataModel.getStartTime())
                .firstServiceTime(truckDataModel.getFirstServiceTime())
                .nextServiceDue(truckDataModel.getNextServiceDue())
                .build() ;
        VolvoTruck truck  = volvoTruckRepository.save(volvoTruck) ;
        return volvoTruckRepository.findByModelId(truck.getModelId()).getModelId();

    }
}
