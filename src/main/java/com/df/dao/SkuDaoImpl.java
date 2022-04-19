package com.df.dao;

import com.df.pojo.Sku;
import org.apache.tomcat.jni.SSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SkuDaoImpl implements SkuDao{

    @Override
    public List<Sku> querySkuList() {
        // 创建数据库连接
        Connection connection = null;
        // 创建 Statement对象
        PreparedStatement preparedStatement = null;
        // 创建 结果集对象
        ResultSet resultSet = null;
        // 创建封装后的结果集列表对象
        List<Sku> skuList = new ArrayList<>();
        try {
            // 创建数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false", "root", "123abc");

            // sql 语句字符串
            String sql="select * from tb_sku";
            // 执行 sql 语句
            preparedStatement = connection.prepareStatement(sql);
            // 执行 sql 语句后返回ResultSet结果集对象
            resultSet = preparedStatement.executeQuery();
            // 遍历结果集
            while(resultSet.next()) {
                Sku sku = new Sku();
                sku.setId(resultSet.getString("id"));
                sku.setName(resultSet.getString("name"));
                sku.setSpec(resultSet.getString("spec")); // 规格属性
                sku.setBrandName(resultSet.getString("brand_name"));
                sku.setCategoryName(resultSet.getString("category_name"));
                sku.setImage(resultSet.getString("image"));
                sku.setNum(resultSet.getInt("num"));
                sku.setPrice(resultSet.getInt("price"));
                sku.setSaleNum(resultSet.getInt("sale_num"));

                skuList.add(sku);
            }
            // 提取数据封装成 sku 集合对象返回
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

        return skuList;
    }
}
