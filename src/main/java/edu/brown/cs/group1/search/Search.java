package edu.brown.cs.group1.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.util.concurrent.AtomicDouble;

import edu.brown.cs.group1.template.Template;

/**
 * The Search class has our tf-idf algorithm for ranking documents by relevance
 * of search terms.
 * @author yma37
 *
 */
public class Search {

  private int totalSize;
  private Map<String, Double> frequencies;

  public Search() {
  }

  public Search(List<List<String>> docs) {
    this.init(docs);
  }

  public void init(List<List<String>> docs) {
    // TODO: Create a Synonyms or Vocabulary class that contains
    // all of the words in all of the documents, then calculate
    // idf for each term in the vocabulary, then put them all in
    // our map (AKA caching)

    totalSize = docs.size();
    frequencies = new HashMap<String, Double>();
  }

  public double termFrequency(String term, List<String> doc) {
    double freq = 0;

    for (int i = 0; i < doc.size(); i++) {
      if (term.equals(doc.get(i))) {
        freq += 1;
      }
    }

    return freq;
  }

  public double inverseDocumentFrequency(String term, List<List<String>> docs) {
    double numDocs = 0;
    for (List<String> doc : docs) {
      if (doc.contains(term)) {
        numDocs += 1;
      }
    }

    return Math.log(totalSize / (numDocs + 1));
  }

  public double tfIdf(String term, List<String> doc) {
    double tf = termFrequency(term, doc);
    double idf;

    if (frequencies.containsKey(term)) {
      idf = frequencies.get(term);
    } else {
      idf = Math.log(totalSize);
    }

    return tf * idf;
  }

  public double keywordsTfIdf(List<String> terms, List<String> doc) {
    double sum = 0;
    for (String s : terms) {
      // System.out.println("TERM IS: " + s);
      sum += tfIdf(s, doc);
    }
    return sum;
  }

  public List<List<String>>
      rankDocs(List<String> terms, List<List<String>> docs) {

    Map<List<String>, Double> docMap = new HashMap<List<String>, Double>();
    for (List<String> doc : docs) {
      double ti = keywordsTfIdf(terms, doc);
      // System.out.println("DOC IS: " + doc.get(0) + " TI IS: " + ti);
      docMap.put(doc, ti);
    }

    List<Map.Entry<List<String>, Double>> entries = new ArrayList<Map.Entry<List<String>, Double>>(docMap.entrySet());
    Collections.sort(entries,
        new Comparator<Map.Entry<List<String>, Double>>() {
          public int compare(Map.Entry<List<String>, Double> a,
              Map.Entry<List<String>, Double> b) {
            return Double.compare(b.getValue(), a.getValue());
          }
        });
    List<List<String>> toret = new ArrayList<>();
    for (Map.Entry<List<String>, Double> e : entries) {
      System.out.println("KEY: " + e.getKey().get(0)
          + " VALUE: "
          + e.getValue());
      toret.add(e.getKey());
    }
    return toret;
  }

  public List<Template> threadedRankTemplates(List<String> terms,
      List<Template> templates) throws InterruptedException {

    int numDocs = templates.size();
    List<AtomicDouble> sizeList = new ArrayList<AtomicDouble>();
    for (int i = 0; i < numDocs; i++) {
      sizeList.add(new AtomicDouble());
    }

    List<Template> toret = new ArrayList<Template>();
    sizeList = Collections.synchronizedList(sizeList);

    class Worker implements Runnable {
      private final BlockingQueue<String> queue;
      List<AtomicDouble> results;
      List<Template> temps;

      Worker(BlockingQueue<String> q,
          List<AtomicDouble> r,
          List<Template> templates) {
        queue = q;
        results = r;
        temps = templates;
        // docs = documents;
      }

      @Override
      public void run() {
        // TODO Auto-generated method stub
        while (!queue.isEmpty()) {
          task(queue.poll());
        }
      }

      void task(Object x) {
        for (int i = 0; i < temps.size(); i++) {
          // System.out.println("STRING IS: " + (String) x);
          double sum = tfIdf((String) x, temps.get(i).getFields().getContent());
          results.get(i).addAndGet(sum);
        }
      }

    }

    int nThreads = 4;
    ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);

    BlockingQueue<String> bqueue = new LinkedBlockingQueue<String>();
    for (String s : terms) {
      bqueue.put(s);
    }

    List<Callable<Object>> todo = new ArrayList<Callable<Object>>(nThreads);

    for (int i = 0; i < nThreads; i++) {
      Worker w = new Worker(bqueue, sizeList, templates);
      todo.add(Executors.callable(w));
    }

    threadPool.invokeAll(todo);

    threadPool.shutdownNow();

    // for (int i = 0; i < numDocs; i++) {
    // System.out.println("DOC: " + templates.get(i)
    // .getFields()
    // .getContent()
    // .get(0)
    // + " IN LIST: "
    // + sizeList.get(i));
    // }
    Map<Template, AtomicDouble> docMap = new HashMap<Template, AtomicDouble>();
    for (int i = 0; i < numDocs; i++) {
      docMap.put(templates.get(i), sizeList.get(i));
    }
    List<Map.Entry<Template, AtomicDouble>> entries = new ArrayList<Map.Entry<Template, AtomicDouble>>(docMap.entrySet());
    Collections.sort(entries,
        new Comparator<Map.Entry<Template, AtomicDouble>>() {
          public int compare(Map.Entry<Template, AtomicDouble> a,
              Map.Entry<Template, AtomicDouble> b) {
            return Double.compare(b.getValue().doubleValue(), a.getValue()
                .doubleValue());
          }
        });
    // List<List<String>> toret = new ArrayList<>();
    for (Map.Entry<Template, AtomicDouble> e : entries) {
      // System.out.println("KEY: " + e.getKey().get(0)
      // + " VALUE: "
      // + e.getValue());
      toret.add(e.getKey());
    }
    return toret;
  }

  public List<List<String>> threadedRankDocs(List<String> terms,
      List<List<String>> docs) throws InterruptedException {

    int numDocs = docs.size();
    List<AtomicDouble> sizeList = new ArrayList<AtomicDouble>();
    for (int i = 0; i < numDocs; i++) {
      sizeList.add(new AtomicDouble());
    }
    sizeList = Collections.synchronizedList(sizeList);

    class Worker implements Runnable {
      private final BlockingQueue<String> queue;
      List<AtomicDouble> results;
      List<List<String>> docs;

      Worker(BlockingQueue<String> q,
          List<AtomicDouble> r,
          List<List<String>> documents) {
        queue = q;
        results = r;
        docs = documents;
      }

      @Override
      public void run() {
        // TODO Auto-generated method stub
        while (!queue.isEmpty()) {
          task(queue.poll());
        }
      }

      void task(Object x) {
        for (int i = 0; i < docs.size(); i++) {
          // System.out.println("STRING IS: " + (String) x);
          double sum = tfIdf((String) x, docs.get(i));
          results.get(i).addAndGet(sum);
        }
      }

    }

    int nThreads = 4;
    ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);

    BlockingQueue<String> bqueue = new LinkedBlockingQueue<String>();
    for (String s : terms) {
      bqueue.put(s);
    }

    List<Callable<Object>> todo = new ArrayList<Callable<Object>>(nThreads);

    for (int i = 0; i < nThreads; i++) {
      Worker w = new Worker(bqueue, sizeList, docs);
      todo.add(Executors.callable(w));
    }

    threadPool.invokeAll(todo);

    threadPool.shutdownNow();

    for (int i = 0; i < numDocs; i++) {
      System.out.println("DOC: " + docs.get(i).get(0)
          + " IN LIST: "
          + sizeList.get(i));
    }
    Map<List<String>, AtomicDouble> docMap = new HashMap<List<String>, AtomicDouble>();
    for (int i = 0; i < numDocs; i++) {
      docMap.put(docs.get(i), sizeList.get(i));
    }
    List<Map.Entry<List<String>, AtomicDouble>> entries = new ArrayList<Map.Entry<List<String>, AtomicDouble>>(docMap.entrySet());
    Collections.sort(entries,
        new Comparator<Map.Entry<List<String>, AtomicDouble>>() {
          public int compare(Map.Entry<List<String>, AtomicDouble> a,
              Map.Entry<List<String>, AtomicDouble> b) {
            return Double.compare(b.getValue().doubleValue(), a.getValue()
                .doubleValue());
          }
        });
    List<List<String>> toret = new ArrayList<>();
    for (Map.Entry<List<String>, AtomicDouble> e : entries) {
      System.out.println("KEY: " + e.getKey().get(0)
          + " VALUE: "
          + e.getValue());
      toret.add(e.getKey());
    }
    return toret;
    // return docs;
  }

  // TODO: Write a method that ranks search on Templates! Just use their
  // toString thing
}
