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

class ArrayType implements JavaType
{
    private final JavaType basicType;
    private final int dimension;

    ArrayType(JavaType basicType, int dimension)
    {
        this.basicType = basicType;
        this.dimension = dimension;
    }

    public String getBasicName()
    {
        return basicType.getBasicName();
    }

    public String getName()
    {
        StringBuffer arrayDimIndicator = new StringBuffer();
        final int arrayDimension = getArrayDimension();
        for (int i = 0; i < arrayDimension; i++)
        {
            arrayDimIndicator.append("[]");
        }

        return basicType.getBasicName() + arrayDimIndicator;
    }

    public JavaType getContainingClass()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public JavaType[] getSuperClasses()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public JavaType[] getAllInterfaces()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Method[] getMethods()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Field[] getFields()
    {
        return new Field[0];
    }

    public int getArrayDimension()
    {
        return dimension;
    }

    public boolean isPrimitive()
    {
        return basicType.isPrimitive();
    }

    public boolean isFinal()
    {
        return false;
    }

    public boolean isAbstract()
    {
        return false;
    }

    public boolean isInterface()
    {
        // TODO Auto-generated method stub
        return false;
    }

    public Scope getDeclaredScope()
    {
        return basicType.getDeclaredScope();
    }

    public Scope getEffectiveScope()
    {
        return basicType.getEffectiveScope();
    }

    public int getClassFormatVersion()
    {
        return basicType.getClassFormatVersion();
    }

    public String toString()
    {

        return "ArrayType[" + basicType.toString() + " ^ " + dimension + "]";
    }
}
