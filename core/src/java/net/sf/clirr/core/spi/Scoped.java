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
 * A Java source code entity like a type or a method that has the 
 * concept of a visibility scope.
 * 
 * Each entity has two scopes: One that is declared and the effective scope.
 * For example a public method can have an effective scope of package if it
 * appears in a class that is package visible.
 *  
 * @author lk
 *
 */
public interface Scoped
{
    /**
     * The declared scope of this entity.
     * @return the scope that appears in the modifiers of this entity.
     */
    Scope getDeclaredScope();
    
    /**
     * The effective Scope of this entity.
     * 
     * @return the minimum scope of the modifiers of this entity and
     * all of it's containers.
     */
    Scope getEffectiveScope();
}
