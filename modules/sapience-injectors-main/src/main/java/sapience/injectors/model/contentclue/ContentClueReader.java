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

/**
 * 
 */
package sapience.injectors.model.contentclue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.CharBuffer;

/**
 * @author Henry Michels
 *
 */

@Deprecated
public abstract class ContentClueReader {
	
	private final int bufSize = 1000;
	
	/**
	 * 
	 * @return
	 */
	public int getBufferSize(){
		return bufSize;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public CharBuffer getContentClue(File file) throws Exception{
		return getContentClue(new FileInputStream(file));
	}
	
	/**
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public abstract CharBuffer getContentClue(InputStream inputStream) throws Exception;

}
