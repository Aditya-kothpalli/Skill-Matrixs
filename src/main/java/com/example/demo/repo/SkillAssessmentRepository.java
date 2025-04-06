package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SkillAssessment;
import com.example.demo.model.Users;

public interface SkillAssessmentRepository extends JpaRepository<SkillAssessment, Long> {

	List<SkillAssessment> findByUser(Users user);}