package com.flat.controller;

import com.flat.entity.Complaints;
import com.flat.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flat")
public class StatsController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getComplaintStats() {
        List<Complaints> complaints = complaintRepository.findAll();
        long totalComplaints = complaints.size();
        long resolvedComplaints = complaints.stream()
                .filter(complaint -> "RESOLVED".equals(complaint.getComplainType()))
                .count();
        long openComplaints = totalComplaints - resolvedComplaints;
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalComplaints", totalComplaints);
        stats.put("resolvedComplaints", resolvedComplaints);
        stats.put("openComplaints", openComplaints);
        return ResponseEntity.ok(stats);
    }
}