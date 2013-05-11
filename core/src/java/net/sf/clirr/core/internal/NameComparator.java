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

package net.sf.clirr.core.internal;

import java.io.Serializable;
import java.util.Comparator;

import net.sf.clirr.core.spi.Named;

/**
 * Compares {@link Named named entities} by their name.
 *
 * @author Simon Kitching
 * @author lkuehne
 */
public final class NameComparator implements Comparator, Serializable
{
    private static final long serialVersionUID = -6730235504132614557L;

    public NameComparator()
    {
    }

    public int compare(Object o1, Object o2)
    {
        Named f1 = (Named) o1;
        Named f2 = (Named) o2;

        final String name1 = f1.getName();
        final String name2 = f2.getName();

        return name1.compareTo(name2);
    }
}

