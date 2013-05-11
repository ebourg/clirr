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

package net.sf.clirr.core.internal.asm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.clirr.core.spi.JavaType;

import org.objectweb.asm.ClassReader;

/**
 * Stores all known JavaTypes, used to implement cross references between types.
 *
 * @author lkuehne
 */
class Repository
{
    private static final Pattern PRIMITIVE_PATTERN = Pattern.compile("(int|float|long|double|boolean|char|short|byte)");
    private static final Pattern ARRAY_PATTERN = Pattern.compile("(\\[\\])+$");

    private final ClassLoader classLoader;
    private final Map nameTypeMap = new HashMap();
    private final int primitiveClassFormatVersion;

    public Repository(ClassLoader classLoader)
    {
        this.classLoader = classLoader;
        primitiveClassFormatVersion = findTypeByName("java.lang.Integer").getClassFormatVersion();
    }

    /**
     * @param is
     * @return
     * @throws IOException
     */
    AsmJavaType readJavaTypeFromStream(InputStream is) throws IOException
    {
        ClassReader parser = new ClassReader(is);

        ClassInfoCollector infoCollector = new ClassInfoCollector(this);

        // TODO: Code for ASM 3.0: parser.accept(infoCollector, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG);
        parser.accept(infoCollector, true);

        final AsmJavaType javaType = infoCollector.getJavaType();

        nameTypeMap.put(javaType.getName(), javaType);
        return javaType;
    }

    public JavaType findTypeByName(String fullTypeName)
    {
        // separate basic typeName and array brackets
        final Matcher arrayMatcher = ARRAY_PATTERN.matcher(fullTypeName);
        final String typeName;
        final int dimension;
        if (arrayMatcher.find())
        {
            String brackets = arrayMatcher.group();
            typeName = fullTypeName.substring(0, fullTypeName.length() - brackets.length());
            dimension = brackets.length() / 2;
        }
        else
        {
            typeName = fullTypeName;
            dimension = 0;
        }

        // search cache for basic typeName
        JavaType type = (JavaType) nameTypeMap.get(typeName);
        if (type != null)
        {
            return wrapInArrayTypeIfRequired(dimension, type);
        }

        // OK, typeName is not in the cache. Is it a primitive type?
        final Matcher primitiveMatcher = PRIMITIVE_PATTERN.matcher(typeName);
        if (primitiveMatcher.matches())
        {

            JavaType primitive = new PrimitiveType(primitiveClassFormatVersion, typeName);
            nameTypeMap.put(typeName, primitive);
            return wrapInArrayTypeIfRequired(dimension, primitive);
        }

        // it must be a normal class then, load it as a resource
        String resourceName = typeName.replace('.', '/') + ".class";
        InputStream is = classLoader.getResourceAsStream(resourceName);
        if (is == null)
        {
            reportTypeUnknownInClassLoader(typeName);
        }
        try
        {
            final AsmJavaType javaType = readJavaTypeFromStream(is);
            return wrapInArrayTypeIfRequired(dimension, javaType);
        }
        catch (IOException ex)
        {
            throw new IllegalStateException();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    /**
     * @param dimension
     * @param javaType
     * @return
     */
    private JavaType wrapInArrayTypeIfRequired(final int dimension, final JavaType javaType)
    {
        if (dimension == 0)
        {
            return javaType;
        }
        final ArrayType arrayType = new ArrayType(javaType, dimension);
        return arrayType;
    }

    /**
     * @param typeName
     */
    private void reportTypeUnknownInClassLoader(final String typeName)
    {
        String clDetails;
        if (classLoader instanceof URLClassLoader)
        {
            URLClassLoader ucl = (URLClassLoader) classLoader;
            clDetails = String.valueOf(Arrays.asList(ucl.getURLs()));
        }
        else
        {
            clDetails = String.valueOf(classLoader);
        }
        throw new IllegalArgumentException("Type " + typeName + " is unknown in classLoader " + clDetails);
    }

}
