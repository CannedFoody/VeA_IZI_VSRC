package eu.virac.vea_izi_vsrc.service.impl;


import eu.virac.vea_izi_vsrc.model.SubCategory;
import eu.virac.vea_izi_vsrc.repo.ISubCategoryRepo;
import eu.virac.vea_izi_vsrc.service.ICRUDSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CRUDSubCategoryServiceImpl implements ICRUDSubCategoryService {

    @Autowired
    private ISubCategoryRepo sub_cat_repo;

    @Override
    public SubCategory createNewSubCategory(SubCategory sub_category) throws Exception {
        if(sub_category == null){
            throw new Exception("Passed SubCategory is null...");
        }

        if(sub_cat_repo.existsById(sub_category.getIdSubcategory())){
            throw new Exception("SubCategory with that ID already exists...");
        }

        if(sub_category.getTitle().isEmpty() || sub_category.getDescription().isEmpty()){
            throw new Exception("SubCategory title or description field is empty...");
        }

        return  sub_cat_repo.save(sub_category);
    }

    @Override
    public ArrayList<SubCategory> getAllSubCategories() throws Exception {
        if(sub_cat_repo.count() == 0){
            throw new Exception("SubCategory repo is empty...");
        }

        ArrayList<SubCategory> returned_list = (ArrayList<SubCategory>) sub_cat_repo.findAll();

        return returned_list;
    }

    @Override
    public SubCategory getSubCategoryById(long idSubCategory) throws Exception {
        if (idSubCategory < 1) {
            throw new Exception("Invalid SubCategory ID passed...");
        }

        if(sub_cat_repo.count() == 0){
            throw new Exception("SubCategory repo is empty...");
        }

        if(!sub_cat_repo.existsById(idSubCategory)){
            throw new Exception("No SubCategory exists by that ID...");
        }

        return sub_cat_repo.findById(idSubCategory).get();
    }

    @Override
    public SubCategory updateSubCategory(long idSubCategory, String title, String description) throws Exception {
        if(!sub_cat_repo.existsById(idSubCategory)){
            throw new Exception("No SubCategory with that ID exists...");
        }

        if(idSubCategory < 1){
            throw new Exception("Incorrect SubCategory ID passed...");
        }

        if(title == null || description == null){
            throw new Exception("Incorrect SubCategory input data...");
        }

        SubCategory sub_category_to_update = sub_cat_repo.findById(idSubCategory).get();

        if(!sub_category_to_update.getTitle().equals(title)){
            sub_category_to_update.setTitle(title);
        }

        if(!sub_category_to_update.getDescription().equals(description)){
            sub_category_to_update.setDescription(description);
        }

        return sub_cat_repo.save(sub_category_to_update);
    }

    @Override
    public void deleteSubCategory(long idSubCategory) throws Exception {
        if(idSubCategory < 1){
            throw new Exception("Invalid SubCategory ID passed...");
        }

        if(sub_cat_repo.existsById(idSubCategory)){
            sub_cat_repo.deleteById(idSubCategory);
        }
        else{
            throw new Exception("No SubCategory exists by that ID...");
        }
    }
}
