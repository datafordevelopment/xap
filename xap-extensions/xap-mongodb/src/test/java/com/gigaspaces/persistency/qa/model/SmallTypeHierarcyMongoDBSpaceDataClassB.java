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

package com.gigaspaces.persistency.qa.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceProperty;

@SpaceClass
public class SmallTypeHierarcyMongoDBSpaceDataClassB extends
        SmallTypeHierarcyMongoDBSpaceDataClassA {
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((bProp == null) ? 0 : bProp.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SmallTypeHierarcyMongoDBSpaceDataClassB other = (SmallTypeHierarcyMongoDBSpaceDataClassB) obj;
        if (bProp == null) {
            if (other.bProp != null)
                return false;
        } else if (!bProp.equals(other.bProp))
            return false;
        return true;
    }

    private String bProp;

    @SpaceProperty
    public String getBProp() {
        return bProp;
    }

    public void setBProp(String bProp) {
        this.bProp = bProp;
    }
}
