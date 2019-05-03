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
  private Map<List<String>, Double> listCache;

  // private Map<Template, Double> templateCache;

  /**
   * Constructor for Search.
   */
  public Search() {
    frequencies = new HashMap<String, Double>();
    listCache = new HashMap<List<String>, Double>();
    // templateCache = new HashMap<Template, Double>();
  }

  /**
   * Constructor for Search.
   * @param docs
   *          A list of "documents"
   */
  public Search(List<List<String>> docs) {
    this.init(docs);
  }

  /**
   * Sets totalSize.
   * @param i
   *          The size totalSize is being set to.
   */
  public void setSize(int i) {
    totalSize = i;
  }

  /**
   * Sets initial parameters.
   * @param docs
   *          A list of "documents".
   */
  public void init(List<List<String>> docs) {

    totalSize = docs.size();
    frequencies = new HashMap<String, Double>();
    listCache = new HashMap<List<String>, Double>();
  }

  /**
   * Term Frequency.
   * @param term
   *          Word being searched for.
   * @param doc
   *          Document being searched.
   * @return The frequency with which the word appears in the doc.
   */
  public double termFrequency(String term, List<String> doc) {
    double freq = 0;

    for (int i = 0; i < doc.size(); i++) {
      System.out.println("TERM: " + term + ", DOC WORD: " + doc.get(i));
      if (term.equals(doc.get(i))) {
        freq += 1;
      }
    }

    return freq;
  }

  /**
   * Inverse Document Frequency.
   * @param term
   *          Word being searched for.
   * @param docs
   *          A list of documents being searched.
   * @return The inverse of the frequency with which the word appears in the
   *         docs.
   */
  public double inverseDocumentFrequency(String term, List<List<String>> docs) {
    double numDocs = 0;
    for (List<String> doc : docs) {
      if (doc.contains(term)) {
        numDocs += 1;
      }
    }

    return Math.log(totalSize / (numDocs + 1));
  }

  /**
   * TF-IDF.
   * @param term
   *          Word being searched for.
   * @param doc
   *          Document being searched.
   * @return The TF-IDF score of a document.
   */
  public double tfIdf(String term, List<String> doc) {
    // if (listCache.containsKey(doc)) {
    // return listCache.get(doc);
    // }
    double tf = termFrequency(term, doc);
    System.out.println("term is " + term + " tf is " + tf);
    double idf;

    if (frequencies.containsKey(term)) {
      idf = frequencies.get(term);
    } else {
      System.out.println(totalSize);
      idf = Math.log(totalSize + 1);
      System.out.println("idf is " + idf);
    }

    double toret = tf * idf;
    listCache.put(doc, toret);
    return toret;
  }

  /**
   * Key words TF-IDF.
   * @param terms
   *          A list of terms.
   * @param doc
   *          A document.
   * @return The sum of the tf-idfs for all of the terms.
   */
  public double keywordsTfIdf(List<String> terms, List<String> doc) {
    double sum = 0;
    for (String s : terms) {
      // System.out.println("TERM IS: " + s);
      sum += tfIdf(s, doc);
    }
    return sum;
  }

  /**
   * Map Rank Template.
   * @param terms
   *          A list of terms.
   * @param tags
   *          A list of tags.
   * @param templates
   *          A list of templates
   * @return A sorted map of templates
   * @throws InterruptedException
   *           an interrupted exception.
   */
  public List<Map.Entry<Template, AtomicDouble>> mapRankTemplate(
      List<String> terms,
      List<String> tags,
      List<Template> templates) throws InterruptedException {
    this.totalSize = templates.size();
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
      List<List<String>> docs;

      Worker(BlockingQueue<String> q,
          List<AtomicDouble> r,
          List<Template> templates) {
        queue = q;
        results = r;
        temps = templates;
        // docs = new ArrayList<List<String>>();
        // for (int i = 0; i < temps.size(); i++) {
        // docs.add(temps.get(i).getTrueContent());
        // }
        // docs = documents;
      }

      @Override
      public void run() {
        // TODO Auto-generated method stub
        while (!queue.isEmpty()) {
          // System.out.println("Not empty");
          task(queue.poll());
        }
      }

      void task(Object x) {
        for (int i = 0; i < temps.size(); i++) {
          // System.out.println("STRING IS: " + (String) x);
          // System.out.println("yes hello it's me " + (String) x);
          // System.out.println("x is " + (String) x);
          double sum = tfIdf((String) x, temps.get(i).getTrueContent());
          // double sum = newtfidf((String) x, docs);
          System.out.println("I AM TASKED WITH " + sum);
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
    List<Map.Entry<Template, AtomicDouble>> entries =
        new ArrayList<Map.Entry<Template, AtomicDouble>>(docMap.entrySet());
    Collections.sort(entries,
        new Comparator<Map.Entry<Template, AtomicDouble>>() {
          public int compare(Map.Entry<Template, AtomicDouble> a,
              Map.Entry<Template, AtomicDouble> b) {
            return Double.compare(b.getValue().doubleValue(),
                a.getValue().doubleValue());
          }
        });
    // List<List<String>> toret = new ArrayList<>();
    // for (Map.Entry<Template, AtomicDouble> e : entries) {
    // if (e.getValue().doubleValue() != 0.0) {
    // System.out.println("KEY: " + e.getKey().getTrueContent().get(0)
    // + " VALUE: "
    // + e.getValue());
    // }
    // toret.add(e.getKey());
    // }
    System.out.println("IN SEARCH");
    for (Map.Entry<Template, AtomicDouble> e : entries) {
      System.out.println(e.getValue().doubleValue());
    }
    return entries;
  }

  /**
   * Rank Docs.
   * @param terms
   *          A list of terms.
   * @param docs
   *          A list of documents.
   * @return A ranked list of documents.
   */
  public List<List<String>> rankDocs(List<String> terms,
      List<List<String>> docs) {
    this.totalSize = docs.size();
    Map<List<String>, Double> docMap = new HashMap<List<String>, Double>();
    for (List<String> doc : docs) {
      double ti = keywordsTfIdf(terms, doc);
      // System.out.println("DOC IS: " + doc.get(0) + " TI IS: " + ti);
      docMap.put(doc, ti);
    }

    List<Map.Entry<List<String>, Double>> entries =
        new ArrayList<Map.Entry<List<String>, Double>>(docMap.entrySet());
    Collections.sort(entries,
        new Comparator<Map.Entry<List<String>, Double>>() {
          public int compare(Map.Entry<List<String>, Double> a,
              Map.Entry<List<String>, Double> b) {
            return Double.compare(b.getValue(), a.getValue());
          }
        });
    List<List<String>> toret = new ArrayList<>();
    for (Map.Entry<List<String>, Double> e : entries) {
      System.out
          .println("KEY: " + e.getKey().get(0) + " VALUE: " + e.getValue());
      toret.add(e.getKey());
    }
    return toret;
  }

  /**
   * Rank Templates.
   * @param terms
   *          A list of terms.
   * @param templates
   *          A list of templates.
   * @return A ranked list of templates.
   */
  public List<Template> rankTemplates(List<String> terms,
      List<Template> templates) {
    this.totalSize = templates.size();
    Map<Template, Double> docMap = new HashMap<Template, Double>();
    for (Template template : templates) {
      double ti = keywordsTfIdf(terms, template.getTrueContent());
      // System.out.println("DOC IS: " + doc.get(0) + " TI IS: " + ti);
      docMap.put(template, ti);
    }

    List<Map.Entry<Template, Double>> entries =
        new ArrayList<Map.Entry<Template, Double>>(docMap.entrySet());
    Collections.sort(entries, new Comparator<Map.Entry<Template, Double>>() {
      public int compare(Map.Entry<Template, Double> a,
          Map.Entry<Template, Double> b) {
        return Double.compare(b.getValue(), a.getValue());
      }
    });
    List<Template> toret = new ArrayList<>();
    for (Map.Entry<Template, Double> e : entries) {
      if (e.getValue() != 0.0) {
        System.out.println("KEY: " + e.getKey().getTrueContent().get(0)
            + " VALUE: "
            + e.getValue());
      }

      toret.add(e.getKey());
    }
    return toret;
  }

  /**
   * Threaded Rank Templates.
   * @param terms
   *          A list of terms.
   * @param templates
   *          A list of templates.
   * @return A ranked list of templates.
   * @throws InterruptedException
   */
  public List<Template> threadedRankTemplates(List<String> terms,
      List<Template> templates) throws InterruptedException {

    // for (String s : terms) {
    // System.out.println("TERM: " + s);
    // }
    this.totalSize = templates.size();
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
        while (!queue.isEmpty()) {
          System.out.println("Not empty");
          task(queue.poll());
        }
      }

      void task(Object x) {
        for (int i = 0; i < temps.size(); i++) {
          // System.out.println("STRING IS: " + (String) x);
          System.out.println("yes hello it's me " + (String) x);
          double sum = tfIdf((String) x, temps.get(i).getTrueContent());
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
    List<Map.Entry<Template, AtomicDouble>> entries =
        new ArrayList<Map.Entry<Template, AtomicDouble>>(docMap.entrySet());
    Collections.sort(entries,
        new Comparator<Map.Entry<Template, AtomicDouble>>() {
          public int compare(Map.Entry<Template, AtomicDouble> a,
              Map.Entry<Template, AtomicDouble> b) {
            return Double.compare(b.getValue().doubleValue(),
                a.getValue().doubleValue());
          }
        });
    // List<List<String>> toret = new ArrayList<>();
    for (Map.Entry<Template, AtomicDouble> e : entries) {
      if (e.getValue().doubleValue() != 0.0) {
        System.out.println("KEY: " + e.getKey().getTrueContent().get(0)
            + " VALUE: "
            + e.getValue());
      }
      toret.add(e.getKey());
    }
    return toret;
  }

  public List<List<String>> threadedRankDocs(List<String> terms,
      List<List<String>> docs) throws InterruptedException {
    this.totalSize = docs.size();
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
      System.out.println(
          "DOC: " + docs.get(i).get(0) + " IN LIST: " + sizeList.get(i));
    }
    Map<List<String>, AtomicDouble> docMap =
        new HashMap<List<String>, AtomicDouble>();
    for (int i = 0; i < numDocs; i++) {
      docMap.put(docs.get(i), sizeList.get(i));
    }
    List<Map.Entry<List<String>, AtomicDouble>> entries =
        new ArrayList<Map.Entry<List<String>, AtomicDouble>>(docMap.entrySet());
    Collections.sort(entries,
        new Comparator<Map.Entry<List<String>, AtomicDouble>>() {
          public int compare(Map.Entry<List<String>, AtomicDouble> a,
              Map.Entry<List<String>, AtomicDouble> b) {
            return Double.compare(b.getValue().doubleValue(),
                a.getValue().doubleValue());
          }
        });
    List<List<String>> toret = new ArrayList<>();
    for (Map.Entry<List<String>, AtomicDouble> e : entries) {
      if (e.getValue().doubleValue() != 0.0) {
        System.out
            .println("KEY: " + e.getKey().get(0) + " VALUE: " + e.getValue());
      }

      toret.add(e.getKey());
    }
    return toret;
    // return docs;
  }

  // TODO: Write a method that ranks search on Templates! Just use their
  // toString thing
}
