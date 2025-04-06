package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



import jakarta.persistence.*;

@Entity
@Table(name = "skill_assessment")
public class SkillAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🧑 User who submitted the skill assessment
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    // 🎯 Skill being assessed (e.g., Linux, AWS)
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    // 📊 Skill level (e.g., Beginner, Intermediate, Expert)
    private String level;

    // ✅ Verification status - to be done by SME
    private boolean isVerified = false;

    // 🕵️ You can add fields later like who verified, verifiedDate etc. if needed

    // ---------- Getters and Setters -----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
