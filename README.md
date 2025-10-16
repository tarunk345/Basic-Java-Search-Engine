An efficient and lightweight full-text search engine built in Java, designed to index and retrieve documents with high accuracy and speed. It supports multiple file formats and integrates core information retrieval techniques such as TF-IDF scoring, boolean query operations, and inverted indexing for optimized performance.

## Key Features
- Full-text search with relevance ranking
- Query term highlighting and snippet generation
- Configurable stopword filtering
- Stemming and lemmatization for language normalization
- Concurrent indexing for improved scalability

## Requirements
- Java JDK 11+

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/java-search-engine.git
   cd java-search-engine

2. **Build the project:**
   ```bash
   mvn clean install

3. **Basic Usage:**
   ```bash
   SearchEngine engine = new SearchEngine();
   engine.addDocument("doc1", "path/to/document1.txt");
   engine.addDocument("doc2", "path/to/document2.txt");
   SearchResults results = engine.search("your query here");

