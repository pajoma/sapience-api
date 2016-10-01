package sapience.annotations.hibernate.tests.composite;

public class CompositeKey {

	public CompositeKey() {
		// TODO Auto-generated constructor stub
	}
	
	private CompositeKey primaryKey;
	public CompositeKey getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(CompositeKey primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getSomeValue1() {
		return someValue1;
	}
	public void setSomeValue1(String someValue1) {
		this.someValue1 = someValue1;
	}
	public String getSomeValue2() {
		return someValue2;
	}
	public void setSomeValue2(String someValue2) {
		this.someValue2 = someValue2;
	}
	private String someValue1;
	private String someValue2;
}
