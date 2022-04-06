package com.example.dao;

import com.example.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * ClassName:ProductDao
 * Package:com.example.dao
 * Description: 没有实际连数据库，仅是模拟
 *
 * @Date:2022/4/2 9:10
 * @Author:qs@1.com
 */
@Repository
public class ProductDao {
    public Product create(Product product) {
        System.out.println("创建商品成功");
        return product;
    }

    public Product update(Product product) {
        System.out.println("修改商品成功");
        return product;
    }

    public Product get(Long productId) {
        System.out.println("查询商品成功");
        Product product = new Product();
        product.setId(productId);
        product.setName("test");
        return product;
    }
}
