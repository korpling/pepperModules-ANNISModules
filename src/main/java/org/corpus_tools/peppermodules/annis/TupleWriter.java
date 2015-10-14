/**
 * Copyright 2015 Humboldt-Universität zu Berlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package org.corpus_tools.peppermodules.annis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of a tuple writer that uses temoporary files instead
 * of the main memory to store non-commited tupels.
 * 
 * This allows to actually have large transactions open without running
 * short on memory. A disadvantage is that the data is written twice,
 * one time into the temporary file and another when appending it to the
 * real file when committing.
 * 
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class TupleWriter
{
	private static final Logger logger= LoggerFactory.getLogger(TupleWriter.class);
	
	private final Lock writerLock = new ReentrantLock();
	
	private String encoding= "UTF-8";
	private String sperator= "\t";
	
	private boolean escapeCharacters = false;
  
	/**
	 * output file 
	 */
	private File outFile= null;
	/**
	 * Names of stored attributes
	 */
	private Collection<String> attNames= null;
	
	/**
	 * Constructor
	 */
	public TupleWriter(){
	}
	
	public void addTuple(Collection<String> tuple) throws FileNotFoundException
	{
//		if (logger!= null) logger.debug("adding tuple for TA: (without ta-control)");
		Long taId= this.beginTA();
		this.addTuple(taId, tuple);
		this.commitTA(taId);
	}

// ----------------------------- Start TA-Management -----------------------------
	private final AtomicLong TAId = new AtomicLong(0l);
	/**
	 * relates tuple-Ids to Collections of tuples
	 */
  private final ConcurrentHashMap<Long, TransactionFile> transactionToTmpStream 
    = new ConcurrentHashMap<>();
	
	/**
	 * Returns a new TA-Id
	 * @return TAId
	 */
	private synchronized Long getNewTAId()
	{
		long currTAId= TAId.getAndIncrement();
		return(currTAId);
	}
	
	public long beginTA() 
	{
		if (logger!= null) logger.debug("begin ta of TupleWriter '"+this.getFile()+"' with id: "+ TAId);
		Long taId= getNewTAId();
    
    transactionToTmpStream.put(taId, createNewTmpStream());
    
		return(taId);
	}
  
  private TransactionFile createNewTmpStream()
  {
    try
    {
      File tmpFile = File.createTempFile("tupelwriter", ".tmp");
      TransactionFile result = new TransactionFile(
        tmpFile,
        new PrintStream(new FileOutputStream(tmpFile), false, getEncoding()));
      result.getFile().deleteOnExit();
      
      return result;
    }
    catch(IOException ex)
    {
      logger.error("Could not create temporary file", ex);
    }
    return null;
  }
	
	public void addTuple(Long TAId, Collection<String> tuple) throws FileNotFoundException
	{
//		if (logger!= null) logger.debug("adding tuple for TA: "+ TAId);
    TransactionFile tmpFile = transactionToTmpStream.get(TAId);
		
		if (tmpFile== null)
    {
      TransactionFile internalTmpFile = createNewTmpStream();
      printTupelToStream(internalTmpFile.stream, tuple);
      internalTmpFile.stream.close();
    }
    else
    {
      printTupelToStream(tmpFile.stream, tuple);
    }
	}
	
	public void commitTA(Long TAId) throws FileNotFoundException
	{
		if (TAId== null)
		{
			throw new TupleWriterException("Cannot commit an empty transaction id for TupleWriter controlling file '"+this.getFile()+"'.");
		}
		if (logger!= null) logger.debug("commiting ta of TupleWriter '"+this.getFile()+"' with id: "+ TAId);
    copyToRealFile(TAId);
    TransactionFile tmp = transactionToTmpStream.remove(TAId);
    if(tmp != null)
    {
      tmp.stream.close();
      tmp.file.delete();
    }
	}
	
	public void abortTA(Long TAId) 
	{
		if (logger!= null) logger.debug("aborting ta with id: "+ TAId);
		TransactionFile tmp = transactionToTmpStream.remove(TAId);
    if(tmp != null)
    {
      tmp.stream.close();
      tmp.file.delete();
    }
	}
  
  private void printTupelToStream(PrintStream stream, Collection<String> tuple)
  {
    StringBuilder tupleAsString= new StringBuilder();
    int i = 0;
    for (String att : tuple)
    {
      // don't escape if attribute is NULL
      if (this.escapeCharacters && att != null)
      { // if escaping should be done
        StringBuilder escaped = new StringBuilder();
        for (char chr : att.toCharArray())
        { // for every char in the atring
          String escapeString = getEscapeTable().get(chr);
          if (escapeString != null)
          { // if there is some escape sequence
            escaped.append(escapeString);
          }
          else
          {
            escaped.append(chr);
          }
        }
        tupleAsString.append(escaped.toString());
      }
      else
      { // if NO escaping should be done
        tupleAsString.append(att);
      }

      i++;
      if (i < tuple.size())
      {
        tupleAsString.append(this.getSeperator());
      }
    }
    tupleAsString.append("\n");
    stream.print(tupleAsString.toString());
  }
  
  private void copyToRealFile(Long TAId)
  { 
    TransactionFile tmp = transactionToTmpStream.get(TAId);
    if(tmp != null)
    {
      writerLock.lock();
      try(OutputStream out = Files.newOutputStream(outFile.toPath(), 
            StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

        Files.copy(tmp.getFile().toPath(), out);
      }
      catch (IOException ex) {
        logger.error("Could not copy transaction file to output file", ex);
      }
      finally
      {
        writerLock.unlock();
      }
    }
  }
	
// ----------------------------- End TA-Management -----------------------------
	
	public void setEncoding(String encoding) 
	{
		this.encoding= encoding;
	}
	
	public void setEscaping(boolean escape){
		this.escapeCharacters = escape;
	}
	
	/** internal table containing the escapings, key= character to escape, value= replacement */
	private ConcurrentMap<Character,String> escapeTable;
	
	public void setEscapeTable(ConcurrentMap<Character,String> escapeTable){
		if (escapeTable != null){
			this.escapeTable = escapeTable;
		}
	}
	
	/**
	 * If no other escaping is set, the following one is used:
	 * <table border="0">
	 *   <tr><td>\t</td><td> </td></tr>
	 *   <tr><td>\n</td><td> </td></tr>
	 *   <tr><td>\r</td><td> </td></tr>
	 *   <tr><td>\\</td><td>\\\\</td></tr>
	 *   <tr><td>\</td><td>\\</td></tr>
	 * </table>
	 * @return
	 */
	public ConcurrentMap<Character, String> getEscapeTable() {
    
    
    
		if (escapeTable == null) {
			escapeTable = new ConcurrentHashMap<>();
			/**
			 * Standard escaping \t \n \r \ '
			 */
			escapeTable.put('\t', "\\t");
			escapeTable.put('\n', "\\n");
			escapeTable.put('\r', "\\r");
			escapeTable.put('\\', "\\\\");
			escapeTable.put('\'', "\\'");
		}
		return this.escapeTable;
	}
	
	public String getEncoding() 
	{
		return(this.encoding);
	}

	public void setFile(File out) 
	{
		if (out== null) 
			throw new TupleWriterException("Error(TupleWriter): an empty file-object is given.");
		
		if (out.getParent()== null)
			throw new TupleWriterException("Cannot set the given file, because it has no parent folder: "+out+".");
		if (!out.getParentFile().exists())
			out.getParentFile().mkdirs();
    
    // delete any old file: since we are only appending from now on this is important
    if(out.exists())
    {
      if(!out.delete())
      {
        logger.error("Could not delete existing output file " + out.getAbsolutePath());
      }
      try
      {
        // create a new empty file
        if(!out.createNewFile())
        {
          logger.error("Could create new empty output file " + out.getAbsolutePath());
        }
      }
      catch (IOException ex)
      {
        logger.error("Could create new empty output file " + out.getAbsolutePath(), ex);
      }
    }
		this.outFile= out;
	}
	
	public File getFile() 
	{
		return(this.outFile);
	}

	public void setAttNames(Collection<String> attNames)
	{
		if (attNames== null) throw new TupleWriterException("ERROR(TupleWriter): The given collection with attribute names is empty.");
		this.attNames= attNames;
	}
	
	public Collection<String> getAttNames() 
	{
		return(this.attNames);
	}

	public void setSeperator(String seperator) 
	{
		this.sperator= seperator;
	}
	
	public String getSeperator() 
	{
		return(this.sperator);
	}

  
  public static class TransactionFile
  {
    private File file;
    private PrintStream stream;

    public TransactionFile(File file, PrintStream stream)
    {
      this.file = file;
      this.stream = stream;
    }

    public File getFile()
    {
      return file;
    }
    
    public PrintStream getStream()
    {
      return stream;
    }
  }
}
