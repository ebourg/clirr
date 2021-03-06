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

package net.sf.clirr.core.internal.checks;

import net.sf.clirr.core.Severity;
import net.sf.clirr.core.Message;
import net.sf.clirr.core.internal.AbstractDiffReporter;
import net.sf.clirr.core.internal.ApiDiffDispatcher;
import net.sf.clirr.core.internal.ClassChangeCheck;
import net.sf.clirr.core.spi.JavaType;

/**
 * Detects gender changes (a class became an interface or vice versa).
 *
 * @author lkuehne
 */
public final class GenderChangeCheck
    extends AbstractDiffReporter
    implements ClassChangeCheck
{
    private static final Message MSG_GENDER_CLASS_TO_INTERFACE = new Message(2000);
    private static final Message MSG_GENDER_INTERFACE_TO_CLASS = new Message(2001);

    /**
     * Create a new instance of this check.
     * @param dispatcher the diff dispatcher that distributes the detected changes to the listeners.
     */
    public GenderChangeCheck(ApiDiffDispatcher dispatcher)
    {
        super(dispatcher);
    }


    /** {@inheritDoc} */
    public boolean check(JavaType baseLine, JavaType current)
    {
        if (!baseLine.isInterface() && current.isInterface())
        {
            log(MSG_GENDER_CLASS_TO_INTERFACE,
                getSeverity(baseLine, Severity.ERROR),
                baseLine.getName(), null, null, null);
        }
        else if (baseLine.isInterface() && !current.isInterface())
        {
            log(MSG_GENDER_INTERFACE_TO_CLASS,
                getSeverity(baseLine, Severity.ERROR),
                baseLine.getName(), null, null, null);
        }

        return true;
    }
}
