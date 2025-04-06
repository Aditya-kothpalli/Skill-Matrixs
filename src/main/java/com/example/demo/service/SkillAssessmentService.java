package com.example.demo.service;





import com.example.demo.DTO.SkillAssessmentDTO;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.Skill;
import com.example.demo.model.SkillAssessment;

import com.example.demo.model.Users;
import com.example.demo.repo.RoleRepository;
import com.example.demo.repo.SkillAssessmentRepository;
import com.example.demo.repo.SkillRepository;
import com.example.demo.repo.UserRepo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillAssessmentService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private SkillRepository skillRepo;

    @Autowired
    private SkillAssessmentRepository assessmentRepo;

    public String saveAssessment(String email, SkillAssessmentDTO dto) {
        Users user = userRepo.findByEmail(email);

        if (user.getUserProfile() == null || !user.getUserProfile().isProfileSubmitted()) {
            return "Please submit your profile before skill assessment.";
        }

        RoleEntity role = roleRepo.findByRoleName(dto.getRoleName());
        if (role == null) {
            return "Role not found.";
        }

        List<Skill> roleSkills = role.getSkills();

        for (String submittedSkillName : dto.getSkillLevels().keySet()) {
            boolean exists = roleSkills.stream()
                    .anyMatch(skill -> skill.getSkillName().equalsIgnoreCase(submittedSkillName));

            if (!exists) {
                return "Skill " + submittedSkillName + " is not part of the role: " + dto.getRoleName();
            }
        }

        List<SkillAssessment> existingAssessments = assessmentRepo.findByUser(user);
        for (SkillAssessment existing : existingAssessments) {
            if (dto.getSkillLevels().containsKey(existing.getSkill().getSkillName())) {
                return "Assessment already submitted for skill: " + existing.getSkill().getSkillName();
            }
        }

        for (Map.Entry<String, String> entry : dto.getSkillLevels().entrySet()) {
            Skill skill = roleSkills.stream()
                    .filter(s -> s.getSkillName().equalsIgnoreCase(entry.getKey()))
                    .findFirst()
                    .orElse(null);

            if (skill == null) {
                return "Skill not found: " + entry.getKey();
            }

            SkillAssessment assessment = new SkillAssessment();
            assessment.setUser(user);
            assessment.setSkill(skill);
            assessment.setLevel(entry.getValue());
            assessment.setVerified(false);

            assessmentRepo.save(assessment);
        }

        return "Skill assessment submitted successfully!";
    }
}