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

package net.sf.clirr.core.internal.checks;

import net.sf.clirr.core.CheckerException;
import net.sf.clirr.core.Message;
import net.sf.clirr.core.Severity;
import net.sf.clirr.core.internal.AbstractDiffReporter;
import net.sf.clirr.core.internal.ApiDiffDispatcher;
import net.sf.clirr.core.internal.ClassChangeCheck;
import net.sf.clirr.core.spi.JavaType;

public class ClassFormatVersionCheck extends AbstractDiffReporter implements ClassChangeCheck
{
    private static final Message MSG_CLASS_FORMAT_VERSION_INCREASED = new Message(10000);

    private static final Message MSG_CLASS_FORMAT_VERSION_DECREASED = new Message(10001);

    public ClassFormatVersionCheck(ApiDiffDispatcher dispatcher)
    {
        super(dispatcher);
    }

    public boolean check(JavaType compatBaseline, JavaType currentVersion) throws CheckerException
    {
        final int oldClassFormatVersion = compatBaseline.getClassFormatVersion();
        final int newClassFormatVersion = currentVersion.getClassFormatVersion();
        final String className = compatBaseline.getName();

        final String[] args = new String[]{
                String.valueOf(oldClassFormatVersion), String.valueOf(newClassFormatVersion)};
        if (oldClassFormatVersion < newClassFormatVersion)
        {
            // don't use severity getSeverity(compatBaseline, Severity.ERROR) here,
            // as even classes that are not visible to the client code will trigger a
            // requirement for a higher JVM version.  
            log(MSG_CLASS_FORMAT_VERSION_INCREASED, Severity.ERROR, className, null, null, args);
        }
        else if (newClassFormatVersion < oldClassFormatVersion)
        {
            log(MSG_CLASS_FORMAT_VERSION_DECREASED, Severity.INFO, className, null, null, args);
        }
        return true;
    }
}
