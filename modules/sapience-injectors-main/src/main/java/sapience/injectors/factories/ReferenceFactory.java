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

package sapience.injectors.factories;

import java.io.Serializable;

import sapience.injectors.model.Reference;

public interface ReferenceFactory {

	public abstract Serializable createDocumentName(Serializable documentName);

	public abstract Serializable createElementName(Serializable elementName);

	public abstract Serializable createCompositeName(Serializable first,
			Serializable second);

	public abstract Serializable createReference(Serializable reference);

	public abstract Reference createBinding(Serializable name,
			Serializable reference);

}