package com.souls.titanic.repo;

import com.souls.titanic.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PassengerRepo extends JpaRepository<Passenger,Long> {
    Page<Passenger> findAll(Pageable pageable);

    Page<Passenger> findAllByOrderByNameAsc(Pageable pageable);
    Page<Passenger> findAllByOrderByNameDesc(Pageable pageable);
    Page<Passenger> findAllByOrderByAgeAsc(Pageable pageable);
    Page<Passenger> findAllByOrderByAgeDesc(Pageable pageable);
    Page<Passenger> findAllByOrderByFareAsc(Pageable pageable);
    Page<Passenger> findAllByOrderByFareDesc(Pageable pageable);
}
