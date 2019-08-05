package com.liupeng.sell.service;


import com.liupeng.sell.dataObject.ProductCategory;

import java.util.List;

/**
 * 类目Service
 * @author liufeifei
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
