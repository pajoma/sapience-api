package sapience.lookup.hibernate.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import sapience.injectors.model.LocalElement;
import sapience.injectors.model.Reference;

@Entity
@Table(name="Bindings")
public class HibernateReference implements Reference,DatabaseEntity{
	
	private static final long serialVersionUID = 3646052842396727461L;
	
	private HibernateLocalElement id;		//url of the document and xPath of the annotation

	private String element;					//url of the annotation
	
	/**
	 * 
	 */
	public HibernateReference() {
		
	}

	public HibernateReference(HibernateLocalElement name, String reference)   {
		this.id = name;
		this.element = reference;
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.Reference#getTarget()
	 */
	@Transient
	public Serializable getTarget() {
		return element;
	}
	
	/* (non-Javadoc)
	 * @see sapience.injectors.model.impl.Reference#getSource()
	 */
	@Transient
	public LocalElement getSource() {
		return id;
	}
	
	
	
	
	@Override
	public String toString() {
		return new StringBuilder().append(id.toString()).append(";").append(element).toString();
	}

	/*
	 * (non-Javadoc)
	 * @see sapience.injectors.model.Reference#setSource(java.io.Serializable)
	 */
	@Transient
	public void setSource(Serializable source) {
		if(! (source instanceof HibernateLocalElement)) throw new IllegalArgumentException("Parameter has to be of type HibernateLocalElement");
		this.id = (HibernateLocalElement) source;
	}

	/*
	 * (non-Javadoc)
	 * @see sapience.injectors.model.Reference#setTarget(java.io.Serializable)
	 */
	@Transient
	public void setTarget(Serializable target) {
		this.element = target.toString();
		
	}

	/**
	 * @return the id
	 */
	@Id
	public HibernateLocalElement getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(HibernateLocalElement id) {
		this.id = id;
	}

	/**
	 * @return the element
	 */
	public String getElement() {
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public void setElement(String element) {
		this.element = element;
	}


}
