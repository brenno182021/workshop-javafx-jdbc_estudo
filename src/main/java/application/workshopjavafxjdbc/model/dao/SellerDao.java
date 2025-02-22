package application.workshopjavafxjdbc.model.dao;

import application.workshopjavafxjdbc.model.entities.Department;
import application.workshopjavafxjdbc.model.entities.Seller;

import java.util.List;


public interface SellerDao {

	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
}
