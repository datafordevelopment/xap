/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigaspaces.internal.transport;

import com.gigaspaces.document.DocumentProperties;
import com.gigaspaces.internal.metadata.ITypeDesc;
import com.gigaspaces.internal.server.storage.IEntryData;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

/**
 * Contains projection information and logic
 *
 * @author yechiel
 * @since 9.7
 */

public abstract class AbstractProjectionTemplate implements Externalizable {
    private static final long serialVersionUID = 2034439490260048928L;

    private transient volatile PathsProjectionHandler _pathsHandler;


    public AbstractProjectionTemplate() {
    }

    /**
     * returns projection of fixed properties, null indicates no projection is requested of fixed
     * properties.
     */
    public abstract int[] getFixedPropertiesIndexes();

    /**
     * return projection of dynamic properties, null indicates no projection is requested of dynamic
     * properties.
     */
    public abstract String[] getDynamicProperties();

    /**
     * returns projection of  fixed properties that include paths, null indicates no projection is
     * requested of fixed properties.
     */
    public abstract String[] getFixedPaths();

    /**
     * returns projection of  dynamic properties that include paths, null indicates no projection is
     * requested of fixed properties.
     */
    public abstract String[] getDynamicPaths();

    /**
     * given a bentry-packet perform projection on it
     */
    public void filterOutNonProjectionProperties(
            final IEntryPacket entryPacket) {
        if (entryPacket == null) {
            return;
        }

        PathsProjectionHandler pathsHandler = null;
        int[] fixedPropertiesIndexes = getFixedPropertiesIndexes();
        if (entryPacket.getFieldValues() != null) {
            final int numOfFixedProperties = entryPacket.getTypeDescriptor().getNumOfFixedProperties();
            final Object[] projectedValues = new Object[numOfFixedProperties];
            if (fixedPropertiesIndexes != null) {
                for (int index : fixedPropertiesIndexes)
                    projectedValues[index] = entryPacket.getFieldValue(index);
            }
            if (getFixedPaths() != null) {
                pathsHandler = getPathsHandler(entryPacket.getTypeDescriptor());
                pathsHandler.applyFixedPathsProjections(entryPacket, projectedValues);
            }
            entryPacket.setFieldsValues(projectedValues);
        }

        if (entryPacket.getDynamicProperties() != null) {
            Map<String, Object> projectedDynamicProperties = null;
            if (getDynamicProperties() != null) {
                projectedDynamicProperties = new DocumentProperties(getDynamicProperties().length);
                for (String dynamicProperty : getDynamicProperties())
                    projectedDynamicProperties.put(dynamicProperty, entryPacket.getDynamicProperties().get(dynamicProperty));
            }
            if (getDynamicPaths() != null) {
                if (projectedDynamicProperties == null)
                    projectedDynamicProperties = new DocumentProperties(getDynamicPaths().length);
                if (pathsHandler == null)
                    pathsHandler = getPathsHandler(entryPacket.getTypeDescriptor());
                pathsHandler.applyDynamicPathsProjections(entryPacket, projectedDynamicProperties);
            }
            entryPacket.setDynamicProperties(projectedDynamicProperties);
        }
    }

    public void filterOutNonProjectionProperties(final IEntryData entryData) {
        if (entryData == null) return;

        PathsProjectionHandler pathsHandler = null;
        int[] fixedPropertiesIndexes = getFixedPropertiesIndexes();

        if (entryData.getFixedPropertiesValues() != null) {
            final int numberOfFixedProperties = entryData.getNumOfFixedProperties();
            final Object[] projectedValues = new Object[numberOfFixedProperties];
            if (fixedPropertiesIndexes != null) {
                for (int index : fixedPropertiesIndexes) {
                    projectedValues[index] = entryData.getFixedPropertiesValues()[index];
                }
            }
            if (getFixedPaths() != null) {
                pathsHandler = getPathsHandler(entryData.getEntryTypeDesc().getTypeDesc());
                pathsHandler.applyFixedPathsProjections(entryData, projectedValues);
            }
            entryData.setFixedPropertyValues(projectedValues);
        }
    }

    private PathsProjectionHandler getPathsHandler(ITypeDesc typeDesc) {
        if (getFixedPaths() == null && getDynamicPaths() == null)
            return null;
        PathsProjectionHandler pathsHandler = _pathsHandler;
        if (pathsHandler != null)
            return pathsHandler;
        //create it
        Object lockObject = getFixedPaths() != null ? getFixedPaths() : getDynamicPaths();
        synchronized (lockObject) //used as a sync object
        {
            if (_pathsHandler != null)
                return _pathsHandler;
            _pathsHandler = new PathsProjectionHandler(getFixedPaths(), getDynamicPaths(), typeDesc);
            return _pathsHandler;
        }
    }


    @Override
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
    }

    @Override
    public void writeExternal(ObjectOutput out)
            throws IOException {
    }
}
