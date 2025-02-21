package com.flat.Service;

import com.flat.entity.Complaints;


import java.util.List;

public interface ComplaintService {
    Complaints fileComplaint(Complaints complaints);
    List<Complaints> getAllComplaints();
    Complaints getComplaintById(Long id);
    void deleteComplaint(Long id);
}
