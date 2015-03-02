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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.resources;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate.Factory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;


public class RelANNISResourceFactory extends ResourceFactoryImpl 
{
	/**
	 * determines the relANNIS version to be used. This flag can be used in options for {@link Resource#save(Map)}.
	 */
	public static final String RELANNIS_VERSION ="RELANNIS_VERSION";
	/**
	 * Map which contains uri's and the corresponding resources. Keys are the uri's.
	 */
	private static volatile Map<URI, Resource> uriResourceMap= null;
	
	public RelANNISResourceFactory()
	{
		super();
		if (uriResourceMap== null)
			uriResourceMap= new Hashtable<URI, Resource>();
	}
	
	public synchronized Resource createResource(URI uri)
	{
		Resource resource= null;
		File file= new File(uri.toFileString());
		if (!file.exists())
			file.mkdirs();
		if (!file.isDirectory())
			throw new RelANNISException("Cannot create a resource for the given uri, because it is not a directory '"+uri+"'.");
		if (uriResourceMap.containsKey(uri))
		{
			resource = uriResourceMap.get(uri);
		}
		else
		{
			resource=new RelANNISResource();
			resource.setURI(uri);
			uriResourceMap.put(uri, resource);
		}
		return(resource);
	}
}
