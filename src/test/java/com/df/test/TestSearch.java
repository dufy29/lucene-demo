package com.df.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
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
        Query query = queryParser.parse("苹果手机");
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
}
