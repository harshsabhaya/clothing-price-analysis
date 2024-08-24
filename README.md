# Clothing Price Analysis

The Clothing Price Analysis project aims to analyze clothing information from various online clothing retailers to provide customers with insights into clothing trends. By exploring factors such as product names, categories, brands, and prices, the project seeks to assist customers in making informed decisions when purchasing clothing items online. This project enhances the online shopping experience by offering tools and information necessary for smart purchasing decisions.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
  - [Web Crawler](#web-crawler)
  - [HTML Parser](#html-parser)
  - [Frequency Count](#frequency-count)
  - [Spell Checking](#spell-checking)
  - [Search Frequency](#search-frequency)
  - [Word Completion](#word-completion)
  - [Inverted Indexing](#inverted-indexing)
  - [Page Ranking](#page-ranking)
  - [Data Validation using Regex](#data-validation-using-regex)
  - [Finding Patterns using Regex](#finding-patterns-using-regex)

## Introduction

The Clothing Price Analysis project explores clothing trends from three distinct online clothing websites:
- [AJIO](https://www.ajio.com/)
- [Flipkart](https://www.flipkart.com/)
- [Myntra](https://www.myntra.com/)

By analyzing the data gathered from these websites, the project aims to provide users with insights into clothing trends, helping them make better purchasing decisions.

## Features

### 1. Web Crawler
The web crawler scrapes data from the three websites, gathering information such as product category, product name, product price, and brand. The data is analyzed and stored for future trend examination.

### 2. HTML Parser
The HTML parser fetches specific data from the websites and stores it in CSV files for further analysis.

### 3. Frequency Count
The frequency count feature reads a CSV file containing clothing data and calculates the frequency of each word. The results are printed, showing the number of times each word occurred in the CSV file.

### 4. Spell Checking
This feature rectifies spelling errors in written text by cross-referencing them with a designated dictionary.

### 5. Search Frequency
The system tracks how many times a user has searched for a specific word.

### 6. Word Completion
The word completion feature predicts the remaining portion of a word based on user input.

### 7. Inverted Indexing
This feature stores words and associates them with their locations within a set of documents, allowing for rapid searches.

### 8. Page Ranking
The system assesses a product name and category based on its occurrences on different web pages, ranking them according to the highest occurrence.

### 9. Data Validation using Regex
User input is verified by applying predefined regular expression patterns to ensure data accuracy.

### 10. Finding Patterns using Regex
While performing read and write operations, the system uses regex to fetch expected data patterns.

## Command Line Interface
![Picture1](https://github.com/user-attachments/assets/8fa74b20-af71-4624-b7ab-353d1ab6c55c)


