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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public abstract class TypeArrayBuilderSupport implements TypeArrayBuilder
{

    protected ClassLoader createClassLoader(File[] classPathEntries, ClassLoader thirdPartyClasses)
    {
        final URL[] entryUrls = new URL[classPathEntries.length];
        for (int i = 0; i < classPathEntries.length; i++)
        {
            File entry = classPathEntries[i];
            try
            {
                URL url = entry.toURI().toURL();
                entryUrls[i] = url;
            }
            catch (MalformedURLException ex)
            {
                String fileType = entry.isDirectory() ? "directory" : "jar file";
                throw new IllegalArgumentException(
                        "Cannot create classloader with " + fileType + " " + entry, ex);
            }
        }
        final URLClassLoader loader = new URLClassLoader(entryUrls, thirdPartyClasses);

        return loader;
    }

}
