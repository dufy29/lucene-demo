package com.df.test;

import com.df.dao.SkuDao;
import com.df.dao.SkuDaoImpl;
import com.df.pojo.Sku;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引库维护测试
 *
 */
public class TestIndexManage {
    /**
     * 创建索引和 文档数据
     */
    @Test
    public void createIndexTest() throws Exception{
        // 1. 采集数据
        SkuDao skuDao = new SkuDaoImpl();
        List<Sku> skuList= skuDao.querySkuList();
        //2. 创建文档集合对象
        List<Document> docList = new ArrayList<>();

        //3. 创建文档对象
        // 需要搜索哪些字段，就存哪些域
        for (Sku sku : skuList) {
            Document doc =new Document();
            // 向文档对象 中加入域对象， 参数：（域名，域值， 是否存储）， 是否索引等
            // id
            doc.add(new StringField("id", sku.getId(), Field.Store.YES));
            // 名称
            doc.add(new TextField("name", sku.getName(), Field.Store.YES));
            //  价格
            doc.add(new IntPoint("price", sku.getPrice()));
            doc.add(new StoredField("price", sku.getPrice()));
            // 商品图片
            doc.add(new StringField("image", sku.getImage(), Field.Store.YES));
            // 分类名称
            doc.add(new StringField("categoryName", sku.getCategoryName(), Field.Store.YES));
            // 品牌名称
            doc.add(new StringField("brandName", sku.getBrandName(), Field.Store.YES));

            docList.add(doc);
        }

        //4. 创建分词器，默认是 StandardAnalyzer 标准分词器，只对英文分词效果比较好，对中文是单字分词
        StandardAnalyzer analyzer = new StandardAnalyzer();

        //5. 创建 Directory目录对象，指定索引库位置
        // FSDirectory,全称为 File System Directory 会将数据存储到硬盘中
        Directory dir = FSDirectory.open(Paths.get("/Users/dufy/IdeaProjects/lucene/indexDataDemo"));
        //6. 创建 IndexWriterConfig对象，指定使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //7. 创建输出流 IndexWriter 对象
        IndexWriter indexWriter = new IndexWriter(dir, config);
        // 8. 写入文档到索引库
        for (Document document : docList) {
            indexWriter.addDocument(document);
        }
        //9. 释放资源
        indexWriter.close();
    }
}
