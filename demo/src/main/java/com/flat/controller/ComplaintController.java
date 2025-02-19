package com.flat.controller;

import com.flat.Service.ComplaintService;
import com.flat.entity.Complaints;
import com.flat.payload.ComplaintDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;


    @PostMapping
    public ResponseEntity<Complaints> fileComplaint(@RequestBody ComplaintDto complaintDTO) {
        Complaints complaint = complaintService.fileComplaint(complaintDTO);
        return ResponseEntity.ok(complaint);
    }

    @GetMapping
    public ResponseEntity<List<Complaints>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Complaints> getComplaintById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.getComplaintById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.ok("Complaint deleted successfully.");
    }
}
