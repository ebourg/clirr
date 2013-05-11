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

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import net.sf.clirr.core.spi.Field;
import net.sf.clirr.core.spi.JavaType;
import net.sf.clirr.core.spi.Scope;

class AsmField extends AbstractAsmScoped implements Field
{
    private final String name;
    private final Object value;
    private final Type type;
    private final Repository repository;
    private final AsmJavaType container;

    AsmField(AsmJavaType container, int access, String name, Object value, Type type)
    {
        super(access);
        this.container = container;
        this.repository = container.getRepository();
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public JavaType getType()
    {
        // todo: handle primitive values and arrays
        return repository.findTypeByName(type.getClassName());
    }

    public boolean isFinal()
    {
        return checkFlag(Opcodes.ACC_FINAL);
    }

    public boolean isStatic()
    {
        return checkFlag(Opcodes.ACC_STATIC);
    }

    public boolean isDeprecated()
    {
        return checkFlag(Opcodes.ACC_DEPRECATED);
    }

    public Object getConstantValue()
    {
        return value;
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
