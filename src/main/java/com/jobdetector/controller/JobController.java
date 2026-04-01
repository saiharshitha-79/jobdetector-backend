package com.jobdetector.controller;

import com.jobdetector.entity.Job;
import com.jobdetector.repository.JobRepository;
import com.jobdetector.service.JobAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class JobController {
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private JobAnalysisService jobAnalysisService;
    
    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeJob(@RequestBody Job job) {
        try {
            boolean validEmail = job.getEmail() != null && job.getEmail().contains("@") && job.getEmail().split("@", -1).length == 2 && !job.getEmail().split("@", -1)[0].isBlank() && !job.getEmail().split("@", -1)[1].isBlank();
            if (job == null || job.getCompanyName() == null || job.getCompanyName().isBlank() || !validEmail || job.getSalary() == null || job.getSalary().isNaN() || job.getSalary() <= 0 || job.getDescription() == null || job.getDescription().isBlank()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Job analysis failed: companyName, valid email, positive salary, and description are required.");
                return ResponseEntity.badRequest().body(response);
            }
            
            Job analyzedJob = jobAnalysisService.analyzeJob(job);
            
            Job savedJob = jobRepository.save(analyzedJob);
            
            Map<String, Object> response = new HashMap<>();
            response.put("riskScore", savedJob.getRiskScore());
            response.put("status", savedJob.getStatus());
            response.put("jobId", savedJob.getId());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Job analysis failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllJobs() {
        try {
            List<Job> jobs = jobRepository.findAll();
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to fetch jobs: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
