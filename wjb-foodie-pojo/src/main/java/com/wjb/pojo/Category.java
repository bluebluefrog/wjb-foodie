package com.wjb.pojo;

import javax.persistence.Column;
import javax.persistence.Id;

public class Category {
    /**
     * 分类编号
     */
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 店铺分类
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 获取分类编号
     *
     * @return category_id - 分类编号
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置分类编号
     *
     * @param categoryId 分类编号
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取店铺分类
     *
     * @return category_name - 店铺分类
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置店铺分类
     *
     * @param categoryName 店铺分类
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}