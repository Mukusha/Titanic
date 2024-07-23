package com.souls.titanic.repo;

import com.souls.titanic.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepo extends JpaRepository<Passenger, Long> {
    Page<Passenger> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM passenger " +
            "WHERE (?1 is null OR upper(name) like UPPER(CONCAT('%', ?1, '%')) ) AND" +
            "(?2 = false OR survived = true) AND" +
            "(?3 = false OR age >= 16.0) AND" +
            "(?4 = false OR sex = 'male') AND" +
            "(?5 = false OR (siblings_or_spouses_aboard = 0 AND parents_or_children_aboard= 0))",
            nativeQuery = true
    )
    Page<Passenger> findByNameSort(String name, Boolean showSurvivesPassengers, Boolean showAdultPassengers, Boolean showMalePassengers, Boolean showWithoutRelatives, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM passenger " +
            "WHERE (?1 is null OR upper(name) like UPPER(CONCAT('%', ?1, '%')) ) AND" +
            "(?2 = false OR survived = true) AND" +
            "(?3 = false OR age >= 16.0) AND" +
            "(?4 = false OR sex = 'male') AND" +
            "(siblings_or_spouses_aboard > 0 OR parents_or_children_aboard > 0)",
            nativeQuery = true
    )
    Integer countPassengersWithRelatives(String name, Boolean showSurvivesPassengers, Boolean showAdultPassengers, Boolean showMalePassengers);

    @Query(value = "SELECT COUNT(*) FROM passenger " +
            "WHERE (?1 is null OR upper(name) like UPPER(CONCAT('%', ?1, '%')) ) AND " +
            "(survived = true) AND" +
            "(?2 = false OR age >= 16.0) AND" +
            "(?3 = false OR sex = 'male') AND" +
            "(?4 = false OR (siblings_or_spouses_aboard = 0 AND parents_or_children_aboard= 0))",
            nativeQuery = true
    )
    Integer countSurvivors(String name, Boolean showAdultPassengers, Boolean showMalePassengers, Boolean showWithoutRelatives);

    @Query(value = "SELECT SUM(fare) FROM passenger " +
            "WHERE (?1 is null OR upper(name) like UPPER(CONCAT('%', ?1, '%')) ) AND" +
            "(?2 = false OR survived = true) AND" +
            "(?3 = false OR age >= 16.0) AND" +
            "(?4 = false OR sex = 'male') AND" +
            "(?5 = false OR (siblings_or_spouses_aboard = 0 AND parents_or_children_aboard= 0))",
            nativeQuery = true
    )
    Double sumFare(String name, Boolean showSurvivesPassengers, Boolean showAdultPassengers, Boolean showMalePassengers, Boolean showWithoutRelatives);

}
