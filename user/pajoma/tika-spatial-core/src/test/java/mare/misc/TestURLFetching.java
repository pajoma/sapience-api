package mare.misc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.config.TikaConfigCollection;
import org.junit.Before;
import org.junit.Test;



/**
 * Problem: gae apparently doesn't support mark/rest for input streams, which is needed
 * by tika to do the mimetype detection
 * @author pajoma
 *
 */
public class TestURLFetching {

	String url = "http://www.assembla.com/spaces/sapience/documents/co52n0A88r3OmHeJe5afGb/download/Japan.kml";
	private TikaConfigCollection configs;
	
	@Before
	public void setup() {
		configs = createTikaConfiguration();
		
	}
	
	@Test
	public void testMimeType() throws MalformedURLException, IOException {
		String mimeType = configs.getMimeRepository().getType(new URL(url));
	}
	
	@Test
	public void testResetting() throws IOException {
		URL url = new URL(this.url);
		InputStream input = url.openStream();
		BufferedInputStream bis = new BufferedInputStream(input);
		
		 if (input != null) {
	            input.mark(getMinLength());
	            try {
	                byte[] prefix = readMagicHeader(input);
	                System.out.println(prefix);
	            } finally {
	                input.reset();
	            }
	        }
	}
	
	
	public TikaConfigCollection createTikaConfiguration() {
		TikaConfigCollection tcc = new TikaConfigCollection();
		tcc.addConfiguration(TikaConfig.getDefaultConfig());
		return tcc;
	}
	
	/* copied from Tika MimeTypes.java */
	
    public int getMinLength() {
        // This needs to be reasonably large to be able to correctly detect
        // things like XML root elements after initial comment and DTDs
        return 8 * 1024;
    }
    
    /**
     * Reads the first {@link #getMinLength()} bytes from the given stream.
     * If the stream is shorter, then the entire content of the stream is
     * returned.
     * <p>
     * The given stream is never {@link InputStream#close() closed},
     * {@link InputStream#mark(int) marked}, or
     * {@link InputStream#reset() reset} by this method.
     *
     * @param stream stream to be read
     * @return first {@link #getMinLength()} (or fewer) bytes of the stream
     * @throws IOException if the stream can not be read
     */
    private byte[] readMagicHeader(InputStream stream) throws IOException {
        if (stream == null) {
            throw new IllegalArgumentException("InputStream is missing");
        }

        byte[] bytes = new byte[getMinLength()];
        int totalRead = 0;

        int lastRead = stream.read(bytes);
        while (lastRead != -1) {
            totalRead += lastRead;
            if (totalRead == bytes.length) {
                return bytes;
            }
            lastRead = stream.read(bytes, totalRead, bytes.length - totalRead);
        }

        byte[] shorter = new byte[totalRead];
        System.arraycopy(bytes, 0, shorter, 0, totalRead);
        return shorter;
    }

}
