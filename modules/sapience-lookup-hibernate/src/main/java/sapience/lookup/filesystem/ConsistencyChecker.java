package sapience.lookup.filesystem;

import java.nio.CharBuffer;

import sapience.injectors.Extractor;
import sapience.injectors.Injector;

@Deprecated
public interface ConsistencyChecker {

	/**
	 * Update method which is called by the Lookup.
	 * This mehtod calls the "storeAnnotation" method of the given Injector.
	 * @param docID
	 * @param contentClue
	 * @param injector
	 */
	public abstract void update(String docID, CharBuffer contentClue,
			Extractor extractor);

}