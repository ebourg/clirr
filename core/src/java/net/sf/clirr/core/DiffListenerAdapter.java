//////////////////////////////////////////////////////////////////////////////
// Clirr: compares two versions of a java library for binary compatibility
// Copyright (C) 2003 - 2004  Lars K�hne
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

package net.sf.clirr.core;

import net.sf.clirr.core.ApiDifference;
import net.sf.clirr.core.DiffListener;


/**
 * Provides empty implementations for all methods
 * in the DiffListener interface.
 *
 * @author lkuehne
 */
public class DiffListenerAdapter implements DiffListener
{
    /**
     * Does nothing
     * @see net.sf.clirr.core.DiffListener#reportDiff(net.sf.clirr.core.ApiDifference)
     */
    public void reportDiff(ApiDifference difference)
    {
    }

    /**
     * Does nothing
     * @see net.sf.clirr.core.DiffListener#start()
     */
    public void start()
    {
    }

    /**
     * Does nothing
     * @see net.sf.clirr.core.DiffListener#stop()
     */
    public void stop()
    {
    }
}