package eu.virac.vea_izi_vsrc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.SubCategory;
import eu.virac.vea_izi_vsrc.repo.ISubCategoryRepo;
import eu.virac.vea_izi_vsrc.service.ISubCategoryFilterService;

@Service
public class SubCategoryFilterServiceImpl implements ISubCategoryFilterService {

    @Autowired
    private ISubCategoryRepo subCategoryRepo;

    @Override
    public List<SubCategory> filterByKeyword(String keyword) {
        return subCategoryRepo.findByTitleContainingIgnoreCase(keyword);
    }
}