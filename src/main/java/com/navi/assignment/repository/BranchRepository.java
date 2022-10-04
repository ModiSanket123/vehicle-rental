package com.navi.assignment.repository;

import com.navi.assignment.models.Branch;

public class BranchRepository extends Repository<String, Branch>{

    @Override
    public String getKey(Branch branch) {
        return branch.getBranchId();
    }
}
