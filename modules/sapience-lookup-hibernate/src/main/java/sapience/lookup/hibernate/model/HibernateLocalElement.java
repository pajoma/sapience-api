package sapience.lookup.hibernate.model;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.Adler32;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import sapience.injectors.model.LocalElement;

@Embeddable
public class HibernateLocalElement implements LocalElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3942444087710159946L;

	private String docID;	
	
	private String elemID;

	@Transient
	private String stringVal;
	
	/**
	 * 
	 */
	public HibernateLocalElement() {
		
	}
	
	public HibernateLocalElement(URI docID, String elementID) {
		super();
		this.docID = docID.toString();
		this.elemID = elementID;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			return obj.hashCode() == this.hashCode();
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	
	
	@Override
	public String toString() {
		
		if (stringVal == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(getDocumentID()).append(";").append(getElementID());
			stringVal = sb.toString();	
		}

		return stringVal;
		
	}

	@Transient
	public Serializable getDocumentID() {
		try {
			return new URI(this.docID);
		} catch (URISyntaxException e) {
			//do nothing
		}
		return null;
	}

	@Transient
	public Serializable getElementID() {
		return elemID;
	}

	@Transient
	public void setDocumentID(Serializable documentID) {
		if(! (documentID instanceof URI)) throw new IllegalArgumentException("Parameter Document ID has to be of type URI");
		this.docID = documentID.toString();
	}

	@Transient
	public void setElementID(Serializable elementID) {
		this.elemID = elementID.toString();
	}
	
	public String checksumForDocID() {
		Adler32 adler32 = new Adler32();
		adler32.update(docID.getBytes());
		return Long.toHexString(adler32.getValue());
	}

	/**
	 * @return the docID
	 */
	public String getDocID() {
		return docID;
	}

	/**
	 * @param docID the docID to set
	 */
	public void setDocID(String docID) {
		this.docID = docID;
	}

	/**
	 * @return the elemID
	 */
	public String getElemID() {
		return elemID;
	}

	/**
	 * @param elemID the elemID to set
	 */
	public void setElemID(String elemID) {
		this.elemID = elemID;
	}
	
}
