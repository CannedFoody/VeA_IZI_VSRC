package eu.virac.vea_izi_vsrc.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.Category;
import eu.virac.vea_izi_vsrc.repo.ICategoryRepo;
import eu.virac.vea_izi_vsrc.service.ICRUDCategoryService;

@Service
public class CRUDCategoryServiceImpl implements ICRUDCategoryService {

	@Autowired
	private ICategoryRepo categoryRepo;
	
	@Override
	public Category createNewCategory(Category category) throws Exception {
		if (category == null) {
			throw new Exception("Passed category object is null...");
		}
		if (categoryRepo.existsById(category.getIdCategory())) {
			throw new Exception("Category with that ID already exists...");
		}
		if (category.getName().isEmpty() || category.getDescription().isEmpty()) {
			throw new Exception("Category name or description field is empty...");
		}
		
		return categoryRepo.save(category);
	}

	@Override
	public ArrayList<Category> getAllCategories() throws Exception {
		if (categoryRepo.count() == 0) {
			throw new Exception("Category repo is empty...");
		}
		
		ArrayList<Category> returned_list = (ArrayList<Category>) categoryRepo.findAll();
		
		return returned_list;
	}

	@Override
	public Category getCategoryById(long idCategory) throws Exception {
		if (idCategory < 1) {
			throw new Exception("Invalid category ID passed...");
		}
		if (categoryRepo.count() == 0) {
			throw new Exception("Category repo is empty...");
		}
		if (!categoryRepo.existsById(idCategory)) {
			throw new Exception("No category exists by that ID...");
		}
		
		return categoryRepo.findById(idCategory).get();
	}

	@Override
	public Category updateCategory(long idCategory, String name, String description) throws Exception {
		if (idCategory < 1) {
			throw new Exception("Invalid category ID passed...");
		}
		if (!categoryRepo.existsById(idCategory)) {
			throw new Exception("No category exists by that ID...");
		}
		if(name == null || description == null){
            throw new Exception("Incorrect category input data...");
        }
		
		Category category_to_update = categoryRepo.findById(idCategory).get();
		
		if(!category_to_update.getName().equals(name)){
			category_to_update.setName(name);
        }
        if(!category_to_update.getDescription().equals(description)){
        	category_to_update.setDescription(description);
        }
		
		return categoryRepo.save(category_to_update);
	}

	@Override
	public void deleteCategory(long idCategory) throws Exception {
		if (idCategory < 1) {
			throw new Exception("Invalid category ID passed...");
		}
		
		if (categoryRepo.existsById(idCategory)) {
			categoryRepo.deleteById(idCategory);
		}
		else{
            throw new Exception("No category exists by that ID...");
        }
	}

}
