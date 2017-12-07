package com.catalog.movies.core.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(final String name);

    @Query(value ="select * from categories where fk_category_category is NULL ", nativeQuery = true)
    List<Category> getAllMainCategories();

    @Query(value ="select * from categories where fk_category_category is not NULL ", nativeQuery = true)
    List<Category> getAllSubCategories();

    @Query(value = "select  * from categories where fk_category_category= ?1" , nativeQuery = true)
    List<Category> getSubCategoryByMainCategoryId(Integer id);

    @Query(value = "select c.name from categories as c where c.id=( select b.fk_category_category from categories as b where b.id=?1)", nativeQuery = true)
    String getMainCategoryNAmeStr(Integer id);

    @Query(value = "select c.fk_category_category from categories as c where c.id=?1", nativeQuery = true)
    Integer getMainCategoryId(Integer id);

}
