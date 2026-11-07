package eu.virac.vea_izi_vsrc.service;

import java.util.List;

import eu.virac.vea_izi_vsrc.model.SubCategory;

public interface ISubCategoryFilterService {
    List<SubCategory> filterByKeyword(String keyword);

}