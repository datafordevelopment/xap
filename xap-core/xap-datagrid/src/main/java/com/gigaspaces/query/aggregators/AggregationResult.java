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


package com.gigaspaces.query.aggregators;

import com.gigaspaces.query.CompoundResult;

import java.util.Map;

/**
 * @author Niv Ingberg
 * @since 10.0
 */

public class AggregationResult extends CompoundResult {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor required for Externalizable
     */
    public AggregationResult() {
    }

    /**
     * This constructor is intended for internal usage.
     */
    public AggregationResult(Object[] values, Map<String, Integer> nameIndexMap) {
        super(values, nameIndexMap);
    }
}
