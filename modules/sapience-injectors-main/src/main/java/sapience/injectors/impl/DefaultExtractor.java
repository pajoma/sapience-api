package sapience.injectors.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import sapience.injectors.Configuration;
import sapience.injectors.model.Reference;
import sapience.injectors.stax.extract.StaxBasedExtractor;

/**
 * Default Extractor, using the Pattern-based detection and the Stax-parser
 * 
 * @author pajoma
 *
 */
public class DefaultExtractor extends AbstractExtractor {

	public DefaultExtractor(Configuration config) {
		super(config);
	}

	@Override
	public List<Reference> extract(Serializable docID, InputStream is)
			throws IOException {

		StaxBasedExtractor sbe = new StaxBasedExtractor(super.getConfiguration());
		Map<String, String> result = sbe.collectReferences(is);
		return super.buildReferencesFromResultMap(result,docID);
	}

}
