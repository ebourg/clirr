//////////////////////////////////////////////////////////////////////////////
// Clirr: compares two versions of a java library for binary compatibility
// Copyright (C) 2003 - 2013  Lars Kühne
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//////////////////////////////////////////////////////////////////////////////

package net.sf.clirr.core.spi;

/**
 * Describes a field of a class.
 */
public interface Field extends Named, Scoped
{
    /**
     * The type of this field.
     */
    JavaType getType();
    
    /**
     * Whether the field is declared as final.
     */
    boolean isFinal();
    
    /**
     * Whether the field is declared as static.
     */
    boolean isStatic();

    /**
     * Whether the field is deprecated.
     */
    boolean isDeprecated();
    
    /**
     * Returns the constant value of this field.
     * The constant value is an Object if the field is static and final and the java compiler 
     * could calculate the value at compilation time.
     * 
     * @return the constant value or <code>null</code> if the compiler could 
     * not calculate the value at compilation time  
     */
    Object getConstantValue();
}
