/*******************************************************************************
 * MontiCore Language Workbench
 * Copyright (c) 2015, 2016, MontiCore, All rights reserved.
 *  
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.monticore.visualoutline.runtime.model;

import org.eclipse.gef.EditPartFactory;

/**
 * Interface custom model elements that to not represent links must implement.
 * 
 * The graphical outlines of some language may need additional model elements so they can be displayed properly.
 * An example is the State Chart language where a start state is visualized by an additional state (without text)
 * and a link from that new state to the original state.
 * Both the new state and the link need their own (custom) model element to work with the framework.
 * 
 * Models that do not represent a link need a possibility to create an edit part     
 * 
 * @author Dennis Birkholz
 */
public interface ICustomModelWithFactory {
	public EditPartFactory getFactory();
	public Object getModel();
}