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

package net.sf.clirr.checks;

import java.util.ArrayList;
import java.util.List;

import net.sf.clirr.event.Severity;
import net.sf.clirr.framework.AbstractDiffReporter;
import net.sf.clirr.framework.ApiDiffDispatcher;
import net.sf.clirr.framework.ClassChangeCheck;
import net.sf.clirr.framework.CoIterator;
import net.sf.clirr.framework.JavaClassNameComparator;
import org.apache.bcel.classfile.JavaClass;

/**
 * Detects changes in the set of superclasses.
 *
 * @author lkuehne
 */
public final class ClassHierarchyCheck
        extends AbstractDiffReporter
        implements ClassChangeCheck
{
    /**
     * Create a new instance of this check.
     * @param dispatcher the diff dispatcher that distributes the detected changes to the listeners.
     */
    public ClassHierarchyCheck(ApiDiffDispatcher dispatcher)
    {
        super(dispatcher);
    }

    private List getSetDifference(JavaClass[] orig, JavaClass[] subtracted)
    {
        List list1 = getClassNames(orig);
        List list2 = getClassNames(subtracted);

        List retval = new ArrayList(list1);
        retval.removeAll(list2);

        return retval;
    }

    private List getClassNames(JavaClass[] orig)
    {
        List list = new ArrayList(orig.length);
        for (int i = 0; i < orig.length; i++)
        {
            JavaClass javaClass = orig[i];
            list.add(javaClass.getClassName());
        }
        return list;
    }

    /** {@inheritDoc} */
    public boolean check(JavaClass compatBaseline, JavaClass currentVersion)
    {
        JavaClass[] compatSupers = compatBaseline.getSuperClasses();
        JavaClass[] currentSupers = currentVersion.getSuperClasses();

        boolean isThrowable = false;
        for (int i = 0; i < compatSupers.length; i++)
        {
            JavaClass javaClass = compatSupers[i];
            if ("java.lang.Throwable".equals(javaClass.getClassName()))
            {
                isThrowable = true;
            }
        }

        final String className = compatBaseline.getClassName();

        CoIterator iter = new CoIterator(
            JavaClassNameComparator.COMPARATOR, compatSupers, currentSupers);

        while (iter.hasNext())
        {
            iter.next();
            JavaClass baselineSuper = (JavaClass) iter.getLeft();
            JavaClass currentSuper = (JavaClass) iter.getRight();

            if (baselineSuper == null)
            {
                log("Added " + currentSuper.getClassName()
                    + " to the list of superclasses of " + className,
                        isThrowable ? Severity.WARNING : Severity.INFO, className, null, null);
            }
            else if (currentSuper == null)
            {
                log("Removed " +  baselineSuper.getClassName()
                    + " from the list of superclasses of " + className,
                        Severity.ERROR, className, null, null);
            }
        }

        return true;
    }
}
