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

package net.sf.clirr.core.internal.checks;

import java.util.Set;
import java.util.TreeSet;

import net.sf.clirr.core.Severity;
import net.sf.clirr.core.Message;
import net.sf.clirr.core.internal.AbstractDiffReporter;
import net.sf.clirr.core.internal.ApiDiffDispatcher;
import net.sf.clirr.core.internal.ClassChangeCheck;
import net.sf.clirr.core.internal.CoIterator;
import net.sf.clirr.core.internal.JavaClassNameComparator;
import org.apache.bcel.classfile.JavaClass;

/**
 * Detects changes in the set of interfaces implemented by a class.
 *
 * @author lkuehne
 */
public final class InterfaceSetCheck
    extends AbstractDiffReporter
    implements ClassChangeCheck
{
    private static final Message MSG_IFACE_ADDED = new Message(4000);
    private static final Message MSG_IFACE_REMOVED = new Message(4001);

    /**
     * Create a new instance of this check.
     * @param dispatcher the diff dispatcher that distributes the detected changes to the listeners.
     */
    public InterfaceSetCheck(ApiDiffDispatcher dispatcher)
    {
        super(dispatcher);
    }

    /** {@inheritDoc} */
    public boolean check(JavaClass compatBaseline, JavaClass currentVersion)
    {
        JavaClass[] compatInterfaces = compatBaseline.getAllInterfaces();
        JavaClass[] currentInterfaces = currentVersion.getAllInterfaces();

        // Note: getAllInterfaces might return multiple array entries with the same
        // interface, so we need to use sets to remove duplicates...
        Set compat = createClassSet(compatInterfaces);
        Set current = createClassSet(currentInterfaces);

        final String className = compatBaseline.getClassName();

        CoIterator iter = new CoIterator(
            JavaClassNameComparator.COMPARATOR, compat, current);

        while (iter.hasNext())
        {
            iter.next();

            JavaClass compatInterface = (JavaClass) iter.getLeft();
            JavaClass currentInterface = (JavaClass) iter.getRight();

            if (compatInterface != null && className.equals(compatInterface.getClassName())
                || currentInterface != null && className.equals(currentInterface.getClassName()))
            {
                // This occurs because an interface has itself in the set of all interfaces.
                // We can't just let the test below handle this case because that won't
                // work when a gender change has occurred.
                continue;
            }

            if (compatInterface == null)
            {
                // TODO: check whether the class already implements
                // throwable. If so, this should probably be a warning,
                // because the presence of this extra interface could
                // change exception-catching behaviour.
                //
                // Actually, it could also change code which uses
                // "instance-of" and similar methods too, even when not
                // a throwable. However this is fairly low probability..
                log(MSG_IFACE_ADDED,
                        Severity.INFO, className, null, null,
                        new String[] {currentInterface.getClassName()});
            }
            else if (currentInterface == null)
            {
                log(MSG_IFACE_REMOVED,
                        getSeverity(compatBaseline, Severity.ERROR),
                        className, null, null,
                        new String[] {compatInterface.getClassName()});
            }
        }

        return true;
    }

    /**
     * Creates a Set of JavaClass objects.
     * @param classes the classes to include in the set, might contain duplicates
     * @return Set<JavaClass>
     */
    private Set createClassSet(JavaClass[] classes)
    {
        // JavaClass does not define equals(), so we use a Set implementation
        // that determines equality by invoking a Comparator instead of calling equals()

        Set current = new TreeSet(JavaClassNameComparator.COMPARATOR);
        for (int i = 0; i < classes.length; i++)
        {
            current.add(classes[i]);
        }
        return current;
    }
}
