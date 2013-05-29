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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Helper class for dealing with ClassLoaders.
 * @author lk
 */
public final class ClassLoaderUtil
{

    /** prevent instantiation. */
    private ClassLoaderUtil()
    {
    }

    /**
     * Builds a ClassLoader for a given classpath.
     * @param cpEntries classpath entries
     * @return the new ClassLoader
     */
    public static ClassLoader createClassLoader(final String[] cpEntries)
    {
        final URL[] cpUrls = new URL[cpEntries.length];
        for (int i = 0; i < cpEntries.length; i++)
        {
            String cpEntry = cpEntries[i];
            File entry = new File(cpEntry);
            try
            {
                URL url = entry.toURI().toURL();
                cpUrls[i] = url;
            }
            catch (MalformedURLException ex)
            {
                throw new IllegalArgumentException(
				    "Cannot create classLoader from classpath entry " + entry, ex);
            }
        }
        final URLClassLoader classPathLoader = new URLClassLoader(cpUrls);
        return classPathLoader;
    }

}
