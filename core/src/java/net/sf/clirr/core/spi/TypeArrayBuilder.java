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

import java.io.File;

import net.sf.clirr.core.CheckerException;
import net.sf.clirr.core.ClassFilter;

public interface TypeArrayBuilder
{
    /**
     * Creates a set of classes to check.
     *
     * @param classPathEntries a set of jar files and directories to scan for class files.
     *
     * @param thirdPartyClasses loads classes that are referenced
     * by the classes in the classPathEntries
     *
     * @param classSelector is an object that determines which classes reachable via the
     * classPathEntries are to be compared. This parameter may be null, in
     * which case all classes in the old and new jars are compared.
     */
    JavaType[] createClassSet(
            File[] classPathEntries, ClassLoader thirdPartyClasses, ClassFilter classSelector)
            throws CheckerException;

}
