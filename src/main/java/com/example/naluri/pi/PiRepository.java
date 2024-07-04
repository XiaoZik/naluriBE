package com.example.naluri.pi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiRepository extends JpaRepository<Pi, Integer> {

}
