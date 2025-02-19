package com.flat.Service;

import com.flat.entity.Complaints;
import com.flat.payload.ComplaintDto;

import java.util.List;

public interface ComplaintService {
    Complaints fileComplaint(ComplaintDto complaintDTO);
    List<Complaints> getAllComplaints();
    Complaints getComplaintById(Long id);
    void deleteComplaint(Long id);
}
