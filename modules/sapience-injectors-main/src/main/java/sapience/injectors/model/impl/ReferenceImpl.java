/**
 * Copyright (C) 2010 Institute for Geoinformatics (ifgi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * ITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package sapience.injectors.model.impl;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import sapience.injectors.model.Reference;




/**
 * The Reference is a pair of LocalElementIdentifier and the Annotation which is to be injected. This can be, for example, 
 * an XPath element such as //wsdl/message[name="myname"] and an attribute such as [sawsdl:modelReference=".."]. 
 * 
 * A short introduction into JDO and annotations: http://code.google.com/appengine/docs/java/gettingstarted/usingdatastore.html
 * @author Henry Michels
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ReferenceImpl implements Serializable, Reference {
	
	private static final long serialVersionUID = 3646052842396727461L;

	@PrimaryKey 
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)  	
	protected Long key;
	
	@Persistent
	private Serializable target;
	
	@Persistent
	private Serializable source;	
	
	// lazy-generated string representations
	private String stringVal = null;
	
	
	public ReferenceImpl(Serializable name, Serializable reference)   {
		this.target = reference;
		this.source = name;
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.Reference#getTarget()
	 */
	public Serializable getTarget() {
		return target;
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.Reference#getSource()
	 */
	public Serializable getSource() {
		return source;
	}
	
	
	
	
	@Override
	public String toString() {
		if(stringVal == null) {
			stringVal = new StringBuilder().append(source).append(";").append(target).toString();
		}
		return stringVal;
	}

	public void setSource(Serializable source) {
		this.source = source;
	}

	public void setTarget(Serializable target) {
		this.target = target;
	}
}
