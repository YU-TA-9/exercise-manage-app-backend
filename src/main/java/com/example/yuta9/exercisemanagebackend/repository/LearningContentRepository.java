package com.example.yuta9.exercisemanagebackend.repository;

import com.example.yuta9.exercisemanagebackend.model.entity.LearningContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningContentRepository extends JpaRepository<LearningContent, Long> {}
