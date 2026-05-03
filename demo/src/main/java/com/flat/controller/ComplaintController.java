package com.flat.controller;

import com.flat.Service.ComplaintService;
import com.flat.entity.Complaints;
import com.flat.entity.User;
import com.flat.payload.ComplaintDto;
import com.flat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<Complaints> createComplaint(@RequestBody ComplaintDto complaintDto) {
        if (complaintDto.getFiledByUserId() == null) {
            throw new RuntimeException("FiledBy (User) is required");
        }
        User user = (User) userRepository.findById(complaintDto.getFiledByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Complaints complaint = new Complaints();
        complaint.setTitle(complaintDto.getTitle());
        complaint.setDescription(complaintDto.getDescription());
        complaint.setComplainType(complaintDto.getComplainType());
        complaint.setSeverityLevel(complaintDto.getSeverityLevel());
        complaint.setResolved(complaintDto.isResolved());
        complaint.setLocalDateTime(complaintDto.getLocalDateTime() != null
                ? complaintDto.getLocalDateTime() : java.time.LocalDateTime.now());
        complaint.setUpVotes(complaintDto.getUpVotes());
        complaint.setDownVotes(complaintDto.getDownVotes());
        complaint.setFiledBy(user);

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
