package com.souls.titanic.repo;

import com.souls.titanic.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PassengerRepo extends JpaRepository<Passenger,Long> {
    Page<Passenger> findAll(Pageable pageable);
}
