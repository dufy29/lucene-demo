package com.df.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class TestSearch {
    /**
     * 测试简单搜搜
     */
    @Test
    public void name() throws Exception{
        //1. 创建分词器
        StandardAnalyzer analyzer = new StandardAnalyzer();

        //2. 创建查询对象
        QueryParser queryParser = new QueryParser("name", analyzer);
        //3. 设置搜索关键词
        Query query = queryParser.parse("修改手机");
        //4. 创建Directory  目录对象，指定索引库的位置
        FSDirectory dir =FSDirectory.open(Paths.get("/Users/dufy/IdeaProjects/lucene/indexDataDemo"));

        // 5. 创建输入流
        IndexReader reader = DirectoryReader.open(dir);
        //、6. 创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        // 7. 搜索并返回结果
        TopDocs topDocs = indexSearcher.search(query, 10);
        // 8. 获得结果集
        System.out.println("======总记录数=========" + topDocs.totalHits);
        // 9. 遍历结果
        ScoreDoc[] scoreDocs= topDocs.scoreDocs;
        if (scoreDocs != null) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 获取文档唯一 ID
                int DocID = scoreDoc.doc;
                // 根据文档ID 获取文档内容
                Document doc = indexSearcher.doc(DocID);

                System.out.println("====id==="+ doc.get("id"));
                System.out.println("====name==="+ doc.get("name"));
                System.out.println("===price===="+ doc.get("price"));
                System.out.println("===image===="+ doc.get("image"));
                System.out.println("===categoryName===="+ doc.get("categoryName"));
                System.out.println("====brandName==="+ doc.get("brandName"));

                System.out.println("=====================================================");

            }
        }
        // 10 关闭资源
        reader.close();
    }

    /**
     * 修改索引库中数据
     */
    @Test
    public void updateIndexTest() throws Exception {
        // 1. 需要变更的内容
        Document doc = new Document();

        doc.add(new StringField("id", "15245462402", Field.Store.YES));
        doc.add(new TextField("name", "修改诺基亚手机名字", Field.Store.YES));
        doc.add(new IntPoint("price", 12000));
        doc.add(new StoredField("price", 12000));
        doc.add(new StringField("image", "xx.jpg", Field.Store.YES));
        doc.add(new StringField("categoryName", "手机", Field.Store.YES));
        doc.add(new StringField("brandName", "诺基亚", Field.Store.YES));

        // 2. 创建分词器
        StandardAnalyzer analyzer = new StandardAnalyzer();
        // 3. 创建 Directory 目录对象，指定 索引库位置
        Directory directory = FSDirectory.open(Paths.get("/Users/dufy/IdeaProjects/lucene/indexDataDemo"));

        // 4. 创建 IndexWriterconfig ，指定使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 5. 创建IndexWriter 输出流对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // 6. 执行修改
        // 第一参数：指定修改条件， 第二参数：修改的具体内容
        indexWriter.updateDocument(new Term("id", "15245462402"), doc);
        // 7. 释放资源
        indexWriter.close();
    }

    /**
     * 测试索引库删除
     */
    @Test
    public void deleteIndexTest() throws Exception {
        // 1. 创建分词器
        StandardAnalyzer analyzer = new StandardAnalyzer();
        // 2. 创建 Directory 目录对象，指定 索引库位置
        Directory directory = FSDirectory.open(Paths.get("/Users/dufy/IdeaProjects/lucene/indexDataDemo"));

        // 3. 创建 IndexWriterconfig ，指定使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 4. 创建IndexWriter 输出流对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // 6. 执行删除
//        indexWriter.deleteDocuments(new Term("id", "15245462402"));
        indexWriter.deleteAll();    // 危险！！
        // 7. 释放资源
        indexWriter.close();
    }

    /**
     *  控制多关键词查询
     * @throws Exception
     */
    @Test
    public void testManyWordsSearch() throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer();

        //2. 创建查询对象
        QueryParser queryParser = new QueryParser("name", analyzer);
        //3. 设置搜索关键词
        Query query = queryParser.parse("name:修改 AND 手机");  // 默认为 OR
        //4. 创建Directory  目录对象，指定索引库的位置
        FSDirectory dir = FSDirectory.open(Paths.get("/Users/dufy/IdeaProjects/lucene/indexDataDemo"));

        // 5. 创建输入流
        IndexReader reader = DirectoryReader.open(dir);
        //、6. 创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        // 7. 搜索并返回结果
        TopDocs topDocs = indexSearcher.search(query, 10);
        // 8. 获得结果集
        System.out.println("======总记录数=========" + topDocs.totalHits);
        // 9. 遍历结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        if (scoreDocs != null) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 获取文档唯一 ID
                int DocID = scoreDoc.doc;
                // 根据文档ID 获取文档内容
                Document doc = indexSearcher.doc(DocID);

                System.out.println("====id===" + doc.get("id"));
                System.out.println("====name===" + doc.get("name"));
                System.out.println("===price====" + doc.get("price"));
                System.out.println("===image====" + doc.get("image"));
                System.out.println("===categoryName====" + doc.get("categoryName"));
                System.out.println("====brandName===" + doc.get("brandName"));

                System.out.println("=====================================================");

            }
        }
        // 10 关闭资源
        reader.close();
    }


    /**
     *  根据数值范围查询
     *  需求：根据价格查询 100-1000元的商品
     * @throws Exception
     */
    @Test
    public void testRangeSearch() throws Exception {

        //1. 创建查询对象
        // 参数含义：1.域名 2. 起始值  3. 结束值
        Query query = IntPoint.newRangeQuery("price", 100, 1000);
        //2. 创建Directory  目录对象，指定索引库的位置
        FSDirectory dir = FSDirectory.open(Paths.get("/Users/dufy/IdeaProjects/lucene/indexDataDemo"));

        // 3. 创建输入流
        IndexReader reader = DirectoryReader.open(dir);
        //、4. 创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        // 7. 搜索并返回结果
        TopDocs topDocs = indexSearcher.search(query, 10);
        // 8. 获得结果集
        System.out.println("======总记录数=========" + topDocs.totalHits);
        // 9. 遍历结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        if (scoreDocs != null) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 获取文档唯一 ID
                int DocID = scoreDoc.doc;
                // 根据文档ID 获取文档内容
                Document doc = indexSearcher.doc(DocID);

                System.out.println("====id===" + doc.get("id"));
                System.out.println("====name===" + doc.get("name"));
                System.out.println("===price====" + doc.get("price"));
                System.out.println("===image====" + doc.get("image"));
                System.out.println("===categoryName====" + doc.get("categoryName"));
                System.out.println("====brandName===" + doc.get("brandName"));

                System.out.println("=====================================================");

            }
        }
        // 10 关闭资源
        reader.close();
    }

    /**
     *  组合查询
     *  需求：根据商品名字查询，查询华为手机关键字
     *      价格 100-1000
     *
     * @throws Exception
     */
    @Test
    public void testBoolSearch() throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer();

        //2. 创建查询对象
        QueryParser queryParser = new QueryParser("name", analyzer);
        //3. 创建组合查询对象
        Query query1 = queryParser.parse("name:华为 AND 手机");  // 默认为 OR
        Query query2 = IntPoint.newRangeQuery("price", 100, 500);
        BooleanQuery.Builder boolQuery = new BooleanQuery.Builder();
        boolQuery.add(query1, BooleanClause.Occur.MUST);
        boolQuery.add(query2, BooleanClause.Occur.MUST);
        // 4
        //2. 创建Directory  目录对象，指定索引库的位置
        FSDirectory dir = FSDirectory.open(Paths.get("/Users/dufy/IdeaProjects/lucene/indexDataDemo"));

        // 3. 创建输入流
        IndexReader reader = DirectoryReader.open(dir);
        //、4. 创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        // 7. 搜索并返回结果
        TopDocs topDocs = indexSearcher.search(boolQuery.build(), 10);
        // 8. 获得结果集
        System.out.println("======总记录数=========" + topDocs.totalHits);
        // 9. 遍历结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        if (scoreDocs != null) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 获取文档唯一 ID
                int DocID = scoreDoc.doc;
                // 根据文档ID 获取文档内容
                Document doc = indexSearcher.doc(DocID);

                System.out.println("====id===" + doc.get("id"));
                System.out.println("====name===" + doc.get("name"));
                System.out.println("===price====" + doc.get("price"));
                System.out.println("===image====" + doc.get("image"));
                System.out.println("===categoryName====" + doc.get("categoryName"));
                System.out.println("====brandName===" + doc.get("brandName"));

                System.out.println("=====================================================");

            }
        }
        // 10 关闭资源
        reader.close();
    }
}


