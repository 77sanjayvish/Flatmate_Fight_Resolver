package com.flat.controller;

import com.flat.Service.ComplaintService;
import com.flat.entity.Complaints;
import com.flat.entity.User;
import com.flat.payload.ComplaintDto;
import com.flat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<Complaints> createComplaint(@RequestBody Complaints complaint) {
        // Check if filedBy is not null
        if (complaint.getFiledBy() != null) {
            User userId = complaint.getFiledBy();
            User user = (User) userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            complaint.setFiledBy(user); // Set the User entity before saving
        } else {
            throw new RuntimeException("FiledBy (User) is required");
        }
        Complaints savedComplaint = complaintService.fileComplaint(complaint);
        return ResponseEntity.ok(savedComplaint);
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
