package com.cg.repository;

import com.cg.model.LocationRegion;
import com.cg.model.dto.LocationRegionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRegionRepository extends JpaRepository<LocationRegion,Long> {


}
