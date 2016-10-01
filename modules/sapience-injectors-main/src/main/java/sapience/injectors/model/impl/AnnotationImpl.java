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

import sapience.injectors.model.Annotation;

/**
 * @author Henry Michels, pajoma
 * The Annotation is the content pointing to an external entity. This can be either a simple attribute (e.g. 
 * sawsdl:modelreference=".."), a set of attributes ("xlink:href="..." xlink:arcrole="...") or a new child element
 * (e.g. <extendedData> ...</extendedData>. The annotation itself does not now in which document it is used
 */
public class AnnotationImpl implements Annotation {
	private static final long serialVersionUID = -1357958656793018560L;
	


}
