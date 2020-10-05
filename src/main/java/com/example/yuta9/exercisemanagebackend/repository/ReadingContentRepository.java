package com.example.yuta9.exercisemanagebackend.repository;

import com.example.yuta9.exercisemanagebackend.model.entity.ReadingContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingContentRepository extends JpaRepository<ReadingContent, Long> {}
