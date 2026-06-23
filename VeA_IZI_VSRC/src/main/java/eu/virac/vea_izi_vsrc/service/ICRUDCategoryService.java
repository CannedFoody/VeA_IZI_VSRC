package eu.virac.vea_izi_vsrc.service;

import java.util.ArrayList;

import eu.virac.vea_izi_vsrc.model.Category;

public interface ICRUDCategoryService {

	public abstract Category createNewCategory(Category category) throws Exception;
	
	public abstract ArrayList<Category> getAllCategories() throws Exception;
	
	public abstract Category getCategoryById(long idCategory) throws Exception;
	
	public abstract Category updateCategory(long idCategory, String name, String description) throws Exception;
	
	public abstract void deleteCategory(long idCategory) throws Exception;
}
