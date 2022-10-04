package com.navi.assignment.service;

import com.navi.assignment.models.Branch;
import com.navi.assignment.repository.BranchRepository;

public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }


    public boolean addBranch(Branch branch){
        if(branchRepository.containsKey(branch.getBranchId())){
            return false;
        }else {
            return branchRepository.save(branch);
        }
    }
}
