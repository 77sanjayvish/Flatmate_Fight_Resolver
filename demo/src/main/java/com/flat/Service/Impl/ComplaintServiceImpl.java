package com.flat.Service.Impl;


import com.flat.Service.ComplaintService;
import com.flat.entity.Complaints;
import com.flat.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public Complaints fileComplaint(Complaints complaints) {
        System.out.println(complaints);
        return complaintRepository.save(complaints);
    }

    @Override
    public List<Complaints> getAllComplaints() {
        return complaintRepository.findAll();
    }

    @Override
    public Complaints getComplaintById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + id));
    }

    @Override
    public void deleteComplaint(Long id) {
        if (!complaintRepository.existsById(id)) {
            throw new RuntimeException("Complaint not found with ID: " + id);
        }
        complaintRepository.deleteById(id);
    }
}
