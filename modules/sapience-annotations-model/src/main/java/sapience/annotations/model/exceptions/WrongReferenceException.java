/**
 * Copyright 2010 Institute for Geoinformatics (ifgi)
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

package sapience.annotations.model.exceptions;

import java.io.IOException;

/**
 * @author Henry Michels
 *
 */
public class WrongReferenceException extends IOException {
	
	private static final long serialVersionUID = -2636616403811983996L;

	private String message = "Could not generate a new Reference object for the database, because of the wrong input model!";
	
	@Override
	public String getMessage() {
		return message;
	}

}
