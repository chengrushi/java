package HighLightSearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

class Index {
    // ��������
    public void index() {
        IndexWriter indexWriter = null;
        try {
            // ����Directory
            Directory directory = FSDirectory.open(
            		FileSystems.getDefault().getPath("C:/Users/Administrator/Desktop/LuceneIndex"));
            // ����IndexWriter
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(directory, indexWriterConfig);
            indexWriter.deleteAll();//�����ǰ��index
            
            //Ҫ������File·��,·����ȫ�Ǻ�׺��Ϊ.txt���ĵ�
            File dFile = new File("C:/Users/Administrator/Desktop/File");
            File[] files = dFile.listFiles();
            for (File file : files) {
            	//��ȡ�ĵ��е�������content�ַ�����
            	 BufferedReader in = new BufferedReader(new FileReader(file)); 
                 String s;
                 String content = "";
                 while((s = in.readLine()) != null){//readLine()����ÿ�ζ�ȡ�ļ���һ��ֱ���������з�
                     content += s+"\n";
                 }
                 in.close();
                 
                 File outputFile = new File("C:/Users/Administrator/Desktop/LuceneOutput/"+file.getName());
                 if(!outputFile.exists()){
                	 System.out.print("�����ļ�:"+file.getName());
                	 outputFile.createNewFile();
                 }
                 
                 //���ַ���������д������ĵ���
                 BufferedWriter bw = new BufferedWriter(
                         new OutputStreamWriter(
                                 new FileOutputStream("C:/Users/Administrator/Desktop/LuceneOutput/"+file.getName())));
                 bw.write(content);
                 bw.close();

                // ����Document����
                Document document = new Document();
                // ΪDocument���Field           
                document.add(new Field("content",content, TextField.TYPE_STORED));
                document.add(new Field("filename", file.getName(), TextField.TYPE_STORED));
                document.add(new Field("filepath", file.getAbsolutePath(), TextField.TYPE_STORED));

                // ͨ��IndexWriter����ĵ���������
                indexWriter.addDocument(document);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (indexWriter != null) {
                    indexWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


class Search {  
    public void search(String keyWord) {  
        DirectoryReader directoryReader = null;  
        try {  
            // ����Directory  
            Directory directory = FSDirectory.open(
            		FileSystems.getDefault().getPath("C:/Users/Administrator/Desktop/LuceneIndex"));
            // ����IndexReader  
            directoryReader = DirectoryReader.open(directory);  
            // ����IndexReader����IndexSearch  
            IndexSearcher indexSearcher = new IndexSearcher(directoryReader);  

            // ����������Query  
            Analyzer analyzer = new StandardAnalyzer();  
            // ����parser��ȷ��Ҫ�����ļ������ݣ���һ������Ϊ��������  
            QueryParser queryParser = new QueryParser("content", analyzer);  
            // ����Query��ʾ������Ϊcontent����UIMA���ĵ�  
            Query query = queryParser.parse(keyWord);  

            // ����searcher�������ҷ���TopDocs  
            TopDocs topDocs = indexSearcher.search(query, 1000);  
            System.out.println("���ҵ����ĵ��ܹ��У�"+topDocs.totalHits);

            // ����TopDocs��ȡScoreDoc����  
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;  
            for (ScoreDoc scoreDoc : scoreDocs) {  

                // ����searcher��ScoreDoc�����ȡ�����Document����  
                Document document = indexSearcher.doc(scoreDoc.doc);  

                // ����Document�����ȡ��Ҫ��ֵ  
                System.out.println(document.get("filename") + " " + document.get("filepath")); 
                System.out.println(document.get("content"));//�õ��������
                
                String value = toHighlighter(query,document,"content",analyzer);
                System.out.println(value);//�õ�������������йؼ��ʸ���
            }  

        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (directoryReader != null) {  
                    directoryReader.close();  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
    //����
    public String toHighlighter(Query query,Document doc,String field,Analyzer analyzer){
        try {
            SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font color=\"red\">", "</font>");
            Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(query));
            TokenStream tokenStream1 = analyzer.tokenStream("text",new StringReader(doc.get(field)));
            String highlighterStr = highlighter.getBestFragment(tokenStream1, doc.get(field));
            return highlighterStr == null ? doc.get(field):highlighterStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
            return null;
        }
    }
}  

public class HighLightSearch {
	//����
    public static void main(String[] args) {
        Index newIndex = new Index();
        newIndex.index();
        Search newSearch = new Search();
        newSearch.search("�����ܽ�Lucene");
    }
}