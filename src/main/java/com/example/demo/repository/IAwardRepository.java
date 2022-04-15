package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.Award;

public interface IAwardRepository extends JpaRepository<Award, Integer> {

}
