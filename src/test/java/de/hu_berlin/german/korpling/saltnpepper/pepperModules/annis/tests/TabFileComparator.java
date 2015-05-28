/*
 * Copyright 2014 Humboldt Univerity of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.tests;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.Assert;

/**
 * Compares two tab-seperated files.
 * 
 * 
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class TabFileComparator
{
  /**
   * Compare two files.
   * They are equal when all lines of the first file are included
   * in the second file and vice versa.
   * If not equal an assert will be issued.
   * @param pathA
   * @param pathB
   * @throws java.io.IOException
   */
  public static void checkEqual(String pathA, String pathB) throws IOException
  {
    List<String> a = Files.readAllLines(Paths.get(pathA), Charsets.UTF_8);
    List<String> b = Files.readAllLines(Paths.get(pathB), Charsets.UTF_8);
   
    checkEqual(a, b, pathA, pathB);
  }
  
  private static void checkEqual(List<String> allLinesA, List<String> allLinesB,
    String titleA, String titleB) throws IOException
  {
    HashMultiset<String> linesA = HashMultiset.create();
    HashMultiset<String> linesB = HashMultiset.create();
    
    linesA.addAll(allLinesA);
    linesB.addAll(allLinesB);
    
    for(Multiset.Entry<String> e : linesA.entrySet())
    {
      int count1 = e.getCount();
      int count2 = linesB.count(e.getElement()); 
      Assert.assertEquals("line\n" + e.getElement() 
        + "\nfrom " + titleA + "\nmissing in " + titleB, 
        count1, count2);
      
      // remove the found element from B so we know which lines are in the B but not in the A
      linesB.remove(e.getElement(), Integer.MAX_VALUE);
    }
    
    Iterator<String> itB = linesB.iterator();
    if(itB.hasNext())
    {
      throw new AssertionError("line\n" + itB.next() + "\nfrom " 
      + titleB + "\nmissing in " + titleA);
    }
  }
  
  /**
   * Compare two tab-seperated files.
   * They are equal when all lines of the first file are included
   * in the second file and vice versa.
   * If not equal an assert will be issued.
   * @param pathA
   * @param pathB
   * @param ignoredColumns 
   * @throws java.io.IOException 
   */
  public static void checkEqual(String pathA, String pathB, int... ignoredColumns) throws IOException
  {
    List<String> a = Files.readAllLines(Paths.get(pathA), Charsets.UTF_8);
    List<String> b = Files.readAllLines(Paths.get(pathB), Charsets.UTF_8);
    
    Set<Integer> ignoredAsSet = new HashSet<>();
    for(Integer i : ignoredColumns)
    {
      ignoredAsSet.add(i);
    }

    a = removeIgnoredColumns(a, ignoredAsSet, "[...]");
    b = removeIgnoredColumns(b, ignoredAsSet, "[...]");
    
    checkEqual(a, b, pathA, pathB);
    
  }
  
  
  private static List<String> removeIgnoredColumns(Collection<String> originalLines, 
    Set<Integer> ignoreColumns, String placeholder)
  {
    List<String> result = new ArrayList<>(originalLines.size());
    
    Splitter splitter = Splitter.on('\t');
    Joiner joiner = Joiner.on("\t");
    
    if(ignoreColumns == null)
    {
      ignoreColumns = new HashSet<>();
    }
    
    for(String l : originalLines)
    {
      List<String> newLine = new LinkedList<>();
      int columnNr = 0;
      for(String cell : splitter.split(l))
      {
        if(ignoreColumns.contains(columnNr))
        {
          if(placeholder != null)
          {
            newLine.add(placeholder);
          }
        }
        else
        {
          newLine.add(cell);
        }
        columnNr++;
      }
      result.add(joiner.join(newLine));
    }
    return result;
  }


}

