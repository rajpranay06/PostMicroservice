package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.Blogger;

public interface IBloggerRepository extends JpaRepository<Blogger, Integer> {

}
