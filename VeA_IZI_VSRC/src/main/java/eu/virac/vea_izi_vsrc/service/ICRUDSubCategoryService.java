package eu.virac.vea_izi_vsrc.service;

import eu.virac.vea_izi_vsrc.model.SubCategory;

import java.util.ArrayList;

public interface ICRUDSubCategoryService {
    public abstract SubCategory createNewSubCategory(SubCategory sub_category) throws Exception;

    public abstract ArrayList<SubCategory> getAllSubCategories()throws Exception;

    public abstract SubCategory getSubCategoryById(long idSubCategory)throws Exception;

    public abstract SubCategory updateSubCategory(long idSubCategory, String title, String description)throws Exception;

    public abstract void deleteSubCategory(long idSubCategory)throws Exception;
}