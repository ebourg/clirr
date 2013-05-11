//////////////////////////////////////////////////////////////////////////////
// Clirr: compares two versions of a java library for binary compatibility
// Copyright (C) 2003 - 2013  Lars K�hne
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

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import net.sf.clirr.core.spi.JavaType;
import net.sf.clirr.core.spi.Method;
import net.sf.clirr.core.spi.Scope;

class AsmMethod extends AbstractAsmScoped implements Method
{
    private final Repository repository;

    private final Type returnType;

    private final String name;

    private final Type[] argumentTypes;

    private final String[] exceptions;

    private AsmJavaType container;

    AsmMethod(AsmJavaType container, int access, Type returnType,
            String name, Type[] argumentTypes, String[] exceptions)
    {
        super(access);
        this.container = container;
        this.repository = container.getRepository();
        this.returnType = returnType;
        this.name = name;
        this.argumentTypes = argumentTypes;
        this.exceptions = exceptions;
    }

    public JavaType getReturnType()
    {
        if (Type.VOID_TYPE.equals(returnType))
        {
            return null;
        }
        final JavaType javaType = findJavaType(returnType);
        return javaType;
    }

    public JavaType[] getArgumentTypes()
    {
        JavaType[] ret = new JavaType[argumentTypes.length];
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = findJavaType(argumentTypes[i]);
        }
        return ret;
    }

    private JavaType findJavaType(Type asmType)
    {
        String name = asmType.getClassName();
        return repository.findTypeByName(name);
    }

    public JavaType[] getDeclaredExceptions()
    {
        JavaType[] ret = new JavaType[exceptions.length];
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = repository.findTypeByName(exceptions[i]);
        }
        return ret;
    }

    public boolean isFinal()
    {
        return checkFlag(Opcodes.ACC_FINAL);
    }

    public boolean isStatic()
    {
        return checkFlag(Opcodes.ACC_STATIC);
    }

    public boolean isAbstract()
    {
        return checkFlag(Opcodes.ACC_ABSTRACT);
    }

    public boolean isDeprecated()
    {
        return checkFlag(Opcodes.ACC_DEPRECATED);
    }

    public String getName()
    {
        return name;
    }

    public Scope getEffectiveScope()
    {
        final Scope containerScope = container.getEffectiveScope();
        final Scope declaredScope = getDeclaredScope();
        return containerScope.isLessVisibleThan(declaredScope) ? containerScope : declaredScope;
    }

}
