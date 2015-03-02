/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.tests.storing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleConnectorFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleReader;

public class FileComparer 
{
	public static boolean compareFiles(URI uri1, URI uri2) throws IOException
	{
		return(FileComparer.compareFiles(new File(uri1.toFileString()), new File(uri2.toFileString())));
	}
	
	public static boolean compareFiles(File file1, File file2) throws IOException
	{
		boolean retVal= false;
		
		if ((file1== null) || (file2== null))
			throw new NullPointerException("One of the files to compare are null.");
		if (	(!file1.exists()) || 
				(!file2.exists()))
			throw new NullPointerException("Cannot compare files, because one does not exist.");
		String contentFile1= null;
		String contentFile2= null;
		BufferedReader brFile1= null;
		BufferedReader brFile2= null;
		try 
		{
			brFile1=  new BufferedReader(new FileReader(file1));
			String line= null;
			while (( line = brFile1.readLine()) != null)
			{
		          contentFile1= contentFile1+  line;
		    }
			brFile2=  new BufferedReader(new FileReader(file2));
			line= null;
			while (( line = brFile2.readLine()) != null)
			{
		          contentFile2= contentFile2+  line;
		    }
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally 
		{
			brFile1.close();
			brFile2.close();
		} 
		
		if (contentFile1== null)
		{
			if (contentFile2== null)
				retVal= true;
			else retVal= false;
		}	
		else if (contentFile1.equals(contentFile2))
			retVal= true;
		return(retVal);
	}
	
	public static boolean compareTabFiles(URI uri1, URI uri2) throws IOException
	{
		return(FileComparer.compareTabFiles(new File(uri1.toFileString()), new File(uri2.toFileString())));
	}
	
	public static boolean compareTabFiles(File file1, File file2) throws IOException
	{
		boolean retVal= true;
		
		TupleReader t1= TupleConnectorFactory.fINSTANCE.createTupleReader();
		t1.setFile(file1);
		t1.readFile();
		
		TupleReader t2= TupleConnectorFactory.fINSTANCE.createTupleReader();
		t2.setFile(file2);
		t2.readFile();
		
		if (!t1.size().equals(t2.size()))
		{
			retVal= false;
		}
		
		if (retVal)
		{
			if (! t1.getTuples().containsAll(t2.getTuples()))
			{
				retVal= false;
			}
		}
		
		return(retVal);
	}
}
