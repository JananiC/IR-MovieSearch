package com.irproject.search.Indexing;







import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;



public class Indexer {

	private StandardAnalyzer analyzer;
	private Directory indexDir;
	private IndexWriter indexWriter = null;


	/** Creates a new instance of Indexer */
	public Indexer(Directory indexDir, StandardAnalyzer analyzer) {
		this.analyzer = analyzer;
		this.indexDir = indexDir;
	}


	public IndexWriter getIndexWriter(boolean create) throws IOException {
		if (indexWriter == null) {
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, analyzer);
			indexWriter = new IndexWriter(indexDir, config);
		}
		return indexWriter;
	}

	public void closeIndexWriter() throws IOException {
		if (indexWriter != null) {
			indexWriter.close();
		}
	}

	public void indexMovie(Movies movie) throws IOException {

		System.out.println("Indexing movies: " + movie);
		IndexWriter writer = getIndexWriter(false);
		Document doc = new Document();


		doc.add(new TextField("title", movie.gettitle(), Field.Store.YES));
		doc.add(new TextField("actor", movie.getactors(), Field.Store.YES));
		doc.add(new TextField("actress", movie.getactress(), Field.Store.YES));
		doc.add(new TextField("director", movie.getdirector(), Field.Store.YES));
		doc.add(new TextField("language", movie.getlanguage(), Field.Store.YES));
		doc.add(new TextField("genre", movie.getgenre(), Field.Store.YES));
		doc.add(new TextField("company", movie.getproductioncomp(), Field.Store.YES));
		doc.add(new TextField("rating", movie.getrating(), Field.Store.YES));
		doc.add(new TextField("plots", movie.getPlots(), Field.Store.YES));
		doc.add(new TextField("locations", movie.getlocations(), Field.Store.YES));
		String fullSearchableText = movie.gettitle() + " " + movie.getactors() + " " + movie.getactress()+" "+movie.getdirector()+" "+movie.getlanguage()+" "+movie.getgenre()+" "+movie.getproductioncomp()+" "+movie.getrating()+" "+movie.getPlots()+" "+movie.getlocations();
		doc.add(new TextField("content", fullSearchableText, Field.Store.YES));

		//doc.add(new TextField("content", movie.gettitle(), Field.Store.YES));
		writer.addDocument(doc);
	}

	public void rebuildIndexes() throws IOException {
		File dataDir = new File(System.getProperty("jboss.server.data.dir"));
		System.out.println(dataDir.getAbsolutePath());
		//File movPlotTitleYear = new File(dataDir.getAbsolutePath()+"/Movie_Plot_Title_Year.txt");
		//  System.out.println("JANANI$$$$$$$$$$$$$$$$$$$$$$$--->"+yourFile.getAbsolutePath());

		File actor = new File(dataDir.getAbsolutePath()+"/Actor.txt");
		File language = new File(dataDir.getAbsolutePath()+"/Language.txt");
		File director = new File(dataDir.getAbsolutePath()+"/Director.txt");
		File genres = new File(dataDir.getAbsolutePath()+"/I_MovieGenres.txt");
		File prodComp = new File(dataDir.getAbsolutePath()+"/I_MovieProductionCompanies.txt");
		File ratings = new File(dataDir.getAbsolutePath()+"/I_MovieRatings.txt");
		File actress = new File(dataDir.getAbsolutePath()+"/Actress.txt");
		File titles = new File(dataDir.getAbsolutePath()+"/titles.txt");
		File locations = new File(dataDir.getAbsolutePath()+"/locations.txt");


		getIndexWriter(true);

		File yourFile22 = new File(dataDir.getAbsolutePath()+"/plots.txt");

		MultiMap plots= new MultiHashMap();

		MultiMap lcns= new MultiHashMap();

		Scanner sc22 = new Scanner(yourFile22, "UTF-8");
		sc22.useDelimiter("\n");

		Scanner sc23 = new Scanner(locations, "UTF-8");
		sc23.useDelimiter("\n");

		String id22 = null;

		String Plots= null;
		String Lcns= null;

		int lineCount = 0;

		while (sc22.hasNext())
		{

			String line=sc22.next();

			if(line.length()>3)
			{
				//System.out.println(line);
				String[] t=line.split(":");



				//System.out.println(t.elementAt(1));
				if(lineCount!=0)
				{
					id22=t[0].trim();
					//System.out.println(t.elementAt(1));
					Plots=t[1];

					//System.out.println(Plots);
					plots.put( id22,Plots.trim());
					// System.out.println( plots.get(id));
				}
				lineCount++;
			}
		}
		Set keySet22 = plots.keySet( );
		Iterator keyIterator22 = keySet22.iterator();
		MultiMap actr = new MultiHashMap();
		MultiMap actrs = new MultiHashMap();
		MultiMap dir = new MultiHashMap();
		MultiMap gen = new MultiHashMap();
		MultiMap lang = new MultiHashMap();
		MultiMap ttl = new MultiHashMap();
		MultiMap prod = new MultiHashMap();
		MultiMap rat = new MultiHashMap();
		Vector <String> idv=new Vector<String>();

		Scanner sc1 = new Scanner(actress, "UTF-8");
		Scanner sc2 = new Scanner(titles, "UTF-8");
		Scanner sc3 = new Scanner(language, "UTF-8");
		Scanner sc4 = new Scanner(director, "UTF-8");
		Scanner sc5 = new Scanner(genres, "UTF-8");
		Scanner sc6 = new Scanner(prodComp, "UTF-8");
		Scanner sc7 = new Scanner(ratings, "UTF-8");
		Scanner sc = new Scanner(actor, "UTF-8");

		sc1.useDelimiter("\n");
		sc2.useDelimiter("\n");
		sc3.useDelimiter("\n");
		sc4.useDelimiter("\n");
		sc5.useDelimiter("\n");
		sc6.useDelimiter("\n");
		sc7.useDelimiter("\n");
		sc.useDelimiter("\n");

		String id1 = null;
		String id2 = null;
		String Actors = null;
		String Actress=null;
		String Directors=null;
		String Genres=null;
		String Language=null;
		String prodcomp=null;
		String id3 = null;
		String title = null;
		String rating=null;



		while (sc.hasNext())
		{
			String line=sc.next();
			String[] t=line.split("\t");

			if(lineCount!=0)
			{
				id1=t[0];
				Actors=t[1];


				actr.put((id1), Actors);
				//idv.add(id1);

				//Movies p=new Movies(id1,Actors,0);
			}
			lineCount++;

		}
		while (sc23.hasNext())
		{
			String line=sc23.next();
			String[] t=line.split("\t");

			if(lineCount!=0)
			{
				id1=t[0];
				Lcns=t[1];


				lcns.put((id1), Lcns);
				//idv.add(id1);

				//Movies p=new Movies(id1,Actors,0);
			}
			lineCount++;

		}
		lineCount=0;
		while (sc7.hasNext())
		{
			String line=sc7.next();
			String[] t=line.split("\t");

			if(lineCount!=0)
			{
				id1=t[0];
				rating=t[3];


				rat.put((id1), rating);
				//idv.add(id1);

				//Movies p=new Movies(id1,Actors,0);
			}
			lineCount++;

		}
		lineCount = 0;
		while (sc1.hasNext())
		{
			String line=sc1.next();
			String[] t=line.split("\t");

			if(lineCount!=0)
			{
				id2=t[0];
				Actress=t[1];
				//	System.out.println("here"+id1+Actress);
				actrs.put((id2), Actress);
			}

			lineCount++;
		}
		lineCount = 0;
		while (sc2.hasNext())
		{
			String line=sc2.next();
			String[] t=line.split("\t");

			if(lineCount!=0)
			{
				id3=t[0];

				title=t[1];
				//	System.out.println("here"+id1+Actress);
				ttl.put((id3), title);
			}

			lineCount++;
		}
		lineCount = 0;
		while (sc3.hasNext())
		{
			String line=sc3.next();
			String[] t=line.split("\t");

			if(lineCount!=0)
			{
				id3=t[0];

				Language=t[1];
				//	System.out.println("here"+id1+Actress);
				lang.put((id3), Language);
			}

			lineCount++;
		}
		lineCount = 0;
		while (sc4.hasNext())
		{
			String line=sc4.next();
			String[] t=line.split("\t");

			if(lineCount!=0)
			{
				id3=t[0];

				Directors=t[1];

				dir.put((id3), Directors);
			}

			lineCount++;
		}
		lineCount = 0;
		while (sc5.hasNext())
		{
			String line=sc5.next();
			String[] t=line.split("\t");

			if(lineCount!=0)
			{
				id3=t[0];

				Genres=t[1];
				//	System.out.println("here"+id1+Actress);
				gen.put((id3), Genres);
			}

			lineCount++;
		}
		lineCount = 0;
		while (sc6.hasNext())
		{
			String line=sc6.next();
			String[] t=line.split("\t");

			if(lineCount!=0)
			{
				id3=t[0];

				prodcomp=t[1];
				//	System.out.println("here"+id1+Actress);
				prod.put((id3), prodcomp);
			}

			lineCount++;
		}
		Set keySet = actr.keySet( );
		Set keySet1 = actrs.keySet( );
		Set keySet2 = ttl.keySet( );
		Set keySet3 = dir.keySet( );
		Set keySet4 = gen.keySet( );
		Set keySet5 = lang.keySet( );
		Set keySet6 = prod.keySet( );
		Set keySet7 = rat.keySet( );
		Set keySet8 = lcns.keySet( );
		Iterator keyIterator = keySet.iterator();
		Iterator keyIterator1 = keySet1.iterator();
		Iterator keyIterator2 = keySet2.iterator();
		Iterator keyIterator3 = keySet3.iterator();
		Iterator keyIterator4 = keySet4.iterator();
		Iterator keyIterator5 = keySet5.iterator();
		Iterator keyIterator6 = keySet6.iterator();
		Iterator keyIterator7 = keySet7.iterator();
		Iterator keyIterator8 = keySet8.iterator();
		while( keyIterator2.hasNext( ) ) {  //checking ids of movie table as the holistic
			Object key = keyIterator2.next( );  //data
			//			System.out.print( "Key2: " + key + "------- " );
			//	System.out.print( "Title: " + ttl.get(key) + "------- " );
			String titl="";
			String act="";
			String actng="";
			String dirn="";
			String genn="";
			String langn="";
			String prodn="";
			String ratn="";
			String location="";
			titl=ttl.get(key).toString();
			if(actr.get(key)!=null)
			{

				Collection values = (Collection) actr.get( key );
				Iterator valuesIterator = values.iterator( );
				while( valuesIterator.hasNext( ) ) {
					String t=valuesIterator.next().toString();
					t=t.substring(0, t.length()-1);
					act=act+" "+t;
				}
				//				System.out.println( "Actor: " + act);
			}
			String plts="";
			if(plots.get(key)!=null)
			{
				//System.out.println( "Actor: " );
				Collection values = (Collection) plots.get(key );
				Iterator valuesIterator = values.iterator( );
				while( valuesIterator.hasNext( ) ) {
					String t=valuesIterator.next().toString();

					plts=plts.trim()+"\n"+t.trim();
					//System.out.println(plts);
				}

			}
			if(dir.get(key)!=null)
			{

				Collection values = (Collection) dir.get( key );
				Iterator valuesIterator = values.iterator( );
				while( valuesIterator.hasNext( ) ) {
					String t=valuesIterator.next().toString();
					t=t.substring(0, t.length()-1);
					dirn=dirn+" "+t;
				}
				//				System.out.println( "Director: " + dirn);
			}
			if(lcns.get(key)!=null)
			{

				Collection values = (Collection) lcns.get( key );
				Iterator valuesIterator = values.iterator( );
				while( valuesIterator.hasNext( ) ) {
					String t=valuesIterator.next().toString();
					t=t.substring(0, t.length()-1);
					location=location+" "+t;
				}
				//				System.out.println( "Director: " + dirn);
			}
			if(gen.get(key)!=null)
			{

				Collection values = (Collection) gen.get( key );
				Iterator valuesIterator = values.iterator( );
				while( valuesIterator.hasNext( ) ) {
					String t=valuesIterator.next().toString();
					t=t.substring(0, t.length()-1);
					genn=genn+" "+t;
				}
				//				System.out.println( "Genre: " + genn);
			}
			if(prod.get(key)!=null)
			{

				Collection values = (Collection) prod.get( key );
				Iterator valuesIterator = values.iterator( );
				while( valuesIterator.hasNext( ) ) {
					String t=valuesIterator.next().toString();
					t=t.substring(0, t.length()-1);
					prodn=prodn+" "+t;
				}
				//				System.out.println( "Company: " + prodn);
			}
			if(lang.get(key)!=null)
			{

				Collection values = (Collection) lang.get( key );
				Iterator valuesIterator = values.iterator( );
				while( valuesIterator.hasNext( ) ) {
					String t=valuesIterator.next().toString();
					t=t.substring(0, t.length()-1);
					langn=langn+" "+t;
				}
				//				System.out.println( "Language: " + langn);
			}

			if(actrs.get(key)!=null)
			{
				Collection values1 = (Collection) actrs.get( key );
				Iterator valuesIterator2 = values1.iterator( );

				while( valuesIterator2.hasNext() ) {
					String t=valuesIterator2.next().toString();
					t=t.substring(0, t.length()-1);
					actng=actng+" "+t;
				}
				//				System.out.println( "Actress: " + actng);

			}
			if(rat.get(key)!=null)
			{
				Collection values1 = (Collection) rat.get( key );
				Iterator valuesIterator2 = values1.iterator( );

				while( valuesIterator2.hasNext() ) {
					String t=valuesIterator2.next().toString();
					t=t.substring(0, t.length()-1);
					ratn=ratn+" "+t;
				}
				System.out.println( "Rating: " + ratn);

			}
			//System.out.println(plts);
			Movies p=new Movies(titl,act,actng,dirn,langn,genn, prodn,ratn,plts,location);
			indexMovie(p);
		}

		sc.close();
		sc1.close();
		sc2.close();
		sc3.close();
		sc4.close();
		sc5.close();
		sc6.close();
		sc7.close();
		sc22.close();
		sc23.close();


		closeIndexWriter();
	}


}
