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

package net.sf.clirr.framework;

import net.sf.clirr.event.ApiDifference;
import net.sf.clirr.event.Severity;
import net.sf.clirr.event.Message;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;

public abstract class AbstractDiffReporter
{

    private ApiDiffDispatcher dispatcher;

    public AbstractDiffReporter(ApiDiffDispatcher dispatcher)
    {
        this.dispatcher = dispatcher;
    }

    protected final ApiDiffDispatcher getApiDiffDispatcher()
    {
        return dispatcher;
    }

    protected final void log(
        Message msg,
        Severity severity,
        String clazz, Method method, Field field,
        String[] args)
    {
        final ApiDifference diff = new ApiDifference(
            msg, severity, clazz, null, null, args);
        getApiDiffDispatcher().fireDiff(diff);
    }
}
