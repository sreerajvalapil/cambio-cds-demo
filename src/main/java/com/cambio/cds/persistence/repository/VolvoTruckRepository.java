package com.cambio.cds.persistence.repository;


import com.cambio.cds.persistence.entity.VolvoTruck;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolvoTruckRepository  extends ElasticsearchRepository<VolvoTruck,String> {

    @Override
    VolvoTruck save(VolvoTruck volvoTruck);

    VolvoTruck findByModelId(String modelId);
}
