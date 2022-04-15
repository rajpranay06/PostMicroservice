package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.Community;

public interface ICommunityRepository extends JpaRepository<Community, Integer> {

}
