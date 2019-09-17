package com.maxzuo.lucene.example;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Lucene 简单使用
 * <p>
 * Created by zfh on 2019/09/17
 */
public class LuceneUseExample {

    /**
     * 创建索引
     * <pre>
     *   1 创建文档对象
     *   2 创建存储目录
     *   3 创建分词器
     *   4 创建索引写入器的配置对象
     *   5 创建索引写入器对象
     *   6 将文档交给索引写入器
     *   7 提交
     *   8 关闭
     * </pre>
     */
    @Test
    public void testCreateTable () {
        // 1.创建文档对象
        Document document = new Document();
        /*
            Field（字段类）
              1）一个Document中可以有很多个不同的字段，每一个字段都是一个Field类的对象。Field类就提供了各种不同类型的子类。
              2）DoubleField、FloatField、IntField、LongField、StringField、TextField这些子类一定会被创建索引，但是
                不会被分词，而且不一定会被存储到文档列表。要通过构造函数中的参数Store来指定：Store.YES代表存储到文档列表
                存储，Store.NO代表不存储。如果不分词，会造成整个字段作为一个词条，除非用户完全匹配，否则搜索不到。
              3）TextField即创建索引，又会被分词。
              4）StoredField一定会被存储，但是一定不创建索引

              其中：
                1.如果一个字段要显示到最终的结果中，那么一定要存储，否则就不存储
                2.如果要根据这个字段进行搜索，那么这个字段就必须创建索引。
                3.如何确定一个字段是否需要分词，前提是这个字段首先要创建索引。然后如果这个字段的值是不可分割的，那么就不需要分词。
         */
        StringField field = new StringField("id", "1", Field.Store.YES);
        document.add(field);
        document.add(new TextField("title", "谷歌地图之父跳槽facebook", Field.Store.YES));
        try {
            /*
                Directory（目录类）
                  指定索引在硬盘中的位置
                  - FSDirectory：文件系统目录，会把索引库指向本地磁盘。
                  - RAMDirectory：内存目录，会把索引库保存在内存。
             */
            File file = new File("/Users/dazuo/workplace/bulb/bulb-third-tool/target");
            FSDirectory directory = FSDirectory.open(file.toPath());
            /*
                Analyzer（分词器类）
                  提供分词算法，可以把文档中的数据按照算法分词
                  - StandardAnalyzer：单字分词：就是按照中文一个字一个字地进行分词。如：“我爱中国”，效果：“我”、“爱”、“中”、“国”。
                  - CJKAnalyzer：二分法分词：按两个字进行切分。如：“我是中国人”，效果：“我是”、“是中”、“中国”“国人”。
                  - IKAnalyzer：第三方分词器，很久没有维护
             */
            StandardAnalyzer analyzer = new StandardAnalyzer();
            // 4.索引写出工具的配置对象
            /*
                IndexWriterConfig（索引写出器配置类）
                  1）设置配置信息：Lucene的版本和分词器类型
                  2）设置是否清空索引库中的数据
             */
            IndexWriterConfig conf = new IndexWriterConfig(analyzer);
            // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
            conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            /*
                IndexWriter（索引写出器类）
                  1）索引写出工具，作用就是 实现对索引的增（创建索引）、删（删除索引）、改（修改索引）
                  2）可以一次创建一个，也可以批量创建索引
             */
            IndexWriter indexWriter = new IndexWriter(directory, conf);
            // 6.把文档集合交给IndexWriter
            indexWriter.addDocument(document);
            // 7.提交
            indexWriter.commit();
            // 8.关闭
            indexWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量创建索引
     */
    @Test
    public void testBatchIndex () {
        // 创建文档的集合
        Collection<Document> docs = new ArrayList<>();
        Document document1 = new Document();
        document1.add(new StringField("id", "1", Field.Store.YES));
        document1.add(new TextField("title", "谷歌地图之父跳槽facebook", Field.Store.YES));
        docs.add(document1);

        Document document2 = new Document();
        document2.add(new StringField("id", "2", Field.Store.YES));
        document2.add(new TextField("title", "谷歌地图之父加盟FaceBook", Field.Store.YES));
        docs.add(document2);

        Document document3 = new Document();
        document3.add(new StringField("id", "3", Field.Store.YES));
        document3.add(new TextField("title", "谷歌地图创始人拉斯离开谷歌加盟Facebook", Field.Store.YES));
        docs.add(document3);

        Document document4 = new Document();
        document4.add(new StringField("id", "4", Field.Store.YES));
        document4.add(new TextField("title", "谷歌地图之父跳槽Facebook与Wave项目取消有关", Field.Store.YES));
        docs.add(document4);

        Document document5 = new Document();
        document5.add(new StringField("id", "5", Field.Store.YES));
        document5.add(new TextField("title", "谷歌地图之父拉斯加盟社交网站Facebook", Field.Store.YES));
        docs.add(document5);

        try {
            // 创建索引目录
            File file = new File("/Users/dazuo/workplace/bulb/bulb-third-tool/target");
            Directory directory = FSDirectory.open(file.toPath());

            // 分词器
            StandardAnalyzer analyzer = new StandardAnalyzer();

            // 写出工具
            IndexWriterConfig conf = new IndexWriterConfig(analyzer);
            conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter indexWriter = new IndexWriter(directory, conf);
            indexWriter.addDocuments(docs);
            indexWriter.commit();
            indexWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改索引
     */
    @Test
    public void testUpdateIndex () {
        try {
            File file = new File("/Users/dazuo/workplace/bulb/bulb-third-tool/target");
            FSDirectory directory = FSDirectory.open(file.toPath());

            // 创建配置对象
            IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
            // 创建索引写出工具
            IndexWriter writer = new IndexWriter(directory, conf);

            // 创建新的文档数据
            Document doc = new Document();
            doc.add(new StringField("id", "1", Field.Store.YES));
            doc.add(new TextField("title", "谷歌地图之父跳槽facebook ", Field.Store.YES));
            /*
                修改索引，参数：
                  词条：根据这个词条匹配到的所有文档都会被修改
                  文档信息：要修改的新的文档数据
             */
            writer.updateDocument(new Term("id", "1"), doc);
            // 提交
            writer.commit();
            // 关闭
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除索引
     */
    @Test
    public void testDeleteIndex () {
        try {
            File file = new File("/Users/dazuo/workplace/bulb/bulb-third-tool/target");
            FSDirectory directory = FSDirectory.open(file.toPath());

            IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
            IndexWriter writer = new IndexWriter(directory, conf);

            /// 根据query对象删除,如果ID是数值类型，那么我们可以用数值范围查询锁定一个具体的ID
            // Query query = NumericRangeQuery.newLongRange("id", 2L, 2L, true, true);
            // writer.deleteDocuments(query);
            /// 删除所有
            // writer.deleteAll();

            // 根据词条进行删除
            writer.deleteDocuments(new Term("id", "1"));

            // 提交
            writer.commit();
            // 关闭
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询索引数据
     * <pre>
     *   1 创建读取目录对象
     *   2 创建索引读取工具
     *   3 创建索引搜索工具
     *   4 创建查询解析器
     *   5 创建查询对象
     *   6 搜索数据
     *   7 各种操作
     * </pre>
     */
    @Test
    public void testSearchIndex () {
        try {
            // 索引目录
            File file = new File("/Users/dazuo/workplace/bulb/bulb-third-tool/target");
            Directory directory = FSDirectory.open(file.toPath());
            // 索引读取工具
            IndexReader reader = DirectoryReader.open(directory);
            // 索引搜索工具：实现快速搜索、排序、打分等功能。
            IndexSearcher searcher = new IndexSearcher(reader);
            /*
                QueryParser（单一字段的查询解析器）
                MultiFieldQueryParser（多字段的查询解析器）
             */
            QueryParser parser = new QueryParser("title", new StandardAnalyzer());
            /*
                查询对象
                  - 创建词条查询对象：new TermQuery(new Term("title", "谷歌地图"));
                  - 通配符查询：new WildcardQuery(new Term("title", "*歌*"));
                  - 模糊查询：new FuzzyQuery(new Term("title","facevool"),1);
                  - 数值范围查询：NumericRangeQuery.newLongRange("id", 2L, 2L, true, true);
                  - 组合查询：new BooleanQuery();
                  - 自定义查询对象：CustomScoreQuery
             */
            Query query = parser.parse("跳槽");

            // 搜索数据,两个参数：查询条件对象要查询的最大结果条数
            // 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
            TopDocs topDocs = searcher.search(query, 10);
            System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
            // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 取出文档编号
                int docID = scoreDoc.doc;
                // 根据编号去找文档
                Document doc = reader.document(docID);
                System.out.println("id: " + doc.get("id"));
                System.out.println("title: " + doc.get("title"));
                // 取出文档得分
                System.out.println("得分： " + scoreDoc.score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
