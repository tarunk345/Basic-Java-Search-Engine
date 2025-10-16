package finalproject;

import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, ArrayList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does an exploration of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
        if (internet.getVisited(url)) {
            return;
        }
        internet.setVisited(url,true);

        ArrayList<String> links = parser.getLinks(url);
        ArrayList<String> content = parser.getContent(url);

        internet.addVertex(url);

        for (String word : content) {
            String lowercase = word.toLowerCase();
            wordIndex.computeIfAbsent(lowercase, k -> new ArrayList<String>());
            if (!wordIndex.get(lowercase).contains(url)) {
                wordIndex.get(lowercase).add(url);
            }
        }

        for (String link : links) {
            crawlAndIndex(link);
            internet.addEdge(url,link);
        }
	}
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
    public void assignPageRanks(double epsilon) {
        boolean converged = false;
        ArrayList<Double> ranks = new ArrayList<>();

        for (String s: internet.getVertices()) {
            internet.setPageRank(s, 1.0);
            ranks.add(1.0);
        }

        while (!converged) {
            ArrayList<Double> newRanks = computeRanks(internet.getVertices());
            converged = true;
            for (int i = 0; i < internet.getVertices().size(); i++) {
                double oldRank = ranks.get(i);
                double newRank = newRanks.get(i);
                if (Math.abs(oldRank - newRank) >= epsilon) {
                    converged = false;
                    break;
                }
            }
            ranks = newRanks;
        }
    }

	
	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 * 
	 * This method will probably fit in about 20 lines.
	 */
    public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
        ArrayList<Double> ranks = new ArrayList<>();
        double df = 0.5;

        for (String s : vertices) {
            double sum = 0;
            for (String t : internet.getEdgesInto(s)) {
                if (internet.getPageRank(t) == 0)
                    internet.setPageRank(t, 1);
                sum += (internet.getPageRank(t)/internet.getOutDegree(t));
            }
            double newRank = df+(df*sum);
            ranks.add(newRank);

        }

        for(int i = 0; i < vertices.size(); i++) {
            internet.setPageRank(vertices.get(i), ranks.get(i));
        }
        return ranks;
    }

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method will probably fit in about 10-15 lines.
	 */
	public ArrayList<String> getResults(String query) {
        ArrayList<String> sorted_results = new ArrayList<>();
        if (wordIndex.containsKey(query.toLowerCase())) {
            ArrayList<String> results = wordIndex.get(query.toLowerCase());
            HashMap<String, Double> search_results = new HashMap<>();

            for (String s : results) {
                search_results.put(s, internet.getPageRank(s));
            }

            sorted_results = Sorting.fastSort(search_results);

        }
        return sorted_results;
	}
}
