package com.irproject.search.Indexing;



import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

public class SearchEngine {

	private IndexSearcher searcher = null;
	private MultiFieldQueryParser parser = null;
	//MultiFieldQueryParser

	int hitsPerPage = 10;

	/** Creates a new instance of SearchEngine */
	public SearchEngine(Directory indexDir, StandardAnalyzer analyzer, String[] arr) throws IOException {
		IndexReader reader = DirectoryReader.open(indexDir);
		searcher = new IndexSearcher(reader);
		parser = new MultiFieldQueryParser(Version.LUCENE_41, arr, analyzer);
	}

    public ScoreDoc[] performSearch(String queryString)
    throws IOException, ParseException {
        Query query = parser.parse(queryString);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
    	searcher.search(query, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        return hits;
    }

    public Document getDoc (int docId)
        throws IOException {
        Document doc = searcher.doc(docId);
        return doc;
    }

}
