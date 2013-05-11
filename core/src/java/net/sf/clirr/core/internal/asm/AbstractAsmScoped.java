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

import net.sf.clirr.core.spi.Scope;
import net.sf.clirr.core.spi.Scoped;

abstract class AbstractAsmScoped implements Scoped
{

    private final int access;

    AbstractAsmScoped(int access)
    {
        this.access = access;
    }

    public Scope getDeclaredScope()
    {
        if (checkFlag(Opcodes.ACC_PRIVATE))
        {
            return Scope.PRIVATE;
        }
        else if (checkFlag(Opcodes.ACC_PROTECTED))
        {
            return Scope.PROTECTED;
        }
        else if (checkFlag(Opcodes.ACC_PUBLIC))
        {
            return Scope.PUBLIC;
        }
        return Scope.PACKAGE;
    }

    /**
     * @return whether access field has mask set
     */
    protected boolean checkFlag(int mask)
    {
        return (access & mask) != 0;
    }
}
