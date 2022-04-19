package com.df.dao;

import com.df.pojo.Sku;

import java.util.List;

public interface SkuDao {
    /**
     * 查询所有库存表数据
     * @return
     */
    public List<Sku> querySkuList();

}
