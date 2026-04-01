package com.jobdetector.service;

import com.jobdetector.entity.Job;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JobAnalysisService {
    
    private static final List<String> SUSPICIOUS_KEYWORDS = Arrays.asList(
        "urgent", "quick money", "no experience", "work from home"
    );
    
    private static final List<String> SUSPICIOUS_EMAIL_DOMAINS = Arrays.asList(
        "gmail.com", "yahoo.com"
    );
    
    public Job analyzeJob(Job job) {
        int riskScore = 0;
        
        // Rule 1: High salary risk
        if (job.getSalary() > 100000) {
            riskScore += 2;
        }
        
        // Rule 2: Suspicious email domains
        String emailDomain = job.getEmail().split("@")[1].toLowerCase();
        if (SUSPICIOUS_EMAIL_DOMAINS.contains(emailDomain)) {
            riskScore += 2;
        }
        
        // Rule 3: Suspicious keywords in description
        String description = job.getDescription().toLowerCase();
        for (String keyword : SUSPICIOUS_KEYWORDS) {
            if (description.contains(keyword)) {
                riskScore += 3;
                break; // Only add once even if multiple keywords match
            }
        }
        
        // Classification based on risk score
        String status;
        if (riskScore >= 5) {
            status = "FAKE";
        } else if (riskScore >= 3) {
            status = "SUSPICIOUS";
        } else {
            status = "SAFE";
        }
        
        job.setRiskScore(riskScore);
        job.setStatus(status);
        
        return job;
    }
}
