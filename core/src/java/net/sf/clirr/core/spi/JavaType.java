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

package net.sf.clirr.core.spi;

/**
 * A Java Type (Object, Interface, primitive type or void).
 *
 * @author lkuehne
 */
public interface JavaType extends Named, Scoped
{
    /**
     * The class format version of this class's bytecode.
     * @return the class format version, like 49 for code that has been
     * generated with the Java5 compiler.
     */
    int getClassFormatVersion();

    /**
     * The type's fully qualified class name.
     * In case of array types, this is the name without the array brackets
     *
     * @return a fully qualified class name,
     * like <code>"my.company.procuct.SampleClass"</code>.
     */
    String getBasicName();

    /**
     * The type's fully qualified class name.
     * In case of array types, this is the name with the array brackets.
     *
     * @return a fully qualified class name,
     * like <code>"my.company.procuct.SampleClass"</code>.
     */
    String getName();

    /**
     * The containing class if this is an inner class.
     *
     * @return the containing class or <code>null</code>
     * if this JavaType does not represent an inner class.
     */
    JavaType getContainingClass();


    /**
     * Return the superclasses of this class.
     *
     * @return the chain of superclasses of this type, starting from
     * the direct superclass and ending with <code>java.lang.Object</code>.
     */
    JavaType[] getSuperClasses();

    /**
     * Return the list of all interfaces this class implements.
     *
     * @return the list of all interfaces this class implements/extends,
     * excluding <code>this</code> if this JavaType represents an interface itself.
     */
    JavaType[] getAllInterfaces();

    /**
     * All methods that are declared by this class.
     * Methods of superclasses/interfaces are not returned
     * if they are not overridden/redeclared here.
     *
     * @return all methods that are declared by this class.
     */
    Method[] getMethods();

    /**
     * All fields that are declared by this class.
     * Fields of superclasses/interfaces are not returned.
     *
     * @return all fields that are declared by this class.
     */
    Field[] getFields();

    /**
     * The number of array dimensions this type has.
     * @return 0 if this type does not represent an array.
     */
    int getArrayDimension();

    /**
     * Whether this type represents a primitive type like <code>int</code>.
     * @return true iff this type represents a primitive type.
     */
    boolean isPrimitive();

    /**
     * Whether this class is declared as final.
     * @return true iff this type represents a final class or a {@link #isPrimitive() primitive} type.
     */
    boolean isFinal();

    /**
     * Whether this type represents a class that is declared as abstract.
     * Note that interfaces are not abstract.
     *
     * @return true iff this type represents an abstract class.
     */
    boolean isAbstract();

    /**
     * Whether this type represents an interface.
     *
     * @return true iff this type represents an interface.
     */
    boolean isInterface();
}
