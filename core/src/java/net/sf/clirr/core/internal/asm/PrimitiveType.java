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

package net.sf.clirr.core.internal.asm;

import net.sf.clirr.core.spi.Field;
import net.sf.clirr.core.spi.JavaType;
import net.sf.clirr.core.spi.Method;
import net.sf.clirr.core.spi.Scope;

class PrimitiveType implements JavaType
{
    private final String basicName;
    private final int classFormatVersion;

    PrimitiveType(int classFormatVersion, String name)
    {
        this.classFormatVersion = classFormatVersion;
        this.basicName = name;
    }
    
    public int getClassFormatVersion()
    {
        return classFormatVersion;
    }
    
    public String getBasicName()
    {
        return basicName;
    }

    public String getName()
    {
        return basicName;
    }

    public JavaType getContainingClass()
    {
        return null;
    }

    public JavaType[] getSuperClasses()
    {
        return new JavaType[0];
    }

    public JavaType[] getAllInterfaces()
    {
        return new JavaType[0];
    }

    public Method[] getMethods()
    {
        return new Method[0];
    }

    public Field[] getFields()
    {
        return new Field[0];
    }

    public int getArrayDimension()
    {
        return 0;
    }

    public boolean isPrimitive()
    {
        return true;
    }

    public boolean isFinal()
    {
        return true;
    }

    public boolean isAbstract()
    {
        return false;
    }

    public boolean isInterface()
    {
        return false;
    }

    public Scope getDeclaredScope()
    {
        return Scope.PUBLIC;
    }

    public Scope getEffectiveScope()
    {
        return Scope.PUBLIC;
    }
    
    public String toString()
    {
        return getName();
    }
}